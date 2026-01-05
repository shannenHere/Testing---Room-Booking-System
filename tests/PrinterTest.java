package STassgn;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class PrinterTest {

	private DisplayUtilities duMock;
	private Printer printer;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		printer = new Printer();
	}
	
	private Object[] getParamsForTest() {
		User john = new User("John", "VIP", false);
	    User alice = new User("Alice", "Normal", true);
	    User bob = new User("Bob", "Non-normal", false);
	    
	    Booking booking1 = new Booking(john, "VIP");
	    Booking booking2 = new Booking(alice, "Standard");
	    Booking booking3 = new Booking(bob, "Deluxe");
	    
	    ArrayList<Booking> bookings1 = new ArrayList<>();
	    bookings1.add(booking1);

	    ArrayList<Booking> bookings2 = new ArrayList<>();
	    bookings2.add(booking2);

	    ArrayList<Booking> bookings3 = new ArrayList<>();
	    bookings3.add(booking3);
	    
		return new Object[] {
				new Object[] {john, bookings1},
				new Object[] {alice, bookings2},
				new Object[] {bob, bookings3}
		};
	}
	
	@Test
	@Parameters(method = "getParamsForTest")
	public void testPrintInfo(User user, ArrayList<Booking> bookings) {
		printer.setDu(duMock);
		printer.printInfo(user, bookings);
		
	    verify(duMock).showToScreen("\nBooking Information:\n");
	    verify(duMock).showToScreen("Name: " + user.getName() + "\n");
	    verify(duMock).showToScreen("Member Type: " + user.getMemberType() + "\n");
	    verify(duMock).showToScreen("Exclusive Reward: " + user.getExclReward() + "\n");
	    verify(duMock).showToScreen("Booked Rooms:\n");
	    for (Booking booking : bookings) {
	    	verify(duMock).showToScreen("- " + booking.getRoomType() + "\n");
        }
	    verify(duMock).showToScreen("\n");
	}

	private Object[] getParamsForTestNoBooking() {
		User john = new User("John", "VIP", false);
	    User alice = new User("Alice", "Normal", true);
	    User bob = new User("Bob", "Non-normal", false);
	    
	    ArrayList<Booking> bookings4 = new ArrayList<>();
	    
		return new Object[] {
				new Object[] {john, bookings4},
				new Object[] {alice, bookings4},
				new Object[] {bob, bookings4}
		};
	}
	
	@Test
	@Parameters(method = "getParamsForTestNoBooking")
	public void testPrintInfo_NoBookings(User user, ArrayList<Booking> bookings) {
		printer.setDu(duMock);
		printer.printInfo(user, bookings);
		
		verify(duMock).showToScreen("\nBooking Information:\n");
	    verify(duMock).showToScreen("Name: " + user.getName() + "\n");
	    verify(duMock).showToScreen("Member Type: " + user.getMemberType() + "\n");
	    verify(duMock).showToScreen("Exclusive Reward: " + user.getExclReward() + "\n");
	    verify(duMock).showToScreen("No bookings.\n");
	    verify(duMock).showToScreen("\n");
	}
}
