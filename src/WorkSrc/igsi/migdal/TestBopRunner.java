package composer;

import com.ibm.dse.base.*;
import migdal.operations.templates.*;

/**
 * Insert the type's description here.
 * Creation date: (02/01/03 16:46:51)
 * @author: Doron
 */
public class TestBopRunner extends BopRunner {

public TestBopRunner(String bop , String ctx) throws Exception
{
	super(bop,ctx);
}
public static void init() throws Exception 
{

	Context.reset();
	Settings.reset("C:/Migdal/dse/server/dse.ini");
	Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
	// set log 4j
	String log4jFileName =
			(String)  "c:/Migdal/dse/server/log4j.properties";
	System.out.println(log4jFileName);
	org.apache.log4j.PropertyConfigurator.configureAndWatch(log4jFileName, 120000);

	
}
public static void main(String argv[])
{
	String bop = "hai045n0BOp";
	String ctx = "mechiraProcCtx";

	try {
		init();
		TestBopRunner br = new TestBopRunner(bop,ctx);
		br.execute();

	} catch(Exception e) {
		e.printStackTrace();
	}
	
}

/**
 * Insert the method's description here.
 * Creation date: (13/01/03 17:24:06)
 * @exception java.lang.Exception The exception description.
 */
public void initCtx() throws java.lang.Exception 
{

	System.out.println("TestBOpRunner.initCtx()");
	m_ctx.setValueAt("MS_MIFAL","9802231");
	m_ctx.setValueAt("MS_ZIHUY","22966774");
	m_ctx.setValueAt("msHazmanaBpr","0");


	IndexedCollection ic = (IndexedCollection)m_ctx.getElementAt("mutzarimMechiraListData");
	KeyedCollection kc = (KeyedCollection) DataField.readObject("mutzarMechiraRecordData");
	kc.setValueAt("MS_POL","14743403");	
	ic.addElement(kc);
	kc = (KeyedCollection) DataField.readObject("mutzarMechiraRecordData");
	kc.setValueAt("MS_POL","14743405");	
	ic.addElement(kc);
	

}
}
