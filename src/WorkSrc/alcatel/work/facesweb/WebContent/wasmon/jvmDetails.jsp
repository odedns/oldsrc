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
	
	
	<h:form id="newServerForm" >
	

            <h:outputText value="JVM Info" />
           <p>
          <h:outputText value="Details for Server: #{sessionBean['selectedServer'].name}" />
          <br>
          
         <h:panelGrid columns="2" border="4"> 
			<h:outputText value="HeapSize:" />
			<h:outputText value="#{sessionBean['selectedServer'].jvmInfo.heapSize}" >
				<f:convertNumber maxFractionDigits="2"/>
			</h:outputText>
			<h:outputText value="FreeMemory:" />
			<h:outputText value="#{sessionBean['selectedServer'].jvmInfo.freeMemory}" />
			<h:outputText value="JavaVersion:" />
			<h:outputText value="#{sessionBean['selectedServer'].jvmInfo.javaVersion}" />
			<h:outputText value="JavaVendor:" />
			<h:outputText value="#{sessionBean['selectedServer'].jvmInfo.javaVendor}" />
			         
         </h:panelGrid>   
        
		
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
