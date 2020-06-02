<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>		
		<td height="100%"><nessfw:table submitOnRowSelection="true" id="addressTableModel"/></td>
	</tr>
	<tr>
		<td style="height:5px;"></td>
	</tr>
	<tr>
		<td><jsp:include page="customerAddressDetails.jsp"/></td>
	</tr>
	<tr>
		<td class="buttonsPadding"> 
			<nessfw:button eventName="new" value="new"/>
			<nessfw:button eventName="update" value="update"/>
			<nessfw:button eventName="delete" value="delete"/>
		</td>
	</tr>	
</table>
