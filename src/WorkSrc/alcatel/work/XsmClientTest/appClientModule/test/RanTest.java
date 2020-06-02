/*
 * Created on 17/10/2006
 *
 */
package test;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import hoshen.common.utils.core.Status;
import hoshen.configuration.entities.HsnConnection;
import hoshen.xsm.client.ActionCommandExecutor;
import hoshen.xsm.client.XSMSession;
import hoshen.xsm.core.response.Response;
import hoshen.xsm.lightsoft.command.GetAllSNCCommand;
import hoshen.xsm.lightsoft.response.LSResponse;

/**
 * @author ranp
 */
public class RanTest {
 private static final Logger log = Logger.getLogger(RanTest.class);
	public static boolean  testGetAllConn()
	{

		String xsmName = "XSM_LIGHTSOFT";
		XSMSession xsmSession = null;
		LSResponse lsResp = null;
		Response response;
		try {

			GetAllSNCCommand cmd = new GetAllSNCCommand();
			response = ActionCommandExecutor.executeCommandOnXSM(
					cmd, xsmName);

			if (response.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
				log.error("####XSM invocation-error "
						+ response.getReturnedStatus().getUserMessage().toString());
				System.out.println("####XSM invocation-error "
						+ response.getReturnedStatus().getUserMessage().toString());
				return false;
			}
			else
				lsResp = (LSResponse)response;
		} catch (Exception e) {
			log.error("findAllManagedElements: Failed invoking command", e);
			e.printStackTrace();
			System.out.println("findAllManagedElements: Failed invoking command");
			return false;
		}
		List l = lsResp.getList();
		System.out.println("size: "+l.size());
		Iterator iterator = l.iterator();
		while(iterator.hasNext())
		{
			HsnConnection connection = (HsnConnection) iterator.next();
			System.out.println(connection.getConnectionId().toString() + "  " + connection.getAdditionalInfo().toString());
		}
		
		return true;
	}
	
	
	public static void main(String[] args) {
		System.out.println("Connection Test");
		if(!testGetAllConn()) System.out.println("Failed");
		
	}
}
