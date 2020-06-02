<%@ page import="com.ness.fw.ui.events.Event" %>
<%@taglib uri="/WEB-INF/tld/taglib.tld" prefix="nessfw" %>

<table width="100%" height="100%">
	<tr>		
		<td height="100%"><nessfw:table cssPre="" submitOnRowSelection="true" id="documentsTableModel"/></td>
	</tr>
	<tr>
		<td class="headerOtherTitle"><nessfw:caption name="documents_title"/> </td>
	</tr>
	<tr>
		<td>
			<jsp:include page="docDetail.jsp"></jsp:include>
		</td>
	</tr>
	<tr>
		<td colspan="4"> 
			<nessfw:button eventName="new" value="new" targetType="<%=Event.EVENT_TARGET_TYPE_DIALOG%>" dialogParams="scroll:no;status:no;dialogHeight:200px;dialogWidth:200px;dialogLeft:412px;dialogTop:290px;"/>
			<nessfw:button eventName="update" value="update"/>
 			<nessfw:button eventName="delete" value="delete"/>
 			<nessfw:button eventName="deleteAll" value="deleteAll"/>
  		</td>
	</tr>	
</table>