<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
 String images = (String)aEditor.getAttribute("images"); 
 String currentControlName = (String)aEditor.getAttribute("currentControlName"); 
 StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); %>
<table border="0" cellspacing="0" cellpadding="0">
    <tr><td><img border="0" height="18" width="3" src="<%= images %>/clearPixel.gif"></td>
    <td <%= style.getClass("img", "toolbarSeparator") %>><img border="0" height="18" width="1" src="<%= images %>/clearPixel.gif"></td><td><img border="0" height="18" width="3" src="<%= images %>/clearPixel.gif"></td></tr>
</table>

