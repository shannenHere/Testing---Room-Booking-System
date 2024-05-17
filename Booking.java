package STassgn;

import java.util.ArrayList;

public class Booking {
	private User user;
	private Room room;
	private String roomType;
	
	//to use in setBooking 
	private DisplayUtilities du;
	
	//to use in cancelBooking
	private Printer printer;
	
	//to use in getting test data from file
	private ArrayList<String> strList;
	private int strLimit = 0;
	
	public Booking(User user, String roomType) {
		this.user = user;
		this.roomType = roomType;
		this.room = new Room();
		this.printer = new Printer();
	}

	//for printer class
	public String getRoomType() {
		return roomType;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public Room getRoom() {
		return room;
	}
	
	public void setDu(DisplayUtilities du) {
		this.du = du;
	}
	
	// Method to set booking
	public String setBooking(User user, String roomType) {
		
		switch (roomType) {
	        case "VIP":
	            if (room.checkRoom("VIP")) {
	                return "VIP";
	            } else if (room.checkRoom("Deluxe")) {
	                du.showToScreen("No available VIP rooms. Booking in Deluxe room instead.\n");
	                return "Deluxe";
	            } else if (room.checkRoom("Standard")) {
	                du.showToScreen("No available VIP or Deluxe rooms. Booking in Standard room instead.\n");
	                return "Standard";
	            } else {
	                du.showToScreen("No available rooms. Adding to waiting list...\n");
	                return "WaitingList";
	            }
	            
	        case "Deluxe":
	        	if (room.checkRoom("Deluxe")) {
	                return "Deluxe";
	            }  else if (room.checkRoom("Standard")) {
	                du.showToScreen("No available Deluxe rooms. Booking in Standard room instead.\n");
	                return "Standard";
	            } else {
	                du.showToScreen("No available Deluxe or Standard rooms. Adding to waiting list...\n");
	                return "WaitingList";
	            }
	            
	        case "Standard":
	        	if (room.checkRoom("Standard")) {
	        		return "Standard";
	            } else {
	            	du.showToScreen("No available Standard rooms. Adding to waiting list...\n");
	            	return "WaitingList";
	            }
	        default: 
	        	throw new IllegalArgumentException("Invalid Room Type!");
	        	
	    }
	}

	public Booking cancelBooking(ArrayList<Booking> allBookings, DisplayUtilities du) {
		
		// found Bookings of user
        ArrayList<Booking> userBookings = new ArrayList<Booking>();
        
        
        for (Booking booking : allBookings) {
            if (booking.getUser().getName().equalsIgnoreCase(user.getName())) {
                userBookings.add(booking);
            } 
        }
        
        if (userBookings.isEmpty()) {
        	du.showToScreen("No bookings found for " + user.getName() + "\n");
        	return null;
        }
        
        printer.printInfo(user, userBookings);
        du.showToScreen("Are you sure you want to cancel the last booking? (Y/N): ");
        
        String choice = du.getFromScreen();
        
        while(!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
        	du.showToScreen("Invalid input. Please enter Y or N: ");
            choice = du.getFromScreen();
        }
        
        if (choice.equalsIgnoreCase("Y")) {
        	return userBookings.get(userBookings.size() - 1);
        	
        } else {
            du.showToScreen("Booking cancellation aborted.\n");
            return null;
        } 
        
	}
	
}
