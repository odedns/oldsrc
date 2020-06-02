/*****functions for handling the events of the table model*****/

/*opens menu of a row*/
function createMenu(openMark,modelId,rowId,menuStr,menuClassName)
{
	//caption-className-event|caption-className-event
	if (countChecked(document.all(modelId + TABLE_CHECKBOX_SUFFIX)) < 2)
	{
		var menu = document.all(modelId + "Menu");
		if (menu == null)
		{
			var menuHtml = getFloatingMenuDivHtml(modelId,menuClassName);
			menuHtml += getMenuTableHtml(modelId,rowId,menuStr,menuClassName);
			menuHtml += "</div>";
			document.body.insertAdjacentHTML(HTML_VALUE_AFTER_BEGIN,menuHtml);
			menu = document.all(modelId + "Menu");		
		}
		else
		{
			menu.innerHTML = getMenuTableHtml(modelId,rowId,menuStr,menuClassName);
		}
		
		menu.style.display = '';
		floatingMenuPopup.document.body.dir = document.body.dir;
		floatingMenuPopup.document.body.innerHTML = menu.outerHTML;
		var relativeObject = openMark.parentElement;
		floatingMenuPopup.show((document.body.dir == SYSTEM_DIR_RTL ? -menu.offsetWidth + relativeObject.offsetWidth : 0) ,0,menu.offsetWidth,menu.offsetHeight + 2,relativeObject);
		menu.style.display = 'none';
	}
}

function onFloatingMenuLoad(src)
{
	floatingMenuPopup.document.write(src);
}

function getFloatingMenuDivHtml(modelId,menuClassName)
{
	return "<div id=" + modelId + "Menu" + " style=\"width:100; position:absolute;display:none;\" class=" + menuClassName + ">"
}

function getMenuTableHtml(modelId,rowId,menuStr,menuClassName)
{
	var menuTableHtml = "<table cellpadding=\"0\" cellspacing=\"1\" border=\"0\" width=100%>"
	var outerIndex = menuStr.indexOf(SEPERATOR);
	var lastIndex = 0;
	while (outerIndex != -1)
	{
		var menuItemStr = menuStr.substring(lastIndex,outerIndex);	
		menuTableHtml += getMenuItemHtml(modelId,rowId,menuItemStr);		
		lastIndex = outerIndex + 1; 
		outerIndex = menuStr.indexOf(SEPERATOR,lastIndex);	
	}		
	if (menuStr.length != 0) 
	{
		var menuItemStr = menuStr.substring(lastIndex);
		menuTableHtml += getMenuItemHtml(modelId,rowId,menuItemStr);	
	}	
	menuTableHtml += "</table>";
	return menuTableHtml;
}

/*create one line in a menu*/
function getMenuItemHtml(modelId,rowId,menuItemStr)
{
	var menuItemsProps = tokenizer(menuItemStr,SEPERATOR_MINUS);
	var index = menuItemStr.indexOf(SEPERATOR_MINUS);
	var lastIndex = menuItemStr.lastIndexOf(SEPERATOR_MINUS);
	var caption = menuItemsProps[0];
	var className = menuItemsProps[1];
	var event = menuItemsProps[2];
	var eventTargetType = menuItemsProps[3];
	var menuItemIndex = menuItemsProps[4];
	var checkDirty = menuItemsProps[5];
	var checkWarnings = menuItemsProps[6];
	var dirtyModelsIds = menuItemsProps[7];
	var allowEventOnDirtyIds = menuItemsProps[8];
	var confirmMessage = menuItemsProps[9];
	var flowPath = menuItemsProps[10];
	var flowState = menuItemsProps[11];
	var dialogParams = menuItemsProps[12];
	var menuItemHtml = 
		"<tr><td nowrap=\"nowrap\" style=\"cursor:hand\""
		+ " onmouseout=\"this.className='" + className + "'\""
	    + " onmouseover=\"this.className='" + className  + "Over'\""
	    + " onclick=\""; 
	menuItemHtml += "parent.rowEvent('" + modelId + "','" + rowId + "','" + menuItemIndex + "','" + event + "','" + flowPath + "','" + flowState + "','" + eventTargetType + "','" + dialogParams + "'," + checkDirty + "," + checkWarnings + ",'" + dirtyModelsIds + "','" + allowEventOnDirtyIds + "','" + confirmMessage + "')\"";	    	
	menuItemHtml += "\" class=" + className + ">" + caption + "</td></tr>";
	return menuItemHtml;	
}

/*closes menu that "hovers"*/
function closeMenu(modelId)
{
	hide(document.all(modelId + "Menu"));
}

/*check all the rows*/
function checkAllRows(modelId,check)
{
	var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
	if (modelCheckboxes != null) {
		if (modelCheckboxes.length == null) {
			modelCheckboxes.checked = check;
		}
		else {
			var l = modelCheckboxes.length;
			for (index = 0;index < l;index++) {
				modelCheckboxes[index].checked = check;
			}
		}
	}
}

/*select one row*/
var currentSelectedRow = new Array();
function rowSelectedStyle(modelId,row,selectedClassName,regClassName,selectedStyle,regStyle,dirtable)
{
	if (dirtable)
	{
		setDirty();
	}
	currentSelected = currentSelectedRow[modelId]
	if (currentSelected != null) 
	{
		if (regClassName != null) 
		{
			var classSeperator = currentSelected.row.className.indexOf(" ");
			if (classSeperator != -1) 
			{
				currentSelected.row.className = currentSelected.row.className.substring(0,classSeperator);
			}
		}
		if (regStyle != null)
		{
			currentSelected.row.style.cssText = currentSelected.rowStyle;
		}
	}
	if (selectedClassName != "null") 
	{
		row.className += " " + selectedClassName;
	}
	if (selectedStyle != "null")
	{
		row.style.cssText = selectedStyle;
	}
	if (currentSelected != null) 
	{
		setCellsClassName(currentSelected.row,selectedClassName);
	}
	currentSelectedRow[modelId] = new Row(row,regClassName,regStyle);	
	setCellsClassName(row,selectedClassName);
	resizeAll();
}

/**
* Change the class name of row's cells by adding a class to the current
* class or removing one of the classes.
*/
function setCellsClassName(row,selectedClassName)
{
	var length = row.cells.length;
	for (index = 0;index < length;index++)
	{
		cell = row.cells[index];
		if (cell.id != null)
		{
			//Check first cell
			if (cell.id == "0")
			{
				//First cell of tree table
				if (cell.children != null && cell.children[0] != null && cell.children[0].tagName == "TABLE")
				{
					var innerCell = cell.children[0].rows[0].cells[1];					
					//Linked inner cell
					if (innerCell.children != null && innerCell.children[0] != null && innerCell.children[0].tagName == "A")
					{
						var cellLink = innerCell.children[0];
						changeClassName(cellLink,cellLink.className + CSS_SUFFIX_SELECTED);
					}		
					//Regular inner cell			
					changeClassName(innerCell,selectedClassName);					
				}
			}
			
			//Linked cell
			if (cell.children != null && cell.children[0] != null && cell.children[0].tagName == "A")
			{
				var cellLink = cell.children[0];
				changeClassName(cellLink,cellLink.className + CSS_SUFFIX_SELECTED);
			}
			//Regular cell
			changeClassName(cell,selectedClassName);
		}
	}			
}

/*make a row selected*/
function selectRow(modelId,rowId,rowClassName,rowStyle)
{
	var row = getObjectById(rowId);
	if (row != null) 
	{		
		currentSelectedRow[modelId] = new Row(row,rowClassName,rowStyle);
		//setCellsClassName(row,rowClassName);
	}
}

/*checks if row is selected*/
function isRowSelected(modelId,row)
{
	if (currentSelectedRow[modelId] != null) {
		return (row == currentSelectedRow[modelId].row);
	}
	return false;
}

/*get single selected row id*/
function getSelectedRowId(modelId)
{
	if (currentSelectedRow[modelId] != null) 
	{
		return currentSelectedRow[modelId].row.rid;
	} 
	else
	{
		return "";
	}
}

/*get array of multiple checked row ids*/
function getCheckedRowIds(modelId)
{
	var checkRows = new Array();
	var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
	if (modelCheckboxes != null)
	{
		var counter = 0;
		var l = modelCheckboxes.length;
		for (i = 0;i < 	l;i++)
		{
			var cb = modelCheckboxes[i];
			if (cb.checked)
			{
				checkRows[counter++] = cb.value.substring(cb.value.indexOf(SEPERATOR) + 1);				
			}
		}
	}
	return checkRows;
}

/*get string representation of checked row ids*/
function getCheckedRowsAsString(modelId)
{
	var checkRows = new String("");
	var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
	if (modelCheckboxes != null)
	{
		var counter = 0;
		var l = modelCheckboxes.length;
		if (l == null)
		{
			if (modelCheckboxes.checked)
			{
				checkRows = modelCheckboxes.value;
			}
		}
		else
		{
			for (i = 0;i < 	l;i++)
			{
				var cb = modelCheckboxes[i];
				var val = cb.value;
				var isChecked = cb.checked;
				if (isChecked)
				{
					checkRows = checkRows.concat(val + MODEL_MULTI_VALUES_SEPERATOR);
				}
			}
			if (checkRows != "")
			{
				checkRows = checkRows.substring(0,checkRows.length - 1);
			}
		}
	}
	return checkRows;
}

/*row - onmouseover and onmouseout event*/
function rowHover(modelId,row,className)
{

}


/*checks or unchecks the "selectAll" checkbox each time a row is checked*/
function checkRow(modelId)
{
	var selectAllCB = document.all(TABLE_CHECK_ALL_CHECKBOX_PREFIX + modelId);
	if (selectAllCB != null) 
	{
		var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
		if (modelCheckboxes != null) {
			if (modelCheckboxes.length == null) 
			{
				selectAllCB.checked = modelCheckboxes.checked;
			}
			else 
			{
				var l = modelCheckboxes.length;
				for (index = 0;index < l;index++) {
					if (!modelCheckboxes[index].checked) 
					{
						selectAllCB.checked = false;
						return;			
					}
				}
				selectAllCB.checked = true;
			}
		}
	}
}

/*returns array of extra data params of selected rows in multiple selection*/
function getSelectedRowsExtraData(modelId,paramName)
{
	var extraDataParams = new Array();
	var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
	if (modelCheckboxes != null)
	{
		var counter = 0;
		var l = modelCheckboxes.length;
		for (i = 0;i < 	l;i++)
		{
			var cb = modelCheckboxes[i];
			
			//search for checked rows
			if (cb.checked)
			{
				var row = cb.parentElement.parentElement;
				if (row != null)
				{
					extraDataParams[counter++] = getExtraData(row.ed,paramName);
				}
				
			}
		}
	}	
	return extraDataParams;
}

function getSelectedRowExtraData(modelId,paramName)
{
	if (currentSelectedRow[modelId] != null)
	{
		var row = currentSelectedRow[modelId].row;
		return (getExtraData(row.ed,paramName));
	}
	else
	{
		return "";
	}
}

/*return rows's extra data param by the param name*/
function getExtraData(extraData,paramName)
{
	var startIndex = extraData.indexOf(paramName + SEPERATOR_MINUS);
	if (startIndex != -1)
	{
		var endIndex = extraData.indexOf(SEPERATOR,startIndex);
		if (endIndex == -1) 
		{
			return extraData.substring(startIndex + paramName.length + 1);
		}
		else 
		{
			return extraData.substring(startIndex + paramName.length + 1,endIndex);
		}
	}
	else
	{
		return "";
	}	
}

/*
returns string of cells values in a specific row of a table model or
a tree table model.
*/
function getRowData(modelId,rowId)
{
	var table = document.getElementById(modelId + TABLE_DIV_ID);
	if (table == null) return "";
	var rows = table.children(0).rows;
	if (rows == null) return "";
	var cellsValues = new Array();
	var length = rows.length;
	for (index = 0;index < length;index++)
	{
		var row = rows[index];
		if (row.rid == rowId)
		{
			var cells = row.cells;
			if (cells != null)
			{
				var cellLength = cells.length;
				for (cellIndex = 0;cellIndex < cells.length;cellIndex++)
				{
					var cell = cells[cellIndex];
					if (cell.id != null)
					{
						cellsValues[cell.id] = cell.innerText;
					}
				}
			}
		}
	}
	return cellsValues;
}

/*
returns value of a cell in the first selected row in the model by the column index
*/
function getCellData(modelId,columnIndex)
{
	var table = document.getElementById(modelId + TABLE_DIV_ID);
	if (table == null) return "";
	var rows = table.children(0).rows;
	if (rows == null) return "";
	
	//single selection
	if (currentSelectedRow[modelId] != null)
	{
		var row = currentSelectedRow[modelId].row;
		var cells = row.cells;
		var length = cells.length;
		if (cells != null)
		{
			for (index = 0;index < length;index++)
			{
				var cell = cells[index];
				if (cell.id != null && cell.id == columnIndex)
				{
					return cell.innerText;
				}
			}
		}
	}
	
	//multiple selection
	else 
	{
		var modelCheckboxes = getObjectById(modelId + TABLE_CHECKBOX_SUFFIX);
		if (modelCheckboxes != null)
		{
			var length = rows.length;
			for (index = 0;index < length;index++)
			{
				var row = rows[index];
				if (modelCheckboxes[index].checked)
				{
					var cells = row.cells;
					if (cells != null)
					{
						var cellLength = cells.length;
						for (cellIndex = 0;index < cellLength;cellIndex++)
						{
							var cell = cells[cellIndex];
							if (cell.id != null && cell.id != "" && cell.id == columnIndex)
							{
								return cell.innerText;
							}
						}
					} 
				}
			}
		}			
	}
	return "";
}

/*
returns value of a cell in a specific row of a table model or a tree
table model by the column index.
*/
function getData(modelId,rowId,columnIndex)
{
	var table = document.getElementById(modelId + TABLE_DIV_ID);
	if (table == null) return "";
	var rows = table.children(0).rows;
	if (rows == null) return "";
	var length = rows.length;
	for (index = 0;index < length;index++)
	{
		var row = rows[index];
		if (row.rid == rowId)
		{
			var cells = row.cells;
			if (cells != null)
			{
				var cellLength = cells.length;
				for (cellIndex = 0;cellIndex < cellLength;cellIndex++)
				{
					var cell = cells[cellIndex];
					if (cell.id != null && cell.id == columnIndex)
					{
						return cell.innerText;
					}
				}
			}
		}
	}
	return "";
}

/*Row object keeps style information about the current selected row*/
function Row(row,rowClassName,rowStyle)
{
	this.row = row;
	this.rowClassName = rowClassName;
	this.rowStyle = rowStyle;	
}

function TableColumn(columnId,columnName,sortOrder,sortDirection)
{
	this.columnId = columnId;
	this.columnName = columnName;
	this.sortOrder = sortOrder;
	this.sortDirection = sortDirection;
}

function onSortLoad(src)
{
	sortPopup.document.write(src);
}

function openSortPopup(tableModelId,relativeObject,eventName,flowPath,flowState,numberOfColumns)
{
	if (self[tableModelId + TABLE_SORT_COLUMNS_SUFFIX] != null && self[tableModelId + TABLE_SORT_COLUMNS_SUFFIX].length > 0)
	{
		sortPopup.document.body.dir = document.body.dir;
		sortPopup.document.all.returnFieldName.value = tableModelId;
		sortPopup.document.all.eventName.value = eventName;
		sortPopup.document.all.flowPath.value = flowPath;
		sortPopup.document.all.flowState.value = flowState;
		sortPopup.document.all.numberOfColumns.value = numberOfColumns;
		sortPopup.show((document.body.dir == SYSTEM_DIR_LTR ? - POPUP_SORT_WIDTH + relativeObject.offsetWidth : 0) , 0,POPUP_SORT_WIDTH,POPUP_SORT_ORDER_HEIGHT,relativeObject);
		sortPopup.document.all.initButton.fireEvent(HTML_EVENT_ONCLICK);	
	}
}

function TableColumnOrder(columnId,columnName,columnOrder,displayable,removable)
{
	this.columnId = columnId;
	this.columnName = columnName;
	this.columnOrder = columnOrder;
	this.displayable = displayable;
	this.removable = removable;
}

function onColumnOrderLoad(src)
{
	columnOrderPopup.document.write(src);
}

function openColumnOrderPopup(tableModelId,relativeObject,eventName,flowPath,flowState)
{
	if (self[tableModelId + TABLE_ORDER_COLUMNS_SUFFIX] != null && self[tableModelId + TABLE_ORDER_COLUMNS_SUFFIX].length > 0)
	{
		columnOrderPopup.document.body.dir = document.body.dir;
		columnOrderPopup.document.all.returnFieldName.value = tableModelId;
		columnOrderPopup.document.all.eventName.value = eventName;
		columnOrderPopup.document.all.flowPath.value = flowPath;
		columnOrderPopup.document.all.flowState.value = flowState;
		columnOrderPopup.show((document.body.dir == SYSTEM_DIR_LTR ? - POPUP_COLUMN_ORDER_WIDTH + relativeObject.offsetWidth : 0) , 0,POPUP_COLUMN_ORDER_WIDTH,POPUP_COLUMN_ORDER_HEIGHT,relativeObject);
		columnOrderPopup.document.all.initButton.fireEvent(HTML_EVENT_ONCLICK);	
	}	
}
