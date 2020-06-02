package tests;
import composer.utils.*;
import com.ibm.dse.base.*;

/**
 * @author o000131
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CtxFormatterTest {

	static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	
	public static void main(String[] args) {
		try {
			init();
			Context ctx = (Context) Context.readObject("overrideCtx");
			ctx.setValueAt("mgrUserId","111111");
			ctx.setValueAt("tellerUserId","foo");
			ctx.setValueAt("trxId","106");
			ctx.setValueAt("trxName","סריקת צקים");
//			ContextFormatter.setFormatter(new DynamicRecordFormat());
			String s = ContextFormatter.formatContext(ctx);
			System.out.println("s = " + s);
			Context ctx2 = ContextFormatter.unformatContext(s,"overrideCtx");
			System.out.println("ctx2 = " + ctx2.getKeyedCollection().toString());
		} catch(Exception e) {
			e.printStackTrace();	
		}

	}
}
