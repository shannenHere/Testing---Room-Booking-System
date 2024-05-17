package STassgn;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class BookingRecordApplicationTest {

	BookingRecordApplication bra;
	
	@Before
    public void setup() {
        bra = new BookingRecordApplication();
        bra.initializeBookings();
    }
	
	@Test
	public void testInitializeBookings() {
		ArrayList<Booking> bookings = bra.getBookingRecords();
		assertNotNull(bookings);
		assertEquals(4, bookings.size());
		
		
		Booking booking1 = bookings.get(0);
		assertEquals("VIP", booking1.getRoomType());
		assertNotNull(booking1.getUser());
		
		Booking booking2 = bookings.get(1);
		assertEquals("Standard", booking2.getRoomType());
		assertNotNull(booking2.getUser());
		
		Booking booking3 = bookings.get(2);
		assertEquals("Standard", booking3.getRoomType());
		assertNotNull(booking3.getUser());
		
		Booking booking4 = bookings.get(3);
		assertEquals("Deluxe", booking4.getRoomType());
		assertNotNull(booking4.getUser());
		
		

	}

}
