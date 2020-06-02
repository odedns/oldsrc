<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<title><nessfw:caption decorated="false" name="searchCustomer"/></title>

<nessfw:body onLoad="initFormParameters();" name="customerDialog">	
	<nessfw:form target="customerDialog">							
		<table width="100%" height="100%">
			<TR>
				<td height="1"><font class="headerTitle"><nessfw:caption name="searchCustomer_title"/></font></td>
			</TR>
		
			<TR valign="top">
				<td height="1">
					<table style="border: 2px solid black;" width="100%" height="100%" cellpadding="0" cellspacing="0">
						<TR>
							<td>
								<table cellpadding="2" cellspacing="2">
									<tr>
										<td nowrap="nowrap"><nessfw:caption name="searchCustomer_id"/></td>
										<td><nessfw:textField id="id" dirtable="false"/></td>
										<td width="100">&nbsp;</td>
										<td nowrap="nowrap"><nessfw:caption name="searchCustomer_type"/></td>
										<td><nessfw:list id="customerTypeModel" dirtable="false"/></td>
										<td width="100">&nbsp;</td>
										<td>&nbsp;</td>
										<td>&nbsp;</td>
									</TR>	
									<tr>	
										<td nowrap="nowrap"><nessfw:caption name="searchCustomer_name"/></td>
										<td><nessfw:textField id="firstName" dirtable="false"/></td>
										<td>&nbsp;</td>
										<td nowrap="nowrap"><nessfw:caption name="searchCustomer_city"/></td>
										<td><nessfw:textField id="city" dirtable="false"/></td>
										<td>&nbsp;</td>
										<td nowrap="nowrap"><nessfw:caption name="searchCustomer_telephone"/></td>
										<td><nessfw:textField id="telephone" dirtable="false"/></td>
									</TR>
								</table>
							</td>
						</tr>
						<TR>
							<td height="1">
								<table width="100%">
									<tr>
										<td width="100%"></td>
										<td>
											<nobr>
											<nessfw:button eventName="search" value="search" tooltip="searchTooltip"/>
											<nessfw:button eventName="clearFields" value="clearFields"/>
											</nobr>
										</td>
									</tr>
								</table>
							</td>
						</TR> 
					</table>
				</td>
			</tr>
	
			<tr>		
				<td height="100%"><nessfw:table  submitOnRowSelection="false" id="searchResultTableModel" onDblClick="returnRow();"/></td>
			</tr>
		
			<tr>
				<td height="1">
					<nessfw:button value="choose"  onClick="returnRow();"/>
					<nessfw:button value="cancel" onClick="self.close();"/>
				</td>
			</tr>
			
			<tr><td><nessfw:messages expandHeight="30" normalHeight="30"/></td></tr>
			
		</table>
	
		<nessfw:variable id="customerName" value=""/>
		<nessfw:variable id="customerId" value=""/>
	</nessfw:form>	
</nessfw:body>

<script language="javascript">
function returnRow()
{
	id = getSelectedRowExtraData("searchResultTableModel","ID");	
	name = getSelectedRowExtraData("searchResultTableModel","NAME");	
	document.all.customerId.value = id;
	document.all.customerName.value = name;
	returnFormParameters();
}
</script>