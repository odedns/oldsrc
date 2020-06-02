<% /* @copyright jsp */ %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.tags.*" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
 String editorName = aEditor.getName(); 
 String locale = (String)aEditor.getAttribute("locale"); 
 String images = (String)aEditor.getAttribute("images"); 
 String doc = (String)aEditor.getAttribute("doc"); 
 String jspContext = (String)aEditor.getAttribute("jspContext"); 
 String styleMap = (String)aEditor.getAttribute("styleMap"); 
 String directory = (String)aEditor.getAttribute("directory"); 
 String relativeDir = (String)aEditor.getAttribute("relativeDir"); 
 String mode = (String)aEditor.getAttribute("mode"); 
 String width = (String)aEditor.getAttribute("width"); 
 String height = (String)aEditor.getAttribute("height"); 
 String tabindex = (String)aEditor.getAttribute("tabindex"); 

 String listener = (String)aEditor.getAttribute("listener"); if (listener == null) listener = ""; 
 ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); 
 StyleHelper style = (StyleHelper)aEditor.getAttribute("style"); 
 boolean editMode = mode.equalsIgnoreCase("edit"); 
 String langToUse = LocaleHelper.getLocale(locale).getLanguage();
 String isBidi = (String)aEditor.getAttribute("isBidi"); 
 String editorDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) editorDir = "RTL"; 
 boolean isAccessibleToolbar = aEditor.getAttribute("isAccessibleToolbar") != null; 
 String userAgent = request.getHeader("user-agent"); 
 boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  
 boolean isNetscape = userAgent.indexOf("Netscape") == -1 ? false : true;  
 String toolbarHeight = ""; 
 if (isAccessibleToolbar) { 
	if (isMozilla) height = "100%"; 
    if(isNetscape) toolbarHeight = "29%"; else toolbarHeight = "33%";
 } %>
<script language="javascript" src="<%= directory %>script_editor.js"></script>
<script language="javascript" src="<%= directory %>script_windowControls.js"></script>
<table dir="<%= editorDir %>" lang="<%= langToUse %>" width="<%= width %>" height="<%= height %>" border="0" cellpadding="0" cellspacing="0">
<% if (editMode) { 
if (isAccessibleToolbar) { %>
<tr><td height="100%" valign="top" align="middle">  <% } else { %>
<tr><td height="1">                                                                
<% } 
 Vector toolbars = aEditor.getOrderedToolbars(); 
 if (toolbars != null) {
     for (int t = 0; t < toolbars.size(); t++) { 
         Toolbar aToolbar = (Toolbar)toolbars.elementAt(t); 
         String toolbarName = aToolbar.getName(); %>
        <table height="<%= toolbarHeight %>" <%= true ? "" : "style=display:none" %>" id="<%= editorName %><%= toolbarName %>" <%= style.getClass("table", "toolbar") %> cellpadding="0" cellspacing="0" border="0">
            <% Vector controls = aToolbar.getOrderedControls(); 
             boolean justifyLastControlRight = aToolbar.getJustify() != null && aToolbar.getJustify().equalsIgnoreCase("right") && controls.size() > 0; %>
            <tr>
                <% aEditor.setAttribute("currentEditorName", editorName); 
                 aEditor.setAttribute("currentToolbarName", editorName + toolbarName); %>
                <td id="<%= editorName %><%= toolbarName %>__Knob">
                    <jsp:include page="control_knob.jsp" flush="true" />
                </td>
                <% for (int c = 0; c < controls.size(); c++) { 
                     Control aControl = (Control)controls.elementAt(c); 
                     String definition = aControl.getDefinition(); 
                     String controlName = aControl.getName(); 
                     aEditor.setAttribute("currentControl", aControl); 
                     aEditor.setAttribute("currentControlName", editorName + toolbarName + controlName); 
                     if (justifyLastControlRight && c == controls.size() - 1) { %>
                        <td id="<%= editorName %><%= toolbarName %>__Button" width="100%">
                            &nbsp;
                        </td>                        
                    <% } %>
                    <td id="<%= editorName %><%= toolbarName %><%= controlName %>">
                        <%-- NOTE: For some reason I can't use the jsp:include/jsp:param combination to pass variables, must be a WP bug? Must have something to do with the portlet objects? --%>
                        <jsp:include page="<%= definition %>" flush="true" />
                    </td>
                <% } 
                     if (!justifyLastControlRight) { %>
                        <td id="<%= editorName %><%= toolbarName %>__Button" width="100%">
                            &nbsp;
                        </td>                        
                    <% } %>
            </tr>                        
         </table>
<% }} %>
</td></tr>
<% } 
 if (!isAccessibleToolbar) { %>
<tr><td valign="center" align="middle">
<table cellpadding="4" cellspacing="0" border="0" width="100%" height="100%">
<tr><td <%= style.getClass("img", "textarea") %> >
<% if(tabindex.trim() == "" || tabindex == null ){%>
    <iframe width="<%= width %>" height="<%= height %>" <%= doc != null ? "onload=\"" + listener + ";\"" : "onload=\"IBM_RTE_loadEditorWithEmptyDocument('" + editorName + "', '" + editorDir + "'); " + listener + ";\"" %> id="<%= editorName %>" <%= style.getClass("iframe", "textareaText") %> style="color:#000000; background-color:#FFFFFF;" width="<%= width %>" height="<%= height %>" <%= doc == null ? "" : "src=\"" + doc + "\"" %> onblur="IBM_RTE_checkEvent('<%= editorName %>')" onfocus="IBM_RTE_checkEvent('<%= editorName %>')" ></iframe>
	<%}else{%>
    <iframe tabIndex="<%=tabindex%>" width="<%= width %>" height="<%= height %>" <%= doc != null ? "onload=\"" + listener + ";\"" : "onload=\"IBM_RTE_loadEditorWithEmptyDocument('" + editorName + "', '" + editorDir + "'); " + listener + ";\"" %> id="<%= editorName %>" <%= style.getClass("iframe", "textareaText") %> style="color:#000000; background-color:#FFFFFF;" width="<%= width %>" height="<%= height %>" <%= doc == null ? "" : "src=\"" + doc + "\"" %> onblur="IBM_RTE_checkEvent('<%= editorName %>')" onfocus="IBM_RTE_checkEvent('<%= editorName %>')" ></iframe>
	<%}%>
    <input type="hidden" id="<%= editorName %>_color">
</td></tr>
</table>
</td></tr>
<% } %>
</table>
