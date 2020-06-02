package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
/**
 * This class represents the <column> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class EJColumn extends EJParameter {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	private String type;

/**
 * EJColumn default constructor.
 */
public EJColumn() {
	super();
}
/**
 * Return the type property value.
 * @return java.lang.String
 */
 
public String getType() {
	return type;
}
/**
 * Sets the value of the type property.
 * @param value - The java.lang.String
 */
 
public void setType(String value) {
	type = value;
}
}
