package STassgn;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class RoomTest {

	private Object[] getParamsForTestRoomType() {
        return new Object[]{
            // VIP room available
            new Object[]{2, 3, 4, "VIP"},
            // Deluxe room available
            new Object[]{0, 3, 4, "Deluxe"},
            // Standard room available
            new Object[]{0, 0, 1, "Standard"},
            // No rooms available
            new Object[]{0, 0, 0, "No rooms available"}
        };
    }
	
	@Test
    @Parameters(method = "getParamsForTestRoomType")
    public void testRoomType(int vip, int deluxe, int standard, String ER) {
        Room room = new Room(vip, deluxe, standard);
        String AR = room.getType();
        assertEquals(ER, AR);
    }
	
	private Object[] getParamsForTestAvailability() {
        return new Object[]{
            // Available VIP room
            new Object[]{2, 3, 4, "VIP", true},
            // No available Deluxe room
            new Object[]{0, 3, 4, "VIP", false},
            // No rooms available
            new Object[]{0, 0, 0, "No rooms available", false}
        };
    }
	
	@Test
    @Parameters(method = "getParamsForTestAvailability")
    public void testRoomAvailability(int vip, int deluxe, int standard, String roomType, boolean ER) {
        Room room = new Room(vip, deluxe, standard);
        boolean AR = room.checkRoom(roomType);
        assertEquals(ER, AR);
    }
	
	private Object[] getParamsForTestCancelBooking() {
        return new Object[]{
            new Object[]{2, 3, 4, "VIP", 3, 3, 4},
            new Object[]{0, 3, 4, "Deluxe", 0, 4, 4},
            new Object[]{0, 0, 0, "Standard", 0, 0, 1}
        };
    }
	
	@Test
	@Parameters(method = "getParamsForTestCancelBooking")
	public void testCancelBooking(int vip, int deluxe, int standard,
			String roomType, int vipER, int deluxeER, int standardER) {
		Room room = new Room(vip, deluxe, standard);
		room.cancelBooking(roomType);
		assertEquals(vipER, room.getVIP());
		assertEquals(deluxeER, room.getDeluxe());
		assertEquals(standardER, room.getStandard());
	}
	
	private Object[] getIllegalParamsForTestCancelBooking() {
        return new Object[]{
            new Object[]{2, 3, 4, "Normal"}
        };
    }
	
	@Test
	@Parameters(method = "getIllegalParamsForTestCancelBooking")
	public void testIllegalCancelBooking(int vip, int deluxe, int standard, String roomType) {
		Room room = new Room(vip, deluxe, standard);
		room.cancelBooking(roomType);
		assertEquals(vip, room.getVIP());
		assertEquals(deluxe, room.getDeluxe());
		assertEquals(standard, room.getStandard());
	}
}
