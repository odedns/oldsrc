<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
	
<table width="100%" height="100%" class="pkgDetailsFrame" cellpadding="0" cellspacing="0">
	<tr>
		<td class="innerPadding">				
			<table width="100%" height="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td><nessfw:caption name="cutomerRelatedDetails_type"/></td>
					<td><nessfw:list id="customerRelatedTypeModel" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>"/></td>		
					<td><nessfw:caption name="cutomerRelatedDetails_id"/></td>
					<td><nessfw:textField id="relatedIdentification" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="9"/></td>
				</tr>
				
				<tr>
					<td><nessfw:caption name="cutomerRelatedDetails_firstName"/></td>
					<td><nessfw:textField id="relatedFirstName" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>"/></td>
			
					<td><nessfw:caption name="cutomerRelatedDetails_lastName"/></td>
					<td><nessfw:textField id="relatedLastName" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>"/></td>
				</tr>
			
				<tr>
					<td><nessfw:caption name="cutomerRelatedDetails_birthDate"/></td>
					<td><nessfw:textField id="relatedBirthDate" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" width="70" textType="date" dateDialogParams="scroll=no;dialogTop=450;dialogLeft=550;dialogWidth=246px;dialogHeight=200px;status=no;" /></td>
					<td><nessfw:caption name="cutomerRelatedDetails_sex"/></td>
					<td><nessfw:list id="relatedSexModel" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>