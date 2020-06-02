package tests;

import com.ibm.dse.base.*;
import org.apache.log4j.*;
import org.apache.log4j.helpers.*;
import java.net.*;

import mataf.logger.*;
import mataf.utils.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Log4jTest {

	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}

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
		try {
			init();
		} catch(Exception e) {
			e.printStackTrace();
		}
		GLogger.configure("http://localhost/MatafServer/log4j.properties", GLogger.SERVER_LOG);			 
		GLogger.warn("warning msg");
		GLogger.debug("debug msg");
		GLogger.error("error msg");
		GLogger.setTrxId("T410");
		GLogger.setMsTachana("WKS100");
		GLogger.info(Log4jTest.class,"GL068","info msg",false);
		GLogger.warn(Log4jTest.class,"GL068","warning msg",false);
		try {
			throw new Exception("some exception");	
			
		} catch(Exception e) {
			GLogger.error(Log4jTest.class,"GL085","error message", e,false);
			GLogger.fatal(Log4jTest.class,"GL085","fatal message", e,false);
			GLogger.error(Log4jTest.class,null,"error message", e,true);
		}
	}
}



class Foo {
	static Logger logger = Logger.getLogger(Foo.class);
	
	void foo()
	{
		logger.debug("foo debug");
	}
}
	