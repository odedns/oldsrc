package tests;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SerTest {

		
		static void init() throws Exception
		{		
			Context.reset();
			Settings.reset("http://localhost/MatafServer/dse/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
		}
	

		static void testCtxSer() throws Exception
		{
			Context ctx = (Context) Context.readObject("halbanathonCtx");
			ctx.setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
			ctx.setValueAt("trxORData.trxId","106");
			ctx.setValueAt("trxORData.trxName","סריקת צקים");		
			ctx.setValueAt("trxORData.viewName","mataf.slika.panels.SlikaConclusionPanel");
			ctx.setValueAt("trxORData.ctxName","slikaCtx");
			ctx.setValueAt("trxORData.mgrUserId","111115");		
			ctx.setValueAt("trxORData.samchutMeasheret","");
			ctx.setValueAt("trxORData.answerCode","answerCode");				
			ctx.setValueAt("trxORData.answerTxt","answerTxt");	
			
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(ctx.getKeyedCollection());
			os.close();
			byte b[] = bos.toByteArray();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			ObjectInputStream is = new ObjectInputStream(bis);
			KeyedCollection kc = (KeyedCollection) is.readObject();
			System.out.println("kc = " + kc.toString());
			//Context ctx2 = (Context )is.readObject();
			//System.out.println("ctx2 = " + ctx2.getKeyedCollection().toString());
									
		}	
		
		static void testHtSer() throws Exception
		{
			Hashtable ht = new Hashtable();
			ht.put("1","one");
			ht.put("2","two");	
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(bos);
			os.writeObject(ht);
			os.close();
			byte b[] = bos.toByteArray();
			
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			ObjectInputStream is = new ObjectInputStream(bis);
			ht = (Hashtable )is.readObject();
			System.out.println("ht = " + ht.toString());
									
		}
		
	public static void main(String[] args) {
		try {
			System.out.println("java.version = " + System.getProperty("java.version"));			
			init();
			testCtxSer();	
	//		testHtSer();
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
	} // main
}
