package ie.gmit.sw.client;

import java.util.Scanner;

import ie.gmit.sw.client.config.*;

public class ClientRunner {

	public static void main(String[] args) throws Throwable {
		// Create a Scanner object and option variable for user interaction
		Scanner console = new Scanner(System.in);
		int option;
		
		// Parse the XML Configuration file
		Config config = new Config();
		XMLParser xp = new XMLParser(config);
		xp.parse();
		System.out.println(config);
		
		// Instantiate a UserInterface object
		UserInterface ui = new UserInterface();

		while (ui.isRunning()) {
			// Print the menu and await user option
			ui.printMenu();
			option = console.nextInt();

			// Act upon the option using a switch statement
			switch (option) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					ui.quit();
					break;
				default:
			}
		} // end while

		console.close();
	} // end main

} // end Runner
