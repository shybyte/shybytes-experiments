package marco.stahl.gpspacman.client.georss;

public class GeoRssFeedCallbackProxy implements GeoRssFeedCallback {
	private final GeoRssFeedCallback geoRssFeedCallback;

	public GeoRssFeedCallbackProxy(GeoRssFeedCallback geoRssFeedCallback) {
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
