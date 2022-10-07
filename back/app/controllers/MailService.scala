package controllers

import models.User
import play.api.libs.mailer._

import javax.inject.{Inject, Singleton}

@Singleton
class MailService @Inject() (mailerClient: MailerClient) {

  def sendRegistrationEmail(user: User, code: Int): String = {
    val email = Email(
      "Registration email",
      "mail_za_isa_mrs@yahoo.com",
      Seq(s"${user.email}"),
      bodyText = Some(
        s"Hey ${user.firstName} ${user.lastName},\nWe are glad that you created account on out platform. Please" +
          s"click on the link below to verify your account."
      ),
      bodyHtml = Some(s"<html><body><p>Your code is: ${code}<p><br><p><a href='http://localhost:3000/activate-account'>Click me!</a></p>")
    )
    mailerClient.send(email)
  }

}
