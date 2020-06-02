package tests;

import junit.framework.TestCase;
import mataf.proxyhandlers.JournalWrapHandler;
import mataf.services.proxy.ProxyReply;
import mataf.services.proxy.ProxyService;
import mataf.services.proxy.RTCommands;
import mataf.services.proxy.SampleRequestHandler;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;

import java.util.HashMap;
/**
 * @author Oded Nissan 12/06/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ProxyServiceTest extends TestCase {

	ProxyService m_proxy = null;
	/**
	 * Constructor for ProxyServiceTest.
	 * @param arg0
	 */
	public ProxyServiceTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(ProxyServiceTest.class);
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		System.out.println("in setUp");
		Context.reset();
		Settings.reset("http://localhost:9080/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		Context ctx = (Context) Context.readObject("startupClientCtx");			
		CSClientService csrv = (CSClientService) ctx.getService("CSClient");
		csrv.establishSession();				
		DSEClientOperation startOp =  (DSEClientOperation) DSEOperation.readObject("startupClientOp");
		startOp.execute();		
		Context wksCtx = (Context) Context.getContextNamed("workstationCtx");
		m_proxy = (ProxyService) wksCtx.getService("proxyService");		
		SampleRequestHandler handler = new SampleRequestHandler();
		m_proxy.addRequestHandler("*", handler);		
		m_proxy.addRequestHandler(RTCommands.WRAP_JOURNAL_COMMAND, new JournalWrapHandler());
		
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();		
	}

	/*
	 * Test for HashMap sendRequest(ProxyRequest)
	 */
	public void testSendRequestProxyRequest() throws Exception {

		Thread.currentThread().sleep(5000);
		
		HashMap ht = m_proxy.sendRequest(Integer.parseInt(RTCommands.MANAGERSLIST_COMMAND),"samchut","1");		
		System.out.println("ht = " + ht.toString());
		// m_proxy.sendRequest(10056,"ejcode","Z");
		/*
		boolean b = m_proxy.checkAccess("T410");
		System.out.println("got b = "+ b);		
		IndexedCollection ic = m_proxy.getManagersList();
		System.out.println("got ic = " + ic.toStrings());
		*/
		/*
		int n = m_proxy.authenticate("111113","181818");
		System.out.println("retCode from authenticate = " + n);
		assertEquals(n,0);
		*/
		/*
		ProxyRequest req = new ProxyRequest();
		req.setCommand(1);
		req.addParam("param1","val1");
		
		try {			
			for(int i=0; i < 3; ++i) {
				m_proxy.activateTransaction("T101-" + i);
			}
		} catch(RequestException rq) {
			if(rq.getErrorCode() == 999) {
				fail();	
			}	
		}
		*/
	}



}
