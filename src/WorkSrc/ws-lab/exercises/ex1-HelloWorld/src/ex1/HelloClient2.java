/*
 * HelloClient2.java
 * Created on 02/08/2004
 *
 */
package ex1;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.*;
import org.apache.axis.encoding.XMLType;

/**
 * HelloClient
 * 
 * @author rank
 */
public class HelloClient2 {

	public static void main(String[] args) {

		String endpoint = "http://localhost:8080/axis/services/HelloWorld";
		String method = "sayHello";

		try {
			Service service = new Service();
			Call call = (Call) service.createCall();

			call.setTargetEndpointAddress(new java.net.URL(endpoint));
			call.setOperationName(method);
			call.addParameter("name", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);

			String ret = (String) call.invoke(new Object[] { "Ran" });

			System.out.println("Got result : " + ret);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
