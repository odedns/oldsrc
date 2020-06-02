<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<f:view>
<body>

	<h:form>

		<f:loadBundle var="bundle" basename="messages"/>		
		<h:panelGrid columns="3">
			<h:outputText value="Enter Long: " />
			<h:inputText id="inputLong" value="#{testBean.longVal}"  required="true">
				<f:validateLongRange minimum="1" maximum="100"/>
			</h:inputText>
			<h:message for="inputLong" />
			<h:outputText value="Enter Date: " />
			<h:inputText id="inputDate" value="#{testBean.dateVal}" />
			<h:message for="inputDate" />
			<h:outputText value="Enter sum : " >
				<f:convertNumber type="currency" currencySymbol="$" pattern="$###.##"/>
			</h:outputText>
			<h:inputText id="inputSum" value="#{testBean.sum}"  required="true"/>		
			<h:message for="inputSum" />
			<h:outputText value="Enter Long Text" ></h:outputText>
			<h:inputTextarea id="area" value="#{testBean.longText}" />
			<f:verbatim>&nbsp</f:verbatim>
			<h:outputText value="Enter Password:" />
			<h:inputSecret id="inputPass" value="#{testBean.password}" >
				<f:validateLength minimum="4" maximum="8"/>
			</h:inputSecret>
			<h:message for="inputPass" />
			<h:outputText value="Check for value change:" ></h:outputText>
			<h:inputText id="valueChange" value="#{testBean.valueChanged}" valueChangeListener="#{testBean.handleValueChange}" />
			<h:message for="valueChange" />
		</h:panelGrid>
		
		<h:panelGrid columns="2">	
			<h:commandButton action="#{testBean.doAction}" value="Submit"/>
			<h:commandButton action="" value="Clear"/>	
			<h:outputText value="Long:" />
			<h:outputText value="#{testBean.longVal}" />
			<h:outputText value="Date: " >
				<f:convertDateTime dateStyle="full"/>
			</h:outputText>
			<h:outputText value="#{testBean.dateVal}" />
			<h:outputText value="Sum : " />
			<h:outputText value="#{testBean.sum}" />
			<h:outputText value="Text Area Text : " />
			<h:outputText value="#{testBean.longText}" />
			<h:outputText value="Password : " />
			<h:outputText value="#{testBean.password}" />
			
		</h:panelGrid>
	
		<h:commandLink action="back" immediate="true">
			<h:outputText value="Back to Main menu" />
		</h:commandLink>
	</h:form>	
</body>
</f:view>


</HTML>
