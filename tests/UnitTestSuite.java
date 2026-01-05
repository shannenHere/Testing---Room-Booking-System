package STassgn;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value = Suite.class)
@SuiteClasses(value = {
		SetBookingTest.class,
		CancelBookingTest.class,
		BookingRecordApplicationTest.class,
		PrinterTest.class,
		RoomTest.class,
		UserRecordApplicationTest.class
})
public class UnitTestSuite {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
