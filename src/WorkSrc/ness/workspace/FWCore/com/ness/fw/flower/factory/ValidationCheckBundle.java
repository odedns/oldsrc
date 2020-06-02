/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: ValidationCheckBundle.java,v 1.1 2005/02/21 15:07:09 baruch Exp $
 */
package com.ness.fw.flower.factory;

import com.ness.fw.util.*;

import java.util.*;

/**
 * com.ness.fw.flower.factory
 * ${CLASS_NAME}
 */
public class ValidationCheckBundle
{
	private MessagesContainer messagesContainer;
	private ValidationCheckFieldData validationCheckFieldData[] = null;
	private ArrayList validationCheckFieldDataArrayList = null;
	private int severity;

	public ValidationCheckBundle(MessagesContainer messagesContainer, ValidationCheckFieldData[] validationCheckFieldData, int severity)
	{
		this.messagesContainer = messagesContainer;
		this.validationCheckFieldData = validationCheckFieldData;
		this.severity = severity;
	}

	public ValidationCheckBundle(MessagesContainer messagesContainer, int severity)
	{
		this.messagesContainer = messagesContainer;
		this.severity = severity;
		validationCheckFieldDataArrayList = new ArrayList();
	}

	public void addValidationCheckFieldData(String fieldName, String caption, boolean affected)
	{
		if (validationCheckFieldDataArrayList == null)
		{
			throw new RuntimeException("Call to the method is not permited if bundle constructed with ValidationCheckFieldData array");
		}

		validationCheckFieldDataArrayList.add(new ValidationCheckFieldData(fieldName, caption, affected));
	}

	public MessagesContainer getMessagesContainer()
	{
		return messagesContainer;
	}

	public ValidationCheckFieldData[] getValidationCheckFieldData()
	{
		if (validationCheckFieldData == null)
		{
			validationCheckFieldData = new ValidationCheckFieldData[validationCheckFieldDataArrayList.size()];
			for (int i = 0; i < validationCheckFieldData.length; i++)
			{
				validationCheckFieldData[i] = (ValidationCheckFieldData) validationCheckFieldDataArrayList.get(i);
			}

		}

		return validationCheckFieldData;
	}

	public int getSeverity()
	{
		return severity;
	}
}
