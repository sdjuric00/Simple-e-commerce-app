package dto

import models.Review
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class NewReviewDTO(productId: Int, rate: Int)

object NewReviewDTO {

  def toDTO(review: Review) = {
    NewReviewDTO(review.productId, review.rate)
  }

  implicit val newReviewFormat: Format[NewReviewDTO] = (
    (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "rate").format[Int](min(1).keepAnd(max(5)))
  )(NewReviewDTO.apply, unlift(NewReviewDTO.unapply))

}
