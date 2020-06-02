package ex6;

import java.io.*;
import java.io.FileInputStream;

import javax.xml.bind.*;

import ex6.message.*;


public class Main {

	public static void main(String[] args) {
		try {
			JAXBContext jc = JAXBContext.newInstance("ex6.message");

			Unmarshaller u = jc.createUnmarshaller();

			

			Message msg = (Message) u.unmarshal(new FileInputStream("message.xml"));

			System.out.println("Message contents: ");

			System.out.println("Destination: " + msg.getDestination());
			System.out.println("Subject: " + msg.getSubject());
			System.out.println("Content: " + msg.getContent());
			
			
			Validator valid = jc.createValidator();
			
			try{
				msg.setSubject(null);
				valid.validate(msg);
			} catch (ValidationException e) {
				msg.setSubject("New Subject");
			}
			
			Marshaller m = jc.createMarshaller();
			
			m.marshal(msg,new FileOutputStream("newMessage.xml"));
			
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}