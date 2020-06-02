package com.ibm.dse.appl.ej.client;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000, 2001
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.Vector;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.math.BigDecimal;
import com.ibm.dse.clientserver.*;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.Trace;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.appl.ej.base.CurrencyDecimalResourceBundle;
import javax.swing.JOptionPane;

/**
 * This operation is invoked to execute the EJ Query Client 
 * operation.  All its behavior is included in the set of operation steps
 * defined inside the operation definition.
 * See the XML operations file for the client workstation.
 * @copyright (c) Copyright  IBM Corporation 2000, 2001
 */
 
public class EJQueryClientOperation extends DSEClientOperation {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000, 2001 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$
	
	static final String PAGE_NUMBER			= "pageNum";
	static final String SQL_STATEMENT		= "sqlWhereClause";
	static final String PROCESS_SQL_STATEMENT		= "processSQLWhereClause";
	protected transient Vector aOperationRepliedListener = null;

	/* Trace component identification */
	public java.lang.String COMPID = "#EJ";
	
/**
 * Executes the EJ Query Client Operation. 
 * Override this method to handle possible exceptions.
 *
 * @exception Exception	thrown for any runtime exception
 */
public void execute() throws Exception {
	try {
		setWhereClause();
		reinitializeCollection();
		super.execute();
		int anInt = ((Vector) getValueAt("ejItems")).size();
		if (anInt == 0) {
			String aMessage = "No match was found !";
			JOptionPane.showMessageDialog(null, aMessage, "Server response", JOptionPane.INFORMATION_MESSAGE);
		}
	} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, e.toString(), "Exception", JOptionPane.ERROR_MESSAGE);
	}
}
/**
 * Removes all entries from the ejItems context.
 * 
 */
private void reinitializeCollection() {
	try {
		IndexedCollection kColl = (IndexedCollection)getElementAt("ejItems");
		kColl.removeAll();
	} catch (DSEObjectNotFoundException e) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString() + ". Could not find ejItems.");
	}
}
/**
 * Converts the object to an acceptable string and adds it to the buffer
 * 
 * @param finalString java.lang.StringBuffer
 * @param extraString java.lang.String
 * @param anObject java.lang.Object
 */
private void replaceAttributesWithValues(StringBuffer finalString, String extraString, Object anObject) {
	if (anObject instanceof String) {
		finalString.append(" '" + anObject + "' " + extraString);
	} else
		if (anObject instanceof Integer) {
			finalString.append(" " + anObject + " " + extraString);
		} else
			if (anObject instanceof Float) {
				try {
					String currCode = (String) getValueAt("currency");
					CurrencyDecimalResourceBundle aBundle = new CurrencyDecimalResourceBundle();
					int anInt = ((Integer) aBundle.getObject("USD")).intValue();
					BigDecimal multiplier = new BigDecimal(Math.pow(10, anInt));
					BigDecimal arg = new BigDecimal(anObject.toString());
					int intValue = arg.multiply(multiplier).intValue();
					finalString.append(" " + intValue + " " + extraString);
				} catch (DSEObjectNotFoundException e) {
					Trace.trace(COMPID,Trace.Severe,Trace.Error,null, e.toString() + ". Could not find currency.");
				}
			} else
				if (anObject instanceof Date) {
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-M-d");
					String dateString = dateFormatter.format((Date) anObject);
					finalString.append(" '" + dateString + "' " + extraString);
				}
}
/**
 * Insert the method's description here.
 * Creation date: (5/4/00 3:54:02 PM)
 */
private void setWhereClause() {
	StringBuffer finalString = null;
	try {
		String newString = (String)getValueAt(PROCESS_SQL_STATEMENT);
		java.util.StringTokenizer st = new java.util.StringTokenizer(newString, ":");
		java.util.Vector aVector = new java.util.Vector();
		String tokenString = st.nextToken().trim();
		finalString = new StringBuffer(tokenString);
		while (st.hasMoreElements()) {
			tokenString = st.nextToken().trim();
			int spaceInt = tokenString.indexOf(" ");
			if (spaceInt > 0) {
				String dummyString = tokenString.substring(0, spaceInt).trim();
				String extraString = tokenString.substring(spaceInt).trim();
				Object anObject = getValueAt(dummyString);
				replaceAttributesWithValues(finalString, extraString, anObject);
			} else {
				String dummyString = tokenString;
				Object anObject = getValueAt(dummyString);
				replaceAttributesWithValues(finalString, "", anObject);
			}
		}
		setValueAt(SQL_STATEMENT, finalString.toString());
		setValueAt(PAGE_NUMBER, new Integer(1)); // This line is to be modified later
	} catch (DSEObjectNotFoundException onfe) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, onfe.toString() + ". Could not find SQL statement.");
	} catch (DSEInvalidArgumentException iae) {
		Trace.trace(COMPID,Trace.Severe,Trace.Error,null, iae.toString() + ". Invalid " + finalString);
	}
}
}
