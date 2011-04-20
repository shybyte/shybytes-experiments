package controllers

import play.mvc.Scope.Session
import play._
import play.mvc._
import play.mvc.Scope.Session;

import models._
import models.User
import java.util._

object Application extends Controller {

  //    def index() = {
  //    	val currentSession = Session.current()
  //    	val userName = currentSession.get("user")
  //    	val counter = currentSession.get("count")
  //    	val newCounter:Int = if (counter == null) 1 else Integer.parseInt(counter)+1
  //    	Session.current().put("count", newCounter)
  //    	Template('userName -> userName)
  //    }

  //    def logout() = {
  //    	Session.current().clear()
  //    	Template()
  //    }

  def index() = {
    if (Auth.isLoggedIn()) {
      var user = User.findByExternalId(Auth.getExternalId()) 
      if (null == user) {
        user = new User()
        user.externalId = Auth.getExternalId()
        user.insert()
        Logger.info("created new User:" + user.externalId);
      }
      Template('userName -> user.externalId)
    } else {
      Template('userName -> "anonymous")
    }
  }

  def login() = {
    Auth.login("Application.index");
  }

  def logout() = {
    Auth.logout("Application.index");
  }

}