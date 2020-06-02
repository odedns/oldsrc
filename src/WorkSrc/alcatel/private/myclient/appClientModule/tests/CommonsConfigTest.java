/*
 * Created on 13/12/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tests;

import java.util.Iterator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;


/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CommonsConfigTest {

	/**
	 * 
	 */
	public CommonsConfigTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration("config.properties");			
			
			Iterator iter = config.getKeys();
			while(iter.hasNext()) {
				System.out.println("key = " + iter.next());
			}
			System.out.println("jdbc.driver= " + config.getString("jdbc.driver"));
			System.out.println("current.version= " + config.getString("current.version"));
		} catch(Exception ce) {
			ce.printStackTrace();
		}
	}
}
