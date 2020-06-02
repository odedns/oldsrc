package composer;

import com.ibm.dse.base.*;
import com.ibm.jvm.io.FileOutputStream;
import composer.utils.*;


import java.io.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Test {

	/**
	 * Constructor for Test.
	 */
	public Test() {
		super();
	}
	public static void init() throws Exception 
	{

		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/client/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
		
		
	}

	static void DataSer() throws Exception
	{
		DataElement opName = (DataElement) DataElement.readObject("opName");
		opName.setValue("foo");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		opName.writeExternal(os);
		os.flush();
		os.close();
		
		byte b[] = bos.toByteArray();			
		System.out.println("len = " + b.length);
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(bis);
		opName.readExternal(is);
		System.out.println("opName = " + opName.toString());
						
	}
	
	
	static String bytesToString(byte b[])
	{
		StringBuffer sb = new StringBuffer();
		for(int i=0; i < b.length; ++i) {
			sb.append(b[i]);
		}		
		return(sb.toString());	
		
	}
	
	
	static byte[] StringToBytes(String s)
	{
		byte b[] = new byte[s.length()];
		for(int i=0; i < s.length(); ++i) {
			b[i]= (byte) s.charAt(i);
		}
		return(b);
	}
	
	static void serTest() throws Exception
	{
		String s = "foo";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream os = new ObjectOutputStream(bos);
		os.writeObject(s);
		os.flush();
		os.close();
			

		byte b[] = bos.toByteArray();
		String n = new String(b);			
		System.out.println("len = " + b.length);	
		b = n.getBytes();
		ByteArrayInputStream bis = new ByteArrayInputStream(b);
		ObjectInputStream is = new ObjectInputStream(bis);
		s = (String )is.readObject();
		System.out.println("after ser s = " + s);
					
		
		
	}
	
	public static void main(String[] args) {
		
		try {
			
			init();
			DataSer();
			
			Context ctx = (Context) Context.readObject("genClientOpCtx");
			ctx.setValueAt("opName", "foo");			
			ctx.setValueAt("ctxName","myctx");
			ctx.setValueAt("ctxData","myctxData");
			String ctxData = ContextSerializer.serialize(ctx);
			//byte b[] = ContextSerializer.serializeBytes(ctx);
			
			/*
			b = Base64.encode(b);
			String data =  new String(b);
			System.out.println("data = " + data);	
			b = data.getBytes();
			b = Base64.decode(b);
			*/
			ctx = ContextSerializer.deserialize(ctxData);
			String s = ctx.toString();
			System.out.println("ctx = " + s);
			
			KeyedCollection kc = (KeyedCollection) DataElement.readObject("formData");
			kc.setValueAt("f1","v1");
			kc.setValueAt("f2","v2");
			System.out.println("kc = " + kc.toString());
			
			
			
			// serTest();
			
		} catch(Exception e) {
			e.printStackTrace();						
		}
		}
}
