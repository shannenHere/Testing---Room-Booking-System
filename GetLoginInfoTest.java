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
public class GetLoginInfoTest {

	HotelBookingSystem hbs;
	DisplayUtilities duMock;
	UserRecordApplication uraMock;
	
	@Before
	public void setup() {
		hbs = new HotelBookingSystem();
		duMock = mock(DisplayUtilities.class);
		uraMock = mock(UserRecordApplication.class);
		hbs.setDu(duMock);
		hbs.setUra(uraMock);
	}
	
	public Object[] getParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", true);
		
		ArrayList<User> userRecords = new ArrayList<User>();
		userRecords.add(john);
		userRecords.add(alice);
		userRecords.add(bob);
		
		return new Object[] {
				new Object[] {john, userRecords},
				new Object[] {alice, userRecords},
				new Object[] {bob, userRecords}
		};
	}
	
	@Test
	@Parameters(method = "getParamsForTest")
	public void testGetLoginInfo(User user, ArrayList<User> userRecords) {
	
		when(uraMock.getUserRecords()).thenReturn(userRecords);
		when(duMock.getFromScreen()).thenReturn(user.getName());
		
		User AR = hbs.getLoginInfo();
		
		assertNotNull(AR);
		assertEquals(AR, user);
	}

	public Object[] getIllegalParamsForTest() {
		User john = new User("John", "VIP", false);
		User alice = new User("Alice", "Normal", true);
		User bob = new User("Bob", "Non-member", true);
		User jay = new User("Jay", "VIP", true);
		
		ArrayList<User> userRecords = new ArrayList<User>();
		userRecords.add(john);
		userRecords.add(alice);
		userRecords.add(bob);
		
		return new Object[] {
				new Object[] {jay, userRecords}
		};
	}
	
	@Test
	@Parameters(method = "getIllegalParamsForTest")
	public void testIllegalGetLoginInfo(User user, ArrayList<User> userRecords) {
	
		when(uraMock.getUserRecords()).thenReturn(userRecords);
		when(duMock.getFromScreen()).thenReturn(user.getName());
		
		User AR = hbs.getLoginInfo();
		
		assertNull(AR);
	}
}
