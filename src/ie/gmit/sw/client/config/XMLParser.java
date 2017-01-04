package ie.gmit.sw.client.config;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class XMLParser implements Parsator {
	
	private Context ctx;
	
	public XMLParser(Context ctx) {
		super();
		this.ctx = ctx;
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
		Document doc = db.parse(Context.CONFIG_FILE);
		
		Element root = doc.getDocumentElement(); //Get the root of the node tree
		NodeList children = root.getChildNodes(); //Get the child node of the root
		
		//Get the "username" from the root Element - <config-client>
		ctx.setUsername(root.getAttribute("username"));
		
		for(int i = 0; i < children.getLength(); i++) {  //Loop over the child nodes
			Node next = children.item(i); //Get the next child
			
			if (next instanceof Element) { //Check if it is an element node
				Element e = (Element) next; //Cast the general node to an element node
				
				if (e.getNodeName().equals("server-host")) { //Check for element <server-host>
					ctx.setServerHost(e.getFirstChild().getNodeValue());
					
				} else if (e.getNodeName().equals("server-port")) { //Check for element <server-port>
					ctx.setServerPort(Integer.parseInt(e.getFirstChild().getNodeValue()));
					
				} else if (e.getNodeName().equals("download-dir")) { //Check for element <download-dir>
					ctx.setDownloadDir(e.getFirstChild().getNodeValue());
				}
			} //end if
		} //end for
	}

	public Context getCtx() {
		return ctx;
	}

	public void setCtx(Context ctx) {
		this.ctx = ctx;
	}

}
