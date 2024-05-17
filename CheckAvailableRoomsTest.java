package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mockito;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class CheckAvailableRoomsTest {

	DisplayUtilities duMock;
	HotelBookingSystem hbs;
	Room roomMock;
	
	static ArrayList<String[]> linesRead;
	
	static {
		Scanner inputStream;
		linesRead = new ArrayList<String[]>();
		String fileName = "checkAvailableRoomsTestData.txt";
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
		hbs = new HotelBookingSystem();
		duMock = mock(DisplayUtilities.class);
		roomMock = mock(Room.class);
		hbs.setDu(duMock);
		hbs.setRoom(roomMock);
	}
	
	private Object[] getParamsForTest1() {
	    return new Object[] {
	    	new Object[] {linesRead.get(0)[0], linesRead.get(0)[1],
	    			linesRead.get(0)[2], linesRead.get(0)[3], 
	    			linesRead.get(0)[4], linesRead.get(0)[5]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest1")
	public void testCheckAvailableRooms_allAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int vipNo, int deluxeNo, int standardNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getVIP()).thenReturn(vipNo);
		when(roomMock.getDeluxe()).thenReturn(deluxeNo);
		when(roomMock.getStandard()).thenReturn(standardNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen( vipNo + " rooms available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen( deluxeNo + " rooms available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen( standardNo + " rooms available\n");
	}
	
	private Object[] getParamsForTest2() {
	    return new Object[] {
	    	new Object[] {linesRead.get(1)[0], linesRead.get(1)[1],
	    			linesRead.get(1)[2], linesRead.get(1)[3], 
	    			linesRead.get(1)[4]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest2")
	public void testCheckAvailableRooms_VIPDeluxeAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int vipNo, int deluxeNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getVIP()).thenReturn(vipNo);
		when(roomMock.getDeluxe()).thenReturn(deluxeNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen( vipNo + " rooms available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen( deluxeNo + " rooms available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen("Not available\n");
	}
	
	private Object[] getParamsForTest3() {
	    return new Object[] {
	    	new Object[] {linesRead.get(2)[0], linesRead.get(2)[1],
	    			linesRead.get(2)[2], linesRead.get(2)[3], 
	    			linesRead.get(2)[4]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest3")
	public void testCheckAvailableRooms_VIPStandardAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int vipNo, int standardNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getVIP()).thenReturn(vipNo);
		when(roomMock.getStandard()).thenReturn(standardNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen( vipNo + " rooms available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen("Not available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen( standardNo + " rooms available\n");
	}
	
	private Object[] getParamsForTest4() {
	    return new Object[] {
	    	new Object[] {linesRead.get(3)[0], linesRead.get(3)[1],
	    			linesRead.get(3)[2], linesRead.get(3)[3], 
	    			linesRead.get(3)[4]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest4")
	public void testCheckAvailableRooms_DeluxeStandardAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int deluxeNo, int standardNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getDeluxe()).thenReturn(deluxeNo);
		when(roomMock.getStandard()).thenReturn(standardNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen("Not available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen( deluxeNo + " rooms available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen( standardNo + " rooms available\n");
	}
	
	private Object[] getParamsForTest5() {
	    return new Object[] {
	    	new Object[] {linesRead.get(4)[0], linesRead.get(4)[1],
	    			linesRead.get(4)[2], linesRead.get(4)[3]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest5")
	public void testCheckAvailableRooms_VIPAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int vipNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getVIP()).thenReturn(vipNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen( vipNo + " rooms available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen("Not available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen("Not available\n");
	}
	
	private Object[] getParamsForTest6() {
	    return new Object[] {
	    	new Object[] {linesRead.get(5)[0], linesRead.get(5)[1],
	    			linesRead.get(5)[2], linesRead.get(5)[3]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest6")
	public void testCheckAvailableRooms_DeluxeAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int deluxeNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getDeluxe()).thenReturn(deluxeNo);
		
		hbs.checkAvailableRooms();
		
		verify(duMock).showToScreen("\nVIP: ");
		verify(duMock).showToScreen("Not available");
		verify(duMock).showToScreen("\nDeluxe: ");
		verify(duMock).showToScreen( deluxeNo + " rooms available");
		verify(duMock).showToScreen("\nStandard: ");
		verify(duMock).showToScreen("Not available\n");
	}
	
	private Object[] getParamsForTest7() {
	    return new Object[] {
	    	new Object[] {linesRead.get(6)[0], linesRead.get(6)[1],
	    			linesRead.get(6)[2], linesRead.get(6)[3]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest7")
	public void testCheckAvailableRooms_StandardAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable,
			int standardNo) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		when(roomMock.getStandard()).thenReturn(standardNo);
		
		hbs.checkAvailableRooms();
		
		//need to use inorder because "Not available" is called twice
		InOrder inOrder = Mockito.inOrder(duMock);
		inOrder.verify(duMock).showToScreen("\nVIP: ");
		inOrder.verify(duMock).showToScreen("Not available");
		inOrder.verify(duMock).showToScreen("\nDeluxe: ");
		inOrder.verify(duMock).showToScreen("Not available");
		inOrder.verify(duMock).showToScreen("\nStandard: ");
		inOrder.verify(duMock).showToScreen( standardNo + " rooms available\n");
	}
	
	private Object[] getParamsForTest8() {
	    return new Object[] {
	    	new Object[] {linesRead.get(7)[0], linesRead.get(7)[1],
	    			linesRead.get(7)[2]}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest8")
	public void testCheckAvailableRooms_allNotAvailable(boolean vipAvailable, boolean deluxeAvailable, boolean standardAvailable) {
		
		when(roomMock.checkRoom("VIP")).thenReturn(vipAvailable);
		when(roomMock.checkRoom("Deluxe")).thenReturn(deluxeAvailable);
		when(roomMock.checkRoom("Standard")).thenReturn(standardAvailable);
		
		
		hbs.checkAvailableRooms();
		
		InOrder inOrder = Mockito.inOrder(duMock);
		inOrder.verify(duMock).showToScreen("\nVIP: ");
		inOrder.verify(duMock).showToScreen("Not available");
		inOrder.verify(duMock).showToScreen("\nDeluxe: ");
		inOrder.verify(duMock).showToScreen("Not available");
		inOrder.verify(duMock).showToScreen("\nStandard: ");
		inOrder.verify(duMock).showToScreen("Not available\n");
	}
}
