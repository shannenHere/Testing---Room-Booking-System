package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


@RunWith(JUnitParamsRunner.class)
public class CancelBookingTest {

	private Booking booking;
    private User userMock;
    private DisplayUtilities duMock;
    private Printer printer;
	
	@Before
	public void setup() {
		userMock = mock(User.class);
		duMock = mock(DisplayUtilities.class);
		printer = new Printer();
		printer.setDu(duMock);
	}
	
	User john = new User("John", "VIP", false);
	User alice = new User("Alice", "Normal", true);
	User bob = new User("Bob", "Non-normal", false);
	
	Booking booking1 = new Booking(john, "VIP");
	Booking booking2 = new Booking(alice, "Standard");
	Booking booking3 = new Booking(bob, "Deluxe");
	Booking booking4 = new Booking(john, "Standard");
	
	private Object[] getParamsForTest() {
		
		ArrayList<Booking> allBookings1 = new ArrayList<Booking>();
		allBookings1.add(booking1);
		allBookings1.add(booking2);
		allBookings1.add(booking3);
		allBookings1.add(booking4);
		
		return new Object[] {
				//John wants to cancel
				new Object[] {allBookings1, john, booking4},
				//Alice wants to cancel
				new Object[] {allBookings1, alice, booking2},
				//Bob wants to cancel
				new Object[] {allBookings1, bob, booking3}
		};
	}
	
	@Test
	@Parameters (method = "getParamsForTest")
	public void testCancelBooking_createdBookingBefore_chooseToCancel(ArrayList<Booking> allBookings, User user, Booking ER) {
		
		when(userMock.getName()).thenReturn(user.getName());
		when(duMock.getFromScreen()).thenReturn("Y");
		 
		//Call MUT
		booking = new Booking(user, "Standard");
		booking.setDu(duMock);
		Booking AR = booking.cancelBooking(allBookings, duMock);
		
		//verify(duMock).getFromScreen();
		
		assertEquals(ER, AR);
	}
	
	private Object[] getParamsForTestNoCancel() {
		
		ArrayList<Booking> allBookings1 = new ArrayList<Booking>();
		allBookings1.add(booking1);
		allBookings1.add(booking2);
		allBookings1.add(booking3);
		allBookings1.add(booking4);
		
		return new Object[] {
				//John wants to cancel
				new Object[] {allBookings1, john},
				//Alice wants to cancel
				new Object[] {allBookings1, alice},
				//Bob wants to cancel
				new Object[] {allBookings1, bob}
		};
	}
	@Test
	@Parameters (method = "getParamsForTestNoCancel")
	public void testCancelBooking_createdBookingBefore_chooseNotToCancel(ArrayList<Booking> allBookings, User user) {
		when(userMock.getName()).thenReturn(user.getName());
		when(duMock.getFromScreen()).thenReturn("N");
		 
		//Call MUT
		booking = new Booking(user, "Standard");
		booking.setDu(duMock);
		Booking AR = booking.cancelBooking(allBookings, duMock);
		
		//verify(duMock).getFromScreen();
		
		assertNull(AR);
	}

	private Object[] getIllegalParamsForTest() {
		//booking record without alice and bob
		ArrayList<Booking> allBookings2 = new ArrayList<Booking>();
		allBookings2.add(booking1);
		allBookings2.add(booking4);
		
		//booking record without john
		ArrayList<Booking> allBookings3 = new ArrayList<Booking>();
		allBookings3.add(booking2);
		allBookings3.add(booking3);
		
		//empty booking record
		ArrayList<Booking> allBookings4 = new ArrayList<Booking>();
		
		//other user record 
		User unknown = new User("Unknown", "VIP", true);
		
		return new Object[] {
				new Object[] {allBookings2, alice},
				new Object[] {allBookings2, bob},
				
				new Object[] {allBookings3, john},
				
				new Object[] {allBookings4, john},
				new Object[] {allBookings4, alice},
				new Object[] {allBookings4, bob},
				
				new Object[] {allBookings2, unknown},
				new Object[] {allBookings3, unknown},
				new Object[] {allBookings4, unknown}
		};
	}
	
	@Test
	@Parameters (method = "getIllegalParamsForTest")
	public void testCancelBooking_noCreatedBookingBefore(ArrayList<Booking> allBookings, User user) {
		when(userMock.getName()).thenReturn(user.getName());
		 
		//Call MUT
		booking = new Booking(user, "Standard");
		booking.setDu(duMock);
		Booking AR = booking.cancelBooking(allBookings, duMock);
		
		assertNull(AR);
	}
}
