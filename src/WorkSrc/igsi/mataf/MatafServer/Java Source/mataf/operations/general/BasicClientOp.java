package mataf.operations.general;


import java.io.IOException;

import mataf.utils.*;
import com.ibm.dse.base.*;

/**
 * @author Oded Nissan 13/07/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BasicClientOp extends DSEClientOperation {

	static long DEFAULT_TIMEOUT = 4000;
	/**
	 * Constructor for BasicClientOp.
	 */
	public BasicClientOp() {
		super();
	}

	/**
	 * Constructor for BasicClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public BasicClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for BasicClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public BasicClientOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for BasicClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public BasicClientOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	
	/**
	 * Execute the operation.
	 * Transfer data to the server operation.
	 * Executes genericClientOp which tranfers the context data to
	 * the server.
	 * @throws Exception in case of error.
	 */
	public void sendReceive() throws Exception
	{		
		sendReceive(DEFAULT_TIMEOUT);
	}
	/**
	 * Execute the operation.
	 * Transfer data to the server operation.
	 * Executes genericClientOp which tranfers the context data to
	 * the server.
	 * @throws Exception in case of error.
	 */
	public void sendReceive(long timeout) throws Exception
	{				
		Context ctx = getContext();
		sendReceive(timeout,ctx);
	}

	/**
	 * Execute the operation.
	 * Transfer data to the server operation.
	 * Executes genericClientOp which tranfers the context data to
	 * the server.
	 * @throws Exception in case of error.
	 */
	public void sendReceive(long timeout, Context context) throws Exception
	{		
		GenericClientOp op = (GenericClientOp) DSEOperation.readObject("genericClientOp");
        Context ctx = context;
        op.setOpData(getServerOperation(), ctx.getName(),ctx);
        op.setTimeout(timeout);
        op.execute();       		
		ctx = op.getCtxData();
		op.close();
		setContext(ctx);		
	}

	/**
	 * Execute the operation.
	 * Transfer data to the server operation.
	 * Executes genericClientOp which tranfers the context data to
	 * the server.
	 * @throws Exception in case of error.
	 */
	public void sendReceive(Context context) throws Exception
	{		
		sendReceive(DEFAULT_TIMEOUT,context);
	}
	
	
	/**
	 * set an error to be fired to the view 
	 * as an OperationRepliedEvent.
	 * @param msg the error message.
	 */
	public void setError(String msg)
	{
		OperationRepliedEvent ev = new OperationRepliedEvent(this);
		Hashtable ht = new Hashtable();
		ht.put("errMessage",msg);
		ev.setName("error");
		ev.setParameters(ht);						
		fireHandleOperationRepliedEvent(ev);	
	}

}
