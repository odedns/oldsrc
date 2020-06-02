/*
 * Created on: 24/06/2003
 * Author: yifat har-nof
 * @version $Id: ContextList.java,v 1.1 2005/02/21 15:07:11 baruch Exp $
 */
package com.ness.fw.flower.core;

import java.util.*;

/**
 * The class provides implementation of list of {@link Context}s. 
 * Based on standard {@link ArrayList} implementation.
 */
public class ContextList
{
	/**
	 * A list of {@link Context}s.
	 */
	private ArrayList contexts;

	/**
	 * Creates new ContextList object. 
	 */
	public ContextList()
	{
		contexts = new ArrayList();
	}


	/**
	 * Add a {@link Context} to the list 
	 * @param ctx Context to add.
	 */
	public void addContext(Context ctx)
	{
		contexts.add(ctx);
	}

	/**
	 * Returns the Context by the index. 
	 * @param index The index of the context.
	 * @return Context
	 */
	public Context getContext(int index)
	{
		return (Context) contexts.get(index);
	}

	/**
	 * Returns the number of contexts in the list.
	 * @return int Contexts count.
	 */
	public int getContextCount()
	{
		return contexts.size();
	}
}
