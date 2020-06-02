package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.*;

public class FWTreeTableTag extends TreeTableTag {

	protected final static String FW_ROW_EVENT = "selection";
	protected final static String FW_ROW_DBL_EVENT = "dblSelection";	
	protected final static String FW_LINK_EVENT = "link";
	private String currentFlowPath = " ";
	private String currentFlowState = " ";
			
	protected void renderJsCallCellEvent(Row row,Cell cell,Event event) throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(FW_LINK_EVENT,event.getEventTargetType(),true,event.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);
		
		//Add the rowlId and cellId parameters
		jsParams.add(row.getId());
		jsParams.add(cell.getId());
		
		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(FW_LINK_EVENT,event,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(event));
		
		renderFunctionCall(JS_CELL_EVENT,jsParams);
	}
	
	protected void renderJsCallRowSelectionSubmitEvent(Row row,Event rowEvent) throws UIException
	{
		String eventName = submitOnDblRowSelection ? FW_ROW_DBL_EVENT : FW_ROW_EVENT;
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(eventName,rowEvent.getEventTargetName(),true,rowEvent.isCheckWarnings());		
		renderFunctionCall
		(
			JS_ROW_SELECTED_SUBMIT_EVENT,
			id + COMMA + 
			row.getId() + COMMA + 
			eventName + COMMA + 
			authorizedEventData.getFlowPath() + COMMA + 
			authorizedEventData.getFlowStateName() + COMMA + 
			rowEvent.getEventTargetName() + COMMA + 
			getEventDirtyFlagsAsString(rowEvent,true)
		);	
	}	
	
	protected void renderJsCallOpenMenu(String rowId,String menuStr,String menuClassName)
	{
		renderFunctionCall
		(
			JS_OPEN_MENU,
			THIS + COMMA +
			getSingleQuot(id) + COMMA + 
			getSingleQuot(rowId) + COMMA + 
			getSingleQuot(menuStr) + COMMA + 
			getSingleQuot(menuClassName), 
			false
		);				
	}	
}
