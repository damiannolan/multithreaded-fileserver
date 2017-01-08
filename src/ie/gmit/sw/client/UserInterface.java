package ie.gmit.sw.client;

import java.util.Scanner;

public class UserInterface {
	Scanner console;
	boolean running = true;

	public UserInterface() {
		console = new Scanner(System.in);
	}
	
	public void printMenu() {
		System.out.println("\n1. Connect to Server");
		System.out.println("2. Print File Listing");
		System.out.println("3. Download File");
		System.out.println("4. Quit");
		
		System.out.print("\nType Option [1-4] > ");
	}
	
	public int getOption() {
		return console.nextInt();
	}
	
	public String getFileName() {
		System.out.print("Please enter a valid file name to download: ");
		return console.next();
	}

	public boolean isRunning() {
		return running;
	}
	
	public void quit() {
		this.running = false;
	}
}
