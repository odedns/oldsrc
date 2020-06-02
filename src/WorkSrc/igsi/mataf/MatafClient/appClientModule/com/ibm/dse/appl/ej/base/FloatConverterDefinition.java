package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.FloatConverter;
import com.ibm.dse.base.*;
import com.ibm.dse.base.types.DSETypeException;
/**
 * FloatConverterDefintion is the class for all elements that externally 
 * define CBTF FloatConverters components as DataElements. The value of these
 * definitions is an instance of the com.ibm.dse.gui.FloatConverter class.
 * 
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class FloatConverterDefinition extends ConverterDefinition
{
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	
	protected String  errorMessage 	= DEFAULT_ERROR;
	protected String  decSep        = DEFAULT_DECSEP;
	protected int     decimals      = DEFAULT_DECIMALS;
	protected String  separator		= DEFAULT_SEPARATOR;
	protected boolean isConvertible = DEFAULT_ISCONVERTIBLE;
	// String PrinterConstants definition....
	protected final String ERROR         = "errorMessage";
	protected final String DECSEP        = "decSep";
	protected final String DECIMALS      = "decimals";
	protected final String SEPARATOR     = "sep";
	protected final String ISCONVERTIBLE = "isConvertible";
	protected final String TRUE          = "true";
	protected final String FALSE         = "false";
	// ATTRIBUTE DEFAULT DEFINITIONS
	protected static final String  DEFAULT_ERROR         = "** Error **";
	protected static final String  DEFAULT_DECSEP        = ",";
	protected static final int     DEFAULT_DECIMALS      = 2;
	protected static final String  DEFAULT_SEPARATOR     = ".";
	protected static final boolean DEFAULT_ISCONVERTIBLE = true;
/**
 * This constructor creates a FloatConverterDefintion object. 
  */
public FloatConverterDefinition() 
{
}
/**
 * Required because of superclass.
 * 
 * @return java.lang.Object
 * @exception java.lang.CloneNotSupportedException - The exception description
 */
public Object clone() throws java.lang.CloneNotSupportedException {
	FloatConverterDefinition aDefinition = new FloatConverterDefinition();
	aDefinition.setErrorMessage(this.getErrorMessage());
	aDefinition.setDecimals(this.getDecimals());
	aDefinition.setDecimalSeparator(this.getDecimalSeparator());
	aDefinition.setIsConvertible(this.isIsConvertible());
	aDefinition.setSeparator(this.getSeparator());
	try {
		aDefinition.setValue(getValue());
	} catch (DSEInvalidArgumentException e) {
		if (Trace.doTrace("JVIEW", Trace.Medium, Trace.Error)) {
			String aMessage = e.getMessage();
			Trace.trace("JVIEW", Trace.Medium, Trace.Error, null, aMessage);
			System.out.println(aMessage);
		}
	}
	return aDefinition;
}
/**
 * Returns the value of the <I>decimals</I> property.
 * @return int - The current value of <I>decimals</I> property
 */
public int getDecimals()
{
	return decimals;
}
/**
 * Returns the value of the <I>decimalSeparator</I> property.
 * @return java.lang.String - The current value of <I>decimalSeparator</I> property
 */
public String getDecimalSeparator() 
{
	return decSep;
}
/**
 * Returns the value of the <I>errorMessage</I> property.
 * @return java.lang.String - The current value of <I>errorMessage</I> attribute
 */
public java.lang.String getErrorMessage() 
{
	return errorMessage;
}
/**
 * Returns the value of the <I>separator</I> property.
 * @return java.lang.String - The current value of <I>separator</I> property
 */
public java.lang.String getSeparator() 
{
	return separator;
}
/**
 * Initializes a <I>FloatConverterDefinition</I> object with the <I>aTag</I> attributes.
 * @return Object - An instance of <I>FloatConverterDefinition</I>
 * @param aTag com.ibm.dse.base.Tag - Contains the name and attributes of the object to be created
 * @exception java.io.IOException - The object can not be created
 */
public Object initializeFrom(com.ibm.dse.base.Tag aTag) throws java.io.IOException 
{
	TagAttribute attribute;
	String attName;
	Object attValue;
	for (int i=aTag.getAttrList().size();--i >= 0;) 
	{
		attribute = (TagAttribute)aTag.getAttrList().elementAt(i); 
		attName = attribute.getName();
		attValue = attribute.getValue();
		if (attName.equals(Constants.Id)) setName( (String) attValue ); 
		else if (attName.equals(DECSEP)) setDecimalSeparator((String)attValue);
		else if (attName.equals(ERROR)) setErrorMessage((String)attValue);
		else if (attName.equals(SEPARATOR)) setSeparator((String)attValue);
		else if (attName.equals(ISCONVERTIBLE)) setIsConvertible(attValue.equals(TRUE));
		else if (attName.equals(DECIMALS)) setDecimals(Integer.parseInt((String)attValue));
	}
	value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
	return this;
}
/**
 * Returns the value of the <I>isConvertible</I> property.
 * @return boolean - The current value of <I>isConvertible</I> property
 */
public boolean isIsConvertible() 
{
	return isConvertible;
}
/**
 * Set the value of the <I>decimals</I> property.
 * @param newDecimals int - The new value of the property
 */
public void setDecimals(int newDecimals) 
{
	decimals = newDecimals;
	value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
}
/**
 * Set the value of the <I>decimalSeparator</I> property.
 * @param newDecimalSeparator java.lang.String - The new value of the property
 */
public void setDecimalSeparator(java.lang.String newDecimalSeparator) 
{
	if (newDecimalSeparator!=null)
	{
		decSep = newDecimalSeparator;
		value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
	}
}
/**
 * Set the value of the <I>errorMessage</I> property.
 * @param newErrorMessage java.lang.String - The new value of the property
 */
public void setErrorMessage(java.lang.String newErrorMessage) 
{
	if (newErrorMessage != null)
	{
		errorMessage = newErrorMessage;
		value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
	}
}
/**
 * Set the value of the <I>isConvertible</I> property.
 * @param newIsConvertible boolean - The new value of the property
 */
public void setIsConvertible(boolean newIsConvertible) 
{
	isConvertible = newIsConvertible;
	value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
}
/**
 * Set the value of the <I>separator</I> property.
 * @param newSeparator java.lang.String - The new value of the property
 */
public void setSeparator(java.lang.String newSeparator) 
{
	if (newSeparator != null)
	{
		separator = newSeparator;
		value = new FloatConverter(errorMessage,separator,decSep.charAt(0),decimals,isConvertible);
	}

}
/**
 * If <I>newValue</I> is an instance of FloatConverter, it sets the value of the <I>value</I> property.
 * @param newValue java.lang.Object - The new value of the property
 * @exception DSEInvalidArgumentException - The parameter is not a FloatConverter Object 
 */
public void setValue(Object newValue) throws com.ibm.dse.base.DSEInvalidArgumentException 
{
	if (newValue instanceof FloatConverter)
	{
		value= (FloatConverter)newValue;
	}
	else 
		throw new DSEInvalidArgumentException(DSEException.harmless, newValue.toString(),
											"InvalidArgument: " + newValue + " should be an instance of FloatConverter.");
}
/**
 * Returns an SGML representation of this FloatConverterDefinition Object.
 * @return java.lang.String
 */
public String toString() 
{
	String s=Constants.Smaller+getTagName()+Constants.Blanc;
	s=JavaExtensions.addAttribute(s,Constants.Id,getName());
	if (!getDecimalSeparator().equals(DEFAULT_DECSEP)) 	s=JavaExtensions.addAttribute(s,DECSEP,getDecimalSeparator());
	if (!getErrorMessage().equals(DEFAULT_ERROR)) 		s=JavaExtensions.addAttribute(s,ERROR,getErrorMessage());
	if (isIsConvertible()!= DEFAULT_ISCONVERTIBLE) 		s=JavaExtensions.addAttribute(s,ISCONVERTIBLE,TRUE);
	if (!getSeparator().equals(DEFAULT_SEPARATOR)) 		s=JavaExtensions.addAttribute(s,SEPARATOR,getSeparator());
	if (getDecimals()!= DEFAULT_DECIMALS) 				s=JavaExtensions.addAttribute(s,DECIMALS,Integer.toString(getDecimals()));
	s=s+Constants.Bigger;
	return s;
}
/**
 * Required by the superclass.
 * 
 * @return java.lang.Object
 * @param anObject java.lang.Object
 * @exception com.ibm.dse.base.types.DSETypeException
 */
public Object validate(Object anObject) throws com.ibm.dse.base.types.DSETypeException {
	String objectType = anObject.getClass().getName();
	if(!objectType.equals("java.lang.Float")){
		throw new DSETypeException(DSETypeException.critical, "Float type required", "The object is not of type java.lang.Float.", null);
	}
	return anObject;
}
/**
 * Required by the superclass.
 * 
 * @return java.lang.Object
 * @param anObject java.lang.Object
 * @param conversionType java.lang.String 
 * @exception com.ibm.dse.base.types.DSETypeException
 */
public Object validate(Object anObject, String conversionType) throws com.ibm.dse.base.types.DSETypeException {
	return validate(anObject);
}
}
