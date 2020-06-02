package work;

import org.apache.log4j.*;
import org.apache.log4j.helpers.*;

import com.ibm.jvm.io.FileInputStream;

import java.io.InputStream;
import java.net.*;
import java.util.Properties;

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
		
		Logger logger = Logger.getRootLogger();
		// BasicConfigurator.configure();
		Properties p = null;		
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("log4j.properties");
			p = new Properties();
			p.load(is);
		} catch(Exception e) {
			e.printStackTrace();	
		}
		PropertyConfigurator.configure(p);
		// logger.setLevel(Level.ALL);
		NDC.push("fooClient");
		logger.warn("this is a warning");
		logger.error("This is an error");
		logger.info("This is info");
		logger.debug("This is debug");
		Foo f = new Foo();
		f.foo();
		Logger logger2 = Logger.getLogger(Log4jTest.class);
		logger2.info("This is info2");
		
		logger2.info("now testing Log class \n\n");
		
	}
}



class Foo2 {
	static Logger logger = Logger.getLogger(Foo2.class);
	
	void foo()
	{
		logger.debug("foo debug");
	}
}
	