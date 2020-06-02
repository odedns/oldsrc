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
 * This class represents the <input> tag in the XML file.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
 
public class EJInput extends EJColumn {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	/**
	* The parameter's label attribute represents the entries to populate the comboBox when the view opens up,
	* if the parameter's name attribute is "ITEM".
	* Otherwise the label attributes will be treated as the combo box properties based on their name.
	*/
	private Vector parameters = new Vector();
	/**
	/**
	* The widget attribute represents the widget type such as <textField> or <comboBox>.
	* The implementor can then use the proper widget to represent such a type.
	* Currently, we are using SpTextField and SpComboBox to represent these types respectively.
	*/
	private String widget;
	/**
	* The defaultValue attribute represents the text to be displayed in the textField widget
	* when the view opens up.
	*/
	private String defaultValue = "";

/**
 * EJInput default constructor.
 */
 
public EJInput() {
	super();
}
/**
 * Adds an EJParameter object to the items vector.
 * @param value com.ibm.dse.appl.ej.client.EJParameter
 */
 
public void addParameter(EJParameter value) {
	parameters.addElement(value);
}
/**
 * Return the defaultValue property value.
 * @return java.lang.String
 */
 
public String getDefaultValue() {
	return defaultValue;
}
/**
 * Return the parameters property value.
 * @return java.util.Vector
 */
 
public Vector getParameters() {
	return parameters;
}
/**
 * Return the widget property value.
 * @return java.lang.String
 */
 
public String getWidget() {
	return widget;
}
/**
 * Sets the value of the defaultValue property.
 * @param value of the java.lang.String
 */
 
public void setDefaultValue(String value) {
	defaultValue = value;
}
/**
 * Sets the value of the parameters property.
 * @param value of the java.util.Vector
 */
 
public void setParameters(Vector value) {
	parameters = value;
}
/**
 * Sets the value of the widget property.
 * @param value of the java.lang.String
 */
 
public void setWidget(String value) {
	widget = value;
}
}
