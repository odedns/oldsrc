package com.ness.fw.ui.taglib;

import java.util.ArrayList;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.ListModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.ListTagData;
import com.ness.fw.ui.events.CustomEvent;

public class ListTag extends AbstractListTag
{
	protected final static String JS_EXPAND_SINGLE = "expandSingleList";
	protected final static String JS_EXPAND_SINGLE_SUBMIT = "expandSingleListAndSubmit";
	protected final static String JS_EXPAND_SINGLE_CALL_BACK = "expandSingleListCallBack";
	protected final static String JS_EXPAND_MULTIPLE = "expandMultipleList";
	protected final static String JS_EXPAND_MULTIPLE_SUBMIT = "expandMultipleListAndSubmit";
	protected final static String JS_EXPAND_MULTIPLE_CALL_BACK = "expandMultipleListCallBack";
	protected final static String JS_DO_EVENTS = "doEvents";
	protected final static String JS_NEW_L2L_OBJECT = "new ListToList";
	protected final static String JS_NEW_LIST_OPTION = "new ListOption";
	protected final static String JS_LIST_SUBMIT_EVENT = "listSubmitEvent";	
	protected final static String JS_LIST_COMPLEX_EVENT = "listComplexEvent";
	protected final static String JS_LIST_EVENT = "listEvent";
	protected final static String JS_LIST_TO_LIST_EVENT = "listToListEvent";
	protected final static String JS_LIST_TEXT_EVENT = "listTextBoxEvent";
	protected final static String JS_LIST_TEXT_KEY_PRESS_EVENT = "listTextBoxKeyPressEvent";
	protected final static String JS_LIST_MOVE_UP = "selectMoveUp";
	protected final static String JS_LIST_MOVE_DOWN = "selectMoveDown";
	protected final static String JS_LIST_REORDER_KEYS = "listReorderKeysEvent";
	
	protected final static String JS_L2L_OBJECT_TYPE_SRC = "1";
	protected final static String JS_L2L_OBJECT_TYPE_TRG = "2";
	protected final static String JS_L2L_OBJECT_TYPE_ALL = "4";
	
	private final static String JS_LIST_OBJECT_FILTER = "0";
	private final static String JS_LIST_OBJECT_RESET = "1";
	
	protected String orderButtonsClassName;
	protected String resetButtonClassName;
	protected String searchButtonClassName;
	protected String filterButtonClassName;
	protected String searchTextClassName;
	protected String openDialogButtonClassName;
	
	protected final static String L2L_SRC_TEXT_SUFFIX = "SrcText";
	protected final static String L2L_TRG_TEXT_SUFFIX = "TrgText";
	protected final static String L2L_SRC_SERACH_ACTION_SUFFIX = "SrcSearchAction";
	protected final static String L2L_TRG_SERACH_ACTION_SUFFIX = "TrgSearchAction";
	protected final static String L2L_SRC_SEARCH_TYPE_SUFFIX = "SrcSearchType";
	protected final static String L2L_TRG_SEARCH_TYPE_SUFFIX = "TrgSearchType";	
	protected final static String L2L_SRC_TO_TRG_ALL_BUTTON_SUFFIX = "SrcToTrgAll";
	protected final static String L2L_SRC_TO_TRG_BUTTON_SUFFIX = "SrcToTrg";
	protected final static String L2L_TRG_TO_SRC_ALL_BUTTON_SUFFIX = "TrgToSrcAll";
	protected final static String L2L_TRG_TO_SRC_BUTTON_SUFFIX = "TrgToSrc";
	protected final static String L2L_SRC_DOWN_BUTTON_SUFFIX = "SrcDown";
	protected final static String L2L_TRG_DOWN_BUTTON_SUFFIX = "TrgDown";
	protected final static String L2L_SRC_UP_BUTTON_SUFFIX = "SrcUp";
	protected final static String L2L_TRG_UP_BUTTON_SUFFIX = "TrgUp";
	protected final static String L2L_SRC_LIST_SUFFIX = "SrcList";
	protected final static String L2L_TRG_LIST_SUFFIX = "TrgList";
	protected final static String L2L_SRC_ARRAY_SUFFIX = "SrcArr";
	protected final static String L2L_TRG_ARRAY_SUFFIX = "TrgArr";
	protected final static String L2L_JSOBJECT_SUFFIX = "list";
	protected final static String L2L_ID = "listComponent";
	protected final static String LIST_LABELS_ARRAY_SUFFIX = "Labels";
	private final static String DEFAULT_LIST_WIDTH = "100%";
	private final static String DEFAULT_LIST_HEIGHT = "100%";
	private final static String LIST_COMPONENTS_ARRAY = "listComponents";
	
	protected final static String[] LIST_SINGLE_EXPANDED_LABELS_ARRAY = 
	{
			"ui.list.approveButton",
			"ui.list.cancelSelectionButton",
			"ui.list.cancelButton",
			"ui.list.search",
			"ui.list.search.filter",
			"ui.list.search.start",
			"ui.list.search.contains",
			"ui.list.search.end"			
	};
	
	protected final static String[] LIST_MULTIPLE_EXPANDED_LABELS_ARRAY = 
	{
		"ui.list.srcToTrgButton",
		"ui.list.srcToTrgAllButton",
		"ui.list.trgToSrcButton",
		"ui.list.trgToSrcAllButton",
		"ui.list.downButton",
		"ui.list.upButton",
		"ui.list.approveButton",
		"ui.list.cancelButton",
		"ui.list.search",			
		"ui.list.search.filter",		
		"ui.list.search.start",
		"ui.list.search.contains",
		"ui.list.search.end"			
	};
	
	
	/**
	 * type of the component
	 * single - standart combo(single value)
	 * type - standart list(type value)
	 * singleExpand - text box and button that opens a dialog which contains 
	 * a list for selecting a value for the text box.
	 * multipleExpand - list and button that opens a dialog which contains 
	 * a listToList component 
	 * listToList
	 */
	protected String type = UIConstants.LIST_TYPE_SINGLE;
	
	/**
	 * the html size of each select in the tag
	 * default is 1(combo)
	 */
	protected int size = 1;
	
	/**
	 * Indicates where to search or filter 
	 * 0 - no search or filter
	 * 1 - search or filter the source select
	 * 2 - search or filter the target select
	 * 3 - search or filter both selects
	 * Relevant in types : listToList
	 */
	protected int searchIn = UIConstants.LIST_SEARCH_IN_NONE;
	
	/**
	 * Indicates the type of the search or filter - starts in,contains,end with
	 * 0 - no search or filter
	 * 1 - start in
	 * 2 - contains
	 * 3 - end with
	 * Relevant in types : listToList
	 */
	protected int searchType = UIConstants.LIST_SEARCH_TYPE_ALL;
	protected int defaultSearchType = UIConstants.LIST_SEARCH_TYPE_START;
	
	/**
	 * Indicates the action when the list component is searched
	 * 0 - no search or filter
	 * 1 - search action
	 * 2 - filter action
	 * 3 - search and filter 
	 */
	protected int searchAction = UIConstants.LIST_SEARCH_ACTION_ALL;
	protected int defaultSearchAction = UIConstants.LIST_SEARCH_ACTION_SEARCH;

	/**
	 * indicates the type of refresh for elements in the source list,when 
	 * opening a window for searching
	 * 0 - no refresh,never submit a request for refreshed list elements
	 * 1 - always submit a request for refreshed list elements
	 * 2 - submit one request for refreshed list elements and from then 
	 * always use those elements
	 * Relevant in types : singleExpand and multipleExpand
	 */
	protected int refreshType = UIConstants.LIST_REFRESH_TYPE_ONCE;
	
	/**
	 * Indicates if component is opened in a modal dialog
	 * Relevant in types : listToList
	 */
	protected boolean controlButtons = false;
	
	/**
	 * Indicates if list to list component should render order buttons,which
	 * moves an item up or down the list.
	 * Relevant in types : listToList
	 */
	protected boolean orderButtons = false;
	
	/**
	 * optional event for sending to the server when selecting value in the list
	 * Relevant in types : multiple and single
	 */
	protected String changeEventName;
	
	/**
	 * optional event for sending to the server when pressing the button
	 * Relevant in types : multipleExpand and singleExpand
	 */
	protected String expandEventName;
				
	protected CustomEvent changeCustomEvent;
	
	/**
	 * the title of empty value of a combo(type single) in a mandatory state
	 * Relevant in types : single
	 */
	protected String mandatoryPrompt;
	
	/**
	 * if true - show the mandatoryPrompt
	 */
	protected boolean addMandatoryPrompt = true;
	
	
	/**
	 * the title of empty value of a combo(type single)
	 * Relevant in types : single
	 */
	protected String optionalPrompt;
	
	/**
	 * if true - show the optionalPrompt
	 */
	protected boolean addOptionalPrompt = true;
	
	/**
	 * script for onchange event
	 */
	protected String onChange = "";
	
	/**
	 * title for the general items list
	 */
	protected String srcListTitle;
	
	protected String srcListTitleClassName;
	/**
	 * title for the selected items list
	 */
	protected String trgListTitle;

	protected String trgListTitleClassName;
	
	/**
	 * 
	 */
	protected String expanderDialogParams = " ";
	
	/**
	 * 
	 */
	protected String expanderImage;
	
	/**
	 * title of the button which opens an expander dialog,relevant for types
	 * singleExpander and multipleExpander.
	 */
	protected String expanderButtonTitle;
	
	/**
	 * title of the dialog which is opended by the expander button,relevant for types
	 * singleExpander and multipleExpander.
	 */
	protected String expanderTitle = " ";
		
	private boolean editable = false;
	
	
	private boolean sourcSearchRendered = false;
	private boolean targetSearchRendered = false;
	private boolean singleComponent = false;
	
	/**
	 * if true - open dialog window when a key is pressed in the textfield of singleExpanded type 
	 */
	protected boolean openOnKeyPress = false;
	
	
	public ListTag()
	{
		dirtable = true;
	}
			
	/**
	 * Initialize the list model and other properties of the list tag
	 */
	protected void initModel() throws UIException
	{
		//getting the list model from the context
		if (listModel == null)
		{
			listModel = (ListModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
		}
		//throw exception if model does mot exist
		if (listModel == null)
		{
			throw new UIException("no list model exists in context's field " + id);
		}
						
		//initialize the changeCustomEvent for this list tag
		changeCustomEvent = new CustomEvent();		
		initEvents();
		
		//initialize the state of this list tag
		initState();
		
		if (isEventRendered(changeCustomEvent.getEventType()))
		{	
			stateString = "";
		}
		else
		{
			stateString = DISABLED;
		}
				
		//initialize more properties for this tag
		//search for errors
		if (FlowerUIUtil.isErrorField(getHttpRequest(),id))
		{
			isError = true;
		}
		//select default key		
		if (type.equals(UIConstants.LIST_TYPE_SINGLE) && !addOptionalPrompt && !listModel.hasSelectedKeys() && !listModel.isEmpty())
		{
			listModel.setSelectedKeyByKeyOrder(0);
		}
		//set inputType from model
		if (listModel.getInputType() != null)
		{
			inputType = listModel.getInputType();
		}
		//set source list title
		if (srcListTitle == null)
		{
			srcListTitle = listModel.getSrcListTitle();
		}
		if (srcListTitle != null)
		{
			srcListTitle = getLocalizedText(srcListTitle);
		}
		//set target list title
		if (trgListTitle == null)
		{
			trgListTitle = listModel.getTrgListTitle();
		}
		if (trgListTitle != null)
		{
			trgListTitle = getLocalizedText(trgListTitle);
		}				
	}
	
	protected void initEvents() throws UIException
	{
		if (!type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) && !type.equals(UIConstants.LIST_TYPE_LABEL))
		{
			if (changeEventName == null)
			{				
				if (listModel.getListOnChangeEvent() != null)
				{
					//initialize change event
					changeCustomEvent = listModel.getListOnChangeEvent();
			
					//check event's flags
					checkDirtyFlag(changeCustomEvent);
				}
			}
			else
			{
				changeCustomEvent.setEventName(changeEventName);
				changeCustomEvent.setWindowExtraParams(expanderDialogParams);
			}
		}
	}
		
	protected void initAttributes()
	{
		super.initAttributes();
		if (tagData != null)
		{
			ListTagData listTagData = (ListTagData)tagData;
			if (listTagData.getType() != null)
			{
				setType(listTagData.getType());
			}
			if (listTagData.getRefreshType() != null)
			{
				setRefreshType(listTagData.getRefreshType().intValue());
			}
			if (listTagData.getSearchType() != null)
			{
				setSearchIn(listTagData.getSearchType().intValue());
			}
			if (listTagData.getSize() != null)
			{
				setSize(listTagData.getSize().intValue());
			}
			if (listTagData.getChangeEventName() != null)
			{
				setChangeEventName(listTagData.getChangeEventName());
			}
			if (listTagData.getExpandEventName() != null)
			{
				setExpandEventName(listTagData.getExpandEventName());
			}
			if (listTagData.getSrcListTitle() != null)
			{
				setSrcListTitle(listTagData.getSrcListTitle());
			}
			if (listTagData.getSrcListTitleClassName() != null)
			{
				setSrcListTitleClassName(listTagData.getSrcListTitleClassName());
			}
			if (listTagData.getTrgListTitle() != null)
			{
				setTrgListTitle(listTagData.getTrgListTitle());
			}
			if (listTagData.getTrgListTitleClassName() != null)
			{ 
				setTrgListTitleClassName(listTagData.getTrgListTitleClassName());
			}
			if (listTagData.getMandatoryPrompt() != null)
			{
				setMandatoryPrompt(listTagData.getMandatoryPrompt());
			}
			if (listTagData.getOptionalPrompt() != null)
			{
				setOptionalPrompt(listTagData.getOptionalPrompt());
			}
			if (listTagData.isAddMandatoryPrompt() != null)
			{
				setAddMandatoryPrompt(listTagData.isAddMandatoryPrompt().booleanValue());
			}
			if (listTagData.isAddOptionalPrompt() != null)
			{
				setAddOptionalPrompt(listTagData.isAddOptionalPrompt().booleanValue());
			}
			if (listTagData.getExpanderTitle() != null)
			{
				setExpanderTitle(listTagData.getExpanderTitle());
			}
			if (listTagData.getExpanderButtonTitle() != null)
			{
				setExpanderButtonTitle(listTagData.getExpanderButtonTitle());
			}
			if (listTagData.getExpanderImage() != null)
			{
				setExpanderImage(listTagData.getExpanderImage());
			}
			if (listTagData.getExpanderDialogParams() != null)
			{
				setExpanderDialogParams(listTagData.getExpanderDialogParams());
			}
			if (listTagData.isOpenOnKeyPress() != null)
			{ 
				setOpenOnKeyPress(listTagData.isOpenOnKeyPress().booleanValue());
			}
			if (listTagData.isEditable() != null)
			{
				setEditable(listTagData.isEditable().booleanValue());
			}
			if (listTagData.isControlButtons() != null)
			{
				setControlButtons(listTagData.isControlButtons().booleanValue());
			}
		}
	}
		
	protected void validateAttributes() throws UIException
	{
		super.validateAttributes();
		if (!editable && listModel.getEditableValue() != null && !listModel.getEditableValue().equals(""))
		{
			throw new UIException("editable value of list model is not allowed to be set when editable attribute of list tag is false in tag " + id);
		}
		if (editable && openOnKeyPress)
		{
			throw new UIException("openOnKeyPress attribute of list tag is not allowed to be set to true when editable attribute of list tag is true in tag " + id);
		}
		if (editable && !type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED))
		{
			throw new UIException("type attribute of list tag must be set to singleExpanded when editable attribute of list tag is true in tag " + id);
		}
		if (listModel.isMultipleSelectionType())
		{
			if (type.equals(UIConstants.LIST_TYPE_LABEL) ||
				type.equals(UIConstants.LIST_TYPE_SINGLE) ||
				type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED)) 
			{
				throw new UIException("type attribute [" + type + "] of tag list with " + id + " is incompatible with the selectionType of the ListModel: [" + listModel.getSelectionType() + "]");
			}
		}
		else
		{
			if (type.equals(UIConstants.LIST_TYPE_MULTIPLE) ||
				type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED) ||
				type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST)) 
			{
				throw new UIException("type attribute [" + type + "] of tag list with " + id + " is incompatible with the selectionType of the ListModel: [" + listModel.getSelectionType() + "]");
			}			
		}
	}
	
	protected void initCss()
	{
		super.initCss();
		orderButtonsClassName = getUIProperty("ui.list.listToList.orderButtons");
		if (stateString.equals(DISABLED))
		{
			orderButtonsClassName += "Disabled";
		}
		resetButtonClassName = getUIProperty("ui.list.listToList.resetButtons");
		searchButtonClassName = getUIProperty("ui.list.listToList.searchButtons");
		filterButtonClassName = getUIProperty("ui.list.listToList.filterButtons");
		searchTextClassName = getUIProperty("ui.list.searchText");
		openDialogButtonClassName = initUIProperty(openDialogButtonClassName,"ui.list.openDialogButton");
		srcListTitleClassName = initUIProperty(srcListTitleClassName,"ui.list.srcListTitle");
		trgListTitleClassName = initUIProperty(trgListTitleClassName,"ui.list.trgListTitle");
		expanderButtonTitle = initUIProperty(expanderButtonTitle,"ui.list.expanderButtonTitle");
		if (searchType == 0)
		{
			searchType = defaultSearchType;
		}
		if (searchAction == 0)
		{
			searchAction = defaultSearchAction;
		}
	}
	
	/**
	 * @see com.ness.fw.ui.taglib.AbstractInputTag#renderStartTag()
	 */
	protected void renderStartTag() throws UIException
	{
		if (type.equals(UIConstants.LIST_TYPE_LABEL))
		{
			renderListLabel();
		}
		else
		{
			sourcSearchRendered = isSearchRendered(true);
			targetSearchRendered = isSearchRendered(false);
			singleComponent = isSingleComponent();
			optionalPrompt = optionalPrompt == null ? "ui.list.optionalPrompt" : optionalPrompt;
			mandatoryPrompt = mandatoryPrompt == null ? "ui.list.mandatoryPrompt" : mandatoryPrompt;
			renderListTable();
		}
		renderHiddenField();
		listModel.setAuthLevel(getAuthLevel());
	}	
		
	/**
	 * returns the tag css prefix
	 * @return the tag css prefix 
	 */
	protected String getDefaultCssPrefix()
	{
		return getUIProperty("ui.list." + type + ".prefix"); 
	}
	
	protected void resetTagState()
	{
		orderButtonsClassName = null;
		resetButtonClassName = null;
		searchButtonClassName = null;
		filterButtonClassName = null;
		searchTextClassName = null;
		openDialogButtonClassName = null;
		type = UIConstants.LIST_TYPE_SINGLE;
		size = 1;
		searchIn = UIConstants.LIST_SEARCH_IN_NONE;
		refreshType = UIConstants.LIST_REFRESH_TYPE_ONCE;
		controlButtons = false;
		changeEventName = null;
		mandatoryPrompt = null;
		addMandatoryPrompt = true;
		optionalPrompt = null;
		addOptionalPrompt = true;
		onChange = "";
		srcListTitle = null;	
		srcListTitleClassName = null;
		trgListTitle = null;
		trgListTitleClassName = null;
		expanderDialogParams = " ";	
		expanderImage = null;
		expanderButtonTitle = null;
		expanderTitle = " ";
		stateString = "";
		openOnKeyPress = false;
		singleComponent = false;
		changeCustomEvent = null;
		searchIn = UIConstants.LIST_SEARCH_IN_NONE;
		searchType = 0;
		searchAction = 0;
		defaultSearchType = UIConstants.LIST_SEARCH_TYPE_START;
		defaultSearchAction = UIConstants.LIST_SEARCH_ACTION_SEARCH;
		targetSearchRendered = false;
		sourcSearchRendered = false;
		super.resetTagState();	
	}
				
	/**
	 * @see com.ness.fw.ui.taglib.AbstractInputTag#renderEndTag()
	 */
	protected void renderEndTag()
	{	
		
	}	
			
	protected void renderMultipleExpandSubmitEvent() throws UIException
	{
		if (expandEventName == null)
		{
			throw new UIException("expandEventName is null in tag list id " + id);
		}
		renderFunctionCall(JS_EXPAND_MULTIPLE_SUBMIT,getComponentId() + COMMA + getComponentId(),false);
	}

	protected void renderSingleExpandSubmitEvent() throws UIException
	{
		if (expandEventName == null)
		{
			throw new UIException("expandEventName is null in tag list id " + id);
		}		
		renderFunctionCall(JS_EXPAND_SINGLE_SUBMIT,getSingleQuot(id) + COMMA + getComponentId() + COMMA + getComponentId(),false);
	}	
	
	protected void renderMultipleExpandEvent() throws UIException
	{
		renderFunctionCall
		(
			JS_EXPAND_MULTIPLE,
			getSingleQuot(id) + COMMA + 
			id + L2L_JSOBJECT_SUFFIX + COMMA + 
			JS_L2L_OBJECT_TYPE_ALL + COMMA +
			getJsListObjectSearchIn() + COMMA + 
			getJsListObjectSearchAction() + COMMA + 
			getJsListObjectSearchType() + COMMA + 
			(searchAction == UIConstants.LIST_SEARCH_ACTION_ALL) + COMMA +
			(searchType == UIConstants.LIST_SEARCH_TYPE_ALL) + COMMA + 
			getSingleQuot(expanderDialogParams) + COMMA + 
			getSingleQuot(getLocalizedText(expanderTitle)) + COMMA + 
			id + LIST_LABELS_ARRAY_SUFFIX + COMMA + 
			orderButtons
			,false
		);		
	}
	
	protected void renderSingleExpandEvent() throws UIException
	{
		renderFunctionCall
		(
			JS_EXPAND_SINGLE,
			getSingleQuot(id) + COMMA + 
			id + L2L_JSOBJECT_SUFFIX + COMMA +  							
			id + L2L_TRG_LIST_SUFFIX + COMMA +							 
			JS_L2L_OBJECT_TYPE_SRC + COMMA +
			getJsListObjectSearchIn() + COMMA + 
			getJsListObjectSearchAction() + COMMA + 
			getJsListObjectSearchType() + COMMA + 
			(searchAction == UIConstants.LIST_SEARCH_ACTION_ALL) + COMMA +
			(searchType == UIConstants.LIST_SEARCH_TYPE_ALL) + COMMA + 
			getSingleQuot(expanderDialogParams) + COMMA + 
			getSingleQuot(getLocalizedText(expanderTitle)) + COMMA + 
			id + LIST_LABELS_ARRAY_SUFFIX
			,false
		);			
	}
	
	/**
	 *	renders list as label,relevant only to label type
	 */
	protected void renderListLabel() throws UIException
	{
		String key = listModel.getSelectedKey();
		if (key != null)
		{
			if (style != null)
			{
				startTag(SPAN);
				addAttribute(STYLE,style);
				endTag();
			}
			append(listModel.getValue(key));
			if (style != null)
			{
				endTag(SPAN);
			}
		}
	}
	
	/**
	 * renders the list table,relevant to all types of list,except of lable type
	 */
	protected void renderListTable() throws UIException
	{
		height = (height == null ? DEFAULT_LIST_HEIGHT : height);
		width = (width == null ? DEFAULT_LIST_WIDTH : width);

		if (singleComponent)
		{
			if (orderButtons)
			{
				renderSingleComponentWithOrderButtons();
			}
			else
			{
				renderList(false);
			}
		}
		else
		{
			renderListInitScript();
			renderListLabels();			
			
			startTag(TABLE);
			addAttribute(ID,id + L2L_TRG_LIST_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
			addAttribute(NAME,id + L2L_JSOBJECT_SUFFIX);
			addAttribute(CELLPADDING,"0");
			addAttribute(CELLSPACING,"0");
			renderStyle(true);
			if (!allowClientEnabling)
			{
				append(ENABLE_NOT_AUTHORIZED);
			}			
			endTag();
			appendln();
			
			renderListTitles();
					
			startTag(ROW,true);
			appendln();		
				
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
			{
				/*****source*******/
				startTag(CELL);
				addAttribute(WIDTH,"50%");
				endTag();
				appendln();
					
				startTag(TABLE);
				addAttribute(HEIGHT,"100%");
				addAttribute(WIDTH,"100%");
				addAttribute(CELLSPACING,"0");
				endTag();
				appendln();
				
				/*****start source search*******/
				if (isSearchRendered(true))
				{
					startTag(ROW,true);
					startTag(CELL);
					addAttribute(HEIGHT,"1");
					endTag();
					renderSearch(true,true);
					endTag(CELL);
					endTag(ROW);
					appendln();
				}
				/*****end source search*******/
	
				/*****start source list**/
				startTag(ROW,true);
				appendln();
				startTag(CELL);
				addAttribute(HEIGHT,"100%");
				addAttribute(VALIGN,TOP);
				endTag();
				appendln();
				renderList(true);
				appendln();	
				endTag(CELL);
				endTag(ROW);
				appendln();
				/*****end source list**/
				
				/*****up down buttons**/
				/*****end up down buttons**/
				
				endTag(TABLE);
						
				endTag(CELL);
				appendln();				
				/*********end source**********/
			}
			
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
			{
				/*******start "move" buttons**********/
				startTag(CELL);
				addAttribute(VALIGN,"middle");
				endTag();			
				renderMoveButtonsTable();	
				endTag(CELL);
				/*******end "move" buttons************/
			}
			
			/*****target*******/
			startTag(CELL);
			addAttribute(WIDTH,type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) ? "50%" : "100%");
			endTag();	
					
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
			{							
				startTag(TABLE);
				addAttribute(HEIGHT,"100%");
				addAttribute(WIDTH,"100%");
				addAttribute(CELLSPACING,"0");
				endTag();
			}			
			
			/*****search in target*******/
			if (isSearchRendered(false))
			{
				startTag(ROW,true);
				startTag(CELL);
				addAttribute(HEIGHT,"1");
				endTag();
				renderSearch(type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons,true);
				endTag(CELL);
				endTag(ROW);
			}
					
			/*****target list**/
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE))
			{
				startTag(ROW,true);
				startTag(CELL);
				addAttribute(HEIGHT,"100%");
				addAttribute(VALIGN,TOP);
				endTag();
				appendln();
			}
			
			renderList(type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons);
			
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE))
			{
				endTag(CELL);
				endTag(ROW);
			}
			
			/*****up and down buttons**/
			if (isOrderButtonsRendered())
			{
				startTag(ROW,true);
				startTag(CELL);
				addAttribute(HEIGHT,"1");
				endTag();
				renderUpDownButtons(true);
				endTag(CELL);						
				endTag(ROW);
			}
			/*****end up and down buttons**/
			
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
			{
				endTag(TABLE);
			}
					
			endTag(CELL);				
			/*****end target*******/
			
			/*****expander buttons***/
			if (isExpanderButtonRendered())
			{
				renderEmptyCell();
				startTag(CELL);
				addAttribute(WIDTH,"1");
				addAttribute(VALIGN,TOP);
				endTag();
				renderExpanderButton();
				endTag(CELL);
			}
			/*****end expander buttons***/
			
			endTag(ROW);
			
			//render buttons for dialog window
			if (isControlButtonsRendered())
			{
				startTag(ROW,true);	
				startTag(CELL);
				addAttribute(HEIGHT,"1");
				addAttribute(COLSPAN,"3");
				addAttribute(ALIGN,CENTER);
				endTag();
				renderControlButtons();
				endTag(CELL);
				endTag(ROW);	
			}
					
			endTag(TABLE);
		}
	}		
	
	
	/**
	 * Renders combo or list with "up" and "down" buttons and optional title.
	 * The table includes the title,the select object and buttons.
	 * @throws UIException
	 */
	private void renderSingleComponentWithOrderButtons() throws UIException
	{
		startTag(TABLE);
		addAttribute(ID,id + L2L_TRG_LIST_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
		addAttribute(NAME,id + L2L_JSOBJECT_SUFFIX);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		renderStyle(true);
		if (!allowClientEnabling)
		{
			append(ENABLE_NOT_AUTHORIZED);
		}			
		endTagLn();
		
		if (trgListTitle != null)
		{
			startTag(ROW,true);
			startTag(CELL);
			addAttribute(CLASS,trgListTitleClassName);
			endTag();
			append(trgListTitle);
			endTag(CELL);
			endTag(ROW);
		}
		
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,"100%");
		addAttribute(VALIGN,TOP);
		endTagLn();
		renderList(false);	
		endTagLn(CELL);
		endTag(ROW);
		
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,"1");
		endTag();
		renderUpDownButtons(true);
		endTag(CELL);						
		endTag(ROW);		
		
		endTag(TABLE);	
	}
	
	protected void renderListTitles()
	{
		if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) && (srcListTitle != null || trgListTitle != null) && (!sourcSearchRendered || !targetSearchRendered))
		{
			startTag(ROW);
			addAttribute(HEIGHT,"1");
			endTag();
			startTag(CELL);
			addAttribute(WIDTH,"50%");
			addAttribute(CLASS,srcListTitleClassName);
			endTag();
			append(srcListTitle != null && !sourcSearchRendered ? srcListTitle : SPACE);
			endTag(CELL);
					
			startTag(CELL);
			addAttribute(WIDTH,"50");
			endTag();
			append(SPACE);
			endTag(CELL);
			
			startTag(CELL);
			addAttribute(WIDTH,"50%");
			addAttribute(CLASS,trgListTitleClassName);
			endTag();
			append(trgListTitle != null && !targetSearchRendered ? trgListTitle : SPACE);
			endTag(CELL);
			endTag(ROW);
		}
	}
	
	/**
	 * renders script for defining constants in java script which will be needed in 
	 * multiExpanded and singleExpanded types when the expansion window is opened from
	 * the client(refreshType = NEVER);
	 */
	protected void renderListLabels() throws UIException
	{
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED) || type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED))
		{
			StringBuffer labels = new StringBuffer(100);
			
			if (type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED))
			{
				for (int index = 0;index < LIST_MULTIPLE_EXPANDED_LABELS_ARRAY.length;index++)
				{
					labels.append(getSingleQuot(getLocalizedText(LIST_MULTIPLE_EXPANDED_LABELS_ARRAY[index])));
					if (index != LIST_MULTIPLE_EXPANDED_LABELS_ARRAY.length - 1) labels.append(COMMA);
				}
				if (srcListTitle != null)
				{
					labels.append(COMMA + getSingleQuot(srcListTitle));
				}
				if (trgListTitle != null)
				{
					labels.append(COMMA + getSingleQuot(trgListTitle));
				}				
			}
			else
			{
				for (int index = 0;index < LIST_SINGLE_EXPANDED_LABELS_ARRAY.length;index++)
				{
					labels.append(getSingleQuot(getLocalizedText(LIST_SINGLE_EXPANDED_LABELS_ARRAY[index])));
					if (index != LIST_SINGLE_EXPANDED_LABELS_ARRAY.length - 1) labels.append(COMMA);
				}	
				if (srcListTitle != null)
				{
					labels.append(COMMA + getSingleQuot(srcListTitle));
				}							
			}
			startTag(SCRIPT,true);
			addVariable(id + LIST_LABELS_ARRAY_SUFFIX,labels.toString(),true);
			endTag(SCRIPT);
			appendln();
		}
	}
	
	/**
	 * renders script for defining java script components which are needed
	 * for the List component and for defining a new List component.
	 * including : source array,target array and the id of the tag,all of them 
	 * are passed to the java script constructor of List component.
	 */
	protected void renderListInitScript()
	{	
		ArrayList keys = listModel.getKeys();
		ArrayList selectedKeys = listModel.getSelectedKeys();
		StringBuffer sourceArrayValues = new StringBuffer(200);
		StringBuffer targetArrayValues = new StringBuffer(200);

		//When type=UIConstants.LIST_TYPE_MULTIPLE,all the keys of the ListModel
		//should be added to the js target array
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE))
		{
			for (int index = 0;index < keys.size();index++)
			{
				String key = (String)keys.get(index);
				String value = getFormattedJSValue(listModel.getValue(key));
				if (targetArrayValues.length() != 0)
				{
					targetArrayValues.append(COMMA);
				}	
				targetArrayValues.append(getFunctionCall(JS_NEW_LIST_OPTION,getQuot(key) + COMMA + getQuot(value) + COMMA + true,false));				
			}
		}


		//type=UIConstants.LIST_TYPE_MULTIPLE_EXPANDED
		//or type=UIConstants.LIST_TYPE_LIST_TO_LIST
		else
		{
			for (int index = 0;index < keys.size();index++)
			{
				String key = (String)keys.get(index);
				String value = getFormattedJSValue(listModel.getValue(key));
				if (listModel.isValueSelected(key))
				{
					if (sourceArrayValues.length() != 0)
					{
						sourceArrayValues.append(COMMA);
					}
					sourceArrayValues.append(getFunctionCall(JS_NEW_LIST_OPTION,getQuot(key) + COMMA + getQuot(value) + COMMA + false,false));
				}
				else
				{
					if (sourceArrayValues.length() != 0)
					{
						sourceArrayValues.append(COMMA);
					}		
					sourceArrayValues.append(getFunctionCall(JS_NEW_LIST_OPTION,getQuot(key) + COMMA + getQuot(value) + COMMA + true,false));
				}
			}
			
			if (selectedKeys != null)
			{
				for (int index = 0;index < selectedKeys.size();index++)
				{
					String key = (String)selectedKeys.get(index);
					String value = getFormattedJSValue(listModel.getValue(key));
					if (targetArrayValues.length() != 0)
					{
						targetArrayValues.append(COMMA);
					}	
					targetArrayValues.append(getFunctionCall(JS_NEW_LIST_OPTION,getQuot(key) + COMMA + getQuot(value) + COMMA + true,false));				
				}
			}
		}
				
		//script for initializing the client list object
		startTag(SCRIPT,true);
		addVariable(id + L2L_SRC_ARRAY_SUFFIX,sourceArrayValues.toString(),true);	
		addVariable(id + L2L_TRG_ARRAY_SUFFIX,targetArrayValues.toString(),true);		
		append(id + L2L_JSOBJECT_SUFFIX + " = ");
		renderFunctionCall
		(
			JS_NEW_L2L_OBJECT,
			id + L2L_SRC_ARRAY_SUFFIX + COMMA + 
			id + L2L_TRG_ARRAY_SUFFIX + COMMA + 
			getSingleQuot(id) + COMMA + 
			getJsObjectType() + COMMA +
			getJsListObjectSearchIn() + COMMA +  
			getJsListObjectSearchType() + COMMA + 
			getJsListObjectSearchAction(),false
		);
		endTag(SCRIPT);
		appendln();
		
		//script for inserting the list component in a client array
		startTag(SCRIPT,true);
		addValueToArray(LIST_COMPONENTS_ARRAY,id + L2L_TRG_LIST_SUFFIX + COMPLEX_COMPONENT_SUFFIX);
		endTag(SCRIPT);
		appendln();
	}
	
	private String getJsObjectType()
	{
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED))
		{
			return JS_L2L_OBJECT_TYPE_TRG;
		}
		else if (type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons)
		{
			return JS_L2L_OBJECT_TYPE_SRC;
		}
		else
		{
			return JS_L2L_OBJECT_TYPE_ALL;
		}	
	}
	
	private String getJsListObjectSearchType()
	{
		if (searchType == UIConstants.LIST_SEARCH_TYPE_CONTAINS || searchType == UIConstants.LIST_SEARCH_TYPE_START || searchType == UIConstants.LIST_SEARCH_TYPE_END)
		{
			return String.valueOf(searchType);
		}
		else
		{
			return String.valueOf(defaultSearchType);
		}		
	}
	
	private String getJsListObjectSearchAction()
	{
		if (searchAction == UIConstants.LIST_SEARCH_ACTION_FILTER || searchAction == UIConstants.LIST_SEARCH_ACTION_SEARCH)
		{
			return String.valueOf(searchAction);
		}
		else
		{
			return String.valueOf(defaultSearchAction);
		}
	}
	
	private String getJsListObjectSearchIn()
	{
		return String.valueOf(searchIn);	
	}
	
	/**
	 * renders the list itself(html Select).
	 * relevant to all types.
	 * @param isSource if true renders the source list,may be true only in ListToList type 
	 */
	protected void renderList(boolean isSource) throws UIException
	{
		String listSuffix = isSource ? L2L_SRC_LIST_SUFFIX : L2L_TRG_LIST_SUFFIX;
		if (type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED))
		{
			renderSingleExpandedTextBox();
		}
		else
		{
			startTag(SELECT);
			addAttribute(ID,id + listSuffix);
			addAttribute(SIZE,String.valueOf(getListSize()));
			renderListStyle();			
			renderListDblClick();		
			renderListOnChange();			
			renderClassByState();			
			if (type.equals(UIConstants.LIST_TYPE_SINGLE))
			{
				int currentlySelected = listModel.getSelectedKeyOrder();
				 
				if (currentlySelected == -1)
				{
					currentlySelected = 0;
				}
				else if (inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE) && addMandatoryPrompt
						|| inputType.equals(UIConstants.COMPONENT_NORMAL_INPUT_TYPE) && addOptionalPrompt)
				{
					currentlySelected++;
				}
				
				addAttribute(CURRENTLY_SELECTED,String.valueOf(currentlySelected));
			}
			if (!singleComponent && !isDisabled())
			{
				renderSetDirtyProperty();
			}
			if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE) && !isDisabled() && !controlButtons)	
			{
				addAttribute(MULTIPLE,MULTIPLE);
			}
			//if (type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED) || type.equals(UIConstants.LIST_TYPE_SINGLE))
			if (type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED) || singleComponent)
			{
				append(stateString);
			}
			if (singleComponent && !allowClientEnabling)
			{
				append(ENABLE_NOT_AUTHORIZED);
			}
			renderFieldFocus();
			renderInputTypeIndication();
			endTag();		
			appendln();
			renderListItems(isSource);
			endTag(SELECT);
			appendln();
		}
	}
	
	protected void renderListStyle()
	{
		StringBuffer style = new StringBuffer(50);
		
		if (singleComponent)
		{
			if (!width.equals(DEFAULT_LIST_WIDTH) || orderButtons)
			{
				style.append(getStyleAttribute(WIDTH,width));
			}
			if (!height.equals(DEFAULT_LIST_HEIGHT))
			{
				style.append(getStyleAttribute(HEIGHT,height));
			}
			if (state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
			{
				style.append(";" + DISPLAY_NONE);
			}
		}
		else
		{
			style.append(getStyleAttribute(WIDTH,DEFAULT_LIST_WIDTH));
			style.append(getStyleAttribute(HEIGHT,DEFAULT_LIST_HEIGHT));
		}
		addAttribute(STYLE,style.toString());
	}
	
	/**
	 * renders the items of the list(options),depending on the component's type 
	 * and on controlButton value:
	 * single type and multiple type(controlButtons=false) - renders all the items and marks the selected items.
	 * listToList type - if isSource is true, renders the unselected items,if false
	 * renders the selected items.
	 * multipleExpanded type - renders the selected items.
	 * singleExpanded type - renders text box. 
	 * @param isSource if true renders the source list's item,may be true only in listToList type.
	 */
	protected void renderListItems(boolean isSource) throws UIException
	{
		if (!orderButtons)
		{
			renderListFirstItem();
		}
		
		ArrayList keys = listModel.getKeys();	
			
		//renders all the keys,marks the selected
		if (type.equals(UIConstants.LIST_TYPE_SINGLE) || type.equals(UIConstants.LIST_TYPE_MULTIPLE) && !controlButtons)	
		{
			for (int index = 0;index < keys.size();index++)
			{
				String key = (String)keys.get(index);
				String value = listModel.getValue(key);
				startTag(OPTION);
				addAttribute(VALUE,key);
				if (!orderButtons && !controlButtons && listModel.isValueSelected(key))
				{
					append(SELECTED);
				}
				endTag();
				append(value);
				endTag(OPTION);
				appendln();
			}
		}
		
		//render the selected keys(listToList - target,multipleExpanded)
		else if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) && !isSource
					|| type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED))
		{
			ArrayList selectedKeys = listModel.getSelectedKeys();
			for (int index = 0;index < selectedKeys.size();index++)
			{
				String key = (String)selectedKeys.get(index);
				String value = listModel.getValue(key);
				if (listModel.isValueSelected(key))
				{
					startTag(OPTION);
					addAttribute(VALUE,key);
					endTag();
					append(value);
					endTag(OPTION);
					appendln();
				}
			}
		}
		
		//render the unselected keys(listToLisy - source,multiple - controlButtons)
		else if ( type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) && isSource ||
		type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons )
		{
			boolean isSearchMarked = false;
			for (int index = 0;index < keys.size();index++)
			{
				String key = (String)keys.get(index);
				String value = listModel.getValue(key);
				if (!listModel.isValueSelected(key))
				{
					startTag(OPTION);
					addAttribute(VALUE,key);
					if (!isSearchMarked && type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons && listModel.getSearchString() != null)
					{
						if (listModel.getValue(key).indexOf(listModel.getSearchString()) != -1)
						{							
							append(SELECTED);
							isSearchMarked = true;
						}
					}
					endTag();
					append(value);
					endTag(OPTION);
					appendln();
				}
			}
		}			
	}
	
	/**
	 * renders the first item of a list,which may be a constant value.
	 * relevant only in single type(combo)
	 */
	protected void renderListFirstItem() throws UIException
	{
		if (type.equals(UIConstants.LIST_TYPE_SINGLE))
		{
			if ( (inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE) || state.equals(UIConstants.COMPONENT_DISABLED_STATE))
				 && addMandatoryPrompt 
				 || 
				 (inputType.equals(UIConstants.COMPONENT_NORMAL_INPUT_TYPE) || state.equals(UIConstants.COMPONENT_DISABLED_STATE)) 
				 && addOptionalPrompt)
			{
				startTag(OPTION);
				addAttribute(VALUE,"");
				endTag();
				if (inputType.equals(UIConstants.COMPONENT_MANDATORY_INPUT_TYPE)) 
				{
					append(getLocalizedText(mandatoryPrompt));
				}
				else
				{
					append(getLocalizedText(optionalPrompt));
				}
				endTag(OPTION);
			}
		}		
	}
	
	/**
	 * Renders the text box only when type is singleExpanded. 
	 */
	protected void renderSingleExpandedTextBox() throws UIException
	{
		startTag(INPUT);
		addAttribute(ID,id + L2L_TRG_LIST_SUFFIX);
		addAttribute(TYPE,TEXT);
		addAttribute(STYLE,WIDTH + ":100%;");
		renderListKeyPress();	
		renderSetDirtyProperty();
		renderClassByState();
		if (listModel.getSelectedKey() != null)
		{
			addAttribute(VALUE,getFormattedHtmlValue(listModel.getValue(listModel.getSelectedKey())));
		}
		else if (editable && listModel.getEditableValue() != null)
		{
			addAttribute(VALUE,getFormattedHtmlValue(listModel.getEditableValue()));
		}
		if (!controlButtons)
		{
			append(stateString);
		}
		renderFieldFocus();
		renderInputTypeIndication();
		endTag();
	}
	
	protected void renderListKeyPress() throws UIException
	{
		if (editable)
		{
			addAttribute(ONKEYUP,getFunctionCall(JS_LIST_TEXT_KEY_PRESS_EVENT,id,true));
		}
		else
		{
			if (openOnKeyPress)
			{
				addAttribute(ONKEYPRESS);
				append(QUOT);
				if (isServer())
				{
					renderSingleExpandSubmitEvent();
				}
				else
				{
					renderSingleExpandEvent();
				}
				append(";return false");
				append(QUOT);
				addAttribute(ONKEYDOWN,getFunctionCall("return cancelTextFieldSpecialKeys",""));
			}
			else
			{
				addAttribute(ONKEYDOWN,"return false;");
			}
			addAttribute(ONDRAG,"return false;");
			addAttribute(ONCONTEXTMENU,"return false;");
		}
	}
	
	/**
	 * renders an event for dblclick event on an item of a list.
	 * relevant only in ListToList type,also when controlButtons=true.
	 */
	protected void renderListDblClick()
	{
		if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
		{
			addAttribute(ONDBLCLICK);
			append(QUOT);
			append(getL2LDoEventsFunction());
			append(";");
			if (!controlButtons)
			{
				append(getListToListEventFunction());
			}
			append(QUOT);
		}	
		else if (type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons)
		{
			addAttribute(ONDBLCLICK,getFunctionCall(JS_EXPAND_SINGLE_CALL_BACK,id + L2L_JSOBJECT_SUFFIX + COMMA + false,false));	
		}
	}
	
	/**
	 * renders an event for onchange event on an item of a list.
	 * relevant in Single type(combo) and regular Multiple type.
	 */
	protected void renderListOnChange() throws UIException
	{
		if (!orderButtons)
		{
			if (type.equals(UIConstants.LIST_TYPE_SINGLE))
			{
				//render the "onchnage" attribute
				addAttribute(ONCHANGE);
				append(QUOT);
				//render the script set in the tag
				if (!onChange.equals(""))
				{
					append(onChange);			
				}
				//render the dirty flag script
				if (isDirtable())
				{
					append(getSetDirtyFunction() + ";");
				}
				//render script set by the event
				if (changeCustomEvent.getEventName() != null)
				{
					append(getListComplexEventFunction());
				}		
				else
				{
					append(getListEventFunction());			
				}
				append(QUOT);	
			}
			else if (!controlButtons  && type.equals(UIConstants.LIST_TYPE_MULTIPLE))
			{
				addAttribute(ONCHANGE);
				append(QUOT);
				append(getListEventFunction() + ";");
				if (!onChange.equals(""))
				{
					append(onChange);			
				}
				if (changeCustomEvent.getEventName() != null)
				{
					append(getListSubmitEventFunction());
				}
				append(QUOT);
			}
		}
	}
	
	/**
	 * renders button for single and multiple expanded
	 */
	protected void renderExpanderButton() throws UIException
	{
		boolean isServer = (refreshType == UIConstants.LIST_REFRESH_TYPE_ALWAYS || refreshType == UIConstants.LIST_REFRESH_TYPE_ONCE);
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED))
		{
			renderMultipleExpandButton(isServer);
		}
		else if (type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED))
		{
			renderSingleExpandButton(isServer);
		}
	}		
	
	/**
	 * renders button for multipleExpanded
	 */
	protected void renderMultipleExpandButton(boolean isServer) throws UIException
	{
		//Used for adding suffixes to the class of the expander button
		int openDialogButtonClassNameSuffixes = CSS_USE_DIRECTION;
		if (isDisabled())
		{
			openDialogButtonClassNameSuffixes = CSS_USE_DISABLED;
		}
		if (expanderImage != null)
		{
			startTag(IMAGE);
			addAttribute(SRC,getLocalizedImagesDir() + expanderImage);
		}
		else
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,expanderButtonTitle);
		}
		if (tooltip != null)
		{
			addAttribute(TITLE,getLocalizedText(tooltip));
		}		
		addAttribute(CLASS,openDialogButtonClassName + getCssClassSuffix(openDialogButtonClassNameSuffixes));
		addAttribute(ONCLICK);
		append(QUOT);
		append("if (!");
		if (isServer)
		{			
			renderMultipleExpandSubmitEvent();
		}
		else
		{
			renderMultipleExpandEvent();
		}	
		append(") return;");
		if (changeCustomEvent.getEventName() != null)
		{
			append(getListSubmitEventFunction());
		}
		append(QUOT);
		if (!controlButtons)
		{
			append(stateString);
		}
		endTag();		
	}
	
	/**
	 * renders button for singleExpanded
	 */
	protected void renderSingleExpandButton(boolean isServer) throws UIException
	{
		//Used for adding suffixes to the class of the expander button
		int openDialogButtonClassNameSuffixes = CSS_USE_DIRECTION;
		if (isDisabled())
		{
			openDialogButtonClassNameSuffixes += CSS_USE_DISABLED;
		}
		
		if (expanderImage != null)
		{
			startTag(IMAGE);
			addAttribute(SRC,getLocalizedImagesDir() + expanderImage);
		}
		else
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(VALUE,expanderButtonTitle);
			addAttribute(CLASS,openDialogButtonClassName + getCssClassSuffix(openDialogButtonClassNameSuffixes));
		}
		if (tooltip != null)
		{
			addAttribute(TITLE,getLocalizedText(tooltip));
		}		
		addAttribute(ONCLICK);
		append(QUOT);
		append("if (!");
		if (isServer)
		{
			renderSingleExpandSubmitEvent();
		}
		else
		{
			renderSingleExpandEvent();
		}	
		append(") return;");
		if (!onChange.equals(""))
		{
			append(onChange);
		}
		if (changeCustomEvent.getEventName() != null)
		{
			append(getListSubmitEventFunction());
		}		
		append(QUOT);
		if (!controlButtons)
		{
			append(stateString);
		}
		endTag();		
	}

	/**
	 * renders search component for searching items in a list and for<br>
	 * filtering items in a list<br>
	 * relevant in:<br>
	 * 1.ListToList object both in source list and on target list,<br>
	 * 2.List object when is used for selecting items for SingleExpanded(controlButtons=true)
	 */
	protected void renderSearch(boolean isSourceList,boolean isRenderButtons) throws UIException
	{
		startTag(TABLE);
		addAttribute(HEIGHT,"100%");
		addAttribute(WIDTH,"100%");
		addAttribute(CELLSPACING,"0");
		endTag();
		startTag(ROW,true);
		if (isSourceList && srcListTitle != null)
		{
			startTag(CELL);
			addAttribute(CLASS,srcListTitleClassName);
			endTag();
			append(srcListTitle);
			endTag(CELL);
		}
		else if (!isSourceList && trgListTitle != null)
		{
			startTag(CELL);
			addAttribute(CLASS,trgListTitleClassName);
			endTag();
			append(trgListTitle);
			endTag(CELL);			
		}
		startTag(CELL);
		addAttribute(WIDTH,"100%");
		endTag();
		renderSearchTextBox(isSourceList);
		endTag(CELL);	
		renderSearchActionSelect(isSourceList);
		renderSearchTypeSelect(isSourceList);
		endTag(ROW);
		endTag(TABLE);
	}
	
	private void renderSearchTextBox(boolean isSourceList) throws UIException
	{
		startTag(INPUT);
		addAttribute(ID,isSourceList ? id + L2L_SRC_TEXT_SUFFIX : id + L2L_TRG_TEXT_SUFFIX);
		addAttribute(TYPE,TEXT);
		addAttribute(STYLE,WIDTH + ":100%;");
		addAttribute(CLASS,searchTextClassName);
		addAttribute(ONKEYUP);
		append(QUOT);
		append(getL2LDoEventsFunction());
		append(JS_END_OF_LINE);
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE))
		{
			append(getListEventFunction());
		}
		append(QUOT);
		
		addAttribute(ONKEYDOWN,getL2LDoEventsFunction());
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE) && controlButtons && listModel.getSearchString() != null)
		{
			addAttribute(VALUE,listModel.getSearchString());
		}
		append(stateString);
	}
	
	private void renderSearchTypeSelect(boolean isSourceList) throws UIException
	{
		if (searchType == UIConstants.LIST_SEARCH_TYPE_ALL)
		{
			startTag(CELL);
			addAttribute(WIDTH,"1");
			endTag();			
			startTag(SELECT);
			addAttribute(ID,isSourceList ? id + L2L_SRC_SEARCH_TYPE_SUFFIX : id + L2L_TRG_SEARCH_TYPE_SUFFIX);
			addAttribute(ONCHANGE);
			append(QUOT);
			append(getL2LDoEventsFunction());
			append(JS_END_OF_LINE);
			if (!controlButtons && !isSourceList && (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED)))
			{
				append(getListToListEventFunction());
			}
			else if (targetSearchRendered && type.equals(UIConstants.LIST_TYPE_MULTIPLE))
			{
				append(getListEventFunction());
			}
			append(QUOT);
			append(stateString);
			endTag();
			startTag(OPTION);
			addAttribute(VALUE,String.valueOf(UIConstants.LIST_SEARCH_TYPE_START));
			if (defaultSearchType == UIConstants.LIST_SEARCH_TYPE_START)
			{
				append(SELECTED);
			}			
			endTag();
			append(getLocalizedText("ui.list.search.start"));
			endTag(OPTION);
			startTag(OPTION);
			addAttribute(VALUE,String.valueOf(UIConstants.LIST_SEARCH_TYPE_CONTAINS));
			if (defaultSearchType == UIConstants.LIST_SEARCH_TYPE_CONTAINS)
			{
				append(SELECTED);
			}			
			endTag();
			append(getLocalizedText("ui.list.search.contains"));
			endTag(OPTION);	
			startTag(OPTION);
			addAttribute(VALUE,String.valueOf(UIConstants.LIST_SEARCH_TYPE_END));
			if (defaultSearchType == UIConstants.LIST_SEARCH_TYPE_END)
			{
				append(SELECTED);
			}
			endTag();
			append(getLocalizedText("ui.list.search.end"));
			endTag(OPTION);					
			endTag(SELECT);
			endTag(CELL);
		}
	}
	
	private void renderSearchActionSelect(boolean isSourceList) throws UIException
	{
		if (searchAction == UIConstants.LIST_SEARCH_ACTION_ALL)
		{
			startTag(CELL);
			addAttribute(WIDTH,1);
			endTag();			
			startTag(SELECT);
			addAttribute(ID,isSourceList ? id + L2L_SRC_SERACH_ACTION_SUFFIX : id + L2L_TRG_SERACH_ACTION_SUFFIX);
			addAttribute(ONCHANGE);
			append(QUOT);
			append(getL2LDoEventsFunction());
			append(JS_END_OF_LINE);
			if (!controlButtons && !isSourceList && (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED)))
			{
				append(getListToListEventFunction());
			}
			else if (targetSearchRendered && type.equals(UIConstants.LIST_TYPE_MULTIPLE))
			{
				append(getListEventFunction());
			}
			append(QUOT);
			append(stateString);
			endTag();
			startTag(OPTION);
			addAttribute(VALUE,String.valueOf(UIConstants.LIST_SEARCH_ACTION_SEARCH));
			if (defaultSearchAction == UIConstants.LIST_SEARCH_ACTION_SEARCH)
			{
				append(SELECTED);
			}
			endTag();
			append(getLocalizedText("ui.list.search"));
			endTag(OPTION);
			startTag(OPTION);
			addAttribute(VALUE,String.valueOf(UIConstants.LIST_SEARCH_ACTION_FILTER));
			if (defaultSearchAction == UIConstants.LIST_SEARCH_ACTION_FILTER)
			{
				append(SELECTED);
			}			
			endTag();
			append(getLocalizedText("ui.list.search.filter"));
			endTag(OPTION);			
			endTag(SELECT);
			endTag(CELL);			
		}
	}	
	
	/**
	 * renders "move up" and "move down" buttons for changing the order of selected items 
	 * relevant in: 
	 * 1.listToList type in target list only.
	 * 2.listToList type in target list only when it is used for selecting items for MultipleExpanded(controlButtons=true).
	 * 3.multiple type when is used for selecting items for SingleExpanded(controlButtons=true).
	 */
	protected void renderUpDownButtons(boolean isRenderButtons) throws UIException
	{
		startTag(TABLE);
		addAttribute(HEIGHT,"100%");
		addAttribute(WIDTH,"100%");
		addAttribute(CELLSPACING,"3");
		endTag();
		startTag(ROW,true);
		
		startTag(CELL);
		addAttribute(WIDTH,"50%");
		endTag();
		endTag(CELL);
		
		startTag(CELL,true);
		
		if (isRenderButtons)
		{
			renderMoveDownButton();
		}
		else
		{
			append(SPACE);		
		}
		endTag(CELL);
		
		startTag(CELL,true);
		append(SPACE + SPACE);
		endTag(CELL);
		
		startTag(CELL,true);
		if (isRenderButtons)
		{
			renderMoveUpButton();		
		}
		else 
		{
			append(SPACE);
		}
		endTag(CELL);
		
		startTag(CELL);
		addAttribute(WIDTH,"50%");
		endTag();
		endTag(CELL);
		
		endTag(ROW);
		endTag(TABLE);		
	}
	
	/**
	 * Renders a button that moves objects down in a list
	 * Relevent in types listToList and single\multiple when orderButtons=true.
	 */
	private void renderMoveDownButton()
	{
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(ID,id + L2L_TRG_DOWN_BUTTON_SUFFIX);
		addAttribute(CLASS,orderButtonsClassName);
		
		if (singleComponent)
		{
			addAttribute(ONCLICK);
			append(QUOT);
			append(getFunctionCall(JS_LIST_MOVE_DOWN,id + L2L_TRG_LIST_SUFFIX,false));
			append(JS_END_OF_LINE);
			append(getFunctionCall(JS_LIST_REORDER_KEYS,getSingleQuot(id) + COMMA + id + L2L_TRG_LIST_SUFFIX,false));
			append(QUOT);
		}
		else
		{
			addAttribute(ONCLICK);
			append(QUOT);
			append(getL2LDoEventsFunction());
			append(JS_END_OF_LINE);
			if (!controlButtons)
			{
				append(getListToListEventFunction());
			}
			append(QUOT);
		}
		
		addAttribute(VALUE,getLocalizedText("ui.list.downButton"));
		append(stateString);
		endTag();		
	}
	
	/**
	 * Renders a button that moves objects up in a list
	 * Relevent in types listToList and single\multiple when orderButtons=true.
	 */
	private void renderMoveUpButton()
	{
		startTag(INPUT);
		addAttribute(VALIGN,TOP);
		addAttribute(TYPE,BUTTON);
		addAttribute(ID,id + L2L_TRG_UP_BUTTON_SUFFIX);
		addAttribute(CLASS,orderButtonsClassName);

		if (singleComponent)
		{
			addAttribute(ONCLICK);
			append(QUOT);
			append(getFunctionCall(JS_LIST_MOVE_UP,id + L2L_TRG_LIST_SUFFIX,false));
			append(JS_END_OF_LINE);
			append(getFunctionCall(JS_LIST_REORDER_KEYS,getSingleQuot(id) + COMMA + id + L2L_TRG_LIST_SUFFIX,false));
			append(QUOT);
		}
		else
		{
			addAttribute(ONCLICK);
			append(QUOT);
			append(getL2LDoEventsFunction());
			append(JS_END_OF_LINE);
			if (!controlButtons)
			{
				append(getListEventFunction());
			}
			append(QUOT);
		}
		
		addAttribute(VALUE,getLocalizedText("ui.list.upButton"));
		append(stateString);
		endTag();		
	}
	
	/**
	 * renders table of "move" buttons.
	 * relevant only for listToList type.
	 */
	private void renderMoveButtonsTable() throws UIException
	{
		startTag(TABLE);
		addAttribute(CELLSPACING,"3");
		endTag();
						
		//move source to target button
		startTag(ROW,true);
		startTag(CELL,true);	
		renderMoveButton(true,false);	
		endTag(CELL);
		endTag(ROW);
		
		//move source to target all button
		startTag(ROW,true);
		startTag(CELL);
		endTag();				
		renderMoveButton(true,true);			
		endTag(CELL);
		endTag(ROW);
				
		renderEmptyRow();
				
		//move left button
		startTag(ROW,true);
		startTag(CELL);
		endTag();		
		renderMoveButton(false,false);			
		endTag(CELL);
		endTag(ROW);
				
		//move all left button
		startTag(ROW,true);
		startTag(CELL);
		endTag();		
		renderMoveButton(false,true);		
		endTag(CELL);
		endTag(ROW);	
		
		endTag(TABLE);		
	}	
	
	/**
	 * render buttons only for ListToList object(also when controlButtons=true).
	 * @param isToSource if true renders "move target to source" button else renders "move source to target" button
	 * @param isAll if true renders "move all" button else regular "move" button
	 */
	protected void renderMoveButton(boolean isToSource,boolean isAll) throws UIException
	{
		String value = "";
		String buttonSuffix = "";
		if (isToSource)
		{
			value = isAll ? getLocalizedText("ui.list.srcToTrgAllButton") : getLocalizedText("ui.list.srcToTrgButton");
			buttonSuffix = isAll ? L2L_SRC_TO_TRG_ALL_BUTTON_SUFFIX : L2L_SRC_TO_TRG_BUTTON_SUFFIX;
		}
		else
		{
			value = isAll ? getLocalizedText("ui.list.trgToSrcAllButton") : getLocalizedText("ui.list.trgToSrcButton");
			buttonSuffix = isAll ? L2L_TRG_TO_SRC_ALL_BUTTON_SUFFIX : L2L_TRG_TO_SRC_BUTTON_SUFFIX;
		}
		startTag(INPUT);	
		addAttribute(TYPE,BUTTON);
		addAttribute(ID,id + buttonSuffix);
		addAttribute(STYLE,WIDTH + ":100%;");
		addAttribute(CLASS,orderButtonsClassName);	
		addAttribute(ONCLICK);
		append(QUOT);
		append(getL2LDoEventsFunction());
		append(";");
		if (!controlButtons)
		{
			append(getListToListEventFunction());
		}
		append(QUOT);
		addAttribute(VALUE,value);
		append(stateString);
		endTag();	
	}
	
	/**
	 * renders control buttons : "approve","cancel" and "delete selection"
	 * relevant in :
	 * 1.ListToList object when controlButtons=true.
	 * 2.List object when controlButtons=true.	
	 */
	protected void renderControlButtons() throws UIException 
	{
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(CLASS,searchButtonClassName);
		addAttribute(VALUE,getLocalizedText("ui.list.approveButton"));
		if (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST))
		{
			addAttribute(ONCLICK,getFunctionCall(JS_EXPAND_MULTIPLE_CALL_BACK,id + L2L_JSOBJECT_SUFFIX,false));	
		}
		else
		{
			addAttribute(ONCLICK,getFunctionCall(JS_EXPAND_SINGLE_CALL_BACK,id + L2L_JSOBJECT_SUFFIX + COMMA + false,false));
		}
		append(stateString);
		endTag();
		if (type.equals(UIConstants.LIST_TYPE_MULTIPLE))
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(CLASS,searchButtonClassName);
			addAttribute(VALUE,getLocalizedText("ui.list.cancelSelectionButton"));
			addAttribute(ONCLICK,getFunctionCall(JS_EXPAND_SINGLE_CALL_BACK,id + L2L_JSOBJECT_SUFFIX + COMMA + true,false));
			append(stateString);
			endTag();
		}
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(CLASS,searchButtonClassName);
		addAttribute(VALUE,getLocalizedText("ui.list.cancelButton"));
		addAttribute(ONCLICK,CLOSE_WINDOW);
		append(stateString);
		endTag();
	}
	
	/**
	 * used for creating a call to "doEvents" js function.
	 * relevent in:
	 * 1.ListToList object.
	 * 2.ListToList object when it used to select items for MultipleExpanded(controlButtons=true).
	 * @return string which represents a  call for "doEvents" js function
	 */
	protected String getL2LDoEventsFunction()
	{
		return getFunctionCall(JS_DO_EVENTS,JS_EVENT_OBJECT,false,id + L2L_JSOBJECT_SUFFIX);
	}
		
	/**
	 * used for creating a call to "listEvent" js function.
	 * relevant in: 
	 * 1.ListToList object when one of the 4 "move" buttons or "up" or "down" buttons are pressed.
	 * 2.ListToList object when the target list is searched or filtered.
	 * 3.Combo or List when an item is selected.
	 * 4.MultipleExpanded and SingleExpanded when items are added or removed. 
	 * not relevant in any case when controlButtons=true.
	 * @return the string representation of the "listEvent" js function.
	 */
	protected String getListEventFunction()
	{
		boolean isSelectAll = (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) || type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED));
		boolean isMultiple = (!type.equals(UIConstants.LIST_TYPE_SINGLE) && !type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED)); 
		return getFunctionCall(JS_LIST_EVENT,getSingleQuot(id) + COMMA + 
												id + L2L_TRG_LIST_SUFFIX + COMMA + 
												isSelectAll + COMMA +
												isMultiple,false);
	}
	
	/**
	 * used for creating a call to "listToListEvent" js function.
	 * relevant in: 
	 * 1.ListToList object when one of the 4 "move" buttons or "up" or "down" buttons are pressed.
	 * 2.ListToList object when the target list is searched or filtered.
	 * 3.Combo or List when an item is selected.
	 * 4.MultipleExpanded and SingleExpanded when items are added or removed. 
	 * not relevant in any case when controlButtons=true.
	 * @return the string representation of the "listEvent" js function.
	 */
	protected String getListToListEventFunction()
	{
		return getFunctionCall
		(
			JS_LIST_TO_LIST_EVENT,
			getSingleQuot(id) + COMMA + 
			id + L2L_TRG_LIST_SUFFIX + COMMA 
			,false
		);
	}	
	
	protected String getListTextEventFunction()
	{
		return getFunctionCall(JS_LIST_TEXT_EVENT,id,true);
	}
	
	/**
	 * used for creating a call to "comboEvent" js function,which sends an 
	 * event to the server
	 * relevant only in combo(type = single).
	 * @return the string representation of the "comboEvent" js function.
	 */
	protected String getListSubmitEventFunction() throws UIException
	{
		return getFunctionCall(JS_LIST_SUBMIT_EVENT,id + COMMA + changeCustomEvent.getEventName() + COMMA + changeCustomEvent.isCheckDirty() + COMMA + changeCustomEvent.isCheckWarnings(),true);
	}
	
	protected String getListComplexEventFunction() throws UIException
	{
		return getFunctionCall(JS_LIST_COMPLEX_EVENT,id + COMMA + changeCustomEvent.getEventName() + COMMA + changeCustomEvent.isCheckDirty() + COMMA + changeCustomEvent.isCheckWarnings(),true);
	}
	
	
	/**
	 * checks if the search component has to be rendered,depending on the type of the 
	 * list component and of the searchIn parameter in case of a listToList type.
	 * @param isSource indicates if a source list is rendered,relevant only in
	 * listToList type
	 * @return true if a search component has to be rendered.
	 */
	protected boolean isSearchRendered(boolean isSource)
	{
		if (isSource)
		{
			return searchIn == UIConstants.LIST_SEARCH_IN_ALL || searchIn == UIConstants.LIST_SEARCH_IN_SRC;
		}
		else
		{
			return (type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST) ||
					type.equals(UIConstants.LIST_TYPE_MULTIPLE)) && 
					(searchIn == UIConstants.LIST_SEARCH_IN_ALL || searchIn == UIConstants.LIST_SEARCH_IN_TARGET);
		}
	}
	
	/**
	 * checks if order buttons("up" and "down") have to be rendered).
	 * @return true if order buttonas has to be rendered.
	 */
	protected boolean isOrderButtonsRendered()
	{
		return orderButtons && type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST); 
				
	}
	
	/**
	 * checks if control buttons("approve","cancel","delete selection") 
	 * have to be rendered - in case of a listToList type or multiple type which open in a dialog window.
	 * @return true if control buttons have to be rendered
	 */
	protected boolean isControlButtonsRendered()
	{
		return (controlButtons && ((type.equals(UIConstants.LIST_TYPE_LIST_TO_LIST)) || type.equals(UIConstants.LIST_TYPE_MULTIPLE)));
	}
	
	protected boolean isExpanderButtonRendered()
	{
		return type.equals(UIConstants.LIST_TYPE_MULTIPLE_EXPANDED) || type.equals(UIConstants.LIST_TYPE_SINGLE_EXPANDED);
	}
	
	protected int getListSize()
	{
		if (singleComponent && !orderButtons)
		{
			return size;
		}
		else
		{
			if (size == 1)
			{ 
				return 2;
			}
			else
			{
				return size;
			}			
		}	
	}
		
	private boolean isServer()
	{
		return (refreshType == UIConstants.LIST_REFRESH_TYPE_ALWAYS || refreshType == UIConstants.LIST_REFRESH_TYPE_ONCE);
	}
	
	private boolean isSingleComponent()
	{
		return 
			type.equals(UIConstants.LIST_TYPE_SINGLE)
			||
			type.equals(UIConstants.LIST_TYPE_MULTIPLE) && !controlButtons && !targetSearchRendered;	
	}
	
	/********methods for initializing the hidden field of the tag**********/	
	protected String getHiddenFieldInit()
	{
		return "";
	}
	
	/**
	 * Returns the type.
	 * @return String
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Sets the type.
	 * @param type The type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Returns the size.
	 * @return int
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Sets the size.
	 * @param size The size to set
	 */
	public void setSize(int size)
	{
		this.size = size;
	}

	/**
	 * @return the event name
	 */
	public String getChangeEventName()
	{
		return changeEventName;
	}

	/**
	 * @return the event name
	 */
	public String getExpandEventName()
	{
		return expandEventName;
	}

	/**
	 * @param string
	 */
	public void setChangeEventName(String string)
	{
		changeEventName = string;
	}
	
	/**
	 * @param string
	 */
	public void setExpandEventName(String expandEventName)
	{
		this.expandEventName = expandEventName;
	}
	

	/**
	 * @return
	 */
	public boolean isControlButtons()
	{
		return controlButtons;
	}

	/**
	 * @param controlButtons
	 */
	public void setControlButtons(boolean inDialog)
	{
		this.controlButtons = inDialog;
	}

	/**
	 * @return
	 */
	public int getSearchIn()
	{
		return searchIn;
	}

	/**
	 * @param i
	 */
	public void setSearchIn(int i)
	{
		searchIn = i;
	}

	/**
	 * @return
	 */
	public String getOptionalPrompt()
	{
		return optionalPrompt;
	}

	/**
	 * @param string
	 */
	public void setOptionalPrompt(String string)
	{
		optionalPrompt = string;
	}

	/**
	 * @return
	 */
	public String getOnChange()
	{
		return onChange;
	}

	/**
	 * @param string
	 */
	public void setOnChange(String string)
	{
		onChange = string;
	}

	/**
	 * @return
	 */
	public int getRefreshType()
	{
		return refreshType;
	}

	/**
	 * @param i
	 */
	public void setRefreshType(int refreshType)
	{
		this.refreshType = refreshType;
	}

	/**
	 * @return
	 */
	public String getMandatoryPrompt()
	{
		return mandatoryPrompt;
	}

	/**
	 * @param string
	 */
	public void setMandatoryPrompt(String string)
	{
		mandatoryPrompt = string;
	}

	/**
	 * @return
	 */
	public String getExpanderImage()
	{
		return expanderImage;
	}

	/**
	 * @param string
	 */
	public void setExpanderImage(String string)
	{
		expanderImage = string;
	}

	/**
	 * @return
	 */
	public String getalogParams()
	{
		return expanderDialogParams;
	}

	/**
	 * @param string
	 */
	public void setExpanderDialogParams(String string)
	{
		expanderDialogParams = string;
	}

	/**
	 * @return
	 */
	public String getExpanderTitle()
	{
		return expanderTitle;
	}

	/**
	 * @param string
	 */
	public void setExpanderTitle(String string)
	{
		expanderTitle = string;
	}


	/**
	 * @return
	 */
	public boolean isOpenOnKeyPress()
	{
		return openOnKeyPress;
	}

	/**
	 * @param b
	 */
	public void setOpenOnKeyPress(boolean b)
	{
		openOnKeyPress = b;
	}

	/**
	 * @return
	 */
	public boolean isAddMandatoryPrompt()
	{
		return addMandatoryPrompt;
	}

	/**
	 * @return
	 */
	public boolean isAddOptionalPrompt()
	{
		return addOptionalPrompt;
	}

	/**
	 * @param b
	 */
	public void setAddMandatoryPrompt(boolean b)
	{
		addMandatoryPrompt = b;
	}

	/**
	 * @param b
	 */
	public void setAddOptionalPrompt(boolean b)
	{
		addOptionalPrompt = b;
	}

	/**
	 * @return
	 */
	public String getExpanderButtonTitle()
	{
		return expanderButtonTitle;
	}

	/**
	 * @param string
	 */
	public void setExpanderButtonTitle(String string)
	{
		expanderButtonTitle = string;
	}

	/**
	 * @return
	 */
	public String getSrcListTitle()
	{
		return srcListTitle;
	}

	/**
	 * @return
	 */
	public String getTrgListTitle()
	{
		return trgListTitle;
	}

	/**
	 * @param string
	 */
	public void setSrcListTitle(String string)
	{
		srcListTitle = string;
	}

	/**
	 * @param string
	 */
	public void setTrgListTitle(String string)
	{
		trgListTitle = string;
	}

	/**
	 * @return
	 */
	public String getSrcListTitleClassName()
	{
		return srcListTitleClassName;
	}

	/**
	 * @return
	 */
	public String getTrgListTitleClassName()
	{
		return trgListTitleClassName;
	}

	/**
	 * @param string
	 */
	public void setSrcListTitleClassName(String string)
	{
		srcListTitleClassName = string;
	}

	/**
	 * @param string
	 */
	public void setTrgListTitleClassName(String string)
	{
		trgListTitleClassName = string;
	}

	/**
	 * @param b
	 */
	public void setEditable(boolean editable) 
	{
		this.editable = editable;
	}

	/**
	 * @return
	 */
	public int getDefaultSearchAction() {
		return defaultSearchAction;
	}

	/**
	 * @return
	 */
	public int getDefaultSearchType() {
		return defaultSearchType;
	}

	/**
	 * @return
	 */
	public int getSearchAction() {
		return searchAction;
	}

	/**
	 * @return
	 */
	public int getSearchType() {
		return searchType;
	}

	/**
	 * @param i
	 */
	public void setDefaultSearchAction(int i) {
		defaultSearchAction = i;
	}

	/**
	 * @param i
	 */
	public void setDefaultSearchType(int i) {
		defaultSearchType = i;
	}

	/**
	 * @param i
	 */
	public void setSearchAction(int i) {
		searchAction = i;
	}

	/**
	 * @param i
	 */
	public void setSearchType(int i) {
		searchType = i;
	}

	/**
	 * @return
	 */
	public String getOpenDialogButtonClassName() {
		return openDialogButtonClassName;
	}

	/**
	 * @param string
	 */
	public void setOpenDialogButtonClassName(String string) {
		openDialogButtonClassName = string;
	}

	/**
	 * @param b
	 */
	public void setOrderButtons(boolean b) {
		orderButtons = b;
	}

}
