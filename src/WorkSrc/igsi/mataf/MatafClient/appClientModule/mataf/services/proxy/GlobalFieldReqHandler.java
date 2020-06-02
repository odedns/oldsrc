package mataf.services.proxy;

import java.util.HashMap;
import mataf.utils.StringUtils;
import com.ibm.dse.base.*;

/**
 * @author Oded Nissan 17/07/2003.
 * Handle the synchronization of global fields between the RT 
 * and the Composer Context.
 */
public class GlobalFieldReqHandler extends AbstractRequestHandler {
	private static final char FIELD_SEPERATOR = '.';
	private Context m_ctx;
	
	
	
	/**
	 * set the context to use when updating global fields.
	 * @param ctx the Context to update.
	 */
	public void setContext(Context ctx)
	{
		m_ctx = ctx;	
	}
	/** 
	 * Get the field codes from the request, translate to field name
	 * and update the field in the context.
	 * @see mataf.services.proxy.RequestHandlerIF#execRequest(ProxyRequest)
	 */
	public HashMap execRequest(ProxyRequest req) throws RequestException 
	{
		return null;
	}
	
	
	/**
	 * convert a string containing field codes 
	 * to a string containing records and fields.
	 * For example:
	 * convert 222.111 to GSKE_REC.GL_SNIF
	 */
	String cvtCodeString(String codeString)	throws Exception			
	{
		String v[] = StringUtils.toStringArray(codeString,FIELD_SEPERATOR);
		StringBuffer sb = new StringBuffer();
		String tmp = null;
		GlobalRecordsMap gMap = GlobalRecordsMap.getInstance();		
		for(int i=0; i < v.length; ++i) {			
			int code = Integer.parseInt(v[i]);
			tmp = gMap.getFieldName(code);	
			if(i > 0) {
				sb.append(FIELD_SEPERATOR);
			}
			sb.append(tmp);
		}
		return(sb.toString());		
	}
	
	
	public static void main(String argv[])
	{
		GlobalFieldReqHandler handler = new GlobalFieldReqHandler();
		try {
			Context.reset();
			Settings.reset("http://localhost/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
			String s = handler.cvtCodeString("1059.2213.641");
			System.out.println("got : " + s);
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}

}
