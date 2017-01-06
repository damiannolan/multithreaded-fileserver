package ie.gmit.sw.request;

import java.io.*;

public class ListRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String command = "Listing";
	
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
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return command + " requested by " + super.getClientIp() + " at " + super.getD().toString();
	}

}
