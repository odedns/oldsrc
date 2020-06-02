package tests;

import junit.framework.TestCase;
import composer.services.*;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MessagingServiceTest extends TestCase {

	MessagingService m_service = null;
	/**
	 * Constructor for MessagingServiceTest.
	 * @param arg0
	 */
	public MessagingServiceTest(String arg0) {
		super(arg0);
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(MessagingServiceTest.class);
		
	}

	/**
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		m_service = (MessagingService) Service.readObject("msgService");
		m_service.setConnectionHandler("composer.services.MessagincConnHandler");
		m_service.setUser("oded");		
	}

	/**
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	//	m_service.terminate();
	}

	public void testSendMessage()  throws Exception
	{
		m_service.sendMessage("oded","message-1 oded");		
		m_service.sendMessage("oded","message-2 oded");
		m_service.sendMessage("oded","message-3 oded");
		m_service.sendMessage("oded","message-4 oded");
		//m_service.sendMessage("poo","message-1 poo");
		
	}

}
