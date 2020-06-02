package tests;

import hoshen.ejb.dynproxy.ServiceProxyFactory;





/**
 * @author Oded Nissan
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvocationProxy
{

	

	
	public static void main(String argv[])
	{
		
		try {
			MyBpoIF bpoIf = (MyBpoIF) ServiceProxyFactory.getService("mybpoif");
			String s = bpoIf.find(new Integer(100));
			System.out.println("got result= " + s.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
