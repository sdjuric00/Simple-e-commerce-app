package repository

import dto.NewChosenProductsDTO
import models.{ChosenProduct, ProductCategory}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
import repository.Tables._

@Singleton
class ChosenProductRepostiory @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def converter(choProduct: ChosenProducts#TableElementType, p: Products#TableElementType): ChosenProduct = {
    val product =
      models.Product(p.productId, p.name, ProductCategory.withName(p.category), p.price, p.quantity, p.sold, p.image, p.description, p.deleted)
    ChosenProduct(choProduct.id, choProduct.productId, choProduct.orderId, choProduct.quantity, choProduct.price, product)
  }

  def findChosenProductsByOrderId(orderId: Int): Future[Seq[ChosenProduct]] = {
    db.run(
      ChosenProducts
        .filter(_.orderId === orderId)
        .join(Products)
        .on(_.productId === _.productId)
        .result
        .map(p => p.map(choPro => converter(choPro._1, choPro._2)))
    )
  }

  def checkIfProductIsBoughtByUser(userId: Int, productId: Int): Future[Int] = {
    db.run(
      ChosenProducts
        .filter(_.productId === productId)
        .join(Orders)
        .on(_.orderId === _.orderId)
        .filter(_._2.userId === userId)
        .result
        .map { res =>
          res.length
        }
    )
  }

  def createChosenProducts(orderId: Int, chosenProducts: Seq[NewChosenProductsDTO]): Unit = {
    val actions = (chosenProducts.map(product => {
      db.run(
        ChosenProducts returning ChosenProducts.map(_.id) += ChosenProductsRow(
          -1,
          product.productId,
          orderId,
          product.quantity,
          product.price
        )
      )
    }))
  }

}
