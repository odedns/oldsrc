package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.*;
import com.ness.fw.ui.events.Event;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.*;

public class FWTableTag extends TableTag
{
	private String currentFlowPath = " ";
	private String currentFlowState = " ";
			
	protected void renderAdvancedSortEvent() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(FW_SORT_EVENT,Event.EVENT_TARGET_TYPE_DEFAULT,true,false);
		renderFunctionCall
		(
			JS_OPEN_SORT_POPUP,
			getSingleQuot(id) + COMMA +
			THIS + COMMA + 
			getSingleQuot(FW_SORT_EVENT) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowPath()) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowStateName()) + COMMA +
			getUIProperty("ui.sort.numberOfColumns"),false
		);
	}
	
	protected void renderColumnOrderEvent() throws UIException 
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(FW_COLUMN_ORDER_EVENT,Event.EVENT_TARGET_TYPE_DEFAULT,true,false);	
		renderFunctionCall
		(
			JS_OPEN_COLUMN_ORDER_POPUP,
			getSingleQuot(id) + COMMA +
			THIS + COMMA + 
			getSingleQuot(FW_COLUMN_ORDER_EVENT) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowPath()) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowStateName()),false
		);	
	}
	
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
	
	protected void renderJsCallPageEvent(int pagingType,int pageNumber,int startPageRange) throws UIException
	{
		Event pagingEvent = ((TableModel)model).getPagingDefaultClickEvent();
		String eventName = getPagingEventName(pagingType);
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(eventName,Event.EVENT_TARGET_TYPE_DEFAULT,true,pagingEvent.isCheckWarnings());
		renderFunctionCall
		(
			JS_PAGE_EVENT_FUNCTION,
			id + COMMA + 
			pageNumber + COMMA + 
			startPageRange + COMMA +
			eventName + COMMA + 
			authorizedEventData.getFlowPath() + COMMA + 
			authorizedEventData.getFlowStateName() + COMMA + 
			getEventDirtyFlagsAsString(pagingEvent)
		);
	}
	
	private String getPagingEventName(int pagingType)
	{
		if (isAutoPaging())
		{
			return FW_PAGING_EVENT;
		}
		else
		{
			if (pagingType == PAGING_FIRST)
			{
				return FW_PAGING_FIRST_EVENT;
			}
			else if (pagingType == PAGING_LAST)
			{
				return FW_PAGING_LAST_EVENT;
			}
			else if (pagingType == PAGING_NEXT)
			{
				return FW_PAGING_NEXT_EVENT;
			}
			else if (pagingType == PAGING_PREV)
			{
				return FW_PAGING_PREV_EVENT;
			}
			else
			{
				return FW_PAGING_SPECIFIC_PAGE_EVENT;
			}
		}
	}
	
	protected void renderJsCallSortEvent(String columnId, String direction,Event columnEvent) throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(FW_SORT_EVENT,Event.EVENT_TARGET_TYPE_DEFAULT,true,columnEvent.isCheckWarnings());
		renderFunctionCall
		(
			JS_SORT_EVENT,
			id + COMMA + 
			columnId + COMMA + 
			direction + COMMA + 
			FW_SORT_EVENT + COMMA + 
			authorizedEventData.getFlowPath() + COMMA + 
			authorizedEventData.getFlowStateName() + COMMA + 
			getEventDirtyFlagsAsString(columnEvent)
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
