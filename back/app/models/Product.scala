package models

import models.ProductCategory.ProductCategory
import play.api.libs.json.{Format, Json}

import play.api.libs.json._
import play.api.libs.functional.syntax._
import play.api.libs.json.Reads._

case class Product(productId: Int, name: String, category: ProductCategory, price: Double, quantity: Int, sold: Int,
                   image: Option[String], description: Option[String], deleted: Boolean)


object Product {

  implicit val productFormat: Format[Product] = (
    (JsPath \ "productId").format[Int](min(0)) and
      (JsPath \ "name").format[String](minLength[String](5).keepAnd(maxLength[String](30))) and
      (JsPath \ "category").format[ProductCategory] and
      (JsPath \ "price").format[Double] and
      (JsPath \ "quantity").format[Int](min(0)) and
      (JsPath \ "sold").format[Int](min(0)) and
      (JsPath \ "image").formatNullable[String] and
      (JsPath \ "description").formatNullable[String] and
      (JsPath \ "deleted").format[Boolean]
  )(Product.apply, unlift(Product.unapply))

}

