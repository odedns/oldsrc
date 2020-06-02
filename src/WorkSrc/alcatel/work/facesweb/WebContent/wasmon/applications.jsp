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


	<h3>Applications Details.</h3>
	<p>
	Applications installed on server. 
	<br>
	
	
	<h:form id="newServerForm" >
	

           <p>
          <h:outputText value="Details for Server: #{sessionBean['selectedServer'].name}" />
          <br>
   		<h:dataTable border="5" cellpadding="2" cellspacing="0"  
		columnClasses="columnClass1" headerClass="headerClass"
			footerClass="footerClass" rowClasses="rowClass1"
			styleClass="dataTable" id="appTable" value="#{wasAdmin.applications}" var="app">
		
			<h:column id="appName">
				<f:facet name="header">
					<h:outputText id="serverNameTxt" styleClass="outputText" value="Name"></h:outputText>
				</f:facet>
				<h:outputText styleClass="outputText" value="#{app.name}" />
			</h:column>
						<h:column id="machine">
				<f:facet name="header">
					<h:outputText id="text1" styleClass="outputText" value="State"></h:outputText>
				</f:facet>
					<h:outputText styleClass="outputText" value="#{app.stateValue}" />
			</h:column>
			
			<h:column id="action">
				<f:facet name="header">
					<h:outputText styleClass="outputText" value="" id="actionText"></h:outputText>
				</f:facet>				
				<h:commandButton id="stopApp" action="" value="Stop" />
				<h:commandButton id="startApp" action="" value="Start" />				
			</h:column>
			
		</h:dataTable>
          
        
		
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
