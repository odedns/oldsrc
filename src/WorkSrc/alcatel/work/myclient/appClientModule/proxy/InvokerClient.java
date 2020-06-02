package proxy;

import java.util.ArrayList;
import java.util.Properties;

import javax.naming.*;
import onjlib.ejb.invoker.*;





/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class InvokerClient
{

	public static void main(String[] args)
	{
			 try {
				String url       = "iiop://localhost:2809";
				String factory = "com.ibm.websphere.naming.WsnInitialContextFactory";
				Properties prop = new Properties();
				prop.put(Context.INITIAL_CONTEXT_FACTORY,factory);
				prop.put(Context.PROVIDER_URL, url);
				Context ctx          = new InitialContext(prop);
				InvokerHome invokerHome = (InvokerHome) ctx.lookup("ejb/onjlib/ejb/invoker/InvokerHome");
				Invoker invoker = invokerHome.create();
				System.out.println("got invoker");								
				MyBpo my = new MyBpo();
				ArrayList ar = new ArrayList();
				ar.add(new Integer(100));
				Object o = invoker.invoke(MyBpo.class,"find",ar);
				System.out.println("return = " + o.toString());
			 } catch(Exception e) {
				 e.printStackTrace();
			 }
		
	}
}
