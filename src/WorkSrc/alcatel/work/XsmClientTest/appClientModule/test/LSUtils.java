/*
 * Created on 06/11/2005
 */
package test;

import hoshen.xsm.lightsoft.corba.globaldefs.NameAndStringValue_T;

import org.apache.log4j.Logger;

/**
 * @author odedn
 *  
 */
public class LSUtils {

	private final static Logger log = Logger.getLogger(LSUtils.class);

	public final static String MANAGED_ELEMENT = "ManagedElement";
	public final static String EMS = "EMS";
	public final static String PTP = "PTP";
	private final static String ME_ADDRESS = "LSNExt_NetworkAddress";

	/**
	 * Empty private constructor. Cannot instantiate class.
	 */
	private LSUtils() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Print an Name and String Value object.
	 * 
	 * @param nsv
	 *            and array of NameAndStringValue objects.
	 * @return String the value field of the first NameAndStringValue object.
	 */
	public static String printNsvString(NameAndStringValue_T nsv[]) {

		if (null == nsv || nsv.length == 0) {
			return (null);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nsv.length; ++i) {
			sb.append("name=");
			sb.append(nsv[i].name);
			sb.append(";value=");
			sb.append(nsv[i].value);
			if (i < nsv.length - 1) {
				sb.append(';');
			}
		}
		return (sb.toString());
	}

	/**
	 * @param portName
	 * @return the Managed Element name in hoshen
	 * 
	 * Managed Element name in hoshen = "[EMS of port name]-[ManagedElement of port name]"
	 */  
	public static String extractMEFromPort(NameAndStringValue_T portName[])
	{
		String ems = extractValue(portName , EMS);
		String me = extractValue(portName , MANAGED_ELEMENT);
		return ems + "-" + me;
	}

	/**
	 * Convert a Name and String Value object to a single String
	 * 
	 * @param nsv
	 *            and array of NameAndStringValue objects.
	 * @return String the value field of the first NameAndStringValue object.
	 */
	public static String nsvToString(NameAndStringValue_T nsv[]) {

		if (null == nsv || nsv.length == 0) {
			return (null);
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nsv.length; ++i) {
			sb.append(nsv[i].value);
			if (i < nsv.length - 1) {
				sb.append('-');
			}
		}
		return (sb.toString());
	}
	/**
	 * Extract a value from a NameAndStringValue_T array by a given name
	 * 
	 * @param nsv
	 *            array of NameAndStringValue_T
	 * @param valueName
	 *            the name of the value to be extracted
	 * @return String value
	 */
	public static String extractValue(NameAndStringValue_T nsv[],
			String valueName) {
		if (null == nsv || nsv.length == 0) {
			return (null);
		}
		for (int i = 0; i < nsv.length; ++i) {
			if (nsv[i].name.equals(valueName)) {
				return nsv[i].value;
			}
		}
		return null;
	}
	
}
