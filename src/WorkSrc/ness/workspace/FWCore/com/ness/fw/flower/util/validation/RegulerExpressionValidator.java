/*
 * Created on: 10/01/2005
 * @author: baruch hizkya
 * @version $Id: RegulerExpressionValidator.java,v 1.1 2005/02/21 15:07:13 baruch Exp $
 */

package com.ness.fw.flower.util.validation;

import com.ness.fw.flower.factory.*;
import com.ness.fw.util.Message;
import com.ness.fw.util.validation.RegularExpressionChecks;

/**
 * Validator for regular expressions Checks.
 */
public class RegulerExpressionValidator implements ValidationCheck
{
	
	/**
	 * 
	 * @param checkBundle
	 * @param description
	 * @param regExPattern
	 * @param regExFlags
	 * @return
	 */
	public boolean checkExpression(ValidationCheckBundle checkBundle, String description, String regExPattern, Integer regExFlags)
	{
		if (!RegularExpressionChecks.checkExpression(description, regExPattern, regExFlags))
		{
			Message message = new Message("GE0044", checkBundle.getSeverity());
			message.addLocalizableParameter(checkBundle.getValidationCheckFieldData()[0].getCaption());
			message.addParameter(regExPattern);
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
