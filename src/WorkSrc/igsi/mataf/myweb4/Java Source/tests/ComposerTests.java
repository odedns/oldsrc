package tests;

import com.ibm.dse.base.*;
import composer.utils.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ComposerTests {

	static void init() throws Exception
	{
		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/client/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);			
	}
	
	static void fmtTest() throws Exception		
	{
			Context ctx = (Context) Context.readObject("transCtx");
			ctx.setValueAt("Name","Oded");
			ctx.setValueAt("Balance","1000");
			System.out.println("before: " + ctx.toString());
//			DynamicXMLFormat fmt = new DynamicXMLFormat();
			DynamicRecordFormat fmt = new DynamicRecordFormat();			
			fmt.setDataElementName(ctx.getKeyedCollection().getName());
			System.out.println("dataElementName = " + fmt.getDataElementName());
			String s = fmt.format(ctx);			
			System.out.println("after format : " + s);
			fmt.unformat(s,ctx);
			System.out.println("after unformat: " + ctx.getKeyedCollection().toString());
						
	
	}
	
	
	
	static void testFoo() throws Exception
	{
		KeyedCollection kc = (KeyedCollection) DataElement.readObject("AccountRecordData");
		kc.setValueAt("AccountNumber","1051008");	
		kc.setValueAt("Type","1");	
		kc.setValueAt("Name","Oded");	
		kc.setValueAt("Balance","90000");	
		
		KeyedCollection kc2 = (KeyedCollection) DataElement.readObject("Myaccount");
		kc2.setValueAt("myname","oded");
		kc2.setValueAt("myid","05918338");
		
		kc2.addElement(kc);
		// kc2.setValueAt("AccountRecordData",kc);
		
		System.out.println("kc2 = " + kc2.toString());
		
	}
	
	public static void main(String[] args) 
	{
		try {
			init();
			testFoo();
			fmtTest();
			
		} catch(Exception e) {
			e.printStackTrace();	
		}
		
		
	}
}
