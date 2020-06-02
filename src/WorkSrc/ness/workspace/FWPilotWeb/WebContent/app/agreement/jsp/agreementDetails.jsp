<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<%@ page import="com.ness.fw.ui.*" %>
<%@ page import="com.ness.fw.ui.events.*" %>
<%@ page import="com.ness.fw.flower.core.*" %>
<%@ page import="com.ness.fw.flower.servlet.*" %>

<%
	// Getting the heskem 
	// TBR -> should be replaces with TAG
	Context ctx = ((ResultEvent)request.getAttribute(HTMLConstants.RESULT_EVENT)).getFlow().getCurrentStateContext();

	ListModel sugRadioModel = (ListModel) ctx.getField("agreementTypeModel");
	String type = sugRadioModel.getValue(sugRadioModel.getSelectedKey());
	ListModel statusModel = (ListModel) ctx.getField("statusModel");	
	String status = statusModel.getValue(statusModel.getSelectedKey());	
%>
<table width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="1" colspan="3" class="headerTitle"><nessfw:label className="emptyClass" id="title"/></td>
	</tr>
	<tr>
		<td colspan="3" height="1">
			<nessfw:panel frameClassName="identityTitle">
				<table cellspacing="0" class="identityDetails" width="100%">
					<tr>
						<td width="8%"><nessfw:caption name="pirteyHeskem_name"/></td>
						<td width="10%"><nessfw:label id="name" /></td>
						<td width="4%"><nessfw:caption name="pirteyHeskem_id"/></td>
						<td width="10%"><nessfw:label id="identification" /></td>
						<td width="4%"><nessfw:caption name="pirteyHeskem_type"/></td>
						<td width="10%" class="label"><%=type%></td>						
						<td width="4%"><nessfw:caption name="pirteyHeskem_status"/></td>
						<td class="label"><%=status%></td>
					</tr>
				</table>
			</nessfw:panel>
		</td>
	</tr>
	<tr>
		<td colspan="3" style="height: 10"></td>
	</tr>
	<tr>
		<td style="width:200;">
		<nessfw:displayState id="agreementDisplayStateModel">	
			<table height="100%" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td class="treeBackground">
						<div id=div2" class="treeDiv" width="100%">
							<nessfw:tree  id="agreementDetailsTreeModel"/>
						</div>
					</td> 
				</tr>
				
				<tr>
					<td height="1" class="treeBackground2" style="text-align: center;">		
						<table height="1" cellpadding="0" cellspacing="0" class="buttonsPadding" width="100%">
							<tr style="vertical-align: bottom;">						
								<td><nessfw:button enabledClassName="treeButton"  eventName="new" value="new" targetType="<%=com.ness.fw.ui.events.Event.EVENT_TARGET_TYPE_DIALOG%>" dialogParams="scroll:no;status:no;dialogHeight:600px;dialogWidth:700px;dialogLeft:162px;dialogTop:90px;"/></td>
								<td style="width: 4px;"></td>
								<td><nessfw:button enabledClassName="treeButton" id="deleteModel" value="delete" /></td>
								<td width="45%"></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</td>
		<td style="width:10;"></td>
		<td valign="top">
			<nessfw:include/>
			</nessfw:displayState>
		</td>		
	</tr>
</table>