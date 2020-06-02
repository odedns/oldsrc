

<%@page import="java.util.*,
javax.sql.*,java.sql.*,javax.ejb.*,javax.naming.*,oded.ejb.utils.*,
oded.ejb.mytest.*" %>

<% 

	InitialContext ctx = EJBUtils.getInitialContext();
  	MytestLocalHome home = (MytestLocalHome) ctx.lookup("jndi.mytestlocal");
        out.println("<p>Creating Mytest session \n");
	MytestLocal mys = (MytestLocal) home.create();
	out.println("<p>Mytest remote created \n");
     	mys.setData("Data [100]");
	out.println("<p>got " + mys.getData());
	 mys.remove();
								      





%>

