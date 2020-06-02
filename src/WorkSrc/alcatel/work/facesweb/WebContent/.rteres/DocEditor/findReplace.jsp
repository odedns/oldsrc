
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
  


<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
  

<% String loc = request.getParameter("locale"); %>
<% String editorName = request.getParameter("editorName"); %>
<% ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); %>

<% String langToUse = LocaleHelper.getLocale(loc).getLanguage(); %>
<% String isBidi = request.getParameter("isBidi"); 
String images = request.getParameter("images"); 
%>
<% String isAccessible = request.getParameter("isAccessible"); %>  <!-- Added By Kiran Defect:143355-->
<% String tableDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) tableDir = "RTL"; %>

<% String noFindTxt = resourceBundle.getString("NothingToFind"); %>
<% String noReplaceTxt = resourceBundle.getString("NothingToReplaceWith"); %>
<% String fnText = resourceBundle.getString("FinishSearching"); %>

<% String userAgent = request.getHeader("user-agent"); %>
<% boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  %>
<script> 
var newArray = new Array(3);
newArray[0] = "<%= noFindTxt %>";
newArray[1] = "<%= fnText %>";
newArray[2] = "<%= noReplaceTxt %>";
</script>

<html>

<head>
<title><%= resourceBundle.getString("FindAndReplace") %></title>

<link rel="STYLESHEET" type="text/css" href="Styles.css">

<script language="javascript" src="script_findReplace.js"></script>
<script language="javascript">

function onCancel() {
   parent.close();
}

    //resize the window to fit the content of the main div
    //note the addition of 35px. This is a rough amount added 
    //to account for the title bar of the window.
    function resizeWindow() {
        var h = document.getElementById("thebody").offsetHeight;
        var w = document.getElementById("thebody").offsetWidth;
        
        window.resizeTo(w + 30, h + 60);
    }
</script>

</head>


<body dir="<%= tableDir %>" lang="<%= langToUse %>" onload="resizeWindow()" style="margin:6px;">

<div id="thebody">
<img src="<%= images %>/findReplace.gif" alt="" width="18" height="18" border="0" align="absmiddle">
<span class="portlet-section-header"><%= resourceBundle.getString("FindAndReplace") %></span>

<hr class="portlet-separator">

<label for="find" class="portlet-form-field-label"><%= resourceBundle.getString("FindWhat") %></label><br>
<input type="text" name="find" id="findWhat" size="50">

<br>&nbsp;<br>

<label for="replace" class="portlet-form-field-label"><%= resourceBundle.getString("ReplaceWith") %></label><br>
<input type="text" name="replace" id="replaceWith" size="50">

<br>&nbsp;<br>
<% if(!isMozilla) {%>
<input type="checkbox" name="match" id="matchWholeWord" value=""><label for="match" class="portlet-form-field-label"><%= resourceBundle.getString("MatchWholeWords") %></label><br>
<% } %>
<input type="checkbox" name="case" id="matchCase" value=""><label for="case" class="portlet-form-field-label"><%= resourceBundle.getString("CaseSensitive") %></label>


<hr class="portlet-separator">

<input type="button" value="<%= resourceBundle.getString("FindNext") %>" class="wpsButtonText" id="btnFindNext" onclick="onFindNext('<%= editorName%>',newArray);">
<input type="button" value="<%= resourceBundle.getString("Replace") %>" class="wpsButtonText" id="btnReplace" onclick="onReplace('<%= editorName%>', newArray, '<%= isAccessible %>');">
<input type="button" value="<%= resourceBundle.getString("ReplaceAll") %>" class="wpsButtonText" id="btnReplaceAll" onclick="onReplaceAll('<%= editorName%>', newArray, '<%= isAccessible %>');">
<input type="button" value="<%= resourceBundle.getString("Close") %>" class="wpsButtonText" id="btnCancel" onclick="onCancel();">

</div>
</body>
</html>

