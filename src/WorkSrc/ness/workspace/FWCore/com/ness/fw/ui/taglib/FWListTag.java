package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.UIConstants;
import com.ness.fw.shared.ui.*;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.ui.events.Event;

public class FWListTag extends ListTag
{	
	protected void renderMultipleExpandSubmitEvent() throws UIException
	{
		if (expandEventName == null)
		{
			throw new UIException("eventName is null in tag list id " + id);
		}
		
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(expandEventName,Event.EVENT_TARGET_TYPE_DIALOG,false);
		renderFunctionCall
		(
			JS_EXPAND_MULTIPLE_SUBMIT,
			getSingleQuot(id) + COMMA + 
			id + L2L_JSOBJECT_SUFFIX + COMMA + 
			getSingleQuot(expandEventName) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowPath()) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowStateName()) + COMMA + 
			JS_L2L_OBJECT_TYPE_ALL + COMMA +
			searchIn + COMMA +  
			searchAction + COMMA + 
			searchType + COMMA + 
			(searchAction == UIConstants.LIST_SEARCH_ACTION_ALL) + COMMA +			
			(searchType == UIConstants.LIST_SEARCH_TYPE_ALL) + COMMA + 
			refreshType + COMMA + 
			getSingleQuot(expanderDialogParams) + COMMA +
			getSingleQuot(getLocalizedText(expanderTitle)) + COMMA + 
			id + LIST_LABELS_ARRAY_SUFFIX
			,false
		);
	}

	protected void renderSingleExpandSubmitEvent() throws UIException
	{
		if (expandEventName == null)
		{
			throw new UIException("eventName is null in tag list id " + id);
		}

		AuthorizedEventData authorizedEventData = addAuthorizedEvent(expandEventName,Event.EVENT_TARGET_TYPE_DIALOG,false);
		renderFunctionCall
		(
			JS_EXPAND_SINGLE_SUBMIT,
			getSingleQuot(id) + COMMA + 
			id + L2L_JSOBJECT_SUFFIX + COMMA +
			id + L2L_TRG_LIST_SUFFIX + COMMA + 
			getSingleQuot(expandEventName) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowPath()) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowStateName()) + COMMA + 
			JS_L2L_OBJECT_TYPE_SRC + COMMA +
			searchIn + COMMA +  
			searchAction + COMMA + 
			searchType + COMMA + 
			(searchAction == UIConstants.LIST_SEARCH_ACTION_ALL) + COMMA +	
			(searchType == UIConstants.LIST_SEARCH_TYPE_ALL) + COMMA + 
			refreshType + COMMA + 
			getSingleQuot(expanderDialogParams) + COMMA +
			getSingleQuot(expanderDialogParams) + COMMA + 
			id + LIST_LABELS_ARRAY_SUFFIX + COMMA + 
			orderButtons
			,false
		);
	}	
	
	protected String getListSubmitEventFunction() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(changeCustomEvent.getEventName(),changeCustomEvent.getEventTargetType(),true,changeCustomEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the event's parameters : 
		//eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(changeCustomEvent.getEventName(),changeCustomEvent,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(changeCustomEvent));

		return getFunctionCall(JS_LIST_SUBMIT_EVENT,jsParams,true);
	}
	
	protected String getListComplexEventFunction() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(changeCustomEvent.getEventName(),changeCustomEvent.getEventTargetType(),true,changeCustomEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(changeCustomEvent.getEventName(),changeCustomEvent,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(changeCustomEvent));
		
		return getFunctionCall(JS_LIST_COMPLEX_EVENT,jsParams,true);
	}	
}
