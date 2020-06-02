package onjlib.ejb.mytest;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;


import java.util.*;



public class MytestClient {
  
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Properties p = new Properties();
			
			System.out.println("java.version: " + System.getProperty("java.version"));
			
			p.put(Context.INITIAL_CONTEXT_FACTORY,"org.jnp.interfaces.NamingContextFactory");
			p.put(Context.PROVIDER_URL,"jnp://localhost:10991");
			InitialContext ctx = new InitialContext(p);
			System.out.println("MytestClient");
		     Object o =  ctx.lookup("myear/MytestBean/remote");	     
		     MytestRemote myr = (MytestRemote) PortableRemoteObject.narrow(o, MytestRemote.class);
		     System.out.println("got =" + myr.getData());
		 } catch (Exception e) {
		   e.printStackTrace ();
		}
	}

 }

