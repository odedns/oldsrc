<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%">
	<tr>		
		<td height="100%"><nessfw:table cssPre="" submitOnRowSelection="true" id="relatedTableModel"/></td>							
	</tr>
	<tr>
		<td><jsp:include page="customerRelatedDetails.jsp"/></td>
	</tr>
	<tr>
		<td colspan="4"> 
			<nessfw:button eventName="new" value="new"/>
			<nessfw:button eventName="update" value="update"/>
 			<nessfw:button eventName="delete" value="delete"/>
  		</td>
	</tr>	
</table>