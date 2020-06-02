package mataf.halbanathon.operations;

import mataf.services.reftables.RefTables;
import mataf.services.reftables.RefTablesService;
import org.uddi4j.request.GetRegisteredInfo;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (03/11/2003 18:01:45).  
 */
public class HalbanatHonTnay4ServerOp extends HalbanatHonAbstractTnayServerOp 
{
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception 
	{		
		String accountType = (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		String accountNumber = (String)getValueAt("GLSX_K86_PARAMS.HA_CH");
		if( (accountType.equals("080")) ||
			(accountType.equals("081")) ||
			(accountType.equals("087")) ||
			(accountType.equals("088")) )
		{
			RefTables refTables = (RefTables) getService("refTablesService");
			IndexedCollection iColl = 
						refTables.getByKey("HAST_CH_80","HA_SCH",accountType);
			for(int i=0;i<iColl.size();i++)
			{
				KeyedCollection kColl = (KeyedCollection)
					iColl.getElementAt(i);
				String val = (String)kColl.getValueAt("HA_KIDOMET_CH_080");
				if(accountNumber.charAt(0)==val.charAt(0))
				{
					setConditionValid(true);
					return;
				}
				
			}
			// The record was not found in the table.
			setConditionValid(false);
		}
		else
		{
			setConditionValid(false);
		}
	}
}
