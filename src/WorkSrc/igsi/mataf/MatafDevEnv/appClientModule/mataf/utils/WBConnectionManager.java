package mataf.utils;

import com.ibm.dse.base.*;
import com.ibm.dse.dw.cm.*;
import com.ibm.dse.dw.model.*;
import com.ibm.dse.dw.model.startup.*;
import com.ibm.dse.dw.model.db2.*;
import java.io.*;
import java.util.*;


/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class WBConnectionManager {

	static Workspace m_wks = null;
	/**
	 * Constructor for WBConnectionManager.
	 */
	private WBConnectionManager() {
		super();
	}
	
	private static void init()	throws Exception
	{
		Startup.getDefault().start(new File("properties/repository").toURL());
		Properties props = new Properties();
		props.load(new FileInputStream("properties/wks.properties"));
		m_wks = new Db2Workspace();
		System.out.println("props = " + props.toString());
		m_wks.setParams(props);
		m_wks.open();
		
		
		
	}
	
	/**
	 * get a workpace connection.
	 * return the open worlkspace if active.
	 */
	public static Workspace getWorkspace() throws Exception
	{
		if(null == m_wks) {
			init();						
		} 
		return(m_wks);
		
	}

	/**
	 * close the workspace connection.
	 * @throws Exception in case of error.
	 */
	public static void close() throws Exception
	{
		m_wks.close();
		m_wks = null;	
	}
	/**
	 * main test program.
	 */	
	public static void main(String argv[])
	{
		System.out.println("in WBConnectionManager ...");
		try {
			Workspace wks = getWorkspace();
			wks.close();	
			System.out.println("workspace closed...");
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}

}
