package ie.gmit.sw.server;

public class ServerRunner {

	public static void main(String[] args) {
		//variables for port number and file path
		int port;
		String filePath;
		
		try {
			//Attempt to assign the the command line arguments to appropriate variables
			
			port = Integer.parseInt(args[0]);
			filePath = args[1];
		} catch(Exception e) {
			//Print a message to the terminal specifying application needs and return from main()
			
			System.out.println("Please provide arguments - port(int) and filePath(String) respectively");
			
			return;
		}
	
		System.out.println("Test - " + port + " " + filePath);
		
		//WebServer ws = new WebServer(port, filePath);
		//new WebServer();
	} //end main()

}
