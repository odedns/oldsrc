package onjlib.ejb.invoker;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.ejb.Stateless;

@Stateless
/**
 * Bean implementation class for Enterprise Bean: Invoker
 */
public class InvokerBean implements Invoker
{
	private javax.ejb.SessionContext mySessionCtx;
	/**
	 * getSessionContext
	 */
	public javax.ejb.SessionContext getSessionContext()
	{
		return mySessionCtx;
	}
	/**
	 * setSessionContext
	 */
	public void setSessionContext(javax.ejb.SessionContext ctx)
	{
		mySessionCtx = ctx;
	}
	/**
	 * ejbCreate
	 */
	public void ejbCreate() throws javax.ejb.CreateException
	{
	}
	/**
	 * ejbActivate
	 */
	public void ejbActivate()
	{
	}
	/**
	 * ejbPassivate
	 */
	public void ejbPassivate()
	{
	}
	/**
	 * ejbRemove
	 */
	public void ejbRemove()
	{
	}
	
	private Class[] convertArgs(Object args[])
	{
		Class vc[] = new Class[args.length];		
		for(int i=0; i < args.length; ++i){
			vc[i] = args[i].getClass();
		}
		return(vc);				
	}
	
	
	/**
	 * invoke a method on the specific object.
	 * @param c
	 * @param methodName
	 * @param args
	 * @return
	 */
	public Object invoke(String className,String methodName, ArrayList argsList, String argsHandler)
	throws Exception
{
	
	
	System.out.println("executing method on class :" + className);
	ArgsHandlerIF handler = null;
	Object args[] = null;
	/*
	 * if we have an arguments handler
	 * call the handler to unpack the args.
	 */
	if(null != argsHandler){ 
		try {
			args = argsList.toArray();
			handler = (ArgsHandlerIF) Class.forName(argsHandler).newInstance();
			args = (Object[]) handler.unpackArgs(args);
		} catch(Exception e) {
			e.printStackTrace();				
		}
	}
	System.out.println("converted list to array");		
	Class vc[] = convertArgs(args);
	
	Object o = null;
	try {
		Class c = Class.forName(className);
		Method m  = c.getMethod(methodName,vc);
		Object obj = c.newInstance();		
		o = m.invoke(obj,args);
	} catch(Exception e) {
		e.printStackTrace();
		throw new Exception("Error in invoke",e);
	}
	if(handler != null) {
		o = handler.packArgs(o);
	}
	System.out.println("returning o = " + o.toString());
	return(o);
}

	
}
