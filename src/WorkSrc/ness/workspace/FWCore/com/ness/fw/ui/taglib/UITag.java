package com.ness.fw.ui.taglib;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.ui.AuthorizedEventData;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.DirtyModel;
import com.ness.fw.ui.UIConstants;
import com.ness.fw.ui.data.TagData;
import com.ness.fw.ui.data.UITagData;
import com.ness.fw.ui.events.CustomEvent;
import com.ness.fw.ui.events.Event;
import com.ness.fw.util.StringFormatterUtil;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.common.auth.*;
import com.ness.fw.flower.common.LanguagesManager;

import java.io.IOException;
import java.util.*;

public abstract class UITag extends BodyTagSupport 

	//In WAS6 this class implements the TryCatchFinally interface
	//implements javax.servlet.jsp.tagext.TryCatchFinally
{
	protected final static String JS_SEND_EVENT_GLOBAL = "sendEventGlobal"; 	
	protected final static String JS_SEND_EVENT = "sendEvent";
	protected final static String JS_SEND_EVENT_POPUP = "sendEventPopup";
	protected final static String JS_SEND_EVENT_DIALOG = "sendEventDialog";
	protected final static String JS_SEND_EVENT_NEW_WINDOW = "sendEventNewWindow";

	protected final static String JS_NEW_TREE = "new Tree";
	protected final static String JS_NEW_DIRTY_FLAG = "new DirtyFlag";
	
	private final static String JS_SET_DIRTY_FUNCTION = "setDirty"; 
	private final static String JS_FUNCTION_ADD_SHORTCUT = "addShortCut";
	private final static String JS_FUNCTION_LOGOUT_SYSTEM = "logoutSystem";
	/*constants html tags*/
	protected final static String DIV = "div";
	protected final static String IFRAME = "iframe";
	protected final static String TABLE = "table";
	protected final static String SPAN = "span";
	protected final static String CELL = "td";
	protected final static String ROW = "tr";
	protected final static String INPUT = "input";
	protected final static String SCRIPT = "script";
	protected final static String IMAGE = "IMG";
	protected final static String FONT = "font";
	protected final static String LINK = "a";
	protected final static String CSS_LINK = "link"; 
	protected final static String SELECT = "select";
	protected final static String OPTION = "option";
	protected final static String BODY = "body";
	protected final static String TEXTAREA = "textarea";
	protected final static String FORM = "form";
	protected final static String HEAD = "head";
	protected final static String PRE = "pre";
	protected final static String BEHAVIOUR_DOWNLOAD = "download";
	
	protected final static String STYLE_DISPLAY = "display";
	protected final static String STYLE_NONE = "none";
	protected final static String STYLE_POSITION = "position";
	protected final static String STYLE_ABSOLUTE = "absolute";
	protected final static String STYLE_VERTICAL_ALIGN = "vertical-align";

	/*constants html attributes*/
	protected final static String ID = "id";
	protected final static String TYPE = "type";
	protected final static String WIDTH = "width";
	protected final static String HEIGHT = "height";
	protected final static String CLASS = "class";
	protected final static String STYLE = "style";
	protected final static String COLSPAN = "colspan";
	protected final static String ROWSPAN = "rowspan";
	protected final static String BORDER = "border";
	protected final static String CELLPADDING = "cellpadding";
	protected final static String CELLSPACING = "cellspacing";
	protected final static String HREF = "href";
	protected final static String CHECKED = " checked";
	protected final static String CURRENTLY_CHECKED = " currentlyChecked";
	protected final static String SELECTED = " selected";
	protected final static String CURRENTLY_SELECTED = "currentlySelected";	
	protected final static String MULTIPLE = "multiple";
	protected final static String SINGLE = "single";
	protected final static String FACE = "face";
	protected final static String NAME = "name";
	protected final static String VALUE = "value";
	protected final static String ALIGN = "align";
	protected final static String VALIGN = "valign";
	protected final static String SIZE = "size";
	protected final static String DISABLED = " disabled";
	protected final static String READONLY = " readonly";
	protected final static String NOWRAP = "nowrap";
	protected final static String DIR = "direction";
	protected final static String DIR_BODY = "dir";
	protected final static String SRC = "src";
	protected final static String TITLE = "title";
	protected final static String MAX_LENGTH = "maxlength";
	protected final static String TA_COLS = "cols";
	protected final static String TA_ROWS = "rows";
	protected final static String TARGET = "target";
	protected final static String ACTION = "action";
	protected final static String METHOD = "method";
	protected final static String ALT = "alt";
	protected final static String CSS_REL = "rel";
	protected final static String CSS_STYLESHEET = "stylesheet";
	protected final static String CSS_PAGE_TYPE = "text/css";
	protected final static String CSS_MEDIA = "media";
	protected final static String CSS_MEDIA_ALL = "all";
	protected final static String CSS_MEDIA_PRINT = "print";
	protected final static String CSS_MEDIA_SCREEN = "screen";
	
	protected final static String ENABLE_NOT_AUTHORIZED = " enableNotAuthorized";

	protected final static String BUTTON = "button";
	protected final static String SUBMIT = "submit";
	protected final static String HIDDEN = "hidden";
	protected final static String IMAGE_BUTTON = "image";
	protected final static String TEXT = "text";
	protected final static String CHECKBOX = "checkbox";
	protected final static String RADIO_BUTTON = "radio";
	protected final static String CENTER = "center";
	protected final static String RIGHT = "right";
	protected final static String LEFT = "left";
	protected final static String TOP = "top";
	protected final static String BOTTOM = "bottom";
	protected final static String CURSOR_HAND = "cursor:hand";
	protected final static String DISPLAY_NONE = "display:none";
	protected final static String WEBDINGS = "font-family:webdings";
	protected final static String DIR_LTR = "ltr";
	protected final static String DIR_RTL = "rtl";
	protected final static String HTML_INPUT_TYPE_SUBMIT = "submit";
	protected final static String HTML_INPUT_TYPE_PASSWORD = "password";
	protected final static String HTML_METHOD_POST = "post";
	protected final static String HTML_VALIGN_MIDDLE = "middle";
	
	protected final static String ONCLICK = "onclick";
	protected final static String ONDBLCLICK = "ondblclick";
	protected final static String ONMOUSEOVER = "onmouseover";
	protected final static String ONMOUSELEAVE = "onmouseleave";
	protected final static String ONMOUSEOUT = "onmouseout";
	protected final static String ONRESIZE = "onresize";
	protected final static String ONCHANGE = "onchange";
	protected final static String ONLOAD = "onload";
	protected final static String ONFOCUS = "onfocus";
	protected final static String ONBLUR = "onblur";
	protected final static String ONKEYPRESS = "onkeypress";
	protected final static String ONPASTE = "onpaste";
	protected final static String ONKEYUP = "onkeyup";
	protected final static String ONKEYDOWN = "onkeydown";
	protected final static String ONPROPERTYCHANGE = "onpropertychange";
	protected final static String ONSUBMIT = "onsubmit";
	protected final static String ONDRAG = "ondrag";
	protected final static String ONCONTEXTMENU = "oncontextmenu";
	
	protected final static String WEBDINGS_SYMBOL_ARROW_RIGHT = "4";
	protected final static String WEBDINGS_SYMBOL_ARROW_LEFT = "3";
	protected final static String WEBDINGS_SYMBOL_ARROW_UP = "5";
	protected final static String WEBDINGS_SYMBOL_ARROW_DOWN = "6";
	protected final static String WEBDINGS_SYMBOL_2_ARROW_LEFT = "7";
	protected final static String WEBDINGS_SYMBOL_2_ARROW_RIGHT = "8";
	
	protected static final String CSS_SUFFIX_SELECTED = "Selected";
	protected static final String CSS_SUFFIX_DISABLED = "Disabled";
	protected static final String CSS_SUFFIX_LAST = "Last";
	protected static final String CSS_SUFFIX_FIRST = "First";
	protected static final String CSS_SUFFIX_TOP = "Top";
	protected static final String CSS_SUFFIX_BOTTOM = "Bottom";
	
	protected static final int CSS_USE_SELECTED = 1;
	protected static final int CSS_USE_DISABLED = 2;
	protected static final int CSS_USE_LAST = 4;
	protected static final int CSS_USE_DIRECTION = 8;
	
	private final static String END_OF_LINE = "\n";
	private final static String HTML_START_TAG = "<";
	private final static String HTML_START_TAG_CLOSE = ">";
	private final static String HTML_END_TAG = "</";
	
	private final static String REMARK_START = "<!--";
	private final static String REMARK_END = "-->";
	private final static String BRACKET_START = "(";
	private final static String BRACKET_END = ")";
	protected final static String SPACE = "&nbsp;";
	protected final static String BR = "<br>";
	protected final static String NOBR = "nobr";
	protected final static String QUOT = "\"";
	protected final static String SLASH = "\\";
	protected final static String SINGLE_QUOT = "'";
	protected final static String BLANK = " ";
	protected final static String COMMA = ",";
	protected final static String COMMA_HTML = "&#44;";
	protected final static String COMMA_QUOT = "&quot;";
	protected final static String SINGLE_QUOT_HTML = "&acute;";
	protected final static String CANCEL_BUBBLE = "event.cancelBubble='true'";
	protected final static String BUBBLE = "event.cancelBubble='false'";
	protected final static String CLOSE_WINDOW = "self.close()";
	protected final static String WINDOW_NAME = "window.name";
	protected final static String JS_RETURN_FALSE = "return false;";
	protected final static String JS_FUNCTION_CALL = "javascript:";
	protected final static String JS_EVENT_OBJECT = "event";
	protected final static String JS_END_OF_LINE = ";";
	protected final static String JS_EQUALS = "=";
	protected final static String JS_DOT = ".";
	protected final static String JS_OBJECT_LENGTH = "length";
	protected final static String JS_VARIABLE_DECLARATION = "var ";
	protected final static String JS_ARRAY_BRACKET_START = "[";
	protected final static String JS_ARRAY_BRACKET_END = "]";
	protected final static String SEPERATOR = "|";
	protected final static String SEPERATOR_MINUS = "-";
	protected final static String UNDERSCORE = "_";
	protected final static String THIS = "this";
	protected final static String DEFAULT_IMAGE_DIR = "images";
	protected final static String DEFAULT_SCRIPT_DIR = "scripts";
	protected final static String DEFAULT_CSS_DIR = "theme";
	
	protected static final String REQUEST_ATTRIBUTE_DISPLAY_STATE = "displayStateRequestAttribute"; 
	protected static final String REQUEST_ATTRIBUTE_DIRTY = "dirtyRequestAttribute";
	protected static final String REQUEST_ATTRIBUTE_DIRTABLE = "dirtableRequestAttribute";
	
	protected final static String MESSAGES_KEY_PREFIX = "msg.";
	protected final static String MESSAGES_KEY_SUFFIX = ".text";
	
	protected final static String PROPERTY_NAME_SYSTEM_FILE_SEPERATOR = "file.separator";
	protected final static String PROPERTY_KEY_SYSTEM_SCRIPTS_DIRECTORY = "ui.directory.scripts";
	protected final static String PROPERTY_KEY_SYSTEM_CSS_DIRECTORY = "ui.directory.css";
	protected final static String PROPERTY_KEY_AUTH_IGNORE = "ui.auth.ignore";
	protected final static String PROPERTY_KEY_USE_BUFFER_SIZE = "ui.useTagBufferSize";
	protected final static String PROPERTY_KEY_BUFFER_SIZE = "ui.tagBufferSize";
	
	private final static String SHORTCUT_SHIFT = "Shift";
	private final static int SHORTCUT_SHIFT_PRESSED = 1;
	private final static String SHORTCUT_CTRL = "Ctrl";
	private final static int SHORTCUT_CTRL_PRESSED = 2;
	private final static String SHORTCUT_ALT = "Alt";
	private final static int SHORTCUT_ALT_PRESSED = 4;
	private final static String SHORTCUT_SEPERATOR = "+";
	private final static String SHORTCUT_COMPONENT_PREFIX = "scId=";
	
	private final static int DEFAULT_BUFFER_SIZE = 5000; 
	
	/**
	 * TagData object used setting attributes for this tag
	 */
	protected TagData tagData;
	
	/**
	 * prefix for all css class names
	 */
	protected String cssPre = "";
	
	/**
	 * buffer for start tag output
	 */
	protected StringBuffer startOutput;
	
	/**
	 * buffer for end tag output
	 */
	protected StringBuffer endOutput;

	/**
	 * indicates if appending data to start tag or end tag
	 */
	protected boolean appendStart = true;
		
	/**
	 * The return value of the doStart method.
	 * Default value is SKIP_BODY.Tags which contains other tags,will probably<br>
	 * change this value to EVAL_BODY_INCLUDE.
	 */
	protected int startTagReturnValue = SKIP_BODY;
	
	/**
	 * The return value of the doStart method when the authorization level is
	 * none.The default value is SKIP_BODY, which prevent the evaluation of all tags 
	 * inside this unauthorized tag,including html tags.In order to evaluate tags
	 * inside an unauthorized tag,the value of startTagNotAuthorizedReturnValue should
	 * be assigned to EVAL_BODY_INCLUDE. 
 	 */
	protected int startTagNotAuthorizedReturnValue = SKIP_BODY;	
		
	/**
	 * The return value of the doEnd method.
	 * Default value is EVAL_PAGE
	 */
	protected int endTagReturnValue = EVAL_PAGE;

	/**
	 * Indicates if the startOutput buffer should be printed by the doStartTag method.
	 * Default value is true.If it is changed to false,other methods of the tag
	 * should print the startOutPut buffer.
	 */
	protected boolean printStartOutput = true;
	
	/**
	 * Indicates if the endOutput buffer should be printed by the doEndTag method.
	 * Default value is true.If it is changed to false,other methods of the tag
	 * should print the endOutPut buffer.
	 */
	protected boolean printEndOutput = true;
	
	/**
	 * Holds the authorization id of the element.
	 */
	protected String authId;
	
	/**
	 * The state of the input tag,which may be on the following constants:<br>
	 * UIConstants.COMPONENT_ENABLED_STATE(default)<br>
	 * UIConstants.COMPONENT_DISABLED_STATE<br>
	 * UIConstants.COMPONENT_HIDDEN_STATE
	 */
	protected String state = UIConstants.COMPONENT_ENABLED_STATE;

	/**
	 * 
	 */
	protected String stateString = "";

	/**
	 * Keeps information of the authorization level of the element.
	 */
	protected ElementAuthLevel elementAuthLevel = null;
	
	/**
	 * Indicates if this tag should ignore the authorization level and render itself.
	 * Default value is false.
	 */
	protected boolean ignoreAuth = false;
	
	/**
	 * Indicates if this tag should ignore the state and render itself.
	 * Default value is false.
	 */
	protected boolean ignoreState = false;
	
	/**
	 * Indicates if this tag should initialize the authorization information.
	 * Default value is true.
	 */
	protected boolean initAuth = true;
	
	/**
	 * Indicates if this tag should be rendered
	 */
	private boolean renderTag = true;
	
	/**
	 * The text to render when the tag has the authorization level of none.
	 */
	protected String unauthorizedMessage = "";
	
	/**
	 * If false,javascript enabling of the component is not allowed.
	 */
	protected boolean allowClientEnabling = true; 
	
	/**
	 * if true,input updates in this tag,will change the value of
	 * the "dirty" flag.
	 */
	protected boolean dirtable = false;
	
	/**
	 * The id of the dirty model that contains this component.If no dirty model<br>
	 * contains this component the value of this attribute is an empty string.
	 */
	protected String dirtyModelId = "";
	
	/**
	 * The user's locale
	 */
	private Locale locale = null;
	
	/**
	 * The user's locale's language
	 */
	private String localeLanguage = null;

	/**
	 * The name of the directory for holding the localized images
	 */
	private String imagesLocalizedDirectory = null;

	/**
	 * The user's locale's direction
	 */
	private String localeDirection = null;
	
	/**
	 * 
	 */
	protected int tagBufferSize = DEFAULT_BUFFER_SIZE; 
	
	/**
	 * 
	 */
	private boolean useTagBufferSize = false;
	
	/**
	 * Indicates if this tag's attributes should be reseted after processing<br>
	 * this tag.
	 */
	private boolean resetTag = true;
	
	/**
	 * The {@link com.ness.fw.common.resources.LocalizedResources} object which
	 * is used by the tag.
	 */
	private LocalizedResources localizedResources = null;	
	
	/**
	 * Renders tag of type UITag
	 * @param tag the tag to render
	 */
	protected void renderTag(UITag tag) throws UIException
	{
		try
		{
			tag.setPageContext(pageContext);
			tag.setParent(this);
			tag.doStartTag();
			tag.doEndTag();	
		}
		catch (JspException je)
		{
			throw new UIException("exception in rendering tag " + tag.getId() + " from tag " + getClass().getName(),je);
		}			
	}
	
	/**
	 * Returns the output of tag
	 * @param tag
	 * @return
	 * @throws UIException
	 */
	protected String getTagOutPut(UITag tag) throws UIException
	{
		try
		{
			tag.setPageContext(pageContext);
			tag.setPrintStartOutput(false);
			tag.setPrintEndOutput(false);
			tag.doStartTag();

			//relevant only in WAS5!!!!!!!!! 
			//in WAS6 the reset method is called from the doFinally method and
			//not from the doEndTag methos,so there is no problem in reseting 
			//the tag.
			tag.resetTag = false;
			
			tag.doEndTag();	
			return tag.getTagOutput();		
		}
		catch (JspException je)
		{
			throw new UIException("exception in rendering tag " + tag.getId() + " from tag " + getClass().getName(),je);
		}		
	}
	
	/**
	 * Called in the start of each tag.
	 * Performs the following:
	 * 1.Calls to the initAuth method which initializes the authorization object for 
	 * this tag.
	 * 2.Calls to the init method which initializes different parameters of
	 * the tag.
	 * 3.Calls the renderStartTag method which renders the html for the first part
	 * of the tag. 
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			initAttributes();
			initSystemProperties();
			init();
			validateAttributes();
			if (isTagAuthorizedToRender())
			{			
				renderStartTag();
			}
			else
			{
				append(getUnauthorizedMessage());
				startTagReturnValue = startTagNotAuthorizedReturnValue;
			}
			if (printStartOutput)
			{
				print(startOutput.toString());			
			}
			return startTagReturnValue;
		}
		catch (UIException ui)
		{
			ui.setUITagName(getClass().getName());
			Logger.debug(getClass().getName(),ui);
			throw new JspException(ui.getMessage(),ui);
		}
		catch (Throwable t)
		{
			Logger.debug(getClass().getName(),t);
			throw new JspException(t);
		}
	}

	/**
	 * Called in the end of each tag.
	 * Performs the following:
	 * 1.Calls the renderEndTag method which renders the html for the last part
	 * of the tag. 
	 * 2.Removes the authorization level from the stack,so it will not affect on
	 * tags outside this tag.
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			if (isTagAuthorizedToRender())
			{
				renderEndTag();
				if (printEndOutput)
				{
					print(endOutput.toString());
				}
			}	
			removeAuth();
			
			//Call the this method for reseting the tag's attribute.
			//In WAS6 this method will be called from the doFinally method
			//and not from the doEndTag method.
			if (resetTag)
			{
				resetTagState();
			}
			return endTagReturnValue;
		}
		catch (UIException ui)
		{
			ui.setUITagName(getClass().getName());
			Logger.debug(getClass().getName(),ui);
			throw new JspException(ui.toString());
		}
		catch (Throwable t)
		{
			Logger.debug(getClass().getName(),t);
			throw new JspException(t);
		}	
	}
	
	protected void resetTagState()
	{
		startOutput = new StringBuffer(tagBufferSize);
		endOutput = new StringBuffer(tagBufferSize);
		authId = null;
		appendStart = true;
		cssPre = "";
		startTagReturnValue = SKIP_BODY;
		endTagReturnValue = EVAL_PAGE;
		startTagNotAuthorizedReturnValue = SKIP_BODY;
		printStartOutput = true;
		printEndOutput = true;
		elementAuthLevel = null;
		ignoreAuth = false;
		initAuth = true;
		allowClientEnabling = true;
		dirtyModelId = "";
		renderTag = true;
		tagData = null;
		locale = null;
		localeLanguage = null;
		localeDirection = null;	
		imagesLocalizedDirectory = null;
		useTagBufferSize = false;
		tagBufferSize = DEFAULT_BUFFER_SIZE;
		localizedResources = null;		
	}
	
	protected boolean isTagAuthorizedToRender()
	{
		return (ignoreAuth || isAuthorized()) && renderTag;
	}
	
	protected void initSystemProperties() throws UIException
	{
		if (!ignoreAuth)
		{
			ignoreAuth = new Boolean(getUIProperty(PROPERTY_KEY_AUTH_IGNORE)).booleanValue();
		}	
		useTagBufferSize = 	new Boolean(getSystemProperty(PROPERTY_KEY_USE_BUFFER_SIZE)).booleanValue();	
		tagBufferSize = Integer.parseInt(getSystemProperty(PROPERTY_KEY_BUFFER_SIZE));
		startOutput = new StringBuffer(tagBufferSize);
		endOutput = new StringBuffer(tagBufferSize);
		initAuth();
	}
			
	protected void initAttributes()
	{
		if (tagData != null)
		{
			UITagData uiTagData = (UITagData)tagData;
			if (uiTagData.getState() != null)
			{
				setState(uiTagData.getState());
			}			
			if (uiTagData.isDirtable() != null)
			{
				setDirtable(uiTagData.isDirtable().booleanValue());
			}
			if (uiTagData.getAuthId() != null)
			{
				setAuthId(uiTagData.getAuthId());
			}
			if (uiTagData.getCssPre() != null)
			{
				setCssPre(uiTagData.getCssPre());
			}
		}
	}
	
	protected void validateAttributes() throws UIException
	{
		//validate state
		if (!state.equals(UIConstants.COMPONENT_ENABLED_STATE) && !state.equals(UIConstants.COMPONENT_DISABLED_STATE) && !state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			throw new UIException("the state attribute of tag with id " + id + " cannot be set to " + state);
		}
	}
	
	/**
	 * Called in the begining renderStartTag method,after the init method.
	 * @throws UIException
	 */
	protected abstract void renderStartTag() throws UIException;
	
	/**
	 * Called in the begining renderEndTag method.
	 * @throws UIException
	 */	
	protected abstract void renderEndTag() throws UIException;
	
	/**
	 * Called in the begining of renderStartTag method.
	 * Used for initializing stuff in the tag.
	 * @throws UIException
	 */
	protected void init() throws UIException
	{
		
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
			elementAuthLevel = FlowerUIUtil.getAuthLevelInformation(authId,getHttpRequest(),getClass().getName());
		}
	}

	/**
	 * Initializes the state of the component
	 * @throws UIException
	 */
	protected void initState() throws UIException
	{
		//First check if the component is not authorized to be rendered
		//In this case a state check is not needed
		if (isAuthorized())
		{ 
			//Check if displayState tag contains this tag
			if (getRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE) != null)
			{
				String displayState = (String)((Stack)getRequestContextValue(REQUEST_ATTRIBUTE_DISPLAY_STATE)).peek();
				
				//Check if the state in the wrapper displayState tag is
				//UIConstants.COMPONENT_HIDDEN_STATE
				if (displayState.equals(UIConstants.COMPONENT_HIDDEN_STATE))
				{
					setRenderTag(false);
				}
				
				//Check if the state in the wrapper displayState tag is
				//UIConstants.COMPONENT_DISABLED_STATE				
				else if (displayState.equals(UIConstants.COMPONENT_DISABLED_STATE))
				{
					if (!state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
					{
						state = displayState;
					}
					stateString = DISABLED;
					allowClientEnabling = false;
				}
			}
			
			//check the state in the model
			setModelState();
			
			//set stateString
			if (state.equals(UIConstants.COMPONENT_DISABLED_STATE))		
			{
				stateString = DISABLED;
			}
		}
	}
	
	protected boolean isDisabled()
	{
		return stateString.equals(DISABLED);
	}
	
	protected boolean isHidden()
	{
		return state.equals(UIConstants.COMPONENT_HIDDEN_STATE);
	}
	
	/**
	 * Compares the severity of 2 states.
	 * @param state1 the first state
	 * @param state2 the second state
	 * @return true in the following cases
	 * 1.state1 is UIConstants.COMPONENT_HIDDEN_STATE and state2 is UIConstants.COMPONENT_DISABLED_STATE or UIConstants.COMPONENT_ENABLED_STATE
	 * 2.state2 is UIConstants.COMPONENT_DISABLED_STATE and state2 is UIConstants.COMPONENT_ENABLED_STATE
	 */
	protected boolean isStricterState(String state1,String state2)
	{
		return 
		state1.equals(UIConstants.COMPONENT_HIDDEN_STATE) && (state2.equals(UIConstants.COMPONENT_DISABLED_STATE) || state2.equals(UIConstants.COMPONENT_ENABLED_STATE))
		||
		state1.equals(UIConstants.COMPONENT_DISABLED_STATE) && state2.equals(UIConstants.COMPONENT_ENABLED_STATE);
	}


	protected void setModelState()
	{
	}
	
	protected void removeAuth() throws UIException
	{
		if (initAuth)
		{
			FlowerUIUtil.removeAuthLevel(elementAuthLevel,getHttpRequest(),getClass().getName());
		}
	}
	
	protected int getAuthLevel()
	{
		return FlowerUIUtil.getAuthLevel(elementAuthLevel);
	}
	
	/**
	 * Checks if the authorization level for this tag is AUTH_LEVEL_READONLY
	 * @return true if the authorization level for this tag is AUTH_LEVEL_READONLY.
	 */
	protected boolean isPreviewAuth()
	{
		return FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_READONLY;
	}
	
	/**
	 * Returns true if the component's authorization level allows the component<br>
	 * to be rendered.
	 * @return
	 */
	protected boolean isAuthorized()
	{
		return FlowerUIUtil.getAuthLevel(elementAuthLevel) != ElementAuthLevel.AUTH_LEVEL_NONE;
	}
	
	/**
	 * 
	 * @param eventName
	 * @param eventTargetType
	 * @return
	 */
	protected AuthorizedEventData addAuthorizedEvent(String eventName,String eventTargetType) throws UIException
	{
		return addAuthorizedEvent(eventName,eventTargetType,false,false);
	}
	
	/**
	 * 
	 * @param eventName
	 * @param eventTargetType
	 * @return
	 */
	protected AuthorizedEventData addAuthorizedEvent(String eventName,String eventTargetType,boolean useIdAsPrefix) throws UIException
	{
		return addAuthorizedEvent(eventName,eventTargetType,useIdAsPrefix,false);
	}	

	/**
	 * 
	 * @param eventName
	 * @param eventTargetType
	 * @param useIdAsPrefix
	 * @return
	 */
	protected AuthorizedEventData addAuthorizedEvent(String eventName,String eventTargetType,boolean useIdAsPrefix,boolean checkWarnings) throws UIException
	{
		validateEventSructure(eventName);
		String componentDesc = "component class " + getClass().getName();
		if (id != null)
		{
			componentDesc += " id " + id;
		}
		if (useIdAsPrefix)
		{
			eventName = id + SEPERATOR + eventName;
		}
		if (checkWarnings)
		{
			FlowerUIUtil.addAuthorizedEvent(eventName + Event.WARNING_EVENT_SUFFIX,eventTargetType,getHttpRequest(),componentDesc);
		}
		return FlowerUIUtil.addAuthorizedEvent(eventName,eventTargetType,getHttpRequest(),componentDesc);
	}
	
	/**
	 * Validates the structure of the name of the event.
	 * Relevant only in debug mode.
	 * @param eventName the name of the event to validate.
	 * @throws UIException if the event name's structure is not valid
	 */
	private void validateEventSructure(String eventName) throws UIException
	{
		if (SystemResources.getInstance().isDebugMode())
		{
			if (eventName.indexOf("'") != -1 || eventName.indexOf("\"") != -1)
			{
				throw new UIException("the name of event " + eventName + " in tag " + id + " is not valid");
			}
		}
	}
				
	/**
	 * Checks if an event should be rendered according to the event and
	 * the authoriztion level.
	 * @param event the event object
	 * @return
	 */
	protected boolean isEventRendered(Event event)
	{
		return event != null &&	isEventAllowedByState(event) &&	isEventAllowedByAuth(event);
	}
					
	/**
	 * Checks if an event should be rendered according to the event type and
	 * the authoriztion level.
	 * @param eventType the type of the event - READONLY or READWRITE
	 * @return
	 */
	protected boolean isEventRendered(int eventType)
	{
		return isEventRendered(new Event(eventType));
	}
	
	/**
	 * Checks if an event should be rendered according to the event type
	 * @param elementAuthLevel
	 * @param eventType
	 * @return
	 */
	protected boolean isEventRendered(ElementAuthLevel elementAuthLevel,int eventType,String state,String stateString)
	{
		return isEventAllowedByAuth(elementAuthLevel,new Event(eventType)) && isEventAllowedByState(state,stateString,new Event(eventType));
	}
	
	private boolean isEventAllowedByState(Event event)
	{
		return 
			state.equals(UIConstants.COMPONENT_ENABLED_STATE)||
			state.equals(UIConstants.COMPONENT_HIDDEN_STATE) && !stateString.equals(DISABLED) ||
			state.equals(UIConstants.COMPONENT_DISABLED_STATE) && event.getEventType() == Event.EVENT_TYPE_READONLY ||
			ignoreState && event != null;			
	}
	
	private boolean isEventAllowedByAuth(Event event)
	{
		if (FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_READONLY && event.getEventType() == Event.EVENT_TYPE_READWRITE)
		{
			allowClientEnabling = false;
		}
		return 
			FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_ALL ||
			FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_READONLY && event.getEventType() == Event.EVENT_TYPE_READONLY ||
			ignoreAuth && event != null;
	}

	private boolean isEventAllowedByState(String state,String stateString,Event event)
	{
		return 
			state.equals(UIConstants.COMPONENT_ENABLED_STATE)||
			state.equals(UIConstants.COMPONENT_HIDDEN_STATE) && !stateString.equals(DISABLED) ||
			state.equals(UIConstants.COMPONENT_DISABLED_STATE) && event.getEventType() == Event.EVENT_TYPE_READONLY ||
			ignoreState && event != null;			
	}

	private boolean isEventAllowedByAuth(ElementAuthLevel elementAuthLevel,Event event)
	{
		return 
			FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_ALL ||
			FlowerUIUtil.getAuthLevel(elementAuthLevel) == ElementAuthLevel.AUTH_LEVEL_READONLY && event.getEventType() == Event.EVENT_TYPE_READONLY ||
			ignoreAuth && event != null;
	}
		
	/**
	 * 
	 * @param event
	 * @throws UIException
	 */
	protected void checkDirtyFlag(Event event) throws UIException
	{
		checkDirtyFlag(event.isCheckDirty(),event.isCheckWarnings(),event.getDirtyModels(),event.isAllowEventOnDirtyIds(),event.getEventTargetType());
	}
	
	/**
	 * 
	 * @param checkDirty
	 * @param checkWarnings
	 * @throws UIException
	 */
	protected void checkDirtyFlag(boolean checkDirty,boolean checkWarnings,List dirtyModels,boolean allowEventOnDirtyIds,String eventTargetType) throws UIException
	{
		if (checkDirty && dirtable)
		{
			throw new UIException("dirty flag of event and dirty flag of tag " + getClass().getName() + " id: " + id + " are not allowed to be true");
		}
		if (checkWarnings && dirtable)
		{
			throw new UIException("check warnings flag of event and dirty flag of tag " + getClass().getName() + " id: " + id + " are not allowed to be true");
		}
		if (checkDirty && checkWarnings)
		{
			throw new UIException("check warnings flag of event and dirty flag of event are not allowed to be true in" + getClass().getName() + " id: " + id);
		}
		if (dirtyModels != null && checkWarnings && allowEventOnDirtyIds)
		{
			throw new UIException("check warnings flag of event and allowEventOnDirtyIds are not allowed to be true in"	+ getClass().getName() + " id: " + id);
		}
		if (checkWarnings && (eventTargetType.equals(Event.EVENT_TARGET_TYPE_DIALOG) || eventTargetType.equals(Event.EVENT_TARGET_TYPE_POPUP)))
		{
			throw new UIException("check warnings flag of event cannot be set to true wheh the eventTargetType of event is set to Event.EVENT_TARGET_TYPE_DIALOG or Event.EVENT_TARGET_TYPE_POPUP");
		}
	}

	/**
	 * Returns the message to write when the authorization level is AUTH_LEVEL_NONE
	 * @return
	 */
	protected String getUnauthorizedMessage()
	{
		return unauthorizedMessage;
	}
	
	/**
	 * Returns a value of a key from a localized properties file
	 * @param key the key to read from the localized properties file
	 */
	protected String getLocalizedText(String key)
	{
		return getLocalizedText(key,false);
	}

	/**
	 * Returns a value of a key from a localized properties file
	 * @param key the key to read from the localized properties file
	 * @param addMessagesSuffix if true add a constant suffix to the key
	 */
	protected String getLocalizedText(String key,boolean addMessagesSuffix)
	{
		return getLocalizedText(key,addMessagesSuffix,false);
	}
	
	/**
	 * Returns a value of a key from a localized properties file
	 * @param key the key to read from the localized properties file
	 * @param addMessagesSuffix if true add a constant suffix to the key
	 * @param htmlFormat if true the single quot of the strings(') are replaced by "\\'"
	 */
	protected String getLocalizedText(String key,boolean addMessagesSuffix,boolean htmlFormat)
	{
		if (addMessagesSuffix)
		{
			key = MESSAGES_KEY_PREFIX + key + MESSAGES_KEY_SUFFIX;
		}
		
		String localizedText = null;
		try
		{
			localizedText = getLocalizedResources().getString(key);
		}
		catch (ResourceException re)
		{
			localizedText = key;
		}		
		
		if (!htmlFormat)
		{
			return localizedText;
		}
		else
		{
			return StringFormatterUtil.replace(localizedText,SINGLE_QUOT,"\\'");
		}
	}
	
	protected LocalizedResources getLocalizedResources()
	{
		if (localizedResources == null)
		{
			initLocalizedResources();
		}
		return localizedResources;
	}
	
	protected void setLocalizedResources(LocalizedResources localizedResources)
	{
		this.localizedResources = localizedResources;
	}
	
	protected void initLocalizedResources()
	{
		setLocalizedResources(FlowerUIUtil.getLocalizedResources(getHttpRequest()));
	}
	
	protected String initLocalizedAttribute(String attribute,String attributeLocalizedKey)
	{
		String returnAttr = "";
		if (attribute == null)
		{
			returnAttr = getLocalizedText(attributeLocalizedKey);
		}
		else
		{
			returnAttr = attribute;
		}
		return returnAttr;
	}
	
	protected String getSystemProperty(String key)
	{
		return FlowerUIUtil.getSystemProperty(key);
	}
	
	protected String initSystemProperty(String prop,String propKey)
	{
		String returnProp = "";
		if (prop == null)
		{
			returnProp = getSystemProperty(propKey);
		}
		else
		{
			returnProp = prop;
		}
		return returnProp;
	}
	
	protected String initUIProperty(String prop,String propKey)
	{
		if (prop == null)
		{
			return getUIProperty(propKey);
		}
		else
		{
			return prop;
		}
	}
	
	protected String getUIProperty(String key)
	{
		return FlowerUIUtil.getSYSUIProperty(key);
	}
	
	protected String getAppUIProperty(String key)
	{
		return FlowerUIUtil.getAPPUIProperty(key);
	}
	
	protected String getFormattedHtmlValue(String value)
	{
		return StringFormatterUtil.replace(value,"\"",COMMA_QUOT);
	}
	
	protected String getFormattedJSValue(String value)
	{
		return StringFormatterUtil.replace(value,"\"",SLASH + QUOT);
	}
	
	protected LocalizedResources getLocalizable() throws UIException
	{
		return FlowerUIUtil.getLocalizable(getHttpRequest());
	}
	
	protected void appendToStart()
	{
		appendStart = true;
	}
	
	protected void appendToEnd()
	{
		appendStart = false;
	}
	
	protected void append(String str)
	{
		if (appendStart)
		{
			if (printStartOutput && useTagBufferSize && isBufferReachedLimit(startOutput,str))
			{
				//System.out.println("buffer reached limit in tag " + this.getClass() + " id=" + id);
				print(startOutput.toString());
				startOutput = new StringBuffer(tagBufferSize);
			}
			startOutput.append(str);
		}
		else
		{
			if (printEndOutput && useTagBufferSize && isBufferReachedLimit(endOutput,str))
			{
				print(endOutput.toString());
				endOutput = new StringBuffer(tagBufferSize);
			}
			endOutput.append(str);
		}
	}
	
	private boolean isBufferReachedLimit(StringBuffer sb,String str)
	{ 
		return (str == null || (sb.length() + str.length() > tagBufferSize));
	}
	
	protected void appendln(String str)
	{
		append(str + END_OF_LINE);
	}
	
	protected void appendln()
	{
		appendln("");
	}
	
	protected void print(String str)
	{
		try
		{
			pageContext.getOut().print(str);
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	
	protected void printStartOutput()
	{
		print(startOutput.toString());
	}
	
	protected void println(String str)
	{
		try
		{
			pageContext.getOut().println(str);
		}
		catch (IOException io)
		{
			io.printStackTrace();
		}
	}
	
	/**
	 * Returns the output of this tag
	 * @return
	 */
	protected String getTagOutput()
	{
		return startOutput.toString() + endOutput.toString();
	}
	
	/**
	 * Sets the printStartOutput attribute.If it is set to true the output
	 * of the start of this tag will be printed
	 * @param printStartOutput
	 */
	protected void setPrintStartOutput(boolean printStartOutput)
	{
		this.printStartOutput = printStartOutput;
	}

	/**
	 * Sets the printEndOutput attribute.If it is set to true the output
	 * of the end of this tag will be printed
	 * @param printStartOutput
	 */
	protected void setPrintEndOutput(boolean printEndOutput)
	{
		this.printEndOutput = printEndOutput;
	}	
	
	protected Object getPageContextValue(String key)
	{
		return pageContext.getAttribute(key);
	}
	
	protected void setPageContextValue(String key,Object value)
	{
		pageContext.setAttribute(key,value);
	}
	
	protected Object getRequestContextValue(String key)
	{
		return pageContext.getRequest().getAttribute(key);
	}	
	
	protected String getRequestParameter(String key)
	{
		return pageContext.getRequest().getParameter(key);
	}
	
	protected void setRequestContextValue(String key,Object value)
	{

		pageContext.getRequest().setAttribute(key,value);
	}
	
	protected void removeRequestContextValue(String key)
	{
		pageContext.getRequest().removeAttribute(key);
	}

	protected HttpServletRequest getHttpRequest()
	{
		return (HttpServletRequest)pageContext.getRequest();
	}
	
	protected HttpSession getHttpSession()
	{
		return pageContext.getSession();
	}
	
	/*util methods for handling ancestor tag*/
	protected void appendToAncestor(Class ancestorClass, boolean appendToStart, String str)
	{
		Tag tag = findAncestorWithClass(this, ancestorClass);
		if (tag instanceof UITag)
		{
			UITag ancestor = (UITag) tag;
			ancestor.println(str);
			/*if (appendToStart) ancestor.appendToStart();
			else ancestor.appendToEnd();
			ancestor.append(str);*/
		}
	}
	/*general html elements*/
	protected void startTag(String name)
	{
		append(HTML_START_TAG + name);
	}
	
	protected void startTag(String name,boolean isClose)
	{
		append(HTML_START_TAG + name);
		if (isClose) append(HTML_START_TAG_CLOSE);
	}
	
	protected void startTagLn(String name,boolean isClose)
	{
		append(HTML_START_TAG + name);
		if (isClose) append(HTML_START_TAG_CLOSE + END_OF_LINE);
	}
	
	
	protected String getStartTag(String name)
	{
		return HTML_START_TAG + name + BLANK;
	}
	
	protected void endTag(String tagName)
	{
		append(HTML_END_TAG + tagName + HTML_START_TAG_CLOSE);
	}
	
	protected void endTagLn(String tagName)
	{
		append(HTML_END_TAG + tagName + HTML_START_TAG_CLOSE + END_OF_LINE);
	}
		
	protected String getEndTag(String tagName)
	{
		return HTML_END_TAG + tagName + HTML_START_TAG_CLOSE;
	}
	
	protected void endTag()
	{
		append(HTML_START_TAG_CLOSE);
	}

	protected void endTagLn()
	{
		append(HTML_START_TAG_CLOSE + END_OF_LINE);
	}
	
	protected String getEndTag()
	{
		return (HTML_START_TAG_CLOSE);
	}
	protected void quot(String str)
	{
		append("\"" + str + "\"");
	}
	
	protected String getQuot(String str)
	{
		return "\"" + str + "\"";
	}
	
	protected void singleQuot(String str)
	{
		append(SINGLE_QUOT + str + SINGLE_QUOT);
	}
	
	protected String getSingleQuot(String str)
	{
		return SINGLE_QUOT + str + SINGLE_QUOT;
	}
	
	protected void addAttribute(String name)
	{
		append(BLANK + name + JS_EQUALS);
	}
	
	protected String getAttribute(String name)
	{
		return (BLANK + name + JS_EQUALS);
	}
	
	protected void addAttribute(String name, String value)
	{
		append(BLANK + name + JS_EQUALS + getQuot(value));
	}
	
	protected void addAttribute(String name,int value)
	{
		append(BLANK + name + JS_EQUALS + getQuot(String.valueOf(value)));
	}
	
	protected String getAttribute(String name, String value)
	{
		return BLANK + name + JS_EQUALS + getQuot(value);
	}
	
	protected void addStyleAttribute(String name,String value)
	{
		append(name + ":" + value + JS_END_OF_LINE);
	}
	
	protected String getStyleAttribute(String name,String value)
	{
		return name + ":" + value + JS_END_OF_LINE;
	}
	
	protected void addVariable(String name,String value,boolean isArray)
	{
		append(JS_VARIABLE_DECLARATION + name + " = ");
		if (isArray)
		{
			value = JS_ARRAY_BRACKET_START + value + JS_ARRAY_BRACKET_END;
		}
		append(value);
		append(JS_END_OF_LINE);
	}
	
	protected void addRemark(String remark)
	{
		append(REMARK_START + remark + REMARK_END);
		appendln();
	}

	protected void addValueToArray(String arrayName,String value)
	{
		addValueToArray(arrayName,value,true);
	}

	protected void addValueToArray(String arrayName,String value,boolean isQuoted)
	{
		addValueToArray(arrayName,value,arrayName + JS_DOT + JS_OBJECT_LENGTH,isQuoted);
	}

	protected void addValueToArray(String arrayName,String value,String key)
	{
		addValueToArray(arrayName,value,key,true);
	}
	
	protected void addValueToArray(String arrayName,String value,String key,boolean isQuoted)
	{
		if (isQuoted)
		{
			value = getSingleQuot(value);
		}
		append(arrayName + JS_ARRAY_BRACKET_START + key + JS_ARRAY_BRACKET_END + BLANK + JS_EQUALS + BLANK + value);
	}
	
	/**
	 * Renders java script tags(<script>) for a list of files 
	 * @param filesPropertyName key in a properties file that holds<br>
	 * the list of files,example : a.js,b.js,c.js,d.js
	 */
	protected void renderScriptFiles(String filesPropertyName)
	{
		StringTokenizer st = new StringTokenizer(getUIProperty(filesPropertyName),COMMA);
		String scriptsDir = getSystemProperty(PROPERTY_KEY_SYSTEM_SCRIPTS_DIRECTORY);
		while (st.hasMoreTokens())
		{
			startTag(SCRIPT);
			addAttribute(SRC,scriptsDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + st.nextToken());
			endTag();
			endTag(SCRIPT);
			appendln();
		}		
	}
	
	/**
	 * Renders java links tags(<link>) for a list of files 
	 * @param filesPropertyName key in a properties file that holds<br>
	 * the list of files,example : a.css,b.css,c.css,d.css
	 */
	protected void renderCssFiles(String filesPropertyName)
	{
		renderCssFiles(filesPropertyName,CSS_MEDIA_ALL);
	}
	
	protected void renderCssFiles(String filesPropertyName,String media)
	{
		StringTokenizer st = new StringTokenizer(getUIProperty(filesPropertyName),COMMA);
		String cssDir = getSystemProperty(PROPERTY_KEY_SYSTEM_CSS_DIRECTORY);
		while (st.hasMoreTokens())
		{
			startTag(CSS_LINK);
			addAttribute(HREF,cssDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + st.nextToken());
			addAttribute(CSS_REL,CSS_STYLESHEET);
			addAttribute(TYPE,CSS_PAGE_TYPE);
			addAttribute(CSS_MEDIA,media);
			endTag();
			appendln();	
		}				
	}
	
	protected void renderGlobalDirty() throws UIException
	{
		DirtyModel globalDirtyModel = (DirtyModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),SystemConstants.DIRTY_FLAG_FIELD_NAME);	
		if (globalDirtyModel != null)
		{
			renderHidden(SystemConstants.DIRTY_FLAG_FIELD_NAME,DirtyModel.DIRTY_MODEL_ISDIRTY_PROPERTY + "=" + globalDirtyModel.getDirtyIndication());
		}
	}
	
	protected void renderHidden(String name, String value)
	{
		startTag(INPUT);
		addAttribute(TYPE,HIDDEN);
		addAttribute(NAME,name);
		addAttribute(VALUE, value);
		endTag();
	}
	protected void renderButton(String value, String onclick)
	{
		startTag(INPUT);
		addAttribute(TYPE,BUTTON);
		addAttribute(ONCLICK, onclick);
		addAttribute(VALUE, value);
		endTag();
	}
	
	protected String getPrevPageSign()
	{
		return getLocaleDirection().equals(DIR_RTL) ? WEBDINGS_SYMBOL_ARROW_RIGHT : WEBDINGS_SYMBOL_ARROW_LEFT;
	}

	protected String getNextPageSign()
	{
		return getLocaleDirection().equals(DIR_RTL) ? WEBDINGS_SYMBOL_ARROW_LEFT : WEBDINGS_SYMBOL_ARROW_RIGHT;
	}
	
	protected String getFirstPageSign()
	{
		return getLocaleDirection().equals(DIR_RTL) ? WEBDINGS_SYMBOL_2_ARROW_RIGHT : WEBDINGS_SYMBOL_2_ARROW_LEFT;
	}
	
	protected String getLastPageSign()
	{
		return getLocaleDirection().equals(DIR_RTL) ? WEBDINGS_SYMBOL_2_ARROW_LEFT : WEBDINGS_SYMBOL_2_ARROW_RIGHT;
	}	
	
	protected void renderEmptyCell()
	{
		startTag(CELL,true);
		append(SPACE);
		endTag(CELL);
	}
	
	protected void renderEmptyRow()
	{
		startTag(ROW,true);
		renderEmptyCell();
		endTag(ROW);
	}
	
	protected void renderArray(String arrayName,String[] array)
	{
		StringBuffer sb = new StringBuffer();
		for (int index = 0;index < array.length;index++)
		{
			sb.append(getSingleQuot(getLocalizedText(array[index])));
			if (index != array.length - 1) sb.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(arrayName,sb.toString(),true);
		endTag(SCRIPT);
		appendln();
	}	
	
	protected void renderFunctionCall(String name, String paramNames)
	{
		renderFunctionCall(name, paramNames, true);
	}
	
	protected void renderFunctionCall(String name, String paramNames, boolean isQuoted)
	{
		renderFunctionCall(name,paramNames,isQuoted,"");
	}
	
	protected void renderFunctionCall(String name, String paramNames, boolean isQuoted,String jsObjectName)
	{
		if (!jsObjectName.equals(""))
		{
			append(jsObjectName + JS_DOT);
		}
		append(name + BRACKET_START);
		StringTokenizer st = new StringTokenizer(paramNames,COMMA);
		int counter = 0;
		int tokens = st.countTokens();
		while (st.hasMoreElements())
		{
			if (isQuoted)
				append(getSingleQuot(st.nextToken()));
			else
				append(st.nextToken());
			if (counter != tokens - 1)
			{
				append(COMMA);
			}
			counter++;
		}
		append(BRACKET_END);		
	}

	protected void renderFunctionCall(String name,ArrayList params)
	{
		renderFunctionCall(name,params,true);
	}

	protected void renderFunctionCall(String name,ArrayList params,boolean isQuoted)
	{
		renderFunctionCall(name,params,isQuoted,"");
	}

	protected void renderFunctionCall(String name,ArrayList params,boolean isQuoted,String jsObjectName)
	{
		if (!jsObjectName.equals(""))
		{
			append(jsObjectName + JS_DOT);
		}	
		append(name + BRACKET_START);
		for (int index = 0;index < params.size();index++)
		{
			if (isQuoted)
			{
				append(getSingleQuot((String)params.get(index)));
			}
			else
			{
				append((String)params.get(index));
			}
			if (index != params.size() - 1)
			{
				append(COMMA);		
			}
		}
		append(BRACKET_END);	
	}

	protected String getFunctionCall(String name)
	{
		return getFunctionCall(name,"");
	}
	
	protected String getFunctionCall(String name,String paramNames)
	{
		return getFunctionCall(name,paramNames,true);
	}
	
	protected String getFunctionCall(String name, String paramNames, boolean isQuoted)
	{
		return getFunctionCall(name,paramNames,isQuoted,"");
	}
	
	protected String getFunctionCall(String name, String paramNames, boolean isQuoted,String jsObjectName)
	{
		StringBuffer sb = new StringBuffer();
		StringTokenizer st = new StringTokenizer(paramNames,COMMA);
		if (!jsObjectName.equals(""))
		{
			sb.append(jsObjectName + JS_DOT);
		}
		sb.append(name + BRACKET_START);
		int counter = 0;
		int tokens = st.countTokens();
		while (st.hasMoreElements())
		{
			if (isQuoted)
				sb.append(getSingleQuot(st.nextToken().trim()));
			else
				sb.append(st.nextToken().trim());
			if (counter != tokens - 1)
			{
				sb.append(COMMA);
			}
			counter++;
		}
		sb.append(BRACKET_END);
		return sb.toString();		
	}
	
	protected String getFunctionCall(String name,ArrayList params)
	{
		return getFunctionCall(name,params,true);
	}
	
	protected String getFunctionCall(String name,ArrayList params,boolean isQuoted)
	{
		return getFunctionCall(name,params,isQuoted,"");
	}
	
	protected String getFunctionCall(String name,ArrayList params,boolean isQuoted,String jsObjectName)
	{
		StringBuffer sb = new StringBuffer();
		if (!jsObjectName.equals(""))
		{
			sb.append(jsObjectName + JS_DOT);
		}
		sb.append(name + BRACKET_START);
		for (int index = 0;index < params.size();index++)
		{
			if (isQuoted)
			{
				sb.append(getSingleQuot((String)params.get(index)));		
			}
			else
			{
				sb.append((String)params.get(index));		
			}	
			if (index != params.size() - 1)
			{
				sb.append(COMMA);		
			}
		}
		sb.append(BRACKET_END);
		return sb.toString();
	}
	
	/**
	 * 
	 * @param customEvent
	 * @param windowExtraParams
	 */
	protected void renderJsCallCustomEvent(CustomEvent customEvent) throws UIException 
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(customEvent.getEventName(),customEvent.getEventTargetType(),false,customEvent.isCheckWarnings());

		//ArrayList for adding the parameters for the java script function
		//which handles this event
		ArrayList jsParams = new ArrayList();
		
		//Add the event's parameters : 
		//eventName,eventName,flowPath,flowState,eventTargetType,dialogParams
		jsParams.addAll(getEventCoreParametersList(customEvent.getEventName(),customEvent,authorizedEventData));
		
		//Add the dirty flag's parameters : 
		//eventTargetType,checkDirty,checkWarnings,dirtyModelsIds,allowEventOnDirtyIds,confirmMessage
		jsParams.addAll(getEventDirtyFlagsList(customEvent));
		
		renderFunctionCall(JS_SEND_EVENT_GLOBAL,jsParams);	
	}	
	
	/**
	 * Returns js function name 
	 * @param eventTargetType
	 * @return
	 */
	protected String getEventJsFunctionName(String eventTargetType)
	{
		if (eventTargetType.equals(Event.EVENT_TARGET_TYPE_DIALOG))
		{
			return JS_SEND_EVENT_DIALOG;
		}
		else if (eventTargetType.equals(Event.EVENT_TARGET_TYPE_POPUP))
		{
			return JS_SEND_EVENT_POPUP;
		}
		else
		{
			return JS_SEND_EVENT;
		}	
	}
	
	/**
	 * 
	 * @param windowExtraParams
	 * @param eventTargetType
	 * @return
	 */
	protected String getEventWindowExtraParams(String windowExtraParams,String eventTargetType)
	{
		if (windowExtraParams == null)
		{
			return BLANK;
		}
		if (eventTargetType.equals(Event.EVENT_TARGET_TYPE_POPUP))
		{
			return StringFormatterUtil.replace(windowExtraParams,COMMA,COMMA_HTML);
		}
		else
		{
			return windowExtraParams;
		}
	}
	
	protected String getLogoutFunctionCall(String logoutParamName,String logoutParamValue)
	{
		ArrayList jsParams = new ArrayList();
		jsParams.add(getHttpRequest().getRequestURI());
		jsParams.add(logoutParamName);
		jsParams.add(logoutParamValue);
		return getFunctionCall(JS_FUNCTION_LOGOUT_SYSTEM,jsParams);
	}
	
	protected String getLocalizedImagesDir()
	{
		return DEFAULT_IMAGE_DIR 
				+ System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR)
				+ getLocaleImagesDirectory() 
				+ System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR);
	}
	
	/**
	 * Returns the direction of the user's locale.
	 * @return the direction of the user's locale which may be "rtl" or "ltr"
	 */
	protected String getLocaleDirection()
	{
		if (localeDirection == null)
		{
			localeDirection = LanguagesManager.getLanguageSet(getLocale()).getDirection();
		}
		return localeDirection;
	}
	
	/**
	 * Returns the language of the user's locale's language.
	 * @return the language of the user's locale's language.
	 */
	protected String getLocaleLanguage()
	{
		if (localeLanguage == null)
		{
			localeLanguage = LanguagesManager.getLanguageSet(getLocale()).getLanguage();
		}
		return localeLanguage;
	}
	
	protected String getLocaleImagesDirectory()
	{
		if (imagesLocalizedDirectory == null)
		{
			imagesLocalizedDirectory = LanguagesManager.getLanguageSet(getLocale()).getImagesDirectoryName();
			if (imagesLocalizedDirectory == null)
			{
				imagesLocalizedDirectory = getLocaleDirection();
			}
		}
		return imagesLocalizedDirectory;
	}
	
	protected String getLocaleCssSuffix()
	{
		return getLocaleDirection().equals(DIR_RTL) ? "" : UNDERSCORE + getLocaleDirection();
	}
	
	/**
	 * Returns the suffix of a components's css class by the types of its suffixes.
	 * The types of suffixes allowed(by the order they are concated)
	 * CSS_SUFFIX_DISABLED - for disabled component
	 * CSS_SUFFIX_SELECTED - for selected component
	 * CSS_SUFFIX_LAST - for last component
	 * getLocaleCssSuffix() - for a component that has a different
	 * class when the system's direction is left to right.
	 * @param suffixes
	 * @return
	 */
	protected String getCssClassSuffix(int suffixes)
	{
		String suffix = "";
		if ((CSS_USE_DISABLED & suffixes) > 0)
		{
			suffix += CSS_SUFFIX_DISABLED;
		}
		if ((CSS_USE_SELECTED & suffixes) > 0)
		{
			suffix += CSS_SUFFIX_SELECTED;
		}
		if ((CSS_USE_LAST & suffixes) > 0)
		{
			suffix += CSS_SUFFIX_LAST;
		}
		if ((CSS_USE_DIRECTION & suffixes) > 0)
		{
			suffix += getLocaleCssSuffix();
		}
		return suffix;
	}
	
	protected String getImageSrcSuffix(String imageSrc,int suffixes)
	{
		int dotIndex = imageSrc.indexOf(JS_DOT);
		return 
			imageSrc.substring(0,dotIndex) 
			+ getCssClassSuffix(suffixes)
			+ imageSrc.substring(dotIndex);
	}


	/**
	 * Returns the user's locale
	 * @return (@link java.util.Lacale} object,representing the user's locale
	 */
	protected Locale getLocale()
	{
		return pageContext.getRequest().getLocale();
	}
	
	protected String getSetDirtyFunction()
	{
		return isDirtable() ? getFunctionCall(JS_SET_DIRTY_FUNCTION,dirtyModelId,true) : "";
	}
	
		
	protected String getEventDirtyModelsIdsAsString(Event event)
	{
		String dirtyModelsStr = "";
		List dirtyModelsIds = event.getDirtyModels();
		if (dirtyModelsIds != null)
		{
			for (int index = 0;index < dirtyModelsIds.size();index++)
			{
				dirtyModelsStr += (String)dirtyModelsIds.get(index);
				if (index != dirtyModelsIds.size() - 1)
				{
					dirtyModelsStr += UIConstants.DIRTY_MODEL_PARAM_SEPERATOR;
				}
			}
		}
		return (dirtyModelsStr.equals("") ? "null" : dirtyModelsStr);	
	}
	
	protected ArrayList getEventCoreParametersList(String eventName,Event event,AuthorizedEventData authorizedEventData)
	{
		ArrayList params = new ArrayList();
		params.add(eventName);
		params.add(authorizedEventData.getFlowPath());
		params.add(authorizedEventData.getFlowStateName());
		params.add(event.getEventTargetType());
		params.add(event.getWindowExtraParams());
		return params;
	}
	
	protected ArrayList getEventDirtyFlagsList(Event event)
	{
		return getEventDirtyFlagsList(event,false);
	}
	
	protected ArrayList getEventDirtyFlagsList(Event event,boolean concatDirtable)
	{
		ArrayList params = new ArrayList();
		params.add(String.valueOf(event.isCheckDirty()));
		if (concatDirtable)
		{
			params.add(String.valueOf(dirtable));
		}
		params.add(String.valueOf(event.isCheckWarnings()));
		params.add(getEventDirtyModelsIdsAsString(event));
		params.add(String.valueOf(event.isAllowEventOnDirtyIds()));
		if (event.getConfirmMessageId() != null)
		{
			params.add(getLocalizedText(MESSAGES_KEY_PREFIX + event.getConfirmMessageId() + MESSAGES_KEY_SUFFIX));	
		}
		else
		{
			params.add(event.getConfirmMessageId());
		}
		return params;
	}
	
	/**
	 * Concats event's flags in the following format:<br>
	 * event.isCheckDirty(),dirtable,event.isCheckWarnings(),dirtyModelIds,event.isAllowEventOnDirtyIds
	 * @param event
	 * @return
	 */
	protected String getEventDirtyFlagsAsString(Event event)
	{
		return getEventDirtyFlagsAsString(event,false,COMMA);
	}

	/**
	 * Concats event's flags in the following format:<br>
	 * event.isCheckDirty(),dirtable(if concatDirtable=true),event.isCheckWarnings(),dirtyModelIds,event.isAllowEventOnDirtyIds
	 * @param event
	 * @param concatDirtable
	 * @return
	 */
	protected String getEventDirtyFlagsAsString(Event event,boolean concatDirtable)
	{
		return getEventDirtyFlagsAsString(event,concatDirtable,COMMA);
	}
	
	/**
	 * Concats event's flags in the following format:<br>
	 * event.isCheckDirty() + seperator + dirtable(if concatDirtable=true) + seperator + <br>
	 * event.isCheckWarnings() + seperator + dirtyModelIds + seperator + <br>
	 * event.isAllowEventOnDirtyIds
	 * @param event
	 * @param concatDirtable
	 * @param seperator
	 * @return
	 */
	protected String getEventDirtyFlagsAsString(Event event,boolean concatDirtable,String seperator)
	{
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf(event.isCheckDirty()) + seperator);
		if (concatDirtable)
		{
			sb.append(String.valueOf(dirtable) + seperator);
		}
		sb.append(String.valueOf(event.isCheckWarnings()) + seperator);
		sb.append(getEventDirtyModelsIdsAsString(event) + seperator);
		sb.append(String.valueOf(event.isAllowEventOnDirtyIds()) + seperator);
		if (event.getConfirmMessageId() != null)
		{
			sb.append(getLocalizedText(MESSAGES_KEY_PREFIX + event.getConfirmMessageId() + MESSAGES_KEY_SUFFIX));	
		}
		else
		{
			sb.append(event.getConfirmMessageId());
		}
		return sb.toString();
	}
	
	/**
	 * Renders call for setDirty js function
	 */
	protected void renderSetDirtyProperty()
	{
		if (isDirtable())
		{
			addAttribute(ONPROPERTYCHANGE,getSetDirtyFunction());
		}
	}

	/**
	 * Renders the style of the html wrapper of the component
	 */
	protected void renderComponentWrapperStyle()
	{
		if (state.equals(UIConstants.COMPONENT_HIDDEN_STATE))
		{
			addAttribute(STYLE,DISPLAY_NONE);
		}
	}
		
	/**
	 * Renders webding font	 
	 * @return 
	 */
	protected void renderWebdingFont(String symbol,String className,boolean cursorHand)
	{
		startTag(SPAN);
		addAttribute(CLASS,className);
		addAttribute(STYLE,(cursorHand ? CURSOR_HAND + JS_END_OF_LINE : "") + WEBDINGS);
		endTag();
		append(symbol);
		endTag(SPAN);	
	}
	
	
	
	/**
	 * Adds the java script for adding a shortcut key
	 * @param key 
	 * @param action
	 */
	protected void addShortCutKey(String key,String action) throws UIException
	{
		addShortCutKey(key,action,false);
	}
	
	/**
	 * Adds the java script for adding a shortcut key
	 * @param key
	 * @param action
	 * @param addScriptTag if true renders also a script tag
	 */
	protected void addShortCutKey(String key,String action,boolean addScriptTag) throws UIException
	{
		addShortCutKey(key,action,addScriptTag,false);
	}
	
	/**
	 * Adds the java script for adding a shortcut key
	 * @param key
	 * @param action
	 * @param addScriptTag if true renders also a script tag
	 */
	protected void addShortCutKey(String key,String action,boolean addScriptTag,boolean useActionAsId) throws UIException
	{
		if (!action.equals(""))
		{
			if (useActionAsId)
			{
				action = SHORTCUT_COMPONENT_PREFIX + action;
			}
			if (addScriptTag)
			{
				startTag(SCRIPT,true);
			}
			ArrayList params = new ArrayList();
			params.add(getQuot(getShortCutMapKey(key)));
			params.add(getQuot(action));
			renderFunctionCall(JS_FUNCTION_ADD_SHORTCUT,params,false);
			append(JS_END_OF_LINE);
			if (addScriptTag)
			{
				endTag(SCRIPT);
			}
			appendln();
		}
	}
	
	private String getShortCutMapKey(String key) throws UIException
	{
		String newKey = null;
		int shortCutModifiers = 0;
		StringTokenizer st = new StringTokenizer(key,SHORTCUT_SEPERATOR);
		int tokenNumber = st.countTokens(); 
		if (tokenNumber == 0 || tokenNumber > 4)		
		{
			handleNotValidShortCutKey(key);	
		}	
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			
			//Shift modifier
			if (token.equals(SHORTCUT_SHIFT))
			{
				if ((SHORTCUT_SHIFT_PRESSED & shortCutModifiers) > 0)
				{
					handleNotValidShortCutKey(key);
				}
				shortCutModifiers += SHORTCUT_SHIFT_PRESSED;	
			}
			
			//Ctrl modifier
			else if (token.equals(SHORTCUT_CTRL))
			{
				if ((SHORTCUT_CTRL_PRESSED & shortCutModifiers) > 0)
				{
					handleNotValidShortCutKey(key);
				}
				shortCutModifiers += SHORTCUT_CTRL_PRESSED;	
			}	
			
			//Alt modifier		
			else if (token.equals(SHORTCUT_ALT))
			{
				if ((SHORTCUT_ALT_PRESSED & shortCutModifiers) > 0)
				{
					handleNotValidShortCutKey(key);
				}
				shortCutModifiers += SHORTCUT_ALT_PRESSED;	
			}
			
			//The key itself
			else
			{
				if (token.length() != 1)
				{
					handleNotValidShortCutKey(key);
				}
				newKey = token.toUpperCase();
			}
		}
		if (newKey == null)
		{
			handleNotValidShortCutKey(key);
		}
		if (shortCutModifiers == 0)
		{
			return String.valueOf((int)newKey.charAt(0));
		}
		else
		{
			return String.valueOf((int)newKey.charAt(0)) + SEPERATOR_MINUS + String.valueOf(shortCutModifiers);
		}
	}
	
	private void handleNotValidShortCutKey(String key) throws UIException
	{
		throw new UIException("shortCutKey attribute " + key + " of tag " + getClass().getName() + " or one of its modifiers is not valid.");
	}
	
	/**
	 * Returns the cssPre.
	 * @return String
	 */
	public String getCssPre()
	{
		return cssPre;
	}
	/**
	 * Sets the cssPre.
	 * @param cssPre The cssPre to set
	 */
	public void setCssPre(String cssPre)
	{
		this.cssPre = cssPre;
	}
	/**
	 * @return
	 */
	public String getAuthId() {
		return authId;
	}

	/**
	 * @param string
	 */
	public void setAuthId(String string) {
		authId = string;
	}

	/**
	 * @param b
	 */
	public void setDirtable(boolean dirtable) 
	{
		this.dirtable = dirtable;
	}
	
	public boolean isDirtable()
	{
		return dirtable;
	}

	protected void setRenderTag(boolean renderTag)
	{
		this.renderTag = renderTag;
	}
	
	/**
	 * Returns the TagData object of this tag
	 * @param tagData
	 */
	public void setTagData(TagData tagData)
	{
		this.tagData = tagData;
	}
	
	
	/**
	 * @param string
	 */
	public void setState(String state)
	{
		this.state = state;
	}
	
	public void doCatch(java.lang.Throwable t) throws java.lang.Throwable
	{
		throw t;
	}
	
	public void doFinally()
	{
		resetTagState();
	}

}
