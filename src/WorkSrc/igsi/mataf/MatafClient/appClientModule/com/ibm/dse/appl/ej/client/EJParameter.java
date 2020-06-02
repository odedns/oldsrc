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
 * This class represents the <param> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class EJParameter {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	private String name;
	private String label;

/**
 * EJItem default constructor.
 */
 
public EJParameter() {
	super();
}
/**
 * Return the label property value.
 * @return java.lang.String
 */
 
public String getLabel() {
	return label;
}
/**
 * Return the name property value.
 * @return java.lang.String
 */
 
public String getName() {
	return name;
}
/**
 * Sets the value of the label property.
 * @param value the java.lang.String
 */
 
public void setLabel(String value) {
	label = value;
}
/**
 * Sets the value of the name property.
 * @param value the java.lang.String
 */
 
public void setName(String value) {
	name = value;
}
}
