package mataf.services.reftables;
import com.ibm.dse.base.*;
import mataf.logger.*;
import mataf.utils.*;

/**
 * @author Oded Nissan 24/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RefTablesClient extends RefTablesService {

	boolean m_loadAll = false;
	RefTablesClientOp m_clientOp = null;
	
	/**
	 * initialize from externalizer.
	 */
	public Object initializeFrom(Tag aTag)
                throws java.io.IOException,
                                       DSEException
	{
		super.initializeFrom(aTag);		
		GLogger.debug("in RefTablesService");		
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if(name.equals("loadAll")) {
				if(value.equalsIgnoreCase("true")) {
					m_loadAll = true;	
				} else {
					m_loadAll = false;	
				}									
			}
	
		}				
		return(this);
	}
	
	/**
	 * set the flag for loading all 
	 * tables into memory.
	 */
	public void setLoadAll(boolean loadAll)
	{
		m_loadAll = loadAll;	
	}
	/**
	 * @see composer.services.reftables.RefTablesService#getByKey(String, String)
	 */
	public IndexedCollection getByKey(String table, String key, Object value) throws Exception 
	{
		if(null == m_clientOp) {
			m_clientOp = (RefTablesClientOp) DSEClientOperation.readObject("refTablesClientOp");					
		}
		m_clientOp.setValueAt("refTablesSearch.tableName", table);
		m_clientOp.setValueAt("refTablesSearch.key", key);
		m_clientOp.setValueAt("refTablesSearch.value", value);
		m_clientOp.execute();
		IndexedCollection ic = (IndexedCollection) m_clientOp.getElementAt("refTablesList");
		return(ic);
	}

	/**
	 * get a record by primary key.
	 * If the table is in memory use the RefTablesMemoryStorage class
	 * otherwise use the ReftablesDBStorage class.
	 * This method should only be called if the results are exactly one row
	 * and we need a specific column.
 	 * @param table the table to search.
	 * @param pk the primary key to search for.
	 * @param value the value of the pk.
	 * @param column the name of the column to retrieve
	 * @return Object the value of the column to retreive or 
	 * null if a  record is not found.
	 * @throws Exception in case of error.
	 */
	public synchronized Object getByKey(String table, String pk, Object value, String column)
		throws Exception			
	{
		IndexedCollection ic = getByKey(table, pk,value);
		if(null == ic ) {
			return(null);	
		}	
		Object o = ((KeyedCollection) ic.getElementAt(0)).getValueAt(column);
		return(o);
	}
	/**
	 * @see composer.services.reftables.RefTablesService#getAll(String)
	 */
	public IndexedCollection getAll(String table) throws Exception 
	{
		if(null == m_clientOp) {
			m_clientOp = (RefTablesClientOp) DSEClientOperation.readObject("refTablesClientOp");					
		}
		m_clientOp.setValueAt("refTablesSearch.tableName", table);
		m_clientOp.setValueAt("refTablesSearch.value", "*");
		m_clientOp.execute();
		IndexedCollection ic = (IndexedCollection) m_clientOp.getElementAt("refTablesList");
		return(ic);
	}
	
	/**
	 * @see mataf.services.reftables.RefTablesService#addToCache(String)
	 */
	public void addToCache(String tblName) {
	}
	

	/**
	 * @see mataf.services.reftables.RefTablesService#shouldCache(String)
	 */
	protected boolean shouldCache(String tblName) {
		return false;
	}

}
