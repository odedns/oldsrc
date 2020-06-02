/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: StringValidator.java,v 1.2 2005/05/04 11:53:35 yifat Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.util.*;

/**
 * Validator for String Checks.
 */
public class StringValidator implements ValidationCheck
{

	/**
	 * Check if the length of the string value is in the length range
	 * @param checkBundle
	 * @param str
	 * @param minLength
	 * @param maxLength
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean checkLengthRange(ValidationCheckBundle checkBundle, String str, Integer minLength, Integer maxLength)
	{
		if (str != null)
		{
			if(str.length() >= minLength.intValue() && str.length() <= maxLength.intValue())
			{
				return true;
			}
		}
		else
		{
			return true;
		}

		Message message = new Message("GE0008", checkBundle.getSeverity());
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		message.addParameter(String.valueOf(minLength.intValue()));
		message.addParameter(String.valueOf(maxLength.intValue()));

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}

	/**
	 * Check if the length of the string value is not greater then the maxLength parameter
	 * @param checkBundle
	 * @param str
	 * @param maxLength
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean checkMaxLength(ValidationCheckBundle checkBundle, String str, Integer maxLength)
	{
		if (str != null)
		{
			if(str.length() >= 0 && str.length() <= maxLength.intValue())
			{
				return true;
			}
		}
		else
		{
			return true;
		}

		Message message = new Message("GE0009", checkBundle.getSeverity());
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		message.addParameter(String.valueOf(maxLength.intValue()));

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}


	/**
		 * Check if the length of the string value is not less then the minLength parameter
		 * @param checkBundle
		 * @param str
		 * @param minLength
		 * @return boolean True if the check was successful, False if the check failed.
		 */
		public boolean checkMinLength(ValidationCheckBundle checkBundle, String str, Integer minLength)
		{
			if (str != null)
			{
				if(str.length() ==0 || str.length() > minLength.intValue())
				{
					return true;
				}
			}
			else
			{
				return true;
			}

			Message message = new Message("GE0038", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}

			message.addParameter(String.valueOf(minLength.intValue()));

			checkBundle.getMessagesContainer().addMessage(message);

			return false;
		}


	/**
		 * Check if the length of the string value is equals to the length parameter
		 * @param checkBundle
		 * @param str
		 * @param minLength
		 * @return boolean True if the check was successful, False if the check failed.
		 */
		public boolean checkEqualLength(ValidationCheckBundle checkBundle, String str, Integer length)
		{
			if (str != null)
			{
				if(str.length() ==0 || str.length() == length.intValue())
				{
					return true;
				}
			}
			else
			{
				return true;
			}

			Message message = new Message("GE0039", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}

			message.addParameter(String.valueOf(length.intValue()));

			checkBundle.getMessagesContainer().addMessage(message);

			return false;
		}

	/**
	 * Check if the string value is numeric
	 * @param checkBundle
	 * @param str
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isNumeric(ValidationCheckBundle checkBundle, String str)
	{
		if (StringFormatterUtil.isNumeric(str))
			return true;

		Message message = new Message("GE0010", checkBundle.getSeverity());
		message.addParameter(str);
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}

	/**
	 * Check if the string value contained in the contained parameter.
	 * @param checkBundle
	 * @param container
	 * @param contained
	 * @return boolean True if the check was successful, False if the check failed.
	 * @throws ContextException
	 */
	public boolean isStringContains(ValidationCheckBundle checkBundle, String container, String contained) throws ContextException
	{
		if (container != null && contained != null)
		{
			if (container.indexOf(contained) != -1)
			{
				return true;
			}
		}
		else
		{
			return true;
		}

		Message message = new Message("GE0011", checkBundle.getSeverity());
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		message.addParameter(contained);

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}

	/**
	 * Check if the string value is contained in the list of items
	 * @param checkBundle
	 * @param value
	 * @param list
	 * @return boolean True if the check was successful, False if the check failed.
	 */	
	public boolean isInStringList(ValidationCheckBundle checkBundle, String value, String list[])
	{
		if (value == null)
		{
			return true;
		}
		else for (int i = 0; i < list.length; i++)
		{
			String s = list[i];
			if(s.equals(value))
			{
				return true;
			}
		}

		Message message = new Message("GE0007", checkBundle.getSeverity());
		message.addParameter(value);
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}
	
}
