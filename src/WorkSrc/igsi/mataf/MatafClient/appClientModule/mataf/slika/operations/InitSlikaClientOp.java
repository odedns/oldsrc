package mataf.slika.operations;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InitSlikaClientOp extends DSEClientOperation {

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute() throws Exception {
		Context ctx = getContext();
		ctx.chainToContextNamed("slikaCtx");
		CSClientService aCSClientService = (CSClientService)getService("CSClient");
		aCSClientService.sendAndWait(this, 600000);
		setinitParams();
		ctx.unchain();
	}
	
	private void setinitParams() throws DSEInvalidArgumentException, DSEObjectNotFoundException {
		try {
			// set kod peula of slika
			setValueAt("GLSF_MAZAV.GKSG_PEULA.GL_KOD_PEULA", "410");
			
			// set default value of branch
			String branchId = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
			setValueAt("BranchIdInput", branchId);
			
			// set GLSG_GLBL.GL_TR_ASAKIM to display
			SimpleDateFormat sourcePattern = new SimpleDateFormat("ddmmyyyy");
			SimpleDateFormat targetPattern = new SimpleDateFormat("dd/mm/yyyy");
			String GL_TR_ASAKIM = (String) getValueAt("GLSG_GLBL.GL_TR_ASAKIM");
			Date date = sourcePattern.parse(GL_TR_ASAKIM);
			String formatedDate = targetPattern.format(date);
			setValueAt("trAsakimFormated", formatedDate);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}
