package repository

import org.postgresql.util.PSQLException
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._
import repository.Tables._

@Singleton
class ProductRepository @Inject() (protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
    extends HasDatabaseConfigProvider[JdbcProfile] {

  def converter(p: Products#TableElementType): models.Product = {
    models.Product(p.productId, p.name, models.ProductCategory.withName(p.category), p.price, p.quantity, p.sold, p.image, p.description, p.deleted)
  }

  def allProducts(): Future[Seq[models.Product]] = {
    db.run(Products.sortBy(_.productId.asc).result).map(_.map(converter))
  }

  def getActiveProducts(): Future[Seq[models.Product]] = {
    db.run(Products.sortBy(_.productId.asc).filter(_.deleted === false).result).map(_.map(converter))
  }

  def findById(id: Int): Future[Option[models.Product]] = {
    db.run(Products.filter(productRow => productRow.productId === id).result).map(_.map(converter).headOption)
  }

  def delete(id: Int): Future[Boolean] = {
    db.run(Products.filter(_.productId === id).delete).map(count => count > 0)
  }

  def getProductsByPopularity(): Future[Seq[models.Product]] = {
    db.run(Products.sortBy(_.sold.desc).filter(_.deleted === false).result.map(_.map(converter)))
  }

  def logicalDelete(id: Int): Future[Boolean] = {
    val deleted = for { p <- Products if p.productId === id } yield p.deleted
    val action = deleted.update(true)

    db.run(action).map(count => count > 0)
  }

  def updateQuantityAndSold(productId: Int, newQuantity: Int, newSold: Int): Future[Any] = {
    val action = Products
      .filter(_.productId === productId)
      .map(p => (p.quantity, p.sold))
      .update(newQuantity, newSold)
    db.run(action).recover { case e: PSQLException => None }
  }

  def create(newProduct: models.Product): Future[Option[models.Product]] = {
    val r = ProductsRow(
      newProduct.productId,
      newProduct.name,
      newProduct.category.toString,
      newProduct.price,
      newProduct.quantity,
      newProduct.sold,
      newProduct.image,
      newProduct.description
    )
    val productWithId = (Products returning Products.map(_.productId)
      into ((product, id) => product.copy(productId = id))) += r
    db.run(productWithId.map(converter).map(Some(_))).recover { case e: PSQLException =>
      None
    }
  }

  def update(product: models.Product): Future[Option[models.Product]] = {
    val action = Products
      .filter(_.productId === product.productId)
      .map(p => (p.name, p.category, p.price, p.quantity, p.sold, p.image, p.description))
      .update(product.name, product.category.toString, product.price, product.quantity, product.sold, product.image, product.description)
    db.run(action).recover { case e: PSQLException => None }
    findById(product.productId)
  }

  def search(name: String, category: String, available: Boolean, lowToHigh: Boolean): Future[Seq[models.Product]] = {
    db.run(
      Products
        .filter(!_.deleted)
        .filterIf(!name.equalsIgnoreCase("ALL"))(_.name.toUpperCase like s"%$name%")
        .filterIf(!category.equalsIgnoreCase("ALL"))(_.category.toUpperCase like s"%$category%")
        .filterIf(available)(_.quantity > 0)
        .filterIf(!available)(_.quantity > -1)
        .sortBy(prod => { if (lowToHigh) prod.price.asc else prod.price.desc })
        .result
    ).map(_.map(converter))
  }

}
