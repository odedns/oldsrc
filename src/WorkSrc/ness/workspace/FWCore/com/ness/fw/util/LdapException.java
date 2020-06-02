/*
 * Created on 28/11/2004
 * Author: Amit Mendelson
 * @version $Id: LdapException.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.util;

import com.ness.fw.common.exceptions.GeneralException;

/**
 */
public class LdapException extends GeneralException
{


	/**
	 * 
	 * @param error
	 */
	public LdapException(Throwable error)
	{
		super(error);
	}


	/**
	 * create new AbstractException Object
	 */
	public LdapException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new AbstractException Object
	 */
	public LdapException(String messageText)
	{
		super(messageText);
	}
}
