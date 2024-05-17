package STassgn;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class GetWaitingTest {

	private WaitingList waitingList;
	
	@Before
	public void setup() {
		waitingList = new WaitingList();
	}
	
	private Object[] getParamsForTest() {
	    User vipUser = new User("John", "VIP", false);
	    User normalUser = new User("Alice", "Normal", true);
	    User nonMemberUser = new User("Bob", "Non-member", false);

	    ArrayList<User> vipList = new ArrayList<>();
	    vipList.add(vipUser);
	    
	    ArrayList<User> memberList = new ArrayList<>();
	    memberList.add(normalUser);
	    
	    ArrayList<User> normalList = new ArrayList<>();
	    normalList.add(nonMemberUser);

	    return new Object[] {
	        new Object[] { vipUser, vipList },
	        new Object[] { normalUser, memberList },
	        new Object[] { nonMemberUser, normalList}
	    };
	}
	
	@Test
	@Parameters(method = "getParamsForTest")
	public void testGetWaiting(User user, ArrayList<User> ER) {
		waitingList.addWaiting(user);
		waitingList.getWaiting(user.getMemberType());
		ArrayList<User> AR = waitingList.getWaiting(user.getMemberType());
		assertEquals(ER, AR);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testIllegalGetWaiting() {
		waitingList.getWaiting("Standard");
	}
}
