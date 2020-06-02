package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.ObjectFormat;
import com.ibm.dse.base.Tag;
import com.ibm.dse.base.TagAttribute;
import com.ibm.dse.base.Constants;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.ListResourceBundle;
import java.math.BigDecimal;

/**
 * The ConvertAmountFormat class is used to format a 
 * BigDecmial based on a currency code.
 * The Amount is stored in a database as a BigDecimal with the decimal moved
 * to the far right.  Based on the currency code stored with
 * the Currency Data Name, we can shift the decimal back to its own place.
 * It is used with the HashtableFormat.
 *
 * @see com.ibm.dse.base.ObjectFormat
 * @see	com.ibm.dse.base.HashtableFormat 
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class ConvertAmountFormat extends ObjectFormat {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	private String amountDataName 	= null;
	private String currencyDataName	= null;
	private ListResourceBundle resource = null;

	private final String defaultResourceBundleName = "com.ibm.dse.appl.ej.base.CurrencyDecimalResourceBundle";

/**
 * Formats aDataElement and returns it as an Object. This method should only be invoked by the format() method of HashtableFormat because this method
 * does not return a String.
 *
 * @param aDataElement DataElement - The data element within the operation context to be formatted
 * @return Object - The value in the data element aDataElement
 * @see	com.ibm.dse.base.DataElement
 */
public Object format(DataElement aDataElement) {
	
	return aDataElement.getValue();
}
/** 
 * Gets an amount data name. 
 *
 * @return String - An amount data name
 */  
protected String getAmountDataName() {

	return amountDataName;
}
/** 
 * Gets a currency data name. 
 *
 * @return String - A currency data name
 */  
protected String getCurrencyDataName() {

	return currencyDataName;
}
/** 
 * Gets a resource bundle.
 *
 * @return ListResourceBundle - A resource bundle
 * @see	java.util.ListResourceBundle
 */  
protected ListResourceBundle getResourceBundle() {

	if (resource == null) {
		resource = (ListResourceBundle) ResourceBundle.getBundle(defaultResourceBundleName);
	}
	
	return resource;
}
/**
 * Initializes an ConvertAmountFormat with the Tag attributes.
 *
 * @param aTag Tag, a Tag
 * @return Object - This object
 * @exception IOException - Throws when problem occurs in accessing SGML files
 * @exception DSEException - Throws when problem occurs in DSE
 * @see	com.ibm.dse.base.Tag
 */
public Object initializeFrom(Tag aTag) throws IOException, DSEException {
	
	setDataElementName(Constants.A_void);
	
	for (Enumeration e = aTag.getAttrList().elements(); e.hasMoreElements();) {
		TagAttribute attr = (TagAttribute) e.nextElement();
		
		if (attr.getName().equals(Constants.DataName)) setDataElementName((String)attr.getValue());
		if (attr.getName().equals("amountDataName")) setAmountDataName((String)attr.getValue()); 
		if (attr.getName().equals("currencyDataName")) setCurrencyDataName((String)attr.getValue());
		if (attr.getName().equals("resourceBundleName")) setResourceBundle((String)attr.getValue());  
	}
	return this;
}
/** 
 * Set an amount data name.
 *
 * @param String - An amount data name
 */  
protected void setAmountDataName(String dataName) {
	
	amountDataName = dataName;
}
/** 
 * Set a currency data name.
 *
 * @param String - A currency data name.
 */  
protected void setCurrencyDataName(String dataName) {

	currencyDataName = dataName;
}
/** 
 * Set a resource bundle by giving a resource bundle name.
 *
 * @param String - A resource bundle name
 */  
protected void setResourceBundle(String rbName) {

	resource = (ListResourceBundle) ResourceBundle.getBundle(rbName);	
}
/**
 * Shift the decimal place of the BigDecimal stored with the AmountDataName.
 * Number of decimal place can be located in a reource bundle using the
 * currency code as a key.  This method should only be invoked
 * from the unformat() method of HashtableFormat.
 *
 * @param aValue Object - A value, not used in this case
 * @param aDataElement DataElement - The data element within the operation context to be formatted
 * @return DataElement - A DataElement
 * @see	com.ibm.dse.base.DataElement
 */
public DataElement unformat(Object aValue, DataElement aDataElement) throws DSEInvalidArgumentException {

	try {

		// get currency code from the given DataElement using the defined currencyDataName
		String currCode = (String) aDataElement.getElementAt(getCurrencyDataName()).getValue();

		// get amount from the given DataElement using the defined amountDataName
		BigDecimal amount = (BigDecimal) aDataElement.getElementAt(getAmountDataName()).getValue();

		// get number of decimal place from resource bundle using currency code
		int decPlace = 0;
		if (currCode != null) {
			decPlace = ((Integer) getResourceBundle().getObject(currCode)).intValue();
		} 

		// shift decimal place
		if (amount != null) {
			aDataElement.getElementAt(getAmountDataName()).setValue(amount.movePointLeft(decPlace));
		}

	} catch (DSEObjectNotFoundException e) {

		throw new DSEInvalidArgumentException(e.getSeverity(), e.getCode(), e.getMessage());
	}
	
	return aDataElement;
}
}
