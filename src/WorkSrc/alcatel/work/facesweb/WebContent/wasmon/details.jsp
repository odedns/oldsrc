<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="../WEB-INF/tld/onjlib.tld" prefix="my" %>

<html>
<body>
<f:view>
	<div id="pageFrame">
	<c:import url="header.jsp" />
  
	<f:subview id="leftView" >
		<c:import url="left.jsp" />
	</f:subview>
	
	<DIV id="contentColumn">
	<div id="innerContentColum">


	<h3>Server Details.</h3>
	<p>
	Server details screen. Displays server information.
	<br>
	
	<h:outputText value="#{wasAdmin.selectedServer.name}"/>
	
	<h:form id="newServerForm" >

	<ul>
		<li> <h:commandLink action="general">
				<h:outputText value="General" />
			</h:commandLink>
		</li>
		<li>
			<h:commandLink action="jvmDetails">
				<h:outputText value="JVM" />
			</h:commandLink>
		</li>
		<li>
			<h:commandLink action="applications">
				<h:outputText value="Applications" />
			</h:commandLink>
		</li>
		<li>
			<h:commandLink action="datasources">
				<h:outputText value="JDBC Data Sources" />
			</h:commandLink>
		</li>
		
	</ul>		
		<h:commandLink action="back" >
			<h:outputText value="Back" />
		</h:commandLink>
		
	</h:form>
	
		</div>
</DIV>
				
	<c:import url="footer.jsp" />
	
</div>
</f:view>
	
	
</body>
</HTML>
