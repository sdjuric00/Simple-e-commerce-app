package repository

import dto.NewReviewDTO
import models.{AccType, ProductCategory, Review, User}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
import repository.Tables._
import org.postgresql.util.PSQLException

import java.time.LocalDateTime
import java.sql.Timestamp

@Singleton
class ReviewRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def convert(r: Reviews#TableElementType, p: Products#TableElementType, u: Users#TableElementType): Review = {
    val product =
      models.Product(p.productId, p.name, ProductCategory.withName(p.category), p.price, p.quantity, p.sold, p.image, p.description, p.deleted)
    val user = User(u.userId, u.email, u.password, u.firstName, u.lastName, u.dateOfBirth.toLocalDate, u.image, AccType.withName(u.accType), u.active)
    Review(r.reviewId, r.userId, r.productId, r.rate, r.time.toLocalDateTime, Some(user), Some(product))
  }

  def getReviewByDate(productId: Int): Future[Seq[Review]] = {
    db.run(
      Reviews
        .filter(_.productId === productId)
        .join(Products)
        .on(_.productId === _.productId)
        .join(Users)
        .on(_._1.userId === _.userId)
        .sortBy(_._1._1.time.desc)
        .result
        .map { res =>
          res.map { case ((reviewsT, productsT), userT) =>
            (reviewsT, productsT, userT)
            convert(reviewsT, productsT, userT)
          }
        }
    )
  }

  def getReviewByRate(productId: Int): Future[Seq[Review]] = {
    db.run(
      Reviews
        .filter(_.productId === productId)
        .join(Products)
        .on(_.productId === _.productId)
        .join(Users)
        .on(_._1.userId === _.userId)
        .sortBy(_._1._1.rate.desc)
        .result
        .map { res =>
          res.map { case ((reviewsT, productsT), userT) =>
            (reviewsT, productsT, userT)
            convert(reviewsT, productsT, userT)
          }
        }
    )
  }

  def createReview(newReviewDTO: NewReviewDTO, userId: Int): Future[Int] = {
    val reviewWithId = (Reviews returning Reviews.map(_.reviewId)
      into ((review, id) => review.copy(reviewId = id))) += ReviewsRow(
      -1,
      userId,
      newReviewDTO.productId,
      newReviewDTO.rate,
      Timestamp.valueOf(LocalDateTime.now())
    )

    val action = db.run(reviewWithId.map(Some(_))).recover { case e: PSQLException => None }
    action.map { o =>
      if (o.isDefined) {
        o.get.reviewId
      } else -1
    }
  }

}
