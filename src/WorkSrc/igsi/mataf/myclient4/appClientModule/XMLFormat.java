

import java.beans.*;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

import com.ibm.dse.base.*;
import java.io.*;
import java.beans.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class XMLFormat {


	public static String formatContext(Context ctx)
	{
		String data = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		XMLEncoder enc = new XMLEncoder(bos);
		enc.writeObject(ctx);
		enc.close();		
		data = new String(bos.toByteArray());		
		return(data);
		
	}
	
	public static Context unformatContext(String data, String ctxName)
		throws Exception
	{
	
		Context ctx = (Context) Context.readObject(ctxName);
		ByteArrayInputStream bis = new ByteArrayInputStream(data.getBytes());
		
		XMLDecoder dec = new XMLDecoder(bis);
		ctx = (Context) dec.readObject();				
		dec.close();
		return(ctx);
	}
	
	public static void main(String[] args) {
		try {
			Context.reset();
			Settings.reset("http://localhost/myweb4/dse/client/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
			Context ctx = (Context) Context.readObject("overrideCtx");
			ctx.setValueAt("mgrUserId","111111");
			ctx.setValueAt("tellerUserId","foo");
			ctx.setValueAt("trxId","106");
			ctx.setValueAt("trxName","סריקת צקים");

			String s = formatContext(ctx);
			System.out.println("s = " + s);
			Context ctx2 = unformatContext(s,"overrideCtx");
			System.out.println("ctx2 = " + ctx2.getKeyedCollection().toString());
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
	}
}
