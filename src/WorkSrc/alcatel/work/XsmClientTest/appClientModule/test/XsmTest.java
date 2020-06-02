/*
 * Created on 15/11/2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;

import java.util.Iterator;
import java.util.List;

import hoshen.configuration.entities.HsnConnection;
import hoshen.xsm.client.XSMSession;
import hoshen.xsm.core.command.NEMaintenanceCommand;
import hoshen.xsm.core.response.Response;
import hoshen.xsm.lightsoft.command.GetAllSNCCommand;
import hoshen.xsm.lightsoft.command.LSPingCommand;
import hoshen.xsm.lightsoft.response.LSResponse;

/**
 * @author odedn
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class XsmTest {

	public static void main(String args[]) {
		
		System.out.println("in XsmTest");
		
		try {
			runTest();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	
	
	
	public static String runTest() throws Exception {
		
		XSMSession xsmSession = null;
		String XsmAppId = "XSM_LIGHTSOFT";
		String s  = null;
		String url = "iiop://localhost:2809";
		xsmSession = new XSMSession(XsmAppId,url);
		NEMaintenanceCommand cmd = new NEMaintenanceCommand();
		Response resp = xsmSession.invoke(cmd,60000);
				
		/*
		GetAllSNCCommand cmd = new GetAllSNCCommand();
		LSResponse resp = (LSResponse) xsmSession.invoke(cmd,60000);
		List l = resp.getList();
		System.out.println("size: "+l.size());
		*/
		/*
		Iterator iterator = l.iterator();
		while(iterator.hasNext())
		{
			HsnConnection connection = (HsnConnection) iterator.next();
			System.out.println(connection.getConnectionId().toString() + "  " + connection.getAdditionalInfo().toString());
		}
		*/
		System.out.println("after invoke command");
		xsmSession.logout();
		return(s);
		
	}
	
	
}
