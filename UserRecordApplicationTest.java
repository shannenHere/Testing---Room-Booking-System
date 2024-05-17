package STassgn;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;

@RunWith(JUnitParamsRunner.class)
public class UserRecordApplicationTest {

	UserRecordApplication ura;
	
	@Before
    public void setup() {
        ura = new UserRecordApplication();
        ura.initializeUsers();
    }
	
	@Test
	public void testInitializeUsers() {
		ArrayList<User> users = ura.getUserRecords();
		assertNotNull(users);
		assertEquals(3, users.size());
		
		User user1 = users.get(0);
		assertEquals("John", user1.getName());
		assertEquals("VIP", user1.getMemberType());
		assertEquals(false, user1.getExclReward());
		
		User user2 = users.get(1);
		assertEquals("Alice", user2.getName());
		assertEquals("Normal", user2.getMemberType());
		assertEquals(true, user2.getExclReward());
		
		User user3 = users.get(2);
		assertEquals("Bob", user3.getName());
		assertEquals("Non-member", user3.getMemberType());
		assertEquals(false, user3.getExclReward());
	}

}
