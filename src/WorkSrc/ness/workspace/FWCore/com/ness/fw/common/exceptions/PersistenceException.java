/*
 * Author: yifat har-nof
 * @version $Id: PersistenceException.java,v 1.2 2005/04/12 13:41:23 baruch Exp $
 */
package com.ness.fw.common.exceptions;

import java.io.PrintWriter;
import java.sql.SQLException;

import com.ness.fw.util.*;

/**
 * Exception thrown from the persistence layer.
 */
public class PersistenceException extends GeneralException
{

	/**
	 * The {@link MessageContainer} that conatines the application messages 
	 * related to the current Exception. 
	 */
	private MessagesContainer messagesContainer;

	/**
	 * create new PersistenceException Object
	 */
	public PersistenceException(Throwable error)
	{
		super(error);
	}

	/**
	 * create new PersistenceException Object
	 */
	public PersistenceException(String messageText, Throwable error)
	{
		super(messageText, error);
	}

	/**
	 * create new PersistenceException Object
	 */
	public PersistenceException(String messageText)
	{
		super(messageText);
	}

	/**
	 * create new LockException Object
	 */
	public PersistenceException (String messageText, Message message) 
	{
		super(messageText);
		addMessage(message);
	}

	/**
	 * create new LockException Object
	 */
	public PersistenceException (String messageText, Throwable error, Message message) 
	{
		super(messageText, error);
		addMessage(message);
	}

	/**
	 * create new PersistenceException Object
	 */
	public PersistenceException (String messageText, MessagesContainer container) 
	{
		super(messageText);
		addMessages(container);
	}


	/**
	 * add a new message to the message container. 
	 */
	public void addMessage(Message message)
	{
		if (messagesContainer == null)
			messagesContainer = new MessagesContainer();

		messagesContainer.addMessage(message);
	}

	/**
	/**
	 * add all the messages from the container to the current one. 
	 */
	public void addMessages(MessagesContainer container)
	{
		if(container != null)
		{
			if (messagesContainer == null)
				messagesContainer = new MessagesContainer();

			messagesContainer.merge(container);
		}
	}


	/* 
	 * @see java.lang.Throwable#printStackTrace(java.io.PrintWriter)
	 */
	public void printStackTrace(PrintWriter pw)
	{
		super.printStackTrace(pw);

		if (getCause() != null)
		{
			pw.write(getErrMessage(getCause()));
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		String errText = super.toString();
		if (getCause() != null)
		{
			errText += getErrMessage(getCause());
		}
		return errText;
	}

	/**
	 * Returns the error message of the {@link SQLException}.  
	 * @param sqle The SQLException that occured.
	 * @return String Error message
	 */
	private String getErrMessage(Throwable throwable)
	{
		String msg = "";
		if(throwable != null && throwable instanceof SQLException)
		{
			SQLException sqle = (SQLException)throwable;
			msg += "\nCause: " 
					+ sqle.getClass().getName()
					+ "\nErrorCode="
					+ sqle.getErrorCode()
					+ " SQLState="
					+ sqle.getSQLState()
					+ "\nErrorText="
					+ sqle.getMessage();
			if (sqle.getNextException() != null)
			{
				msg += "\n Next Exception="
					+ getFullExceptionString(sqle.getNextException());
			}
		}
			
		return msg;
	}

	/**
	 * Returns the exceptions stack trace of all the exceptions included in the given 
	 * {@link SQLException}
	 * @param next The SQLException to format.
	 * @return String The full stack trace.
	 */
	private String getFullExceptionString(SQLException next)
	{
		String errText = " ";
		while (next != null)
		{
			errText += " " + next.getMessage();
			next = next.getNextException();
		}
		return errText;
	}
	

	/**
	 * @return
	 */
	public MessagesContainer getMessagesContainer()
	{
		return messagesContainer;
	}
	
	/**
	 * @return
	 */
	public int getMessagesCount()
	{
		if(messagesContainer != null)
			return messagesContainer.getMessagesCount();
		return 0;
	}
	

}