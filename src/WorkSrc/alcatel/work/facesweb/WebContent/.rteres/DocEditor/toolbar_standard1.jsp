         
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
 String imageDirection = "_ltr"; if (isBidi.equalsIgnoreCase("true")) imageDirection = "_rtl"; 
// defect 140358 fix 
    String fileName;
    if (withHelp) {
        fileName = "print_utf8";
        if (isBidi.equalsIgnoreCase("true")) 
            fileName += "_rtl.html";
        else
            fileName += ".html";
    }
    else {    
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
 String undoImg = "undo" + imageDirection + ".gif"; 
 String redoImg = "redo" + imageDirection + ".gif"; 
 String rb_27 = resourceBundle.getString("CloseToolbar"); 
 String rb_22 = resourceBundle.getString("Print"); 
 String rb_10 = resourceBundle.getString("CutSelectionToClipboard"); 
 String rb_11 = resourceBundle.getString("CopySelectionToClipboard"); 
 String rb_12 = resourceBundle.getString("PasteFromClipboardOverCurrentSelection"); 
 String rb_1 = resourceBundle.getString("Undo"); 
 String rb_2 = resourceBundle.getString("Redo"); 
 String rb_6 = resourceBundle.getString("InsertLink"); 
 String rb_7 = resourceBundle.getString("InsertHorizontalRule"); 
 String rb_24 = resourceBundle.getString("Bold"); 
 String rb_25 = resourceBundle.getString("Italic"); 
 String rb_26 = resourceBundle.getString("Underline");
 String rb_30 = resourceBundle.getString("EnterURL"); 
 String rb_60 = resourceBundle.getString("FindAndReplace"); 
String fn_20 = "IBM_RTE_doPrint('" + editorName + "', '" + directory + fileName + "')"; 
 String fn_10 = "IBM_RTE_doTheCommand('" + editorName + "', 'Cut')"; 
 String fn_11 = "IBM_RTE_doTheCommand('" + editorName + "', 'Copy')"; 
 String fn_12 = "IBM_RTE_doTheCommand('" + editorName + "', 'Paste')"; 
 String fn_1 = "IBM_RTE_doTheCommand('" + editorName + "', 'undo')"; 
 String fn_2 = "IBM_RTE_doTheCommand('" + editorName + "', 'redo')"; %>
<script>
var link_array = new Array();
link_array[0] = "<%= rb_30 %>";
</script>
<% String fn_6 = "IBM_RTE_doLink('" + editorName + "', link_array)";
 String fn_7 = "IBM_RTE_doTheCommand('" + editorName + "', 'inserthorizontalrule')"; 
 String fn_24 = "IBM_RTE_doTheCommand('" + editorName + "', 'bold')"; 
 String fn_25 = "IBM_RTE_doTheCommand('" + editorName + "', 'italic')"; 
 String fn_26 = "IBM_RTE_doTheCommand('" + editorName + "', 'underline')"; 
 String fn_findReplace = "IBM_RTE_findReplace('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "')"; 
 String toolbarName = "ToolbarStandard1"; %>
<docEditor:addToolbar editor="<%= editorName %>" name="<%= toolbarName %>" justify="left" />
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonCloseToolbar" text="<%= rb_27 %>" image="closeToolbarButton.gif" script="IBM_RTE_doAccessToolbarUnload(1);"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider1" />
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonPrint" text="<%= rb_22 %>" image="print.gif" script="<%= fn_20 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonFindReplace" text="<%= rb_60 %>" image="findReplace.gif" script="<%= fn_findReplace %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider2" />
<% String userAgent = request.getHeader("user-agent");
 if (userAgent.indexOf("Gecko") == -1) { %>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonCut"   text="<%= rb_10 %>" image="cut.gif" script="<%= fn_10 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonCopy"  text="<%= rb_11 %>" image="copy.gif" script="<%= fn_11 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonPaste" text="<%= rb_12 %>" image="paste.gif" script="<%= fn_12 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider3" />
<% } %>

<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonUndo" text="<%= rb_1 %>" image="<%= undoImg %>" script="<%= fn_1 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonRedo" text="<%= rb_2 %>" image="<%= redoImg %>" script="<%= fn_2 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider4" />

<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonLink" text="<%= rb_6 %>" image="link.gif" script="<%= fn_6 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonHorizontalRule" text="<%= rb_7 %>" image="insertRule.gif" script="<%= fn_7 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider5" />

<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonBold" text="<%= rb_24 %>" image="bold.gif" script="<%= fn_24 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonItalic" text="<%= rb_25 %>" image="italic.gif" script="<%= fn_25 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonUnderline" text="<%= rb_26 %>" image="underline.gif" script="<%= fn_26 %>"/>
