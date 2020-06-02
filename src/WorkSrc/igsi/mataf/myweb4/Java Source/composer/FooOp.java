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
public class FooOp extends DSEOperation {

	/**
	 * Constructor for FooOp.
	 */
	public FooOp() {
		super();
	}

	/**
	 * Constructor for FooOp.
	 * @param arg0
	 * @throws IOException
	 */
	public FooOp(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for FooOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public FooOp(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for FooOp.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public FooOp(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	
	public void execute() throws Exception
	{
		super.execute();
		System.out.println("in FooOp.execute()");
		setValueAt("Name","fooOp-name");
		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
		//throw new Exception("exception in op1");
	}

}
