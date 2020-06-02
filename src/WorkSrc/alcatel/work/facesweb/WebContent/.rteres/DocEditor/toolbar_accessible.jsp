<% /* @copyright jsp */ %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<% String loc = request.getParameter("locale"); 
 String toolbarNameList = request.getParameter("toolbarNameList"); 
 String toolbarCtrlNameList = request.getParameter("toolbarCtrlNameList"); 
 String editorName = request.getParameter("editorName"); 
 ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); 
String addJsp = request.getParameter("addJsp"); if (addJsp == null) addJsp = ""; 
String jspCtx = request.getParameter("jspCtx"); if (jspCtx == null) jspCtx = ""; 
String jspDir = request.getParameter("jspDir"); if (jspDir == null) jspDir = ""; 
String images = request.getParameter("images"); if (images == null) images = "";
    Vector origToolbarNames = new Vector();
    if (toolbarNameList != null && !toolbarNameList.equals("")) {
        for (StringTokenizer st = new StringTokenizer(toolbarNameList, ";"); st.hasMoreTokens(); origToolbarNames.add(st.nextToken().trim()));
    }
    Vector origToolbarCtrlNames = new Vector();
    if (toolbarCtrlNameList != null && !toolbarCtrlNameList.equals("")) {
            for (StringTokenizer st = new StringTokenizer(toolbarCtrlNameList, ";"); st.hasMoreTokens(); origToolbarCtrlNames.add(st.nextToken().trim()));
    }
%>
<html>
<head>
<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%= resourceBundle.getString("rte.window.title") %></title>
<% String bgcolor = "#C0C0C0"; %>
<script language="javascript">
	var docLocale = "<%= loc %>";
	var isMozilla ;//= IBM_RTE_isMozilla();
	function IBM_RTE_doAccessToolbarUnload(num){
		IBM_RTE_showDefaultToolbar();
		if(num == 1)
			parent.close();
	}
    function IBM_RTE_hideDefaultToolbar() {
        <% for (int t = 0; t < origToolbarNames.size(); t++) { 
                String aToolbarName = (String)origToolbarNames.elementAt(t); %>                
                this.opener.document.getElementById("<%= aToolbarName %>").style.display = 'none';
        <% } %>   
    }
    function IBM_RTE_showDefaultToolbar() {
        <% for (int j = 0; j < origToolbarNames.size(); j++) {
         String aToolbarName = (String)origToolbarNames.elementAt(j); %>                
         this.opener.document.getElementById("<%= aToolbarName %>").style.display = '';
        <% } %>   
		var elem = this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg");
        if (elem != null && elem.title != "<%= resourceBundle.getString("SourceMode") %>") {
            if (isMozilla) {
                var html = IBM_RTE_getDocument("<%= editorName %>").body.ownerDocument.createRange();
                html.selectNodeContents(IBM_RTE_getDocument("<%= editorName %>").body);
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = html.toString();
            }
            else {
                iText = IBM_RTE_getDocument("<%= editorName %>").body.innerText;
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = iText;
            }
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>";
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>";
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>"
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>"
        }
            IBM_RTE_getWindow("<%= editorName %>").focus();            
    }
    function IBM_RTE_setToggleViewTooltip() {
        if (this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title == "<%= resourceBundle.getString("SourceMode") %>") {
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>";
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>";
        }
        else {
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").title = "<%= resourceBundle.getString("DesignMode") %>";
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").alt = "<%= resourceBundle.getString("DesignMode") %>";
        }
    }

    function IBM_RTE_accessibleDoToggleView() {
		// ie the HTML mode = Source mode
        if (this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title == "<%= resourceBundle.getString("SourceMode") %>") {
            if (IBM_RTE_isMozilla()) {
                var html = this.opener.document.createTextNode(IBM_RTE_getDocument("<%= editorName %>").body.innerHTML);
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = "";
                IBM_RTE_getDocument("<%= editorName %>").body.appendChild(html);
            }
            else {
                iHTML = IBM_RTE_getDocument("<%= editorName %>").body.innerHTML;
                IBM_RTE_getDocument("<%= editorName %>").body.innerText = iHTML;
            }
            
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title = "<%= resourceBundle.getString("DesignMode") %>";
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").alt = "<%= resourceBundle.getString("DesignMode") %>";
            
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").title = "<%= resourceBundle.getString("DesignMode") %>"
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").alt = "<%= resourceBundle.getString("DesignMode") %>"
            IBM_RTE_getWindow("<%= editorName %>").focus();            
        }
        else {
           
            if (IBM_RTE_isMozilla()) {
                var html = IBM_RTE_getDocument("<%= editorName %>").body.ownerDocument.createRange();
                html.selectNodeContents(IBM_RTE_getDocument("<%= editorName %>").body);
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = html.toString();
            }
            else {
                iText = IBM_RTE_getDocument("<%= editorName %>").body.innerText;
                IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = iText;
            }
            
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>";
            this.opener.document.getElementById("<%= editorName %>ToolbarFormatButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>";
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").title = "<%= resourceBundle.getString("SourceMode") %>"
            document.getElementById("<%= editorName %>ToolbarStandard2ButtonSourceCodeImg").alt = "<%= resourceBundle.getString("SourceMode") %>"
            IBM_RTE_getWindow("<%= editorName %>").focus();            
        }
    }
</script>

<style type="text/css">
    .toolbar { background-color: #C0C0C0; border-top: 1px solid #ffffff; border-right: 1px solid #999999; border-bottom: 1px solid #999999; border-left: 1px solid #ffffff; }    
    .toolbarButton { background-color: #C0C0C0; padding: 3px; border-width: 1px; border-style: solid; border-color: #C0C0C0; }
    .toolbarButtonRollover { background-color: #C0C0C0; padding: 3px; border-bottom: 1px solid #999999; border-left: 1px solid #ffffff; border-top: 1px solid #ffffff; border-right: 1px solid #999999; }
    .toolbarButtonPressed { background-color: #aaaaaa; padding: 3px; border-top: 1px solid #999999; border-right: 1px solid #ffffff; border-bottom: 1px solid #ffffff; border-left: 1px solid #999999; }
    .toolbarButtonSelected { background-color: #cccccc; padding: 3px; border-top: 1px solid #999999; border-right: 1px solid #ffffff; border-bottom: 1px solid #ffffff; border-left: 1px solid #999999; }
    .toolbarButtonSelectedRollover { background-color: #cccccc; padding: 3px; border-width: 1px; border-style: solid; border-color: #999999; }
    .toolbarControl { padding: 3px; }
    .toolbarSeparator { background-color: #999999; width: 1px; margin: 0px; padding: 0px; border-top: 0px none #999999; border-right: 1px solid #ffffff; border-bottom: 0px none #ffffff; border-left: 0px none #999999; }
    .textarea { background-color: #C0C0C0; border-top: 1px solid #ffffff; border-right: 1px solid #999999; border-bottom: 1px solid #999999; border-left: 1px solid #ffffff; }    
    .textareaText { font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; } 
</style>          

</head>

<body onload="isAccessible=true; IBM_RTE_hideDefaultToolbar(); isMozilla=IBM_RTE_isMozilla();" onunload="IBM_RTE_doAccessToolbarUnload(2); isAccessible=false; " leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0">

<docEditor:createEditor 
     name="<%= editorName %>" 
     locale="<%= loc %>"    
     jspContext="<%= jspCtx %>"
     jsp='<%= jspDir + "toolbar_panel.jsp" %>' 
     controls="toolbar_standard1.jsp, toolbar_standard2.jsp, toolbar_format1.jsp"
    >
<docEditor:addEditorParam param="isAccessibleToolbar" value="yes"/>
<docEditor:addEditorParam param="images" value="<%= images %>"/>
</docEditor:createEditor>
<% if (!addJsp.equals("")) { %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
 Vector theToolbars = aEditor.getOrderedToolbars(); 
 String toolbarNameListNew = ""; 
 
   for (int t = 0; t < theToolbars.size(); t++) { 
      Toolbar aToolbar = (Toolbar)theToolbars.elementAt(t);               
      String toolbarName = aToolbar.getName();           
      toolbarNameListNew += toolbarName +"; ";
   } 

   int lind1 = toolbarNameListNew.lastIndexOf(";");
   if (lind1 != -1)
      toolbarNameListNew = toolbarNameListNew.substring(0, lind1);
%>

<jsp:include page="<%= addJsp %>" flush="true">
    <jsp:param name="toolbarNameList" value="<%= toolbarNameListNew %>"/>
</jsp:include> 
   
<% } %>
<docEditor:getEditor name="<%= editorName %>" />
</body>
</html>
