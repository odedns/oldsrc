/*
 * Author: yifat har-nof
 * @version $Id: StoredProcedureResults.java,v 1.2 2005/04/04 09:37:34 yifat Exp $
 */
package com.ness.fw.persistence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains the execution results of the {@link StoredProcedureService} object.
 */
public class StoredProcedureResults
{

	/** The {@link Page}s, built from the Result Sets of the procedure execution. 
	 */
	private ArrayList pages = null;

	/**  The output values of the procedure execution.
	 */
	private HashMap outputValues = null;

	/**
	 * set a specific parameter output value from the procedure execution.
	 * @param index The index of the parameter.
	 * @param value The output value.
	 */
	protected void setOutputValue(int index, Object value)
	{
		if (outputValues == null)
		{
			outputValues = new HashMap();
		}
		outputValues.put(new Integer(index), value);
	}

	/** Get a specific parameter output value.
	 * @param parameterIndex The parameter index to get the value of.
	 * @return The output value of the specified parameter.
	 */
	public Object getOutputValue(int parameterIndex)
	{
		return outputValues.get(new Integer(parameterIndex));
	}

	/**
	 * Add a {@link Page} to the pages array returned as ResultSets from the procedure execution.
	 * @param page The page to add.
	 */
	protected void addPage(Page page)
	{
		if (pages == null)
		{
			pages = new ArrayList();
		}
		pages.add(page);
	}

	/** Get the number of {@link Page}s returned from the procedure execution.
	 * @return The number of {@link Page}s returned from the procedure execution.
	 */
	public int getResultPagesCount()
	{
		if (pages != null)
		{
			return pages.size();
		}
		return 0;
	}

	/** Get a specific Page returned from the procedure execution.
	 * @param index The Page index to get.
	 * @return The {@link Page} of the specified index.
	 */
	public Page getResultPage(int index)
	{
		if (pages != null)
		{
			return (Page) pages.get(index);
		}
		return null;
	}

}
