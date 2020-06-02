/*
 * Created on: 24/02/2005
 * Author:  user name
 * @version $Id: HelpPageTag.java,v 1.9 2005/04/07 06:50:11 shay Exp $
 */
package com.ness.fw.ui.taglib;

import java.util.ArrayList;
import java.util.Iterator;

import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.resources.ResourceUtils;
import com.ness.fw.ui.help.HelpConstants;
import com.ness.fw.ui.help.HelpModel;
import com.ness.fw.ui.help.HelpTreeNode;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class HelpPageTag extends UITag 
{
	private String mode;
	private String helpId;
	private int htmlRowIndex = 0;
	private boolean loadOnExpand = false;  
	private HelpModel helpModel;
	
	private final static String PARAM_NAME_HELP_ID = "helpId";
	private final static String PARAM_NAME_HELP_EVENT = "helpEvent";
	private final static String PARAM_NAME_HELP_TEXT = "helpText";
	private final static String PARAM_NAME_HELP_CONTENT = "helpContent";
	private final static String PARAM_NAME_HELP_MODE = "helpMode";
	private final static String PARAM_NAME_IS_USER_ADMIN = "isUserAdmin";
	
	private final static String EVENT_SAVE_HELP_PAGE = "save";
		
	private final static String JS_HELP_WINDOW_NAME = "helpWin"; 
	private final static String JS_FUNCTION_OPEN_TREE_NODE = "openTreeNode";
	private final static String JS_FUNCTION_OPEN_TREE_NODE_SUBMIT_EVENT = "openTreeNodeSubmitEvent";
	private final static String JS_FUNCTION_ADD_HELP_ITEM_LINK = "addHelpItemLinkEvent";	
	private final static String JS_FUNCTION_REMOVE_HELP_ITEM_LINK = "removeHelpItemLinkEvent";
	private final static String JS_FUNCTION_SHOW_HELP_ITEM = "showHelpItemEvent";
	private final static String JS_FUNCTION_SELECT_HELP_ITEM = "selectHelpItemEvent";
	private final static String JS_FUNCTION_SELECT_HELP_ITEM_SUBMIT = "selectHelpItemSubmitEvent";
	private final static String JS_FUNCTION_SELECT_HELP_ITEM_SPAN = "selectHelpItemSpan";
	private final static String JS_FUNCTION_SAVE_HELP_ITEM = "saveHelpItem";
	private final static String JS_FUNCTION_CREATE_NEW_ITEM = "createNewHelpItem";
	private final static String JS_FUNCTION_UPDATE_HELP_MENU = "opener.updateHelpMenu";
	private final static String JS_FUNCTION_PREVIEW = "previewHelpItemEvent";
	private final static String JS_FUNCTION_CANCEL_PREVIEW = "cancelPreviewHelpItemEvent";
	private final static String JS_FUNCTION_INIT_EDITOR = "initEditor";
	
	private final static String PROPERTY_KEY_SAVE_BUTTON = "ui.help.saveButton";
	private final static String PROPERTY_KEY_ADD_BUTTON = "ui.help.addButton";
	private final static String PROPERTY_KEY_REMOVE_BUTTON = "ui.help.removeButton";
	private final static String PROPERTY_KEY_CANCEL_BUTTON = "ui.help.cancelButton";
	private final static String PROPERTY_KEY_SHOW_BUTTON = "ui.help.showButton";
	private final static String PROPERTY_KEY_NEW_FILE_BUTTON = "ui.help.newFileButton";
	private final static String PROPERTY_KEY_PREVIEW_BUTTON = "ui.help.previewButton";
	private final static String PROPERTY_KEY_CANCEL_PREVIEW_BUTTON = "ui.help.cancelPreviewButton";
	private final static String PROPERTY_KEY_HELP_BUTTON_CLASS_NAME = "ui.help.button";
	private final static String PROPERTY_KEY_HELP_SCRIPTS_FILE = "ui.help.scripts.files";
	private final static String PROPERTY_KEY_HELP_CSS_FILE = "ui.help.css.files";
	private final static String PROPERTY_KEY_HELP_TREE_LOAD_ON_EXPAND = "ui.help.tree.loadOnExpand";
	private final static String PROPERTY_KEY_TREE_LEAF_IMAGE = "ui.help.tree.image.empty";
	private final static String PROPERTY_KEY_TREE_OPEN_IMAGE = "ui.help.tree.image.open";
	private final static String PROPERTY_KEY_TREE_CLOSE_IMAGE = "ui.help.tree.image.close";
	private final static String PROPERTY_KEY_TREE_DIV_CLASS_NAME = "ui.help.tree.mainDiv";
	private final static String PROPERTY_KEY_TREE_NODE_CLASS_NAME = "ui.help.tree.node";
	private final static String PROPERTY_KEY_TREE_SELECTED_NODE_CLASS_NAME = "ui.help.tree.node.selected";
	private final static String PROPERTY_KEY_FILE_NOT_FOUND_EDIT_MODE = "ui.help.fileNotFoundMessage.edit";
	private final static String PROPERTY_KEY_FILE_NOT_FOUND_VIEW_MODE = "ui.help.fileNotFoundMessage.view";
	private final static String PROPERTY_KEY_TITLE_CLASS_NAME = "ui.help.titleClassName";
	private final static String PROPERTY_KEY_LINK_CLASS_NAME = "ui.help.linkClassName";
	private final static String PROPERTY_KEY_CONTENT_DIV_CLASS_NAME = "ui.help.contentDivClassName";  
	private final static String PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE = "ui.help.helpItemDefaultTitle";
	private final static String PROPERTY_KEY_HELP_DIR_DEFAULT_TITLE = "ui.help.helpDirDefaultTitle";
	private final static String PROPERTY_KEY_HELP_ILLEGAL_ACTION = "ui.help.illegalAction";
	private final static String HELP_SERVLET_ADDRESS = "hs";
	private final static String PROPERTY_KEY_HELP_ITEM_NOT_FOUND = "ui.help.helpItemNotFound";
	private final static String PROPERTY_KEY_HELP_ITEM_NOT_FOUND_ADMIN = "ui.help.helpItemNotFound.admin";
	
	private final static String HTML_TREE_ATTRIBUTE_LEVEL = "level";
	private final static String HTML_TREE_ATTRIBUTE_VISIBLE = "visible";
	private final static String HTML_TREE_ATTRIBUTE_EXPANDED = "expanded";
	private final static String HTML_HELP_TABLE_ID = "helpTreeTable";
	private final static String HTML_HELP_SELECT_LINKS_ID = "helpSelectLinks";
	private final static String HTML_HELP_CONTENT_IFRAME_ID = "contentIframe";
	private final static String HTML_HELP_CONTENT_INITIAL_DIV_ID = "initialDiv";
	
	private final static int NUMBER_OF_SPACES_IN_LEVEL = 6;	
	private final static String LEVEL_SPACE = SPACE;

	protected void init() throws UIException
	{
		ignoreAuth = true;
		initAuth = false;
		helpModel = (HelpModel)getRequestContextValue(HelpConstants.REQUEST_ATTRIBUTE_NAME_HELP_MODEL);
		if (helpModel == null)
		{
			throw new UIException("help model is null");		
		}
		loadOnExpand = new Boolean(getUIProperty(PROPERTY_KEY_HELP_TREE_LOAD_ON_EXPAND)).booleanValue();
	}

	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException 
	{
		renderBodyStart();
		if (helpModel.isLegalReadAction() && helpModel.isLegalSaveAction())
		{
			renderMainTable();
		}
		//Error messages - read or write actios are not legal
		else
		{
			renderErrorTable();
		}
		renderBodyEnd();
	}
	
	/**
	 * Called in the begining of renderStartTag method.
	 * Used for initializing authorization in tha tag.
	 * @throws UIException
	 */
	protected void initAuth() throws UIException
	{
		if (initAuth)
		{
			elementAuthLevel = new ElementAuthLevel(ElementAuthLevel.AUTH_LEVEL_ALL,false);
		}
	}	
	
	protected void initLocalizedResources()
	{
		setLocalizedResources(ResourceUtils.getLocalizedResources(getLocale()));
	}	

	/**
	 * Renders the head tag,including scripts and css ,the start of the body tag<br>
	 * and the start of the form tag
	 *
	 */
	private void renderBodyStart()
	{
		startTagLn(HEAD,true);
		startTag(TITLE,true);
		if (helpModel.isHelpItemExist())
		{
			append(helpModel.getDisplayedHelpItemTitle() != null 
			? helpModel.getDisplayedHelpItemTitle() 
			: getLocalizedText(PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE));	
		}
		endTagLn(TITLE);
		renderHelpScripts();
		renderHelpCss();
		endTagLn(HEAD);
		startTag(BODY);
		addAttribute(DIR_BODY,getLocaleDirection());
		addAttribute(ONLOAD,getBodyOnLoadScript());
		endTagLn();
		startTag(FORM);
		addAttribute(ACTION,HELP_SERVLET_ADDRESS);
		addAttribute(METHOD,HTML_METHOD_POST);
		addAttribute(TARGET,JS_HELP_WINDOW_NAME + getHttpSession().getId());
		endTagLn();
	}
	
	private String getBodyOnLoadScript()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(WINDOW_NAME + JS_EQUALS + getSingleQuot(JS_HELP_WINDOW_NAME + getHttpSession().getId()) + JS_END_OF_LINE);
		if (helpModel.isEditMode())
		{
			sb.append(getFunctionCall(JS_FUNCTION_INIT_EDITOR) + JS_END_OF_LINE);
		}
		if (helpModel.isTitleFileChanged())
		{
			ArrayList jsParams = new ArrayList();
			jsParams.add(helpModel.getDisplayedHelpItemId());
			jsParams.add(helpModel.getDisplayedHelpItemTitle());
			sb.append(getFunctionCall(JS_FUNCTION_UPDATE_HELP_MENU,jsParams,true));
		}
		return sb.toString();
	}
	
	private void renderErrorTable() throws UIException
	{
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(BORDER,0);
		endTagLn();
		startTagLn(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,"100%");
		addAttribute(VALIGN,TOP);
		addAttribute(ALIGN,CENTER);
		addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TITLE_CLASS_NAME));
		endTag();
		append(getLocalizedText(PROPERTY_KEY_HELP_ILLEGAL_ACTION));
		endTag(CELL);
		endTagLn(ROW);
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(ALIGN,CENTER);
		addAttribute(HEIGHT,1);
		endTag();
		renderDefaultSubmitButton();
		renderCancelButton();
		endTag(CELL);
		endTag(ROW);
		endTagLn(TABLE);
	}
	
	private void renderMainTable() throws UIException
	{
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(BORDER,0);
		endTagLn();
		startTagLn(ROW,true);
		
		//The cell of the tree and its button
		startTag(CELL);
		addAttribute(WIDTH,200);
		endTagLn();
		
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTagLn();
		
		//Tree row
		startTagLn(ROW,true);		
		startTag(CELL);
		addAttribute(VALIGN,TOP);
		endTagLn();
		renderTree();
		endTagLn(CELL);
		endTagLn(ROW);
		
		//"show help content" button
		if (helpModel.isEditMode())
		{
			startTagLn(ROW,true);
			startTag(CELL);
			addAttribute(HEIGHT,1);
			endTag();
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_SHOW_BUTTON));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			renderShowButtonEvent();
			endTag();
			endTag(CELL);
			endTagLn(ROW);
		}		
		endTagLn(TABLE);
		endTagLn(CELL);
		
		//The cell of the table with title,editor and linked items area
		startTagLn(CELL,true);
		
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		endTagLn();
		
		if (helpModel.isHelpItemExist())
		{
			//Renders title
			if (helpModel.isFileExist())
			{
				startTagLn(ROW,true);
				renderTitle();
				endTagLn(ROW);	
			}
			
			//Renders help content
			startTag(ROW,true);
			if (helpModel.isFileExist())
			{
				renderHelpContent();
			}
			else
			{
				startTag(CELL);
				addAttribute(VALIGN,TOP);
				addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TITLE_CLASS_NAME));
				endTag();
				renderFileNotFound();
				endTag(CELL);
			}
			endTag(ROW);
			
			//Renders linked items area
			if (helpModel.isFileExist())
			{
				startTag(ROW,true);
				renderLinkedHelpItems();
				endTag(ROW);		
			}		
		}
		else
		{
			startTagLn(ROW,true);
			startTag(CELL);
			addAttribute(VALIGN,TOP);
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TITLE_CLASS_NAME));
			endTag();
			append(getLocalizedText(PROPERTY_KEY_HELP_ITEM_NOT_FOUND));
			if (helpModel.isEditMode())
			{
				append(BLANK + getLocalizedText(PROPERTY_KEY_HELP_ITEM_NOT_FOUND_ADMIN) + BLANK + helpModel.getDisplayedHelpItemId());
			}
			endTag(CELL);
			endTag(ROW);
		}
		endTagLn(TABLE);
				
		endTagLn(CELL);
		
		endTagLn(ROW);
		
		//The row of the "save" and "cancel" buttons(or "create new help item" button)
		renderBottomRowButtons();

		endTagLn(TABLE);
	}
	
	private void renderBottomRowButtons()
	{
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(ALIGN,CENTER);
		addAttribute(HEIGHT,1);
		addAttribute(COLSPAN,2);
		endTag();
		if (helpModel.isHelpItemExist())
		{
			if (helpModel.isFileExist())
			{
				renderSaveButton();
				renderPreviewButton();
			}
			else
			{
				renderNewFileButton();
			}
		}
		renderCancelButton();
		endTag(CELL);
		endTag(ROW);		
	}
	
	private void renderSaveButton()
	{
		if (helpModel.isEditMode() && !helpModel.isPreviewMode())
		{
			ArrayList jsParams = new ArrayList();
			jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
			jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
			jsParams.add(getSingleQuot(HTML_HELP_SELECT_LINKS_ID));
			jsParams.add(getSingleQuot(HelpConstants.REQUEST_PARAM_NAME_HELP_CONTENT));
			
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_SAVE_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_SAVE_HELP_ITEM,jsParams,false));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			endTag();		
		}
	}
	
	private void renderPreviewButton()
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
		if (helpModel.isPreviewMode())
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);	
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_CANCEL_PREVIEW_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_CANCEL_PREVIEW,jsParams,false));
			endTag();	
		}
		
		else if (helpModel.isEditMode())
		{
			jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
			jsParams.add(getSingleQuot(HTML_HELP_SELECT_LINKS_ID));
			jsParams.add(getSingleQuot(HelpConstants.REQUEST_PARAM_NAME_HELP_CONTENT));
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_PREVIEW_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_PREVIEW,jsParams,false));
			endTag();
		}
	}
	
	private void renderCancelButton()
	{
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_CANCEL_BUTTON));
		addAttribute(ONCLICK,CLOSE_WINDOW);
		addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
		endTag();	
	}
	
	private void renderDefaultSubmitButton()
	{
		if (helpModel.getDisplayedHelpItemId() != null)
		{
			startTag(INPUT);
			addAttribute(TYPE,SUBMIT);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_CANCEL_PREVIEW_BUTTON));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			endTag();			
		}
	}
	
	private void renderNewFileButton()
	{
		if (helpModel.isEditMode())
		{
			ArrayList jsParams = new ArrayList();
			jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
			jsParams.add(getSingleQuot(helpModel.getDisplayedHelpItemId()));
			jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
			
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_NEW_FILE_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_CREATE_NEW_ITEM,jsParams,false));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			endTag();	
		}
	}
		
	private void renderTitle()
	{
		String title = helpModel.getDisplayedHelpItemTitle() != null 
		? helpModel.getDisplayedHelpItemTitle() 
		: getLocalizedText(PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE);
		
		if (helpModel.isEditMode() && !helpModel.isPreviewMode())
		{
			startTag(CELL);
			addAttribute(VALIGN,TOP);
			addAttribute(HEIGHT,10);
			endTag();
			startTag(INPUT);
			addAttribute(NAME,HelpConstants.REQUEST_PARAM_NAME_HELP_TITLE);
			addAttribute(VALUE,title);
			addAttribute(STYLE,getStyleAttribute(WIDTH,"100%"));
			endTag();
			endTag(CELL);
		}
		else
		{
			startTag(CELL);
			addAttribute(VALIGN,TOP);
			addAttribute(HEIGHT,10);			
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TITLE_CLASS_NAME));
			endTag();
			append(title);
			endTag(CELL);
			if (helpModel.isPreviewMode())
			{
				renderHidden(HelpConstants.REQUEST_PARAM_NAME_HELP_TITLE,title);
			}
		}
	}

	private void renderHelpContent() throws UIException
	{
		startTag(CELL);
		//addAttribute(HEIGHT,"100%");
		addAttribute(VALIGN,TOP);
		endTag();
		if (helpModel.isEditMode() && !helpModel.isPreviewMode())
		{
			renderEditor();
		}
		else
		{
			startTag(DIV);
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_CONTENT_DIV_CLASS_NAME));
			endTag();
			append(helpModel.getDisplayedHelpItemContent());
			endTag(DIV);
			if (helpModel.isPreviewMode())
			{
				renderHiddenContentContainer();
			}
		}
		endTag(CELL);		
	}
	
	private void renderFileNotFound()
	{
		if (helpModel.isEditMode())
		{
			append(getLocalizedText(PROPERTY_KEY_FILE_NOT_FOUND_EDIT_MODE));
		}
		else
		{
			append(getLocalizedText(PROPERTY_KEY_FILE_NOT_FOUND_VIEW_MODE));
		}
	}
	
	private void renderEditor() throws UIException
	{
		
		RichTextEditorTag editorTag = new RichTextEditorTag();
		editorTag.setId(HTML_HELP_CONTENT_IFRAME_ID);
		append(getTagOutPut(editorTag));		
		renderHiddenContentContainer();
	}
	
	private void renderHiddenContentContainer()
	{
		startTag(TEXTAREA);
		addAttribute(ID,HTML_HELP_CONTENT_INITIAL_DIV_ID);
		addAttribute(STYLE,DISPLAY_NONE);
		if (helpModel.isPreviewMode())
		{
			addAttribute(NAME,HelpConstants.REQUEST_PARAM_NAME_HELP_CONTENT);
		}
		endTag();
		append(helpModel.getDisplayedHelpItemContent().trim());
		endTag(TEXTAREA);		
	}
	
	private void renderLinkedHelpItems()
	{
		startTag(CELL);
		addAttribute(HEIGHT,200);
		endTag();
		ArrayList linkedItems = helpModel.getDisplayedHelpItemLinks();
		ArrayList linkedItemsTitles = helpModel.getDisplayedHelpItemLinksTitles();
		//Edit mode
		if (helpModel.isEditMode() && !helpModel.isPreviewMode())
		{
			startTag(TABLE);
			endTag();
			startTag(ROW,true);
			startTag(CELL,true);
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_ADD_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_ADD_HELP_ITEM_LINK,HTML_HELP_SELECT_LINKS_ID));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			endTag();
			endTag(CELL);
			startTag(CELL);
			addAttribute(ROWSPAN,2);
			addAttribute(WIDTH,"100%");
			endTag();	
			startTag(SELECT);
			addAttribute(ID,HTML_HELP_SELECT_LINKS_ID);
			addAttribute(SIZE,2);
			addAttribute(STYLE,getStyleAttribute(HEIGHT,"100") + getStyleAttribute(WIDTH,"100%"));
			endTag();
			if (linkedItems != null)
			{
				for (int index = 0;index < linkedItems.size();index++)	
				{
					String helpItemTitle = (String)linkedItemsTitles.get(index);
					if (helpItemTitle == null)
					{
						helpItemTitle = getLocalizedText(PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE);
					}
					startTag(OPTION);
					addAttribute(VALUE,(String)linkedItems.get(index));
					endTag();
					append(helpItemTitle);
					endTag(OPTION);			
				}
			}
			endTag(SELECT);
			endTag(CELL);
			endTag(ROW);
			
			startTag(ROW,true);
			startTag(CELL,true);
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,getLocalizedText(PROPERTY_KEY_REMOVE_BUTTON));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_REMOVE_HELP_ITEM_LINK,HTML_HELP_SELECT_LINKS_ID));
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_HELP_BUTTON_CLASS_NAME));
			endTag();
			endTag(CELL);		
			endTag(ROW);
			endTag(TABLE);
		}
		//View mode
		else
		{
			if (linkedItemsTitles != null)
			{
				ArrayList jsParams = new ArrayList();
				jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
				jsParams.add("");
				jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
				startTag(TABLE,true);
				for (int index = 0;index < linkedItemsTitles.size();index++)
				{
					String helpItemTitle = (String)linkedItemsTitles.get(index);
					if (helpItemTitle == null)
					{
						helpItemTitle = getLocalizedText(PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE);
					}
					jsParams.set(1,getSingleQuot((String)linkedItems.get(index)));
					startTag(ROW,true);
					startTag(CELL);
					addAttribute(CLASS,getUIProperty(PROPERTY_KEY_LINK_CLASS_NAME));
					addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_SELECT_HELP_ITEM_SUBMIT,jsParams,false));
					endTag();
					append(helpItemTitle);
					endTag(CELL);
					endTag(ROW);
				}
				endTag(TABLE);				
			}
		}
		endTag(CELL);
	}
	
	private void renderTree() throws UIException 
	{
		startTag(DIV);
		addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TREE_DIV_CLASS_NAME));
		endTagLn();
		startTag(TABLE);
		addAttribute(ID,HTML_HELP_TABLE_ID);
		endTag();
		renderTreeNode(helpModel.getHelpTree(),0);
		endTagLn(TABLE);
		endTagLn(DIV);
	}
	
	private void renderTreeNode(HelpTreeNode helpTreeNode,int level)
	{	
		renderTreeNodeRow(helpTreeNode,level);
		Iterator childDirectories = helpTreeNode.getChildDirectories();
		if (childDirectories != null && (helpModel.isDirectoryExpanded(helpTreeNode.getId()) || !loadOnExpand))
		{
			while (childDirectories.hasNext())
			{
				renderTreeNode((HelpTreeNode)childDirectories.next(),level + 1);
			}
		}		
	}
	
	private void renderTreeNodeRow(HelpTreeNode helpTreeNode,int level)
	{
		//Getting the node type
		boolean isDirectory = helpTreeNode.getType() == HelpConstants.HELP_ITEM_TYPE_DIRECTORY;
		
		//Getting the multipleRoots property
		//boolean isMultipleRoots = helpModel.isHelpTreeMultipleRoots();
		
		startTag(ROW);
		addAttribute(HTML_TREE_ATTRIBUTE_LEVEL,level);
		addAttribute(ID,helpTreeNode.getId());
		renderExpandedAttributes(helpTreeNode);
		renderDisplayedAttribute(helpTreeNode);
		renderVisibleAttribute(helpTreeNode);
		endTagLn();
		startTagLn(CELL,true);
		
		startTag(TABLE);
		addAttribute(CELLPADDING,0);
		addAttribute(CELLSPACING,0);
		endTag();
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(VALIGN,TOP);
		endTagLn();
		renderSpaces(level);
		renderImage(helpTreeNode,isDirectory);
		endTagLn(CELL);
		startTag(CELL);
		endTag();
		renderNodeTitle(helpTreeNode,isDirectory);
		endTagLn(CELL);
		endTagLn(ROW);
		endTagLn(TABLE);
		
		endTagLn(CELL);
		endTagLn(ROW);		
	}
	
	private void renderNodeTitle(HelpTreeNode helpTreeNode,boolean isDirectory)
	{
		String nodeTitle = helpTreeNode.getDescription();
		if (isDirectory)
		{
			if (nodeTitle == null)
			{
				nodeTitle = getLocalizedText(PROPERTY_KEY_HELP_DIR_DEFAULT_TITLE);	
			}
			else
			{
				nodeTitle = getLocalizedText(nodeTitle);	
			}
		}
		else if (nodeTitle == null)
		{
			nodeTitle = getLocalizedText(PROPERTY_KEY_HELP_ITEM_DEFAULT_TITLE);
		}
		startTag(SPAN);
		addAttribute(ID,helpTreeNode.getId() + SPAN.toUpperCase());
		
		if (helpTreeNode.getId().equals(helpModel.getDisplayedHelpItemId()))
		{
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TREE_SELECTED_NODE_CLASS_NAME));
		}
		else
		{
			addAttribute(CLASS,getUIProperty(PROPERTY_KEY_TREE_NODE_CLASS_NAME));
		}
		
		//Render onclick event only for help items
		if (!isDirectory)
		{
			renderNodeTitleEvent(helpTreeNode);
		}
		endTag();
		append(nodeTitle);
		endTag(SPAN);						
	}
	
	private void renderNodeTitleEvent(HelpTreeNode helpTreeNode)
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
		jsParams.add(getSingleQuot(helpTreeNode.getId()));
		
		//In edit mode,select help item for adding it to the linked items list
		//(without sending an event).
		if (helpModel.isEditMode())
		{
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_SELECT_HELP_ITEM,jsParams,false));
		}
		//In view mode,send event to the server in order to show the content
		//of the selected help item. 
		else
		{
			jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
			addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_SELECT_HELP_ITEM_SUBMIT,jsParams,false));
		}
	}
	
	private void renderShowButtonEvent()
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
		jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
		addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_SHOW_HELP_ITEM,jsParams,false));	
	}
	
	private void renderExpandedAttributes(HelpTreeNode helpTreeNode)
	{
		addAttribute(HTML_TREE_ATTRIBUTE_EXPANDED,getExpandedString(helpTreeNode));
	}
	
	private String getExpandedString(HelpTreeNode helpTreeNode)
	{
		return String.valueOf(helpModel.isDirectoryExpanded(helpTreeNode.getId()));
	}
	
	private void renderDisplayedAttribute(HelpTreeNode helpTreeNode)
	{
		HelpTreeNode parent = (HelpTreeNode)helpTreeNode.getParent();
		if (parent != null && !helpModel.isDirectoryExpanded(parent.getId()))
		{
			addAttribute(STYLE,DISPLAY_NONE);	
		}		
	}
		
	private void renderVisibleAttribute(HelpTreeNode helpTreeNode)
	{
		addAttribute(HTML_TREE_ATTRIBUTE_VISIBLE,getVisibleString((HelpTreeNode)helpTreeNode.getParent()));
	}
		
	private String getVisibleString(HelpTreeNode parentHelpTreeNode)
	{
		if (parentHelpTreeNode == null)
		{
			return String.valueOf(true);
		}
		return String.valueOf(helpModel.isDirectoryExpanded(parentHelpTreeNode.getId()));
	}
	/**
	 * Renders the image of the node
	 * @param isDirectory
	 */
	private void renderImage(HelpTreeNode helpTreeNode,boolean isDirectory)
	{
		startTag(IMAGE);
		addAttribute(SRC,getLocalizedImagesDir() + getImageName(helpTreeNode,isDirectory));
		renderImageClick(isDirectory);
		endTag();
	}
	
	/**
	 * Returns the image name of the node
	 * @param helpTreeNode
	 * @param isDirectory
	 * @return
	 */
	private String getImageName(HelpTreeNode helpTreeNode,boolean isDirectory)
	{
		if (!isDirectory)
		{
			return getSystemProperty(PROPERTY_KEY_TREE_LEAF_IMAGE);
		}
		else
		{
			if (helpModel.isDirectoryExpanded(helpTreeNode.getId()))
			{
				return getSystemProperty(PROPERTY_KEY_TREE_OPEN_IMAGE);
			}
			else
			{
				return getSystemProperty(PROPERTY_KEY_TREE_CLOSE_IMAGE);
			}
		}
	}
	
	/**
	 * Returns the node's onclick string 
	 * @param isDirectory
	 */
	private void renderImageClick(boolean isDirectory)
	{
		if (isDirectory)
		{
			ArrayList jsParams = new ArrayList();
			String jsFunction = JS_FUNCTION_OPEN_TREE_NODE;
			//Load the sub directories when the node image is clicked
			if (loadOnExpand)
			{
				jsParams.add(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO);
				jsParams.add(getSingleQuot(HTML_HELP_SELECT_LINKS_ID));
				jsParams.add(getSingleQuot(HelpConstants.REQUEST_PARAM_NAME_HELP_CONTENT));
				jsFunction = JS_FUNCTION_OPEN_TREE_NODE_SUBMIT_EVENT;
			}
			jsParams.add(getSingleQuot(HTML_HELP_TABLE_ID));
			jsParams.add(String.valueOf(htmlRowIndex));
			jsParams.add(getSingleQuot(getSystemProperty(PROPERTY_KEY_TREE_OPEN_IMAGE)));
			jsParams.add(getSingleQuot(getSystemProperty(PROPERTY_KEY_TREE_CLOSE_IMAGE)));
			addAttribute(ONCLICK,getFunctionCall(jsFunction,jsParams,false));				
		}
		htmlRowIndex++;
	}
			
	private void renderSpaces(int level)
	{
		append(getSpaces(level));
	}

	private String getSpaces(int level)
	{
		StringBuffer sb = new StringBuffer(50);
		for (int i = 0; i < level; i++)
		{
			for (int j = 0;j < NUMBER_OF_SPACES_IN_LEVEL;j++)
			{
				sb.append(LEVEL_SPACE);
			}
		}
		return sb.toString();
	}
	
	private void renderBodyEnd()
	{
		renderHidden(HelpConstants.REQUEST_PARAM_NAME_HELP_INFO,helpModel.getInitialModelInfo());
		appendln();
		if (helpModel.isEditMode())
		{
			renderSelectHelpItemSpan();
		}
		endTag(FORM);
		endTag(BODY);		
	}
	
	private void renderSelectHelpItemSpan()
	{
		startTag(SCRIPT,true);
		renderFunctionCall(JS_FUNCTION_SELECT_HELP_ITEM_SPAN,helpModel.getDisplayedHelpItemId());
		endTagLn(SCRIPT);
	}
	
	private void renderHelpScripts()
	{
		renderScriptFiles(PROPERTY_KEY_HELP_SCRIPTS_FILE);
	}
	
	private void renderHelpCss()
	{
		renderCssFiles(PROPERTY_KEY_HELP_CSS_FILE);
	}
	
	private String getHtmlFileName()
	{
		return helpId;
	}
	
	/* (non-Javadoc)
	 * @see com.ness.fw.ui.taglib.UITag#renderEndTag()
	 */
	protected void renderEndTag() throws UIException 
	{
		removeRequestContextValue(PARAM_NAME_HELP_ID);
		removeRequestContextValue(PARAM_NAME_IS_USER_ADMIN);
		removeRequestContextValue(PARAM_NAME_HELP_CONTENT);
	}
	
	protected void resetTagState()
	{
		super.resetTagState();
		helpId = null;
		mode = null;
		helpModel = null;
		htmlRowIndex = 0;		
	}
}
