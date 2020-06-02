<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>


<table width="100%" height="100%" cellspacing="0" cellpadding="0" class="pkgDetailsFrame">
	<tr>
		<td class="headerSubTitle" height="1"><nessfw:caption name="cutomerGeneralDetails_generalDetails" className="emptyClass"/></TD>
	</tr>
	<tr>
		<td valign="top" class="innerPadding">
			<table cellspacing="0" cellpadding="2" width="100%" height="100%">
				<tr>
					<td width="16%"><nessfw:caption name="cutomerGeneralDetails_id"/></td>
					<td width="22%"><nessfw:textField id="identification" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="9"/></td>
					<td width="12%"><nessfw:caption name="cutomerGeneralDetails_firstName"/></td>
					<td width="20%"><nessfw:textField id="firstName" maxLength="50"/></td>
					<td width="8%"><nessfw:caption name="cutomerGeneralDetails_lastName"/></td>
					<td><nessfw:textField id="lastName" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="50"/></td>
				</tr>
				<tr>
					<td><nessfw:caption name="cutomerGeneralDetails_englishFirstName"/></td>
					<td><nessfw:textField id="englishLastName"/></td>
					<td><nessfw:caption name="cutomerGeneralDetails_englishLastName"/></td>
					<td><nessfw:textField id="englishFirstName"/></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td><nessfw:caption name="cutomerGeneralDetails_telephone"/></td>
					<td><nessfw:textField id="telephone" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="10"/></td>
					<td><nessfw:caption name="cutomerGeneralDetails_mobile"/></td>
					<td><nessfw:textField id="mobilePhone" maxLength="10"/></td>
					<td><nessfw:caption name="cutomerAddressDetails_fax"/></td>
					<td><nessfw:textField id="fax" maxLength="10"/></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>		
		<td class="OtherSubTitle" height="1"><nessfw:caption name="cutomerGeneralDetails_details" className="emptyClass"/></td>
	</tr>
	<tr>
		<td class="innerPadding">		
			<table cellspacing="0" cellpadding="2" width="100%" height="100%">			
				<tr>
					<td width="16%"><nessfw:caption name="cutomerGeneralDetails_birthDate"/></td>
					<td width="22%"><nessfw:textField id="birthDate" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="10" width="70" textType="<%=UIConstants.TEXT_FIELD_TYPE_DATE %>" dateDialogParams="scroll=no;dialogTop=370;dialogLeft=580;dialogWidth=246px;dialogHeight=200px;status=no;" /></td>
					<td width="10%"><nessfw:caption name="cutomerGeneralDetails_sex"/></td>
					<td width="20%"><nessfw:list id="customerSexModel" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>"/></td> 
					<td width="10%"><nessfw:caption name="cutomerGeneralDetails_status"/></td>
					<td><nessfw:list id="customerStatusModel"/></td> 
				</tr>
				<tr valign="top">
					<td><nessfw:caption name="cutomerGeneralDetails_numChilds"/></td>
					<td><nessfw:textField id="numberOfChilds" maxLength="2" width="35"/></td>
					<td><nessfw:caption name="cutomerGeneralDetails_profession"/></td>
					<td>
					<nessfw:list 
						refreshType="0" 
						id="customerProfessionModel" 
						type="<%=UIConstants.LIST_TYPE_MULTIPLE_EXPANDED %>" 
						height="100" size="4" width="130" 
						expanderTitle="customerChooseProffesion" 
						expanderDialogParams="dialogHeight:300px;dialogWidth:500px;scroll:no;status=no;"
						searchAction="<%=UIConstants.LIST_SEARCH_ACTION_ALL%>"
						searchType="<%=UIConstants.LIST_SEARCH_TYPE_ALL%>"
						searchIn="<%=UIConstants.LIST_SEARCH_IN_ALL%>"
						srcListTitle="cutomerGeneralDetails_profession"
						trgListTitle="cutomerGeneralDetails_profession"
						/>	
					</td> 
					<td><nessfw:selection type="multiple" id="customerSmokingModel"/></td> 
					<td></td>
				</tr>			
			</table>		
		</td>
	</tr>	
	<tr>
		<td height="100%"></td>
	</tr>
</table>