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
		String httpProxyHost = "genproxy";
		String httpProxyPort = "8080";
		String regUrli = "http://test.uddi.microsoft.com/inquire";

		Properties props = new Properties();
		props.setProperty("javax.xml.registry.queryManagerURL", regUrli);
		//props.setProperty("com.sun.xml.registry.http.proxyHost", httpProxyHost);
		//props.setProperty("com.sun.xml.registry.http.proxyPort", httpProxyPort);
		
		// Search the registry for all organizations the have 'ISR' as part of their name

	}

}
