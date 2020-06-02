package tests;
import mataf.utils.MQUtils;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Settings;


/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MQMFSender {


	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		
	}
	
	
	public static void main(String[] args) {
		try {
			byte[] id;
			init();
			System.out.println("MQMFSender");
			Context ctx = (Context) Context.readObject("overrideCtx");
			ctx.setValueAt("status","1");
			ctx.setValueAt("overrideType","2");
			ctx.setValueAt("trxMqHeader","this mqheader");
			MQUtils.sendMqMsg(ctx);
				
		
				
		}catch(Exception e) {
			e.printStackTrace();	
		}
		System.exit(1);
	}
	
	
}



