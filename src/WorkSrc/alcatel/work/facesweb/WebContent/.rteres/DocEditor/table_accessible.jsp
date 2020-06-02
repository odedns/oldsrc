<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<% String loc = request.getParameter("locale"); %>
<% String img = request.getParameter("images"); %>
<% String editorName = request.getParameter("editorName"); %>
<% ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); %>

<% String langToUse = LocaleHelper.getLocale(loc).getLanguage(); %>
<% String isBidi = request.getParameter("isBidi"); %>
<% String tableDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) tableDir = "RTL"; %>
<% String alignDir = "left"; if (isBidi.equalsIgnoreCase("true")) alignDir = "right"; %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%= resourceBundle.getString("InsertTable") %></title>
</head>

<script language="javascript" src="script_editor.js"></script>                   
<script language="javascript">

    var errorOccurred = false;

    function IBM_RTE_cancelClicked() {    

        parent.close();
    }

    function IBM_RTE_okClicked() {

        if (errorOccurred == true) {

            parent.close();
        }
        else {
    
            var rows = document.getElementById('rows').value;
            var cols = document.getElementById('cols').value;

            // Mozilla and IE have different behaviors in string comparison;
            // Mozilla only compares the 1st character while IE goes through the entire string.
            // Only need the first character to determine if it is a valid number 1-9.
            var rows1 = rows.substring(0, 1);
            var cols1 = cols.substring(0, 1);

            var lowerLimit = "1";
            var upperLimit = "9";

            if ((rows1.localeCompare(lowerLimit) < 0)||(rows1.localeCompare(upperLimit) > 0)||
                (cols1.localeCompare(lowerLimit) < 0)||(cols1.localeCompare(upperLimit) > 0)) 
            {
                alert("<%= resourceBundle.getString("InvalidRowsOrColumns") %>");
                return;
            }
            
            if (this.opener.IBM_RTE_isMozilla()) {
    
                var frame = this.opener.IBM_RTE_getFrame("<%= editorName %>");
                rows = parseInt(rows);
                cols = parseInt(cols);
    
                if ((rows > 0) && (cols > 0)) {
                    var table = frame.contentDocument.createElement("table");
                    table.setAttribute("border", "1");
                    table.setAttribute("cellpadding", "2");
                    table.setAttribute("cellspacing", "2");
                    var tbody = frame.contentDocument.createElement("tbody");
                    for (var i = 0; i < rows; i++) {
                        var tr = frame.contentDocument.createElement("tr");
                        for (var j = 0; j < cols; j++) {
                            var td = frame.contentDocument.createElement("td");
                            var br = frame.contentDocument.createElement("br");
                            td.appendChild(br);
                            tr.appendChild(td);
                        }
                        tbody.appendChild(tr);
                    }
                    table.appendChild(tbody);      
                    this.opener.IBM_RTE_insertNodeAtSelection(frame.contentWindow, table);
                    parent.close();    
            }}
            else {
    
                var tbl = "<table border=1 width='100%'>";
    
                for (i = 0; i < rows; i++) {
                    tbl += "<tr>";
                    for (j = 0; j < cols; j++) {            
                        tbl += "<td></td>";
                    }
                    tbl += "</tr>";
                }
                
                tbl += "</table>";
                  
                try {
    
                    var cursorPos = this.opener.IBM_RTE_getDocument("<%= editorName %>").selection.createRange().duplicate();
                    cursorPos.pasteHTML(tbl);
                    parent.close();    
                }
                catch (e) {
                    
                    errorOccurred = true;
                    document.getElementById("TheMessage").innerHTML = "<span style=\"font-family: Verdana, sans-serif; font-size: 12px;\"><center><%= resourceBundle.getString("rte.error.failed") %></center></span>";
                    document.getElementById("TheCancelButton").innerHTML = "";
        }}}
    }

</script>

<body dir="<%= tableDir %>" lang="<%= langToUse %>" leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0">

<table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
<tr>
<td width="100%" height="35" valign="middle" align="<%= alignDir %>" style="border: 1px #405380 solid; font-family: Verdana, sans-serif; background-color: #A8B0C8;">
    &nbsp;<img border="0" align="middle" src="<%= img %>/table.gif">&nbsp;<span style="color: #2E3D5E; font-family: sans-serif; font-size: 9pt; font-weight: bold; border-color: #000000;"><%= resourceBundle.getString("InsertATable") %></span>
</td>
</tr>
<tr>
<td width="100%" height="100%" style="border: 1px #405380 solid; background-color: #FFFFFF;">
    
    <div id="TheMessage">
    
        <table width="100%" height="100%" cellspacing="0" cellpadding="0" border="0">
        <tr><td align="<%= alignDir %>">
            <span style="font-family: Verdana, sans-serif; font-size: 12px;">
                &nbsp;&nbsp;<%= resourceBundle.getString("NumberOfRows") %>&nbsp;&nbsp;<br>
                &nbsp;&nbsp;<input id="rows" type="text" value="2" size="15" maxsize="2"/>&nbsp;&nbsp;
            </span>    
        </td></tr>
        <tr><td align="<%= alignDir %>">
            <span style="font-family: Verdana, sans-serif; font-size: 12px;">
                &nbsp;&nbsp;<%= resourceBundle.getString("NumberOfColumns") %>&nbsp;&nbsp;<br>
                &nbsp;&nbsp;<input id="cols" type="text" value="2" size="15" maxsize="2"/>&nbsp;&nbsp;
            </span>        
        </td></tr>
        </table>
        
    </div>        

</td>
</tr>
<tr>
<td align="<%= alignDir %>" width="100%" height="35" valign="middle" style="border: 1px #405380 solid; font-family: Verdana, sans-serif; background-color: #A8B0C8;">
    <nobr>
        <% if (alignDir.equalsIgnoreCase("left")) { %>
            &nbsp;<span id="TheOkButton" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)">
                  <a href="javascript:IBM_RTE_okClicked();" tabindex="1" style="text-decoration:none; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; font-weight: bold; background-color: #c1d1e2; background-image: url('<%= img %>/dialogbutton.gif'); background-repeat: repeat-x; background-position: 0px center;  border: 1px solid #405380; cursor: pointer; color: #405380; padding: 2px 11px; margin: 0px; text-align: center; vertical-align: middle;" >
                  <%= resourceBundle.getString("OK") %></a>
            </span>
            <span id="TheCancelButton" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)">
                  <a href="javascript:parent.close();" tabindex="2" style="text-decoration:none; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; font-weight: bold; background-color: #c1d1e2; background-image: url('<%= img %>/dialogbutton.gif'); background-repeat: repeat-x; background-position: 0px center;  border: 1px solid #405380; cursor: pointer; color: #405380; padding: 2px 11px; margin: 0px; text-align: center; vertical-align: middle;">
                  <%= resourceBundle.getString("Cancel") %></a>
            </span>
        <% } else { %>
            &nbsp;<span id="TheOkButton" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)">
                  <a href="javascript:IBM_RTE_okClicked();" tabindex="1" style="text-decoration:none; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; font-weight: bold; background-color: #c1d1e2; background-image: url('<%= img %>/dialogbutton.gif'); background-repeat: repeat-x; background-position: 0px center;  border: 1px solid #405380; cursor: pointer; color: #405380; padding: 2px 11px; margin: 0px; text-align: center; vertical-align: middle;" >
                  <%= resourceBundle.getString("OK") %></a>
            <span id="TheCancelButton" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)">
                  <a href="javascript:parent.close();" tabindex="2" style="text-decoration:none; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 8pt; font-weight: bold; background-color: #c1d1e2; background-image: url('<%= img %>/dialogbutton.gif'); background-repeat: repeat-x; background-position: 0px center;  border: 1px solid #405380; cursor: pointer; color: #405380; padding: 2px 11px; margin: 0px; text-align: center; vertical-align: middle;">
                  <%= resourceBundle.getString("Cancel") %></a>
        <% } %>
    </nobr>
</td>
</tr>
</table>
       
</body>
</html>

