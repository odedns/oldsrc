package tests;

import java.util.Properties;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.rmi.PortableRemoteObject;

public class TestJndi {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Properties p = new Properties();
			
			
			
			p.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
			p.put(Context.PROVIDER_URL,"jnp://localhost:10991");
			InitialContext ctx = new InitialContext(p);
			NamingEnumeration bindings = ctx.listBindings("");

			while (bindings.hasMore()) {
			    Binding bd = (Binding)bindings.next();
			    System.out.println(bd.getName());
			}
		 } catch (Exception e) {
		   e.printStackTrace ();
		}
	}

 }

