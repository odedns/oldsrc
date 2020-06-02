
<%@page import="java.util.*" %>

<HTML>
<BODY>
	<% 
		Properties p = System.getProperties();
		Enumeration e = p.propertyNames();
		while(e.hasMoreElements()) {
			String key = (String) e.nextElement();
			out.println("<p>" + key + " = " + p.getProperty(key));
		}
	%>		

</BODY>
</HTML>
