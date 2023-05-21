package controllers

import dto.NewUserDTO.toNewRegularUser
import dto.{JwtTokenDTO, NewProduct, NewUserDTO, UserDTO, UserLoginDTO, UserWithTokenDTO, VerifyDTO}
import models.AccType
import play.api.mvc.{AbstractController, BodyParsers, ControllerComponents}
import services.UserService
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import models.User.userFormat
import security.{AuthAction, JwtAuthorization}

@Singleton
class UserController @Inject() (cc: ControllerComponents, userService: UserService, mailer: MailService)(implicit
    bodyParser: BodyParsers.Default,
    ec: ExecutionContext
) extends AbstractController(cc) {

  private val allAction = new AuthAction(Seq(AccType.regularUser, AccType.regularUser))
  private val regularAction = new AuthAction(Seq(AccType.regularUser))

  def findUserById(id: Int) = allAction.async { request =>
    userService.findUserById(id).map {
      case Some(user) => {
        Ok(Json.toJson(UserDTO.toUserDTO(user)))
      }
      case None => BadRequest(s"User with id ${id} doesn't exist")
    }
  }

  def findUserByEmail(email: String) = allAction.async { request =>
    userService.findUserByEmail(email).map {
      case Some(user) => Ok(Json.toJson(UserDTO.toUserDTO(user)))
      case None       => BadRequest(s"User with that email ${email} doesn't exist")
    }
  }

  def activeUsers() = Action.async { request =>
    userService.getAllActiveUsers().map(u => Ok(Json.toJson(u)))
  }

  def login() = Action.async(parse.json[UserLoginDTO]) { request =>
    userService.login(request.body.email, request.body.password).map {
      case Some(user) => {
        val token = JwtAuthorization.encode(JwtAuthorization.createClaim(user))
        Ok(Json.toJson(UserWithTokenDTO.toUserWithTokenDTO(user, token)))
      }
      case None => Unauthorized("Your email or password is wrong.")
    }
  }

  def update() = allAction.async(parse.json[UserDTO]) { request =>
    userService.update(request.body, request.userId).map {
      case Some(user) => Ok(Json.toJson(UserDTO.toUserDTO(user)))
      case None       => BadRequest(s"User cannot be updated!")
    }
  }

  def activateAcc() = Action.async(parse.json[VerifyDTO]) { request =>
    userService.activateAcc(request.body.email, request.body.code).map { result =>
      if (result) Ok(Json.toJson(request.body.email))
      else BadRequest("Activation of account failed.")
    }
  }

  def register() = Action.async(parse.multipartFormData) { request =>
    createNewUserDTO(request.body.asFormUrlEncoded)
      .map { dto =>
        {
          userService.register(toNewRegularUser(dto), request.body.file("picture")).flatMap {
            case Some(user) => {
              userService.generateRandonCode(user.email).flatMap {
                case Some(ver) => {
                  Future { mailer.sendRegistrationEmail(user, ver.code) }
                  Future.successful(Ok(Json.toJson(UserDTO.toUserDTO(user))))
                }
                case None => Future.successful(Conflict(s"Cannot verify this account, email is already taken or locked."))
              }
            }
            case None => Future.successful(Conflict(s"User with email ${dto.email} already exists"))
          }
        }
      }
      .getOrElse(Future.successful(BadRequest("Data is in wrong format!")))
  }

  def createNewUserDTO(data: Map[String, Seq[String]]): Option[NewUserDTO] = {
    val email = data.get("email").flatMap(_.headOption)
    val password = data.get("password").flatMap(_.headOption)
    val firstName = data.get("firstName").flatMap(_.headOption)
    val lastName = data.get("lastName").flatMap(_.headOption)
    val dateOfBirth = data.get("dateOfBirth").flatMap(_.headOption)

    try {
      Some(NewUserDTO.apply(email = email, password = password, firstName = firstName, lastName = lastName, dateOfBirth = dateOfBirth))
    } catch {
      case e: IllegalArgumentException =>
        None
    }
  }

}
