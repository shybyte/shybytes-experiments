/**
 * 
 */
package marco.stahl.gpspacman.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

class GeoRssFeed extends JavaScriptObject {
	protected GeoRssFeed() {
		// TODO Auto-generated constructor stub
	}
	
	final native JsArray<GeoRssFeedItem> getEntries() /*-{
		return this.entries;
	}-*/;

}