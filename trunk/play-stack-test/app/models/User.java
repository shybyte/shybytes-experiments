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
    public String email;
    
    public static void facebookOAuthCallback(JsonObject data){
    	Set<Entry<String, JsonElement>> entrySet = data.entrySet();
    	Logger.info("elementsSize:"+ entrySet.size());
    	for (Entry<String, JsonElement> entry : entrySet) {
    		Logger.info(entry.getKey()+":"+ entry.getValue().getAsString());
		}
        Session.current().put("user", data.get("id").getAsString());
    }
    
    /*
     
      #
2011-03-28 15:18:36.882 play.Logger info: id:1482201079
#
I 2011-03-28 15:18:36.882 play.Logger info: name:Mirco Tesla
#
I 2011-03-28 15:18:36.882 play.Logger info: first_name:Mirco
#
I 2011-03-28 15:18:36.882 play.Logger info: last_name:Tesla
#
I 2011-03-28 15:18:36.882 play.Logger info: link:http://www.facebook.com/profile.php?id=1482201079
#
I 2011-03-28 15:18:36.882 play.Logger info: gender:male
#
I 2011-03-28 15:18:36.882 play.Logger info: timezone:2
#
I 2011-03-28 15:18:36.882 play.Logger info: locale:de_DE
#
I 2011-03-28 15:18:36.882 play.Logger info: verified:true
#
I 2011-03-28 15:18:36.882 play.Logger info: updated_time:2011-03-28T20:44:36+0000
#
I 2011-03-28 15:18:36.882 play.Logger info: accessToken:187774674601255|2.XfLijnSvjZZic3iDNSNs0Q__.3600.1301356800-1482201079|rbuGcMBFdZtfoykt3xjy3B0myMM
#
I 2011-03-28 15:18:36.882 play.Logger info: expires:6084

     * 
     */
    
}
