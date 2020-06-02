<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%" class="pkgDetailsFrame" cellpadding="0" cellspacing="0">
	<tr>
		<td class="innerPadding">
			<table cellspacing="0" width="100%" height="100%" cellpadding="0">
				<tr>
					<td width="10%"><nessfw:caption name="cutomerAddressDetails_city"/></td>
					<td width="22%"><nessfw:textField id="city" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE %>" maxLength="50"/></td>
					<td width="6%"><nessfw:caption name="cutomerAddressDetails_street"/></td>
					<td width="22%"><nessfw:textField id="street" maxLength="50"/></td>
					<td width="10%"><nessfw:caption name="cutomerAddressDetails_streetNumber"/></td>
					<td><nessfw:textField id="streetNumber" maxLength="4"/></td>
				</tr>
				<tr>
					<td><nessfw:caption name="cutomerAddressDetails_telephone"/></td>
					<td><nessfw:textField id="addressTelephone"/></td>
					<td><nessfw:caption name="cutomerAddressDetails_fax"/></td>
					<td><nessfw:textField id="addressFax"/></td>
					<td><nessfw:caption name="cutomerAddressDetails_mainAddress"/></td>
					<td><nessfw:selection type="multiple" id="addressMainModel"/></td>
				</tr>
			</table>
		</td>
	</tr>
</table>