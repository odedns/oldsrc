<% /* @copyright jsp */ %>

<%@ page contentType="text/html; charset=utf-8" %>

<%@ page import="java.util.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>

<% String loc = request.getParameter("locale"); %>
<% String img = request.getParameter("images"); %>
<% String editorName = request.getParameter("editorName"); %>
<% String clrType = request.getParameter("clrType"); %>
<% ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc); %>

<% String langToUse = LocaleHelper.getLocale(loc).getLanguage(); %>
<% String isBidi = request.getParameter("isBidi"); %>
<% String tableDir = "LTR"; if (isBidi.equalsIgnoreCase("true")) tableDir = "RTL"; %>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="STYLESHEET" type="text/css" href="Styles.css">

<title><%= resourceBundle.getString("SelectAColor") %></title>
</head>

<style>
table
{
  color: #000000;
  font-family: sans-serif;
  font-size: 12pt;
  font-weight: bold;
  border-color: #000000;
}
</style>


<script language="javascript" src="script_editor.js"></script>
<script language="javascript">

	var colorArray = new Array ( "#000000", "<%= resourceBundle.getString("rte.color.Black").trim()%>",
	"#FFFFFF", "<%= resourceBundle.getString("rte.color.White").trim()%>",
	"#FF0000", "<%= resourceBundle.getString("rte.color.Red").trim()%>",
	"#00FF00", "<%= resourceBundle.getString("rte.color.Green").trim()%>",
	"#0000FF", "<%= resourceBundle.getString("rte.color.Blue").trim()%>",
	"#FF00FF", "<%= resourceBundle.getString("rte.color.Magenta").trim()%>",
	"#FFFF00", "<%= resourceBundle.getString("rte.color.Yellow").trim()%>",
	"#00FFFF", "<%= resourceBundle.getString("rte.color.Cyan").trim()%>",
	"#800000", "<%= resourceBundle.getString("rte.color.DarkRed").trim()%>",
	"#008000", "<%= resourceBundle.getString("rte.color.DarkGreen").trim()%>",
	"#000080", "<%= resourceBundle.getString("rte.color.DarkBlue").trim()%>",
	"#800080", "<%= resourceBundle.getString("rte.color.DarkMagenta").trim()%>",
	"#808000", "<%= resourceBundle.getString("rte.color.DarkYellow").trim()%>",
	"#008080", "<%= resourceBundle.getString("rte.color.DarkCyan").trim()%>",
	"#A16252", "<%= resourceBundle.getString("rte.color.Darktan").trim()%>",
	"#808080", "<%= resourceBundle.getString("rte.color.Gray").trim()%>",
	"#D0B1A1", "<%= resourceBundle.getString("rte.color.Redgray").trim()%>",
	"#FFEFCE", "<%= resourceBundle.getString("rte.color.Vanilla").trim()%>",
	"#FFFFC2", "<%= resourceBundle.getString("rte.color.Parchment").trim()%>",
	"#FFFFD0", "<%= resourceBundle.getString("rte.color.Ivory").trim()%>",
	"#E0FFBF", "<%= resourceBundle.getString("rte.color.Palegreen").trim()%>",
	"#E0FFDF", "<%= resourceBundle.getString("rte.color.Seamist").trim()%>",
	"#E0FFFF", "<%= resourceBundle.getString("rte.color.Iceblue").trim()%>",
	"#C2EFFF", "<%= resourceBundle.getString("rte.color.Powderblue").trim()%>",
	"#E0F1FF", "<%= resourceBundle.getString("rte.color.Arcticblue").trim()%>",
	"#E0E0FF", "<%= resourceBundle.getString("rte.color.Lilacmist").trim()%>",
	"#E8E0FF", "<%= resourceBundle.getString("rte.color.Purplewash").trim()%>",
	"#F1E0FF", "<%= resourceBundle.getString("rte.color.Violetfrost").trim()%>",
	"#FFE0FF", "<%= resourceBundle.getString("rte.color.Seashell").trim()%>",
	"#FFE0F5", "<%= resourceBundle.getString("rte.color.Rosepearl").trim()%>",
	"#FFE0E6", "<%= resourceBundle.getString("rte.color.Palecherry").trim()%>",
	"#C0C0C0", "<%= resourceBundle.getString("rte.color.LightGray").trim()%>",
	"#FFE1DC", "<%= resourceBundle.getString("rte.color.Blush").trim()%>",
	"#FFE1B0", "<%= resourceBundle.getString("rte.color.Sand").trim()%>",
	"#FFFF7F", "<%= resourceBundle.getString("rte.color.Lightyellow").trim()%>",
	"#F1F1B4", "<%= resourceBundle.getString("rte.color.Honeydew").trim()%>",
	"#C2FF91", "<%= resourceBundle.getString("rte.color.Celery").trim()%>",
	"#C1FFD5", "<%= resourceBundle.getString("rte.color.Paleaqua").trim()%>",
	"#A4FFFF", "<%= resourceBundle.getString("rte.color.Paleblue").trim()%>",
	"#A1E2FF", "<%= resourceBundle.getString("rte.color.Crystalblue").trim()%>",
	"#C0E1FF", "<%= resourceBundle.getString("rte.color.Lightcornflower").trim()%>",
	"#BFBFFF", "<%= resourceBundle.getString("rte.color.Palelavender").trim()%>",
	"#D2BFFF", "<%= resourceBundle.getString("rte.color.Grapefizz").trim()%>",
	"#E1BFFF", "<%= resourceBundle.getString("rte.color.Paleplum").trim()%>",
	"#FFC1FD", "<%= resourceBundle.getString("rte.color.Palepink").trim()%>",
	"#FFC0E4", "<%= resourceBundle.getString("rte.color.Palerose").trim()%>",
	"#FFC0CE", "<%= resourceBundle.getString("rte.color.Rosequartz").trim()%>",
	"#F7F7F7", "<%= resourceBundle.getString("rte.color.5%gray").trim()%>",
	"#FFC0B6", "<%= resourceBundle.getString("rte.color.Redsand").trim()%>",
	"#FFC281", "<%= resourceBundle.getString("rte.color.Buff").trim()%>",
	"#FFFF35", "<%= resourceBundle.getString("rte.color.Lemon").trim()%>",
	"#F1F180", "<%= resourceBundle.getString("rte.color.Palelemonlime").trim()%>",
	"#7FFF7F", "<%= resourceBundle.getString("rte.color.Mintgreen").trim()%>",
	"#82FFCA", "<%= resourceBundle.getString("rte.color.Pastelgreen").trim()%>",
	"#7FFFFF", "<%= resourceBundle.getString("rte.color.Pastelblue").trim()%>",
	"#82E0FF", "<%= resourceBundle.getString("rte.color.Sapphire").trim()%>",
	"#82C0FF", "<%= resourceBundle.getString("rte.color.Cornflower").trim()%>",
	"#9F9FFF", "<%= resourceBundle.getString("rte.color.Lightlavender").trim()%>",
	"#C29FFF", "<%= resourceBundle.getString("rte.color.Palepurple").trim()%>",
	"#E29FFF", "<%= resourceBundle.getString("rte.color.Lightorchid").trim()%>",
	"#FF9FFF", "<%= resourceBundle.getString("rte.color.Pinkorchid").trim()%>",
	"#FF9FCF", "<%= resourceBundle.getString("rte.color.Appleblossom").trim()%>",
	"#FF9FA9", "<%= resourceBundle.getString("rte.color.Pinkcoral").trim()%>",
	"#EFEFEF", "<%= resourceBundle.getString("rte.color.10%gray").trim()%>",
	"#FF9F9F", "<%= resourceBundle.getString("rte.color.Lightsalmon").trim()%>",
	"#FF9F9F", "<%= resourceBundle.getString("rte.color.Lightpeach").trim()%>",
	"#FFE118", "<%= resourceBundle.getString("rte.color.Mustard").trim()%>",
	"#E0E074", "<%= resourceBundle.getString("rte.color.Avocado").trim()%>",
	"#82C168", "<%= resourceBundle.getString("rte.color.Bamboogreen").trim()%>",
	"#42FFC7", "<%= resourceBundle.getString("rte.color.Lightaqua").trim()%>",
	"#42FFFF", "<%= resourceBundle.getString("rte.color.Lightturquoise").trim()%>",
	"#00BFFF", "<%= resourceBundle.getString("rte.color.Lightcerulean").trim()%>",
	"#5291EF", "<%= resourceBundle.getString("rte.color.Azure").trim()%>",
	"#8080FF", "<%= resourceBundle.getString("rte.color.Lavender").trim()%>",
	"#C082FF", "<%= resourceBundle.getString("rte.color.Lightpurple").trim()%>",
	"#E081FF", "<%= resourceBundle.getString("rte.color.Dustyviolet").trim()%>",
	"#FF7FFF", "<%= resourceBundle.getString("rte.color.Pink").trim()%>",
	"#FF82C2", "<%= resourceBundle.getString("rte.color.Pastelpink").trim()%>",
	"#FF82A0", "<%= resourceBundle.getString("rte.color.Pastelred").trim()%>",
	"#E1E1E1", "<%= resourceBundle.getString("rte.color.15%gray").trim()%>",
	"#FF8080", "<%= resourceBundle.getString("rte.color.Salmon").trim()%>",
	"#FF8141", "<%= resourceBundle.getString("rte.color.Peach").trim()%>",
	"#FFBF18", "<%= resourceBundle.getString("rte.color.Gold").trim()%>",
	"#E1E140", "<%= resourceBundle.getString("rte.color.Lemonlime").trim()%>",
	"#41FF32", "<%= resourceBundle.getString("rte.color.Leafgreen").trim()%>",
	"#00FFB2", "<%= resourceBundle.getString("rte.color.Aqua").trim()%>",
	"#00E0E0", "<%= resourceBundle.getString("rte.color.Darkpastelblue").trim()%>",
	"#00A1E0", "<%= resourceBundle.getString("rte.color.Cerulean").trim()%>",
	"#2181FF", "<%= resourceBundle.getString("rte.color.Wedgewood").trim()%>",
	"#6181FF", "<%= resourceBundle.getString("rte.color.Heather").trim()%>",
	"#A160FF", "<%= resourceBundle.getString("rte.color.Purplehaze").trim()%>",
	"#C062FF", "<%= resourceBundle.getString("rte.color.Orchid").trim()%>",
	"#FF5FFF", "<%= resourceBundle.getString("rte.color.Flamingo").trim()%>",
	"#FF60AF", "<%= resourceBundle.getString("rte.color.Cherrypink").trim()%>",
	"#FF6088", "<%= resourceBundle.getString("rte.color.Redcoral").trim()%>",
	"#D2D2D2", "<%= resourceBundle.getString("rte.color.20%gray").trim()%>",
	"#FF4040", "<%= resourceBundle.getString("rte.color.Darksalmon").trim()%>",
	"#FF421E", "<%= resourceBundle.getString("rte.color.Darkpeach").trim()%>",
	"#FF8100", "<%= resourceBundle.getString("rte.color.Orange").trim()%>",
	"#E1E100", "<%= resourceBundle.getString("rte.color.Yellowgreen").trim()%>",
	"#00E100", "<%= resourceBundle.getString("rte.color.Lightgreen").trim()%>",
	"#00E1AD", "<%= resourceBundle.getString("rte.color.Caribbean").trim()%>",
	"#00C1C2", "<%= resourceBundle.getString("rte.color.Darkturquoise").trim()%>",
	"#0082BF", "<%= resourceBundle.getString("rte.color.Darkcerulean").trim()%>",
	"#0080FF", "<%= resourceBundle.getString("rte.color.Manganeseblue").trim()%>",
	"#4181FF", "<%= resourceBundle.getString("rte.color.Lilac").trim()%>",
	"#8242FF", "<%= resourceBundle.getString("rte.color.Purple").trim()%>",
	"#C140FF", "<%= resourceBundle.getString("rte.color.Lightredviolet").trim()%>",
	"#FF42F9", "<%= resourceBundle.getString("rte.color.Lightmagenta").trim()%>",
	"#FF40A0", "<%= resourceBundle.getString("rte.color.Rose").trim()%>",
	"#FF4070", "<%= resourceBundle.getString("rte.color.Carnationpink").trim()%>",
	"#C0C0C0", "<%= resourceBundle.getString("rte.color.25%gray").trim()%>",
	"#FF1F35", "<%= resourceBundle.getString("rte.color.Watermelon").trim()%>",
	"#FF1F10", "<%= resourceBundle.getString("rte.color.Tangerine").trim()%>",
	"#E26200", "<%= resourceBundle.getString("rte.color.Darkorange").trim()%>",
	"#BFBF00", "<%= resourceBundle.getString("rte.color.Chartreuse").trim()%>",
	"#00C200", "<%= resourceBundle.getString("rte.color.Green").trim()%>",
	"#00C196", "<%= resourceBundle.getString("rte.color.Teal").trim()%>",
	"#3F8080", "<%= resourceBundle.getString("rte.color.Aztecblue").trim()%>",
	"#4181C0", "<%= resourceBundle.getString("rte.color.Lightslateblue").trim()%>",
	"#0062E1", "<%= resourceBundle.getString("rte.color.Mediumblue").trim()%>",
	"#4141FF", "<%= resourceBundle.getString("rte.color.Darklilac").trim()%>",
	"#4200FF", "<%= resourceBundle.getString("rte.color.Royalpurple").trim()%>",
	"#C200FF", "<%= resourceBundle.getString("rte.color.Fuchsia").trim()%>",
	"#FF22FF", "<%= resourceBundle.getString("rte.color.Confettipink").trim()%>",
	"#F52B97", "<%= resourceBundle.getString("rte.color.Paleburgundy").trim()%>",
	"#FF2259", "<%= resourceBundle.getString("rte.color.Strawberry").trim()%>",
	"#B2B2B2", "<%= resourceBundle.getString("rte.color.30%gray").trim()%>",
	"#E01F25", "<%= resourceBundle.getString("rte.color.Rouge").trim()%>",
	"#E12000", "<%= resourceBundle.getString("rte.color.Burntorange").trim()%>",
	"#82823F", "<%= resourceBundle.getString("rte.color.Olivegray").trim()%>",
	"#A1A100", "<%= resourceBundle.getString("rte.color.Lightolive").trim()%>",
	"#00A000", "<%= resourceBundle.getString("rte.color.Kellygreen").trim()%>",
	"#009F82", "<%= resourceBundle.getString("rte.color.Seagreen").trim()%>",
	"#006062", "<%= resourceBundle.getString("rte.color.Spruce").trim()%>",
	"#0060A0", "<%= resourceBundle.getString("rte.color.Dustyblue").trim()%>",
	"#0041C2", "<%= resourceBundle.getString("rte.color.Blueberry").trim()%>",
	"#0021BF", "<%= resourceBundle.getString("rte.color.Violet").trim()%>",
	"#4100C2", "<%= resourceBundle.getString("rte.color.Deeppurple").trim()%>",
	"#8100FF", "<%= resourceBundle.getString("rte.color.Redviolet").trim()%>",
	"#E000E0", "<%= resourceBundle.getString("rte.color.Hotpink").trim()%>",
	"#FF0080", "<%= resourceBundle.getString("rte.color.Darkrose").trim()%>",
	"#FF0041", "<%= resourceBundle.getString("rte.color.Poppyred").trim()%>",
	"#A2A2A2", "<%= resourceBundle.getString("rte.color.35%gray").trim()%>",
	"#C20000", "<%= resourceBundle.getString("rte.color.Crimson").trim()%>",
	"#ff000e", "<%= resourceBundle.getString("rte.color.Safari").trim()%>",
	"#BF4100", "<%= resourceBundle.getString("rte.color.Lightbrown").trim()%>",
	"#80803F", "<%= resourceBundle.getString("rte.color.Olive").trim()%>",
	"#3F803F", "<%= resourceBundle.getString("rte.color.Darkgreen").trim()%>",
	"#008250", "<%= resourceBundle.getString("rte.color.Darkteal").trim()%>",
	"#37605E", "<%= resourceBundle.getString("rte.color.Sprucegray").trim()%>",
	"#004080", "<%= resourceBundle.getString("rte.color.Slateblue").trim()%>",
	"#001FE2", "<%= resourceBundle.getString("rte.color.Navyblue").trim()%>",
	"#4040C2", "<%= resourceBundle.getString("rte.color.Blueviolet").trim()%>",
	"#4000A2", "<%= resourceBundle.getString("rte.color.Amethyst").trim()%>",
	"#6000A1", "<%= resourceBundle.getString("rte.color.Darkredviolet").trim()%>",
	"#A1009F", "<%= resourceBundle.getString("rte.color.Deepmagenta").trim()%>",
	"#DF007F", "<%= resourceBundle.getString("rte.color.Lightburgundy").trim()%>",
	"#C20041", "<%= resourceBundle.getString("rte.color.Cherryred").trim()%>",
	"#8F8F8F", "<%= resourceBundle.getString("rte.color.40%gray").trim()%>",
	"#A00000", "<%= resourceBundle.getString("rte.color.arkcrimson").trim()%>",
	"#E10000", "<%= resourceBundle.getString("rte.color.Darkred").trim()%>",
	"#A13F00", "<%= resourceBundle.getString("rte.color.Hazelnut").trim()%>",
	"#626200", "<%= resourceBundle.getString("rte.color.Darkolive").trim()%>",
	"#006000", "<%= resourceBundle.getString("rte.color.Emerald").trim()%>",
	"#00603C", "<%= resourceBundle.getString("rte.color.Malachite").trim()%>",
	"#004041", "<%= resourceBundle.getString("rte.color.Darkspruce").trim()%>",
	"#002F80", "<%= resourceBundle.getString("rte.color.Steelblue").trim()%>",
	"#424282", "<%= resourceBundle.getString("rte.color.Atlanticgray").trim()%>",
	"#2020A0", "<%= resourceBundle.getString("rte.color.Iris").trim()%>",
	"#2200A1", "<%= resourceBundle.getString("rte.color.Grape").trim()%>",
	"#400080", "<%= resourceBundle.getString("rte.color.Plum").trim()%>",
	"#602162", "<%= resourceBundle.getString("rte.color.Plumgray").trim()%>",
	"#C0007F", "<%= resourceBundle.getString("rte.color.Burgundy").trim()%>",
	"#9F000F", "<%= resourceBundle.getString("rte.color.Cranberry").trim()%>",
	"#5F5F5F", "<%= resourceBundle.getString("rte.color.60%gray").trim()%>",
	"#600000", "<%= resourceBundle.getString("rte.color.Mahogany").trim()%>",
	"#C21212", "<%= resourceBundle.getString("rte.color.Brick").trim()%>",
	"#824200", "<%= resourceBundle.getString("rte.color.Darkbrown").trim()%>",
	"#424200", "<%= resourceBundle.getString("rte.color.Deepolive").trim()%>",
	"#004200", "<%= resourceBundle.getString("rte.color.Darkemerald").trim()%>",
	"#004023", "<%= resourceBundle.getString("rte.color.Evergreen").trim()%>",
	"#00323F", "<%= resourceBundle.getString("rte.color.Balticblue").trim()%>",
	"#002060", "<%= resourceBundle.getString("rte.color.Bluedenim").trim()%>",
	"#0020C2", "<%= resourceBundle.getString("rte.color.Cobaltblue").trim()%>",
	"#2222C0", "<%= resourceBundle.getString("rte.color.Darkiris").trim()%>",
	"#624181", "<%= resourceBundle.getString("rte.color.Purplegray").trim()%>",
	"#1F007F", "<%= resourceBundle.getString("rte.color.Darkplum").trim()%>",
	"#622152", "<%= resourceBundle.getString("rte.color.Burgundygray").trim()%>",
	"#820040", "<%= resourceBundle.getString("rte.color.Darkburgundy").trim()%>",
	"#813F62", "<%= resourceBundle.getString("rte.color.Darkrosegray").trim()%>",
	"#4F4F4F", "<%= resourceBundle.getString("rte.color.70%gray").trim()%>",
	"#400000", "<%= resourceBundle.getString("rte.color.Chestnut").trim()%>",
	"#A11F12", "<%= resourceBundle.getString("rte.color.Terracotta").trim()%>",
	"#604200", "<%= resourceBundle.getString("rte.color.Umber").trim()%>",
	"#212100", "<%= resourceBundle.getString("rte.color.Amazon").trim()%>",
	"#002100", "<%= resourceBundle.getString("rte.color.Peacockgreen").trim()%>",
	"#00201F", "<%= resourceBundle.getString("rte.color.Pine").trim()%>",
	"#002041", "<%= resourceBundle.getString("rte.color.Metallicblue").trim()%>",
	"#00204F", "<%= resourceBundle.getString("rte.color.Darkslateblue").trim()%>",
	"#0000E0", "<%= resourceBundle.getString("rte.color.Royalblue").trim()%>",
	"#0000A1", "<%= resourceBundle.getString("rte.color.Lapis").trim()%>",
	"#000061", "<%= resourceBundle.getString("rte.color.Darkgrape").trim()%>",
	"#1F0062", "<%= resourceBundle.getString("rte.color.Aubergine").trim()%>",
	"#40005F", "<%= resourceBundle.getString("rte.color.Darkplumred").trim()%>",
	"#620042", "<%= resourceBundle.getString("rte.color.Raspberry").trim()%>",
	"#620012", "<%= resourceBundle.getString("rte.color.Deepscarlet").trim()%>",
	"#4F4F4F", "<%= resourceBundle.getString("rte.color.80%gray").trim()%>",
	"#7F604F", "<%= resourceBundle.getString("rte.color.Darkredgray").trim()%>",
	"#E0A175", "<%= resourceBundle.getString("rte.color.Tan").trim()%>",
	"#D2B06A", "<%= resourceBundle.getString("rte.color.Khaki").trim()%>",
	"#C0C27C", "<%= resourceBundle.getString("rte.color.Putty").trim()%>",
	"#3F621F", "<%= resourceBundle.getString("rte.color.Jade").trim()%>",
	"#81C097", "<%= resourceBundle.getString("rte.color.Greengray").trim()%>",
	"#7FC2BC", "<%= resourceBundle.getString("rte.color.Balticgray").trim()%>",
	"#71B2CF", "<%= resourceBundle.getString("rte.color.Bluegray").trim()%>",
	"#B1B1D2", "<%= resourceBundle.getString("rte.color.Raincloud").trim()%>",
	"#9F9FE0", "<%= resourceBundle.getString("rte.color.Lilacgray").trim()%>",
	"#C0A1E0", "<%= resourceBundle.getString("rte.color.Lightpurplegray").trim()%>",
	"#E29FDE", "<%= resourceBundle.getString("rte.color.Lightmauve").trim()%>",
	"#EF91EB", "<%= resourceBundle.getString("rte.color.Lightplumgray").trim()%>",
	"#E29FC8", "<%= resourceBundle.getString("rte.color.Lightburgundygray").trim()%>",
	"#F18FBC", "<%= resourceBundle.getString("rte.color.Rosegray").trim()%>",
	"#603181", "<%= resourceBundle.getString("rte.color.Mauve").trim()%>");



// Setting the title on the element as title="javascript:colorArray[idx];" works for WindowEyes (reads the value
// of colorArray[idx] but on mouse over the javascript is NOT executed and "javascript:colorArray[idx]" is displayed.
// Use this to set the title from the array (easier than entering title entries for already exising areas
function setMapInfo() {
	var mapAreas = document.getElementById("colorMap").areas;
	var cLen = mapAreas.length;
	var colorArrayIdx = 1;
	for (var i=0; i<cLen; i++) {
		if (mapAreas[i].tagName == "AREA") {
			mapAreas[i].title=colorArray[colorArrayIdx];
			mapAreas[i].id=i; //colorArray[(colorArrayIdx-1)];
			//alert("mapAreas[" + i+"].title "+ mapAreas[i].title + " map.Areas["+i+"].id " + mapAreas[i].id);
			colorArrayIdx +=2;
		}

	}
}


var oldElem;
function IBM_RTE_selectColor(clrValue) {

    parent.close();

    var clrType = "<%= clrType %>";

    this.opener.IBM_RTE_getDocument("<%= editorName %>").execCommand(clrType, false, clrValue);
    if (oldElem) {
        oldElem.style.borderWidth = 1;
    }
    //event.srcElement.style.borderWidth = 2;
    //oldElem = event.srcElement;
}


function showColor(clrValue, clrName) {

    var iColor = document.getElementById('colorInput');
    iColor.value=clrName + " - " + clrValue;

    var colorBlock = document.getElementById('colorBlock');
    colorBlock.style.backgroundColor=clrValue;
}

</script>


<body dir="<%= tableDir %>" lang="<%= langToUse %>" leftMargin="0" rightMargin="0" topMargin="3" bottomMargin="0" marginheight="0" marginwidth="0" onload="setMapInfo();">

 <table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
   <tr>
       <td width="100%">
           <table cellpadding="0" cellspacing="0">
             <tr>
                 <td>&nbsp;&nbsp;<label for="colorInput" class="portlet-form-field-label"><%= resourceBundle.getString("SelectedColorValue") %></label></td>
             </tr>
             <tr>      
                 <td>&nbsp;&nbsp;<input type="text" id="colorInput" readonly=true tabindex="30" size="50">&nbsp;&nbsp;</td>
                 <td><span id="colorBlock">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></td>
             </tr>
           </table>
       </td>
   </tr>

   <tr>
      <td width="100%" align="middle">
           <table cellpadding="0" cellspacing="0">
             <tr><td>



  <img id="ColorMapImage" align="middle" src="<%= img %>/ColorPicker.gif" border="0" width="300" height="280" usemap="#colors" style="position:relative">

    <MAP name="colors"  id="colorMap">

<!-- 1st row -->

	<AREA shape="rect" id="row1"
		href="javascript:IBM_RTE_selectColor(colorArray[0]);"  coords="2,4,18,20" tabindex="101" 
            onfocus="showColor(colorArray[0], colorArray[1]);" onmouseover="showColor(colorArray[0], colorArray[1]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[2]);"  coords="20,4,36,20" tabindex="102" 
            onfocus="showColor(colorArray[2], colorArray[3]);" onmouseover="showColor(colorArray[2], colorArray[3]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[4]);"  coords="40,4,56,20" tabindex="103" 
            onfocus="showColor(colorArray[4], colorArray[5]);" onmouseover="showColor(colorArray[4], colorArray[5]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[6]);"  coords="58,4,74,20" tabindex="104" 
            onfocus="showColor(colorArray[6], colorArray[7]);" onmouseover="showColor(colorArray[6], colorArray[7]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[8]);"  coords="78,4,94,20" tabindex="105" 
            onfocus="showColor(colorArray[8], colorArray[9]);" onmouseover="showColor(colorArray[8], colorArray[9]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[10]);"  coords="96,4,112,20" tabindex="106" 
            onfocus="showColor(colorArray[10], colorArray[11]);" onmouseover="showColor(colorArray[10], colorArray[11]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[12]);"  coords="114,4,130,20" tabindex="107" 
            onfocus="showColor(colorArray[12], colorArray[13]);" onmouseover="showColor(colorArray[12], colorArray[13]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[14]);"  coords="132,4,148,20" tabindex="108" 
            onfocus="showColor(colorArray[14], colorArray[15]);" onmouseover="showColor(colorArray[14], colorArray[15]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[16]);"  coords="150,4,166,20" tabindex="109" 
            onfocus="showColor(colorArray[16], colorArray[17]);" onmouseover="showColor(colorArray[16], colorArray[17]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[18]);"  coords="170,4,186,20" tabindex="110" 
            onfocus="showColor(colorArray[18], colorArray[19]);" onmouseover="showColor(colorArray[18], colorArray[19]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[20]);"  coords="188,4,204,20" tabindex="111" 
            onfocus="showColor(colorArray[20], colorArray[21]);" onmouseover="showColor(colorArray[20], colorArray[21]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[22]);"  coords="208,4,224,20" tabindex="112" 
            onfocus="showColor(colorArray[22], colorArray[23]);" onmouseover="showColor(colorArray[22], colorArray[23]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[24]);"  coords="226,4,242,20" tabindex="113" 
            onfocus="showColor(colorArray[24], colorArray[25]);" onmouseover="showColor(colorArray[24], colorArray[25]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[26]);"  coords="244,4,260,20" tabindex="114" 
            onfocus="showColor(colorArray[26], colorArray[27]);" onmouseover="showColor(colorArray[26], colorArray[27]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[28]);"  coords="264,4,280,20" tabindex="115" 
            onfocus="showColor(colorArray[28], colorArray[29]);" onmouseover="showColor(colorArray[28], colorArray[29]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[30]);"  coords="282,4,298,20" tabindex="116" 
            onfocus="showColor(colorArray[30], colorArray[31]);" onmouseover="showColor(colorArray[30], colorArray[31]);" >

<!-- 2nd row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[32]);" coords="2,24,18,40" tabindex="117" 
            onfocus="showColor(colorArray[32], colorArray[33]);" onmouseover="showColor(colorArray[32], colorArray[33]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[34]);" coords="20,24,36,40" tabindex="118" 
            onfocus="showColor(colorArray[34], colorArray[35]);" onmouseover="showColor(colorArray[34], colorArray[35]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[36]);" coords="40,24,56,40" tabindex="119" 
            onfocus="showColor(colorArray[36], colorArray[37]);" onmouseover="showColor(colorArray[36], colorArray[37]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[38]);" coords="58,24,74,40" tabindex="120" 
            onfocus="showColor(colorArray[38], colorArray[39]);" onmouseover="showColor(colorArray[38], colorArray[39]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[40]);" coords="78,24,94,40" tabindex="121" 
            onfocus="showColor(colorArray[40], colorArray[41]);" onmouseover="showColor(colorArray[40], colorArray[41]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[42]);" coords="96,24,112,40" tabindex="122" 
            onfocus="showColor(colorArray[42], colorArray[43]);" onmouseover="showColor(colorArray[42], colorArray[43]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[44]);" coords="114,24,130,40" tabindex="123" 
            onfocus="showColor(colorArray[44], colorArray[45]);" onmouseover="showColor(colorArray[44], colorArray[45]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[46]);" coords="132,24,148,40" tabindex="124" 
            onfocus="showColor(colorArray[46], colorArray[47]);" onmouseover="showColor(colorArray[46], colorArray[47]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[48]);" coords="150,24,166,40" tabindex="125" 
            onfocus="showColor(colorArray[48], colorArray[49]);" onmouseover="showColor(colorArray[48], colorArray[49]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[50]);" coords="170,24,186,40" tabindex="126" 
            onfocus="showColor(colorArray[50], colorArray[51]);" onmouseover="showColor(colorArray[50], colorArray[51]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[52]);" coords="188,24,204,40" tabindex="127" 
            onfocus="showColor(colorArray[52], colorArray[53]);" onmouseover="showColor(colorArray[52], colorArray[53]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[54]);" coords="208,24,224,40" tabindex="128" 
            onfocus="showColor(colorArray[54], colorArray[55]);" onmouseover="showColor(colorArray[54], colorArray[55]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[56]);" coords="226,24,242,40" tabindex="129" 
            onfocus="showColor(colorArray[56], colorArray[57]);" onmouseover="showColor(colorArray[56], colorArray[57]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[58]);" coords="244,24,260,40" tabindex="130" 
            onfocus="showColor(colorArray[58], colorArray[59]);" onmouseover="showColor(colorArray[58], colorArray[59]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[60]);" coords="264,24,280,40" tabindex="131" 
            onfocus="showColor(colorArray[60], colorArray[61]);" onmouseover="showColor(colorArray[60], colorArray[61]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[62]);" coords="282,24,298,40" tabindex="132" 
            onfocus="showColor(colorArray[62], colorArray[63]);" onmouseover="showColor(colorArray[62], colorArray[63]);" >


<!-- 3rd row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[64]);" coords="2,44,18,60" tabindex="133" 
            onfocus="showColor(colorArray[64], colorArray[65]);" onmouseover="showColor(colorArray[64], colorArray[65]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[66]);" coords="20,44,36,60" tabindex="134" 
            onfocus="showColor(colorArray[66], colorArray[67]);" onmouseover="showColor(colorArray[66], colorArray[67]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[68]);" coords="40,44,56,60" tabindex="135" 
            onfocus="showColor(colorArray[68], colorArray[69]);" onmouseover="showColor(colorArray[68], colorArray[69]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[70]);" coords="58,44,74,60" tabindex="136" 
            onfocus="showColor(colorArray[70], colorArray[71]);" onmouseover="showColor(colorArray[70], colorArray[71]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[72]);" coords="78,44,94,60" tabindex="137" 
            onfocus="showColor(colorArray[72], colorArray[73]);" onmouseover="showColor(colorArray[72], colorArray[73]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[74]);" coords="96,44,112,60" tabindex="138" 
            onfocus="showColor(colorArray[74], colorArray[75]);" onmouseover="showColor(colorArray[74], colorArray[75]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[76]);" coords="114,44,130,60" tabindex="139" 
            onfocus="showColor(colorArray[76], colorArray[77]);" onmouseover="showColor(colorArray[76], colorArray[77]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[78]);" coords="132,44,148,60" tabindex="140" 
            onfocus="showColor(colorArray[78], colorArray[79]);" onmouseover="showColor(colorArray[78], colorArray[79]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[80]);" coords="150,44,166,60" tabindex="141" 
            onfocus="showColor(colorArray[80], colorArray[81]);" onmouseover="showColor(colorArray[80], colorArray[81]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[82]);" coords="170,44,186,60" tabindex="142" 
            onfocus="showColor(colorArray[82], colorArray[83]);" onmouseover="showColor(colorArray[82], colorArray[83]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[84]);" coords="188,44,204,60" tabindex="143" 
            onfocus="showColor(colorArray[84], colorArray[85]);" onmouseover="showColor(colorArray[84], colorArray[85]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[86]);" coords="208,44,224,60" tabindex="144" 
            onfocus="showColor(colorArray[86], colorArray[87]);" onmouseover="showColor(colorArray[86], colorArray[87]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[88]);" coords="226,44,242,60" tabindex="145" 
            onfocus="showColor(colorArray[88], colorArray[89]);" onmouseover="showColor(colorArray[88], colorArray[89]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[90]);" coords="244,44,260,60" tabindex="146" 
            onfocus="showColor(colorArray[90], colorArray[91]);" onmouseover="showColor(colorArray[90], colorArray[91]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[92]);" coords="264,44,280,60" tabindex="147" 
            onfocus="showColor(colorArray[92], colorArray[93]);" onmouseover="showColor(colorArray[92], colorArray[93]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[94]);" coords="282,44,298,60" tabindex="148" 
            onfocus="showColor(colorArray[94], colorArray[95]);" onmouseover="showColor(colorArray[94], colorArray[95]);" >



<!-- 4th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[96]);" coords="2,64,18,80" tabindex="149" 
            onfocus="showColor(colorArray[96], colorArray[97]);" onmouseover="showColor(colorArray[96], colorArray[97]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[98]);" coords="20,64,36,80" tabindex="150" 
            onfocus="showColor(colorArray[98], colorArray[99]);" onmouseover="showColor(colorArray[98], colorArray[99]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[100]);" coords="40,64,56,80" tabindex="151" 
            onfocus="showColor(colorArray[100], colorArray[101]);" onmouseover="showColor(colorArray[100], colorArray[101]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[102]);" coords="58,64,74,80" tabindex="152" 
            onfocus="showColor(colorArray[102], colorArray[103]);" onmouseover="showColor(colorArray[102], colorArray[103]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[104]);" coords="78,64,94,80" tabindex="153" 
            onfocus="showColor(colorArray[104], colorArray[105]);" onmouseover="showColor(colorArray[104], colorArray[105]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[106]);" coords="96,64,112,80" tabindex="154" 
            onfocus="showColor(colorArray[106], colorArray[107]);" onmouseover="showColor(colorArray[106], colorArray[107]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[108]);" coords="114,64,130,80" tabindex="155" 
            onfocus="showColor(colorArray[108], colorArray[109]);" onmouseover="showColor(colorArray[108], colorArray[109]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[110]);" coords="132,64,148,80" tabindex="156" 
            onfocus="showColor(colorArray[110], colorArray[111]);" onmouseover="showColor(colorArray[110], colorArray[111]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[112]);" coords="150,64,166,80" tabindex="157" 
            onfocus="showColor(colorArray[112], colorArray[113]);" onmouseover="showColor(colorArray[112], colorArray[113]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[114]);" coords="170,64,186,80" tabindex="158" 
            onfocus="showColor(colorArray[114], colorArray[115]);" onmouseover="showColor(colorArray[114], colorArray[115]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[116]);" coords="188,64,204,80" tabindex="159" 
            onfocus="showColor(colorArray[116], colorArray[117]);" onmouseover="showColor(colorArray[116], colorArray[117]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[118]);" coords="208,64,224,80" tabindex="160" 
            onfocus="showColor(colorArray[118], colorArray[119]);" onmouseover="showColor(colorArray[118], colorArray[119]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[120]);" coords="226,64,242,80" tabindex="161" 
            onfocus="showColor(colorArray[120], colorArray[121]);" onmouseover="showColor(colorArray[120], colorArray[121]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[122]);" coords="244,64,260,80" tabindex="162" 
            onfocus="showColor(colorArray[122], colorArray[123]);" onmouseover="showColor(colorArray[122], colorArray[123]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[124]);" coords="264,64,280,80" tabindex="163" 
            onfocus="showColor(colorArray[124], colorArray[125]);" onmouseover="showColor(colorArray[124], colorArray[125]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[126]);" coords="282,64,298,80" tabindex="164" 
            onfocus="showColor(colorArray[126], colorArray[127]);" onmouseover="showColor(colorArray[126], colorArray[127]);" >



<!-- 5th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[128]);" coords="2,84,18,100" tabindex="165" 
            onfocus="showColor(colorArray[128], colorArray[129]);" onmouseover="showColor(colorArray[128], colorArray[129]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[130]);" coords="20,84,36,100" tabindex="166" 
            onfocus="showColor(colorArray[130], colorArray[131]);" onmouseover="showColor(colorArray[130], colorArray[131]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[132]);" coords="40,84,56,100" tabindex="167" 
            onfocus="showColor(colorArray[132], colorArray[133]);" onmouseover="showColor(colorArray[132], colorArray[133]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[134]);"  coords="58,84,74,100" tabindex="168" 
            onfocus="showColor(colorArray[134], colorArray[135]);" onmouseover="showColor(colorArray[134], colorArray[135]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[136]);" coords="78,84,94,100" tabindex="169" 
            onfocus="showColor(colorArray[136], colorArray[137]);" onmouseover="showColor(colorArray[136], colorArray[137]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[138]);" coords="96,84,112,100" tabindex="170" 
            onfocus="showColor(colorArray[138], colorArray[139]);" onmouseover="showColor(colorArray[138], colorArray[139]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[140]);"  coords="114,84,130,100" tabindex="171" 
            onfocus="showColor(colorArray[140], colorArray[141]);" onmouseover="showColor(colorArray[140], colorArray[141]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[142]);" coords="132,84,148,100" tabindex="172" 
            onfocus="showColor(colorArray[142], colorArray[143]);" onmouseover="showColor(colorArray[142], colorArray[143]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[144]);" coords="150,84,166,100" tabindex="173" 
            onfocus="showColor(colorArray[144], colorArray[145]);" onmouseover="showColor(colorArray[144], colorArray[145]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[146]);" coords="170,84,186,100" tabindex="174" 
            onfocus="showColor(colorArray[146], colorArray[147]);" onmouseover="showColor(colorArray[146], colorArray[147]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[148]);" coords="188,84,204,100" tabindex="175" 
            onfocus="showColor(colorArray[148], colorArray[149]);" onmouseover="showColor(colorArray[148], colorArray[149]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[150]);" coords="208,84,224,100" tabindex="176" 
            onfocus="showColor(colorArray[150], colorArray[151]);" onmouseover="showColor(colorArray[150], colorArray[151]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[152]);" coords="226,84,242,100" tabindex="177" 
            onfocus="showColor(colorArray[152], colorArray[153]);" onmouseover="showColor(colorArray[152], colorArray[153]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[154]);" coords="244,84,260,100" tabindex="178" 
            onfocus="showColor(colorArray[154], colorArray[155]);" onmouseover="showColor(colorArray[154], colorArray[155]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[156]);" coords="264,84,280,100" tabindex="179" 
            onfocus="showColor(colorArray[156], colorArray[157]);" onmouseover="showColor(colorArray[156], colorArray[157]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[158]);" coords="282,84,298,100" tabindex="180" 
            onfocus="showColor(colorArray[158], colorArray[159]);" onmouseover="showColor(colorArray[158], colorArray[159]);" >


<!-- 6th row -->

	<AREA shape="rect" id="row1"
		href="javascript:IBM_RTE_selectColor(colorArray[160]);" coords="2,104,18,120" tabindex="181" 
            onfocus="showColor(colorArray[160], colorArray[161]);" onmouseover="showColor(colorArray[160], colorArray[161]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[162]);" coords="20,104,36,120" tabindex="182" 
            onfocus="showColor(colorArray[162], colorArray[163]);" onmouseover="showColor(colorArray[162], colorArray[163]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[164]);" coords="40,104,56,120" tabindex="183" 
            onfocus="showColor(colorArray[164], colorArray[165]);" onmouseover="showColor(colorArray[164], colorArray[165]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[166]);" coords="58,104,74,120" tabindex="184" 
            onfocus="showColor(colorArray[166], colorArray[167]);" onmouseover="showColor(colorArray[166], colorArray[167]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[168]);" coords="78,104,94,120" tabindex="185" 
            onfocus="showColor(colorArray[168], colorArray[169]);" onmouseover="showColor(colorArray[168], colorArray[169]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[170]);" coords="96,104,112,120" tabindex="186" 
            onfocus="showColor(colorArray[170], colorArray[171]);" onmouseover="showColor(colorArray[170], colorArray[171]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[172]);" coords="114,104,130,120" tabindex="187" 
            onfocus="showColor(colorArray[172], colorArray[173]);" onmouseover="showColor(colorArray[172], colorArray[173]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[174]);" coords="132,104,148,120" tabindex="188" 
            onfocus="showColor(colorArray[174], colorArray[175]);" onmouseover="showColor(colorArray[174], colorArray[175]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[176]);" coords="150,104,166,120" tabindex="189" 
            onfocus="showColor(colorArray[176], colorArray[177]);" onmouseover="showColor(colorArray[176], colorArray[177]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[178]);" coords="170,104,186,120" tabindex="190" 
            onfocus="showColor(colorArray[178], colorArray[179]);" onmouseover="showColor(colorArray[178], colorArray[179]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[180]);" coords="188,104,204,120" tabindex="191" 
            onfocus="showColor(colorArray[180], colorArray[181]);" onmouseover="showColor(colorArray[180], colorArray[181]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[182]);" coords="208,104,224,120" tabindex="192" 
            onfocus="showColor(colorArray[182], colorArray[183]);" onmouseover="showColor(colorArray[182], colorArray[183]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[184]);" coords="226,104,242,120" tabindex="193" 
            onfocus="showColor(colorArray[184], colorArray[185]);" onmouseover="showColor(colorArray[184], colorArray[185]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[186]);" coords="244,104,260,120" tabindex="194" 
            onfocus="showColor(colorArray[186], colorArray[187]);" onmouseover="showColor(colorArray[186], colorArray[187]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[188]);" coords="264,104,280,120" tabindex="195" 
            onfocus="showColor(colorArray[188], colorArray[189]);" onmouseover="showColor(colorArray[188], colorArray[189]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[190]);" coords="282,104,298,120" tabindex="196" 
            onfocus="showColor(colorArray[190], colorArray[191]);" onmouseover="showColor(colorArray[190], colorArray[191]);" >



<!-- 7th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[192]);" coords="2,124,18,140" tabindex="197" 
            onfocus="showColor(colorArray[192], colorArray[193]);" onmouseover="showColor(colorArray[192], colorArray[193]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[194]);" coords="20,124,36,140" tabindex="198" 
            onfocus="showColor(colorArray[194], colorArray[195]);" onmouseover="showColor(colorArray[194], colorArray[195]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[196]);" coords="40,124,56,140" tabindex="199" 
            onfocus="showColor(colorArray[196], colorArray[197]);" onmouseover="showColor(colorArray[196], colorArray[197]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[198]);" coords="58,124,74,140" tabindex="200" 
            onfocus="showColor(colorArray[198], colorArray[199]);" onmouseover="showColor(colorArray[198], colorArray[199]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[200]);" coords="78,124,94,140" tabindex="201" 
            onfocus="showColor(colorArray[200], colorArray[201]);" onmouseover="showColor(colorArray[200], colorArray[201]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[202]);" coords="96,124,112,140" tabindex="202" 
            onfocus="showColor(colorArray[202], colorArray[203]);" onmouseover="showColor(colorArray[202], colorArray[203]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[204]);" coords="114,124,130,140" tabindex="203" 
            onfocus="showColor(colorArray[204], colorArray[205]);" onmouseover="showColor(colorArray[204], colorArray[205]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[206]);" coords="132,124,148,140" tabindex="204" 
            onfocus="showColor(colorArray[206], colorArray[207]);" onmouseover="showColor(colorArray[206], colorArray[207]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[208]);" coords="150,124,166,140" tabindex="205" 
            onfocus="showColor(colorArray[208], colorArray[209]);" onmouseover="showColor(colorArray[208], colorArray[209]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[210]);" coords="170,124,186,140" tabindex="206" 
            onfocus="showColor(colorArray[210], colorArray[211]);" onmouseover="showColor(colorArray[210], colorArray[211]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[212]);" coords="188,124,204,140" tabindex="207" 
            onfocus="showColor(colorArray[212], colorArray[213]);" onmouseover="showColor(colorArray[212], colorArray[213]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[214]);" coords="208,124,224,140" tabindex="208" 
            onfocus="showColor(colorArray[214], colorArray[215]);" onmouseover="showColor(colorArray[214], colorArray[215]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[216]);"  coords="226,124,242,140" tabindex="209" 
            onfocus="showColor(colorArray[216], colorArray[217]);" onmouseover="showColor(colorArray[216], colorArray[217]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[218]);"  coords="244,124,260,140" tabindex="210" 
            onfocus="showColor(colorArray[218], colorArray[219]);" onmouseover="showColor(colorArray[218], colorArray[219]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[220]);"  coords="264,124,280,140" tabindex="211" 
            onfocus="showColor(colorArray[220], colorArray[221]);" onmouseover="showColor(colorArray[220], colorArray[221]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[222]);"  coords="282,124,298,140" tabindex="212" 
            onfocus="showColor(colorArray[222], colorArray[223]);" onmouseover="showColor(colorArray[222], colorArray[223]);" >



<!-- 8th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[224]);"  coords="2,142,18,158" tabindex="213" 
            onfocus="showColor(colorArray[224], colorArray[225]);" onmouseover="showColor(colorArray[224], colorArray[225]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[226]);"  coords="20,142,36,158" tabindex="214" 
            onfocus="showColor(colorArray[226], colorArray[227]);" onmouseover="showColor(colorArray[226], colorArray[227]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[228]);"  coords="40,142,56,158" tabindex="215" 
            onfocus="showColor(colorArray[228], colorArray[229]);" onmouseover="showColor(colorArray[228], colorArray[229]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[230]);"  coords="58,142,74,158" tabindex="216" 
            onfocus="showColor(colorArray[230], colorArray[231]);" onmouseover="showColor(colorArray[230], colorArray[231]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[232]);"  coords="78,142,94,158" tabindex="217" 
            onfocus="showColor(colorArray[232], colorArray[233]);" onmouseover="showColor(colorArray[232], colorArray[233]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[234]);"  coords="96,142,112,158" tabindex="218" 
            onfocus="showColor(colorArray[234], colorArray[235]);" onmouseover="showColor(colorArray[234], colorArray[235]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[236]);"  coords="114,142,130,158" tabindex="219" 
            onfocus="showColor(colorArray[236], colorArray[237]);" onmouseover="showColor(colorArray[236], colorArray[237]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[238]);"  coords="132,142,148,158" tabindex="220" 
            onfocus="showColor(colorArray[238], colorArray[239]);" onmouseover="showColor(colorArray[238], colorArray[239]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[240]);"  coords="150,142,166,158" tabindex="221" 
            onfocus="showColor(colorArray[240], colorArray[241]);" onmouseover="showColor(colorArray[240], colorArray[241]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[242]);"  coords="170,142,186,158" tabindex="222" 
            onfocus="showColor(colorArray[242], colorArray[243]);" onmouseover="showColor(colorArray[242], colorArray[243]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[244]);"  coords="188,142,204,158" tabindex="223" 
            onfocus="showColor(colorArray[244], colorArray[245]);" onmouseover="showColor(colorArray[244], colorArray[245]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[246]);"  coords="208,142,224,158" tabindex="224" 
            onfocus="showColor(colorArray[246], colorArray[247]);" onmouseover="showColor(colorArray[246], colorArray[247]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[248]);"  coords="226,142,242,158" tabindex="225" 
            onfocus="showColor(colorArray[248], colorArray[249]);" onmouseover="showColor(colorArray[248], colorArray[249]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[250]);"  coords="244,142,260,158" tabindex="226" 
            onfocus="showColor(colorArray[250], colorArray[251]);" onmouseover="showColor(colorArray[250], colorArray[251]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[252]);"  coords="264,142,280,158" tabindex="227" 
            onfocus="showColor(colorArray[252], colorArray[253]);" onmouseover="showColor(colorArray[252], colorArray[253]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[254]);"  coords="282,142,298,158" tabindex="228" 
            onfocus="showColor(colorArray[254], colorArray[255]);" onmouseover="showColor(colorArray[254], colorArray[255]);" >




<!-- 9th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[256]);"  coords="2,162,18,178" tabindex="229" 
            onfocus="showColor(colorArray[256], colorArray[257]);" onmouseover="showColor(colorArray[256], colorArray[257]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[258]);"  coords="20,162,36,178" tabindex="230" 
            onfocus="showColor(colorArray[258], colorArray[259]);" onmouseover="showColor(colorArray[258], colorArray[259]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[260]);"  coords="40,162,56,178" tabindex="231" 
            onfocus="showColor(colorArray[260], colorArray[261]);" onmouseover="showColor(colorArray[260], colorArray[261]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[262]);"  coords="58,162,74,178" tabindex="232" 
            onfocus="showColor(colorArray[262], colorArray[263]);" onmouseover="showColor(colorArray[262], colorArray[263]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[264]);"  coords="78,162,94,178" tabindex="233" 
            onfocus="showColor(colorArray[264], colorArray[265]);" onmouseover="showColor(colorArray[264], colorArray[265]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[266]);"  coords="96,162,112,178" tabindex="234" 
            onfocus="showColor(colorArray[266], colorArray[267]);" onmouseover="showColor(colorArray[266], colorArray[267]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[268]);"  coords="114,162,130,178" tabindex="235" 
            onfocus="showColor(colorArray[268], colorArray[269]);" onmouseover="showColor(colorArray[268], colorArray[269]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[270]);"  coords="132,162,148,178" tabindex="236" 
            onfocus="showColor(colorArray[270], colorArray[271]);" onmouseover="showColor(colorArray[270], colorArray[271]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[272]);"  coords="150,162,166,178" tabindex="237" 
            onfocus="showColor(colorArray[272], colorArray[273]);" onmouseover="showColor(colorArray[272], colorArray[273]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[274]);"  coords="170,162,186,178" tabindex="238" 
            onfocus="showColor(colorArray[274], colorArray[275]);" onmouseover="showColor(colorArray[274], colorArray[275]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[276]);"  coords="188,162,204,178" tabindex="239" 
            onfocus="showColor(colorArray[276], colorArray[277]);" onmouseover="showColor(colorArray[276], colorArray[277]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[278]);"  coords="208,162,224,178" tabindex="240" 
            onfocus="showColor(colorArray[278], colorArray[279]);" onmouseover="showColor(colorArray[278], colorArray[279]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[280]);"  coords="226,162,242,178" tabindex="241" 
            onfocus="showColor(colorArray[280], colorArray[281]);" onmouseover="showColor(colorArray[280], colorArray[281]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[282]);"  coords="244,162,260,178" tabindex="242" 
            onfocus="showColor(colorArray[282], colorArray[283]);" onmouseover="showColor(colorArray[282], colorArray[283]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[284]);"  coords="264,162,280,178" tabindex="243" 
            onfocus="showColor(colorArray[284], colorArray[285]);" onmouseover="showColor(colorArray[284], colorArray[285]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[286]);" coords="282,162,298,178" tabindex="244" 
            onfocus="showColor(colorArray[286], colorArray[287]);" onmouseover="showColor(colorArray[286], colorArray[287]);" >




<!-- 10th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[288]);"  coords="2,182,18,198" tabindex="245" 
            onfocus="showColor(colorArray[288], colorArray[289]);" onmouseover="showColor(colorArray[288], colorArray[289]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[290]);"  coords="20,182,36,198" tabindex="246" 
            onfocus="showColor(colorArray[290], colorArray[291]);" onmouseover="showColor(colorArray[290], colorArray[291]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[292]);"  coords="40,182,56,198" tabindex="247" 
            onfocus="showColor(colorArray[292], colorArray[293]);" onmouseover="showColor(colorArray[292], colorArray[293]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[294]);"  coords="58,182,74,198" tabindex="248" 
            onfocus="showColor(colorArray[294], colorArray[295]);" onmouseover="showColor(colorArray[294], colorArray[295]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[296]);"  coords="78,182,94,198" tabindex="249" 
            onfocus="showColor(colorArray[296], colorArray[297]);" onmouseover="showColor(colorArray[296], colorArray[297]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[298]);"  coords="96,182,112,198" tabindex="250" 
            onfocus="showColor(colorArray[298], colorArray[299]);" onmouseover="showColor(colorArray[298], colorArray[299]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[300]);"  coords="114,182,130,198" tabindex="251" 
            onfocus="showColor(colorArray[300], colorArray[301]);" onmouseover="showColor(colorArray[300], colorArray[301]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[302]);"  coords="132,182,148,198" tabindex="252" 
            onfocus="showColor(colorArray[302], colorArray[303]);" onmouseover="showColor(colorArray[302], colorArray[303]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[304]);"  coords="150,182,166,198" tabindex="253" 
            onfocus="showColor(colorArray[304], colorArray[305]);" onmouseover="showColor(colorArray[304], colorArray[305]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[306]);"  coords="170,182,186,198" tabindex="254" 
            onfocus="showColor(colorArray[306], colorArray[307]);" onmouseover="showColor(colorArray[306], colorArray[307]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[308]);"  coords="188,182,204,198" tabindex="255" 
            onfocus="showColor(colorArray[308], colorArray[309]);" onmouseover="showColor(colorArray[308], colorArray[309]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[310]);"  coords="208,182,224,198" tabindex="256" 
            onfocus="showColor(colorArray[310], colorArray[311]);" onmouseover="showColor(colorArray[310], colorArray[311]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[312]);"  coords="226,182,242,198" tabindex="257" 
            onfocus="showColor(colorArray[312], colorArray[313]);" onmouseover="showColor(colorArray[312], colorArray[313]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[314]);"  coords="244,182,260,198" tabindex="258" 
            onfocus="showColor(colorArray[314], colorArray[315]);" onmouseover="showColor(colorArray[314], colorArray[315]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[316]);"  coords="264,182,280,198" tabindex="259" 
            onfocus="showColor(colorArray[316], colorArray[317]);" onmouseover="showColor(colorArray[316], colorArray[317]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[318]);"  coords="282,182,298,198" tabindex="260" 
            onfocus="showColor(colorArray[318], colorArray[319]);" onmouseover="showColor(colorArray[318], colorArray[319]);" >



<!-- 11th row -->

	<AREA shape="rect" id="row1"
		href="javascript:IBM_RTE_selectColor(colorArray[320]);"  coords="2,202,18,218" tabindex="261" 
            onfocus="showColor(colorArray[320], colorArray[321]);" onmouseover="showColor(colorArray[320], colorArray[321]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[322]);"  coords="20,202,36,218" tabindex="262" 
            onfocus="showColor(colorArray[322], colorArray[323]);" onmouseover="showColor(colorArray[322], colorArray[323]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[324]);" coords="40,202,56,218" tabindex="263" 
            onfocus="showColor(colorArray[324], colorArray[325]);" onmouseover="showColor(colorArray[324], colorArray[325]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[326]);"  coords="58,202,74,218" tabindex="264" 
            onfocus="showColor(colorArray[326], colorArray[327]);" onmouseover="showColor(colorArray[326], colorArray[327]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[328]);"  coords="78,202,94,218" tabindex="265" 
            onfocus="showColor(colorArray[328], colorArray[329]);" onmouseover="showColor(colorArray[328], colorArray[329]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[330]);"  coords="96,202,112,218" tabindex="266" 
            onfocus="showColor(colorArray[330], colorArray[331]);" onmouseover="showColor(colorArray[330], colorArray[331]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[332]);"  coords="114,202,130,218" tabindex="267" 
            onfocus="showColor(colorArray[332], colorArray[333]);" onmouseover="showColor(colorArray[332], colorArray[333]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[334]);"  coords="132,202,148,218" tabindex="268" 
            onfocus="showColor(colorArray[334], colorArray[335]);" onmouseover="showColor(colorArray[334], colorArray[335]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[336]);"  coords="150,202,166,218" tabindex="269" 
            onfocus="showColor(colorArray[336], colorArray[337]);" onmouseover="showColor(colorArray[336], colorArray[337]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[338]);"  coords="170,202,186,218" tabindex="270" 
            onfocus="showColor(colorArray[338], colorArray[339]);" onmouseover="showColor(colorArray[338], colorArray[339]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[340]);"  coords="188,202,204,218" tabindex="271" 
            onfocus="showColor(colorArray[340], colorArray[341]);" onmouseover="showColor(colorArray[340], colorArray[341]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[342]);"  coords="208,202,224,218" tabindex="272" 
            onfocus="showColor(colorArray[342], colorArray[343]);" onmouseover="showColor(colorArray[342], colorArray[343]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[344]);" coords="226,202,242,218" tabindex="273" 
            onfocus="showColor(colorArray[344], colorArray[345]);" onmouseover="showColor(colorArray[344], colorArray[345]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[346]);"  coords="244,202,260,218" tabindex="274" 
            onfocus="showColor(colorArray[346], colorArray[347]);" onmouseover="showColor(colorArray[346], colorArray[347]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[348]);"  coords="264,202,280,218" tabindex="275" 
            onfocus="showColor(colorArray[348], colorArray[349]);" onmouseover="showColor(colorArray[348], colorArray[349]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[350]);"  coords="282,202,298,218" tabindex="276" 
            onfocus="showColor(colorArray[350], colorArray[351]);" onmouseover="showColor(colorArray[350], colorArray[351]);" >



<!-- 12th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[352]);"  coords="2,220,18,236" tabindex="277" 
            onfocus="showColor(colorArray[352], colorArray[353]);" onmouseover="showColor(colorArray[352], colorArray[353]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[354]);"  coords="20,220,36,236" tabindex="278" 
            onfocus="showColor(colorArray[354], colorArray[355]);" onmouseover="showColor(colorArray[354], colorArray[355]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[356]);"  coords="40,220,56,236" tabindex="279" 
            onfocus="showColor(colorArray[356], colorArray[357]);" onmouseover="showColor(colorArray[356], colorArray[357]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[358]);"  coords="58,220,74,236" tabindex="280" 
            onfocus="showColor(colorArray[358], colorArray[359]);" onmouseover="showColor(colorArray[358], colorArray[359]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[360]);"  coords="78,220,94,236" tabindex="281" 
            onfocus="showColor(colorArray[360], colorArray[361]);" onmouseover="showColor(colorArray[360], colorArray[361]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[362]);"  coords="96,220,112,236" tabindex="282" 
            onfocus="showColor(colorArray[362], colorArray[363]);" onmouseover="showColor(colorArray[362], colorArray[363]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[364]);"  coords="114,220,130,236" tabindex="283" 
            onfocus="showColor(colorArray[364], colorArray[365]);" onmouseover="showColor(colorArray[364], colorArray[365]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[366]);"  coords="132,220,148,236" tabindex="284" 
            onfocus="showColor(colorArray[366], colorArray[367]);" onmouseover="showColor(colorArray[366], colorArray[367]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[368]);"  coords="150,220,166,236" tabindex="285" 
            onfocus="showColor(colorArray[368], colorArray[369]);" onmouseover="showColor(colorArray[368], colorArray[369]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[370]);"  coords="170,220,186,236" tabindex="286" 
            onfocus="showColor(colorArray[370], colorArray[371]);" onmouseover="showColor(colorArray[370], colorArray[371]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[372]);"  coords="188,220,204,236" tabindex="287" 
            onfocus="showColor(colorArray[372], colorArray[373]);" onmouseover="showColor(colorArray[372], colorArray[373]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[374]);"  coords="208,220,224,236" tabindex="288" 
            onfocus="showColor(colorArray[374], colorArray[375]);" onmouseover="showColor(colorArray[374], colorArray[375]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[376]);"  coords="226,220,242,236" tabindex="289" 
            onfocus="showColor(colorArray[376], colorArray[377]);" onmouseover="showColor(colorArray[376], colorArray[377]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[378]);"  coords="244,220,260,236" tabindex="290" 
            onfocus="showColor(colorArray[378], colorArray[379]);" onmouseover="showColor(colorArray[378], colorArray[379]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[380]);"  coords="264,220,280,236" tabindex="291" 
            onfocus="showColor(colorArray[380], colorArray[381]);" onmouseover="showColor(colorArray[380], colorArray[381]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[382]);"  coords="282,220,298,236" tabindex="292" 
            onfocus="showColor(colorArray[382], colorArray[383]);" onmouseover="showColor(colorArray[382], colorArray[383]);" >




<!-- 13th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[384]);"  coords="2,240,18,256" tabindex="293" 
            onfocus="showColor(colorArray[384], colorArray[385]);" onmouseover="showColor(colorArray[384], colorArray[385]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[386]);"  coords="20,240,36,256" tabindex="294" 
            onfocus="showColor(colorArray[386], colorArray[387]);" onmouseover="showColor(colorArray[386], colorArray[387]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[388]);"  coords="40,240,56,256" tabindex="295" 
            onfocus="showColor(colorArray[388], colorArray[389]);" onmouseover="showColor(colorArray[388], colorArray[389]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[390]);"  coords="58,240,74,256" tabindex="296" 
            onfocus="showColor(colorArray[390], colorArray[391]);" onmouseover="showColor(colorArray[390], colorArray[391]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[392]);"  coords="78,240,94,256" tabindex="297" 
            onfocus="showColor(colorArray[392], colorArray[393]);" onmouseover="showColor(colorArray[392], colorArray[393]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[394]);"  coords="96,240,112,256" tabindex="298" 
            onfocus="showColor(colorArray[394], colorArray[395]);" onmouseover="showColor(colorArray[394], colorArray[395]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[396]);"  coords="114,240,130,256" tabindex="299" 
            onfocus="showColor(colorArray[396], colorArray[397]);" onmouseover="showColor(colorArray[396], colorArray[397]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[398]);"  coords="132,240,148,256" tabindex="300" 
            onfocus="showColor(colorArray[398], colorArray[399]);" onmouseover="showColor(colorArray[398], colorArray[399]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[400]);"  coords="150,240,166,256" tabindex="301" 
            onfocus="showColor(colorArray[400], colorArray[401]);" onmouseover="showColor(colorArray[400], colorArray[401]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[402]);"  coords="170,240,186,256" tabindex="302" 
            onfocus="showColor(colorArray[402], colorArray[403]);" onmouseover="showColor(colorArray[402], colorArray[403]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[404]);"  coords="188,240,204,256" tabindex="303" 
            onfocus="showColor(colorArray[404], colorArray[405]);" onmouseover="showColor(colorArray[404], colorArray[405]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[406]);"  coords="208,240,224,256" tabindex="304" 
            onfocus="showColor(colorArray[406], colorArray[407]);" onmouseover="showColor(colorArray[406], colorArray[407]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[408]);"  coords="226,240,242,256" tabindex="305" 
            onfocus="showColor(colorArray[408], colorArray[409]);" onmouseover="showColor(colorArray[408], colorArray[409]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[410]);"  coords="244,240,260,256" tabindex="306" 
            onfocus="showColor(colorArray[410], colorArray[411]);" onmouseover="showColor(colorArray[410], colorArray[411]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[412]);"  coords="264,240,280,256" tabindex="307" 
            onfocus="showColor(colorArray[412], colorArray[413]);" onmouseover="showColor(colorArray[412], colorArray[413]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[414]);"  coords="282,240,298,256" tabindex="308" 
            onfocus="showColor(colorArray[414], colorArray[415]);" onmouseover="showColor(colorArray[414], colorArray[415]);" >




<!-- 14th row -->

	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[416]);"  coords="2,260,18,276" tabindex="309" 
            onfocus="showColor(colorArray[416], colorArray[417]);" onmouseover="showColor(colorArray[416], colorArray[417]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[418]);"  coords="20,260,36,276" tabindex="310" 
            onfocus="showColor(colorArray[418], colorArray[419]);" onmouseover="showColor(colorArray[418], colorArray[419]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[420]);"  coords="40,260,56,276" tabindex="311" 
            onfocus="showColor(colorArray[420], colorArray[421]);" onmouseover="showColor(colorArray[420], colorArray[421]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[422]);"  coords="58,260,74,276" tabindex="312" 
            onfocus="showColor(colorArray[422], colorArray[423]);" onmouseover="showColor(colorArray[422], colorArray[423]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[424]);"  coords="78,260,94,276" tabindex="313" 
            onfocus="showColor(colorArray[424], colorArray[425]);" onmouseover="showColor(colorArray[424], colorArray[425]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[426]);"  coords="96,260,112,276" tabindex="314" 
            onfocus="showColor(colorArray[426], colorArray[427]);" onmouseover="showColor(colorArray[426], colorArray[427]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[428]);"  coords="114,260,130,276" tabindex="315" 
            onfocus="showColor(colorArray[428], colorArray[429]);" onmouseover="showColor(colorArray[428], colorArray[429]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[430]);"  coords="132,260,148,276" tabindex="316" 
            onfocus="showColor(colorArray[430], colorArray[431]);" onmouseover="showColor(colorArray[430], colorArray[431]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[432]);"  coords="150,260,166,276" tabindex="317" 
            onfocus="showColor(colorArray[432], colorArray[433]);" onmouseover="showColor(colorArray[432], colorArray[433]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[434]);"  coords="170,260,186,276" tabindex="318" 
            onfocus="showColor(colorArray[434], colorArray[435]);" onmouseover="showColor(colorArray[434], colorArray[435]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[436]);"  coords="188,260,204,276" tabindex="319" 
            onfocus="showColor(colorArray[436], colorArray[437]);" onmouseover="showColor(colorArray[436], colorArray[437]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[438]);"  coords="208,260,224,276" tabindex="320" 
            onfocus="showColor(colorArray[438], colorArray[439]);" onmouseover="showColor(colorArray[438], colorArray[439]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[440]);"  coords="226,260,242,276" tabindex="321" 
            onfocus="showColor(colorArray[440], colorArray[441]);" onmouseover="showColor(colorArray[440], colorArray[441]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[442]);"  coords="244,260,260,276" tabindex="322" 
            onfocus="showColor(colorArray[442], colorArray[443]);" onmouseover="showColor(colorArray[442], colorArray[443]);" >
	<AREA shape="rect" 
		href="javascript:IBM_RTE_selectColor(colorArray[444]);"  coords="264,260,280,276" tabindex="323" 
            onfocus="showColor(colorArray[444], colorArray[445]);" onmouseover="showColor(colorArray[444], colorArray[445]);" >
	<AREA shape="rect"
		href="javascript:IBM_RTE_selectColor(colorArray[446]);"  coords="282,260,298,276" tabindex="324" 
            onfocus="showColor(colorArray[446], colorArray[447]);" onmouseover="showColor(colorArray[446], colorArray[447]);" >



	<AREA shape="default" nohref>
    </MAP>



             </td></tr>
           </table>
      </td>
   </tr>  

<tr>
<td>
<hr class="portlet-separator">

&nbsp;<input id="TheCancelButton" type="button" tabindex="52" class="wpsButtonText" value="<%= resourceBundle.getString("Cancel") %>" onclick="parent.close();" onmouseout="IBM_RTE_btn_mouseoout(id)" onmouseover="IBM_RTE_btn_mouseover(id)">

<br><br>

</td>
</tr>

</table>



</body>
</html>

