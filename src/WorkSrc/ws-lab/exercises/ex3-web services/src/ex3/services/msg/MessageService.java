/*
 * MessageService.java
 * Created on 02/08/2004
 *
 */
package ex3.services.msg;

import org.w3c.dom.*;

/**
 * MessageService
 * 
 * @author rank
 */
public class MessageService {

	private static final String NAMESPACE = "http://courses.ws";

	public Element[] echoElements(Element[] elems) {
		// Get the message SOAP elements and return a response
		Element e = elems[0];
		
		NodeList nl = e.getElementsByTagNameNS("http://courses.ws","subject");
		System.out.println(getTextNode(nl.item(0))); 
		
		return null;
		
	}

	private String getTextNode(Node e) {
		return ((Text) ((Element) e).getChildNodes().item(0)).getData();
	}
}
