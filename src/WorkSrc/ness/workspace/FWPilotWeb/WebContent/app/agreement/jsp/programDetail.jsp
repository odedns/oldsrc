<%@ page import="com.ness.fw.ui.*" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<table class="pkgDetailsFrame" width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" class="headerSubTitle"><nessfw:caption name="programDetails" className="emptyClass"/></td>
	</tr>
	<tr>
		<td class="innerPadding">
			<table cellpadding="0" cellspacing="3" width="100%" height="100%">
				<tr valign="top">
					<td width="10%"><nessfw:caption name="program_id"/></td>
					<td width="12%"><nessfw:textField id="progId" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td width="10%"><nessfw:caption name="program_name"/></td>
					<td colspan="3"><nessfw:textField id="progName" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="350"/></td>
				</tr>	
				<tr valign="top">
					<td><nessfw:caption name="program_minAge"/></td>
					<td><nessfw:textField id="progMinimalStartAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td><nessfw:caption name="program_maxAge"/></td>
					<td width="10%"><nessfw:textField id="progMaximalStartAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>
					<td width="10%"><nessfw:caption name="program_endAge"/></td>
					<td><nessfw:textField id="progEndAge" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="35"/></td>	
				</tr>
				<tr valign="top">
					<td><nessfw:caption name="program_description"/></td>
					<td colspan="5"><nessfw:textArea id="progDescription" state="<%=UIConstants.COMPONENT_DISABLED_STATE %>" width="500"/></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height="1050%">&nbsp;</td>
	</tr>	
</table>