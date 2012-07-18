package org.webrtc.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

import org.eclipse.jetty.websocket.WebSocket;
import org.webrtc.model.Room;


public class SignalingWebSocket implements WebSocket.OnTextMessage {

	private static final Logger logger = Logger.getLogger(SignalingWebSocket.class.getName()); 
	private static final ConcurrentMap<String, SignalingWebSocket> channels = new ConcurrentHashMap<String, SignalingWebSocket>();
	private Connection connection;
	private String token;
	
	public static boolean send(String token, String message) {
		logger.info("Signalisation: sending out message ("+message+") for "+token);
		boolean success = false;
		SignalingWebSocket ws = channels.get(token);
		if(ws!=null) 
			success = ws.send(message);					
		return success;
	}

	public void onOpen(Connection connection) {
		logger.info("Signalisation: connection opened.");
		// Client (Browser) WebSockets has opened a connection.
		// 1) Store the opened connection
		this.connection = connection;
		// 2) Add ChatWebSocket in the global list of SignalingWebSocket instances instance.
		//webSockets.add(this);
	}

	/**	check if message is token declaration and then store mapping between the token and this ws. */
	public void onMessage(String data) {
		
		try {			
			if(data.startsWith("token")) {
				int index = data.indexOf(":");
				String token = data.substring(index+1);
				channels.put(token, this);
				logger.info("Adding token: "+token);
			}
		} catch (Exception x) {
			// Error was detected, close the ChatWebSocket client side
			this.connection.disconnect();
		}

	}

	/** Remove ChatWebSocket in the global list of SignalingWebSocket instance. */
	public void onClose(int closeCode, String message) {
		logger.info("Signalisation: connection closed.");
		if(token!=null) {
			Room.disconnect(token);
			channels.remove(token);
		}
	}
	
	/** Send a message out */
	public boolean send(String message) {
		logger.info("Signalisation: sending message ... " + message);
		boolean success = false;
		if(connection!=null) {
			try {
				connection.sendMessage(message);
				success = true;
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return success;
	}
}
