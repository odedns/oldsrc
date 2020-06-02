
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
<% String isAccessible = request.getParameter("isAccessible"); %>

<% String userAgent = request.getHeader("user-agent"); %>
<% boolean isMozilla = userAgent.indexOf("Gecko") == -1 ? false : true;  %>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title><%= resourceBundle.getString("Paragraph") %></title>

<link rel="STYLESHEET" type="text/css" href="Styles.css">


<script language="javascript">

    function setValues() {
   
        var rng = this.opener.IBM_RTE_getSelectionRange("<%= editorName %>");

        var parNode;
        if (this.opener.IBM_RTE_isMozilla()) {
            parNode = rng.startContainer;
            var strName = parNode.nodeName;
            while(strName != "P" && strName != "DIV" && strName != "BODY"){
                parNode = parNode.parentNode;
                strName = parNode.nodeName;
            } 
        }
        else {
            parNode = rng.parentElement();
            var strName = parNode.tagName;

            while(strName != "P" && strName != "DIV" && strName != "BODY"){
                parNode = parNode.parentNode;
                strName = parNode.tagName;
            } 
        }
			
			if(this.opener.IBM_RTE_isMozilla())
			{
				if(parNode.align != null)
				{
					parAlign = parNode.style.textAlign;
				}
				
				else
				{
					parAlign = parNode.align;
				}
			}

			else
			{
				parAlign = parNode.align;
			} 
        parLineHeight = parNode.style.lineHeight;
        parMgnLeft = parNode.style.marginLeft;
        parMgnRight = parNode.style.marginRight;
        parMgnTop = parNode.style.marginTop;
        parMgnBtm = parNode.style.marginBottom;    
		if("el" == "<%=loc%>" || "ro" == "<%=loc%>"){
			parMgnLeft = parMgnLeft.replace('.',',');
			parMgnRight = parMgnRight.replace('.',',');
			parMgnTop = parMgnTop.replace('.',',');
			parMgnBtm = parMgnBtm.replace('.',',');
		}
        var alnList = document.getElementById('alignList');
        for (i=0; i<alnList.options.length; i++) {
            if (alnList.options[i].value == parAlign) {
                 alnList.options[i].selected = true;
                 break;
            }
        }

        var mgnUnit = parMgnLeft.substring(parMgnLeft.length-2, parMgnLeft.length);
        var lt = parMgnLeft.substring(0, parMgnLeft.length-2);
        var rt = parMgnRight.substring(0, parMgnRight.length-2);
        if (lt)
            document.getElementById('leftId').value = lt;
        else 
            document.getElementById('leftId').value = 0;
        if (rt)
            document.getElementById('rightId').value = rt;
        else 
            document.getElementById('rightId').value = 0;



        var mgnUList = document.getElementById('mgnUnit');
        for (i=0; i<mgnUList.options.length; i++) {
            if (mgnUList.options[i].text == mgnUnit) {
                 mgnUList.options[i].selected = true;
                 break;
            }
        }

        var spUnit = parMgnTop.substring(parMgnTop.length-2, parMgnTop.length);
        var bf = parMgnTop.substring(0, parMgnTop.length-2);
        var at = parMgnBtm.substring(0, parMgnBtm.length-2);

        if (bf)
            document.getElementById('beforeId').value = bf;
        else
            document.getElementById('beforeId').value = 0;
        if (at)
            document.getElementById('afterId').value = at;
        else
            document.getElementById('afterId').value = 0;

        var spUList = document.getElementById('spUnit');
        for (i=0; i<spUList.options.length; i++) {
            if (spUList.options[i].text == spUnit) {
                 spUList.options[i].selected = true;
                 break;
            }
        }

        var lsList = document.getElementById('lineSpace');
        for (i=0; i<lsList.options.length; i++) {
            if (lsList.options[i].value == parLineHeight) {
                 lsList.options[i].selected = true;
                 break;
            }
        }

    }


    function okClicked() {
		if(!this.opener.IBM_RTE_isMozilla()){
			var isAccessible=<%=isAccessible%>
			if(isAccessible)
				this.opener.opener.IBM_RTE_backup("<%= editorName %>");
			else
				this.opener.IBM_RTE_backup("<%= editorName %>");
		}
        var alnList = document.getElementById('alignList');
        var alnType = alnList.options[alnList.selectedIndex].value;
  
        var lt = document.getElementById('leftId').value;
        var rt = document.getElementById('rightId').value;
		var notANumber;

        // Mozilla and IE have different behaviors in string comparison;
        // Mozilla only compares the 1st character while IE goes through the entire string.
        // Only need the first character to determine if it is a valid number 1-9.
		if("el" == "<%=loc%>" || "ro" == "<%=loc%>"){
			lt = lt.replace(/,/g,".");
			rt = rt.replace(/,/g,".");
		}
        var lt1 = lt.substring(0, 1);
        var rt1 = rt.substring(0, 1);

        var lowerLimit = "0";
        var upperLimit = "9";

		notANumber = checkIfNumber(lt);
		if(notANumber == "false")
		{
			alert("<%= resourceBundle.getString("InvalidLeftMargin") %>");
			document.getElementById('leftId').value = "0";
			return;
		}
		else if(notANumber == "decimal")
		{	
			lt = parseFloat(lt); } 
		else if(notANumber == "invalidNumber") {
			document.getElementById('leftId').value = "0";
			return;
		}		
		else if(notANumber == "integer")
			 lt = parseInt(lt);

		notANumber = checkIfNumber(rt);
		if(notANumber == "false")
		{
			alert("<%= resourceBundle.getString("InvalidRightMargin") %>");
			document.getElementById('rightId').value = "0";
			return;
		}
		else if(notANumber == "decimal")
			rt = parseFloat(rt);
		else if(notANumber == "invalidNumber") {
			document.getElementById('rightId').value = "0";
			return;
		}	
		else    
			rt = parseInt(rt);

        if ((lt1.localeCompare(lowerLimit) < 0)||(lt1.localeCompare(upperLimit) > 0)||
            (rt1.localeCompare(lowerLimit) < 0)||(rt1.localeCompare(upperLimit) > 0)) 
        {
		alert("<%= resourceBundle.getString("InvalidMargin") %>");
//            alert("Invalid Left or Right Margin");
            return;
        }

        var mgnUnit = document.getElementById('mgnUnit');
        var mgnU = mgnUnit.options[mgnUnit.selectedIndex].text;
		
		var items = new Array("ex", "em", "px", "cm", "mm", "pc", "in", "pt");
		var index = -1;

		for(var xx=0; xx < items.length; xx++){
			if(items[xx] == mgnU){
				index = xx;
				break;
			}
		}

		switch(index){
			case 0:
				if(lt > 100000 || rt > 100000){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 1:
				if(lt > 100000 || rt > 100000){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 2:
				if(lt > 1000000 || rt > 1000000){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 3:
				if(lt > 75750 || rt > 75750){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 4:
				if(lt > 757500 || rt > 757500){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 5:
				if(lt > 170000 || rt > 170000){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 6:
				if(lt > 29000 || rt > 29000){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
			case 7:
				if(lt > 134217.727 || rt > 134217.727){
					alert("<%= resourceBundle.getString("InvalidMargin") %>");
					return;
				}
				break;
		}

        var bf = document.getElementById('beforeId').value;
        var at = document.getElementById('afterId').value;
		if("el" == "<%=loc%>" || "ro" == "<%=loc%>"){
			bf = bf.replace(/,/g,".");
			at = at.replace(/,/g,".");
		}
        var bf1 = bf.substring(0, 1);
        var at1 = at.substring(0, 1);

        if ((bf1.localeCompare(lowerLimit) < 0)||(bf1.localeCompare(upperLimit) > 0)||
            (at1.localeCompare(lowerLimit) < 0)||(at1.localeCompare(upperLimit) > 0)) 
        {
            alert("<%= resourceBundle.getString("InvalidSpacing") %>");
            return;
        }
		
		notANumber = checkIfNumber(bf);
		if(notANumber == "false")
		{
			alert("<%= resourceBundle.getString("InvalidAboveSpace") %>");
			document.getElementById('beforeId').value = "0";
			return;
		}
		else if(notANumber == "decimal")
			bf = parseFloat(bf);
		else if(notANumber == "invalidNumber") {
			document.getElementById('beforeId').value = "0";
			return;
		}	
				
		else
			bf = parseInt(bf);

		notANumber = checkIfNumber(at);
		if(notANumber == "false")
		{
			alert("<%= resourceBundle.getString("InvalidBelowSpace") %>");
			document.getElementById('afterId').value = "0";
			return;
		}
		else if(notANumber == "decimal")
			at = parseFloat(at);
		else if(notANumber == "invalidNumber") {
			document.getElementById('afterId').value = "0";
			return;
		}	
		else
		    at = parseInt(at);

        var spUnit = document.getElementById('spUnit');
        var spU = spUnit.options[spUnit.selectedIndex].text;

		index = -1;

		for(var xx=0; xx < items.length; xx++){
			if(items[xx] == spU){
				index = xx;
				break;
			}
		}

		switch(index){
			case 0:
				if(bf > 100000 || at > 100000){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 1:
				if(bf > 100000 || at > 100000){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 2:
				if(bf > 1000000 || at > 1000000){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 3:
				if(bf > 75750 || at > 75750){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 4:
				if(bf > 757500 || at > 757500){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 5:
				if(bf > 170000 || at > 170000){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 6:
				if(bf > 29000 || at > 29000){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
			case 7:
				if(bf > 134217.727 || at > 134217.727){
					alert("<%= resourceBundle.getString("InvalidSpacing") %>");
					return;
				}
				break;
		}

        var lsList = document.getElementById('lineSpace');
        var lnSpace = lsList.options[lsList.selectedIndex].value;

        var rng = this.opener.IBM_RTE_getSelectionRange("<%= editorName %>");
		if(<%=isAccessible%>){
			if (this.opener.opener.IBM_RTE_isMozilla())
				this.opener.opener.IBM_RTE_Mozilla_addPTags("<%= editorName %>");
		}else{
			if (this.opener.IBM_RTE_isMozilla())
				this.opener.IBM_RTE_Mozilla_addPTags("<%= editorName %>");
		}

		var parNode;
        if (this.opener.IBM_RTE_isMozilla()) {
            parNode = rng.startContainer;
            var strName = parNode.nodeName;
            while(strName != "P" && strName != "DIV" && strName != "BODY"){
                parNode = parNode.parentNode;
                strName = parNode.nodeName;
            } 
        }
        else {
            parNode = rng.parentElement();
            var strName = parNode.tagName;

            while(strName != "P" && strName != "DIV" && strName != "BODY"){
                parNode = parNode.parentNode;
                strName = parNode.tagName;
            } 
        }
	<%-- Following code is for Mozilla --%>
	if(this.opener.IBM_RTE_isMozilla())
	{
		var arr = this.opener.IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName('P');
		var xx = 0;
		while(arr[xx] != null)
		{
			var bodyRange = this.opener.IBM_RTE_getDocument("<%= editorName %>").createRange();
			var inRange = false;
			paraRange = bodyRange;
			paraRange.setStartBefore(arr[xx]);
			paraRange.setEndAfter(arr[xx]);
			var START_TO_START = 0;
			var END_TO_START = 3;
			if(paraRange.compareBoundaryPoints(START_TO_START,rng) >= 0){
				if(paraRange.compareBoundaryPoints(END_TO_START,rng) <= 0)
					inRange = true;
			}
			else if(rng.compareBoundaryPoints(END_TO_START,paraRange) <= 0)
					inRange = true;
			if(inRange)
			{
					//parNode = arr[xx];
					arr[xx].align = alnType;
					arr[xx].style.textAlign = alnType;
					arr[xx].style.lineHeight = lnSpace;
					arr[xx].style.marginLeft = lt + mgnU;
					arr[xx].style.marginRight = rt + mgnU;
					arr[xx].style.marginTop = bf + spU;
					arr[xx].style.marginBottom = at + spU;
			}
			xx++;
		}
	}
<%-- Mozilla code end--%>
else
<%-- Internet Explore Code start--%>
{
		var arr = this.opener.IBM_RTE_getDocument("<%= editorName %>").body.getElementsByTagName('P');
		var xx = 0;
		var enteredInRange = false;
		var paraRange;
		while(arr[xx]!=null)
		{
			var inRange = false;
			paraRange = this.opener.IBM_RTE_getDocument("<%= editorName %>").body.createTextRange();
			paraRange.moveToElementText(arr[xx]);
			if(paraRange.compareEndPoints("StartToStart",rng) <= 0){
				if(paraRange.compareEndPoints("EndToStart",rng) >= 0)
					inRange = true;
			}
			else if(rng.compareEndPoints("EndToStart",paraRange) >= 0)
					inRange = true;
			if(inRange){
				parNode = arr[xx];
				parNode.align = alnType;
				parNode.style.lineHeight = lnSpace;
				parNode.style.marginLeft = lt + mgnU;
				parNode.style.marginRight = rt + mgnU;
				parNode.style.marginTop = bf + spU;
				parNode.style.marginBottom = at + spU;
				enteredInRange = true;
			}
			else if(enteredInRange)break;
			xx++;
		}
}
<%-- Internet Explore code end--%>
        parent.close();
    }


    function cancelClicked() {

        parent.close();   
    }

    //resize the window to fit the content of the main div
    //note the addition of 35px. This is a rough amount added 
    //to account for the title bar of the window.
    function resizeWindow() {
        var h = document.getElementById("thebody").offsetHeight;
        var w = document.getElementById("thebody").offsetWidth;
        
        window.resizeTo(w + 30, h + 60);
    }

    function initPara() {
        setValues();
        resizeWindow();
    }

function checkIfNumber(lt)
{	
	var ch;
	var flag = "integer";
	var dot = false;
	
	for(i=0; i<lt.length; i++)
	{	
		ch = lt.charAt(i);
	
		if((ch>=0 && ch <= 9) || ch == '.')
		{		
			if(dot == true && ch =='.'){
				alert("<%= resourceBundle.getString("InvalidNumberEntry") %>");
				flag = "invalidNumber";
				break;
			}
			if(ch == '.') 
			{	
				flag = "decimal";
				dot = true;
				continue;
			}
		}

		else
		{
			flag = "false";
			break;
		}
	}
	return flag;
}
</script>

</head>

<body dir="<%= tableDir %>" lang="<%= langToUse %>" onLoad="initPara();" style="margin:6px;">
<table>
<tr>
<td>
<div id="thebody">

<img src="<%= img %>/paragraphSupport.gif" alt="" width="18" height="18" border="0" align="absmiddle">
<span class="portlet-section-header"><%= resourceBundle.getString("Paragraph") %></span>

<hr class="portlet-separator">


<label for="alignment" class="portlet-form-field-label"><%= resourceBundle.getString("Alignment") %></label><br>
<SELECT id="alignList" >
			<OPTION value="left"><%= resourceBundle.getString("ParaLeft") %></OPTION>
			<OPTION value="center"><%= resourceBundle.getString("ParaCenter") %></OPTION>
			<OPTION value="right"><%= resourceBundle.getString("ParaRight") %></OPTION>
			<OPTION value="justify"><%= resourceBundle.getString("ParaJustify") %></OPTION>
</SELECT>

<br>&nbsp;<br>


<table border="0" cellpadding="0" cellspacing="0">

  <tr>
    <td colspan="5"><span class="portlet-section-header"><%= resourceBundle.getString("Margins") %></span></td>
  </tr>
  <tr>
    <td colspan="5" height="5"><img src="../images/dot.gif" alt="" width="1" height="5" border="0"></td>
  </tr>


  <tr>
    <td><%= resourceBundle.getString("Left") %></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><%= resourceBundle.getString("Right") %></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><%= resourceBundle.getString("Units") %></td>
  </tr>

  <tr>
    <td><input id="leftId" type="text" value="0" size="5" style="text-align: right;"/></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><input id="rightId" style="text-align: right;" type="text" value="0" size="5"></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td>
      <select id="mgnUnit">
        <option>ex</option>
        <option>em</option>
        <option selected>px</option>
        <option>cm</option>
        <option>mm</option>
        <option>pc</option>
        <option>in</option>
        <option>pt</option>
      </select>
    </td>
  </tr>

  <tr>
    <td colspan="5" height="15"><img src="../images/dot.gif" alt="" width="1" height="15" border="0"></td>
  </tr>
  <tr>
    <td colspan="5"><span class="portlet-section-header"><%= resourceBundle.getString("Spacing") %></span></td>
  </tr>
  <tr>
    <td colspan="5" height="5"><img src="../images/dot.gif" alt="" width="1" height="5" border="0"></td>
  </tr>

  <tr>
    <td><%= resourceBundle.getString("Above") %></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><%= resourceBundle.getString("Below") %></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><%= resourceBundle.getString("Units") %></td>
  </tr>

  <tr>
    <td><input id="beforeId" style="text-align: right;" type="text" value="0" size="5"></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td><input id="afterId" style="text-align: right;" type="text" value="0" size="5"></td>
    <td width="20"><img src="../images/dot.gif" alt="" width="20" height="1" border="0"></td>
    <td>
      <select id="spUnit">
        <option>ex</option>
        <option>em</option>
        <option selected>px</option>
        <option>cm</option>
        <option>mm</option>
        <option>pc</option>
        <option>in</option>
        <option>pt</option>
      </select>
    </td>
  </tr>

</table>

<br>

<%= resourceBundle.getString("LineSpacing") %><br>
      <select id="lineSpace">
        <option value="normal"><%= resourceBundle.getString("Normal") %></option>
        <option value="6pt">6pt</option>
        <option value="12pt">12pt</option>
        <option value="18pt">18pt</option>
        <option value="24pt">24pt</option>
        <option value="30pt">30pt</option>
        <option value="36pt">36pt</option>
      </select>

<hr class="portlet-separator">
<table>
<tr>
<td>
<input type="button" class="wpsButtonText"  value="<%= resourceBundle.getString("OK")%>" id="btnOk" onclick="okClicked();">
</td>
<td>
<input type="button" class="wpsButtonText" value="<%= resourceBundle.getString("Cancel") %>" id="btnCancel" onclick="cancelClicked();">
</td>
</tr>
</table>
</div>
</td>
</tr>
</table>
</body>
</html>

