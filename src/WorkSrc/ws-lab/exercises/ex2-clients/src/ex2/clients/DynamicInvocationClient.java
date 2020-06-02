/*
 * DynamicInvocationClient.java
 * Created on 02/08/2004
 *
 */
package ex2.clients;

import javax.xml.namespace.QName;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.XMLType;

import sun.print.resources.serviceui;



/**
 * DynamicStubClient
 * 
 * @author rank
 */
public class DynamicInvocationClient {

	public static void main(String[] args) {
		
		String qnameService="WeatherService";
		String endpoint = "http://localhost:8080/axis/services/WeatherService";
		String method = "getForecast";
		try {
			ServiceFactory factory =  ServiceFactory.newInstance();
			Service srv = factory.createService(new QName(qnameService));
			Call call = srv.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName(new QName(method));
			call.setReturnType(XMLType.XSD_STRING);
			call.addParameter("dayInWeek", XMLType.XSD_INT,ParameterMode.IN);
			Object params[] = { new Integer(1) };			
		// Call the weather service and print the result
			String result = (String) call.invoke(params);
			System.out.println("result = " + result);
			
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
