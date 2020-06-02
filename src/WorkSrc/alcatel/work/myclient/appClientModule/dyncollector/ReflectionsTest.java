/**
 * Created on 09/01/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package dyncollector;

import java.lang.reflect.*;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ReflectionsTest
{

	public static void main(String args[]) 
	{
		
		System.out.println("ReflectionsTest");
		try {
			String s = new String("Ffff");
			Integer i = new Integer(1);
			
			Class c[] = new Class[] { Integer.TYPE	};
			Method m = s.getClass().getMethod("charAt",c );
			Object arg[] = new Object[1];
			arg[0] = i;
			Object o = m.invoke(s,arg);
			System.out.println("o = " + o.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	

}
