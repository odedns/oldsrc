<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); %>
<% String images = (String)aEditor.getAttribute("images"); %>
<% String currentToolbarName = (String)aEditor.getAttribute("currentToolbarName"); %>
<% StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); %>


<table border="0" cellspacing="0" cellpadding="0" width="10">
<tr>
    <td width="3"><img border="0" height="1" width="3" src="<%= images %>/clearPixel.gif"></td>
    <td id="<%= currentToolbarName %>__KnobTop" width="4" colspan="3"><img border="0" height="1" width="4" src="<%= images %>/clearPixel.gif"></td>
    <td width="3"><img border="0" height="1" width="3" src="<%= images %>/clearPixel.gif"></td>
</tr>
<tr>
    <td width="3"><img border="0" height="16" width="3" src="<%= images %>/clearPixel.gif"></td>
    <td id="<%= currentToolbarName %>__KnobLeft" width="1"><img border="0" height="18" width="1" src="<%= images %>/clearPixel.gif"></td>
    <td width="2" <%= style.getClass("img", "toolbar") %>><img border="0" height="18" width="2" src="<%= images %>/clearPixel.gif"></td>
    <td id="<%= currentToolbarName %>__KnobRight" width="1"><img border="0" height="18" width="1" src="<%= images %>/clearPixel.gif"></td>
    <td width="3"><img border="0" height="16" width="3" src="<%= images %>/clearPixel.gif"></td>
</tr>
<tr>
    <td width="3"><img border="0" height="1" width="3" src="<%= images %>/clearPixel.gif"></td>
    <td id="<%= currentToolbarName %>__KnobBottom" width="4" colspan="3"><img border="0" height="1" width="4" src="<%= images %>/clearPixel.gif"></td>
    <td width="3"><img border="0" height="1" width="3" src="<%= images %>/clearPixel.gif"></td>
</tr>
</table>
