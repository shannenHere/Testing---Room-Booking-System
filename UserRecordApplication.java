package STassgn;

import java.util.ArrayList;

public class UserRecordApplication {

	ArrayList<User> userRecords = new ArrayList<User>();
	
	public void setUserRecords(ArrayList<User> userRecords) {
		this.userRecords = userRecords;
	}
	
	public ArrayList<User> getUserRecords(){
		return userRecords;
	}
	
	
	public void initializeUsers() {
		User user1 = new User("John", "VIP", false);
		User user2 = new User("Alice", "Normal", true);
		User user3 = new User("Bob", "Non-member", false);
		
		userRecords.add(user1);
		userRecords.add(user2);
		userRecords.add(user3);
		
	}
}
