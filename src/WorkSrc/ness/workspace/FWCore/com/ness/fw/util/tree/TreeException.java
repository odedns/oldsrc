/*
 * Created on 10/10/2004
 *
 */
package com.ness.fw.util.tree;

import com.ness.fw.common.exceptions.GeneralException;

/**
 * @author Amit Mendelson
 *
 * This class represents Exceptions thrown from the com.ness.fw.util.tree package.
 */
public class TreeException extends GeneralException
{

	/**
	 * create new TreeException Object
	 */
	public TreeException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new TreeException Object
	 */
	public TreeException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new TreeException Object
	 */
	public TreeException(String messageText)
	{
		super(messageText);
	}
}
