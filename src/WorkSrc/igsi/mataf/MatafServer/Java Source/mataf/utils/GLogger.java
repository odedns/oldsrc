package mataf.utils;
import org.apache.log4j.*;
import java.net.*;


/**
 * @author Oded Nissan 03/07/2003.
 *
 * Utility class that wraps Log4j functionality.
 * Use this class to log debug, error, warning and info messages.
 */
public class GLogger {

	public static final String SERVER_LOG = "MatafServer";
	public static final String CLIENT_LOG = "MatafClient";
	static Logger m_logger = null;	
	static String m_name = null;
	/**
	 * object cannot be instantiated.
	 */
	private GLogger()
	{
	}
	
	
	/**
	 * initialize log4j coniguration.
	 * If the configuration file cannot be found
	 * use the BasicConfigurator.	 * 
	 */
	public static void configure(String urlString,String name)
	{
		try {
			URL url = new URL(urlString);	
			PropertyConfigurator.configure(url);
		} catch(Exception e) {
			e.printStackTrace();	
			BasicConfigurator.configure();
			Logger.getRootLogger().warn("cannot find configuration file using basic log4j configuration");
		}
		if(null == name) {
			name = SERVER_LOG;	
		}
		m_logger = Logger.getLogger(name);
		
	}
	
	
	/**
	 * get a log4j logger.
	 * @return Logger a log4j logger object.
	 */
	public static Logger getLogger()
	{
		return(m_logger);
	}
	
	
	/**
	 * log a fatal log message.
	 * @param Object msg the message to log.
	 */
	public static void fatal(Object msg)
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.warn("Using BasicConfiguration");
		}
		m_logger.fatal(msg);
		
	}
	
	/**
	 * log a warning log message.
	 * @param Object msg the message to log.
	 */
	public static void warn(Object msg)
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.setLevel(Level.ALL);
			m_logger.warn("Using BasicConfiguration");
		}
		m_logger.warn(msg);
		
	}
	
	/**
	 * log an error log message.
	 * @param Object msg the message to log.
	 */
	public static void error(Object msg)
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.setLevel(Level.ALL);
			m_logger.warn("Using BasicConfiguration");
		}
		m_logger.error(msg);
		
	}
	
	/**
	 * log an info log message.
	 * @param Object msg the message to log.
	 */
	public static void info(Object msg)
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.setLevel(Level.ALL);
			m_logger.warn("Using BasicConfiguration");
		}
		m_logger.info(msg);
		
	}
	
	/**
	 * log a debug log message.
	 * @param Object msg the message to log.
	 */
	public static void debug(Object msg)
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.setLevel(Level.ALL);
			m_logger.warn("Using BasicConfiguration");	
		}		
		m_logger.debug(msg);
		
	}
	
	
	
	public static void main(String args[])
	{
		GLogger.debug("this is debug");	
	}

	
}



