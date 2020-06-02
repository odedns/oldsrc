<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:codeBehind language="java" location="/JavaSource/pagecode/Catalog.java" --%><%-- /jsf:codeBehind --%>
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
<TITLE>catalog.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY>
	<h:form styleClass="form" id="movieForm">
			<P>This is the movie catalog<BR>
			<BR>
			Store : <h:outputText styleClass="outputText" id="text6"
				value="#{movieBean.store}" /></P>
			<h:dataTable styleClass="dataTable" id="movieTbl" border="5" 
				value="#{movieBean.movies}" var="movie">
				<h:column id="select">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="" id="selectBox" />
					</f:facet>
					<h:selectBooleanCheckbox id="selectMovie" value="#{movie.selected}" />
				</h:column>
				
				<h:column id="name">
					<f:facet name="header">
						<h:commandLink action="#{movieBean.sortMovies}">
						<h:outputText styleClass="outputText" value="Name" id="text2"></h:outputText>
						</h:commandLink>
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
			<h:commandButton id="newMovie"action="movieDetails" value="New" />
			<h:commandButton id="deleteMovie" actionListener="#{movieBean.deleteMovie}" value="Delete" />
			<BR>
			<h:commandLink styleClass="commandLink" id="link1" action="back">
				<h:outputText id="text1" styleClass="outputText" value="back"></h:outputText>
			</h:commandLink>
		</h:form>
		
	
	
</f:view>
</HTML>
