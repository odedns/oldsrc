<%@ page import="com.ness.fw.ui.UIConstants" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td height="22"><nessfw:caption className="headerTitle" name="searchAgreement_title"/></td>
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
										<table cellpadding="2" cellspacing="2">
											<tr>
												<td nowrap="nowrap"><nessfw:caption name="searchAgreement_name"/></td>
												<td><nessfw:textField id="name" dirtable="false"/></td>
												<td width="100">&nbsp;</td>
												<td nowrap="nowrap"><nessfw:caption name="searchAgreement_status"/></td>
												<td ><nessfw:list id="statusModel" type="<%=UIConstants.LIST_TYPE_SINGLE %>"  dirtable="false"/></td> 		
											</tr>	
											<tr>	
												<td nowrap="nowrap"><nessfw:caption name="searchAgreement_id"/></td>
												<td><nessfw:textField id="identification"  dirtable="false"/></td>
												<td width="100">&nbsp;</td>
												<td nowrap="nowrap"><nessfw:caption name="searchAgreement_description"/></td>
												<td><nessfw:list id="likeModel" addOptionalPrompt="false"  dirtable="false"/>
												<td><nessfw:textField id="description"  dirtable="false" width="200"/></td>
											</tr>
											<tr>	
												<td nowrap="nowrap"><nessfw:caption name="searchAgreement_type"/></td>
												<td><nessfw:list id="sugModel" type="<%=UIConstants.LIST_TYPE_SINGLE %>"  dirtable="false"/></td> 						
												<td></td>
												<td><nessfw:caption name="searchAgreement_cust"/></td>
												<td><nessfw:textField id="customerID"  dirtable="false" width="100"/></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td height="1">
										<table width="100%">
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
						<td height="100%"><nessfw:table id="searchResultTableModel" allowColumnOrder="true"/></td>							
					</tr>
					<tr>
						<td align="center"><nessfw:toolbar group="paging" id="pagingToolBar"/></td>
					</tr>
				</table>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="2" class="buttonsPadding">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td><nessfw:button eventName="new" value="new"/></td>
					<td><nessfw:button eventName="delete" value="delete"/></td>
					<td width="100%"></td>
				</tr>
			</table>
		</td>		
	</tr>		
</table>
