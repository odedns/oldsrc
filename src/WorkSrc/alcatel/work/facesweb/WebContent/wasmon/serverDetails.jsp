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
	
		<h:commandLink action="#{wasAdmin.jvmDetails}" >
			<h:outputText value="JVM Details" />
		</h:commandLink>
 <my:panelTabbed labelAreaClass="labels"
          selectedLabelClass="selected-tab" unselectedLabelClass="tab">
		
	 <h:panelGrid columns="2">

            <f:facet name="label">
              <my:tabLabel>				
                <h:outputText value="General" />
              </my:tabLabel>
            </f:facet>	
		
	<h:panelGrid columns="2" border="4">
		<h:outputText value="Name:" />
		<h:outputText value="#{sessionBean['selectedServer'].name}" />
		<h:outputText value="Cell Name:"/>
		<h:outputText value="#{sessionBean['selectedServer'].cellName}" />
		<h:outputText value="Node Name:" />
		<h:outputText value="#{sessionBean['selectedServer'].nodeName}" />
		<h:outputText value="Server Name:" />
		<h:outputText value="#{sessionBean['selectedServer'].serverName}" />
		<h:outputText value="Port:" />
		<h:outputText value="#{sessionBean['selectedServer'].port}" />
		<h:outputText value="Pid:" />
		<h:outputText value="#{sessionBean['selectedServer'].pid}" />	
		<h:outputText value="State:" />
		<h:outputText value="#{sessionBean['selectedServer'].state}" />	
		<h:outputText value="Platform Name:" />
		<h:outputText value="#{sessionBean['selectedServer'].platformName}" />
		<h:outputText value="Platform Version:" />
		<h:outputText value="#{sessionBean['selectedServer'].platformVersion}" />
		<h:outputText value="Server Vendor:" />
		<h:outputText value="#{sessionBean['selectedServer'].serverVendor}" />
	</h:panelGrid>

	</h:panelGrid>
	

 <h:panelGrid columns="2">

            <f:facet name="label">
              <my:tabLabel>				
                <h:outputText value="JVM Info" />
              </my:tabLabel>
            </f:facet>	
            <f:verbatim><p></f:verbatim>
          <h:outputText value="Details for Server: #{sessionBean['selectedServer'].name}" />
          <f:verbatim><br></f:verbatim>
          
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
        
</h:panelGrid>
		
	</my:panelTabbed>
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
