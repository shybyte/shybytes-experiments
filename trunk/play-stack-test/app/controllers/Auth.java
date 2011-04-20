package controllers;


import play.modules.gae.*;
import play.mvc.Scope.Session;

import com.google.appengine.api.users.*;

public class Auth {

	public static boolean isLoggedIn() {
		return GAE.isLoggedIn() || Session.current().get("user")!=null;
	}
	
	public static String getExternalId() {
		if (GAE.isLoggedIn()) {
			return GAE.getUser().getEmail();
		} else {
			return Session.current().get("user");
		}
	}
	public static User getUser() {
		return GAE.getUser();
	}

    public static void login(String action) {
        GAE.login(action);
    }

    public static void logout(String action) {
        GAE.logout(action);
    }
}