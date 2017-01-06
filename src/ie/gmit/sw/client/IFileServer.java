package ie.gmit.sw.client;

public interface IFileServer {
	
	public void connect();
	
	public void listFiles();
	
	//change this
	public void downloadFile();
	
	public void quit();
}
