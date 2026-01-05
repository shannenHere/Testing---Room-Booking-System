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
public class ViewBookingsTest {

	DisplayUtilities duMock;
	BookingRecordApplication braMock;
	Printer printerMock;
	HotelBookingSystem hbs;
	    
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
        braMock = mock(BookingRecordApplication.class);
        printerMock = mock(Printer.class);
        hbs = new HotelBookingSystem();
        hbs.setDu(duMock);
        hbs.setBra(braMock);
        hbs.setPrinter(printerMock);
	}
	
	private Object[] getParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", false);
		
		Booking booking1 = new Booking(john, "Deluxe");
		Booking booking2 = new Booking(john, "Standard");
		Booking booking3 = new Booking(alice, "Standard");
		Booking booking4 = new Booking(bob, "Standard");
		
		ArrayList<Booking> allBookings1 = new ArrayList<>();
		allBookings1.add(booking1);
		allBookings1.add(booking2);
		allBookings1.add(booking3);
		allBookings1.add(booking4);
		
		ArrayList<Booking> johnBookings = new ArrayList<>();
		johnBookings.add(booking1);
		johnBookings.add(booking2);
		
		ArrayList<Booking> aliceBookings = new ArrayList<>();
		aliceBookings.add(booking3);
		
		ArrayList<Booking> bobBookings = new ArrayList<>();
		bobBookings.add(booking4);
		
		return new Object[] {
				new Object[] {john, allBookings1, johnBookings},
				new Object[] {alice, allBookings1, aliceBookings},
				new Object[] {bob, allBookings1, bobBookings}
		};
	}
	
	@Test
	@Parameters (method = "getParamsForTest")
	public void testViewBookings_BookingsFound(User user, ArrayList<Booking> allBookings, ArrayList<Booking> ER) {
		when(braMock.getBookingRecords()).thenReturn(allBookings);
		hbs.viewBookings(user);
		verify(duMock).showToScreen("\nBookings for " + user.getName() + ":\n");
		verify(printerMock).printInfo(user, ER);
	}
	
	private Object[] getIllegalParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", false);
		User jay = new User("Jay", "VIP", true);
		
		Booking booking1 = new Booking(john, "Deluxe");
		Booking booking2 = new Booking(john, "Standard");
		Booking booking3 = new Booking(alice, "Standard");
		Booking booking4 = new Booking(bob, "Standard");
		
		ArrayList<Booking> allBookings2 = new ArrayList<>();
		allBookings2.add(booking1);
		allBookings2.add(booking2);
		allBookings2.add(booking3);
		allBookings2.add(booking4);
		
		ArrayList<Booking> allBookings3 = new ArrayList<>();
		
		return new Object[] {
				new Object[] {jay, allBookings2},
				new Object[] {john, allBookings3},
				new Object[] {alice, allBookings3},
				new Object[] {bob, allBookings3},
				new Object[] {jay, allBookings3}
		};
	}
	
	@Test
	@Parameters (method = "getIllegalParamsForTest")
	public void testViewBookings_BookingsNotFound(User user, ArrayList<Booking> allBookings) {
		when(braMock.getBookingRecords()).thenReturn(allBookings);
		hbs.viewBookings(user);
		verify(duMock).showToScreen("No bookings found for " + user.getName() + ".\n");
	}

}
