package dogather.facebook

case class FacebookRequest(
  user: FacebookUser, 
  algorithm: String, 
  issued_at: Long,
  user_id: String = null,
  oauth_token:String = null,
  expires: Long = 0) {
  def this() = this(new FacebookUser(), "", 0);
}