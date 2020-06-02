/*
 * Created on 19/09/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package hoshen.startup;

import hoshen.common.utils.exception.HoshenException;
import hoshen.scheduler.HoshenTaskInfo;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

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
public class HoshenStartupConfig extends DefaultHandler {

	private static HoshenStartupConfig m_instance = null;
	private List m_taskInfoList = null;
	private HoshenTaskInfo m_currentTaskInfo;
	private static Logger log = Logger.getLogger(HoshenStartupConfig.class);
	
	/**
	 * obtain the HoshenSchedulerConfig singleton instance.
	 * @return HoshenSchedulerConfig instance.
	 * @throws HoshenException in case of error.
	 */
		public static synchronized HoshenStartupConfig getInstance() throws HoshenException
		{
			log.debug(">getInstance()");
			if(null == m_instance) {
						m_instance = new HoshenStartupConfig();
					
			}
			return(m_instance);
		}
	

		/**
		 * private constructor for the singleton instance.
		 * @throws HoshenException in case of error.
		 */
		private HoshenStartupConfig() throws HoshenException
		{
			SAXParserFactory fact = SAXParserFactory.newInstance();
			fact.setValidating(false);
			SAXParser sp;
			try {
				sp = fact.newSAXParser();
				InputStream is = getInputStream("startup.xml");
				sp.parse(is,this);
		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new HoshenException("Error parsing scheduler configuration file",e);
		
			}
		
		}	
	
	
		/**
		 * get the list of task info 
		 * for the startup tasks.
		 * @return List the task list.
		 */	
		public List getTaskInfoList()
		{
			return(m_taskInfoList);
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
		 * startElement callback for SaxHandler.
		 */
		public void startElement( String namespaceURI,
				   String localName,
				   String qName,
				   Attributes attr ) throws SAXException {

			
		      if(qName.equals("HoshenTaskInfo")) {
	   	
				String name = attr.getValue("name");
				String className = attr.getValue("className");
				String tmp = attr.getValue("priority");
				int priority = Integer.parseInt(tmp);					   		
	
				m_currentTaskInfo = new HoshenTaskInfo(name,className,null,null,0,priority);
	   	
		   }
		   
		   if(qName.equals("HoshenStartupTasks")) {
		   		m_taskInfoList = new LinkedList();
		   }

		}
	
		/**
		 * callback for the end of element.
		 */
		public void endElement(String uri, String localName, String qName) throws SAXException 
		 {
		   if(qName.equals("HoshenTaskInfo")) {
		   		log.debug("adding startup task to list: " + m_currentTaskInfo.getName());
				m_taskInfoList.add(m_currentTaskInfo);
				m_currentTaskInfo = null;	   	
		   }
	   
		 }
		 
}
