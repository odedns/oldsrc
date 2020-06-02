//In IE textRange.findText() does the text search and it is based upon the text range;
//Mozilla does not support findText in range, instead its window.find() invokes 
//window's find function, which is simpler and more straightforward 
//in the search/replace support.
//Two global variables are needed to keep track of the find/replace actions.
//They are used by findNext and replace only; replaceAll does not check these values.
//lastWordRange is used by IE support only.
var lastWordRange = null;  
var alreadyFound = false;

function IBM_RTE_isMozilla() {
    return navigator.product == 'Gecko';
}
//Extract user input info from the findReplace dialog.
//There are 4 fields to extract:
// 	findWhat, replaceWith, matchWholeWord, matchCase.
function extractInput() {
    var word = new Object();
    word.text = document.getElementById("findWhat").value;     
    word.replaceText = document.getElementById("replaceWith").value;     

	if(!IBM_RTE_isMozilla())
	{
    if (document.getElementById("matchWholeWord").checked == true) {
        word.matchWholeWord = 2;
    } else {
        word.matchWholeWord = 0;
		}
    }
    if (document.getElementById("matchCase").checked == true) {
        word.matchCase = 4;
    } else {
        word.matchCase = 0;
    }
    return word;
}

function getEditorDocRange(editorName) {
    var docRange = null;
    var doc = this.opener.IBM_RTE_getDocument(editorName);
    docRange = doc.body.createTextRange();
    return docRange.duplicate();
}

//This function is a utility function from the Mozilla sample.In this particular case, 
//we only need a portion of it, e.g. textNode manipulation. But it doesn't hurt to keep 
//the whole function. 
function insertNodeAtSelection(doc, win, insertNode) {
      // get current selection
      var sel = win.getSelection();
      // get the first range of the selection; (there's almost always only one range)
      var range = sel.getRangeAt(0);

      // deselect everything
      sel.removeAllRanges();
      // remove content of current selection from document
      range.deleteContents();

      // get location of current selection
      var container = range.startContainer;
      var pos = range.startOffset;
      // make a new range for the new selection
      range=doc.createRange();

      if (container.nodeType==3 && insertNode.nodeType==3) {
        // if we insert text in a textnode, do optimized insertion
        container.insertData(pos, insertNode.nodeValue);
        // put cursor after inserted text
        range.setEnd(container, pos+insertNode.length);
        range.setStart(container, pos+insertNode.length);
      } else {
        var afterNode;
        if (container.nodeType==3) {
          // when inserting into a textnode we create 2 new textnodes and put the insertNode in between
          var textNode = container;
          container = textNode.parentNode;
          var text = textNode.nodeValue;

          // text before the split
          var textBefore = text.substr(0,pos);
          // text after the split
          var textAfter = text.substr(pos);

          var beforeNode = doc.createTextNode(textBefore);
          var afterNode = doc.createTextNode(textAfter);
          // insert the 3 new nodes before the old one
          container.insertBefore(afterNode, textNode);
          container.insertBefore(insertNode, afterNode);
          container.insertBefore(beforeNode, insertNode);

          // remove the old node
          container.removeChild(textNode);
        } else {
          // else simply insert the node
          afterNode = container.childNodes[pos];
          container.insertBefore(insertNode, afterNode);
        }
        range.setEnd(afterNode, 0);
        range.setStart(afterNode, 0);
      }
      sel.addRange(range);
}


function findWord(editorName, word, offSet, fnText) {
    if (this.opener.IBM_RTE_isMozilla()){
        var doc = this.opener.IBM_RTE_getDocument(editorName);
        var win = this.opener.IBM_RTE_getWindow(editorName);
        //Needed by Mozilla 1.3
        win.focus();

        //This find invokes windows native Find() dialog.
        //This window.find() only works in Mozilla. IE does not support window.find().
        //var result = win.find(word.text, word.matchCase, false, false, word.matchWholeWord);
	    var result = win.find(word.text, word.matchCase, false, false, false);
        if (result) {
            alreadyFound = true;
        }
        else {
            alreadyFound = false;
            alert(fnText);
            //Deselect the last word found.
            var sel = win.getSelection();
            sel.removeAllRanges();
        }
    }
    else {
        //Create a new docRange from the editor document
        var docRange = getEditorDocRange(editorName);
        word.range = docRange;

        // Set the start of the range to the end point of the previous range
        if (lastWordRange) {
            word.range.setEndPoint('StartToEnd', lastWordRange);
        }

        //Do findText().
        if (word.range.findText(word.text, 99999999, word.matchWholeWord + word.matchCase)) {
            word.range.select();       
            word.range.scrollIntoView();

            // Move the text range to the end of the findWhat or replaceWith word.
            word.range.moveStart("character", offSet);     
            lastWordRange = word.range;
            alreadyFound = true;
        }		
        else {
            lastWordRange = null;
            alreadyFound = false;
            alert(fnText);
            //Deselect the last word found.
            this.opener.IBM_RTE_getDocument(editorName).selection.empty();
        }		
    }			      
}


function replaceWord(editorName, word,isAccessible) {
	if(!this.opener.IBM_RTE_isMozilla()){
		if(isAccessible=="false"){
			this.opener.IBM_RTE_backup(editorName);
		}else{
			this.opener.opener.IBM_RTE_backup(editorName);
		}
	}

    if (this.opener.IBM_RTE_isMozilla()){
        var win = this.opener.IBM_RTE_getWindow(editorName);
        var doc = this.opener.IBM_RTE_getDocument(editorName);
        var replaceNode = doc.createTextNode(word.replaceText);
        insertNodeAtSelection(doc, win, replaceNode);
    }
    else {
        var wordRange = this.opener.IBM_RTE_getSelectionRange(editorName);
        wordRange.text = word.replaceText;
    }
}

function onFindNext(editorName, newArray) {
	var findText = newArray[0];
	var fnText = newArray[1];
	if (document.getElementById("findWhat").value == "") {
        alert(findText);
        return;
    }
    var word = extractInput();
    findWord(editorName, word, word.text.length, fnText);    
}

function onReplace(editorName, newArray, isAccessible) {  //Accessible Added By Kiran
	var findText = newArray[0];
	var replaceText = newArray[2];
	var fnText = newArray[1];
	if (document.getElementById("findWhat").value == "") {
        alert(findText);
        return;
    }
	if (document.getElementById("replaceWith").value == "") {
        alert(replaceText);
        return;
    }
    var word = extractInput();
    if (!alreadyFound) {
        //If not already found, find one first.
        findWord(editorName, word, word.replaceText.length, fnText);   
    }
    else {
        //Replace the word and find the next.
        replaceWord(editorName, word, isAccessible); //isAccessible Added By Kiran Defect 143355 
        findWord(editorName, word, word.replaceText.length, fnText);         
    }
}

function onReplaceAll(editorName, newArray, isAccessible) { //isAccessible added By Kiran  Defect 143355
	var findText = newArray[0];
	var replaceText = newArray[2];
	var fnText = newArray[1];
	
	if (document.getElementById("findWhat").value == "") {
        alert(findText);
        return;
    }
	if (document.getElementById("replaceWith").value == "") {
        alert(replaceText);
        return;
    }
    var word = extractInput();
    if (this.opener.IBM_RTE_isMozilla()){
        var found = false;
  
        var doc = this.opener.IBM_RTE_getDocument(editorName);
        var win = this.opener.IBM_RTE_getWindow(editorName);
        //Needed by Mozilla 1.3
        win.focus();

       //reset cursor hack for replace all
		win.getSelection().collapse(doc.body,0);

        do {
            var found = win.find(word.text, word.matchCase, false, false, word.matchWholeWord);
            if (found) {
				replaceWord(editorName, word, isAccessible); //isAccessible added By Kiran  Defect 143355
            }
            else {
                found = false;
                alert(fnText);
            }
        } while(found)
    }
    else {
        var lastWordRng = null;

        do {
            var docRange = getEditorDocRange(editorName);
            word.range = docRange;

            //Move the start of the range to the end point of the previous range
            if (lastWordRng) {
                word.range.setEndPoint('StartToEnd', lastWordRng);
            }

            if (word.range.findText(word.text, 99999999, word.matchWholeWord + word.matchCase)) {
                    word.range.select();   
                // Move the text range to the end of the replaceWith word.
                word.range.moveStart("character", word.replaceText.length);   
                lastWordRng = word.range;

                //Replace the word
                replaceWord(editorName, word, isAccessible); //isAccessible added By Kiran  Defect 143355
            }		
            else {
                lastWordRng = null;
                alert(fnText);
            }		
        } while(lastWordRng)
    }
}