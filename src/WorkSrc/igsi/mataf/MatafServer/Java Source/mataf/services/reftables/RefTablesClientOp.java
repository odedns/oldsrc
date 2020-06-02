package mataf.services.reftables;

import java.io.IOException;

import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;
import mataf.utils.*;
import mataf.logger.*;
import mataf.operations.general.*;

/**
 * @author Oded Nissan 24/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class RefTablesClientOp extends BasicClientOp {

	/**
	 * Constructor for RefTablesClientOp.
	 */
	public RefTablesClientOp() {
		super();
	}

	/**
	 * Constructor for RefTablesClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public RefTablesClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for RefTablesClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public RefTablesClientOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for RefTablesClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public RefTablesClientOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}

	public void execute() throws Exception
	{
		GLogger.debug("in RefTablesClientOp,execute()");
		// CSClientService cs = (CSClientService) Context.getCSClient();	
		sendReceive(getContext());
		
		
	}

}
