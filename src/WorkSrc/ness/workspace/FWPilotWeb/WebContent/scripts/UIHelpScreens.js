var selectedHelpItemSpan = null;

function openTreeNode(tableId,rowIndex,openImage,closeImage)
{
	//The main table of the tree
	var mainTable = document.all(tableId);
	//The row whose node was clicked
	var currentRow = mainTable.rows[rowIndex];
	//The level of the row whose node was clicked
	var currentRowLevel = new Number(currentRow.level);
	//Indicates if the node is clicked for opening or closing
	var isOpenAction = (currentRow.expanded != "true");
	
	//Iterarating the rows of the table from the row that was clicked,
	//until the row whose level is the same like the row that was clicked.
	for (index = rowIndex + 1;index < mainTable.rows.length;index++)
	{
		var row = mainTable.rows[index];
		var rowLevel = new Number(row.level);
		currentRow.expanded = "true";
		
		//Open action
		if (isOpenAction)
		{
			changeImageSrc(event.srcElement,openImage);
			//When next level is reached, break
			if (rowLevel - currentRowLevel == 0)
			{
				break;
			}
			//Direct child
			else if (rowLevel - currentRowLevel == 1)
			{
				show(row);
				row.visible = "true";
			}
			//Other child(not direct)
			else
			{
				if (row.visible == "true")
				{
					show(row);
				}
			}
		}
		//Close action
		else
		{
			changeImageSrc(event.srcElement,closeImage);
			currentRow.expanded = "false";
			if (rowLevel - currentRowLevel == 0)
			{
				break;
			}
			//Direct child
			else if (rowLevel - currentRowLevel == 1)
			{
				hide(row);
				row.visible = "false";
			}
			//Other child(not direct)
			else
			{
				hide(row);
			} 
		}		
	}
}

function openTreeNodeSubmitEvent(helpField,listId,contentFieldName,tableId,rowIndex,openImage,closeImage)
{
	//The main table of the tree
	var mainTable = document.all(tableId);
	//The row whose node was clicked
	var currentRow = mainTable.rows[rowIndex];
	//Indicates if the node is clicked for opening or closing
	var isOpenAction = (currentRow.expanded != "true");
	//Close action - do not send event
	if (!isOpenAction)
	{
		openTreeNode(tableId,rowIndex,openImage,closeImage);
	}
	//Open action - send an event only if the children of the node
	//were not already loaded
	else
	{
		var childLoaded = false;
		//The level of the row whose node was clicked
		var currentRowLevel = new Number(currentRow.level);	
		//Get the next row of the table
		var nextRow = mainTable.rows[rowIndex + 1];
		if (nextRow != null)
		{
			var nextRowLevel = new Number(nextRow.level);
			//Check if a child of the row that was clicked ia already loaded
			if (currentRowLevel < nextRowLevel)
			{
				openTreeNode(tableId,rowIndex,openImage,closeImage);
				childLoaded = true;
			}		
		}
		//If the children of the row that was clicked,were not loaded,
		//send an event
		if (!childLoaded)
		{
			setModelProperty(helpField,HELP_MODEL_EXPANDED_DIRECTORY_PROPERTY,currentRow.id,false);
			setHelpItemLinkedItemsProperty(helpField,listId);
			setEventTypeProperty(helpField,HELP_MODEL_EXPAND_NODE_EVENT_TYPE);
			setHelpItemContent(contentFieldName);
		}
	}
}

function addHelpItemLinkEvent(listId)
{
	var isValueExist;
	var select = document.all(listId);
	if (select != null && selectedHelpItemSpan != null)
	{
		var selectedHelpId = selectedHelpItemSpan.id.substring(0,selectedHelpItemSpan.id.indexOf("SPAN"));
		var options = select.options;
		for (index = 0;index < options.length;index++)
		{
			var option = options[index];
			if (selectedHelpItemSpan.id.indexOf(option.value) != -1)
			{
				isValueExist = true;
			}
		}
		if (!isValueExist)
		{
			select.add(new Option(selectedHelpItemSpan.innerText,selectedHelpId));
		}	
	}
}

function removeHelpItemLinkEvent(listId)
{
	var select = document.all(listId);
	if (select != null)
	{
		if (select.selectedIndex != -1)
		{
			select.remove(select.selectedIndex);
		}
	}
}

function previewHelpItemEvent(helpField,tableId,listId,contentFieldName)
{
	setHelpItemLinkedItemsProperty(helpField,listId);
	setExpandedDirectoriesProperty(helpField,tableId);
	setEventTypeProperty(helpField,HELP_MODEL_PREVIEW_EVENT_TYPE);	
	if (contentIframe != null && contentIframe.document != null)
	{
		var form = document.forms[0];
		var content = contentIframe.document.body.innerHTML;
		//Content field already exist
		if (form[contentFieldName] != null)
		{
			form[contentFieldName].value = content; 			 
		}
		//Else - create the field
		else
		{
			addField(form,contentFieldName,content);
		}
		//Submit
		form.submit();
	}
}

function cancelPreviewHelpItemEvent(helpField)
{
	setEventTypeProperty(helpField,HELP_MODEL_CANCEL_PREVIEW_EVENT_TYPE);
	document.forms[0].submit();
}

function showHelpItemEvent(helpField,tableId)
{
	helpField.value = "";
	if (selectedHelpItemSpan != null)
	{
		var selectedHelpId = selectedHelpItemSpan.id.substring(0,selectedHelpItemSpan.id.indexOf("SPAN"));
		//Setting the helpId in the hidden field
		setModelProperty(helpField,HELP_MODEL_SELECTED_ITEM_PROPERTY,selectedHelpId,false);
	}
	//Setting the expanded directories in the hidden field
	setModelProperty(helpField,HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY,getTreeExpandedNodes(tableId),true)
	//Setting the event type before submitting
	setModelProperty(helpField,MODEL_EVENT_TYPE_PROPERTY,HELP_MODEL_SHOW_EVENT_TYPE,false);
	//Submit
	document.forms[0].submit();
}

function selectHelpItemEvent(helpField,helpId)
{
	if (selectedHelpItemSpan != null)
	{
		selectedHelpItemSpan.className = selectedHelpItemSpan.className.substring(0,selectedHelpItemSpan.className.indexOf(CSS_SUFFIX_SELECTED));
	}
	selectedHelpItemSpan = event.srcElement;
	selectedHelpItemSpan.className += CSS_SUFFIX_SELECTED;
}

function selectHelpItemSpan(helpId)
{
	selectedHelpItemSpan = document.all(helpId + "SPAN");
}

function selectHelpItemSubmitEvent(helpField,helpId,tableId)
{
	helpField.value = "";
	//Setting the helpId in the hidden field
	setHelpIdProperty(helpField,helpId);
	setExpandedDirectoriesProperty(helpField,tableId)
	setEventTypeProperty(helpField,HELP_MODEL_SHOW_EVENT_TYPE);
	//Submit
	document.forms[0].submit();
}

function saveHelpItem(helpField,tableId,listId,contentFieldName)
{
	//Setting the expanded directories in the hidden field
	setModelProperty(helpField,HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY,getTreeExpandedNodes(tableId),true)
	//Setting the linked items in the hidden field
	setModelProperty(helpField,HELP_MODEL_LINKED_ITEMS_PROPERTY,getLinkedHelpItems(listId),true)
	//Setting the event type before submitting
	setModelProperty(helpField,MODEL_EVENT_TYPE_PROPERTY,HELP_MODEL_SAVE_EVENT_TYPE,false);
	//Setting the content of the editor(iframe) in the hidden field by the name
	//of contentFieldName
	if (contentIframe != null && contentIframe.document != null)
	{
		var form = document.forms[0];
		var content = contentIframe.document.body.innerHTML;
		//Content field already exist
		if (form[contentFieldName] != null)
		{
			form[contentFieldName].value = content; 			 
		}
		//Else - create the field
		else
		{
			addField(form,contentFieldName,content);
		}
		//Submit
		form.submit();
	}
}

function createNewHelpItem(helpField,helpId,tableId)
{
	//Setting the helpId in the hidden field
	setHelpIdProperty(helpField,helpId);
	//Setting the expanded directories in the hidden field
	setExpandedDirectoriesProperty(helpField,tableId);
	//Setting the event type before submitting
	setEventTypeProperty(helpField,HELP_MODEL_NEW_EVENT_TYPE);
	//Submit
	document.forms[0].submit();	
}

function expandHelpDirectoryEvent(helpField,helpDirId)
{
}

function setHelpIdProperty(helpField,helpId)
{
	setModelProperty(helpField,HELP_MODEL_SELECTED_ITEM_PROPERTY,helpId,false);	
}

function setHelpItemLinkedItemsProperty(helpField,listId)
{
	setModelProperty(helpField,HELP_MODEL_LINKED_ITEMS_PROPERTY,getLinkedHelpItems(listId),true);
}

function setExpandedDirectoriesProperty(helpField,tableId)
{
	setModelProperty(helpField,HELP_MODEL_EXPANDED_DIRECTORIES_PROPERTY,getTreeExpandedNodes(tableId),true)	
}

function setEventTypeProperty(helpField,eventType)
{
	setModelProperty(helpField,MODEL_EVENT_TYPE_PROPERTY,eventType,false);
}

function setHelpItemContent(contentFieldName)
{
	if (contentIframe != null && contentIframe.document != null)
	{
		var form = document.forms[0];
		var content = contentIframe.document.body.innerHTML;
		//Content field already exist
		if (form[contentFieldName] != null)
		{
			form[contentFieldName].value = content; 			 
		}
		//Else - create the field
		else
		{
			addField(form,contentFieldName,content);
		}
		//Submit
		form.submit();
	}
}

function getTreeExpandedNodes(tableId)
{
	//The main table of the tree
	var mainTable = document.all(tableId);
	//The rows of the table
	var rows = mainTable.rows;
	var nodes = "";
	
	for (index = 0;index < rows.length;index++)
	{
		var row = rows[index];
		if (row.expanded == "true")
		{
			nodes += row.id + MODEL_MULTI_VALUES_SEPERATOR;
		}
	}
	return nodes;
}

function getLinkedHelpItems(listId)
{
	var items = "";
	var select = document.all(listId);
	var options = select.options;
	for (index = 0;index < options.length;index++)
	{
		var option = options[index];	
		items += option.value + MODEL_MULTI_VALUES_SEPERATOR;
	}
	return items;
}

