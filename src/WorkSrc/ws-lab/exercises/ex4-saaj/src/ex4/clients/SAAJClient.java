/*
 * SAAJClient.java
 * Created on 03/08/2004
 *
 */
package ex4.clients;

import java.io.IOException;

import javax.xml.soap.*;

/**
 * SAAJClient
 * 
 * @author rank
 */
public class SAAJClient {

	public static void main(String[] args) {
		try {
			SOAPMessage reply=null;

			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();
			MessageFactory msgFactory = MessageFactory.newInstance();
			// Call the WeatherService
			SOAPMessage message = msgFactory.createMessage();
			//Create objects for the message parts            
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			SOAPBody body = envelope.getBody();
			Name bodyNane = envelope.createName("getForecast", "ns1","http://localhost");
			SOAPElement bodyElement = body.addBodyElement(bodyNane);
			bodyElement.addChildElement("dayInWeek").addTextNode("1");
			String desctination = "http://localhost:8080/axis/services/WeatherService";
			reply = connection.call(message,desctination);											
			
			// Print the response
			reply.writeTo(System.out);
			
			try {
				reply.writeTo(System.out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
