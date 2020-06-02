package tests;

import java.io.IOException;

import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ServerOp extends DSEServerOperation {

	/**
	 * Constructor for ServerOp.
	 */
	public ServerOp() {
		super();
	}

	/**
	 * Constructor for ServerOp.
	 * @param arg0
	 * @throws IOException
	 */
	public ServerOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for ServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public ServerOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for ServerOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public ServerOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	public void execute() throws Exception
	{
		System.out.println("In ServerOp execute() ");
		String id = (String) getValueAt("GLOBAL_RECORDS.GLSE_GLBL.GKSE_KEY.GL_SNIF");
		System.out.println("got GL_SNIF = " + id);
		System.out.println("ctx = " + getContext().toString());
		setValueAt("GLOBAL_RECORDS.GLSE_GLBL.GL_LAST_NAME","my_last_name");
		setValueAt("GLOBAL_RECORDS.GLSE_GLBL.GKSE_KEY.GL_SNIF",id + "-server");
		
		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
	}

}
