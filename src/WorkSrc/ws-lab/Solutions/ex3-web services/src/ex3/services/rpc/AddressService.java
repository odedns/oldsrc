/*
 * AddressService.java
 * Created on 02/08/2004
 *
 */
package ex3.services.rpc;

import java.util.*;
import javax.xml.rpc.*;

/**
 * AddressService
 * 
 * @author rank
 */
public class AddressService {
	private Map addresses = new HashMap();
	
	public void addAddress(String userName, AddressBean address) {
		addresses.put(userName,address);
		
	}
	
	public AddressBean getAddress(String userName) {
		AddressBean addr = (AddressBean)addresses.get(userName);
		if (addr==null) {
			throw new JAXRPCException("User name doesn't exist.");
		}
		return addr;
	}
	
		
	
}
