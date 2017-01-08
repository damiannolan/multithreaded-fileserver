package ie.gmit.sw.client;

import ie.gmit.sw.client.config.*;

public class ClientRunner {

	public static void main(String[] args) throws Throwable {
		// Parse the XML Configuration file
		Config config = new Config();
		XMLParser xp = new XMLParser(config);
		xp.parse();
		System.out.println(config);
		
		// Instantiate a UserInterface object
		UserInterface ui = new UserInterface();
		int option;
		String fileName;
		
		//Instantiate a FileServerService object to handle server interaction
		//Pass the config object as a parameter so that it knows its target
		FileServerService fileServerService = new FileServerService(config);
		
		while (ui.isRunning()) {
			// Print the menu and await user option
			ui.printMenu();
			option = ui.getOption();

			// Act upon the option using a switch statement
			switch (option) {
				case 1:
					fileServerService.connect();
					break;
				case 2:
					fileServerService.listFiles();
					break;
				case 3:
					fileName = ui.getFileName();
					fileServerService.downloadFile(fileName);
					break;
				case 4:
					ui.quit();
					break;
				default:
			}
		} // end while

		//console.close();
	} // end main

} // end Runner
