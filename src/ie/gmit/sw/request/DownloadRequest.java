package ie.gmit.sw.request;

import java.io.*;

public class DownloadRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String command = "Download";
	private String status = "[INFO]";

	private String filePath;
	private String fileName;
	
	public DownloadRequest(String clientIp, String fileName) {
		super(clientIp);
		this.fileName = fileName;
	}

	@Override
	public void run() {	
		try {
			File file = new File(filePath + "/" + fileName);
			byte[] byteArray = new byte[(int)file.length()];
			
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(byteArray,0,byteArray.length); // copied file into byteArray
			
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(byteArray);
			out.flush();
			
			bis.close();
			fis.close();
			
			//Add to queue for logging
			super.addToQueue(this);

		} catch (FileNotFoundException e) {
			this.status = "[ERROR]";
			//Add to the queue for logging
			super.addToQueue(this);
			
			try {
				this.sendErrorResponse();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendErrorResponse() throws IOException {
		String errorResponse = "An error has occurred. Make sure you are using a valid file name";
		
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
