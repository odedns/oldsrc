// This global variable serves as a flag for RTE acessibility support.
// This var is set in options_ie.jsp when the accessible toolbar is invoked.
var isAccessible = false;
// This global variable is used to transfer the editor content from the main 
// editor window to the child print window. 
// This var is added in 5.1 release. See IBM_RTE_doPrint() for details. 
var cnt;
var mode = 0;
function IBM_RTE_getEditorMode(editorName){
	if(IBM_RTE_getDocument(editorName).mode == null)
		IBM_RTE_getDocument(editorName).mode = 0;
	return IBM_RTE_getDocument(editorName).mode;
}

function IBM_RTE_isMozilla() {
    return navigator.product == 'Gecko';
}

function IBM_RTE_isNetscape() {
    return /netscape/i.test(navigator.userAgent);
}

function IBM_RTE_getFrame(editorName) {
    if (!isAccessible)
       return document.getElementById(editorName );
    else
       return this.opener.document.getElementById(editorName );      
}

function IBM_RTE_getWindow(editorName) {
    return IBM_RTE_getFrame(editorName).contentWindow;
}

function IBM_RTE_getDocument(editorName) {
    return IBM_RTE_getWindow(editorName).document;
}

function IBM_RTE_setDesignMode1(editorName) {
    IBM_RTE_getDocument(editorName).designMode = "on";  
}
//In IE, use body.contentEditable to set the document to be editable.
//This allows elements to be hidden or shown as needed using style.display attribute.
//The change is mainly for 5.1 annotation feature (available in the popup editor version only).
//No existing features are affected by this change.
function IBM_RTE_setDesignMode(editorName) {
    if (IBM_RTE_isMozilla()) {
        IBM_RTE_getDocument(editorName).designMode = "on";  
    }else {

		if ((IBM_RTE_getDocument(editorName).body)==null){
			setTimeout("IBM_RTE_setDesignMode('" + editorName + "')", 1500);
		}else{
			IBM_RTE_getDocument(editorName).body.contentEditable = true;
		}
    }
	editorNameSaved = editorName;
	if(!IBM_RTE_isMozilla())
		backup[0]=IBM_RTE_getDocument(editorName).body.innerHTML;
}

function IBM_RTE_getToolbarColor(editorName) {
    return IBM_RTE_isMozilla() ? document.defaultView.getComputedStyle(document.getElementById(editorName), '').getPropertyValue("background-color") : document.getElementById(editorName).currentStyle.backgroundColor;
}
function IBM_RTE_loadEditorWithEmptyDocument(editorName, docDir) {
    IBM_RTE_getDocument(editorName).body.innerHTML = "<html><head><title></title></head><body dir='" + docDir + "'></body></html>";
}
function IBM_RTE_doTheCommand(editorName, commandname) {        
/**
 *  undo/redo breaks for RAD RTE plugin in IE.
 *  manual undo/redo implementation used.
 */
    IBM_RTE_getWindow(editorName).focus();

	if(!IBM_RTE_isMozilla() && (commandname == 'undo' || commandname == 'redo')){
		if(commandname == 'undo'){
			if(isAccessible) 
				this.opener.IBM_RTE_undo(editorName);
			else
				IBM_RTE_undo(editorName);
		}
    else{
		if(isAccessible) 
			this.opener.IBM_RTE_redo(editorName);
		else
			IBM_RTE_redo(editorName);
		}
    }
   else{
		if(!IBM_RTE_isMozilla()){
			if(isAccessible) 
				this.opener.IBM_RTE_backup(editorName);
			else
				IBM_RTE_backup(editorName);
		}
    IBM_RTE_getDocument(editorName).execCommand(commandname, false, null);
    }
}

function IBM_RTE_doLink(editorName, prompttext) {    
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
    if (IBM_RTE_isMozilla()) {
		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
		var xxx=0;

		while(a[xxx] != null){
			if(a[xxx].getAttribute("odc") != null)
				a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
				xxx++;
		}
        var theurl = prompt(prompttext[0], "http://");
        IBM_RTE_getDocument(editorName).execCommand('createlink', false, theurl);

		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
	    var xxx=0;

		while(a[xxx] != null){
			 a[xxx].setAttribute("odc", a[xxx].getAttribute("href"));
			 xxx++;
		}
    }
    else {
		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
		var xxx=0;

		while(a[xxx] != null){
			if(a[xxx].odc != null)
				a[xxx].href = a[xxx].odc;
				xxx++;
		}
        IBM_RTE_getWindow(editorName).focus();
        IBM_RTE_getDocument(editorName).execCommand('createlink', true, null);

		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
	    var xxx=0;

		while(a[xxx] != null){
			 a[xxx].setAttribute("odc", a[xxx].href);
			 xxx++;
		}
	   }
}	

function IBM_RTE_doFontStyle(editorName, ctrl, commandname) {
    if (ctrl) {
        var hType = ctrl.options[ctrl.selectedIndex].value;

        if (hType != '') {
            IBM_RTE_getWindow(editorName).focus();
		if(!IBM_RTE_isMozilla()){
			if(isAccessible) 
				this.opener.IBM_RTE_backup(editorName);
			else
				IBM_RTE_backup(editorName);
		}
            IBM_RTE_getDocument(editorName).execCommand(commandname, false, hType);
    }}
}

function IBM_RTE_getOffsetTop(ctrl) {
    var offsetTop = ctrl.offsetTop;
    var offsetParent = ctrl.offsetParent;

    while (offsetParent) {            
        offsetTop += offsetParent.offsetTop;
        offsetParent = offsetParent.offsetParent;
    }
    return offsetTop;
}

function IBM_RTE_getOffsetLeft(ctrl) {
    var offsetLeft = ctrl.offsetLeft;
    var offsetParent = ctrl.offsetParent;

    while (offsetParent) {
        offsetLeft += offsetParent.offsetLeft;
        offsetParent = offsetParent.offsetParent;
    }
    return offsetLeft;
}

function IBM_RTE_doColor(editorName, clrType, buttonElement, locale, images, directory, isBidi) {
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
    if (IBM_RTE_isMozilla()) {
        if (clrType.localeCompare("backcolor") == 0) 
             clrType = "hilitecolor"; 
    }

	IBM_RTE_getWindow(editorName).focus();
    var jrl = "colorPicker_accessible.jsp";
    jrl = directory + jrl;
    var wcp_colorWin = window.open(jrl + '?locale=' + locale + '&images=' + images + '&editorName=' + editorName + '&isBidi=' + isBidi  + '&clrType=' + clrType, 'colors', 'scrollbars=0, toolbar=0, statusbar=0, width=320, height=450, left=' + ((screen.width - 260) / 2) + ', top=' + ((screen.height - 150) / 2));
    wcp_colorWin.focus();
}

// Used to insert elements in Mozilla editor.
function IBM_RTE_insertNodeAtSelection(win, insertNode) {
    var sel = win.getSelection();
    var range = sel.getRangeAt(0);

    var container = range.endContainer;
    var pos = range.endOffset;

    // make a new range for the new selection
    range = document.createRange();

    if (container.nodeType == 3 && insertNode.nodeType == 3) {
        // if we insert text in a textnode, do optimized insertion
        container.insertData(pos, insertNode.nodeValue);
        // put cursor after inserted text
        range.setEnd(container, pos + insertNode.length);
        range.setStart(container, pos + insertNode.length);        
    }else {
        var afterNode;
        if (container.nodeType == 3) {
            // when inserting into a textnode we create 2 new textnodes and put the insertNode in between
            var textNode = container;
            container = textNode.parentNode;
            var text = textNode.nodeValue;

            // text before the split
            var textBefore = text.substr(0,pos);
            // text after the split
            var textAfter = text.substr(pos);
            
            var beforeNode = document.createTextNode(textBefore);
            var afterNode = document.createTextNode(textAfter);
            
            // insert the 3 new nodes before the old one
            container.insertBefore(afterNode, textNode);
            container.insertBefore(insertNode, afterNode);
            container.insertBefore(beforeNode, insertNode);
            
            // remove the old node
            container.removeChild(textNode);
        }
        else {
            // else simply insert the node
            afterNode = container.childNodes[pos];
            container.insertBefore(insertNode, afterNode);
        }
        range.setEnd(afterNode, 0);
        range.setStart(afterNode, 0);
    }
    sel.addRange(range);
}

function IBM_RTE_doTableInsert(editorName, buttonElement, locale, images, directory, tab_array, isBidi) {
	var numrows = tab_array[0];
	var numcols = tab_array[1];

    IBM_RTE_getWindow(editorName).focus();
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
    var wcp_tableWin = window.open(directory + 'table_ie.jsp?locale=' + locale + '&images=' + images + '&editorName=' + editorName + '&isBidi=' + isBidi, 'table', 'scrollbars=0, toolbar=0, statusbar=0,resizable=yes, width=200, height=190, left=' + ((screen.width - 200) / 2) + ', top=' + ((screen.height - 170) / 2));
    wcp_tableWin.focus();
}

function IBM_RTE_getEditorHTML(editorName) {
    var bodyDir =  IBM_RTE_getDocument(editorName).body.dir;
    var lnks = IBM_RTE_getDocument(editorName).getElementsByTagName("link");
    var iHead = '<html>\n<head>\n<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />\n';
    var ttls = IBM_RTE_getDocument(editorName).getElementsByTagName("title");

	if ((ttls) && ttls.length > 0) {
        iHead += "<title>" + ttls[0].text + "</title>\n";
    }
    
    for (i = 0; (lnks) && (i < lnks.length); i++) {
        iHead += "<link rel='" + lnks[0].rel + "' href='" + lnks[i].href + "' />\n";
    }
    iHead += "</head>\n";

    if (bodyDir.localeCompare('ltr') == 0)
        iHead += "<body dir='ltr'>\n";
    else if (bodyDir.localeCompare('rtl') == 0) 
        iHead += "<body dir='rtl'>\n";
    else
        iHead += "<body>\n";

    iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
    iFoot = "</body>\n</html>";

    iAll = iHead + iHTML + iFoot;
    return iAll;
}


function IBM_RTE_getEditorContent(editorName, mode,encoding) {
    var bodyDir =  IBM_RTE_getDocument(editorName).body.dir;
    var lnks = IBM_RTE_getDocument(editorName).getElementsByTagName("link");
    var iHead = '<html>\n<head>\n<meta http-equiv="content-type" content="text/html; charset="';
	iHead += encoding;
	iHead += "\" />\n";
    var ttls = IBM_RTE_getDocument(editorName).getElementsByTagName("title");
    if ((ttls) && ttls.length > 0) {
        iHead += "<title>" + ttls[0].text + "</title>\n";
    }
    
    for (i = 0; (lnks) && (i < lnks.length); i++) {
        iHead += "<link rel='" + lnks[0].rel + "' href='" + lnks[i].href + "' />\n";
    }

    iHead += "</head>\n";

    if (bodyDir.localeCompare('ltr') == 0)
        iHead += "<body dir='ltr'>\n";
    else if (bodyDir.localeCompare('rtl') == 0) 
        iHead += "<body dir='rtl'>\n";
    else
        iHead += "<body>\n";
    if (IBM_RTE_getEditorMode(editorName) == 0){
                 iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
		 IBM_RTE_getDocument(editorName).body.innerText = iHTML;
		 iText = IBM_RTE_getDocument(editorName).body.innerText;
		 IBM_RTE_getDocument(editorName).body.innerHTML = iText;
       iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
    }
    else if (IBM_RTE_getEditorMode(editorName) == 1) {
         if (IBM_RTE_isMozilla()) {
            var html = IBM_RTE_getDocument(editorName).body.ownerDocument.createRange();
            html.selectNodeContents(IBM_RTE_getDocument(editorName).body);
            iHTML = html.toString();
         }
         else {
            iHTML = IBM_RTE_getDocument(editorName).body.innerText;
         }
    }      

    iFoot = "</body>\n</html>";
	iHTML= iHTML.replace(/\<TD\>\<\/TD\>/g, "<TD>&nbsp;</TD>");
    iAll = iHead + iHTML + iFoot;

	return iAll;
}

function IBM_RTE_getEditorHTML2(editorName, metaCnt) {
    var bodyDir =  IBM_RTE_getDocument(editorName).body.dir;
    var lnks = IBM_RTE_getDocument(editorName).getElementsByTagName("link");
    var iHead = '<html>\n<head>\n';
    iHead += '<meta http-equiv="content-type" content="' + metaCnt + '" />\n';
    var ttls = IBM_RTE_getDocument(editorName).getElementsByTagName("title");
    if ((ttls) && ttls.length > 0) {
        iHead += "<title>" + ttls[0].text + "</title>\n";
    }
    for (i = 0; (lnks) && (i < lnks.length); i++) {
        iHead += "<link rel='" + lnks[0].rel + "' href='" + lnks[i].href + "' />\n";
    }
    iHead += "</head>\n";
    if (bodyDir.localeCompare('ltr') == 0)
        iHead += "<body dir='ltr'>\n";
    else if (bodyDir.localeCompare('rtl') == 0) 
        iHead += "<body dir='rtl'>\n";
    else
        iHead += "<body>\n";

    iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
    iFoot = "</body>\n</html>";
    iAll = iHead + iHTML + iFoot;
    return iAll;
}

function IBM_RTE_getEditorHTMLFragment(editorName) {
	if(IBM_RTE_getEditorMode(editorName) == 0){
		if (IBM_RTE_isMozilla()){
			var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
			var xxx=0;
			while(a[xxx] != null)
			{
				if(a[xxx].getAttribute("odc")!=null&&a[xxx].getAttribute("odc") != ""){
					a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
					a[xxx].removeAttribute("odc", 0);	
				}
				else
					a[xxx].removeAttribute("odc", 0); 
				xxx++;
			}
			/*
			var img = IBM_RTE_getDocument(editorName).body.getElementsByTagName("img");
			xxx=0;
			
			while(img[xxx] != null){
				if(img[xxx].getAttribute("odc")!=null&&img[xxx].getAttribute("odc")!= ""){
					img[xxx].setAttribute("src",img[xxx].getAttribute("odc"));
					img[xxx].removeAttribute("odc", 0);
				}
				else
					img[xxx].removeAttribute("odc", 0);
				xxx++;
			}
			*/
	   }
		else{
			var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
			var xxx=0;
			while(a[xxx] != null){
				if(a[xxx].getAttribute("odc") != "" && a[xxx].getAttribute("odc") != null) {
					a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
					a[xxx].removeAttribute("odc");				
				}
				 xxx++;
			}
			/*var img = IBM_RTE_getDocument(editorName).body.getElementsByTagName("img");
			var xxx=0;
			while(img[xxx] != null){
				if(img[xxx].getAttribute("odc") != "" && img[xxx].getAttribute("odc") != null){
					img[xxx].setAttribute("src", img[xxx].getAttribute("odc"));
					img[xxx].removeAttribute("odc");
				}
				xxx++;
			}*/
	   }
		var hh = IBM_RTE_getDocument(editorName).body.innerHTML;
	//		if(hh.match(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i) != null)
	//			hh = hh.replace(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i, "");
			hh = hh.replace(/&#[0-2]?[0-9];/g,"");
			hh = hh.replace(/&#3[01];/g,"");
			hh = stripControlCharacters(hh);

			var html = IBM_RTE_getDocument(editorName).body.innerHTML;
			var xhtml = html.replace(/\\/g,'\\\\');
			//alert('The xhtml : ' + xhtml);
			return xhtml;
//			return hh;
	}
}

function IBM_RTE_setEditorHTML(editorName, content) {
if(IBM_RTE_getEditorMode(editorName) != 0){
	var Text1 = new String(content);

	Text1 = Text1.replace(/&#[0-2]?[0-9];/g,"");
	Text1 = Text1.replace(/&#3[01];/g,"");
	Text1 = stripControlCharacters(Text1);
	
	if(IBM_RTE_isMozilla()){
		var html = IBM_RTE_getDocument(editorName).createTextNode(Text1);
		IBM_RTE_getDocument(editorName).body.innerHTML = "";
		IBM_RTE_getDocument(editorName).body.appendChild(html);
	}
	else
		IBM_RTE_getDocument(editorName).body.innerText = Text1;
}else {
	if (IBM_RTE_isMozilla()){
		var Text1 = new String(content);
		Text1 = Text1.replace(/&#[0-2]?[0-9];/g,"");
		Text1 = Text1.replace(/&#3[01];/g,"");
		Text1 = stripControlCharacters(Text1);
	
		var str3    = "" + "<a .*?[^\/]>";
		var re3     = new RegExp(str3.toString(),"gi");
		var matches = Text1.match(re3);
		var xx      = 0;
		if(matches != null)
			while(true){
				if(matches[xx] == null) break;
				var Smatch = matches[xx];
				var str69  = "" + "href=\"'";
				var re69   = new RegExp(str69.toString(),"i");

				var str96  = "" + "href='\"";
				var re96   = new RegExp(str96.toString(),"i");
				if(re69.test(Smatch) == false && re96.test(Smatch) == false && isCustomTag(Smatch)==false){
					var cmpts = matches[xx].match(/href=(["'])([^\1]*?)\1/i);
					if(cmpts != null)
					{
						var quot  = cmpts[1];
						var href  = cmpts[2];
						temp = "href=\"" + href.replace(/\"/gi,"'") + "\" odc=\"" + href.replace(/\"/gi,"'") + "\" "; 	
						matches[xx] = matches[xx].replace(/href=(["'])([^\1]*?)\1/i, temp);
						Text1 = Text1.replace(Smatch.toString(), matches[xx].toString()); 
					}
				}
				xx++;
			}
			/*
			var matches = Text1.match(/<img.*?src=(['"])[^\1]*?\1.*?[\/]?>/gi);
			var xx      = 0;
		
			if(matches != null)
				while(true){
					if(matches[xx] == null) break;

					var Smatch = matches[xx];
					var str69  = "" + "src=\"'";
					var re69   = new RegExp(str69.toString(),"i");
					var str96  = "" + "src='\"";
					var re96   = new RegExp(str96.toString(),"i");
					if(re69.test(Smatch) == false && re96.test(Smatch) == false && isCustomTag(Smatch)==false){
						var cmpts = matches[xx].match(/src=(["'])([^\1]*?)\1/i);
						if(cmpts != null){
							var quot = cmpts[1];
							var src  = cmpts[2];
							temp = "src=\"" + src.replace(/\"/gi,"'") + "\" odc=\"" + src.replace(/\"/gi,"'") + "\" "; 	
							matches[xx] = matches[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);
							Text1 = Text1.replace(Smatch.toString(), matches[xx].toString()); 
						}
					}
					xx++;
				}*/
	}else{
		var Text1 = new String(content);
		Text1 = Text1.replace(/&#[0-2]?[0-9];/g,"");
		Text1 = Text1.replace(/&#3[01];/g,"");
		Text1 = stripControlCharacters(Text1);
		
//		if(Text1.match(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i) == null)
//			Text1 = "<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"></xmp>" + Text1;
		var str3    = "" + "<a .*?[^/]>";
		var re3     = new RegExp(str3.toString(),"gi");
		var matches = Text1.match(re3);
		var xx      = 0;
		
		if(matches != null)
			while(true){
				if(matches[xx] == null) break;
				
				var Smatch = matches[xx];
				var str69  = "" + "href=\"'";
				var re69   = new RegExp(str69.toString(),"i");
				var str96  = "" + "href='\"";
				var re96   = new RegExp(str96.toString(),"i");
				if(re69.test(Smatch) == false && re96.test(Smatch) == false && isCustomTag(Smatch)==false)
				{				
					var cmpts = matches[xx].match(/href=(["'])([^\1]*?)\1/i);
					if(cmpts != null)
					{
						var quot  = cmpts[1];
						var href  = cmpts[2];
						temp = "href=\"" + href.replace(/\"/gi,"'") + "\" odc=\"" + href.replace(/\"/gi,"'") + "\" "; 	
						matches[xx] = matches[xx].replace(/href=(["'])([^\1]*?)\1/i, temp);
						Text1 = Text1.replace(Smatch.toString(), matches[xx].toString()); 
					}
				}
				xx++;
			}
			/*
		var matchesImg = Text1.match(/<img.*?src=(['"])[^\1]*?\1.*?[\/]?>/gi);
		xx = 0;
			
		if(matchesImg != null)
			while(true)
			{
				if(matchesImg[xx] == null) break;

				var Smatch = matchesImg[xx];
				var str69  = "" + "src=\"'";
				var re69   = new RegExp(str69.toString(),"i");
	
				var str96  = "" + "src='\"";
				var re96   = new RegExp(str96,"i");
				if(re69.test(Smatch) == false && re96.test(Smatch) == false && isCustomTag(Smatch)==false)
				{
					var cmpts = matchesImg[xx].match(/src=(["'])([^\1]*?)\1/i);
					if(cmpts != null)
					{
						var quot = cmpts[1];
						var src  = cmpts[2];
						temp = "src=\"" + src.replace(/\"/gi,"'") + "\" odc=\"" + src.replace(/\"/gi,"'") + "\" "; 	
						matchesImg[xx] = matchesImg[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);
						Text1 = Text1.replace(Smatch.toString(), matchesImg[xx].toString()); 
					}
				}
				xx++;
			}*/
	}
IBM_RTE_getDocument(editorName).body.innerHTML = Text1;

	if (IBM_RTE_isMozilla()){
		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
		var xxx=0;
		while(a[xxx] != null)
		{
			if(a[xxx].getAttribute("odc") != "" && a[xxx].getAttribute("odc") != null) 
				a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
			xxx++;
		}

		/*var b = IBM_RTE_getDocument(editorName).body.getElementsByTagName("IMG");
		var xxx=0;
		while(b[xxx] != null)
		{
			if(b[xxx].getAttribute("odc") != "" && b[xxx].getAttribute("odc") != null) 
				b[xxx].setAttribute("src", b[xxx].getAttribute("odc"));
			xxx++;
		}*/
	}
	else{
		var a = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
		var xxx=0;
		while(a[xxx] != null){
			if(a[xxx].odc != "" && a[xxx].odc != null) 
				a[xxx].href = a[xxx].odc;
			xxx++;
		}

		/*var b = IBM_RTE_getDocument(editorName).body.getElementsByTagName("IMG");
		var xxx=0;
		while(b[xxx] != null){
			if(b[xxx].odc!= "" && b[xxx].odc != null) 
				b[xxx].src = b[xxx].odc;
			xxx++;
		}*/
	}
}
}

function IBM_RTE_flipDocument(editorName) {
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
    var origDir =  IBM_RTE_getDocument(editorName).body.dir;

    if ((origDir.localeCompare('') == 0)||(origDir.localeCompare('ltr') == 0))
        IBM_RTE_getDocument(editorName).body.dir = "rtl";
    else if (origDir.localeCompare('rtl') == 0)
        IBM_RTE_getDocument(editorName).body.dir = "ltr";

    IBM_RTE_getWindow(editorName).focus();

}


function IBM_RTE_getSelectionRange(editorName)
{
      var doc = IBM_RTE_getDocument(editorName);
      var win = IBM_RTE_getWindow(editorName);

	var ret = null; 
	if(doc.selection)
		ret = doc.selection.createRange();
	else if (win.getSelection)
	{
		var sel = win.getSelection();
		if(sel != null && sel.rangeCount > 0)
			ret = sel.getRangeAt(0);
	}
	return ret;
}

// TODO: detect and clean up redundant DIR tags while preserving other tags.

// Mozilla has no good way to insert a new node to subsume the selection content 
// other than using surroundContents(). SurroundContents() is not very tolerant in
// the selection, such as if the Range partially selects a non-Text node, 
// surroundContents() raises an exception.
// Using separate operations extractContents(), insertNode() will NOT help, it would
// only make things worse, as when insertNode() throws exception, the range content 
// has been removed already. Using surroundContents(), when it throws exception, the 
// range content will remain unchanged.
//
// May need to find a way to mimic IE's behavior???

function IBM_RTE_flipSelection(editorName, dir) {
    // Get a selection range.
    var rng = IBM_RTE_getSelectionRange(editorName);
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}    
    if (IBM_RTE_isMozilla()) {
        if (rng) {
  		var arr = IBM_RTE_getDocument(editorName).body.getElementsByTagName('P');
		var xx = 0;
		while(arr[xx] != null)
		{
			var bodyRange = IBM_RTE_getDocument(editorName).createRange();
			var inRange = false;
			paraRange = bodyRange;
			paraRange.setStartBefore(arr[xx]);
			paraRange.setEndAfter(arr[xx]);
			var START_TO_START = 0;
			var END_TO_START = 3;
			if(paraRange.compareBoundaryPoints(START_TO_START,rng) >= 0){
				if(paraRange.compareBoundaryPoints(END_TO_START,rng) <= 0)
					inRange = true;
			}
			else if(rng.compareBoundaryPoints(END_TO_START,paraRange) <= 0)
				inRange = true;
			if(inRange){
				arr[xx].dir = dir;
			}
			xx++;
		}

		}
    }else {
        if (rng) {
			var parent = rng.parentElement();
			var arr = IBM_RTE_getDocument(editorName).body.getElementsByTagName('P');
			var xx = 0;
			var enteredInRange = false;
			var paraRange;
			while(arr[xx]!=null)
			{
				var inRange = false;
				paraRange = IBM_RTE_getDocument(editorName).body.createTextRange();
				paraRange.moveToElementText(arr[xx]);
				try{
					if(paraRange.compareEndPoints("StartToStart",rng) <= 0){
						if(paraRange.compareEndPoints("EndToStart",rng) >= 0){
							inRange = true;
						}
					}
					else if(rng.compareEndPoints("EndToStart",paraRange) >= 0)
						inRange = true;
				}catch(e){}
				if(inRange){
					parent = arr[xx];
					parent.dir = dir;
					enteredInRange = true;
				}
				else if(enteredInRange)break;
				xx++;
			}
		}
	}
    IBM_RTE_getWindow(editorName).focus();
}


function IBM_RTE_checkEvent(editorName) {

   if (typeof checkEvent != 'undefined') {
       checkEvent();
    }
    
}

// These three functions are actually used in dialogs to highlight 
// buttons when mouse is moved over. Use document instead of 
// IBM_RTE_getDocument() because dialogs are in their own windows.
function setStyle(element, name, value) {
        if (element.style && (element.style[name] != value) )
            element.style[name] = value;
}

function IBM_RTE_btn_mouseover(id) {
        
        var elmt = document.getElementById(id);

        if (elmt) {
            setStyle(elmt, 'color', '#0000FF');
            setStyle(elmt, 'borderColor', '#0000FF');
        }
}

function IBM_RTE_btn_mouseoout(id) {
        
        var elmt = document.getElementById(id);

        if (elmt) {
            setStyle(elmt, 'color', '#405380');
            setStyle(elmt, 'borderColor', '#405380');
        }
}


function IBM_RTE_setATag(editorName) {

        var aTags = IBM_RTE_getDocument(editorName).body.getElementsByTagName("A");
        var len = aTags.length;
        for (var i=0; i < len; i++) {
            var aLink = aTags[i].href;
            if (aLink.substring(0, 5) == "http:") {
                aTags[i].target = "_blank";
            }
            else if (aLink.substring(0, 6) == "https:") {
                aTags[i].target = "_blank";
            }
            else if (aLink.substring(0, 4) == "ftp:") {
                aTags[i].target = "_blank";
            }
            else if (aLink.substring(0, 5) == "file:") {
                aTags[i].target = "_blank";
            }
        }
}

// The following functions are for advanced table support.
// Added in 5.1 release.

//This is a utility function to return the current row where the cursor is.
function IBM_RTE_getCurrentRow(editorName) {

    var selRange = IBM_RTE_getSelectionRange(editorName);
    var currentRow;

    if (IBM_RTE_isMozilla()) {
        currentRow = selRange.startContainer;
        var strName = currentRow.nodeName;
         
        while(strName != "TR") {
            currentRow = currentRow.parentNode;
            if (currentRow == null) break;
            strName = currentRow.nodeName;
        }
    }
    else {
        currentRow = selRange.parentElement();
        var strName = currentRow.tagName;
        
        while(strName != "TR") {
            currentRow = currentRow.parentNode;
            if (currentRow == null) break;
            strName = currentRow.tagName;
        }
    }
 
    return currentRow;
}

//This is a utility function to return the number of cells in one row.
//Nested table in a row is considered as one cell. 
function IBM_RTE_getNumOfCells(editorName, tagName, nextRow) {
   var currentRow = IBM_RTE_getCurrentRow(editorName);

   var cells; 
   if(!nextRow || (nextRow && (currentRow.nextSibling != 'null' && currentRow.nextSibling != 'undefined'))){
	   if(tagName == 'TH')
		cells = currentRow.getElementsByTagName("TH");
	else
		cells = currentRow.getElementsByTagName("TD");
   }else{
	   var row1;
	   if(currentRow.nextSibling != 'null' && currentRow.nextSibling != 'undefined'){
			row1 = currentRow.nextSibling;
			if(tagName == 'TH')
				cells = currentRow.nextSibling.getElementsByTagName("TH");
			else
				cells = currentRow.nextSibling.getElementsByTagName("TD");
		}
		else{

		}
   
   }
   // Can not return cells.length directly because if there is
   // a sub-table embedded, the cells.length will include the sub 
   // table cells. Using nextSibling can solve this issue. 
   // See below.
   var j=0;
   if (cells.length > 0) {
      var cellNode = cells[0];

      if (IBM_RTE_isMozilla()) {
        for (;;) {
           if (cellNode!=null && cellNode.nodeName == tagName){
              j++;
              cellNode = cellNode.nextSibling;     
           }else {
              break;
           }
        }
      }
      else {
        for (;;) {
           if (cellNode!=null && cellNode.tagName == tagName){
              j++;
              cellNode = cellNode.nextSibling;     
           }else {
              break;
           }
        }
      }
   }
   if(nextRow && (currentRow.nextSibling != 'null' && currentRow.nextSibling != 'undefined'))
	   return j-1;
   else
	   return j;
}

//Utility function to create a new cell.
//In Mozilla, <BR> has to be added in each cell.
function IBM_RTE_createNewCell(editorName, thCell) {
    // In Mozilla, the new cell needs to add a <br> to a new cell.
    var doc = IBM_RTE_getDocument(editorName);
    var newCell;
	if(thCell)
		newCell = doc.createElement("TH");
	else
		newCell = doc.createElement("TD");

	if (IBM_RTE_isMozilla()) {
        var brElem = doc.createElement("BR");
        newCell.appendChild(brElem);
    }else{
		newCell.insertAdjacentText("afterBegin"," ");
	}
    return newCell;
}

function IBM_RTE_deleteRow(editorName, text, error_rowDelete) {
   var currentRow = IBM_RTE_getCurrentRow(editorName);
	if (currentRow == null) return;
	var numOfTDCells = IBM_RTE_getNumOfCells(editorName,"TD",false);  
	if(numOfTDCells == 0){
		alert(error_rowDelete);
		return;
	}
	if(IBM_RTE_isMozilla() || IBM_RTE_isNetscape()){
	   var ret = confirm(text);
	   if (!ret) {
		   return;
	   }
	}

   var container = currentRow.parentNode;
   container.removeChild(currentRow);
   //When child nodes in TBODY is 0, delete the whole table.
   if (container.nodeName == "TBODY" && container.childNodes.length == 0){
       var parTable = container.parentNode;
       parTable.parentNode.removeChild(parTable);
   }
}

function IBM_RTE_insertRowAbove(editorName, error_rowInsert) {
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}

	var currentRow = IBM_RTE_getCurrentRow(editorName);

	if (currentRow == null) return;
	var numOfTHCells = IBM_RTE_getNumOfCells(editorName,"TH",false);  
	var numOfTDCells = IBM_RTE_getNumOfCells(editorName,"TD",false);  

	// find if currentRow is a header row..
	var prevRow = currentRow.previousSibling;
	var isTHCell = false;
	if(prevRow == null || prevRow == 'undefined'){
		// see if it is a purely Header Row
		if( (numOfTHCells + numOfTDCells) == numOfTHCells){
			alert(error_rowInsert); // Put the resource bundle error msg.
			return;
		}
	}
	var newRow = IBM_RTE_getDocument(editorName).createElement("TR");
	for (i=0; i<numOfTHCells; i++) {
		var col = IBM_RTE_createNewCell(editorName, true);
		newRow.appendChild(col);
	}
	for (i=0; i<numOfTDCells; i++) {
		var col= IBM_RTE_createNewCell(editorName, false);
		newRow.appendChild(col);
	}
	var container = currentRow.parentNode;
	var newRow=container.insertBefore(newRow, currentRow);
	newRow.focus();
}

function IBM_RTE_insertRowBelow(editorName) {
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
   var currentRow = IBM_RTE_getCurrentRow(editorName);
    if (currentRow == null) return;

	var numOfTHCells = IBM_RTE_getNumOfCells(editorName,"TH",false);  
	var numOfTDCells = IBM_RTE_getNumOfCells(editorName,"TD",false);  
	if (numOfTDCells == 0) {
		var numTDCells = IBM_RTE_getNumOfCells(editorName,"TD", true);  
		var numTHCells = IBM_RTE_getNumOfCells(editorName,"TH", true);  
		if(numTDCells == -1) {
			numOfTDCells = numOfTHCells -1;
			numOfTHCells = 1;
		}else{
			numOfTHCells = numTHCells;  
			numOfTDCells = numTDCells;  
		}
	}
   var newRow = IBM_RTE_getDocument(editorName).createElement("TR");

   for (i=0; i<numOfTHCells; i++) {
       var col = IBM_RTE_createNewCell(editorName, true);
       newRow.appendChild(col);
   }
   for (i=0; i<numOfTDCells; i++) {
       var col = IBM_RTE_createNewCell(editorName,false);
       newRow.appendChild(col);
   }

   var container = currentRow.parentNode;
   var prevRow = currentRow.nextSibling;
   var newRow=container.insertBefore(newRow, prevRow);
   newRow.focus();
}

//Utility function to return the cellIndex in the current row. 
// Cell index starts from 0.
function IBM_RTE_getColumnIndex(editorName) {
    var selRange = IBM_RTE_getSelectionRange(editorName);
    var currentCell;

   if (IBM_RTE_isMozilla()) {
        currentCell = selRange.startContainer;
        var strName = currentCell.nodeName;
        while(strName != "TD" && strName != "TH") {
            currentCell = currentCell.parentNode;
            if (currentCell == null) return null;
            strName = currentCell.nodeName;
        }
    } else {
        currentCell = selRange.parentElement();
        var strName = currentCell.tagName;

        while(strName != "TD" && strName != "TH") {
            currentCell = currentCell.parentNode;
            if (currentCell == null) return null;
            strName = currentCell.tagName;
        }
    }
    return currentCell.cellIndex;
}

//Utility function to return the current table.
function IBM_RTE_getTable(editorName) {
    var currRow = IBM_RTE_getCurrentRow(editorName);
    if (currRow == null) return null;
    var currTable;

    if (IBM_RTE_isMozilla()) {  
        currTable = currRow.parentNode.parentNode;
    }
    else {
        currTable = currRow.parentNode;
        var strName = currTable.tagName;

        while(strName != "TABLE") {
            currTable = currTable.parentNode;
            strName = currTable.tagName;
        }
    }
   
    return currTable;
}


function IBM_RTE_deleteColumn(editorName, text, error_columnDelete) {
    var currColIndex = IBM_RTE_getColumnIndex(editorName);
    var currTable = IBM_RTE_getTable(editorName);
    if (currTable == null) return;

    var rows = currTable.rows;
    var numOfRows = rows.length

	// check to see if the first col is the header.
	if(currColIndex == 0){
		var numOfTHCells = 0;
		var numOfTDCells = 0;
		// check to see if the coulmn is header col.
		for (var k = 0 ; k < numOfRows ; k++ ){
			var row = rows[k];
			var col = row.cells[currColIndex];
			var tagname;
			if(IBM_RTE_isMozilla()) 
				tagname = col.nodeName;
			else
				tagname = col.tagName;
			if(tagname == 'TH')
				numOfTHCells++;
			else
				numOfTDCells++;
		}
		if (numOfTDCells == 0)
		{
			alert(error_columnDelete);
			return;
		}
	}
	if(IBM_RTE_isMozilla() || IBM_RTE_isNetscape()){
		var ret = confirm(text);
		if (!ret) {
			return;
		}
	}
	for (i=0; i<numOfRows; i++) {
        var rowElement = rows[i];
        if (rowElement.cells.length > currColIndex) {
            var cellNode = rowElement.cells[currColIndex];
            rowElement.removeChild(cellNode);
        }
    }
    if (rows[0].cells.length == 0) {
            // When a row has no cell, delete the whole table;
            var parNode = currTable.parentNode;
            parNode.removeChild(currTable);
    }
}


function IBM_RTE_insertColumn(isLeft, editorName, error_columnInsert) {
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}

    var currColIndex = IBM_RTE_getColumnIndex(editorName);
	var currTable = IBM_RTE_getTable(editorName);
    if (currTable == null) return;
    var rows = currTable.rows;
    var numOfRows = rows.length;

	var numOfTHCells = 0;
	var numOfTDCells = 0;
	// check to see if the coulmn is header col.
	for (var k = 0 ; k < numOfRows ; k++ ){
		var row = rows[k];
		var col = row.cells[currColIndex];
		var tagname;
		if(IBM_RTE_isMozilla())
			tagname = col.nodeName;
		else
			tagname = col.tagName;
		if(tagname == 'TH')
			numOfTHCells++;
		else
			numOfTDCells++;
	}
	if ((numOfTDCells == 0) && isLeft){
		alert(error_columnInsert);
		return;
	}
	if(currColIndex == 0 && (row.cells[currColIndex+1] == null || row.cells[currColIndex+1] == 'undefined')){
		numOfTHCells = 1;
		numOfTDCells = numOfRows -1;
	}else if(currColIndex == 0){
		numOfTHCells = 0;
		numOfTDCells = 0;
		for (var k = 0 ; k < numOfRows ; k++ ){
			var row = rows[k];
			var col = row.cells[currColIndex+1];
			var tagname;
			if(IBM_RTE_isMozilla())   
				tagname = col.nodeName;
			else
				tagname = col.tagName;
			if(tagname == 'TH')
				numOfTHCells++;
			else
				numOfTDCells++;
		}
	}
    for (i=0; i<numOfRows; i++) {
        var rowElement = rows[i];
        if (rowElement.cells.length > currColIndex) {          
           var cellNode = rowElement.cells[currColIndex];
           var newCell;
		   if((numOfTHCells > 0) && (i < numOfTHCells))
			   newCell = IBM_RTE_createNewCell(editorName, true);
			else 
				newCell = IBM_RTE_createNewCell(editorName, false);
           if (isLeft) {
               rowElement.insertBefore(newCell, cellNode);
           }
           else {
               if (rowElement.cells.length == currColIndex + 1) {
                   rowElement.appendChild(newCell);
               }
               else {
                   cellNode = rowElement.cells[currColIndex + 1];
                   rowElement.insertBefore(newCell, cellNode);
               }
           }
        }
    } //End of for loop.
}


function IBM_RTE_insertColumnLeft(editorName, error_columnInsert) {
    IBM_RTE_insertColumn(true, editorName, error_columnInsert); 
}

function IBM_RTE_insertColumnRight(editorName, error_columnInsert) {
    IBM_RTE_insertColumn(false, editorName, error_columnInsert); 
}
// In IE, when move from one op to another, the selRange.parentElement 
// becomes BODY for the second op. You have to reselect a cell using 
// mouse or keyboard. This seems like an IE bug. 
// Mozilla does not have this problem. 
// The following two functions are to prove this.
function IBM_RTE_insertColumnLeft1(editorName) {
    var selRange = IBM_RTE_getSelectionRange(editorName);
}

function IBM_RTE_insertColumnRight1(editorName) {
    var selRange = IBM_RTE_getSelectionRange(editorName);
}

//Other issues with the advanced table support:
// 1. After delete a row/column, the selection goes away. This makes sense.
// 2. Undo/redo does not take effect since these new functions are DOM manipulation.
function IBM_RTE_insertPageBreak(editorName){
	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else{
			IBM_RTE_backup(editorName);
			IBM_RTE_getWindow(editorName).focus();
		}
	}

    var rand = Math.round(Math.random()*100000000000000000);
    var pageBreakID = "pagebreak_"+rand;

    var rng = IBM_RTE_getSelectionRange(editorName);

    newNode = IBM_RTE_getDocument(editorName).createElement("div");

    if (IBM_RTE_isMozilla()) {  
        //Since Mozilla does not support contentEditable attribute, 
        //per M. Kaply's suggestion, use <HR> as the page break indicator.
        newNode.setAttribute("id", pageBreakID);
        newNode.setAttribute("align", "center");
        var newChild = IBM_RTE_getDocument(editorName).createElement("HR");
        newNode.appendChild(newChild);
        newNode.style.pageBreakBefore = "always";

        //Use Koranteng's insertNodeIntoRange() utility function to insert the pagebreak node.
        rng.deleteContents();
        var container = rng.startContainer;
        var pos = rng.startOffset;

        var afterNode = null;
        if (container.nodeType == 3) {
		// when inserting into a textnode
		// we create 2 new textnodes
		// and put the insertNode in between
        var textNode = container;
        container = textNode.parentNode;
        var text = textNode.nodeValue;

		// split the text
        var textBefore = text.substr(0, pos);
        var textAfter = text.substr(pos);

        var beforeNode = IBM_RTE_getDocument(editorName).createTextNode(textBefore);
        afterNode = IBM_RTE_getDocument(editorName).createTextNode(textAfter);

		// insert the 3 new nodes before the old one
        container.insertBefore(afterNode, textNode);
        container.insertBefore(newNode, afterNode);
        container.insertBefore(beforeNode, newNode);

        // remove the old node
		container.removeChild(textNode);
        }
        else {
		// else simply insert the node
            // Somehow, after the new node is inserted, the rest of the container is selected and 
            // deselecting everyting does not seem to work. Mozilla problem ???
		afterNode = container.childNodes[pos];
		container.insertBefore(newNode, afterNode);
        }
	  if(afterNode != null) {
		rng.setEnd(afterNode, 0);
		rng.setStart(afterNode, 0);
	  }       
    }
    else {
        newNode.id = pageBreakID;
        newNode.align = "center";
        newNode.innerText = "------------ Page Break ------------";
        newNode.contentEditable = false;
        newNode.disabled = true;
        rng.pasteHTML(newNode.outerHTML);

        //It is strange that I can't set the pagebreak style before it is inserted.
        var pbNode = IBM_RTE_getDocument(editorName).getElementById(pageBreakID);
        pbNode.style.pageBreakBefore = "always";
    }
}

// When pagebreak indicators are inserted, a new getEditorContent will be 
// needed to remove the indicators for print.
function IBM_RTE_getEditorContentPrint(editorName, mode) {
    var text = "";
    var divNodes = IBM_RTE_getDocument(editorName).getElementsByTagName("div");

    if (IBM_RTE_isMozilla()) {  
        for(i=0; i<divNodes.length; i++) {
            if (divNodes[i].id.substring(0, 10) == "pagebreak_") {
                var childNode = divNodes[i].childNodes[0];
                divNodes[i].removeChild(childNode);
            }       
        }
    }
    else {
        for(i=0; i<divNodes.length; i++) {
            if (divNodes[i].id.substring(0, 10) == "pagebreak_") {
                text = divNodes[i].innerText;
                divNodes[i].innerText = "";
            }
        }
    }

    var editorCnt = IBM_RTE_getEditorContentPrint2(editorName, IBM_RTE_getEditorMode(editorName));  

	if (IBM_RTE_isMozilla()) {  
        for(i=0; i<divNodes.length; i++) {
            if (divNodes[i].id.substring(0, 10) == "pagebreak_") {
                var newChild = IBM_RTE_getDocument(editorName).createElement("HR");
                divNodes[i].appendChild(newChild);
            }       
        }
    }
    else {
        for(i=0; i<divNodes.length; i++) {
            if (divNodes[i].id.substring(0, 10) == "pagebreak_") {
                divNodes[i].innerText = text;
            }
        }
    }

    return editorCnt;
}

function IBM_RTE_getEditorContentPrint2(editorName, mode) {

    if (IBM_RTE_getEditorMode(editorName) == 0){
                 iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
		 IBM_RTE_getDocument(editorName).body.innerText = iHTML;
		 iText = IBM_RTE_getDocument(editorName).body.innerText;
		 IBM_RTE_getDocument(editorName).body.innerHTML = iText;
       iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
    }
    else if (IBM_RTE_getEditorMode(editorName) == 1) {
         if (IBM_RTE_isMozilla()) {
            var html = IBM_RTE_getDocument(editorName).body.ownerDocument.createRange();
            html.selectNodeContents(IBM_RTE_getDocument(editorName).body);
            iHTML = html.toString();
         }
         else {
            iHTML = IBM_RTE_getDocument(editorName).body.innerText;
         }
    }      

    return iHTML;
}

function IBM_RTE_doPrint(editorName, fileName) {
   
    cnt = IBM_RTE_getEditorContentPrint(editorName, 0);
	if (IBM_RTE_isMozilla()) //making the image URLs absolute
		cnt = IBM_RTE_Mozilla_putAbsoluteURLs(editorName,cnt);
     var win = window.open(fileName, "printPreview", "width=600,height=400,menubar=yes,location=no");
}

function IBM_RTE_Mozilla_putAbsoluteURLs(editorName,cnt)
{
	Text1 = cnt;
	var url = IBM_RTE_getDocument(editorName).URL;
	url = url.substring(0,url.lastIndexOf('/'));
	var matchesImg = Text1.match(/<img .*?src=(['"])[^\1]*?\1[\/]?>/gi);
		xx      = 0;
	if(matchesImg != null)
         while(true){
            if(matchesImg[xx] == null) break;

            var Smatch = matchesImg[xx];
            var cmpts = matchesImg[xx].match(/src=(["'])([^\1]*?)\1/i);
   
            var quot = cmpts[1];
            var src = cmpts[2];

            if(quot == "'"){
               var str2 = "" + "\"";
               var re2 = new RegExp(str2.toString(),"g");

               temp = "src='" + url+"/"+src + "' "; 
               matchesImg[xx] = matchesImg[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);

               // now replace matches[xx] in Text1
               var str9 = "" + Smatch.replace(/\\/g,"\\\\").replace(re2, "\\\"").replace(/\//,"\\\/"); 
               var re9 = new RegExp(str9.toString(),"g");

               Text1 = Text1.replace(re9, matchesImg[xx].toString()); 
             }
             else{
               
               var str10 = "" + "\"";
               var re10 = new RegExp(str10.toString(),"g");

               temp = "src=\"" + url+"/"+src + "\" ";    
      
               matchesImg[xx] = matchesImg[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);
               
               // now replace matches[xx] in Text1
               var str11 = "" + Smatch.replace(/\\/g,"\\\\").replace(re10, "\\\"").replace(/\//g,"\\\/");
               var re11 = new RegExp(str11.toString(),"g");
               
               Text1 = Text1.replace(re11, matchesImg[xx].toString()); 
            }
            xx++;
         }

	return Text1;
}

function IBM_RTE_paraSupport(editorName, buttonElement, locale, images, directory, isBidi){
    IBM_RTE_getWindow(editorName).focus();

	if(!IBM_RTE_isMozilla()){
		if(isAccessible) 
			this.opener.IBM_RTE_backup(editorName);
		else
			IBM_RTE_backup(editorName);
	}
    // calculate the position for the dialog relative to the parent window
    var winx = IBM_RTE_isMozilla() ? window.screenX : window.screenLeft;
    var winy = IBM_RTE_isMozilla() ? window.screenY : window.screenTop;
    var winw = IBM_RTE_isMozilla() ? window.innerWidth : IBM_RTE_getDocument(editorName).body.offsetWidth;
    var winh = IBM_RTE_isMozilla() ? window.innerHeight: IBM_RTE_getDocument(editorName).body.offsetHeight;

    var w = 330;
    var h = 330;
    var x = (winw-w)/2+winx;
    var y = (winh-h)/2+winy;

    var rand = Math.round(Math.random()*100000000000000000);
    var winID = "paraSupport_"+rand;
    var flags = "screenX="+x+",screenY="+y+",left="+x+",top="+y+",width="+w+",height="+h+",resizable=yes,status=no,scrollbars=no,dependent=yes,modal=yes";

	var paraWin = window.open(directory + 'paraSupport.jsp?locale=' + locale + '&images=' + images + '&editorName=' + editorName + '&isBidi=' + isBidi+ '&isAccessible='+isAccessible,winID, flags);
    paraWin.focus();
}

function IBM_RTE_findReplace(editorName, buttonElement, locale, images, directory, isBidi){
    IBM_RTE_getWindow(editorName).focus();

    // calculate the position for the dialog relative to the parent window
    var winx = IBM_RTE_isMozilla() ? window.screenX : window.screenLeft;
    var winy = IBM_RTE_isMozilla() ? window.screenY : window.screenTop;
    var winw = IBM_RTE_isMozilla() ? window.innerWidth : IBM_RTE_getDocument(editorName).body.offsetWidth;
    var winh = IBM_RTE_isMozilla() ? window.innerHeight: IBM_RTE_getDocument(editorName).body.offsetHeight;

    var w = 450;
    var h = 230;
    var x = (winw-w)/2+winx;
    var y = (winh-h)/2+winy;

    var rand = Math.round(Math.random()*100000000000000000);
    var winID = "FindReplace_"+rand;

    var flags = "screenX="+x+",screenY="+y+",left="+x+",top="+y+",width="+w+",height="+h+",resizable=yes,status=no,scrollbars=no,dependent=yes,modal=yes";
	if(IBM_RTE_isMozilla()){
		var win = IBM_RTE_getWindow(editorName);
		win.getSelection().collapse(IBM_RTE_getDocument(editorName).body,0);
	}
	var findReplWin = window.open(directory + 'findReplace.jsp?locale=' + locale + '&images=' + images + '&editorName=' + editorName + '&isAccessible=' + isAccessible+ '&isBidi=' + isBidi, winID, flags);
    findReplWin.focus();
}


//I don't like this way of restoring comments in Mozilla. It is a hack, 
//not a solution. However, since Mozilla currently does not support contentEditable
//attribute, we probably have to use Mozilla properrietary technologies to mimic IE 
//functions.
function IBM_RTE_MozillaRestoreComments(editorName) {
    if (IBM_RTE_isMozilla()) {
        var commentNodes = IBM_RTE_getDocument(editorName).getElementsByTagName("description");
        for (var i = 0; i < commentNodes.length; i++){  
            var cmtStyle = commentNodes[i].getAttribute("style");
            var XULNS = "http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul";
            var newNode = IBM_RTE_getDocument(editorName).createElementNS(XULNS, "description");
            newNode.setAttribute("value", commentNodes[i].getAttribute("value"));
            commentNodes[i].parentNode.replaceChild(newNode, commentNodes[i]);
 
			if (cmtStyle != "display: none;"){
                cmtStyle = "display:block; padding:5px; color:red !important; font-style: italic; font-size: 10pt;"
            }
            newNode.setAttribute("style", cmtStyle);
       }
    }
}

function IBM_RTE_getCell(editorName){
	var selRange = IBM_RTE_getSelectionRange(editorName);
	if(selRange == null)return null;
	if( selRange.text == null)return null;
	var currentRow= selRange.parentElement();
	if(currentRow.tagName=="TD" || currentRow.tagName=="TH"){
		return currentRow;
	}else{
		return null;
	}
}

function isBlank(val){
	if(val==null){return true;}
	for(var i=0;i<val.length;i++) {
		if ((val.charAt(i)!=' ')&&(val.charAt(i)!="\t")&&(val.charAt(i)!="\n")&&(val.charAt(i)!="\r")){return false;}
	}
	return true;
}

function IBM_RTE_changeContent(editorName){
var iHTML = IBM_RTE_getDocument(editorName).body.innerHTML;
iHTML= iHTML.replace(/\<TD\>\<\/TD\>/g, "<TD>&nbsp;</TD>");
IBM_RTE_getDocument(editorName).body.innerHTML=iHTML;
}

function IBM_RTE_validateAnchorTags(editorName, anchor)
{
	var quot;
	var i;
	var index;
	var attrList = new Array(" type=", " name="," hreflang="," rel="," rev="," accesskey="," shape="," coords="," tabindex="," onfocus="," onblur=", " onclick="," ondblclick="," onmouseup="," onmousedown="," onmouseover="," onmousemove="," onmouseout="," onkeypress="," onkeydown="," onkeyup="," id="," title="," lang="," dir="," class="," target=");		         
	
	var hrefIndex = anchor.indexOf("href=");

	if(hrefIndex == -1)	
		return anchor;

	if((index = anchor.indexOf("<",hrefIndex)) == -1) 
		index = hrefIndex;
	else 
		if((index = anchor.indexOf("/>")) == -1)
			return anchor;

	if(anchor.charAt(hrefIndex+5) != "\"")
		if(anchor.charAt(hrefIndex+5) != "\'")
			anchor = anchor.substring(0, hrefIndex+5) + "\"" + anchor.substring(hrefIndex+5);

	if(anchor.charAt(hrefIndex+5) == "\"") quot = "\""; else quot = "\'";

	index = anchor.indexOf(" charset=",hrefIndex);

	for(i=0; i<attrList.length; i++)
		if((anchor.indexOf(attrList[i],hrefIndex) < index && index != -1)  || index == -1) 
			if(anchor.indexOf(attrList[i],hrefIndex) != -1) 
				index = anchor.indexOf(attrList[i],hrefIndex);

	if(index != -1)
	{
		if(anchor.charAt(index-1) != quot)
		{
			if(anchor.charAt(index-1) == "\"")
				anchor = anchor.substring(0,index-1) + quot + anchor.substring(index);
			else 
				if(anchor.charAt(index-1) == "\'")
					anchor = anchor.substring(0,index-1)+quot+anchor.substring(index);
				else {
					    var xx=index;
						var flag = false;

						for(xx; xx > hrefIndex+5; xx--){
							if(anchor.charAt(xx) == " ") 
								continue;
							if(anchor.charAt(xx) == "\"" || anchor.charAt(xx) == "'" )
									return anchor;
							else
									return (anchor.substring(0, xx+1) + quot + anchor.substring(xx+1));
							}
					}
			}
	}
	else
	{
		var al = anchor.length;
		if(anchor.charAt(al-2) != quot)
		{
			if(anchor.charAt(al-2) == "\"")
				anchor = anchor.substring(0,al-2)+quot+">";
			else
				if(anchor.charAt(al-2) == "\'")
					anchor = anchor.substring(0,al-2)+quot+">";
				else 
					anchor = anchor.substring(0,al-1)+quot+">";
		}
	}

	return anchor;
}

function isCustomTag(str){
	var match = new String(str);
	str3 = "href=&quot;\"";
	str4 = "src=&quot;\"";
	str5 = "href=\"\"";
	str6 = "src=\"\"";
			if(match.indexOf(str3) == -1)
				if(match.indexOf(str4) == -1)
					if(match.indexOf(str5) == -1)
						if(match.indexOf(str6) == -1)
							return false;
	return true;
}

var index 	  = 0;           
var flag      = false; 
var backup    = new Array();

function IBM_RTE_backup(editorName){ // called on triggers
    
	if(flag){
		var removed = backup.splice(flag, backup.length - flag);
		
		for (var i = 0; i < removed.length; i++)
			delete removed[i];
	}
	
	backup[backup.length] = IBM_RTE_getDocument(editorName).body.innerHTML;
	index	 			  = backup.length - 1;
	flag 				  = false;
}

function IBM_RTE_handler(event) {// events triggering backup - space, backspace, return, del
	if (event) {
		switch (event.keyCode) {
			case 32: //space
				break;
			case 8://back
				IBM_RTE_backup(editorNameSaved);
				break;
			case 13://return
				IBM_RTE_backup(editorNameSaved);
				break;
			case 46://delete
				IBM_RTE_backup(editorNameSaved);
				break;
			default:
				return;
		}
	}
}

function IBM_RTE_undo(editorName){
	if (backup.length != 1){
		if(index == 0) 
			return;
	
	if(flag == false)
		IBM_RTE_backup(editorName);
	 
	IBM_RTE_getDocument(editorName).body.innerHTML = backup[index - 1];
	
	index = index - 1;
	}
	else{
	 if(flag == false){
		IBM_RTE_backup(editorName);// takes care of first backup if triggers not used

		IBM_RTE_getDocument(editorName).body.innerHTML = "";
		
		index = index - 1;
		}
	}
	
	 flag = index + 1;
}

function IBM_RTE_redo(editorName){
	
	if(index == backup.length - 1) return;
	//end of backup array
	IBM_RTE_getDocument(editorName).body.innerHTML = backup[index + 1];
	index = index + 1;
	flag 	  = index + 1;
}

function IBM_RTE_delayedBak() {
 if(backup[backup.length - 1] == "") IBM_RTE_backup(editorNameSaved);
  else
   if(IBM_RTE_getDocument(editorNameSaved).body.innerHTML != backup[backup.length - 1])
    IBM_RTE_backup(editorNameSaved);
}

function IBM_RTE_printBackup(){
	for(var i=0; i <= index; i++)
	 alert("<<" + i + ">>  " + backup[i]);
}

function IBM_RTE_doAccessibleCompliant(editorName, buttonElement, locale, images, directory, isBidi) {
        IBM_RTE_getWindow(editorName).focus();
	// calculate the position for the dialog relative to the parent window
    var winx = IBM_RTE_isMozilla() ? window.screenX : window.screenLeft;
    var winy = IBM_RTE_isMozilla() ? window.screenY : window.screenTop;
    var winw = IBM_RTE_isMozilla() ? window.innerWidth : IBM_RTE_getDocument(editorName).body.offsetWidth;
    var winh = IBM_RTE_isMozilla() ? window.innerHeight: IBM_RTE_getDocument(editorName).body.offsetHeight;

    var w = 450;
    var h = 230;
    var x = (winw-w)/2+winx;
    var y = (winh-h)/2+winy;

    var rand = Math.round(Math.random()*100000000000000000);
    var winID = "Toxhtml_"+rand;

    var flags = "screenX="+x+",screenY="+y+",left="+x+",top="+y+",width="+w+",height="+h+",resizable=yes,status=no,scrollbars=no,dependent=yes,modal=yes";
	var xhtmlWin = window.open(directory + 'makeAccessibilityCompliant.jsp?locale=' + locale + '&images=' + images + '&editorName=' + editorName + '&directory=' + directory + '&isBidi=' + isBidi, winID, flags);
	xhtmlWin.focus();
}

function addXMPTags(iHTML, remove)
{
	if(remove == true){
		if(iHTML.match(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i) != null){
			iHTML = iHTML.replace(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i, "");
		}
	return iHTML;
	}else{
		if(iHTML.match(/<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"><\/xmp>/i) == null){
					iHTML = "<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"></xmp>" + iHTML;
		}
		return iHTML;
	}
}

// only for gecko
var currentParam = -1;
var notnull = false;
var doneNodes = null;
var same_val_across_range_nodes = null;
function checkOverlap(selRange, editorName, which)
{
	if(IBM_RTE_isMozilla()){
		var bodyNode = IBM_RTE_getDocument(editorName).body;
		
		currentParam = -1;
		notnull = false;
		doneNodes = new Array();
		same_val_across_range_nodes = null;

		crawlDomTree(editorName, bodyNode, selRange, which);
		
		if(notnull == true){// display --font--
			return "notnull";
		}else if(same_val_across_range_nodes != null){// there are > 1 text nodes, but all have same font face
			return same_val_across_range_nodes;
		}else{
			return null;
		}
	}
}

function crawlDomTree(editorName, currentElement, selRange, which)
{
	if (currentElement){
		var j;
		var i=0;
		var currentElementChild=currentElement.childNodes[i];
		
		if(currentElementChild != null && currentElementChild.nodeType == 3){
			if(! checkElementAgain(currentElementChild)){
				var gotParam = MozillaIsNodeInRange(editorName, currentElementChild, selRange, which);
				doneNodes[doneNodes.length] = currentElementChild;

				if(currentParam == -1){
					currentParam = gotParam;
				}else	if(gotParam != -1 && gotParam != currentParam && notnull == false){
							if(notnull == false){
								notnull = true; //display --font--
							}
				}else if(gotParam != -1 && gotParam != null){
					same_val_across_range_nodes = gotParam;
				}
			}
		}
		
		while (currentElementChild){
			crawlDomTree(editorName, currentElementChild, selRange, which);
			i++;
			currentElementChild=currentElement.childNodes[i];
		}
	}
}

function checkElementAgain(node)
{
	for(var xxx=0; xxx < doneNodes.length; xxx++){
		if(doneNodes[xxx] == node){
			return true;
		}
	}
	return false;
}

function MozillaIsNodeInRange(editorName, node, selRange, which)
{
	var bodyRange = IBM_RTE_getDocument(editorName).createRange();
	bodyRange.setStartBefore(node);
	bodyRange.setEndAfter(node);

	if(bodyRange.compareBoundaryPoints(0, selRange) >= 0 &&
	   bodyRange.compareBoundaryPoints(3, selRange) <= 0){
				return MozillaGetParam(node, which); //yep, in range :)
	}else if(selRange.compareBoundaryPoints(3, bodyRange) <= 0 &&
		bodyRange.compareBoundaryPoints(0, selRange) <= 0 ){
				return MozillaGetParam(node, which); //yep, in range :)
	}

	return -1;
}

function MozillaGetParam(node, which)
{
	if(which == 1){
			while(node.nodeName != "BODY"){
				if(node.nodeName == "P"){
					if(node.style.fontFamily != "" && node.style.fontFamily != null){
							var mE = MozillaEval(node.style.fontFamily, 1);
							return mE;
					}
				}else if(node.nodeName == "SPAN"){
						if(node.style.fontFamily != "" && node.style.fontFamily != null){
							mE1 = MozillaEval(node.style.fontFamily, 1);
							return mE1;
						}
				}else if(node.nodeName == "FONT"){
					if(node.getAttribute("face") != "" && node.getAttribute("face") != null){
							return MozillaEval(node.getAttribute("face"), 1);
					}
				}
				node = node.parentNode;
			}

		return null;
	}else{//size
		while(node.nodeName != "BODY"){
			if(node.nodeName == "FONT"){
				return MozillaEval(node.getAttribute("size"), 0);
			}

			node = node.parentNode;
		}
		return null;
	}
}

function MozillaEval(fontParam, which)
{
	var fontFaces = new Array("Arial", "Bookman", "Courier", "Garamond", "Lucida Console", "Symbol", "Tahoma", "Times", "Trebuchet", "Verdana");
	var altFontFaces = new Array("arial,helvetica,sans-serif", "bookman old style,new york,times,serif","courier,monaco,monospace,sans-serif","garamond,new york,times,serif", "lucida console,sans-serif", "symbol,fantasy", "tahoma,new york,times,serif", "times new roman,new york,times,serif", "trebuchet ms,helvetica,sans-serif", "verdana,helvetica,sans-serif");

	var fontSizes = new Array("7pt", "9pt", "12pt", "14pt", "18pt", "24pt");

	if(which == 1){//face
		if(fontParam != null){
			fontParam = fontParam.replace(/,[ ]*/gi, ",");
		}

		var face;

		for(var xxx = 0; xxx < fontFaces.length; xxx++){
			if(fontParam.toLowerCase() == fontFaces[xxx].toLowerCase() || 
			   fontParam.toLowerCase() == altFontFaces[xxx].toLowerCase()){
				return fontFaces[xxx];
			}
		}
	}else{//size
		if(fontParam > 0 && fontParam < 7){
			pt = fontSizes[fontParam - 1];
		}else{
			fontParam = 3;
			fontParam = "12pt";
			pt = "12pt";
		}

		return fontParam;
	}
}
// only for gecko

function geckoTableDelete(editorName)
{
	var rng = IBM_RTE_getSelectionRange(editorName);
	var parNode = rng.startContainer;
	/**
	 * if (parNode is a text node){ 
	 *	 nothing  to do
	 * } else if(node at startOffset-1 is a table node){
	 *				delete it baby!
	 *          }
	 * }
	 */

	if(parNode.nodeType != 3){
		if(parNode.hasChildNodes){						
			var children = parNode.childNodes;			
			if(children[rng.startOffset-1].tagName == "P"){  
				var childChildren = children[rng.startOffset-1].childNodes;
				if(childChildren.length != 0){
					if(childChildren[childChildren.length - 1].tagName == "TABLE"){
						children[rng.startOffset-1].removeChild(childChildren[childChildren.length - 1]);
					}
				}
			} else if(children[rng.startOffset-1].tagName == "TABLE"){  
						parNode.removeChild(children[rng.startOffset-1]);
			}
		}
	}else if(rng.startOffset == 0){
		var textNode  = parNode;
		var parent      = parNode.parentNode;
		/**
		 * now, recursively try to find the leftSibling
		 *
		 */
		
		deleteLeftSibling(parent, textNode);

	}
}

function deleteLeftSibling(parent, curNode)
{
	// check if for the parent, the first child is curNode - else go one level up
	var children = parent.childNodes;
	if(children[0] == curNode && parent.tagName != "BODY"){
			deleteLeftSibling(parent.parentNode, parent);
	}else{
		if(curNode.tagName != "BODY"){
			var leftSibling = null;

			for(var xxx = 1; xxx < children.length; xxx++){
				if(children[xxx] == curNode){
					leftSibling = children[xxx - 1];
					break;
				}
			}
			
			if(leftSibling.tagName == "P"){  
				var children = leftSibling.childNodes;
				if(children.length != 0){
					if(children[children.length - 1].tagName == "TABLE"){
						leftSibling.removeChild(children[children.length - 1]);
					}
				}
			} else if(children[rng.startOffset-1].tagName == "TABLE"){  
						parNode.removeChild(children[rng.startOffset-1]);
			}
			if(leftSibling.tagName == "TABLE"){
				parent.removeChild(leftSibling);
			}
		}
	}
}

function printNodes(siblings)
{
	for(var xxx=0; xxx < siblings.length; xxx++){
		alert(siblings[xxx].tagName);
	}
}
// gecko only: backspace table delete

function IBM_RTE_removeDanglingP_BR(editorName)
{
	var bodyNode     = IBM_RTE_getDocument(editorName).body;
	var bodyChildren = bodyNode.childNodes;
	var xmpString     = "<xmp id=9669 contentEditable=false style=\"DISPLAY: inline\"></xmp>";
	/**
	 * IE will always have one node for this case - <P>&nbsp;</P>
	 * Mozilla #@#@#$ has a bit more:
	 *		<br>[#text]
	 *     <p style=".."><br></p>[#text]
	 *     <p style".."><br><br></p>[#text]
	 */
	if(IBM_RTE_isMozilla()){
		switch(bodyChildren.length){
			case 1:
						if(bodyChildren[0].nodeName.toLowerCase() == "br"){
							bodyNode.removeChild(bodyChildren[0]);
							return true; // we removed a <BR> node, so add one before toggling back to view mode.
						}else if(bodyChildren[0].nodeName.toLowerCase() == "p"){ 
							var pKids = bodyChildren[0].childNodes;
							switch(pKids.length){
								case 1:
									if(pKids[0].nodeName.toLowerCase() == "br"){
										bodyNode.removeChild(bodyChildren[0]);
										return true;
									}
								case 2:
									if(pKids[0].nodeName.toLowerCase() == "br" && 
									   pKids[1].nodeName.toLowerCase() == "br" ){
										bodyNode.removeChild(bodyChildren[0]);
										return true;
									}
							}

							return false;
						}else {
								return false;
						}
			case 2: // two nodes, one #text and one <BR> or <BR> inside a <P>
						var textNode = null
						var otherNode = null;

						if(bodyChildren[0].nodeName == "#text"){
							textNode = bodyChildren[0];
							otherNode = bodyChildren[1];
						}else if(bodyChildren[1].nodeName == "#text"){
									textNode = bodyChildren[1];
									otherNode = bodyChildren[0];
						}

						var removeOtherNode = false;
						if(textNode != null && textNode.data == "\n"){ // assure an empty text node
							if(otherNode.nodeName.toLowerCase() == "br"){
								removeOtherNode = true;
							}else 	if(otherNode.nodeName.toLowerCase() == "p"){
								// two cases, innerHTML can be <br> or <br><br>
								var kids = otherNode.childNodes;
								if(kids.length == 1){
									if(kids[0].nodeName.toLowerCase() == "br"){
										removeOtherNode = true;
									}
								}else if(kids.length == 2){
									if(kids[0].nodeName.toLowerCase() == "br" &&
									   kids[1].nodeName.toLowerCase() == "br"){
											removeOtherNode = true;
									}
								}
							}else if(otherNode.nodeName.toLowerCase() == "style" &&
							   otherNode.innerHTML == ""){
								removeOtherNode = true;
							}
							if(removeOtherNode){
								bodyNode.removeChild(otherNode);
								return true;
							}

							return false;
						}
			}
	}else{
		switch(bodyChildren.length){
			case 1: 
				 if(bodyChildren[0] != null &&
					bodyChildren[0].nodeType != 3 &&
				   bodyChildren[0].tagName.toLowerCase() != "p" && 
				  (bodyChildren[0].innerHTML == "" || 
				   bodyChildren[0].innerHTML.toLowerCase() == xmpString.toLowerCase())){ // IE
						bodyNode.removeChild(bodyChildren[0]);
						return false; // always!
				}
			case 2:
				if(bodyChildren[0] != null &&
				   bodyChildren[1] != null &&
				   bodyChildren[0].tagName.toLowerCase() == "p" && 
				  (bodyChildren[0].innerHTML == "" || 
				   bodyChildren[0].innerHTML.toLowerCase() == xmpString.toLowerCase()) &&
				   bodyChildren[1].tagName.toLowerCase() == "p" && 
				  (bodyChildren[1].innerHTML == "" || 
				   bodyChildren[1].innerHTML.toLowerCase() == xmpString.toLowerCase())){ // IE
						bodyNode.removeChild(bodyChildren[1]);
						bodyNode.removeChild(bodyChildren[0]);
						return false; // always!
				}
		}
	}
}