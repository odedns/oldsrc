         
<%@ page contentType="text/html; charset=utf-8" %>

<%@ page buffer="none" autoFlush="true" %>
<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>

<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
                            
<% Editor aEditor = (Editor)request.getAttribute("com_ibm_pvc_wps_docEditor_" + com_ibm_pvc_wps_docEditor); 
 String editorName = aEditor.getName(); 
 String locale = (String)aEditor.getAttribute("locale"); 
 ResourceBundle resourceBundle = (ResourceBundle)aEditor.getAttribute("resourceBundle"); 
 String jspPath = (String)aEditor.getAttribute("jspPath"); if (jspPath == null) jspPath = ""; 
 String jspCtx = (String)aEditor.getAttribute("jspCtx"); if (jspCtx == null) jspCtx = "";
 String jspDir = (String)aEditor.getAttribute("jspDir"); if (jspDir == null) jspDir = ""; 
 String userAgent = request.getHeader("user-agent"); 
 boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  
 String backColor = "backcolor"; 
if(isMozilla) backColor = "hilitecolor"; %>
<docEditor:addWindowControl 
           name="accessibleToolbar" 
           definition='<%= jspPath + "toolbar_accessible.jsp" %>'
           editor="<%= editorName %>" 
           keyName="toolbarKey" 
           width="400" 
           height="20" 
           left="80"
           top="80"
           addToolbarNames="Yes"
           >

<docEditor:addWindowControlParam param="jspCtx" value="<%= jspCtx %>"/>
<docEditor:addWindowControlParam param="jspDir" value="<%= jspDir %>"/>
</docEditor:addWindowControl>


<docEditor:addWindowControl 
           name="tableInsert" 
           definition='<%= jspPath + "table_ie.jsp" %>'
           editor="<%= editorName %>" 
           keyName="tableInsertKey" 
           width="200" 
           height="170" 
           />
<docEditor:addWindowControl 
           name="colorPickerFore" 
           definition='<%= jspPath + "colorPicker_accessible.jsp" %>'
           editor="<%= editorName %>" 
           keyName="colorPickerKey1" 
           width="320" 
           height="450" 
           >
<docEditor:addWindowControlParam param="clrType" value="forecolor"/>
</docEditor:addWindowControl>
<docEditor:addWindowControl 
           name="colorPickerBack" 
           definition='<%= jspPath + "colorPicker_accessible.jsp" %>'
           editor="<%= editorName %>" 
           keyName="colorPickerKey2" 
           width="320" 
           height="450" 
           >
<docEditor:addWindowControlParam param="clrType" value="<%= backColor %>"/>
</docEditor:addWindowControl>
<%-- The following two tag lib calls give examples of using user-defined key sequence file and 
setting in line key sequence; also, a way to pass left/top attributes using JS variables;
<docEditor:addWindowControl 
           name="tableInsert" 
           definition="table_ie.jsp" 
           editor="<%= editorName %>" 
           keyName="tableInsertKey" 
           keyFile="KeyFile1.properties" 
           width="200" 
           height="170" 
           left='"+ tableIstLeft +"'
           top='"+ tableIstTop +"'
           />
<docEditor:addWindowControl 
           name="colorPickerBack" 
           definition="colorPicker_accessible.jsp" 
           editor="<%= editorName %>" 
           keyName="colorPickerKey2" 
           keySequence="17:10" 
           width="320" 
           height="450" 
           left='"+ colorPickerLeft +"'
           top='"+ colorPickerTop +"'
           >
<docEditor:addWindowControlParam param="clrType" value="backcolor"/>
</docEditor:addWindowControl>
--%>
