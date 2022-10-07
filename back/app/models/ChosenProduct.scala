package models

import play.api.libs.json.{Format, Json}
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class ChosenProduct(id: Int, productId: Int, orderId: Int, quantity: Int, price: Double, product: models.Product) {

  def totalPrice(): Double = { quantity * price }

}

object ChosenProduct {

  implicit val chosenProductFormat: Format[ChosenProduct] = (
    (JsPath \ "id").format[Int](min(0)) and
      (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "quantity").format[Int](min(0)) and
      (JsPath \ "price").format[Double] and
      (JsPath \ "product").format[models.Product]
    )(ChosenProduct.apply, unlift(ChosenProduct.unapply))

}


