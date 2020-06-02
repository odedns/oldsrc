/*
 * Created on 18/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tests;

import java.util.EventObject;
import javax.jms.JMSException;

import onjlib.events.EventNotifier;
import junit.framework.TestCase;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestEventNotifier extends TestCase {

	EventNotifier m_notifier;
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestEventNotifier.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		m_notifier = EventNotifier.getInstance();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		m_notifier.close();
	}

	public void testSendEvent() throws JMSException{
		
		EventObject event = new EventObject(new String("some fucking message"));
		m_notifier.sendEvent("oded","xsm",event);
	}

}
