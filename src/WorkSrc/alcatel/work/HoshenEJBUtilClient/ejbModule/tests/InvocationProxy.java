package tests;

import hoshen.ejb.dynproxy.ServiceProxyFactory;





/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvocationProxy
{

	

	
	public static void main(String argv[])
	{
		/*
		MyBpo bpo = new MyBpo();
		
		MyBpoIF bpoIf = (MyBpoIF) getProxyObject(MyBpo.class,MyBpoIF.class);
		*/
		try {
			MyBpoIF bpoIf = (MyBpoIF) ServiceProxyFactory.getService("mybpoif");
			String s = bpoIf.find(new Integer(100));
			System.out.println("got result= " + s.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
