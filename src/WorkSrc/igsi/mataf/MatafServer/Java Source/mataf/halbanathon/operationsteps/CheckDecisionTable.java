package mataf.halbanathon.operationsteps;

import java.util.Hashtable;

import mataf.general.operations.MatafOperationStep;
import mataf.halbanathon.operations.HalbanatHonUtil;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckDecisionTable extends MatafOperationStep {

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		// Get the pre-fetched record.
		Hashtable record = HalbanatHonUtil.getDecisionTableRecord(getContext());
		String imutMalam = (String)record.get("HA_SW_IMUT_MALAM");
		String kesherMalam = (String)getValueAt("HASR_MIZDAMEN_PRATIM.HA_KESHER_LE_MALAM");
		
		if ((imutMalam==null) || (kesherMalam==null))
			return RC_ERROR;
		if (imutMalam.equals("0")) {
			return RC_OK;
		} else if (imutMalam.equals("1") && (kesherMalam.equals("0"))) {
			return 2;
		}
		
		return RC_OK;
	}

}
