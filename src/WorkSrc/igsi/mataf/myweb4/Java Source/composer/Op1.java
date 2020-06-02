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
public class Op1 extends DSEOperation {

	/**
	 * Constructor for Op1.
	 */
	public Op1() {
		super();
	}

	/**
	 * Constructor for Op1.
	 * @param arg0
	 * @throws IOException
	 */
	public Op1(String arg0) throws IOException {
		super(arg0);
	}

	/**
	 * Constructor for Op1.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 */
	public Op1(String arg0, Context arg1)
		throws IOException, DSEInvalidRequestException {
		super(arg0, arg1);
	}

	/**
	 * Constructor for Op1.
	 * @param arg0
	 * @param arg1
	 * @throws IOException
	 * @throws DSEInvalidRequestException
	 * @throws DSEObjectNotFoundException
	 */
	public Op1(String arg0, String arg1)
		throws IOException, DSEInvalidRequestException, DSEObjectNotFoundException {
		super(arg0, arg1);
	}
	public void execute() throws Exception
	{
		super.execute();
		System.out.println("in Op1.execute()");
		setValueAt("Name","op1-name");
		setValueAt("MaxAmount","99");
		OperationRepliedEvent event = new OperationRepliedEvent(this);
		
		Hashtable ht = new Hashtable();
		ht.put("dse_exitEventName","foo");
		event.setParameters(ht);
		
		fireHandleOperationRepliedEvent(event);

	}

}
