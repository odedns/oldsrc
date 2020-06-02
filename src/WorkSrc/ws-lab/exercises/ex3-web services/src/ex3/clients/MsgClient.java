package ex3.clients;

import javax.xml.namespace.QName;
import javax.xml.parsers.*;
import javax.xml.rpc.*;

import org.apache.axis.message.SOAPBodyElement;
import org.w3c.dom.*;

/**
 * Simple test driver for our message service.
 */
public class MsgClient {

	public String doit(String[] args) throws Exception {
		String endpoint;
		String serviceName;


		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse("message.xml");
		Element docRoot = doc.getDocumentElement();
		
		// Call the service and return the response
		
	}

	public static void main(String[] args) throws Exception {
		String res = (new MsgClient()).doit(args);
		System.out.println(res);
	}
}
