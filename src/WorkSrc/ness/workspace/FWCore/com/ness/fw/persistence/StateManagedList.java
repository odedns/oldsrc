/*
 * Created on 12/05/2004
 * Author: yifat har-nof
 * @version $Id: StateManagedList.java,v 1.2 2005/04/04 09:37:34 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.FatalException;

/**
 * A List of <code>StateManaged</code> objects.
 */
public interface StateManagedList
{
	/**
	 * Clears the state of the <code>StateManaged</code> objects.
	 */
	public void clearObjectsState() throws FatalException;

}
