/*
 * Created on 12/07/2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package tests;

import javax.jms.*;

import junit.framework.TestCase;
import onjlib.j2ee.*;

/**
 * @author odedn
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestServiceLocator extends TestCase {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestServiceLocator.class);
	}

	public void testFindObject() throws Exception {
		ServiceLocator srv = ServiceLocator.getInstance();
		QueueConnectionFactory qcf = (QueueConnectionFactory) srv.findObject("jms/QCF", QueueConnectionFactory.class);
		QueueConnection conn = qcf.createQueueConnection();
		assertTrue(conn != null);
	}

}
