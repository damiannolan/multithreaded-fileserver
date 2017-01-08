package ie.gmit.sw.client;

public interface IFileServer {
	
	public void connect();
	
	public void listFiles();
	
	public void downloadFile(String fileName);
	
	public void quit();
}
