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
 String images = (String)aEditor.getAttribute("images"); 
 String directory = (String)aEditor.getAttribute("directory"); 
 boolean withSource = aEditor.getAttribute("addSource") != null; %>

<% String isBidi = (String)aEditor.getAttribute("isBidi"); %>
<% String imageDirection = "_ltr"; if (isBidi.equalsIgnoreCase("true")) imageDirection = "_rtl"; %>
<% String indentImg = "indent" + imageDirection + ".gif"; %>
<% String outdentImg = "outdent" + imageDirection + ".gif"; %>

<%                                
    String[] fonts = {"Arial", "Courier", "SansSerif", "Tahoma", "Verdana", "Wingdings"};
    String[] fontNames = {"Arial", "Courier", "Sans Serif", "Tahoma", "Verdana", "Wingdings"};

    ResourceBundle fontBundle = null;
    try { 
          fontBundle = ResourceBundle.getBundle("com.ibm.wps.odc.editors.Config", LocaleHelper.getLocale(locale)); 
    } 
    catch (MissingResourceException e) { fontBundle = null; }

    if (fontBundle != null) {

        Vector vFonts = new Vector();
        Vector vFontNames = new Vector();
        
        int i = 0;
        while (i != -1) {
            try {
                // If fontEntry is null, throws exception, sets i=-1, loop stops;
                String fontEntry = fontBundle.getString("fontmenu." + i);

                // If fontmenu.x= - , stop falling back to parent properties;
                if ("-".equals(fontEntry.trim())) break;

                int ind = fontEntry.indexOf(";");
                if (ind != -1) {
                    if (ind == 0) {
                        vFontNames.add(fontEntry.substring(ind + 1));
                        vFonts.add(fontEntry.substring(ind + 1));
                    } else {                 
                        vFontNames.add(fontEntry.substring(0, ind));
                        vFonts.add(fontEntry.substring(ind + 1));
                    }
                }
                else {
                    if (!("".equals(fontEntry))){
                        vFontNames.add(fontEntry);
                        vFonts.add(fontEntry);
                    }
                }
                i++;
            }
            catch (Exception e) {
                i = -1;
            }
        }

        try {
            fonts = (String[])vFonts.toArray(new String[vFonts.size()]);
            fontNames = (String[])vFontNames.toArray(new String[vFontNames.size()]);
        }
        catch (Exception e) {
        }
   }

%>
                                
<% String rb_0 = resourceBundle.getString("Format"); %>
<% String rb_1 = resourceBundle.getString("Format2"); %>
<% String rb_2 = resourceBundle.getString("Normal"); %>
<% String rb_3 = resourceBundle.getString("Heading1"); %>
<% String rb_4 = resourceBundle.getString("Heading2"); %>
<% String rb_5 = resourceBundle.getString("Heading3"); %>
<% String rb_6 = resourceBundle.getString("Heading4"); %>
<% String rb_7 = resourceBundle.getString("Heading5"); %>
<% String rb_8 = resourceBundle.getString("Heading6"); %>
<% String rb_9 = resourceBundle.getString("Font"); %>
<% String rb_10 = resourceBundle.getString("Font2"); %>
<% //String rb_11 = resourceBundle.getString("Arial"); %>
<% //String rb_12 = resourceBundle.getString("Courier"); %>
<% //String rb_13 = resourceBundle.getString("SansSerif"); %>
<% //String rb_14 = resourceBundle.getString("Tahoma"); %>
<% //String rb_15 = resourceBundle.getString("Verdana"); %>
<% //String rb_16 = resourceBundle.getString("Wingdings"); %>
<% String rb_17 = resourceBundle.getString("Size2"); %>
<% String rb_18 = "7pt"; //resourceBundle.getString("7pt"); %>
<% String rb_19 = "9pt"; //resourceBundle.getString("9pt"); %>
<% String rb_20 = "12pt"; //resourceBundle.getString("12pt"); %>
<% String rb_21 = "14pt"; //resourceBundle.getString("14pt"); %>
<% String rb_22 = "18pt"; //resourceBundle.getString("18pt"); %>
<% String rb_23 = "24pt"; //resourceBundle.getString("24pt"); %>
<% String rb_24 = resourceBundle.getString("Bold"); %>
<% String rb_25 = resourceBundle.getString("Italic"); %>
<% String rb_26 = resourceBundle.getString("Underline"); %>
<% String rb_27 = resourceBundle.getString("AlignLeft"); %>
<% String rb_28 = resourceBundle.getString("AlignCenter"); %>
<% String rb_29 = resourceBundle.getString("AlignRight"); %>
<% String rb_30 = resourceBundle.getString("OrderedList"); %>
<% String rb_31 = resourceBundle.getString("BulletedList"); %>
<% String rb_32 = resourceBundle.getString("Indent"); %>
<% String rb_33 = resourceBundle.getString("Outdent"); %>
<% String rb_34 = resourceBundle.getString("SourceMode"); %>

<% String rb_40 = resourceBundle.getString("Paragraph"); %>


<% String fn_0 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'formatblock')"; %>
<% String fn_1 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'fontname')"; %>
<% String fn_2 = "IBM_RTE_doFontStyle('" + editorName + "', this, 'fontsize')"; %>
<% String fn_3 = "IBM_RTE_doTheCommand('" + editorName + "', 'bold')"; %>
<% String fn_4 = "IBM_RTE_doTheCommand('" + editorName + "', 'italic')"; %>
<% String fn_5 = "IBM_RTE_doTheCommand('" + editorName + "', 'underline')"; %>
<% String fn_6 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifyleft')"; %>
<% String fn_7 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifycenter')"; %>
<% String fn_8 = "IBM_RTE_doTheCommand('" + editorName + "', 'justifyright')"; %>
<% String fn_9 = "IBM_RTE_doTheCommand('" + editorName + "', 'insertorderedlist')"; %>
<% String fn_10 = "IBM_RTE_doTheCommand('" + editorName + "', 'insertunorderedlist')"; %>
<% String fn_11 = "IBM_RTE_doTheCommand('" + editorName + "', 'indent')"; %>
<% String fn_12 = "IBM_RTE_doTheCommand('" + editorName + "', 'outdent')"; %>
<% String fn_13 = "IBM_RTE_" + editorName + "doToggleView()"; %>

<% String fn_paraSupport = "IBM_RTE_paraSupport('" + editorName + "', this, '" + locale + "', '" + images + "', '" + directory + "','" + isBidi + "')"; %>


<% String toolbarName = "ToolbarFormat"; %>

<docEditor:addToolbar editor="<%= editorName %>" name="<%= toolbarName %>" justify="right"/>

<docEditor:addImage editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ImageFontFormat" text="<%= rb_0 %>" image="format.gif"/>
<docEditor:addList editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ListFontFormat"  script="<%= fn_0 %>">

<% if (isMozilla) { %>
    <docEditor:addListItem item="" text="<%= rb_1 %>"/>
    <docEditor:addListItem item="P" text="<%= rb_2 %>"/>
    <docEditor:addListItem item="H1" text="<%= rb_3 %>"/>
    <docEditor:addListItem item="H2" text="<%= rb_4 %>"/>
    <docEditor:addListItem item="H3" text="<%= rb_5 %>"/>
    <docEditor:addListItem item="H4" text="<%= rb_6 %>"/>
    <docEditor:addListItem item="H5" text="<%= rb_7 %>"/>
    <docEditor:addListItem item="H6" text="<%= rb_8 %>"/>

<% } else { %>
    <docEditor:addListItem item="" text="<%= rb_1 %>"/>
    <docEditor:addListItem item="Normal" text="<%= rb_2 %>"/>
    <docEditor:addListItem item="<H1>" text="<%= rb_3 %>"/>
    <docEditor:addListItem item="<H2>" text="<%= rb_4 %>"/>
    <docEditor:addListItem item="<H3>" text="<%= rb_5 %>"/>
    <docEditor:addListItem item="<H4>" text="<%= rb_6 %>"/>
    <docEditor:addListItem item="<H5>" text="<%= rb_7 %>"/>
    <docEditor:addListItem item="<H6>" text="<%= rb_8 %>"/>
<% } %>

</docEditor:addList>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider1" />

<docEditor:addImage editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ImageFontStyle" text="<%= rb_9 %>" image="font.gif"/>
<docEditor:addList editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ListFontStyle" script="<%= fn_1 %>">
    <docEditor:addListItem item="" text="<%= rb_10 %>"/>
    <% for (int i = 0; i < fonts.length; i++) { %>
        <% String font = fonts[i]; %>
        <% String fontname = fontNames[i]; %>
            <docEditor:addListItem item="<%= font %>" text="<%= fontname %>"/>
    <% } %>    
</docEditor:addList>
<docEditor:addList editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ListFontSize" script="<%= fn_2 %>">
    <docEditor:addListItem item="" text="<%= rb_17 %>"/>
    <docEditor:addListItem item="1" text="<%= rb_18 %>"/>
    <docEditor:addListItem item="2" text="<%= rb_19 %>"/>
    <docEditor:addListItem item="3" text="<%= rb_20 %>"/>
    <docEditor:addListItem item="4" text="<%= rb_21 %>"/>
    <docEditor:addListItem item="5" text="<%= rb_22 %>"/>
    <docEditor:addListItem item="6" text="<%= rb_23 %>"/>
</docEditor:addList>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider2" />

<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonBold" text="<%= rb_24 %>" image="bold.gif" script="<%= fn_3 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonItalic" text="<%= rb_25 %>" image="italic.gif" script="<%= fn_4 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonUnderline" text="<%= rb_26 %>" image="underline.gif" script="<%= fn_5 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider3" />

<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonParagraph" text="<%= rb_40 %>" image="paragraphSupport.gif" script="<%= fn_paraSupport %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignLeft" text="<%= rb_27 %>" image="alignLeft.gif" script="<%= fn_6 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignMiddle" text="<%= rb_28 %>" image="alignCenter.gif" script="<%= fn_7 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonAlignRight" text="<%= rb_29 %>" image="alignRight.gif" script="<%= fn_8 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider4" />

<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonNumbers" text="<%= rb_30 %>" image="number.gif" script="<%= fn_9 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonBullets" text="<%= rb_31 %>" image="bullets.gif" script="<%= fn_10 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider5" />

<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonIndent" text="<%= rb_32 %>" image="<%= indentImg %>" script="<%= fn_11 %>"/>
<docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonOutdent" text="<%= rb_33 %>" image="<%= outdentImg %>" script="<%= fn_12 %>"/>
<docEditor:addDivider editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="Divider6" />

<% if (!withSource) { %>
  <docEditor:addImage editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="withoutSource" text="" image="clearPixel.gif"/>
<% } else { %>
  <docEditor:addButton editor="<%= editorName %>" toolbar="<%= toolbarName %>" name="ButtonSourceCode" text="<%= rb_34 %>" image="sourceCode.gif" script="<%= fn_13 %>"/>
<% } %>

