<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<%@ page import="com.ness.fw.ui.events.Event" %>
<TITLE><nessfw:caption decorated="false" name="choosePackage"/></TITLE>
<%	
	String level[] = new String[5];
	level[1] = "level1Class";
	level[2] = "level2Class";
	level[3] = "level3Class";
	level[4] = "level4Class";
%>
<nessfw:body className="bodyDialog">
	<nessfw:form>
		<table height="100%" width="100%" cellpadding="0" cellspacing="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td height="100%" class="mainScreenSpace">
					<table height="100%" width="100%" cellpadding="0" cellspacing="3">
						<tr>
							<td valign="top">
								<nessfw:treeTable openImage="open.png" closeImage="close.png"  emptyImage="leaf.png"  submitOnRowSelection="false" id="catalogTableModel" levelClassNames="<%=level%>" onDblClick="getRowAndClose();" submitOnDblRowSelection="true"/>
							</td>
						</tr>
						<tr>
							<td height="1">
								<table cellpadding="0">
									<tr> 									
										<td width="99%"></td>
										<td>
											<nobr>
												<nessfw:button eventName="ok"  value="choose" onClick="getRow()" targetType="<%=Event.EVENT_TARGET_TYPE_CLOSE_DIALOG%>"/>
												<nessfw:button onClick="self.close()" value="cancel"/>
											</nobr>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<nessfw:variable id="selectedPackage"></nessfw:variable>
	</nessfw:form>
</nessfw:body>

<SCRIPT language="javascript">
function getRow()
{
	data = getSelectedRowExtraData("catalogTableModel","ID");
	document.all.selectedPackage.value = data;
}

function getRowAndClose()
{
	getRow();
	self.close();
}

</SCRIPT>