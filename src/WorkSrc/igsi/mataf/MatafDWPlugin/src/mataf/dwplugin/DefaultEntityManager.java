package mataf.dwplugin;

import com.ibm.dse.dw.model.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DefaultEntityManager {

	static String m_name = null;
	static HashMap m_tbl = new HashMap();
	
	static {
		
		m_tbl.put("Fields", "TGL data field");	
		m_tbl.put("Operations","TGL operation");
		m_tbl.put("Records", "TGL data record"); 
	}
	/**
	 * Constructor for DefaultEntityManager.
	  */
	private DefaultEntityManager() {
		super();
	}
	
	
	/**
	 * set the object which instances should be added to.
	 * @param o the object, either an Instance or a Group.
	 */
	public static void setObject(Object o)
	{
		if(o instanceof Group) {
			m_name = ((Group) o).getName();	
		} else {
			m_name = ((Instance) o).getName();	
		}
	}

	/**
	 * get the default entity that is suitable
	 * for the current Group or instance by searchin the 
	 * hash table.
	 * if there are no entries in the hash table return
	 * an empty String.
	 * @param String the name of the returned default entity.
	 */	
	public static String getDefaultEntity()
	{
		if(null == m_name) {
			return("");	
		}
		String defEntity = (String) m_tbl.get(m_name);
		if(null == defEntity) {
			defEntity = "";	
		}
		m_name = null;
		return(defEntity);	
	}

}
