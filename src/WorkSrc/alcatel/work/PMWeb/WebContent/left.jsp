<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>left.html</TITLE>
</HEAD>
<BODY bgcolor="aqua">
<p> <h2> Menu </h2>
<p><A href="/PMWeb/servlet/test.SnoopServlet" target="details">SnoopServlet</A>

<p><A href="/PMWeb/servlet/test.MemoryLeakServlet" target="details">MemoryLeakServlet</A>
<p><A href="/PMWeb/servlet/test.CollectionLeakServlet" target="details">CollectionLeakServlet</A>
<p><A href="/PMWeb/servlet/test.RequestServlet?action=select" target="details">Select Action</A>
<p><A href="/PMWeb/servlet/test.RequestServlet?action=insert" target="details">Insert Action</A>


</BODY>
</HTML>
