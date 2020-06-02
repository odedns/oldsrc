<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
	<div id="pageFrame">

	<h2> Thanks you for using Jadmin !!</h2>
	<p>
		<f:view>
		<h:form>
		<h:commandLink action="login">
			<h:outputText value="Go back to Login page" />
		</h:commandLink>
		</h:form>
		</f:view>
</div>	
	
	</BODY>
</HTML>
