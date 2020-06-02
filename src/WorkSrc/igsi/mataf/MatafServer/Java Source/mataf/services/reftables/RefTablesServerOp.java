package mataf.services.reftables;

import java.io.IOException;

import com.ibm.dse.base.*;
import mataf.utils.*;
import mataf.logger.*;
import mataf.operations.general.*;

/**
 * @author Oded Nissan 24/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RefTablesServerOp extends GenericSrvOp {
	RefTables m_service = null;
	private static final char WILDCARD_CHAR='%';
	/**
	 * Constructor for RefTablesServerOp.
	 */
	public RefTablesServerOp() {
		super();
	}

	/**
	 * Constructor for RefTablesServerOp.
	 * @param arg0
	 * @throws IOException
	 */
	public RefTablesServerOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for RefTablesServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public RefTablesServerOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for RefTablesServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public RefTablesServerOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	

	private boolean isWildcard(String s) 
	{
		boolean res = false;		
		if(WILDCARD_CHAR == s.charAt(0) &&
			WILDCARD_CHAR == s.charAt(s.length()-1)) {
			res =true;
		}
		return(res);
			
	}
	/**
	 * parse the reftablesSearch params and call the appropriate
	 * function on the RefTables service.
	 * return the results back to the RefTablesClientOp.
	 */	
	public void execute() throws Exception
	{
		GLogger.debug("in RefTablesServerOp,execute()");
		
		
		String tableName = (String) getValueAt("refTablesSearch.tableName");
		String key = (String) getValueAt("refTablesSearch.key");		
		Object value =(Object) getValueAt("refTablesSearch.value");
		if(m_service == null) {
			m_service = (RefTables) getService("refTablesService");
		}
		
		IndexedCollection ic = null;		
		if(value.toString().equals("*")) {
			ic = m_service.getAll(tableName);
			if(ic != null) {
				ic.setName("refTablesList");
				addElement(ic);
			}
		} else {
			if(isWildcard(value.toString())) {
				String tmp = value.toString();
				String newVal = tmp.substring(1,tmp.length()-1);
				ic = m_service.getByKeyEx(tableName,key, newVal);
			}else {
				ic = m_service.getByKey(tableName,key, value);
			}
			if(ic != null) {
				ic.setName("refTablesList");
				addElement(ic);			
			}
		}				
					
		
	}

}
