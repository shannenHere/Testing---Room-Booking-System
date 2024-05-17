package STassgn;

public class User {
	String name;
	String member_type;
	boolean excl_reward;
	
	public User() {
		
	}
	
	public User(String name, String member_type, boolean excl_reward) {
		this.name = name;
		this.member_type = member_type;
		this.excl_reward = excl_reward;
	}

	public String getMemberType() {
		return member_type;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean getExclReward() {
		return excl_reward;
	}

	public void setExclusiveReward(boolean excl_reward) {
		this.excl_reward = excl_reward;
	}
	
	// Override equals method
	@Override
	public boolean equals(Object obj) {
	    // Check if the object is compared with itself
	    if (this == obj) {
	        return true;
	    }
	    // Check if the object is null or of a different class
	    if (obj == null || getClass() != obj.getClass()) {
	        return false;
	    }
	    // Convert the object to a User instance
	    User otherUser = (User) obj;
	    // Check if both names are null or if one of them is null
	    if (name == null && otherUser.getName() == null) {
	        return true; // Both names are null, so consider them equal
	    }
	    // Compare user names (case-insensitive)
	    return name != null && name.equalsIgnoreCase(otherUser.getName());
	}

}
