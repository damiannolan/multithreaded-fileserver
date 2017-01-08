package ie.gmit.sw.request;

import java.io.*;
import java.util.concurrent.BlockingQueue;

public class RequestLogger implements Runnable {
	
	private BlockingQueue<Request> q;
	private FileWriter fw;
	private boolean keepRunning = true;
	
	public RequestLogger(BlockingQueue<Request> q) throws IOException {
		this.q = q;
		fw = new FileWriter(new File("log.txt"));
	}
	
	@Override
	public void run() {
		while(keepRunning) {
			try {
				//Take from the queue
				Request r = q.take();
				
				if(r instanceof PoisonRequest) {
					keepRunning = false;
				}
				//Print to request to the terminal
				System.out.println("\n" + r.toString());
				//Write the request to the log file
				fw.write(r.toString() + "\n");
				
				//fw.close();				
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} //end while
		
		//Close the file
		this.closeFile(fw);
	}
	
	public void closeFile(FileWriter fw) {
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
