package mataf.utils;
import com.ibm.dse.dw.model.*;
import java.util.*;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class InstanceUtils {

	/**
	 * Constructor for InstanceUtils.
	 */
	private  InstanceUtils() {
		super();
	}


	
	public static DataPair[] toDataPairs(Instance ins)
	{
		ArrayList ar = toArrayList(ins);
		DataPair v[] = new DataPair[ar.size()];
		v = (DataPair[])ar.toArray(v);
		return(v);	
	}
	/**
	 * convert a wb instance object into an array
	 * of DataPair objects containing key and value.
	 */	
	public static ArrayList toArrayList(Instance ins)
	{
		ArrayList ar = new ArrayList();		
		Enumeration e = ins.getAttributeNames();
		String name = null;
		String value = null;
		DataPair pair = null;
				
		
		while(e.hasMoreElements()) {
			name = (String) e.nextElement();
			value = ins.getValue(name);
			pair = new DataPair(name,value);
			ar.add(pair);
		}
		
		return(ar);
	}
	
	/**
	 * check if instance already exists. if it does add counter
	 * to the new name and return. it.
	 * else return the original name.
	 */
	public static String checkExisting(String id, int counter)
	{
		
		return(id);	
	}

}
