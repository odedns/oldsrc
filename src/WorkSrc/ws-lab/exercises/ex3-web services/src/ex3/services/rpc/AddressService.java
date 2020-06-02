/*
 * AddressService.java
 * Created on 02/08/2004
 *
 */
package ex3.services.rpc;

import java.util.HashMap;

/**
 * AddressService
 * 
 * @author rank
 */
public class AddressService {
	
	static HashMap map = new HashMap(); 
	public void addAddress(String userName, AddressBean address)
	{
		map.put(userName, address);
	}
	
	public AddressBean getAddress(String userName)
	{
		return((AddressBean) map.get(userName));
	}
	
}
