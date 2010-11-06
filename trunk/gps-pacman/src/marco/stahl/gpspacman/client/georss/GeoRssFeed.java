/**
 * 
 */
package marco.stahl.gpspacman.client.georss;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class GeoRssFeed extends JavaScriptObject {
	protected GeoRssFeed() {
		// TODO Auto-generated constructor stub
	}
	
	public final native JsArray<GeoRssFeedItem> getEntries() /*-{
		return this.entries;
	}-*/;

}