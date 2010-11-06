package marco.stahl.gpspacman.client.georss;

public class GeoRssFeedCallbackProxy implements JsCallback<GeoRssFeed> {
	private final JsCallback<GeoRssFeed> geoRssFeedCallback;

	public GeoRssFeedCallbackProxy(JsCallback<GeoRssFeed> geoRssFeedCallback) {
		this.geoRssFeedCallback = geoRssFeedCallback;
	}
	
	@Override
	public void onFailure(String caught) {
		geoRssFeedCallback.onFailure(caught);
	}

	@Override
	public void onSuccess(GeoRssFeed result) {
		geoRssFeedCallback.onSuccess(result);
	}

}
