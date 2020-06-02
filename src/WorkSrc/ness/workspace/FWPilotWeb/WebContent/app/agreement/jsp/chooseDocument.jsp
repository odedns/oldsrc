<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<%@ page import="com.ness.fw.ui.events.Event" %>
<TITLE><nessfw:caption decorated="false" name="newDocument"></nessfw:caption> </TITLE>

<nessfw:body className="bodyDialog">
<nessfw:form>
<div class="innerPadding" style="width: 100%;height: 100%;">
	<table height="100%" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td class="tableTitle"><nessfw:caption className="emptyClass" name="chooseDocument_docType"/></td>
		</tr>
		<tr>
			<td><nessfw:selection type="single" id="documentTypeModel"/></td>
		</tr>

		<tr>
			<td align="center" class="buttonsPadding">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td><nessfw:button eventName="ok" value="ok" targetType="<%=Event.EVENT_TARGET_TYPE_CLOSE_DIALOG%>" /></td>
					<td width="4"></td>
					<td><nessfw:button eventName="cancel" value="cancel" targetType="<%=Event.EVENT_TARGET_TYPE_CLOSE_DIALOG%>" /></td>
				</tr>
			</table>
			</td>	
		</tr>
	</table>
</div>
</nessfw:form>
</nessfw:body>