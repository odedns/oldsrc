/*
 * Created on 29/03/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


package test;

import java.util.Properties;

import org.omg.CORBA.ORB;
import hoshen.xsm.lightsoft.corba.CosNaming.Binding;
import hoshen.xsm.lightsoft.corba.CosNaming.BindingHolder;
import hoshen.xsm.lightsoft.corba.CosNaming.BindingIteratorHolder;
import hoshen.xsm.lightsoft.corba.CosNaming.BindingListHolder;
import hoshen.xsm.lightsoft.corba.CosNaming.NameComponent;
import hoshen.xsm.lightsoft.corba.CosNaming.NamingContext;
import hoshen.xsm.lightsoft.corba.CosNaming.NamingContextExt;
import hoshen.xsm.lightsoft.corba.CosNaming.NamingContextExtHelper;
import hoshen.xsm.lightsoft.corba.CosNaming.NamingContextHelper;

/**
 * @author alex
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestORB {

	public static void main(String[] args) {
		System.out.println( System.getProperty( "java.fullversion" ) );
		
		Properties p = new Properties();
		p.put( "org.omg.CORBA.ORBClass", "com.ibm.CORBA.iiop.ORB" );
		p.put( "com.ibm.CORBA.Debug", "1" );
		p.put( "com.ibm.CORBA.CommTrace", "1" );
		p.put( "com.ibm.CORBA.ORBInitRef.NameService", "corbaloc:iiop:hsn-hsn-02-190r:9200/NameServiceCellPersistentRoot");
		//p.put( "com.ibm.CORBA.ORBInitRef.NameServiceServerRoot", "corbaloc:iiop:localhost:9100/NameServiceServerRoot" );


		try {
			ORB orb = ORB.init( ( String[] ) null, p );
			org.omg.CORBA.Object obj = orb.resolve_initial_references( "NameService" );
			NamingContextExt initCtx = NamingContextExtHelper.narrow( obj );
			System.out.println( orb.object_to_string(initCtx) );
			//initCtx.destroy();
			listBinding(initCtx);
		} catch(Exception e) {
			
			e.printStackTrace();
		}
			
			
	}	
			
			
			
		private static void listBinding(NamingContextExt ctx ) throws Exception 
		{
			System.out.println("in listBinding()");
			BindingListHolder holder = new BindingListHolder(new Binding[0]);
			BindingIteratorHolder iter = new BindingIteratorHolder();
			ctx.list(0,holder,iter);
			BindingHolder bh = new BindingHolder();
			
			if(iter.value == null) {
				System.err.println("iter is null..");
				return;				
			}
			
			while(iter.value.next_one(bh) ) {
				
				NameComponent n[] = bh.value.binding_name;				
				String name = ctx.to_string(n);
				System.out.println("name=" + name);
				NameComponent nc[] = ctx.to_name(name);
				NamingContextExt subContext = NamingContextExtHelper.narrow(ctx.resolve(nc));
				if(subContext != null) {
					listBinding(subContext);
				}
				
								
			}
			
			
		
		}
}
