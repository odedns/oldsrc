<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<%-- jsf:pagecode language="java" location="/JavaSource/pagecode/custom/Tab_panel.java" --%><%-- /jsf:pagecode --%>

<html>
<body>
	<f:view>
<P>This is test jsp.<BR>
<BR>
</P>
	<br> Panel Grid 2 col
	<h:form>
	<h:panelGrid columns="1" border="2">
		<h:commandLink  action="" >
			<h:outputText value="Some text 1" />
		</h:commandLink>
		<h:commandLink  action="" >
			<h:outputText value="Some text 2" />
		</h:commandLink>		
	</h:panelGrid>
	
	<f:subview id="tab" >
		<c:import url="tab1.jsp" />
	</f:subview>
	</h:form>
	</BODY></f:view>

</HTML>
