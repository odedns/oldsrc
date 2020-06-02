var columns;
var sortedColumns;
var numberOfSortedColumns;
var maximumNumberOfSortedColumns;
function initSort()
{	
	columns = parent[document.all.returnFieldName.value + "Columns"];
	maximumNumberOfSortedColumns = new Number(numberOfColumns.value);
	if (maximumNumberOfSortedColumns > columns.length)
	{
		maximumNumberOfSortedColumns = columns.length;
	}
	initSortedColumns();
	var totalNumberOfColumns = sortedColumns.length;
	if (totalNumberOfColumns > numberOfColumns.value)
	{
		totalNumberOfColumns = numberOfColumns.value;
	}
	
	removeAllRows();
	for (col = 0;col < totalNumberOfColumns;col++)
	{
		addRow(col);
	}
	
	initLabels();
}

function removeAllRows()
{
	var l = sortTable.rows.length;
	for (index = 0;index < l;index++)
	{
		sortTable.deleteRow();
	}
}

function addRow(columnIndex)
{
	var upChecked = "";
	var downChecked = "";
	if (sortedColumns[columnIndex] != null)
	{
		upChecked = (sortedColumns[columnIndex].sortOrder != -1 && sortedColumns[columnIndex].sortDirection == SORT_ASC) ? "checked" : "";
		downChecked = (sortedColumns[columnIndex].sortOrder != -1 && sortedColumns[columnIndex].sortDirection == SORT_DESC) ? "checked" : "";
	}
	if (upChecked == "" && downChecked == "")
	{
		upChecked = "checked";
	}

	addTableSortSeperator();
	addTableSortRow(columnIndex,upChecked,downChecked);
}

function addNewRow()
{
	addTableSortSeperator();
	addTableSortRow(-1,"checked","");
}

function addTableSortRow(columnIndex,upChecked,downChecked)
{
	var sortTableRow = sortTable.insertRow();
	sortTableRow.id = "sortTableTr";
	addSelectCell(sortTableRow,columnIndex);
	addRadioCell(sortTableRow,upChecked,downChecked);	
}

function addTableSortSeperator()
{
	var sortRows = document.all("sortTableTr");
	if (sortRows != null && sortRows.length != 0)
	{
		sortTableSeperatorRow = sortTable.insertRow();
		sortTableSeperatorCell = sortTableSeperatorRow.insertCell();
		sortTableSeperatorCell.colSpan = "6";	
		sortTableSeperatorCell.innerHTML = "<hr class='sortSeperator'>";
	}
}

function addSelectCell(sortTableRow,columnIndex)
{
	var selectCell = sortTableRow.insertCell();
	selectCell.className = "sortSelectTd"
	var select = document.createElement("select");
	select.setAttribute("id","sortSelect");
	select.attachEvent("onchange",changeSelect);
	select.className = "sortSelect";
	if (columnIndex != 0 || numberOfSortedColumns == 0)
	{
		select.add(getOption("empty","",false));
	}
	for (index = 0;index < columns.length;index++)
	{
		var currentColumn = columns[index];
		var isSelected = columnIndex == -1 ? false : (new Number(currentColumn.sortOrder) == columnIndex);
		select.add(getOption(currentColumn.columnId,currentColumn.columnName,isSelected));
		if (isSelected)
		{
			select.selectedIndex = select.options.length - 1;
		}
	}	
	selectCell.appendChild(select);
}

function addRadioCell(sortTableRow,upChecked,downChecked)
{
	var radioSuffix = Math.random();

	var ascRadioCell = sortTableRow.insertCell();
	ascRadioCell.width = "1";
	ascRadioCell.innerHTML = getRadioHtml(SORT_ASC,upChecked,radioSuffix);

	var ascRadioTextCell = sortTableRow.insertCell();
	ascRadioTextCell.className = "sortRadioText";
	ascRadioTextCell.innerText = parent.SORT_MESSAGES[0];
	
	var seperatorCell = sortTableRow.insertCell();
	seperatorCell.width = "1";
	
	var descRadioCell = sortTableRow.insertCell();
	descRadioCell.width = "1";
	descRadioCell.innerHTML = getRadioHtml(SORT_DESC,downChecked,radioSuffix);
	
	var descRadioTextCell = sortTableRow.insertCell();
	descRadioTextCell.className = "sortRadioText";
	descRadioTextCell.innerText = parent.SORT_MESSAGES[1];
}



/**
* Returns the index of a select,which value is equal to the value of
* the currentSelect.
*/
function getSelectValueIndex(currentSelect)
{
	var value = currentSelect.value;
	var selects = document.all("sortSelect");
	var l = selects.length;
	for (index = 0;index < l;index++)
	{
		if (selects[index].tagName != "SELECT")
		{
			return -1;
		}
		if (currentSelect != selects[index] && selects[index].value == value)
		{
			return index;
		}
	}
	return -1;
}

function changeSelect()
{
	var select = event.srcElement;
	var selects = document.all("sortSelect");
	var row = select.parentElement.parentElement;
	var rowIndex = getRowIndex(row);
	var tableLength = sortTable.rows.length;
	var sortRows = document.all("sortTableTr");
	var sortedRowsNumber = sortRows.length != null ? sortRows.length : 1;
	
	//tableLength = sortedRowsNumber;
	//if (tableLength == null) tableLength = 1;
	
	var value = select.value;
	var selectValueIndex = getSelectValueIndex(select);
	
	//The combo's value is changed to empty
	if (value == "empty" && selects[selects.length - 1] != select)
	{
		//If maximum number of selects is already rendered,
		//delete row and add another empty one in the last place.
		if (sortedRowsNumber == maximumNumberOfSortedColumns)
		{
			deleteRow(rowIndex);
			
			//If last row is not empty,add a new select with empty value selected
			if (selects != null && selects.length != null && selects[selects.length - 1].value != "empty")
			{
				addNewRow();
			}
		}
		else
		{
			deleteRow(rowIndex);
		}
	}
	else
	{
		//If last combo's value was changed,add new combo
		if (rowIndex == tableLength - 1)
		{
			//if value already exist in another combo,cancel selection in the combo,
			//change its selectedIndex property to 0.
			if (selectValueIndex != -1)
			{
				select.selectedIndex = 0;
			}
			//If value is new,add another select in a new row.
			else if (sortedRowsNumber < maximumNumberOfSortedColumns)
			{
				addRow(tableLength);
			}
		}	
		//If not last combo,check that value does not already exist in 
		//another combo.If it already exists,delete the combo whose row number
		//is higher.
		else
		{
			if (selectValueIndex != -1)
			{			
				deleteRow((rowIndex < selectValueIndex) ? selectValueIndex : rowIndex);
			}
		}
	}
}

function getRowIndex(row)
{
	var l = sortTable.rows.length;
	for (index = 0;index < l;index++)
	{
		if (sortTable.rows[index] == row)
		{
			return index;
		}
	}
	return -1;
}

function deleteRow(rowIndex)
{
	//Delete row in the table which contains a select object
	sortTable.deleteRow(rowIndex);

	//Delete also the seperator of the deleted row,if one exists
	if (sortTable.rows.length > 1)
	{
		sortTable.deleteRow(rowIndex);
	}
	
}

function getRadioHtml(value,checked,radioSuffix)
{
	return "<input type=radio name=dir" + radioSuffix + " id=direction class='radioSort' value=" + value + " " + checked + ">";
}

function getOption(value,text,selected)
{
	var option = new Option(text,value,false,false);
	option.selected = selected;
	return option;
}

function initLabels()
{
	var labels = parent.SORT_MESSAGES;
	sortButton.value = labels[2];
	cancelButton.value = labels[3];
	sortTitle.innerText = labels[5];
}

function initSortedColumns()
{
	sortedColumns = new Array();
	notSortedColumns = new Array();
	for (index = 0;index < columns.length;index++)
	{
		var tableColumn = columns[index];
		if (isSorted(tableColumn))
		{
			sortedColumns[tableColumn.sortOrder] = tableColumn;
		}
		else
		{
			notSortedColumns[notSortedColumns.length] = tableColumn;
		}
	}
	numberOfSortedColumns = sortedColumns.length;
	if (maximumNumberOfSortedColumns != numberOfSortedColumns)
	{
		sortedColumns[numberOfSortedColumns] = new TableColumn("","",-1,"1");
	}
}

function isSorted(tableColumn)
{
	return tableColumn.sortOrder != -1;
}

function submitSort()
{
	var selects = document.all("sortSelect");
	var directionRadio = document.all("direction");
	var ids = "";
	var directions = "";
	var selectedItems = new Array();
	if (selects != null)
	{
		var length = selects.length;
		
		//only 1 combo
		if (length == null || selects[0].tagName != "SELECT")
		{
			if (selects.value != "empty")
			{
				ids += selects.value + MODEL_MULTI_VALUES_SEPERATOR;
				if (directionRadio[0].checked)
				{
					directions += directionRadio[0].value;
				}
				else
				{
					directions += directionRadio[1].value;
				}
				directions += MODEL_MULTI_VALUES_SEPERATOR;	
			}	
			else
			{
				parent.sortPopup.hide();
				return;
			}		
		}
		else
		{
			for (index = 0;index < length;index++)
			{
				if (selects[index].value != "empty" && selectedItems[selects[index].value] == null)
				{
					ids += selects[index].value + MODEL_MULTI_VALUES_SEPERATOR;
					if (directionRadio[2 * index].checked)
					{
						directions += directionRadio[2 * index].value;
					}
					else
					{
						directions += directionRadio[2 * index + 1].value;
					}
					directions += MODEL_MULTI_VALUES_SEPERATOR;	
					selectedItems[selects[index].value] = "";
				}
			}
		}
	}
	parent.sortEvent(returnFieldName.value,ids,directions,eventName.value,flowPath.value,flowState.value,false,false,null,true,null);
}