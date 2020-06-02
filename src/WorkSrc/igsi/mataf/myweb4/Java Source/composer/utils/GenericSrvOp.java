package composer.utils;

import java.io.IOException;

import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;

import composer.utils.*;

/**
 * @author Oded Nissan 13/07/2003
 *
 * A Generic server operation.
 * The operation unformats the generic context and executes
 * the requested operation with the received context data.
 */
public class GenericSrvOp extends DSEServerOperation {

	/**
	 * Constructor for GenericSrvOp.
	 */
	public GenericSrvOp() {
		super();
	}

	/**
	 * Constructor for GenericSrvOp.
	 * @param arg0
	 * @throws IOException
	 */
	public GenericSrvOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for GenericSrvOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public GenericSrvOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for GenericSrvOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public GenericSrvOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	/**
	 * execute the operation.
	 * Get the operation name to execute and its context name.
	 * Deserialize the context data and execute the operation name
	 * passed in opName, assign the deserialized context to the 
	 * operation.
	 * After the operation executes get the updated context, 
	 * serialize it and pass it back to GenericClientOp.
	 */
	public void execute() throws Exception
	{
		GLogger.debug("in GenericSrvOp Execute");
		String opName = (String) getValueAt("opName");
		GLogger.debug("opName =" + opName);		
		String data = (String) getValueAt("ctxData");
		GLogger.debug("data =" + data);		
		String ctxName = (String) getValueAt("ctxName");
		GLogger.debug("ctxName =" + ctxName);		
		Context ctx = ContextFormatter.unformatContext(data,ctxName);
		DSEServerOperation op = (DSEServerOperation) DSEOperation.readObject(opName);
		op.setContext(ctx);
		op.execute();
		ctx = op.getContext();
		op.close();
		data = ContextFormatter.formatContext(ctx);
		setValueAt("ctxData",data);
	}
	

}
