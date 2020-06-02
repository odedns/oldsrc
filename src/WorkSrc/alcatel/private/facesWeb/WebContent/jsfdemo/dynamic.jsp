<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<f:view>

<h:form id="dynForm">
	<h:panelGrid id="dynPanel" columns="1">
	<h:outputText value="Press button to dynamically add component" />
	<h:commandButton value="Add Component" actionListener="#{testBean.addComponent}"/>
	</h:panelGrid>


</h:form>


</f:view>