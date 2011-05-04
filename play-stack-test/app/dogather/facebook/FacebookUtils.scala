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

	def decodeSignedRequest(sigreq:String): FacebookRequest = {
		/* split the string into signature and payload */
		val idx = sigreq.indexOf(".") 
		val sig = new Base64(true).decode(sigreq.substring(0, idx).getBytes()) 
		val rawpayload = sigreq.substring(idx+1) 
		val payload = new String(new Base64(true).decode(rawpayload)) 

		/* parse the JSON payload and do the signature check */
		val ret = new Gson().fromJson(payload, classOf[FacebookRequest]) 
		/* check if it is HMAC-SHA256 */
		if (!ret.algorithm.equals("HMAC-SHA256")) {
			/* note that this follows facebooks example, as published on 2010-07-21 (I wonder when this will break) */
			throw new IllegalArgumentException("Unexpected hash algorithm " + ret.algorithm) 
		}
		/* then check the signature */
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