package ie.gmit.sw.request;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PoisonRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PoisonRequest(String clientIp) {
		super(clientIp);
	}

	@Override
	public void run() {
		String message = "Shutting down server";
		try {
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(message);
			out.flush();
			out.close();
			
			//Add the poison request to the queue to kill
			super.addToQueue(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		return "Exiting...and closing log.txt";
	}

}
