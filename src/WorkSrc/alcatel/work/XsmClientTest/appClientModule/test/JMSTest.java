/*
 * Created on 27/12/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.util.Hashtable;

import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class JMSTest {
	 // Defines the JNDI context factory.
	  public final static String JNDI_FACTORY="com.ibm.websphere.naming.WsnInitialContextFactory";

	  // Defines the JMS context factory.
	  public final static String JMS_FACTORY="jms/qcfHOSHEN";
	  
	  public final static String URL  = "iiop://n798:2810";
	/**
	 * 
	 */
	public JMSTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		try {
			System.out.println("trying to connect to : " + URL);
			Hashtable env = new Hashtable();
			env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
			env.put(Context.PROVIDER_URL, URL);
			InitialContext ctx =  new InitialContext(env);
			QueueConnectionFactory qconFactory = (QueueConnectionFactory) ctx.lookup(JMS_FACTORY);
			QueueConnection qcon = qconFactory.createQueueConnection();
			System.out.println("created queue connection");
		} catch(Exception e ){
			e.printStackTrace();
		}
	    
	    
		
	}
}
