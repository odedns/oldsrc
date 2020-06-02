package com.ness.fw.ui.taglib;

import java.util.StringTokenizer;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.ui.AuthorizedEventData;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.DirtyModel;
import com.ness.fw.ui.events.Event;

public class FWBodyTag extends AbstractBodyTag
{
	/*constants for js functions*/
	private final static String JS_SEND_EVENT_ONLOAD= "sendEventOnLoad";
	private static final String JS_ONLOAD_ACTION = "init()";
	private static final String JS_ONRESIZE_ACTION = "";
	private static final String JS_CONFIRM_MODAL = "confirmModal";
	private static final String JS_ADJUST_CSS = "adjustCss";
	private static final String JS_INIT_NAME = "initName";
	private static final String JS_CHECK_ERROR = "checkError";	
	private static final String JS_CHECK_WARNINGS = "checkWarnings";
	private static final String JS_CHECK_POPUP_WARNINGS_MESSAGES = "checkPopupWarningMessages";
	private static final String JS_HIDE_MENU = "hideMenu";
	private static final String JS_CHECK_SHORTCUT = "checkShortCut";
		
	private static final String L2L_MESSAGES_ARRAY_NAME = "L2L_MESSAGES";
	private static final String CALENDAR_MONTHS_ARRAY_NAME = "CALENDAR_MONTHS";
	private static final String CALENDAR_DAYS_ARRAY_NAME = "CALENDAR_DAYS";
	private static final String SORT_MESSAGES_ARRAY_NAME = "SORT_MESSAGES";
	private static final String POPUP_WARNINGS_LABELS_ARRAY_NAME = "WARNINGS_LABELS";
	private static final String POPUP_COLUMN_ORDER_LABELS_ARRAY_NAME = "COLUMN_ORDER_LABELS";
	private static final String GENERAL_MESSAGES_ARRAY_NAME = "GENERAL_MESSAGES";
	private static final String JS_VAR_HELP_IDS_ARRAY_NAME = "helpIdsArray";
	private static final String JS_VAR_SHORTCUTS_MAP = "shortcuts";
	private static final String JS_VAR_DIRTY_TREE = "dirtyTree";
	private static final String JS_VAR_DIRECTION = "SYSTEM_DIR";
	private static final String JS_VAR_LANGUAGE = "SYSTEM_LANG";
	private static final String JS_VAR_TITLE = "WINDOW_TITLE";

	private static final String CALENDAR_DOWNLOAD_OBJECT_ID = "dwn";
	private static final String CALENDAR_DOWNLOAD_CLASS_NAME = "download";
	
	private static final String[] GENERAL_MESSAGES_ARRAY =
	{
		"ui.general.message.confirmDirty",
		"ui.general.message.confirmWarnings",
		"ui.general.message.errorDirty",
		"ui.general.message.confirmDirtyAreas",
		"ui.general.message.dirtyAreaNotExist1",
		"ui.general.message.dirtyAreaNotExist2",
		"ui.general.message.popupNotLegal",
	};
	
	private static final String[] POPUP_SORT_LABELS_ARRAY =
	{
		"ui.table.sort.up",
		"ui.table.sort.down",
		"ui.table.sort.sortButton",
		"ui.table.sort.cancelButton",
		"ui.table.sort.resetButton",
		"ui.table.sort.message"
	};
	
	private static final String[] POPUP_WARNINGS_LABELS_ARRAY =
	{
		"ui.popup.warning.cancel",
		"ui.popup.warning.approve",
		"ui.popup.warning.message",
		"ui.popup.warning.title"
	};
	
	private static final String[] POPUP_COLUMN_ORDER_LABELS_ARRAY =
	{
		"ui.popup.columnOrder.add",
		"ui.popup.columnOrder.remove",
		"ui.popup.columnOrder.addAll",
		"ui.popup.columnOrder.removeAll",
		"ui.popup.columnOrder.up",
		"ui.popup.columnOrder.down",
		"ui.popup.columnOrder.approve",
		"ui.popup.columnOrder.cancel",
		"ui.popup.columnOrder.displayedColumnsTitle",
		"ui.popup.columnOrder.hiddenColumnsTitle",
		"ui.popup.columnOrder.message.notRemovable.fieldName",
		"ui.popup.columnOrder.message.notRemovable"
	};
	
	private static final String[] L2L_MESSAGES_ARRAY =
	{
		"ui.list.messages1",
		"ui.list.messages2",
		"ui.list.messages3",
		"ui.list.messages4",
		"ui.list.messages5",
		"ui.list.messages6",
		"ui.list.messages7"
	};
	
	protected final static String[] CALENDAR_DAYS_ARRAY = 
	{
		"ui.calendar.days.sun",
		"ui.calendar.days.mon",
		"ui.calendar.days.tue",
		"ui.calendar.days.wed",
		"ui.calendar.days.thu",
		"ui.calendar.days.fri",
		"ui.calendar.days.sat",
		"ui.calendar.title",
		"ui.calendar.today",
		"ui.calendar.noDate",
		"ui.calendar.thisMonth"			
	};
	
	protected final static String[] CALENDAR_MONTHS_ARRAY = 
	{
		"ui.calendar.month.jan",
		"ui.calendar.month.feb",
		"ui.calendar.month.mar",
		"ui.calendar.month.apr",
		"ui.calendar.month.may",
		"ui.calendar.month.jun",
		"ui.calendar.month.jul",
		"ui.calendar.month.aug",
		"ui.calendar.month.sep",
		"ui.calendar.month.oct",
		"ui.calendar.month.nov",
		"ui.calendar.month.dec"
	};
	
	public FWBodyTag()
	{
		ignoreAuth = true;
	}
	
	/**
	 * @see com.ness.fw.ui.taglib.AbstractBodyTag#renderScripts()
	 */
	protected void renderScripts() throws UIException
	{		
		//renderTitleScript();
		renderGlobalScripts();
		renderSystemScripts();
		renderApplicationScripts();
		//renderMenuScripts();
		renderGeneralMessages();		
		renderL2LMessages();
		renderSortMessages();
		renderWarningLabels();
		renderColumnOrderLabels();
		renderCalendarDays();
		renderCalendarMonths();
		renderCalendarDownload();
		renderDirtyTree();
	}	
	
	protected void renderTitleScript()
	{
		startTag(SCRIPT,true);
		//append("document.write(" + getQuot(getStartTag(TITLE) + getEndTag()) + "+" + getFunctionCall("getDialogFixedTitle",title,true) + "+" +  getQuot(getEndTag(TITLE)) + ");");
		append("document.write(" + getQuot(getStartTag(TITLE) + getEndTag()) + "+" + getQuot(title) + "+" +  getQuot(getEndTag(TITLE)) + ");");
		endTag(SCRIPT);
		appendln();		
	}
	
	protected void renderGlobalScripts()
	{
		startTag(SCRIPT,true);
		addVariable(JS_VAR_DIRECTION,getSingleQuot(getLocaleDirection()),false);
		addVariable(JS_VAR_LANGUAGE,getSingleQuot(getLocaleImagesDirectory()),false);
		addVariable(JS_VAR_HELP_IDS_ARRAY_NAME,"new Array()",false);
		addVariable(JS_VAR_SHORTCUTS_MAP,"null",false);
		endTag(SCRIPT);
		appendln();
	}
	
	protected void renderDirtyTree() throws UIException
	{
		String globalDirtyFlagId = SystemConstants.DIRTY_FLAG_FIELD_NAME;
		DirtyModel globalDirtyModel = (DirtyModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),globalDirtyFlagId);
//		if (globalDirtyModel == null)
//		{
//			throw new UIException("Global dirty model with id " + globalDirtyFlagId + " is null");
//		}
		if (globalDirtyModel != null)
		{
			String messageKey = globalDirtyModel.getMessageId() == null ? "ui.general.message.confirmDirty" : globalDirtyModel.getMessageId();
			startTag(SCRIPT,true);
			addVariable(JS_VAR_DIRTY_TREE,getFunctionCall(JS_NEW_TREE,getSingleQuot(globalDirtyFlagId) + COMMA + getFunctionCall(JS_NEW_DIRTY_FLAG,getSingleQuot(globalDirtyFlagId) + COMMA + getSingleQuot(getLocalizedText(messageKey,true,true)),false),false),false);
			endTag(SCRIPT);
			appendln();
		}
	}
	
	/**
	 * Renders the script tags which are needed for normal page in the system. 
	 */
	protected void renderSystemScripts()
	{
		renderScriptFiles("ui.scripts.files");
	}
	
	/**
	 * Renders the script tags which are needed for normal page in the application. 
	 */
	protected void renderApplicationScripts()
	{
		String value = getAppUIProperty("app.ui.scripts.files");
		if(value != null)
		{
			StringTokenizer st = new StringTokenizer(value ,COMMA);
			String scriptsDir = getSystemProperty("ui.directory.scripts");
			while (st.hasMoreTokens())
			{
				startTag(SCRIPT);
				addAttribute(SRC,scriptsDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + st.nextToken());
				endTag();
				endTag(SCRIPT);
				appendln();
			}
		}
	}
	
	
	/**
	 * Renders the script tags which are needed for supporting the menu tag.
	 */
	protected void renderMenuScripts()
	{
		String menuDir = getSystemProperty("ui.directory.menu");
		startTag(SCRIPT);
		addAttribute(SRC,menuDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.scripts.files.menu"));
		endTag();
		endTag(SCRIPT);
		appendln();
		startTag(SCRIPT);
		addAttribute(SRC,menuDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.directory.menu.panel") + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.scripts.files.menu.panel"));
		endTag();
		endTag(SCRIPT);
		appendln();
		startTag(SCRIPT);
		addAttribute(SRC,menuDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.directory.menu.ie") + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.scripts.files.menu.ie"));
		endTag();
		endTag(SCRIPT);
		appendln();
	}
		
	protected void renderL2LMessages() throws UIException
	{
		StringBuffer messages = new StringBuffer(100);
		for (int index = 0;index < L2L_MESSAGES_ARRAY.length;index++)
		{
			messages.append(getSingleQuot(getLocalizedText(L2L_MESSAGES_ARRAY[index],false,true)));
			if (index != L2L_MESSAGES_ARRAY.length - 1) messages.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(L2L_MESSAGES_ARRAY_NAME,messages.toString(),true);
		endTag(SCRIPT);
		appendln();
	}
	
	protected void renderGeneralMessages() throws UIException
	{
		StringBuffer messages = new StringBuffer(100);
		for (int index = 0;index < GENERAL_MESSAGES_ARRAY.length;index++)
		{
			messages.append(getSingleQuot(getLocalizedText(GENERAL_MESSAGES_ARRAY[index],true,true)));
			if (index != GENERAL_MESSAGES_ARRAY.length - 1) messages.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(GENERAL_MESSAGES_ARRAY_NAME,messages.toString(),true);
		endTag(SCRIPT);
		appendln();		
	}
	
	protected void renderSortMessages() throws UIException
	{
		StringBuffer messages = new StringBuffer(100);
		for (int index = 0;index < POPUP_SORT_LABELS_ARRAY.length;index++)
		{
			messages.append(getSingleQuot(getLocalizedText(POPUP_SORT_LABELS_ARRAY[index])));
			if (index != POPUP_SORT_LABELS_ARRAY.length - 1) messages.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(SORT_MESSAGES_ARRAY_NAME,messages.toString(),true);
		endTag(SCRIPT);
		appendln();
	}
	
	protected void renderWarningLabels() throws UIException
	{
		StringBuffer labels = new StringBuffer(100);
		for (int index = 0;index < POPUP_WARNINGS_LABELS_ARRAY.length;index++)
		{
			labels.append(getSingleQuot(getLocalizedText(POPUP_WARNINGS_LABELS_ARRAY[index],false,false)));
			if (index != POPUP_WARNINGS_LABELS_ARRAY.length - 1) labels.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(POPUP_WARNINGS_LABELS_ARRAY_NAME,labels.toString(),true);
		endTag(SCRIPT);
		appendln();			
	}
	
	protected void renderColumnOrderLabels() throws UIException
	{
		renderArray(POPUP_COLUMN_ORDER_LABELS_ARRAY_NAME,POPUP_COLUMN_ORDER_LABELS_ARRAY);
	}
	
	protected void renderCalendarMonths() throws UIException
	{
		StringBuffer months = new StringBuffer(100);
		for (int index = 0;index < CALENDAR_MONTHS_ARRAY.length;index++)
		{
			months.append(getSingleQuot(getLocalizedText(CALENDAR_MONTHS_ARRAY[index])));
			if (index != CALENDAR_MONTHS_ARRAY.length - 1) months.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(CALENDAR_MONTHS_ARRAY_NAME,months.toString(),true);
		endTag(SCRIPT);
		appendln();		
	}
	
	protected void renderCalendarDays() throws UIException
	{
		StringBuffer days = new StringBuffer(100);
		for (int index = 0;index < CALENDAR_DAYS_ARRAY.length;index++)
		{
			days.append(getSingleQuot(getLocalizedText(CALENDAR_DAYS_ARRAY[index])));
			if (index != CALENDAR_DAYS_ARRAY.length - 1) days.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(CALENDAR_DAYS_ARRAY_NAME,days.toString(),true);
		endTag(SCRIPT);
		appendln();		
	}	
	
	protected void renderCalendarDownload()
	{
		startTag(BEHAVIOUR_DOWNLOAD);
		addAttribute(ID,CALENDAR_DOWNLOAD_OBJECT_ID);
		addAttribute(CLASS,CALENDAR_DOWNLOAD_CLASS_NAME);
		append("/>");
		appendln();		
	}
	
	protected void renderCss() throws UIException
	{
		renderSystemCss();
		renderSystemPrintCss();
		renderApplicationCss();
		renderAdjustCssScript();	
	}
	
	protected void renderSystemCss()
	{
		renderCssFiles("ui.css.files");
	}
	
	protected void renderSystemPrintCss()
	{
		renderCssFiles("ui.css.files.print",CSS_MEDIA_PRINT);
	}
	
	protected void renderApplicationCss()
	{
		String value = getAppUIProperty("app.ui.css.files");
		if(value != null)
		{
			StringTokenizer st = new StringTokenizer(value, COMMA);
			String cssDir = getSystemProperty("ui.directory.css");
			while (st.hasMoreTokens())
			{
				startTag(CSS_LINK);
				addAttribute(HREF,cssDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + st.nextToken());
				addAttribute("rel","stylesheet");
				addAttribute(TYPE,"text/css");
				endTag();
				appendln();	
			}		
		}
	}	
	
	protected void renderMenuCss()
	{
		//String menuDir = getUIProperty("ui.directory.menu");
		String menuDir = getSystemProperty("ui.directory.menu");

		startTag(CSS_LINK);
		addAttribute(HREF,menuDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.directory.menu.panel") + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.css.files.menu.panel"));
		addAttribute("rel","stylesheet");
		addAttribute(TYPE,"text/css");
		endTag();
		appendln();
		startTag(CSS_LINK);
		addAttribute(HREF,menuDir + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.directory.menu.ie") + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR) + getUIProperty("ui.css.files.menu.ie"));
		addAttribute("rel","stylesheet");
		addAttribute(TYPE,"text/css");
		endTag();
		appendln();		
	}
	
	protected void renderAdjustCssScript()
	{
		startTag(SCRIPT,true);
		renderFunctionCall
		(
			JS_ADJUST_CSS,
			getUIProperty("ui.resolutions.800X600.suffix"),
			true
		);
		endTag(SCRIPT);
		appendln();
	}
	
	protected void addEvents() throws UIException
	{
		if (eventName == null)
		{
			//check for error page
			onLoad = getFunctionCall(JS_CHECK_ERROR,"",false) + ";" + onLoad; 
			
			//call the init function
			onLoad += JS_ONLOAD_ACTION;
			
			//set name
			if (name != null)
			{
				onLoad += ";" + getFunctionCall(JS_INIT_NAME,getWindowName(),true);
			}
			
			//check for warnings or errors in dialog or popup window
			if (checkPopupWarningMessages())
			{
				onLoad += ";" + getFunctionCall(JS_CHECK_POPUP_WARNINGS_MESSAGES,"");	
			}
			
			//check for warnings in the same window
			if (checkWarnings())
			{
				onLoad += ";" + getFunctionCall(JS_CHECK_WARNINGS,FlowerUIUtil.getLastEventName(getHttpRequest()) + COMMA + FlowerUIUtil.getLastFlowState(getHttpRequest()) + COMMA + FlowerUIUtil.getLastFlowPath(getHttpRequest()),true);
			}
							
			if (showConfirmModalOnLoad())
			{
				onLoad += ";" + getFunctionCall(JS_CONFIRM_MODAL,getConfirmModalParameters(),true);
			}
			onMouseLeave = getFunctionCall(JS_HIDE_MENU,"",false);
			onMouseOver = getFunctionCall(JS_HIDE_MENU,"",false);
		}
		else
		{
			renderClosingEvent();
		}
	}
	
	protected void renderClosingEvent() throws UIException
	{
		AuthorizedEventData authorizedEventData = addAuthorizedEvent(eventName,Event.EVENT_TARGET_TYPE_CLOSE_DIALOG,false,false);
		onLoad = getFunctionCall(JS_SEND_EVENT_ONLOAD,eventName + COMMA + authorizedEventData.getFlowPath() + COMMA + authorizedEventData.getFlowStateName(),true);
	}
	
	protected String getWindowName()
	{
		return name;
	}
	
	private boolean checkWarnings() throws UIException
	{
		return FlowerUIUtil.checkWarnings(getHttpRequest());
	}
	
	private boolean checkPopupWarningMessages() throws UIException
	{
		return FlowerUIUtil.isPopupWindow(getHttpRequest()) && FlowerUIUtil.isValidationException(getHttpRequest());
	}
	
	protected boolean showConfirmModalOnLoad()
	{
		return false;
	}
	
	protected String getConfirmModalParameters()
	{
		return "message1|message2|message3,flowId,flowState,eventName";
	}
}
