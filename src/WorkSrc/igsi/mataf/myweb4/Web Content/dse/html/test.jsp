<%@page import="java.util.*" %>
<%-- This is an addition 
     to top of the Parent Frame,
     just for the cc_search_user page and bill_overview page.
--%>
<% 
	out.println("test jsp page");
%>
	<p>
<%
	out.println(request.toString());
%>
<%
	Enumeration e = request.getHeaderNames();
	while(e.hasMoreElements()) {
%>
	<p> 
	<% 
	 String s = (String)e.nextElement();	
	 out.println(s + "\t" + request.getHeader(s));
	}
%>

