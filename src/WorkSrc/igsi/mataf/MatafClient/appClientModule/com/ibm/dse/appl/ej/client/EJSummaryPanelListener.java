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
 * @copyright (c) Copyright  IBM Corporation 2000
 */

public interface EJSummaryPanelListener extends java.util.EventListener {

/**
 * 
 * @param newEvent java.util.EventObject
 */
void detailsButtonAction_actionPerformed(java.util.EventObject newEvent);
/**
 * 
 * @param newEvent java.util.EventObject
 */
void scrollPaneTableAction_actionPerformed(java.util.EventObject newEvent);
/**
 * 
 * @param newEvent java.util.EventObject
 */
void summaryCloseButtonAction_actionPerformed(java.util.EventObject newEvent);
/**
 * 
 * @param newEvent java.util.EventObject
 */
void summaryHelpButtonAction_actionPerformed(java.util.EventObject newEvent);
}
