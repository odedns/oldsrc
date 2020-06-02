package onjlib.ejb.mytest;

import javax.ejb.*;
import javax.naming.*;
import javax.rmi.PortableRemoteObject;

import java.rmi.RemoteException;
import java.util.*;


public class Client {
  static String url       = "iiop://localhost:2809";


  public static void main(String[] args) {
	System.out.println("\nBeginning .Client...\n");

	try {
		Properties prop = new Properties();
		prop.put(Context.INITIAL_CONTEXT_FACTORY,
			"com.ibm.websphere.naming.WsnInitialContextFactory");
		prop.put(Context.PROVIDER_URL, url);
	  Context ctx          = new InitialContext(prop);	  
	  Object homeObj = (Object) ctx.lookup("jndi.mytest");
	  MytestHome home = (MytestHome) PortableRemoteObject.narrow(homeObj,MytestHome.class);

	  for(int i=0; i < 3; ++i) {

		System.out.println("Creating Mytest session \n");
		Mytest mys = (Mytest) home.create();
		System.out.println("Mytest remote created \n");
		mys.setData("Data [" + i + "]");
		System.out.println("got " + mys.getData());
		mys.remove();

	  /*
	  System.out.println("Sleeping ....");
	  Thread.sleep(30000);
	  System.out.println("got " + mys.getData());
	  */

	  // remove  the bean,
		 //mys.remove();
	 }

	}
	catch (Exception e) {
	  System.out.println(":::::::::::::: Error :::::::::::::::::");
	  e.printStackTrace();
	}
	System.out.println("\nEnd Mytest session.Client...\n");
  }
 }

