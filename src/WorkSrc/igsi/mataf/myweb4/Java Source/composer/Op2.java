package composer;

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
public class Op2 extends DSEOperation {

	/**
	 * Constructor for Op2.
	 */
	public Op2() {
		super();
	}

	/**
	 * Constructor for Op2.
	 * @param arg0
	 * @throws IOException
	 */
	public Op2(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for Op2.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public Op2(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for Op2.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public Op2(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	public void execute() throws Exception
	{
		super.execute();
		System.out.println("in Op2.execute()");
		setValueAt("Name","op2-name");
		setValueAt("Balance", "1000");
		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));

	}

}
