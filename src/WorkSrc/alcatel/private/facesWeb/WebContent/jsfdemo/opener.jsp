<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<HEAD>
<script type="text/javascript">
// open a small selection window.
function openWin(url)
{
	window.open(url,"selectWindow","status,width=400,height=300");

}
</script>

</HEAD>
<body>
<f:view>

		<h:form id="popupForm" >
			<h:inputText id="popupInput" value="#{testBean.valueChanged}" />
			<h:commandButton value="Press to Open Popup"  action="" onclick="openWin('/facesWeb/faces/jsfdemo/window.jsp?type=unix')"/>

		<br><br>		
		<h:commandLink action="back">
			<h:outputText value="Back to Main menu" />
		</h:commandLink>
		
		</h:form>

	</f:view>

</body>

</html>
