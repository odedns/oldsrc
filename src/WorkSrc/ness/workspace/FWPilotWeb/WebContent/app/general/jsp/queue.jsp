<%@page import="com.ness.fw.ui.*"%>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw"%>
<%		
	ListModel selModel = new ListModel();
	selModel.setSelectionType(UIConstants.LIST_TYPE_SINGLE);
	selModel.addValue("0","גיל");
	selModel.addValue("1","יפעת");
	selModel.addValue("2","שי");
	selModel.addValue("3","ברוך");
	selModel.addValue("4","מירי בוהדנה");
	selModel.addValue("5","סופי (טופי) צדקה");
	selModel.addValue("6","בר פאלי");
%>

<table height="100%" width="100%" border="0">			
	<tr class="desktopTitle">
		<td height="1">
			<table cellspacing="5" cellpadding="0">
				<tr>
					<td nowrap="nowrap"><nessfw:caption name="queue_owner"/></td>
					<td><nessfw:list dirtable="false" openOnKeyPress="true" searchIn="<%=UIConstants.LIST_SEARCH_IN_ALL %>" id="selExample" type="<%=UIConstants.LIST_TYPE_SINGLE_EXPANDED %>" listModel="<%=selModel%>" size="1" width="100" refreshType="0" expanderDialogParams="dialogHeight=250px;dialogWidth=250px;scroll=no"/></td> 
					<td nowrap="nowrap"><nessfw:caption name="queue_process"/> </td>
					<td><nessfw:textField id="f1" defaultValue="" dirtable="false" /></td>
					<td nowrap="nowrap"><nessfw:caption name="queue_stage"/> </td>
					<td><nessfw:textField id="f2" defaultValue="" dirtable="false" /></td>
					<td><nessfw:button name="search" value="show"/></td>
					<td nowrap="nowrap"><nessfw:link name="search" value="queue_link" /></td>
				</tr>
			</table>
		</td>
	</tr>	
	<tr>
		<td valign="top"><nessfw:table submitOnRowSelection="false" id="queueTableModel" pagingControlType="15"/></td>
	</tr>
</table>		