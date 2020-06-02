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
	<H1>Application Servers List.</H1>
<br>
<br>
<p>serverList.jsp
This is a server management screen. It displays all application servers in the network, 
displays their status and general information. 
The user can start/stop the server or press the link and access all server data. 
The user can also delete servers and create new servers. 
	<h:form styleClass="form" id="serverListForm">
		<h:dataTable border="5" cellpadding="2" cellspacing="0"  
		columnClasses="columnClass1" headerClass="headerClass"
			footerClass="footerClass" rowClasses="rowClass1"
			styleClass="dataTable" id="serverTable" value="#{wasAdmin.servers}" var="server">
			<h:column id="select">
				<f:facet name="header">
					<h:outputText id="voidText" styleClass="outputText" value=""></h:outputText>
				</f:facet>
				<h:selectBooleanCheckbox id="serverSelect" value="#{server.selected}" />
			</h:column>
		
			<h:column id="serverName">
				<f:facet name="header">
					<h:outputText id="serverNameTxt" styleClass="outputText" value="Name"></h:outputText>
				</f:facet>
					<h:commandLink styleClass="commandLink" id="serverLink" action="#{wasAdmin.serverDetails}">
								<h:outputText styleClass="outputText" value="#{server.name}" />
					</h:commandLink>								
			</h:column>
						<h:column id="machine">
				<f:facet name="header">
					<h:outputText id="text1" styleClass="outputText" value="Machine"></h:outputText>
				</f:facet>
					<h:outputText styleClass="outputText" value="#{server.host}" />
			</h:column>
			
			<h:column id="prodName">
				<f:facet name="header">
					<h:outputText styleClass="outputText" value="Product Name"
						id="text2"></h:outputText>
				</f:facet>
				<h:outputText styleClass="outputText" value="#{server.productName}" />
			</h:column>
			<h:column id="pid">
				<f:facet name="header">
					<h:outputText styleClass="outputText" value="PID" id="text3"></h:outputText>
				</f:facet>
				<h:outputText styleClass="outputText" value="#{server.pid}" />
			</h:column>
			<h:column id="status">
				<f:facet name="header">
					<h:outputText styleClass="outputText" value="State" id="text4"></h:outputText>
				</f:facet>
				<h:outputText styleClass="outputText" value="#{server.state}" />
			</h:column>
			<h:column id="action">
				<f:facet name="header">
					<h:outputText styleClass="outputText" value="" id="actionText"></h:outputText>
				</f:facet>				
				<h:commandButton id="stopServer" action="#{wasAdmin.stopServer}" value="Stop" />
				<h:commandButton id="restartServer" action="#{wasAdmin.restartServer}" value="Restart" />				
			</h:column>
			
		</h:dataTable>
		<h:commandButton id="newServer" action="newServer" value="New" />
		<h:commandButton id="deleteServer" action="#{wasAdmin.deleteServer}" value="Delete" />
		</h:form>
	</div>
</DIV>
				
	<c:import url="footer.jsp" />
	
</div>
</f:view>
	
</body>	
</HTML>
