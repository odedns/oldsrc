package mataf.services.reftables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mataf.services.JdbcConnectionService;
import mataf.utils.IndexedColUtils;
import mataf.logger.*;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;

/**
 * @author Oded Nissan 24/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RefTables extends RefTablesService {
	static final String REFTABLES_CTL_NAME = "REFTABLES_CTL";
	boolean m_loadAll = false;
	String m_jdbcService= null;
	JdbcConnectionService m_jdbcSrv;
	static HashMap m_tables = new HashMap();		
	static HashMap m_tsTable = new HashMap();
	static HashMap m_tblToCache = new HashMap();
	/**
	 * constuctor.
	 */
	public RefTables()
	{
	}

	/**
	 * terminates the service.
	 * called when context is unchained.
	 */
	public void terminate() throws DSEException 
	{
		m_jdbcSrv.terminate();
	}	
	
	void init() throws IOException
	{
		m_jdbcSrv = (JdbcConnectionService) Service.readObject(m_jdbcService);			
	}
	/**
	 * initialize from externalizer.
	 */
	public Object initializeFrom(Tag aTag)
                                throws java.io.IOException,
                                       DSEException
	{
		super.initializeFrom(aTag);
		String name = null;
		String value = null;
		
		for (int i=0;i<aTag.getAttrList().size();i++) {
			TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);			
			name = attribute.getName();
			value = (String) attribute.getValue();
			if (name.equals("jdbcService")) {				
				m_jdbcService = value;
				continue;
			}
			if(name.equals("loadAll")) {
				if(value.equalsIgnoreCase("true")) {
					m_loadAll = true;	
				} else {
					m_loadAll = false;	
				}									
			}
			
		}		
		init();
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
	 * get a record by primary key.
	 * If the table is in memory use the RefTablesMemoryStorage class
	 * otherwise use the ReftablesDBStorage class.
 	 * @param table the table to search.
	 * @param pk the primary key to search for.
	 * @return KeyedColl a KeyedColl containing the
	 * record data. or null if record is not found.
	 * @throws Exception in case of error.
	 */
	public synchronized IndexedCollection getByKey(String table, String pk, Object value)
		throws Exception			
	{	
		/*
		 * if table should not be cached.
		 */
		if(!shouldCache(table)) {
			GLogger.debug("no caching calling getRecord...");
			IndexedCollection ic = getRecord(table,pk,value);
			return(ic);	
		}
		
		/*
		 * else table should be cached.
		 */		
		IndexedCollection ic = (IndexedCollection) m_tables.get(table);
		if(null == ic || shouldLoad(table)) {
			ic = loadTable(table);
			m_tables.put(table,ic);			
		}
		IndexedCollection ic2 = IndexedColUtils.find(ic,pk,value);		
		return(ic2);
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
	 * get all records where the specified key contains the specified 
	 * substring.	 
	 * @param table the table to search.
	 * @param pk the key to match.
	 * @param value the substring to try and match.
	 * @return IndexedCollection  an IndexedColl containing all
	 * records in the table. or null if table is empty.
	 * @throws Exception in case of error.
	 */
	public synchronized IndexedCollection getByKeyEx(String table, String pk, Object value)	
		throws Exception
	{
		IndexedCollection ic = (IndexedCollection) m_tables.get(table);
		if(null == ic || shouldLoad(table)) {
			ic = loadTable(table);
			if(shouldCache(table)) {
				m_tables.put(table,ic);			
			}
		}
		IndexedCollection ic2 = IndexedColUtils.findEx(ic,pk,value);		
		return(ic2);	
	}
		
	
	
	/**
	 * get all records from the table.
	 * If the table is in memory use the RefTablesMemoryStorage class
	 * otherwise use the ReftablesDBStorage class.
	 * @param table the table to search.
	 * @return IndexedCollection  an IndexedColl containing all
	 * records in the table. or null if table is empty.
	 * @throws Exception in case of error.
	 */
	public synchronized IndexedCollection getAll(String table) throws Exception
	{		
		/*
		 * if table should not be cached.
		 */
		if(!shouldCache(table)) {
			IndexedCollection ic = loadTable(table);
			GLogger.debug("getAll - no cahing ");
			System.out.println("getAll - no cahing ");
			return(ic);
		}
		/*
		 * else table shoud be cached.
		 */
		IndexedCollection ic = (IndexedCollection) m_tables.get(table);
		if(null == ic || shouldLoad(table)) {
			ic = loadTable(table);
			m_tables.put(table,ic);			
			Timestamp ts = getTableTs(table);
			m_tsTable.put(table,ts);
		}			
		return(ic);
	}
	
	/**
	 * load a table into memory.
	 * Read all records from the DB. map the records into 
	 * an IndexedCollection.
	 * @param table the name of the table to load.
	 * @return IndexedCollection  an IndexedColl containing all
	 * records in the table. or null if table is empty.	
	 */
	protected synchronized IndexedCollection loadTable(String table) 
		throws Exception
	{	
		String sql = "SELECT * FROM " + table;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		IndexedCollection ic = null;
		try {
			conn = m_jdbcSrv.getConnection();
			st = conn.createStatement();	
			rs = st.executeQuery(sql.toString());
			ic = (IndexedCollection)DataElement.readObject("refTablesList");
			ic.removeAll();
			
			mapList(rs,ic);			
		}
		finally {
			try {
				if(null != rs) {
					rs.close();
				}
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();	
				}				

		    } catch(java.sql.SQLException se) {
				se.printStackTrace();
		    }
		} //finally	
		return(ic);		
	}

	/**
	 * get a record from the DB using a primary key.
	 */
	private  synchronized IndexedCollection getRecord(String tblName, String colName, Object value)
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		boolean useComma = false;
		IndexedCollection ic = null;
	
		useComma = (value instanceof String ) ? true : false;
			
		try {
			conn = m_jdbcSrv.getConnection();
		
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT * FROM ");
			sb.append(tblName);
			sb.append(" WHERE ");
			sb.append(colName);
			sb.append("=");
			if(useComma) {
				sb.append("'");	
			}
			sb.append(value);
			if(useComma) {
				sb.append("'");	
			}
		
			System.out.println("sql = " + sb);
			st = conn.createStatement();
			rs = st.executeQuery(sb.toString());
			ic = (IndexedCollection)DataElement.readObject("refTablesList");
			ic.removeAll();			
			mapList(rs,ic);						
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
			
				if(null != conn) {
					conn.close();
				}
				if(null != st) {
					st.close();	
				}
				if(null != rs) {
					rs.close();
				}
			
			} catch(Exception e) {
				e.printStackTrace();			
			}

		} // finally
		return(ic);	
	}

	/**
	 * map a result set data into an IndexecCollection 
	 * list.
	 */
	private void mapList(ResultSet rs, IndexedCollection ic)
		throws Exception
	{
		KeyedCollection kc = null;
		ResultSetMetaData meta = rs.getMetaData();
		int colNums = meta.getColumnCount();
		
		/**
		 * read the column names into a String array.
		 */
		String cols[] = new String[colNums];
		for(int i=1; i <= colNums; ++i) {
			cols[i-1] = meta.getColumnName(i);
				
		}	
			
		while(rs.next()) {
			kc = (KeyedCollection) DataElement.readObject("refTablesRecord");
			mapRecord(rs, kc, cols);				
			ic.addElement(kc);
	//		System.out.println("added kc = " + kc.toString());
		}	

	}	
	
	
	/**
	 * map a record from a result set object
	 * into a KeyedCollection.
	 */
	private void mapRecord(ResultSet rs, KeyedCollection kc, String cols[])
		throws Exception
	{
	
		Object o = null;
		for(int i=0; i < cols.length; ++i) {
			o = rs.getObject(cols[i]);
			kc.setValueAt(cols[i], o);
		}		
	}
	
	
	/**
	 * get the timestamp for the table from 
	 * the control table.
	 */
	private Timestamp getTableTs(String tableName) 
	{
		Timestamp ts = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TS FROM ");
		sql.append(REFTABLES_CTL_NAME);
		sql.append(" WHERE TABLE_NAME='");
		sql.append(tableName);
		sql.append('\'');
				
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {	
			conn = m_jdbcSrv.getConnection();
			st = conn.createStatement();	
			rs = st.executeQuery(sql.toString());
			if(rs.next()) {
				ts = rs.getTimestamp(1);
			}
		} catch(Exception ex) {
			ex.printStackTrace();				
			ts = null;
		}
		finally {
			try {
				if(null != rs) {
					rs.close();
				}
				if(st != null) {
					st.close();
				}
				if(conn != null) {
					conn.close();	
				}				

		    } catch(java.sql.SQLException se) {
				se.printStackTrace();
		    }
		} //finally	
		return(ts);		

		
	}


	/**
	 * check if the table should be loaded.
	 */
	private boolean shouldLoad(String tableName) 
	{
		boolean result = false;
		Timestamp ts = getTableTs(tableName);
		if(ts == null ) {
			return(false);	
		}		
		Timestamp oldts = (Timestamp) m_tsTable.get(tableName);
		if(null != oldts && null != ts) {
			result = ts.after(oldts) ? true : false;
		}
		return(result);
			
	}
		
	/**
	 * @see mataf.services.reftables.RefTablesService#addToCache(String)
	 */
	public void addToCache(String tblName) {
		m_tblToCache.put(tblName,"");
	}
	
	
	/**
	 * check if a table should be cached
	 * by the service.
	 * @param tblName the name of the table.
	 * @return boolean true if the table should be cached.
	 */
	protected boolean shouldCache(String tblName)
	{
		boolean res = true;
		
		if(!m_loadAll) {
			String s = (String) m_tblToCache.get(tblName);
			res = (s != null) ? true : false;
		}		
		return(res);		
	}

}

