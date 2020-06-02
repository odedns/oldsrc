package tests;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.rmi.RemoteException;
import java.util.*;





/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class JndiTest {


	public static void foo() throws Exception
	{
  		//String url       = "iiop://localhost:2809";
		String url= "corbaloc:iiop:localhost:2809/NameServiceServerRoot";
  		String factory = "com.ibm.websphere.naming.WsnInitialContextFactory";
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,factory);
		prop.put(Context.PROVIDER_URL, url);
		/*
		prop.put(Context.SECURITY_PRINCIPAL,"odedn");
		prop.put(Context.SECURITY_CREDENTIALS,"odedn01");	
		*/
 		Context ctx          = new InitialContext(prop);
 		NamingEnumeration ne = ctx.listBindings("");
 		while(ne.hasMoreElements()) {
 			Binding b = (Binding) ne.next(); 			
 			System.out.println(b.getName() + "=\t" + b.getClassName());	
 			
 		}
 		
 		
 		ctx.bind("test", new String("test"));
 		String s = (String) ctx.lookup("test");
 		System.out.println("jndi got: " + s);
 		ctx.unbind("test");
 		
		System.out.println("\nEnd Mytest session.Client...\n");
 
 	}


	public static void main(String argv[])
	{
		System.out.println("in JNDITest");
		try {
			foo();
		} catch(Exception e) {
			e.printStackTrace();	
		}
			
	}
}
