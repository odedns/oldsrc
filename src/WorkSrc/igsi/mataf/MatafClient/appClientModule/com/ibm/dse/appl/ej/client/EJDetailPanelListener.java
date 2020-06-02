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

public interface EJDetailPanelListener extends java.util.EventListener {

/**
 * 
 * @param newEvent java.util.EventObject
 */
void detailsCloseButtonAction_actionPerformed(java.util.EventObject newEvent);
/**
 * 
 * @param newEvent java.util.EventObject
 */
void detailsHelpButtonAction_actionPerformed(java.util.EventObject newEvent);
/**
 * 
 * @param newEvent java.util.EventObject
 */
void summaryButtonAction_actionPerformed(java.util.EventObject newEvent);
}
