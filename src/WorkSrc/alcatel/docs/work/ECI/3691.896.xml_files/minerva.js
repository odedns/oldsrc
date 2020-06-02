function isBlank(s){
	//alert('isBlank string s = ' + s + ' ' + s.length);
	for (var i=0; i< s.length; i++){
		var c = s.charAt(i);
		if((c != ' ') && (c != '\n') && (c != '\t'))
			return false;
	}
	return true;
}

function throwFormError(errorMessage) {
	alert('One or more fields are incorrect.' + '\n\n' + errorMessage + ' and try again.');
return true;
}

function IsElemComplete(FormName,ElemName,ElemType,ElemHelpMessage) {
	// Checks to ensure that the element has been filled in or set to a value other than blank
	//
	var ElemOk  = true;
	var Elem    = document.forms[FormName].elements[ElemName]
	//alert('IsElemComplete ' + FormName + ' ' + Elem.name +' = ' + Elem.value);
	// check a dropdown list
	if ( ElemType == 'selectOnepulldown') {
		if ( !validateSelect(Elem)) {
			throwFormError(ElemHelpMessage);
			Elem.focus();
			return false;
		}
	}
	// check a radio button list
	if ( ElemType == 'selectOneradioGroup') {
		if ( !validateRadioCheckbox(Elem)) {
			throwFormError(ElemHelpMessage);
			Elem.focus();
			return false;
		}
	}
	// check an input field
	if ( ElemType == 'input' || ElemType == 'textarea' || ElemType == 'secret') {
		if ( Elem.value == '' || isBlank(Elem.value)){
			throwFormError(ElemHelpMessage);
			Elem.focus();
			return false;
		}
	}

	return ElemOk;
}

function IsEmailValid(FormName,ElemName,ElemHelpMessage) {
	var EmailOk  = true
	var Temp     = document.forms[FormName].elements[ElemName]
	var AtSym    = Temp.value.indexOf('@')
	var Period   = Temp.value.lastIndexOf('.')
	var Space    = Temp.value.indexOf(' ')
	var Length   = Temp.value.length - 1   // Array is from 0 to length-1

	if ((AtSym < 1) ||                     // '@' cannot be in first position
		(Period <= AtSym+1) ||             // Must be atleast one valid char btwn '@' and '.'
		(Period == Length ) ||             // Must be atleast one valid char after '.'
		(Space  != -1))                    // No empty spaces permitted
	{
		EmailOk = false
		throwFormError(ElemHelpMessage);
		Temp.focus()
	}
	return EmailOk
}

function validateSelect(field) {

	index = field.selectedIndex;
	//alert('validateSelect ' + field.value + ' index ' + index);
	//alert('validateSelect option length ' + field.options[index].value.length);
	if ((field.options[index].value == '--') || (field.options[index].value == '-') || (field.options[index].value.length == 0)) {
		return false;
	}
	return true;
}

function validateRadioCheckbox( FormName, ElemName, ElemHelpMessage) {

	var Temp     = document.forms[FormName].elements[ElemName]

	//TBD - there cannot be more than 10 in the radio group
	//excuse Friday before holidays

	//First check to see if there are any product selected
	if(  ( Temp.value == "" ) ||  ( Temp.value == "-" ) ) {
		throwFormError(ElemHelpMessage);
		return false;
	}

	if ( Temp.checked  ) return true;

	for (i=0;i<Temp.length; i++) {

 		//alert('validateRadioCheckbox checked' + Temp[i].checked);
 		//alert('validateRadioCheckbox checked' + Temp[i].value);

		if (Temp[i].checked) {
			if ( ( Temp[i].value != "" ) || ( Temp[i].value != "-" ) )  return true;
		}
	}
	throwFormError(ElemHelpMessage);
	return false;

}

//This function will process the form, looking for
//contactid, agreementnumber, and siteid.  It will return
//true if any of the 3 are valid
function hasValidVantiveDetails(FormName){
	var hasValidDetails  = true;
	if (
	isBlank(document.forms[FormName].elements['siteid'].value) &&
	isBlank(document.forms[FormName].elements['contactid'].value) &&
	isBlank(document.forms[FormName].elements['agreementnumber'].value) ){
	//One of the above must be filled in
		throwFormError('Please enter one of\nCustomer ID or\nAgreement Number or\n Site ID');
		document.forms[FormName].elements['contactid'].focus();
		hasValidDetails = false;
	} else


	if (!isBlank(document.forms[FormName].elements['contactid'].value)){

		var CIDwhitespace = document.forms[FormName].elements['contactid'].value;

		var CID = CIDwhitespace.replace(/\s+/, "");

		var contactMatch1 = CID.match(/^c\d{4}-\d{4}-\d{4}$/);
		//alert("contactMatch1=" + contactMatch1);
		var contactMatch2 = CID.match(/^c\d{4}-\d{4}-\d{2}$/);
		//alert("contactMatch2=" + contactMatch2);
		var contactMatch3 = CID.match(/^c\d{5}-\d{4}-\d{2}$/);
		//alert("contactMatch3=" + contactMatch3);
		var contactMatch4 = CID.match(/^c\d{5}-\d{4}-\d{3}$/);
		//alert("contactMatch4=" + contactMatch4);

		if(!(contactMatch1 || contactMatch2 || contactMatch3 || contactMatch4)){
			throwFormError('Please enter a valid Contact ID');
			document.forms[FormName].elements['contactid'].focus();
			hasValidDetails = false;
		}
	} else if (!isBlank(document.forms[FormName].elements['agreementnumber'].value)){
		if ( (document.forms[FormName].elements['agreementnumber'].value.length < 12) ||
			(document.forms[FormName].elements['agreementnumber'].value.length > 30)){

			throwFormError('Please enter a valid Agreement Number');
			document.forms[FormName].elements['agreementnumber'].focus();
			hasValidDetails = false;
		} else {
			hasValidDetails = true;
		}
	} else if (!isBlank(document.forms[FormName].elements['siteid'].value)){
		//alert('Checking siteID');
		var siteid = document.forms[FormName].elements['siteid'].value;
		siteid = siteid.replace(/\s+/, "");

		if ( (siteid.match(/^\s*\d{5}\s*$/) || siteid.match(/^\s*\d{8}\s*$/)) ){
			hasValidDetails = true;
		} else {
			throwFormError('Please enter a valid Site ID');
			document.forms[FormName].elements['siteid'].focus();
			hasValidDetails = false;
		}
	}

	return hasValidDetails;
}
function hasValidElevatedDetails(FormName){
	var hasValidDetails  = true;
	if (
	isBlank(document.forms[FormName].elements['siteid'].value) &&
	isBlank(document.forms[FormName].elements['agreementnumber'].value) ){
	//One of the above must be filled in
		alert('You must enter one of\nAgreement Number or\n Site ID');
		document.forms[FormName].elements['agreementnumber'].focus();
		hasValidDetails = false;
	} else
	if (!isBlank(document.forms[FormName].elements['agreementnumber'].value)){
		if ( (document.forms[FormName].elements['agreementnumber'].value.length < 9) ||
			(document.forms[FormName].elements['agreementnumber'].value.length > 30)){

			alert('The Agreement Number you entered was not valid. Please try again');
			document.forms[FormName].elements['agreementnumber'].focus();
			hasValidDetails = false;
		} else {
			hasValidDetails = true;
		}
	} else if (!isBlank(document.forms[FormName].elements['siteid'].value)){
		if ( (document.forms[FormName].elements['siteid'].value.length == 5) ||
			(document.forms[FormName].elements['siteid'].value.length == 8)){
			alert('The Site ID you entered was not valid. Please try again');
			document.forms[FormName].elements[siteid].focus();
			hasValidDetails = false;
		}
	}

	return hasValidDetails;
}

function verify(){
	return (IsFormComplete('ArticleFeedback') && IsEmailValid('ArticleFeedback', 'usermail') );
}

function verify3(){
	return (IsFormComplete('contactSupport') && IsEmailValid('contactSupport', 'EmailAddress') && hasValidElevatedDetails('contactSupport'));
}
function verify4(){
	return (IsFormComplete('updateCentre') && IsEmailValid('updateCentre', 'EmailAddress') && hasValidVantiveDetails('updateCentre'));
}

function buildQueryXML(form) {
    var vals = form.criteria.options[form.criteria.selectedIndex].value.split(',');
    var criteria = vals[0];
    var cOption  = vals[1];
    var cValue   = form.criteriaValue.value;
    var attrib   = "";

    if (cOption == "ID")
        attrib = ' range="EQUAL"';

    form.query.value = '<search start="0" count="20"><'+criteria+' boolean="AND"><'+cOption+attrib+'>'+cValue+'</'+cOption+'></'+criteria+'></search>';
}

function instanceData(form) {
	//convert all form element into an xml fragment. This fragement has no root element and so is not valid.
	//It has to be included into an xform:instance element for it to be valid xml.

	var len = form.elements.length;
	//alert("calling instanceData function " + len);
	var i=0;
	//zero the instance hidden form field
	form.elements['instance'].value = "";
	while (i < len ) {
		var elem    = form.elements[i];
		//alert(elem.name + " = " + elem.value);
		if ((elem.value.length > 0) && (form.elements[i].name != 'instance')) {
			form.elements['instance'].value += "<" + elem.name + ">" + elem.value +"</" + elem.name + ">\n";
		}
		i++;
	}
	alert("instance = " + form.elements['instance'].value);
}

var topbarwidth,bodycellwidth;

function forPrinting() {
  document.all.sidebarcell.style.display='none';
  topbarwidth=document.all.topbar.style.width;
  document.all.topbar.style.width='100%';
  bodycellwidth=document.all.bodycell.style.width;
  document.all.bodycell.style.width='100%';
  document.all.navbar.style.letterSpacing='0pt';
}

function forDisplay() {
  document.all.sidebarcell.style.display='';
  document.all.topbar.style.width=topbarwidth;
  document.all.bodycell.style.width=bodycellwidth;
  document.all.navbar.style.letterSpacing='0.5pt';
}
