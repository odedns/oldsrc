package composer;

import java.io.IOException;

import com.ibm.dse.base.*;
import com.ibm.dse.cs.servlet.CSServer;

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
/*		
		MyNotifier notifier = new MyNotifier();
		getContext().addNotifier(notifier,"MyNotifier","cs");	
		
		*/
		Context ctx = Context.getContextNamed("branchServer");
		System.out.println("branchServer= " + ctx.toString());
		MyNotifier notifier = (MyNotifier) ctx.getNotifier("MyNotifier");
		for(int i=0; i < 5; ++i) {
			notifier.sendEvent("Some fucking event");
		}
		Hashtable ht = notifier.getHandlersList();
		System.out.println("ServerOp.execute() : handlers ht = " +ht.toString());
		
		String id = (String) getValueAt("Name");
		System.out.println("got Name = " + id);
		System.out.println("ctx = " + getContext().toString());
		setValueAt("Name","srv100");
		
		
		fireHandleOperationRepliedEvent(new OperationRepliedEvent(this));
	}

}
