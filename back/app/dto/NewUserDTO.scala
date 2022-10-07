package dto

import models.{AccType, User}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

import java.time.LocalDate
import java.time.format.DateTimeFormatter

case class NewUserDTO(
    email: String,
    password: String,
    firstName: String,
    lastName: String,
    dateOfBirth: LocalDate,
    image: Option[String]
)

object NewUserDTO {

  def toNewUserDTO(user: User): NewUserDTO = {
    NewUserDTO(user.email, user.password, user.firstName, user.lastName, user.dateOfBirth, user.image)
  }

  def apply(
      email: Option[String],
      password: Option[String],
      firstName: Option[String],
      lastName: Option[String],
      dateOfBirth: Option[String]
  ): NewUserDTO = {
    require(email.isDefined && password.isDefined && firstName.isDefined && lastName.isDefined && dateOfBirth.isDefined)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    NewUserDTO(
      email.get,
      password.get,
      firstName.get,
      lastName.get,
      LocalDate.parse(dateOfBirth.get, formatter),
      Some(email.get + "profile-pic.png")
    )
  }

  def toNewRegularUser(newUserDTO: NewUserDTO): User = {
    User(
      id = -1,
      newUserDTO.email,
      newUserDTO.password,
      newUserDTO.firstName,
      newUserDTO.lastName,
      newUserDTO.dateOfBirth,
      newUserDTO.image,
      AccType.regularUser,
      active = false
    )
  }

  implicit val newUserFormat: Format[NewUserDTO] = (
    (JsPath \ "email").format[String](email) and
      (JsPath \ "password").format[String](minLength[String](5)) and
      (JsPath \ "firstName").format[String](minLength[String](1)) and
      (JsPath \ "lastName").format[String](minLength[String](1)) and
      (JsPath \ "dateOfBirth").format[LocalDate] and
      (JsPath \ "image").formatNullable[String]
  )(NewUserDTO.apply, unlift(NewUserDTO.unapply))

}
