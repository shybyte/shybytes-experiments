package dogather.facebook

case class FacebookRequest(user:FacebookUser,algorithm:String,issued_at:Long) {
	def this() = this(new FacebookUser(),"",0);
}