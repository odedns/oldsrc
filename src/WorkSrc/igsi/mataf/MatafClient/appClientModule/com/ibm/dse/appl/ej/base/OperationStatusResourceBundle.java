package com.ibm.dse.appl.ej.base;

/*_
 * Licensed Materials - Property of IBM
 * Restricted Materials of IBM
 * 5648-D89
 * (C) Copyright IBM Corp. 2000
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp
 */
import java.util.ListResourceBundle;

/**
 * The OperationStatusResourceBundle class contains operation
 * status codes.
 *
 * @see	java.util.ListResourceBundle
 * @copyright (c) Copyright  IBM Corporation 2000
 */

public class OperationStatusResourceBundle extends ListResourceBundle {
	private static final java.lang.String COPYRIGHT =
		"Licensed Materials - Property of IBM "+//$NON-NLS-1$
		"Restricted Materials of IBM "+//$NON-NLS-1$
		"5648-D89 "+//$NON-NLS-1$
		"(C) Copyright IBM Corp. 2000 All Rights Reserved. "+//$NON-NLS-1$
		"US Government Users Restricted Rights - Use, duplication or disclosure "+//$NON-NLS-1$
		"restricted by GSA ADP Schedule Contract with IBM Corp ";//$NON-NLS-1$

	
	private static Object[][] contents = {	
		{"INPRGR", 		"IP"}, 		// in progress
		{"POSTED",		"AC"},		// posted
		{"INPFRWRED",	"IF"},		// in progress forwarded
		{"FRWRD",		"AF"},		// forwarded
		{"REJ",			"RJ"},		// rejected
		{"REJFRWD",		"RF"},		// reject forwarded
		{"OVRD",		"OR"},		// overridden
		{"INPSTORED",	"IS"},		// in progress stored
		{"STORED",		"ST"},		// stored
		{"FAILED",		"FL"},		// failed
		{"FAILEDFRWD",	"FF"},		// failed forwarded
		{"ERROR",		"SE"},		// system error
		{"CANCELED",	"NP"}		// cancelled
	};
/**
 * Get a key code.
 *
 * @return String - A key code
 * @param aKey Object - A key code
 */
 
public String getCode(Object aKey) {
	return (String) aKey;
}
/**
 * Get the whole content in this resource bundle.
 * 
 * @return Object[][] - The whole content
 */
 
public Object [][] getContents() {
	return contents;
}
/**
 * Get the description of a given code.
 *
 * @return String - A description
 * @param aKey Object - A code
 */
 
public String getDescription(Object aKey) {
	return (String)((getObject(aKey.toString())));
}
}
