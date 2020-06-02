var START_YEAR = 1900;
function monthCalInit()
{
	var today = new Date();
	var currentMonth = today.getMonth() + 1;
	var currentYear = today.getYear();
	var currentDate = parent.document.all(document.all.returnFieldName.value).value;
	if (currentDate != null && currentDate != "" && isDate("1/" + currentDate))
	{
		var index = currentDate.indexOf("/");
		currentMonth = currentDate.substring(0,index);
		currentYear =  currentDate.substring(index + 1);
	}
	initYears(currentYear);
	initMonths(currentYear,currentMonth);
}

function initYears(currentYear)
{
	for (index = START_YEAR;index < 2100;index++)
	{
		year.add(new Option(index,index,currentYear == index,currentYear == index));
	}
}

function initMonths(currentYear,currentMonth)
{
	var today = new Date();
	var months = parent.CALENDAR_MONTHS;
	var daysLabels = parent.CALENDAR_DAYS;
	var buttons = document.all("monthButton");
	for (index = 0;index < months.length;index++)
	{
		buttons[index].value = months[index];
		
		//selected month
		if (currentMonth - 1 == index && year.value == currentYear)
		{
			if (today.getMonth() == index && year.value == today.getYear())
			{
				buttons[index].className = "todaySelectedMonthButton";
			}
			else
			{
				buttons[index].className = "selectedMonthButton";
			}
		}

		//month of today	
		else if (today.getMonth() == index && year.value == today.getYear())
		{
			buttons[index].className = "todayMonthButton";
		}
		
		//normal month
		else
		{
			buttons[index].className = "monthButton";
		}
	}
	noDateButton.value = daysLabels[9];
	thisMonthButton.value = daysLabels[10];
}

function selectMonth(currentMonth)
{
	parent.document.all(document.all.returnFieldName.value).value = currentMonth + "/" + year.value;
	parent.monthCalendarPopup.hide();
}

function prevYear() 
{
	var index = year.selectedIndex;
	if (index != 0)
	{
		year.selectedIndex--;
	}
	else
	{
		year.selectedIndex = year.options.length - 1;
	}
}

function nextYear()
{
	var index = year.selectedIndex;
	if (index != year.options.length - 1)
	{
		year.selectedIndex++;
	}
	else
	{
		year.selectedIndex = 0;
	}
}

function returnThisMonth()
{
	var thisMonth = new Date();
	parent.document.all(document.all.returnFieldName.value).value = (thisMonth.getMonth()+1) + "/" + thisMonth.getFullYear();
	parent.monthCalendarPopup.hide();
}

function clearDate()
{
	parent.document.all(document.all.returnFieldName.value).value = "";
	parent.monthCalendarPopup.hide();
}

