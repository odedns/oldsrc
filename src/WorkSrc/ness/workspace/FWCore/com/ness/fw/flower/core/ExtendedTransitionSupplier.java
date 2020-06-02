/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ExtendedTransitionSupplier.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Implementation of the interface can be used as supplier of additional transitions to state while event processing.
 */
public interface ExtendedTransitionSupplier
{
	/**
	 * Called by <code>Flow</code> to ask for additional transitions.
	 *
	 * @param clickEventName name of event foe which transitions should be suppplied
	 * @return list of transitions
	 */
	public TransitionList getTransitionList(String eventName);
}
