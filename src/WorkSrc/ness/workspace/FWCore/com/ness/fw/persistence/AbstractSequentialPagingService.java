/*
 * Author: yifat har-nof
 * @version $Id: AbstractSequentialPagingService.java,v 1.2 2005/04/04 09:39:22 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.*;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Provide basic implementation of sequential paging.
 */
public abstract class AbstractSequentialPagingService
	extends BasicPagingService
{
	/** A constant that says that  a key order is ascending
	 */
	public static final boolean ASCEND = true;
	/** A constant that says that  a key order is descending
	 */
	public static final boolean DESCEND = false;
	/** XML-like tag that mark the place where the parser should
	 * generate the keys to start from within the WHERE clause.
	 */
	public static final String PAGING_WHERE_TAG = "<paging:where/>";
	/** XML-like tag that mark the place where the parser should
	 * generate the paging keys within the ORDER BY clause.
	 */
	public static final String PAGING_SORT_TAG = "<paging:sort/>";
	/** The Statement of the initial SQL Service to use.
	 */
	protected String statement;
	/** The Parameters of  the initial SQL Service.
	 */
	protected ArrayList sqlServiceParams;
	/** The Key names.
	 */
	protected List keyNames;
	/**  The keys ordering asc/desc
	 */
	protected List keyOrder;
	/** Is first (or last) page requested ?
	 */
	protected boolean isFirstPage = true;

	/** Create a new PagingService.
	 * @param sqlService The SqlService to use.
	 * @param rowsInPage The number of rows in each page.
	 * @param dbProperties The database properties file to use.
	 */
	public AbstractSequentialPagingService(
		SqlService sqlService,
		int rowsInPage)
	{
		super(rowsInPage);
		this.statement = sqlService.getStatementString();
		this.sqlServiceParams =
			new ArrayList((ArrayList) sqlService.getParameters());
		this.keyNames = new ArrayList();
		this.keyOrder = new ArrayList();
	}

	/** Add a key to be used in the paging mechanism with default ascending order.
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param columnName The Column name to be added as a key.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void addPagingKey(String columnName) throws PersistenceException
	{
		addOrderedPagingKey(columnName, ASCEND);
	}

	/** Add a key to be used in the paging mechanism with specified order.
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param columnName The Column name to be added as a key.
	 * @param order The Column order. can be ASCEND or DESCEND.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public abstract void addOrderedPagingKey(String columnName, boolean order)
		throws PersistenceException;

	/** Add a List of keys to be used in the paging mechanism.
	 * these keys will be used in the SQL WHERE and ORDER BY clauses.
	 * @param pagingKeys List of Column names to be used as keys.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void setPagingKeys(List pagingKeys) throws PersistenceException
	{
		Iterator i = pagingKeys.iterator();
		while (i.hasNext())
		{
			addPagingKey((String) i.next());
		}
	}

	/** Get a list of the paging key names set for this service.
	 * @return String[] An array of the paging key names set for this service.
	 */
	public String[] getPagingKeyNames()
	{
		return (String[]) keyNames.toArray();
	}

}