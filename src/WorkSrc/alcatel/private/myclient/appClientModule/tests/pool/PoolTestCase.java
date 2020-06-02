/* Created on 14/03/2006 */
package tests.pool;

import org.apache.commons.pool.impl.GenericObjectPool;

import tests.TestServiceLocator;
import junit.framework.TestCase;

/**
 * 
 * @author Odedn
 */
public class PoolTestCase extends TestCase
{
	 
	public static void main(String[] args) {
		junit.textui.TestRunner.run(PoolTestCase.class);
	}

	public void testPool() throws Exception
	{
		GenericObjectPool pool = new GenericObjectPool(new TestFactory());
		GenericObjectPool.Config config = new GenericObjectPool.Config();
	    config.maxActive = 2;
	    config.maxIdle = 3;
	    config.maxWait = 5L;
	    config.minEvictableIdleTimeMillis = 7L;
	    config.numTestsPerEvictionRun = 9;
	    config.testOnBorrow = true;
	    config.testOnReturn = true;
	    config.testWhileIdle = true;
	    config.timeBetweenEvictionRunsMillis = 11L;
	    config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
		pool.setConfig(config);
		String s= (String) pool.borrowObject();
		System.out.println("got object : " + s);
		pool.returnObject(s);
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

}
