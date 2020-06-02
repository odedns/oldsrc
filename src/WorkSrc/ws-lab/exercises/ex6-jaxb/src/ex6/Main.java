package ex6;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javax.xml.bind.Validator;

import ex6.message.Message;



public class Main {

	public static void main(String[] args) {
		try {
			
			// Unmarshall and print the content of a message
			JAXBContext jc = JAXBContext.newInstance("ex6.message");
			Unmarshaller u = jc.createUnmarshaller();
			Message msg = (Message) u.unmarshal(new FileInputStream("message.xml"));
			System.out.println("Message contents:");
			System.out.println("Destination=" + msg.getDestination());
			System.out.println("Subject=" + msg.getSubject());
			System.out.println("Content=" + msg.getContent());
			//msg.setSubject(null);
			Validator valid = jc.createValidator();
			
			MyValidationHandler myHandler = new MyValidationHandler();
			valid.setEventHandler(myHandler);
			valid.validate(msg);
			
			Marshaller m = jc.createMarshaller();
			m.marshal(msg, new FileOutputStream("newmsg.xml"));
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}