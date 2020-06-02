/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: NumberValidator.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.factory.*;
import com.ness.fw.util.*;

/**
 * Validator for Number Checks.
 */
public class NumberValidator implements ValidationCheck
{

	/**
	 * Check if the numeric value is in the range
	 * @param checkBundle
	 * @param value
	 * @param minValue
	 * @param maxValue
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isInRange(ValidationCheckBundle checkBundle, Number value, Number minValue, Number maxValue)
	{

		double dValue;
		double dMinValue;
		double dMaxValue;
		
		if (value == null || minValue == null || maxValue == null)
		{
			return true;
		}
		else
		{
			dValue = value.doubleValue();
			dMinValue = minValue.doubleValue();
			dMaxValue = maxValue.doubleValue();

			if (dValue >= dMinValue && dValue <= dMaxValue)
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
	
				message.addParameter(formatNumber(dMinValue));
				message.addParameter(formatNumber(dMaxValue));
	
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
	public boolean isNumberLessThen(ValidationCheckBundle checkBundle, Number value, Number maxValue)
	{
		double dValue;
		double dMaxValue;
		if (value == null || maxValue == null)
		{
			return true;
		}
		else 
		{
			dValue = value.doubleValue();
			dMaxValue = maxValue.doubleValue();

			if (dValue <= dMaxValue)
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
				message.addParameter(formatNumber(dMaxValue));
	
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
	public boolean isNumberGreaterThen(ValidationCheckBundle checkBundle, Number value, Number minValue)
	{

		double dValue;
		double dMinValue;
		
		if (value == null || minValue == null)
		{
			return true;
		}
		else
		{ 
			dValue = value.doubleValue();
			dMinValue = minValue.doubleValue();
			
			if (dValue > dMinValue)
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
				message.addParameter(formatNumber(dMinValue));
	
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
	public boolean isInNumberList(ValidationCheckBundle checkBundle, Number value, Number list[])
	{
		double dValue;
		if (value == null)
		{
			return true;
		}
		else 
		{
			dValue = value.doubleValue();
			for (int i = 0; i < list.length; i++)
			{
				Number s = list[i];
				if(s.doubleValue() == (dValue))
				{
					return true;
				}
			}
		}

		Message message = new Message("GE0007", checkBundle.getSeverity());
		message.addParameter(formatNumber(dValue));
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
	public boolean checkMaxDigits(ValidationCheckBundle checkBundle, Integer value, Integer maxDigits)
	{
		int numDigits;
		if (value == null || maxDigits == null)
		{
			return true;
		}
		else 
		{
			numDigits = value.toString().length();
			if (numDigits <= maxDigits.intValue())
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0021", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}
				message.addParameter(String.valueOf(maxDigits));
	
				checkBundle.getMessagesContainer().addMessage(message);
	
				return false;
			}
		}
		
	}
	
	private String formatNumber(double number)
	{
		String formatNumber = null;
		if ((number - (int)number) == 0)
		{
			formatNumber = String.valueOf((int)number);
		}
		else
		{
			formatNumber = String.valueOf(number);
		}
		
		return formatNumber;
	}

	
}
