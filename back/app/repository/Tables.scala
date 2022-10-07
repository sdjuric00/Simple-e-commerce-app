package repository
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Array(ChosenProducts.schema, Orders.schema, Products.schema, Reviews.schema, Users.schema, Verify.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table ChosenProducts
   *  @param id Database column id SqlType(serial), AutoInc, PrimaryKey
   *  @param productId Database column product_id SqlType(int4)
   *  @param orderId Database column order_id SqlType(int4)
   *  @param quantity Database column quantity SqlType(int4)
   *  @param price Database column price SqlType(float8) */
  case class ChosenProductsRow(id: Int, productId: Int, orderId: Int, quantity: Int, price: Double)
  /** GetResult implicit for fetching ChosenProductsRow objects using plain SQL queries */
  implicit def GetResultChosenProductsRow(implicit e0: GR[Int], e1: GR[Double]): GR[ChosenProductsRow] = GR{
    prs => import prs._
    ChosenProductsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[Double]))
  }
  /** Table description of table chosen_products. Objects of this class serve as prototypes for rows in queries. */
  class ChosenProducts(_tableTag: Tag) extends profile.api.Table[ChosenProductsRow](_tableTag, "chosen_products") {
    def * = (id, productId, orderId, quantity, price) <> (ChosenProductsRow.tupled, ChosenProductsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(productId), Rep.Some(orderId), Rep.Some(quantity), Rep.Some(price))).shaped.<>({r=>import r._; _1.map(_=> ChosenProductsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(serial), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column product_id SqlType(int4) */
    val productId: Rep[Int] = column[Int]("product_id")
    /** Database column order_id SqlType(int4) */
    val orderId: Rep[Int] = column[Int]("order_id")
    /** Database column quantity SqlType(int4) */
    val quantity: Rep[Int] = column[Int]("quantity")
    /** Database column price SqlType(float8) */
    val price: Rep[Double] = column[Double]("price")

    /** Foreign key referencing Orders (database name fk_order) */
    lazy val ordersFk = foreignKey("fk_order", orderId, Orders)(r => r.orderId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Products (database name fk_product) */
    lazy val productsFk = foreignKey("fk_product", productId, Products)(r => r.productId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table ChosenProducts */
  lazy val ChosenProducts = new TableQuery(tag => new ChosenProducts(tag))

  /** Entity class storing rows of table Orders
   *  @param orderId Database column order_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int4)
   *  @param time Database column time SqlType(timestamp)
   *  @param total Database column total SqlType(float8) */
  case class OrdersRow(orderId: Int, userId: Int, time: java.sql.Timestamp, total: Double)
  /** GetResult implicit for fetching OrdersRow objects using plain SQL queries */
  implicit def GetResultOrdersRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp], e2: GR[Double]): GR[OrdersRow] = GR{
    prs => import prs._
    OrdersRow.tupled((<<[Int], <<[Int], <<[java.sql.Timestamp], <<[Double]))
  }
  /** Table description of table orders. Objects of this class serve as prototypes for rows in queries. */
  class Orders(_tableTag: Tag) extends profile.api.Table[OrdersRow](_tableTag, "orders") {
    def * = (orderId, userId, time, total) <> (OrdersRow.tupled, OrdersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(orderId), Rep.Some(userId), Rep.Some(time), Rep.Some(total))).shaped.<>({r=>import r._; _1.map(_=> OrdersRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column order_id SqlType(serial), AutoInc, PrimaryKey */
    val orderId: Rep[Int] = column[Int]("order_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column time SqlType(timestamp) */
    val time: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("time")
    /** Database column total SqlType(float8) */
    val total: Rep[Double] = column[Double]("total")

    /** Foreign key referencing Users (database name fk_user) */
    lazy val usersFk = foreignKey("fk_user", userId, Users)(r => r.userId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Orders */
  lazy val Orders = new TableQuery(tag => new Orders(tag))

  /** Entity class storing rows of table Products
   *  @param productId Database column product_id SqlType(serial), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(varchar), Length(30,true)
   *  @param category Database column category SqlType(varchar), Length(20,true)
   *  @param price Database column price SqlType(float8)
   *  @param quantity Database column quantity SqlType(int4)
   *  @param sold Database column sold SqlType(int4)
   *  @param image Database column image SqlType(varchar), Length(150,true), Default(None)
   *  @param description Database column description SqlType(varchar), Length(150,true), Default(None)
   *  @param deleted Database column deleted SqlType(bool), Default(false) */
  case class ProductsRow(productId: Int, name: String, category: String, price: Double, quantity: Int, sold: Int, image: Option[String] = None, description: Option[String] = None, deleted: Boolean = false)
  /** GetResult implicit for fetching ProductsRow objects using plain SQL queries */
  implicit def GetResultProductsRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Double], e3: GR[Option[String]], e4: GR[Boolean]): GR[ProductsRow] = GR{
    prs => import prs._
    ProductsRow.tupled((<<[Int], <<[String], <<[String], <<[Double], <<[Int], <<[Int], <<?[String], <<?[String], <<[Boolean]))
  }
  /** Table description of table products. Objects of this class serve as prototypes for rows in queries. */
  class Products(_tableTag: Tag) extends profile.api.Table[ProductsRow](_tableTag, "products") {
    def * = (productId, name, category, price, quantity, sold, image, description, deleted) <> (ProductsRow.tupled, ProductsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(productId), Rep.Some(name), Rep.Some(category), Rep.Some(price), Rep.Some(quantity), Rep.Some(sold), image, description, Rep.Some(deleted))).shaped.<>({r=>import r._; _1.map(_=> ProductsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column product_id SqlType(serial), AutoInc, PrimaryKey */
    val productId: Rep[Int] = column[Int]("product_id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(varchar), Length(30,true) */
    val name: Rep[String] = column[String]("name", O.Length(30,varying=true))
    /** Database column category SqlType(varchar), Length(20,true) */
    val category: Rep[String] = column[String]("category", O.Length(20,varying=true))
    /** Database column price SqlType(float8) */
    val price: Rep[Double] = column[Double]("price")
    /** Database column quantity SqlType(int4) */
    val quantity: Rep[Int] = column[Int]("quantity")
    /** Database column sold SqlType(int4) */
    val sold: Rep[Int] = column[Int]("sold")
    /** Database column image SqlType(varchar), Length(150,true), Default(None) */
    val image: Rep[Option[String]] = column[Option[String]]("image", O.Length(150,varying=true), O.Default(None))
    /** Database column description SqlType(varchar), Length(150,true), Default(None) */
    val description: Rep[Option[String]] = column[Option[String]]("description", O.Length(150,varying=true), O.Default(None))
    /** Database column deleted SqlType(bool), Default(false) */
    val deleted: Rep[Boolean] = column[Boolean]("deleted", O.Default(false))

    /** Uniqueness Index over (name) (database name products_name_key) */
    val index1 = index("products_name_key", name, unique=true)
  }
  /** Collection-like TableQuery object for table Products */
  lazy val Products = new TableQuery(tag => new Products(tag))

  /** Entity class storing rows of table Reviews
   *  @param reviewId Database column review_id SqlType(serial), AutoInc, PrimaryKey
   *  @param userId Database column user_id SqlType(int4)
   *  @param productId Database column product_id SqlType(int4)
   *  @param rate Database column rate SqlType(int4)
   *  @param time Database column time SqlType(timestamp) */
  case class ReviewsRow(reviewId: Int, userId: Int, productId: Int, rate: Int, time: java.sql.Timestamp)
  /** GetResult implicit for fetching ReviewsRow objects using plain SQL queries */
  implicit def GetResultReviewsRow(implicit e0: GR[Int], e1: GR[java.sql.Timestamp]): GR[ReviewsRow] = GR{
    prs => import prs._
    ReviewsRow.tupled((<<[Int], <<[Int], <<[Int], <<[Int], <<[java.sql.Timestamp]))
  }
  /** Table description of table reviews. Objects of this class serve as prototypes for rows in queries. */
  class Reviews(_tableTag: Tag) extends profile.api.Table[ReviewsRow](_tableTag, "reviews") {
    def * = (reviewId, userId, productId, rate, time) <> (ReviewsRow.tupled, ReviewsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(reviewId), Rep.Some(userId), Rep.Some(productId), Rep.Some(rate), Rep.Some(time))).shaped.<>({r=>import r._; _1.map(_=> ReviewsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column review_id SqlType(serial), AutoInc, PrimaryKey */
    val reviewId: Rep[Int] = column[Int]("review_id", O.AutoInc, O.PrimaryKey)
    /** Database column user_id SqlType(int4) */
    val userId: Rep[Int] = column[Int]("user_id")
    /** Database column product_id SqlType(int4) */
    val productId: Rep[Int] = column[Int]("product_id")
    /** Database column rate SqlType(int4) */
    val rate: Rep[Int] = column[Int]("rate")
    /** Database column time SqlType(timestamp) */
    val time: Rep[java.sql.Timestamp] = column[java.sql.Timestamp]("time")

    /** Foreign key referencing Products (database name fk_product) */
    lazy val productsFk = foreignKey("fk_product", productId, Products)(r => r.productId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
    /** Foreign key referencing Users (database name fk_user) */
    lazy val usersFk = foreignKey("fk_user", userId, Users)(r => r.userId, onUpdate=ForeignKeyAction.NoAction, onDelete=ForeignKeyAction.NoAction)
  }
  /** Collection-like TableQuery object for table Reviews */
  lazy val Reviews = new TableQuery(tag => new Reviews(tag))

  /** Entity class storing rows of table Users
   *  @param userId Database column user_id SqlType(serial), AutoInc, PrimaryKey
   *  @param email Database column email SqlType(varchar), Length(30,true)
   *  @param password Database column password SqlType(varchar), Length(30,true)
   *  @param firstName Database column first_name SqlType(varchar), Length(30,true)
   *  @param lastName Database column last_name SqlType(varchar), Length(30,true)
   *  @param dateOfBirth Database column date_of_birth SqlType(date)
   *  @param image Database column image SqlType(varchar), Length(150,true), Default(None)
   *  @param accType Database column acc_type SqlType(varchar), Length(20,true)
   *  @param active Database column active SqlType(bool), Default(true) */
  case class UsersRow(userId: Int, email: String, password: String, firstName: String, lastName: String, dateOfBirth: java.sql.Date, image: Option[String] = None, accType: String, active: Boolean = true)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Int], e1: GR[String], e2: GR[java.sql.Date], e3: GR[Option[String]], e4: GR[Boolean]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Int], <<[String], <<[String], <<[String], <<[String], <<[java.sql.Date], <<?[String], <<[String], <<[Boolean]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, "users") {
    def * = (userId, email, password, firstName, lastName, dateOfBirth, image, accType, active) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userId), Rep.Some(email), Rep.Some(password), Rep.Some(firstName), Rep.Some(lastName), Rep.Some(dateOfBirth), image, Rep.Some(accType), Rep.Some(active))).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6.get, _7, _8.get, _9.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column user_id SqlType(serial), AutoInc, PrimaryKey */
    val userId: Rep[Int] = column[Int]("user_id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(varchar), Length(30,true) */
    val email: Rep[String] = column[String]("email", O.Length(30,varying=true))
    /** Database column password SqlType(varchar), Length(30,true) */
    val password: Rep[String] = column[String]("password", O.Length(30,varying=true))
    /** Database column first_name SqlType(varchar), Length(30,true) */
    val firstName: Rep[String] = column[String]("first_name", O.Length(30,varying=true))
    /** Database column last_name SqlType(varchar), Length(30,true) */
    val lastName: Rep[String] = column[String]("last_name", O.Length(30,varying=true))
    /** Database column date_of_birth SqlType(date) */
    val dateOfBirth: Rep[java.sql.Date] = column[java.sql.Date]("date_of_birth")
    /** Database column image SqlType(varchar), Length(150,true), Default(None) */
    val image: Rep[Option[String]] = column[Option[String]]("image", O.Length(150,varying=true), O.Default(None))
    /** Database column acc_type SqlType(varchar), Length(20,true) */
    val accType: Rep[String] = column[String]("acc_type", O.Length(20,varying=true))
    /** Database column active SqlType(bool), Default(true) */
    val active: Rep[Boolean] = column[Boolean]("active", O.Default(true))

    /** Uniqueness Index over (email) (database name users_email_key) */
    val index1 = index("users_email_key", email, unique=true)
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))

  /** Entity class storing rows of table Verify
   *  @param verifyId Database column verify_id SqlType(serial), AutoInc, PrimaryKey
   *  @param email Database column email SqlType(varchar), Length(30,true)
   *  @param code Database column code SqlType(int4)
   *  @param numTries Database column num_tries SqlType(int4) */
  case class VerifyRow(verifyId: Int, email: String, code: Int, numTries: Int)
  /** GetResult implicit for fetching VerifyRow objects using plain SQL queries */
  implicit def GetResultVerifyRow(implicit e0: GR[Int], e1: GR[String]): GR[VerifyRow] = GR{
    prs => import prs._
    VerifyRow.tupled((<<[Int], <<[String], <<[Int], <<[Int]))
  }
  /** Table description of table verify. Objects of this class serve as prototypes for rows in queries. */
  class Verify(_tableTag: Tag) extends profile.api.Table[VerifyRow](_tableTag, "verify") {
    def * = (verifyId, email, code, numTries) <> (VerifyRow.tupled, VerifyRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(verifyId), Rep.Some(email), Rep.Some(code), Rep.Some(numTries))).shaped.<>({r=>import r._; _1.map(_=> VerifyRow.tupled((_1.get, _2.get, _3.get, _4.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column verify_id SqlType(serial), AutoInc, PrimaryKey */
    val verifyId: Rep[Int] = column[Int]("verify_id", O.AutoInc, O.PrimaryKey)
    /** Database column email SqlType(varchar), Length(30,true) */
    val email: Rep[String] = column[String]("email", O.Length(30,varying=true))
    /** Database column code SqlType(int4) */
    val code: Rep[Int] = column[Int]("code")
    /** Database column num_tries SqlType(int4) */
    val numTries: Rep[Int] = column[Int]("num_tries")

    /** Uniqueness Index over (email) (database name verify_email_key) */
    val index1 = index("verify_email_key", email, unique=true)
  }
  /** Collection-like TableQuery object for table Verify */
  lazy val Verify = new TableQuery(tag => new Verify(tag))
}
