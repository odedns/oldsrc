<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor);
 String images = (String)aEditor.getAttribute("images"); 
 String editorName = (String)aEditor.getAttribute("currentEditorName"); 
 Control currentControl = (Control)aEditor.getAttribute("currentControl");
 String toolbarName = (String)aEditor.getAttribute("currentToolbarName"); 
 StyleHelper style = (StyleHelper)aEditor.getAttribute("style");
 String resource = (String)currentControl.getAttribute("text"); 
 String id = toolbarName + currentControl.getName() + "Img"; 
 String image = (String)currentControl.getAttribute("image"); 
 String script = (String)currentControl.getAttribute("script"); %>
<table border="0" cellspacing="0" cellpadding="0"><tr><td <%= style.getClass("img", "toolbarButton") %> onmouseup="this.className='<%= style.getClassName("img", "toolbarButtonRollover") %>';" onmousedown="this.className='<%= style.getClassName("img", "toolbarButtonPressed") %>';" onmouseover="this.className='<%= style.getClassName("img", "toolbarButtonRollover") %>';" onmouseout="this.className='<%= style.getClassName("img", "toolbarButton") %>';" onclick="<%= script == null ? "" : script %>"><img ondragstart="noDrag();" id="<%= id %>" width="18" height="18" title="<%= resource %>" alt="<%= resource %>" src="<%= images %>/<%= image %>"></td></tr></table>
<script language="JavaScript">
function noDrag(){
       var oData = event.dataTransfer;
       oData.effectAllowed = "move";
       event.cancelBubble=true;
       event.returnValue=false;
}
</script>
