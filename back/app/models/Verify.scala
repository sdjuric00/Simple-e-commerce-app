package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class Verify(verifyId: Int, email: String, code: Int, numTries: Int)

object Verify {

  implicit val verifyFormat: Format[Verify] = (
    (JsPath \ "verifyId").format[Int](min(0)) and
      (JsPath \ "email").format[String](email) and
      (JsPath \ "code").format[Int](min(10000).keepAnd(max(99999))) and
      (JsPath \ "numTries").format[Int](min(0).keepAnd(max(3)))
  )(Verify.apply, unlift(Verify.unapply))

}
