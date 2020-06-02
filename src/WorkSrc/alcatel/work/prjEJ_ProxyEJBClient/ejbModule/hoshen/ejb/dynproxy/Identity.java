/* Created on 01/05/2007 */
package hoshen.ejb.dynproxy;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author c36036556
 */
public class Identity
{
	private static ThreadLocal identity = new ThreadLocal()
	{
		protected Object initialValue()
		{
			return StringUtils.EMPTY;
		}
	};
	
	
	public static String getIdentity()
	{
		return (String)identity.get();
	}
	
	/* package visibility */ static void setIdentity(String identity)
	{
		Identity.identity.set(identity);
	}
}
