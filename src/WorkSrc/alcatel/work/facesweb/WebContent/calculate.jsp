<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:codeBehind language="java" location="/JavaSource/pagecode/Calculate.java" --%><%-- /jsf:codeBehind --%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@taglib uri="http://www.ibm.com/jsf/html_extended" prefix="hx"%>
<HTML >
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
<TITLE>calculate.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="theme/stylesheet.css"
	title="Style">
<LINK rel="stylesheet" type="text/css" href="theme/rte_style.css"
	title="Style">
</HEAD>
<f:view>
	<BODY>
	<hx:scriptCollector id="scriptCollector1">
		<h:form styleClass="form" id="form1">
			<H1>JSF Calculator</H1>
			<P><BR>
			<BR>
			<h:outputText styleClass="outputText" id="comment"
				value="Created using WSAD" style="font-size: 18; font-weight: bold"></h:outputText><BR>
			
			</P>
			<TABLE border="0" cellpadding="3">
				<TBODY>
					<TR>
						<TD><h:outputText styleClass="outputText" id="label1"
							value="Number 1 (11-888) :">
							<f:convertNumber integerOnly="true" />
						</h:outputText></TD>
						<TD><h:inputText styleClass="inputText" id="number1"
							required="true" maxlength="3" size="12" value="#{pc_Calculate.calculator.number1}">
							<f:convertNumber integerOnly="true" />
							<f:validateLongRange minimum="11" maximum="888"></f:validateLongRange>
						</h:inputText><h:message styleClass="message" id="message1"
							for="number1"></h:message></TD>
					</TR>
					<TR>
						<TD><h:outputText styleClass="outputText" id="operationLabel"
							value="Operation"></h:outputText></TD>
						<TD><h:selectOneMenu styleClass="selectOneMenu" id="operation" value="#{pc_Calculate.calculator.operation}" valueChangeListener="#{pc_Calculate.handleOperationValueChange}">
							<f:selectItem itemLabel="add" itemValue="Add" />
							<f:selectItem itemLabel="subtract" itemValue="Subtract" />
							<f:selectItem itemLabel="multiply" itemValue="Multiply" />
							<f:selectItem itemLabel="divide" itemValue="Divide" />
						</h:selectOneMenu></TD>
					</TR>
					<TR>
						<TD><h:outputText styleClass="outputText" id="label2"
							value="Number 2 (1-19, odd):"></h:outputText></TD>
						<TD><h:inputText styleClass="inputText" id="number2" size="12"
							maxlength="2" value="#{pc_Calculate.calculator.number2}">
							<f:convertNumber integerOnly="true" /><f:validateLongRange minimum="1" maximum="19"></f:validateLongRange></h:inputText></TD>
					</TR>
					<TR>
						<TD></TD>
						<TD><hx:commandExButton type="submit" value="Calculate"
							styleClass="commandExButton" id="calc" action="#{pc_Calculate.doCalcAction}"></hx:commandExButton></TD>
					</TR>
					<TR>
						<TD><h:outputText styleClass="outputText" id="resultLabel"
							value="Result:"></h:outputText></TD>
						<TD><h:outputText styleClass="outputText" id="result"
							style="background-color: white; color: blue; font-size: 18pt" value="#{pc_Calculate.calculator.result}"><f:convertNumber /></h:outputText></TD>
					</TR>
				</TBODY>
			</TABLE>
			<TABLE>
				<TBODY>
					<TR>
						<TD align="left">ErrorMessage:</TD>
						<TD style="width: 5px">&nbsp;</TD>
						<TD><h:outputText id="text1"
							value="#{pc_Calculate.calculator.errorMessage}"
							styleClass="outputText">
						</h:outputText></TD>
					</TR>
					<TR>
						<TD align="left">Operation:</TD>
						<TD style="width: 5px">&nbsp;</TD>
						<TD><h:outputText id="text2"
							value="#{pc_Calculate.calculator.operation}"
							styleClass="outputText">
						</h:outputText></TD>
					</TR>
					<TR>
						<TD align="left">Number1:</TD>
						<TD style="width: 5px">&nbsp;</TD>
						<TD><h:outputText id="text3"
							value="#{pc_Calculate.calculator.number1}"
							styleClass="outputText">
							<f:convertNumber />
						</h:outputText></TD>
					</TR>
					<TR>
						<TD align="left">Number2:</TD>
						<TD style="width: 5px">&nbsp;</TD>
						<TD><h:outputText id="text4"
							value="#{pc_Calculate.calculator.number2}"
							styleClass="outputText">
							<f:convertNumber />
						</h:outputText></TD>
					</TR>
					<TR>
						<TD align="left">Result:</TD>
						<TD style="width: 5px">&nbsp;</TD>
						<TD><h:outputText id="text5"
							value="#{pc_Calculate.calculator.result}" styleClass="outputText">
							<f:convertNumber />
						</h:outputText></TD>
					</TR>
				</TBODY>
			</TABLE>
			<h:messages styleClass="messages" id="messages1"></h:messages>
			<BR>
			<hx:commandExButton id="button1" styleClass="commandExButton"
				type="submit" value="Submit">
			</hx:commandExButton>
		</h:form>
	</hx:scriptCollector>
	</BODY>
</f:view>
</HTML>
