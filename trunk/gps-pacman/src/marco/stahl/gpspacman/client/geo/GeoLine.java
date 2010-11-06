package marco.stahl.gpspacman.client.geo;

import java.util.List;

import com.google.common.collect.Lists;


public class GeoLine {
	List<GeoPoint> points;

	public GeoLine(GeoPoint... points) {
		this(Lists.newArrayList(points));
	}
	
	public GeoLine(List<GeoPoint> points) {
		super();
		this.points = points;
	}

	public List<GeoPoint> getPoints() {
		return points;
	}
	
	
	
}
