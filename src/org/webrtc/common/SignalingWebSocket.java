package org.webrtc.common;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.eclipse.jetty.websocket.WebSocket;


public class SignalingWebSocket implements WebSocket.OnTextMessage {

	private static final ConcurrentMap<String, SignalingWebSocket> channels = new ConcurrentHashMap<String, SignalingWebSocket>();
	private Connection connection;
	private String token;
	
	public static boolean send(String token, String message) {
		boolean success = false;
		SignalingWebSocket ws = channels.get(token);
		if(ws!=null) 
			success = ws.send(message);					
		return success;
	}

	public void onOpen(Connection connection) {
		// Client (Browser) WebSockets has opened a connection.
		// 1) Store the opened connection
		this.connection = connection;
		// 2) Add ChatWebSocket in the global list of SignalingWebSocket instances instance.
		//webSockets.add(this);
	}

	/**	Loop for each instance of SignalingWebSocket to send message server to each client WebSockets. */
	public void onMessage(String data) {
		try {
/*			
			for (SignalingWebSocket webSocket : webSockets) {
				// send a message to the current client WebSocket.
				webSocket.connection.sendMessage(data);
			}
*/			
		} catch (Exception x) {
			// Error was detected, close the ChatWebSocket client side
			this.connection.disconnect();
		}

	}

	/** Remove ChatWebSocket in the global list of SignalingWebSocket instance. */
	public void onClose(int closeCode, String message) {
		channels.remove(token);
	}
	
	/** Send a message out */
	public boolean send(String message) {
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
