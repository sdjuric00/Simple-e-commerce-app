package dto

import models.AccType.AccType
import models.AccType.AccType
import models.User
import play.api.libs.json.{Format, Json}

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

import java.time.LocalDate

case class JwtTokenDTO(userId: Int, email: String, role: AccType)

object JwtTokenDTO {

  def toJwtDTO(obj: JsObject): JwtTokenDTO = {
    obj.validate[JwtTokenDTO].get
  }

  implicit val toJwtTokenFormat: Format[JwtTokenDTO] = (
    (JsPath \ "userId").format[Int](min(0)) and
    (JsPath \ "email").format[String](email) and
    (JsPath \ "role").format[AccType]
    )(JwtTokenDTO.apply, unlift(JwtTokenDTO.unapply))

}
