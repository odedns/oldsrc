/*
 * Author: shay rancus
 * @version $Id: UIException.java,v 1.1 2005/02/21 15:07:19 baruch Exp $
 */
package com.ness.fw.common.exceptions;

/**
 * Exception thrown from the UI layer.
 * @alias UIException
 */
public class UIException extends GeneralException
{
	protected String exceptionSourceTag; 
	protected String exceptionCause;
	
	/**
	 * create new UIException Object
	 */
	public UIException(Throwable error)
	{
		super(error);
		exceptionCause = error.getMessage();
	}

	/**
	 * create new UIException Object
	 */
	public UIException(String messageText, Throwable error)
	{
		super(messageText,error);
		exceptionCause = messageText;
	}

	/**
	 * create new UIException Object
	 */
	public UIException(String messageText)
	{
		super(messageText);
		exceptionCause = messageText;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuffer message = new StringBuffer();
		if (exceptionSourceTag == null)
		{
			exceptionSourceTag = "no information about source tag";
		}
		message.append("UIException thrown from tag " + exceptionSourceTag + "\n");
		message.append(exceptionCause);
		
		message.append(super.toString());
		return message.toString();
	}

	/**
	 * Overriding getMessage in exception
	 * @return the detial message
	 */
	public String getMessage()
	{
		StringBuffer message = new StringBuffer();
		if (exceptionSourceTag == null)
		{
			exceptionSourceTag = "no information about source tag";
		}
		message.append("UIException thrown from tag " + exceptionSourceTag + "\n");
		message.append(exceptionCause);
		
		return message.toString();
	}

	
	/**
	 * @return
	 */
	public String getUITagName()
	{
		
		return exceptionSourceTag;
	}

	/**
	 * @param string
	 */
	public void setUITagName(String string)
	{
		exceptionSourceTag = string;
	}

}
