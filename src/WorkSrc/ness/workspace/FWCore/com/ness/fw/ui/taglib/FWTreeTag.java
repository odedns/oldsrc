package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.ui.*;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.AuthorizedEventData;

public class FWTreeTag extends TreeTag 
{
	protected void renderJsCallLinkedTreeNodeSubmitEvent(TreeNode treeNode) throws UIException
	{
		CustomEvent treeNodeEvent = treeNode.getTreeNodeClickEvent();
		String eventName = treeNodeEvent.getEventName() != null ? treeNodeEvent.getEventName() : TREE_NODE_EVENT;
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(eventName,treeNodeEvent.getEventTargetType(),true,treeNodeEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the nodeId parameter
		jsParams.add(treeNode.getId());

		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(eventName,treeNodeEvent,authorizedEventData));

		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,dirtable,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(treeNodeEvent,true));
		
		renderFunctionCall(JS_TREE_NODE_SUBMIT_EVENT_FUNCTION,jsParams,true);
	}
	
	protected void renderJsCallTreeNodeSubmitEvent(TreeNode treeNode) throws UIException
	{			
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(TREE_NODE_EVENT,treeModel.getTreeNodeDefaultClickEvent().getEventTargetType(),true,treeModel.getTreeNodeDefaultClickEvent().isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the id parameter
		jsParams.add(id);

		//Add the nodeId parameter
		jsParams.add(treeNode.getId());

		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(TREE_NODE_EVENT,treeModel.getTreeNodeDefaultClickEvent(),authorizedEventData));

		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,dirtable,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(treeModel.getTreeNodeDefaultClickEvent(),true));
		
		renderFunctionCall(JS_TREE_NODE_SUBMIT_EVENT_FUNCTION,jsParams,true);		
	}
	
	protected void renderOpenNodeSubmitEvent() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(TREE_EXPAND_NODE_EVENT,com.ness.fw.ui.events.Event.EVENT_TARGET_TYPE_DEFAULT,true,treeModel.getTreeNodeDefaultExpandEvent().isCheckWarnings());
		renderFunctionCall
		(
			JS_TREE_OPEN_NODE_SUBMIT_EVENT_FUNCTION,
			THIS + COMMA + 
			getSingleQuot(id) + COMMA + 
			getSingleQuot(TREE_EXPAND_NODE_EVENT) + COMMA + 
			getSingleQuot(authorizedEventData.getFlowPath()) + COMMA +
			getSingleQuot(authorizedEventData.getFlowStateName()) + COMMA +  
			getEventDirtyFlagsAsString(treeModel.getTreeNodeDefaultExpandEvent()),
			false
		);
	}			
}
