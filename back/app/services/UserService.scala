package services

import akka.actor.Status.Success
import dto.{NewUserDTO, UserDTO}
import repository.{UserRepository, VerifyRepository}
import models.{User, Verify}
import play.api.libs.Files
import play.api.mvc.MultipartFormData
import utils.Constants

import java.nio.file.Paths
import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Random

class UserService @Inject() (userRepository: UserRepository, verifyRepository: VerifyRepository)(implicit ec: ExecutionContext) {

  def findUserById(id: Int): Future[Option[User]] = {
    userRepository.findUserById(id)
  }

  def findUserByEmail(email: String): Future[Option[User]] = {
    userRepository.findUserByEmail(email)
  }

  def getAllUsers(): Future[Seq[User]] = {
    userRepository.getAllUsers()
  }

  def getAllActiveUsers(): Future[Seq[User]] = {
    userRepository.getActiveUsers()
  }

  def login(email: String, password: String): Future[Option[User]] = {
    userRepository.login(email, password)
  }

  def register(newUser: User, data: Option[MultipartFormData.FilePart[Files.TemporaryFile]]): Future[Option[User]] = {
    saveImage(data, newUser.email).flatMap { result =>
      if (result) userRepository.register(newUser)
      else Future.successful(None)
    }
  }

  def update(user: UserDTO, userId: Int): Future[Option[User]] = {
    userRepository.update(user, userId)
  }

  def activateAcc(email: String, code: Int): Future[Boolean] = {
    verifyRepository.checkActivation(email, code).flatMap { res =>
      if (res) userRepository.activateAcc(email)
      else Future.successful(false)
    }
  }

  def generateRandonCode(email: String): Future[Option[Verify]] = {
    val code = Random.between(Constants.minCodeGenNum, Constants.maxCodeGenNum)
    verifyRepository.create(email, code)
  }

  def saveImage(data: Option[MultipartFormData.FilePart[Files.TemporaryFile]], email: String): Future[Boolean] = {
    data
      .map { picture =>
        picture.ref.moveTo(Paths.get(s"public/images/${email}-profile-pic.png"), replace = true)
        Future.successful(true)
      }
      .getOrElse {
        Future.successful(false)
      }
  }

}
