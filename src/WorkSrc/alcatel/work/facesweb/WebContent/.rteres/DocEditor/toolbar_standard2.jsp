         
<%@ page contentType="text/html; charset=utf-8" %>

<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% String userAgent = request.getHeader("user-agent"); 
 boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  
Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
 String editorName = aEditor.getName(); 
 String locale = (String)aEditor.getAttribute("locale"); 
 ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); 
 boolean withSource = aEditor.getAttribute("addSource") != null; 
 String isBidi = (String)aEditor.getAttribute("isBidi"); 
 String imageDirection = "_ltr"; if (isBidi.equalsIgnoreCase("true")) imageDirection = "_rtl"; 
 String indentImg = "indent" + imageDirection + ".gif"; 
 String outdentImg = "outdent" + imageDirection + ".gif"; 
 String images = (String)aEditor.getAttribute("images"); 
 String directory = (String)aEditor.getAttribute("directory"); 
 String rb_27 = resourceBundle.getString("AlignLeft"); 
 String rb_28 = resourceBundle.getString("AlignCenter");
 String rb_29 = resourceBundle.getString("AlignRight"); 
 String rb_30 = resourceBundle.getString("OrderedList"); 
 String rb_31 = resourceBundle.getString("BulletedList"); 
 String rb_32 = resourceBundle.getString("Indent"); 
 String rb_33 = resourceBundle.getString("Outdent"); 
 String rb_34 = resourceBundle.getString("SourceMode");
 String rb_40 = resourceBundle.getString("Paragraph"); 
 String rb_50 = resourceBundle.getString("FlipWholeDocument"); 
 String rb_52 = resourceBundle.getString("TextDirectionLTR"); 
 String rb_53 = resourceBundle.getString("TextDirectionRTL"); 
 String fn_0 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'formatblock')"; 
 String fn_1 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'fontname')";
 String fn_2 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'fontsize')"; 
 String fn_6 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifyleft')"; 
 String fn_7 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifycenter')";
 String fn_8 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifyright')"; 
 String fn_9 = "IBM_RTE_doTheCommand('" + editorName + "', 'insertorderedlist')"; 
 String fn_10 = "IBM_RTE_doTheCommand('" + editorName + "', 'insertunorderedlist')"; 
 String fn_11 = "IBM_RTE_doTheCommand('" + editorName + "', 'indent')"; 
 String fn_12 = "IBM_RTE_doTheCommand('" + editorName + "', 'outdent')"; 
 String fn_13 = "IBM_RTE_accessDoToggleView()"; 
 String fn_50 = "IBM_RTE_flipDocument('" + editorName + "')"; 
 String fn_52 = "IBM_RTE_flipSelection('" + editorName + "', 'LTR')"; 
 String fn_53 = "IBM_RTE_flipSelection('" + editorName + "', 'RTL')"; 
 String fn_paraSupport = "IBM_RTE_paraSupport('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "','"+isBidi+"')"; 
 String toolbarName = "ToolbarStandard2"; %>
<docEditor:addToolbar editor="<%= editorName %>" name="<%= toolbarName %>" justify="left"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonParagraph" text="<%= rb_40 %>" image="paragraphSupport.gif" script="<%= fn_paraSupport %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignLeft" text="<%= rb_27 %>" image="alignLeft.gif" script="<%= fn_6 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignMiddle" text="<%= rb_28 %>" image="alignCenter.gif" script="<%= fn_7 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignRight" text="<%= rb_29 %>" image="alignRight.gif" script="<%= fn_8 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider1" />
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonNumbers" text="<%= rb_30 %>" image="number.gif" script="<%= fn_9 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonBullets" text="<%= rb_31 %>" image="bullets.gif" script="<%= fn_10 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider2" />
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonIndent" text="<%= rb_32 %>" image="<%= indentImg %>" script="<%= fn_11 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonOutdent" text="<%= rb_33 %>" image="<%= outdentImg %>" script="<%= fn_12 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider3" />
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocWhole" text="<%= rb_50 %>" image="docwholebidi.gif" script="<%= fn_50 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocSelectionLTR" text="<%= rb_52 %>" image="docselectionLTR.gif" script="<%= fn_52 %>"/>
<docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonDocSelectionRTL" text="<%= rb_53 %>" image="docselectionRTL.gif" script="<%= fn_53 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider4" />
<% if (withSource) { %>
  <docEditor:addButton definition="control_button2.jsp" editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonSourceCode" text="<%= rb_34 %>" image="sourceCode.gif" script="<%= fn_13 %>"/>
<% } %>
