package org.webrtc.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class Room {
	
	private static final Logger log = Logger.getLogger(Room.class.getName()); 
	private static final ConcurrentMap<String, Room> DB = new ConcurrentHashMap<String, Room>();
	
	/** Retrieve a {@link Room} instance from database */
	public static Room get_by_key_name(String room_key) {
		return DB.get(room_key);
	}
	
	//All the data we store for a room
	protected String key_name;
	protected String user1;
	protected String user2;
	
	public Room() {}
	
	public Room(String room_key) {
		key_name = room_key;		
		put();
	}
	
	/** @return a {@link String} representation of this room */
	public String toString() {
		String str = "[";
		if(user1!=null && !user1.equals(""))		      
			str += user1;
		if(user2!=null && !user2.equals(""))		      
			str += ", " + user2;		   
		str += "]";		   
		return str;
	}
	
	/** @return number of participant in this room */
	public int get_occupancy() {
		int occupancy = 0;
	    if(user1!=null && !user1.equals(""))
	    	occupancy += 1;
	    if(user2!=null && !user2.equals(""))	
	    	occupancy += 1;
	    return occupancy;
	}
	
	/** @return the name of the other participant */
	public String get_other_user(String user) {		
	    if(user.equals(user1))	      
	    	return user2;
	    else if(user.equals(user2))
	    	return user1;
	    else
	      return null;
	}
	
	/** @return true if one the participant is named as the input paramter, false otherwise */
	public boolean has_user(String user) {	    
		return (user!=null && (user.equals(user1) || user.equals(user2)));
	}
	
	/** Add a new participant to this room 
	 * @return if participant is found */
	public boolean add_user(String user) {
		boolean success = true; 
	    if(user1==null || user1.equals(""))	        
	    	user1 = user;
	    else if(user2==null || user2.equals(""))
	        user2 = user;
	    else {
	        success = false;
	    	System.out.print("room is full");
	    }
	    return success;
	}

	/** Removed a participant form current room */
	public void remove_user(String user) {
	    if(user!=null && user.equals(user2))
	    	user2 = null;
	    if(user!=null && user.equals(user1)) {
	    	if(user2!=null && !user2.equals("")) {
	    		user1 = user2;
	    		user2 = null;
	    	} else
	    		user1 = null;
	    }  
	    if(get_occupancy() > 0)
	      put();
	    else
	      delete();
	      
	}
	
	/**@return the key of this room. */
	public String key() {
		return key_name;
	}
	
	/** Store current instance into database */
	public void put() {
		log.info("Saving current room instance (key: "+key_name+") to database.");
		DB.put(key_name, this);
	}
	/** Delete/Remove current instance from database */
	public void delete() {
		log.info("Deleting current room instance (key: "+key_name+") from database.");
		if(key_name!=null)
			DB.remove(key_name);
	}
}
