/* Created on 07/09/2006 */
package hoshen.xsm.lightsoft;

import hoshen.xsm.lightsoft.corba.CosNaming.NamingContextExt;
import hoshen.xsm.lightsoft.corba.CosNaming.NamingContextExtHelper;

import java.util.Properties;

import org.omg.CORBA.ORB;

/**
 * 
 * @author odedn
 */
public class GenerateIOR {
	public static void main(String[] args) {
		Properties p = new Properties();
		p.put( "org.omg.CORBA.ORBClass", "com.ibm.CORBA.iiop.ORB" );
		p.put( "com.ibm.CORBA.Debug", "1" );
		p.put( "com.ibm.CORBA.CommTrace", "1" );
		p.put( "com.ibm.CORBA.ORBInitRef.NameService", "corbaloc:iiop:localhost:9100/NameServiceCellPersistentRoot");
		

		try {
			ORB orb = ORB.init( ( String[] ) null, p );
			org.omg.CORBA.Object obj = orb.resolve_initial_references( "NameService" );
			NamingContextExt initCtx = NamingContextExtHelper.narrow( obj );
			System.out.println( orb.object_to_string(initCtx) );
		} catch(Exception e) {
			
			e.printStackTrace();
		}
			
	}	
}
