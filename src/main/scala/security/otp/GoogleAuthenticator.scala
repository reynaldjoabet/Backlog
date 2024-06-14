package security.otp

// import org.apache.commons.codec.binary.Base32
// import org.apache.commons.codec.digest.{HmacAlgorithms, HmacUtils}

import java.security.GeneralSecurityException

import scala.concurrent.duration._

import javax.crypto.Mac

object GoogleAuthenticator {

  private[this] val interval = 30.seconds

  def generateTotp(
    encoded: String,
    currentTimeMillis: Long
  ): Either[GeneralSecurityException, String] = {
    val mac               = Mac.getInstance("SHA512")
    val passcodeGenerator = OneTimePassword(mac)
    passcodeGenerator.generateOtp(currentTimeMillis / interval.toMillis)
  }

}
