package repository

import dto.NewChosenProductsDTO
import models.{ChosenProduct, Order, ProductCategory}
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{Await, ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
import repository.Tables._
import org.postgresql.util.PSQLException

import java.time.LocalDateTime
import java.sql.Timestamp

@Singleton
class OrderRepository @Inject() (
    protected val dbConfigProvider: DatabaseConfigProvider,
    chosenProductRep: ChosenProductRepostiory,
    userRep: UserRepository
)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def converter(o: Orders#TableElementType): Future[Order] = {
    val choProFuture = chosenProductRep.findChosenProductsByOrderId(o.orderId)
    choProFuture.map(chosen =>
      Order(
        o.orderId,
        o.userId,
        chosen,
        None,
        o.time.toLocalDateTime,
        o.total
      )
    )
  }

  def converter(
      data: Seq[((Orders#TableElementType, ChosenProducts#TableElementType), Products#TableElementType)],
      order: Orders#TableElementType
  ): Order = {
    val chosenProds = data.map { d =>
      val product = models.Product(
        d._2.productId,
        d._2.name,
        ProductCategory.withName(d._2.category),
        d._2.price,
        d._2.quantity,
        d._2.sold,
        d._2.image,
        d._2.description,
        d._2.deleted
      )
      ChosenProduct(d._1._2.id, d._1._2.productId, d._1._2.orderId, d._1._2.quantity, d._1._2.price, product)
    }
    Order(order.orderId, order.userId, chosenProds, None, order.time.toLocalDateTime, order.total)
  }

  def converterWithUserAndProducts(o: Orders#TableElementType, userRow: Users#TableElementType, orderId: Int): Future[Order] = {
    val chosenProductsF = chosenProductRep.findChosenProductsByOrderId(orderId)
    val user = userRep.converter(userRow)
    chosenProductsF.map(chosenProducts => Order(o.orderId, o.userId, chosenProducts, Some(user), o.time.toLocalDateTime, o.total))
  }

  //by default with products but without user object
  def getOrdersBy(userId: Int, sortBy: String): Future[Seq[Order]] = {
    db.run(
      Orders
        .filter(_.userId === userId)
        .sortBy(order => { if (sortBy.equalsIgnoreCase("date")) order.time.asc else order.total.asc })
        .result
    ).map(_.map(converter))
      .flatMap(f => Future.sequence(f))
  }

  def getAllOrders(sortBy: String): Future[Seq[Order]] = {
    db.run(
      Orders
        .sortBy(order => { if (sortBy.equalsIgnoreCase("date")) order.time.asc else order.total.asc })
        .result
    ).map(_.map(converter))
      .flatMap(f => Future.sequence(f))
  }

  def findOrderWithUserAndProduct(orderId: Int, userId: Int): Future[Seq[Order]] = {
    db.run(
      Orders
        .filter(_.orderId === orderId)
        .filter(_.userId === userId)
        .join(Users)
        .on(_.userId === _.userId)
        .result
        .map(res => res.map(tup => converterWithUserAndProducts(tup._1, tup._2, orderId)))
    ).flatMap(f => Future.sequence(f))
  }

  def findDetailOrderData(orderId: Int, userId: Int): Future[Seq[Order]] = {
    db.run(
      Orders
        .filter(_.orderId === orderId)
        .filter(_.userId === userId)
        .join(ChosenProducts)
        .on(_.orderId === _.orderId)
        .join(Products)
        .on(_._2.productId === _.productId)
        .result
        .map { res =>
          res
            .groupBy(_._1._1.orderId)
            .map { case (id, triple) =>
              converter(triple, triple(0)._1._1)
            }
            .toSeq
        }
    )
  }

  def createOrder(userId: Int, chosenProducts: Seq[NewChosenProductsDTO]): Future[Int] = {
    val orderWithId = (Orders returning Orders.map(_.orderId)
      into ((order, id) => order.copy(orderId = id))) += OrdersRow(
      -1,
      userId,
      Timestamp.valueOf(LocalDateTime.now()),
      chosenProducts.map(c => c.totalPrice()).map(price => price).sum
    )

    val action = db.run(orderWithId.map(Some(_))).recover { case e: PSQLException => None }
    action.map { o =>
      if (o.isDefined) {
        chosenProductRep.createChosenProducts(o.get.orderId, chosenProducts)
        o.get.orderId
      } else -1
    }
  }

}
