

<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
  

<% String loc = request.getParameter("locale"); %>
<% String editorName = request.getParameter("editorName"); %>
<% String images = request.getParameter("images"); %>
<% String directory = request.getParameter("directory"); %>
<% String isBidi = request.getParameter("isBidi"); 
String tableDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) tableDir = "RTL"; 
ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); %>
<html>

<head>
<title><%= resourceBundle.getString("ToXHTMLStandard")%></title>
<link rel="STYLESHEET" type="text/css" href="Styles.css">
<script language="javascript">
//alert('<%= editorName %>');
//var cnt = this.opener.IBM_RTE_getEditorContent("<%= editorName %>", 0);
var cnt = this.opener.IBM_RTE_getEditorHTMLFragment("<%= editorName %>");
function ToXhtml()
{
	var accessFrm = document.getElementById("accessForm");
	accessFrm.inputContent.value=cnt;
  //alert("in make next jsp" + cnt);
  accessFrm.submit();

}
 function resizeWindow() {
        var h = document.getElementById("thebody").offsetHeight;
        var w = document.getElementById("thebody").offsetWidth;
        
        window.resizeTo(w + 30, h + 60);
    }
</script>

</head>


<body dir="<%= tableDir %>" onload="resizeWindow()" leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0" style="margin:6px;">
<FORM  id="accessForm" NAME="accessForm" method="POST" ACTION="updateContentAccessibilityCompliant.jsp?editorName=<%= editorName %>&images=<%=images%>">
<div id="thebody">
<img src="<%=images%>/makeCodeCompliant.gif" alt="" width="15" height="15" border="0" align="absmiddle">
<span class="portlet-section-header"><%= resourceBundle.getString("ToXHTMLStandard")%></span>

<hr class="portlet-separator">


<label for="xhtml standard version" class="portlet-form-field-label"><%= resourceBundle.getString("AllStandards")%></label><br>

<select name="standards" id="standards"><option value="xhtmltags_10.xml" selected>XHTML 1.0</option></select> 
<br>&nbsp;<br>

<label for="dtd" class="portlet-form-field-label">DTD</label><br>
<select name="dtd" id="dtd"><option value="transitional" selected>Transitional</option></select> 
<br>&nbsp;<br>
<input class="wpsButtonText" type="button" value="<%= resourceBundle.getString("OK") %>" onClick="javascript:ToXhtml();" >
<input class="wpsButtonText" type="button" value="<%= resourceBundle.getString("Cancel") %>" onClick="javascript:parent.close()" >
<INPUT TYPE="HIDDEN" NAME="inputContent" VALUE="" />
<INPUT TYPE="HIDDEN" NAME="locale" VALUE="<%=loc%>" />
</div>
</FORM>
</body>
</html>

