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


	<h3>DataSources Details.</h3>
	<br>
	
	
	<h:form id="newServerForm" >
	

           <p>
          <h:outputText value="Details for Server: #{sessionBean['selectedServer'].name}" />
          <br>
          
        
		
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
