package mataf.halbanathon.operations;

import mataf.services.reftables.RefTables;

import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (28/10/2003 19:27:24).  
 */
public class HalbanatHonTnay2ServerOp extends HalbanatHonAbstractTnayServerOp {
	
	/**
	 * @see com.ibm.dse.base.DSEServerOperation#execute()
	 */
	public void execute() throws Exception {
		String snifPeula = (String)getValueAt("GLSX_K86P_PARAMS.HA_SNIF");
		String snifMaarechet = (String)getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String accountType = (String)getValueAt("GLSX_K86P_PARAMS.HA_SCH");
		
		if(snifPeula.equals(snifMaarechet)) 
		{
			setConditionValid(false);
			return;
		}
		
		if((accountType.length()==0) || accountType.equals("0")) 
		{
			setConditionValid(false);
			return;
		}
		
		String kodMatbea = (String)getValueAt("GLSX_K86P_PARAMS.HA_MATBEA");
		if(kodMatbea.equals("0"))
			setConditionValid(HalbanatHonUtil.isExistsInMati(getContext()));
		else
			setConditionValid(HalbanatHonUtil.isExistsInMatach(getContext()));
	}
}
