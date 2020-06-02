<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%" class="pkgDetailsFrame">
	<tr>
		<td class="innerpadding">
			<table cellpadding="0" cellspacing="0" width="100%">	
				<tr>
					<td width="12%"><nessfw:caption name="documents_id"/></td>
					<td width="20%"><nessfw:textField id="docId" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="9"/></td>
					<td width="7%"><nessfw:caption name="documents_system"/></td>
					<td width="10%"><nessfw:label id="system"></nessfw:label> </td>
					<td width="12%"><nessfw:caption name="documents_date"/></td>
					<td><nessfw:textField id="attachDate" maxLength="10" width="70" textType="<%=UIConstants.TEXT_FIELD_TYPE_DATE %>" dateDialogParams="scroll=no;dialogTop=370;dialogLeft=200;dialogWidth=246px;dialogHeight=200px;status=no;" /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="innerpadding">
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<td width="12%" valign="top"><nessfw:caption name="documents_description"/></td>
					<td><nessfw:textArea id="docDescription" width="500" height="100" expandable="true"  expanderTitle="documentDescription"  maxLength="250" expanderDialogParams="dialogWidth:700px;dialogHeight:300px;dialogLeft:150;dialogTop:350;scroll=no"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>