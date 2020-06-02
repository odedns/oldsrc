<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.dse.base.*" %>
<%@ page import="com.ibm.dse.cs.html.RequestValidationServices" %>
<%@ page import="com.ibm.dse.cs.servlet.CSConstants" %>
<jsp:useBean id="utb" scope="page" class="com.ibm.dse.cs.html.DSEJspContextServices">
<% utb.initialize ( request ); %>
</jsp:useBean>


 
<html>
 <head>
 <META http-equiv="Content-Style-Type" content="text/css">

 <title>Startup page</title>
 </head>
<body>
<br><br><br>

<form name="hitomForm" method=post action="/CSRequestServlet">
	<%= utb.getRequiredHtmlFields ( "","hitomTestFlow" ) %>
	<%= utb.getErrorPageHtml ( "startup.jsp" ) %>
	<input type=hidden name=dse_processorState value=initial>
	<input type=hidden name=dse_nextEventName value=start>
  <input type=hidden name=type value=page>
  <input type=hidden name=partDestination value=hitomMain>
	<INPUT type="submit" value="sheelon Test">
</form>

</body>
</html>
