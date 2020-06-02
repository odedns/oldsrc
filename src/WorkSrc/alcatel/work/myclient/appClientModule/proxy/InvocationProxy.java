package proxy;

import java.lang.reflect.*;

import onjlib.ejb.invoker.*;




/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvocationProxy
{

	

	public static Object getProxyObject(Object o, Class c)
	{
		InvocationHandler handler = new ProxyHandler(o);
		Object proxyObj = Proxy.newProxyInstance(c.getClassLoader(),
												  new Class[] { c },
												  handler);
                                          
		return(proxyObj);
	}
	
	
	public static void main(String argv[])
	{
		MyBpo bpo = new MyBpo();
		
		MyBpoIF bpoIf = (MyBpoIF) getProxyObject(bpo, MyBpoIF.class);
		String s = bpoIf.find(new Integer(100));
		System.out.println("got result= " + s.toString());
		
		
	}
}
