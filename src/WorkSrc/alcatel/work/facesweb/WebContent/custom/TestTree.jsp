<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%-- jsf:pagecode language="java" location="/JavaSource/pagecode/custom/TestTree.java" --%><%-- /jsf:pagecode --%>
<%@taglib uri="http://www.ibm.com/jsf/html_extended" prefix="hx"%>
<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<HTML>
<HEAD>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://www.ibm.com/jsf/BrowserFramework" prefix="odc"%>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<META http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>TestTree.jsp</TITLE>
<LINK rel="stylesheet" type="text/css" href="../theme/tree.css"
	title="Style">
<link rel="stylesheet" type="text/css" href="../theme/stylesheet.css"
	title="Style">
</HEAD>
<f:view>
	<BODY>
	<hx:scriptCollector id="scriptCollector1">
	<h:form styleClass="form" id="form1"><P><BR>
			<odc:tree enableSelect="false" rootVisibleFlag="true" id="tree1"
				styleClass="tree" value="#{treeRoot}">
				<odc:treeNodeAttr attributeName="name"
					className="com.ibm.odcb.tutorial.businessobjects.Root"
					id="treenodeattr1" referenceName="placeHolderStock, users, stocks"></odc:treeNodeAttr>
				<odc:treeNodeAttr
					className="com.ibm.odcb.tutorial.businessobjects.Stock"
					id="treenodeattr2" attributeName="change"></odc:treeNodeAttr>
				<odc:treeNodeAttr
					className="com.ibm.odcb.tutorial.businessobjects.User"
					id="treenodeattr3" attributeName="lastName"
					referenceName="portfolios"></odc:treeNodeAttr>
				<odc:treeNodeAttr
					className="com.ibm.odcb.tutorial.businessobjects.Portfolio"
					id="treenodeattr4" attributeName="portfolioName"
					referenceName="positions"></odc:treeNodeAttr>
				<odc:treeNodeAttr
					className="com.ibm.odcb.tutorial.businessobjects.Position"
					id="treenodeattr5" attributeName="refNum"
					referenceName="stock, purchaseDate"></odc:treeNodeAttr>
				<odc:treeNodeAttr className="java.util.Date" id="treenodeattr6"
					attributeName="month"></odc:treeNodeAttr>
			</odc:tree>
			
	</p>
			<p></p>
			<p><hx:commandExButton type="submit" value="Open Dialog" id="open"
				styleClass="commandExButton"></hx:commandExButton> <hx:inputMiniCalendar
				id="miniCalendar1" styleClass="inputMiniCalendar">
				<hx:convertDateTime />
			</hx:inputMiniCalendar><br>

		</h:form>
	</hx:scriptCollector>
	</BODY>
</f:view>
</HTML>
