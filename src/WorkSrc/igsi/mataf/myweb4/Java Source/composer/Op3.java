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
public class Op3 extends DSEOperation {

	/**
	 * Constructor for Op3.
	 */
	public Op3() {
		super();
	}

	/**
	 * Constructor for Op3.
	 * @param arg0
	 * @throws IOException
	 */
	public Op3(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for Op3.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public Op3(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for Op3.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public Op3(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	public void execute() throws Exception
	{
		super.execute();
		System.out.println("in Op3.execute()");
		setValueAt("Name","op3-name");
		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));

	}

}
