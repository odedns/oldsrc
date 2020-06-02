package mataf.operations.general;

import java.io.IOException;

import mataf.utils.ContextFormatter;
import mataf.utils.GLogger;


import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;

/**
 * @author Oded Nissan 13/07/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class GenericClientOp extends DSEClientOperation {

	Context m_ctx = null;
	long m_timeout = 40000;
	/**
	 * Constructor for GenericClientOp.
	 */
	public GenericClientOp() {
		super();
	}

	/**
	 * Constructor for GenericClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public GenericClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for GenericClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public GenericClientOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for GenericClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public GenericClientOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	/**
	 * set the operation name and data to access on the 
	 * server side.
	 * @param opName - the server operation to execute.
	 * @param ctxName - the name of the context of the server operation.
	 * @param ctxData - the context data to be passed to the
	 * server operation.
	 */
	public void setOpData(String opName, String ctxName, Context ctx) 
		throws Exception
	{
		setValueAt("opName", opName);
		setValueAt("ctxName", ctxName);		
		String ctxData = ContextFormatter.formatClientContext(ctx);
		setValueAt("ctxData",ctxData);
		
	}
	
	
	/**
	 * return the desrialized context to the calling
	 * operation.
	 * @param return Context the returned context.
	 */
	public Context getCtxData()
	{
		return(m_ctx);	
	}
	
	/**
	 * set the timeout to use when calling
	 * sendAndWait.
	 * @param timeout long the timeout to use.
	 */
	public void setTimeout(long timeout)
	{
		m_timeout = timeout;
	}
	/**
	 * Execute the operation.
	 * Transfer data to the server operation.
	 * @throws Exception in case of error.
	 */
	public void execute() throws Exception
	{
		GLogger.debug("in GenericClientOp Execute");
		CSClientService cs = (CSClientService) getService("CSClient");
		cs.sendAndWait(this,m_timeout);	
		/**
		 * get the context servilized data from the server
		 * and deserialize it.
		 */
		String data = (String) getValueAt("ctxData");		
		GLogger.debug("got data = " + data);
		String ctxName = (String) getValueAt("ctxName");
		m_ctx = ContextFormatter.unformatContext(data,ctxName);
		
		
	}
	
}
