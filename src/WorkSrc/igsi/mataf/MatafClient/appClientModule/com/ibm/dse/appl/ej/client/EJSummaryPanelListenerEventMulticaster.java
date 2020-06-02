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
 * This is the event multicaster class to support the 
 * com.ibm.dse.appl.ej.client.EJSummaryPanelListenerEventMulticaster interface.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class EJSummaryPanelListenerEventMulticaster extends java.awt.AWTEventMulticaster implements EJSummaryPanelListener {
	private static final java.lang.String COPYRIGHT = "(c) Copyright IBM Corporation 2000. ";//$NON-NLS-1$
/**
 * Constructor to support multicast events.
 * @param a java.util.EventListener
 * @param b java.util.EventListener
 */
protected EJSummaryPanelListenerEventMulticaster(java.util.EventListener a, java.util.EventListener b) {
	super(a, b);
}
/**
 * Adds a new listener to support multicast events.
 * @return com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 * @param a com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 * @param b com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 */
public static com.ibm.dse.appl.ej.client.EJSummaryPanelListener add(com.ibm.dse.appl.ej.client.EJSummaryPanelListener a, com.ibm.dse.appl.ej.client.EJSummaryPanelListener b) {
	return (com.ibm.dse.appl.ej.client.EJSummaryPanelListener)addInternal(a, b);
}
/**
 * Adds a new listener to support multicast events.
 * @return java.util.EventListener
 * @param a java.util.EventListener
 * @param b java.util.EventListener
 */
protected static java.util.EventListener addInternal(java.util.EventListener a, java.util.EventListener b) {
	if (a == null)  return b;
	if (b == null)  return a;
	return new EJSummaryPanelListenerEventMulticaster(a, b);
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void detailsButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)a).detailsButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)b).detailsButtonAction_actionPerformed(newEvent);
}
/**
 * 
 * @return java.util.EventListener
 * @param oldl com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 */
protected java.util.EventListener remove(com.ibm.dse.appl.ej.client.EJSummaryPanelListener oldl) {
	if (oldl == a)  return b;
	if (oldl == b)  return a;
	java.util.EventListener a2 = removeInternal(a, oldl);
	java.util.EventListener b2 = removeInternal(b, oldl);
	if (a2 == a && b2 == b)
		return this;
	return addInternal(a2, b2);
}
/**
 * Removes a listener to support multicast events.
 * @return com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 * @param l com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 * @param oldl com.ibm.dse.appl.ej.client.EJSummaryPanelListener
 */
public static com.ibm.dse.appl.ej.client.EJSummaryPanelListener remove(com.ibm.dse.appl.ej.client.EJSummaryPanelListener l, com.ibm.dse.appl.ej.client.EJSummaryPanelListener oldl) {
	if (l == oldl || l == null)
		return null;
	if(l instanceof EJSummaryPanelListenerEventMulticaster)
		return (com.ibm.dse.appl.ej.client.EJSummaryPanelListener)((com.ibm.dse.appl.ej.client.EJSummaryPanelListenerEventMulticaster) l).remove(oldl);
	return l;
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void scrollPaneTableAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)a).scrollPaneTableAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)b).scrollPaneTableAction_actionPerformed(newEvent);
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void summaryCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)a).summaryCloseButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)b).summaryCloseButtonAction_actionPerformed(newEvent);
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void summaryHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)a).summaryHelpButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJSummaryPanelListener)b).summaryHelpButtonAction_actionPerformed(newEvent);
}
}
