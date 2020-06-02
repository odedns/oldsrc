package onjlib.ejb.invoker;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * Bean implementation class for Enterprise Bean: Invoker
 */
public class InvokerBean implements javax.ejb.SessionBean
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
	
	private Class[] convertArgs(ArrayList args)
	{
		Class vc[] = new Class[args.size()];		
		for(int i=0; i < args.size(); ++i){
			Object o = args.get(i);
			vc[i] = o.getClass();
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
	public Object invoke(Class c,String methodName, ArrayList args)
		throws Exception
	{
		System.out.println("executing method on class :" + c.getName());
		Class vc[] = convertArgs(args);
		Object vo[] = args.toArray();
		Object o = null;
		try {
		
			Method m  = c.getMethod(methodName,vc);
			Object obj = c.newInstance();		
			o = m.invoke(obj,vo);
		} catch(Exception e) {
			throw new Exception("Error in invoke",e);
		}
		System.out.println("returning o = " + o.toString());
		return(o);
	}
	
}
