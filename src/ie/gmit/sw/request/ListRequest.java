package ie.gmit.sw.request;

import java.io.*;

public class ListRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String command = "Listing";
	private String status = "[INFO]";
	
	private String filePath;
	
	public ListRequest(String clientIp) {
		super(clientIp);
	}

	@Override
	public void run() {
		try {
			File folder = new File(filePath);
			File[] files = folder.listFiles();
		
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(files);
			out.flush();
			out.close();

			//Add to queue for logging
			super.addToQueue(this);
			
		} catch (IOException e) {
			this.status = "[ERROR]";
			//Add to the queue for logging
			super.addToQueue(this);

			try {
				this.sendErrorResponse();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	public void sendErrorResponse() throws IOException {
		String errorResponse = "An error has occurred. Resources at the file path could not be found.";
		ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
		out.writeObject(errorResponse);
		out.flush();
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return status + " " + command + " requested by " + super.getClientIp() + " at " + super.getD().toString();
	}

}
