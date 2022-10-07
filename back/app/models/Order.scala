package models

import play.api.libs.json.{Format, Json}

import java.time.LocalDateTime
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class Order(orderId: Int, userId: Int, products: Seq[ChosenProduct], user: Option[User], time: LocalDateTime, total: Double)

object Order {

  implicit val orderFormat: Format[Order] = (
    (JsPath \ "orderId").format[Int](min(0)) and
      (JsPath \ "userId").format[Int](min(0)) and
      (JsPath \ "products").format[Seq[ChosenProduct]] and
      (JsPath \ "user").formatNullable[User] and
      (JsPath \ "time").format[LocalDateTime] and
      (JsPath \ "total").format[Double]
  )(Order.apply, unlift(Order.unapply))

}
