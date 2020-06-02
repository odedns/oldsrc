/*
 * Created on 13/06/2004
 * Created By yharnof
 */
package com.ness.fw.shared.validation.ui;

import com.ness.fw.ui.ListModel;
import com.ness.fw.util.*;
import com.ness.fw.flower.factory.*;

public class ListValidator implements ValidationCheck
{
	/**
	 * Check if the list model contains selected values (for mandatory fields).
	 * @param checkBundle
	 * @param listModel
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isSelectionExists(ValidationCheckBundle checkBundle, ListModel listModel)
	{
		if (!listModel.hasSelectedKeys())
		{
			Message message = new Message("GE0012",Message.SEVERITY_ERROR);
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

	/**
	 * Check if the list model contains selected values or editable value.
	 * For mandatory fields of type Single Expander with editabe = true.
	 * @param checkBundle
	 * @param listModel
	 * @return boolean True if the check was successful, False if the check failed.
	 */
	public boolean isEditableSelectionExists(ValidationCheckBundle checkBundle, ListModel listModel)
	{
		if (!listModel.hasSelectedKeys() && StringFormatterUtil.isEmpty(listModel.getEditableValue()))
		{
			Message message = new Message("GE0012",Message.SEVERITY_ERROR);
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
