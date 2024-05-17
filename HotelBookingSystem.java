package STassgn;

import java.util.ArrayList;
import java.util.Scanner;

//class to replace scanner and System.out.println for testing
class DisplayUtilities {
	Scanner input = new Scanner(System.in);
	
	public void showToScreen(String message) {
		System.out.print(message);
	}
	
	public String getFromScreen() {
		return input.nextLine();
	}
}

public class HotelBookingSystem {
	
	//to discard the use of ArrayList initialization in main
	//for easier testing
	private UserRecordApplication ura;
	private BookingRecordApplication bra;
	
	private DisplayUtilities du;
	private User user;
	private Room room;
	private Booking booking;
	private Printer printer;
	private WaitingList waitingList;
    
    
    public HotelBookingSystem() {
    	this.du = new DisplayUtilities();
    	this.ura = new UserRecordApplication();
    	user = new User();
    	room = new Room();
    }
    
    //setups for test
    public void setDu(DisplayUtilities du) {
    	this.du = du;
    }
    
    public void setUra(UserRecordApplication ura) {
    	this.ura = ura;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
    
    public void setRoom(Room room) {
    	this.room = room;
    }
    
    public void setBra(BookingRecordApplication bra) {
    	this.bra = bra;
    }
    
    public void setPrinter(Printer printer) {
    	this.printer = printer;
    }
    
    public void setWaitingList(WaitingList waitingList) {
    	this.waitingList = waitingList;
    }
    
    public void setBooking(Booking booking) {
    	this.booking = booking;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void performRecordInitializationBeforeLogin() {
        ura = new UserRecordApplication();
        bra = new BookingRecordApplication();
        du = new DisplayUtilities();
        room = new Room();
        booking = new Booking(user, room.getType());
        booking.setDu(du);
        printer = new Printer();
        printer.setDu(du);
        waitingList = new WaitingList();
        
        ura.initializeUsers();
        bra.initializeBookings();
        
        //Add bob to waiting list
        waitingList.addWaiting(ura.getUserRecords().get(2));
    }
    
    public void performRecordInitializationAfterLogin(User user) {
        this.user = user;
        booking = new Booking(user, room.getType());
    }
    
    public User getLoginInfo() {
    	du.showToScreen("Enter your name to log in: ");
    	String name = du.getFromScreen();
    	
    	ArrayList<User> userRecords = ura.getUserRecords();
  		for (User user: userRecords) {
  			if (user.getName().equalsIgnoreCase(name)) {
  				du.showToScreen("Welcome " + user.getName() + "\n");
  				return user;
  			} 
  		}
  		
  		du.showToScreen("User is not in system, please contact an administrator for registration.\n\n");
  		return null;
    }
    
    final String mainMenuDisplayMessage = 
    		"\nMain Menu:\n" +
    		"1. Check available rooms\n" +
    		"2. View bookings\n" +
    		"3. Create bookings\n" +
    		"4. Cancel booking\n" +
    		"5. Exit\n" +
    		"6. Log out\n" +
    		"Enter your choice: ";
    
    public void mainMenu() {
    	String choice = "";
    	while (!choice.equals("5")) {
    		du.showToScreen(mainMenuDisplayMessage);
    		choice = du.getFromScreen();
    		
    		switch(choice) {
    		case "1": 
    			checkAvailableRooms();
    			break;
    		case "2":
    			viewBookings(user);
    			break;
    		case "3":
    			createBooking(user);
    			break;
    		case "4":
    			cancelBooking(bra.getBookingRecords());
    			break;
    		case "5":
    			du.showToScreen("Exiting...");
    			break;
    		case "6":
    			logout();
    			break;
    		default:
    			du.showToScreen("Invalid input, please enter 1-6 only.\n");
    		}
    	} 
    }
    
    
    
    // Method to check available rooms
    public void checkAvailableRooms() {
       du.showToScreen("\nVIP: ");
       if (room.checkRoom("VIP")) {
    	   du.showToScreen(room.getVIP() + " rooms available");
       } else {
    	   du.showToScreen("Not available");
       }
        
       du.showToScreen("\nDeluxe: ");
        if (room.checkRoom("Deluxe")) {
        	du.showToScreen(room.getDeluxe() + " rooms available");
        } else {
        	du.showToScreen("Not available");
        }
        
        du.showToScreen("\nStandard: ");
        if (room.checkRoom("Standard")) {
        	du.showToScreen(room.getStandard() + " rooms available\n");
        } else {
        	du.showToScreen("Not available\n");
        }
        return;
    }
    
    // Method to view bookings for the current user
    public void viewBookings(User user) {
        
        boolean foundBookings = false;
        du.showToScreen("\nBookings for " + user.getName() + ":\n");
        
        ArrayList<Booking> allBookings = bra.getBookingRecords();
        
        //to store bookings of the user
        ArrayList<Booking> userBookings = new ArrayList<Booking>();
        
        for (Booking booking : allBookings) {
            if (booking.getUser().equals(user)) {
            	userBookings.add(booking);
                foundBookings = true;
            }
        }
        
        if (foundBookings) {
            printer.printInfo(user, userBookings); // Print booking information
        } else {
            du.showToScreen("No bookings found for " + user.getName() + ".\n");
        }
    }
    
    final String createBookingDisplayMessage = 
    		"Select room type:\n" +
    		"1. VIP\n" +
    		"2. Deluxe\n" +
    		"3. Standard\n" +
    		"Enter your choice: ";
    
    public void createBooking(User user) {
        du.showToScreen("\nPerforming booking for user: " + user.getName() + "\n");
        boolean maxLimitNotReached = checkMaxBookingLimit(user);
        
        if (maxLimitNotReached) {
        	if (user.getExclReward()) {
        		createWithExclReward();
        	} else {
        		createWithoutExclReward();
        	}
        }
    }

	public boolean checkMaxBookingLimit(User user) {
		
		int maxRoomsToBook = 1; // Default maximum rooms to book
	    
	    // Determine the maximum rooms to book based on the user's member type
	    switch (user.getMemberType()) {
	        case "VIP":
	            maxRoomsToBook = 3;
	            break;
	        case "Normal":
	            maxRoomsToBook = 2;
	            break;
	        case "Non-member":
	            maxRoomsToBook = 1;
	            break;
	        default:
	            du.showToScreen("Invalid user type");
	            return false;
	    }
	    
	    int roomsBooked = 0;
	    
	    ArrayList<Booking> allBookings = bra.getBookingRecords();
        for (Booking booking : allBookings) {
        	if (booking.getUser().equals(user)) {
	                roomsBooked++;
	        }
	    }
        
	    if (roomsBooked < maxRoomsToBook) {
	    	return true;
	    } else {
	    	du.showToScreen("You have already booked the maximum allowed number of rooms.\n");
	        return false;
	    } 
	}

	public void createWithExclReward() {
		
	    boolean redeemExclusiveReward = false;
	    
	    du.showToScreen("You have an exclusive reward.\n");
	    du.showToScreen("Do you want to redeem it? (Y/N): ");
	    String redeemChoice = du.getFromScreen();
	    
	    if (redeemChoice.equalsIgnoreCase("Y")) {
	    	redeemExclusiveReward = true;
	    	user.setExclusiveReward(false);
	    } else if (redeemChoice.equalsIgnoreCase("N")){
	    	du.showToScreen("You chose not to redeem your exclusive reward.\n");
	    	du.showToScreen("You can still redeem it in the future.\n\n");
	    	redeemExclusiveReward = false;
	    	createWithoutExclReward();
	    } else {
	    	redeemExclusiveReward = false;
	    	du.showToScreen("Invalid input. Please enter Y or N.\n");
	    	createWithExclReward();
	    	return;
	    }
	  
	    Booking booking;
	    if (redeemExclusiveReward) {
	        if (room.checkRoom("VIP")) {
	            du.showToScreen("You got a free VIP room from exclusive reward!\n\n");
	            room.vip--;
	            booking = new Booking(user, "VIP");
	            bra.addBookingRecord(booking);
	        } else if (room.checkRoom("Standard")){
	        	du.showToScreen("VIP room not available. Allocating Standard room instead for exclusive reward.\n");
	        	room.standard--;
	        	du.showToScreen("Your exclusive reward remains valid for future redemptions\n\n");
	        	booking = new Booking(user, "Standard");
	        	bra.addBookingRecord(booking);
	        } else {
	        	waitingList.addWaiting(user);
	        	return;
	        }
	    mainMenu();
	   }
	}
	
	public void createWithoutExclReward() {
		du.showToScreen(createBookingDisplayMessage);
		String choice = du.getFromScreen();

		String roomType = null;
        // Validate user input
        switch (choice) {
            case "1":
                roomType = "VIP";
                break;
            case "2":
                roomType = "Deluxe";
                break;
            case "3":
                roomType = "Standard";
                break;
            default:
                du.showToScreen("Invalid choice. Please select a number between 1 and 3.\n");
        }
        
        Booking bookingToAdd;
        if (roomType != null) {
        	String roomToCreate = booking.setBooking(user, roomType);
        	if (roomToCreate == "WaitingList") {
        		waitingList.addWaiting(user);
        	} else {
        		bookingToAdd = new Booking(user, roomToCreate);
        		bra.addBookingRecord(bookingToAdd);
        		
            	if (roomType.equals("VIP")) {
            		room.vip--;
            	} else if (roomType.equals("Deluxe")) {
            		room.deluxe--;
            	} else if (roomType.equals("Standard")) {
            		room.standard--;
            	}

        		du.showToScreen(roomToCreate + " Room Booking created successfully!\n");
        		return;
        	}
        }
	}
	
	// Method to cancel booking
    public void cancelBooking(ArrayList<Booking> allBookings) {
        
        Booking lastBooking = booking.cancelBooking(allBookings, du);
        
        if (lastBooking != null) {
        	allBookings.remove(lastBooking);
        	room.cancelBooking(lastBooking.getRoomType());
        	du.showToScreen("Booking canceled successfully.\n");
        	
        } else {
        	du.showToScreen("Booking cancellation failed or aborted.\n");
        }
            
        // Check if the user is in any of the waiting lists
        if (waitingList.vipWaitingList.contains(user) || 
        	waitingList.memberWaitingList.contains(user) ||
        	waitingList.normalWaitingList.contains(user)) {
        	
            waitingList.removeWaiting(user);
        }
    }
    
    public void logout() {
    	if (user != null) {
    		user = null;
    		du.showToScreen("Logout successful\n\n");
    		
    	} else {
    		du.showToScreen("User is not logged in!\n\n");
    	}
    	
    	user = getLoginInfo();
    	
    	if (user != null) {
    		performRecordInitializationAfterLogin(user);
    		mainMenu();
    	}
    }
    
	public static void main(String[] args) {
		
		HotelBookingSystem hbs = new HotelBookingSystem();
	    hbs.performRecordInitializationBeforeLogin();

	    User user = null;
	    while (user == null) {
	        user = hbs.getLoginInfo();
	    }

	    // Now that a valid user is logged in, perform initialization after login
	    hbs.performRecordInitializationAfterLogin(user);
	    hbs.mainMenu(); // Proceed to the main menu
	}       
} 

