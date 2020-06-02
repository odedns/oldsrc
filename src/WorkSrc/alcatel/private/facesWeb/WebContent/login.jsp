<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:codeBehind language="java" location="/JavaSource/pagecode/Login.java" --%><%-- /jsf:codeBehind --%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
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
<TITLE>login.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY>
	<h:form styleClass="form" id="form1">
		<h:outputText id="text1" styleClass="outputText" value="User:"></h:outputText>
	    <h:inputText styleClass="inputText" id="userId" value="#{sharedBean.user}" required="true"></h:inputText> 
	    <h:message id="msg1" for="userId" />
	    <h:commandButton id="loginButton" value="Submit" action="#{sharedBean.login}" /></h:form>
	<P>Place content here.</P>
	</BODY>
</f:view>
</HTML>

