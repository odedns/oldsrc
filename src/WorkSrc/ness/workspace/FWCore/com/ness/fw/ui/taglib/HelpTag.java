/*
 * Created on 23/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpTag extends UITag
{
	private static final String JS_SHOW_HELP_SCREEN = "showHelpScreen";
	private static final String JS_NEW_HELP_MENU_ITEM = "new HelpMenuItem";
	
	private static final String JS_VAR_HELP_IDS_ARRAY_NAME = "helpIdsArray";

	private static final String PROPERTY_KEY_OPEN_AS_DIALOG = "ui.help.openHelpMenuAsDialog";
	private static final String PROPERTY_KEY_DIALOG_PARAMS = "ui.help.dialog.parameters";
	
	private String helpId;
	private boolean renderButton = false;
	private boolean addIdToContainer = true;
	private boolean openHelpMenuAsDialog = true;
	private String dialogParams;

	protected void init() throws UIException
	{
		openHelpMenuAsDialog = new Boolean(getUIProperty(PROPERTY_KEY_OPEN_AS_DIALOG)).booleanValue();	
		dialogParams = getUIProperty(PROPERTY_KEY_DIALOG_PARAMS);		
		initAuth = false;
		ignoreAuth = true;		
	}
		
	protected void validateAttributes() throws UIException
	{
		super.validateAttributes();
		if (!renderButton && !addIdToContainer)
		{
			throw new UIException("attributes renderButton and addIdToContainer of help tag with id " + helpId + " cannot be both set to false");
		}
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		if (renderButton)
		{
			renderHelpButton();
		}
		if (addIdToContainer)
		{
			renderAddToContainerJscript();
		}
	}
	
	private void renderHelpButton() throws UIException
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(getSingleQuot(helpId));
		jsParams.add(String.valueOf(openHelpMenuAsDialog));
		jsParams.add(getSingleQuot(dialogParams));
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(VALUE,getLocalizedHelpTitle(helpId));
		addAttribute(ONCLICK,getFunctionCall(JS_SHOW_HELP_SCREEN,jsParams,false));
		endTag();	
	}
	
	private void renderAddToContainerJscript() throws UIException
	{
		startTag(SCRIPT,true);
		addValueToArray(JS_VAR_HELP_IDS_ARRAY_NAME,getHelpMenuItemJscript(helpId),false);
		endTagLn(SCRIPT);
	}
	
	private String getHelpMenuItemJscript(String helpId) throws UIException
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(helpId);
		jsParams.add(getLocalizedHelpTitle(helpId));
		return getFunctionCall(JS_NEW_HELP_MENU_ITEM,jsParams);
	}
	
	private String getLocalizedHelpTitle(String helpId) throws UIException
	{
		String fileName = "/help/" + getLocaleImagesDirectory() + "/helpTitles";
		try 
		{
			return ResourceUtils.load(fileName).getProperty(helpId);
		}
		catch (ResourceException re)
		{
			throw new UIException(fileName +  " was not found");
		}
		
	}

	protected void resetTagState()
	{
		super.resetTagState();
		helpId = null;
		renderButton = false;
		addIdToContainer = true;
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
	 * @return
	 */
	public String getHelpId() 
	{
		return helpId;
	}

	/**
	 * @param string
	 */
	public void setHelpId(String string) 
	{
		helpId = string;
	}
	/**
	 * @param b
	 */
	public void setAddIdToContainer(boolean b) 
	{
		addIdToContainer = b;
	}

	/**
	 * @param b
	 */
	public void setRenderButton(boolean b) 
	{
		renderButton = b;
	}

}
