package services

import dto.NewChosenProductsDTO
import models.{ChosenProduct, Order}
import repository.{OrderRepository, ProductRepository}

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class OrderService @Inject() (orderRepository: OrderRepository, productRepository: ProductRepository)(implicit ec: ExecutionContext) {

  def getOrders(sortBy: String, userId: Int): Future[Seq[Order]] = {
    orderRepository.getOrdersBy(userId, sortBy)
  }

  def getOrderWithUserAndProduct(orderId: Int, userId: Int): Future[Seq[Order]] = {
    orderRepository.findDetailOrderData(orderId, userId)
  }

  def getAllOrders(sortBy: String): Future[Seq[Order]] = {
    orderRepository.getAllOrders(sortBy)
  }

  //case od none se ne moze desiti jer to proverava checkOrderData
  def updateProductData(chosenProducts: Seq[NewChosenProductsDTO]): Unit = {
    for (chosen <- chosenProducts) {
      productRepository.findById(chosen.productId).map { case Some(value) =>
        productRepository.updateQuantityAndSold(value.productId, value.quantity - chosen.quantity, value.sold + chosen.quantity)
      }
    }
  }

  def checkOrderData(chosenProducts: Seq[NewChosenProductsDTO]): Future[Boolean] = {
    val validation = chosenProducts.map { chosen =>
      productRepository.findById(chosen.productId).map {
        case Some(value) if (value.deleted || value.quantity < chosen.quantity) => false
        case Some(value)                                                        => true
        case None                                                               => false
      }
    }

    Future.sequence(validation).map(_.forall(identity))
  }

  def createOrder(userId: Int, chosenProducts: Seq[NewChosenProductsDTO]): Future[Int] = {
    checkOrderData(chosenProducts).flatMap { flag =>
      if (!flag) Future.successful(-1)
      else {
        updateProductData(chosenProducts)
        orderRepository.createOrder(userId, chosenProducts)
      }
    }
  }
//  Reference
//  sealed trait ErrorMessage
//  case object NotAvailable extends ErrorMessage
//  case object InvalidQuantity extends ErrorMessage
//
//  val error: Either[ErrorMessage, _] = Left(NotAvaiable)
//
//  error match {
//    case Left(value) =>
//      value match {
//        case NotAvaiable     => ???
//        case InvalidQuantity => ???
//      }
//    case Right(value) => ???
//  }

}
