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

<% boolean isAccessible = request.getParameter("isAccessible") != null; %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title><%= resourceBundle.getString("InsertTable") %></title>

<link rel="STYLESHEET" type="text/css" href="Styles.css">

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
	}else {
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
		(cols1.localeCompare(lowerLimit) < 0)||(cols1.localeCompare(upperLimit) > 0)) {
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
				table.setAttribute("width", "100%");
				table.setAttribute("cellpadding", "2");
				table.setAttribute("cellspacing", "2");
				var tbody = frame.contentDocument.createElement("tbody");
				for (var i = 0; i < rows; i++) {
					var tr = frame.contentDocument.createElement("tr");
					for (var j = 0; j < cols; j++) {
						//var td = frame.contentDocument.createElement("td");
						var col;
						if(i == 0 || j == 0){ // first row or first column
							col = frame.contentDocument.createElement("th");
						}else{
							col = frame.contentDocument.createElement("td");
						}
						var br = frame.contentDocument.createElement("br");
						col.appendChild(br);
						//tr.appendChild(td);
						tr.appendChild(col);
					}
					tbody.appendChild(tr);
				}
				table.appendChild(tbody);      
				this.opener.IBM_RTE_insertNodeAtSelection(frame.contentWindow, table);
				parent.close();    
			}
		}else {
			var tbl = "<table border=1 width='100%'>";

			for (i = 0; i < rows; i++) {
				tbl += "<tr>";
				for (j = 0; j < cols; j++) {            
					if(i == 0 || j == 0){
						tbl += "<th>&nbsp;</th>";	
					}else{
					tbl += "<td>&nbsp;</td>";
					}
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
			}
		}
	}
}

    var timer = null;
    var skipfocus = false;
    function focusOnMe() {
        if (!skipfocus) {
            window.focus();
        }
        timer = setTimeout('focusOnMe()', 100);
    }

    function resizeWindow() {
        var h = document.getElementById("thebody").offsetHeight;
        var w = document.getElementById("thebody").offsetWidth;
        window.resizeTo(w + 30, h + 60);
    }

</script>
<body dir="<%= tableDir %>" lang="<%= langToUse %>" onLoad="resizeWindow(); mytimer=setTimeout('focusOnMe()', 500);" leftMargin="0" rightMargin="0" topMargin="0" marginheight="0" marginwidth="0" style="margin:6px;">
<div id="thebody">

<img src="<%= img %>/table.gif" alt="" width="18" height="18" border="0" align="absmiddle">
<span class="portlet-section-header"><%= resourceBundle.getString("InsertATable") %></span>

<hr class="portlet-separator">

<label for="rows"><%= resourceBundle.getString("NumberOfRows") %></label><br>
<input id="rows" type="text" value="2" onfocus="skipfocus=true" onblur="skipfocus=false" size="20" maxsize="2" class="portlet-form-input-field"/>

<br>&nbsp;<br>
<label for="rows"><%= resourceBundle.getString("NumberOfColumns") %></label><br>
<input id="cols" type="text" value="2" onfocus="skipfocus=true" onblur="skipfocus=false" size="20" maxsize="2" class="portlet-form-input-field"/>

<br>&nbsp;<br>
<hr class="portlet-separator">
<input class="wpsButtonText" type="button" value="<%= resourceBundle.getString("OK") %>" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)" onClick="javascript:IBM_RTE_okClicked()" onfocus="skipfocus=true" onblur="skipfocus=false">
<input class="wpsButtonText" type="button" value="<%= resourceBundle.getString("Cancel") %>" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)" onClick="javascript:parent.close()" onfocus="skipfocus=true" onblur="skipfocus=false">

</div> 
</body>
</html>

