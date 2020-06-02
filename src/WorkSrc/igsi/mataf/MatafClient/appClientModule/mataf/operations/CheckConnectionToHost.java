/*
 * Created on 18/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mataf.operations;

import java.io.IOException;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.clientserver.CSClientService;

/**
 * @author eyalbz
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CheckConnectionToHost extends DSEClientOperation {

	public CheckConnectionToHost() {
		super();
		
	}

	public CheckConnectionToHost(String anOperationName) throws IOException {
		super(anOperationName);
		
	}

	public CheckConnectionToHost(String anOperationName, Context aParentContext) throws IOException, DSEInvalidRequestException {
		super(anOperationName, aParentContext);
		
	}

	public CheckConnectionToHost(String anOperationName, String aParentContext)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
		
	}
	
	public void execute() throws Exception {
		super.execute();
		CSClientService aCSClientService = (CSClientService)getService("CSClient");
		aCSClientService.sendAndWait(this, 600000);
	}


}
