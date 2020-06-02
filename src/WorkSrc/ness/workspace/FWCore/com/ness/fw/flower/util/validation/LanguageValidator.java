/*
 * Created on: 15/10/2004
 * Author: David Dahan
 * @version $Id: LanguageValidator.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.factory.ValidationCheck;
import com.ness.fw.flower.factory.ValidationCheckBundle;
import com.ness.fw.util.Message;

/**
 * This validator check that the field given is in a certain language
 */
public class LanguageValidator implements ValidationCheck
{

	/**
	 * check that a certain field is written in hebrew and is without numbers
	 * 
	 * @param checkBundle
	 * @param field
	 * @return true if it is written just in hebrew
	 */
	public boolean isHebrew(ValidationCheckBundle checkBundle, String field)
	{
		boolean returnedValue = true;
		// if the field to check is null, skip the check!!
		if (field != null)
		{
			// for all chars in string 
			for (int i = 0; i < field.length(); i++)
			{
				// check the char is in hebrew
				if ((!Character.UnicodeBlock.HEBREW.equals(Character.UnicodeBlock.of(field.charAt(i))) 
				    && Character.isLetter(field.charAt(i))) || (Character.isDigit(field.charAt(i))))
				{
					returnedValue = false;
				}
			}

			if (returnedValue == false)
			{
				Message message = new Message("GE0040", Message.SEVERITY_ERROR);
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				message.addLocalizableParameter("system.hebrew");
				checkBundle.getMessagesContainer().addMessage(message);
			}
		}
		return returnedValue;
	}

	/**
	 * check that a certain field is written in latin characters without 
	 * numbers in it.
	 * 
	 * @param checkBundle
	 * @param field
	 * @return true if it is written just in latin
	 */
	public boolean isLatin(ValidationCheckBundle checkBundle, String field)
	{
		boolean returnedValue = true;
		// if the field to check is null, skip the check!!
		if (field != null)
		{
			// for all chars in string 
			for (int i = 0; i < field.length(); i++)
			{
				//	check that the char is in latin
				if ((!Character.UnicodeBlock.BASIC_LATIN.equals(Character.UnicodeBlock.of(field.charAt(i))) && Character.isLetter(field.charAt(i)))
					|| (Character.isDigit(field.charAt(i))))
				{
					returnedValue = false;
				}
			}
			if (returnedValue == false)
			{
				Message message = new Message("GE0040", Message.SEVERITY_ERROR);
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				message.addLocalizableParameter("system.latin");
				checkBundle.getMessagesContainer().addMessage(message);
			}
		}
		return returnedValue;
	}
}
