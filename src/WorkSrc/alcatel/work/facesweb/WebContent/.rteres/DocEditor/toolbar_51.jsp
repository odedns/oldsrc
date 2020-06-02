         
<%@ page contentType="text/html; charset=utf-8" %>

<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>


<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
                            
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); %>
<% String editorName = aEditor.getName(); %>
<% String locale = (String)aEditor.getAttribute("locale"); %>
<% String images = (String)aEditor.getAttribute("images"); %>
<% String directory = (String)aEditor.getAttribute("directory"); %>
<% ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); %>
<% boolean withHelp = aEditor.getAttribute("addHelp") != null; %>

<% String isBidi = (String)aEditor.getAttribute("isBidi"); %>
<% String imageDirection = "_ltr"; if (isBidi.equalsIgnoreCase("true")) imageDirection = "_rtl"; %>
<% String undoImg = "undo" + imageDirection + ".gif"; %>
<% String redoImg = "redo" + imageDirection + ".gif"; %>

<% String rb_1 = resourceBundle.getString("InsertRowAbove"); %>
<% String rb_2 = resourceBundle.getString("InsertRowBelow"); %>
<% String rb_3 = resourceBundle.getString("DeleteRow"); %>
<% String rb_4 = resourceBundle.getString("InsertColumnLeft"); %>
<% String rb_5 = resourceBundle.getString("InsertColumnRight"); %>
<% String rb_6 = resourceBundle.getString("DeleteColumn"); %>
<% String rb_7 = resourceBundle.getString("InsertPageBreak"); %>
<% String rb_8 = resourceBundle.getString("Paragraph"); %>
<% String rb_9 = resourceBundle.getString("FindAndReplace"); %>
<% String rb_10 = resourceBundle.getString("OperationCannotBeUndone"); %>

<% String error_rowInsert = resourceBundle.getString("rte.table.incorrectRowInsert"); %>
<% String error_columnInsert = resourceBundle.getString("rte.table.incorrectColumnInsert"); %>
<% String error_rowDelete = resourceBundle.getString("rte.table.incorrectRowDelete"); %>
<% String error_columnDelete = resourceBundle.getString("rte.table.incorrectColumnDelete"); %>

<% String fn_table1 = "IBM_RTE_insertRowAbove('" + editorName + "','"+error_rowInsert+"')"; 
 String fn_table2 = "IBM_RTE_insertRowBelow('" + editorName + "')"; 
 String fn_table3 = "IBM_RTE_deleteRow('" + editorName + "', '" + rb_10 + "','"+error_rowDelete+"')"; 
 String fn_table4 = "IBM_RTE_insertColumnLeft('" + editorName + "','"+error_columnInsert+"')"; 
 String fn_table5 = "IBM_RTE_insertColumnRight('" + editorName + "','"+error_columnInsert+"')"; 
 String fn_table6 = "IBM_RTE_deleteColumn('" + editorName + "', '" + rb_10 + "','"+error_columnDelete+"')"; %>

<% String fn_insertPageBreak = "IBM_RTE_insertPageBreak('" + editorName + "')"; %>
<% String fn_paraSupport = "IBM_RTE_paraSupport('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "')"; %>
<% String fn_findReplace = "IBM_RTE_findReplace('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "')";%>



<% String toolbarName = "Toolbar51"; %>


<docEditor:addToolbar editor="<%= editorName %>" name="<%= toolbarName %>" justify="left" />

                                      
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowAbove" text="<%= rb_1 %>" image="insertrowabove.gif" script="<%= fn_table1 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowBelow" text="<%= rb_2 %>" image="insertrowbelow.gif" script="<%= fn_table2 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowDelete" text="<%= rb_3 %>" image="deleterow.gif" script="<%= fn_table3 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColLeft" text="<%= rb_4 %>" image="insertcolumnleft.gif" script="<%= fn_table4 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColRight" text="<%= rb_5 %>" image="insertcolumnright.gif" script="<%= fn_table5 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColDelete" text="<%= rb_6 %>" image="deletecolumn.gif" script="<%= fn_table6 %>"/>

<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider1" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonInsertPageBreak"  text="<%= rb_7 %>" image="bold.gif" script="<%= fn_insertPageBreak %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonParagraph" text="<%= rb_8 %>" image="link.gif" script="<%= fn_paraSupport %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonFindReplace" text="<%= rb_9 %>" image="closeToolbarButton.gif" script="<%= fn_findReplace %>"/>


