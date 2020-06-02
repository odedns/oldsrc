<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>

<f:view>
	<BODY>
	<h:form styleClass="form" id="movieForm">
			<P>This is the movie catalog<BR>
			<BR>
			Store : <h:outputText styleClass="outputText" id="text6"
				value="#{movieBean.store}" /></P>
			<h:dataTable styleClass="dataTable" id="movieTbl" border="5" 
				value="#{movieBean.movies}" var="movie">
				
				<h:column id="name" >
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Name" id="text2"></h:outputText>
					</f:facet>
					<h:commandLink styleClass="commandLink" id="link2" action="#{movieBean.showMovieDetails}" immediate="false">
									<h:outputText styleClass="outputText" value="#{movie.name}" />
					</h:commandLink>
				</h:column>
				<h:column id="year1">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Year" id="text3"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.year}"  />
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
			
			<h:panelGrid columns="2" border="2">
				<h:outputText value="Name: " />
				<h:inputText styleClass="inputText" id="currName" value="#{movieBean.currentMovie.name}">
				</h:inputText>
				<h:outputText value="Year: " />
				<h:inputText styleClass="inputText" id="currYear" value="#{movieBean.currentMovie.year}" maxlength="4">
					<f:validateLongRange minimum="1900" maximum="2020"/>
				</h:inputText>
				<h:outputText value="Director: " />
				<h:inputText styleClass="inputText" id="currDirector" value="#{movieBean.currentMovie.director}" />
				<h:panelGroup>
				<h:commandButton id="newMovie"action="movieDetails" value="New" />
				<h:commandButton id="deleteMovie" actionListener="#{movieBean.deleteMovie}" value="Delete" />
				</h:panelGroup>
				<h:commandButton type="submit" value="Save" action="#{movieBean.showMovieDetails}" immediate="false"
							 id="save" />			
				
			</h:panelGrid>		
			
			
			<h:commandLink styleClass="commandLink" id="link1" action="back">
				<h:outputText id="text1" styleClass="outputText" value="back"></h:outputText>
			</h:commandLink>
			
			<h:messages />
							
		</h:form>			
	
</f:view>
</HTML>
