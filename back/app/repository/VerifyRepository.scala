package repository

import org.postgresql.util.PSQLException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import repository.Tables._
import slick.jdbc.PostgresProfile.api._

@Singleton
class VerifyRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def converter(ver: Verify#TableElementType): models.Verify = {
    models.Verify(ver.verifyId, ver.email, ver.code, ver.numTries)
  }

  def findByEmail(email: String): Future[Option[models.Verify]] = {
    db.run(Verify.filter(row => row.email === email).result).map(_.map(converter).headOption)
  }

  def create(email: String, code: Int): Future[Option[models.Verify]] = {
    val action = (Verify returning Verify.map(_.verifyId)
      into ((verify, id) => verify.copy(verifyId = id))) +=
      VerifyRow(-1, email, code, 0)
    db.run(action.map(converter).map(Some(_))).recover { case e: PSQLException =>
      None
    }
  }

  def checkActivation(email: String, code: Int): Future[Boolean] = {
    val action = (Verify
      .filter(_.email === email)
      .filter(_.code === code)
      .filter(_.numTries <= 3)
      .map(res => (res.numTries))
      .update(+1))
    db.run(action).map(count => count > 0).recover { case e: PSQLException => false }
  }

}
