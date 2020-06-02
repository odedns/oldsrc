package mataf.utils;

import com.ibm.dse.base.*;
/**
 * @author Oded Nissan 17/11/2003
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ContextUtils {

	/**
	 * don't instantiate class.
	 */
	private ContextUtils()
	{
	}
	
	/**
	 * get the named context from the context hierarchy.
	 * @param ctx the context we will search for the named context.
	 * @param ctxName the name of the context to find in the hierarchy.
	 * @return the found Context or null if a context was not found.
	 */
	public static Context getNamedContext(Context ctx, String ctxName)
	{
		if(ctxName.equals(ctx.getName())) {
			return(ctx);	
		}
		if(ctx.getParent() == null) {
			return(null);	
		}
		return(getNamedContext(ctx.getParent(),ctxName));
		
	}
	
}
