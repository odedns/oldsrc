package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.Vector;

/**
 * This class represents the <result> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class EJResult extends EJColumn {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	/**
	* The columns attribute represents a vector of EJColumns.
	*/
	private Vector columns = new Vector();

/**
 * EJResult default constructor.
 */
 
public EJResult() {
	super();
}
/**
 * Adds an entry to the columns vector.
 * @param value com.ibm.dse.appl.ej.client.EJColumn
 */
 
public void addColumn(EJColumn value) {
	columns.addElement(value);
}
/**
 * Returns the columns property value.
 * @return java.util.Vector
 */
 
public Vector getColumns() {
	return columns;
}
/**
 * Sets the value of the columns property.
 * @param value java.util.Vector
 */
 
public void setColumns(Vector value) {
	columns = value;
}
}
