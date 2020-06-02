package mataf.logger;
import java.net.URL;
import org.apache.log4j.*;

import com.ibm.dse.base.*;
import mataf.services.reftables.*;

/**
 * @author Oded Nissan 03/07/2003.
 *
 * Utility class that wraps Log4j functionality.
 * Use this class to log debug, error, warning and info messages.
 */
public class GLogger {

	public static final String SERVER_LOG = "MatafServer";
	public static final String CLIENT_LOG = "MatafClient";
	private static final String MQ_LOG_SERVICE= "MQ_LOG";
	static Logger m_logger = null;	
	static String m_name = null;
	static String m_trxId = null;
	static String m_msTachana = null;
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
		m_name = name;
		m_logger = Logger.getLogger(name);
		
	}


	/**
	 * initialize the logger.
	 */	
	private static void initLogger()
	{
		if(null == m_logger) {
			m_logger = Logger.getRootLogger();		
			BasicConfigurator.configure();
			m_logger.setLevel(Level.ALL);
			m_logger.warn("Using BasicConfiguration");
		}
		
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
	 * @deprecated
	 */
	public static void fatal(Object msg)
	{
		initLogger();
		m_logger.fatal(msg);
		
	}
	
	/**
	 * log a warning log message.
	 * @param Object msg the message to log.
	 */
	public static void warn(Object msg)
	{
		initLogger();
		m_logger.warn(msg);
		
	}
	
	/**
	 * log an error log message.
	 * @param Object msg the message to log.
	 * @deprecated
	 */
	public static void error(Object msg)
	{
		initLogger();
		m_logger.error(msg);
		
	}
	
	/**
	 * log an info log message.
	 * @param Object msg the message to log.
	 */
	public static void info(Object msg)
	{
		initLogger();
		m_logger.info(msg);
		
	}
	
	/**
	 * log a debug log message.
	 * @param Object msg the message to log.
	 */
	public static void debug(Object msg)
	{
		initLogger();
		m_logger.debug(msg);
		
	}
	
	/**
	 * set the transaction id to be used for
	 * subsequent logging.
	 * @param trxId the transaction id.
	 */
	public static void setTrxId(String trxId)
	{
		m_trxId = trxId;	
	}
	
	/**
	 * set the workstation num  to be used for
	 * subsequent logging.
	 * @param trxId the transaction id.
	 */
	public static void setMsTachana(String msTachana)
	{
		m_msTachana = msTachana;	
	}
	
	/**
	 * create a message from paramters.
	 */
	private static String buildMsg(String msTachana, String trxId,Class c, String msgId, String userMsg)
	{
		StringBuffer sb = new StringBuffer();
		String msg = null;
		
		if(msTachana != null) {
			sb.append(msTachana);
			sb.append(' ');
		}

		if(trxId != null) {
			sb.append(trxId);
			sb.append(' ');
		}
	
		if(c != null) {
			sb.append(c.getName());
			sb.append(' ');
		}
		if(msgId != null ) {
			sb.append(msgId);
			sb.append(':');
			msg = getMessage(msgId);
		}
		if(msg != null) {
			sb.append(msg);
			sb.append(' ');	
		}
		if(userMsg != null) {
			sb.append(userMsg);	
			sb.append(' ');
		}
		return(sb.toString());	
	}
	

	private static String getMessage(String  msgId)
	{
		String msg = null;
		try {
			RefTablesService srv = (RefTablesService) Service.readObject("refTables");
			msg  = (String) srv.getByKey("GLST_SGIA","GL_ZIHUY_HODAA",msgId,"GL_HODAA");	
		} catch(Exception e) {
			e.printStackTrace();	
			msg = null;
		}
		return(msg);
	}

	/**
	 * log an info message.
	 * For use on the client where we can set the tranaction and 
	 * workstation number in advance.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */
	public static void info(Class c, String msgId, String msg, boolean send)
	{
		initLogger();
		String s = buildMsg(m_msTachana, m_trxId,c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.info(s);
		
	}

	/**
	 * log a warning message.
 	 * For use on the client where we can set the tranaction and 
	 * workstation number in advance.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */
	public static void warn(Class c, String msgId, String msg, boolean send)
	{
		initLogger();
		String s = buildMsg(m_msTachana, m_trxId,c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.warn(s);
		
	}

	/**
	 * log a debug message.
 	 * For use on the client where we can set the tranaction and 
	 * workstation number in advance.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */

	public static void debug(Class c, String msgId, String msg)
	{
		initLogger();
		String s = buildMsg(m_msTachana, m_trxId, c,msgId,msg);
		m_logger.debug(s);
		
	}

	/**
	 * log an error message.
 	 * For use on the client where we can set the tranaction and 
	 * workstation number in advance.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 * @param e the Exception to log.
	 */

	public static void error(Class c, String msgId, String msg, Exception e, boolean send)
	{
		initLogger();
		String s = buildMsg(m_msTachana, m_trxId,c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.error(s,e);		
	}

	/**
	 * log a fatal message.
	 * log an error message.
 	 * For use on the client where we can set the tranaction and 
	 * workstation number in advance.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
 	 * @param e the Exception to log.
	 */
	public static void fatal(Class c, String msgId, String msg, Exception e, boolean send)
	{
		initLogger();
		String s = buildMsg(m_msTachana, m_trxId, c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}

		m_logger.fatal(s,e);		
	}


	/**
	 * log an info message.
 	 * For use on the server where we can need to pass the tranaction and 
	 * workstation number.
	 * @param msTachana  the workstation number from GL_MISPAR_TACHANA
	 * @param trxId the tranaction id.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */
	public static void info(String msTachana, String trxId,Class c, String msgId, String msg, boolean send)
	{
		initLogger();
		String s = buildMsg(msTachana, trxId,c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.info(s);		
	}

	/**
	 * log a warning message.
	 * For use on the server where we can need to pass the tranaction and 
	 * workstation number.
	 * @param msTachana  the workstation number from GL_MISPAR_TACHANA
	 * @param trxId the tranaction id.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */
	public static void warn(String msTachana, String trxId, Class c, String msgId, String msg, boolean send)
	{
		initLogger();
		String s = buildMsg(msTachana, trxId,c,msgId,msg);
		m_logger.warn(s);		
	}

	/**
	 * log a debug message.
	 * For use on the server where we can need to pass the tranaction and 
	 * workstation number.
	 * @param msTachana  the workstation number from GL_MISPAR_TACHANA
	 * @param trxId the tranaction id.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 */
	public static void debug(String msTachana, String trxId, Class c, String msgId, String msg)
	{
		initLogger();
		String s = buildMsg(msTachana, trxId, c,msgId,msg);
		m_logger.debug(s);		
	}

	/**
	 * log an error message.
	 * For use on the server where we can need to pass the tranaction and 
	 * workstation number.
	 * @param msTachana  the workstation number from GL_MISPAR_TACHANA
	 * @param trxId the tranaction id.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 * @param e the Exception to log.
	 */
	public static void error(String msTachana, String trxId, Class c, String msgId, String msg, Exception e, boolean send)
	{
		initLogger();
		String s = buildMsg(msTachana, trxId,c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.error(s,e);		
	}

	/**
	 * log a fatal message.
	 * For use on the server where we can need to pass the tranaction and 
	 * workstation number.
	 * @param msTachana  the workstation number from GL_MISPAR_TACHANA
	 * @param trxId the tranaction id.
	 * @param c  the current Class.
	 * @param msgNum the message number from the APP_MSG table.
	 * @param msg a user specific message.
	 * @param e the Exception to log.
	 */
	public static void fatal(String msTachana, String trxId, Class c, String msgId, String msg, Exception e, boolean send)
	{
		initLogger();
		String s = buildMsg(msTachana, trxId, c,msgId,msg);
		if(send) {
			sendMqMsg(s);	
		}
		m_logger.fatal(s,e);		
	}


	/**
	 * send the log message to an MQ queue.
	 */
	private static void sendMqMsg(String msg)
	{	
		try {
			mataf.utils.MQUtils.sendMsg(MQ_LOG_SERVICE,msg);		
			debug("message sent to mq");
		} catch(DSEException e) {
			error("Error sending message to MQ : " + e.getMessage());	
		}
	}	
	
	
}



