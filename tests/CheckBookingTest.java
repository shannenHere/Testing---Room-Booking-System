package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameter;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CreateBookingTest {

	DisplayUtilities duMock;
	HotelBookingSystem hbs;
	User userMock;
	BookingRecordApplication braMock;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		userMock = mock(User.class);
		braMock = mock(BookingRecordApplication.class);
		hbs = new HotelBookingSystem();
		hbs.setDu(duMock);
		hbs.setUser(userMock);
		hbs.setBra(braMock);
	}
	
	private Object[] getParamsForTest() {
		User john = new User("John", "VIP", true);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", true);
		
		Booking booking1 = new Booking(john, "Deluxe");
		Booking booking2 = new Booking(john, "Standard");
		Booking booking3 = new Booking(alice, "Standard");
		Booking booking4 = new Booking(bob, "Standard");
		
		ArrayList<Booking> allBookings1 = new ArrayList<>();
		allBookings1.add(booking1);
		allBookings1.add(booking2);
		allBookings1.add(booking3);
		allBookings1.add(booking4);
		
		return new Object[] {
			new Object[] {john, allBookings1},	
			new Object[] {alice, allBookings1},
			new Object[] {bob, allBookings1}
		};
	}
	
	@Test
	@Parameters (method = "getParamsForTest")
	public void testCreateBooking_MaxLimitNotReached_UserHaveExclReward(User user, ArrayList<Booking> allBookings) {
		
		when(hbs.checkMaxBookingLimit(user)).thenReturn(true);
		
		//called in checkMaxBookingLimit
		when(braMock.getBookingRecords()).thenReturn(allBookings);
		
		hbs.createBooking(user);
		
		verify(duMock).showToScreen("\nPerforming booking for user: " + user.getName() + "\n");
		verify(hbs).createWithExclReward();
	}
	
	private Object[] getParamsForTest2() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", false);
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
		
		return new Object[] {
			new Object[] {john, allBookings1},	
			new Object[] {alice, allBookings1},
			new Object[] {bob, allBookings1}
		};
	}
	
	@Test
	@Parameters (method = "getParamsForTest2")
	public void testCreateBooking_MaxLimitNotReached_UserDontHaveExclReward(User user, ArrayList<Booking> allBookings) {
		
		when(hbs.checkMaxBookingLimit(user)).thenReturn(true);
		
		//called in checkMaxBookingLimit
		when(braMock.getBookingRecords()).thenReturn(allBookings);
				
		hbs.createBooking(user);
		
		
		verify(duMock).showToScreen("\nPerforming booking for user: " + user.getName() + "\n");
		verify(hbs).createWithoutExclReward();
	}
}


