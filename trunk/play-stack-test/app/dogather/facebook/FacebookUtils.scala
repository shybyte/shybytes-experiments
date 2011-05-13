package dogather.facebook

import java.security.InvalidKeyException 
import java.security.NoSuchAlgorithmException 
import java.util.Arrays 

import javax.crypto.Mac 
import javax.crypto.spec.SecretKeySpec 

import org.apache.commons.codec.binary.Base64 


import com.google.gson.Gson 

object FacebookUtils {
	private val SIGN_ALGORITHM = "HMACSHA256" 
	private val secret = "68bce34bffaa034b704a484cd51d2e3d" 

	val base64 = new Base64(true);
	def base64UrlDecode(encoded:String) = base64.decode(encoded.replace('-', '+').replace('_', '/').trim())
		
	def decodeSignedRequest(sigreq:String): FacebookRequest = {
		val Array(encodedSig,encodedPayload) = sigreq.split('.')
		val sig = base64UrlDecode(encodedSig) 
		
		val rawpayload = encodedPayload.replace("-", "+").replace("_", "/").trim()
		val payload = new String(base64.decode(rawpayload)) 

		val ret = new Gson().fromJson(payload, classOf[FacebookRequest]) 
		if (!ret.algorithm.equals("HMAC-SHA256")) {
			throw new IllegalArgumentException("Unexpected hash algorithm " + ret.algorithm) 
		}
		checkSignature(rawpayload, sig) 
		return ret
	}

	def checkSignature(rawpayload:String, sig:Array[Byte]) {
		try {
			val secretKeySpec = new SecretKeySpec(secret.getBytes(), SIGN_ALGORITHM) 
			val mac = Mac.getInstance(SIGN_ALGORITHM) 
			mac.init(secretKeySpec) 
			val mysig = mac.doFinal(rawpayload.getBytes()) 
			if (!Arrays.equals(mysig, sig)) {
				throw new IllegalArgumentException("Non-matching signature for request") 
			}
		} catch {
		     case e: NoSuchAlgorithmException => {
		    	 throw new IllegalArgumentException("Unknown hash algorithm " + SIGN_ALGORITHM, e) 
		     }
		     case e: InvalidKeyException => {
		    	 throw new IllegalArgumentException("Wrong key for " + SIGN_ALGORITHM, e)
		     }
		}
	}
}