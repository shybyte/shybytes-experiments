/**
 * 
 */
package marco.stahl.gpspacman.client.georss;

import com.google.gwt.core.client.JavaScriptObject;

public class GeoRssFeedItem extends JavaScriptObject {
	protected GeoRssFeedItem() {
		// TODO Auto-generated constructor stub
	}
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final native String getLine() /*-{
		var line = $wnd.google.feeds.getElementsByTagNameNS(this.xmlNode, "http://www.opengis.net/gml", "posList")[0];
		return line ? line.firstChild.data : null;
	}-*/;
}