package models

import play.api.libs.json.{Format, JsString, JsValue, Json}

object ProductCategory extends Enumeration {

  type ProductCategory = Value

  val sweats = Value("SWEATS")
  val drinks = Value("DRINKS")
  val milkProducts = Value("MILK_PRODUCTS")
  val meatProducts = Value("MEAT_PRODUCTS")

  implicit val format: Format[ProductCategory] = Json.formatEnum(this)

}
