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
 * This class extends DataField. Adds a new attribute (hostIdentifier)
 * to hold the host identification. It is used by the sample application
 * when formatting the data to be sent to the host. 
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 */
public class HostField extends DataField {
	protected String hostIdentifier;
/**
 * HostField default constructor.
 */
public HostField() {
	super();
}
/**
 * HostField constructor with name.
 * @param aName java.lang.String
 * @exception java.io.IOException.
 */
public HostField(String aName) throws java.io.IOException {
	super(aName);
}
/**
 * Gets the hostIdentifier attribute.
 * @return java.lang.String
 */
public String getHostIdentifier ( ) {
	return  hostIdentifier;
}
/**
 * Initializes an instance of HostField with its attributes.
 * @return Object
 * @param aTag Tag
 * @exception java.io.IOException.
 */
public Object initializeFrom(Tag aTag) throws java.io.IOException {
	super.initializeFrom(aTag);
	for (int i=0;i<aTag.getAttrList().size();i++) {
		TagAttribute attribute = (TagAttribute) aTag.getAttrList().elementAt(i);
		if (attribute.getName().equals("hostId")) setHostIdentifier((String) (attribute.getValue()));//$NON-NLS-1$
	}		
	return this;
}
/**
 * Sets the hostIdentifier attribute.
 * @param hostId java.lang.String
 */
public void setHostIdentifier (String hostId ) {
	hostIdentifier = hostId;
	return;
}
/**
 * Sets the value of hostIdentifier to aValue.
 * @param aValue int
 */
public void setValue(int aValue) {
	value = new String(Integer.toString(aValue));
}
/**
 * Returns a representation of HostField.
 * @return java.lang.String
 */
public String toString() {
	String s="<"+getTagName()+" ";//$NON-NLS-2$//$NON-NLS-1$
	s=JavaExtensions.addAttribute(s,"id",getName());//$NON-NLS-1$
	if (getValue()!=null) s=JavaExtensions.addAttribute(s,"value",""+getValue());//$NON-NLS-2$//$NON-NLS-1$
	s=JavaExtensions.addAttribute(s,"description",getDescription());//$NON-NLS-1$
	s=JavaExtensions.addAttribute(s,"hostId",getHostIdentifier());//$NON-NLS-1$
	s=s+"/>";//$NON-NLS-1$
	return s;
}
}
