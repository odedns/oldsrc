package com.ness.fw.ui.taglib.flower;

import java.util.ArrayList;

import com.ness.fw.flower.core.ContextException;
import com.ness.fw.flower.servlet.FlowerHTMLUtil;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.util.*;
import com.ness.fw.common.exceptions.ResourceException;
import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.LocalizedResources;
import com.ness.fw.ui.MessagesModel;
import com.ness.fw.shared.ui.*;
import com.ness.fw.ui.taglib.AbstractModelTag;

public class FWMessagesTag extends AbstractModelTag
{
	private static final String MESSAGE_SEVERITY_CRITICAL = "critical";
	private static final String MESSAGE_SEVERITY_ERROR = "error";
	private static final String MESSAGE_SEVERITY_INFORMATION = "information";
	private static final String MESSAGE_SEVERITY_WARNING = "warning";
	private static final String MESSAGE_SEVERITY_DEBUG = "debug";
		
	private static final String JS_EXPAND_ERROR_AREA = "expandErrorArea";
	private static final String JS_EXPAND_ERROR_AREA_WITH_IMAGE = "expandErrorAreaWithImage";
	private static final String JS_MESSAGES_AREA_EVENT = "messagesAreaEvent";
	private static final String JS_SHOW_FIELD = "showField";
		
	private static final String HTML_MESSAGE_DIV_NAME = "errorDiv";
	private static final String HTML_MESSAGE_CONTAINER_NAME = "errorContainer";
	
	private String wrapperClassName;
	private String containerClassName;
	private String tableClassName;
	private String rowClassName;
	private String severityCellClassName;
	private String messageCellClassName;
	
	private String buttonClassName;
	private String normalImage;
	private String expandImage;

	private String severityCriticalClassName;
	private String severityErrorClassName;
	private String severityWarningClassName;
	private String severityInfoClassName;
	private String severityDebugClassName;

	private String severityCriticalImage;
	private String severityErrorImage;
	private String severityWarningImage;
	private String severityInfoImage;
	private String severityDebugImage;
	
	private String expandButtonLabel;
	private String normalButtonLabel;
		
	private int normalHeight = 100;
	private int expandHeight = 300;
	private int state = MessagesModel.MESSAGES_CLOSE;
	
	private boolean reverseOrder = false;
	
	private MessagesModel messagesModel;
	
	protected void initModel() throws UIException
	{
		if (messagesModel == null && id != null)
		{
			messagesModel = (MessagesModel)FlowerUIUtil.getObjectFromContext(getHttpRequest(),id);
			if (messagesModel.getExpandHeight() != -1)
			{
				expandHeight = messagesModel.getExpandHeight();		
			}
			if (messagesModel.getNormalHeight() != -1)
			{
				normalHeight = messagesModel.getNormalHeight();		
			}
			state = messagesModel.getMessagesState();
		}
	}
	
	protected void validateAttributes() throws UIException
	{
		super.validateAttributes();
		if (normalImage != null && expandImage == null)
		{
			throw new UIException("expandImage attribute of messages tag is missing");
		}
		if (expandImage != null && normalImage == null)
		{
			throw new UIException("normalImage attribute of messages tag is missing");
		}		
	}
	
	protected void initCss()
	{
		wrapperClassName = initUIProperty(wrapperClassName,"ui.messages.wrapper");
		containerClassName =initUIProperty(containerClassName,"ui.messages.container");
		tableClassName = initUIProperty(tableClassName,"ui.messages.table");
		rowClassName = initUIProperty(rowClassName,"ui.messages.row");
		severityCellClassName = initUIProperty(severityCellClassName,"ui.messages.cell.severity");
		buttonClassName = initUIProperty(buttonClassName,"ui.messages.button");

		severityCriticalClassName = initUIProperty(severityCriticalClassName,"ui.messages.severity.critical");
		severityErrorClassName = initUIProperty(severityErrorClassName,"ui.messages.severity.error");
		severityWarningClassName = initUIProperty(severityWarningClassName,"ui.messages.severity.warning");
		severityInfoClassName = initUIProperty(severityInfoClassName,"ui.messages.severity.info");
		severityDebugClassName = initUIProperty(severityDebugClassName,"ui.messages.severity.debug");

		severityCriticalImage = initUIProperty(severityCriticalImage,"ui.messages.image.severity.critical");
		severityErrorImage = initUIProperty(severityErrorImage,"ui.messages.image.severity.error");
		severityWarningImage = initUIProperty(severityWarningImage,"ui.messages.image.severity.warning");
		severityInfoImage = initUIProperty(severityInfoImage,"ui.messages.image.severity.info");
		severityDebugImage = initUIProperty(severityDebugImage,"ui.messages.image.severity.debug");		
	
		if (expandButtonLabel == null)
		{
			expandButtonLabel = getLocalizedText("ui.messages.expandButton");
		}
		if (normalButtonLabel == null)
		{
			normalButtonLabel = getLocalizedText("ui.messages.normalButton");		
		}
	}
	
	protected void renderStartTag() throws UIException
	{
		try
		{
			initCss();
			MessagesContainer messagesContainer = FlowerUIUtil.cleanMessageContainer(getHttpRequest());
			messagesContainer.sort();
			appendln();
			append("<!--start messages-->");
			appendln();
			if (messagesContainer.getMessagesCount() != 0)
			{
				startTag(DIV);
				addAttribute(STYLE,"padding-top:6px;");
				endTag();
				startTag(TABLE);
				addAttribute(CELLPADDING,"0");
				addAttribute(CELLSPACING,"0");
				addAttribute(WIDTH,"100%");
				addAttribute(CLASS,wrapperClassName);
				addAttribute(ID,HTML_MESSAGE_DIV_NAME);
				addAttribute(STYLE,"height:" + (state == MessagesModel.MESSAGES_CLOSE ? normalHeight : expandHeight));
				endTag();
				appendln();
				startTag(ROW,true);
				appendln();
				startTag(CELL);
				addAttribute(WIDTH,"100%");
				endTag();
				appendln();
				
				startTag(TABLE);
				addAttribute(WIDTH,"100%");
				addAttribute(HEIGHT,"100%");
				addAttribute(CELLPADDING,"0");
				addAttribute(CELLSPACING,"0");
				endTag();
				startTag(ROW,true);
				
				startTag(CELL,true);
				renderMessagesTable(messagesContainer);
				appendln();
				endTag(CELL);
				appendln();
				
				startTag(CELL);
				addAttribute(WIDTH,"1");
				endTag();
				append(SPACE);
				endTag(CELL);
				appendln();
				
				startTag(CELL);
				addAttribute(VALIGN,BOTTOM);
				addAttribute(WIDTH,"1");
				endTag();
				appendln();
				renderButton();
				appendln();
				endTag(CELL);
				appendln();
				
				endTag(ROW);
				endTag(TABLE);
				endTag(CELL);
				endTag(ROW);
				appendln();
				endTag(TABLE);
				endTag(DIV);
				appendln();
			}
			else if (messagesModel != null)
			{
				messagesModel.setMessagesState(MessagesModel.MESSAGES_CLOSE);
			}
			append("<!--end messages-->");
			appendln();
		}
		catch (ContextException ce)
		{		
			throw new UIException(ce.toString());
		}
		catch (ResourceException re)
		{		
			throw new UIException(re.toString());
		}
		
	}
	
	private void renderMessagesTable(MessagesContainer messagesContainer) throws ContextException, ResourceException
	{
		startTag(DIV);
		addAttribute(ID,HTML_MESSAGE_CONTAINER_NAME);
		addAttribute(CLASS,containerClassName);
		addAttribute(STYLE,HEIGHT + ":100%;" + WIDTH + ":100%;");
		endTag();
		appendln();
		startTag(TABLE);
		addAttribute(CELLPADDING,"0");
		addAttribute(CELLSPACING,"0");
		addAttribute(CLASS,tableClassName);
		endTag();
		appendln();
		renderMessages(messagesContainer);
		endTag(TABLE);
		appendln();
		endTag(DIV);
	}
	
	private void renderButton()
	{
		if (normalImage == null)
		{
			startTag(INPUT);
			addAttribute(TYPE,BUTTON);
			addAttribute(CLASS,buttonClassName);
			addAttribute(VALUE,state == MessagesModel.MESSAGES_CLOSE ? expandButtonLabel : normalButtonLabel);
			renderMessagesAreaEvent(true);
			endTag();
		}
		else
		{
			startTag(IMAGE);
			addAttribute(SRC,state == MessagesModel.MESSAGES_CLOSE ? getLocalizedImagesDir() + normalImage : getLocalizedImagesDir() + expandImage);
			renderMessagesAreaEvent(false);
			endTag();
		}
	}
		
	private void renderMessagesAreaEvent(boolean isButton)
	{
		addAttribute(ONCLICK);
		append(QUOT);
		if (isButton)
		{
			append(getFunctionCall(JS_EXPAND_ERROR_AREA,
									HTML_MESSAGE_DIV_NAME + COMMA + 
									THIS + COMMA + 
									String.valueOf(normalHeight) + COMMA + 
									String.valueOf(expandHeight) + COMMA + 
									getSingleQuot(normalButtonLabel) + COMMA + 
									getSingleQuot(expandButtonLabel),false));
		}
		else
		{
			append(getFunctionCall(JS_EXPAND_ERROR_AREA_WITH_IMAGE,
									HTML_MESSAGE_DIV_NAME + COMMA + 
									THIS + COMMA + 
									String.valueOf(normalHeight) + COMMA + 
									String.valueOf(expandHeight) + COMMA + 
									getSingleQuot(normalImage) + COMMA + 
									getSingleQuot(expandImage),false));			
		}
		append(";");
		if (messagesModel != null)
		{
			renderFunctionCall(JS_MESSAGES_AREA_EVENT,getSingleQuot(id) + COMMA + HTML_MESSAGE_DIV_NAME + COMMA + normalHeight + COMMA + expandHeight,false);
		}
		append(QUOT);			
	}
	
	private void renderMessages(MessagesContainer messagesContainer) throws ContextException, ResourceException
	{
		LocalizedResources localizable = ApplicationUtil.getLocalizedResources(FlowerHTMLUtil.getMainFlowCurrentStateContext(getHttpRequest()));
		if (reverseOrder)
		{
			for (int i = messagesContainer.getMessagesCount() - 1;i >= 0;i++)
			{
				Message message = messagesContainer.getMessage(i);
				Logger.info("messages tag","sessionId[" + getHttpSession().getId() + "] " + message.toString());
				startTag(ROW);
				addAttribute(CLASS,rowClassName);
				if (message.getFieldsCount() > 0)
				{
					addAttribute(ONCLICK,getFunctionCall(JS_SHOW_FIELD,message.getFieldName(0),true));	
					addAttribute(ONDBLCLICK,getFunctionCall(JS_SHOW_FIELD,message.getFieldName(0),true));
					addAttribute(STYLE,CURSOR_HAND);
				}
				endTag();
				startTag(CELL);
				addAttribute(WIDTH,"1");
				renderClassBySeverity(severityCellClassName,message.getSeverity());
				endTag();
				renderSeverityImage(message.getSeverity());
				endTag(CELL);
				startTag(CELL);
				addAttribute(WIDTH,"1");
				endTag();
				append(SPACE);
				endTag(CELL);
				startTag(CELL);
				renderClassBySeverity(severityCellClassName,message.getSeverity());
				endTag();
				append(getMessageText(message,localizable));
				endTag(CELL);
				endTag(ROW);
				appendln();				
			}
		}
		else
		{
			for (int i = 0;i < messagesContainer.getMessagesCount();i++)
			{
				Message message = messagesContainer.getMessage(i);
				Logger.info("messages tag","sessionId[" + getHttpSession().getId() + "] " + message.toString());
				startTag(ROW);
				addAttribute(CLASS,rowClassName);
				if (message.getFieldsCount() > 0)
				{
					addAttribute(ONCLICK,getFunctionCall(JS_SHOW_FIELD,messagesContainer.getHTMLFieldName(message.getFieldName(0)),true));	
					addAttribute(ONDBLCLICK,getFunctionCall(JS_SHOW_FIELD,messagesContainer.getHTMLFieldName(message.getFieldName(0)),true));
					addAttribute(STYLE,CURSOR_HAND);
				}
				endTag();
				startTag(CELL);
				addAttribute(WIDTH,"1");
				renderClassBySeverity(severityCellClassName,message.getSeverity());
				endTag();
				renderSeverityImage(message.getSeverity());
				endTag(CELL);
				startTag(CELL);
				addAttribute(WIDTH,"1");
				endTag();
				append(SPACE);
				endTag(CELL);
				startTag(CELL);
				renderClassBySeverity(severityCellClassName,message.getSeverity());
				endTag();
				append(getMessageText(message,localizable));
				endTag(CELL);
				endTag(ROW);
				appendln();
			}		
		}
	}
	
	private void renderClassBySeverity(String severityClass,int severity)
	{
		if (severity == Message.SEVERITY_CRITICAL)
		{
			addAttribute(CLASS,severityCriticalClassName);
		}
		else if (severity == Message.SEVERITY_ERROR)
		{
			addAttribute(CLASS,severityErrorClassName);
		}
		else if (severity == Message.SEVERITY_INFO)
		{
			addAttribute(CLASS,severityInfoClassName);
		}
		else if (severity == Message.SEVERITY_DEBUG)
		{
			addAttribute(CLASS,severityDebugClassName);
		}
		else if (severity == Message.SEVERITY_WARNING)
		{
			addAttribute(CLASS,severityWarningClassName);
		}		
	}
	
	private void renderSeverityImage(int severity)
	{
		if (severity == Message.SEVERITY_CRITICAL)
		{
			renderSeverityImage(severityCriticalImage);
		}
		else if (severity == Message.SEVERITY_ERROR)
		{
			renderSeverityImage(severityErrorImage);
		}
		else if (severity == Message.SEVERITY_INFO)
		{
			renderSeverityImage(severityInfoImage);
		}
		else if (severity == Message.SEVERITY_DEBUG)
		{
			renderSeverityImage(severityInfoImage);
		}
		else if (severity == Message.SEVERITY_WARNING)
		{
			renderSeverityImage(severityWarningImage);
		}		
	}
	
	private void renderSeverityImage(String imageSource)
	{
		startTag(IMAGE);
		if (imageSource != null)
		{
			addAttribute(SRC,getLocalizedImagesDir() + imageSource);			
		}
		endTag();
	}
	
	private String getMessageText(Message message,LocalizedResources localizable) throws ResourceException
	{
		String messageText = localizable.getString(MESSAGES_KEY_PREFIX + message.getMessageId() + MESSAGES_KEY_SUFFIX);
		ArrayList messages = new ArrayList();
		for (int index = 0;index < message.getParametersCount();index++)
		{
			MessageParameter messageParameter = message.getParameter(index);
			if (messageParameter.getType() == MessageParameter.TYPE_LOCALIZED)
			{
				messages.add(localizable.getString(messageParameter.getValue()));	
			}
			else
			{
				messages.add(messageParameter.getValue());
			}		
		}
		return StringFormatterUtil.buildText(messageText,messages);
	}
	
	private String getMessageSeverity(String messageId,LocalizedResources localizable) throws ResourceException
	{
		return localizable.getString("msg." + messageId + ".severity");
	}
	
	protected void resetTagState()
	{
		wrapperClassName = null;
		containerClassName = null;
		tableClassName = null;
		rowClassName = null;
		severityCellClassName = null;
		messageCellClassName = null;
		buttonClassName = null;
		severityCriticalClassName = null;
		severityErrorClassName = null;
		severityWarningClassName = null;
		severityInfoClassName = null;
		severityDebugClassName = null;
		severityCriticalImage = null;
		severityErrorImage = null;
		severityWarningImage = null;
		severityInfoImage = null;
		severityDebugImage = null;
		expandButtonLabel = null;
		normalButtonLabel = null;
		normalHeight = 100;
		expandHeight = 300;
		normalImage = null;
		expandImage = null;
		state = MessagesModel.MESSAGES_CLOSE;
		reverseOrder = false;
		messagesModel = null;	
		super.resetTagState();
	}
	
	protected void renderEndTag() throws UIException
	{
		appendToEnd();
		renderHiddenField();
	}
	
	/**
	 * @return
	 */
	public String getMessageCellClassName()
	{
		return messageCellClassName;
	}

	/**
	 * @return
	 */
	public String getRowClassName()
	{
		return rowClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityCellClassName()
	{
		return severityCellClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityCriticalClassName()
	{
		return severityCriticalClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityDebugClassName()
	{
		return severityDebugClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityErrorClassName()
	{
		return severityErrorClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityInfoClassName()
	{
		return severityInfoClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityWarningClassName()
	{
		return severityWarningClassName;
	}

	/**
	 * @return
	 */
	public String getTableClassName()
	{
		return tableClassName;
	}

	/**
	 * @param string
	 */
	public void setMessageCellClassName(String string)
	{
		messageCellClassName = string;
	}

	/**
	 * @param string
	 */
	public void setRowClassName(String string)
	{
		rowClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityCellClassName(String string)
	{
		severityCellClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityCriticalClassName(String string)
	{
		severityCriticalClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityDebugClassName(String string)
	{
		severityDebugClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityErrorClassName(String string)
	{
		severityErrorClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityInfoClassName(String string)
	{
		severityInfoClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityWarningClassName(String string)
	{
		severityWarningClassName = string;
	}

	/**
	 * @param string
	 */
	public void setTableClassName(String string)
	{
		tableClassName = string;
	}

	/**
	 * @return
	 */
	public int getExpandHeight()
	{
		return expandHeight;
	}

	/**
	 * @return
	 */
	public int getNormalHeight()
	{
		return normalHeight;
	}

	/**
	 * @param i
	 */
	public void setExpandHeight(int i)
	{
		expandHeight = i;
	}

	/**
	 * @param i
	 */
	public void setNormalHeight(int i)
	{
		normalHeight = i;
	}

	/**
	 * @return
	 */
	public String getButtonClassName()
	{
		return buttonClassName;
	}

	/**
	 * @return
	 */
	public String getContainerClassName()
	{
		return containerClassName;
	}

	/**
	 * @return
	 */
	public String getSeverityDebugImage()
	{
		return severityDebugImage;
	}

	/**
	 * @return
	 */
	public String getSeverityErrorImage()
	{
		return severityErrorImage;
	}

	/**
	 * @return
	 */
	public String getSeverityInfoImage()
	{
		return severityInfoImage;
	}

	/**
	 * @return
	 */
	public String getSeverityWarningImage()
	{
		return severityWarningImage;
	}

	/**
	 * @return
	 */
	public String getWrapperClassName()
	{
		return wrapperClassName;
	}

	/**
	 * @param string
	 */
	public void setButtonClassName(String string)
	{
		buttonClassName = string;
	}

	/**
	 * @param string
	 */
	public void setContainerClassName(String string)
	{
		containerClassName = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityDebugImage(String string)
	{
		severityDebugImage = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityErrorImage(String string)
	{
		severityErrorImage = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityInfoImage(String string)
	{
		severityInfoImage = string;
	}

	/**
	 * @param string
	 */
	public void setSeverityWarningImage(String string)
	{
		severityWarningImage = string;
	}

	/**
	 * @param string
	 */
	public void setWrapperClassName(String string)
	{
		wrapperClassName = string;
	}

	/**
	 * @return
	 */
	public String getSeverityCriticalImage()
	{
		return severityCriticalImage;
	}

	/**
	 * @param string
	 */
	public void setSeverityCriticalImage(String string)
	{
		severityCriticalImage = string;
	}

	/**
	 * @return
	 */
	public boolean isReverseOrder()
	{
		return reverseOrder;
	}

	/**
	 * @param b
	 */
	public void setReverseOrder(boolean b)
	{
		reverseOrder = b;
	}

	/**
	 * @return
	 */
	public MessagesModel getMessageModel() {
		return messagesModel;
	}

	/**
	 * @param model
	 */
	public void setMessageModel(MessagesModel model) {
		messagesModel = model;
	}

	/**
	 * @return
	 */
	public String getExpandImage() {
		return expandImage;
	}

	/**
	 * @return
	 */
	public String getNormalImage() {
		return normalImage;
	}

	/**
	 * @param string
	 */
	public void setExpandImage(String string) {
		expandImage = string;
	}

	/**
	 * @param string
	 */
	public void setNormalImage(String string) {
		normalImage = string;
	}

}
