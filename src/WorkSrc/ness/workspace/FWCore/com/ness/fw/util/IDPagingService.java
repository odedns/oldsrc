/*
 * Created on: 28/04/2005
 * @author: baruch hizkya
 * @version $Id: IDPagingService.java,v 1.5 2005/05/09 11:36:12 baruch Exp $
 */

package com.ness.fw.util;
import java.util.ArrayList;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * @author bhizkya
 *
 */
public class IDPagingService
{

	private ArrayList idList;
	private int numRows = -1;
	private int cursorPosition = -1;
	
	/** A constant for the isAllowed getFirst operation.
	 */
	public static final int GET_FIRST = 0;

	/** A constant for the isAllowed getLast operation.
	 */
	public static final int GET_LAST = 1;

	/** A constant for the isAllowed getNext operation.
	 */
	public static final int GET_NEXT = 2;

	/** A constant for the isAllowed getPrevious operation.
	 */
	public static final int GET_PREVIOUS = 3;


	/**
	 * A constans for the default num rows
	 */
	private static final int DEFAULT_NUM_ROWS = 10;

	/**
	 * 
	 * @param idList
	 */
	public IDPagingService(ArrayList idList)
	{
		this(idList,DEFAULT_NUM_ROWS);
	}

	/**
	 * 
	 * @param idList
	 * @param numRows
	 */
	public IDPagingService(ArrayList idList, int numRows)
	{
		this.idList = idList;
		this.numRows = numRows;
	}
	
	/**
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public boolean next() throws GeneralException
	{
		cursorPosition++;
		return cursorPosition >= 0
			&& cursorPosition < getSubIdsCount()
			&& getSubIdsCount() > 0;
	}

	/**
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public boolean previous() throws GeneralException
	{
		cursorPosition--;
		return cursorPosition >= 0
			&& cursorPosition < getSubIdsCount()
			&& getSubIdsCount() > 0;
	}

	
	/**
	 * 
	 * @return
	 * @throws GeneralException
	 */
	public ArrayList getSubId() throws GeneralException
	{
		return getSubId(cursorPosition);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getSubIdsCount()
	{
		if (numRows > 0)
		{
			return idList.size() / numRows
				+ (((idList.size() % numRows) > 0) ? 1 : 0);
		}
		return 0;
	}


	/**
	 * 
	 * @return
	 */
	public boolean first()
	{
		cursorPosition = 0;
		return getSubIdsCount() > 0;
	}

	/**
	 * 
	 * @return
	 */
	public boolean last()
	{
		cursorPosition = getSubIdsCount() - 1;
		return getSubIdsCount() > 0;
	}


	/**
	 * 
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public ArrayList getSubId(int index) throws GeneralException
	{
		int idCount = getSubIdsCount();

		int fromIndex = index * numRows;
		int toIndex = fromIndex + numRows;
		if (toIndex > idList.size())
		{
			toIndex = idList.size();
		}
		
		if (index < 0 || index >= idCount)
		{
			throw new GeneralException("Requested ids ["
					+ fromIndex + " - " + toIndex + "]) is not valid.");
		}

		ArrayList subSetIds = new ArrayList();

		for (int i=fromIndex ; i<toIndex; i++)
		{
			subSetIds.add(idList.get(i));
		}
		
		cursorPosition = index;

		return subSetIds;
	}

	/**
	 * Checks if the operation {GET_NEXT,GET_PREVIOUS,GET_FIRST,GET_LAST}
	 * is allowed
	 * @param operation
	 * @return boolean. true if the operation is allowed, othrwise false
	 */
	public boolean isOperationAllowed(int operation)
	{
		if (operation == GET_NEXT)
		{
			if (cursorPosition < getSubIdsCount() - 1
				&& getSubIdsCount() > 0)
			{
				return true;
			}
		}
		else if (operation == GET_PREVIOUS)
		{
			if (cursorPosition > 0 && getSubIdsCount() > 0)
			{
				return true;
			}
		}
		else if (operation == GET_FIRST)
		{
			if (getSubIdsCount() > 0)
			{
				return true;
			}
		}
		else if (operation == GET_LAST)
		{
			if (getSubIdsCount() > 0)
			{
				return true;
			}
		}

		return false;
	}
}
