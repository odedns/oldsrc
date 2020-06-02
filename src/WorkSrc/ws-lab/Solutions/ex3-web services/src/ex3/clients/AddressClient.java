/*
 * AddressClient.java
 * Created on 02/08/2004
 *
 */
package ex3.clients;


import ex3.stub.*;
import ex3.stub.Address;

/**
 * AddressClient
 * 
 * @author rank
 */
public class AddressClient {

	public static void main(String[] args) {
		try {
			AddressService services = (new AddressServiceServiceLocator()).getAddressService();

			// Add a new user and address
			Address addr = new Address();
			addr.setStreet("Aviv");
			addr.setPostcode(4455);
			
			services.addAddress("Ran",addr);
			
			addr = services.getAddress("Ran");
			
			System.out.println("Street: "+addr.getStreet());
			System.out.println("Postcode: "+addr.getPostcode());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
