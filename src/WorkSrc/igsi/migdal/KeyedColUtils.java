package migdal.utils;
import com.ibm.dse.base.*;
import java.util.*;
import java.text.*;

/**
 * Insert the type's description here.
 * Creation date: (05/12/02 11:01:44)
 * @author: Oded
 */
public abstract class KeyedColUtils {
/**
 * KeyedColUtils constructor comment.
 */
public KeyedColUtils() {
	super();
}
/**
 * Insert the method's description here.
 * Get a string from the KeyedCollection field
 * and parse it as an integer.
 * if parsng fails an exception is thrown.
 * @param kc the KeyedCollection to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return int the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static int getInt(KeyedCollection kc, String name) throws Exception
{
	String s = (String) kc.getValueAt(name);
	int n = Integer.parseInt(s);
	return(n);
}


/**
 * Insert the method's description here.
 * Get a string from the KeyedCollection field
 * and parse it as a float.
 * if parsng fails an exception is thrown.
 * @param kc the KeyedCollection to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return float the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static float getFloat(KeyedCollection kc, String name) throws Exception
{
	String s = (String) kc.getValueAt(name);
	float f = Float.parseFloat(s);
	return(f);
}





/**
 * Insert the method's description here.
 * Get a string from the KeyedCollection field
 * and parse it as a float.
 * if parsng fails an exception is thrown.
 * @param kc the KeyedCollection to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return float the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static Date getDate(KeyedCollection kc, String name)
{

	Date d = null;
	try {
		String s = (String) kc.getValueAt(name);
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
 * Get a string from the KeyedCollection field
 * and parse it as a boolean.
 * if parsng fails an exception is thrown.
 * @param kc the KeyedCollection to get the field from.
 * @param name the name of the field to extract.
 * Creation date: (05/12/02 11:44:25)
 * @return boolean the field value.
 * @throws Exception if an error occured in parsing
 * or accessing the context.
 */
public static boolean getBoolean(KeyedCollection kc, String name) throws Exception
{

	boolean b1 = false;
	Boolean b  = (Boolean ) kc.getValueAt(name);
	if(null != b) {
		b1 = b.booleanValue();
	}

	return(b1);

}


}