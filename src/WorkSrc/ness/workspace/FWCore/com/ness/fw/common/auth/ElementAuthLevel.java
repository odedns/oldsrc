/*
 * Created on 15/03/2004
 * Author: yifat har-nof
 * @version $Id: ElementAuthLevel.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.common.auth;

/**
 * Holds the authorization level of a specific element.
 */
public class ElementAuthLevel 
{
	public static final int AUTH_LEVEL_ALL = AuthManagerImpl.AUTH_LEVEL_ALL;
	public static final int AUTH_LEVEL_READONLY = AuthManagerImpl.AUTH_LEVEL_READONLY;
	public static final int AUTH_LEVEL_NONE = AuthManagerImpl.AUTH_LEVEL_NONE;

	/**
	 * The authorization level determined for the element.
	 */
	private int authLevel;
	
	/**
	 * Indication whether the authorization level of the element was inserted 
	 * to the stack, and should be removed when the flow ends.
	 */
	private boolean authLevelInsertedToStack;
	
	/**
	 *  
	 * @param authLevel The authorization level determined for the element.
	 * @param authLevelInsertedToStack Indication whether the authorization
	 * level of the element was inserted  to the stack, and should be removed 
	 * when the flow ends.
	 */
	public ElementAuthLevel(int authLevel, boolean authLevelInsertedToStack)
	{
		this.authLevel = authLevel;
		this.authLevelInsertedToStack = authLevelInsertedToStack;
	}

	/**
	 * Returns the authorization level determined for the element.
	 * @return int authLevel
	 */
	public int getAuthLevel() 
	{
		return authLevel;
	}

	/**
	 * Indicates whether the authorization level of the element was inserted to the stack,
	 * and should be removed when the flow ends.
	 * @return boolean
	 */
	public boolean isAuthLevelInsertedToStack() 
	{
		return authLevelInsertedToStack;
	}

	/**
	 * Sets the indication whether the authorization  level of the element was 
	 * inserted  to the stack, and should be removed when the flow ends.
	 * @param boolean authLevelInsertedToStack
	 */
	public void setAuthLevelInsertedToStack(boolean authLevelInsertedToStack) {
		this.authLevelInsertedToStack = authLevelInsertedToStack;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return " authLevel [" + authLevel + "] authLevelInsertedToStack [" + authLevelInsertedToStack + "]";
	}

}
