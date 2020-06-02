/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.common.exceptions.UIException;

public class HelpContainerTag extends UITag 
{
	private static final String JS_SHOW_HELP_MENU = "showHelpMenu";

	private static final String HELP_CONTAINER_DIV_ID = "helpLinksDiv";
	
	private static final String PROPERTY_KEY_BUTTON_IMAGE = "ui.help.container.image";
	private static final String PROPERTY_KEY_OPEN_AS_DIALOG = "ui.help.openHelpMenuAsDialog";
	private static final String PROPERTY_KEY_DIALOG_PARAMS = "ui.help.dialog.parameters";
	
	private boolean openHelpMenuAsDialog = false;
	private String dialogParams;
	
	protected void init() throws UIException
	{
		if (openHelpMenuAsDialog)
		{
			openHelpMenuAsDialog = new Boolean(getUIProperty(PROPERTY_KEY_OPEN_AS_DIALOG)).booleanValue();
		}
		dialogParams = getUIProperty(PROPERTY_KEY_DIALOG_PARAMS);
		initAuth = false;
		ignoreAuth = true;				
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(String.valueOf(openHelpMenuAsDialog));
		jsParams.add(getSingleQuot(dialogParams));
		startTag(IMAGE);
		addAttribute(SRC,getLocalizedImagesDir() + getUIProperty(PROPERTY_KEY_BUTTON_IMAGE));
		addAttribute(ONCLICK,getFunctionCall(JS_SHOW_HELP_MENU,jsParams,false));
		addAttribute(STYLE,CURSOR_HAND);
		endTag();
	}
	
	protected void resetTagState()
	{
		super.resetTagState();
		openHelpMenuAsDialog = true;
		dialogParams = null;
	}
		
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
	}

	/**
	 * @param b
	 */
	public void setOpenHelpMenuAsDialog(boolean openHelpMenuAsDialog) 
	{
		this.openHelpMenuAsDialog = openHelpMenuAsDialog;
	}

}
