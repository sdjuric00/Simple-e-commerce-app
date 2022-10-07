package models

import play.api.libs.json.{Format, JsString, JsValue, Json}

object AccType extends Enumeration {

  type AccType = Value

  val admin = Value("ADMIN")
  val regularUser = Value("REGULAR_USER")

  implicit val format: Format[AccType] = Json.formatEnum(this)

}
