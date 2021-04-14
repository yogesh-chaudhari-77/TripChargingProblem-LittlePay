import org.junit.*;

import static org.junit.Assert.fail;

import java.text.ParseException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class BusinessHandlerTests {

	BusinessHandler b = new BusinessHandler();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		this.b.getTravelRoutes().addEdge("1", "2", 3.25);
		this.b.getTravelRoutes().addEdge("2", "1", 3.25);
		this.b.getTravelRoutes().addEdge("2", "3", 5.50);
		this.b.getTravelRoutes().addEdge("3", "2", 5.50);
		this.b.getTravelRoutes().addEdge("1", "3", 7.30);
		this.b.getTravelRoutes().addEdge("3", "1", 7.30);


		//		this.b.getRawTrips().add("3,22-01-2018 13:00:00,ON,2,Company1,Bus37,5500005555555558");
		//		this.b.getRawTrips().add("4,22-01-2018 13:05:00,OFF,1,Company1,Bus37,5500005555555558");
		//		this.b.getRawTrips().add("5,22-01-2018 13:00:00,ON,2,Company1,Bus37,5500005555555560");
		//		this.b.getRawTrips().add("6,22-01-2018 13:05:00,ON,3,Company1,Bus37,5500005555555561");
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testCompleteTripCharge() {
		double charge = this.b.getCompleteTripCharge("1", "2");
		Assert.assertEquals("Charge should be 3.25", 3.25, charge, 0.0001);
	}

	@Test
	public void testCompleteTripCharge_reverseDire() {
		double charge = this.b.getCompleteTripCharge("2", "1");
		Assert.assertEquals("Charge should be 3.25", 3.25, charge, 0.0001);
	}

	@Test
	public void testIncompleteTripCharge() {
		String[] charge = this.b.getIncompleteTripCharge("1");
		Assert.assertEquals("Charge should be 7.30", 7.30, Double.parseDouble(charge[1]), 0.0001);
	}

	@Test
	public void testCancelledCharge() {
		double charge = this.b.getCancelledTripDetails("1", "1");
		Assert.assertEquals("Charge should be 0", 0, charge, 0.0001);
	}


	@Test
	public void testCreateTrip_complete() throws ParseException {

		this.b.createTrip("1,22-01-2018 13:00:00,ON,1,Company1,Bus37,5500005555555559");
		Assert.assertEquals("Size should become 1", 1, this.b.getTrips().size());

		this.b.createTrip("2,22-01-2018 13:05:00,OFF,2,Company1,Bus37,5500005555555559");
		Assert.assertEquals("Size should remain 1", 1, this.b.getTrips().size());
	}

	@Test
	public void testCreateTrip_cancelled() throws ParseException {

		this.b.createTrip("1,22-01-2018 13:00:00,ON,1,Company1,Bus37,5500005555555559");
		Assert.assertEquals("Size should become 1", 1, this.b.getTrips().size());

		this.b.createTrip("2,22-01-2018 13:05:00,OFF,1,Company1,Bus37,5500005555555559");
		Assert.assertEquals("Size should remain 1", 1, this.b.getTrips().size());
	}



}
