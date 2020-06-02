<%-- This is an addition 
     to top of the Parent Frame,
     just for the cc_search_user page and bill_overview page.
--%>
<%@ page import="java.util.*" %>
<%! int cnt = 0; %>
<% 
	out.println("test jsp page");
	if(session.isNew() ) {
		out.println("session is new ..");
	}else {
		out.println("existing session id = " + session.getId());
	}
	
%>
	<p>
<%
	out.println(request.toString());
	Enumeration e = request.getHeaderNames();
	while(e.hasMoreElements()) {
%>
	<p> 
	<% 
	 String s = (String)e.nextElement();	
	 out.println(s + "\t" + request.getHeader(s));
	}
%>

