/* Created on 13/05/2007 */
package test;

import hoshen.common.utils.db.ConnectionManager;
import hoshen.common.utils.db.ConnectionManagerFactory;
import junit.framework.TestCase;

/**
 * 
 * @author c59183384
 */
public class ConnectionManagerTest extends TestCase {

	public static void main(String[] args)
	{
		junit.textui.TestRunner.run(ConnectionManagerTest.class);
	}

	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		super.setUp();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		super.tearDown();
	}

	public void testGetConnectionManager() throws Exception
	{
		ConnectionManager cm = ConnectionManagerFactory.getConnectionManager();
		assertNotNull(cm);
	}

}
