<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="WINDOWS-1255"%>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="22"><nessfw:caption className="headerTitle" name="manageCustomers_title"/></td>
	</tr>

	<tr>		
		<td height="100%">
			<nessfw:table submitOnRowSelection="true" id="customersTable"/>
		</td>
	</tr>

	<tr><td>&nbsp;</td></tr>
	<tr class="label">
	<td>
		<nessfw:panel>
			<table width="100%">
				<tr>
					<td><nessfw:caption name="cutomerRelatedDetails_firstName"/></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<td><nessfw:textField id="customerName" dirtable="false"/></td>
					<td><nessfw:caption name="cutomerRelatedDetails_lastName"/></td>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<td><nessfw:textField id="customerFamily" dirtable="false"/></td>
				</tr>
			</table>
		</nessfw:panel>
	</td>
</tr>
	
<tr>
	<td> 
		<nessfw:button eventName="new" value="new"/>
		<nessfw:button eventName="update" value="update"/>
		<nessfw:button eventName="delete" value="delete"/>
	</td>
</tr>	
	
	<tr><td height="100%">&nbsp;</td></tr>
</table>