package controllers

import dto.{CartDTO, NewChosenProductsDTO}
import models.AccType
import play.api.mvc.{AbstractController, BodyParsers, ControllerComponents}
import security.{AuthAction, JwtAuthorization}
import services.OrderService
import play.api.libs.json._

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import utils.Constants

@Singleton
class OrderController @Inject() (
    cc: ControllerComponents,
    orderService: OrderService
)(implicit
    bodyParser: BodyParsers.Default,
    ec: ExecutionContext
) extends AbstractController(cc) {

  private val regularAction = new AuthAction(Seq(AccType.regularUser))
  private val adminAction = new AuthAction(Seq(AccType.admin))

  def getOrders(sortBy: String) = regularAction.async { implicit request =>
    orderService.getOrders(sortBy, request.userId).map(o => Ok(Json.toJson(o)))
  }

  def getAllOrders(sortBy: String) = adminAction.async { implicit request =>
    orderService.getAllOrders(sortBy).map(o => Ok(Json.toJson(o)))
  }

  def getFullOrderData(orderId: Int) = regularAction.async { implicit request =>
    orderService.getOrderWithUserAndProduct(orderId, request.userId).map(o => Ok(Json.toJson(o)))
  }

  //dto da se kreira koji ima seq i onda min lenght da se definise za json reads i bacice bad request
  def createOrder() = regularAction.async(parse.json[CartDTO]) { implicit request =>
    orderService.createOrder(userId = request.userId, request.body.products).map { o =>
      if (o == -1) BadRequest("Oops. You cannot buy product that is deleted or out of stock.")
      else Ok(Json.toJson(o))
    }
  }

}
