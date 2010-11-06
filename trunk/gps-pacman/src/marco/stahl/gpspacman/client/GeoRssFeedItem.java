/**
 * 
 */
package marco.stahl.gpspacman.client;

import com.google.gwt.core.client.JavaScriptObject;

class GeoRssFeedItem extends JavaScriptObject {
	protected GeoRssFeedItem() {
		// TODO Auto-generated constructor stub
	}
	
	final native String getTitle() /*-{
		return this.title;
	}-*/;

	final native String getLine() /*-{
		var line = $wnd.google.feeds.getElementsByTagNameNS(this.xmlNode, "http://www.opengis.net/gml", "posList")[0];
		return line ? line.firstChild.data : null;
	}-*/;
}