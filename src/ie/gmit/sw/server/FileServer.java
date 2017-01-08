package ie.gmit.sw.server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import ie.gmit.sw.request.*;

public class FileServer {
	//A server socket listens on a port number for incoming requests
	private ServerSocket ss;
	
	private int portNumber;
	private String filePath;
		
	//The boolean value keepRunning is used to control the while loop in the inner class called
	//Listener. The volatile keyword tells the JVM not to cache the value of keepRunning during
	//Optimization, but to check it's value in memory before using it.
	private volatile boolean keepRunning = true;
	
	//Constant for blocking queue size
	private static final int MAX_SIZE = 7;
	
	//Initialize a BlockingQueue with a max size
	private BlockingQueue<Request> q = new ArrayBlockingQueue<>(MAX_SIZE);
		
	public FileServer(int portNumber, String filePath) {
		try { //Try the following. If anything goes wrong, the error will be passed to the catch block
			
			this.portNumber = portNumber;
			this.filePath = filePath;
			
			ss = new ServerSocket(portNumber); //Start the server socket listening on port 8080
			
			Thread server = new Thread(new Listener(), "File Server Listener"); //We can also name threads
			server.setPriority(Thread.MAX_PRIORITY); //Ask the Thread Scheduler to run this thread as a priority
			server.start(); //The Hollywood Principle - Don't call us, we'll call you
			
			//Run a new instance of RequestLogger on its own thread.
			//This thread is the consumer
			new Thread(new RequestLogger(q), "RequestLogger").start();
			
			System.out.println("Server started and listening on port " + this.portNumber);
			
		} catch (IOException e) {
			System.out.println("Error..." + e.getMessage());
		}
	}
	
	/* The inner class Listener is a Runnable, i.e. a job that can be given to a Thread. The job that
	 * the class has been given is to intercept incoming client requests and farm them out to other
	 * threads. Each client request is in the form of a socket and will be handled by a separate new thread.
	 */
	private class Listener implements Runnable{ //A Listener IS-A Runnable
		
		//Implement the method run()
		public void run() {
			int counter = 0; //A counter to track the number of requests
			
			while (keepRunning){ //Loop will keepRunning is true. Note that keepRunning is "volatile"
				try { //Try the following. If anything goes wrong, the error will be passed to the catch block
					
					Socket s = ss.accept(); //This is a blocking method, causing this thread to stop and wait here for an incoming request
					
					//This is the entry point for a request
					
					//This process is called Deserialization or Unmarshalling
	            	ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	            	//Deserialize the request into a Request Object
	                Request request = (Request) in.readObject();
	                
	                //If the Request is a listing request or download request
	                //Set the file path of the directory with content to download
	                if(request instanceof ListRequest) {
	                	((ListRequest) request).setFilePath(filePath);
	                } else if (request instanceof DownloadRequest) {
	                	((DownloadRequest) request).setFilePath(filePath);
	                }
	                
	                //Run the job on its own thread and then add it to the queue for logging
	                request.setSocket(s);
	                request.setQ(q);
	                new Thread(request, "Request-" + counter).start();
	                
	                //Add the request to the queue for logging
					//q.put(request);
										
					//Spawn new thread to handle the request - i.e Log it & response
					//new Thread(new RequestLogger(q), "RequestLogger").start();
					
					counter++; //Increment counter
				} catch (Exception e) {
					System.out.println("Error handling incoming request..." + e.getMessage());
				}
			} //end while
		}
	}//End of inner class Listener
}
