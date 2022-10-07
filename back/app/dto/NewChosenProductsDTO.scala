package dto

import models.ChosenProduct
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class NewChosenProductsDTO(productId: Int, quantity: Int, price: Double) {

  def totalPrice(): Double = { quantity * price }

}

object NewChosenProductsDTO {

  def toDTO(chosenProduct: ChosenProduct) = {
    NewChosenProductsDTO(chosenProduct.productId, chosenProduct.quantity, chosenProduct.price)
  }

  implicit val newChosenProductsFormat: Format[NewChosenProductsDTO] = (
      (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "quantity").format[Int](min(0)) and
      (JsPath \ "price").format[Double]
    )(NewChosenProductsDTO.apply, unlift(NewChosenProductsDTO.unapply))

}
