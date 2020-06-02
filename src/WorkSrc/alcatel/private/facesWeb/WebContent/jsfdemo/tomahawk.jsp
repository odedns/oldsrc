<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>

<html>
<body>

<f:view>
	<h:form>
		<h:outputText value="Enter date : " />
	     <t:inputCalendar monthYearRowClass="yearMonthHeader" weekRowClass="weekHeader"
                currentDayCellClass="currentDayCell" value="#{testBean.dateVal}" renderAsPopup="true"
                renderPopupButtonAsImage="true" />
	
		<br>
			<h:commandLink styleClass="commandLink" id="link1" action="back">
				<h:outputText id="text1" styleClass="outputText" value="back"></h:outputText>
			</h:commandLink>
			
	</h:form>


</f:view>
</body>
</html>