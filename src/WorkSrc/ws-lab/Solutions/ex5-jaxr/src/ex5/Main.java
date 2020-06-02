/*
 * Main.java
 * Created on 04/08/2004
 *
 */
package ex5;

import java.util.*;

import javax.xml.registry.*;
import javax.xml.registry.infomodel.*;

/**
 * Main
 * 
 * @author rank
 */
public class Main {

	public static void main(String[] args) {
		String httpProxyHost = "localhost";
		String httpProxyPort = "8888";
		//String regUrli = "http://uddi.microsoft.com/inquire";
		String regUrli = "http://uddi.ibm.com/ubr/inquiryapi";
		//String regUrli = "http://uddi.sap.com/UDDI/api/inquiry/";
		
		try {
			ConnectionFactory connFactory = ConnectionFactory.newInstance();
 
			Properties props = new Properties();
			props.setProperty("javax.xml.registry.queryManagerURL", regUrli);
			props.setProperty("com.sun.xml.registry.http.proxyHost", httpProxyHost);
			props.setProperty("com.sun.xml.registry.http.proxyPort", httpProxyPort);

			connFactory.setProperties(props);
			Connection con = connFactory.createConnection();

			RegistryService rs = con.getRegistryService();
			BusinessQueryManager bqm = rs.getBusinessQueryManager();

			// Define find qualifiers and name patterns
			Collection findQualifiers = new ArrayList();
			findQualifiers.add(FindQualifier.SORT_BY_NAME_DESC);
			Collection namePatterns = new ArrayList();
			namePatterns.add("%ibm%");
				
			System.out.println("Looking for services...");
			BulkResponse response = bqm.findOrganizations(findQualifiers, namePatterns, null, null, null, null);
			
				
			Collection orgs = response.getCollection();
			
			Iterator iter = orgs.iterator();
			while (iter.hasNext()) {
				Organization org = (Organization) iter.next();
				System.out.println("Organization Name: " + org.getName().getValue());
				System.out.println("Organization Description: " + org.getDescription().getValue());
				
				System.out.println("Services:");
				Collection services = org.getServices();
				Iterator siter = services.iterator();
				while (siter.hasNext()) {
					Service service = (Service) siter.next();
					System.out.println("\t"+service.getName().getValue());
					
					Collection sb = service.getServiceBindings();
					Iterator sbiter = sb.iterator();
					while (sbiter.hasNext()) {
						ServiceBinding binding = (ServiceBinding) sbiter.next();
						System.out.println("\t"+binding.getAccessURI());
					}
					
					System.out.println("\t");
				}
				System.out.println("");
			}
		} catch (JAXRException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
