package work.composer;


/**
 * Compares two objects of type com.ibm.dse.base.KeyedCollection.
 * Creation date: (20/05/01 09:54:17)
 * @author: Avi
 */
import java.text.*;
import java.util.*;
import java.math.*;
import com.ibm.dse.base.*;
import migdal.operations.templates.*;
import migdal.utils.*;
public class KeyedCollectionComparator implements java.util.Comparator {
	protected java.lang.String fieldName = null;
	protected String type = null;
	protected boolean ascendingOrder = true;
/**
 * Creates KeyedCollectionComparator object.
 */
public KeyedCollectionComparator() {
	super();
}
/**
 * Creates KeyedCollectionComparator object.
 * Creation date: (20/05/01 09:56:18)
 * @param param java.lang.String
 */
public KeyedCollectionComparator(String kCollName,boolean newSortingOrder) {
		super();
		fieldName = kCollName;
		ascendingOrder = newSortingOrder;
}
/**
 * Creates KeyedCollectionComparator object.
 * Creation date: (20/05/01 09:56:18)
 * @param param java.lang.String
 */
public KeyedCollectionComparator(String kCollName,boolean newSortingOrder,String newType) {
		super();
		fieldName = kCollName;
		ascendingOrder = newSortingOrder;
		type = newType;
}
/**
 * Compares two keyed collection objects and retrieves value correspondingly.
 * Creation date: (20/05/01 09:57:54)
 * @return int
 * @param ob1 java.lang.Object
 * @param ob2 java.lang.Object
 */
public int compare(Object o1, Object o2) throws ClassCastException {
	try {
		//get objects from context
		
		if(o1 instanceof KeyedCollection && o2 instanceof KeyedCollection)
		{
		o1 = ((KeyedCollection) o1).getValueAt(fieldName);
		o2 = ((KeyedCollection) o2).getValueAt(fieldName);
		}
		if((o1 instanceof Dictionary && o2 instanceof Dictionary))
		{
		o1 = ((Dictionary) o1).get(fieldName);
		o2 = ((Dictionary) o2).get(fieldName);
		}	
		
		

		if (o1 instanceof String && o2 instanceof String) {
			if (type != null && type.equals("date")) {
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				return getDirectionResult(
					(dateFormatter.parse((String) o1)).compareTo(dateFormatter.parse((String) o2)));
			}
			try {
				Object oo1 = new Double((String) o1);
				Object oo2 = new Double((String) o2);
				// if not number, exception here.
				o1 = oo1;
				o2 = oo2;
			} catch (NumberFormatException ex) {
				// not Number..
			}
		}
		// check if field is boolean
		if (o1 instanceof Boolean && o2 instanceof Boolean) {
			Object oo1 = o1.toString();
			Object oo2 = o2.toString();
			o1 = oo1;
			o2 = oo2;
		}
		//compare algorythm due to type
		if (o1 instanceof Comparable && o2 instanceof Comparable) {
			return getDirectionResult(((Comparable) o1).compareTo((Comparable) o2));
		}
		Tracer.trace("Can't compare - " + o1 + " , " + o2);

	} catch (DSEObjectNotFoundException e) {
		Tracer.trace("KeyedCollectionComparator.compare - " + e);
	} catch (ParseException e) {
		Tracer.trace("KeyedCollectionComparator.compare - " + e);
	} 
	return 0;
}
/**
 * Insert the method's description here.
 * Creation date: (23/07/01 12:36:29)
 * @return int
 * @param result int
 */
int getDirectionResult(int result)
{
	if (!ascendingOrder)
	{
		//change value sign to opposite for reverse sort direction
		if (result > 0)
		{
			result = -1;
		} else
			if (result < 0)
			{
				result = 1;
			}
	}
	return result;
}
}
