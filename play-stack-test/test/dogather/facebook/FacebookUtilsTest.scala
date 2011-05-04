package dogather.facebook

import org.scalatest.junit.AssertionsForJUnit
import scala.collection.mutable.ListBuffer
import org.junit.Assert._
import org.junit.Test
import org.junit.Before

class FacebookUtilsTest extends AssertionsForJUnit {
  @Before
  def initialize() {
  }

  @Test
  def verifyEasy() {
    val data = "dYL9zkPDedL3De4s1pnVYLZIKNaIHp0wMxPGZyU9Ssk.eyJhbGdvcml0aG0iOiJITUFDLVNIQTI1NiIsImV4cGlyZXMiOjEzMDMzNzY0MDAsImlzc3VlZF9hdCI6MTMwMzM3MTIzMSwib2F1dGhfdG9rZW4iOiIxODc3NzQ2NzQ2MDEyNTV8Mi5nY3ZWd2xtaXE4aGNNbjM0dndPZkV3X18uMzYwMC4xMzAzMzc2NDAwLjEtMTQ4MjIwMTA3OXxUbVNTM0VhOXdHVWNsYlE5R2d1b1FIU1ZjRnMiLCJ1c2VyIjp7ImNvdW50cnkiOiJkZSIsImxvY2FsZSI6ImRlX0RFIiwiYWdlIjp7Im1pbiI6MjF9fSwidXNlcl9pZCI6IjE0ODIyMDEwNzkifQ";
    val result = FacebookUtils.decodeSignedRequest(data);
    println(result);
    assertEquals(FacebookRequest(FacebookUser("de_DE", "de"),
      "HMAC-SHA256", 1303371231, "1482201079", "187774674601255|2.gcvVwlmiq8hcMn34vwOfEw__.3600.1303376400.1-1482201079|TmSS3Ea9wGUclbQ9GguoQHSVcFs", 1303376400), result);
  }

}