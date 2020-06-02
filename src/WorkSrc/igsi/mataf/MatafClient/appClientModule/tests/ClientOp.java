package tests;

import java.io.IOException;

import mataf.operations.general.BasicClientOp;
import mataf.services.printer.PrinterClientOp;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;

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
//		System.out.println("in ClientOp Execute");
//		CSClientService cs = (CSClientService) getService("CSClient");			
//		setValueAt("GLSE_GLBL.GKSE_KEY.GL_SNIF","Shoken");	
//		sendReceive();		
//		String id = (String) getValueAt("GLOBAL_RECORDS.GLSE_GLBL.GKSE_KEY.GL_SNIF");
//		System.out.println("after serverOp Name =" + id);
		DSEClientOperation op = new  	PrinterClientOp();
		op.execute();						
	
	}
	

}
