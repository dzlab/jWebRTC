package org.webrtc.common;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.webrtc.model.Room;

import net.sf.jtpl.Template;

public class Helper {

	public static final String SERVER = "http://localhost:8080";
	
	/** Used to generate a random room number */
	public static String generate_random(int len) {
		String generated = "";
		for(int i=0; i<len; i++) {
			int index = ((int) Math.round(Math.random()*10))%10;
			generated += "0123456789".charAt(index);
		}		
		return generated;
	}
	
	public static String sanitize(String key){  
		return key.replace("[^a-zA-Z0-9\\-]", "-");
	}
	
	public static String make_token(Room room, String user) {		 
		return room.key() + "/" + user;
	}
	public static String make_token(String room_key, String user) {		 
		return room_key + "/" + user;
	}
	
	public static String make_pc_config(String stun_server) {		
		if(stun_server!=null && !stun_server.equals(""))	
			return "STUN " + stun_server;		 
		else
		    return "STUN stun.l.google.com:19302";
	}

	
	/** Create a {@link Map} from a {@link String} representing an URL query */
	public static Map<String, String> get_query_map(String query) {  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	} 
	
	/** Create a {@link String} from an {@link InputStream} */
	public static String get_string_from_stream(InputStream input) {
		String output = null;
		try {
			StringWriter writer = new StringWriter();
			IOUtils.copy(input, writer);
			output = writer.toString();
		}catch(IOException e) {
			e.printStackTrace();
		}		
		return output;
	}
	
	/** Generate an HTML file by using JTPL template engine to replace variables with their values provided in the map. */
	public static String generatePage(File file, Map<String, String> values) {
		String block = "main"; 
		String output = null;
		try {
			Template tpl = new Template(file);
			for(String key :values.keySet()) {
				tpl.assign(key, values.get(key));
			}     		
			tpl.parse(block);
	        output = tpl.out();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return output;
	}
}
