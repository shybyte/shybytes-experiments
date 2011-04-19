package controllers

import play.mvc.Scope.Session
import play._
import play.mvc._
import play.mvc.Scope.Session;

object Application extends Controller {
    
    def index() = {
    	val currentSession = Session.current()
    	val userName = currentSession.get("user")
    	val counter = currentSession.get("count")
    	val newCounter:Int = if (counter == null) 1 else Integer.parseInt(counter)+1
    	Session.current().put("count", newCounter)
    	Template('userName -> userName)
    }
    
    def logout() = {
    	Session.current().clear()
    	Template()
    }
    
}