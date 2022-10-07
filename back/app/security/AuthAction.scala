package security

import models.AccType.AccType
import play.api.http.HeaderNames
import play.api.mvc.Results.Forbidden
import play.api.mvc._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

case class UserRequest[A](userId: Int, userRole: AccType, request: Request[A]) extends WrappedRequest[A](request)

class AuthAction(allowedRoles: Seq[AccType])(implicit bodyParser: BodyParsers.Default, ec: ExecutionContext)
    extends ActionBuilder[UserRequest, AnyContent] {
  override def parser: BodyParser[AnyContent] = bodyParser

  override protected def executionContext: ExecutionContext = ec

  override def invokeBlock[A](request: Request[A], block: UserRequest[A] => Future[Result]): Future[Result] = {
    val token = request.headers.get(HeaderNames.AUTHORIZATION)
    JwtAuthorization.decodeJSON(token) match {
      case Right(id -> role) if allowedRoles.contains(role) => block(UserRequest(id, role, request)) //token is valid! :D
      case Right(_)                                         => Future.successful(Forbidden("You cannot perform this action."))
      case Left(_) =>
        Future.successful(Results.Unauthorized("Error. You need to be authenticated, please log in."))
    }
  }
}
