/*
 * Created on 03/02/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package tests;


/**
 * @author Nati
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestFinally
{

	public static void main(String[] args) throws Throwable
	{
		new TestFinally().finalize();
		System.out.println("TEST = "+getTest());
	}
	
	private static int getTest()
	{
		try
		{
			
			return 1;
		}
		finally
		{
			try
			{
				return 2;
			}
			finally
			{
				try
				{
					return 3;
				}
				finally
				{
					System.out.println("Hello World");
					return 4;
				}
			}				
		}
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable
	{
		super.finalize();
		System.out.println("5");
	}

}