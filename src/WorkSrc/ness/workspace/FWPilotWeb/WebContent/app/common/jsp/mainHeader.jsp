<%@ page import="java.util.*, java.text.*" %>
<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="2" height="1" class="headerImage"></td>
    </tr>
    <tr>
		<td valign="top">
        	<table width="100%" cellpadding="0" cellspacing="0">
        		<tr>
            		<td valign="bottom" class="headerImage2" colspan="3">
            			<table width="100%" height="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="100%" height="100%"><nessfw:menu/></td>
								<td></td>
								<td nowrap="nowrap" width="1%"></td>
								<td>&nbsp;&nbsp;</td>
								<td width="1">
			                  		<table width="100%" height="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td width="100%"></td>
											<td width="1">
												<table cellspacing="0" cellpadding="0">
													<tr>	
														<td class="headerButton"><nessfw:button imageSrc="I_Desktop.png" name="desktop" width="22px" height="22px" onClick="return false" value=""/></td>										
														<td class="headerButton"><nessfw:button imageSrc="I_Print.png" name="print" width="22px" height="22px" onClick="return false" value=""/></td>
														<td class="headerButton"><nessfw:button imageSrc="I_Help.png" name="help" width="22px" height="22px" onClick="return false" value=""/></td>
														<td class="headerButton"><nessfw:button imageSrc="I_Exit.png" name="exit" width="22px" height="22px" onClick="return false" value=""/></td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
            		</td>
            	</tr>
        		<tr>
        			<td class="headerImage3"></td>
        			<td class="headerImage4"></td>
                  	<td height="31" valign="bottom" class="headerImage5">        		
                  		<table width="100%" height="100%" cellspacing="0" cellpadding="0">
                  			<tr>
								<td width="100%"></td>
								<td nowrap class="mainHeaderCaption"><nessfw:label className="emptyClass" id="userId"/></td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<% SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm");%>
		               			<td class="mainHeaderCaption" nowrap="nowrap"><%=sdf.format(new Date())%></td>
     						</tr>
     					</table>
                	</td>
            	</tr>
            </table>
    	</td>
        <td valign="bottom" width="1"><nessfw:button imageSrc="Harel_Logo.jpg" width="63px" height="53px" value=""/></td>
	</tr>
</table>

