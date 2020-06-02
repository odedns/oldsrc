/*popup*/
var calendarPopup = window.createPopup();
var monthCalendarPopup = window.createPopup();
var floatingMenuPopup = window.createPopup();
var sortPopup = window.createPopup();
var columnOrderPopup = window.createPopup();
var helpLinksPopup = window.createPopup();

/*arrays*/
var listComponents = new Array();

function init()
{
	/**********initialize modal dialog********/
	initModal();
		
	/**********init tables***********/
	var components = getObjectById(TABLE_COMPONENT_NAME);
	if (components != null)
	{
		var length = components.length;
		if (length == null)
		{
			initScrollTable(components);
		}
		else
		{
			for (i = 0;i < length;i++)
			{
				initScrollTable(components[i]);
			}
		}
	}
	
	/**********init trees***********/
	var trees = getObjectById(TREE_COMPONENT_NAME);
	if (trees != null)
	{
		if (trees.length == null)
		{
			initSelectedNode(trees);
			trees.attachEvent("onresize", function () {
				initSelectedNode(trees);
			});		
		}
		else
		{
			initSelectedNode(trees[0]);		
			trees[0].attachEvent("onresize", function () {
				initSelectedNode(trees[0]);
			});	
		}
		
	}
	
	/**********init menu************/
	
	if (document.getElementById("menuTd") != null)
	{
		cmDraw('menuTd', myMenu, 'hb' + document.body.dir.charAt(2), cmThemeIE, 'ThemeIE');
		//resizeAll();
	}

	/*********init lists**************/
	for (index = 0;index < listComponents.length;index++)
	{
		var listComponent = document.all(listComponents[index])
		if (listComponent.length != null)
		{
			alert("error - field " + listComponent[0].name + " is defined " + listComponent.length + " times");
			return;
		}
		else
		{
			eval(listComponent.name + ".bindControls()");
		}
	}
		
	/*******init focus for form elements*************/
	if (document.all(FORM_NAME) != null)
	{
		var formElements = document.all(FORM_NAME).elements;
		var formElementsLength = formElements.length;
		var firstElement;
		for (index = 0;index < formElementsLength;index++)
		{
			var formElement = formElements[index];
			if ((formElement.type == "text" || formElement.tagName == "TEXTAREA" || formElement.tagName == "SELECT") && isDisplayed(formElement) && !formElement.disabled && (formElement.readOnly == null || !formElement.readOnly) && firstElement == null)
			{
				firstElement = formElement;
			} 
			if (formElement.setFocus != null && formElement.setFocus == "true")
			{
				setComponentFocus(formElement);
				firstElement = null;
				break;
			}
		}
		if (firstElement != null)
		{
			setComponentFocus(firstElement);
		}
		
	}	
	
	/*******init popups*************/
    // startDownload is a member of the download default behavior.
    // The callback function pointer (second parameter) specifies a 
    // function. When a file downloads successfully, the file contents
    // are passed as the parameter to onDone().
    if (dwn != null)
    {
    	dwn.startDownload("html/UICalendar.html",onCalendarLoad);
    	dwn.startDownload("html/UIMonthCalendar.html",onMonthCalendarLoad);
    	dwn.startDownload("html/UIFloatingMenu.html",onFloatingMenuLoad);
    	dwn.startDownload(HELP_CONTAINER_DIALOG_FILENAME,onHelpLinksLoad);
    	dwn.startDownload("html/UISort.html",onSortLoad);
    	dwn.startDownload("html/UIColumnOrder.html",onColumnOrderLoad);
    }		

	/*******init dialog title*******/
	//if (window.dialogArguments != null)
	//{
		//window.document.title = "x";
		//document.write("<title>"  + WINDOW_TITLE + "</title>");

	//}
	

}

function initName(windowName)
{
	window.name = windowName + new Date().getMilliseconds();
}

function initSelectedRow(modelId)
{
	if (modelId != null && currentSelectedRow[modelId] != null)
	{
		currentSelectedRow[modelId].row.scrollIntoView();
	}	
}

function initSelectedNode(treeDiv)
{
	if (!isMenuCurrentlyOpen && !isMenuCloseEventOccured)
	{
		if (treeDiv.model != null && selectedNodesArray[treeDiv.model] != null)
		{
			selectedNodesArray[treeDiv.model].scrollIntoView();
		}
	}
}

function resizeAll()
{
	var components = document.getElementById(TABLE_COMPONENT_NAME);
	if (components != null)
	{
		var length = components.length;
		if (length == null)
		{
			resizeScrollTable(components);
		}
		else
		{
			for (i = 0;i < length;i++)
			{
				resizeScrollTable(components[i]);
			}
		}
	}
}

function isMenuOpen()
{
	return false;
}

function showMenuArray()
{
	for (index = 0;index < menuArray.length;index++)
	{
		alert(menuArray[index] + ":" + isVisible(document.all(menuArray[index])));
	}
}

function initScrollTable(oElement)
{
	resizeScrollTable(oElement);
	oElement.firstChild.syncTo = oElement.children[1].uniqueID;
	oElement.firstChild.syncDirection = "horizontal";
	oElement.lastChild.syncTo = oElement.children[1].uniqueID;
	oElement.lastChild.syncDirection = "horizontal";
	oElement.attachEvent("onresize", function () {
		resizeScrollTable(oElement);
	});
}

function resizeScrollTable(oElement) 
{
	if (!isMenuCurrentlyOpen && !isMenuCloseEventOccured)
	{
		var head = oElement.firstChild;
		var headTable = head.firstChild;
		var body = oElement.children[1];
		var bodyTable = body.firstChild;
		var footer = oElement.lastChild;
		var footerTable = footer.firstChild;
		
		//body.style.height = Math.max(0, oElement.clientHeight - head.offsetHeight - footer.offsetHeight);
		
		var scrollBarWidth = body.offsetWidth - body.clientWidth;
		
		// set width of the table in the head
		headTable.style.width = Math.max(0, Math.max(bodyTable.offsetWidth + scrollBarWidth, oElement.clientWidth));
		footerTable.style.width = Math.max(0, Math.max(bodyTable.offsetWidth + scrollBarWidth, oElement.clientWidth));
	
		// go through each cell in the head and resize
		var headCells = headTable.rows[0].cells;
		var bodyCells = bodyTable.rows[0].cells;
		var footerCells = footerTable.rows[0].cells;
		
		for (var i = 0; i < bodyCells.length; i++)
		{
			//var width = Math.max(bodyCells[i].offsetWidth,Math.max(headCells[i].offsetWidth,footerCells[i].offsetWidth));	
			if (headCells[i] != null && footerCells[i] != null)
			{
				headCells[i].style.width = bodyCells[i].offsetWidth;
				footerCells[i].style.width = bodyCells[i].offsetWidth;
			}
		}
	
		body.style.height = Math.max(0, oElement.clientHeight - head.offsetHeight - footer.offsetHeight);	
	
		//scroll row into view,only in case of resize,not in case a row was clicked
		if (event != null && event.type != "click")
		{
			initSelectedRow(oElement.model);
		}		
	}
}

/****************illegal flow error***********************************/
function closeWindowOnLoad(message)
{
	if (window.opener != null || window.dialogArguments != null)
	{
		document.write("");
		alert(message);
		self.close();
	}
}

function confirmModal(messages,flowPath,flowState,eventName)
{
	var paramArray = new Array();
	paramArray["messages"] = messages;
	var isConfirm = window.showModalDialog("html/UIConfirm.html",paramArray,"");
	if (isConfirm)
	{
		sendEvent(eventName,flowPath,flowState,"");
	}
}

function checkError()
{
	var start = document.body.innerHTML.indexOf(ERROR_AREA_START_SIGN);
	if (start != -1)
	{
		var end = document.body.innerHTML.indexOf(ERROR_AREA_END_SIGN);
		document.body.innerHTML = document.body.innerHTML.substring(start + ERROR_AREA_START_SIGN.length,end);
	}
}

function checkWarnings(lastEventName,lastFlowState,lastFlowPath)
{
	var parameters = new Array();
	parameters["lastEventName"] = lastEventName;
	parameters["lastFlowState"] = lastFlowState;
	parameters["lastFlowPath"] = lastFlowPath;
	parameters["parentWindow"] = window;
	window.showModalDialog("html/UIConfirmWarnings.html",parameters,"dialogHeight=300px;dialogWidth=500px;scroll=no;status=no");
}

function checkPopupWarningMessages()
{
	if ((opener || window.dialogArguments != null && window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW] != null))
	{
		if (opener != null)
		{
			opener.document.body.innerHTML = document.body.innerHTML;
			opener.init();
		}
		else if (window.dialogArguments != null && window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW] != null)
		{
			var parentWindow = window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW];
			parentWindow.document.body.innerHTML = document.body.innerHTML;
			parentWindow.init();	
		}
		self.close();
	}
}

/***************css adjustments**************/
function adjustCss(res800)
{
	var suffix = window.screen.width == 1024 ? "" : res800;
	var css = document.all.tags("link");
	if (css != null)
	{
		var length = css.length;
		for (index = 0;index < length;index++)
		{
			var cssIndex = css[index].href.lastIndexOf(".css");
			css[index].href =  css[index].href.substring(0,cssIndex) + suffix + ".css";
		}
	}
}