package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.IntegerConverter;
import com.ibm.dse.base.*;
import com.ibm.dse.base.types.DSETypeException;
/**
 * IntegerConverterDefintion is the class for all elements that externally 
 * define CBTF IntegerConverters components as DataElements. The value of these
 * definitions is an instance of the com.ibm.dse.gui.IntegerConverter class.
 * 
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class IntegerConverterDefinition extends ConverterDefinition
{
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	
	protected String errorMessage = DEFAULT_ERROR;
	protected String separator    = DEFAULT_SEPARATOR;
	// String PrinterConstants definition....
	protected final String ERROR      = "errorMessage";
	protected final String SEPARATOR  = "sep";
	// ATTRIBUTE DEFAULT DEFINITIONS
	protected static final String  DEFAULT_ERROR      = "** Error **";
	protected static final String  DEFAULT_SEPARATOR  = ".";
/**
 * This constructor creates a IntegerConverterDefintion object. 
  */
public IntegerConverterDefinition() 
{
}
/**
 * Required because of superclass.
 * 
 * @return java.lang.Object
 * @exception java.lang.CloneNotSupportedException - The exception description
 */
public Object clone() throws java.lang.CloneNotSupportedException {
	IntegerConverterDefinition aDefinition = new IntegerConverterDefinition();
	aDefinition.setErrorMessage(this.getErrorMessage());
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
 * Initializes an <I>IntegerConverterDefinition</I> object with the <I>aTag</I> attributes.
 * @return Object - An instance of <I>IntegerConverterDefinition</I>
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
		else if (attName.equals(ERROR)) setErrorMessage((String)attValue);
		else if (attName.equals(SEPARATOR)) setSeparator((String)attValue);
	}
	value = new IntegerConverter(errorMessage,separator);
	return this;
}
/**
 * Set the value of the  <I>errorMessage</I> property.
 * @param newErrorMessage java.lang.String - The new value of the property
 */
public void setErrorMessage(java.lang.String newErrorMessage) 
{
	if (newErrorMessage != null) 
	{
		errorMessage = newErrorMessage;
		value = new IntegerConverter(errorMessage);
	}
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
		value = new IntegerConverter(errorMessage, separator);
	}
}
/**
 * If <I>newValue</I> is an instance of IntegerConverter, sets the value of the <I>value</I> property.
 * @param newValue java.lang.Object - The new value of the property
 * @exception DSEInvalidArgumentException - The parameter is not a IntegerConverter Object
 */
public void setValue(Object newValue) throws com.ibm.dse.base.DSEInvalidArgumentException 
{
	if (newValue instanceof IntegerConverter)
	{
		value= (IntegerConverter)newValue;
	}
	else 
		throw new DSEInvalidArgumentException(DSEException.harmless, newValue.toString(),
											"InvalidArgument: " + newValue + " should be an instance of IntegerConverter.");
}
/**
 * Returns an SGML representation of this IntegerConverterDefinition Object.
 * @return java.lang.String
 */
public String toString() 
{
	String s=Constants.Smaller+getTagName()+Constants.Blanc;
	s=JavaExtensions.addAttribute(s,Constants.Id,getName());
	if (!getErrorMessage().equals(DEFAULT_ERROR))  s=JavaExtensions.addAttribute(s,ERROR,getErrorMessage());
	if (!getSeparator().equals(DEFAULT_SEPARATOR)) s=JavaExtensions.addAttribute(s,SEPARATOR,getSeparator());
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
	if(!objectType.equals("java.lang.Integer")){
		throw new DSETypeException(DSETypeException.critical, "Integer type required", "The object is not of type java.lang.Integer.", null);
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
