package ie.gmit.sw.server;

import java.io.*;
import java.net.*;

public class Listener implements Runnable {

	private volatile boolean keepRunning = true;
	private ServerSocket ss;
	
	public Listener(ServerSocket ss) {
		this.ss = ss;
	}
	
	//Implement the run() method
	@Override
	public void run() {
		int counter = 0; //A counter to track the number of requests
		while (keepRunning){ //Loop will keepRunning is true. Note that keepRunning is "volatile"
			try { //Try the following. If anything goes wrong, the error will be passed to the catch block
				
				Socket s = ss.accept(); //This is a blocking method, causing this thread to stop and wait here for an incoming request
				
				/* If we get to this line, it means that a client request was received and that the socket "s" is a real network
				 * connection between some computer and this program. We'll farm out this request to a new Thread (worker), 
				 * allowing us to handle the next incoming request (we could have many requests hitting the server at the same time),
				 * so we have to be able to handle them quickly.
				 */
				//new Thread(new HTTPRequest(s), "T-" + counter).start(); //Give the new job to the new worker and tell it to start work
				counter++; //Increment counter
			} catch (IOException e) { //Something nasty happened. We should handle error gracefully, i.e. not like this...
				System.out.println("Error handling incoming request..." + e.getMessage());
			}
		}
	}

}
