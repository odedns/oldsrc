package mataf.services.reftables;

import com.ibm.dse.base.*;

/**
 * @author Oded Nissan 24/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public abstract class RefTablesService extends Service 	 {
	/**
	 * get a list of records by primary key.
 	 * @param table the table to search.
	 * @param pk the primary key to search for.
	 * @return IndexedCollection  a list containing the
	 * records. or null if records are not found.
	 * @throws Exception in case of error.
	 */
	public abstract IndexedCollection getByKey(String table, String pk, Object value)	
		throws Exception; 
	
	/**
	 * get all records from the table.
	 * @param table the table to search.
	 * @return IndexedCollection  an IndexedColl containing all
	 * records in the table. or null if table is empty.
	 * @throws Exception in case of error.
	 */
	public abstract IndexedCollection getAll(String table) throws Exception;
	
	
	public abstract Object getByKey(String table, String pk, Object value, String column)
		throws Exception;			
		
	/**
	 * specify that a table should be cached.
	 * The table will be cached on the next call 
	 * that retrieves data from the table.
	 * @param tblName the name of the table to mark 
	 * for caching.
	 */ 	
	public abstract void addToCache(String tblName);
	
	/**
	 * check if a table should be cached
	 * by the service.
	 * @param tblName the name of the table.
	 * @return boolean true if the table should be cached.
	 */
	protected abstract boolean shouldCache(String tblName);

	
	

}
