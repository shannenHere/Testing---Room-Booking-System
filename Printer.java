package STassgn;

import java.util.ArrayList;
import java.util.List;

public class Printer {
	
	DisplayUtilities du;
	
	public Printer() {
		this.du = new DisplayUtilities();
	}
	
	public void setDu(DisplayUtilities du) {
		this.du = du;
	}
	
    public void printInfo(User user, ArrayList<Booking> bookings) {
    	// Check if du is null before proceeding
        if (du == null) {
            throw new IllegalStateException("DisplayUtilities (du) is not set. Please set DisplayUtilities before calling printInfo.");
        }
        
    	du.showToScreen("\nBooking Information:\n");
        du.showToScreen("Name: " + user.getName() + "\n");
        du.showToScreen("Member Type: " + user.getMemberType() + "\n");
        du.showToScreen("Exclusive Reward: " + user.getExclReward() + "\n");

        List<String> bookedRooms = new ArrayList<>();
        boolean hasBookings = false;
        for (Booking booking : bookings) {
        	if (booking.getUser().equals(user)) {
                    bookedRooms.add(booking.getRoomType());
                    hasBookings = true;
            }
        }

        if (!hasBookings) {
        	du.showToScreen("No bookings.\n");
        } else {
        	du.showToScreen("Booked Rooms:\n");
             for (String room : bookedRooms) {
            	 du.showToScreen("- " + room + "\n");
             }
        }
        du.showToScreen("\n"); // Empty line for separation
    }
}
