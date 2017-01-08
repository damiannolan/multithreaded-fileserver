package ie.gmit.sw.client;

/*
 * This class provides the application with an object pretending to the remote server on the client side - (proxy)
 * It is used for sending requests and receiving responses from the remote via Sockets
 */

import java.io.*;
import java.net.Socket;

import ie.gmit.sw.client.config.Config;
import ie.gmit.sw.request.*;

public class FileServerService implements IFileServer {

	private Socket s;
	
	private String host;
	private int port;
	private String downloadDir;
	private String username;
	
	private String clientIp;
	private File[] filesList;
	
	public FileServerService (Config cfg) {
		this.host = cfg.getServerHost();
		this.port = cfg.getServerPort();
		this.downloadDir = cfg.getDownloadDir();
		this.username = cfg.getUsername();
	}
	
	@Override
	public void connect() {
		
		try {
			this.s = new Socket(host, port);
			
			//Get clientIp
	        this.clientIp = this.s.getLocalAddress().getHostAddress();
			
	        //Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	        
	        out.writeObject(new ConnectRequest(clientIp, username)); //Serialise
	        out.flush(); //Ensure all data sent by flushing buffers
	        
	        Thread.yield(); //Pause the current thread for a short time (not used much)
	        
	        //Handle response from server 
	        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	        String response = (String) in.readObject(); //Deserialise
	        	        
	        //Display connection message to client
	        System.out.println(response);
	        
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} //end connect()

	@Override
	public void listFiles() {
		try {
			s = new Socket(host, port);
			clientIp = s.getLocalAddress().getHostAddress();
			
			//Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(this.s.getOutputStream());
	        
	        out.writeObject(new ListRequest(this.clientIp));
	        out.flush();

	        Thread.yield(); //Pause the current thread for a short time
	        
	        //Handle response
	        ObjectInputStream in = new ObjectInputStream(this.s.getInputStream());
	        Object obj = in.readObject(); //Deserialise
	        
	        //If the object is a String it will be an error response message
	        if(obj instanceof String) {
	        	String response = (String)obj;
	        	System.out.println("\n" + response);
	        } else { //otherwise it will be the expected filesList
	        	filesList = (File[]) obj;
	 	        
	 	        for(int i = 0; i < filesList.length; i++) {
	 	        	System.out.println(filesList[i].getName());
	 	        }
	        }
	       
		} catch (Exception e) {
			System.out.println("There is no existing connection to the server");
		}
		
        
	} //end listFiles()

	@Override
	public void downloadFile(String fileName) {
		try {
			s = new Socket(host, port);
			clientIp = s.getLocalAddress().getHostAddress();
			
			//Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	        
	        out.writeObject(new DownloadRequest(clientIp, fileName));
	        out.flush();
	
	        Thread.yield(); //Pause the current thread for a short time
	        
	        //Handle response
	        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	        Object obj = in.readObject(); //Deserialise
	        
	        //If the object is a String it will be an error response message
	        if(obj instanceof String) {
	        	String response = (String)obj;
	        	System.out.println("\n" + response);
	        } else { //otherwise it will be the expected byte array
	        	byte[] byteArray = (byte[]) obj;
		        
		        FileOutputStream fos = new FileOutputStream(this.downloadDir + "/" + fileName);
		        fos.write(byteArray);
		        fos.close();
		        
		        //Confirmation message
		        System.out.println("\nSuccessfully downloaded " + fileName + " to " + this.downloadDir);
	        }
	        
		} catch (Exception e) {
			System.out.println("There is no existing connection to the server");
		}
	} //end downloadFile()

	@Override
	public void quit() {	
		try {
			s = new Socket(host, port);
			clientIp = s.getLocalAddress().getHostAddress();
			
			//Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			out.writeObject(new PoisonRequest(clientIp));
	        out.flush();
	        
	        Thread.yield(); //Pause the current thread for a short time
	        
	        //Response
	        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	        String response = (String) in.readObject();
	        
	        System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //end quit()

}
