<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<f:view>
	<BODY>
	<P><BR>This is the movie details page.<BR>
	<BR>
	</P>
	<h:form styleClass="form" id="movieDetailsForm"><TABLE border="1">
		<TBODY>
			<TR>
				<TD>Name:</TD>
				<TD><h:inputText styleClass="inputText" id="text1" value="#{movieDetails.name}"><f:validateLength minimum="4"></f:validateLength></h:inputText>
				</TD>				
			</TR>
				
			<TR>
				<TD>Year</TD>
				<TD><h:inputText styleClass="inputText" id="text2" value="#{movieDetails.year}" maxlength="4">
					<f:validateLongRange minimum="1900" maximum="2020" />
				</h:inputText></TD>
			</TR>
			<TR>
				<TD>Director</TD>
				<TD><h:inputText styleClass="inputText" id="text3" value="#{movieDetails.director}" ></h:inputText></TD>
			</TR>
			<TR>
				<TD>Gender</TD>
				<TD><h:selectOneMenu styleClass="selectOneMenu" id="menu1" value="#{movieDetails.currentGender}">					
						<f:selectItems value="#{movieDetails.genders}"/>
					</h:selectOneMenu></TD>
			</TR>

					<TR>
						<TD></TD>
						<TD>
						<h:outputText id="text4" styleClass="outputText" rendered="false"
						value="Some more information"></h:outputText>
					</TR>
					<TR>
						<TD></TD>
						<TD> <h:messages id="msg" styleClass="errorMessage" layout="table" showDetail="true" errorClass="errors"/>
						</TD>
					</TR>
					<TR>
						<TD><h:commandButton type="submit" value="Close" action="closeDetails" immediate="true"
							styleClass="commandExButton" id="closeButton" /></TD>
						<TD>
						<h:commandButton type="submit" value="Save" action="#{movieDetails.saveMovieDetails}" immediate="false"
							styleClass="commandExButton" id="save" />
							<h:commandButton	value="Show Details" action="#{movieDetails.showDetails}"
								actionListener="#{movieDetails.movieActionListener}"
								 id="details" immediate="true"/>
					</TR>
				</TBODY>

	</TABLE>
	
	</h:form>



</f:view>
</HTML>
