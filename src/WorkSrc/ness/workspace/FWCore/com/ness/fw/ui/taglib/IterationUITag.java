package com.ness.fw.ui.taglib;

import javax.servlet.jsp.JspException;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.common.logger.Logger;

public abstract class IterationUITag extends UITag 
{
	/**
	 * The return value of the doAfterBody method.
	 * Default value is EVAL_BODY_AGAIN.Iteration tag will probably change this value<br>
	 * to SKIP_BODY in order to stop the iteration.
	 */
	protected int afterBodyReturnValue = EVAL_BODY_AGAIN;
	
	/**
	 * Called before the end of each tag and normally is used for deciding if the<br>
	 * body of the tag should be reevaluated.
	 * This method calls the renderBeforeEndTag method which should decide about<br>
	 * continue the iteration or break by changing the value of afterBodyReturnValue.
	 * Only iteration tag should implement this method
	 */
	public int doAfterBody() throws JspException
	{
		try 
		{
			renderBeforeEndTag();
			return afterBodyReturnValue;
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
		afterBodyReturnValue = EVAL_BODY_AGAIN;
		super.resetTagState();
	}
	
	/**
	 * Called from the doAfterBody method and used for continuing or breaking an<br>
	 * iteration. 
	 */
	protected abstract void renderBeforeEndTag() throws UIException;
}
