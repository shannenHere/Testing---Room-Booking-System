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
public class AddWaitingTest {

	private WaitingList waitingList;
	private DisplayUtilities duMock;
	
	@Before
	public void setup() {
		duMock = mock(DisplayUtilities.class);
		waitingList = new WaitingList();
		waitingList.du = duMock;
	}
	
	private Object[] getUsersToAdd() {
		User vipUser = new User("John", "VIP", false);
        User memberUser = new User("Alice", "Normal", true);
        User normalUser = new User("Bob", "Non-member", false);

        ArrayList<User> vipList = new ArrayList<>();
        vipList.add(vipUser);

        ArrayList<User> memberList = new ArrayList<>();
        memberList.add(memberUser);

        ArrayList<User> normalList = new ArrayList<>();
        normalList.add(normalUser);
        
        return new Object[] {
                new Object[] { vipUser, "Added John to VIP waiting list.\n", vipList },
                new Object[] { memberUser, "Added Alice to Member waiting list.\n", memberList },
                new Object[] { normalUser, "Added Bob to Normal waiting list.\n", normalList }
            };
	}
	
	@Test
	@Parameters(method = "getUsersToAdd")
	public void testAddWaiting(User user, String expectedMessage, ArrayList<User> ER) {
        waitingList.addWaiting(user);
        verify(duMock).showToScreen(expectedMessage);
        
        ArrayList<User> AR = waitingList.getWaiting(user.getMemberType());
        assertEquals(ER, AR);
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void testAddWaitingInvalidUserType() {
        User user = new User("Unknown", "Standard", false);
        waitingList.addWaiting(user);
    }

}
