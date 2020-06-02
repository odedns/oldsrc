<% /* @copyright jsp */ %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.tags.*" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% String userAgent = request.getHeader("user-agent");
Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor);
String editorName = aEditor.getName();
String locale = (String)aEditor.getAttribute("locale");
String images = (String)aEditor.getAttribute("images");
String directory = (String)aEditor.getAttribute("directory");
String mode = (String)aEditor.getAttribute("mode");
ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle");
boolean editMode = mode.equalsIgnoreCase("edit"); 
String isBidi = (String)aEditor.getAttribute("isBidi"); 
String enableWindowControl = (String)aEditor.getAttribute("enableWindowControl"); 
boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  
boolean withSource = aEditor.getAttribute("addSource") != null; 
String evtHandler = (String)aEditor.getAttribute("evtHandler"); if (evtHandler == null) evtHandler = ""; 
String apostrophe = "\\u2019"; %>
<jsp:include page="toolbar_panel.jsp" flush="true" />
<% String controlChars[] = {"\\u0000", "\\u0001", "\\u0002", "\\u0003", "\\u0004", "\\u0005", "\\u0006", "\\u0007", "\\u0008", "\\u0009", "\\u000A", "\\u000B", "\\u000C", "\\u000D", "\\u000E", "\\u000F", "\\u0010", "\\u0011", "\\u0012", "\\u0013", "\\u0014", "\\u0015", "\\u0016", "\\u0017", "\\u0018", "\\u0019", "\\u001A", "\\u001B", "\\u001C", "\\u001D", "\\u001E", "\\u001F"}; %>
<script language="javascript">
var FirstComments="";
var theScriptToolbars = new Array();
var theScriptToolbars1 = new Array();
var apostrophe = "<%= apostrophe %>";
var addBrTag = false;
<%-- //TODO: Convert this to a single method and use the DOM instead of scriplets --%>
function IBM_RTE_<%= editorName %>doToggleView() {
	<% Vector theToolbars = aEditor.getOrderedToolbars();
    for (int t = 0; t < theToolbars.size(); t++) { 
		Toolbar aToolbar = (Toolbar)theToolbars.elementAt(t);
		String toolbarName = aToolbar.getName(); %>            
		theScriptToolbars[<%=t%>]="<%=toolbarName%>";
        <% Vector controls = aToolbar.getOrderedControls(); %>
		var scriptcontrolName=new Array();
        <% for (int c = 0; c < controls.size(); c++) {
			Control aControl = (Control)controls.elementAt(c); 
			String controlName = aControl.getName(); 
            if (!controlName.equals("ButtonSourceCode")) { %>
				scriptcontrolName[<%=c%>]="<%=controlName%>";
			<% }}%>
			theScriptToolbars1["<%=toolbarName%>"]=scriptcontrolName;
			<%} %>
			if (document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title == "<%= resourceBundle.getString("SourceMode") %>") {// to src mode
			IBM_RTE_setMode("<%= editorName%>", 1);
			addBrTag = IBM_RTE_removeDanglingP_BR("<%= editorName %>");
			if (IBM_RTE_isMozilla()) {// tag fix - begin
				var a = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("A");
				var xxx=0;
				while(a[xxx] != null){
					if(a[xxx].getAttribute("odc") != "" && a[xxx].getAttribute("odc") != null){ 
						a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
						a[xxx].removeAttribute("odc", 0);
					}
					xxx++;
			    }
	/*			var b = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("IMG");
				var xxx=0;
				while(b[xxx] != null){
					if(b[xxx].getAttribute("odc") != "" && b[xxx].getAttribute("odc") != null){
						b[xxx].setAttribute("src", b[xxx].getAttribute("odc"));
						b[xxx].removeAttribute("odc", 0);
					}
					xxx++;
				}
				*/
                var html = document.createTextNode(IBM_RTE_getDocument("<%= editorName %>").body.innerHTML);
				var iText = html.data;
				iText = iText.replace(/&#[0-2]?[0-9];/g,"");
			    iText = iText.replace(/&#3[01];/g,"");
				iText = stripControlCharacters(iText); 
				html.data = iText;
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = "";
                IBM_RTE_getDocument("<%= editorName %>").body.appendChild(html);
            }
            else {
				var a = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("A");
				var xxx=0;
				while(a[xxx] != null){
					if(a[xxx].odc != "" && a[xxx].odc != null) a[xxx].href = a[xxx].odc;
					a[xxx].removeAttribute("odc", 0);
					xxx++;
			    }
/*
				var b = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("IMG");
				var xxx=0;
				while(b[xxx] != null){
					if(b[xxx].odc != "" && b[xxx].odc != null) b[xxx].src = b[xxx].odc;
					b[xxx].removeAttribute("odc", 0);
					xxx++;
				}*/
                iHTML = IBM_RTE_getDocument("<%= editorName %>").body.innerHTML;
				iHTML = iHTML.replace(/&#[0-2]?[0-9];/g,"");
				iHTML = iHTML.replace(/&#3[01];/g,"");
				iHTML = 	stripControlCharacters(iHTML); 
				//iHTML = addXMPTags(iHTML, true);

				var test2=getFirstComments();
					if (test2.substring(0,4)=="<!--" && test2!=""  ){
		                IBM_RTE_getDocument("<%= editorName %>").body.innerText =test2+iHTML;
					}else{
					   IBM_RTE_getDocument("<%= editorName %>").body.innerText =iHTML;
					}
            }
            document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title = "<%= resourceBundle.getString("DesignMode") %>";
            document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").alt = "<%= resourceBundle.getString("DesignMode") %>";
            for (var i=0;i<theScriptToolbars.length;i++){
				var temparray=theScriptToolbars1[theScriptToolbars[i]];
				for (var j=0;j<temparray.length;j++){	
				var tmparr=	"<%= editorName %>"+theScriptToolbars[i]+temparray[j];
				document.getElementById(tmparr).style.display = 'none';
				}	
			}

            IBM_RTE_getWindow("<%= editorName %>").focus();            
        }else {
            // to view mode
			IBM_RTE_setMode("<%= editorName%>", 0);
			if (IBM_RTE_isMozilla()) {
                var html = IBM_RTE_getDocument("<%= editorName %>").body.ownerDocument.createRange();
                html.selectNodeContents(IBM_RTE_getDocument("<%= editorName %>").body);
			   // anchor tag fix - start
				var Text1   = html.toString();
				if(addBrTag){
					Text1 = "<BR>" + Text1;
				}
				var str3    = "" + "<a .*?[^\/]>";
				var re3     = new RegExp(str3.toString(),"gi");
				var matches = Text1.match(re3);
				var xx      = 0;
				if(matches != null)
					while(true){
						if(matches[xx] == null) break;

						var Smatch = matches[xx];
						var validAnchor = IBM_RTE_validateAnchorTags("<%= editorName %>", matches[xx].toString());
						matches[xx]  = matches[xx].replace(matches[xx].toString(),validAnchor);
						var str69  = "" + "href=\"'";
						var re69   = new RegExp(str69.toString(),"i");
						var str96  = "" + "href='\"";
						var re96   = new RegExp(str96.toString(),"i");
						if(re69.test(Smatch) == false && re96.test(Smatch) == false && IBM_RTE_isCustomTag(Smatch) == false)
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

				// anchor tag fix - end
				// img tag fix - start
				var matches = Text1.match(/<img.*?src=(['"])[^\1]*?\1.*?[\/]?>/gi);
				var xx      = 0;
				if(matches != null)
					while(true){
						if(matches[xx] == null) break;
						var Smatch = matches[xx];
						var str69  = "" + "src=\"'";
						var re69   = new RegExp(str69,"i");
						var str96  = "" + "src='\"";
						var re96   = new RegExp(str96,"i");
						if(re69.test(Smatch) == false && re96.test(Smatch) == false && IBM_RTE_isCustomTag(Smatch)== false)
						{
							var cmpts = matches[xx].match(/src=(["'])([^\1]*?)\1/i);
							if(cmpts != null)
							{
								var quot = cmpts[1];
								var src  = cmpts[2];
								temp = "src=\"" + src.replace(/\"/gi,"'") + "\" odc=\"" + src.replace(/\"/gi,"'") + "\" "; 	
								matches[xx] = matches[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);
								Text1 = Text1.replace(Smatch.toString(), matches[xx].toString()); 
							}
						}
						xx++;
					}
				Text1 = Text1.replace(/&#[0-2]?[0-9];/g,"");
			    Text1 = Text1.replace(/&#3[01];/g,"");
				Text1 = stripControlCharacters(Text1);
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML=Text1;
		    IBM_RTE_MozillaRestoreComments("<%= editorName %>");
            }else { // IE
				var Text1   = IBM_RTE_getDocument("<%= editorName %>").body.innerText;
				var str3    = "" + "<a .*?[^/]>";
				var re3     = new RegExp(str3.toString(),"gi");
				var matches = Text1.match(re3);
				var xx      = 0;
				if(matches != null)
					while(true){
						if(matches[xx] == null) break;
						var Smatch = matches[xx];
						var validAnchor = IBM_RTE_validateAnchorTags("<%= editorName %>", matches[xx].toString());
						matches[xx]  = matches[xx].replace(matches[xx].toString(),validAnchor);
						var str69  = "" + "href=\"'";
						var re69   = new RegExp(str69.toString(),"i");
						var str96  = "" + "href='\"";
						var re96   = new RegExp(str96.toString(),"i");
						if(re69.test(Smatch) == false && re96.test(Smatch) == false && IBM_RTE_isCustomTag(Smatch) == false){
							var cmpts = matches[xx].match(/href=(["'])([^\1]*?)\1/i);
							if(cmpts != null){
								var quot  = cmpts[1];
								var href  = cmpts[2];
								temp = "href=\"" + href.replace(/\"/gi,"'") + "\" odc=\"" + href.replace(/\"/gi,"'") + "\" "; 	
								matches[xx] = matches[xx].replace(/href=(["'])([^\1]*?)\1/i, temp);
								Text1 = Text1.replace(Smatch.toString(), matches[xx].toString());
							}
						}
						xx++;
					}
				IBM_RTE_getDocument("<%= editorName %>").body.innerText = Text1;
				Text1 = IBM_RTE_getDocument("<%= editorName %>").body.innerText;
				var matches = Text1.match(/<img.*?src=(['"])[^\1]*?\1.*?[\/]?>/gi);
				var xx      = 0;
				if(matches != null)
					while(true){
						if(matches[xx] == null) break;
						var Smatch = matches[xx];
						var str69  = "" + "src=\"'";
						var re69   = new RegExp(str69,"i");
						var str96  = "" + "src='\"";
						var re96   = new RegExp(str96,"i");
						if(re69.test(Smatch) == false && re96.test(Smatch) == false && IBM_RTE_isCustomTag(Smatch)== false){
							var cmpts = matches[xx].match(/src=(["'])([^\1]*?)\1/i);
							if(cmpts != null){
								var quot = cmpts[1];
								var src  = cmpts[2];
								temp = "src=\"" + src.replace(/\"/gi,"'") + "\" odc=\"" + src.replace(/\"/gi, "'") + "\" "; 	
								matches[xx] = matches[xx].replace(/src=(["'])([^\1]*?)\1/i, temp);
								Text1 = Text1.replace(Smatch.toString(), matches[xx].toString()); 
							}
						}
						xx++;
					}

				Text1 = Text1.replace(/&#[0-2]?[0-9];/g,"");
				Text1 = Text1.replace(/&#3[01];/g,"");
				Text1 = 	stripControlCharacters(Text1); 
			    IBM_RTE_getDocument("<%= editorName %>").body.innerText = Text1;
                iText = IBM_RTE_getDocument("<%= editorName %>").body.innerText;
				//iText = addXMPTags(iText, false);
				var	test3=retainFirstComments(iText);
				if (test3.substring(0,4)=="<!--" &&  test3!=""){
		        IBM_RTE_getDocument("<%= editorName %>").body.innerHTML =test3+iText;
				}
				else{
				   IBM_RTE_getDocument("<%= editorName %>").body.innerHTML=iText;
				}
          }
		  if(IBM_RTE_isMozilla()){
		      var a = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("A");
		 	  var xxx=0;
			  while(a[xxx] != null){
				if(a[xxx].getAttribute("odc") != "" && a[xxx].getAttribute("odc") != null)
					a[xxx].setAttribute("href", a[xxx].getAttribute("odc"));
				xxx++;
			  }

			  var b = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("IMG");
			  var xxx=0;
			  while(b[xxx] != null){
					if(b[xxx].getAttribute("odc") != "" && b[xxx].getAttribute("odc") != null)
						b[xxx].setAttribute("src", b[xxx].getAttribute("odc"));
						xxx++;
  		       }
		  }
		  else{
			  var a = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("A");
		 	  var xxx=0;
			  while(a[xxx] != null){
				if(a[xxx].odc != "" && a[xxx].odc != null)
					a[xxx].href= a[xxx].odc;
				xxx++;
			  }

			  var b = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("IMG");
			  var xxx=0;
			   while(b[xxx] != null){
				if(b[xxx].odc != "" && b[xxx].odc != null)
					b[xxx].src= b[xxx].odc;
					xxx++;
 		       }
		   }
            
            document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>";
            document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>";

            for (var i=0;i<theScriptToolbars.length;i++){
				var temparray=theScriptToolbars1[theScriptToolbars[i]];
				for (var j=0;j<temparray.length;j++){	
					var tmparr=	"<%= editorName %>"+theScriptToolbars[i]+temparray[j];
					document.getElementById(tmparr).style.display = '';
				}	
			}
            IBM_RTE_getWindow("<%= editorName %>").focus();            
        }
    }

function getFirstComments(){
     return FirstComments;
}
function setFirstComments(fcomment){
	FirstComments=fcomment;
}
function Trim(s){
	while ((s.substring(0,1) == ' ')|| (s.substring(0,1) == '\n') || (s.substring(0,1) == '\r'))
	  {
	    s = s.substring(1,s.length);
	  }
	return s;
}
function retainFirstComments(fullString){
	var substr1="";
	var substr2="";
	var gout=true;
    var	secondTime=false;
	while(gout==true){

		if ( fullString.indexOf("<!--") !=-1  || fullString.indexOf("-->")!=-1 ){
		    var  substr3=Trim(fullString.substring(0,	fullString.indexOf("<!--")));
			    if (substr3!="" && secondTime==true)
				    gout=false;
	             else{
			    	substr1= 
					fullString.substring(0,fullString.indexOf("-->")+3);
				    substr2=substr2+substr1;
				fullString=fullString.substring(fullString.indexOf("-->")+3,fullString.length);
				secondTime=true;
 	           }//if1 end
     }// outer if

	 else {
	    gout=false;
	    }// outer end
  }// for while
	setFirstComments(substr2);
	return substr2;
 }//function end

    // Define JS object array to hold window controls data
function winControls(name, definition, editor, loadUrl, keyModifier, keyAlphaNum, keyFile, keySequence, keyName, width, height, left, top) {

	this.name = name;
	this.definition = definition;
	this.editor = editor;
	this.loadUrl = loadUrl;
	this.keyModifier = keyModifier;
	this.keyAlphaNum = keyAlphaNum;
	this.keyFile = keyFile;
	this.keySequence = keySequence;
	this.keyName = keyName;
	this.width = width;
	this.height = height;
	this.left = left;
	this.top = top;
	this.accesswindow = null;
}

var <%= editorName %>theWinControls = new Array();

function IBM_RTE_<%= editorName %>getWinControls() {
	var i = 0;
	<!-- Big for loop to get all window controls information -->
	<% Vector theWinControls = aEditor.getOrderedWindowControls();             
	 for (int t = 0; t < theWinControls.size(); t++) { 
		   WindowControl theWinControl = (WindowControl)theWinControls.elementAt(t); 
		   String winControlName = (String)theWinControl.getName(); 
		   String winControlDefinition = (String)theWinControl.getDefinition(); 
		   String winControlEditor = (String)theWinControl.getAttribute("editor"); 
		   String winControlWidth = (String)theWinControl.getAttribute("width"); 
		   String winControlHeight = (String)theWinControl.getAttribute("height");
		   String winControlLeft = (String)theWinControl.getAttribute("left"); 
		   String winControlTop = (String)theWinControl.getAttribute("top"); %>
		  <!-- Position the window control at the center of the screen as default -->
		  var winLeft;
		  <% if (winControlLeft.equals("")) { %>
				 winLeft = (screen.width - <%= winControlWidth %>) / 2;
		  <% } else { %>
				 winLeft = "<%= winControlLeft %>";
		  <% } %>
		  var winTop;
		  <% if (winControlTop.equals("")) { %>
				 winTop = (screen.height - <%= winControlHeight %>) / 2;
		  <% } else { %>
				 winTop = "<%= winControlTop %>";
		  <% } 
		   String winControlKeyName = (String)theWinControl.getKeyName();
		   String winControlKeySequence = (String)theWinControl.getAttribute("keySequence");
		   String winControlKeyFile = (String)theWinControl.getAttribute("keyFile"); 
		   Vector winControlParams = (Vector)theWinControl.getAttribute("params"); 
		   Vector winControlParamValues = (Vector)theWinControl.getAttribute("value"); %>  
<!-- Construct window loading URL, JSP plus basic query strings, including locale, image dir, editor name, and Bidi flag. -->
		  <% String basicQueryStr = "?locale=" + locale + "&images=" + images + "&editorName=" + editorName + "&isBidi=" + isBidi; %>
		  var winUrl = "<%= winControlDefinition %>";
		  winUrl += "<%= basicQueryStr %>" + "&isAccessible=true";    
		  <!-- All the parameters added by <addWindowControlParams> are passed to the popup window via query strings in request -->
		  <% String extraQueryStr = "";
			if ((winControlParams.size() > 0)&&(winControlParamValues.size() > 0)) { 
			for (int m = 0; m < winControlParams.size(); m++) {
			   extraQueryStr += "&" + winControlParams.elementAt(m) + "=" + winControlParamValues.elementAt(m);
			}%>
	   winUrl += "<%= extraQueryStr %>";    
		  <% } %>
<!-- Get the default toolbar name list per toolbar name flag in <addWindowControl> -->
<% 
			 String addToolbarNames = (String)theWinControl.getAttribute("addToolbarNames"); 
			 if (addToolbarNames.equalsIgnoreCase("yes")) {
				 theToolbars = aEditor.getOrderedToolbars(); 
				 String toolbarNameList = "toolbarNameList=";
				 String toolbarCtrlNameList = "toolbarCtrlNameList=";
				 for (int m = 0; m < theToolbars.size(); m++) {
					 Toolbar aToolbar = (Toolbar)theToolbars.elementAt(m);
					 String toolbarName = aToolbar.getName();
					 toolbarNameList += editorName + toolbarName +"; ";
					 Vector controls = aToolbar.getOrderedControls();
					 int ctrlNum = controls.size();
					 if (!withSource)
						  ctrlNum = controls.size() - 1;
					 for (int n = 0; n < ctrlNum; n++) {
						 Control aControl = (Control)controls.elementAt(n); 
						 String controlName = aControl.getName(); 
						 toolbarCtrlNameList += editorName + toolbarName + controlName +"; ";
					 }
				 }           
				 int lind1 = toolbarNameList.lastIndexOf(";");
				 if (lind1 != -1)
					 toolbarNameList = toolbarNameList.substring(0, lind1);
				 int lind2 = toolbarCtrlNameList.lastIndexOf(";");
				 if (lind2 != -1)
					 toolbarCtrlNameList = toolbarCtrlNameList.substring(0, lind2);
		  %>
winUrl += "&" + "<%= toolbarNameList %>" + "&" + "<%= toolbarCtrlNameList %>";
		  <% } %>
 <!-- Retrieve keystroke sequence, key modifier and alphanumeric key, from key sequence or key properties file -->
 <%  		int j = 0;
			String winControlKeyModi = null;
			String winControlKeyCode = null;
// Ignore any key sequence properties file if keystroke sequence is set at tag lib call;
			int ind = winControlKeySequence.indexOf(":");
			if (ind != -1) { 
				 winControlKeyModi = winControlKeySequence.substring(0, ind).trim();
				 winControlKeyCode = winControlKeySequence.substring(ind+1).trim();
			}                    
// Look up the properties file for keystroke sequence
			else {
				 String theKeyFile = null; 

// Ignore default key sequence properties file if key file is set at tag lib call;
				 if ((ind = winControlKeyFile.indexOf(".")) != -1) 
					theKeyFile = winControlKeyFile.substring(0, ind).trim();
				 else 
					theKeyFile = "KeySequence";
				 ResourceBundle keyBundle = null;
				 try { 
					 keyBundle = ResourceBundle.getBundle("com.ibm.wps.odc.editors." + theKeyFile, LocaleHelper.getLocale(locale)); 
				 } 
				 catch (MissingResourceException e1) { 
					 try {
						 keyBundle = ResourceBundle.getBundle("com.ibm.pvc.wps.docEditor.keysequence." + theKeyFile, LocaleHelper.getLocale(locale)); 
					 }
					 catch (MissingResourceException e2) { 
						 keyBundle = null; 
					 }
				 }
				 if (keyBundle == null) {
					 j = -1;
				 }else {
					 String keySeq = null;
//Support user-agent, keyName should be in the format "name.ie, or name.mozilla, or name";
					if (winControlKeyName.indexOf(".") == -1) { 
						// keyName without user-agent extension;
						 if (isMozilla) {
							 try{
								 keySeq = keyBundle.getString(winControlKeyName + ".mozilla");
							 }catch(Exception e) {
								 keySeq = null;
							 }
						 }else {
							 try{
								 keySeq = keyBundle.getString(winControlKeyName + ".ie");
							 }catch(Exception e) {
								 keySeq = null;
							 }
						 }
						 if (keySeq == null) { // fall back to name without extension
							 try{
								 keySeq = keyBundle.getString(winControlKeyName);
							 }catch(Exception e) {
								 keySeq = null;
							 }
						 }                 
					}else { //keyName with user-agent extension;
						 try{
							 keySeq = keyBundle.getString(winControlKeyName);
						 }catch(Exception e) {
							 keySeq = null;
						 }
					} // End of key name extension checking
					if (keySeq == null) // Can't find winControlKeyName in properties file
						 j = -1;
					else {
						  // key sequence must be in formate "keymodifier:keyalphanumeric"
						  ind = keySeq.indexOf(":");
						  if (ind != -1) { 
							  winControlKeyModi = keySeq.substring(0, ind).trim();
							  winControlKeyCode = keySeq.substring(ind+1).trim();
						  }                    
					} // End of keySeq != null
				 } // End of keyBundle != null
			} // End of lookup properties file
			if ((winControlKeyModi == null) || (winControlKeyCode == null)){
				j = -1; // key sequence syntax is incorrect.
			}
			if ( j == -1) {
				winControlKeyModi  = null;
				winControlKeyCode  = null;
			}
%>                  
		  <!-- Save data to the JS object -->
  <%= editorName %>theWinControls[i] = new winControls("<%= winControlName %>", "<%= winControlDefinition %>", "<%= winControlEditor %>", winUrl, "<%= winControlKeyModi %>", "<%= winControlKeyCode %>", "<%= winControlKeyFile %>", "<%= winControlKeySequence %>", "<%= winControlKeyName %>", "<%= winControlWidth %>", "<%= winControlHeight %>", winLeft, winTop);
		  i++;
	<% } //End of for loop %>
}
function IBM_RTE_<%= editorName %>enablekeypress(editorName) {
//      IBM_RTE_getDocument(editorName).editorName = editorName;

	if (IBM_RTE_isMozilla()) {
		IBM_RTE_getDocument(editorName).addEventListener("keypress", IBM_RTE_<%= editorName %>keypress, false);
		IBM_RTE_getDocument(editorName).addEventListener("keyup", IBM_RTE_<%= editorName %>keyup, false);
		IBM_RTE_getDocument(editorName).addEventListener("mouseup", IBM_RTE_<%= editorName %>mouseup, false);
	}else {
	IBM_RTE_getDocument(editorName).attachEvent("onkeypress",IBM_RTE_<%= editorName %>keypress);
	IBM_RTE_getDocument(editorName).attachEvent("onkeyup",IBM_RTE_<%= editorName %>keyup);
	IBM_RTE_getDocument(editorName).attachEvent("onmouseup", IBM_RTE_<%= editorName %>mouseup);
			// _for_manual_undo_redo_
	IBM_RTE_getDocument(editorName).attachEvent("onkeydown",IBM_RTE_handler);
	// _for_manual_undo_redo_
	} 
}

function IBM_RTE_<%= editorName %>mouseup(eventObj) 
{
	IBM_RTE_modFormatList(false);
	IBM_RTE_modFontSizeList(false);
	IBM_RTE_modFontFaceList(false);
}
// To open a regular window as the toolbar window, 
// need to set the restriction to allow one RTE at a time for now;
function IBM_RTE_<%= editorName %>keypress(eventObj) {
	var editorName;
	var evt;
	var charCode;
	var wcp_optionsWin;
	//PMR fix
	if(!IBM_RTE_isMozilla()){
		var rownat=IBM_RTE_getCell('<%= editorName %>');
		if(rownat==null){
		}else{
			var rowvalue=rownat.firstChild.data;
			if(rowvalue.charCodeAt(0)==160){
				rownat.firstChild.data="";
			}
		}
	}
	//PMR fix
	// Attach key press event 
	if( IBM_RTE_isMozilla()) {
		editorName = eventObj.target.ownerDocument.editorName;
		evt = eventObj;
		charCode = evt.charCode;
		if(evt.keyCode==13){ 		//for paragraph support in Mozilla on Key enter press
			IBM_RTE_Mozilla_addPTags("<%= editorName %>");	
		}
	}else {
		var len = window.frames.length;
		for (var i=0; i<len; i++) {
			if (window.frames[i].event) {
				evt = window.frames[i].event;
				var doc = evt.srcElement;
				//  editorName = doc.ownerDocument.editorName;
				charCode = evt.keyCode; 
				break;
			} 
		} 
		if(evt.keyCode == 13){
			IBM_RTE_IE_addPTags("<%= editorName%>");
		}
	} 
	if(!IBM_RTE_isMozilla())
		if(IBM_RTE_getCell("<%= editorName %>") != null) IBM_RTE_backup("<%= editorName %>");

	// Check keystroke sequences and launch popup windows
	for (var i = 0; i < <%= editorName %>theWinControls.length; i++) {
		if (charCode != <%= editorName %>theWinControls[i].keyAlphaNum) continue;
		switch (<%= editorName %>theWinControls[i].keyModifier){
			case "17":
				if (evt.ctrlKey == true) {
					if (!IBM_RTE_isMozilla()) {
						evt.cancelBubble;
						evt.returnValue = false;
					}			
					if (charCode == <%= editorName %>theWinControls[i].keyAlphaNum) {
						var jrl = <%= editorName %>theWinControls[i].loadUrl;
						var wcp_win = window.open(jrl, <%= editorName %>theWinControls[i].name, 'scrollbars=0, toolbar=0, statusbar=0, , width=' + <%= editorName %>theWinControls[i].width + ', height=' + <%= editorName %>theWinControls[i].height + ', left=' + <%= editorName %>theWinControls[i].left + ', top=' + <%= editorName %>theWinControls[i].top);
						wcp_win.focus();                       
					}
				}
	// Most of alt+key sequences are reserved; developers would have to find out valid key combinations;
	// put it here for future extension;
			case "18":
				if (evt.altKey == true) {
					if (IBM_RTE_isMozilla())
					evt.preventDefault();
					else {
						evt.cancelBubble;
						evt.returnValue = false;
					}			
					if (charCode == <%= editorName %>theWinControls[i].keyAlphaNum) {
						var jrl = <%= editorName %>theWinControls[i].loadUrl;
						var wcp_win = window.open(jrl, <%= editorName %>theWinControls[i].name, 'scrollbars=0, toolbar=0, statusbar=0, width=' + <%= editorName %>theWinControls[i].width + ', height=' + <%= editorName %>theWinControls[i].height + ', left=' + <%= editorName %>theWinControls[i].left + ', top=' + <%= editorName %>theWinControls[i].top);
						wcp_win.focus();                       
					}
				}

	// Most of key modifier sequences are reserved; to facilitate future extension, 
	// key modifier 19 is set to the combination of shift+ctrl, this will give 
	// developers a lot more choices. 
			case "19":
				if (evt.shiftKey && evt.ctrlKey) {
					if (IBM_RTE_isMozilla())
						evt.preventDefault();
					else {
						evt.cancelBubble;
						evt.returnValue = false;
					}			
					if (charCode == <%= editorName %>theWinControls[i].keyAlphaNum) {
						var jrl = <%= editorName %>theWinControls[i].loadUrl;
						var wcp_win;
						try{
							if(<%= editorName %>theWinControls[i].accesswindow != null){
								<%= editorName %>theWinControls[i].accesswindow.focus();
								if(!IBM_RTE_isMozilla()){
									break;
								}
							}
						}catch(e){}
						wcp_win= window.open(jrl, <%= editorName %>theWinControls[i].name, 'scrollbars=0, toolbar=0, statusbar=0, width=' + <%= editorName %>theWinControls[i].width + ', height=' + <%= editorName %>theWinControls[i].height + ', left=' + <%= editorName %>theWinControls[i].left + ', top=' + <%= editorName %>theWinControls[i].top);
						<%= editorName %>theWinControls[i].accesswindow = wcp_win;
						wcp_win.focus(); 
					}
				}
		} // End of switch.
	} // End of for loop.
// gecko only: backspace table delete
	if(IBM_RTE_isMozilla()){
		if(eventObj.keyCode == 8){
			if (document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title != "Design Mode"){ 
				geckoTableDelete("<%= editorName %>");	
			}     
		}     
	}
	// gecko only: backspace table delete
}

function IBM_RTE_<%= editorName %>keyup(eventObj) {
	if(eventObj.charCode != 13 && eventObj.keyCode != 13){
	 IBM_RTE_modFormatList(false);
	 IBM_RTE_modFontSizeList(false);
	 IBM_RTE_modFontFaceList(false);
	}
	 if(!IBM_RTE_isMozilla()){
		var rownat=IBM_RTE_getCell('<%= editorName %>');
		if(rownat==null){
		}else{
			var rowvalue=rownat.firstChild.data;
			if(rowvalue==""){
			  rownat.innerHTML="&nbsp;";
			}
		}
	}
}
function IBM_RTE_<%= editorName %>init(editorName) {
<% if (editMode) { %>
   if (IBM_RTE_isMozilla()) {
     setTimeout("IBM_RTE_<%= editorName %>enablekeypress('" +editorName + "')", 1500);
     setTimeout("IBM_RTE_setDesignMode('" + editorName + "')", 2500);
     <% if (!evtHandler.equals("")) { %>
		setTimeout("<%= evtHandler %>", 1500);
      <% } %>
   }else {
  //When using body.contentEditable to replace designMode=on, IE needs timeout as well.
  //Modified in 5.1 release to support Annotation feature for PDM version RTE.
      setTimeout("IBM_RTE_<%= editorName %>enablekeypress('" +editorName + "')", 1500); 
      setTimeout("IBM_RTE_setDesignMode('" + editorName + "')", 1500);
     <% if (!evtHandler.equals("")) { %>
        <%= evtHandler %>;                 
    <% } %>
    }
<% }
 if (enableWindowControl.equalsIgnoreCase("yes")) { %>
 // These functions are to enable accessibility.
   IBM_RTE_<%= editorName %>getWinControls();
   <% } %>
    if (IBM_RTE_isMozilla()) {
     IBM_RTE_getWindow("<%= editorName %>").focus();            
    }
}
IBM_RTE_<%= editorName %>init("<%= editorName %>");
//init'ed the control character render
var renderedCtrlChars = new Array(<% for(int i = 0; i < controlChars.length; i++){ %><% if(i+1 != controlChars.length){%>"<%= controlChars[i] %>", <%}else{%>"<%= controlChars[i] %>"<%}%><% } %>);

function IBM_RTE_Mozilla_addPTags(editorName){
	var rng = IBM_RTE_getSelectionRange(editorName);
		for(var j = 1; j <= 7; j++)
		{
			var h = IBM_RTE_getDocument(editorName).body.getElementsByTagName("h"+j);
			var i = 0;
			while(h[i]){
				var bodyRange = IBM_RTE_getDocument(editorName).createRange();
				var inRange = false;
				paraRange = bodyRange;
				paraRange.setStartBefore(h[i]);
				paraRange.setEndAfter(h[i]);
				var START_TO_START = 0;
				var END_TO_START = 3;
				if(paraRange.compareBoundaryPoints(START_TO_START,rng) >= 0){
					if(paraRange.compareBoundaryPoints(END_TO_START,rng) <= 0)
						inRange = true;
				}
				else if(rng.compareBoundaryPoints(END_TO_START,paraRange) <= 0)
						inRange = true;
				if(inRange){
					h[i].innerHTML = "odcH"+j+h[i].innerHTML;
				}
				i++;
			}
		}
		IBM_RTE_getDocument(editorName).execCommand("formatblock", false, "P");

		var par = IBM_RTE_getDocument(editorName).body.getElementsByTagName("P");
		var pi = 0;
		var index;
		while(par[pi]){
			if((index = par[pi].innerHTML.indexOf("odcH"))!= -1)
			{
				var hNo = par[pi].innerHTML.charAt(index+4);
				var node = IBM_RTE_getDocument(editorName).createElement("h"+hNo);
				var newNode = par[pi].parentNode.insertBefore(node,par[pi]);
				var iHTML = par[pi].innerHTML.substring(index+5);
				par[pi].parentNode.removeChild(par[pi]);
				newNode.innerHTML = iHTML;
			}
			pi++;
		}
		for(var j = 1; j <= 7; j++){
			var h = IBM_RTE_getDocument(editorName).body.getElementsByTagName("h"+j);
			var i = 0;
			while(h[i]){
				if(h[i].innerHTML.indexOf("odcH")!= -1){
					h[i].innerHTML = h[i].innerHTML.substring(5);;
				}
				i++;
			}
		}
		//For Setting Pararaph Line Spacing to single
		var ps = IBM_RTE_getDocument(editorName).body.getElementsByTagName("P");
		var psi = 0;
			while(ps[psi] != null){	
				if(ps[psi].style.marginTop == ""){
					ps[psi].style.marginBottom = "10px";
					ps[psi].style.marginTop = "10px";
					if(ps[psi].style.margin=="")
						ps[psi].style.margin = "0px";
					if(ps[psi].style.LineHeight=="")
						ps[psi].style.LineHeight = "normal";
				}
				
				psi++;
			}//End Of While
}
function IBM_RTE_IE_addPTags(editorName){
	var elem = document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg");
	if(elem != null && elem.title != "Design Mode")
	{
		var ps = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("P");
		if(ps.length == 0){
			var bodyRange = IBM_RTE_getDocument("<%= editorName %>").body.createTextRange();
			if(bodyRange.text != ""){
				<%-- Header Fix IE--%>
				var rng = IBM_RTE_getSelectionRange("<%= editorName %>");
				var parNode = rng.parentElement();
				var nodeName = parNode.tagName;
				if(!(nodeName == "H1" || nodeName == "H2" || nodeName == "H3" || nodeName == "H4" || nodeName == "H5" || nodeName == "H6")) <%-- Header Fix IE--%>
				{
					IBM_RTE_getDocument("<%= editorName %>").execCommand("formatblock", false, "<p>");
					ps = IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName("P");
				}	
			}
		}
		var psi = 0;
		while(ps[psi] != null){	
			if(ps[psi].style.marginTop == ""){
				ps[psi].style.marginBottom = "1px";
				ps[psi].style.marginTop = "1px";
				if(ps[psi].style.margin=="")
					ps[psi].style.margin = "0px";
				//Condition to avoid double coming of the same attribute
				if(ps[psi].style.LineHeight=="")
					ps[psi].style.LineHeight = "normal";
			}
			psi++;
		}//End Of While
	}
}
function IBM_RTE_isCustomTag(str){
	var match = new String(str);
	str1 = "href="+"<%= apostrophe%>"+"\"";
	str2 = "src="+"<%= apostrophe%>"+"\"";
	str3 = "href=&quot;\"";
	str4 = "src=&quot;\"";
	str5 = "href=\"\"";
	str6 = "src=\"\"";
	if(match.indexOf(str1) == -1)
		if(match.indexOf(str2) == -1)
			if(match.indexOf(str3) == -1)
				if(match.indexOf(str4) == -1)
					if(match.indexOf(str5) == -1)
						if(match.indexOf(str6) == -1)
							return false;
	return true;
}
function IBM_RTE_setMode(editorName, mode){
   IBM_RTE_getDocument(editorName).mode = mode;
}
function IBM_RTE_getFormat(editorName){
	var selRange = IBM_RTE_getSelectionRange(editorName);
if(selRange == null )return null; if(selRange.text == null)return null;
	if(!IBM_RTE_isMozilla()){
		var node = selRange.parentElement();
		while(node.tagName != "BODY"){
			if(node.tagName.length == 2 && node.tagName.charAt(0) == "H" && (node.tagName.charAt(1) == 1 || node.tagName.charAt(1) == 2 || node.tagName.charAt(1) == 3 || node.tagName.charAt(1) == 4 || node.tagName.charAt(1) == 5 || node.tagName.charAt(1) == 6)){//H[1-6]
				return "<" + node.tagName + ">";
			}else{
				node = node.parentElement;
			}
		}
		return null;
	}else{
		var node = selRange.startContainer;
		node      = node.parentNode;

		while(node.nodeName != "BODY"){
			if(node.nodeName.length == 2 && node.nodeName.charAt(0) == "H" && (node.nodeName.charAt(1) == 1 || node.nodeName.charAt(1) == 2 || node.nodeName.charAt(1) == 3 || node.nodeName.charAt(1) == 4 || node.nodeName.charAt(1) == 5 || node.nodeName.charAt(1) == 6)){//H[1-6]
				return "<" + node.nodeName + ">";
			}else{
				node = node.parentNode;
			}
		}
	return null;
	}
}

function IBM_RTE_getFontSize(editorName)
{
	var selRange = IBM_RTE_getSelectionRange(editorName);
if(selRange == null)return null; if(selRange.text == null)return null;
	if(!IBM_RTE_isMozilla()){
		return 	selRange.queryCommandValue("FontSize");
	}else{
		var startNode = selRange.startContainer;
		var endNode = selRange.endContainer;

		if(startNode != endNode){
			return checkOverlap(selRange, editorName, 0); // size --> 0
		}
		var node = startNode.parentNode;
		while(node.nodeName != "BODY"){
			if(node.nodeName == "FONT"){
				return node.getAttribute("size");
			}else{
				node = node.parentNode;
			}
		}

		return null;
	}
}

function IBM_RTE_getFontFace(editorName)
{
	var selRange = IBM_RTE_getSelectionRange(editorName);
if(selRange == null)return null; if(selRange.text == null)return null;

	if(!IBM_RTE_isMozilla()){
		return selRange.queryCommandValue("FontName");
	}
	else{
		var startNode = selRange.startContainer;
		var endNode = selRange.endContainer;

		if(startNode != endNode){
			return checkOverlap(selRange, editorName, 1); // face --> 1
		}else{
			var node = startNode.parentNode;
			while(node.nodeName != "BODY"){
				if(node.nodeName == "SPAN"){
					if(node.style.fontFamily != "" && node.style.fontFamily != null){
							return node.style.fontFamily;
					}
				}
				if(node.nodeName == "P"){
					if(node.style.fontFamily != "" && node.style.fontFamily != null){
							return node.style.fontFamily;
					}
				}
				node = node.parentNode;
			}
			return null;
		}
	}
}

function IBM_RTE_modFormatList(flag)
{
	if(flag == false){
		var formatInfo =  IBM_RTE_getFormat("<%= editorName %>");
		var node = this.document.getElementById("<%= editorName %>" + "ToolbarFormatListFontFormat");
		var kids = node.childNodes;
	
		if(kids.length == (IBM_RTE_isMozilla()==true)?3:1 ) {// div block
			var kidsofkids = kids[(IBM_RTE_isMozilla()==true)?1:0].childNodes;

			if(kidsofkids.length == 1){
				var selectNode = kidsofkids[0];
				if(formatInfo != null || formatInfo == ""){
					optionSelected = "Heading " + formatInfo.charAt(2);
					for(var i=0; i<selectNode.options.length; i++)
						if(selectNode.options[i].text == optionSelected){
							selectNode.options.selectedIndex = i;		
							return;
					}
				}else{
					for(var j=0; j<selectNode.options.length; j++)
						if(selectNode.options[j].text == "Normal"){
							selectNode.options.selectedIndex = 1;	
							return;
					}
				}
			}
		}
	}
}

function IBM_RTE_modFontSizeList(flag)
{
	if(flag == true) return; 

	var fontSizes = new Array("7pt", "9pt", "12pt", "14pt", "18pt", "24pt");

	var fontSizeInfo =  IBM_RTE_getFontSize("<%= editorName %>");

	var pt;

	var node = this.document.getElementById("<%= editorName %>" + "ToolbarFormatListFontSize");
	var kids = node.childNodes;

	if(kids.length == (IBM_RTE_isMozilla()==true)?3:1) {// div block
		var kidsofkids = kids[(IBM_RTE_isMozilla()==true)?1:0].childNodes;

		if(kidsofkids.length == 1){
			var selectNode = kidsofkids[0];
			
			if(fontSizeInfo == "notnull" || (!IBM_RTE_isMozilla() && fontSizeInfo == null)){
				selectNode.options[0] = new Option("--Size--", "", false, true);
				return;
			}

			if(fontSizeInfo != null && fontSizeInfo != ""){
				if(fontSizeInfo > 0 && fontSizeInfo < 7){
					pt = fontSizes[fontSizeInfo - 1];
				}else{
					fontSizeInfo = 3;
					pt = "12pt";
				}
				for(i=0; i<selectNode.options.length; i++){			
					if(selectNode.options[i].text == pt){
						selectNode.options.selectedIndex = i;
						return;
					}
				}
			}
			if(!IBM_RTE_isMozilla())
				selectNode.options[0] = new Option("12pt", "3", true, true);
			if(IBM_RTE_isMozilla())
				selectNode.options.selectedIndex = 3;
		}
	}
}

function IBM_RTE_modFontFaceList(flag)
{
	if(flag == true) return;

	var fontFaces = new Array("Arial", "Bookman", "Courier", "Garamond", "Lucida Console", "Symbol", "Tahoma", "Times", "Trebuchet", "Verdana");
	var altFontFaces = new Array("arial,helvetica,sans-serif", "bookman old style,new york,times,serif","courier,monaco,monospace,sans-serif","garamond,new york,times,serif", "lucida console,sans-serif", "symbol,fantasy", "tahoma,new york,times,serif", "times new roman,new york,times,serif", "trebuchet ms,helvetica,sans-serif", "verdana,helvetica,sans-serif");
	var altAltFontFaces = new Array("Arial", "Bookman Old Style", "Courier", "Garamond", "Lucida Console", "Symbol", "Tahoma", "Times New Roman", "Trebuchet MS", "Verdana");
	
	var fontFaceInfo =  IBM_RTE_getFontFace("<%= editorName %>");
	
	if(fontFaceInfo != null){
		fontFaceInfo = fontFaceInfo.replace(/,[ ]*/gi, ",");
	}

	var face;

	var node = this.document.getElementById("<%= editorName %>" + "ToolbarFormatListFontStyle");
	var kids = node.childNodes;

	if(kids.length == (IBM_RTE_isMozilla()==true)?3:1){// div block
		var kidsofkids = kids[(IBM_RTE_isMozilla()==true)?1:0].childNodes;

		if(kidsofkids.length == 1){
			var selectNode = kidsofkids[0];

			if(fontFaceInfo == "notnull" || (!IBM_RTE_isMozilla() && fontFaceInfo == null)){
				selectNode.options[0] = new Option("--Font--", "", false, true);
				return;
			}
			
			if(fontFaceInfo != null && fontFaceInfo != ""){
				for(var xxx = 0; xxx < fontFaces.length; xxx++){
					if(fontFaceInfo.toLowerCase() == fontFaces[xxx].toLowerCase() || 
						fontFaceInfo.toLowerCase() == altFontFaces[xxx].toLowerCase() ||
						fontFaceInfo.toLowerCase() == altAltFontFaces[xxx].toLowerCase()){
						fontFaceInfo = altFontFaces[xxx];
						face = fontFaces[xxx];
						break;
					}
				}
				
				for(i=0; i<selectNode.options.length; i++){
					if(selectNode.options[i].text == face){
						selectNode.selectedIndex = i;
						return;
					}
				}
			}
			if(!IBM_RTE_isMozilla())
				selectNode.options[0] = new Option("Times", "times new roman,new york,times,serif", true, true);
			else{ //if fontFaceInfo == null or if it is set to times
				if(fontFaceInfo == null)
					selectNode.options.selectedIndex = 8;
			}
		}
	}
}
// defect 147838 end
//control character fix
function stripControlCharacters(content)
{

		var xxx = 0;
		while(xxx < renderedCtrlChars.length){
			while(content.indexOf(renderedCtrlChars[xxx]) != -1){
				content = content.replace(renderedCtrlChars[xxx], "");
			}
			xxx++
		}

	return content;
}
//control character fix

</script>