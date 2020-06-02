/*
 * AddressClient.java
 * Created on 02/08/2004
 *
 */
package ex3.clients;



import ex3.stub.*;



/**
 * AddressClient
 * 
 * @author rank
 */
public class AddressClient {

	public static void main(String[] args) {
		try {
			// Add a new user and address
			AddressService srv = new AddressServiceServiceLocator().getAddressService();
			Address  address = new Address();
			address.setCity("Tel-Aviv");
			address.setStreet("Raul Valenberg");
			address.setNum(30);			
			srv.addAddress("oded", address);
			
			address.setCity("Or-Yehuda");
			address.setStreet("Bar-Lev");
			address.setNum(4);			
			srv.addAddress("mitzy", address);
			
			Address address2 = srv.getAddress("mitzy");
			
			System.out.println("address city = " + address2.getCity());
			System.out.println("address street = " + address2.getStreet());
			System.out.println("address num = " + address2.getNum());
			
			address2 = srv.getAddress("oded");
			
			System.out.println("address city = " + address2.getCity());
			System.out.println("address street = " + address2.getStreet());
			System.out.println("address num = " + address2.getNum());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
