package tests;
import mataf.utils.*;
import com.ibm.dse.base.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SlikaCtxFmt {

static void init() throws Exception
	{		
		Context.reset();
		Settings.reset("http://localhost/MatafServer/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	
	public static void main(String[] args) {
		try {
			init();
			Context ctx = (Context) Context.readObject("slikaCtx");
			ctx.setValueAt("AccountNumber","123456789");
			ctx.setValueAt("BranchIdInput","999");
			ctx.setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
			ctx.setValueAt("trxORData.trxId","106");
			ctx.setValueAt("trxORData.trxName","סריקת צקים");		
			ctx.setValueAt("trxORData.viewName","mataf.slika.panels.SlikaConclusionPanel");
			ctx.setValueAt("trxORData.ctxName","slikaCtx");
			ctx.setValueAt("trxORData.mgrUserId","");		
			ctx.setValueAt("trxORData.samchutMeasheret","");
			ctx.setValueAt("trxORData.answerCode","answerCode");				
			ctx.setValueAt("trxORData.answerTxt","answerTxt");	
			String s = ContextFormatter.formatContext(ctx);
			// System.out.println("s = " + s);
			Context ctx2 = ContextFormatter.unformatContext(s,"slikaCtx");
			System.out.println("ctx2 = " + ctx2.toString());
			KeyedCollection kc = (KeyedCollection) ctx2.getElementAt("trxORData");
			System.out.println("kc = " + kc.toString());		
		} catch(Exception e) {
			e.printStackTrace();	
		}

	}
}
