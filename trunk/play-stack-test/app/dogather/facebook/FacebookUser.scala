package dogather.facebook

case class FacebookUser(locale: String, country:String) {
	def this() = this("","");
}