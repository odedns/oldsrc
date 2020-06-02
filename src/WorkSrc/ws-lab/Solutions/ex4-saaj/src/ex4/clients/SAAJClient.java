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
			SOAPConnectionFactory soapConnFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection connection = soapConnFactory.createConnection();

			MessageFactory messageFactory = MessageFactory.newInstance();
			SOAPMessage message = messageFactory.createMessage();

			//Create objects for the message parts            
			SOAPPart soapPart = message.getSOAPPart();
			SOAPEnvelope envelope = soapPart.getEnvelope();
			SOAPBody body = envelope.getBody();

			Name bodyName = envelope.createName("getForecast", "ns1", "http://courses.ws");

			SOAPElement bodyElement = body.addBodyElement(bodyName);

			//Add content
			bodyElement.addChildElement("dayInWeek").addTextNode("1");
			//Save the message
			message.saveChanges();

			String destination = "http://localhost:8080/axis/services/WeatherService";
			SOAPMessage reply = connection.call(message, destination);
			
			// Print the response
			try {
				reply.writeTo(System.out);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SOAPException e) {
			e.printStackTrace();
		}

	}
}
