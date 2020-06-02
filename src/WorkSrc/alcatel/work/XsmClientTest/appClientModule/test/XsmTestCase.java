/*
 * Created on 20/12/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;



import java.util.List;

import hoshen.common.utils.core.Status;
import hoshen.common.utils.exception.HoshenException;
import hoshen.configuration.entities.ResourceId;
import hoshen.configuration.entities.TpName;
import hoshen.configuration.entities.valuelists.ResourceType;
import hoshen.xsm.client.XSMSession;
import hoshen.xsm.core.command.GetAllActiveAlarmsCommand;
import hoshen.xsm.core.command.NEMaintenanceCommand;
import hoshen.xsm.core.response.Response;
import hoshen.xsm.lightsoft.command.*;
import hoshen.xsm.lightsoft.command.GetAllEquipmentCommand;
import hoshen.xsm.lightsoft.command.GetAllSNCCommand;
import hoshen.xsm.lightsoft.command.GetAllTlinksCommand;
import hoshen.xsm.lightsoft.response.LSResponse;
import junit.framework.TestCase;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public  class XsmTestCase extends TestCase {

	private static final String LS_APP_ID = "XSM_LIGHTSOFT";
	private static  XSMSession xsmSession = null;
		
	
	public static void setXSMSession(XSMSession xs)
	{
		xsmSession = xs;
	}
	
	
	/*
	public void testGetAllMe() throws Exception
	{
		System.out.println(">testGetAllMe");
		GetAllManagedElementsCommand cmd = new GetAllManagedElementsCommand();
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("got results form testGetMe");
		List l = ((LSResponse) resp).getList();
		assertTrue(l != null);
		assertTrue(l.size() > 0);
		System.out.println("< testGetAllMe");
		
		
	}
	*/
	/*
	public void testGetAllSNC() throws Exception
	{
		
		System.out.println(">testGetAllSNC");
		GetAllSNCCommand cmd = new GetAllSNCCommand();
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("got results form testGetAllSNC");
		List l = ((LSResponse) resp).getList();
		assertTrue(l != null);
		assertTrue(l.size() > 0);
		System.out.println("< testGetAllSNC");
		
	}

	
	public void testGetAllTlinks() throws Exception
	{
		System.out.println(">testGetAllTlinks");
		GetAllTlinksCommand cmd = new GetAllTlinksCommand();
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("got results form testGetAllTlinks");
		List l = ((LSResponse) resp).getList();
		assertTrue(l != null);
		assertTrue(l.size() > 0);
		System.out.println("<testGetAllTlinks");
	}
	
	*/
	/*
	public void testGetAllEquipment() throws Exception
	{
		System.out.println(">testGetAllEquipment");
		ResourceId resId = new ResourceId();
		resId.setIsn(92);
		resId.setResourceType(ResourceType.PHYSICALDEVICE);
		GetAllEquipmentCommand cmd  = new GetAllEquipmentCommand(resId);
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("got results form testGetAllEquipment");
		List l = ((LSResponse) resp).getList();
		assertTrue(l != null);
		assertTrue(l.size() > 0);
		System.out.println("<testGetAllEquipment");
	}
	*/
	public void testPing() throws HoshenException
	{
		System.out.println(">testPing");
		LSPingCommand cmd = new LSPingCommand(null);
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("< testPing");
	}
	
	public void testTestCmd() throws HoshenException
	{
		System.out.println(">testTestCmd");
		LSTestCommand cmd = new LSTestCommand();
		cmd.setData("foo");
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("< testTestCmd");
		
	}
	/*
	public void testMOP() throws Exception
	{
		System.out.println(">testMOP");
		NEMaintenanceCommand cmd = new NEMaintenanceCommand();
		ResourceId resId = new ResourceId(ResourceType.PHYSICALDEVICE,10700);
		String tpName="A70TX";
		TpName tp = new TpName();
		tp.setFullName(tpName);
		cmd.fillInMaintenanceData(resId,"IC1",null,tp,"FACILITY_LOOPBACK",true);
		// cmd.fillInMaintenanceData()
		Response resp = (Response) xsmSession.invoke(cmd,160000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("<testMOP");				
		
	}

	public void testMOPRelease() throws Exception
	{
		System.out.println(">testMOPRelease");
		NEMaintenanceCommand cmd = new NEMaintenanceCommand();
		ResourceId resId = new ResourceId(ResourceType.PHYSICALDEVICE,10700);
		String tpName="A70TX";
		TpName tp = new TpName();
		tp.setFullName(tpName);
		cmd.fillInMaintenanceData(resId,"IC1",null,tp,"FACILITY_LOOPBACK",false);
		// cmd.fillInMaintenanceData()
		Response resp = (Response) xsmSession.invoke(cmd,160000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("<testMOPRelease");				
		
	}
*/
	/*
	public void testGetAllActiveAlarms() throws HoshenException
	{
		System.out.println("> testGetAllActiveAlarms");
		GetAllActiveAlarmsCommand cmd = new GetAllActiveAlarmsCommand();
		Response resp = (Response) xsmSession.invoke(cmd,60000);
		if(resp.getReturnedStatus().getCode() != Status.CODE_SUCCESS) {
			fail();
		}
		System.out.println("< testGetAllActiveAlarms");
	}
	*/
	
	public static void main(String[] args) {
		
		XSMSession xsmSession = null;
		String XsmAppId = "XSM_LIGHTSOFT";
		String url = "iiop://localhost:2809";
		try {
			xsmSession = new XSMSession(XsmAppId,url);
		} catch(Exception e ){
			System.err.println("error creating XsmSession..");
			e.printStackTrace();
			System.exit(1);
		}
		XsmTestCase.setXSMSession(xsmSession);
		
		//  now run the test.
		junit.textui.TestRunner.run(XsmTestCase.class);
		
		xsmSession.logout();
		
		
	}

	
	
}
