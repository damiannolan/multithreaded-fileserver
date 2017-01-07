package ie.gmit.sw.client;

import java.io.*;
import java.net.*;
import java.util.*;
import ie.gmit.sw.client.config.Config;
import ie.gmit.sw.request.*;

public class FileServerService implements IFileServer {

	private Socket s;
	
	private String host;
	private int port;
	private String downloadDir;
	
	private String clientIp;
	private File[] filesList;
	
	
	public FileServerService (Config cfg) {
		this.host = cfg.getServerHost();
		this.port = cfg.getServerPort();
		this.downloadDir = cfg.getDownloadDir();
	}
	
	@Override
	public void connect() {
		
		try {
			s = new Socket(host, port);
			
			//Get clientIp 
	        clientIp = s.getLocalAddress().getHostAddress();
			
	        //Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	        
	        out.writeObject(new ConnectRequest(clientIp)); //Serialise
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
	}

	@Override
	public void listFiles() {
		try {
			s = new Socket(host, port);
			clientIp = s.getLocalAddress().getHostAddress();
			
			//Serialise / marshal a request to the server
	        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
	        
	        out.writeObject(new ListRequest(clientIp));
	        out.flush();

	        Thread.yield(); //Pause the current thread for a short time
	        
	        //Handle response
	        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
	        filesList = (File[]) in.readObject(); //Deserialise
	        
	        for(int i = 0; i < filesList.length; i++) {
	        	System.out.println(filesList[i].getName());
	        }
		} catch (Exception e) {
			//System.out.println("There is no existing connection");
			e.printStackTrace();
		}
		
        
	}

	@Override
	public void downloadFile() {
		Scanner console = new Scanner(System.in);
		
		System.out.print("Enter a file name from the list to download: ");
		String fileName = console.next();
				
		for(int i = 0; i < filesList.length; i++) {
			if(fileName.equals(filesList[i].getName())) {

				try {
					s = new Socket(host, port);
					clientIp = s.getLocalAddress().getHostAddress();
					
					//Serialise / marshal a request to the server
			        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			        
			        out.writeObject(new DownloadRequest(clientIp, fileName));
			        out.flush();
			
			        Thread.yield(); //Pause the current thread for a short time
			        
			        ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			        byte[] byteArray = (byte[]) in.readObject();
			        
			        FileOutputStream fos = new FileOutputStream(downloadDir + "/" + fileName);
			        fos.write(byteArray);
			        fos.close();
			        
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void quit() {
		// TODO Auto-generated method stub
		
	}

}
