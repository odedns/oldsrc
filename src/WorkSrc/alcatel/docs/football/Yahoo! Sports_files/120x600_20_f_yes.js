function trv_writeSelect(name, options) {document.write('<select name="' + name + '" class="trv_short_input">' + options + '</select>');}
function trv_submitForm() {if (trv_validateData()) {var f = document.trv_bannerForm;var url = trv_formUrl + "?";for(var i=0; i<f.elements.length; i++) {url += f.elements[i].name + "=" + escape(f.elements[i].value) + "&";}window.top.location.href = url;}return;}
function trv_validateData() {var f = document.trv_bannerForm;var s = "";if (f["dep_arp_cd(1)"].value.length==0) {s += "\n\"From\" is a required field.";}if (f["arr_arp_cd(1)"].value.length==0) {s += "\n\"To\" is a required field.";}var dte1 = (f.dep_dt_mn_1.selectedIndex+1) + "/" + f.dep_dt_dy_1.options[f.dep_dt_dy_1.selectedIndex].value + "/" + new Date().getFullYear();dte1 = new Date(dte1);var dte2 = (f.dep_dt_mn_2.selectedIndex+1) + "/" + f.dep_dt_dy_2.options[f.dep_dt_dy_2.selectedIndex].value + "/" + new Date().getFullYear();dte2 = new Date(dte2);if (dte1>dte2) {s += "\nYou have entered conflicting departing and returning dates.";}if (s.length==0) {return true;}else {alert("The form could not be submitted for the following reason(s)\n" + s);return false;}}

function writeHTML() {
document.write('<table id="trv_banner" width="120" height="600" border="0" cellpadding="0" cellspacing="0" class="trv_table"><form target="_top" name="trv_bannerForm" method="get" action="#"><tr><td width="120" height="81"><a target="_top" href="' + trv_defaultUrl + '"><img SRC="http://us.a1.yimg.com/us.yimg.com/a/tr/travelocity/20030610/120x600_20_f_yes_logo.gif" WIDTH="120" HEIGHT="81" border="0"></a></td></tr><tr><td><img SRC="http://us.a1.yimg.com/us.yimg.com/a/tr/travelocity/20030610/120x600_20_f_yes_text.gif" width="120" height="170"></td></tr><tr><td bgcolor="#FF9900" width="120" height="162" valign="top" align="center">');
document.write('<input type="hidden" name="Service" value="YHOT" /><input type="hidden" name="module" value="tripsrch"><input type="hidden" name="num_count" value="9"><input type="hidden" name="pref_aln" value="all"><input type="hidden" name="cls_svc" value="YR"><input type="hidden" name="pax_cnt" value="1"><input type="hidden" name="chld_pax_cnt" value="0"><input type="hidden" name="dep_tm_1" value="12:00 AM"><input type="hidden" name="dep_tm_2" value="12:00 AM"><input type="hidden" name="trip_option" value="roundtrp"><table cellpadding="0" cellspacing="2" border="0"><tr><td align="center">From:</td></tr><tr><td><input type="text" name="dep_arp_cd(1)" class="trv_input" value="" size="9" maxlength="20"></td></tr><tr><td align="center">To:</td></tr><tr><td><input type="text" class="trv_input" name="arr_arp_cd(1)" value="" size="9" maxlength="20"></td></tr><tr><td align="center">Departing:</td></tr><tr><td align="center">');
trv_writeSelect("dep_dt_mn_1", trv_monthOptions);
document.write('&nbsp;');
trv_writeSelect("dep_dt_dy_1", trv_dayOptions);
document.write('</td></tr><tr><td align="center">Returning:</td></tr><tr><td align="center">');
trv_writeSelect("dep_dt_mn_2", trv_monthOptions);
document.write('&nbsp;');
trv_writeSelect("dep_dt_dy_2", trv_dayOptions);
document.write('</td></tr></table>');
document.write('</td></tr><tr><td width="120" height="187"><img src="http://us.a1.yimg.com/us.yimg.com/a/tr/travelocity/20030610/120x600_20_f_yes_icon.gif" width="120" height="187" border="0" usemap="#trv_imageMap"></td></tr></form></table><map name="trv_imageMap"><area shape="rect" coords="46,13,74,32" href="javascript:trv_submitForm();"><area target="_top" shape="rect" coords="5,96,116,186" href="' + trv_specialUrl + '"></map>');

}