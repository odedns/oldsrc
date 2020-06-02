package composer;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 1999 - All Rights Reserved
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
import  com.ibm.dse.base.*;
/**
 * This class extends StringFormat. Works with HostField instances of type String.
 * In the format process adds the contents of hostIdentifier attribute of the HostField
 * instance plus the equal sign to the string value of the instance.
 * The unformat process does the opposite.
 * It is used by the sample application when formatting the data to be sent to the host.
 * The decorator implemented in class HostDecoratort does the same for any type of data
 * (no just for strings).
 * @copyright(c) Copyright IBM Corporation 1998, 1999.
 */
public class HostStringFormat extends StringFormat {
/**
 * HostStringFormat default constructor.
 */
public HostStringFormat() {
	super();
}
/**
 * Constructor for the Class HostStringFormat.
 * @param aName java.lang.String
 * @exception java.io.IOException.
 */
public HostStringFormat(String aName) throws java.io.IOException {
	super(aName);
}
/**
 * Format process. If aDataField is of type HostField, contructs a string with
 * the value of hostIdentifier attribute plus the equal signe plus the contents 
 * of the value attribute.
 * @return java.lang.String
 * @param aDataField DataField
 */
public String formatField( DataField aDataField) throws com.ibm.dse.base.DSEInvalidClassException {
	String hostId;
	try {
		HostField hField;
		hField =(HostField)aDataField;
		hostId=hField.getHostIdentifier()+"=";//$NON-NLS-1$
	}		
	catch (ClassCastException e) { // aDataField class doesn't extend HostField
   	hostId ="";//$NON-NLS-1$
	}	
	
	return hostId + super.formatField(aDataField);
}
/**
 * Unformat process. If aDataField is of type HostField, remove from the received
 * string anything before the equal sign and update the value of aDataField with
 * the resulting string.
 * @return DataField
 * @param aDataField DataField
 * @param aString String
 */
public DataField unformatField(String aString, DataField aDataField) {
	int idx;
	String tmpString;
	String hostId;
 	try  {
 		hostId = ((HostField)aDataField).getHostIdentifier()+"=";//$NON-NLS-1$
	}	
	catch (ClassCastException e) { // aDataField class doesn't extend HostField
  	return null;
}	

	if ((idx=aString.indexOf(hostId)) != -1) {
	    tmpString = aString.substring(idx+hostId.length());
			aDataField.setValue(tmpString);
			return aDataField;
	} else						
		return null;
	}
}
