package composer;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 2000 - All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 * 
 * DISCLAIMER:
 * The following [enclosed] code is sample code created by IBM
 * Corporation. This sample code is not part of any standard IBM product
 * and is provided to you solely for the purpose of assisting you in the
 * development of your applications. The code is provided 'AS IS',
 * without warranty of any kind. IBM shall not be liable for any damages
 * arising out of your use of the sample code, even if they have been
 * advised of the possibility of such damages.
 */
import com.ibm.dse.base.*;
/**
 * This class extends FormatDecorator. Adds/Removes a decorator which is the
 * contents of hostIdentifier attribute of a HostField instance plus the equal sign.
 * It is used by the sample application when formatting the data to be sent to the host.
 * The format implemented in class HostStringFormat does the same only for Strings.
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 */
public class HostDecorator extends FormatDecorator {
	private String hostId;
/**
 * Adds the decorator.
 * The decorator is the contents of the hostId attribute
 * plus the equal sign.
 * @return java.lang.String
 * @param aString java.lang.String
 */
public String addDecoration(String aString) throws DSEInvalidArgumentException {
	if (hostId.length() >0) return hostId + "=" +aString;//$NON-NLS-1$
	return aString;
}
/**
 * Formats the data element.
 * @return java.lang.String
 * @param aDataElement com.ibm.dse.base.DataElement
 * @exception com.ibm.dse.base.DSEInvalidClassException.
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 * @exception com.ibm.dse.base.DSEInvalidArgumentException.
 */
public String format(DataElement aDataElement) throws DSEInvalidClassException,DSEInvalidRequestException, DSEInvalidArgumentException {
	try {
		hostId = ((HostField)aDataElement).getHostIdentifier();
	} catch (ClassCastException e) { // aDataElement class doesn't extend HostField
   		hostId ="";//$NON-NLS-1$
	}	
	return 	addDecoration(getDecorated().format(aDataElement));
}
/**
 * Initializes an instance of HostDecorator with its attributes.
 * @return Object
 * @param aTag Tag
 */
public Object initializeFrom ( Tag aTag) {
	return this;
}
/**
 * Removes the decorator.
 * The decorator is the contents of the hostId attribute
 * plus the equal sign.
 * @return java.lang.String
 * @param aString java.lang.String
 * @exception com.ibm.dse.base.DSEInvalidArgumentException.
 */
public String removeDecoration(String aString) throws DSEInvalidArgumentException {
	int idx;
	if ((idx=aString.indexOf(hostId)) != -1)
	    return aString.substring(idx+hostId.length()+1);
	return aString;
}
/**
 * Returns a representation of HostDecorator.
 * @return java.lang.String
 */
public Vector toStrings() {
	Vector v = new Vector();
	if (getDecorated()!=null) 
		v.add(getDecorated().toStrings());
	String s="<"+getTagName()+" hostId = " + hostId;//$NON-NLS-2$//$NON-NLS-1$
	s=s+"/>";//$NON-NLS-1$
	v.addElement(s);
	return v;
}
/**
 * Unformats the data element.
 * @return com.ibm.dse.base.DataElement
 * @param aString java.lang.String
 * @param aDataElement com.ibm.dse.base.DataElement
 * @exception com.ibm.dse.base.DSEInvalidRequestException.
 * @exception com.ibm.dse.base.DSEInvalidArgumentException.
 */
public DataElement unformat(String aString, DataElement aDataElement) throws DSEInvalidRequestException,DSEInvalidArgumentException {
	hostId = ((HostField)aDataElement).getHostIdentifier();
	return getDecorated().unformat(removeDecoration(aString),aDataElement);
}
}
