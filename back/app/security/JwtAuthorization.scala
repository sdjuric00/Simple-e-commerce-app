package security

import dto.JwtTokenDTO
import models.AccType.AccType
import models.User

import java.time.Clock
import pdi.jwt.{JwtAlgorithm, JwtClaim, JwtJson}
import play.api.libs.json.{JsObject, Json}
import utils.Constants

import scala.util.{Failure, Success}

case class JwtAuthorization()

object JwtAuthorization {

  implicit val clock: Clock = Clock.systemUTC

  val key = "secretKey"

  val algo = JwtAlgorithm.HS256

  def createClaim(user: User): JwtClaim = {
     val content = Json.obj("userId"-> user.id, "role" -> user.accType)
    JwtClaim(content = content.toString).expiresIn(3600 * 24)
  }

  def encode(claim: JwtClaim): String = {
    JwtJson.encode(claim, key, algo)
  }

  def checkRole(userRole: AccType, expectedRole: AccType) = {
    userRole == expectedRole
  }

  def decodeJSON(tokenOpt: Option[String]): Either[Int, (Int, AccType)] = {
    tokenOpt.flatMap(_.split(" ").lift(1).flatMap(t => JwtJson.decodeJson(t, key, Seq(algo)).toOption).map {
      valueObj => {
        val idOpt = (valueObj \ "userId").asOpt[Int]
        val roleOpt = (valueObj \ "role").asOpt[AccType]
        (idOpt, roleOpt) match {
          case Some(id) -> Some(role) => Right(id, role)
          case _ => Left(Constants.nonAuthorised)
        }
      }
    }).getOrElse(Left(Constants.nonAuthorised))
  }


}
