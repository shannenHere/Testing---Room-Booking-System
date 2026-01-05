package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class LogOutTest {

	DisplayUtilities duMock;
	HotelBookingSystem hbs;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		hbs = new HotelBookingSystem();
		hbs.setDu(duMock);
	}
	
	@Test
    public void testLogout_UserLoggedIn() {
        
        User user = new User("John", "VIP", false);
        hbs.setUser(user);

        hbs.logout();

        verify(duMock).showToScreen("Logout successful\n\n");
        assertEquals(null, hbs.getUser());
    }

    @Test
    public void testLogout_UserNotLoggedIn() {
       
        hbs.setUser(null);

        hbs.logout();

        verify(duMock).showToScreen("User is not logged in!\n\n");
        assertEquals(null, hbs.getUser());
    }

}
