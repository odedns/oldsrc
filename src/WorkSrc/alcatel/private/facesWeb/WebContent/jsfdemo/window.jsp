
<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<HEAD>
<script type="text/javascript">

	function updateParentVar(formId, fieldId, value)
	{
		opener.document.forms[formId][fieldId].value = value;
		self.close();
	}
</script>

</HEAD>
<body>
<f:view>

		<h:form id="windowForm" >
				<h:outputText value="#{param.type}" />
				<br>
		    <h:dataTable value="#{testBean.osNames}" var="osName">
               <h:column>
                  <h:outputLink value="#" 
                     onclick="updateParentVar('popupForm','popupForm:popupInput', '#{osName}')">
                     <h:outputText value="#{osName}" />
                  </h:outputLink>
               </h:column>
            </h:dataTable>
			<br>
		</h:form>

	</f:view>

</body>

</html>
