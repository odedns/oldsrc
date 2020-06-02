// myjs.js


// submit a form with a parameter
// indicating the operation to be performed.
function submitForm(form,command)
{
	form.operation.value=command;
	form.submit();
} 

// move an entry from a list to a list.
function moveList(leftBox,rightBox)
{
	var pos = rightBox.length;
	//alert('in moveListRight\n selected=' + leftBox.selectedIndex + '\tpos=' + pos);
	if(leftBox.selectedIndex > -1) {
		var optionName=leftBox.options[leftBox.selectedIndex];
		rightBox.options[pos] = new Option(optionName.text,optionName.value,false,false);
		leftBox.options[leftBox.selectedIndex] = null;
	}
}

// open a small selection window.
function openWin(url)
{
	window.open(url,"selectWindow","status,width=400,height=300");

}


// get the value of the selcted radio button.
function getSelectedRadio(radio)
{	
	var obj = radio;
	var val = "";
	for(var i=0; i < obj.length; ++i) {
		if(obj[i].checked == true) {
			val = obj[i].value;
			break;
		}
	}
	return(val);
}

// update a parent variable with the 
// selected radio button value.
function updateParent(radio,parentVar)
{
	var val = getSelectedRadio(radio);
	parentVar.value = val;
	self.close();
}


