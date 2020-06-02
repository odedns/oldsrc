package tests;

import javax.ejb.*;
import javax.naming.*;
import java.util.*;

import onjlib.ejb.mytest.*;


public class Client {
  
  public static void main(String[] args) {
	System.out.println("\nBeginning .Client...\n");

	String url       = "iiop://localhost:2809";
	String factory = "com.ibm.websphere.naming.WsnInitialContextFactory";
	
	try {
		Hashtable h = new Hashtable();
		h.put(Context.INITIAL_CONTEXT_FACTORY,factory);
			
		h.put(Context.PROVIDER_URL, url);
	  Context ctx          = new InitialContext(h);
	  MytestHome home = (MytestHome) ctx.lookup("ejb/mytest");

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

