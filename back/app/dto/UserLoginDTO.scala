package dto

import play.api.libs.json.{Format, Json}

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class UserLoginDTO(email: String, password: String)

object UserLoginDTO {

  implicit val userLoginFormat: Format[UserLoginDTO] = (
      (JsPath \ "email").format[String](email) and
      (JsPath \ "password").format[String](minLength[String](5))
    )(UserLoginDTO.apply, unlift(UserLoginDTO.unapply))

}



