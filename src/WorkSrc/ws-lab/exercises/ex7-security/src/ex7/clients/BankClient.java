/*
 * BankClient.java
 * Created on 02/08/2004
 *
 */
package ex7.clients;

import javax.xml.namespace.QName;
import javax.xml.rpc.*;
import javax.xml.rpc.encoding.XMLType;



/**
 * BankClient
 * 
 * @author rank
 */
public class BankClient {

    public static final String USERNAME = "oded";
    public static final String PASSWORD = "foo";
    
	public static void main(String[] args) {
		
		String qnameService = "BankServiceService";
		String endpoint = "http://localhost:8080/axis/services/BankService";
		
		try {
			ServiceFactory factory = ServiceFactory.newInstance();
			Service service = factory.createService(new QName(qnameService));
	
			
			Call call = service.createCall();
			call.setTargetEndpointAddress(endpoint);
			call.setOperationName(new QName("getAccountBalance"));
			
			call.setReturnType(XMLType.XSD_STRING);
			
			call.setProperty(Call.USERNAME_PROPERTY,USERNAME);
			call.setProperty(Call.PASSWORD_PROPERTY,PASSWORD);
			
			
			Object[] params = {};

			String result = (String) call.invoke(params);
			System.out.println(result);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
}
