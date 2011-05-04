package controllers

import play.mvc.Scope.Session
import play._
import play.mvc._
import play.mvc.Scope.Session

import dogather.facebook._
import models._
import models.User
import java.util._
import java.net.URLEncoder

import play.Play;
import play.exceptions.UnexpectedException;
import play.libs.WS;
import play.libs.WS.WSRequest;
import play.mvc.Http;
import play.mvc.Router;

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

  def authUrl = "https://www.facebook.com/dialog/oauth?client_id=%s&redirect_uri=%s" format ("187774674601255", WS.encode(Router.getFullUrl("Application.canvas")))
  
  def canvas(signed_request: String) = {
    if (signed_request == null) {
      "Hossa: " + authUrl
    } else {
      Logger.info("canvas with" + signed_request);
      val fbRequest = FacebookUtils.decodeSignedRequest(signed_request);
      if (fbRequest.user_id == null) {
        "<script> top.location.href='" + authUrl + "'</script>"
      } else {
        Template('signed_request -> signed_request, 'fbRequest -> fbRequest)
      }
    }
  }

}