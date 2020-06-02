/*
 * Created on: 15/12/2003
 * Author: yifat har-nof
 * @version $Id: MessagesContainer.java,v 1.3 2005/04/14 15:57:11 yifat Exp $
 */
package com.ness.fw.util;

import java.io.Serializable;
import java.util.*;

/**
 * A container for the user messages. 
 */
public class MessagesContainer implements Serializable, Cloneable
{
	private ArrayList list;
	private HashMap htmlName2businessNameMap;
	private HashMap messagesByFieldName;

	public MessagesContainer()
	{
		list = new ArrayList();
		htmlName2businessNameMap = new HashMap();
		messagesByFieldName = new HashMap();
	}

	/**
	 * Returns the message according to the given index
	 * @param index The message index
	 * @return Message
	 */
	public Message getMessage(int index)
	{
		return (Message) list.get(index);
	}

	/**
	 * add a new message to the message container. 
	 * @param message The message to add.
	 */
	public void addMessage(Message message)
	{
		list.add(message);
		for (int i = 0; i < message.getFieldsCount(); i++)
		{
			messagesByFieldName.put(message.getFieldName(i), message);
		}
	}

	/**
	 * Returns the messages count.
	 * @return int
	 */
	public int getMessagesCount()
	{
		return list.size();
	}

	public void addMapping(String htmlName, String businessName)
	{
		htmlName2businessNameMap.put(htmlName, businessName);
	}

	public String getHTMLFieldName(String businessName)
	{
		String htmlFieldName = getFieldMapping(businessName);
		if (htmlFieldName == null)
		{
			htmlFieldName = businessName;
		}
		
		return htmlFieldName;
	}

	public String getFieldMapping(String businessName)
	{
		String retVal = null;
		if(htmlName2businessNameMap.containsValue(businessName))
		{
			Iterator iter = htmlName2businessNameMap.keySet().iterator();
			while (iter.hasNext())
			{
				String key = (String) iter.next();
				String value = (String) htmlName2businessNameMap.get(key);
				if(value != null && value.equals(businessName))
				{
					retVal = key;
					break;
				}
			}
		}
		return retVal;
	}


	public Message getMessageByFieldName(String fieldName)
	{
		String temp = (String) htmlName2businessNameMap.get(fieldName);
		if (temp != null)
		{
			fieldName = temp;
		}

		return (Message) messagesByFieldName.get(fieldName);
	}

	public void merge(MessagesContainer container)
	{
		for (int i = 0; i < container.getMessagesCount(); i++)
		{
			Message msg = container.getMessage(i);
			addMessage(msg);
			for (int j = 0; j < msg.getFieldsCount(); j++)
			{
				String localField = msg.getFieldName(j);
				System.out.println(container.htmlName2businessNameMap);
				String mappingField = container.getFieldMapping(localField);
				if(mappingField != null)
				{
					addMapping(mappingField,localField);
				}
			}
			
		}
	}
	
	/**
	 * check if the contains severe error messages that should stop the process.
	 * @return true when the container contains severe errors.
	 */
	public boolean containsErrors()
	{
		boolean containsErrors = false;
		int severity;
		int count = getMessagesCount();
		for (int i = 0 ; i < count ; i++)
		{
			severity = getMessage(i).getSeverity();
			if (severity == Message.SEVERITY_CRITICAL ||
				severity == Message.SEVERITY_ERROR)
			{
				containsErrors = true;
				break;  
			}
		}		
		return containsErrors;
	}

	/**
	 * check if the contains warning messages that should be accepted by the user.
	 * @return true when the container contains warnings.
	 */
	public boolean containsWarnings()
	{
		boolean containsWarnings = false;
		int count = getMessagesCount();
		for (int i = 0 ; i < count ; i++)
		{
			if (getMessage(i).getSeverity() == Message.SEVERITY_WARNING)
			{
				containsWarnings = true;
				break;  
			}
		}		
		return containsWarnings;
	}

	/**
	 * Check if contains errors or warning messages.
	 * @return true when the container contains errors or warnings.
	 */
	public boolean containsErrorsOrWarnings()
	{
		return containsErrors() || containsWarnings();
	}


	/** Sort the masseage container according to the severity and the adding order.
	 */
	public void sort() 
	{
		ArrayList sortedMessages = new ArrayList();
		filterMessages(sortedMessages,Message.SEVERITY_CRITICAL);
		filterMessages(sortedMessages,Message.SEVERITY_ERROR);
		filterMessages(sortedMessages,Message.SEVERITY_WARNING);
		filterMessages(sortedMessages,Message.SEVERITY_INFO);
		filterMessages(sortedMessages,Message.SEVERITY_DEBUG);
		list.clear();
		list.addAll(sortedMessages);
	}


	/**
	 * fill the sortedMessages with the messages that got a severity identicak to
	 * the parameter severity
	 * @param sortedMessages
	 * @param severity
	 */
	private void filterMessages(ArrayList sortedMessages, int severity)
	{
		for (int i=0; i<list.size(); i++)
		{
			Message message =  (Message)list.get(i);
			if (message.getSeverity() == severity)
			{
				sortedMessages.add(message);
			}
		}		
	}
}
