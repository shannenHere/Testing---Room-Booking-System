package STassgn;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class MainMenuTest {

	DisplayUtilities duMock;
	User userMock;
	HotelBookingSystem hbs;
	BookingRecordApplication braMock;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		userMock = mock(User.class);
		braMock = mock(BookingRecordApplication.class);
		
		hbs = new HotelBookingSystem();
		hbs.setDu(duMock);
		hbs.setUser(userMock);
	}
	
	final String mainMenuDisplayMessage = 
    		"\nMain Menu:\n" +
    		"1. Check available rooms\n" +
    		"2. View bookings\n" +
    		"3. Create bookings\n" +
    		"4. Cancel booking\n" +
    		"5. Exit\n" +
    		"6. Log out\n" +
    		"Enter your choice: ";
	
	@Test
	public void testMainMenu_choice1() {
		when(duMock.getFromScreen()).thenReturn("1");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(hbs).checkAvailableRooms();
	}
	
	@Test
	public void testMainMenu_choice2() {
		when(duMock.getFromScreen()).thenReturn("2");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(hbs).viewBookings(userMock);
	}
	
	@Test
	public void testMainMenu_choice3() {
		when(duMock.getFromScreen()).thenReturn("3");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(hbs).createBooking(userMock);
	}
	
	@Test 
	public void testMainMenu_choice4() {
		when(duMock.getFromScreen()).thenReturn("4");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(hbs).cancelBooking(braMock.getBookingRecords());
	}
	
	@Test
	public void testMainMenu_choice5() {
		when(duMock.getFromScreen()).thenReturn("5");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(duMock).showToScreen("Exiting...");
	}
	
	@Test
	public void testMainMenu_choice6() {
		when(duMock.getFromScreen()).thenReturn("6");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(hbs).logout();
	}
	
	@Test
	public void testMainMenu_Invalidchoice() {
		when(duMock.getFromScreen()).thenReturn("7");
		hbs.mainMenu();
		verify(duMock).showToScreen(hbs.mainMenuDisplayMessage);
		verify(duMock).showToScreen("Invalid input, please enter 1-6 only.\n");
	}
}
