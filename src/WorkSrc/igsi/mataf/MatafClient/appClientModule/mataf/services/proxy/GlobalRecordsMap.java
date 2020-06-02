package mataf.services.proxy;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;

/**
 * @author Oded Nissan 23/07/2003
 * This class provides a map for mapping field codes to field names
 * and a map for mapping field names to field codes.
 * The class reads the global_records.properties file and then 
 * creates a reverse map for the names to codes map.
 */
public class GlobalRecordsMap {

	private static GlobalRecordsMap m_instance = null;	
	private static final String FILE_NAME = "global_records.properties";
	private static final String SERVER_NAME_PROPERTY = "CSClient.realCSClient.serverName";
	private static Properties m_codeMap = null;
	private static Properties m_nameMap = null;
	
	/**
	 * private constructor.
	 * no instantiation.
	 */
	private GlobalRecordsMap()
	{
		
	}
	

	private static String getServerUrl()
	{
		KeyedCollection	kc = Settings.getSettings();
		String url = (String) kc.tryGetValueAt(SERVER_NAME_PROPERTY);
		return(url);
	}
	
	
	
	public static GlobalRecordsMap getInstance()
		throws Exception
	{
		if(null == m_instance) {
			m_instance = new GlobalRecordsMap();
			m_instance.init();	
		}
		return(m_instance);
	}
	/**
	 * read the properties file and create two hashtables
	 * one maspping field codes to field names and the 
	 * other mapping field names to field codes.
	 */	
	private void init() throws Exception
	{
		String fileUrl = getServerUrl() + '/'  + FILE_NAME;
		m_codeMap = new Properties();
		m_nameMap = new Properties();		
		URL url = new URL(fileUrl);
        URLConnection urlconnection = url.openConnection();	          
        InputStream is = urlconnection.getInputStream();
		m_codeMap.load(is);		 
		
		Enumeration e = m_codeMap.keys();
		String key = null;
		String value = null;
		while(e.hasMoreElements()) {
			key = (String) e.nextElement();		
			value = (String) m_codeMap.getProperty(key);			
			m_nameMap.setProperty(value,key);
		}
		
	}
	
	/**
	 * get the name of the field corresponding
	 * to a specific code.
	 * @param code the field code.
	 * @return String the field name.
	 */
	public String getFieldName(int code)
	{
		String name = m_codeMap.getProperty(new Integer(code).toString());
		return(name);	
	}
	

	/**
	 * get the field code corresponding to 
	 * a field name.
	 * @param fieldName the name of the field.
	 * @return int the field code.
	 */	
	public int getFieldCode(String fieldName) 
	{
		String code = m_nameMap.getProperty(fieldName);
		int i = Integer.parseInt(code);
		return(i);	
	}

}
