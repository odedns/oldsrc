package mataf.halbanathon.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.OperationsUtil;

import com.ibm.dse.base.DSEServerOperation;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/11/2003 15:49:55).  
 */
public class HalbanatHonSugCheshbonAcherServerOp extends DSEServerOperation 
{
	public void execute() throws Exception 
	{
		String screenCoin = (String)getValueAt("HASS_LAKOACH_SUG.HA_MATBEA");
		String screenAccountType = (String)getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER");
		
		
		if(screenCoin.equals("0"))
		{
			JDBCTable service = (JDBCTable)getService("GLST_SCH");
			
			String SQLCondition = "GL_SCH=" + screenAccountType ;
			Vector aDataVector = 
				service.retrieveRecordsMatching(SQLCondition.toString());
			
			if(aDataVector.size()==0)
				return;
				
			Hashtable row = (Hashtable)aDataVector.elementAt(0);
			String sb = (String)row.get("GL_SW_SB_CH");
			if(sb.equals("0"))
			{
				HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","KL008");
				((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.HA_SCH_ACHER")).setShouldRequestFocus(Boolean.TRUE);
			}
		}
		else
		{
			JDBCTable service = (JDBCTable)getService("MZST_SCH");
			
			String SQLCondition = "GL_SCH=" + screenAccountType ;
			Vector aDataVector = 
				service.retrieveRecordsMatching(SQLCondition.toString());
			
			if(aDataVector.size()==0)
				return;
				
			Hashtable row = (Hashtable)aDataVector.elementAt(0);
			String sb = (String)row.get("MZ_SW_SB_CH");
			if(sb.equals("1"))
			{
				HalbanatHonUtil.addErrorMsg(getContext(),"runtimeMsgs","KL008");
				((VisualDataField) getElementAt(
					"HASS_LAKOACH_SUG.HA_SCH_ACHER")).setShouldRequestFocus(Boolean.TRUE);
			}
		}
	}
}
