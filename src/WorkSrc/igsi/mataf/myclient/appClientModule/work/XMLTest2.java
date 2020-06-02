package work;
/*
 */

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;

public class XMLTest2
{
	
	
	
	void testEx()
	{
		String fName = "c:/app/conf/config.xml";
		try {
		  FileInputStream fis = new FileInputStream(fName);			
		} catch(FileNotFoundException fe) {
			System.err.println("cannot find file: " + fName);
			fe.printStackTrace();	
		}
		
		
	}
	public static void main (String argv [])
	throws IOException
	{
	String		uri;

	/*
	if (argv.length != 1) {
		System.err.println ("Usage: cmd filename");
		System.exit (1);
	}
	*/

	try {
		uri = "c:/dev/src/java/xml/logger.xml";


	  FileInputStream fis = new FileInputStream(uri);
	  DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
	  DocumentBuilder db = fact.newDocumentBuilder();
	  Document doc = db.parse(new InputSource(fis));
		Element root = doc.getDocumentElement();
		/*
		System.out.println("TagName = " + root.getTagName());
		Node node =  root.getFirstChild();
		dumpTree(node);

		handleLogger(root);
		*/
		System.out.println("logfile-name = " + getElementValue(root,"logfile-name"));
		System.out.println("Property name = " + getElementAttribute(root, "property","name"));
		System.out.println("type = " + getElementValue(root,"type"));
		System.out.println("type = " + getSubElementValue(root,"rotation","type"));
	} catch (Exception err) {
		err.printStackTrace();
	}
	System.exit (0);
	}



	static void handleLogger(Element root)
	{
	NodeList nl = root.getElementsByTagName("logfile-name");
	Node n = nl.item(0);
	System.out.println("toString = " + n.toString());
	System.out.println(n.getNodeName());
	System.out.println(n.getFirstChild().getNodeValue());
	nl = root.getElementsByTagName("log-level");
	n = nl.item(0);
	System.out.println(n.getNodeName());
	System.out.println(n.getFirstChild().getNodeValue());
	nl = root.getElementsByTagName("rotation");
	n = nl.item(0);
	System.out.println(n.getNodeName());
	NodeList rnl = n.getChildNodes();
	for(int i=0; i < rnl.getLength(); ++i) {
		Node cn = rnl.item(i);
		String s = cn.getNodeName();
		if(!s.equals("#text")) {
			System.out.println(cn.getNodeName());
			System.out.println(cn.getFirstChild().getNodeValue());
		}
	}
	}

	static String getElementValue(Element root, String elemName)
	{
		NodeList nl = root.getElementsByTagName(elemName);
		if(null == nl ) {
			return(null);
		}
		Node n = nl.item(0);
		return(n.getFirstChild().getNodeValue().trim());
	}

	static String getSubElementValue(Element root, String parent,String elemName)
	{
		NodeList nl = root.getElementsByTagName(parent);
		String value = null;
		if(null == nl ) {
			return(null);
		}
		Node node = nl.item(0);
		nl = node.getChildNodes();
		if(null == nl) {
			return(null);
		}

		boolean found = false;
		for(int i=0;  !found && i < nl.getLength(); ++i) {
			Node n = nl.item(i);
			if(elemName.equals(n.getNodeName())) {
				value = n.getFirstChild().getNodeValue().trim();
				found = true;
				break;
			} // if
		} // for
		return(value);
	}

	static String getElementAttribute(Element root, String elemName, String att)
	{
		NodeList nl = root.getElementsByTagName(elemName);
		if(null == nl ) {
			return(null);
		}
		Node n = nl.item(0);
		if(null == n) {
			return(null);
		}
		NamedNodeMap attributes = n.getAttributes();
		if(null == attributes) {
			return(null);
		}
		n = attributes.getNamedItem(att);
		if(null == n) {
			return(null);
		}
		return(n.getNodeValue().trim());

	}


	private static void dumpTree(Node node)
	{
		String name = node.getNodeName();
		if(name.equals("#text")) {
		  System.out.println("NodeValue = " + node.getNodeValue());
		} else {
		  System.out.println("NodeName = " + name);
		}


		NamedNodeMap attributes = node.getAttributes();
		if(null != attributes) {
		int len = attributes.getLength();
		for(int i=0; i<len; ++i) {
		  Node att = attributes.item(i);
		  System.out.println("AttributeName = " + att.getNodeName());
		  System.out.println("AttributeValue = " + att.getNodeValue());
		}
		}
		Node n =  node.getFirstChild();
		if(null != n) {
		  dumpTree(n);
		}
		n = node.getNextSibling();
		if(n != null) {
		 dumpTree(n);
		}
	}

}

