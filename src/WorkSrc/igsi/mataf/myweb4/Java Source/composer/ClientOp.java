package composer;

import java.io.IOException;

import com.ibm.dse.base.*;
import com.ibm.dse.clientserver.*;
import composer.utils.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ClientOp extends BasicClientOp {

	/**
	 * Constructor for ClientOp.
	 */
	public ClientOp() {
		super();
	}

	/**
	 * Constructor for ClientOp.
	 * @param arg0
	 * @throws IOException
	 */
	public ClientOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for ClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public ClientOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for ClientOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public ClientOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	public void execute() throws Exception
	{
		System.out.println("in ClientOp Execute");
		Context ctx = (Context) Context.getContextNamed("workstationCtx");
		CSClientService cs = (CSClientService) ctx.getService("CSClient");		
//		CSClientService cs = (CSClientService) Context.getCSClient();	
		EventManager.registerInterestInRemoteEvent("allEvents","MyNotifier");
		MyHandler handler = new MyHandler();
		handler.handleEvent("allEvents","MyNotifier",getContext());
	
		setValueAt("UserId", "100");
		setValueAt("Name", "oded");
		// call the sendReceive in BasicClientOp.
		sendReceive();		
		String id = (String) getValueAt("Name");
		System.out.println("after serverOp Name =" + id);
								
	
	}
	

}
