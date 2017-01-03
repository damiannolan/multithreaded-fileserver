package ie.gmit.sw.client;

import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		// Create a Scanner object and option variable for user interaction
		Scanner console = new Scanner(System.in);
		int option;

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
