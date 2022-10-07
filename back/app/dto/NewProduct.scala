package dto

import models.Product
import models.ProductCategory.ProductCategory
import play.api.libs.json.Reads.{maxLength, min, minLength}
import play.api.libs.json.{Format, JsPath, Json}

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class NewProduct(name: String, category: ProductCategory, price: Double, quantity: Int, sold: Int,
                      image: Option[String], description: Option[String])

object NewProduct {

  def toProduct(p: NewProduct): Product = {
    Product(productId = -1, p.name, p.category, p.price, p.quantity, p.sold, p.image, p.description, deleted = false)
  }

  def toNewProduct(p: Product) = {
    NewProduct(p.name, p.category, p.price, p.quantity, p.sold, p.image, p.description)
  }

  implicit val newProductFormat: Format[NewProduct] = (
      (JsPath \ "name").format[String](minLength[String](5).keepAnd(maxLength[String](30))) and
      (JsPath \ "category").format[ProductCategory] and
      (JsPath \ "price").format[Double] and
      (JsPath \ "quantity").format[Int](min(0)) and
      (JsPath \ "sold").format[Int](min(0)) and
      (JsPath \ "image").formatNullable[String] and
      (JsPath \ "description").formatNullable[String]
    )(NewProduct.apply, unlift(NewProduct.unapply))

}
