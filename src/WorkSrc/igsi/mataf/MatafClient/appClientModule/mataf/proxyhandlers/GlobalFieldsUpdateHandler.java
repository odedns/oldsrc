package mataf.proxyhandlers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import mataf.logger.GLogger;
import mataf.services.proxy.AbstractRequestHandler;
import mataf.services.proxy.GlobalRecordsMap;
import mataf.services.proxy.ProxyRequest;
import mataf.services.proxy.RequestException;
import mataf.utils.StringUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Settings;

/**
 * @author Oded Nissan 17/07/2003.
 * Handle the synchronization of global fields from the RT 
 * to Composer Context.
 */
public class GlobalFieldsUpdateHandler extends AbstractRequestHandler {
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
		HashMap params = req.getParams();
		Set keys = params.keySet();
		Iterator iter = keys.iterator();
		String key = null;
		String value = null;
		while(iter.hasNext()) {
			key = (String) iter.next();
			value = (String) params.get(key);
			try {
				key = cvtCodeString(key);				
				if(null != key ) {
					try {
						//GLogger.debug("GlobalFieldsReqHandler: key = " + key + " value=" + value);
						m_ctx.setValueAt(key,value);
					} catch(DSEObjectNotFoundException dseo) {
						GLogger.warn("object not found: " + key);
					}
				}
			} catch(Exception dsee) {
				dsee.printStackTrace();
				throw new RequestException(100,dsee.getMessage());
				
			}
		}	
		return(null);
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
			if(null == tmp) {
				return(null);
			}
			if(i > 0) {
				sb.append(FIELD_SEPERATOR);
			}
			//System.out.println("Java Version ="+System.getProperty("java.version"));
			sb.append(tmp.toString());
		}
		return(sb.toString());		
	}
	
	
	public static void main(String argv[])
	{
		GlobalFieldsUpdateHandler handler = new GlobalFieldsUpdateHandler();
		try {
			Context.reset();
			Settings.reset("http://localhost/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
			String dataNameCode = "1059.2213.641";
			String s = handler.cvtCodeString(dataNameCode);
			System.out.println("Sent : "+dataNameCode);
			System.out.println("Got : " + s);
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}	
}
