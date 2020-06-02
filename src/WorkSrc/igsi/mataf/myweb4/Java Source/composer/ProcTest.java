package composer;

import com.ibm.dse.automaton.*;
import com.ibm.dse.automaton.ext.DSEOperationProcessor;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProcTest {

	public static void init() throws Exception 
	{

		Context.reset();
//		Settings.reset("d:/work/dse/server/dse.ini");
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
				
	}
	static void runSrvOp() throws Exception
	{
		DSEOperation op =  (DSEOperation) DSEOperation.readObject("op2");	
		op.execute();
		op.close();

		
	}
	public static void main(String[] args) {
		try {
			init();
			//runSrvOp();
			System.out.println("ProcTest ..");
			DSEProcessor proc = (DSEProcessor)DSEProcessor.readObject("myproc");
			Context ctx = proc.getContext();
			System.out.println("proc = " + proc.toString());
			// System.out.println("ctx = " + ctx.toString());						

			State st = proc.getCurrentState();
			System.out.println("state = " + st);
			
			System.out.println("proc.execute()");
			proc.execute();
			System.out.println("after proc.execute()");
			proc.close();
			System.out.println("proc lastState = " + ((MyProcessor)proc).getLastState());
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
