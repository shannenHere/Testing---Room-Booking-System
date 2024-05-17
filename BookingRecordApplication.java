package STassgn;

import java.util.ArrayList;

public class BookingRecordApplication {
	
	UserRecordApplication ura = new UserRecordApplication();

	ArrayList<Booking> bookingRecords = new ArrayList<Booking>();
	
	public void setBookingRecords(ArrayList<Booking> bookingRecords) {
        this.bookingRecords = bookingRecords;
    }
	
	public ArrayList<Booking> getBookingRecords() {
		return bookingRecords;
	}
	
	public void addBookingRecord(Booking booking) {
		bookingRecords.add(booking);
	}
	
	public void initializeBookings() {
		ura.initializeUsers();
		
		Booking booking1 = new Booking(ura.userRecords.get(0), "VIP");
		Booking booking2 = new Booking(ura.userRecords.get(0), "Standard");
		Booking booking3 = new Booking(ura.userRecords.get(1), "Standard");
		Booking booking4 = new Booking(ura.userRecords.get(2), "Deluxe");
		
		bookingRecords.add(booking1);
		bookingRecords.add(booking2);
		bookingRecords.add(booking3);
		bookingRecords.add(booking4);
		
	}

}
