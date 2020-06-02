<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:codeBehind language="java" location="/JavaSource/pagecode/Catalog.java" --%><%-- /jsf:codeBehind --%>
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
<TITLE>catalog.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY><hx:scriptCollector id="scriptCollector1">
	<h:form styleClass="form" id="form1">
			<P>This is the movie catalog<BR>
			<BR>
			Store : <h:outputText styleClass="outputText" id="text6"
				value="#{movieBean.store}" /></P>
			<h:dataTable styleClass="dataTable" id="movieTbl"
				value="#{movieBean.movies}" var="movie">
				<h:column id="name">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Name" id="text2"></h:outputText>
					</f:facet>
					<h:commandLink styleClass="commandLink" id="link2" action="#{movieBean.movieDetails}">
									<h:outputText styleClass="outputText" value="#{movie.name}" />
					</h:commandLink>
				</h:column>
				<h:column id="year1">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Year" id="text3"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.year}" />
				</h:column>
				<h:column id="director1">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Director" id="text4"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.director}" />
				</h:column>
				<h:column id="gender">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Gender" id="text5"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.genderString}" />
				</h:column>
			</h:dataTable>
			<BR>
			<h:commandLink styleClass="commandLink" id="link1" action="back">
				<h:outputText id="text1" styleClass="outputText" value="back"></h:outputText>
			</h:commandLink>
		</h:form>
		
		</hx:scriptCollector></BODY>
	
	
</f:view>
</HTML>
