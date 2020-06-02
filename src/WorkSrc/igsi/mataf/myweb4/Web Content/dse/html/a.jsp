<%@page import="com.ibm.dse.base.*" %>
<html>
<title>Demo Bank: User Account Access</title>
<body>
<h2>This is a.jsp</h2>

<jsp:useBean id="utb" scope="page" class="com.ibm.dse.cs.html.DSEJspContextServices">
<% utb.initialize(request); %>
</jsp:useBean>

<% 
	// Common definitions for the three links which will call the generic operation 
	// to display a given page
	//String displayPageOpRequest = utb.getRequiredHtmlFieldsForGETMethod("", "displayPageOp");
	
	Context ctx = utb.getContext();
	out.println("<p>" + ctx.toString());
%>
</body>
</html>
