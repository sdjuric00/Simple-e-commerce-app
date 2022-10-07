package controllers

import dto.NewReviewDTO
import models.AccType
import play.api.libs.json.Json
import play.api.mvc.{AbstractController, BodyParsers, ControllerComponents}
import security.{AuthAction, JwtAuthorization}
import services.{ReviewService, UserService}
import utils.Constants

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReviewController @Inject() (cc: ControllerComponents, reviewService: ReviewService)(implicit
    bodyParser: BodyParsers.Default,
    ec: ExecutionContext
) extends AbstractController(cc) {

  private val regularAction = new AuthAction(Seq(AccType.regularUser))

  def getReviews(productId: Int, sortParameter: String) = Action.async { request =>
    reviewService.getReviews(productId, sortParameter).map(r => Ok(Json.toJson(r)))
  }

  def createReview() = regularAction.async(parse.json[NewReviewDTO]) { request =>
    reviewService.createReview(request.body, request.userId).map { res =>
      if (res == Constants.error) BadRequest(s"You cannot add review if you never bought product wtih id ${request.body.productId}")
      else Ok(Json.toJson(res))
    }
  }

}
