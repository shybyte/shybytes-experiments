package models;

import java.util.*;
import java.util.Map.Entry;

import play.Logger;
import play.mvc.Scope.Session;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import siena.*;

public class Thing extends Model {

    @Id(Generator.AUTO_INCREMENT)
    public Long id;        	
	public String title;
	
    @Index("userId_index")
    public String userId;

    static Query<Thing> all() {
        return Model.all(Thing.class);
    }
    
    public static List<Thing> findByUser(String userName) {
        return all().filter("userId", userName).fetch();
    }    
    
}
