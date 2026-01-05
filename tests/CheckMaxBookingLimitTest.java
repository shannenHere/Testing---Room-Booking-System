package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CheckMaxBookingLimitTest {

	HotelBookingSystem hbs;
	BookingRecordApplication braMock;
	User userMock;
	
	@Before
	public void setup() {
		hbs = new HotelBookingSystem();
		braMock = mock(BookingRecordApplication.class);
		userMock = mock(User.class);
		hbs.setBra(braMock);
		hbs.setUser(userMock);
	}
	
	private Object[] getParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", false);
		
		Booking booking1 = new Booking(john, "Deluxe");
		Booking booking2 = new Booking(john, "Standard");
		Booking booking3 = new Booking(alice, "Standard");
		Booking booking4 = new Booking(bob, "Standard");
		
		//0 john bookings
		ArrayList<Booking> allBookings1 = new ArrayList<>();
		allBookings1.add(booking3);
		allBookings1.add(booking4);
		
		//2 john bookings, 1 alice bookings
		ArrayList<Booking> allBookings2 = new ArrayList<>();
		allBookings2.add(booking1);
		allBookings2.add(booking2);
		allBookings2.add(booking3);
		allBookings2.add(booking4);
		
		//0 alice bookings
		ArrayList<Booking> allBookings3 = new ArrayList<>();
		allBookings3.add(booking1);
		allBookings3.add(booking2);
		allBookings3.add(booking4);
		
		//0 bob bookings
		ArrayList<Booking> allBookings4 = new ArrayList<>();
		allBookings4.add(booking1);
		allBookings4.add(booking2);
		allBookings4.add(booking3);
		
		
		return new Object[] {
			new Object[] {john, allBookings1},
			new Object[] {john, allBookings2},
			
			new Object[] {alice, allBookings3},
			new Object[] {alice, allBookings2},
			
			new Object[] {bob, allBookings4}
		};
	}
	
	@Test
	@Parameters (method = "getParamsForTest")
	public void testCheckMaxBookingLimit_WithinRange(User user, ArrayList<Booking> allBookings) {
	    
	    when(braMock.getBookingRecords()).thenReturn(allBookings);
	    
	    boolean AR = hbs.checkMaxBookingLimit(user);
	    assertTrue(AR);
	    
	}
	
	private Object[] getIllegalParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", false);
		
		Booking booking1 = new Booking(john, "Deluxe");
	    Booking booking2 = new Booking(john, "Standard");
	    Booking booking3 = new Booking(john, "Standard"); // Third booking for John
	    Booking booking4 = new Booking(alice, "Standard");
	    Booking booking5 = new Booking(alice, "Deluxe"); // Second booking for Alice
	    Booking booking6 = new Booking(bob, "Standard");

		
		//3 john bookings, 2 alice bookings, 1 bob bookings
	    ArrayList<Booking> allBookings = new ArrayList<>();
	    allBookings.add(booking1);
	    allBookings.add(booking2);
	    allBookings.add(booking3);
	    allBookings.add(booking4);
	    allBookings.add(booking5);
	    allBookings.add(booking6);
		
		return new Object[] {
				new Object[] {john, allBookings},
				new Object[] {alice, allBookings},
				new Object[] {bob, allBookings}
		};

	}
	
	@Test
	@Parameters (method = "getIllegalParamsForTest")
	public void testCheckMaxBookingLimit_ExceedLimit(User user, ArrayList<Booking> allBookings) {

	    when(braMock.getBookingRecords()).thenReturn(allBookings);
	    
	    boolean AR = hbs.checkMaxBookingLimit(user);
	    assertFalse(AR);
	}
	

}
