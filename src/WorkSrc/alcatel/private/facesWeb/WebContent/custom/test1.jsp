<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:pagecode language="java" location="/JavaSource/pagecode/custom/Test1.java" --%><%-- /jsf:pagecode --%>
<HTML>
<HEAD>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<META http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>test.jsp</TITLE>
</HEAD>

	<BODY>	
	<H1>Some JSF Examples.</H1>
	<h2>Text examples </h2>
	<f:view>
	<h:form>
	<p>
	<br>
	<h:outputText value="input text example" />
	<h:inputText value="some value" />
	<br>
	<h:outputText value="input secret example" />
	<h:inputSecret value="some value" />

	<br>
	<h:outputText value="input text area example" />
	<h:inputTextarea value="some value" rows="5" cols="40"/>
	
	<h2> Commands and links </h2>
	<p>
	<br>
	Command Button: <h:commandButton action="" value="Press Me" />
	<br>
	Command Link:  <h:commandLink action="" >
					<h:outputText value="some Link" />
		 </h:commandLink>
		
		
	<h2> Some selection examples: </h2>
	
	<p>
	
	<br> SelectBoolena Checkbox: <h:selectBooleanCheckbox value="select me" />
	<br>
	<br>Select Many CheckBoxes: 
	<h:selectManyCheckbox layout="pageDirection">
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>
	</h:selectManyCheckbox>	
	<br><br>
	<h:selectOneRadio layout="lineDirection">
		<f:selectItem itemLabel="item-1" itemValue="1"/>
		<f:selectItem itemLabel="item-2" itemValue="2"/>
		<f:selectItem itemLabel="item-3" itemValue="3"/>
	</h:selectOneRadio>	
	
	
	<br> Panel Grid 2 col
	<h:panelGrid columns="2" border="4">
		<h:outputText value="Some text 1" />
		<h:outputText value="Some text 2" />
		<h:outputText value="Some text 3" />
		<h:outputText value="Some text 4" />
	</h:panelGrid>

	<br> Panel Grid with panel group 2 col
	<h:panelGrid columns="2" >
		<h:outputText value="Some text 1" />
		<h:outputText value="Some text 2" />
		<h:panelGroup>
			<h:outputText value="Some text 2a" />
			<h:outputText value="Some text 2b" />
		</h:panelGroup>
		<h:outputText value="Some text 3" />
		<h:outputText value="Some text 4" />
	</h:panelGrid>
	
	<br> some List
	<ul>
		<li> <h:outputText value="item-1" /></li>
		<li> <h:outputText value="item-2" /></li>
		<li> <h:outputText value="item-3" /></li>
	</ul>
		<f:subview id="footerView">
			<c:import url="inc_test1.jsp" />
		</f:subview>	
	</h:form>
	
	
	</f:view>
	
	
</BODY>
</HTML>	
