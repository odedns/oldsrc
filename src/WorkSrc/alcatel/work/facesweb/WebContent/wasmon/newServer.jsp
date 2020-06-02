<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<f:view>

	<div id="pageFrame">
	<c:import url="header.jsp" />
  
	<f:subview id="leftView" >
		<c:import url="left.jsp" />
	</f:subview>
	
	<DIV id="contentColumn">
	<div id="innerContentColum">

	<H3>Add a new application server.</H3>
	<p>
	Add a new application server to be monitored by the application.
	<br> specify the server's machine name and SOAP port.
	<h:form id="newServerForm" >
	

		<h:panelGrid styleClass="panelGrid" id="grid1" columns="2">
			<h:outputText value="Name:"/>
			<h:inputText id="name" value="#{wasAdmin.newServer.name}"/>
			<h:outputText value="Machine Name:"/>
			<h:inputText id="machineName" value="#{wasAdmin.newServer.host}"/>			
			<h:outputText value="SOAP port:"/>
			<h:inputText id="port" value="#{wasAdmin.newServer.port}"/>		
			<h:commandButton id="save" action="#{wasAdmin.addServer}" value="Save" />
			<h:commandButton id="close" action="close" value="Close" />
		</h:panelGrid>
		
	</h:form>
	
		</div>
</DIV>
				
	<c:import url="footer.jsp" />
	
</div>
</f:view>
	
</body>	
</HTML>
