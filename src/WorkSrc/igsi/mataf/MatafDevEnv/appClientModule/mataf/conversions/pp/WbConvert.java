package mataf.conversions.pp;
import mataf.utils.*;
import com.ibm.dse.dw.model.*;
import java.util.*;
import java.sql.*;

/**
 * Convert work bench data to a custom db for
 * Pre processor use.
 * @author odedn
 *
 */
public class WbConvert {

	static final String MATAF_FLD_INSERT="insert into mataf_data_field (";
	static final String MATAF_SCR_INSERT="insert into mataf_scr_attrs (";
	static final String MATAF_RECORD_INSERT="insert into mataf_record (";
	static final String MATAF_RECORD__DETAILS_INSERT="insert into mataf_record_details (";
	static final String USAGE = "Usage mataf.conversions.pp.WbConvert -dbname <databasename> -export <fields|record|all>";
	
	Workspace m_wks = null;
	Connection m_conn;
	/**
	 * Constructor for WbConvert.
	 */
	public WbConvert() {
		super();
	}

	/**
	 * process all instances -
	 * convert them to DB2 tables for pre processor use.
	 */
	public void process(String dbName, String option) throws Exception
	{
		m_wks = WBConnectionManager.getWorkspace();
		m_conn = DBConnectionManager.getConnection(dbName);
		try {
			if(option.equals("fields")) {
				processFields();	
				return;
			}
			if(option.equals("records")) {
				processRecords();
				return;
				
			}
			if(option.equals("all")) {
				processFields();
				processRecords();	
			}
		
		} finally {
			m_wks.close();					
			m_conn.close();
		}
		
	}
	
	/**
	 * process data fields and screen attributes.
	 * insert into two field tables.
	 * @throws Exception in case of error.
	 */
	void processFields() throws Exception
	{
		System.out.println("process fields");
		Entity ent = m_wks.getEntity("Mataf data field");
		Instance ins[] = m_wks.getInstancesByEntity(ent);
		Instance subIns[]= null;
		String id=null;
		String desc = null;
		for(int i=0; i< ins.length; ++i) {
			System.out.println("Inserting  Field(" + i +"): " + ins[i].getName());
			id = ins[i].getName();
			insertField(ins[i],MATAF_FLD_INSERT,null,desc);
			desc = ins[i].getDescription();
			InstanceComposition comp = ins[i].getComposition();			
			if(null != comp) {
				subIns = comp.getInstances();
				if(subIns.length > 0) {
					insertField(subIns[0],MATAF_SCR_INSERT,id,null);
				}
			} // if
		}	// for					
		System.out.println("Inserted " + ins.length + " fields...");
	}
	
	/**
	 * process data records and their details.
	 * insert into two record tables.
	 * @throws Exception in case of error.
	 */	
	void processRecords() throws Exception
	{		
		Entity ent = m_wks.getEntity("Mataf record");
		Instance ins[] = m_wks.getInstancesByEntity(ent);
		Instance subIns[]= null;
		String id=null;
		String desc = null;
		for(int i=0; i< ins.length; ++i) {
			System.out.println("Inserting  Record(" + i +"): " + ins[i].getName());
			id = ins[i].getName();
			desc = ins[i].getDescription();
			insertField(ins[i],MATAF_RECORD_INSERT,null,desc);
			InstanceComposition comp = ins[i].getComposition();			
			if(null != comp) {
				subIns = comp.getInstances();
				if(subIns.length > 0) {
					insertRecordDetails(subIns[0],MATAF_RECORD__DETAILS_INSERT,id,null);
										
				}
			} // if
		}	// for					
		System.out.println("Inserted " + ins.length + " records...");
	}
	

	/**
	 * insert a field into the db table.
	 * @param ins the Instance containing a 
	 * Mataf data field.
	 */
	void insertRecordDetails(Instance ins, String sql, String id, String desc) throws Exception
	{
		String value = null;
		ArrayList ar = null;
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if(null !=id) {
			sb.append("id,");	
		}
		if(null !=desc) {
			sb.append("description,");	
		}
	
		DataPair pairs[] = null;
		DataPair pair = null;
		
		ar = InstanceUtils.toArrayList(ins);
		Instance subIns[] = null;
		InstanceComposition comp = ins.getComposition();			
		if(null != comp) {
			subIns = comp.getInstances();
			if(subIns.length > 0) {
				for(int i=0; i < subIns.length; ++i) {
					pair = new DataPair(subIns[i].getName(), subIns[i].getValue("value"));
					ar.add(pair);
				}
			}
			
		} // if
						
		pairs = new DataPair[ar.size()];
		pairs = (DataPair[]) ar.toArray(pairs);
		for(int i=0; i < pairs.length; ++i) {
			sb.append(pairs[i].getKey());
			if(i < pairs.length - 1) {
				sb.append(",");			
			}
		}
		sb.append(") VALUES (");
		if(null !=id) {
			sb.append('\'');	
			sb.append(id);
			sb.append("\',");	
		}
		if(null !=desc) {
			sb.append('\'');	
			sb.append(StringUtils.handleCommas(desc));
			sb.append("\',");	
		}

		for(int i=0; i < pairs.length; ++i) {
			value = (String) pairs[i].getValue();
			sb.append('\'');
			sb.append(StringUtils.handleCommas(value));
			sb.append('\'');
			if(i < pairs.length - 1) {
				sb.append(",");			
			}
		}
		sb.append(')');		
//		System.out.println("sb = " + sb.toString());
		
		Statement st = m_conn.createStatement();
		st.executeUpdate(sb.toString());
		
		
	}
	

	
	/**
	 * insert a field into the db table.
	 * @param ins the Instance containing a 
	 * Mataf data field.
	 */
	void insertField(Instance ins, String sql, String id, String desc) throws Exception
	{
		String value = null;
		StringBuffer sb = new StringBuffer();
		sb.append(sql);
		if(null !=id) {
			sb.append("id,");	
		}
		if(null !=desc) {
			sb.append("description,");	
		}
	
		DataPair pairs[] = InstanceUtils.toDataPairs(ins);
		for(int i=0; i < pairs.length; ++i) {
			sb.append(pairs[i].getKey());
			if(i < pairs.length - 1) {
				sb.append(",");			
			}
		}
		sb.append(") VALUES (");
		if(null !=id) {
			sb.append('\'');	
			sb.append(id);
			sb.append("\',");	
		}
		if(null !=desc) {
			sb.append('\'');	
			sb.append(StringUtils.handleCommas(desc));
			sb.append("\',");	
		}

		for(int i=0; i < pairs.length; ++i) {
			value = (String) pairs[i].getValue();
			sb.append('\'');
			sb.append(StringUtils.handleCommas(value));
			sb.append('\'');
			if(i < pairs.length - 1) {
				sb.append(",");			
			}
		}
		sb.append(')');		
//		System.out.println("sb = " + sb.toString());
		
		Statement st = m_conn.createStatement();
		st.executeUpdate(sb.toString());
		
		
	}
	
	
	/**
	 * main program.
	 */
	public static void main(String[] args) 
	{
				
		System.out.println("WbConvert ...");
		if(args.length < 1) {
			System.err.println(USAGE);
			System.exit(1);
		}
		String dbName = null;
		String option = null;
		
		for(int i=0; i < args.length; ++i) {
			if(args[i].equals("-dbname")) {
				dbName = args[++i];
				continue;
			}
			if(args[i].equals("-option")) {
				option = args[++i];
				continue;
			}			
		}
		System.out.println("dbName = " + dbName + "\toption = " + option);
		
		WbConvert cvt = new WbConvert();	
		try {
			cvt.process(dbName,option);
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
