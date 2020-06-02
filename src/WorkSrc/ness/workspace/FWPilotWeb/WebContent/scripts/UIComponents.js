/*****functions for creating and handling form parameters*****/
var srcFormName;
var outputParameters;
var callBackFunction;
var callBackEvent;
var flowPath;
var flowState;

/*function which is called by the formParameters tag,from the main window*/
function createFormParameters(modelId,srcFormName,input,output,eventName,flowState,flowPath,action,openDialog,dialogParams,callBackFunction,callBackEvent)
{
	/*get source form*/
	var srcForm = document.all(srcFormName);
	if (srcForm == null) return;
	if (action == " ") 
	{
		action = srcForm.action;
	}

	/*create new form*/
	var newForm = document.all.formParameters;
	if (newForm != null) {
		document.removeChild(newForm);
	}
	newForm = document.createElement("form");	
	document.appendChild(newForm);
	
	/*get the input fields of the source form*/
	var inputFields = tokenizer(input,SEPERATOR);	
	for (index = 0;index < inputFields.length;index++) 
	{
		var token = inputFields[index];
		var srcFieldName = token.substring(0,token.indexOf(SEPERATOR_MINUS));
		var newFieldName = token.substring(token.indexOf(SEPERATOR_MINUS) + 1);
		/*append field to new form*/
		var srcField = srcForm.elements[srcFieldName];
		if (srcField != null) 
		{
			copyFormElement(newForm,srcField,newFieldName);
		}
	}
	
	/*append model information,if model for formParameters exists*/
	if (modelId != "" && modelId != " ")
	{
		formParametersEvent(modelId);
		copyFormElement(newForm,srcForm.elements[modelId],srcForm.elements[modelId].name);
	}
	
	/*set form's other properties*/
	newForm.srcFormName = srcFormName;
	newForm.outputParameters = output;
	newForm.callBackFunction = callBackFunction;
	newForm.callBackEvent = callBackEvent;
	newForm.originalFlowPath = flowPath;
	newForm.originalFlowState = flowState;
	newForm.id = FORM_PARAMETERS_ID;
	newForm.method = HTML_VALUE_POST;
	newForm.target = new Date().getMilliseconds();
	newForm.action = action;
	
	/*open in modal dialog*/
	if (openDialog == "true")
	{
		var arr = new Array();
		arr[DIALOG_ARGUEMENT_SOURCE_FORM] = newForm;
		arr["srcForm"] = srcForm; 
		arr["target"] = window.name;
		arr[DIALOG_ARGUEMENT_PARENT_WINDOW] = window;
		arr[FORM_FIELD_EVENT_NAME] = eventName;
		arr[FORM_FIELD_FLOW_ID] = flowPath;
		arr[FORM_FIELD_FLOW_STATE] = flowState;
		arr[FORM_FIELD_EVENT_CHECK_WARNINGS] = "1";
		window.showModalDialog(DIALOG_FILENAME,arr,dialogParams);	
	}
	/*open in popup*/
	else
	{
		if (isDialogWindow())
		{
			alert(GENERAL_MESSAGES[GENERAL_MESSAGE_POPUP_NOT_LEGAL]);
			return;
		}
		
		/*set the framework variables*/
		newForm.appendChild(getHidden(FORM_FIELD_EVENT_NAME,eventName));
		newForm.appendChild(getHidden(FORM_FIELD_FLOW_ID,flowPath));
		newForm.appendChild(getHidden(FORM_FIELD_FLOW_STATE,flowState));
		newForm.appendChild(getHidden(FORM_FIELD_EVENT_CHECK_WARNINGS,"1"));
		window.open('',newForm.target,dialogParams);
		newForm.submit();
	}	
}

function setSrcFormElementValue(srcFormElementId,value,isFireEvent)
{
	if (srcFormName != null)
	{
		if (opener != null)
		{
			var srcForm = opener.document.all(srcFormName);
			var srcWindow = opener;
		}
		else if (dialogArguments != null && dialogArguments[0] != null)
		{
			var srcForm = dialogArguments["srcForm"];
			var srcWindow = dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW];
		}	
		if (srcForm	!= null)
		{
			var srcFormElement = srcForm[srcFormElementId + LIST_SUFFIX];
			 
			if (srcFormElement != null)
			{
				//select
				if (srcFormElement.type == "select-one")
				{
					srcFormElement.value = value;
					if (isFireEvent)
					{
						srcFormElement.fireEvent(HTML_EVENT_ONCHANGE);
					}
					else
					{
						srcWindow.listEvent(srcFormElementId,srcFormElement,false,false);
					}
				}				
			}
			else
			{
				srcFormElement = srcForm[srcFormElementId + SELECTION_SUFFIX];
				//checkbox or radio
				if (srcFormElement != null)
				{
					var length = srcFormElement.length;	
					var isMultiple = (value.length != null);

					//only one checkbox (or radio)		
					if (length == null)
					{
						var type = srcFormElement.type;
						//check value
						if (srcFormElement.value == value)
						{
							srcFormElement.checked = true;
							srcWindow.selectionEvent(srcFormElementId,srcFormElement,type == "checkbox");
						}
						//uncheck value - only in checkbox!!!
						else if (type == HTML_VALUE_CHECKBOX)
						{
							srcFormElement.checked = false;
							srcWindow.selectionEvent(srcFormElementId,srcFormElement,true);
						}
					}
					
					//more than one checkbox (or radio)
					else
					{
						var type = srcFormElement[0].type;
						var isChecked = false;
						
						for (index = 0;index < srcFormElement.length;index++)
						{
							if (srcFormElement[index].value == value)
							{
								srcFormElement[index].checked = true;	
								isChecked = true;
							}
							else if (type == HTML_VALUE_RADIO) 
							{
								srcFormElement[index].checked = false;
							}
						}
						
						//if empty
						if (!isChecked)
						{
							//check the first radio button
							if (type == HTML_VALUE_RADIO)
							{
								srcFormElement[0].checked = true;
							}
							//uncheck all in checkbox
							else
							{
								for (index = 0;index < srcFormElement.length;index++)
								{
									srcFormElement[index].checked = false;
								}
							}
						}
						srcWindow.selectionEvent(srcFormElementId,srcFormElement,type == HTML_VALUE_CHECKBOX);
					}
				}
				//text or hidden
				else
				{
					srcFormElement = srcForm[srcFormElementId];
				}				
			}
		}	
	}
}

/*function which is called by the user,from the formParameters window*/
function returnFormParameters()
{
	if (srcFormName != null && outputParameters != null)
	{
		if (opener != null)
		{
			var srcForm = opener.document.all(srcFormName);
			var srcWindow = opener;
		}
		else if (dialogArguments != null && dialogArguments[0] != null)
		{
			var srcForm = dialogArguments["srcForm"];
			var srcWindow = dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW];		
		}	
		var outputFields = tokenizer(outputParameters,SEPERATOR);
		for (index = 0;index < outputFields.length;index++) 
		{		
			var i = outputFields[index].indexOf(SEPERATOR_MINUS);
			var srcFormElement = srcForm.elements[outputFields[index].substring(i + 1)];
			var formElement = document.all(outputFields[index].substring(0,i)); 
			if (srcFormElement != null && formElement) 
			{
				srcFormElement.value = formElement.value;
			}
		}	
		if (callBackFunction != " ")
		{
			eval(callBackFunction + "()");
		}
		if (callBackEvent != null && callBackEvent != " ")
		{
			srcWindow.sendEvent(callBackEvent,flowPath,flowState,"");			
		}
		self.close();
	}	
}

/*
function which is called by the user,from the formParameters window,when
the body is loaded
*/
function initFormParameters()
{
	if (opener != null) {
		var formParameters = opener.document.all.formParameters;
	}
	else if (window.dialogArguments != null)
	{
		var formParameters = window.dialogArguments[0];
	}
	if (formParameters != null)
	{
		srcFormName = formParameters.srcFormName;
		outputParameters = formParameters.outputParameters;
		callBackFunction = formParameters.callBackFunction;
		callBackEvent = formParameters.callBackEvent;
		flowPath = formParameters.originalFlowPath;
		flowState = formParameters.originalFlowState;
	}	
}

/*****functions for handling text components*****/

/***********text area**************/
function expandTextArea(textArea,expanderTitle,expanderDialogParams,expanderApproveButtonTitle,expanderCancelButtonTitle)
{
	if (expanderDialogParams == " ")
	{
		expanderDialogParams = getTextAreaExpanderDefaultParams();
	}
	var arr = new Array();
	arr[TEXTAREA_EXPANDER_PARAM_TEXTAREA] = textArea;
	arr[TEXTAREA_EXPANDER_PARAM_DIR] = document.body.dir;
	arr[TEXTAREA_EXPANDER_PARAM_TITLE] = expanderTitle;
	arr[TEXTAREA_EXPANDER_PARAM_APPROVE_BUTTON_TITLE] = expanderApproveButtonTitle;
	arr[TEXTAREA_EXPANDER_PARAM_CANCEL_BUTTON_TITLE] = expanderCancelButtonTitle;

	var text = window.showModalDialog(TEXTAREA_EXPANDER_DIALOG_FILENAME,arr,expanderDialogParams);
	if (text != null && text != textArea.value)
	{
		textArea.value = text;
	}
}

function getTextAreaExpanderDefaultParams()
{
	return getModalDialogParamsAsString(TEXT_AREA_DIALOG_WIDTH,TEXT_AREA_DIALOG_HEIGHT,null,null,TEXT_AREA_DIALOG_SCROLL);
}

/***********text field**************/
function openCalendarPopup(textField,relativeObject)
{
	calendarPopup.document.body.dir = document.body.dir;
	calendarPopup.document.all.returnFieldName.value = textField.id;
	calendarPopup.show((document.body.dir == SYSTEM_DIR_RTL ? - 245 + relativeObject.offsetWidth : 0) , 0, 245, 205,relativeObject);
	calendarPopup.document.all.initButton.fireEvent(HTML_EVENT_ONCLICK);	
}

function onCalendarLoad(src)
{
    calendarPopup.document.write(src);	
}

function openMonthCalendarPopup(textField,relativeObject)
{
	monthCalendarPopup.document.body.dir = document.body.dir;
	monthCalendarPopup.document.all.returnFieldName.value = textField.id;
	monthCalendarPopup.show((document.body.dir == SYSTEM_DIR_RTL ? - 200 + relativeObject.offsetWidth : 0) , 0, 200, 155,relativeObject);
	monthCalendarPopup.document.all.initButton.fireEvent(HTML_EVENT_ONCLICK);	
}

function onMonthCalendarLoad(src)
{
    monthCalendarPopup.document.write(src);	
}

function numbersonly(field,decimal,negative) 
{
	var key;
	var keychar;
	if (window.event)
	   key = window.event.keyCode;
	else
	   return true;
	keychar = String.fromCharCode(key);
	
	// control keys
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) )
	   return true;
	
	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
	   return true;
	
	// decimal point jump
	else if (decimal && (keychar == ".") && field.value.indexOf(".") == -1) 
	{
	   return true;
	}
	
	//negative
	else if (negative && keychar == SEPERATOR_MINUS && field.value.length == 0)
	{
		return true;
	}
	else
	   return false;
}

/*******************list************************/

/*expand multiple list(no call to server)*/
function expandMultipleList(modelId,listObject,objectType,searchIn,searchAction,searchType,isActionSelect,isTypeSelect,dialogParams,dialogTitle,labels,isOrderButtons)
{
	var arr = new Array();
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_DIR] = document.body.dir;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_OBJECT_TYPE] = objectType;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_TYPE] = searchType;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_ACTION] = searchAction;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SEARCH_IN] = searchIn;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_ACTION_SELECT] = isActionSelect;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_TYPE_SELECT] = isTypeSelect;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_IS_ORDER_BUTTONS] = isOrderButtons;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TITLE] = dialogTitle;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_LABELS] = labels;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_MESSAGES] = L2L_MESSAGES;
	var newSrcArray = new Array();
	for (index = 0;index < listObject.srcArray.length;index++)
	{
		newSrcArray[index] = newListOption(listObject.srcArray[index]);
	}
	var newTgtArray = new Array();
	for (index = 0;index < listObject.tgtArray.length;index++)
	{
		newTgtArray[index] = newListOption(listObject.tgtArray[index]);
	}
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SRC_ARR] = newSrcArray;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR] = newTgtArray;
	var returnArr = window.showModalDialog(LIST_MULTIPLE_EXPANDER_DIALOG_FILENAME,arr,dialogParams);
	if (returnArr != null)
	{
		if (!compareListOptionsArray(listObject.tgtArray,returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR]))
		{
			listObject.srcArray = returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SRC_ARR];
			listObject.tgtArray = returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR];	
			listObject.arrayToList(listObject.tgtList,"",listObject.tgtArray,0,0,false);
			listMultipleExpandedEvent(modelId,listObject.tgtList);
		}
		return true;
	}
	return false;
}

/*expand multiple list(call to server)*/
function expandMultipleListAndSubmit(modelId,listObject,eventName,flowPath,flowState,objectType,searcIn,searchAction,searchType,isActionSelect,isTypeSelect,refreshType,dialogParams,dialogTitle,labels,isOrderButtons)
{
	if (refreshType == LIST_REFRESH_TYPE_ONCE && !listObject.isSubmit)
	{
		expandMultipleList(modelId,listObject,objectType,searcIn,searchAction,searchType,isActionSelect,isTypeSelect,dialogParams,dialogTitle,labels,isOrderButtons);
	}
	else
	{
		var arr = new Array();
		arr[DIALOG_ARGUEMENT_SOURCE_FORM] = document.forms[0];
		arr[FORM_FIELD_EVENT_NAME] = eventName;
		arr[FORM_FIELD_FLOW_ID] = flowPath;
		arr[FORM_FIELD_FLOW_STATE] = flowState;
		arr[DIALOG_ARGUEMENT_PARENT_TARGET] = window.name;
		var returnArr = window.showModalDialog(DIALOG_FILENAME,arr,dialogParams);
		if (returnArr != null)
		{
			if (!compareListOptionsArray(listObject.tgtArray,returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR]))
			{
				listObject.srcArray = returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SRC_ARR];
				listObject.tgtArray = returnArr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR];	
				listObject.arrayToList(listObject.tgtList,"",listObject.tgtArray,0,0,false);
				listMultipleExpandedEvent(modelId,listObject.tgtList);
				if (refreshType == 2)
				{
					listObject.isSubmit = false;
				}
			}
			return true;
		}
		return false;
	}
}

/*called when return from dialog*/
function expandMultipleListCallBack(listObject)
{
	var arr = new Array();
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_SRC_ARR] = listObject.srcArray;
	arr[LIST_MULTIPLE_EXPANDER_DIALOG_PARAM_TRG_ARR] = listObject.tgtArray;
	window.returnValue = arr;
	self.close();		
}

/*expand single list(no call to server)*/
function expandSingleList(modelId,listObject,textBox,objectType,searchIn,searchAction,searchType,isActionSelect,isTypeSelect,dialogParams,dialogTitle,labels)
{
	var arr = new Array();
	var originalValue = textBox.value;
	if (event.type == HTML_EVENT_KEYPRESS)
	{
		arr["searchStr"] = String.fromCharCode(event.keyCode);
	}
	arr["dir"] = document.body.dir;
	arr["objectType"] = objectType;
	arr["searchType"] = searchType;
	arr["searchAction"] = searchAction;
	arr["searchIn"] = searchIn;
	arr["isActionSelect"] = isActionSelect;
	arr["isTypeSelect"] = isTypeSelect;
	arr["title"] = dialogTitle;
	arr["labels"] = labels;
	
	var newSrcArray = new Array();
	for (index = 0;index < listObject.srcArray.length;index++)
	{
		newSrcArray[index] = newListOption(listObject.srcArray[index]);
	}
	arr["srcArr"] = newSrcArray;
	arr["title"] = dialogTitle;
	var results = window.showModalDialog(LIST_SINGLE_EXPANDER_DIALOG_FILENAME,arr,dialogParams);
	if (results != null)
	{
		textBox.value = results["value"];
		listTextBoxEvent(modelId,results["key"]);
		listObject.srcArray = arr["srcArr"];
		return (originalValue == "" && textBox.value == "") ? false : true;
	}
	else
	{
		return false;
	}
}

function expandSingleListAndSubmit(modelId,listObject,textBox,eventName,flowPath,flowState,objectType,searchIn,searchAction,searchType,isActionSelect,isTypeSelect,refreshType,dialogParams,dialogTitle,labels)
{
	if (refreshType == LIST_REFRESH_TYPE_ONCE && !listObject.isSubmit)
	{
		expandSingleList(modelId,listObject,textBox,objectType,searchIn,searchAction,searchType,isActionSelect,isTypeSelect,dialogParams,dialogTitle,labels);
	}
	else
	{
		listTextBoxSearchStrEvent(modelId);
		var arr = new Array();
		var originalValue = textBox.value;
		arr[DIALOG_ARGUEMENT_SOURCE_FORM] = document.forms[0];
		arr[FORM_FIELD_EVENT_NAME] = eventName;
		arr[FORM_FIELD_FLOW_ID] = flowPath;
		arr[FORM_FIELD_FLOW_STATE] = flowState;
		arr[DIALOG_ARGUEMENT_PARENT_TARGET] = window.name;
		var returnArr = window.showModalDialog(DIALOG_FILENAME,arr,dialogParams);
		if (returnArr != null)
		{	
			textBox.value = returnArr["value"];
			listTextBoxEvent(modelId,returnArr["key"]);
			listObject.srcArray = returnArr["srcArr"];	
			listObject.isSubmit = false;
			return (originalValue == "" && textBox.value == "") ? false : true;
		}
		else
		{
			return false;
		}
	}
}

/*called when return from dialog*/
function expandSingleListCallBack(listObject,cancelSelection)
{
	var arr = new Array(); 
	var srcArr = listObject.srcArray;
	if (cancelSelection)
	{
		for (index = 0;index < srcArr.length;index++)
		{
			srcArr[index].isSelected = true;
		}
		arr["srcArr"]  = srcArr;
		arr["key"] = "";
		arr["value"] = "";
		window.returnValue = arr;
	}
	else
	{
		if (listObject.srcList.selectedIndex != -1) 
		{
			for (index = 0;index < srcArr.length;index++)
			{
				if (srcArr[index].value == listObject.srcList.value) 
				{
					srcArr[index].isSelected = false;
					arr["key"] = srcArr[index].value;
					arr["value"] = srcArr[index].text;					
				}
				else
				{
					srcArr[index].isSelected = true;
				}
			}
			arr["srcArr"]  = srcArr;			
			window.returnValue = arr;
		}
	}
	self.close();
}

function cancelTextFieldSpecialKeys()
{
	var key = event.keyCode;
	//cancel Ctrl,Alt,Back space and Delete buttons
	if (key == null || key == 8 || key == 46 || event.ctrlKey || event.ctrlLeft || event.altKey || event.altLeft)
	{
		return false;
	}
	return true;
}

/**************collapsable***************************/
function openCollapse(sectionId,openImage,closeImage,imageCell)
{
	var collapsable = document.all(sectionId);
	var isImage = false;
	if (imageCell.children != null && imageCell.children[0] != null && imageCell.children[0].tagName == "IMG")
	{
		isImage = true;
	}
	if (isDisplayed(collapsable))
	{
		if (isImage)
		{
			changeImageSrc(imageCell.children[0],closeImage);
		}
		else
		{
			imageCell.innerText = closeImage
		}
		hide(collapsable);
	}
	else
	{
		if (isImage)
		{
			changeImageSrc(imageCell.children[0],openImage);
		}
		else
		{
			imageCell.innerText = openImage
		}	
		show(collapsable);	
	}
}

/**************message area expand*******************/
function expandErrorArea(errorDiv,messageButton,normalHeight,expandHeight,normalLabel,expandLabel)
{
	normalHeight += PIXEL;
	expandHeight += PIXEL;
	if (errorDiv.style.height == normalHeight)
	{
		errorDiv.style.height = expandHeight;
		messageButton.value = normalLabel;
	}
	else
	{
		errorDiv.style.height = normalHeight;
		messageButton.value = expandLabel;
	}
}

function expandErrorAreaWithImage(errorDiv,messageImage,normalHeight,expandHeight,normalImage,expandImage)
{
	normalHeight += PIXEL;
	expandHeight += PIXEL;
	if (errorDiv.style.height == normalHeight)
	{
		errorDiv.style.height = expandHeight;
		changeImageSrc(messageImage,expandImage);
	}
	else
	{
		errorDiv.style.height = normalHeight;
		changeImageSrc(messageImage,normalImage);
	}	
}

function showField(id)
{
	//first check if field is of type select
	var field = document.getElementById(id + LIST_SUFFIX);
	if (field == null)
	{
		field = document.getElementById(id);
	}	
	if (field != null && isDisplayed(field) && !field.disabled)
	{
		field.focus();
		field.scrollIntoView();
	}
}

/**************help screens******************/
function showHelpMenu(isDialog,dialogParams)
{
	if (helpIdsArray.length > 0)
	{
		var helpDiv = document.all(HELP_CONTAINER_DIV_ID);
		if (helpDiv == null)
		{
			var helpMenuHtml = getHelpMenuDivHtml();
			helpMenuHtml += getHelpMenuHtml(isDialog,dialogParams);
			helpMenuHtml += "</div>";
			document.body.insertAdjacentHTML(HTML_VALUE_AFTER_BEGIN,helpMenuHtml);
			helpDiv = document.all(HELP_CONTAINER_DIV_ID);		
		}
		else
		{
			helpDiv.innerHTML = getHelpMenuHtml(isDialog,dialogParams);
		}
		
		show(helpDiv);
		helpLinksPopup.document.body.dir = document.body.dir;
		helpLinksPopup.document.body.innerHTML = helpDiv.outerHTML;
		var relativeObject = event.srcElement;
		helpLinksPopup.show((document.body.dir == SYSTEM_DIR_RTL ? relativeObject.offsetWidth - helpDiv.offsetWidth : 0) ,relativeObject.offsetHeight,helpDiv.offsetWidth,helpDiv.offsetHeight + 2,relativeObject);	
		hide(helpDiv);
	}
}

function getHelpMenuDivHtml()
{
	return "<div id=\"" + HELP_CONTAINER_DIV_ID + "\" style=\"width:100;position:absolute;display:none;\" class=\"menu\">";
}

function getHelpMenuHtml(isDialog,dialogParams)
{
	var html = "";
	html += "<table width=\"100%\" cellspacing=\"1\" cellpadding=\"0\">"
	for (index = 0;index < helpIdsArray.length;index++)
	{
		html += "<tr><td nowrap=\"nowrap\" style=\"cursor:hand\" class=\"menuItem\" onmouseout=\"this.className='menuItem'\" onmouseover=\"this.className+= 'Over'\" ";
		html += getHelpMenuItemOnClickHtml(helpIdsArray[index].itemId,isDialog,dialogParams);
		html += ">";
		html += helpIdsArray[index].itemTitle;
		html += "</td></tr>";
	}
	html += "</table>";
	return html;
}

function getHelpMenuItemOnClickHtml(helpId,isDialog,dialogParams)
{
	return "onclick=\"parent.showHelpScreen('" + helpId + "'," + isDialog + ",'" + dialogParams + "')\"";
}

function showHelpScreen(helpId,isDialog,dialogParams)
{
	//Get the action attribute of the main form of the screen
	var mainForm = document.getElementById(FORM_NAME);
	var mainFormAction = mainForm.action;
	var newFormAction = mainFormAction.substring(0,mainFormAction.lastIndexOf("/") + 1) + HELP_FORM_ACTION; 
	var params = new HashMap();
	var helpField = MODEL_EVENT_TYPE_PROPERTY + 
					MODEL_KEY_VALUE_SEPERATOR +
					HELP_MODEL_OPEN_EVENT_TYPE + 
					MODEL_PARAM_SEPERATOR + 
					HELP_MODEL_SELECTED_ITEM_PROPERTY + 
					MODEL_KEY_VALUE_SEPERATOR + helpId; 
	params.put(HELP_PARAM_NAME_HELP_INFO,helpField);
	createNewForm(HELP_FORM_NAME,newFormAction,HTML_VALUE_POST,HELP_WINDOW_NAME,params,true,isDialog,dialogParams);
}

function onHelpLinksLoad(src)
{
	helpLinksPopup.document.write(src);
}

function updateHelpMenu(itemId,itemTitle)
{
	for (index = 0;index < helpIdsArray.length;index++)
	{
		var helpMenuItem = helpIdsArray[index];
		if (helpMenuItem.itemId == itemId)
		{
			helpMenuItem.itemTitle = itemTitle;
		}
	}
}

function HelpMenuItem(itemId,itemTitle)
{
	this.itemId = itemId;
	this.itemTitle = itemTitle;
}
/**************shortcuts**********************/
function addShortCut(key,action)
{
	if (window.shortcuts == null)
	{
		window.shortcuts = new HashMap();
	}
	window.shortcuts.put(key,action);
}

function checkBodyKeyDown()
{
	var keyCode = event.keyCode;
	var element = event.srcElement;
	var isShortCutKey = false;
	var isNotInputElement = (element.tagName != HTML_TAG_INPUT && element.tagName != HTML_TAG_TEXTAREA || (element.type != null && element.type == HTML_VALUE_BUTTON)); 
	
	//Check for shortcut that was set for this key code
	if (keyCode != null && window.shortcuts != null)
	{
		var shortCutModifiers = getShortCutModifiers();
		
		//If source element is not input element(text,textarea,select)
		//or if a modifier(Ctrl,Alt) was clicked
		if (isNotInputElement || shortCutModifiers != "" && shortCutModifiers != SHORTCUT_SHIFT_PRESSED)
		{
			if (shortCutModifiers != "")
			{
				shortCutModifiers = SEPERATOR_MINUS + shortCutModifiers;
			}
			var func = shortcuts.get(event.keyCode + shortCutModifiers);
			if (func != null)
			{
				isShortCutKey = true;
				var index = func.indexOf(SHORTCUT_COMPONENT_PREFIX); 
				if (index != -1)
				{
					var componentId = func.substring(SHORTCUT_COMPONENT_PREFIX.length);
					var component = document.all(componentId);
					if (component != null)
					{
						cancelKey(event);
						component.click();
					}
				}
				else
				{
					cancelKey(event);
					eval(func);
				}
			}		
		}
	}
	
	//Check for special keys
	if (!isShortCutKey)
	{
		checkDisabledKeys();
	}
}

function getShortCutModifiers()
{
	var shortCutModifiers = 0;
	if (event.shiftKey || event.shiftLeft)
	{
		shortCutModifiers += SHORTCUT_SHIFT_PRESSED;
	}
	if (event.ctrlKey || event.ctrlLeft)
	{
		shortCutModifiers += SHORTCUT_CTRL_PRESSED;
	}
	if (event.altKey || event.altLeft)
	{
		shortCutModifiers += SHORTCUT_ALT_PRESSED;
	}	
	return (shortCutModifiers == 0 ? "" : shortCutModifiers);
}

function checkDisabledKeys() 
{
	var element = event.srcElement;
	if (event.keyCode == 8) 
	{
		if (element.readOnly || element.type != HTML_VALUE_TEXT && element.type != HTML_VALUE_PASSWORD && element.tagName != HTML_TAG_TEXTAREA)
		{
			//When backspace is pressed but not in form element
			cancelKey(event);
		}
    }
    else if (event.keyCode == 116) 
    {
        // When F5 is pressed
        cancelKey(event);
    }
    else if (event.ctrlKey && (event.keyCode == 78 || event.keyCode == 82)) 
    {
    	// When ctrl is pressed with R or N
    	cancelKey(event);
    }
    
}

function cancelKey(event) 
{
	if (event.preventDefault) 
	{
	    event.preventDefault();
	    return false;
	}
	else 
	{
		event.keyCode = 0;
		event.returnValue = false;
	}
}