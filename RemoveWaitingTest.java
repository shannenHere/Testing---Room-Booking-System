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
public class RemoveWaitingTest {

	private WaitingList waitingList;
	private DisplayUtilities duMock;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		waitingList = new WaitingList();
		waitingList.du = duMock;
	}
	
	private Object[] getUsersToRemove() {
        User vipUser = new User("John", "VIP", false);
        User memberUser = new User("Alice", "Normal", true);
        User normalUser = new User("Bob", "Non-member", false);

        return new Object[] { vipUser, memberUser, normalUser };
    }

    @Test
    @Parameters(method = "getUsersToRemove")
    public void testRemoveWaiting(User user) {
        waitingList.addWaiting(user);
        waitingList.removeWaiting(user);

        ArrayList<User> AR = waitingList.getWaiting(user.getMemberType());
        assertTrue(AR.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWaitingNullUser() {
        waitingList.removeWaiting(null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveWaitingInvalidUserType() {
        User user = new User("Unknown", "Standard", false);
        waitingList.removeWaiting(user);
    }

}
