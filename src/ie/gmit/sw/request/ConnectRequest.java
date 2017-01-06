package ie.gmit.sw.request;

import java.io.*;

public class ConnectRequest extends Request {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private static final String command = "Connection";
	
	public ConnectRequest (String clientIp) {
		super(clientIp);
	}
	
	@Override
	public void run() {		
		try {
			String message = "Connection Successful";
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(message);
			out.flush();
			out.close();
			
			//close socket
			//super.getSocket().close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public String toString() {
		return command + " requested by " + super.getClientIp() + " at " + super.getD().toString();
	}
	
	
}
