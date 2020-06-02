<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>


<table width="100%" height="100%">
	<tr>
		<td height="1"><nessfw:label className="headerTitle" id="title"/></td>
	</tr>

	<tr>
		<td colspan="2" height="1">
			<nessfw:panel frameClassName="identityTitle">
				<table cellspacing="3" cellpadding="0">
					<tr>
						<td><nessfw:caption name="cutomerGeneralDetails_id"/></td>
						<td><nessfw:label id="identification"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><nessfw:caption name="cutomerGeneralDetails_name" /></td>
						<td><nessfw:label id="firstName"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><nessfw:caption name="cutomerGeneralDetails_birthDate"/></td>
						<td><nessfw:label id="birthDate" mask="<%=com.ness.fw.util.MaskConstants.DATE_MASK %>"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><nessfw:caption name="cutomerGeneralDetails_telephone"/></td>
						<td><nessfw:label id="telephone"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><nessfw:caption name="cutomerGeneralDetails_mobile"/></td>
						<td><nessfw:label id="mobilePhone"/></td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td><nessfw:caption name="cutomerGeneralDetails_id2"/></td>
						<td><nessfw:label methodName="getId" id="customer"/></td>
					</tr>
				</table>
			</nessfw:panel>
		</td>
	</tr>

	<tr>
		<td><nessfw:include/></td>
	</tr>
	
</table>

