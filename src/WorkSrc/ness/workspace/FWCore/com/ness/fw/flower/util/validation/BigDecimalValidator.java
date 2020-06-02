/*
 * Created on: 1/10/2003
 * Author: yifat har-nof
 * @version $Id: BigDecimalValidator.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import java.math.BigDecimal;

import com.ness.fw.flower.factory.*;
import com.ness.fw.util.*;

/**
 * Validator for Number Checks.
 */
public class BigDecimalValidator implements ValidationCheck
{

	/**
	 * Check if the numeric value is in the range
	 * @param checkBundle
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isInRange(ValidationCheckBundle checkBundle, BigDecimal value, BigDecimal minValue, BigDecimal maxValue)
	{
		
		if (value == null || minValue == null || maxValue == null)
		{
			return true;
		}
		else
		{

			if (value.compareTo(minValue) >=0  && value.compareTo(maxValue) <=0)
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0014", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}
	
				message.addParameter(String.valueOf(minValue));
				message.addParameter(String.valueOf(maxValue));
	
				checkBundle.getMessagesContainer().addMessage(message);
	
				return false;
			}
		}
	}

	/**
	 * Check if the numeric value is not greater then the maxValue parameter
	 * @param checkBundle
	 * @param value
	 * @param maxValue
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isNumberLessThen(ValidationCheckBundle checkBundle, BigDecimal value, BigDecimal maxValue)
	{
		if (value == null || maxValue == null)
		{
			return true;
		}
		else 
		{
			if (value.compareTo(maxValue) <=0)
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0022", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}
				message.addParameter(String.valueOf(maxValue));
	
				checkBundle.getMessagesContainer().addMessage(message);
	
				return false;
			}
		}
	}
	
	/**
	 * Check if the numeric value is not less then the minValue parameter
	 * @param checkBundle
	 * @param value
	 * @param maxValue
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isNumberGreaterThen(ValidationCheckBundle checkBundle, BigDecimal value, BigDecimal minValue)
	{
		if (value == null || minValue == null)
		{
			return true;
		}
		else
		{ 
			if (value.compareTo(minValue) > 0)
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0029", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}
				message.addParameter(String.valueOf(minValue));
	
				checkBundle.getMessagesContainer().addMessage(message);
	
				return false;
			}
		}
	}


	/**
	 * Check if the numeric value is contained in the list of items
	 * @param checkBundle
	 * @param value
	 * @param list
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isInNumberList(ValidationCheckBundle checkBundle, BigDecimal value, BigDecimal list[])
	{
		if (value == null)
		{
			return true;
		}
		else 
		
		for (int i = 0; i < list.length; i++)
		{
			BigDecimal s = list[i];
			if(s.compareTo(value) == 0)
			{
				return true;
			}
		}

		Message message = new Message("GE0007", checkBundle.getSeverity());
		message.addParameter(String.valueOf(value));
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}
	
	/**
	 * Check if the length of the number value is not greater then the maxLength parameter
	 * @param checkBundle
	 * @param value
	 * @param maxDigits
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean checkMaxDigits(ValidationCheckBundle checkBundle, BigDecimal value, Integer maxScaleDigits, Integer maxWholeDigits)
	{
		int numWholeDigits;
		int numScaleDigits;
		
		if (value == null || maxScaleDigits == null || maxWholeDigits == null)
		{
			return true;
		}
		else 
		{
			numScaleDigits = value.scale();
			numWholeDigits = value.unscaledValue().toString().length() - numScaleDigits ;

			if (numScaleDigits <= maxScaleDigits.intValue() && numWholeDigits <= maxWholeDigits.intValue())
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0043", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}

				message.addParameter(String.valueOf(maxScaleDigits));
				message.addParameter(String.valueOf(maxWholeDigits));
	
				checkBundle.getMessagesContainer().addMessage(message);
	
				return false;
			}
		}
	}
	

}
