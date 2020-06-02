package tests;


import com.ibm.dse.base.*;
/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CTracer {


	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	public static void main(String[] args) {
	
		try {
			init();	
			Trace.trace("foo", Trace.Debug, Trace.Debug,"TID","some message");
		
		} catch(Exception e) {
			e.printStackTrace();	
		}
	}
}
