package com.ibm.dse.appl.ej.client;


/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2003
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */

import com.ibm.dse.gui.SpButton;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.util.EventObject;
import java.util.ResourceBundle;
import javax.swing.*;

/**
 * This class represents the dynamic button added in the ejview.xml file.
 * @copyright (c) Copyright IBM Corporation 2003
 */
 
public class EJButton 
{
    private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2003 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

    private String label = null;
    
    private String type;
    
    private String opId;
    
    private String selection;
    
    private String kCollName;
    
    public EJButton()
    {
        super();
    }

/**
 * Return the type property value.
 * @return java.util.String
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

/**
 * Return the label property value.
 * @return java.util.String
 */
public String getLabel() {
	return label;
}

/**
 * Sets the value of the label property.
 * @param value - The java.lang.String
 */
public void setLabel(String value) {
	label = value;
}

/**
 * Return the opId property value.
 * @return java.util.String
 */
public String getOpId() {
	return opId;
}

/**
 * Sets the value of the opId property.
 * @param value - The java.lang.String
 */
public void setOpId(String value) {
	opId = value;
}

/**
 * Return the selection property value.
 * @return java.util.String
 */
public String getSelection() {
	return selection;
}
/**
 * Sets the value of the selection property.
 * @param value - The java.lang.String
 */
public void setSelection(String value) {
	selection = value;
}

/**
 * Return the kCollName property value.
 * @return java.util.String
 */
public String getKCollName() {
	return kCollName;
}

/**
 * Sets the value of the kCollName property.
 * @param value - The java.lang.String
 */
public void setKCollName(String value) {
	kCollName = value;
}
}


