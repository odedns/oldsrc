<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<HTML>
<h3> This is a sample tabs page </h3>
<body>
<f:view>

<h:form>
	<h:panelGrid columns="3" border="1" cellpadding="4">
		<h:commandLink  id="tab1" actionListener="#{testBean.handleTab}" immediate="true">
			<h:outputText value="Tab-1" />
		</h:commandLink>
		<h:commandLink  id="tab2" actionListener="#{testBean.handleTab}" immediate="true">
			<h:outputText value="Tab-2" />
		</h:commandLink>
		<h:commandLink  id="tab3" actionListener="#{testBean.handleTab}" immediate="true">
			<h:outputText value="Tab-3" />
		</h:commandLink>
	
	</h:panelGrid>
	<f:subview id="tabView">
		<c:import url="tab1.jsp" />
		<c:import url="tab2.jsp" />
		<c:import url="tab3.jsp" />
	</f:subview>
	<br>	
	<h:commandLink action="back">
		<h:outputText value="Back to Main menu" />
	</h:commandLink>
</h:form>
</f:view>
</body>
</HTML>

