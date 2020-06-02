<%@ taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>
<html>
<HEAD>
<TITLE><nessfw:caption decorated="false" name="title"></nessfw:caption> </TITLE>
</HEAD>
<nessfw:body name="main" className="body">
	<nessfw:form>
	
	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="mainHeaderHeight"><jsp:include page="mainHeader.jsp" /></td>
		</tr>
		<tr valign="top" class="mainScreenSpace">
			<td>
				<div id="DivMainScreen" class="mainScreenDiv">
					<nessfw:include/> 
				</div>
			</td>
		</tr>
		<tr>
			<td class="mainMessage">
				<nessfw:messages normalHeight="1" expandHeight="80" id="messagesModel"/>
			</td>
		</tr> 			
		<tr>
			<td class="mainFooter">
				<table cellpadding="0" cellspacing="0" width="100%" height="1" border="0">
					<tr class="footerButtons">
						<td><nessfw:toolbar id="rightToolBar" group="right"/></td>		
						<td width="100%"></td>						
						<td><nessfw:toolbar id="leftToolBar" group="left" /></td> 
					</tr>
				</table>
			</td>
		</tr>			 
	</table>


	</nessfw:form>
</nessfw:body>
</html>