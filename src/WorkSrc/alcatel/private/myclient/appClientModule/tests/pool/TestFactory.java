/* Created on 14/03/2006 */
package tests.pool;

import org.apache.commons.pool.PoolableObjectFactory;

/**
 * 
 * @author Odedn
 */
public class TestFactory implements PoolableObjectFactory
{

	private String m_obj;
	/* (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#makeObject()
	 */
	public Object makeObject() throws Exception
	{
		// TODO Auto-generated method stub
		m_obj = "fooString";
		System.out.println("> makeObject");
		return(m_obj);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#destroyObject(java.lang.Object)
	 */
	public void destroyObject(Object arg0) throws Exception
	{
		// TODO Auto-generated method stub
		m_obj = null;
		System.out.println("> destoryObject");

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#validateObject(java.lang.Object)
	 */
	public boolean validateObject(Object arg0)
	{
		// TODO Auto-generated method stub
		System.out.println("> validateObject");
		boolean b = false;
		if("fooString".equals(m_obj)) {
			b = true;
		}
		return(b);
	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#activateObject(java.lang.Object)
	 */
	public void activateObject(Object arg0) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("> activateObject");

	}

	/* (non-Javadoc)
	 * @see org.apache.commons.pool.PoolableObjectFactory#passivateObject(java.lang.Object)
	 */
	public void passivateObject(Object arg0) throws Exception
	{
		// TODO Auto-generated method stub
		System.out.println("> passivateObject");

	}

}
