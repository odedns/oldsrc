package composer.utils;
import com.ibm.dse.base.*;
import java.util.*;

/**
 * Insert the type's description here.
 * Creation date: (05/12/02 11:02:18)
 * @author: Oded Nissan
 */
public abstract class IndexedColUtils {
/**
 * IndexedColUtils constructor comment.
 */


/**
 * Insert the type's description here.
 * Find a keyecCollection in an IndexedCollection by a key field.
 * @param ic the IndexedCollection to search.
 * @param name the name of the field to search.
 * @param value the value of the field.
 * @return KeyedCollection a KeyedCollection in the IndexedCollection matching
 * the key field.
 * Creation date: (05/12/02 11:02:18)
 * @author: Oded
 */

public static KeyedCollection find(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	KeyedCollection found = null;

	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);			
			curr = kc.getValueAt(name);
			if(curr.equals(value)) {
				found = kc;
				break;
			}
		}
	} catch (Exception e ) {
			e.printStackTrace();
	}
	return(found);
}


public static int findIndex(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	int inx = -1;

	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);
			curr = kc.getValueAt(name);
			if(curr.equals(value)) {
				inx = i;
				break;
			}
		}
	} catch (Exception e ) {
			e.printStackTrace();
	}
	return(inx);
}


/**
 * Insert the type's description here.
 * Find an entry in an IndexedCollection by a key field.
 * @param ic the IndexedCollection to search.
 * @param name the name of the field to search.
 * @param value the value of the field.
 * @return boolean if there is a  KeyedCollection in the IndexedCollection matching
 * the key field.
 * Creation date: (05/12/02 11:02:18)
 * @author: Oded
 */
public static boolean contains(IndexedCollection ic, String name, Object value)
{
	KeyedCollection kc = find(ic, name, value);
	return(kc != null ? true : false);
}

/**
 * Insert the type's description here.
 * Return a string array containing the values of
 * a field in an indexedCollection.
 * @param ic the IndexedCollection to search.
 * @param name the name of the field to extract.
 * @return String[] an array of strings for the values of
 * the input field.
 * Creation date: (05/12/02 11:02:18)
 * @author: Oded
 */
public static String[] toStringArray(IndexedCollection ic, String name)
{
	String v[] = new String[ic.size()];
	KeyedCollection kc = null;

	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);
			v[i] = (String) kc.getValueAt(name);			
		}
	} catch (Exception e ) {
			e.printStackTrace();
			v = null;
	}
	return(v);

	
}


}
