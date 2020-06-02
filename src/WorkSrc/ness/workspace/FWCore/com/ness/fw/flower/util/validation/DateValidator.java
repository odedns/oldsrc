/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: DateValidator.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.core.*;
import com.ness.fw.flower.factory.*;
import com.ness.fw.util.Message;
import com.ness.fw.util.validation.DateValidations;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Validator for Date Checks.
 */
public class DateValidator implements ValidationCheck
{

	/**
	 * Check if the value of date1 is before the value date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsBefore(ValidationCheckBundle checkBundle, Date date1, Date date2)
	{
		if (date1 != null && date2 != null)
		{
			if (date1.before(date2))
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0018", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}

				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
				if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
				}

				checkBundle.getMessagesContainer().addMessage(message);
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	/**
	 * Check if the value of date1 is before the value of date2, 
	 * after constructing a date value using the date2Mask
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @param date2Mask
	 * @return boolean True if the check was successful, False if the check failed.
	 * @throws ValidationProcessException
	 */
	public boolean strDateIsBefore(ValidationCheckBundle checkBundle, Date date1, String date2, String date2Mask) throws ValidationProcessException
	{
		try{
			if (date1 != null && date2 != null)
			{
				SimpleDateFormat df = new SimpleDateFormat(date2Mask);
				df.setLenient(false);
				Date d2 = df.parse(date2);
				if (date1.before(d2))
				{
					return true;
				}
				else
				{
					Message message = new Message("GE0028", checkBundle.getSeverity());
					message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
					if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
					{
						message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
					}

					message.addParameter(date2);

					checkBundle.getMessagesContainer().addMessage(message);
					return false;
				}
			}
			else
			{
				return true;
			}


		}catch (Throwable ex)
		{
			throw new ValidationProcessException("Error while DateIsBefore validator", ex);
		}
	}
	
	/**
	 * Check if the value of date1 is before or equal the value of date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsBeforeOrEqual(ValidationCheckBundle checkBundle, Date date1, Date date2)
	{
		if (date1 != null && date2 != null)
		{
			if (!date1.after(date2))
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0027", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}

				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
				if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
				}

				checkBundle.getMessagesContainer().addMessage(message);
				return false;
			}
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * Check if the value of date1 is before or equal the value of date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsBeforeOrEqual(ValidationCheckBundle checkBundle, Date date1, Date date2, Boolean ignoreTime)
	{
		if (!DateValidations.isBeforeOrEqual(date1,date2,ignoreTime.booleanValue()))
		{
			Message message = new Message("GE0027", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}
	
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
			if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
			}
	
			checkBundle.getMessagesContainer().addMessage(message);
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Check if the value of date1 is before or equal the value of date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsAfterOrEqual(ValidationCheckBundle checkBundle, Date date1, Date date2, Boolean ignoreTime)
	{
		if (!DateValidations.isAfterOrEqual(date1,date2,ignoreTime.booleanValue()))
		{
			Message message = new Message("GE0042", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}
	
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
			if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
			}
	
			checkBundle.getMessagesContainer().addMessage(message);
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Check if the value of date1 is after the value date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsAfter(ValidationCheckBundle checkBundle, Date date1, Date date2)
	{
		if (date1 != null && date2 != null)
		{
			if (date1.after(date2))
			{
				return true;
			}
			else
			{
				Message message = new Message("GE0019", checkBundle.getSeverity());
				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				}

				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
				if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
				}

				checkBundle.getMessagesContainer().addMessage(message);
				return false;
			}
		}
		else
		{
			return true;
		}
	}

	/**
	 * Check if the value of date1 is after the value of date2, after constructing a date value using the date2Mask
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @param date2Mask
	 * @return boolean True if the check was successful, False if the check failed.
	 * @throws ValidationProcessException
	 */
	public boolean strDateIsAfter(ValidationCheckBundle checkBundle, Date date1, String date2, String date2Mask) throws ValidationProcessException
	{
		try{
			if (date1 != null && date2 != null)
			{
				SimpleDateFormat df = new SimpleDateFormat(date2Mask);
				df.setLenient(false);
				Date d2 = df.parse(date2);
				if (date1.after(d2))
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

					message.addParameter(date2);

					checkBundle.getMessagesContainer().addMessage(message);
					return false;
				}
			}
			else
			{
				return true;
			}

		}catch (Throwable ex)
		{
			throw new ValidationProcessException("Error while DateIsAfter validator", ex);
		}
	}
	
	/**
	 * Check if the value of date1 is equals to the value of date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsEquals(ValidationCheckBundle checkBundle, Date date1, Date date2)
	{
		if (date1 != null && date2 != null)
		{
			if (date1.equals(date2))
			{
				return true;
			}
		}
		else if (date1 == null || date2 == null)
		{
			return true;
		}

		Message message = new Message("GE0020", checkBundle.getSeverity());
		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
		if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
		}

		message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
		if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
		{
			message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
		}

		checkBundle.getMessagesContainer().addMessage(message);

		return false;
	}

	/**
	 * Check if the value of date1 is equals to the value of date2
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @param ignoreTime
	 * 	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsEquals(ValidationCheckBundle checkBundle, Date date1, Date date2, Boolean ignoreTime)
	{
		if (!DateValidations.isEquals(date1,date2,ignoreTime.booleanValue()))
		{
			Message message = new Message("GE0020", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}
	
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
			if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
			}
	
			checkBundle.getMessagesContainer().addMessage(message);
			return false;
		}
		else
		{
			return true;
		}
	}



	/**
	 * Check if the value of date1 is equals to the value of date2, after constructing a date value using the date2Mask
	 * @param checkBundle
	 * @param date1
	 * @param date2
	 * @param date2Mask
	 * @return boolean True if the check was successful, False if the check failed.
	 * @throws ValidationProcessException
	 */
	public boolean strDateIsEquals(ValidationCheckBundle checkBundle, Date date1, String date2, String date2Mask) throws ValidationProcessException
	{
		try{
			if (date1 != null && date2 != null)
			{
				SimpleDateFormat df = new SimpleDateFormat(date2Mask);
				df.setLenient(false);
				Date d2 = df.parse(date2);
				if (date1.equals(d2))
				{
					return true;
				}
				else
				{
					Message message = new Message("GE0031", checkBundle.getSeverity());
					message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
					if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
					{
						message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
					}

					message.addParameter(date2);

					checkBundle.getMessagesContainer().addMessage(message);

					return false;
				}
			}
			else
			{
				return true;
			}


		}catch (Throwable ex)
		{
			throw new ValidationProcessException("Error while DateIsEquals validator", ex);
		}
	}

	/**
	 * Check if the value of date is between the values of date1 & date2
	 * @param checkBundle
	 * @param date
	 * @param date1
	 * @param date2
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean dateIsBetween(ValidationCheckBundle checkBundle, Date date, Date date1, Date date2)
	{
		if (date1 != null && date2 != null && date != null)
		{
			if (date.before(date2) && date.after(date1))
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

				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[1].getCaption());
				if (checkBundle.getValidationCheckFieldData()[1].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[1].getFieldName());
				}

				message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[2].getCaption());
				if (checkBundle.getValidationCheckFieldData()[2].isSetErrorStateToField())
				{
					message.addFieldName(checkBundle.getValidationCheckFieldData()[2].getFieldName());
				}

				checkBundle.getMessagesContainer().addMessage(message);
				return false;
			}
		}
		else
		{
			return true;
		}


	}

	/**
	 * Check if the value of date is between the values of date1 & date2, after constructing a date values using the date masks
	 * @param checkBundle
	 * @param date
	 * @param date1
	 * @param date1Mask
	 * @param date2
	 * @param date2Mask
	 * @return boolean True if the check was successful, False if the check failed.
	 * @throws ValidationProcessException
	 */
	public boolean dateIsBetweenStrDates(ValidationCheckBundle checkBundle, Date date, String date1, String date1Mask, String date2, String date2Mask) throws ValidationProcessException
	{
		try{
			if (date1 != null && date2 != null && date != null)
			{
				SimpleDateFormat df = new SimpleDateFormat(date1Mask);
				df.setLenient(false);
				Date d1 = df.parse(date1);

				df = new SimpleDateFormat(date2Mask);
				df.setLenient(false);
				Date d2 = df.parse(date2);

				if(date.before(d2) && date.after(d1))
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

					message.addParameter(date1);
					message.addParameter(date2);

					checkBundle.getMessagesContainer().addMessage(message);
					return false;
				}
			}
			else
			{
				return true;
			}


		}catch (Throwable ex)
		{
			throw new ValidationProcessException("Error while DateIsBetween check", ex);
		}
	}
	
	/**
	 * check the date given in the field is before the current date
	 * 
	 * @param checkBundle
	 * @param field the date field
	 * @return true, if the date is before the current date
	 */
	
	public boolean dateIsBeforeCurrent(ValidationCheckBundle checkBundle, Date field) 
	{		
		Date current = new Date();	
		if (field!=null)
		{
			if (field.after(current))
			{
				Message mess = new Message("GE0041",Message.SEVERITY_ERROR);
				mess.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
				mess.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
				checkBundle.getMessagesContainer().addMessage(mess);
				return false;
			}
		}
		return true;
	}
	
}
