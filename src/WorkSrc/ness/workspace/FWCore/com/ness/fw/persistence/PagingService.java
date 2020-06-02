/*
 * Created on: 04/08/2004
 * Author: yifat har-nof
 * @version $Id: PagingService.java,v 1.2 2005/04/04 09:45:06 yifat Exp $
 */
package com.ness.fw.persistence;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * The interface for the paging services.
 */
public interface PagingService
{
	/** A constant that says that last operation was getFirst.
	 */
	public static final int GET_FIRST = 0;

	/** A constant that says that last operation was getLast.
	 */
	public static final int GET_LAST = 1;

	/** A constant that says that last operation was getNext.
	 */
	public static final int GET_NEXT = 2;

	/** A constant that says that last operation was getPrevious.
	 */
	public static final int GET_PREVIOUS = 3;

	/** Is the specified operation valid based on the last operation
	 * results.
	 * @param operation The requested operation. 
	 * Can be one of the following: GET_NEXT, GET_PREVIOUS, GET_FIRST, GET_LAST.
	 * @return boolean Whether the operation is valid or not.
	 * @throws PersistenceException
	 */
	public boolean isOperationAllowed(int operation)
		throws PersistenceException;

}
