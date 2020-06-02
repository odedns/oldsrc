/*
 * Created on 26/10/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tests;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.transaction.UserTransaction;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JTATest {

	public static void main(String argv[]){
		
//		 Get the initial context
		java.util.Properties p = new java.util.Properties();
		p.put(Context.PROVIDER_URL, "IIOP://localhost:2809");
		p.put(Context.INITIAL_CONTEXT_FACTORY, "com.ibm.websphere.naming.WsnInitialContextFactory");
		
		try  {
			
		
			InitialContext initContext = new InitialContext(p);
			//		 Look up a transaction
			UserTransaction userTran = (UserTransaction)initContext.lookup("jta/usertransaction");
			System.out.println("got userTranaction object ..");
			userTran.begin();
			//		 Call the finder
	
			userTran.commit();
			System.out.println("done ...");
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	
}
