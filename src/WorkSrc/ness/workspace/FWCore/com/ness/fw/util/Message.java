/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: Message.java,v 1.3 2005/02/23 15:36:23 yifat Exp $
 */
package com.ness.fw.util;

import java.io.Serializable;
import java.util.*;

/**
 * Represent a message in the MessagesContainer.
 */
public class Message implements Serializable, Cloneable
{
	public static final int SEVERITY_CRITICAL =    1;
	public static final int SEVERITY_ERROR    =    2;
	public static final int SEVERITY_WARNING  =    4;
	public static final int SEVERITY_INFO     =    8;
	public static final int SEVERITY_DEBUG    =    16;

	/**
	 * The message id, will be translated before the message displaying to the user.
	 */
	private String messageId;
	/**
	 * The severity of the message. 
	 * could be one of the following: SEVERITY_ERROR, SEVERITY_WARNING, SEVERITY_INFO, SEVERITY_DEBUG, SEVERITY_CRITICAL.  
	 */
	private int severity;

	/**
	 * The thrown exception that related to the message.
	 */
	private Throwable throwable;

	/**
	 * A list of the message parameters to display in the messgae text. 
	 * The parameters will replace the %1, %2 parameters in the message text related to 
	 * the message id.  
	 */
	private ArrayList parameters;

	/**
	 * A list of the field names that will be marked as error when the message severity is error/warning.
	 */
	private ArrayList fieldNamesList;

	/**
	 * Creates new Message object.
	 * @param messageId The message id, will be translated before the message displaying to the user. 
	 * @param severity The severity of the message. 
	 * could be one of the following: SEVERITY_ERROR, SEVERITY_WARNING, SEVERITY_INFO, SEVERITY_DEBUG, SEVERITY_CRITICAL.
	 */
	public Message(String messageId, int severity)
	{
		this(messageId, null, severity);
	}
	
	/**
	 * Creates new Message object.
	 * @param messageId The message id, will be translated before the message displaying to the user. 
	 * @param throwable The thrown exception that related to the message. 
	 * @param severity The severity of the message. 
	 * could be one of the following: SEVERITY_ERROR, SEVERITY_WARNING, SEVERITY_INFO, SEVERITY_DEBUG, SEVERITY_CRITICAL.
	 */
	public Message(String messageId, Throwable throwable, int severity)
	{
		this.messageId = messageId;
		this.throwable = throwable;
		this.severity = severity;
	}

	/**
	 * Returns the thrown exception that related to the message.
	 * @return Throwable
	 */
	public Throwable getThrowable()
	{
		return throwable;
	}

	/**
	 * Add a field name to the field names list.
	 * The field will be marked as error when the message severity is error/warning.
	 * @param fieldName
	 */
	public void addFieldName(String fieldName)
	{
		if (fieldNamesList == null)
		{
			fieldNamesList = new ArrayList();
		}

		fieldNamesList.add(fieldName);
	}

	/**
	 * Returns the field name according to his index.
	 * @param index The field index
	 * @return String Field name
	 */
	public String getFieldName(int index)
	{
		return (String) fieldNamesList.get(index);
	}

	/**
	 * Retruns the fields count.
	 * @return int count
	 */
	public int getFieldsCount()
	{
		return fieldNamesList == null ? 0 : fieldNamesList.size();
	}

	/**
	 * Retruns the message id, will be translated before the message displaying to the user.
	 * @return String
	 */
	public String getMessageId()
	{
		return messageId;
	}

	/**
	 * Add a parameter value to the message parameters list.   
	 * @param parameterValue The parameter value to display in the messgae text. 
	 * The parameter will replace the %1, %2 parameter in the message text related to 
	 * the message id.
	 */
	public void addParameter(String parameterValue)
	{
		if (parameters == null)
		{
			parameters = new ArrayList();
		}

		parameters.add(new MessageParameter(parameterValue, MessageParameter.TYPE_NON_LOCALIZED));
	}

	/**
	 * Add a parameter value to the message parameters list.   
	 * @param parameterName The parameter name to translate from properties file and display in the message text. 
	 * The parameter will replace the %1, %2 parameter in the message text related to 
	 * the message id.
	 */
	public void addLocalizableParameter(String parameterName)
	{
		if (parameters == null)
		{
			parameters = new ArrayList();
		}

		parameters.add(new MessageParameter(parameterName, MessageParameter.TYPE_LOCALIZED));
	}

	/**
	 * The message parameters count.
	 * @return int count
	 */
	public int getParametersCount()
	{
		return parameters == null ? 0 : parameters.size();
	}

	/**
	 * Returns a specific message parameter according to the index.
	 * @param index The param index
	 * @return MessageParameter
	 */
	public MessageParameter getParameter(int index)
	{
		return (MessageParameter) parameters.get(index);
	}

	/**
	 * Returns the severity of the message. 
	 * could be one of the following: SEVERITY_ERROR, SEVERITY_WARNING, SEVERITY_INFO, SEVERITY_DEBUG, SEVERITY_CRITICAL.
	 * @return int
	 */
	public int getSeverity()
	{
		return severity;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return "Message: messageId=" + messageId + " severity=" + severity;
	}

}
