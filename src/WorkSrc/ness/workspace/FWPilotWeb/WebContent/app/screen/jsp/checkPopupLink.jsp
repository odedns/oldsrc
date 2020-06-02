<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<TITLE><nessfw:caption decorated="false" name="dialog"/></TITLE>

<nessfw:body className="bodyDialog">
<nessfw:form>
	<table width="100%">
		<tr>
			<td><nessfw:label value="Dummy page"/></td>
		</tr>
		<tr>
			<td>
				<table>
					<tr>
						<td width="50">&nbsp;</td>
						<td><nessfw:button value="close" onClick="self.close()" enabledClassName="buttonDialog"/></td>
					</tr>
				</table>
			</td>	
		</tr>
	</table>
</nessfw:form>
</nessfw:body>