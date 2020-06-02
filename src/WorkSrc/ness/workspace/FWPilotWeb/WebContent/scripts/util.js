/*****************RegExp utility*******************/
function startWith(sourceString,findString)
{
	return match(sourceString,"^" + findString);
}

function endWith(sourceString,findString)
{
	return match(sourceString,findString + "$");
}

function match(sourceString,regExpPattern)
{
	return new RegExp(regExpPattern).test(sourceString);
}

/*****************general utility functions******************/
function getClipboardData()
{
	return window.clipboardData.getData('Text');
}

function changeClassName(obj,className)
{
	var classSeperator = obj.className.indexOf(" ");
	if (classSeperator == -1)
	{
		obj.className += " " + className;	
	}
	else
	{
		obj.className = obj.className.substring(0,classSeperator);
	}	
}

function hide(obj)
{
	if (obj != null)
		obj.style.display = "none";
}

function show(obj)
{
	//obj.style.display = "inline";
	if (obj != null)
		obj.style.display = "";
}

function showHide(obj)
{
	if (isDisplayed(obj)) hide(obj);
	else show(obj);
}

function setVisible(obj)
{
	obj.style.visibility = "visible";	
}

function setHidden(obj)
{
	obj.style.visibility = "hidden";	
}

function isDisplayed(obj)
{
	return obj.style.display != "none";
}

function isVisible(obj)
{
	return obj.style.visibility != "hidden";
}

function isWarningsFound()
{
	var form = document.forms[0];
	var warningField = form[MESSAGES_MODEL_WARNING_FIELD_NAME];
	if (warningField == null)
	{
		return false;
	}
	else
	{
		return (warningField.value == "1");
	}
}

function getObjectById(id)
{
	return document.all(id);
}


function moveObj(obj,x,y)
{
	obj.style.position = "absolute";
	obj.style.left = x;
	obj.style.top = y;	
}

function getMax(arr)
{
	var max = -10000;
	var l = arr.length;
	for (j = 0;j < l;j++) {
		if (arr[j] != null && arr[j] > max) max = arr[j];
	}
	return max;
}

function tokenizer(str,chr)
{
	var counter = 0;
	var tokens = new Array();
	if (str == "")
	{
		return tokens;
	}	
	var tokensNumber = 0;
	var index = str.indexOf(chr);
	if (index == -1) {
		tokens[0] = str;
	}
	else {
		var lastIndex = -1;
		while (index != -1) {
			tokens[counter++] = str.substring(lastIndex + 1,index);
			lastIndex = index;
			index = str.indexOf(chr,lastIndex + 1);
		}
		if (lastIndex < str.length) {
			tokens[counter] = str.substring(lastIndex + 1);
		}
	}	
	return tokens;
}

function closeDialogWindowWithEsc()
{
	if (window.dialogArguments != null)
	{
		if (window.event.keyCode == 27) 
		{
			window.close();
		}
	}
}

function isDialogWindow()
{
	return window.dialogArguments != null && window.dialogArguments[DIALOG_ARGUEMENT_PARENT_WINDOW] != null;
}

function initModal()
{
	if (window.dialogArguments != null && window.dialogArguments["lastTop"] != null)
	{
		var marginTop = window.dialogArguments["marginTop"];
		var marginLeft = window.dialogArguments["marginLeft"]
		if (marginTop == null)
		{
			marginTop = getDialogHeight() - document.body.clientHeight - (screenTop - getDialogTop());
			window.dialogArguments["marginTop"] = marginTop;
			marginLeft = getDialogWidth() - document.body.clientWidth - (screenLeft - getDialogLeft());
			window.dialogArguments["marginLeft"] = marginLeft;
		}
		dialogTop = dialogArguments["lastTop"] - getDialogHeight() + document.body.clientHeight + marginTop;
		dialogLeft = dialogArguments["lastLeft"] - getDialogWidth() + document.body.clientWidth + marginLeft;	
	}	
}

function getModalDialogParamsAsString(width,height,left,top,scroll)
{
	var dialogParams = "";
	if (width != null)
	{
		dialogParams += DIALOG_PARAM_WIDTH + width + ";";
	}
	if (height != null)
	{
		dialogParams += DIALOG_PARAM_HEIGHT + height + ";";
	}
	if (top != null)
	{
		dialogParams += DIALOG_PARAM_TOP + top + ";";
	}
	if (left != null)
	{
		dialogParams += DIALOG_PARAM_LEFT + left + ";";
	}
	if (scroll != null)
	{
		dialogParams += DIALOG_PARAM_SCROLL + scroll + ";";
	}
	return dialogParams	
}

function getDialogHeight()
{
	var index = dialogHeight.indexOf("px");
	return new Number(dialogHeight.substring(0,index));
}

function getDialogTop()
{
	var index = dialogTop.indexOf("px");
	return new Number(dialogTop.substring(0,index));
}

function getDialogWidth()
{
	var index = dialogWidth.indexOf("px");
	return new Number(dialogWidth.substring(0,index));
}

function getDialogLeft()
{
	var index = dialogLeft.indexOf("px");
	return new Number(dialogLeft.substring(0,index));
}

function getDialogFixedTitle(dialogTitle)
{
	/*if (window.dialogArguments != null && dialogWidth != null)
	{
		var width = dialogWidth.substring(0,dialogWidth.indexOf("px"));
		var spaces = "";
		for (i = 0;i <  width;i = i+ 1)
		{
			spaces += "&nbsp;"
		}
		return dialogTitle + spaces;
	}
	else
	{
		return dialogTitle;
	}*/
	return dialogTitle;
}

function cmGetX (obj)
{
	var x = 0;

	do
	{
		x += obj.offsetLeft;
		obj = obj.offsetParent;
	}
	while (obj);
	return x;
}

function cmGetXAt (obj, elm)
{
	var x = 0;

	while (obj && obj != elm)
	{
		x += obj.offsetLeft;
		obj = obj.offsetParent;
	}
	return x;
}

function cmGetY (obj)
{
	var y = 0;
	do
	{
		y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	while (obj);
	return y;
}

function cmGetYAt (obj, elm)
{
	var y = 0;

	while (obj && obj != elm)
	{
		y += obj.offsetTop;
		obj = obj.offsetParent;
	}
	return y;
}

/*****************positioning utility functions******************/
function getModalDialogCoordinates(dialogParams)
{
	var dWidth = 0;
	var dHeight = 0;
	var widthFirstIndex = dialogParams.indexOf("dialogWidth:");
	if  (widthFirstIndex != -1)
	{
		widthLastIndex = dialogParams.indexOf("px",widthFirstIndex + "dialogWidth:".length);
		if (widthLastIndex != -1)
		{
			dWidth = dialogParams.substring(widthFirstIndex + "dialogWidth:".length,widthLastIndex);
		}
	}
	var heightFirstIndex = dialogParams.indexOf("dialogHeight:");
	if  (heightFirstIndex != -1)
	{
		heightLastIndex = dialogParams.indexOf("px",heightFirstIndex + "dialogHeight:".length);
		if (heightLastIndex != -1)
		{
			dHeight = dialogParams.substring(heightFirstIndex + "dialogHeight:".length,heightLastIndex);
		}
	}
	return (getPopupElementCoordinates(dWidth,dHeight));
	
}
function getPopupElementCoordinates(elementWidth,elementHeight)
{
	var hOverlap = -5;
	var vOverlap = 5;
	var hScrollBarWidth = document.body.offsetWidth - document.body.clientWidth;
	var vScrollBarWidth = document.body.offsetHeight - document.body.clientHeight;
	var gap = document.body.scrollWidth - document.body.clientWidth - document.body.scrollLeft;
	var realX = event.clientX - elementWidth + hOverlap - hScrollBarWidth;
	var realY = event.clientY + elementHeight - vOverlap;
	
	var x = event.x - hScrollBarWidth - gap - elementWidth + hOverlap;
	x -= (realX < 0) ? realX : 0;
	var y = event.clientY + document.body.scrollTop - vOverlap;
	y -= (realY > document.body.clientHeight) ? realY - document.body.clientHeight : 0;
	return new Coordinate(x,y);
}

function Coordinate(x,y)
{
	this.x = x;
	this.y = y;
}

/*****************formatting and validation util*******************/
function formatNumeric(field,isInteger) 
{
	var key;
	var keychar;
	if (window.event)
	{
	   key = window.event.keyCode;
	}
	else
	{
	   return true;
	}
	keychar = String.fromCharCode(key);
	
	// control keys
	if ((key==null) || (key==0) || (key==8) || 
	    (key==9) || (key==13) || (key==27) )
	   return true;
	
	// numbers
	else if ((("0123456789").indexOf(keychar) > -1))
	{
		return true;
	}
	// decimal point jump
	else if (!isInteger && keychar == "." && field.value.indexOf(".") == -1) 
	{
		return true;
	}
	
	//negative
	else if (keychar == "-" && field.value.indexOf("-") == -1)
	{
		return true;
	}
	
	//currency
	else if (keychar == ",")
	{
		return true;
	}
	 
	else
	{
	   return false;
	}
}

function validateNumeric(field,isInteger,message,regExpPattern,regExpMessage)
{
	if (!isNumber(field.value,isInteger))
	{
		field.scrollIntoView();
		alert(message);
	}
	else if (regExpPattern != null && regExpPattern != "null")
	{
		validateRegExp(regExpPattern,regExpMessage);
	}
}

function validateNumericOnPaste(isInteger)
{
	return isNumber(getClipboardData(),isInteger);
}

function formatDate(field)
{
	var key;
	var keychar;
	if (window.event)
	   key = window.event.keyCode;
	else
	   return true;
	keychar = String.fromCharCode(key);
	if ((("0123456789/").indexOf(keychar) > -1))	
	{
		return true;
	}
	else
	{
		return false;	
	}
}

function validateDateOnPaste(isMonth)
{
	var data = getClipboardData();
	for (index = 0;index < data.length;index++)
	{
		if ((("0123456789/").indexOf(data.charAt(index)) == -1))
		{
			return false;
		} 	
	}
	return true;
}

function validateDate(field,message,isMonth,regExpPattern,regExpMessage)
{
	var isValid = true;
	if (isMonth)
	{
		if (field.value != "" && !isMonthDate(field.value)) 
		{
			field.scrollIntoView();		
			alert(message);
			isValid = false;
		}		
	}
	else
	{
		if (field.value != "" && !isDate(field.value,0)) 
		{
			field.scrollIntoView();		
			alert(message);
			isValid = false;
		}
	}
	if (isValid && regExpPattern != null && regExpPattern != "null")
	{
		validateRegExp(regExpPattern,regExpMessage);
	}
}

function validateRegExp(regExpPattern,message)
{
	var textField = event.srcElement;
	if (!match(textField.value,regExpPattern))
	{
		alert(message);
		textField.scrollIntoView();	
	}
}

function isNumber(number,isInteger)
{
	var index = number.indexOf("-");
	if (index != -1 && index != 0 && index != number.length - 1)
	{
		return false;
	}
	else
	{
		index = number.indexOf(".");
		if (index != -1)
		{		
			if (isInteger)
			{
				return false;
			}
			else
			{
				var currencyIndex = number.indexOf(",");
				while (currencyIndex != -1)
				{	
					if (currencyIndex > index)
					{
						return false;
					}
					currencyIndex = number.indexOf(",",currencyIndex + 1);
				}
			}
		}
	}
	for (index = 0;index < number.length;index++)
	{
		if ((("0123456789.,").indexOf(number.charAt(index)) == -1))
		{
			return false;
		} 	
	}	
	return true;
}

/**********************************************************************/ 
/*Function name :isDigit(theDigit) */ 
/*Usage of this function :test for an digit */ 
/*Input parameter required:thedata=string for test whether is digit */ 
/*Return value :if is digit,return true */ 
/* else return false */ 
/**********************************************************************/ 
function isDigit(theDigit) 
{ 
	var digitArray = new Array('0','1','2','3','4','5','6','7','8','9'); 

	for (j = 0; j < digitArray.length; j++) 
	{
		if (theDigit == digitArray[j]) 
		{	
			return true 
		}
	} 
	return false 
} 

/*************************************************************************/ 
/*Function name :isPositiveInteger(theString) */ 
/*Usage of this function :test for an +ve integer */ 
/*Input parameter required:thedata=string for test whether is +ve integer*/ 
/*Return value :if is +ve integer,return true */ 
/* else return false */ 
/*function require :isDigit */ 
/*************************************************************************/ 
function isPositiveInteger(theString) 
{ 
	var theData = new String(theString) 
	if (!isDigit(theData.charAt(0))) 
	{ 
		if (!(theData.charAt(0)== '+')) 
		{		
			return false
		} 
	}
	for (var i = 1; i < theData.length; i++)
	{ 
		if (!isDigit(theData.charAt(i))) 
		{ 
			return false 
		}
	}
	return true 
} 
/**********************************************************************/ 
/*Function name :isDate(s,f) */ 
/*Usage of this function :To check s is a valid format */ 
/*Input parameter required:s=input string */ 
/* f=input string format */ 
/* =1,in mm/dd/yyyy format */ 
/* else in dd/mm/yyyy */ 
/*Return value :if is a valid date return 1 */ 
/* else return 0 */ 
/*Function required :isPositiveInteger() */ 
/**********************************************************************/ 
function isDate(date,dateMask) 
{
	var isDateOk = true;	
	var a1=date.split("/"); 
	var a2=date.split("-"); 
	if ((a1.length!=3) && (a2.length!=3)) 
	{ 
		isDateOk=false; 
	} 
	else 
	{
		if (a1.length==3)
		{ 
			var na=a1;
		} 
		if (a2.length==3)
		{ 
			var na=a2;
		} 
		if (isPositiveInteger(na[0]) && isPositiveInteger(na[1]) && isPositiveInteger(na[2])) 
		{ 
			if (dateMask==1) 
			{
				var d=na[1],m=na[0]; 
			} 
			else 
			{
				var d=na[0],m=na[1]; 
			} 
			var y=na[2]; 
			if (((isDateOk) && (y<1000)||y.length>4)) 
			{
				isDateOk=false 
			}
			if (isDateOk) 
			{ 
				v = new Date(m + "/" + d +"/" + y); 
				if (v.getMonth()!=m-1)
				{ 
					isDateOk=false; 
				} 
			}	
		} 
		else 
		{ 
			isDateOk=false; 
		} 
	} 
	return isDateOk 
}

function isMonthDate(date) 
{
	var isDateOk = true;
	
	var a1 = date.split("/"); 
	var a2 = date.split("-"); 
	if ((a1.length!=2) && (a2.length!=2)) 
	{ 
		isDateOk=false; 	
	} 
	else
	{
		if (a1.length == 2)
		{ 
			var na=a1;
		} 
		if (a2.length == 2)
		{ 
			var na=a2;
		} 
		if (isPositiveInteger(na[0]) && isPositiveInteger(na[1])) 
		{ 
			var m = na[0]; 
			var y = na[1]; 
			if (((isDateOk) && (y<1000)||y.length>4)) 
			{
				isDateOk=false 
			}
			if (isDateOk) 
			{ 
				v = new Date(m + "/" + 1 +"/" + y); 
				if (v.getMonth()!= m-1)
				{ 
					isDateOk=false; 
				} 
			}	
		} 
		else 
		{ 
			isDateOk=false; 
		} 
	} 
	return isDateOk 
}

/*****************list and combo utility functions******************/
function addToSelect(select,option)
{
	select.options[select.options.length] = option;
}

function fillArray(select)
{
	arr = new Array();
	var optionsLength = select.options.length;
	for (index = 0;index < optionsLength;index++) {
		arr[index] = new SelectObject(select.options[index].value,select.options[index].text);
	}
	return arr;
}

function fillSelect(arr,select)
{
	for (index = 0;index < arr.length;index++) {
		select.add(new Option(arr[index].text,arr[index].value));
	}
}

function removeAll(select)
{
	var length = select.length;
	for (i = 0;i < length;i++) {
		select.remove(0);
	}
}

function selectAll(select)
{
    if (select != null) {
		for (index = 0;index < select.length;index++) {
			select[index].selected = true;
		}
    }
}

function copySelect(src,target)
{
	var length = src.options.length;
	for (i = 0;i < length;i++)
	{
		target.add(new Option(src.options[i].text,src.options[i].value));	
	}
}

function getSelectedAsKeyValue(select,isSelectAll)
{
	var length = select.options.length;
	var options = select.options;
	var str = "";
	for (i = 0;i < length;i++)
	{
		if (isSelectAll || options[i].selected)
		{
			if (options[i].value != "")
			{
				str += options[i].value + "-" + options[i].text + "|";
			}
		}
	}
	return str;
}

function getSelectedKeys(select,isSelectAll,keySeparator)
{
	var length = select.options.length;
	var options = select.options;
	var str = "";
	for (i = 0;i < length;i++)
	{
		if (isSelectAll || options[i].selected)
		{
			str += options[i].value + keySeparator;
		}
	}	
	if (str.length > 0 && str.charAt(str.length - 1) == keySeparator)
	{
		str = str.substring(0,str.length - 1);
	}
	return str;
}

function SelectObject(value,text)
{
	this.value = value;
	this.text = text;	
}

/*****************checkbox && radio utility functions******************/
function check(checkbox)
{
	checkbox.checked = true;	
}

function uncheck(checkbox)
{
	checkbox.checked = false;	
}

function countChecked(checkBoxArray)
{
	if (checkBoxArray == null) {
		return 0;
	}
	else if (checkBoxArray.length == null) {
		return 1;
	}
	var counter = 0;
	for (i = 0 ;i < checkBoxArray.length;i++) {
		if (checkBoxArray[i].checked) counter++;
	}
	return counter;
}

function getCheckedValues(checkbox,keySeparator)
{
	if (checkbox == null)
	{
		return "";
	}
	else 
	{
		var length = checkbox.length;
		if (length == null)
		{
			if (checkbox.checked) return checkbox.value;
			else return "";
		}
		else
		{
			var str = "";
			for (index = 0;index < length;index++)
			{
				if (checkbox[index].checked)
				{
					str += checkbox[index].value + keySeparator;
				}
			}
			if (str.length > 0 && str.charAt(str.length - 1) == keySeparator)
			{
				str = str.substring(0,str.length - 1);
			}
			return str;
		}	
	}
	
}

function getCheckedValue(radioArray)
{
	if (radioArray == null)
	{
		return "";
	}
	else
	{
		var length = radioArray.length;
		if (length == null)
		{
			return radioArray.value;
		}
		else
		{
			for (index = 0;index < length;index++)
			{
				if (radioArray[index].checked)
				{
					return radioArray[index].value;
				}
			}
		}
	}
}

function radioClick()
{
	var radio = event.srcElement;
	var radioGroup = document.all(radio.id);
	if (radioGroup.length == null)
	{
		radio.checked = !radio.checked;
	}
	else
	{
		for (index = 0;index < radioGroup.length;index++)
		{
			radioGroup[index].checked = (radioGroup[index] == radio);
		}				
	}
}

/*****************images*****************/
function changeImageSrc(img,newImg)
{
	var index = img.src.lastIndexOf("/");
	var imgSrc = img.src.substring(index + 1);
	img.src = img.src.substring(0,index + 1) + newImg;
}

/*****************forms utility functions******************/
function addField(form,name,value)
{
    form.appendChild(getHidden(name,value));
}

function setField(form,name,value)
{
	var formElement = form.elements[name];	
	if (formElement != null)
	{
		formElement.value = value;			
	}
	else
	{
		addField(form,name,value);	
	}
}

function getHidden(name,value)
{
    var input = document.createElement("input");
    input.type = "hidden";
    input.name  = name;
    input.value = value;
	return input;
}

function getHiddenHtml(name,value)
{
	return "<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\">";
}

function isFieldExist(form,name)
{
	for (i=0;i<form.elements.length;i++) {
		if (form.elements[i].name == name) return i;	
	}
	return -1;
}

function copyForms(srcForm,srcFormParams,targetFormAction)
{
	var targetForm = document.createElement("form");
	document.body.appendChild(targetForm);
	if (targetFormAction == "") targetFormAction = srcForm.action;
	targetForm.method = "post";
	targetForm.target = "newWin";
	targetForm.action = targetFormAction;	
	for (index = 0;index < srcFormParams.length;index++) {
		var srcFormElement = srcForm.elements[srcFormParams[index]];
		if (srcFormElement != null) {
			var input = document.createElement("input");
			input.type = "hidden";
			input.name = srcFormElement.name;
			input.value = srcFormElement.value;
			targetForm.appendChild(input);
		}
	}
	window.open("","newWin","height=300,width=300,left=300,top=100");
	targetForm.submit();
}

function copyFormElement(newForm,srcFormElement,newFormName)
{
	if (srcFormElement.type == "radio" || srcFormElement.type == "checkbox")
	{
		if (srcFormElement.checked)
		{
			newForm.appendChild(getHidden(newFormName,srcFormElement.value));
		}
	}	
	else if (srcFormElement.type == "select-multiple")
	{
		var options = srcFormElement.options;
		var optionsLength = options.length;
		for (j = 0;j < optionsLength;j++)
		{
			if (options[j].selected)
			{
				newForm.appendChild(getHidden(newFormName,options[j].value));
			}
		}
	}
	else
	{
		newForm.appendChild(getHidden(newFormName,srcFormElement.value));
	}	 
}

/**
 * Function that creates a new form and copies the hidden fields of 
 * another form into the new form.
 * parameters : 
 * form - the original form whose hidden fields are copied into the new form.
 * submitNewForm - boolean parameter,if true the newely created form is submitted.	
 */
function createNewForm(form,submitNewForm)
{
	var newForm = document.createElement("form");	
	
	//Copy attributes
	newForm.action = form.action;
	newForm.method = form.method;
	newForm.target = form.target;
	
	//Appends new form to document
	document.appendChild(newForm);
 	
 	//Copy only fields with names
 	var elements = form.elements;
 	var length = elements.length;
 	for (index = 0;index < length;index++)
 	{
 		var srcElement = elements[index];
		if (srcElement.name != null && srcElement.name != "")
		{
			copyFormElement(newForm,srcElement,srcElement.name);
 		}
 	}
 	
 	//Submit new form if submitNewForm=true
	if (submitNewForm)
	{
		newForm.submit();
	}
}

/*******************change target of a link in a popup or a dialog************************/
function closePopup()
{
	var a = window.event.srcElement;
	if (a != null && a.tagName == "A")
	{
		if (window.dialogArguments != null && window.dialogArguments["target"] != null)
		{
			a.target = window.dialogArguments["target"];
			self.close();	
		} 
		else if (opener != null)
		{
			a.target = opener.name;
			self.close();
		}
	}
}

/*******************user utility functions**********************/
function enableComponent(componentId)
{
	var component = getUIComponentWrapper(componentId);
	if 	(component != null && component.enableNotAuthorized == null)
	{
		enableComponentWrapper(component);		
	}
}

function enableComponentWrapper(component)
{
	component.disabled = false;  
	enableComponentCss(component);  
	var children = component.children;
	for (var i = 0, len = children.length; i < len; i++)
	{
		if (children[i].disabled)
		{
	   		enableComponentWrapper(children[i]);
		}
	}
}

function enableComponentCss(component)
{
	//If the component is image,delete the "Disabled" suffix from
	//its src attribute  
	if (component.tagName == "IMG")
	{
		var index = component.src.indexOf(CSS_SUFFIX_DISABLED);
		component.src = component.src.substring(0,index) + component.src.substring(index + CSS_SUFFIX_DISABLED.length);
	}
	//If the component is button,delete the "Disabled" suffix from
	//its className attribute
	else if (component.tagName == "INPUT" && component.type == "button")
	{
		
	}
}

function disableComponent(componentId)
{
	var component = getUIComponentWrapper(componentId);
	if (component != null)
	{
		disableComponentWrapper(component);
	}
}

function disableComponentWrapper(component)
{
	component.disabled = true;    
	disableComponentCss(component);
	var children = component.children;
	for (var i = 0, len = children.length; i < len; i++)
	{
		if (!children[i].disabled)
		{
			disableComponentWrapper(children[i]);
		}
	}
}

function disableComponentCss(component)
{
	//If the component is image,add the "Disabled" suffix to
	//its src attribute  
	if (component.tagName == "IMG")
	{
		var index = component.src.indexOf(".");
		component.src = component.src.substring(0,index) + CSS_SUFFIX_DISABLED + component.src.substring(index);
	}
	//If the component is button,delete the "Disabled" suffix from
	//its className attribute
	else if (component.tagName == "INPUT" && component.type == "button")
	{
		
	}	
}

function showComponent(componentId)
{
	var component = getUIComponentWrapper(componentId);
	if 	(component != null)
	{
			show(component);
	}		
}

function hideComponent(componentId)
{
	var component = getUIComponentWrapper(componentId);
	if 	(component != null)
	{
		hide(component);
	}	
}

function getUIComponent(componentId)
{
	//search for list component
	component = document.all(componentId + LIST_SUFFIX);
	if (component == null)
	{
		//search for selection component
		component = document.all(componentId + SELECTION_SUFFIX);
		if (component == null)
		{
			//search for text component
			component = document.all(componentId);
		}
	}
	return component;	
}

function getUIComponentWrapper(componentId)
{
	//search for list component's table wrapper
	var component = document.getElementById(componentId + LIST_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
	if (component == null)
	{
		//search for list component
		component = document.getElementById(componentId + LIST_SUFFIX);
		if (component == null)
		{
			//search for selection component's table wrapper
			component = document.getElementById(componentId + SELECTION_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
			if (component == null)
			{
				//search for text component's table wrapper
				component = document.getElementById(componentId + COMPLEX_COMPONENT_SUFFIX);
				if (component == null)
				{
					//search for tree component
					component = document.getElementById(componentId + TREE_TABLE_SUFFIX);
					if (component == null)
					{
						//search for text component
						component = document.getElementById(componentId);
					}
				}			
			}
		}
	}
	return component;
}

function getTextComponentData(componentId)
{
	component = document.all(componentId);
	if (component != null)
	{
		return (component.value != null ? component.value : "");
	}
	else
	{
		return TEXT_FIELD_NOT_AVAILABLE;
	}
}

function setComponentFocus(field)
{
	if (field != null && isDisplayed(field) && !field.disabled)
	{
		field.focus();
	}	
}