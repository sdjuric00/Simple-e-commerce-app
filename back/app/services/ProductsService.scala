package services

import dto.NewProduct
import repository.ProductRepository
import models.ProductCategory

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ProductsService @Inject() (productRepository: ProductRepository)(implicit ec: ExecutionContext) {

  def getAllProducts(): Future[Seq[models.Product]] = {
    productRepository.allProducts()
  }

  def getActiveProducts(): Future[Seq[models.Product]] = {
    productRepository.getActiveProducts()
  }

  def getProductsByPopularity(): Future[Seq[models.Product]] = {
    productRepository.getProductsByPopularity()
  }

  def findById(id: Int): Future[Option[models.Product]] = {
    productRepository.findById(id)
  }

  def physicalDelete(id: Int): Future[Boolean] = {
    productRepository.delete(id)
  }

  def logicalDelete(id: Int): Future[Boolean] = {
    productRepository.logicalDelete(id)
  }

  def create(newProduct: models.Product): Future[Option[models.Product]] = {
    productRepository.create(newProduct)
  }

  def update(product: models.Product): Future[Option[models.Product]] = {
    productRepository.update(product)
  }

  def search(name: String, category: String, available: Boolean, lowToHigh: Boolean): Future[Seq[models.Product]] = {
    productRepository.search(name, category, available, lowToHigh)
  }

}
