var columns;
var columnsMap;
var labels;
function initColumnOrder()
{	
	initLabels();
	resetMessage();
	columns = parent[document.all.returnFieldName.value + "ColumnsOrder"];
	columnsMap = new Array();
	var srcArr = new Array();
	var trgArr = new Array();
	for (index = 0;index < columns.length;index++)
	{
		var column = columns[index];
		columnsMap[column.columnId] = column;
		srcArr[index] = new ListOption(column.columnId,column.columnName,!column.displayable);
		if (column.displayable)
		{
			trgArr[trgArr.length] = new ListOption(column.columnId,column.columnName,column.displayable);
		}
	}
	l2l = new ListToList(srcArr,trgArr,"",JS_L2L_OBJECT_TYPE_ALL,LIST_SEARCH_IN_NONE,LIST_SEARCH_TYPE_NONE,LIST_SEARCH_ACTION_NONE);
	l2l.bindControls();
	l2l.arrayToList(l2l.srcList,"",l2l.srcArray,l2l.sourceSearchAction,l2l.sourceSearchType,false);
	l2l.arrayToList(l2l.tgtList,"",l2l.tgtArray,l2l.targetSearchAction,l2l.targetSearchType,false);		
}

function isColumnMovementLegal(moveAll)
{
	var message = "";
	var options = TrgList.options;
	var length = options.length;
	for (index = 0;index < length;index++)
	{
		if (moveAll)
		{
			options[index].selected = true;
		}
		var column = columnsMap[options[index].value];
		if ((options[index].selected || moveAll) && !column.removable)
		{
			options[index].selected = false;
			message += labels[10] + " " + column.columnName + " " + labels[11] + "<br>";
			show(messagesCell);
		}
	}
	messagesCell.innerHTML = message;
	/*if (moveAll && message != null)
	{
		return false;
	}*/
	return true;
}

function listToListAction(listToList,clearMessage)
{
	listToList.doEvents(event);
	if (clearMessage)
	{
		resetMessage();
	}
}

function resetMessage()
{
	messagesCell.innerText = "";
}

function submitColumnOrder()
{
	var columnIds = "";
	var length = TrgList.length;
	for (index = 0;index < length;index++)
	{
		columnIds += TrgList.options[index].value;
		if (index != length - 1)
		{
			columnIds += MODEL_MULTI_VALUES_SEPERATOR;
		}
	}
	parent.orderEvent(returnFieldName.value,columnIds,eventName.value,flowPath.value,flowState.value,false,false,null,true,null);
}

function initLabels()
{
	labels = parent.COLUMN_ORDER_LABELS;
	SrcToTrg.value = labels[0];
	TrgToSrc[0].value = labels[1];
	SrcToTrgAll.value = labels[2];
	TrgToSrc[1].value = labels[3];
	TrgUp.value = labels[4];
	TrgDown.value = labels[5];
	approveButton.value = labels[6];
	cancelButton.value = labels[7];
	trgTitle.innerText = labels[8];
	srcTitle.innerText = labels[9];
}