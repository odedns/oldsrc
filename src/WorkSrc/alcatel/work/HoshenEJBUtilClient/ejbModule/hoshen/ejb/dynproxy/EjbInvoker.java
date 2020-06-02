package hoshen.ejb.dynproxy;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;



/**
 * @author Oded Nissan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EjbInvoker
{
	private static final String URL       = "iiop://localhost:2809";
	private static final String FACTORY = "com.ibm.websphere.naming.WsnInitialContextFactory";
	
	
	public static Object invokeEjb(Class c, String methodName,
						ArrayList args) throws Exception
	{
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,FACTORY);
		prop.put(Context.PROVIDER_URL, URL);
		InitialContext ctx          = new InitialContext(prop);
		InvokerHome invokerHome = (InvokerHome) ctx.lookup("ejb/hoshen/ejb/dynproxy/InvokerHome");
		Invoker invoker = invokerHome.create();
		System.out.println("got invoker invoking class=" + c.getName() + "\tmethod = " + methodName);
		Object o = invoker.invoke(c,methodName,args);
												
		return(o);
	}


}
