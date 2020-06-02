/*
 * MessageService.java
 * Created on 02/08/2004
 *
 */
package ex3.services.msg;

import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * MessageService
 * 
 * @author rank
 */
public class MessageService {

	private static final String NAMESPACE = "http://courses.ws";

	public Element[] echoElements(Element[] elems) {
		Element e = elems[0];

		Element result = null;
		try {
			System.out.println("Subject: " + getTextNode(e.getElementsByTagNameNS(NAMESPACE, "subject").item(0)));
			System.out.println("Content: " + getTextNode(e.getElementsByTagNameNS(NAMESPACE, "content").item(0)));

			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.newDocument();
			result = doc.createElementNS(NAMESPACE, "result");
			Text te =
				doc.createTextNode(
					"Message sent successfully to "
						+ getTextNode(e.getElementsByTagNameNS(NAMESPACE, "destination").item(0)));
			result.appendChild(te);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return new Element[] { result };
	}

	private String getTextNode(Node e) {
		return ((Text) ((Element) e).getChildNodes().item(0)).getData();
	}
}
