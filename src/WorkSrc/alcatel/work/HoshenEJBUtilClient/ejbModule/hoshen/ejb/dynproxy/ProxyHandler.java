package hoshen.ejb.dynproxy;

import java.lang.reflect.*;
import java.util.ArrayList;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ProxyHandler implements InvocationHandler
{

private Class realClass;

		ProxyHandler(Class c) 		
		{			
			realClass = c;
		}
		
		public Object invoke(Object proxy, Method method,
					 Object[] args)
			  throws Throwable
		{
			System.out.println("in invoke");			
			//method.invoke(realObj,args);
			
			ArrayList list = new ArrayList();
			for(int i=0; i< args.length; ++i) {
				list.add(args[i]);
			}
			Object o = EjbInvoker.invokeEjb(realClass, method.getName(),list);
			System.out.println("after invoke..");
			return(o);
       	
		}
	
}
