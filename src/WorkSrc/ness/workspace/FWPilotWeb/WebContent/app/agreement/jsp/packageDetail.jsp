<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<table class="pkgDetailsFrame" width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" class="headerSubTitle"><nessfw:caption name="package_title" className="emptyClass" /></td>
	</tr>
	<tr>
		<td class="innerPadding">
			<table cellpadding="0" cellspacing="3" width="100%" height="100%">
				<tr valign="top">
					<td width="10%"><nessfw:caption name="package_id"/></td>
					<td width="12%"><nessfw:textField id="packId" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td width="10%"><nessfw:caption name="package_name"/></td>
					<td colspan="5"><nessfw:textField id="packName"  state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="350"/></td>
				</tr>
				<tr valign="top">
					<td><nessfw:caption name="package_minAge"/></td>
					<td><nessfw:textField id="packMinimalStartAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td><nessfw:caption name="package_maxAge"/></td>
					<td width="10%"><nessfw:textField id="packMaximalStartAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td width="10%"><nessfw:caption name="package_endAge"/></td>
					<td colspan="3"><nessfw:textField id="packEndAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
				</tr>
				<tr valign="top">
					<td><nessfw:caption name="package_description"/></td>
					<td colspan="7"><nessfw:textArea id="packDescription" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="500"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td class="innerPadding">
			<table cellpadding="0" cellspacing="3" width="100%" height="100%">
				<tr valign="top">
					<td width="10%"><nessfw:caption name="package_insurance"/></td>
					<td width="14%"><nessfw:textField id="packInsuranceSum" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE%>" width="90"/></td>
					<td width="4%"><nessfw:caption name="package_premia"/></td>
					<td width="14%"><nessfw:textField id="packPremia" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE%>" width="90"/></td>
					<td width="4%"><nessfw:caption name="package_discount"/></td>
					<td width="14%"><nessfw:textField id="packAfterDiscount" width="90"/></td>	
					<td width="4%"><nessfw:caption name="package_type"/></td>
					<td><nessfw:textField id="packType" inputType="<%=UIConstants.COMPONENT_MANDATORY_INPUT_TYPE%>" width="90"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="100%">&nbsp;</td>
	</tr>
</table>