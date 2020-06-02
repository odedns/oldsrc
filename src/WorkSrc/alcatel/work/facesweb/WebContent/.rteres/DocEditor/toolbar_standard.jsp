<%@ page contentType="text/html; charset=utf-8" %>

<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
  String editorName = aEditor.getName(); 
 String locale = (String)aEditor.getAttribute("locale"); 
 String images = (String)aEditor.getAttribute("images"); 
 String directory = (String)aEditor.getAttribute("directory"); 
 ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); 
 boolean withHelp = aEditor.getAttribute("addHelp") != null; 
 String isBidi = (String)aEditor.getAttribute("isBidi"); 
 String fileName;
 if (withHelp) {
   fileName = "print_utf8";
   if (isBidi.equalsIgnoreCase("true")) 
            fileName += "_rtl.html";
        else
            fileName += ".html";
    }else {    
        if (locale.equalsIgnoreCase("ko")) 
            fileName = "print_euc_kr.html";
        else if (locale.equalsIgnoreCase("zh")) 
            fileName = "print_GB2312.html";
        else if (locale.equalsIgnoreCase("zh_tw")) 
            fileName = "print_big5.html";
        else if (locale.equalsIgnoreCase("ja")) 
            fileName = "print_shift_jis.html";
        else { 
            fileName = "print_utf8";
            if (isBidi.equalsIgnoreCase("true")) 
                fileName += "_rtl.html";
            else
                fileName += ".html";
        }
    }
 String imageDirection = "_ltr"; if (isBidi.equalsIgnoreCase("true")) imageDirection = "_rtl"; 
 String undoImg = "undo" + imageDirection + ".gif"; 
 String redoImg = "redo" + imageDirection + ".gif"; 
 String rb_1 = resourceBundle.getString("Undo"); 
 String rb_2 = resourceBundle.getString("Redo");
 String rb_3 = resourceBundle.getString("TextColor"); 
 String rb_4 = resourceBundle.getString("BackgroundColor"); 
 String rb_5 = resourceBundle.getString("InsertTable"); 
 String rb_6 = resourceBundle.getString("InsertLink"); 
 String rb_7 = resourceBundle.getString("InsertHorizontalRule"); 
 String rb_10 = resourceBundle.getString("CutSelectionToClipboard"); 
 String rb_11 = resourceBundle.getString("CopySelectionToClipboard"); 
 String rb_12 = resourceBundle.getString("PasteFromClipboardOverCurrentSelection"); 
 String rb_22 = resourceBundle.getString("Print"); 
 String rb_30 = resourceBundle.getString("rte.hyperlink.url"); 
 String rb_31 = resourceBundle.getString("NumberOfRows"); 
 String rb_32 = resourceBundle.getString("NumberOfColumns"); 
 String rb_50 = resourceBundle.getString("FlipWholeDocument"); 
 String rb_52 = resourceBundle.getString("TextDirectionLTR"); 
 String rb_53 = resourceBundle.getString("TextDirectionRTL"); 
 String rb_60 = resourceBundle.getString("FindAndReplace"); 
 String rb_61 = resourceBundle.getString("InsertRowAbove"); 
 String rb_62 = resourceBundle.getString("InsertRowBelow"); 
 String rb_63 = resourceBundle.getString("DeleteRow"); 
 String rb_64 = resourceBundle.getString("InsertColumnLeft"); 
 String rb_65 = resourceBundle.getString("InsertColumnRight"); 
 String rb_66 = resourceBundle.getString("DeleteColumn"); 
 String rb_67 = resourceBundle.getString("InsertPageBreak"); 
 String rb_70 = resourceBundle.getString("OperationCannotBeUndone"); 
 String rb_73 = resourceBundle.getString("ToXhtml"); 
 String error_rowInsert = resourceBundle.getString("rte.table.incorrectRowInsert"); 
 String error_columnInsert = resourceBundle.getString("rte.table.incorrectColumnInsert"); 
 String error_rowDelete = resourceBundle.getString("rte.table.incorrectRowDelete"); 
 String error_columnDelete = resourceBundle.getString("rte.table.incorrectColumnDelete"); 
 String fn_1 = "IBM_RTE_doTheCommand('" + editorName + "', 'undo')"; 
 String fn_2 = "IBM_RTE_doTheCommand('" + editorName + "', 'redo')"; 
 String fn_3 = "IBM_RTE_doColor('" + editorName + "', 'forecolor', this, '" + locale + "', '" + images + "', '" + directory + "', '" + isBidi + "')"; 
 String fn_4 = "IBM_RTE_doColor('" + editorName + "', 'backcolor', this, '" + locale + "', '" + images + "', '" + directory + "', '" + isBidi + "')"; %>
<script>
var tab_array = new Array();
tab_array[0] = "<%= rb_31 %>";
tab_array[1] = "<%= rb_32 %>";
</script>
<% String fn_5 = "IBM_RTE_doTableInsert('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "', tab_array, '" + isBidi + "')"; %>
<script>
var link_array = new Array();
link_array[0] = "<%= rb_30 %>";
</script>
<% String fn_6 = "IBM_RTE_doLink('" + editorName + "', link_array)";
 String fn_7 = "IBM_RTE_doTheCommand('" + editorName + "', 'inserthorizontalrule')"; 
 String fn_10 = "IBM_RTE_doTheCommand('" + editorName + "', 'Cut')"; 
 String fn_11 = "IBM_RTE_doTheCommand('" + editorName + "', 'Copy')"; 
 String fn_12 = "IBM_RTE_doTheCommand('" + editorName + "', 'Paste')"; 
 String fn_20 = "IBM_RTE_doPrint('" + editorName + "', '" + directory + fileName + "')"; 
 String fn_50 = "IBM_RTE_flipDocument('" + editorName + "')"; 
 String fn_52 = "IBM_RTE_flipSelection('" + editorName + "', 'LTR')"; 
 String fn_53 = "IBM_RTE_flipSelection('" + editorName + "', 'RTL')"; 
 String fn_findReplace = "IBM_RTE_findReplace('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "', '" + isBidi + "')"; 
 String localeInsertLeftPic = (locale.compareToIgnoreCase("ar") == 0 || locale.compareToIgnoreCase("iw") == 0)?"insertColumnRight.gif":"insertColumnLeft.gif"; 
 String localeInsertRightPic = (locale.compareToIgnoreCase("ar") == 0 || locale.compareToIgnoreCase("iw") == 0)?"insertColumnLeft.gif":"insertColumnRight.gif"; %>
<script>
var tab_errors1 = new Array();
tab_errors1[0] = "<%= error_rowInsert %>";
var tab_errors2 = new Array();
tab_errors2[0] = "<%= error_rowDelete %>";
var tab_errors3 = new Array();
tab_errors3[0] = "<%= error_columnInsert %>";
var tab_errors4 = new Array();
tab_errors4[0] = "<%= error_columnDelete %>";
var tab_errors5 = new Array();
tab_errors5[0] = "<%= rb_70 %>";
</script>
<% String fn_table1 = "IBM_RTE_insertRowAbove('" + editorName + "',tab_errors1)";
 String fn_table2 = "IBM_RTE_insertRowBelow('" + editorName + "')"; 
 String fn_table3 = "IBM_RTE_deleteRow('" + editorName + "', tab_errors5, tab_errors2)"; 
 String fn_table4 = "IBM_RTE_insertColumnLeft('" + editorName + "',tab_errors3)"; 
 String fn_table5 = "IBM_RTE_insertColumnRight('" + editorName + "',tab_errors3)"; 
 String fn_table6 = "IBM_RTE_deleteColumn('" + editorName + "', tab_errors5, tab_errors4)"; 
 String fn_insertPageBreak = "IBM_RTE_insertPageBreak('" + editorName + "')"; 
 String fn_accessible = "IBM_RTE_doAccessibleCompliant('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "', '"+isBidi+"')"; 
 String toolbarName = "ToolbarStandard"; %>
<docEditor:addToolbar editor="<%= editorName %>" name="<%= toolbarName %>" justify="right" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonPrint" text="<%= rb_22 %>" image="print.gif" script="<%= fn_20 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonFindReplace" text="<%= rb_60 %>" image="findReplace.gif" script="<%= fn_findReplace %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider1" />
<% String userAgent = request.getHeader("user-agent");
 if (userAgent.indexOf("Gecko") == -1 && userAgent.indexOf("Netscape") == -1) { %>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonCut"   text="<%= rb_10 %>" image="cut.gif" script="<%= fn_10 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonCopy"  text="<%= rb_11 %>" image="copy.gif" script="<%= fn_11 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonPaste" text="<%= rb_12 %>" image="paste.gif" script="<%= fn_12 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider2" />
<% } %>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonUndo" text="<%= rb_1 %>" image="<%= undoImg %>" script="<%= fn_1 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRedo" text="<%= rb_2 %>" image="<%= redoImg %>" script="<%= fn_2 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider3" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonFontColor" text="<%= rb_3 %>" image="textColor.gif" script="<%= fn_3 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonBackgroundColor" text="<%= rb_4 %>" image="backColor.gif" script="<%= fn_4 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider4" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonTable" text="<%= rb_5 %>" image="insertTable.gif" script="<%= fn_5 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowAbove" text="<%= rb_61 %>" image="insertRowAbove.gif" script="<%= fn_table1 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowBelow" text="<%= rb_62 %>" image="insertRowBelow.gif" script="<%= fn_table2 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRowDelete" text="<%= rb_63 %>" image="deleteRow.gif" script="<%= fn_table3 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColLeft" text="<%= rb_64 %>" image="<%= localeInsertLeftPic %>" script="<%= fn_table4 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColRight" text="<%= rb_65 %>" image="<%= localeInsertRightPic %>" script="<%= fn_table5 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonColDelete" text="<%= rb_66 %>" image="deleteColumn.gif" script="<%= fn_table6 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider5" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonLink" text="<%= rb_6 %>" image="link.gif" script="<%= fn_6 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider7" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonHorizontalRule" text="<%= rb_7 %>" image="insertRule.gif" script="<%= fn_7 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonInsertPageBreak"  text="<%= rb_67 %>" image="insertPageBreak.gif" script="<%= fn_insertPageBreak %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider6" />
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocWhole" text="<%= rb_50 %>" image="docwholebidi.gif" script="<%= fn_50 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocSelectionLTR" text="<%= rb_52 %>" image="docselectionLTR.gif" script="<%= fn_52 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocSelectionRTL" text="<%= rb_53 %>" image="docselectionRTL.gif" script="<%= fn_53 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAccessibility" text="<%= rb_73 %>" image="makeCodeCompliant.gif" script="<%= fn_accessible %>"/>
<% if (!withHelp) { %>
  <docEditor:addImage editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="withoutHelp" text="" image="clearPixel.gif"/>
<% } %>
