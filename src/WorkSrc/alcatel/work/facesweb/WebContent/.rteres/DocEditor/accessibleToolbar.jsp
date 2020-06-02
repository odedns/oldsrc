<% /* @copyright jsp */ %>
        
<%@ page contentType="text/html; charset=utf-8" %>

<%@ page buffer="none" autoFlush="true" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.tags.*" %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>

<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); %>
<% String editorName = aEditor.getName(); %>
<% String locale = (String)aEditor.getAttribute("locale"); %>
<% String directory = (String)aEditor.getAttribute("directory"); %>
<% String width = (String)aEditor.getAttribute("width"); %>
<% String height = (String)aEditor.getAttribute("height"); %>
<% ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); %>
<% StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); %>
<% String langToUse = LocaleHelper.getLocale(locale).getLanguage(); %>                             
<% String isBidi = (String)aEditor.getAttribute("isBidi"); %>
<% String editorDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) editorDir = "RTL"; %>

<% String userAgent = request.getHeader("user-agent"); %>
<% boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  %>
<% if (isMozilla) height = "70%"; %>

<% Vector toolbars = aEditor.getOrderedToolbars(); %>


<script language="javascript" src="<%= directory %>script_editor.js"></script>
<script language="javascript" src="<%= directory %>script_windowControls.js"></script>

<table width="<%= width %>" height="<%= height %>" border="0" cellpadding="0" cellspacing="0">

<tr><td height="100%" valign="top" align="middle">                                                               
                                                                 
<% if (toolbars != null) { %>

    <% for (int t = 0; t < toolbars.size(); t++) { %>
        <% Toolbar aToolbar = (Toolbar)toolbars.elementAt(t); %>
        <% String toolbarName = aToolbar.getName(); %>

        <table height="33%" <%= true ? "" : "style=display:none" %>" id="<%= editorName %><%= toolbarName %>" <%= style.getClass("table", "toolbar") %> cellpadding="0" cellspacing="0" border="0">
        
            <% Vector controls = aToolbar.getOrderedControls(); %>
            <% boolean justifyLastControlRight = aToolbar.getJustify() != null && aToolbar.getJustify().equalsIgnoreCase("right") && controls.size() > 0; %>
           
            <tr>

                <% aEditor.setAttribute("currentEditorName", editorName); %>
                <% aEditor.setAttribute("currentToolbarName", editorName + toolbarName); %>                         

                <td id="<%= editorName %><%= toolbarName %>__Knob">
                    <jsp:include page="control_knob.jsp" flush="true" />
                </td>

                          
                <% for (int c = 0; c < controls.size(); c++) { %>
                                       
                    <% Control aControl = (Control)controls.elementAt(c); %>
                    <% String definition = aControl.getDefinition(); %>
                    <% String controlName = aControl.getName(); %>
                    <% aEditor.setAttribute("currentControl", aControl); %>
                    <% aEditor.setAttribute("currentControlName", editorName + toolbarName + controlName); %>


                    <td id="<%= editorName %><%= toolbarName %><%= controlName %>">
                        <%-- NOTE: For some reason I can't use the jsp:include/jsp:param combination to pass variables, must be a WP bug? Must have something to do with the portlet objects? --%>
                        <jsp:include page="<%= definition %>" flush="true" />
                    </td>
                <% } %>
                    
                <% if (!justifyLastControlRight) { %>
                       <td id="<%= editorName %><%= toolbarName %>__Button" width="100%">
                            &nbsp;
                       </td>                        
                <% } %>

            </tr>                        
         </table>
<% }} %>

</td></tr>

</table>

