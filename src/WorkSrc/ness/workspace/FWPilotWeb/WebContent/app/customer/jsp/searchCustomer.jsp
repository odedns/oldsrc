<%@ page import="com.ness.fw.ui.events.Event" %>
<%@ page import="com.ness.fw.ui.UIConstants" %>

<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td height="22"><nessfw:caption className="headerTitle" name="searchCustomer_title"/></td>
	</tr>
	<tr>
		<td width="100%" height="100%">
			<div class="genericTableFrame">
				<table width="100%" height="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td>
							<table width="100%" height="100%" cellpadding="0" cellspacing="0">
								<tr>
									<td>
										<table cellpadding="2" cellspacing="2" cellpadding="0" cellspacing="0">
											<tr>
												<td nowrap="nowrap"><nessfw:caption name="searchCustomer_id"/></td>
												<td><nessfw:textField id="id" dirtable="false"/></td>
												<td width="100">&nbsp;</td>
												<td nowrap="nowrap"><nessfw:caption name="searchCustomer_type"/></td>
												<td><nessfw:list id="customerTypeModel" dirtable="false"/></td>
												<td width="100">&nbsp;</td>
												<td>&nbsp;</td>
												<td>&nbsp;</td>
											</tr>	
											<tr>	
												<td nowrap="nowrap"><nessfw:caption name="searchCustomer_name"/></td>
												<td><nessfw:textField id="firstName" dirtable="false"/></td>
												<td>&nbsp;</td>
												<td nowrap="nowrap"><nessfw:caption name="searchCustomer_city"/></td>
												<td><nessfw:textField id="city" dirtable="false"/></td>
												<td>&nbsp;</td>
												<td nowrap="nowrap"><nessfw:caption name="searchCustomer_telephone"/></td>
												<td><nessfw:textField id="telephone" dirtable="false" textType="<%=UIConstants.TEXT_FIELD_TYPE_INTEGER %>"/></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="1">
										<table width="100%" cellpadding="0" cellspacing="0">
											<tr>
												<td width="100%"></td>
												<td>
													<nobr>
													<nessfw:button eventName="search" value="search" tooltip="searchTooltip"/>
													<nessfw:button eventName="clearFields" value="clearFields"/>
													</nobr>
												</td>
											</tr>
										</table>
									</td>
								</tr> 
							</table>
						</td>
					</tr>
					<tr>		
						<td height="100%"><nessfw:table submitOnRowSelection="false" id="searchResultTableModel" pagingControlType="<%=UIConstants.PAGING_FIRSTLAST + UIConstants.PAGING_PREVNEXT + UIConstants.PAGING_SPECIFIC_LINK + UIConstants.PAGING_SPECIFIC_COMBO %>" allowColumnOrder="true"/></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td class="buttonsPadding">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td><nessfw:button eventName="new" value="new" targetType="<%=Event.EVENT_TARGET_TYPE_DIALOG%>" dialogParams="scroll:no;status:no;dialogHeight:200px;dialogWidth:200px;dialogLeft:412px;dialogTop:290px;"/></td>
					<td></td>
				</tr>
			</table>
		</td>		
	</tr>	
</table>