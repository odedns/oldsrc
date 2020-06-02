<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); %>
<% Control currentControl = (Control)aEditor.getAttribute("currentControl"); %>
<% StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); %>

<% String script = (String)currentControl.getAttribute("script"); %>
<% Vector items = (Vector)currentControl.getAttribute("items"); %>
<% Vector text = (Vector)currentControl.getAttribute("text"); %>
<% String editorName = (String)aEditor.getName(); %>


<!--
<div <%= style.getClass("img", "toolbarControl") %>><select onclick="<%= script == null ? "" : script %>;" onkeydown="keydown(this);">
-->

<div <%= style.getClass("img", "toolbarControl") %>><select onclick="<%= script == null ? "" : script %>;" onkeydown="event.keyCode == 13 ? <%= script %> : ''">


<% for (int i = 0; i < items.size(); i++) { %>
    <option value="<%= items.elementAt(i) %>"><%= text.elementAt(i) %></option>
<% } %>

</select></div>


