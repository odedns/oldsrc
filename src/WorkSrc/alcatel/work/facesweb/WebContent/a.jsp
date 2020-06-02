<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://www.ibm.com/jsf/html_extended" prefix="hx"%>
<HTML >
<HEAD>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="WEB-INF/tld/onjlib.tld" prefix="my" %>
<%@ page 
language="java"
contentType="text/html; charset=WINDOWS-1255"
pageEncoding="WINDOWS-1255"
%>
<META http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>a.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY><hx:scriptCollector id="scriptCollector1">
	<P><BR>
	
	</P>
		<h:form styleClass="form" id="form1">
			<hx:panelActionbar styleClass="panelActionbar" id="actionbar1">
				<h:commandLink id="link1" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="item1" id="text1"></h:outputText>
				</h:commandLink>
				<h:commandLink id="link2" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="item2" id="text2"></h:outputText>
				</h:commandLink>
				<h:commandLink id="link3" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="item3" id="text3"></h:outputText>
				</h:commandLink>
			</hx:panelActionbar>
			<my:imageButton src="../theme/submit.gif" id="test" action="#{sharedBean.testAction}"></my:imageButton>
			<my:menu id="mymenu" />
			<BR>Select one list box
			<BR>
			<h:selectOneListbox styleClass="selectOneListbox" id="listbox1">
				<f:selectItem itemValue="1" itemLabel="First" />
				<f:selectItem itemValue="2" itemLabel="Second" />
				<f:selectItem itemValue="3" itemLabel="Third" />
			</h:selectOneListbox>

				<BR>
			Panel Menu bar<BR>
			<hx:panelActionbar styleClass="panelActionbar" id="actionbar2">
				<h:commandLink id="link4" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="Hyperlink Label"
						id="text4"></h:outputText>
				</h:commandLink>
				<h:commandLink id="link5" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="Hyperlink Label"
						id="text5"></h:outputText>
				</h:commandLink>
				<h:commandLink id="link6" styleClass="commandLink">
					<h:outputText styleClass="outputText" value="Hyperlink Label"
						id="text6"></h:outputText>
				</h:commandLink>
			</hx:panelActionbar>
			<BR>a link<BR>
			<hx:outputLinkEx styleClass="outputLinkEx"
				value="http://localhost:9080/servlet/SnoopServlet" id="linkEx1">
				<h:outputText id="text7" styleClass="outputText" value="Test Link"></h:outputText>
			</hx:outputLinkEx>
		
			<BR>
			<my:menu id="mymenu2" label="mymenu" styleClass="outputText" value="#{sharedBean.items}"/>
		</h:form>
	</hx:scriptCollector></BODY>
</f:view>

</HTML>
