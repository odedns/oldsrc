<%@page import="java.sql.*,com.ibm.dse.base.*,composer.utils.*" %>

<%

		Context.reset();
		Settings.reset("http://localhost/myweb4/dse/server/dse.ini");
		Settings.initializeExternalizers(com.ibm.dse.base.Settings.MEMORY);	
		out.println("jdbc.jsp --> initialized !");		
		Context ctx = (Context) Context.readObject("myCtx");
		JdbcConnectionService srv = (JdbcConnectionService) ctx.getService("jdbcSrv");
		out.println("got service: " + srv.toString());
		out.println("ds name = " + srv.getDataSourceName());
		Connection conn = srv.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select name from groups");
		String s = null;
		while(rs.next()) {
			s = rs.getString(1);
			out.println("s = " + s);
		}		
%>
		