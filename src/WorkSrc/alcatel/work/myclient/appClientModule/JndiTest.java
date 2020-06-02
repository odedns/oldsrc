import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;
import javax.sql.*;
import java.sql.*;

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
  		String url       = "iiop://localhost:2809";
  		String factory = "com.ibm.websphere.naming.WsnInitialContextFactory";
		
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,factory);
		prop.put(Context.PROVIDER_URL, url);
		//prop.put("com.ibm.websphere.naming.namespace.connection", "eager");
     
 		Context ctx          = new InitialContext(prop);
 		ctx.bind("test", new String("test"));
 		String s = (String) ctx.lookup("test");
 		System.out.println("got from JNDI : " + s);
 		ctx.unbind("test");
		Object o = ctx.lookup("jdbc/mydb");
		DataSource sd = (DataSource) javax.rmi.PortableRemoteObject.narrow(ctx.lookup("jdbc/mydb"), DataSource.class); 
		System.out.println(sd);
		System.out.println("got my db = " + o.getClass().getName());
		DataSource ds = (DataSource) o;
		Connection conn = ds.getConnection();
		System.out.println(conn);
		conn.close();
		System.out.println("\nEnd JndiTest...\n");
		
 
 
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
