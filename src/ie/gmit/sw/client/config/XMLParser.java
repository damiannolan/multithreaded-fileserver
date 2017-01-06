package ie.gmit.sw.client.config;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser implements Parsator {
	
	private Config config;
	
	public XMLParser(Config config) {
		super();
		this.config = config;
	}
	
	@Override
	public void parse() throws Throwable {
		/* These three lines are part of JAXP (Java API for XML Processing) and are designed to
		 * completely encapsulate how a DOM node tree is manufactured. The concrete classes that
		 * are doing the actual work are part of the Apache Xerces project. JAXP shields us from
		 * having to know and understand the complexity of Xerces through encapsulating the
		 * process.
		 */
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(Config.CONFIG_FILE);
		
		Element root = doc.getDocumentElement(); //Get the root of the node tree
		NodeList children = root.getChildNodes(); //Get the child node of the root
		
		//Get the "username" from the root Element - <config-client>
		config.setUsername(root.getAttribute("username"));
		
		for(int i = 0; i < children.getLength(); i++) {  //Loop over the child nodes
			Node next = children.item(i); //Get the next child
			
			if (next instanceof Element) { //Check if it is an element node
				Element e = (Element) next; //Cast the general node to an element node
				
				if (e.getNodeName().equals("server-host")) { //Check for element <server-host>
					config.setServerHost(e.getFirstChild().getNodeValue());
					
				} else if (e.getNodeName().equals("server-port")) { //Check for element <server-port>
					config.setServerPort(Integer.parseInt(e.getFirstChild().getNodeValue()));
					
				} else if (e.getNodeName().equals("download-dir")) { //Check for element <download-dir>
					config.setDownloadDir(e.getFirstChild().getNodeValue());
				}
			} //end if
		} //end for
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

}
