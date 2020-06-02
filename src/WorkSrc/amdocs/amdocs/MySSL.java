/*
 * Copyright (c) 1997.  Netscape Communications Corporation.  All
 * rights reserved.
 * 
 * Search the directory for all people whose surname (last name) is
 * "Jensen".  Since the "sn" attribute is a caseignorestring (cis), case
 * is not significant when searching.
 *
 * You can control whether to block on receiving all results or on one
 * at a time by setting the batchSize.
 *
 */

import netscape.ldap.*;
import java.util.*;
import java.io.*;

public class MySSL {
    public static void main( String[] args )
	{
		LDAPConnection ld = null;
		int status = -1;
		int MY_PORT = 14201;
		String MY_HOST = "sne451";
		try {
			LDAPSSLSocketWrapFactory ssl = 
			new LDAPSSLSocketWrapFactory("netscape.ldap.LDAPSSLSocket");
//			ssl.makeSocket(MY_HOST,MY_PORT);
			ld = new LDAPConnection(ssl);
			System.out.println("connecting ....");
			System.out.println("ssl on : " + MY_PORT);
			/*
			System.out.println("ssl socket = " +
					ssl.getSSLSocketImpl());
					*/
			System.out.println("isClientAuthEnabled = " +
					ssl.isClientAuth());
			System.out.println("ssl = " + ssl.toString());

			
			/* Connect to server */
			ld.connect( MY_HOST, MY_PORT ,
				"uid=amdocs,ou=People,o=DT.com","amdocs");
			/* Done, so disconnect */
			if ( (ld != null) && ld.isConnected() ) {
			    ld.disconnect();
    		}
    		System.in.read();

		} catch( LDAPException e ) {
			System.out.println( "Error: " + e );
			e.printStackTrace();
		}
		catch(IOException ie) {
		    ie.printStackTrace();
		}



		System.exit(status);
	}
}



