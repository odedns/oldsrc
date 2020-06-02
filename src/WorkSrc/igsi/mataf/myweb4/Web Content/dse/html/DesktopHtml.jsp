<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page import="com.ibm.dse.base.*" %>
<%@ page import="com.ibm.dse.cs.html.RequestValidationServices" %>
<%@ page import="com.ibm.dse.cs.servlet.CSConstants" %>
<jsp:useBean id="utb" scope="page" class="com.ibm.dse.cs.html.DSEJspContextServices">
<% utb.initialize ( request ); %>
</jsp:useBean>

<html>
<head>
	<title>Welcome</title>
<META name="GENERATOR" content="IBM WebSphere Studio">
</head>
<body bgcolor="#3366FF" text="#FFFFFF" background="/WSBCCSampleApplicationWeb/dse/html/back_interior.gif" >
<form method=post action="/WSBCCSampleApplicationWeb/servlet/com.ibm.dse.cs.servlet.CSReqServlet">
<FONT size="5" color="#FFFFFF" face="Arial">Demo Bank - Welcome</FONT>

<BR>
<HR>
<BR>
<BR>

<%= utb.getRequiredHtmlFields ("", "sampleHtmlFlow") %>
<%= utb.getErrorPageHtml ( "DesktopHtml.jsp" ) %>
<input type=hidden name=dse_processorState value=initial>
<input type=hidden name=dse_nextEventName value=start>

<center>
<table border="0" cellspacing="0" cellpadding="0" width=60%>
	<TR>
		<TD align="center"><FONT face="Verdana" size="1" color="#FFFFFF"><input type=submit value="Click to start"></FONT></TD>
	</TR>
</table>
</center>
</form>
</body>
</html>