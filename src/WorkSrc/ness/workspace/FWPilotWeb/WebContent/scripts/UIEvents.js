/**************hidden fields*********************/
function getHiddenField(id)
{
	var form = document.forms[0];
	if (form == null)
	{
		alert("error - form does not exist");
		return null;
	}	
	else
	{
		var field = form[id + MODEL_EVENT_DATA_CONSTANT]; 
		if (field == null)
		{
			alert("error - field " + id + " does not exist");
			return null;
		}
		else if (field.length != null)
		{
			alert("error - field " + id + " is defined " + field.length + " times");
			return null;
		}
		return field;
	}
}

function resetHiddenField(isEventSent,originalHiddenFieldValue)
{
	if (!isSentEvent)
	{
		hiddenField.value = originalHiddenFieldValue;
	}	
}

/*****menu event - select from main menu*****/
function menuEvent(eventName,flowPath,flowState,isLeaf,eventTargetType,dialogParams)
{
	if (isLeaf)
	{
		if (eventTargetType == EVENT_TARGET_TYPE_NORMAL)
		{
			sendEvent(eventName,flowPath,flowState,POPUP_TARGET_SELF,true,false,null,true,null);
		}
		else if (eventTargetType == EVENT_TARGET_TYPE_POPUP)
		{
			sendEventPopup(eventName,flowPath,flowState,"",true,false,null,true,null,dialogParams);
		}
		else if (eventTargetType == EVENT_TARGET_TYPE_DIALOG)
		{
			sendEventDialog(eventName,flowPath,flowState,"",true,false,null,true,null,dialogParams);
		}
		else if (eventTargetType == EVENT_TARGET_TYPE_NEW_WINDOW)
		{
			sendEventNewWindow(eventName,flowPath,flowState);
		}			
	}
}

/*******button model - click on button**********/
function buttonEvent(modelId)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,BUTTON_MODEL_CLICK_EVENT_TYPE,false);		
}

function toolBarEvent(modelId,buttonName,buttonSet)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TOOLBAR_MODEL_CLICKED_BUTTON_PROPERTY,buttonName,false);		
	setModelProperty(hiddenField,TOOLBAR_MODEL_CLICKED_BUTTON_SET_PROPERTY,buttonSet,false);		
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TOOLBAR_MODEL_CLICK_EVENT_TYPE,false);			
}

/*******form parameters model - click on button**********/
function formParametersEvent(modelId)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,FORM_PARAMETERS_MODEL_CLICK_EVENT_TYPE,false);		
}

/*******messages model - open or close messages area************/
function messagesAreaEvent(modelId,errorDiv,normalHeight,expandHeight)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	
	//messages area is closed
	if (errorDiv.style.height == normalHeight + PIXEL)
	{
		setModelProperty(hiddenField,MESSAGES_MODEL_STATE,MESSAGES_MODEL_CLOSE,false);
	}	
	//messages area is open
	else 
	{
		setModelProperty(hiddenField,MESSAGES_MODEL_STATE,MESSAGES_MODEL_OPEN,false);
	}
}

/*******collapsible model - open or close section************/
function collapseEvent(modelId,collapsibleId)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var collapsibleSections = document.all(collapsibleId).rows;
	if (collapsibleSections != null)
	{
		var openSections = "";
		var l = collapsibleSections.length;
		for (index = 0;index < l;index++)
		{
			var collapsibleSection = collapsibleSections[index];
			if (collapsibleSection.id != "" && isDisplayed(collapsibleSection))
			{
				openSections = openSections.concat(collapsibleSection.id.substring(collapsibleSection.id.indexOf(COLLAPSIBLE_SECTION_PREFIX) + COLLAPSIBLE_SECTION_PREFIX.length));
				openSections = openSections.concat(MODEL_MULTI_VALUES_SEPERATOR);
			}		
		}
		setModelProperty(hiddenField,COLLAPSIBLE_MODEL_OPEN_SECTIONS,openSections,true);
	}		
}

/*******list model - set hidden field and send event to server when selecting value***************/
function listComplexEvent(modelId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	var select = window.event.srcElement;
	var originalHiddenFieldValue = hiddenField.value;
	var originalSelectedIndex = select.currentlySelected;
	listEvent(modelId,select,false,false);
	var isEventSent = sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
	if (!isEventSent)
	{
		hiddenField.value = originalHiddenFieldValue;
		if (select.currentlySelected != null)
		{
			select.selectedIndex = select.currentlySelected;
		}
	}	
}

/*******list model - send event to server when selecting value***************/
function listSubmitEvent(modelId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId)
	if (hiddenField == null) return;	
	var originalHiddenFieldValue = hiddenField.value;
	var originalSelectedIndex = window.event.srcElement.selectedIndex;
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,LIST_MODEL_CHANGE_EVENT_TYPE,false);
	sendEventGlobal(eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
}

/*******list model - set hidden field when selecting value***************/
function listEvent(modelId,select,isSelectAll,isMultiple)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedKeys = getSelectedKeys(select,isSelectAll,MODEL_MULTI_VALUES_SEPERATOR);
	if (isMultiple)
	{
		setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys,true);	
	}
	else
	{
		setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEY_PROPERTY,selectedKeys,false);	
	}
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,LIST_MODEL_CHANGE_EVENT_TYPE,false);
}

/*******list model - single expand change value***************/
function listTextBoxEvent(modelId,key)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEY_PROPERTY,key,false);
	setModelProperty(hiddenField,LIST_MODEL_EDITABLE_VALUE_PROPERTY,"",false);	
}

function listTextBoxKeyPressEvent(modelId)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var textBox = window.event.srcElement;
	if (textBox == null)
	{
		return;
	}
	setModelProperty(hiddenField,LIST_MODEL_EDITABLE_VALUE_PROPERTY,textBox.value,false);	
	setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEY_PROPERTY,"",false);
}

function listTextBoxSearchStrEvent(modelId)
{
	if (event.type == HTML_EVENT_KEYPRESS)
	{
		var form = document.forms[0];
		var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
		if (hiddenField == null) return;
		setModelProperty(hiddenField,LIST_MODEL_SEARCH_STRING_PROPERTY,String.fromCharCode(event.keyCode),false);
		setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,LIST_MODEL_SEARCH_EVENT_TYPE,false);		
	}
}

/*******list model - value change in list to list object***************/
function listToListEvent(modelId,select)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedKeys = getSelectedKeys(select,true,MODEL_MULTI_VALUES_SEPERATOR);
	setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys,true);	
}

/*******list model - value change in multiple expanded object***************/
function listMultipleExpandedEvent(modelId,select)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedKeys = getSelectedKeys(select,true,MODEL_MULTI_VALUES_SEPERATOR);
	setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys,true);	
}

/**
* list model - set hidden field when selecting value in radio or checkbox objects
*/
function selectionEvent(modelId,select,isMultiple)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	var selectedKeys = getCheckedValues(select,MODEL_MULTI_VALUES_SEPERATOR);
	if (isMultiple)
	{
		setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEYS_PROPERTY,selectedKeys,true);	
	}
	else
	{
		setModelProperty(hiddenField,LIST_MODEL_SELECTED_KEY_PROPERTY,selectedKeys,false);	
	}
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,LIST_MODEL_CLICK_EVENT_TYPE,false);
}

/**
* list model - send event to server when selecting value in radio button or checkbox
*/
function selectionSubmitEvent(modelId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,LIST_MODEL_CLICK_EVENT_TYPE,false);
	sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/**
* list model - set hidden field and send event to server when selecting value 
* in radio button
*/
function selectionComplexEvent(modelId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	var originalHiddenFieldValue = hiddenField.value;
	var checkedRadio = window.event.srcElement;
	var checkedRadioGroup = document.all(checkedRadio.id);
	selectionEvent(modelId,checkedRadioGroup,false);
	var isEventSent = sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
	if (!isEventSent)
	{
		hiddenField.value = originalHiddenFieldValue;
		var length = checkedRadioGroup.length;
		if (length == null)
		{
			checkedRadioGroup.checked = false;
		}
		else
		{
			for (index = 0;index < length;index++)
			{
				checkedRadioGroup[index].checked = (checkedRadioGroup[index].currentlyChecked != null);
			}
		}
	}
}

/*******table model - paging event***************/
function page(modelId,pageNumber,pageRange,eventName,flowPath,flowState,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	if (pageNumber == 0) 
	{
		pageNumber = document.all("pageTo" + modelId).value;
	}	
	setModelProperty(hiddenField,TABLE_MODEL_PAGE_NUMBER_PROPERTY,pageNumber,false);	
	setModelProperty(hiddenField,TABLE_MODEL_PAGE_START_RANGE_PROPERTY,pageRange,false);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_PAGING_EVENT_TYPE,false);		
	sendEvent(modelId + SEPERATOR + eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/*******table model - sort event***************/
function sortEvent(modelId,columnsIds,direction,eventName,flowPath,flowState,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
 	setModelProperty(hiddenField,TABLE_MODEL_SORT_COLUMN_PROPERTY,columnsIds,true);	
	setModelProperty(hiddenField,TABLE_MODEL_SORT_DIR_PROPERTY,direction,true);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_SORT_EVENT_TYPE,false);	
	sendEvent(modelId + SEPERATOR + eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/*******table model - column order event***************/
function orderEvent(modelId,columnsIds,eventName,flowPath,flowState,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TABLE_MODEL_ORDER_PROPERTY,columnsIds,true);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_COLUMN_ORDER_EVENT_TYPE,false);
	sendEvent(modelId + SEPERATOR + eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/*******table model - row selected in single selection mode and submit***************/
function rowSelectedSubmitEvent(modelId,rowId,eventName,flowPath,flowState,eventTargetType,checkDirty,dirtable,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var originalHiddenFieldValue = hiddenField.value;
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_ROW_PROPERTY,rowId,false);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_ROW_EVENT_TYPE,false);
	if (dirtable == "true")
	{
		setGlobalDirty();
	}
	var isEventSent = sendEvent(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
	if (!isEventSent)
	{
		hiddenField.value = originalHiddenFieldValue;
	}	
}

/*******table model - row selected in single selection mode without submit***************/
function rowSelectedEvent(modelId,rowId,dirtable)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_ROW_PROPERTY,rowId,false);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_ROW_EVENT_TYPE,false);
	if (dirtable == "true")
	{
		setGlobalDirty();
	}
}

/*******table model - row selected in multiple selection mode***************/
function rowMultipleSelectedEvent(modelId,rowId,checked)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedRows = getCheckedRowsAsString(modelId);
 	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows,true);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_ROWS_EVENT_TYPE,false);	
}

function rowSelectRowSonsEvent(modelId,checkbox,treeSelectionType)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedRows;
	
	//indicates if unchecking is legal
	if (treeSelectionType == TREE_TABLE_SELECTION_TYPE_CHECK_ONLY && !checkbox.checked)
	{
		selectedRows = getCheckedRowsAsString(modelId);
	}
	else
	{
		//get the row of the checkbox
		var row = checkbox.parentElement.parentElement;
		//get the id of the checkbox
		var rowId = row.id;
	
		//get an array of all the tree's nodes
		var nodes = row.parentElement.parentElement.rows;
	
		//one row
		if (nodes.length == null)
		{
			
		}
		//few rows
		else
		{
			var l = nodes.length;
			for (index = 0;index < l;index++)
			{
				var node = nodes[index];
				//indication of a son
				if (node.id != rowId && node.id.indexOf(rowId) != -1)
				{
					//get the sons's checkbox
					var cb = node.cells[0].children[0];
					cb.checked = checkbox.checked;
				}
			}
		}
		selectedRows = getCheckedRowsAsString(modelId);
	}
 	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows,true);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_ROWS_EVENT_TYPE,false);			
}

/*******table model - all rows selected in multiple selection mode***************/
function rowSelectAllEvent(modelId,check)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	var selectedRows = new String("");
	var modelCheckboxes = getObjectById(modelId + "Checkbox");
	if (modelCheckboxes != null)
	{
		if (modelCheckboxes.length == null) 
		{
			modelCheckboxes.checked = check;
			if (check) selectedRows = modelCheckboxes.value;
		}
		else 
		{
			var l = modelCheckboxes.length;
			for (index = 0;index < l;index++) 
			{
				var cb = modelCheckboxes[index];
				var val = cb.value;
				cb.checked = check;
				if (check)
				{
					selectedRows = selectedRows.concat(val);
					if (index != l -1) selectedRows = selectedRows.concat(MODEL_MULTI_VALUES_SEPERATOR);
				}
			}
		}
		setModelProperty(hiddenField,TABLE_MODEL_SELECTED_ROWS_PROPERTY,selectedRows,true);
	}
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_ROWS_EVENT_TYPE,false);
}

/*******table model - cell's link is pressed***************/
function cellEvent(modelId,rowId,cellId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_CELL_PROPERTY,cellId,false);
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_LINK_ROW_PROPERTY,rowId,false);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_LINK_EVENT_TYPE,false);
	sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/*******table model - action selected from floating menu***************/
function rowEvent(modelId,rowId,menuItemIndex,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_MENU_ROW_PROPERTY,rowId,false);
	setModelProperty(hiddenField,TABLE_MODEL_SELECTED_MENU_ITEM_PROPERTY,menuItemIndex,false);
	setModelProperty(hiddenField,TABLE_MODEL_MENU_ACTION_PROPERTY,eventName,false);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TABLE_MODEL_MENU_EVENT_TYPE,false);
	sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
}

/*******tree and table tree model - tree's node is opened/closed*****/
function nodeOpenEvent(modelId,openNodes,isOpen)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
 	setModelProperty(hiddenField,TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY,openNodes,true);	
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TREE_TABLE_MODEL_OPEN_EVENT_TYPE,false);	
}

function nodeOpenSubmitEvent(nodeSign,modelId,eventName,flowPath,flowState,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{	
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
 	var currentNode = getObjectById(TREE_ROW_PREFIX + nodeSign.id);
 	var isOpenAction = (currentNode.open == "false");
 	if (isOpenAction)
 	{
	 	openNodes = getOpenNodesAsString(modelId);
	 	if (openNodes != "")
	 	{
	 		openNodes += MODEL_MULTI_VALUES_SEPERATOR;
	 	}
	 	openNodes += currentNode.rid;
		setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TREE_MODEL_OPEN_NODE_EVENT_TYPE,false);	
		setModelProperty(hiddenField,TREE_MODEL_OPEN_NODE_PROPERTY,currentNode.rid,false);
	 	setModelProperty(hiddenField,TREE_TABLE_MODEL_OPENED_ROWS_PROPERTY,openNodes,true);		
		sendEvent(modelId + SEPERATOR + eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
	}
	else
	{
		openTree(nodeSign,false);
	}
}

/**
* tree model - send event when node in a tree is clicked 
**/
function treeNodeSubmitEvent(modelId,nodeId,eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,dirtable,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var hiddenField = getHiddenField(modelId);
	if (hiddenField == null) return;
	var originalHiddenFieldValue = hiddenField.value;
	setModelProperty(hiddenField,TREE_MODEL_SELECTED_NODE_PROPERTY,nodeId,false);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TREE_MODEL_OPEN_EVENT_TYPE,false);
	if (dirtable == "true")
	{
		setGlobalDirty();
	}
	var isEventSent;
	isEventSent = sendEventGlobal(modelId + SEPERATOR + eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
	if (!isEventSent)
	{
		hiddenField.value = originalHiddenFieldValue;
	}
}

/**
* tree model - node in a tree is clicked 
**/
function treeNodeEvent(modelId,nodeId,selectedNodeClassName,dirtable)
{
	var form = document.forms[0];
	var hiddenField = form[modelId + MODEL_EVENT_DATA_CONSTANT];
	if (hiddenField == null) return;
	setModelProperty(hiddenField,TREE_MODEL_SELECTED_NODE_PROPERTY,nodeId,false);
	setModelProperty(hiddenField,MODEL_EVENT_TYPE_PROPERTY,TREE_MODEL_OPEN_EVENT_TYPE,false);
	selectTreeNode(modelId,"nodeDiv" + modelId + nodeId,selectedNodeClassName);
	if (dirtable == "true")
	{
		setGlobalDirty();
	}
}

/****************util methods for models*********/
function getModelProperty(fieldValue,propertyConstant)
{
	var first = fieldValue.indexOf(propertyConstant + MODEL_KEY_VALUE_SEPERATOR);
	if (first == -1) return "";
	var last = fieldValue.indexOf(MODEL_PARAM_SEPERATOR,first + (MODEL_KEY_VALUE_SEPERATOR + propertyConstant).length);
	var value;
	if (last == -1)
	{
		value = fieldValue.substring(first + (MODEL_KEY_VALUE_SEPERATOR + propertyConstant).length);
	}
	else
	{
		value = fieldValue.substring(first + (MODEL_KEY_VALUE_SEPERATOR + propertyConstant).length,last);
	}
	var multiStart = value.indexOf(MODEL_MULTI_VALUES_START);
	if (multiStart != -1)
	{
		var multiEnd = value.indexOf(MODEL_MULTI_VALUES_END);
		value = value.substring(multiStart + 1,multiEnd);
	}
	return value;
}

function setModelProperty(field,propertyConstant,newValue,isMultiValue)
{
	var first = field.value.indexOf(propertyConstant);
	if (first == -1)
	{
		if (isMultiValue)
		{
			newValue = MODEL_MULTI_VALUES_START + newValue + MODEL_MULTI_VALUES_END; 
		}
		field.value += propertyConstant 
				+ MODEL_KEY_VALUE_SEPERATOR
				+ newValue 
				+ MODEL_PARAM_SEPERATOR;
	}
	else 
	{
		var length = (MODEL_KEY_VALUE_SEPERATOR + propertyConstant).length;
		if (isMultiValue)
		{
			var last = field.value.indexOf(MODEL_MULTI_VALUES_END,first + length);
			field.value = field.value.substring(0,first + length + MODEL_MULTI_VALUES_START.length)
							+ newValue
							+ field.value.substring(last);
		}
		else
		{
			var last = field.value.indexOf(MODEL_PARAM_SEPERATOR,first + length);
			if (last == -1)
			{
				field.value = field.value.substring(0,first + length)
								+ newValue
								+ MODEL_PARAM_SEPERATOR;
			}
			else
			{
				field.value = field.value.substring(0,first + length)
								+ newValue
								+ field.value.substring(last);
			}
		}
	}
}

function removeModelPropertyValue(propertyContent,id)
{
	if (propertyContent == "" || propertyContent == id) return "";
	var index = propertyContent.indexOf(MODEL_MULTI_VALUES_SEPERATOR + id + MODEL_MULTI_VALUES_SEPERATOR);
	if (index != -1)
	{
		return propertyContent.substring(0,index) 
				+ propertyContent.substring(index + (MODEL_MULTI_VALUES_SEPERATOR + id).length);
	}
	else
	{
		//last
		index = propertyContent.indexOf(MODEL_MULTI_VALUES_SEPERATOR + id);
		if (index + (id + MODEL_MULTI_VALUES_SEPERATOR).length == propertyContent.length)
		{
			return propertyContent.substring(0,index);
		}
		else
		{
			//first
			index = propertyContent.indexOf(id + MODEL_MULTI_VALUES_SEPERATOR);
			if (index == 0)
			{
				return propertyContent.substring(index + (id + MODEL_MULTI_VALUES_SEPERATOR).length);
			}
			else
			{
				return propertyContent;
			}
		}
	}
}
	
/*send event global*/
function sendEventGlobal(eventName,flowPath,flowState,eventTargetType,dialogParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	if (eventTargetType == EVENT_TARGET_TYPE_DIALOG)
	{
		return sendEventDialog(eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage,dialogParams);
	}
	else if (eventTargetType == EVENT_TARGET_TYPE_POPUP)
	{
		return sendEventPopup(eventName,flowPath,flowState,"",checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage,dialogParams);
	}
	else
	{
		return sendEvent(eventName,flowPath,flowState,eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);
	}
	return false;
}		

/*send event and open new window*/
function sendEventNewWindow(eventName,flowPath,flowState)
{
	var form = document.getElementById(FORM_NAME);
	var ce = validateEvent(false,false,eventName,"",false,null);	
	eventName = ce.getEventName();
	if (ce.getIsEventOk())
	{
		form[FORM_FIELD_EVENT_NAME].value = eventName;
		form[FORM_FIELD_FLOW_ID].value    = flowPath;
		form[FORM_FIELD_FLOW_STATE].value = flowState;
		form[FORM_FIELD_EVENT_CHECK_WARNINGS].value = false;
		form[FORM_FIELD_IS_POPUP_WINDOW].value = "0";
		form.target = new Date().getMilliseconds();
		form.submit();		
	}
}

function sendEventOnLoad(eventName,flowPath,flowState)
{
	var isTargetWindowDialog = false;
	var srcWin;
	var form = document.getElementById(FORM_NAME);
	//close popup
	if (opener != null)
	{
		//Set the form's target to be the parent's name
		form.target = opener.name;
		
		//Check if the target window is modal dialog
		isTargetWindowDialog = opener.dialogArguments != null; 
		srcWin = opener;
	}
	
	//close modal dialog
	else if (window.dialogArguments != null && window.dialogArguments[DIALOG_ARGUEMENT_PARENT_TARGET] != null)
	{
		//Set the form's target to be the parent's name
		form.target = window.dialogArguments[DIALOG_ARGUEMENT_PARENT_TARGET];
		
		//Check if the target window is modal dialog
		srcWin = window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW];
		isTargetWindowDialog = srcWin.dialogArguments != null; 
	}
	form[FORM_FIELD_EVENT_NAME].value = eventName;
	form[FORM_FIELD_FLOW_ID].value    = flowPath;
	form[FORM_FIELD_FLOW_STATE].value = flowState;
	form[FORM_FIELD_IS_POPUP_WINDOW].value = "0";
	if (isTargetWindowDialog)
	{
		srcWin.createNewFormByForm(form,true);
	}
	else
	{
		form.submit();
	}
	self.close();
}

/*send event and open the same window*/
function sendEvent(eventName,flowPath,flowState,eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var form = document.getElementById(FORM_NAME);
	//Send event only if form was not already submitted
	if (form.isSubmitted == null || form.isSubmitted == false)
	{
		var isTargetWindowDialog = false;
		var srcWin;
		var ce = validateEvent(checkDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
		eventName = ce.getEventName();
		if (ce.getIsEventOk())
		{
			var closeWindow = false;
			if (eventTargetType == EVENT_TARGET_TYPE_CLOSE_POPUP)
			{
				closeWindow = true;
				//Check if the target window is modal dialog
				srcWin = opener;	
				isTargetWindowDialog = opener.dialogArguments != null; 
	
				if (opener != null)
				{
					form.target = opener.name;
				}
			}
			else if (eventTargetType == EVENT_TARGET_TYPE_CLOSE_DIALOG)
			{
				closeWindow = true;
				//Check if the target window is modal dialog itself
				srcWin = window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW];
				isTargetWindowDialog = srcWin.dialogArguments != null; 
	
				if (dialogArguments != null && dialogArguments[DIALOG_ARGUEMENT_PARENT_TARGET] != null)
				{
					form.target = dialogArguments[DIALOG_ARGUEMENT_PARENT_TARGET];
				}
			}
			else
			{
				if (self.name != null && window.name != "")
				{
					form.target = window.name;
				}
				else
				{
					form.target = POPUP_TARGET_SELF;
				}
				setDialogParameters();
			}
			form[FORM_FIELD_EVENT_NAME].value = eventName;
			form[FORM_FIELD_FLOW_ID].value    = flowPath;
			form[FORM_FIELD_FLOW_STATE].value = flowState;
			form[FORM_FIELD_EVENT_CHECK_WARNINGS].value = getWarningIndication(checkWarnings);
			form[FORM_FIELD_IS_POPUP_WINDOW].value = NORMAL_WINDOW_INIDCATION;
			
			if (isTargetWindowDialog)
			{
				srcWin.createNewFormByForm(form,true)
			}
			else
			{
				submitForm(form);
			}
			if (closeWindow)
			{
				self.close();
			}
		}
		else
		{
			return false;
		}
	}
}

/*send event and confirm*/
function sendEventConfirm(eventName,flowPath,flowState,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	var ce = validateEvent(checkDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
	eventName = ce.getEventName();
	if (ce.getIsEventOk())
	{
		var form = document.getElementById(FORM_NAME);
		form[FORM_FIELD_EVENT_NAME].value = eventName;
		form[FORM_FIELD_FLOW_ID].value    = flowPath;
		form[FORM_FIELD_FLOW_STATE].value = flowState;
		form[FORM_FIELD_IS_POPUP_WINDOW].value = NORMAL_WINDOW_INIDCATION;
		form.target = window.name;
		form.submit();		
	}
	else
	{
		return false;
	}	
}

/*send event from tab*/
function sendEventTab(eventName,flowPath,flowState,flowExtraParams,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds)
{
	//Send event only if form was not already submitted
	var form = document.getElementById(FORM_NAME);
	if (form.isSubmitted == null || form.isSubmitted == false)
	{
		var ce = validateEvent(checkDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,null);	
		eventName = ce.getEventName();
		if (ce.getIsEventOk())
		{
			form[FORM_FIELD_EVENT_NAME].value = eventName;
			form[FORM_FIELD_FLOW_ID].value = flowPath;
			form[FORM_FIELD_FLOW_STATE].value = flowState;
			form[FORM_FIELD_EXTRA_PARAMS].value = flowExtraParams;
			form[FORM_FIELD_IS_POPUP_WINDOW].value = NORMAL_WINDOW_INIDCATION;
			form.target = window.name;
			submitForm(form);	
		}
		else
		{
			return false;
		}	
	}
}

/*send event and open popup window*/
function sendEventPopup(eventName,flowPath,flowState,formTarget,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage,dialogParams)
{
	//If current window is modal dialog it is not legal to open a popup 
	//window.
	if (isDialogWindow())
	{
		alert(GENERAL_MESSAGES[GENERAL_MESSAGE_POPUP_NOT_LEGAL]);
		return;
	} 

	var ce = validateEvent(checkDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
	eventName = ce.getEventName();
	if (ce.getIsEventOk())
	{
		if (formTarget == " " || formTarget == "")
		{
			formTarget = new Date().getMilliseconds()
		}
		var form = document.getElementById(FORM_NAME);
		if (dialogParams == null || dialogParams == "" || dialogParams == " ")
		{
			dialogParams = POPUP_DEFAULT_PARAMS;
		}		
		window.open('',formTarget,dialogParams);
		form.target = formTarget;
		form[FORM_FIELD_EVENT_NAME].value = eventName;
		form[FORM_FIELD_FLOW_ID].value = flowPath;
		form[FORM_FIELD_FLOW_STATE].value = flowState;
		form[FORM_FIELD_IS_POPUP_WINDOW].value = POPUP_WINDOW_INDICATION;
		form.submit();
	}
	else
	{
		return false;
	}	
}

/*send event and open modal dialog window*/
function sendEventDialog(eventName,flowPath,flowState,formTarget,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage,dialogParams)
{
	var ce = validateEvent(checkDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage);	
	eventName = ce.getEventName();
	if (ce.getIsEventOk())
	{
		var form = document.getElementById(FORM_NAME);
		var arr = new Array();
		arr[DIALOG_ARGUEMENT_SOURCE_FORM] = form;
		arr[FORM_FIELD_EVENT_NAME] = eventName;
		arr[FORM_FIELD_FLOW_ID] = flowPath;
		arr[FORM_FIELD_FLOW_STATE] = flowState;
		arr[DIALOG_ARGUEMENT_PARENT_TARGET] = window.name;
		arr[DIALOG_ARGUEMENT_PARENT_WINDOW] = window;
		if (dialogParams == null || dialogParams == "" || dialogParams == " ")
		{
			dialogParams = DIALOG_DEFAULT_PARAMS;
		}
		window.showModalDialog(DIALOG_FILENAME,arr,dialogParams);	
	}
	else
	{
		return false;
	}	
}

/**
* Sets the "top" and "left" parameters when opening a modal dialog 
* or when sending an event from a modal dialog to the dialog itself  
*/
function setDialogParameters()
{
	if (window.dialogArguments != null)
	{
		window.dialogArguments["lastTop"] = screenTop;
		window.dialogArguments["lastLeft"] = screenLeft
	}	
}

function validateEvent(checkGlobalDirty,checkWarnings,eventName,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage)
{
	//validate dirty flag for areas
	var message = getDirtyFlagMessage(dirtyModelsIds,checkGlobalDirty);
	if (message != null)
	{
		//message is empty only when one of the dirty areas does not exist
		if (message != "")
		{
			//if events are allowed when there are dirty areas or there are no dirty
			//areas,show a confirm message
			if (allowEventOnDirtyIds.toString() == "true" || !isCheckDirtyAreas(dirtyModelsIds))
			{
				return new CheckEvent(eventName,(confirm(message)));
			}
			//if events are not allowed when there are dirty areas,show an alert message
			else 
			{
				alert(GENERAL_MESSAGES[GENERAL_MESSAGE_ERROR_DIRTY]);
				return new CheckEvent(eventName,false);
			}
		}
		else
		{
			return new CheckEvent(eventName,false);
		}
	}
	
	//if confirmation message is needed, show confirm message
	else if (confirmMessage != null && confirmMessage != "null") 
	{
		return new CheckEvent(eventName,confirm(confirmMessage));
	}
	
	//send event without any alerts or confirmation
	else
	{
		return new CheckEvent(eventName,true);
	}
}

function getWarningIndication(checkWarnings)
{
	return checkWarnings != null && (checkWarnings.toString() == "true")
}

function submitForm(form)
{
	if (form.isSubmitted == null || form.isSubmitted == false)
	{
		form.isSubmitted = true;
		form.submit();
	}
}

/**
 * Search for dirty tags and returns the dirty flag's message, if such a flag exits.
 */
function getDirtyFlagMessage(dirtyModelsIds,checkGlobalDirty)
{
	//global dirty flag
	if (isCheckGlobalDirty(checkGlobalDirty))
	{
		return getAreaMessage(getGlobalDirtyFlag().dirtyFlagId,true);
	}
	//area dirty flags
	else if (isCheckDirtyAreas(dirtyModelsIds))	
	{
		var dirtyModelsIdsArr = tokenizer(dirtyModelsIds,DIRTY_MODEL_PARAM_SEPERATOR);
		for (index = 0;index < dirtyModelsIdsArr.length;index++)
		{
			//Check if the dirty area exists,if not show an error message
			if (!isDirtyAreaExist(dirtyModelsIdsArr[index]))
			{
				alert(GENERAL_MESSAGES[GENERAL_MESSAGE_DIRTY_AREA_NOT_FOUND2] + " \"" + dirtyModelsIdsArr[index] + "\" " + GENERAL_MESSAGES[GENERAL_MESSAGE_DIRTY_AREA_NOT_FOUND1]);
				return "";
			}
			var message = getAreaMessage(dirtyModelsIdsArr[index],false);
			if (message != null)
			{
				return message;
			}
		}
	}	
	return null;
}

/**
 * Returns the message of a dirty flag, or the message of one of its children.
 */
function getAreaMessage(dirtyModelId,checkGlobalDirty)
{
	//search in the dirty flag
	if (isDirty(dirtyModelId))
	{
		var dirtyFlag = dirtyTree.search(dirtyModelId);
		if (dirtyFlag != null)
		{
			//return the dirty flag's message
			return dirtyFlag.obj.message;
		}
		return null;
	}
	//search the dirty flag's children
	else
	{
		var dirtyModelChildIds = dirtyTree.searchChilds(dirtyModelId);
		if (dirtyModelChildIds != null)
		{
			for (i = 0;i < dirtyModelChildIds.length;i++)
			{
				var dirtyFlag = dirtyModelChildIds[i];
				if (isDirty(dirtyFlag.dirtyFlagId))
				{
					//return the dirty flag's message or the global dirty flag's message
					//if checkGlobalDirty=true
					return checkGlobalDirty ? getGlobalDirtyFlag().message : dirtyFlag.message;
				}	
			}
		}
		return null;
	}	
}

/**
 * Returns true if dirty areas flags are found in the screen and should be checked
 * before sending an event.
 */
function isCheckDirtyAreas(dirtyModelsIds)
{
	return dirtyModelsIds != null && dirtyModelsIds.toString() != "null";
}

function isDirtyAreaExist(dirtyModelId)
{
	return dirtyTree.search(dirtyModelId) != null;
}

/**
 * Returns true if global dirty flag should be checked before sending an event.
 */
function isCheckGlobalDirty(checkGlobalDirty)
{
	return checkGlobalDirty != null && (checkGlobalDirty.toString() == "true");
}

function CheckEvent(eventName,isEventOk)
{
	this.eventName = eventName;
	this.isEventOk = isEventOk;
	this.getIsEventOk = getIsEventOk;
	this.getEventName = getEventName;
	function getIsEventOk()
	{
		return isEventOk;
	}
	function getEventName()
	{
		return eventName;
	}
}
