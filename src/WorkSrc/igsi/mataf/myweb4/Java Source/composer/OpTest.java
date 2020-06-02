package composer;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpTest {

	/**
	 * Constructor for OpTest.
	 */
	public OpTest() {
		super();
	}
	public static void init() throws Exception 
	{

		Context.reset();
//		Settings.reset("d:/work/dse/server/dse.ini");
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		
		
	}
	

	public static void main(String argv[])
	{
		try {
			System.out.println("Begin OpTest");
			init();
			Context ctx = (Context) Context.readObject("myCtx");	
			DSEOperation cop = (DSEOperation) DSEOperation.readObject("fooOp");
			cop.setContext(ctx);
			cop.execute();
			cop.close();
			System.out.println("end OpTest");
		}catch(Exception e) {
			e.printStackTrace();	
		}

		
	}

}
