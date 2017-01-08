package ie.gmit.sw.client;

/*An interface defining the behavior associated with the file server*/

public interface IFileServer {
	
	public void connect();
	
	public void listFiles();
	
	public void downloadFile(String fileName);
	
	public void quit();
}
