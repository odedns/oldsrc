/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: Guard.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

/**
 * Common ansestor for all guard condition implementations
 */
public interface Guard
{
	/**
	 * Should be implemented by guard implementation. Performs tasks on context fields and returns boolean value.
	 *
	 * @param ctx <code>Context</code> that contains fields to be checked
	 * @return boolean value indicating that the guard can be passed (Transition can be toggled).
	 */
	public boolean check(Context ctx) throws GuardException;
	public void initialize(ParameterList parameterList) throws GuardException;
}
