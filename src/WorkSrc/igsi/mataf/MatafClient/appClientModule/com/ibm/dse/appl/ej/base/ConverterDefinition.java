package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import com.ibm.dse.base.*;
import com.ibm.dse.gui.Converter;
/**
 * ConverterDefintion is the abstract class for all elements that externally 
 * define GUI converters components as DataElements. The value of these
 * definitions is an instance of the com.ibm.dse.gui.Converter class.
 * 
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public abstract class ConverterDefinition extends com.ibm.dse.base.DataElement 
{
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	protected Converter value = null;

/**
 * Returns the value of the <I>value</I> property. The value property corresponds
 * to the Converter instance defined in the SGML file.
 * @return java.lang.Object - The current value of <I>value</I>.
 */
public Object getValue() 
{
	return value;
}
/**
 * Abstract method. Initializes a <I>ConverterDefinition</I> object with the <I>aTag</I> attributes.
 * @return Object - An instance of <I>ConverterDefinition</I>
 * @param aTag com.ibm.dse.base.Tag - Contains the name and attributes of the object to be created
 * @exception java.io.IOException - The object can not be created
 */
public abstract Object initializeFrom(Tag aTag) throws java.io.IOException ;
/**
 * Returns an empty Vector because Converter definitions are simple fields that
 * contain no subelements.
 * @return com.ibm.dse.base.Vector
 */
public Vector nestedElements() 
{
	return new Vector(1);
}
/**
 * Because it isn't a collection, it adds itself in the hashtable <I>theTable</I>.
 */
public void nestedElements(java.util.Hashtable theTable) 
{
	theTable.put(getName(),this);
}
/**
 * Because it is a simple DataElement it returns a Vector containing itself.
 * @return com.ibm.dse.base.Vector
 */
public Vector nestedFields() 
{
	Vector list = new Vector(1);
	list.addElement(this);
	return list;
}
/**
 * If <I>newValue</I> is an instance of Converter, it sets the value of the <I>value</I> property.
 * @param newValue java.lang.Object - The new value of the property
 * @exception DSEInvalidArgumentException - The parameter is not a Hashtable Object
 */
public void setValue(Object newValue) throws com.ibm.dse.base.DSEInvalidArgumentException 
{
	if (newValue instanceof Converter)
	{
		value= (Converter)newValue;
	}
	else 
		throw new DSEInvalidArgumentException(DSEException.harmless, newValue.toString(),
											"InvalidArgument: " + newValue + " should be an instance of Converter.");
}
}
