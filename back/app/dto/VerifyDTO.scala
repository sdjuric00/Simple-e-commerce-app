package dto

import models.Verify

case class VerifyDTO(email: String, code: Int)
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

object VerifyDTO {

  def toDTO(verify: Verify): Unit = {
    VerifyDTO(verify.email, verify.code)
  }

  implicit val verifyDTOFormat: Format[VerifyDTO] = (
    (JsPath \ "email").format[String](email) and
      (JsPath \ "code").format[Int](min(10000).keepAnd(max(99999)))
  )(VerifyDTO.apply, unlift(VerifyDTO.unapply))

}
