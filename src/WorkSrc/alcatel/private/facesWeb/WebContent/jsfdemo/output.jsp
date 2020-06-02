<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<HTML>
<f:view>
<body>

	<f:verbatim>
		<h3> Display JSF Output Components </h3>
	
	</f:verbatim>
	<h:form>

	<f:loadBundle var="bundle" basename="messages"/>		
	<br>
	<h:outputText value="an OutputText component" />
	<br>
	<h:outputFormat value="bundle.formatMsg"> 
		<f:param value="Some param"/>
	</h:outputFormat>
	<br>
	<h:outputLink value="/facesWeb/MemoryTest.jsp">
		<f:verbatim>an Output Link to an external page</f:verbatim>
	</h:outputLink>
	
	<br> Panel Grid 2 col
		<h:panelGrid columns="2" border="4">
		<h:outputText value="Some text 1" />
		<h:outputText value="Some text 2" />
		<h:outputText value="Some text 3" />
		<h:outputText value="Some text 4" />
	</h:panelGrid>

	<br> Panel Grid with panel group 2 col
	<h:panelGrid columns="2" >
		<h:outputText value="Some text 1" />
		<h:outputText value="Some text 2" />
		<h:panelGroup>
			<h:outputText value="Some text 2a" />
			<h:outputText value="Some text 2b" />
		</h:panelGroup>
		<h:outputText value="Some text 3" />
		<h:outputText value="Some text 4" />
	</h:panelGrid>
	
	<br> some List
	<ul>
		<li> <h:outputText value="item-1" /></li>
		<li> <h:outputText value="item-2" /></li>
		<li> <h:outputText value="item-3" /></li>
	</ul>
	
	<br> Graphic image.
	<h:graphicImage alt="Jefferson"  value="/jsfdemo/tj.gif"/>
	<br>
		<h:commandLink action="back">
			<h:outputText value="Back to Main menu" />
		</h:commandLink>
	</h:form>	
</body>
</f:view>


</HTML>
