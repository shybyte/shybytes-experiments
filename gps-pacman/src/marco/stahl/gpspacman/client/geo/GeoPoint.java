package marco.stahl.gpspacman.client.geo;

import com.google.common.primitives.Doubles;


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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Doubles.hashCode(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Doubles.hashCode(lat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		return this.toString().equals(String.valueOf(obj));
	}
	
	
}
