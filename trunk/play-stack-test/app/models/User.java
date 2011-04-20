package models;

import java.util.*;
import java.util.Map.Entry;

import play.Logger;
import play.mvc.Scope.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import siena.*;

public class User extends Model {

	@Id
	public Long id;
	public String externalId;
	public String name;

	public static void facebookOAuthCallback(JsonObject data){
    	logFBUserData(data);
        String fbId = data.get("id").getAsString();
		String externalId = makeExternalIdFromFacebookId(fbId);
		Session.current().put("user", externalId);
    }

	public static void googleAuth(JsonObject data){
    	logFBUserData(data);
        String fbId = data.get("id").getAsString();
		String externalId = makeExternalIdFromFacebookId(fbId);
		Session.current().put("user", externalId);
    }
	
	static Query<User> all() {
		return Model.all(User.class);
	}

	public static User findByExternalId(String externalId) {
		return all().filter("externalId", externalId).get();
	}

	private static String makeExternalIdFromFacebookId(String fbId) {
		return fbId + "@@facebook.com";
	}

	private static void logFBUserData(JsonObject data) {
		Set<Entry<String, JsonElement>> entrySet = data.entrySet();
		Logger.info("elementsSize:" + entrySet.size());
		for (Entry<String, JsonElement> entry : entrySet) {
			Logger.info(entry.getKey() + ":" + entry.getValue().getAsString());
		}
	}

	/*
	 * 
	 * # 2011-03-28 15:18:36.882 play.Logger info: id:1482201079 # I 2011-03-28
	 * 15:18:36.882 play.Logger info: name:Mirco Tesla # I 2011-03-28
	 * 15:18:36.882 play.Logger info: first_name:Mirco # I 2011-03-28
	 * 15:18:36.882 play.Logger info: last_name:Tesla # I 2011-03-28
	 * 15:18:36.882 play.Logger info:
	 * link:http://www.facebook.com/profile.php?id=1482201079 # I 2011-03-28
	 * 15:18:36.882 play.Logger info: gender:male # I 2011-03-28 15:18:36.882
	 * play.Logger info: timezone:2 # I 2011-03-28 15:18:36.882 play.Logger
	 * info: locale:de_DE # I 2011-03-28 15:18:36.882 play.Logger info:
	 * verified:true # I 2011-03-28 15:18:36.882 play.Logger info:
	 * updated_time:2011-03-28T20:44:36+0000 # I 2011-03-28 15:18:36.882
	 * play.Logger info:
	 * accessToken:187774674601255|2.XfLijnSvjZZic3iDNSNs0Q__.3600
	 * .1301356800-1482201079|rbuGcMBFdZtfoykt3xjy3B0myMM # I 2011-03-28
	 * 15:18:36.882 play.Logger info: expires:6084
	 */

}
