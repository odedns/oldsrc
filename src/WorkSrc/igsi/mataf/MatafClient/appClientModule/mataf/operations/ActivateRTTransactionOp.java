package mataf.operations;

import java.io.IOException;

import mataf.logger.GLogger;
import mataf.services.proxy.ProxyService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (23/06/2003 12:44:24).  
 */
public class ActivateRTTransactionOp extends DSEClientOperation 
{
	/**
	 * Constructor for ActivateRTTransaction.
	 */
	public ActivateRTTransactionOp() {
		super();
	}

	/**
	 * Constructor for ActivateRTTransaction.
	 * @param arg0
	 * @throws IOException
	 */
	public ActivateRTTransactionOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for ActivateRTTransaction.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public ActivateRTTransactionOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for ActivateRTTransaction.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public ActivateRTTransactionOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}

	/**
	 * @see com.ibm.dse.base.Operation#execute()
	 */
	public void execute(String transactionName) throws Exception 
	{
		try
		{
			ProxyService ps = (ProxyService)getService("proxyService");
			ps.activateTransaction(transactionName);
			close();
		}
		catch (Exception e) {e.printStackTrace();}
	}
}
