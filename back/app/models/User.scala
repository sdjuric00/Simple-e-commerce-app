package models

import models.AccType.AccType
import play.api.libs.json.{Format, Json}

import java.time.LocalDate

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class User(id: Int, email: String, password: String, firstName: String, lastName: String,
                dateOfBirth: LocalDate, image: Option[String], accType: AccType, active: Boolean)

object User {

  implicit val userFormat: Format[User] = (
    (JsPath \ "id").format[Int](min(0)) and
      (JsPath \ "email").format[String](email) and
      (JsPath \ "password").format[String](minLength[String](5)) and
      (JsPath \ "firstName").format[String](minLength[String](1)) and
      (JsPath \ "lastName").format[String](minLength[String](1)) and
      (JsPath \ "dateOfBirth").format[LocalDate] and
      (JsPath \ "image").formatNullable[String] and
      (JsPath \ "accType").format[AccType] and
      (JsPath \ "active").format[Boolean]
    )(User.apply, unlift(User.unapply))

}
