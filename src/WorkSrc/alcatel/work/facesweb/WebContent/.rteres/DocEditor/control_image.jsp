<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); %>
<% String images = (String)aEditor.getAttribute("images"); %>
<% Control currentControl = (Control)aEditor.getAttribute("currentControl"); %>
<% StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); %>

<% String resource = (String)currentControl.getAttribute("text"); %>
<% String image = (String)currentControl.getAttribute("image"); %>

<div <%= style.getClass("img", "toolbarControl") %>><img title="<%= resource %>" alt="<%= resource %>" src="<%= images %>/<%= image %>" width="18" height="18"></div>

