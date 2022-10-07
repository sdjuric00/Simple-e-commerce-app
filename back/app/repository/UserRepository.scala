package repository

import dto.{NewUserDTO, UserDTO}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{Await, ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
import repository.Tables._
import models.{AccType, User}
import org.postgresql.util.PSQLException

import scala.concurrent.duration.Duration

@Singleton
class UserRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def converter(u: Users#TableElementType): User = {
    User(u.userId, u.email, u.password, u.firstName, u.lastName, u.dateOfBirth.toLocalDate, u.image, AccType.withName(u.accType), u.active)
  }

  def convert(f: () => Future[Int]): Future[() => Int] =
    Future { () =>
      Await.result(f(), Duration.Inf)
    }

  def getAllUsers(): Future[Seq[User]] = {
    db.run(Users.sortBy(_.userId.desc).result).map(_.map(converter))
  }

  def getActiveUsers(): Future[Seq[User]] = {
    db.run(Users.sortBy(_.userId.asc).filter(_.active === true).result).map(_.map(converter))
  }

  def findUserById(id: Int): Future[Option[User]] = {
    db.run(Users.filter(userRow => userRow.userId === id).result).map(_.map(converter).headOption)
  }

  def findUserByEmail(email: String): Future[Option[User]] = {
    db.run(Users.filter(userRow => userRow.email === email).result).map(_.map(converter).headOption)
  }

  def login(email: String, password: String): Future[Option[User]] = {
    db.run(Users.filter(userRow => userRow.email === email && userRow.password === password && userRow.active).result)
      .map(_.map(converter).headOption)
      .recover { case e: PSQLException => None }
  }

  //registration for regular user
  def register(newUser: User): Future[Option[User]] = {
    val user = UsersRow(
      newUser.id,
      newUser.email,
      newUser.password,
      newUser.firstName,
      newUser.lastName,
      java.sql.Date.valueOf(newUser.dateOfBirth),
      newUser.image,
      newUser.accType.toString,
      newUser.active
    )
    val usersWithEmail =
      (Users returning Users.map(_.email)
        into ((u, uEmail) => u.copy(email = uEmail))) += user
    db.run(usersWithEmail.map(converter).map(Some(_))).recover { case e: PSQLException =>
      None
    }
  }

  //update svega sem lozinke
  def update(user: UserDTO, userId: Int): Future[Option[User]] = {
    val action = Users.filter(_.userId === userId).map(u => (u.firstName, u.lastName, u.image)).update(user.firstName, user.lastName, user.image)
    db.run(action).recover { case e: PSQLException =>
      None
    }
    findUserById(user.id)
  }

  def activateAcc(email: String): Future[Boolean] = {
    val action = for { u <- Users if u.email === email } yield u.active
    db.run(action.update(true)).map(count => count > 0)
  }

}
