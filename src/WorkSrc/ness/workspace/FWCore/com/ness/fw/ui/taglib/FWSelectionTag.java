package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.*;

public class FWSelectionTag extends SelectionTag
{
	protected void renderSelectionSubmitEvent() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(clickCustomEvent.getEventName(),clickCustomEvent.getEventTargetType(),true,clickCustomEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(clickCustomEvent.getEventName(),clickCustomEvent,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(clickCustomEvent));

		renderFunctionCall(JS_SELECTION_SUBMIT_EVENT,jsParams,true);
	}
	
	protected void renderSelectionComplexEvent() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(clickCustomEvent.getEventName(),clickCustomEvent.getEventTargetType(),true,clickCustomEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(clickCustomEvent.getEventName(),clickCustomEvent,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(clickCustomEvent));

		renderFunctionCall(JS_SELECTION_COMPLEX_EVENT,jsParams,true);
	}	
	
}
