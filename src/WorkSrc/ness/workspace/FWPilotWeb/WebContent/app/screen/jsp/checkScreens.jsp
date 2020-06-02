<%@ page import="com.ness.fw.ui.events.Event" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw"%>

<table height="100%" width="100%">			
	<tr>
		<td height="1">
			<table cellspacing="5" cellpadding="0">
				<tr>
					<td><nessfw:button name="check" value="btnSame" eventName="btnSame"/></td>
					<td><nessfw:button name="check" value="btnDialog" eventName="btnDialog" targetType="<%=Event.EVENT_TARGET_TYPE_DIALOG%>" dialogParams="scroll:no;status:no;dialogHeight:250px;dialogWidth:350px;dialogLeft:350px;dialogTop:150px;"/></td>
					<td><nessfw:button name="check" value="btnPopup" eventName="btnPopup" targetType="<%=Event.EVENT_TARGET_TYPE_POPUP%>" dialogParams="scroll=no,status=no,height=250,width=350,left=150,top=150"/></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr><td><nessfw:table id="checkTableModel" submitOnRowSelection="false"/></td></tr>
</table>		