// ==========================================================================
// ListToList.js
// 
// List To List UI Widget 1.0 by Gil Tabakman
// ==========================================================================

// ==========================================================================
// The ListToList Object Constructor
// Arguments:
//		srcArray - Array of options to choose from
//		tgtArray - Array of selected options which are also in srcArray
//		ctlPrefix - The prefix that distinguish the specific ListToList object
// Returns:
//		A new ListToList object
// ==========================================================================
function ListToList(srcArray,tgtArray,ctlPrefix,objectType,searchIn,searchType,searchAction)
{
	var MSG_PLACE_HOLDER = '^';

	//variables
	this.ctlPrefix = ctlPrefix;
	this.objectType = objectType;
	this.searchIn = searchIn;
	this.sourceSearchType = searchType;
	this.sourceSearchAction = searchAction;
	this.targetSearchType = searchType;
	this.targetSearchAction = searchAction;	
	
	this.isSubmit = true;
	this.srcArray = srcArray;
	this.tgtArray = tgtArray;
	this.prvSrcText = null;
	this.prvTgtText = null;
	
	//functions
	this.setSearchType = setSearchType;
	this.setSearchAction = setSearchAction;
	this.drawListToList = drawListToList;
	this.setFilter = setFilter;
	this.setOrder = setOrder;
	this.handleKeys = handleKeys;
	this.bindControls = bindControls;
	this.moveFromSrcToTgt = moveFromSrcToTgt;
	this.moveFromTgtToSrc = moveFromTgtToSrc;
	this.arrayToList = arrayToList;
	this.doEvents = doEvents;

	function isSourceFilled()
	{
		return (objectType == JS_L2L_OBJECT_TYPE_ALL || objectType == JS_L2L_OBJECT_TYPE_SRC);
	}
	function isTargetFilled()
	{
		return (objectType == JS_L2L_OBJECT_TYPE_ALL || objectType == JS_L2L_OBJECT_TYPE_TRG);
	}
	

	// ==========================================================================
	// Bind the Inputs drawn in the HTML to the ListToList Object.
	// Arguments: none
	// Returns: none
	// ==========================================================================

	function bindControls()
	{
		if (isSourceFilled())
		{
			this.srcText = document.getElementById(this.ctlPrefix + 'SrcText');
			this.srcSearchAction = document.getElementById(this.ctlPrefix + 'SrcSearchAction');
			this.srcSearchType = document.getElementById(this.ctlPrefix + 'SrcSearchType');
			this.srcList = document.getElementById(this.ctlPrefix + 'SrcList');
			this.srcToTgt = document.getElementById(this.ctlPrefix + 'SrcToTrg');
			this.srcToTgtAll = document.getElementById(this.ctlPrefix + 'SrcToTrgAll');
			this.srcUp = document.getElementById(this.ctlPrefix + 'SrcUp');
			this.srcDown = document.getElementById(this.ctlPrefix + 'SrcDown');
		}
		if (isTargetFilled())
		{
			this.tgtText = document.getElementById(this.ctlPrefix + 'TrgText');
			this.tgtSearchAction = document.getElementById(this.ctlPrefix + 'TrgSearchAction');
			this.tgtSearchType = document.getElementById(this.ctlPrefix + 'TrgSearchType');
			this.tgtList = document.getElementById(this.ctlPrefix + 'TrgList');
			this.tgtToSrc = document.getElementById(this.ctlPrefix + 'TrgToSrc');
			this.tgtToSrcAll = document.getElementById(this.ctlPrefix + 'TrgToSrcAll');
			this.tgtUp = document.getElementById(this.ctlPrefix + 'TrgUp');
			this.tgtDown = document.getElementById(this.ctlPrefix + 'TrgDown');
		}
	}

	// ==========================================================================
	// Update the ListToList object display.
	// Arguments:
	//		drawSrc - Wheather to update the source list display
	//		drawTgt - Wheather to update the target list display
	// Returns: none
	// ==========================================================================

	function drawListToList(drawSrc,drawTgt,filter)
	{
		if (isSourceFilled() && drawSrc)
		{
			arrayToList(this.srcList, 
						this.srcText != null ? this.srcText.value : "", 
						this.srcArray,
						this.sourceSearchAction,
						this.sourceSearchType,
						filter)
		}
		if (isTargetFilled() && drawTgt)
		{
			arrayToList(this.tgtList, 
						this.tgtText != null ? this.tgtText.value : "", 
						this.tgtArray,
						this.targetSearchAction,
						this.targetSearchType,
						filter);
		
		}
	}

	// ==========================================================================
	// Handle key events in the filter text fields above the lists.
	// Arguments:
	//		eventType - The key event type (keyup, keydown)
	// Returns: none
	// ==========================================================================

	function handleKeys(eventType) 
	{
		if(eventType == 'keydown')
		{
			if (isSourceFilled())
			{
				if (this.srcText != null)
				{
					this.prvSrcText = this.srcText.value;
				}
			}
			if (isTargetFilled())
			{
				if (this.tgtText != null)
				{
					this.prvTgtText = this.tgtText.value;
				}
			}
		} 
		else if(eventType == 'keyup')
		{
			var drawSrc = false;
			if (isSourceFilled())
			{
				drawSrc = (this.srcText != null && this.prvSrcText != this.srcText.value) ? true : false;
			}
			if (isTargetFilled())
			{
				var drawTgt = (this.tgtText != null && this.prvTgtText != this.tgtText.value) ? true : false;
			}
			this.drawListToList(drawSrc,drawTgt,true);
		} 
		else 
		{
			alert(errorMessage(0));
		}
	}

	// ==========================================================================
	// Toggles between search anf filter actions
	// Arguments:
	//		filter - The Select list to toggle (src/ tgt)
	// Returns: none
	// ==========================================================================

	function setFilter(filter)
	{
		if(filter == 'src')
		{
			if (isSourceFilled())
			{
				this.drawListToList(true, false);
			}
		} 
		else if (isTargetFilled())
		{
			this.drawListToList(false, true);			
		}
	}
	
	/**
	 * Sets new searchType
	 */
	function setSearchType(searchType,type)
	{
		if (type == "src" && isSourceFilled())
		{
			this.sourceSearchType = searchType;
			this.drawListToList(true,false,true);
		}
		else if (isTargetFilled())
		{
			this.targetSearchType = searchType;
			this.drawListToList(false,true,true);
		}
	}
	
	/**
	 * Sets new searchAction
	 */
	function setSearchAction(searchAction,type)
	{
		if (type == "src" && isSourceFilled())
		{
			this.sourceSearchAction = searchAction;
			this.drawListToList(true,false,true);
		}
		else if (isTargetFilled())
		{
			this.targetSearchAction = searchAction;
			this.drawListToList(false,true,true);
		}		
	}
	

	// ==========================================================================
	// Find an option index by its value
	// Arguments:
	//		array - The array object to search in
	//		value - The value to search for
	// Returns:
	//		The index of the requested value or -1 if not found
	// ==========================================================================

	function posInArray(array, value)
	{
		for(var i=0; i<array.length; i++){
			if(value == array[i].value){
				return i;
			}
		}
		return -1;
	}

	// ==========================================================================
	// Delete a specific element from an array
	// Arguments:
	//		array - The array object to delete from
	//		n - The requested element to delete
	// Returns: none
	// ==========================================================================

	function deleteElement(array, n) 
	{
		var length = array.length;
		if (n < length && n >= 0){
			for (var i = n; i < length-1; i++){
				array[i] = array[i+1];
			}
			array.length--;
		}
	}

	// ==========================================================================
	// Inserty an element into an array after e specified element
	// Arguments:
	//		array - The array object to insert into
	//		n - The requested element to insert after
	// Returns: none
	// ==========================================================================

	function insertEmptyElement(array, n) 
	{
		var length = array.length;
		if (n >= 0 && n < length) {
			for (var i=length; i>n; i--){
				array[i] = array[i-1];
			}
			array[i] = new ListOption("","",true);
		}
	}

	// ==========================================================================
	// Switch two elements in an array
	// Arguments:
	//		array - The array object to switch elements in
	//		element1 - The 1st element to switch
	//		element2 - The 2nd element to switch
	// Returns: none
	// ==========================================================================

	function switchElements(array, element1, element2){
		var temp = array[element1];
		array[element1] = array[element2];
		array[element2] = temp;
	}

	// ==========================================================================
	// Perform the movement of options in a Select option / array
	// Arguments:
	//		side - The Select list / array to act on (src/ tgt)
	//		direction - The direction to move the selected option (up / down)
	// Returns: none
	// ==========================================================================

	function setOrder(side, direction) 
	{
		var list = (side == 'src') ? this.srcList : this.tgtList;
		var array = (side == 'src') ? this.srcArray : this.tgtArray;
		var drawSrc = (side == 'src') ? true : false;
		var selIdx = list.selectedIndex;
		var length = list.length;
		if(selIdx == -1){
			errorMessage(1);
		} else if(selIdx == 0 && direction == 'up'){
			errorMessage(2);
		} else if(selIdx == length - 1 && direction == 'down'){
			errorMessage(3);
		} else {
			var newIdx = (direction == 'up') ? selIdx - 1 : selIdx + 1;
			var src = posInArray(array, list.options(selIdx).value);
			var tgt = posInArray(array, list.options(newIdx).value);
			switchElements(array, src, tgt);
			this.drawListToList(drawSrc,!drawSrc,false);
			list.selectedIndex = newIdx;
		}
	}

	// ==========================================================================
	// Move options from the Source Select / array to the target
	// Arguments:
	//		moveAll - Wheathe to move all of the options or just the selected
	// Returns: none
	// ==========================================================================

	function moveFromSrcToTgt(moveAll)
	{
		if(this.srcList.selectedIndex == -1 && !moveAll)
		{
			errorMessage(4);
		} 
		else 
		{
			for(var i=0; i<this.srcList.options.length; i++)
			{
				option = this.srcList.options(i);
				if(moveAll || option.selected){
					idx = posInArray(this.srcArray, option.value);
					if(idx >= 0){
						this.tgtArray[this.tgtArray.length] = new ListOption(this.srcArray[idx].value,this.srcArray[idx].text,true);
						this.srcArray[idx].isSelected = false;
					}
				}
			}
			this.drawListToList(true,true,true);
		}
	}

	// ==========================================================================
	// Move options from the Target Select / array to the source
	// Arguments:
	//		moveAll - Wheathe to move all of the options or just the selected
	// Returns: none
	// ==========================================================================
	
	function moveFromTgtToSrc(moveAll)
	{
		if(this.tgtList.selectedIndex == -1 && !moveAll){
			errorMessage(5);
		} else {
			for(var i=0; i<this.tgtList.options.length; i++){
				option = this.tgtList.options(i);
				if(moveAll || option.selected){
					tgtIdx = posInArray(this.tgtArray, option.value);
					srcIdx = posInArray(this.srcArray, option.value);
					if(srcIdx >= 0)
					{
						this.srcArray[srcIdx].isSelected = true;
					}
					if(tgtIdx >= 0)
					{
						deleteElement(this.tgtArray,tgtIdx);
					}
				}
			}
			this.drawListToList(true,true,true);
		}
	}

	// ==========================================================================
	// Event handler for the ListToList object
	// Arguments:
	//		event - The original event object
	// Returns: none
	// ==========================================================================

	function doEvents(event)
	{
		var elementId = event.srcElement.id;
		var eventType = event.type;
		var elementValue = event.srcElement.value;
		
		if(elementId == this.ctlPrefix + 'SrcList'){
			this.moveFromSrcToTgt(false);
		} else if(elementId == this.ctlPrefix + 'SrcToTrgAll'){
			this.moveFromSrcToTgt(true);
		} else if(elementId == this.ctlPrefix + 'SrcToTrg'){
			this.moveFromSrcToTgt(false);
		} else if(elementId == this.ctlPrefix + 'TrgList'){
			this.moveFromTgtToSrc(false);
		} else if(elementId == this.ctlPrefix + 'TrgToSrcAll'){
			this.moveFromTgtToSrc(true);
		} else if(elementId == this.ctlPrefix + 'TrgToSrc'){
			this.moveFromTgtToSrc(false);
		} else if(elementId == this.ctlPrefix + 'SrcText'){
			this.handleKeys(eventType);
		} else if(elementId == this.ctlPrefix + 'TrgText'){
			this.handleKeys(eventType);
		} 
		else if(elementId == this.ctlPrefix + 'SrcSearchAction')
		{
			this.setSearchAction(elementValue,'src');
		} 
		else if(elementId == this.ctlPrefix + 'TrgSearchAction')
		{
			this.setSearchAction(elementValue,'trg');
		} 
		else if(elementId == this.ctlPrefix + 'SrcSearchType')
		{
			this.setSearchType(elementValue,'src');
		} 
		else if(elementId == this.ctlPrefix + 'TrgSearchType')
		{
			this.setSearchType(elementValue,'trg');
		}		
		else if(elementId == this.ctlPrefix + 'TrgDown'){
			this.setOrder('trg', 'down');
		} else if(elementId == this.ctlPrefix + 'TrgUp'){
			this.setOrder('trg', 'up');
		} else if(elementId == this.ctlPrefix + 'SrcDown'){
			this.setOrder('src', 'down');
		} else if(elementId == this.ctlPrefix + 'SrcUp'){
			this.setOrder('src', 'up');
		} else if (self.L2L_MESSAGES != null) {
			alert(composeString(L2L_MESSAGES[6], elementId));
		}
	}

	// ==========================================================================
	// Reflects an options array to the select input.
	// Arguments:
	//		list - The Select input object
	//		text - The text to search/filter with
	//		filter - The filter type (search or filter)
	//		array - The array to reflect in the Select object
	// Returns: none
	// ==========================================================================

	function arrayToList(list,text,array,searchAction,searchType,filter)
	{	
		list.multiple = false;
		var firstMatch = -1;
		var performFilter = text != null && text != '' && filter;
		list.options.length = 0;
		var arrLen = array.length;
		var textFound = true;
		for(var i = 0;i < array.length;i++)
		{
			if(performFilter)
			{
				//text found(contains)
				var textFound = searchText(array[i].text,text,searchType);

				//strIdx = array[i].text.indexOf(text);
				
				//mark the option which contains the first text found
				if(textFound && firstMatch == -1 && array[i].isSelected)
				{
					firstMatch = list.options.length;
				}
			}
			if(array[i].isSelected && (textFound || searchAction == LIST_SEARCH_ACTION_SEARCH))
			{
				var option = document.createElement("OPTION");
				list.options.add(option);
				option.value = array[i].value;
				option.innerText = array[i].text;
			}
		} 
		
		list.selectedIndex = firstMatch;
		if(firstMatch >= 0 && firstMatch < list.length)
		{
			list.options(firstMatch).scrollIntoView();
		} 
		else if(list.options.length > 0)
		{
			list.options(0).scrollIntoView();
		}
		list.multiple = true;
	}
	
	function searchText(sourceText,findText,searchType)
	{
		if (searchType == LIST_SEARCH_TYPE_START)
		{
			return startWith(sourceText,findText);
		}
		else if (searchType == LIST_SEARCH_TYPE_END)
		{
			return endWith(sourceText,findText);
		}
		else if (searchType == LIST_SEARCH_TYPE_CONTAINS)
		{
			return sourceText.indexOf(findText) != -1;
		}
	}
}

// ==========================================================================
// Replace place holders in a string with given arguments.
// Arguments:
//		msgTxt - The String containing the place holders
//		Any Other - Strings to be replaced into the place holders
// Returns:
//		The Composed String where the place holders are replaced	
// ==========================================================================

function composeString(msgTxt)
{
	var MSG_PLACE_HOLDER = '^';
	for(var i = 1; i < composeString.arguments.length; i++){
		if(msgTxt.indexOf(MSG_PLACE_HOLDER) > -1){
			msgTxt = msgTxt.replace(MSG_PLACE_HOLDER, composeString.arguments[i]);
		}
	}
	return msgTxt;
}


// ==========================================================================
// The ListOption Constructor
// Arguments:
// 		value
//		text
//		isSelected
// ==========================================================================
function ListOption(value,text,isSelected)
{
	this.value = value;
	this.text = text;
	this.isSelected = isSelected;

	function compare(listOption)
	{
		return (listOption.value == this.value 
				&& listOption.text == this.text
				&& listOption.isSelected == this.isSelected)
	}
}

function compareListOptionsArray(arr1,arr2)
{
	if (arr1 == null || arr2 == null || arr1.length != arr2.length)
	{
		return false;
	}
	for (index = 0;index < arr1.length;index++)
	{
		if (arr1[index].text != arr2[index].text 
			||
			arr1[index].value != arr2[index].value
			||
			arr1[index].isSelected != arr2[index].isSelected) 
		{
			return false
		}
	}
	return true;
}

// ==========================================================================
// creates and returns new ListOption object by another ListOption object 
function newListOption(lp)
{
	return new ListOption(lp.value,lp.text,lp.isSelected);
}

function errorMessage(messageCode)
{
	if (self.L2L_MESSAGES != null)
	{
		alert(L2L_MESSAGES[messageCode]);
	}
}



