package tests;
import org.apache.log4j.*;
import org.apache.log4j.helpers.*;
import java.net.*;

import composer.utils.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Log4jTest {



	public static void main(String[] args) {
/*		
		Logger logger = Logger.getRootLogger();
		// BasicConfigurator.configure();
		
		URL url = null;
		try {
			url = new URL("http://localhost/myweb4/log4j.properties");	
		} catch(Exception e) {
			e.printStackTrace();	
		}
		PropertyConfigurator.configure(url);
		// logger.setLevel(Level.ALL);
		logger.warn("this is a warning");
		logger.error("This is an error");
		logger.info("This is info");
		logger.debug("This is debug");
		Foo f = new Foo();
		f.foo();
		Logger logger2 = Logger.getLogger(Log4jTest.class);
		logger2.info("This is info2");
		
		logger2.info("now testing Log class \n\n");
*/		
		GLogger.configure("http://localhost/myweb4/log4j.properties", GLogger.SERVER_LOG);			 
		GLogger.warn("warning msg");
		GLogger.debug("debug msg");
		GLogger.error("error msg");
		GLogger.info("T410",Log4jTest.class,400,"some info");
	}
}



class Foo {
	static Logger logger = Logger.getLogger(Foo.class);
	
	void foo()
	{
		logger.debug("foo debug");
	}
}
	