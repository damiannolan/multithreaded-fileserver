# A Multithreaded Fileserver & Client Application

##Overview

The purpose of this application is to demonstrate an understanding of Object Orientated Programming using Java.
The application encompasses abstraction, encapsulation, composition, inheritance and polymorphism as well as multithreading.

The client application features functionality such as:
- Parsing of XML configuration file
- Connecting to the server
- Printing a list of files in a resources directory
- Downloading a file from the resources directory to the downloads directory
- Exiting the server application and saving the logs to a text file

When the client application is launched it parses the XML file - config.xml to extract from it a set of values required to interact with the remote server. This is done using the Document Object Model (DOM).
This can be found in the `config` package subpackaged underneath `ie.gmit.sw.client`.

The client application employs an object pretending to be the remote resource (proxy) - `FileServerService`. This implements methods from the interface `IFileServer`.

Requests have been constructed from an abstract class - `Request` which is extended by 4 classes and used for server interaction.
- `ConnectRequest`
- `ListRequest`
- `DownloadRequest`
- `PoisonRequest`

These classes along with the `RequestLogger` have been packaged together in a package - `request`

The File Server running on the user specified port implements a Request Logger that writes user requests made by the user to a text file - `log.txt`. The Request Logger hires a Blocking Queue to handle writing requests to the file.

A `Listener` runs on a separate thread and intercepts client requests and farms them out to other threads as each type of Request is a runnable of its own. When the Request runs its job and sends a response, it is added to the queue for logging to the text file.

## Usage

1. Start the server in your terminal specifying the port number and resources directory

		java -cp oop.jar ie.gmit.sw.server.ServerRunner 7777 myfiles
	
2. Start the client app in your terminal

		java -cp oop.jar ie.gmit.sw.client.ClientRunner
		
##Screenshots

###Server
[Imgur](http://i.imgur.com/sKM1XLI.png)

###Client
[Imgur](http://i.imgur.com/1wFfGA4.png)