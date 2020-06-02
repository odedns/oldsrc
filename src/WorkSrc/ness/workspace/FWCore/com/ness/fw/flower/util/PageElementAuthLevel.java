/*
 * Created on 24/03/2004
 * Author: yifat har-nof
 * @version $Id: PageElementAuthLevel.java,v 1.1 2005/02/21 15:07:20 baruch Exp $
 */
package com.ness.fw.flower.util;

import com.ness.fw.common.auth.ElementAuthLevel;

/**
 * Holds the authorization level of a page element and the jsp page name.
 */
public class PageElementAuthLevel extends ElementAuthLevel
{
	/**
	 * The authorized page.
	 */
	private String page;

	/**
	 * 
	 * @param authLevel The authorization level determined for the element.
	 * @param authLevelInsertedToStack Indication whether the authorization
	 * level of the element was inserted  to the stack, and should be removed 
	 * when the flow ends.
	 * @param page The authorized page.
	 */
	public PageElementAuthLevel(
		int authLevel,
		boolean authLevelInsertedToStack,
		String page)
	{
		super(authLevel, authLevelInsertedToStack);
		this.page = page;
	}

	/**
	 * Returns the authorized page.
	 * @return String
	 */
	public String getPage()
	{
		return page;
	}

	/**
	 * Indicates whether the element contains page. 
	 * @return boolean
	 */
	public boolean isContainsPage()
	{
		return page != null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Page [" + page + "] " + super.toString();
	}

}
