var bLoad=false
var pureText=true
var bTextMode=false
var isInitialized = false;

//Initialize everything when the document is ready
function initEditor() 
{
	if (isInitialized) 
	{
		return;
	}
	isInitialized = true;
	if (document.all.contentIframe!=null) 
	{
		contentIframe.document.designMode = "On";
		if (document.all.initialDiv != null)
		{
			if (document.all.initialDiv.value != "")
			{
				contentIframe.document.write(document.all.initialDiv.value);
			}
			else
			{
				contentIframe.document.write(EDITOR_INITIAL_CONTENT);
			}
			contentIframe.document.body.dir = document.body.dir;
		}
		contentIframe.focus();
        //contentIframe.document.attachEvent("onkeypress",setDirty);
        //contentIframe.document.attachEvent("onkeydown",setDirty);
	}
}

// Check if toolbar is being used when in text mode
function validateMode() 
{
	if (!bTextMode) return true;
	alert("Please uncheck the \"View HTML source\" checkbox to use the toolbars");
	contentIframe.focus();
	return false;
}

//Formats text in composition.
function editorFormat(formatType,formatValue) 
{
	if (!validateMode()) return;
	if (formatValue == null)
	{
		formatValue = event.srcElement.value;
		event.srcElement.selectedIndex = 0;
	}
	if (formatValue == "removeFormat") 
	{
    	formatType = formatValue;
    	formatValue = null;
	}

	if (formatValue == null) 
	{
		contentIframe.document.execCommand(formatType);
	}
	else 
	{
		contentIframe.document.execCommand(formatType,"",formatValue);
  	}
	pureText = false;
	contentIframe.focus();
}

//Switches between text and html mode.
function setMode() 
{
	bTextMode = event.srcElement.checked;
	var cont;
	if (bTextMode) 
	{
	    cleanHtml();
	    cleanHtml();
	    //cont=contentIframe.FilterSourceCode(contentIframe.document.body.innerHTML);
	    cont = contentIframe.document.body.innerHTML;
	    contentIframe.document.body.innerText=cont;
	}	 
	else 
	{
		cont = contentIframe.document.body.innerText;
		contentIframe.document.body.innerHTML=cont;
	}
	contentIframe.focus();
}

//Finds and returns an element.
function getEl(sTag,start) 
{
	while ((start!=null) && (start.tagName!=sTag)) 
	{	
		start = start.parentElement;
	}
	return start;
}

function createLink() 
{
	if (!validateMode()) return;
  
	if (contentIframe.document.selection.type!="Control") {
  	var isA = getEl("A",contentIframe.document.selection.createRange().parentElement());
  	var str=prompt("Enter link location (e.g. http://www.idor.com):", isA ? isA.href : "http:\/\/");
 	 
 	if ((str!=null) && (str!="http://")) {
 	  if (contentIframe.document.selection.type=="None") {
      	  var sel=contentIframe.document.selection.createRange();
      	  sel.pasteHTML("<A target=_whole HREF=\""+str+"\">"+str+"</A> ");
      	  sel.select();
   	  }
   	else editorFormat("CreateLink",str);
 	}
  	else contentIframe.focus();
  }
}

function createTable() 
{
	if (!validateMode()) return;
	var arr = showModalDialog(EDITOR_CREATE_TABLE_FILENAME,EDITOR_TABLE_LABELS, "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:35em");
	if (arr!="") 
	{
		var sel=contentIframe.document.selection.createRange();
		if (contentIframe.document.selection.type=="Control") 
		{
			contentIframe.focus();
			contentIframe.document.selection.clear();
			sel=contentIframe.document.selection.createRange();			
		}
		sel.pasteHTML(arr);
	}
     
}

//Sets the text color.
function foreColor() {
  if (!validateMode()) return;
  var arr = showModalDialog(EDITOR_SELECT_COLOR_FILENAME,EDITOR_COLOR_LABELS, "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:35em");
  if (arr != null) editorFormat('forecolor', arr);
  else contentIframe.focus();
}

//Sets the background color.
function backColor() {
  if (!validateMode()) return;
  var arr = showModalDialog(EDITOR_SELECT_COLOR_FILENAME,EDITOR_COLOR_LABELS, "font-family:Verdana; font-size:12; dialogWidth:30em; dialogHeight:35em");
  if (arr != null) editorFormat('backcolor', arr);
  else contentIframe.focus()
}

function cleanHtml() {
  var fonts = contentIframe.document.body.all.tags("FONT");
  var curr;
  for (var i = fonts.length - 1; i >= 0; i--) {
    curr = fonts[i];
    if (curr.style.backgroundColor == "#ffffff") curr.outerHTML = curr.innerHTML;
  }
}

function getPureHtml() {
  var str = "";
  var paras = contentIframe.document.body.all.tags("P");
  if (paras.length > 0) {
    for (var i=paras.length-1; i >= 0; i--) str = paras[i].innerHTML + "<br>" + str;
  } else {
    str = contentIframe.FilterSourceCode(contentIframe.document.body.innerHTML);
  }
  return str;
}