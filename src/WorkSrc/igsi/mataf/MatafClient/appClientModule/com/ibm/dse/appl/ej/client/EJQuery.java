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
 * This class represents the <query> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class EJQuery extends EJParameter {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	private Vector inputs = new Vector(); // a vector of EJInputs
	private EJResult result = new EJResult();
	/**
	* The statement attribute represents the entire sql query statement.
	*/
	private String statement;
	private String description;

/**
 * EJQuery default constructor.
 */
 
public EJQuery() {
	super();
}
	public void addInput(EJInput value) {
		inputs.addElement(value);
	}
/**
 * Returns the description property value.
 * @return java.lang.String
 */
 
public String getDescription() {
	return description;
}
/**
 * Returns the inputs property value.
 * @return java.util.Vector
 */
 
public Vector getInputs() {
	return inputs;
}
/**
 * Returns the result property value.
 * @return com.ibm.dse.appl.ej.client.EJResult
 */
 
public EJResult getResult() {
	return result;
}
/**
 * Returns the statement property value.
 * @return java.lang.String
 */
 
public String getStatement() {
	return statement;
}
/**
 * Sets the value of the description property.
 * @param value java.lang.String
 */
 
public void setDescription(String value) {
	description = value;
}
/**
 * Sets the value of the inputs property.
 * @param value java.util.Vector
 */
 
public void setInputs(Vector value) {
	inputs = value;
}
/**
 * Sets the value of the result property.
 * @param value com.ibm.dse.appl.ej.client.EJResult
 */
 
public void setResult(EJResult value) {
	result = value;
}
/**
 * Sets the value of the statement property.
 * @param value java.lang.String
 */
 
public void setStatement(String value) {
	statement = value;
}
}
