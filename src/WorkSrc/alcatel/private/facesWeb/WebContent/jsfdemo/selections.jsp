<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<f:view>
<body>
		<h2> Some selection examples: </h2>
	
	<h:form>

		<f:loadBundle var="bundle" basename="messages"/>		
			<br> SelectBoolean Checkbox: <h:selectBooleanCheckbox value="#{testBean.checkMe}" />
	<br>
	<br>Select Many CheckBoxes: 
	<h:selectManyCheckbox layout="pageDirection" value="#{testBean.selectMany}">
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>
	</h:selectManyCheckbox>	
	<br><br>
	<h:selectOneRadio layout="lineDirection" value="#{testBean.selectOne}" >
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>
	</h:selectOneRadio>	
	
	<br> SelectOneMenu with data from bean.
	<br>
	<h:selectOneMenu value="#{testBean.currSelection}" >
	<f:selectItems value="#{testBean.selections}"/>
	</h:selectOneMenu>
	
	<br> SelectOneListBox with data from bean.
	<br>
	<h:selectOneListbox size="3" value="#{testBean.currSelection2}">
		<f:selectItems value="#{testBean.selections}"/>
	</h:selectOneListbox>
	
	<br> SelectManyListBox with default value  from bean.
	<br>
	<h:selectManyListbox size="3"  value="#{testBean.selectMany2}">
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>		
	</h:selectManyListbox>
	
	<br> SelectManyMenu with data from bean.
	<br>
	<h:selectManyMenu value="#{testBean.selectMany2}">
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>		
	</h:selectManyMenu>
	
	<br> SelectOneMenu with auto submit.
	<br>
	<h:selectOneMenu value="#{testBean.currSelection}"  onchange="submit()">
	<f:selectItems value="#{testBean.selections}"/>
	</h:selectOneMenu>
	
	<br>
		<h:commandButton action="#{testBean.doAction}" value="Submit"/>
	<br>
	<h:panelGrid columns="2">
		<h:outputText value="SelectBoolean:" />
		<h:outputText value="#{testBean.checkMe}" />
		<h:outputText value="SelectOneRadio:" />
		<h:outputText value="#{testBean.selectOne}" />
		<h:outputText value="SelectManyCheckBox:" />
		<h:outputText value="#{testBean.selectMany}" />		
		<h:outputText value="SelectListBox:" />
		<h:outputText value="#{testBean.selectMany2}" />				
		<h:outputText value="SelectOneListBox:" />
		<h:outputText value="#{testBean.currSelection2}" />		
		<h:outputText value="SelectOneMenu:" />
		<h:outputText value="#{testBean.currSelection}" />		
		
	</h:panelGrid>
	<h:commandLink action="back">
		<h:outputText value="Back to Main menu" />
	</h:commandLink>	
	
	</h:form>	
</body>
</f:view>


</HTML>
