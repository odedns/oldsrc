/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: CombinedGuard.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * Used to combine list of {@link Guard}'s to be checked together
 */
public class CombinedGuard implements Guard
{
	/**
	 * The contained guards list.
	 */
	private ArrayList guards;

	/**
	 * The constructor used while creating {@link Guard}'s instance.
	 */
	public CombinedGuard()
	{
		guards = new ArrayList();
	}

	/**
	 * This method run over the list of all contained <code>Guard</code>s and check them 
	 * one by one. 
	 *
	 * @param ctx The {@link Context} to check on.
	 * @return boolean Returns true if all the guards has returned true.
	 */
	public boolean check(Context ctx) throws GuardException
	{
		for (int i = 0; i < guards.size(); i++)
		{
			Guard guard = (Guard) guards.get(i);
			if (!guard.check(ctx))
			{
				return false;
			}
		}

		return true;
	}

	/**
	 * The method was not implemented.
	 *
	 * @param parameterList The {@link ParameterList} for the initialization process.
	 * @throws GuardException
	 */
	public void initialize(ParameterList parameterList) throws GuardException
	{
		throw new RuntimeException("Method is not implemented");
	}

	/**
	 * Used by framework while initializing guards.
	 * Add a guard to the end of the list.
	 *
	 * @param guard to be added to list
	 */
	public void addGuard(Guard guard)
	{
		guards.add(guard);
	}
	
	/**
	 * Returns the number of gurads
	 * @return int
	 */
	public int getCount()
	{
		return guards.size();
	}
}
