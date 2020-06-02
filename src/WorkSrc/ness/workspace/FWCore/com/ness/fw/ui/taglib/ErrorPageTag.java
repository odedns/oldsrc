package com.ness.fw.ui.taglib;

import java.io.*;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.flower.core.*;
import com.ness.fw.flower.util.ApplicationUtil;
import com.ness.fw.shared.common.SystemConstants;
import com.ness.fw.shared.common.SystemUtil;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.util.DateFormatterUtil;
import com.ness.fw.util.MaskConstants;

public class ErrorPageTag extends UITag
{
	protected String titleClassName;
	protected String messageClassName;
	protected String divClassName;	
	protected String title;
	protected String message;
	protected String link;

	/** A constant used to set the priniting mode type to dispaly nothing.*/
	protected String PRINT_NONE = "NONE";

	/** A constant used to set the priniting mode type to dispaly the first
	 * message in the exception.
	 */
	protected String PRINT_FIRST_MESSAGE = "FIRST_MESSAGE";

	/** A constant used to set the priniting mode type to dispaly the first
	 * stack trace in the exception.
	 */
	protected String PRINT_FIRST_STACKTRACE = "FIRST_STACKTRACE";

	/** A constant used to set the priniting mode type to dispaly the all
	 * messages in the exception.
	 */
	protected String PRINT_ALL_MESSAGES = "ALL_MESSAGES";

	/** A constant used to set the priniting mode type to dispaly the
	 * whole exception content.
	 */
	protected String PRINT_ALL = "ALL";	

	protected String printMode = PRINT_ALL;
	
	private int exceptionsIndex = 0;
	
	public ErrorPageTag()
	{
		ignoreAuth = true;
		initAuth = false;	
	}	
	
	/**
	 * Init the tag
	 */
	protected void init() throws UIException
	{
		// Init css defaults
		initCss();

		// Getting the system print mode
		printMode = getSystemProperty("printMode");

		// no authorization is nedd
		ignoreAuth = true;
	}

	/**
	 * init css defaults
	 */
	protected void initCss()
	{
		titleClassName = initUIProperty(titleClassName,"ui.errorPage.titleClassName");
		messageClassName = initUIProperty(messageClassName,"ui.errorPage.messageClassName");
		divClassName = initUIProperty(divClassName,"ui.errorPage.divClassName");
	}

	/**
	 * Called in the doStartTag method,after the init method.
	 * @throws UIException
	 */
	protected void renderStartTag() throws UIException
	{
		renderErrorPage();
	}


	/**
	 * Called in the begining doEndTag method.
	 * @throws UIException
	 */	
	protected void renderEndTag() throws UIException
	{
	}

	/**
	 * Rendering the tag
	 * @throws UIException
	 */
	private void renderErrorPage() throws UIException
	{
		startTag(TABLE);
		addAttribute(WIDTH,"100%");
		addAttribute(HEIGHT,"100%");
		endTag();
		appendln();

		// Adding title row
		startTag(ROW,true);
		appendln();		
		startTag(CELL);
		addAttribute(CLASS,titleClassName);
		addAttribute(ALIGN,CENTER);
		addAttribute(HEIGHT,"1");
		endTag();
		append(title);
		endTag(CELL);
		appendln();		
		endTag(ROW);

		// Adding message row
		startTag(ROW,true);
		appendln();		
		startTag(CELL);
		addAttribute(CLASS,messageClassName);
		addAttribute(HEIGHT,"1");
		endTag();
		append(message);
		endTag(CELL);
		appendln();		
		endTag(ROW);
		
		// Adding exceptions
		renederExceptions();
		
		// Adding link
		renderLink();
		
		endTag(TABLE);

	}

	/**
	 * Rendering the login link
	 */
	private void renderLink()
	{	
		// Adding link row
		startTag(ROW,true);
		appendln();		
		startTag(CELL);
		addAttribute(HEIGHT,"1");
		addAttribute(ALIGN,CENTER);
		endTag();
		startTag(SPAN);
		addAttribute(CLASS,getUIProperty("ui.errorPage.linkClassName"));
		addAttribute(STYLE,CURSOR_HAND);
		addAttribute(ONCLICK,getLogoutFunctionCall(SystemConstants.REQUEST_PARAM_NAME_LOGIN_ONERROR,SystemConstants.SYSTEM_REQUEST_PARAM_VALUE));
		endTag();
		append(link);
		endTag(SPAN);
		endTag(CELL);
		appendln();		
		endTag(ROW);
	}

	/**
	 * Rendering the exceptions
	 * @throws UIException
	 */
	private void renederExceptions() throws UIException
	{
		// Adding div
		startTag(ROW,true);
		appendln();		
		startTag(CELL);
		endTag();

		startTag(DIV);
		addAttribute(CLASS,divClassName);
		endTag();

		// Getting the occured exceptions 
		List exceptions = getExceptions();
		Throwable firstException  = null;
		if (exceptions != null)
		{ 
			firstException = (Exception)exceptions.get(0);
		}
	
		PrintWriter pw = null;
		StringWriter sw = null;
		
		// Add additinal data like user, time, session id
		append(printAdditionalData());

		// Adding the desired exception data according to printMode
		if (printMode.equals(PRINT_NONE))
		{
			// do nothing			
		}
		else if (printMode.equals(PRINT_FIRST_MESSAGE))
		{
			// Adding first message
			if (firstException != null)
			{
				append(firstException.getMessage());
			}
		}
		else if (printMode.equals(PRINT_FIRST_STACKTRACE))
		{
			// Adding first stackTrace
			if (firstException != null)
			{
				append(SystemUtil.convertThrowable2String(firstException));
			}
		}
		else if (printMode.equals(PRINT_ALL_MESSAGES))
		{
			// Looping through all exception. For each exception get all
			// the messages in it
			for (int i=0; i<exceptions.size(); i++)
			{
				Throwable exception = (Throwable)exceptions.get(i);
				append(printException(exception,pw,sw,false));
			}
		}
		else if (printMode.equals(PRINT_ALL))
		{
			// Looping through all exception. For each exception get all
			// the stack traces in it
			for (int i=0; i<exceptions.size(); i++)
			{
				Throwable exception = (Throwable)exceptions.get(i);
				append(printException(exception,pw,sw,true));
			}
		}
		else
		{
			// setting print mode to the default value
			appendln("print mode property wasn't found !!!. Setting to default (All exceptions & messages)");		
			appendln("<BR>");
			// Looping through all exception. For each exception get all
			// the stack traces in it
			for (int i=0; i<exceptions.size(); i++)
			{
				Throwable exception = (Throwable)exceptions.get(i);
				append(printException(exception,pw,sw,true));
			}		
		}
		
		endTag(DIV);

		endTag(CELL);
		endTag(ROW);
		
	}


	/**
	 * 
	 * @param exception
	 * @param pw
	 * @param sw
	 * @param printStack if true print stack should be printed, otherwise message
	 * @return
	 */
	private String printException(Throwable exception, PrintWriter pw, StringWriter sw, boolean printStack)
	{
		if (pw == null)
		{
			sw = new StringWriter();
			pw = new PrintWriter(sw);
		}

		// printStack should be printed
		if (printStack)
		{
			pw.println("---------- Exception no' " + (exceptionsIndex++) + "------------");
			exception.printStackTrace(pw);
		}
		// message should be printed
		else
		{
			String message = exception.getMessage();
			if (message != null)
			{
				pw.println("---------- message no' " + (exceptionsIndex++) + "------------");
				pw.println(message);
			}
		}

		if (!printStack)
		{
			if (exception instanceof FlowerException && ((FlowerException)exception).getCause() != null && (! (exception instanceof SubflowNotFoundException)))
			{
				return printException(((FlowerException)exception).getCause(), pw, sw, printStack);
			}
			else if (exception instanceof ServletException && ((ServletException)exception).getRootCause() != null)
			{
				return printException(((ServletException)exception).getRootCause(), pw, sw, printStack);
			}
		}

		pw.flush();
		String s = sw.toString();
		char arr[] = s.toCharArray();
		StringBuffer sb = new StringBuffer(arr.length + 20);
		for (int i = 0; i < arr.length; i++)
		{
			char c = arr[i];
			if (c == '\n')
			{
				sb.append("<BR>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}

			sb.append(c);
		}

		return sb.toString();
	}
	
	private String printAdditionalData() throws UIException
	{
		StringBuffer data = new StringBuffer();
		String userName = null;
		userName = FlowerUIUtil.getErrorPageCurrentUserId(getHttpRequest());
		
		String currentTime;
		try
		{
			currentTime = DateFormatterUtil.format(new Date(),getLocalizedText(MaskConstants.TIMESTAMP_MASK));
		}
		catch (Throwable e)
		{
			currentTime = "An internal error was occured while getting the time";
		}

		String sessionId = getHttpSession().getId();

		data.append("----------------------------------------------").append("<br>");
		data.append("Username:").append(userName).append("<br>");
		data.append("Time:").append(currentTime).append("<br>");
		data.append("sessionId:").append(sessionId).append("<br>");
		data.append("----------------------------------------------").append("<br>");

		return data.toString();
	}


	/**
	 * Getting all the thrown exception
	 * @return
	 */
	private List getExceptions()
	{
		return ApplicationUtil.getRequestThrowables(getHttpRequest());
	}
	/**
	 * @return div className
	 */
	public String getDivClassName()
	{
		return divClassName;
	}

	/**
	 * @return message
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @return message className
	 */
	public String getMessageClassName()
	{
		return messageClassName;
	}

	/**
	 * @return title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return title className
	 */
	public String getTitleClassName()
	{
		return titleClassName;
	}

	/**
	 * @param divClassName
	 */
	public void setDivClassName(String divClassName)
	{
		this.divClassName = divClassName;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @param messageClassName
	 */
	public void setMessageClassName(String messageClassName)
	{
		this.messageClassName = messageClassName;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @param titleClassName
	 */
	public void setTitleClassName(String titleClassName)
	{
		this.titleClassName = titleClassName;
	}

	/**
	 * @param link
	 */
	public void setLink(String link)
	{
		this.link = link;
	}

	/**
	 * 
	 * @return link
	 */
	public String getLink()
	{
		return link;
	}

}
