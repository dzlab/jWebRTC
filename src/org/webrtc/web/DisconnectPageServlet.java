package org.webrtc.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.webrtc.common.Helper;
import org.webrtc.common.SignalingWebSocket;
import org.webrtc.model.Room;

public class DisconnectPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DisconnectPageServlet.class.getName()); 
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String[] key = req.getParameterValues("from");
		if(key != null && key.length>0) {
			String[] values = key[0].split("/");
			String room_key = values[0];
			String user = values[1];
		    log.info("Removing user " + user + " from room " + room_key);

		    Room room = Room.get_by_key_name(room_key);
		    if(room!=null && room.has_user(user)) {
		    	String other_user = room.get_other_user(user);
		    	room.remove_user(user);
		    	log.info("Room " + room_key + " has state " + room.toString());
		    	if(other_user!=null) {
		    		//TODO send a message to other party on it async channel
		    		SignalingWebSocket.send(Helper.make_token(room, other_user), "{'type':'bye'}");
			        log.info("Sent BYE to " + other_user);
			    }
		    } else
		    	log.warning("Unknown room " + room_key);
		}
		
	}
}
