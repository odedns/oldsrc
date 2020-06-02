package onjlib.ejb.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @author Oded Nissan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractProxyHandler implements InvocationHandler
{

private String realClassName;
private String m_argsHandler;
		/**
		 * default empty constructor
		 *
		 */
		public AbstractProxyHandler()
		{
			
		}
		
		
		public Object invoke(Object proxy, Method method,
					 Object[] args)
			  throws Throwable
		{
			System.out.println("in invoke");
			ArgsHandlerIF handler = null;
			
			/*
			 * if there is an argsHandler
			 * call it to pack the args.
			 */
	        if(m_argsHandler != null) {
	            try {
	                handler = (ArgsHandlerIF)Class.forName(m_argsHandler).newInstance();
	                args = (Object[]) handler.packArgs(args);
	            } catch(Exception e)  {
	                e.printStackTrace();
	            }
	        }
	       
	        System.out.println("converting array to list");
			ArrayList list = new ArrayList();
			for(int i=0; i< args.length; ++i) {
				list.add(args[i]);
			}
			
			Object o = invokeServer(realClassName, method.getName(),list,m_argsHandler);
			System.out.println("after invoke..");
			if(handler != null) {
				o = handler.unpackArgs(o);
			}
			return(o);
       	
		}
		
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
		public abstract Object invokeServer(String className, String methodName,
							ArrayList args,String argsHandler) throws Exception;					    						
		

	
		/**
		 * 	@return Returns the m_argsHandler.
		 */
		public String getArgsHandler() {
			return m_argsHandler;
		}
		
		/**
		 * @param handler The m_argsHandler to set.
		 */
		public void setArgsHandler(String handler) {
			m_argsHandler = handler;
		}
		/**
		 * @return Returns the realClassName.
		 */
		public String getRealClassName() {
			return realClassName;
		}
		/**
		 * @param realClassName The realClassName to set.
		 */
		public void setRealClassName(String realClassName) {
			this.realClassName = realClassName;
		}
}
