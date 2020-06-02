<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>

<f:verbatim>
<DIV id="leftColumn" >
	<div id="innerLeftColumn" >	
</f:verbatim>

	<h:form>	
		<h:panelGrid columns="1">
			<h:commandLink action="serverList">
				<h:outputText value="Servers" />
			</h:commandLink>
			
			<h:commandLink action="#{wasAdmin.logout}">
				<h:outputText value="Logout" />
			</h:commandLink>
		</h:panelGrid>			
	</h:form>
	
<f:verbatim>
	</div>
</DIV>
</f:verbatim>
