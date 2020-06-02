package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.Hashtable;

/**
 * This class represents the <querySet> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000, 2003
 */
 
public class EJQuerySet {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	/**
	* The queries attribute represents a hashtable of EJQuerys keyed by query label.
	*/
	private Hashtable queries = new Hashtable();
	private Hashtable buttons = new Hashtable();
	private String name;

/**
 * EJQuerySet default constructor.
 */
 
public EJQuerySet() {
	super();
}
/**
 * Adds an entry into the queries hash table.
 * @param value the java.util.Hashtable
 */
 
public void addQuery(EJQuery value) {
	queries.put(value.getLabel(), value);
}

/**
 * Adds an entry into the buttons hash table.
 * @param value the java.util.Hashtable
 */
 
public void addButton(EJButton value) {
	buttons.put(value.getLabel(), value);
}
/**
 * Return the name property value.
 * @return java.lang.String
 */
 
public String getName() {
	return name;
}
/**
 * Return the queries property value.
 * @return java.util.Hashtable
 */
 
public Hashtable getQueries() {
	return queries;
}

/**
 * Return the buttons property value.
 * @return java.util.Hashtable
 */
 
public Hashtable getButtons() {
	return buttons;
}
/**
 * Sets the value of the name property.
 * @param value java.lang.String
 */
 
public void setName(String value) {
	name = value;
}
/**
 * Sets the value of the queries property.
 * @param value java.util.Hashtable
 */
 
public void setQueries(Hashtable value) {
	queries = value;
}

/**
 * Sets the value of the buttons property.
 * @param value java.util.Hashtable
 */
 
public void setButtons(Hashtable value) {
	buttons = value;
}
}
