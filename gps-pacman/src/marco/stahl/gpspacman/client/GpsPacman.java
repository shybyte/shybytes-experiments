package marco.stahl.gpspacman.client;

import marco.stahl.gpspacman.client.georss.GeoRssFeed;
import marco.stahl.gpspacman.client.georss.GeoRssFeedCallback;
import marco.stahl.gpspacman.client.georss.GeoRssFeedCallbackProxy;
import marco.stahl.gpspacman.client.georss.GeoRssFeedCallback;
import marco.stahl.gpspacman.client.georss.GeoRssFeedItem;

import com.google.common.collect.Lists;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Element;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class GpsPacman implements EntryPoint {
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	// GWT module entry point method.
	public void onModuleLoad() {
		AjaxLoader.init();
		Maps.loadMapsApi("", "2", false, new Runnable() {
			public void run() {
				AjaxLoader.loadApi("feeds", "1", new Runnable() {
					public void run() {
						buildUi();
					}
				}, null);
			}
		});

	}

	private void buildUi() {
		final FlowPanel flowPanel = new FlowPanel();
		if (true) {

			LatLng auerPark = LatLng.newInstance(52.518688, 13.445066);

			MapOptions mapOptions = MapOptions.newInstance().setMapTypes(
					Lists.newArrayList(MapType.getHybridMap()));
			final MapWidget map = new MapWidget(auerPark, 18, mapOptions);
			map.setSize("600px", "400px");
			// Add some controls for the zoom level
			map.addControl(new LargeMapControl());

			// Add a marker
			Marker center = new Marker(auerPark);
			map.addOverlay(center);
			center.setImage("images/pacman.png");

			Polyline polyline = new Polyline(
					new LatLng[] { LatLng.newInstance(52.518490, 13.444229), LatLng
							.newInstance(52.518459, 13.444300), LatLng
							.newInstance(52.518429, 13.444290), LatLng
							.newInstance(52.518291, 13.445630), LatLng
							.newInstance(52.519058, 13.445140), LatLng
							.newInstance(52.518848, 13.444470), LatLng
							.newInstance(52.518490, 13.444232) });
			map.addOverlay(polyline);

			flowPanel.add(map);
		}
		RootPanel.get("app").add(flowPanel);

		FlowPanel console = new FlowPanel();
		flowPanel.add(console);
		loadMap(new GeoRssFeedCallbackProxy(new GeoRssFeedCallback() {
			
			@Override
			public void onSuccess(GeoRssFeed feed) {
				JsArray<GeoRssFeedItem> items = feed.getEntries();
				for (int i = 0; i < items.length(); i++) {
					GeoRssFeedItem item = items.get(i);
					flowPanel.add(new Label(item.getTitle()+" ---- " +item.getLine()));
				}				
				
			}
			
			@Override
			public void onFailure(String caught) {
				// TODO Auto-generated method stub
				
			}
		}));
	}
	
	private native void loadMap(GeoRssFeedCallbackProxy callback) /*-{
		var feed = new $wnd.google.feeds.Feed("http://maps.google.com/maps/ms?ie=UTF8&hl=en&vps=1&jsv=290a&msa=0&output=georss&msid=100976889524593770371.0004935c45a9beae52c82");
		feed.setResultFormat($wnd.google.feeds.Feed.MIXED_FORMAT);
		feed.load(function(result) {
		  if (!result.error) {          
		      //var entry = result.feed.entries[i];
		      //var line = $wnd.google.feeds.getElementsByTagNameNS(entry.xmlNode, "http://www.opengis.net/gml", "posList")[0];
		      //gwtResult.push({title: entry.title,line:line.firstChild.data});
		      //callback.@marco.stahl.gpspacman.client.JsCallback::onSuccess(Lcom/google/gwt/core/client/JsArray;)(result.feed.entries);
		      callback.@marco.stahl.gpspacman.client.georss.GeoRssFeedCallbackProxy::onSuccess(Lmarco/stahl/gpspacman/client/georss/GeoRssFeed;)(result.feed);
		  } else {
		  	$wnd.alert($wnd.JSON.stringify(result.error));
		  }
		});
	}-*/;

	private native void nativeMakeImagePieChart(Element chartDiv) /*-{
		var data = new $wnd.google.visualization.DataTable();
		data.addColumn('string', 'Task');
		data.addColumn('number', 'Hours per Day');
		data.addRows(5);
		data.setValue(0, 0, 'Work');
		data.setValue(0, 1, 11);
		data.setValue(1, 0, 'Eat');
		data.setValue(1, 1, 2);
		data.setValue(2, 0, 'Commute');
		data.setValue(2, 1, 2);
		data.setValue(3, 0, 'Watch TV');
		data.setValue(3, 1, 2);
		data.setValue(4, 0, 'Sleep');
		data.setValue(4, 1, 7);

		var chart = new $wnd.google.visualization.ImagePieChart(chartDiv);
		chart.draw(data, {width: 500, height: 200, is3D: true, title: 'My Daily Activities'});
	}-*/;
}
