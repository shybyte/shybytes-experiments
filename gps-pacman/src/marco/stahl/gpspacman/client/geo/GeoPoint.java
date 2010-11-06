package marco.stahl.gpspacman.client.geo;

import com.google.common.base.Objects;


public class GeoPoint {
	private double lat;
	private double lng;
	
	public GeoPoint(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLong() {
		return lng;
	}
	
	@Override
	public String toString() {
		return "GeoPoint [lat=" + lat + ", lng=" + lng + "]";
	}

	@SuppressWarnings("boxing")
	@Override
	public int hashCode() {
		return Objects.hashCode(lat,lng);
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(String.valueOf(obj));
	}
	
	
}
