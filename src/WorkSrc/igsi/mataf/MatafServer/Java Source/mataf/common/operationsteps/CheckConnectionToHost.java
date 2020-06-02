/*
 * Created on 17/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.common.operationsteps;

import com.ibm.dse.base.OperationStep;
import com.ibm.dse.services.comms.CommonCommunicationsService;
import com.ibm.dse.services.comms.CommunicationsPoolService;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckConnectionToHost extends OperationStep {
	private Boolean connectionToHost;
	private CommunicationsPoolService pool;
	private CommonCommunicationsService service;
	/**
	 * 
	 */
	public CheckConnectionToHost() {
		super();
		
	}

	/* (non-Javadoc)
	 * @see com.ibm.dse.base.OperationStepInterface#execute()
	 */
	public int execute() throws Exception {
//		get reference to communication service from context 
		pool = (CommunicationsPoolService) getService("pool");
		service = (CommonCommunicationsService) pool.getPoolService();
		
		if (service == null){
			setValueAt("returnCode", String.valueOf(RC_ERROR));
			return RC_ERROR;
		}
		 pool.releasePoolService(service);
		 setValueAt("returnCode", String.valueOf(RC_OK));
		return RC_OK;
	}

	public Boolean getConnectionToHost() {
		return connectionToHost;
	}

	public void setConnectionToHost(Boolean boolean1) {
		connectionToHost = boolean1;
	}

}
