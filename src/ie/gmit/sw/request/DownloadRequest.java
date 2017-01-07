package ie.gmit.sw.request;

import java.io.*;

public class DownloadRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String command = "Download";

	private String filePath;
	private String fileName;
	
	public DownloadRequest(String clientIp, String fileName) {
		super(clientIp);
		this.fileName = fileName;
	}

	@Override
	public void run() {
		File file = new File(filePath + "/" + fileName);
		byte[] byteArray = new byte[(int)file.length()];
		
		try {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(byteArray,0,byteArray.length); // copied file into byteArray
			
			ObjectOutputStream out = new ObjectOutputStream(super.getSocket().getOutputStream());
			out.writeObject(byteArray);
			out.flush();
			
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
