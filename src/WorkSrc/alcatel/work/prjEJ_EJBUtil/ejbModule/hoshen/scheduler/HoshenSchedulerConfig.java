/*
 * Created on 13/09/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoshen.scheduler;

import hoshen.common.utils.exception.HoshenException;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.*;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author odedn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HoshenSchedulerConfig  extends DefaultHandler {

	private List m_taskInfoList;
	private boolean m_disabled;
	private boolean m_cleanupTasks;
	private String m_jndiName;
	private String m_notificationSinkJndi;
	private String m_taskHandlerJndi;
	private String m_dateFormat;
	private static HoshenSchedulerConfig m_instance = null;
	
	private HoshenTaskInfo m_currentTaskInfo;
	private SimpleDateFormat m_sdf;
	private String m_tblPrefix;
	private static final Logger log = Logger.getLogger(HoshenSchedulerConfig.class);
	/**
	 * obtain the HoshenSchedulerConfig singleton instance.
	 * @return HoshenSchedulerConfig instance.
	 */
	public static synchronized HoshenSchedulerConfig getInstance() throws HoshenException
	{
		if(null == m_instance) {
					m_instance = new HoshenSchedulerConfig();
					
		}
		return(m_instance);
	}
	

	/**
	 * private constructor for the singleton instance.
	 * @throws HoshenException in case of error.
	 */
	private HoshenSchedulerConfig() throws HoshenException
	{
		SAXParserFactory fact = SAXParserFactory.newInstance();
		fact.setValidating(false);
		SAXParser sp;
		try {
			m_sdf = new SimpleDateFormat("HH:mm:ss");
			sp = fact.newSAXParser();
			InputStream is = getInputStream("scheduler.xml");
			sp.parse(is,this);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new HoshenException("Error parsing scheduler configuration file",e);
		
		}
		
	}	
	
	
	/**
	 * get an InputStream from the classpath.
	 * @param fName the fileName to find.
	 * @return InputStream to the file.
	 */
	private static InputStream getInputStream(String fName)
	{
	
		InputStream is = fName.getClass().getResourceAsStream(fName);
		if(is == null){		
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			is = cl.getResourceAsStream(fName);
		}
		return(is);
	}
	
	/**
	 * @return
	 */
	public boolean isCleanupTasks() {
		return m_cleanupTasks;
	}

	/**
	 * @return
	 */
	public boolean isDisabled() {
		return m_disabled;
	}

	/**
	 * @return
	 */
	public String getJndiName() {
		return m_jndiName;
	}

	/**
	 * @return
	 */
	public List getTaskInfoList() {
		return m_taskInfoList;
	}

	/**
	 * @param b
	 */
	public void setCleanupTasks(boolean b) {
		m_cleanupTasks = b;
	}

	/**
	 * @param b
	 */
	public void setDisabled(boolean b) {
		m_disabled = b;
	}

	/**
	 * @param string
	 */
	public void setJndiName(String string) {
		m_jndiName = string;
	}

	/**
	 * @param list
	 */
	public void setTaskInfoList(List list) {
		m_taskInfoList = list;
	}
	
	public String getTaskHandlerJndi()
	{
		return(m_taskHandlerJndi);
	}
	
	public String getNotificationSinkJndi()
	{
		return(m_notificationSinkJndi);
	}


	/**
	 * return the date format to be used
	 * in scheduling tasks.
	 * @return String 
	 */
	public String getDateFormat()
	{
		return(m_dateFormat);
	}
	
	public String getTablePrefix()
	{
		return(m_tblPrefix);
	}
	
	/**
	 * startElement callback for SaxHandler.
	 */
	public void startElement( String namespaceURI,
			   String localName,
			   String qName,
			   Attributes attr ) throws SAXException {

	   String tmp = null;
	   if(qName.equals("SchedulerConfig")) {
	   		tmp = attr.getValue("disabled");
	   		Boolean b = new Boolean(tmp);
	   		m_disabled = b.booleanValue();
	   		tmp = attr.getValue("cleanupTasks");
			b = new Boolean(tmp);
			m_cleanupTasks = b.booleanValue();
			m_jndiName = attr.getValue("jndiName");
			m_notificationSinkJndi = attr.getValue("notificationSinkJndi");
			m_taskHandlerJndi = attr.getValue("taskHandlerJndi");
			m_dateFormat = attr.getValue("dateFormat");
			m_tblPrefix = attr.getValue("tablePrefix");
			if(null != m_dateFormat) {			
				m_sdf = new SimpleDateFormat(m_dateFormat);
			}						
			return;	   		 
	   }
	   
	   if(qName.equals("HoshenScheduledTasks")) {
	   		m_taskInfoList = new LinkedList();
	   		return;
		}
		
		
	   if(qName.equals("HoshenTaskInfo")) {
	   	
	   		String name = attr.getValue("name");
	   		String className = attr.getValue("className");
	   		String repeatInterval = attr.getValue("repeatInterval");
	   		tmp = attr.getValue("numberOfRepeats");
	   		int numRepeats = Integer.parseInt(tmp);
	   		tmp = attr.getValue("startDate");
	   		Date d = new Date();
	   		
	   		/*
	   		 * if startDate is empty then
	   		 * we take the current date.
	   		 */
	   		if(tmp != null) {	   		
	   			try {	   	
	   				/*
	   				 * we need to to this because otherwise the date 
	   				 * defaults to 01-01-1970
	   				 */
	   				d = m_sdf.parse(tmp);
	   				Date d2 = new Date();
	   				d.setYear(d2.getYear());
	   				d.setMonth(d2.getMonth());
	   				d.setDate(d2.getDate());
	   				log.debug("date = " + d.toString() + "\tdateString= " + tmp + "\tformat = " + m_dateFormat);
	   			} catch(ParseException pe) {
	   				pe.printStackTrace();
	   				d = new Date();
	   			}
	   			
	   		}
	   		m_currentTaskInfo = new HoshenTaskInfo(name,className,d,repeatInterval,numRepeats,0);
	   	
	   }

	}
	
	/**
	 * callback for the end of element.
	 */
	public void endElement(String uri, String localName, String qName) throws SAXException 
	 {
	   if(qName.equals("HoshenTaskInfo")) {
	   		m_taskInfoList.add(m_currentTaskInfo);
	   		m_currentTaskInfo = null;	   	
	   }
	   
	 }

}
