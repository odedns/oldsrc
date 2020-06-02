package composer.utils;
import com.ibm.dse.base.*;
import java.util.Date;

/**
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ContextUtils {

	/**
	 * Constructor for ContextUtils.
	 */
	private ContextUtils() 
	{
		super();
	}
	
/**
 * Insert the method's description here.
 * Get a string from the Context field
 * and parse it as an integer.
 * if parsng fails an exception is thrown.
 * @param ctx the Context to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return int the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static int getInt(Context ctx, String name) throws Exception
{
	String s = (String) ctx.getValueAt(name);
	int n = Integer.parseInt(s);
	return(n);
}


/**
 * Insert the method's description here.
 * Get a string from the Context field
 * and parse it as a float.
 * if parsng fails an exception is thrown.
 * @param ctx the Context to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return float the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static float getFloat(Context ctx, String name) throws Exception
{
	String s = (String) ctx.getValueAt(name);
	float f = Float.parseFloat(s);
	return(f);
}

/**
 * Insert the method's description here.
 * Get a string from the Context field
 * and parse it as a float.
 * if parsng fails an exception is thrown.
 * @param ctx the Context to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return float the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static Date getDate(Context ctx, String name)
{

	Date d = null;
	try {
		String s = (String) ctx.getValueAt(name);
		java.text.DateFormat df = java.text.DateFormat.getDateInstance();
		d = df.parse(s);

	} catch(Exception e) {
		e.printStackTrace();
		d = null;
	}
	return(d);
}

/**
 * Insert the method's description here.
 * Get a string from the Context field
 * and parse it as a boolean.
 * if parsng fails an exception is thrown.
 * @param ctx the Context to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return boolean the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static boolean getBoolean(Context ctx, String name) throws Exception
{

	boolean b1 = false;
	Boolean b  = (Boolean ) ctx.getValueAt(name);
	if(null != b) {
		b1 = b.booleanValue();
	}

	return(b1);
}




/* main test */	
	public static void main(String[] args) {
		try {
			Context.reset();
			Settings.reset("d:/work/dse/client/dse.ini");
			Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);
			Context ctx = (Context) Context.readObject("accountViewClientCtx");
			ctx.setValueAt("AccountNumber", "100");
			ctx.setValueAt("Date", new Date());
			
			for(int i=0; i < 2; ++i) {
				ctx.setValueAt("accountStatementDetails." + i + ".OpnAmount","100");									
			}
						
			System.out.println("ctx=" + ctx.toString());
			KeyedCollection kc = ctx.getKeyedCollection();
			System.out.println("kc =" + kc.toString());
			Hashtable hash = kc.getElements();
			System.out.println("hash  = " + hash.toString());
			
			KeyedCollection kCol = (KeyedCollection)DataElement.readObject("ctxData");
			kCol.setValueAt("kColl", kc);
			System.out.println("got new ctx data\nkCol = " + kCol.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
