package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.AuthorizedEventData;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.events.Event;

public class FWFormParametersTag extends FormParametersTag
{
	protected void renderJsCallCreateForm() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(eventName,Event.EVENT_TARGET_TYPE_DIALOG,false);
		if (!callBackEvent.equals(" "))
		{
			addAuthorizedEvent(callBackEvent,Event.EVENT_TARGET_TYPE_DIALOG,false);
		}
		renderFunctionCall
		(
			JS_CREATE_FORM,
			(formParametersModel == null ? " " : id) + COMMA +
			(srcFormName == null ? UIConstants.FORM_DEFAULT_ID : srcFormName) + COMMA + 
			inputParameters + COMMA + 
			outputParameters + COMMA + 
			eventName + COMMA + 
			authorizedEventData.getFlowStateName() + COMMA +
			authorizedEventData.getFlowPath() + COMMA +
			action + COMMA
			+ openDialog + COMMA + 
			dialogParams + COMMA + 
			callBackFunction + COMMA +
			callBackEvent);
	}
	
}
