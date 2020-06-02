package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.gui.DateConverter;
import com.ibm.dse.base.*;
import com.ibm.dse.base.types.DSETypeException;
/**
 * DateConverterDefintion is the class for all elements that externally 
 * define CBTF DateConverters components as DataElements. The value of these
 * definitions is an instance of the com.ibm.dse.gui.DateConverter class.
 * 
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class DateConverterDefinition extends ConverterDefinition
{
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	
	protected String  pattern       = DEFAULT_PATTERN;
	protected String  errorMessage  = DEFAULT_ERROR;
	protected boolean fourDigitYear = DEFAULT_FOURDIGITS;
	protected int     ordering      = DEFAULT_ORDERING;
	protected String  separator     = DEFAULT_SEPARATOR;
	protected boolean useSep        = DEFAULT_USESEP;
	// String PrinterConstants definition....
	protected final String PATTERN    = "pattern";
	protected final String ERROR      = "errorMessage";
	protected final String USESEP     = "useSep";
	protected final String ORDERING   = "ordering";
	protected final String FOURDIGITS = "fourDigitsYear";
	protected final String SEPARATOR  = "sep";
	protected final String TRUE       = "true";
	protected final String FALSE      = "false";
	// ATTRIBUTE DEFAULT DEFINITIONS
	protected final static String  DEFAULT_PATTERN    = "dd/MM/yy";
	protected final static String  DEFAULT_ERROR      = "** Error **";
	protected final static boolean DEFAULT_FOURDIGITS = false;
	protected final static int     DEFAULT_ORDERING   = 0;
	protected final static String  DEFAULT_SEPARATOR  = "/";
	protected final static boolean DEFAULT_USESEP     = true;
/**
 * This constructor creates a DateConverterDefintion object. 
  */
public DateConverterDefinition() 
{
}
/**
 * Required because of superclass.
 * 
 * @return java.lang.Object
 * @exception java.lang.CloneNotSupportedException - The exception description
 */
public Object clone() throws java.lang.CloneNotSupportedException {
	DateConverterDefinition aDefinition = new DateConverterDefinition();
	aDefinition.setErrorMessage(this.getErrorMessage());
	aDefinition.setFourDigitYear(this.isFourDigitYear());
	aDefinition.setOrdering(this.getOrdering());
	aDefinition.setPattern(this.getPattern());
	aDefinition.setSeparator(this.getSeparator());
	aDefinition.setUseSep(this.isUseSep());
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
 * @return java.lang.String - The current value of <I>errorMessage</I>
 */
public java.lang.String getErrorMessage() 
{
	return errorMessage;
}
/**
 * Returns the value of the <I>ordering</I> property.
 * @return int - The current value of <I>ordering</I>
 */
public int getOrdering()
{
	return ordering;
}
/**
 * Returns the value of the <I>pattern</I> property.
 * @return java.lang.String - The current value of <I>pattern</I>
 */
public java.lang.String getPattern() 
{
	return pattern;
}
/**
 * Returns the value of the <I>separator</I> property.
 * @return java.lang.String - The current value of <I>separator</I>
 */
public java.lang.String getSeparator() 
{
	return separator;
}
/**
 * Initializes a <I>DateConverterDefinition</I> object with the <I>aTag</I> attributes.
 * @return Object - An instance of <I>DateConverterDefinition</I>
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
		else if (attName.equals(PATTERN)) setPattern((String)attValue);
		else if (attName.equals(ERROR)) setErrorMessage((String)attValue);
		else if (attName.equals(SEPARATOR)) setSeparator((String)attValue);
		else if (attName.equals(FOURDIGITS)) setFourDigitYear(attValue.equals(TRUE));
		else if (attName.equals(ORDERING)) setOrdering(Integer.parseInt((String)attValue));
		else if (attName.equals(USESEP)) setUseSep(attValue.equals(TRUE));
	}
	value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);	
	return this;
}
/**
 * Returns the value of the <I>fourDigitYear</I> property.
 * @return boolean - The current value of <I>fourDigitYear</I>
 */
public boolean isFourDigitYear() 
{
	return fourDigitYear;
}
/**
 * Returns the value of the <I>useSep</I> property.
 * @return boolean - The current value of <I>useSep</I>
 */
public boolean isUseSep() 
{
	return useSep;
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
		value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);
	}	
}
/**
 * Set the value of the <I>fourDigitYear</I> property.
 * @param newFourDigitYear boolean - The new value of the property
 */
public void setFourDigitYear(boolean newFourDigitYear) 
{
	fourDigitYear = newFourDigitYear;
	value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);	
}
/**
 * Set the value of the <I>ordering</I> property.
 * @param newOrdering int - The new value of the property
 */
public void setOrdering(int newOrdering) 
{
	ordering = newOrdering;
	value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);	
}
/**
 * Set the value of the <I>pattern</I> property.
 * @param newPattern java.lang.String - The new value of the property
 */
public void setPattern(java.lang.String newPattern) 
{
	if (newPattern != null) 
	{
		pattern = newPattern;
		value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);
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
		value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);
	}
}
/**
 * Set the value of the <I>useSep</I> property.
 * @param newUseSep boolean - The new value of the property
 */
public void setUseSep(boolean newUseSep) 
{
	useSep = newUseSep;
	value = new DateConverter(errorMessage, pattern, fourDigitYear, separator,useSep ,ordering);	
}
/**
 * If <I>newValue</I> is an instance of DateConverter, it sets the value of the <I>value</I> property.
 * @param newValue java.lang.Object - The new value of the property
 * @exception DSEInvalidArgumentException - The parameter is not a DateConverter Object
 */
public void setValue(Object newValue) throws com.ibm.dse.base.DSEInvalidArgumentException 
{
	if (newValue instanceof DateConverter)
	{
		value= (DateConverter)newValue;
	}
	else 
		throw new DSEInvalidArgumentException(DSEException.harmless, newValue.toString(),
											"InvalidArgument: " + newValue + " should be an instance of DateConverter.");
}
/**
 * Returns an SGML representation of this DateConverterDefinition Object.
 * @return java.lang.String
 */
public String toString() 
{
	String s=Constants.Smaller+getTagName()+Constants.Blanc;
	s=JavaExtensions.addAttribute(s,Constants.Id,getName());
	if (!getPattern().equals(DEFAULT_PATTERN)) 		s=JavaExtensions.addAttribute(s,PATTERN,getPattern());
	if (!getErrorMessage().equals(DEFAULT_ERROR)) 	s=JavaExtensions.addAttribute(s,ERROR,getErrorMessage());
	if (isFourDigitYear() != DEFAULT_FOURDIGITS) 	s=JavaExtensions.addAttribute(s,FOURDIGITS,TRUE);
	if (!getSeparator().equals(DEFAULT_SEPARATOR)) 	s=JavaExtensions.addAttribute(s,SEPARATOR,getSeparator());
	if (isUseSep()!=DEFAULT_USESEP) 				s=JavaExtensions.addAttribute(s,USESEP,(isUseSep()?TRUE:FALSE));
	if (getOrdering()!= DEFAULT_ORDERING) 			s=JavaExtensions.addAttribute(s,ORDERING,Integer.toString(getOrdering()));
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
	if(!objectType.equals("java.util.Date")){
		throw new DSETypeException(DSETypeException.critical, "Date type required", "The object is not of type java.util.Date.", null);
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
