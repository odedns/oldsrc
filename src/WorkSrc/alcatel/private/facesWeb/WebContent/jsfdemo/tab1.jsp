<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


		<h:panelGrid columns="2" border="2" rendered="#{testBean.tabRenderMap['tab1']}">
			<h:outputText value="This is a sample tab-1 page" />		
			<f:verbatim>&nbsp</f:verbatim>
			<h:outputText value="Enter Long: " />
			<h:inputText id="inputLong" value="#{testBean.longVal}"  required="true">
			</h:inputText>
			<h:outputText value="Enter Date: " />
			<h:inputText id="inputDate" value="#{testBean.dateVal}" />
			<h:outputText value="Enter Long Text" ></h:outputText>
			<h:inputTextarea id="area" value="#{testBean.longText}" />
		</h:panelGrid>