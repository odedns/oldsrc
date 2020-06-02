/*
 * Author: yifat har-nof
 * @version $Id: SubPagingService.java,v 1.2 2005/04/04 09:36:59 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;
import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Provide a Sub Paging mechanism to page within an existing Page.
 */
public class SubPagingService
{

	/** A constant that says that last operation was getFirst.
	 */
	public static final int GET_FIRST = BasicPagingService.GET_FIRST;
	/** A constant that says that last operation was getLast.
	 */
	public static final int GET_LAST = BasicPagingService.GET_LAST;
	/** A constant that says that last operation was getNext.
	 */
	public static final int GET_NEXT = BasicPagingService.GET_NEXT;
	/** A constant that says that last operation was getPrevious.
	 */
	public static final int GET_PREVIOUS = BasicPagingService.GET_PREVIOUS;
	/** A constant used to add a sorting column with ascending order.
	 */
	private int subPagePosition = -1;
	/** The size of each subPage
	 */
	private int rowsInSubPage = -1;
	/** The Page that the SubPagingService is based on.
	 */
	private Page page;

	/** 
	 * Create a new SubPagingService with specified number of rows in each SubPage.
	 * @param page The Page to base upon.
	 * @param rowsInSubPage The number of rows in each SubPage.
	 */
	public SubPagingService(Page page, int rowsInSubPage)
	{
		this.page = page;
		this.rowsInSubPage = rowsInSubPage;
	}

	/** 
	 * Set the size of each subPage.
	 * @param rowsInSubPage The number of rows in each SubPage.
	 */
	public void setSubPageSize(int rowsInSubPage)
	{
		this.rowsInSubPage = rowsInSubPage;
	}

	/** 
	 * Get the number of subPages in the page.
	 * @return int The number of subPages in the page.
	 */
	public int getSubPageCount()
	{
		if (rowsInSubPage > 0)
		{
			return page.getRowCount() / rowsInSubPage
				+ (((page.getRowCount() % rowsInSubPage) > 0) ? 1 : 0);
		}
		return 0;
	}

	/**
	 * Get a Specific subPage from the Page.
	 * @param index The requested subPage number.
	 * @return Page The requested subPage from the Page.
	 */
	public Page getSubPage(int index) throws PersistenceException
	{
		int pageCount = getSubPageCount();
		if (index < 0 || index >= pageCount)
		{
			throw new PersistenceException(
				"Requested page number ("
					+ index
					+ ") is not valid. (page count = "
					+ pageCount
					+ ")");
		}

		Page newPage = new Page(page);
		int fromRow = index * rowsInSubPage;
		int toRow = fromRow + rowsInSubPage;
		if (toRow > page.getRowCount())
		{
			toRow = page.getRowCount();
		}
		newPage.setRows(new ArrayList(page.getRows().subList(fromRow, toRow)));
		subPagePosition = index;
		return newPage;
	}

	/** 
	 * Get the current subPage.
	 * @return Page The current subPage.
	 */
	public Page getSubPage() throws PersistenceException
	{
		return getSubPage(subPagePosition);
	}

	/** 
	 * Set the subPage position before the first subPage.
	 */
	public void beforeFirst()
	{
		subPagePosition = -1;
	}

	/** 
	 * Set the subPage position after the last subPage.
	 */
	public void afterLast()
	{
		subPagePosition = getSubPageCount();
	}

	/** 
	 * Set the subPage position to the first subPage .
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 */
	public boolean first()
	{
		subPagePosition = 0;
		return getSubPageCount() > 0;
	}

	/** 
	 * Set the subPage position to the last subPage.
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 */
	public boolean last()
	{
		subPagePosition = getSubPageCount() - 1;
		return getSubPageCount() > 0;
	}

	/** 
	 * Set the subPage position to the next subPage .
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 */
	public boolean next()
	{
		subPagePosition++;
		return subPagePosition >= 0
			&& subPagePosition < getSubPageCount()
			&& getSubPageCount() > 0;
	}

	/** 
	 * Set the subPage position to the previous subPage.
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 */
	public boolean previous()
	{
		subPagePosition--;
		return subPagePosition >= 0
			&& subPagePosition < getSubPageCount()
			&& getSubPageCount() > 0;
	}

	/** 
	 * Set the subPage position to the subPage number specified.
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 * @param subPageNumber The requested subPage number.
	 */
	public boolean absolute(int subPageNumber)
	{
		subPagePosition = subPageNumber;
		return subPagePosition >= 0
			&& subPagePosition < getSubPageCount()
			&& getSubPageCount() > 0;
	}

	/** 
	 * Set the subPage position specified number of
	 * subPage from the current subPage position.
	 * @return boolean <B>True</B> - a valid subPage found.<br>
	 * <B>False</B> - invalid subPage position.
	 * @param numberOfSubPages The number of subPages from the current subPage to set the
	 * subPage position to.
	 */
	public boolean relative(int numberOfSubPages)
	{
		subPagePosition += numberOfSubPages;
		return subPagePosition >= 0
			&& subPagePosition < getSubPageCount()
			&& getSubPageCount() > 0;
	}

	/** 
	 * Is the specified operation valid based on the last operation results.
	 * @param operation The requested operation. Can be one of the following: GET_NEXT, GET_PREVIOUS, GET_FIRST, GET_LAST.
	 * @return boolean Whether the operation is valid or not.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public boolean isOperationAllowed(int operation)
		throws PersistenceException
	{
		try
		{
			if (operation == GET_NEXT)
			{
				if (subPagePosition < getSubPageCount() - 1
					&& getSubPageCount() > 0)
				{
					return true;
				}
			}
			else if (operation == GET_PREVIOUS)
			{
				if (subPagePosition > 0 && getSubPageCount() > 0)
				{
					return true;
				}
			}
			else if (operation == GET_FIRST)
			{
				if (getSubPageCount() > 0)
				{
					return true;
				}
			}
			else if (operation == GET_LAST)
			{
				if (getSubPageCount() > 0)
				{
					return true;
				}
			}
		}
		catch (Throwable e)
		{
			throw new PersistenceException(e);
		}
		return false;
	}

}