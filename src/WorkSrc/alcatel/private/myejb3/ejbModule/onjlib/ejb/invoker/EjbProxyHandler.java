/*
 * Created on 27/09/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package onjlib.ejb.invoker;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EjbProxyHandler extends AbstractProxyHandler {

	private static final String URL       = "iiop://localhost:2809";
	private static final String FACTORY = "com.ibm.websphere.naming.WsnInitialContextFactory";

	
	/**
	 * Invoke the EJB on the server side. 
	 * @param className the name of the implementation class
	 * @param methodName the name of the method to execute/
	 * @param args ArrayList of params.
	 * @param argsHandler the arguments handler to use for packing and unpacking
	 * args.
	 * @return the retrun value.
	 * @throws Exception in case of error.
	 */
	public Object invokeServer(String className, String methodName,
						ArrayList args,String argsHandler) throws Exception					    						
	{
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,FACTORY);
		prop.put(Context.PROVIDER_URL, URL);
		InitialContext ctx          = new InitialContext(prop);
		InvokerRemote invoker = (InvokerRemote) ctx.lookup("ejb/hoshen/ejb/dynproxy/InvokerHome");
		System.out.println("got invoker invoking class=" + className + "\tmethod = " + methodName);
		Object o = invoker.invoke(className,methodName,args,argsHandler);												
		return(o);
	}


}
