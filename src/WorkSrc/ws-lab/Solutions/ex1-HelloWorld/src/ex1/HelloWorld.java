/**
 * HelloWorld.java
 */

package ex1;

public class HelloWorld {
	
    public String sayHello(String name) throws java.rmi.RemoteException {
        return "Hello to "+name+" from a Web Service!";
    }

}
