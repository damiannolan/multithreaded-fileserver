package ie.gmit.sw.request;

import java.io.*;

public class ConnectRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String status = "[INFO]";
	
	private static final String command = "Connection";
	private String username;
	
	public ConnectRequest (String clientIp, String username) {
		super(clientIp);
		this.username = username;
	}
	
	@Override
	public void run() {		
		try {
			String message = "Connection Successful";
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(message);
			out.flush();
			out.close();
			
			//Add to the queue for logging
			super.addToQueue(this);
			
			//close socket
			//super.getSocket().close();
			
		} catch (IOException e) {
			this.status = "[ERROR]";
			
			//Add to the queue for logging
			super.addToQueue(this);
		}
		
	}

	@Override
	public String toString() {
		return status + " " + command + " requested by " + username + " @ "+ super.getClientIp() + " at " + super.getD().toString();
	}
	
	
}
