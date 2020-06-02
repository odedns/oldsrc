<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<% String loc = request.getParameter("locale"); %>
<% String img = request.getParameter("images"); %>
<% String editorName = request.getParameter("editorName"); %>
<% String clrType = request.getParameter("clrType"); %>
<% ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%= resourceBundle.getString("SelectAColor") %></title>
</head>
                    
<style>
table
{
  color: #000000;
  font-family: sans-serif;
  font-size: 9pt;
  font-weight: bold;
  border-color: #000000;
}
</style>
                          
<script language="javascript">

var oldElem;

function IBM_RTE_selectColor(clrValue) {


    parent.close();

    var clrType = "<%= clrType %>";
//    var clrType = this.opener.document.getElementById("<%= editorName %>_color").value;  

    this.opener.IBM_RTE_getDocument("<%= editorName %>").execCommand(clrType, false, clrValue);
    if (oldElem) {
        oldElem.style.borderWidth = 1;
    }
//    event.srcElement.style.borderWidth = 2;
//    oldElem = event.srcElement;


this.opener.IBM_RTE_getWindow("<%= editorName %>").focus();            

}
</script>               

<body style="margin-top: 0; margin-bottom: 0;" onblur="window.focus()">

    <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
        <tr>
            <td width="100%" valign="middle" align="center">

                <table border="1" bordercolor="#000000" cellpadding="0" cellspacing="0">
                    <tr>

<td bgcolor="#FFFFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFFFF')"><img width="1" height="1"></td>
<td bgcolor="#FFFFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFFCE')"><img width="1" height="1"></td>
<td bgcolor="#FFFF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFF9C')"><img width="1" height="1"></td>
<td bgcolor="#FFFF63" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFF63')"><img width="1" height="1"></td>
<td bgcolor="#FFFF31" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFF31')"><img width="1" height="1"></td>
<td bgcolor="#FFFF00" width="11" height="11" onclick="IBM_RTE_selectColor('#FFFF00')"><img width="1" height="1"></td>
<td bgcolor="#CEFFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#CEFFFF')"><img width="1" height="1"></td>
<td bgcolor="#CEFFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#CEFFCE')"><img width="1" height="1"></td>
<td bgcolor="#CEFF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#CEFF9C')"><img width="1" height="1"></td>
<td bgcolor="#CEFF63" width="11" height="11" onclick="IBM_RTE_selectColor('#CEFF63')"><img width="1" height="1"></td>
<td bgcolor="#CEFF31" width="11" height="11" onclick="IBM_RTE_selectColor('#CEFF31')"><img width="1" height="1"></td>
<td bgcolor="#CDFF00" width="11" height="11" onclick="IBM_RTE_selectColor('#CDFF00')"><img width="1" height="1"></td>
<td bgcolor="#9CFFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFFFF')"><img width="1" height="1"></td>
<td bgcolor="#9CFFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFFCE')"><img width="1" height="1"></td>
<td bgcolor="#9CFF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFF9C')"><img width="1" height="1"></td>
<td bgcolor="#9CFF63" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFF63')"><img width="1" height="1"></td>
<td bgcolor="#9CFF31" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFF31')"><img width="1" height="1"></td>
<td bgcolor="#9CFF00" width="11" height="11" onclick="IBM_RTE_selectColor('#9CFF00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#FFCFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCFFF')"><img width="1" height="1"></td>
<td bgcolor="#FFCFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCFCE')"><img width="1" height="1"></td>
<td bgcolor="#FFCF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCF9C')"><img width="1" height="1"></td>
<td bgcolor="#FFCF63" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCF63')"><img width="1" height="1"></td>
<td bgcolor="#FFCF31" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCF31')"><img width="1" height="1"></td>
<td bgcolor="#FFCF00" width="11" height="11" onclick="IBM_RTE_selectColor('#FFCF00')"><img width="1" height="1"></td>
<td bgcolor="#CECFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#CECFFF')"><img width="1" height="1"></td>
<td bgcolor="#CECFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#CECFCE')"><img width="1" height="1"></td>
<td bgcolor="#CECF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#CECF9C')"><img width="1" height="1"></td>
<td bgcolor="#CECF63" width="11" height="11" onclick="IBM_RTE_selectColor('#CECF63')"><img width="1" height="1"></td>
<td bgcolor="#CECF31" width="11" height="11" onclick="IBM_RTE_selectColor('#CECF31')"><img width="1" height="1"></td>
<td bgcolor="#CDCF00" width="11" height="11" onclick="IBM_RTE_selectColor('#CDCF00')"><img width="1" height="1"></td>
<td bgcolor="#9CCFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCFFF')"><img width="1" height="1"></td>
<td bgcolor="#9CCFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCFCE')"><img width="1" height="1"></td>
<td bgcolor="#9CCF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCF9C')"><img width="1" height="1"></td>
<td bgcolor="#9CCF63" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCF63')"><img width="1" height="1"></td>
<td bgcolor="#9CCF31" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCF31')"><img width="1" height="1"></td>
<td bgcolor="#9CCF00" width="11" height="11" onclick="IBM_RTE_selectColor('#9CCF00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#FF9AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9AFF')"><img width="1" height="1"></td>
<td bgcolor="#FF9ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9ACE')"><img width="1" height="1"></td>
<td bgcolor="#FF9A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9A9C')"><img width="1" height="1"></td>
<td bgcolor="#FF9A63" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9A63')"><img width="1" height="1"></td>
<td bgcolor="#FF9A31" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9A31')"><img width="1" height="1"></td>
<td bgcolor="#FF9A00" width="11" height="11" onclick="IBM_RTE_selectColor('#FF9A00')"><img width="1" height="1"></td>
<td bgcolor="#CE9AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#CE9AFF')"><img width="1" height="1"></td>
<td bgcolor="#CE9ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#CE9ACE')"><img width="1" height="1"></td>
<td bgcolor="#CE9A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#CE9A9C')"><img width="1" height="1"></td>
<td bgcolor="#CE9A63" width="11" height="11" onclick="IBM_RTE_selectColor('#CE9A63')"><img width="1" height="1"></td>
<td bgcolor="#CE9A31" width="11" height="11" onclick="IBM_RTE_selectColor('#CE9A31')"><img width="1" height="1"></td>
<td bgcolor="#CD9A00" width="11" height="11" onclick="IBM_RTE_selectColor('#CD9A00')"><img width="1" height="1"></td>
<td bgcolor="#9C9AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9AFF')"><img width="1" height="1"></td>
<td bgcolor="#9C9ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9ACE')"><img width="1" height="1"></td>
<td bgcolor="#9C9A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9A9C')"><img width="1" height="1"></td>
<td bgcolor="#9C9A63" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9A63')"><img width="1" height="1"></td>
<td bgcolor="#9C9A31" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9A31')"><img width="1" height="1"></td>
<td bgcolor="#9C9A00" width="11" height="11" onclick="IBM_RTE_selectColor('#9C9A00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#FF65FF" width="11" height="11" onclick="IBM_RTE_selectColor('#FF65FF')"><img width="1" height="1"></td>
<td bgcolor="#FF65CE" width="11" height="11" onclick="IBM_RTE_selectColor('#FF65CE')"><img width="1" height="1"></td>
<td bgcolor="#FF659C" width="11" height="11" onclick="IBM_RTE_selectColor('#FF659C')"><img width="1" height="1"></td>
<td bgcolor="#FF6563" width="11" height="11" onclick="IBM_RTE_selectColor('#FF6563')"><img width="1" height="1"></td>
<td bgcolor="#FF6531" width="11" height="11" onclick="IBM_RTE_selectColor('#FF6531')"><img width="1" height="1"></td>
<td bgcolor="#FF6500" width="11" height="11" onclick="IBM_RTE_selectColor('#FF6500')"><img width="1" height="1"></td>
<td bgcolor="#CE65FF" width="11" height="11" onclick="IBM_RTE_selectColor('#CE65FF')"><img width="1" height="1"></td>
<td bgcolor="#CE65CE" width="11" height="11" onclick="IBM_RTE_selectColor('#CE65CE')"><img width="1" height="1"></td>
<td bgcolor="#CE659C" width="11" height="11" onclick="IBM_RTE_selectColor('#CE659C')"><img width="1" height="1"></td>
<td bgcolor="#CE6563" width="11" height="11" onclick="IBM_RTE_selectColor('#CE6563')"><img width="1" height="1"></td>
<td bgcolor="#CE6531" width="11" height="11" onclick="IBM_RTE_selectColor('#CE6531')"><img width="1" height="1"></td>
<td bgcolor="#CD6500" width="11" height="11" onclick="IBM_RTE_selectColor('#CD6500')"><img width="1" height="1"></td>
<td bgcolor="#9C65FF" width="11" height="11" onclick="IBM_RTE_selectColor('#9C65FF')"><img width="1" height="1"></td>
<td bgcolor="#9C65CE" width="11" height="11" onclick="IBM_RTE_selectColor('#9C65CE')"><img width="1" height="1"></td>
<td bgcolor="#9C659C" width="11" height="11" onclick="IBM_RTE_selectColor('#9C659C')"><img width="1" height="1"></td>
<td bgcolor="#9C6563" width="11" height="11" onclick="IBM_RTE_selectColor('#9C6563')"><img width="1" height="1"></td>
<td bgcolor="#9C6531" width="11" height="11" onclick="IBM_RTE_selectColor('#9C6531')"><img width="1" height="1"></td>
<td bgcolor="#9C6500" width="11" height="11" onclick="IBM_RTE_selectColor('#9C6500')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#FF30FF" width="11" height="11" onclick="IBM_RTE_selectColor('#FF30FF')"><img width="1" height="1"></td>
<td bgcolor="#FF30CE" width="11" height="11" onclick="IBM_RTE_selectColor('#FF30CE')"><img width="1" height="1"></td>
<td bgcolor="#FF309C" width="11" height="11" onclick="IBM_RTE_selectColor('#FF309C')"><img width="1" height="1"></td>
<td bgcolor="#FF3063" width="11" height="11" onclick="IBM_RTE_selectColor('#FF3063')"><img width="1" height="1"></td>
<td bgcolor="#FF3031" width="11" height="11" onclick="IBM_RTE_selectColor('#FF3031')"><img width="1" height="1"></td>
<td bgcolor="#FF3000" width="11" height="11" onclick="IBM_RTE_selectColor('#FF3000')"><img width="1" height="1"></td>
<td bgcolor="#CE30FF" width="11" height="11" onclick="IBM_RTE_selectColor('#CE30FF')"><img width="1" height="1"></td>
<td bgcolor="#CE30CE" width="11" height="11" onclick="IBM_RTE_selectColor('#CE30CE')"><img width="1" height="1"></td>
<td bgcolor="#CE309C" width="11" height="11" onclick="IBM_RTE_selectColor('#CE309C')"><img width="1" height="1"></td>
<td bgcolor="#CE3063" width="11" height="11" onclick="IBM_RTE_selectColor('#CE3063')"><img width="1" height="1"></td>
<td bgcolor="#CE3031" width="11" height="11" onclick="IBM_RTE_selectColor('#CE3031')"><img width="1" height="1"></td>
<td bgcolor="#CD3000" width="11" height="11" onclick="IBM_RTE_selectColor('#CD3000')"><img width="1" height="1"></td>
<td bgcolor="#9C30FF" width="11" height="11" onclick="IBM_RTE_selectColor('#9C30FF')"><img width="1" height="1"></td>
<td bgcolor="#9C30CE" width="11" height="11" onclick="IBM_RTE_selectColor('#9C30CE')"><img width="1" height="1"></td>
<td bgcolor="#9C309C" width="11" height="11" onclick="IBM_RTE_selectColor('#9C309C')"><img width="1" height="1"></td>
<td bgcolor="#9C3063" width="11" height="11" onclick="IBM_RTE_selectColor('#9C3063')"><img width="1" height="1"></td>
<td bgcolor="#9C3031" width="11" height="11" onclick="IBM_RTE_selectColor('#9C3031')"><img width="1" height="1"></td>
<td bgcolor="#9C3000" width="11" height="11" onclick="IBM_RTE_selectColor('#9C3000')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#FF00FF" width="11" height="11" onclick="IBM_RTE_selectColor('#FF00FF')"><img width="1" height="1"></td>
<td bgcolor="#FF00CE" width="11" height="11" onclick="IBM_RTE_selectColor('#FF00CE')"><img width="1" height="1"></td>
<td bgcolor="#FF009C" width="11" height="11" onclick="IBM_RTE_selectColor('#FF009C')"><img width="1" height="1"></td>
<td bgcolor="#FF0063" width="11" height="11" onclick="IBM_RTE_selectColor('#FF0063')"><img width="1" height="1"></td>
<td bgcolor="#FF0031" width="11" height="11" onclick="IBM_RTE_selectColor('#FF0031')"><img width="1" height="1"></td>
<td bgcolor="#FF0000" width="11" height="11" onclick="IBM_RTE_selectColor('#FF0000')"><img width="1" height="1"></td>
<td bgcolor="#CE00FF" width="11" height="11" onclick="IBM_RTE_selectColor('#CE00FF')"><img width="1" height="1"></td>
<td bgcolor="#CE00CE" width="11" height="11" onclick="IBM_RTE_selectColor('#CE00CE')"><img width="1" height="1"></td>
<td bgcolor="#CE009C" width="11" height="11" onclick="IBM_RTE_selectColor('#CE009C')"><img width="1" height="1"></td>
<td bgcolor="#CE0063" width="11" height="11" onclick="IBM_RTE_selectColor('#CE0063')"><img width="1" height="1"></td>
<td bgcolor="#CE0031" width="11" height="11" onclick="IBM_RTE_selectColor('#CE0031')"><img width="1" height="1"></td>
<td bgcolor="#CD0000" width="11" height="11" onclick="IBM_RTE_selectColor('#CD0000')"><img width="1" height="1"></td>
<td bgcolor="#9C00FF" width="11" height="11" onclick="IBM_RTE_selectColor('#9C00FF')"><img width="1" height="1"></td>
<td bgcolor="#9C00CE" width="11" height="11" onclick="IBM_RTE_selectColor('#9C00CE')"><img width="1" height="1"></td>
<td bgcolor="#9C009C" width="11" height="11" onclick="IBM_RTE_selectColor('#9C009C')"><img width="1" height="1"></td>
<td bgcolor="#9C0063" width="11" height="11" onclick="IBM_RTE_selectColor('#9C0063')"><img width="1" height="1"></td>
<td bgcolor="#9C0031" width="11" height="11" onclick="IBM_RTE_selectColor('#9C0031')"><img width="1" height="1"></td>
<td bgcolor="#9C0000" width="11" height="11" onclick="IBM_RTE_selectColor('#9C0000')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#63FFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#63FFFF')"><img width="1" height="1"></td>
<td bgcolor="#63FFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#63FFCE')"><img width="1" height="1"></td>
<td bgcolor="#63FF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#63FF9C')"><img width="1" height="1"></td>
<td bgcolor="#63FF63" width="11" height="11" onclick="IBM_RTE_selectColor('#63FF63')"><img width="1" height="1"></td>
<td bgcolor="#63FF31" width="11" height="11" onclick="IBM_RTE_selectColor('#63FF31')"><img width="1" height="1"></td>
<td bgcolor="#63FF00" width="11" height="11" onclick="IBM_RTE_selectColor('#63FF00')"><img width="1" height="1"></td>
<td bgcolor="#31FFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#31FFFF')"><img width="1" height="1"></td>
<td bgcolor="#31FFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#31FFCE')"><img width="1" height="1"></td>
<td bgcolor="#31FF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#31FF9C')"><img width="1" height="1"></td>
<td bgcolor="#31FF63" width="11" height="11" onclick="IBM_RTE_selectColor('#31FF63')"><img width="1" height="1"></td>
<td bgcolor="#31FF31" width="11" height="11" onclick="IBM_RTE_selectColor('#31FF31')"><img width="1" height="1"></td>
<td bgcolor="#31FF00" width="11" height="11" onclick="IBM_RTE_selectColor('#31FF00')"><img width="1" height="1"></td>
<td bgcolor="#00FFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#00FFFF')"><img width="1" height="1"></td>
<td bgcolor="#00FFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#00FFCE')"><img width="1" height="1"></td>
<td bgcolor="#00FF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#00FF9C')"><img width="1" height="1"></td>
<td bgcolor="#00FF63" width="11" height="11" onclick="IBM_RTE_selectColor('#00FF63')"><img width="1" height="1"></td>
<td bgcolor="#00FF31" width="11" height="11" onclick="IBM_RTE_selectColor('#00FF31')"><img width="1" height="1"></td>
<td bgcolor="#00FF00" width="11" height="11" onclick="IBM_RTE_selectColor('#00FF00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#63CFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#63CFFF')"><img width="1" height="1"></td>
<td bgcolor="#63CFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#63CFCE')"><img width="1" height="1"></td>
<td bgcolor="#63CF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#63CF9C')"><img width="1" height="1"></td>
<td bgcolor="#63CF63" width="11" height="11" onclick="IBM_RTE_selectColor('#63CF63')"><img width="1" height="1"></td>
<td bgcolor="#63CF31" width="11" height="11" onclick="IBM_RTE_selectColor('#63CF31')"><img width="1" height="1"></td>
<td bgcolor="#63CF00" width="11" height="11" onclick="IBM_RTE_selectColor('#63CF00')"><img width="1" height="1"></td>
<td bgcolor="#31CFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#31CFFF')"><img width="1" height="1"></td>
<td bgcolor="#31CFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#31CFCE')"><img width="1" height="1"></td>
<td bgcolor="#31CF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#31CF9C')"><img width="1" height="1"></td>
<td bgcolor="#31CF63" width="11" height="11" onclick="IBM_RTE_selectColor('#31CF63')"><img width="1" height="1"></td>
<td bgcolor="#31CF31" width="11" height="11" onclick="IBM_RTE_selectColor('#31CF31')"><img width="1" height="1"></td>
<td bgcolor="#31CF00" width="11" height="11" onclick="IBM_RTE_selectColor('#31CF00')"><img width="1" height="1"></td>
<td bgcolor="#00CFFF" width="11" height="11" onclick="IBM_RTE_selectColor('#00CFFF')"><img width="1" height="1"></td>
<td bgcolor="#00CFCE" width="11" height="11" onclick="IBM_RTE_selectColor('#00CFCE')"><img width="1" height="1"></td>
<td bgcolor="#00CF9C" width="11" height="11" onclick="IBM_RTE_selectColor('#00CF9C')"><img width="1" height="1"></td>
<td bgcolor="#00CF63" width="11" height="11" onclick="IBM_RTE_selectColor('#00CF63')"><img width="1" height="1"></td>
<td bgcolor="#00CF31" width="11" height="11" onclick="IBM_RTE_selectColor('#00CF31')"><img width="1" height="1"></td>
<td bgcolor="#00CF00" width="11" height="11" onclick="IBM_RTE_selectColor('#00CF00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#639AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#639AFF')"><img width="1" height="1"></td>
<td bgcolor="#639ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#639ACE')"><img width="1" height="1"></td>
<td bgcolor="#639A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#639A9C')"><img width="1" height="1"></td>
<td bgcolor="#639A63" width="11" height="11" onclick="IBM_RTE_selectColor('#639A63')"><img width="1" height="1"></td>
<td bgcolor="#639A31" width="11" height="11" onclick="IBM_RTE_selectColor('#639A31')"><img width="1" height="1"></td>
<td bgcolor="#639A00" width="11" height="11" onclick="IBM_RTE_selectColor('#639A00')"><img width="1" height="1"></td>
<td bgcolor="#319AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#319AFF')"><img width="1" height="1"></td>
<td bgcolor="#319ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#319ACE')"><img width="1" height="1"></td>
<td bgcolor="#319A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#319A9C')"><img width="1" height="1"></td>
<td bgcolor="#319A63" width="11" height="11" onclick="IBM_RTE_selectColor('#319A63')"><img width="1" height="1"></td>
<td bgcolor="#319A31" width="11" height="11" onclick="IBM_RTE_selectColor('#319A31')"><img width="1" height="1"></td>
<td bgcolor="#319A00" width="11" height="11" onclick="IBM_RTE_selectColor('#319A00')"><img width="1" height="1"></td>
<td bgcolor="#009AFF" width="11" height="11" onclick="IBM_RTE_selectColor('#009AFF')"><img width="1" height="1"></td>
<td bgcolor="#009ACE" width="11" height="11" onclick="IBM_RTE_selectColor('#009ACE')"><img width="1" height="1"></td>
<td bgcolor="#009A9C" width="11" height="11" onclick="IBM_RTE_selectColor('#009A9C')"><img width="1" height="1"></td>
<td bgcolor="#009A63" width="11" height="11" onclick="IBM_RTE_selectColor('#009A63')"><img width="1" height="1"></td>
<td bgcolor="#009A31" width="11" height="11" onclick="IBM_RTE_selectColor('#009A31')"><img width="1" height="1"></td>
<td bgcolor="#009A00" width="11" height="11" onclick="IBM_RTE_selectColor('#009A00')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#6365FF" width="11" height="11" onclick="IBM_RTE_selectColor('#6365FF')"><img width="1" height="1"></td>
<td bgcolor="#6365CE" width="11" height="11" onclick="IBM_RTE_selectColor('#6365CE')"><img width="1" height="1"></td>
<td bgcolor="#63659C" width="11" height="11" onclick="IBM_RTE_selectColor('#63659C')"><img width="1" height="1"></td>
<td bgcolor="#636563" width="11" height="11" onclick="IBM_RTE_selectColor('#636563')"><img width="1" height="1"></td>
<td bgcolor="#636531" width="11" height="11" onclick="IBM_RTE_selectColor('#636531')"><img width="1" height="1"></td>
<td bgcolor="#636500" width="11" height="11" onclick="IBM_RTE_selectColor('#636500')"><img width="1" height="1"></td>
<td bgcolor="#3165FF" width="11" height="11" onclick="IBM_RTE_selectColor('#3165FF')"><img width="1" height="1"></td>
<td bgcolor="#3165CE" width="11" height="11" onclick="IBM_RTE_selectColor('#3165CE')"><img width="1" height="1"></td>
<td bgcolor="#31659C" width="11" height="11" onclick="IBM_RTE_selectColor('#31659C')"><img width="1" height="1"></td>
<td bgcolor="#316563" width="11" height="11" onclick="IBM_RTE_selectColor('#316563')"><img width="1" height="1"></td>
<td bgcolor="#316531" width="11" height="11" onclick="IBM_RTE_selectColor('#316531')"><img width="1" height="1"></td>
<td bgcolor="#316500" width="11" height="11" onclick="IBM_RTE_selectColor('#316500')"><img width="1" height="1"></td>
<td bgcolor="#0065FF" width="11" height="11" onclick="IBM_RTE_selectColor('#0065FF')"><img width="1" height="1"></td>
<td bgcolor="#0065CE" width="11" height="11" onclick="IBM_RTE_selectColor('#0065CE')"><img width="1" height="1"></td>
<td bgcolor="#00659C" width="11" height="11" onclick="IBM_RTE_selectColor('#00659C')"><img width="1" height="1"></td>
<td bgcolor="#006563" width="11" height="11" onclick="IBM_RTE_selectColor('#006563')"><img width="1" height="1"></td>
<td bgcolor="#006531" width="11" height="11" onclick="IBM_RTE_selectColor('#006531')"><img width="1" height="1"></td>
<td bgcolor="#006500" width="11" height="11" onclick="IBM_RTE_selectColor('#006500')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#6330FF" width="11" height="11" onclick="IBM_RTE_selectColor('#6330FF')"><img width="1" height="1"></td>
<td bgcolor="#6330CE" width="11" height="11" onclick="IBM_RTE_selectColor('#6330CE')"><img width="1" height="1"></td>
<td bgcolor="#63309C" width="11" height="11" onclick="IBM_RTE_selectColor('#63309C')"><img width="1" height="1"></td>
<td bgcolor="#633063" width="11" height="11" onclick="IBM_RTE_selectColor('#633063')"><img width="1" height="1"></td>
<td bgcolor="#633031" width="11" height="11" onclick="IBM_RTE_selectColor('#633031')"><img width="1" height="1"></td>
<td bgcolor="#633000" width="11" height="11" onclick="IBM_RTE_selectColor('#633000')"><img width="1" height="1"></td>
<td bgcolor="#3130FF" width="11" height="11" onclick="IBM_RTE_selectColor('#3130FF')"><img width="1" height="1"></td>
<td bgcolor="#3130CE" width="11" height="11" onclick="IBM_RTE_selectColor('#3130CE')"><img width="1" height="1"></td>
<td bgcolor="#31309C" width="11" height="11" onclick="IBM_RTE_selectColor('#31309C')"><img width="1" height="1"></td>
<td bgcolor="#313063" width="11" height="11" onclick="IBM_RTE_selectColor('#313063')"><img width="1" height="1"></td>
<td bgcolor="#313031" width="11" height="11" onclick="IBM_RTE_selectColor('#313031')"><img width="1" height="1"></td>
<td bgcolor="#313000" width="11" height="11" onclick="IBM_RTE_selectColor('#313000')"><img width="1" height="1"></td>
<td bgcolor="#0030FF" width="11" height="11" onclick="IBM_RTE_selectColor('#0030FF')"><img width="1" height="1"></td>
<td bgcolor="#0030CE" width="11" height="11" onclick="IBM_RTE_selectColor('#0030CE')"><img width="1" height="1"></td>
<td bgcolor="#00309C" width="11" height="11" onclick="IBM_RTE_selectColor('#00309C')"><img width="1" height="1"></td>
<td bgcolor="#003063" width="11" height="11" onclick="IBM_RTE_selectColor('#003063')"><img width="1" height="1"></td>
<td bgcolor="#003031" width="11" height="11" onclick="IBM_RTE_selectColor('#003031')"><img width="1" height="1"></td>
<td bgcolor="#003000" width="11" height="11" onclick="IBM_RTE_selectColor('#003000')"><img width="1" height="1"></td>
</tr>
<tr>
<td bgcolor="#6300FF" width="11" height="11" onclick="IBM_RTE_selectColor('#6300FF')"><img width="1" height="1"></td>
<td bgcolor="#6300CE" width="11" height="11" onclick="IBM_RTE_selectColor('#6300CE')"><img width="1" height="1"></td>
<td bgcolor="#63009C" width="11" height="11" onclick="IBM_RTE_selectColor('#63009C')"><img width="1" height="1"></td>
<td bgcolor="#630063" width="11" height="11" onclick="IBM_RTE_selectColor('#630063')"><img width="1" height="1"></td>
<td bgcolor="#630031" width="11" height="11" onclick="IBM_RTE_selectColor('#630031')"><img width="1" height="1"></td>
<td bgcolor="#630000" width="11" height="11" onclick="IBM_RTE_selectColor('#630000')"><img width="1" height="1"></td>
<td bgcolor="#3100FF" width="11" height="11" onclick="IBM_RTE_selectColor('#3100FF')"><img width="1" height="1"></td>
<td bgcolor="#3100CE" width="11" height="11" onclick="IBM_RTE_selectColor('#3100CE')"><img width="1" height="1"></td>
<td bgcolor="#31009C" width="11" height="11" onclick="IBM_RTE_selectColor('#31009C')"><img width="1" height="1"></td>
<td bgcolor="#310063" width="11" height="11" onclick="IBM_RTE_selectColor('#310063')"><img width="1" height="1"></td>
<td bgcolor="#310031" width="11" height="11" onclick="IBM_RTE_selectColor('#310031')"><img width="1" height="1"></td>
<td bgcolor="#310000" width="11" height="11" onclick="IBM_RTE_selectColor('#310000')"><img width="1" height="1"></td>
<td bgcolor="#0000FF" width="11" height="11" onclick="IBM_RTE_selectColor('#0000FF')"><img width="1" height="1"></td>
<td bgcolor="#0000CE" width="11" height="11" onclick="IBM_RTE_selectColor('#0000CE')"><img width="1" height="1"></td>
<td bgcolor="#00009C" width="11" height="11" onclick="IBM_RTE_selectColor('#00009C')"><img width="1" height="1"></td>
<td bgcolor="#000063" width="11" height="11" onclick="IBM_RTE_selectColor('#000063')"><img width="1" height="1"></td>
<td bgcolor="#000031" width="11" height="11" onclick="IBM_RTE_selectColor('#000031')"><img width="1" height="1"></td>
<td bgcolor="#000000" width="11" height="11" onclick="IBM_RTE_selectColor('#000000')"><img width="1" height="1"></td>

                    </tr>
                </table>
            
            </td>
        </tr>
    </table>

</body>
</html>
