package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;

public class FWLinkTag extends FWButtonTag
{
	protected void initCss()
	{
		enabledClassName = initUIProperty(enabledClassName,"ui.link.enabled");
		disabledClassName = initUIProperty(disabledClassName,"ui.link.disabled");
	}
	protected void renderLink() throws UIException
	{
		String linkFunction = null;
		startTag(LINK);
		if (id != null)
		{
			addAttribute(ID,id + HTML_BUTTON_SUFFIX);
		}
		renderStyle();
		
		addAttribute(CLASS,getClassByState());
		renderConfirmSubmit();
		if (!stateString.equals(DISABLED))
		{
			linkFunction = onClick + getButtonEventFunction() + ";" + getSendEventFunction();
			addAttribute(HREF,"#");
			addAttribute(ONCLICK,linkFunction);
		}
		endTag();
		append(value);
		endTag(LINK);	
		if (shortCutKey != null && linkFunction != null)
		{
			addShortCutKey(shortCutKey,linkFunction,true);
		}			
	}
}
