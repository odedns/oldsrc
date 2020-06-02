<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:codeBehind language="java" location="/JavaSource/pagecode/Userdetails.java" --%><%-- /jsf:codeBehind --%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://www.ibm.com/jsf/html_extended" prefix="hx"%>
<HTML>
<HEAD>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ page 
language="java"
contentType="text/html; charset=WINDOWS-1255"
pageEncoding="WINDOWS-1255"
%>
<META http-equiv="Content-Type"
	content="text/html; charset=WINDOWS-1255">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>UserDetails.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY><hx:scriptCollector id="scriptCollector1">
	<h:form styleClass="form" id="form1"><P>Here are the details for the user:<BR>
		<h:outputText styleClass="outputText" id="text1" value="#{sharedBean.user}"></h:outputText>
		<BR>
		User balance:<BR>
		<h:outputText styleClass="outputText" id="text2" value="#{sharedBean.balance}"></h:outputText>
		<BR>
			<h:commandLink styleClass="commandLink" id="catalog" action="#{sharedBean.catalog}">
				<h:outputText id="text3" styleClass="outputText" value="Movie catalog"></h:outputText>
			</h:commandLink>
		<BR>
		<BR>
		</P></h:form>

	</hx:scriptCollector></BODY>
</f:view>
</HTML>
