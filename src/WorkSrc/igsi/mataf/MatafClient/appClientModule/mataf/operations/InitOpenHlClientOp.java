package mataf.operations;

import mataf.services.proxy.ProxyService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.clientserver.CSClientService;
//import com.ibm.dse.services.appltables.*;
/**
 * This operation is invoked after initialization to create the
 * client workstation context in the server workstation. 
 * This is the client side.
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 */
public class InitOpenHlClientOp extends DSEClientOperation 
{
	/**
	 * StartupClientOp default constructor.
	 */
	public InitOpenHlClientOp() 
	{
		super();
	}

	public InitOpenHlClientOp(String anOperationName)
		throws java.io.IOException {
		super(anOperationName);
	}

	public InitOpenHlClientOp(String anOperationName, String aParentContext)
		throws
			java.io.IOException,
			DSEInvalidRequestException,
			DSEObjectNotFoundException {
		super(anOperationName, aParentContext);
	}

	public InitOpenHlClientOp(String anOperationName, Context aParentContext)
		throws java.io.IOException, DSEInvalidRequestException 
	{
		super(anOperationName, aParentContext);
	}

	public void execute() throws Exception 
	{
//		MonitorService monitor = (MonitorService)getService("Monitor");
//		setValueAt("","")
//		setValueAt("chosenMarechetText","הלוואה מכספי הבנק");
/*		ApplicationTablesService appTableService = (ApplicationTablesService) getService("appTablesService");
		appTableService.		*/
		CSClientService csClientService = null;
		csClientService = (CSClientService) getService("CSClient"); //$NON-NLS-1$	
		csClientService.sendAndWait(this, 40000);
		
		testRC();
	}
	
	private void testRC()
	{
		System.out.println("TestRC()");
		try 
		{
			ProxyService ps = (ProxyService)getService("proxyService");
			ps.activateTransaction("T001");
		} 
		catch (Exception e) {e.printStackTrace();}
	}
}
