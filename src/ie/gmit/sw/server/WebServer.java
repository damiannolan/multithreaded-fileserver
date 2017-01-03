package ie.gmit.sw.server;

import java.io.IOException;
import java.net.*;

public class WebServer {
	private ServerSocket ss; //A server socket listens on a port number for incoming requests
		
	public WebServer(int portNumber) {
		try { //Try the following. If anything goes wrong, the error will be passed to the catch block
			
			ss = new ServerSocket(portNumber); //Start the server socket listening on port 8080
			
			Thread server = new Thread(new Listener(ss), "Web Server Listener"); //We can also name threads
			server.setPriority(Thread.MAX_PRIORITY); //Ask the Thread Scheduler to run this thread as a priority
			server.start(); //The Hollywood Principle - Don't call us, we'll call you
			
			System.out.println("Server started and listening on port " + portNumber);
			
		} catch (IOException e) {
			System.out.println("Error..." + e.getMessage());
		}
	}
}
