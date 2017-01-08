# A Multithreaded Fileserver - Client Application

##Overview

The purpose of this application is to demonstrate an understanding of Object Orientated Programming using Java.
The application encompasses abstraction, encapsulation, composition, inheritance and polymorphism as well as multithreading.

The File Server running on the user specified port implements a Request Logger that writes user requests made by the user to a text file - log.txt. The Request Logger uses a Blocking Queue to handle writing requests to the file.
A Listener on a separate thread and intercepts client requests and farms them out to other threads as each type of Request is a runnable of its own. When the Request runs its job and sends a response, it is added to the queue for logging to the text file.

The client application features functionality such as:
- Connecting to the server
- Printing a list of files in a resources directory
- Downloading a file from the resources directory to the downloads directory
- Exiting the server application and saving the logs to a text file

## Usage

1. Start the server in your terminal specifying the port number and resources directory

	java -cp oop.jar ie.gmit.sw.server.ServerRunner 7777 myfiles
	
2. Start the client app in your terminal

	java -cp oop.jar ie.gmit.sw.client.ClientRunner