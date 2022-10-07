package dto
import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class CartDTO(email: String, products: Seq[NewChosenProductsDTO])

object CartDTO {

  implicit val CartDTOFormat: Format[CartDTO] = (
    (JsPath \ "email").format[String](email) and
      (JsPath \ "products").format[Seq[NewChosenProductsDTO]](minLength[Seq[NewChosenProductsDTO]](1))
  )(CartDTO.apply, unlift(CartDTO.unapply))

}
