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
 * This is the event multicaster class to support the com.ibm.dse.appl.ej.client.EJDetailPanelListenerEventMulticaster interface.
 * @copyright (c) Copyright  IBM Corporation 2000
 */
public class EJDetailPanelListenerEventMulticaster extends java.awt.AWTEventMulticaster implements EJDetailPanelListener {
	private static final java.lang.String COPYRIGHT = "(c) Copyright IBM Corporation 2000. ";//$NON-NLS-1$
/**
 * Constructor to support multicast events.
 * @param a java.util.EventListener
 * @param b java.util.EventListener
 */
protected EJDetailPanelListenerEventMulticaster(java.util.EventListener a, java.util.EventListener b) {
	super(a, b);
}
/**
 * Add new listener to support multicast events.
 * @return com.ibm.dse.appl.ej.client.EJDetailPanelListener
 * @param a com.ibm.dse.appl.ej.client.EJDetailPanelListener
 * @param b com.ibm.dse.appl.ej.client.EJDetailPanelListener
 */
public static com.ibm.dse.appl.ej.client.EJDetailPanelListener add(com.ibm.dse.appl.ej.client.EJDetailPanelListener a, com.ibm.dse.appl.ej.client.EJDetailPanelListener b) {
	return (com.ibm.dse.appl.ej.client.EJDetailPanelListener)addInternal(a, b);
}
/**
 * Add new listener to support multicast events.
 * @return java.util.EventListener
 * @param a java.util.EventListener
 * @param b java.util.EventListener
 */
protected static java.util.EventListener addInternal(java.util.EventListener a, java.util.EventListener b) {
	if (a == null)  return b;
	if (b == null)  return a;
	return new EJDetailPanelListenerEventMulticaster(a, b);
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void detailsCloseButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)a).detailsCloseButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)b).detailsCloseButtonAction_actionPerformed(newEvent);
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void detailsHelpButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)a).detailsHelpButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)b).detailsHelpButtonAction_actionPerformed(newEvent);
}
/**
 * 
 * @return java.util.EventListener
 * @param oldl com.ibm.dse.appl.ej.client.EJDetailPanelListener
 */
protected java.util.EventListener remove(com.ibm.dse.appl.ej.client.EJDetailPanelListener oldl) {
	if (oldl == a)  return b;
	if (oldl == b)  return a;
	java.util.EventListener a2 = removeInternal(a, oldl);
	java.util.EventListener b2 = removeInternal(b, oldl);
	if (a2 == a && b2 == b)
		return this;
	return addInternal(a2, b2);
}
/**
 * Remove listener to support multicast events.
 * @return com.ibm.dse.appl.ej.client.EJDetailPanelListener
 * @param l com.ibm.dse.appl.ej.client.EJDetailPanelListener
 * @param oldl com.ibm.dse.appl.ej.client.EJDetailPanelListener
 */
public static com.ibm.dse.appl.ej.client.EJDetailPanelListener remove(com.ibm.dse.appl.ej.client.EJDetailPanelListener l, com.ibm.dse.appl.ej.client.EJDetailPanelListener oldl) {
	if (l == oldl || l == null)
		return null;
	if(l instanceof EJDetailPanelListenerEventMulticaster)
		return (com.ibm.dse.appl.ej.client.EJDetailPanelListener)((com.ibm.dse.appl.ej.client.EJDetailPanelListenerEventMulticaster) l).remove(oldl);
	return l;
}
/**
 * 
 * @param newEvent java.util.EventObject
 */
public void summaryButtonAction_actionPerformed(java.util.EventObject newEvent) {
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)a).summaryButtonAction_actionPerformed(newEvent);
	((com.ibm.dse.appl.ej.client.EJDetailPanelListener)b).summaryButtonAction_actionPerformed(newEvent);
}
}
