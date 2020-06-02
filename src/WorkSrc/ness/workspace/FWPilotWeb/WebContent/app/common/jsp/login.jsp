<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %> 
<html>
<head>
<TITLE><nessfw:caption decorated="false" name="title"></nessfw:caption> </TITLE>
</head>
<nessfw:body className="body">
	<nessfw:form>
		<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="text-align: center;">
			<tr>
				<td class="headerTitle"><nessfw:caption name="loginTitle"/></td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="20">
						<tr>
							<td>
								<table cellpadding="0" cellspacing="8">
									<tr>
										<td class="label"><nessfw:caption name="user" suffix=":"/></td>				
										<td><nessfw:textField id="inputUserId" defaultValue="admin" width="130" /></td>
									</tr>
									<tr>
										<td class="label"><nessfw:caption name="password" suffix=":"/></td>										
										<td><nessfw:textField id="password" defaultValue="admin" textType="<%=com.ness.fw.ui.UIConstants.TEXT_FIELD_TYPE_PASSWORD %>" width="130" /></td>
									</tr>		
									<tr>
										<td></td>
										<td><nessfw:button eventName="login" value="login" width="50%" /></td>
									</tr>
								</table>
							</td>
							<td>
								<nessfw:button imageSrc="Harel_LogoBig.jpg" width="270" height="396" />
							</td>
						</tr>
					</table>
				</td>						
			</tr>
		</table>		
	</nessfw:form>	
</nessfw:body>
</html>



