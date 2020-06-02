/*
 * DynamicInvocationClient.java
 * Created on 02/08/2004
 *
 */
package ex2.clients;

import javax.xml.namespace.QName;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.XMLType;



/**
 * DynamicStubClient
 * 
 * @author rank
 */
public class DynamicInvocationClient {

	public static void main(String[] args) {
		
		String qnameService = "WeatherServiceService";
		String endpoint = "http://localhost:8080/axis/services/WeatherService";
		
		try {
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(new QName(qnameService));
			
			Call call = service.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName(new QName("getForecast"));
			
			call.setReturnType(XMLType.XSD_STRING);

			call.addParameter("dayInWeek",XMLType.XSD_INT, ParameterMode.IN);
			Object[] params = { new Integer(1) };

			String result = (String) call.invoke(params);
			System.out.println(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
