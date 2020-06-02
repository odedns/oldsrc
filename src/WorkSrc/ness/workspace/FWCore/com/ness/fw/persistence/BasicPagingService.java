/*
 * Author: yifat har-nof
 * @version $Id: BasicPagingService.java,v 1.1 2005/02/21 15:07:18 baruch Exp $
 */
package com.ness.fw.persistence;

import java.io.Serializable;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * This class is the super class for all paging services.
 */
public abstract class BasicPagingService implements PagingService , Serializable
{

	/** The amount of rows to be returned in each page.
	 */
	protected int rowsInPage;

	/** Are there any more pages after the last one fetched.
	 */
	protected boolean hasMorePages = false;


	/** Create a new PagingService using the default ConnectionManager.
	 * @param rowsInPage The number of rows in each page.
	 */
	public BasicPagingService(int rowsInPage)
	{
		this.rowsInPage = rowsInPage;
	}

	/**
	 * Returns the indication whether the current object has more pages.
	 * @return boolean returns true when the current object has more pages.
	 */
	public boolean isHasMorePages()
	{
		return hasMorePages;
	}
	
	/** Get the first page of rows.
	 * @return Page The first page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract Page firstPage(ConnectionProvider connectionProvider) throws PersistenceException;

	/** Get the last page of rows.
	 * @return Page The last page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract Page lastPage(ConnectionProvider connectionProvider) throws PersistenceException;

	/** Get the next page of rows.
	 * @return Page The next page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract Page nextPage(ConnectionProvider connectionProvider) throws PersistenceException;

	/** Get the previous page of rows.
	 * @return Page The previous page of rows in the form of a Page.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract Page previousPage(ConnectionProvider connectionProvider) throws PersistenceException;
	

}
