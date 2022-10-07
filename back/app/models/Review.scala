package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._
import java.time.LocalDateTime

case class Review(reviewId: Int, userId: Int, productId: Int, rate: Int, time: LocalDateTime, user: Option[User], product: Option[Product])

object Review {

  implicit val reviewFormat: Format[Review] = (
    (JsPath \ "reviewId").format[Int](min(0)) and
      (JsPath \ "userId").format[Int](min(0)) and
      (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "rate").format[Int](min(1).keepAnd(max(5))) and
      (JsPath \ "time").format[LocalDateTime] and
      (JsPath \ "user").formatNullable[User] and
      (JsPath \ "product").formatNullable[Product]
  )(Review.apply, unlift(Review.unapply))

}
