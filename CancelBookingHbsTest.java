package STassgn;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CancelBookingHbsTest {

	HotelBookingSystem hbs;
    BookingRecordApplication braMock;
    Booking bookingMock;
    Room roomMock;
    WaitingList waitingListMock;
    User user;
    DisplayUtilities duMock;
    
    @Before
    public void setup() {
        hbs = new HotelBookingSystem();
        duMock = mock(DisplayUtilities.class);
        braMock = mock(BookingRecordApplication.class);
        bookingMock = mock(Booking.class);
        roomMock = mock(Room.class);
        waitingListMock = mock(WaitingList.class);

        hbs.setDu(duMock);
        hbs.setBra(braMock);
        hbs.setBooking(bookingMock);
        hbs.setRoom(roomMock);
        hbs.setWaitingList(waitingListMock);
    }
    
    User john = new User("John", "VIP", false);
	User alice = new User("Alice", "Normal", true);
	User bob = new User("Bob", "Non-member", false);
	
	Booking booking1 = new Booking(john, "Deluxe");
	Booking booking2 = new Booking(john, "VIP");
	Booking booking3 = new Booking(alice, "Deluxe");
	Booking booking4 = new Booking(bob, "Standard");
	
	ArrayList<User> vipWaitingList = new ArrayList<User>();
	ArrayList<User> memberWaitingList = new ArrayList<User>();
	ArrayList<User> normalWaitingList = new ArrayList<User>();
	
	
    private Object[] getParamsForTest() {
    	ArrayList<Booking> allBookings = new ArrayList<>();
    	allBookings.add(booking1);
    	allBookings.add(booking2);
    	allBookings.add(booking3);
    	allBookings.add(booking4);
		
		ArrayList<Booking> johnBookings = new ArrayList<>();
		johnBookings.add(booking1);
		johnBookings.add(booking2); //VIP
		
		ArrayList<Booking> aliceBookings = new ArrayList<>();
		aliceBookings.add(booking3); //Deluxe
		
		ArrayList<Booking> bobBookings = new ArrayList<>();
		bobBookings.add(booking4); //Standard
        
    	return new Object[] {
    			new Object[] {john, allBookings, booking2},
    			new Object[] {alice, allBookings, booking3},
    			new Object[] {bob, allBookings, booking4}
    	};
    }
    
    @Test
    @Parameters (method = "getParamsForTest")
    public void testCancelBooking_NotInWaitingList(User user, ArrayList<Booking> allBookings, Booking bookingToRemove) {
        
    	//empty waiting lists
    	waitingListMock.vipWaitingList = new ArrayList<User>();
    	waitingListMock.memberWaitingList = new ArrayList<User>();
    	waitingListMock.normalWaitingList = new ArrayList<User>();
    	
        when(bookingMock.cancelBooking(allBookings, duMock)).thenReturn(bookingToRemove);
        
        hbs.cancelBooking(allBookings);
        
        assertFalse(allBookings.contains(bookingToRemove));
        
    }
    
    @Test
    @Parameters (method = "getParamsForTest")
    public void testCancelBooking_InWaitingList(User user, ArrayList<Booking> allBookings, Booking bookingToRemove) {
    	
    	ArrayList<User> vipWaitingList = new ArrayList<>();
        vipWaitingList.add(user);
        waitingListMock.vipWaitingList = vipWaitingList;
        waitingListMock.memberWaitingList = new ArrayList<>();
        waitingListMock.normalWaitingList = new ArrayList<>();
    	
        assertTrue(waitingListMock.vipWaitingList.contains(user));
        
    	when(bookingMock.cancelBooking(allBookings, duMock)).thenReturn(bookingToRemove);
    	
        hbs.cancelBooking(allBookings);
       
        assertFalse(waitingListMock.getWaiting("VIP").contains(user));
        assertFalse(allBookings.contains(bookingToRemove));
        
    }

}
