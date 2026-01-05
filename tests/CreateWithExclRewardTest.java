package STassgn;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class CreateWithExclRewardTest {

	DisplayUtilities duMock;
	Room roomMock;
	BookingRecordApplication braMock;
	User userMock;
	HotelBookingSystem hbs;
	WaitingList waitingListMock;
	
	@Before
	public void setup() {
		hbs = new HotelBookingSystem();
		duMock = mock(DisplayUtilities.class);
		roomMock = mock(Room.class);
		braMock = mock(BookingRecordApplication.class);
		waitingListMock = mock(WaitingList.class);
		hbs.setDu(duMock);
		hbs.setRoom(roomMock);
		hbs.setBra(braMock);
		hbs.setWaitingList(waitingListMock);
	}
	
	@Test
	public void testCreateWithExclReward_RedeemExclReward_VIPAvailable() {
		when(duMock.getFromScreen()).thenReturn("Y");
		when(roomMock.checkRoom("VIP")).thenReturn(true);
		
		hbs.createWithExclReward();
		
		verify(duMock).showToScreen("You have an exclusive reward.\n");
		verify(duMock).showToScreen("Do you want to redeem it? (Y/N): ");
		verify(roomMock).checkRoom("VIP");
        verify(duMock).showToScreen("You got a free VIP room from exclusive reward!\n\n");
        verify(roomMock).vip--;
        verify(braMock).addBookingRecord(any(Booking.class));
        
	}
	
	@Test
	public void testCreateWithExclReward_RedeemExclReward_VIPNotAvailable_StandardAvailable() {
		when(duMock.getFromScreen()).thenReturn("Y");
		when(roomMock.checkRoom("VIP")).thenReturn(false);
		when(roomMock.checkRoom("Standard")).thenReturn(true);
		
		hbs.createWithExclReward();
		
		verify(duMock).showToScreen("You have an exclusive reward.\n");
		verify(duMock).showToScreen("Do you want to redeem it? (Y/N): ");
		verify(roomMock).checkRoom("VIP");
		verify(roomMock).checkRoom("Standard");
        verify(duMock).showToScreen("VIP room not available. Allocating Standard room instead for exclusive reward.\n");
        verify(roomMock).standard--;
        verify(braMock).addBookingRecord(any(Booking.class));
        
	}
	
	@Test
	public void testCreateWithExclReward_NoRedeemExclReward() {
		when(duMock.getFromScreen()).thenReturn("N");
		
		hbs.createWithExclReward();
		
		verify(duMock).showToScreen("You have an exclusive reward.\n");
		verify(duMock).showToScreen("Do you want to redeem it? (Y/N): ");
        verify(waitingListMock).addWaiting(any(User.class));
	}
}
