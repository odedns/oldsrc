var START_YEAR = 1900;
var hDay=new Array(30);
var hNum=0;
var months = parent.CALENDAR_MONTHS;
var daysLabels = parent.CALENDAR_DAYS;
var rDay=new Date(START_YEAR,1,1);
var cDay=new Date();
var sDay=new Date();
var tDay=new Date();
var sStart;
function calInit()
{
	initDays();
	var currentDate = parent.document.all(document.all.returnFieldName.value).value;
	if (currentDate != null && currentDate != "" && isDate(currentDate))
	{
		var firstIndex = currentDate.indexOf("/");
		var lastIndex = currentDate.lastIndexOf("/");
		if (firstIndex != -1 && lastIndex != -1)
		{
			var day = currentDate.substring(0,firstIndex);
			var month = currentDate.substring(firstIndex + 1,lastIndex);
			var year = currentDate.substring(lastIndex + 1);
			cDay.setFullYear(year,month - 1,day);
			sDay.setFullYear(year,month - 1,day);
			tDay.setFullYear(year,month - 1,day);
		}
	}
	addYear();
	addMonth();	
	showCalendar();
}

function initDays()
{
	var daysTr = document.all("daysTr");
	var daysTds = daysTr.children;
	
	for (index = 0;index < daysTds.length;index++)
	{
		daysTds[index].innerText = daysLabels[index];
	}
	todayButton.value = daysLabels[8];
	noDateButton.value = daysLabels[9];
}

function addYear() {
  for(i = START_YEAR; i<2100; i++) {
    var oOption = document.createElement("OPTION");
    oOption.text=i;
    oOption.value=i;
    document.all.s2.add(oOption);
  }
}

function addMonth() 
{
  if (document.all.s1.options.length == 0)
  {	
	  for(i=0; i<12; i++) 
	  {
	    var oOption = document.createElement("OPTION");
	    oOption.text=months[i];
	    oOption.value=i+1;
	    document.all.s1.add(oOption);
	  }
  }
}

function getDaysInMonth(e, day) {
  var MDays=new Array(31,28,31,30,31,30,31,31,30,31,30,31);
  D=MDays[e];
  if(day.getFullYear()%4==0 && e==1) {
    D=29;
  }
  return D;
}

function setDate(y, m, d) {
  setYear(y);
  setMonth(m);
  setDay(d);
}

function setYear(y) {
  sDay.setFullYear(y);
}
function setMonth(m) {
  sDay.setMonth(m);
}

function setDay(d) {
  sDay.setDate(d);
}


function showCalendar() 
{
  var today = new Date();
  document.all.s2.selectedIndex=sDay.getFullYear() - START_YEAR;
  document.all.s1.selectedIndex=sDay.getMonth();

  var d = getDaysInMonth(sDay.getMonth(), sDay);
  var y = sDay.getFullYear() - START_YEAR;
  if (y < 0) y = sDay.getFullYear();

  var oDate = new Date(sDay.getFullYear(), (sDay.getMonth()), 1); 
  sStart = oDate.getDay();
  if (sStart < 1) sStart += 7;
  
  preDate();
  var predn=getDaysInMonth(tDay.getMonth(), tDay);
  for(i=0; i<42; i++) 
  {
    id=document.all("b"+(i+1));
    if(i<sStart) 
    {
      id.className = "dayDisabledButton";
      id.value=predn-sStart+i+1;
    }
    
	else if (today.getDate() == i-sStart+1 && today.getMonth() == sDay.getMonth() && today.getYear() == sDay.getYear())
	{ 
	    id.className = "todayButton";
	    id.value=i-sStart+1;	
	}
    
    else if(i>=sStart+d) 
    {
      id.className = "dayDisabledButton";
      id.value=i-d-sStart+1;
    }
    else if(!((i+1)%7)) 
    {
	  id.className = "daySatButton"; 
      id.value=i-sStart+1;
    }
    else 
    {
      id.className = "dayButton";
      id.value=i-sStart+1;
    }
  }
  showCurrent();
}

function showCurrent() 
{
  if(cDay.getFullYear() == sDay.getFullYear() && cDay.getMonth() == sDay.getMonth()) 
  {
  	var today = new Date();
    id=document.all("b"+(sStart+cDay.getDate()));
    if (today.getDate() == sDay.getDate() && today.getMonth() == sDay.getMonth() && today.getYear() == sDay.getYear())
    {
    	id.className = "todayCurrentButton";
    }
    else
    {
    	id.className = "dayCurrentButton";
	}
	oldid=id;
  }
}

function preDate() 
{
  var m=sDay.getMonth()-1;
  var y=sDay.getFullYear();
  if(m<0) {
    y--;
    if(y<START_YEAR) y=2099;
    m=11;
  }
  tDay.setFullYear(y);
  tDay.setMonth(m);
}

function nextDate() 
{
  var m=sDay.getMonth()+1;
  var y=sDay.getFullYear();
  if(m>11) {
    y++;
    if(y>2099) y=START_YEAR;
    m=0;
  }
  tDay.setFullYear(y);
  tDay.setMonth(m);
}

function ck(k) 
{
  var d=getDaysInMonth(sDay.getMonth(), sDay);
  c=k-sStart;
  if(c<=0)
    pr();
  else if(c>d)
    ne();
  else 
  {
    setDay(k-sStart);
    parent.document.all(document.all.returnFieldName.value).value = sDay.getDate()+ "/" + (sDay.getMonth()+1) + "/" + sDay.getFullYear();
    parent.calendarPopup.hide();
  }
}

function sm() 
{
  setMonth(s1.selectedIndex);
  showCalendar();
}

function sy()
{
  setYear(s2.selectedIndex + START_YEAR);
  showCalendar();
}

function pr() 
{
  preDate();
  setDate(tDay.getFullYear(), tDay.getMonth(), sDay.getDate());
  showCalendar();
}

function ne() {
  nextDate();
  setDate(tDay.getFullYear(), tDay.getMonth(), sDay.getDate());
  showCalendar();
}

function returnToday()
{
	var today = new Date();
	parent.document.all(document.all.returnFieldName.value).value = today.getDate()+ "/" + (today.getMonth()+1) + "/" + today.getFullYear();
	parent.calendarPopup.hide();
}

function clearDate()
{
	parent.document.all(document.all.returnFieldName.value).value = "";
	parent.calendarPopup.hide();
}