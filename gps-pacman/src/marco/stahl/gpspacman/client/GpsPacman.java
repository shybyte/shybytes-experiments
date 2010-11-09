package marco.stahl.gpspacman.client;

import java.util.List;

import marco.stahl.gpspacman.client.geo.GeoLine;
import marco.stahl.gpspacman.client.geo.GeoPoint;
import marco.stahl.gpspacman.client.georss.GeoRssFeed;
import marco.stahl.gpspacman.client.georss.GeoRssFeedCallbackProxy;
import marco.stahl.gpspacman.client.georss.GeoRssFeedItem;
import marco.stahl.gpspacman.client.georss.JsCallback;

import com.google.code.gwt.geolocation.client.Coordinates;
import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.code.gwt.geolocation.client.PositionOptions;
import com.google.common.collect.Lists;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapType;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.Polyline;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class GpsPacman implements EntryPoint, PositionCallback {
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);
	private MapWidget map;
	private Marker playerPos;
	private Geolocation geo;
	private PositionOptions positionOptions;

	@Override
	public void onModuleLoad() {
		AjaxLoader.init("ABQIAAAACYb_oLnlxYFD0wSNzyZHGhT_xBYFzPTH5JbfPqjwCFLIVf5KjxTGuWOcBwVvHZ-8Nz0beAmaWI2T-g");
		Maps.loadMapsApi("", "2", false, new Runnable() {
			@Override
			public void run() {
				AjaxLoader.loadApi("feeds", "1", new Runnable() {
					@Override
					public void run() {
						buildUi();
						initGeoLocation();
					}
				}, null);
			}
		});

	}

	private void initGeoLocation() {
		if (Geolocation.isSupported()) {
			geo = Geolocation.getGeolocation();
			if (geo != null) {
				positionOptions = PositionOptions.create();
				positionOptions.setEnableHighAccuracy(true);
				geo.watchPosition(this, positionOptions);
			} else {
				Window.alert("Geo was null");
			}
		} else {
			Window.alert("No Geo Position Support");
		}
	}

	private void buildUi() {
		final FlowPanel flowPanel = new FlowPanel();
		if (true) {

			LatLng auerPark = LatLng.newInstance(52.518688, 13.445066);

			MapOptions mapOptions = MapOptions.newInstance().setMapTypes(
					Lists.newArrayList(MapType.getHybridMap()));
			map = new MapWidget(auerPark, 18, mapOptions);
			map.setSize("320px", "320px");
			// Add some controls for the zoom level
			map.addControl(new LargeMapControl());

			playerPos = new Marker(auerPark);
			map.addOverlay(playerPos);
			playerPos.setImage("images/pacman.png");

			// Polyline polyline = new Polyline(
			// new LatLng[] { LatLng.newInstance(52.518490, 13.444229), LatLng
			// .newInstance(52.518459, 13.444300), LatLng
			// .newInstance(52.518429, 13.444290), LatLng
			// .newInstance(52.518291, 13.445630), LatLng
			// .newInstance(52.519058, 13.445140), LatLng
			// .newInstance(52.518848, 13.444470), LatLng
			// .newInstance(52.518490, 13.444232) });
			// map.addOverlay(polyline);

			flowPanel.add(map);
		}
		RootPanel.get("app").add(flowPanel);

		FlowPanel console = new FlowPanel();
		flowPanel.add(console);
		loadMap(new GeoRssFeedCallbackProxy(new JsCallback<GeoRssFeed>() {

			@Override
			public void onSuccess(GeoRssFeed feed) {
				drawLines(feed);
			}

			@Override
			public void onFailure(String caught) {
				Window.alert(caught);

			}
		}));
	}

	private native void loadMap(GeoRssFeedCallbackProxy callback) /*-{
		var feed = new $wnd.google.feeds.Feed("http://maps.google.com/maps/ms?ie=UTF8&hl=en&vps=1&jsv=290a&msa=0&output=georss&msid=100976889524593770371.0004935c45a9beae52c82");
		feed.setResultFormat($wnd.google.feeds.Feed.MIXED_FORMAT);
		feed.load(function(result) {
			if (!result.error) {          
				callback.@marco.stahl.gpspacman.client.georss.GeoRssFeedCallbackProxy::onSuccess(Lmarco/stahl/gpspacman/client/georss/GeoRssFeed;)(result.feed);
			} else {
				callback.@marco.stahl.gpspacman.client.georss.GeoRssFeedCallbackProxy::onFailure(Ljava/lang/String;)(result.error);
			}
		});
	}-*/;

	private void drawLines(GeoRssFeed feed) {
		JsArray<GeoRssFeedItem> items = feed.getEntries();
		for (int i = 0; i < items.length(); i++) {
			GeoRssFeedItem item = items.get(i);
			GeoLine line = item.getLine();
			if (line != null) {
				Polyline polyline = createGoogleMapsPolyline(line);
				map.addOverlay(polyline);
			}
		}
	}

	private Polyline createGoogleMapsPolyline(GeoLine line) {
		List<GeoPoint> geoPoints = line.getPoints();
		LatLng[] linePoints = new LatLng[geoPoints.size()];
		for (int i = 0; i < linePoints.length; i++) {
			GeoPoint geoPoint = geoPoints.get(i);
			linePoints[i] = LatLng.newInstance(geoPoint.getLat(),
					geoPoint.getLong());
		}
		return new Polyline(linePoints);
	}

	@Override
	public void onSuccess(Position position) {
		if (map != null) {
			Coordinates coords = position.getCoords();
			LatLng playerLatLng = LatLng.newInstance(coords.getLatitude(),
					coords.getLongitude());
			playerPos.setLatLng(playerLatLng);
			map.setCenter(playerLatLng);
		}
	}

	@Override
	public void onFailure(PositionError error) {
		String message = "";
		switch (error.getCode()) {
		case PositionError.UNKNOWN_ERROR:
			message = "Unknown Error";
			break;
		case PositionError.PERMISSION_DENIED:
			message = "Permission Denied";
			break;
		case PositionError.POSITION_UNAVAILABLE:
			message = "Position Unavailable";
			break;
		case PositionError.TIMEOUT:
			message = "Time-out";
			break;
		default:
			message = "Unknown error code.";
		}
		Window.alert(message);
	}
}
