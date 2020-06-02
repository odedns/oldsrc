<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
	<div id="pageFrame">

<f:view>
	<P>Place content here.</P>
	<h:form id="loginForm" >	
		<center>
		<h:panelGrid styleClass="panelGrid" id="grid1" columns="2" border="5">
			<h:outputText value="Login:"/>
			<h:inputText id="login" value=""/>
			<h:outputText value="Password :"/>
			<h:inputSecret id="password" value=""/>		
			<h:commandButton id="loginButton" action="#{wasAdmin.login}" value="Login" />
			<h:commandButton id="cancelButton" action="" value="Cancel" />
		</h:panelGrid>
		</center>
	</h:form>

</div>	
	
</f:view>
	</BODY>
</HTML>
