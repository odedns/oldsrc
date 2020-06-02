<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<%@ page import="com.ness.fw.ui.events.Event" %>
<%@ page import="com.ness.fw.ui.*" %>
<nessfw:body className="bodyDialog">
<nessfw:form>
	<table width="100%" cellpadding="0" cellspacing="0" border>
		<tr><td><nessfw:caption name="chooseCustomer_customerType"/></td></tr>
		<tr>
			<td><nessfw:selection type="<%=UIConstants.SELECTION_SINGLE %>" id="customerTypeModel" /></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="50">&nbsp;</td>
						<td colspan="2"><nessfw:button eventName="ok" value="ok" targetType="<%=Event.EVENT_TARGET_TYPE_CLOSE_DIALOG%>" enabledClassName="buttonDialog"/> 
										<nessfw:button eventName="cancel" value="cancel" targetType="<%=Event.EVENT_TARGET_TYPE_CLOSE_DIALOG%>" enabledClassName="buttonDialog"/>
						</td>
					</tr>
				</table>
			</td>	
		</tr>
	</table>
</nessfw:form>
</nessfw:body>