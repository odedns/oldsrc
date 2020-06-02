package mataf.utils;

import com.ibm.dse.base.*;
/**
 * @author Oded Nissan 13/07/2003
 * A context formatting utility class using the 
 * Dynamic xml format to format the context.
 * The class uses a single instace of Dynamic Xml format and
 * is thread safe.
 */
public class ContextFormatter {	
	private static DynamicXMLFormat m_fmt = new DynamicXMLFormat();	
	/**
	 * cannot instantiate class.
	 */
	private ContextFormatter()
	{
	}
	
	
	/**
	 * format a context into a String.
	 * using dynamic xml format.
	 * Copy the global records from the branch and workstation 
	 * contexts to the transaction context.
	 * @param ctx the Context to format.
	 * @return String the formatted context in a String.
	 * @throws Exception in case of error.
	 */
	public static synchronized String formatContext(Context ctx)
		throws Exception
	{
		m_fmt.setDataElementName(ctx.getKeyedCollection().getName());
		String s = m_fmt.format(ctx);
		
		return(s);	
	}
	/**
	 * format a context into a String.
	 * using dynamic xml format.
	 * Copy the global records from the branch and workstation 
	 * contexts to the transaction context.
	 * @param ctx the Context to format.
	 * @return String the formatted context in a String.
	 * @throws Exception in case of error.
	 */
	public static synchronized String formatClientContext(Context ctx, boolean passGlobals)
		throws Exception
	{
		if(passGlobals) {
			KeyedCollection globals = (KeyedCollection) ctx.getElementAt("GLOBAL_RECORDS");
			KeyedCollection kc = (KeyedCollection) ctx.tryGetElementAt("GLSE_GLBL");
			if(null != kc) {
				globals.addElement(kc);
			}		
			kc = (KeyedCollection) ctx.tryGetElementAt("GLSF_GLBL");
			if(null != kc) {
				globals.addElement(kc);
			}
			kc = (KeyedCollection) ctx.tryGetElementAt("GLSG_GLBL");
			if(null != kc) {
				globals.addElement(kc);
			}		
			kc = (KeyedCollection) ctx.tryGetElementAt("GLSF_MAZAV");
			if(null != kc) {
				globals.addElement(kc);
			}
		}
			
		m_fmt.setDataElementName(ctx.getKeyedCollection().getName());
		String s = m_fmt.format(ctx);
		return(s);	
	}
	
	/**
	 * unformats the String using dynamic xml format
	 * into a Context.
	 * Copy the global records from the transaction context to the
	 * workstation and branch contexts.
	 * @param s a String containing the formatted context.
	 * @return Context the Context received from the unformat 
	 * on the input String.
	 * @throws Exception in case of error.
	 */ 
	public static synchronized Context unformatContext(String s,String ctxName)
		throws Exception
	{
		Context ctx = (Context) Context.readObject(ctxName);	
		m_fmt.unformat(s,ctx);						
		return(ctx);
		
	}
}

