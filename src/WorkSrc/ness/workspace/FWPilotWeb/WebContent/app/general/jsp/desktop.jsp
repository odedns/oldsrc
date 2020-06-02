<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table height="100%" width="100%" border="0">
	<tr>
		<td><nessfw:caption name="desktop"/></td>
	</tr>

	<tr>
		<td>
			<table><tr>
				<td><nessfw:caption name="desktop_search"/></td>
				<td><select></td>
				<td><nessfw:caption name="desktop_by"/></td>
				<td><select></td>
				<td><INPUT type="text"/></td>
				<td><nessfw:button name="search" value="search" eventName=""/>
				<td><nessfw:link name="search" value="advancedSearch" eventName=""/>
			</tr></table>
		</td>
	</tr>
		
	<tr>
		<td><nessfw:caption name="desktop_queue"/></td>
	</tr>



