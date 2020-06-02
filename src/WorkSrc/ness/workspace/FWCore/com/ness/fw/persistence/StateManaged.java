/*
 * Author: yifat har-nof
 * @version $Id: StateManaged.java,v 1.2 2005/04/04 09:37:34 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.FatalException;

/**
 * Represent a state managed object that should handle the state in the DB.
 */
public interface StateManaged
{

	/**
	 * Returns the object state in the DB.
	 * @return int The object state.
	 */
	public int getObjectState();

	/**
	 * Clears the object state to non-dirty for the current object.
	 * Can be overridden for cleaning of the persistable children’s.
	 * @return boolean Return true if the object can be set to non dirty.
	 * @throws FatalException
	 */
	public void clearObjectState() throws FatalException;

}
