package ex3.clients;

import java.util.*;

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
		String endpoint = "http://localhost:8081/axis/services/MessageService";
		String serviceName = "MessageServiceService";


		Service service = ServiceFactory.newInstance().createService(new QName(serviceName));
		Call call = (Call) service.createCall();
		call.setTargetEndpointAddress(endpoint);
		call.setOperationName(new QName("echoElements"));


		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(true);
		DocumentBuilder builder = factory.newDocumentBuilder();

		Document doc = builder.parse("message.xml");
		Element docRoot = doc.getDocumentElement();

		SOAPBodyElement[] input = new SOAPBodyElement[1];
		input[0] = new SOAPBodyElement(docRoot);

		Vector elems = (Vector) call.invoke(input);

		SOAPBodyElement elem = (SOAPBodyElement) elems.get(0);

		return (elem.toString());
	}

	public static void main(String[] args) throws Exception {
		String res = (new MsgClient()).doit(args);
		System.out.println(res);
	}
}
