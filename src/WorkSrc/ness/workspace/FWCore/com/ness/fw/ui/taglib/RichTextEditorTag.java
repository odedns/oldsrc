/*
 * Created on: 21/03/2005
 * Author:  user name
 * @version $Id: RichTextEditorTag.java,v 1.1 2005/03/23 17:37:40 shay Exp $
 */
package com.ness.fw.ui.taglib;

import java.util.StringTokenizer;

import com.ness.fw.common.auth.ElementAuthLevel;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.resources.ResourceUtils;

/**
 * @author srancus
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class RichTextEditorTag extends UITag 
{
	private final static String PROPERTY_KEY_EDITOR_IMAGES_DIR = "ui.editor.images.directory";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR = "ui.editor.image.separator";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_PASTE = "ui.editor.image.paste";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_CUT = "ui.editor.image.cut";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_COPY = "ui.editor.image.copy";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_BOLD = "ui.editor.image.bold";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_ITALIC = "ui.editor.image.italic";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_UNDERLINE = "ui.editor.image.under";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_TPAINT = "ui.editor.image.tpaint";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_PAREA = "ui.editor.image.parea";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_ALIGN_LEFT = "ui.editor.image.aleft";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_ALIGN_RIGHT = "ui.editor.image.aright";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_ALIGN_CENTER = "ui.editor.image.acenter";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_NLIST = "ui.editor.image.nlist";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_BLIST = "ui.editor.image.blist";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_LINK = "ui.editor.image.link";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_TABLE = "ui.editor.image.table";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_OUTDENT = "ui.editor.image.outdent";
	private final static String PROPERTY_KEY_EDITOR_IMAGE_INDENT = "ui.editor.image.indent";
	
	private final static String PROPERTY_KEY_EDITOR_SELECT_HEADING = "ui.editor.select.heading";
	private final static String PROPERTY_KEY_EDITOR_SELECT_FONT_TYPE = "ui.editor.select.fontType";
	private final static String PROPERTY_KEY_EDITOR_SELECT_FONT_SIZE = "ui.editor.select.fontSize";
	
	private final static String PROPERTY_KEY_EDITOR_COLOR_WINDOW_APPROVE_BUTTON = "ui.editor.colorWindow.approveButton";
	private final static String PROPERTY_KEY_EDITOR_COLOR_WINDOW_CANCEL_BUTTON = "ui.editor.colorWindow.cancelButton";
	private final static String PROPERTY_KEY_EDITOR_COLOR_WINDOW_SELECT_COLOR = "ui.editor.colorWindow.selectColor";
	
	private final static String PROPERTY_KEY_EDITOR_TABLE_WINDOW_APPROVE_BUTTON = "ui.editor.tableWindow.approveButton";
	private final static String PROPERTY_KEY_EDITOR_TABLE_WINDOW_CANCEL_BUTTON = "ui.editor.tableWindow.cancelButton";	
	private final static String PROPERTY_KEY_EDITOR_TABLE_WINDOW_ROWS = "ui.editor.tableWindow.rows";
	private final static String PROPERTY_KEY_EDITOR_TABLE_WINDOW_COLS = "ui.editor.tableWindow.cols";
	
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_PASTE = "ui.editor.tooltip.paste";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_CUT = "ui.editor.tooltip.cut";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_COPY = "ui.editor.tooltip.copy";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_BOLD = "ui.editor.tooltip.bold";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_ITALIC = "ui.editor.tooltip.italic";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_UNDERLINE = "ui.editor.tooltip.under";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_COLOR = "ui.editor.tooltip.tpaint";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_BGCOLOR = "ui.editor.tooltip.parea";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_LEFT = "ui.editor.tooltip.aleft";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_RIGHT = "ui.editor.tooltip.aright";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_CENTER = "ui.editor.tooltip.acenter";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_ORDERED_LIST = "ui.editor.tooltip.nlist";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_UNORDERED_LIST = "ui.editor.tooltip.blist";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_TABLE = "ui.editor.tooltip.table";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_LINK = "ui.editor.tooltip.link";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_INDENT = "ui.editor.tooltip.indent";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_OUTDENT = "ui.editor.tooltip.outdent";
	private final static String PROPERTY_KEY_EDITOR_TOOLTIP_VIEW_SOURCE = "ui.editor.tooltip.viewSourcCB";
	
	private final static String JS_FUNCTION_EDITOR_FORMAT = "editorFormat";
	private final static String JS_FUNCTION_EDITOR_CREATE_LINK = "createLink";
	private final static String JS_FUNCTION_EDITOR_CREATE_TABLE = "createTable";
	private final static String JS_FUNCTION_EDITOR_SWITCH_MODE = "switchMode";
	private final static String JS_FUNCTION_EDITOR_FORE_COLOR = "foreColor";
	private final static String JS_FUNCTION_EDITOR_BACK_COLOR = "backColor";
	private final static String JS_FUNCTION_EDITOR_SET_MODE = "setMode";
	
	private final static String JS_VAR_EDITOR_TABLE_WINDOW_LABELS_ARRAY_NAME = "EDITOR_TABLE_LABELS";
	private static final String[] EDITOR_TABLE_LABELS_PROPERTY_KEYS =
	{
		"ui.editor.tableWindow.approveButton",
		"ui.editor.tableWindow.cancelButton",
		"ui.editor.tableWindow.rows",
		"ui.editor.tableWindow.cols"
	};
	
	private final static String JS_VAR_EDITOR_COLOR_WINDOW_LABELS_ARRAY_NAME = "EDITOR_COLOR_LABELS";
	private static final String[] EDITOR_COLOR_LABELS_PROPERTY_KEYS =
	{
		"ui.editor.colorWindow.approveButton",
		"ui.editor.colorWindow.cancelButton",
		"ui.editor.colorWindow.selectColor"
	};
	
	private final static String EDITOR_FORMAT_TYPE_BLOCK = "formatBlock";
	private final static String EDITOR_FORMAT_TYPE_FONT_NAME = "fontname";
	private final static String EDITOR_FORMAT_TYPE_FONT_SIZE = "fontsize";
	private final static String EDITOR_FORMAT_TYPE_CUT = "cut";
	private final static String EDITOR_FORMAT_TYPE_COPY = "copy";
	private final static String EDITOR_FORMAT_TYPE_PASTE = "paste";
	private final static String EDITOR_FORMAT_TYPE_BOLD = "bold";
	private final static String EDITOR_FORMAT_TYPE_ITALIC = "italic";
	private final static String EDITOR_FORMAT_TYPE_UNDERLINE = "underline";
	private final static String EDITOR_FORMAT_TYPE_JUSTIFY_LEFT = "justifyleft";
	private final static String EDITOR_FORMAT_TYPE_JUSTIFY_RIGHT = "justifyright";
	private final static String EDITOR_FORMAT_TYPE_JUSTIFY_CENTER = "justifycenter";
	private final static String EDITOR_FORMAT_TYPE_INSERT_ORDERED_LIST = "insertorderedlist";
	private final static String EDITOR_FORMAT_TYPE_INSERT_UNORDERED_LIST = "insertunorderedlist";
	private final static String EDITOR_FORMAT_TYPE_OUTDENT = "outdent";
	private final static String EDITOR_FORMAT_TYPE_INDENT = "indent";
	
	private String editorImagesDirectory; 
	
	protected void init() throws UIException
	{
		ignoreAuth = true;
		initAuth = false;		
		editorImagesDirectory = getUIProperty(PROPERTY_KEY_EDITOR_IMAGES_DIR);
	}
	
	protected void renderStartTag() throws UIException 
	{
		renderEditorVariables();
		renderMainTable();
	}
	
	private void renderEditorVariables()
	{
		renderConstantsArray(JS_VAR_EDITOR_TABLE_WINDOW_LABELS_ARRAY_NAME,EDITOR_TABLE_LABELS_PROPERTY_KEYS);
		renderConstantsArray(JS_VAR_EDITOR_COLOR_WINDOW_LABELS_ARRAY_NAME,EDITOR_COLOR_LABELS_PROPERTY_KEYS);
	}
	
	private void renderConstantsArray(String jsArrayName,String[] arr)
	{
		StringBuffer labels = new StringBuffer(100);
		labels.append(getSingleQuot(getLocaleDirection()) + COMMA);
		for (int index = 0;index < arr.length;index++)
		{
			labels.append(getSingleQuot(getLocalizedText(arr[index],false,true)));
			if (index != arr.length - 1) labels.append(COMMA);
		}
		startTag(SCRIPT,true);
		addVariable(jsArrayName,labels.toString(),true);
		endTag(SCRIPT);
		appendln();		
	}
	
	private void renderMainTable()
	{
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(CELLPADDING,1);
		addAttribute(CELLSPACING,1);
		addAttribute(BORDER,0);
		endTag();
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,1);
		endTag();
		renderTopBar();
		endTag(CELL);
		endTag(ROW);
		
		startTag(ROW,true);
		startTag(CELL);
		addAttribute(HEIGHT,1);
		endTag();
		renderBottomBar();
		endTag(CELL);
		endTag(ROW);
		
		startTag(ROW,true);
		startTag(CELL,true);
		renderEditor();
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);
	}
	
	private void renderTopBar()
	{
		startTag(TABLE);
		addAttribute(CELLPADDING,1);
		addAttribute(CELLSPACING,1);
		endTag();
		startTag(ROW,true);
		startTag(CELL,true);
		renderHeadingSelect();
		endTag(CELL);
		startTag(CELL,true);
		renderFontTypeSelect();
		endTag(CELL);	
		startTag(CELL,true);
		renderFontSizeSelect();
		endTag(CELL);	
		startTag(CELL,true);
		startTag(IMAGE);
		addAttribute(SRC,getEditorImagesDirectory() + getUIProperty(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR));	
		endTag();
		endTag(CELL);
		startTag(CELL,true);
		append(getLocalizedText(PROPERTY_KEY_EDITOR_TOOLTIP_VIEW_SOURCE));
		endTag(CELL);
		startTag(CELL,true);
		startTag(INPUT);
		addAttribute(TYPE,CHECKBOX);
		addAttribute(ONCLICK,getFunctionCall(JS_FUNCTION_EDITOR_SET_MODE));
		addAttribute(TITLE,getLocalizedText(PROPERTY_KEY_EDITOR_TOOLTIP_VIEW_SOURCE));
		endTag();
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);
	}
		
	private void renderHeadingSelect()
	{
		startTag(SELECT);
		addAttribute(ONCHANGE,getFunctionCall(JS_FUNCTION_EDITOR_FORMAT,EDITOR_FORMAT_TYPE_BLOCK));
		endTagLn();
		startTag(OPTION);
		append(SELECTED);
		endTag();
		append("Paragraph");
		endTagLn(OPTION);
		renderEditorSelectOptions(PROPERTY_KEY_EDITOR_SELECT_HEADING);
		endTagLn(SELECT);
	}
	
	private void renderFontTypeSelect()
	{
		startTag(SELECT);
		addAttribute(ONCHANGE,getFunctionCall(JS_FUNCTION_EDITOR_FORMAT,EDITOR_FORMAT_TYPE_FONT_NAME));
		endTagLn();
		startTag(OPTION);
		append(SELECTED);
		endTag();
		append("");
		endTagLn(OPTION);		
		renderEditorSelectOptions(PROPERTY_KEY_EDITOR_SELECT_FONT_TYPE);
		endTagLn(SELECT);		
	}

	private void renderFontSizeSelect()
	{
		startTag(SELECT);
		addAttribute(ONCHANGE,getFunctionCall(JS_FUNCTION_EDITOR_FORMAT,EDITOR_FORMAT_TYPE_FONT_SIZE));
		endTagLn();
		startTag(OPTION);
		append(SELECTED);
		endTag();
		append("");
		endTagLn(OPTION);		
		renderEditorSelectOptions(PROPERTY_KEY_EDITOR_SELECT_FONT_SIZE);
		endTagLn(SELECT);		
	}
	
	private void renderEditorSelectOptions(String propertyKey)
	{
		String selectDef = getUIProperty(propertyKey);
		StringTokenizer st = new StringTokenizer(selectDef,COMMA);
		while (st.hasMoreTokens())
		{
			String token = st.nextToken();
			int index = token.indexOf(SEPERATOR);
			startTag(OPTION);
			addAttribute(VALUE,token.substring(0,index));
			endTag();
			append(token.substring(index + 1));
			endTagLn(OPTION);
		}
	}
	
	private void renderBottomBar()
	{
		startTag(TABLE,true);
		startTag(ROW,true);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_CUT,getFormatFunction(EDITOR_FORMAT_TYPE_CUT),PROPERTY_KEY_EDITOR_TOOLTIP_CUT);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_COPY,getFormatFunction(EDITOR_FORMAT_TYPE_COPY),PROPERTY_KEY_EDITOR_TOOLTIP_COPY);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_PASTE,getFormatFunction(EDITOR_FORMAT_TYPE_PASTE),PROPERTY_KEY_EDITOR_TOOLTIP_PASTE);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_BOLD,getFormatFunction(EDITOR_FORMAT_TYPE_BOLD),PROPERTY_KEY_EDITOR_TOOLTIP_BOLD);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_ITALIC,getFormatFunction(EDITOR_FORMAT_TYPE_ITALIC),PROPERTY_KEY_EDITOR_TOOLTIP_ITALIC);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_UNDERLINE,getFormatFunction(EDITOR_FORMAT_TYPE_UNDERLINE),PROPERTY_KEY_EDITOR_TOOLTIP_UNDERLINE);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_TPAINT,getFunctionCall(JS_FUNCTION_EDITOR_FORE_COLOR),PROPERTY_KEY_EDITOR_TOOLTIP_COLOR);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_PAREA,getFunctionCall(JS_FUNCTION_EDITOR_BACK_COLOR),PROPERTY_KEY_EDITOR_TOOLTIP_BGCOLOR);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_ALIGN_LEFT,getFormatFunction(EDITOR_FORMAT_TYPE_JUSTIFY_LEFT),PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_LEFT);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_ALIGN_CENTER,getFormatFunction(EDITOR_FORMAT_TYPE_JUSTIFY_CENTER),PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_CENTER);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_ALIGN_RIGHT,getFormatFunction(EDITOR_FORMAT_TYPE_JUSTIFY_RIGHT),PROPERTY_KEY_EDITOR_TOOLTIP_ALIGN_RIGHT);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_NLIST,getFormatFunction(EDITOR_FORMAT_TYPE_INSERT_ORDERED_LIST),PROPERTY_KEY_EDITOR_TOOLTIP_ORDERED_LIST);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_BLIST,getFormatFunction(EDITOR_FORMAT_TYPE_INSERT_UNORDERED_LIST),PROPERTY_KEY_EDITOR_TOOLTIP_UNORDERED_LIST);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_OUTDENT,getFormatFunction(EDITOR_FORMAT_TYPE_OUTDENT),PROPERTY_KEY_EDITOR_TOOLTIP_OUTDENT);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_INDENT,getFormatFunction(EDITOR_FORMAT_TYPE_INDENT),PROPERTY_KEY_EDITOR_TOOLTIP_INDENT);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_SEPERATOR,null,null);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_LINK,getFunctionCall(JS_FUNCTION_EDITOR_CREATE_LINK),PROPERTY_KEY_EDITOR_TOOLTIP_LINK);
		renderBottomBarElement(PROPERTY_KEY_EDITOR_IMAGE_TABLE,getFunctionCall(JS_FUNCTION_EDITOR_CREATE_TABLE),PROPERTY_KEY_EDITOR_TOOLTIP_TABLE);
		endTag(ROW);
		endTag(TABLE);
	}
	
	private void renderBottomBarElement(String imageSrcKey,String onclick,String titleKey)
	{
		startTag(CELL,true);
		startTag(IMAGE);
		addAttribute(SRC,getEditorImagesDirectory() + getUIProperty(imageSrcKey));
		if (onclick != null)
		{
			addAttribute(ONCLICK,onclick);
			addAttribute(STYLE,CURSOR_HAND);
		}
		if (titleKey != null)
		{
			addAttribute(TITLE,getLocalizedText(titleKey));
		}
		endTag(CELL);
	}
	
	private String getFormatFunction(String formatType)
	{
		return getFunctionCall(JS_FUNCTION_EDITOR_FORMAT,formatType);
	}
	
	private void renderEditor()
	{
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");		
		endTag();
		startTag(ROW,true);
		startTag(CELL,true);
		startTag(IFRAME);
		addAttribute(ID,id);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		addAttribute(SRC,"");
		endTag();
		endTag(IFRAME);		
		endTag(CELL);
		endTag(ROW);
		endTag(TABLE);
	}
	
	private String getEditorImagesDirectory()
	{
		return getLocalizedImagesDir() + editorImagesDirectory + System.getProperty(PROPERTY_NAME_SYSTEM_FILE_SEPERATOR);
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
	

	protected void renderEndTag() throws UIException 
	{

	}

}
