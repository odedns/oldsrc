package mataf.common.operationsteps;

import com.ibm.dse.base.CompareAssertion;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Settings;
import com.ibm.dse.base.Trace;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompareAssertionAndConstants extends CompareAssertion {
	
	public int execute() throws Exception {
		KeyedCollection kc = getParams();
		String dataElementNameA = null;
		String dataElementNameB = null;
		String constant = null;
		String operator = null;
		Object objA = null;
		Object objB = null;
				
		try {
			dataElementNameA = (String) kc.getValueAt("dataElementA");
			operator = (String) kc.getValueAt("operator");
			objA = getValueAt(dataElementNameA);
			if (objA == null)
				throw new Exception("The value of the data element named: " + dataElementNameA + " is null");
		} catch (Exception e) {
			if (Trace.doTrace("#CORE", 256, 8))
					Trace.trace("#CORE", 256, 8, Settings.getTID(), "CompareAssertionAndConstants.execute: " + e);
				throw e;
		}
		
		constant = (String) kc.tryGetValueAt("const");		
		if (constant != null) {
			objB = constant;
		} else {
			try {				
				dataElementNameB = (String) kc.getValueAt("dataElementB");
				objB = getValueAt(dataElementNameB);
				if (objB == null)
					throw new Exception("The value of the data element named: " + dataElementNameB + " is null");
			} catch (Exception e) {
				if (Trace.doTrace("#CORE", 256, 8))
					Trace.trace("#CORE", 256, 8, Settings.getTID(), "CompareAssertionAndConstants.execute: " + e);
				throw e;
			}
		}
		
		if (!objA.getClass().getName().equals(objB.getClass().getName()))
			throw new Exception("Type mismatch: " + objA.getClass().getName() + " - " + objB.getClass().getName());
		if (objA instanceof String)
			return compareString((String) objA, (String) objB, operator);
		if (objA instanceof Integer)
			return compareInt((Integer) objA, (Integer) objB, operator);
		if (objA instanceof Long)
			return compareLong((Long) objA, (Long) objB, operator);
		if (objA instanceof Double)
			return compareDouble((Double) objA, (Double) objB, operator);
		if (objA instanceof Float)
			return compareFloat((Float) objA, (Float) objB, operator);
		else
			throw new Exception("Comparation not done");

	}

}
