package utils

import models.AccType

object Constants {

  val adminAuth = AccType.admin

  val regularUserAuth = AccType.regularUser

  val nonAuthorised = -1

  val error = -2

  val minCodeGenNum = 10000

  val maxCodeGenNum = 99999

}
