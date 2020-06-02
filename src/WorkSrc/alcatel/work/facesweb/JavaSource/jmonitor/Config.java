/* Created on 02/08/2006 */
package jmonitor;

import java.io.*;
import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import onjlib.utils.Path;
import onjlib.utils.ToStringBuilder;
import onjlib.utils.xml.*;

import org.apache.log4j.Logger;
import org.w3c.dom.*;



/**
 * This class provides access to the Jadmin configuration data
 * which is stored in an XML file jadmin.xml
 * @author odedn
 */
public class Config {
	private final static Logger logger = Logger.getLogger(Config.class);
	private static final String FILE_NAME = "jadmin.xml";
	private static final String BAK_FILE_NAME = "jadmin.old";
	private static Config m_instance = null;
	private Document m_doc;
	private List m_srvList;
	
	/**
	 * the singleton method for Config.
	 * get an instance or instantiate the singleton instance
	 * if it does not exist.
	 * @return Config the singleton instance.
	 */
	public static synchronized Config getInstance()
	{
		if(null == m_instance) {
			try {
				m_instance = new Config();
			} catch(GXmlException ge) {
				ge.printStackTrace();
				logger.error("Error initializing configuration file: " + ge);				
			}
		}
		return(m_instance);
	}	
	
	
	/**
	 * private constructor for singleton class.
	 *
	 */
	private Config() throws GXmlException
	{
		InputStream is = Path.findFile(FILE_NAME);		
		if(is == null) {
			logger.error("file not found");
			return;
		}
		m_doc = XmlUtils.readXmlDocument(is);
		m_srvList = getServers();

	}

	/**
	 * get the list of servers from the config file.
	 * @return List the list of servers from the xml file.
	 */
	private synchronized List getServers()
	{
		LinkedList srvList = null;
		String s = null;
		Element servers = m_doc.getDocumentElement();
		
		NodeList nodes = servers.getElementsByTagName("server");
		for(int i=0; i < nodes.getLength(); ++i) {
			if(null == srvList) {
				srvList = new LinkedList();
			}
			Node n  = nodes.item(i);
			Element e = (Element)n;			
			Server srv = new Server();
			s = e.getAttribute("name");
			if(null != s) {
				srv.setName(s);
			}
			s = e.getAttribute("host");
			if(null != s) {
				srv.setHost(s);
			}
			s = e.getAttribute("port");
			if(s != null) {
				srv.setPort(s);
			}
			srvList.add(srv);
			
										
		}
		return(srvList);
	}
	
	/**
	 * get the list of servers.
	 * @return List of Server objects.
	 */
	public List getServerList()
	{
		return(m_srvList);
	}
	
	/**
	 * add a server to the list and the XML file.
	 * @param srv the server to add.
	 */
	public synchronized void addServer(Server srv) throws JadminException
	{
		m_srvList.add(srv);
		Element servers = m_doc.getDocumentElement();
		Element newServer = m_doc.createElement("server");
		newServer.setAttribute("name", srv.getName());
		newServer.setAttribute("host", srv.getHost());
		String s = new Integer(srv.getPort()).toString();
		newServer.setAttribute("port", s);
		servers.appendChild(newServer);
		saveDocument(m_doc);
	}
	
	/**
	 * save the jadmin.xml file.
	 * backup the old file.
	 * @param doc The Document object to save.
	 * @throw JadminException in case of error.
	 */
	public synchronized void saveDocument(Document doc) throws JadminException
	{
		String fname = Path.findFileInClassPath(FILE_NAME);
		File f = null;
		if(fname == null) {
			logger.info("saveDocument: file not found: " + fname);
			throw new JadminException("saveDocument: file not found: " + fname);
		} else {
			f = new File(fname);
			File toFile = new File(f.getParentFile().toString() + '/' + BAK_FILE_NAME);
			f.renameTo(toFile);
		}
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(f));
			XmlUtils.writeXmlDocument(doc,pw);
		} catch(Exception ge) {
			logger.error(ge.getMessage());
			throw new JadminException("Error in saveDocument",ge);
		}
		
		
	}
	
	
	/**
	 * main test.
	 * @param args
	 */
	public static void main(String[] args)
	{
		logger.info("in main");
		try {
			Config cfg = Config.getInstance();			
			List servers = cfg.getServerList();
			Iterator iter = servers.iterator();
			while(iter.hasNext()) {
				Server srv = (Server) iter.next();
				logger.info(ToStringBuilder.toString(srv));
			}
			
			/*
			Element servers = doc.getDocumentElement();
			Element newServer = doc.createElement("server");
			newServer.setAttribute("name", "xsmServer");
			newServer.setAttribute("host", "N814");
			newServer.setAttribute("port", "8880");
			servers.appendChild(newServer);
		
			XmlUtils.writeXmlDocument(doc,System.out);
			*/

	} catch(Exception e) {
		e.printStackTrace();
	}
	} // main
}


