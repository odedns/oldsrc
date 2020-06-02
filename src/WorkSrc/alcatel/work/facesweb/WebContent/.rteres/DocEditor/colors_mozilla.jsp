<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<% String editorName = request.getParameter("editorName"); %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>

<script language="javascript">

    function IBM_RTE_selectColor(color) {

        parent.IBM_RTE_getDocument("<%= editorName %>").execCommand(parent.command, false, color);
        parent.document.getElementById("<%= editorName %>_colorpalette").style.visibility = "hidden";
        parent.IBM_RTE_getWindow("<%= editorName %>").focus();
    }

    function IBM_RTE_initColorPalette() {
        
        if (document.getElementsByTagName) {        
            var x = document.getElementsByTagName('TD');
        }
        else if (document.all) {
            var x = document.all.tags('TD');
        }
        for (var i = 0; i < x.length; i++) {
            
            x[i].onmouseover = over;
            x[i].onmouseout = out;
            x[i].onclick = click;
        }
    }

    function over() {
        //this.style.border='2px dotted white';
        this.style.border='1px dotted white';
    }

    function out() {
        this.style.border='1px solid gray';
    }

    function click() {
        IBM_RTE_selectColor(this.id);
    }
</script>
</head>

<body bgcolor="white" onLoad="IBM_RTE_initColorPalette()" onblur="window.focus()">
<table border="1" cellpadding="1" cellspacing="1">
<tr>
<td id="#FFFFFF" bgcolor="#FFFFFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCCCC" bgcolor="#FFCCCC" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCC99" bgcolor="#FFCC99" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFFF99" bgcolor="#FFFF99" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFFFCC" bgcolor="#FFFFCC" width="20" height="20"><img width="1" height="1"></td>
<td id="#99FF99" bgcolor="#99FF99" width="20" height="20"><img width="1" height="1"></td>
<td id="#99FFFF" bgcolor="#99FFFF" width="20" height="20"><img width="1" height="1"></td>

<td id="#CCFFFF" bgcolor="#CCFFFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#CCCCFF" bgcolor="#CCCCFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCCFF" bgcolor="#FFCCFF" width="20" height="20"><img width="1" height="1"></td>
</tr>
<tr>
<td id="#CCCCCC" bgcolor="#CCCCCC" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF6666" bgcolor="#FF6666" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF9966" bgcolor="#FF9966" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFFF66" bgcolor="#FFFF66" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFFF33" bgcolor="#FFFF33" width="20" height="20"><img width="1" height="1"></td>
<td id="#66FF99" bgcolor="#66FF99" width="20" height="20"><img width="1" height="1"></td>
<td id="#33FFFF" bgcolor="#33FFFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#66FFFF" bgcolor="#66FFFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#9999FF" bgcolor="#9999FF" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF99FF" bgcolor="#FF99FF" width="20" height="20"><img width="1" height="1"></td>
</tr>
<tr>

<td id="#C0C0C0" bgcolor="#C0C0C0" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF0000" bgcolor="#FF0000" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF9900" bgcolor="#FF9900" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCC66" bgcolor="#FFCC66" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFFF00" bgcolor="#FFFF00" width="20" height="20"><img width="1" height="1"></td>
<td id="#33FF33" bgcolor="#33FF33" width="20" height="20"><img width="1" height="1"></td>
<td id="#66CCCC" bgcolor="#66CCCC" width="20" height="20"><img width="1" height="1"></td>
<td id="#33CCFF" bgcolor="#33CCFF" width="20" height="20"><img width="1" height="1"></td>
<td id="#6666CC" bgcolor="#6666CC" width="20" height="20"><img width="1" height="1"></td>
<td id="#CC66CC" bgcolor="#CC66CC" width="20" height="20"><img width="1" height="1"></td>
</tr>
<tr>
<td id="#999999" bgcolor="#999999" width="20" height="20"><img width="1" height="1"></td>
<td id="#CC0000" bgcolor="#CC0000" width="20" height="20"><img width="1" height="1"></td>
<td id="#FF6600" bgcolor="#FF6600" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCC33" bgcolor="#FFCC33" width="20" height="20"><img width="1" height="1"></td>
<td id="#FFCC00" bgcolor="#FFCC00" width="20" height="20"><img width="1" height="1"></td>

<td id="#33CC00" bgcolor="#33CC00" width="20" height="20"><img width="1" height="1"></td>
<td id="#00CCCC" bgcolor="#00CCCC" width="20" height="20"><img width="1" height="1"></td>
<td id="#3366FF" bgcolor="#3366FF" width="20" height="20"><img width="1" height="1"></td>
<td id="#6633FF" bgcolor="#6633FF" width="20" height="20"><img width="1" height="1"></td>
<td id="#CC33CC" bgcolor="#CC33CC" width="20" height="20"><img width="1" height="1"></td>
</tr>
<tr>
<td id="#666666" bgcolor="#666666" width="20" height="20"><img width="1" height="1"></td>
<td id="#990000" bgcolor="#990000" width="20" height="20"><img width="1" height="1"></td>
<td id="#CC6600" bgcolor="#CC6600" width="20" height="20"><img width="1" height="1"></td>
<td id="#CC9933" bgcolor="#CC9933" width="20" height="20"><img width="1" height="1"></td>
<td id="#999900" bgcolor="#999900" width="20" height="20"><img width="1" height="1"></td>
<td id="#009900" bgcolor="#009900" width="20" height="20"><img width="1" height="1"></td>
<td id="#339999" bgcolor="#339999" width="20" height="20"><img width="1" height="1"></td>
<td id="#3333FF" bgcolor="#3333FF" width="20" height="20"><img width="1" height="1"></td>
<td id="#6600CC" bgcolor="#6600CC" width="20" height="20"><img width="1" height="1"></td>
<td id="#993399" bgcolor="#993399" width="20" height="20"><img width="1" height="1"></td>

</tr>
<tr>
<td id="#333333" bgcolor="#333333" width="20" height="20"><img width="1" height="1"></td>
<td id="#660000" bgcolor="#660000" width="20" height="20"><img width="1" height="1"></td>
<td id="#993300" bgcolor="#993300" width="20" height="20"><img width="1" height="1"></td>
<td id="#996633" bgcolor="#996633" width="20" height="20"><img width="1" height="1"></td>
<td id="#666600" bgcolor="#666600" width="20" height="20"><img width="1" height="1"></td>
<td id="#006600" bgcolor="#006600" width="20" height="20"><img width="1" height="1"></td>
<td id="#336666" bgcolor="#336666" width="20" height="20"><img width="1" height="1"></td>
<td id="#000099" bgcolor="#000099" width="20" height="20"><img width="1" height="1"></td>
<td id="#333399" bgcolor="#333399" width="20" height="20"><img width="1" height="1"></td>
<td id="#663366" bgcolor="#663366" width="20" height="20"><img width="1" height="1"></td>
</tr>
<tr>
<td id="#000000" bgcolor="#000000" width="20" height="20"><img width="1" height="1"></td>
<td id="#330000" bgcolor="#330000" width="20" height="20"><img width="1" height="1"></td>
<td id="#663300" bgcolor="#663300" width="20" height="20"><img width="1" height="1"></td>

<td id="#663333" bgcolor="#663333" width="20" height="20"><img width="1" height="1"></td>
<td id="#333300" bgcolor="#333300" width="20" height="20"><img width="1" height="1"></td>
<td id="#003300" bgcolor="#003300" width="20" height="20"><img width="1" height="1"></td>
<td id="#003333" bgcolor="#003333" width="20" height="20"><img width="1" height="1"></td>
<td id="#000066" bgcolor="#000066" width="20" height="20"><img width="1" height="1"></td>
<td id="#330099" bgcolor="#330099" width="20" height="20"><img width="1" height="1"></td>
<td id="#330033" bgcolor="#330033" width="20" height="20"><img width="1" height="1"></td>
</tr>
</table>
</body>
</html>

