
<%@page import="java.util.*,
javax.sql.*,java.sql.*,javax.ejb.*,javax.naming.*,oded.ejb.utils.*,
oded.ejb.mytest.*" %>

<% 

	InitialContext ctx = EJBUtils.getInitialContext();
  	MytestHome home = (MytestHome) ctx.lookup("jndi.mytest");
        out.println("<p>Creating Mytest session \n");
	Mytest mys = (Mytest) home.create();
	out.println("<p>Mytest remote created \n");
     	mys.setData("Data [100]");
	out.println("<p>got " + mys.getData());
	 mys.remove();
								      





%>

