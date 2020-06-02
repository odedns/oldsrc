<%@page import="oded.utils.*, java.util.*" %>
<% 

	String cred = request.getHeader("Authorization");
	if(cred !=    null) {
		out.println("<p> credentials : " + cred);
		StringTokenizer st = new StringTokenizer(cred," ");
		st.nextToken();
		String pass = st.nextToken();
		byte b[] = pass.getBytes();
		byte b1[] = Base64.decode(b);
		out.println("<p>decode = " + new String(b1));
	} else {
		response.setHeader("WWW-Authenticate","Basic realm=weblogic");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		//response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

	}


%>

