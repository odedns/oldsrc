var isMenuFirstOpened = false;
var isMenuCurrentlyOpen = false;
var isMenuCloseEventOccured = false;
var hideMenuIntervalId;
function menuMainItemOver()
{
	if (isMenuFirstOpened)
	{
		isMenuCurrentlyOpen = true;
		var menuLevels = document.all(MENU_DIV_LEVEL);
		var menuIframes = document.all(MENU_IFRAME);
		
		if (menuLevels != null)
		{
			var length = menuLevels.length;
			for (index = 1;index < length;index++)
			{
				hide(menuLevels[index]);
				hide(menuIframes[index]);
			}
		}	
					
		//Get the menu header
		var menuHeader = event.srcElement;
		changeMenuHeaderClassName(menuHeader,true);
		var menuDiv = menuLevels[0];
		var menuIframe = menuIframes[0];
		menuDiv.nodeId = menuHeader.id;
		show(menuDiv);
		var menuHtml = getMenuDivHtml(menuHeader.id,0);
		if (menuHtml != "")
		{
			menuDiv.innerHTML = menuHtml; 
			if (document.body.dir == SYSTEM_DIR_RTL)
			{
				menuDiv.style.left = menuHeader.offsetLeft + getParenLeftOffset(menuHeader,0) + menuHeader.offsetWidth - (menuDiv.offsetWidth == 0 ? 102 : menuDiv.offsetWidth) - 5;
			}
			else
			{
				menuDiv.style.left = menuHeader.offsetLeft + getParenLeftOffset(menuHeader,0) + 5;	
			}
			menuDiv.style.top = menuSpace.offsetTop + getParentTopOffset(menuHeader,0);			
			menuDiv.style.zIndex = 899;
			show(menuDiv);	
			menuIframe.style.top = menuDiv.style.top;
			menuIframe.style.left = menuDiv.style.left;
			menuIframe.style.width = menuDiv.offsetWidth;
			menuIframe.style.height = menuDiv.offsetHeight;
			menuIframe.style.zIndex = 499;		
			show(menuIframe);
		}
		else
		{
			hide(menuDiv);
			hide(menuIframe);
		}
	}
}

function menuMainItemOut()
{
	if (isMenuFirstOpened)
	{
		var menuHeader = event.srcElement;
		changeMenuHeaderClassName(menuHeader,false);
	}
}

function menuFirstOpen()
{
	if (!isMenuFirstOpened)
	{
		isMenuFirstOpened = true;
		menuMainItemOver();
	}
}

function getMenuDivHtml(divNodeId,divLevel)
{
	var str = "";
	var menuItems = menuTree.searchDirectNodeChilds(divNodeId);
	if (menuItems != null && menuItems.length > 0)
	{
		str += "<table class='menuItemsTable' height='100%' cellpadding=0 cellspacing=0>\n";
		for (index = 0;index < menuItems.length;index++)
		{
			menuItem = menuItems[index].getObject();
			var rowId = index + 1;
			str += "<tr class='menuItemTr' id=" + rowId + " onmouseout='this.className=\"menuItemTr\"' onmouseover='this.className += \"Over\";menuItemOver(" + divNodeId + "," + divLevel + ",this.id)' onclick=\"" + menuItem.functionCall + "\" >";
			str += "<td class='menuItemTd' nowrap"
			if (!menuItems[index].hasChildren())
			{
				str += " colspan=2";
			} 
			str += ">" + menuItem.title + "</td>";
			if (menuItems[index].hasChildren())
			{
				str += "<td class='menuItemArrowTd' style='font-family:webdings;'>" + (document.body.dir == "rtl" ? "3" : "4") + "</td>";
			}
			str += "</tr>\n";
		}
		str += "</table>";
	}
	return str;
}

function menuItemOver(divNodeId,divLevel,rowIndex)
{
	var menuLevels = document.all(MENU_DIV_LEVEL);
	var menuIframes = document.all(MENU_IFRAME);
	var nextLevel = divLevel + 1;
	var closeLevel = divLevel + 2;
	var menuDivCloseLevel = menuLevels[closeLevel];
	if (menuDivCloseLevel != null) 
	{
		hide(menuDivCloseLevel);
		hide(menuIframes[closeLevel]);
	}
	
	var menuDivLastLevel = menuLevels[divLevel];	
	var menuDivNextLevel = menuLevels[nextLevel];
	var menuIframeNextLevel = menuIframes[nextLevel];

	if (menuDivNextLevel != null)
	{
		menuDivNextLevel.nodeId = menuDivLastLevel.nodeId + SEPERATOR_MINUS + rowIndex;
		var menuHtml = getMenuDivHtml(menuDivNextLevel.nodeId,nextLevel);
		if (menuHtml != "")
		{
			menuDivNextLevel.innerHTML = menuHtml;
			menuDivNextLevel.style.top = new Number(getTopPos(menuDivLastLevel)) + event.srcElement.offsetTop;
			menuDivNextLevel.style.zIndex = 899;
			show(menuDivNextLevel);		
			if (document.body.dir == SYSTEM_DIR_RTL)
			{
				menuDivNextLevel.style.left = new Number(getLeftPos(menuDivLastLevel)) - menuDivNextLevel.offsetWidth + 5;
			}
			else
			{
				menuDivNextLevel.style.left = new Number(getLeftPos(menuDivLastLevel)) + menuDivLastLevel.offsetWidth - 5;
			}
			menuIframeNextLevel.style.zIndex = 199;
			menuIframeNextLevel.style.left = menuDivNextLevel.style.left;
			menuIframeNextLevel.style.top = menuDivNextLevel.style.top;
			menuIframeNextLevel.style.width = menuDivNextLevel.offsetWidth;
			menuIframeNextLevel.style.height = menuDivNextLevel.offsetHeight;
			show(menuIframeNextLevel);
		}
		else
		{
			hide(menuDivNextLevel);
			hide(menuIframeNextLevel);
		}	
	}
}

function getParentTopOffset(obj,offset)
{
	if (obj.offsetParent == null)
	{
		return offset;
	}
	return offset + getParentTopOffset(obj.offsetParent,obj.offsetParent.offsetTop);
}

function getParenLeftOffset(obj,offset)
{
	if (obj.offsetParent == null)
	{
		return offset;
	}
	return offset + getParenLeftOffset(obj.offsetParent,obj.offsetParent.offsetLeft);
}

function hideMenu()
{
	var menuLevels = document.all(MENU_DIV_LEVEL);
	var menuIframes = document.all(MENU_IFRAME);
	
	if (menuLevels != null)
	{
		var length = menuLevels.length;
		for (index = 0;index < length;index++)
		{
			if (isDisplayed(menuLevels[index]))
			{
				hide(menuIframes[index]);
				hide(menuLevels[index]);
				isMenuCloseEventOccured = true;
				isMenuCurrentlyOpen = false;
			}
		}
	}
	
	if (isMenuCloseEventOccured)
	{
		hideMenuIntervalId = window.setInterval(setHideMenuInterval,1000);
	}
}

function setHideMenuInterval()
{
	isMenuCloseEventOccured = false;
	window.clearInterval(hideMenuIntervalId);
}

function menuItemExitSystem(url)
{
	window.open(url,POPUP_TARGET_SELF,"");
}

function menuItemExitSystemOver()
{
	hideMenu();	
	isMenuCurrentlyOpen = true;	
	var exitItem = event.srcElement;
	changeMenuHeaderClassName(exitItem,true);	
}

function menuItemExitSystemOut()
{
	var exitItem = event.srcElement;
	changeMenuHeaderClassName(exitItem,false);
	hideMenu();	
	isMenuCurrentlyOpen = false;	
	isMenuCloseEventOccured = false;	
}

function changeMenuHeaderClassName(menuHeader,isOnOver)
{
	var menuHeaderClassName = menuHeader.className;
	var index = menuHeaderClassName.indexOf(CSS_SUFFIX_LTR);

	//onmouseover
	if (isOnOver)
	{
		//ltr - class has a suffix(the contant CSS_SUFFIX_LTR)
		if (index != -1)
		{
			menuHeader.className = menuHeaderClassName.substring(0,index) + CSS_SUFFIX_OVER + CSS_SUFFIX_LTR;
		}
		//rtl - class does not have s suffix
		else
		{
			menuHeader.className += CSS_SUFFIX_OVER;
		}		
	}
	
	//onmouseout
	else
	{
		menuHeader.className = menuHeaderClassName.substring(0,menuHeaderClassName.indexOf(CSS_SUFFIX_OVER));	
		//ltr suffix
		if (index != -1)
		{
			menuHeader.className += CSS_SUFFIX_LTR;
		}		
	}	
}

function MenuItem(title,functionCall)
{
	this.title = title;
	this.functionCall = functionCall;
	
	function toString()
	{
		return this.title;
	}
}

/*util*/
function getLeftPos(obj)
{
	return obj.style.left.substring(0,obj.style.left.indexOf(PIXEL));
}

function getTopPos(obj)
{
	return obj.style.top.substring(0,obj.style.top.indexOf(PIXEL));
}

