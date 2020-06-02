<%@page import="java.util.*, javax.sql.*,java.sql.*,javax.naming.*" %>

<% 

	InitialContext ctx = new InitialContext();
	DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/myDS");

	Connection conn = ds.getConnection();

	out.println("got connection ...");



%>

