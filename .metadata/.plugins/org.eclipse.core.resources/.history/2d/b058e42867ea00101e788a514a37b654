package STassgn;

public class Room {
	int vip;
    int deluxe;
    int standard;
    
    DisplayUtilities du;

    public Room(int vip, int deluxe, int standard) {
        this.vip = vip;
        this.deluxe = deluxe;
        this.standard = standard;
        this.du = new DisplayUtilities();
    }
    
    public Room() {
    	vip = 1;
    	deluxe = 2;
    	standard = 3;
    }
    
    public int getVIP() {
    	return vip;
    }
    
    public int getDeluxe() {
    	return deluxe;
    }
    
    public int getStandard() {
    	return standard;
    }
    
    public String getType() {
        if (vip > 0) {
            return "VIP";
        } else if (deluxe > 0) {
            return "Deluxe";
        } else if (standard > 0) {
            return "Standard";
        } else {
            return "No rooms available"; // Handle case when no rooms are available
        }
    }

    // Method to check if a room of a specific type is available
    public boolean checkRoom(String roomType) {
        switch (roomType) {
            case "VIP":
                return vip > 0;
            case "Deluxe":
                return deluxe > 0;
            case "Standard":
                return standard > 0;
            default:
                return false; // Invalid room type
        }
    }
    
    public void cancelBooking(String roomType) {
    	switch(roomType) {
    	case "VIP":
    		vip++;
    		break;
    	case "Deluxe":
    		deluxe++;
    		break;
    	case "Standard":
    		standard++;
    		break;
    	default:
    		du.showToScreen("Invalid room type");
    		break;
    	}
    }
}
