package mataf.utils;
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
public static KeyedCollection findFirst(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	KeyedCollection found = null;

	if(null == value) {
		return(null);	
	}
	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);			
			curr = kc.getValueAt(name);			
			if(curr.toString().equals(value.toString())) {
				found = kc;
				break;
			}
		}
	} catch (Exception e ) {
			e.printStackTrace();
	}
	return(found);
}
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

public static IndexedCollection find(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	IndexedCollection found = new IndexedCollection();

	if(null == value) {
		return(null);	
	}
	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);			
			curr = kc.getValueAt(name);			
			if(curr.toString().equals(value.toString())) {
				found.addElement(kc);
			}
		}
	} catch (Exception e ) {
			e.printStackTrace();
	}
	return(found);
}

/**
 * Insert the type's description here.
 * Find a keyecCollection in an IndexedCollection by a key field substring.
 * @param ic the IndexedCollection to search.
 * @param name the name of the field to search.
 * @param value the substring value of the field.
 * @return KeyedCollection a KeyedCollection in the IndexedCollection matching
 * the key field.
 * @author: Oded
 */
public static IndexedCollection findEx(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	IndexedCollection found = new IndexedCollection();

	if(null == value) {
		return(null);	
	}
	try {
		for(int i=0; i < ic.size(); ++i) {
			kc = (KeyedCollection) ic.getElementAt(i);			
			curr = kc.getValueAt(name);			
			if(StringUtils.contains(curr.toString(),value.toString())) {
				found.addElement(kc);
			}
		}
	} catch (Exception e ) {
			e.printStackTrace();
	}
	return(found);
}


/**
 * find the index of the searched record.
 */
public static int findIndex(IndexedCollection ic, String name, Object value)
{
	Object curr=null;
	KeyedCollection kc = null;
	int inx = -1;
	
	if(null == value) {
		return(-1);	
	}
	
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
	KeyedCollection kc = findFirst(ic, name, value);
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
			v[i] = kc.getValueAt(name).toString();			
		}
	} catch (Exception e ) {
			e.printStackTrace();
			v = null;
	}
	return(v);
	
}

	/**
	 * search the IndexedCollection ic for entry name containing value.
	 * put that entry first.
	 * @param ic the IndexedCollection to process.
	 * @param name the name of the field to put first.
	 * @param value the value of the field to put first.
	 */
	public static void putFirst(IndexedCollection ic, String name, String value)
	{
		int index = IndexedColUtils.findIndex(ic,name,value);
		if(index > 0) {
			swap(ic, index,0);		
		}
	}
		
	/**
	 * swap two entries in the IndexedCollection.
	 * @param ic the IndexedCollection to operate on.
	 * @param i an index.
	 * @param j an index.
	 */
	public static void swap(IndexedCollection ic, int i, int j)
	{		
		java.util.Vector v = ic.getElements();
		Object o1 = v.get(i);
		Object o2 = v.get(j);
		v.setElementAt(o1,j);
		v.setElementAt(o2,i);		
	}
	
	/**
	 * copy the contents of one indexed collection 
	 * to another.
	 * @param from the IndexedCollection to copy from
	 * @param to the IndexedCollection to copy to.
	 * @exception DSEObjectNotFoundException in case of error.
	 */
	public static void copy(IndexedCollection from, IndexedCollection to) 
		throws DSEObjectNotFoundException
	{
		for(int i=0; i < from.size(); ++i) {
			DataElement kc = from.getElementAt(i);
			to.addElement(kc);		
		}	
	}


}
