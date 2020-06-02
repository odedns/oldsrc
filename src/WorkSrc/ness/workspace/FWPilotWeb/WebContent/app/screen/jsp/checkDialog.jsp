<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<TITLE><nessfw:caption decorated="false" name="dialog"/></TITLE>

<nessfw:body className="bodyDialog">
<nessfw:form>
	<table width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td height="1"><nessfw:textField id="custId" defaultValue="58"></nessfw:textField></td>
			<td><nessfw:textField id="customerName"/></td> 
		</tr>		
		<tr>								
			<td>
				<table>
					<tr>
						<td><nessfw:button eventName="search" value="search" enabledClassName="buttonDialog"/></td>
						<td><nessfw:button value="close" onClick="self.close()" enabledClassName="buttonDialog"/></td> 
					</tr>
				</table>
			</td>						
		</tr>
		
		<tr>
			<td height="1"><nessfw:link eventName="realLink" value="ok"/></td>
		</tr>					
	</table>
</nessfw:form>
</nessfw:body>