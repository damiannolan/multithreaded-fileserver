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
				Request r = q.take();
				System.out.println(r.toString());
				fw.write(r.toString() + "\n");
				
				//fw.close();
				
				//new Thread(r);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
