/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: FieldChecks.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */
package com.ness.fw.flower.util.validation;

import com.ness.fw.util.*;
import com.ness.fw.flower.factory.*;

/**
 * Validator for general fields Checks.
 */
public class FieldChecks implements ValidationCheck
{
	/**
	 * Check if the field contains value (for mandatory fields)
	 * @param checkBundle
	 * @param fieldData
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isFieldExists(ValidationCheckBundle checkBundle, Object fieldData)
	{
		if (fieldData == null || (fieldData instanceof String && ((String)fieldData).length() == 0))
		{
			Message message = new Message("GE0012", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			if (checkBundle.getValidationCheckFieldData()[0].isSetErrorStateToField())
			{
				message.addFieldName(checkBundle.getValidationCheckFieldData()[0].getFieldName());
			}

			checkBundle.getMessagesContainer().addMessage(message);

			return false;
		}
		else
		{
			return true;
		}
	}
}
