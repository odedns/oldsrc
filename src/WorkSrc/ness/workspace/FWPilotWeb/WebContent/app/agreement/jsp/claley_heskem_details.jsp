<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table height="100%" width="100%" cellspacing="0" cellpadding="0" class="pkgDetailsFrame">
	<tr>
		<td height="1" class="headerSubTitle"><nessfw:caption name="claleyHeskemDetails_version"/></td>
	</tr>
	<tr>
		<td valign="top" class="innerPadding">
			<table height="100%" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="16%" height="1"><nessfw:caption name="claleyHeskemDetails_agreementName"/></td>
					<td width="24%"><nessfw:textField id="name" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE%>" maxLength="50"/></td>
					<td width="8%"><nessfw:caption  name="claleyHeskemDetails_id"/></td>
					<td><nessfw:label id="identification"/></td>
				</tr>
				<tr>
					<td><nessfw:caption name="claleyHeskemDetails_status"/></td>
					<td colspan="3"><nessfw:list type="single" id="statusModel" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE%>"/></td>
				</tr>
				<tr>
					<td><nessfw:caption name="claleyHeskemDetails_description"/></td>
					<td colspan="3"><nessfw:textField id="description" width="300" maxLength="250"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="OtherSubTitle"><nessfw:caption name="claleyHeskemDetails_agreement"/></td>
	</tr>
		<td class="innerPadding">
			<table cellpadding="0" cellspacing="0" width="100%" height="100%">
				<tr>
					<td valign="top" width="10%"><nessfw:caption name="claleyHeskemDetails_type" suffix=":" /></td>
					<td colspan="4"><nessfw:selection type="<%=UIConstants.SELECTION_SINGLE%>" id="agreementTypeModel" orientation="<%=UIConstants.SELECTION_VERTICAL %>"/></td>				
				</tr>	
				<tr>
					<td width="16%" colspan="2"><nessfw:caption name="claleyHeskemDetails_startDate"/></td>
					<td width="18%"><nessfw:textField id="startDate" maxLength="10" width="70" textType="<%=UIConstants.TEXT_FIELD_TYPE_DATE %>" dateDialogParams="scroll=no;dialogTop=350;dialogLeft=450;dialogWidth=246px;dialogHeight=200px;status=no;"/></td>
					<td width="18%"><nessfw:caption name="claleyHeskemDetails_endDate"/></td>
					<td><nessfw:textField id="endDate" maxLength="10" width="70" textType="<%=UIConstants.TEXT_FIELD_TYPE_DATE %>" dateDialogParams="scroll=no;dialogTop=350;dialogLeft=200;dialogWidth=246px;dialogHeight=200px;status=no;"/></td>
				</tr>
				<tr>
					<td colspan="5"><nessfw:selection type="<%=UIConstants.SELECTION_MULTIPLE %>" id="automaticCheckBoxModel" orientation="<%=UIConstants.SELECTION_VERTICAL%>"/></td>
				</tr>
				<tr>	
					<td colspan="3"><nessfw:selection type="multiple" id="canBeCanceledCheckBoxModel"  orientation="<%=UIConstants.SELECTION_VERTICAL%>" onClick="checkPeriod(this);"/></td>
					<td><nessfw:caption name="claleyHeskemDetails_minPeriod"/></td>
					<td><nessfw:textField id="minimalPeriod" maxLength="3"/></td>		
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="OtherSubTitle"><nessfw:caption name="claleyHeskemDetails_customer"/></td>
	</tr>
	<tr>	
		<td height="1" class="innerPadding">
			<table height="100%" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top" width="10%"><nessfw:caption name="claleyHeskemDetails_custType"/></td>
					<td valign="top" width="12%"><nessfw:selection id="customerTypeModel" type="<%=UIConstants.SELECTION_SINGLE %>" onClick="checkCustomer(this);"/></td>
					<td valign="top" id="tr1" <nessfw:if fieldName="customerTypeModel" result="<%=String.valueOf(fwpilot.agreement.vo.Agreement.CUSTOMER_TYPE_COMPANY)%>" methodName="getSelectedKey" equal="false">  style="display: none;" </nessfw:if> >	
						<table cellspacing="0" cellpadding="0" class="pkgDetailsFrame" width="100%">	
							<tr>			
								<td class="innerPadding">
									<table height="100%" width="100%" cellspacing="4" cellpadding="0">			
										<tr>
											<td><nessfw:caption name="claleyHeskemDetails_name"/></td>
											<td><nessfw:caption name="claleyHeskemDetails_custid"/></td>
											<td colspan="2"></td>
										</tr>
										<tr>
											<td><nessfw:textField id="customerName"/></td>		
											<td><nessfw:textField id="customerId" maxLength="9"/></td>
											<td>
												<nessfw:formParameters 															
														srcFormName="mainForm"
														inputParameters="customerName-customerName|customerId-customerId"
														outputParameters="customerName-customerName|customerId-customerId"
														openDialog="true"
														value="search" eventName="searchCust" 
														dialogParams="scroll:no;status:no;dialogHeight:500px;dialogWidth:700px;dialogLeft:250px;dialogTop:50px;"	
	
												/>													
											</td>
											<td nowrap><nessfw:link value="claleyHeskemDetails_custDetails" id="customerLinkModel"/></td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td valign="top" id="tr2" <nessfw:if fieldName="customerTypeModel" result="<%=String.valueOf(fwpilot.agreement.vo.Agreement.CUSTOMER_TYPE_COMPANY)%>" methodName="getSelectedKey" equal="true">  style="display: none;" </nessfw:if> >&nbsp;</td>
				</tr>
			</table>
		</td>	
	</tr>
	<tr>
		<td height="20%"></td>
	</tr>
</table>

<!-- ---------------------------------------------------------------------- -->

<script language="javascript">
function checkPeriod(check)
{
	if (check.checked)
	{
		document.all.minimalPeriod.disabled=false;
		document.all.minimalPeriod.className="textFieldDefaultClass";
	}
	else
	{
		document.all.minimalPeriod.disabled=true;
		document.all.minimalPeriod.className="textFieldDisableClass";
	}
}

function checkCustomer(radio)
{
	if (radio.value == "2")
	{
		show(document.all("tr1"));
		show(document.all("tr2"));
	}
	else
	{
		hide(document.all("tr1"));
		hide(document.all("tr2"));
	}

}
function getData()
{
	selected = getSelectedRowId("agentsTable");
	alert(selected);
	data = getExtraData("agentsTable", selected, "id");	
	alert(data); 
}
</script>