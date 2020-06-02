<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<body>
<f:view>

	<h:form>
		<f:verbatim>
		<H3> JSF Demo Application </H3>
		<p> Display JSF features and components.		
		</f:verbatim>
		<h:panelGrid columns="1">
		<h:commandLink action="catalog">
			<h:outputText value="Tables Example" />
		</h:commandLink>	

		<h:commandLink action="input">
			<h:outputText value="Input Components" />
		</h:commandLink>	

		<h:commandLink action="output">
			<h:outputText value="Output Components" />
		</h:commandLink>	

		<h:commandLink action="selections">
			<h:outputText value="Selections and Lists" />
		</h:commandLink>	
		
		<h:commandLink action="tabs">
			<h:outputText value="Tabs" />
		</h:commandLink>	
		
		<h:commandLink action="popup">
			<h:outputText value="Popup Example" />
		</h:commandLink>	
		
		<h:commandLink action="dynamic">
			<h:outputText value="Dynamically changing components" />
		</h:commandLink>	


		<h:commandLink action="masterdetail">
			<h:outputText value="Master Detail Pattern" />
		</h:commandLink>	
				
		<h:commandLink action="tomahawk">
			<h:outputText value="Tomahawk Components" />
		</h:commandLink>	
		</h:panelGrid>
	
	</h:form>

</f:view>

	</body>
</html>