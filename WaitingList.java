package STassgn;

import java.util.ArrayList;

public class WaitingList {
    ArrayList<User> vipWaitingList;
    ArrayList<User> memberWaitingList;
    ArrayList<User> normalWaitingList;

    DisplayUtilities du;
    
    public WaitingList() {
        vipWaitingList = new ArrayList<>();
        memberWaitingList = new ArrayList<>();
        normalWaitingList = new ArrayList<>();
        this.du = new DisplayUtilities();
    }
    
    public void addWaiting(User user) {
    	String memberType = user.getMemberType();
        if (memberType.equals("VIP")) {
        	du.showToScreen("Added " + user.getName() + " to VIP waiting list.\n");
            vipWaitingList.add(user);
        } else if (memberType.equals("Normal")) {
        	du.showToScreen("Added " + user.getName() + " to Member waiting list.\n");
            memberWaitingList.add(user);
        } else if (memberType.equals("Non-member")) {
        	du.showToScreen("Added " + user.getName() + " to Normal waiting list.\n");
        	normalWaitingList.add(user);
        } else {
        	throw new IllegalArgumentException("Invalid user type!");
        }
    }
    
    public ArrayList<User> getWaiting(String memberType) {
        switch (memberType) {
            case "VIP":
                return vipWaitingList;
            case "Normal":
                return memberWaitingList;
            case "Non-member":
                return normalWaitingList;
            default:
                throw new IllegalArgumentException("Invalid user type");
        }
    }


    public void removeWaiting(User user) {
    	if (user == null) {
    		throw new IllegalArgumentException("User is null");
    	}
    	if (user.getMemberType() == "VIP") {
            vipWaitingList.remove(user);
            du.showToScreen("Removed " + user.getName() + "\n");
        } else if (user.getMemberType() == "Normal") {
            memberWaitingList.remove(user);
            du.showToScreen("Removed " + user.getName() + " from Member waiting list\n");
        } else if (user.getMemberType() == "Non-member") {
            normalWaitingList.remove(user);
            du.showToScreen("Removed " + user.getName() + " removed from Normal waiting list\n");
        } else {
        	throw new IllegalArgumentException("Invalid user type: " + user.getMemberType());
        }
    }

}
