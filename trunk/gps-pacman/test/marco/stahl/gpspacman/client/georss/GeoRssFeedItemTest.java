package marco.stahl.gpspacman.client.georss;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import marco.stahl.gpspacman.client.geo.GeoLine;
import marco.stahl.gpspacman.client.geo.GeoPoint;

import org.junit.Before;
import org.junit.Test;

public class GeoRssFeedItemTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void test_readLine() throws Exception {
		GeoLine line = GeoRssFeedItem.createLine("1.123 2.456");
		assertNotNull(line);
		assertThat(line.getPoints(), contains(new GeoPoint(1.123, 2.456)));
	}
	
	@Test
	public void test_readLineWithLineBreak() throws Exception {
		GeoLine line = GeoRssFeedItem.createLine("1.123 2.456 \n 1 2.2 ");
		assertThat(line.getPoints(), contains(new GeoPoint(1.123, 2.456),new GeoPoint(1, 2.2)));
	}
}
