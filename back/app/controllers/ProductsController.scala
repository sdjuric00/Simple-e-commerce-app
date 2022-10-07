package controllers

import dto.NewProduct
import dto.NewProduct.toProduct
import models.AccType
import play.api.libs.json._
import play.api.mvc._
import services.ProductsService

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import models.Product.productFormat
import security.{AuthAction, JwtAuthorization}
import utils.Constants

@Singleton
class ProductsController @Inject() (
    cc: ControllerComponents,
    productService: ProductsService
)(implicit
    bodyParser: BodyParsers.Default,
    ec: ExecutionContext
) extends AbstractController(cc) {

  private val adminAction = new AuthAction(Seq(AccType.admin))

  def withJsonBody[A](
      f: A => Result
  )(implicit request: Request[AnyContent], reads: Reads[A]) = {
    request.body.asJson
      .map { body =>
        Json.fromJson[A](body) match {
          case JsSuccess(a, path) => f(a)
          case e @ JsError(_)     => new Exception("Unparsable.")
        }
      }
      .getOrElse(new Exception("Unparsable."))
  }

  def allProducts = Action.async { request =>
    productService.getAllProducts().map(p => Ok(Json.toJson(p)))
  }

  def activeProducts = Action.async { request =>
    productService.getActiveProducts().map(p => Ok(Json.toJson(p)))
  }

  def productsByPopularity = Action.async { request =>
    productService.getProductsByPopularity().map(p => Ok(Json.toJson(p)))
  }

  def findById(id: Int) = Action.async { request =>
    productService
      .findById(id)
      .map(prod =>
        if (prod.isEmpty) BadRequest("Product with that id doesn't exist.")
        else Ok(Json.toJson(prod))
      )
  }

  def logicalDelete(id: Int) = adminAction.async { implicit request =>
    productService.logicalDelete(id).map(_ => Ok(Json.toJson(id)))
  }

  def create() = adminAction.async(parse.json[NewProduct]) { implicit request =>
    productService.create(toProduct(request.body)).map {
      case Some(product) => Ok(Json.toJson(product))
      case None          => Conflict(s"Product with name ${request.body.name} already exists.")
    }
  }

  def update() = adminAction.async(parse.json[models.Product]) { request =>
    productService.update(request.body).map {
      case Some(product) => Ok(Json.toJson(product))
      case None          => Conflict(s"Product cannot be updated. Name should be unique. All number values must be greater than 0.")
    }
  }

  def search(name: String, category: String, available: Boolean, lowToHigh: Boolean) = Action.async { request =>
    productService.search(name.toUpperCase(), category.toUpperCase(), available, lowToHigh).map(p => Ok(Json.toJson(p)))
  }

}
