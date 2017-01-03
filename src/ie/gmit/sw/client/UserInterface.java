package ie.gmit.sw.client;

public class UserInterface {
	boolean running = true;

	public UserInterface() {
		
	}
	
	public void printMenu() {
		System.out.println("\n1. Connect to Server");
		System.out.println("2. Print File Listing");
		System.out.println("3. Download File");
		System.out.println("4. Quit");
		
		System.out.print("\nType Option [1-4] > ");
	}

	public boolean isRunning() {
		return running;
	}
	
	public void quit() {
		this.running = false;
	}
}
