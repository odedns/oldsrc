/*
 * HelloClient.java
 * Created on 02/08/2004
 *
 */
package ex1;

import ex1.stub.*;

/**
 * HelloClient2
 * 
 * @author rank 
 */
public class HelloClient {

	public static void main(String[] args) {
		try {
			ex1.stub.HelloWorld service = (new HelloWorldServiceLocator()).getHelloWorld();
			System.out.println("Got result: "+service.sayHello("Ran"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
