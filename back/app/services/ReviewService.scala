package services

import dto.NewReviewDTO
import models.Review
import repository.{ChosenProductRepostiory, ReviewRepository}
import utils.Constants

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReviewService @Inject() (reviewRepository: ReviewRepository, chosenProRep: ChosenProductRepostiory)(implicit ec: ExecutionContext) {

  def getReviews(productId: Int, sortParam: String): Future[Seq[Review]] = {
    if (sortParam.equals("rate"))
      reviewRepository.getReviewByRate(productId)
    else
      reviewRepository.getReviewByDate(productId) //by default by date even when sortParam is not passed
  }

  def createReview(dto: NewReviewDTO, userId: Int): Future[Int] = {
    val productExists = chosenProRep.checkIfProductIsBoughtByUser(userId, dto.productId)
    productExists.map { res =>
      if (res >= 1) reviewRepository.createReview(dto, userId)
      else Future.successful(Constants.error)
    }.flatten
  }

}
