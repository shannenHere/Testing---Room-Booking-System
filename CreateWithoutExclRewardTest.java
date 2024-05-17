package STassgn;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class CreateWithoutExclRewardTest {

	DisplayUtilities duMock;
	Room roomMock;
	BookingRecordApplication braMock;
	User userMock;
	HotelBookingSystem hbs;
	WaitingList waitingListMock;
	Booking bookingMock;
	
	@Before
	public void setup() {
		hbs = new HotelBookingSystem();
		duMock = mock(DisplayUtilities.class);
		roomMock = mock(Room.class);
		braMock = mock(BookingRecordApplication.class);
		waitingListMock = mock(WaitingList.class);
		bookingMock = mock(Booking.class);
		hbs.setDu(duMock);
		hbs.setRoom(roomMock);
		hbs.setBra(braMock);
		hbs.setWaitingList(waitingListMock);
		hbs.setBooking(bookingMock);
	}
	
	ArrayList<Booking> bookingRecords = new ArrayList<Booking>();
	
	@Test
	public void testCreateWithoutExclReward_VIP() {
		when(duMock.getFromScreen()).thenReturn("1");
		when(bookingMock.setBooking(any(User.class), anyString())).thenReturn("VIP");
		
		hbs.createWithoutExclReward();
		
		verify(roomMock).vip--;
		verify(duMock).showToScreen("VIP" + " Room Booking created successfully!\n");
	}
	
	@Test
	public void testCreateWithoutExclReward_Deluxe() {
		when(duMock.getFromScreen()).thenReturn("2");
		when(bookingMock.setBooking(any(User.class), anyString())).thenReturn("Deluxe");
		
		hbs.createWithoutExclReward();
		
		verify(roomMock).deluxe--;
		verify(duMock).showToScreen("Deluxe" + " Room Booking created successfully!\n");
	}
	
	@Test
	public void testCreateWithoutExclReward_Standard() {
		when(duMock.getFromScreen()).thenReturn("3");
		when(bookingMock.setBooking(any(User.class), anyString())).thenReturn("Standard");
		
		hbs.createWithoutExclReward();
		
		verify(roomMock).standard--;
		verify(duMock).showToScreen("Standard" + " Room Booking created successfully!\n");
	}
	
	@Test
	public void testCreateWithoutExclReward_Invalid() {
		when(duMock.getFromScreen()).thenReturn("4");
		
		hbs.createWithoutExclReward();
		
		verify(duMock).showToScreen("Invalid choice. Please select a number between 1 and 3.\n");
	}
	
	@Test
	public void testCreateWithoutExclReward_WaitingList() {
		when(duMock.getFromScreen()).thenReturn("1");
		when(bookingMock.setBooking(any(User.class), anyString())).thenReturn("WaitingList");
		
		hbs.createWithoutExclReward();
		
		verify(waitingListMock).addWaiting(any(User.class));
	}
}
