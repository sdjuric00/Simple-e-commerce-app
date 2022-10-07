package dto

import models.AccType.AccType
import models.User
import play.api.libs.json.{Format, Json}

import java.time.LocalDate

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class UserWithTokenDTO(id: Int, email: String, firstName: String, lastName: String,
                            dateOfBirth: LocalDate, image: Option[String], accType: AccType, active: Boolean, token: String)

object UserWithTokenDTO {

  def toUserWithTokenDTO(user: User, token: String): UserWithTokenDTO = {
    UserWithTokenDTO(user.id, user.email, user.firstName, user.lastName, user.dateOfBirth, user.image, user.accType, user.active, token)
  }

  implicit val userWithTokenFormat: Format[UserWithTokenDTO] = (
      (JsPath \ "id").format[Int](min(0)) and
      (JsPath \ "email").format[String](email) and
      (JsPath \ "firstName").format[String](minLength[String](1)) and
      (JsPath \ "lastName").format[String](minLength[String](1)) and
      (JsPath \ "dateOfBirth").format[LocalDate] and
      (JsPath \ "image").formatNullable[String] and
      (JsPath \ "accType").format[AccType] and
      (JsPath \ "active").format[Boolean] and
      (JsPath \ "token").format[String]
    )(UserWithTokenDTO.apply, unlift(UserWithTokenDTO.unapply))

}
