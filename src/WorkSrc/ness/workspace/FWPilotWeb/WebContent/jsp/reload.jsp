<%@ page import="com.ness.fw.flower.servlet.*"%>
<%@ page language="java"%>
<html>
<HEAD>
	<TITLE>Reload Page</TITLE>
</HEAD>
<body>
	<form action="<%= request.getRequestURI()%>">
		<input type="submit" name="<%= HTMLConstants.RELOAD%>" value="Press me to reload"/>
	</form>
</body>
</html>