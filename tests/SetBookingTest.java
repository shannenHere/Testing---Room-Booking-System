package STassgn;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class SetBookingTest {

	Booking booking;
	User userMock;
	Room roomMock;
	DisplayUtilities duMock;
	
	static ArrayList<String[]> linesRead;
	
	static {
		Scanner inputStream;
		linesRead = new ArrayList<String[]>();
		String fileName = "setBookingTestData.txt";
		inputStream = null;
		System.out.println("Reading test values from file" + fileName + "\n");
		try {
			inputStream = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file " + fileName);
			System.exit(0);
		}
		while (inputStream.hasNextLine()) {
			String singleLine = inputStream.nextLine();
			String[] tokens = singleLine.split(" ");
			linesRead.add(tokens);
		}
	}

	
	@Before
	public void setup() {
		userMock = mock(User.class);
		roomMock = mock(Room.class);
		duMock = mock(DisplayUtilities.class);
	}
	
	private Object[] getParamsForTest1() {
	    return new Object[] {
	    	new Object[] {linesRead.get(0)[0], linesRead.get(0)[1]}
	    };
	}
	
	//----VIP wanted----
	@Test
	@Parameters(method = "getParamsForTest1")
	public void testSetBooking_VIPwanted_VIPRoomAvailable(String roomType, String ER) {
		//stub VIP as available
		when(roomMock.checkRoom("VIP")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		//call MUT
		String AR = booking.setBooking(userMock, roomType);
		
		//Verify that roomType is returned correctly
		assertEquals(AR, ER);
		
		// No messages should be shown
		verify(duMock, never()).showToScreen(anyString()); 
	}
	
	private Object[] getParamsForTest2() {
	    return new Object[] {
	    	new Object[] {linesRead.get(1)[0], linesRead.get(1)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest2")
	public void testSetBooking_VIPwanted_VIPNotAvailable_DeluxeAvailable(String roomType, String ER) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(false);
		when(roomMock.checkRoom("Deluxe")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		//call MUT
		String AR = booking.setBooking(userMock, roomType);
		
		//Verify that roomType returned correctly
		assertEquals(ER, AR);
		
		//Verify correct message is shown
		verify(duMock).showToScreen("No available VIP rooms. Booking in Deluxe room instead.\n");
	}
	
	private Object[] getParamsForTest3() {
	    return new Object[] {
	    	new Object[] {linesRead.get(2)[0], linesRead.get(2)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest3")
	public void testSetBooking_VIPwanted_VIPDeluxeNotAvailable_StandardAvailable(String roomType, String ER){
		when(roomMock.checkRoom("VIP")).thenReturn(false);
		when(roomMock.checkRoom("Deluxe")).thenReturn(false);
		when(roomMock.checkRoom("Standard")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		//call MUT
		String AR = booking.setBooking(userMock, roomType);
		
		//Verify that roomType returned correctly
		assertEquals(ER, AR);
		
		//Verify correct message is shown
		verify(duMock).showToScreen("No available VIP or Deluxe rooms. Booking in Standard room instead.\n");
	}
	
	private Object[] getParamsForTest4() {
	    return new Object[] {
	    	new Object[] {linesRead.get(3)[0], linesRead.get(3)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest4")
	public void testSetBooking_VIPwanted_AllRoomsNotAvailable(String roomType, String ER) {
		when(roomMock.checkRoom("VIP")).thenReturn(false);
		when(roomMock.checkRoom("Deluxe")).thenReturn(false);
		when(roomMock.checkRoom("Standard")).thenReturn(false);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		//call MUT
		String AR = booking.setBooking(userMock, roomType);
		
		//Verify that roomType returned correctly
		assertEquals(ER, AR);
				
		//Verify correct message is shown
		verify(duMock).showToScreen("No available rooms. Adding to waiting list...\n");
	}
	
	private Object[] getParamsForTest5() {
	    return new Object[] {
	    	new Object[] {linesRead.get(4)[0], linesRead.get(4)[1]}
	    };
	}
	
	//----Deluxe wanted----
	@Test
	@Parameters(method = "getParamsForTest5")
	public void testSetBooking_Deluxewanted_DeluxeAvailable(String roomType, String ER) {
		when(roomMock.checkRoom("Deluxe")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		String AR = booking.setBooking(userMock, roomType);
		
		assertEquals(ER, AR);
		verify(duMock, never()).showToScreen(anyString()); 
	}
	
	private Object[] getParamsForTest6() {
	    return new Object[] {
	    	new Object[] {linesRead.get(5)[0], linesRead.get(5)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest6")
	public void testSetBooking_Deluxewanted_DeluxeNotAvailable_StandardAvailable(String roomType, String ER) {
		when(roomMock.checkRoom("Deluxe")).thenReturn(false);
		when(roomMock.checkRoom("Standard")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		String AR = booking.setBooking(userMock, roomType);
		assertEquals(ER, AR);
		verify(duMock).showToScreen("No available Deluxe rooms. Booking in Standard room instead.\n");
	}
	
	private Object[] getParamsForTest7() {
	    return new Object[] {
	    	new Object[] {linesRead.get(6)[0], linesRead.get(6)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest7")
	public void testSetBooking_Deluxewanted_NoAvailableRooms(String roomType, String ER) {
		when(roomMock.checkRoom("Deluxe")).thenReturn(false);
		when(roomMock.checkRoom("Standard")).thenReturn(false);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		String AR = booking.setBooking(userMock, roomType);
		assertEquals(ER, AR);
		verify(duMock).showToScreen("No available Deluxe or Standard rooms. Adding to waiting list...\n");
	}
	
	private Object[] getParamsForTest8() {
	    return new Object[] {
	    	new Object[] {linesRead.get(7)[0], linesRead.get(7)[1]}
	    };
	}
	
	//----Standard wanted----
	@Test
	@Parameters(method = "getParamsForTest8")
	public void testSetBooking_Standardwanted_StandardAvailable(String roomType, String ER) {
		when(roomMock.checkRoom("Standard")).thenReturn(true);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		String AR = booking.setBooking(userMock, roomType);
		assertEquals(ER, AR);
		verify(duMock, never()).showToScreen(anyString()); 
	}
	
	private Object[] getParamsForTest9() {
	    return new Object[] {
	    	new Object[] {linesRead.get(8)[0], linesRead.get(8)[1]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest9")
	public void testSetBooking_Standardwanted_StandardNotAvailable(String roomType, String ER) {
		when(roomMock.checkRoom("Standard")).thenReturn(false);
		
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		String AR = booking.setBooking(userMock, roomType);
		assertEquals(ER, AR);
		verify(duMock).showToScreen("No available Standard rooms. Adding to waiting list...\n");
	}
	
	private Object[] getParamsForTest10() {
	    return new Object[] {
	    	new Object[] {linesRead.get(9)[0]}
	    };
	}
	
	//----Invalid room type----
	@Test (expected = IllegalArgumentException.class)
	@Parameters(method = "getParamsForTest10")
	public void testSetBooking_InvalidRoomType(String roomType) {
		Booking booking = new Booking(userMock, roomType);
		booking.setDu(duMock);
		booking.setRoom(roomMock);
		
		booking.setBooking(userMock, roomType);
	}
}
