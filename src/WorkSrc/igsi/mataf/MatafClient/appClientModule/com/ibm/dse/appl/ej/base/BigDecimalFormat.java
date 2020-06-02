package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.Enumeration;
import java.io.IOException;
import java.math.BigDecimal;
import com.ibm.dse.base.Constants;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.FieldFormat;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.FormatExternalizer;
import com.ibm.dse.base.JavaExtensions;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidArgumentException;

/**
 * The BigDecmialFormat class formats a BigDecmial object into 
 * a string.
 *
 * @see	java.math.BigDecimal;
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class BigDecimalFormat extends FieldFormat {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

/**
 * This constructor creates a BigDecmialFormat object. 
 */
public BigDecimalFormat ( ) {
}
/**
 * This constructor creates a BigDecmialFormat object.
 *
 * @param aName String - A formater name
 */
public BigDecimalFormat ( String aName) throws IOException {
	setName(aName);
	readExternal();
}
/**
 * Returns the string representation of the BigDecimal.
 *
 * @return String - A String respresentation of the BigDecimal
 * @param aValue BigDecimal - A BigDecimal
 */
public String format(BigDecimal aValue) {

	return aValue.toString();
}
/** 
 * Accesses the value of the DataField and checks that it is a valid BigDecmial. 
 *
 * @return String - A string respresentation of the BigDecmial
 * @param aDataField DataField - A DataField
 */
	
public String formatField(DataField aDataField) throws DSEInvalidArgumentException, DSEInvalidClassException{

	BigDecimal value = null;
	
	if (aDataField.getValue() instanceof BigDecimal) {
		value = (BigDecimal) aDataField.getValue();
		
	} else {
		throw new DSEInvalidClassException(
			DSEException.harmless,
			aDataField.getValue().getClass().getName(),
			"Invalid Class: class " + aDataField.getValue().getClass().getName()+
			"is not valid " + ". Format: "+getName() +
			", DataElement: " + getDataElementName() +
			", Process: formatField");
	}
	
	return format(value);
}
/**
 * Initializes a BigDecmialFormat with the aTag attributes.
 * 
 * @return Object - A FormatExternalizer
 * @param aTag - Configuration parameter
 * @exception IOException - Throws when a problem occurs in accessing SGML files
 * @see	com.ibm.dse.base.Tag
 */
public Object initializeFrom(Tag aTag) throws IOException {
	
	setDataElementName(Constants.A_void);
	Enumeration attributes = aTag.getAttrList().elements();
	TagAttribute attribute;
	
	while (attributes.hasMoreElements()) {
		attribute = (TagAttribute) attributes.nextElement();
		if (attribute.getName().equals(Constants.DataName)) {
			setDataElementName((String)attribute.getValue());
		}
	}	
	return ((FormatExternalizer) getExternalizer()).linkToDecorators(this, aTag);
}
/**
 * Converts the BigDecimalFormat to a String.
 *
 * @return String - A String respresentation of this object
 */
public String toString() {
	String s="<"+getTagName()+" ";
	s=JavaExtensions.addAttribute(s,"dataName",getDataElementName());
	s=s+">";
	return s;
}
/**
 * Updates the DataField value by interpreting the input string.
 *
 * @return DataField - A DataField contains a new BigDecimal
 * @param aString String - A String respresentation of a BigDecimal
 * @param aDataField DataField - A given DataField
 */
public DataField unformatField(String aString, DataField aDataField) throws DSEInvalidArgumentException {
	
	BigDecimal value = new BigDecimal(aString);
	aDataField.setValue(value);
	return aDataField;
}
}
