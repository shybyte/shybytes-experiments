/**
 * 
 */
package marco.stahl.gpspacman.client.georss;

import java.util.Iterator;
import java.util.List;

import marco.stahl.gpspacman.client.geo.GeoLine;
import marco.stahl.gpspacman.client.geo.GeoPoint;


import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.gwt.core.client.JavaScriptObject;

public class GeoRssFeedItem extends JavaScriptObject {
	protected GeoRssFeedItem() {
		// TODO Auto-generated constructor stub
	}
	
	public final native String getTitle() /*-{
		return this.title;
	}-*/;

	public final GeoLine getLine(){
		String postListString = this.getLineAsString();
		if (postListString==null) {
			return null;
		}
		return createLine(postListString);
	}
	
	public final static GeoLine createLine(String geoRssPosList) {
		List<GeoPoint> points = Lists.newArrayList();
		Iterable<String> tokens = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().split(geoRssPosList);
		Iterator<String> it = tokens.iterator();
		while (it.hasNext()) {
			double lat = Double.parseDouble(it.next());
			double lng = Double.parseDouble(it.next());;
			points.add(new GeoPoint(lat, lng));
		}
		return new GeoLine(points);
	}

	public final native String getLineAsString() /*-{
		var line = $wnd.google.feeds.getElementsByTagNameNS(this.xmlNode, "http://www.opengis.net/gml", "posList")[0];
		return line ? line.firstChild.data : null;
	}-*/;
	
	
}