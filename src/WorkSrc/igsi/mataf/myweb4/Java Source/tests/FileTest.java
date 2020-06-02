package tests;

import java.io.*;
import java.net.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class FileTest {

	public static void main(String[] args) {
		
		String path = "http://localhost/myweb4/dse/server/dse.ini";
		try {
							
			String s = null;
			URL url = new URL(path);
            URLConnection urlconnection = url.openConnection();
	    
            System.out.println("contentype = " + urlconnection.getContentType());
            InputStream is = urlconnection.getInputStream();
           	BufferedReader br = new BufferedReader(new InputStreamReader(is));		
					
			while(null !=(s = br.readLine())) {
				System.out.println(s);	
			}
			br.close();
					
		}catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
