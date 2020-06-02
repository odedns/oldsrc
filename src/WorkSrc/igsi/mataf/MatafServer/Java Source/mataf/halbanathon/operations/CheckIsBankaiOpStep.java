package mataf.halbanathon.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (28/10/2003 14:57:02).  
 */
public class CheckIsBankaiOpStep extends MatafOperationStep 
{
	private static final String SUG_CHESHBON_LAKOACH	= "0";
	private static final String SUG_CHESHBON_BANKAI	= "1";
	
	private static final String SUG_MATBEA_TELLER  	= "0";
	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception 
	{
		String val = getStringAt("GLSX_K86P_PARAMS.HA_SCH");
		
		if((val.length()==0) || (val.equals("0")))
			setValueAt("HASX_PIRTEY_CHESHBON.GL_SW_BANKAI", SUG_CHESHBON_BANKAI);
		else
		if(isTeller())
		{
			val = (String)getRefTablesService().getByKey("GLST_SCH","GL_SCH",val,"GL_SW_SB_CH");
			setValueAt("HASX_PIRTEY_CHESHBON.GL_SW_BANKAI",val.equals(SUG_CHESHBON_LAKOACH) ? 
																		SUG_CHESHBON_BANKAI : SUG_CHESHBON_LAKOACH);				
		} else 
		{
			val = (String)getRefTablesService().getByKey("MZST_SCH","GL_SCH",val,"MZ_SW_BANKAI");
			setValueAt("HASX_PIRTEY_CHESHBON.GL_SW_BANKAI",val.equals(SUG_CHESHBON_LAKOACH) ? 
																		SUG_CHESHBON_BANKAI : SUG_CHESHBON_LAKOACH);
		}
		
		return RC_OK;
	}
	
	private boolean isTeller() throws DSEObjectNotFoundException
	{
		return getStringAt("GLSX_K86P_PARAMS.HA_MATBEA").equals(SUG_MATBEA_TELLER);
	}
}
