package mataf.operations;

import java.util.Enumeration;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.FormatElement;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.HashtableFormat;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.cics.CICSConnection;
import com.ibm.dse.services.comms.CCMessage;
import com.ibm.dse.services.comms.CommonCommunicationsService;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JDBCServerOp extends DSEServerOperation {
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		JDBCTable tableService = (JDBCTable) getService("noDataAccount");
		HashtableFormat tableFormat = (HashtableFormat) FormatElement.readObject("noDataAccountsFmt");
		Context tableContext = getContext();
		try {
			Vector aDataVector = null;
			aDataVector = tableService.retrieveRecordsMatching("bankId=31");
			Enumeration aDataVectorEnum = aDataVector.elements();
			while (aDataVectorEnum.hasMoreElements()) {
				tableFormat.unformat((Hashtable) aDataVectorEnum.nextElement(), tableContext);
				System.out.println("*************************");
				System.out.println("Record retrieved with data:" + tableContext.getKeyedCollection());
			} // End while 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
