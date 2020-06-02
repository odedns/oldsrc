package com.ness.fw.ui.taglib;

import com.ness.fw.common.exceptions.UIException;
import com.ness.fw.shared.ui.FlowerUIUtil;
import com.ness.fw.ui.UIConstants;

public class FWFormTag extends FormTag
{
	private static final String FORM_NAME = UIConstants.FORM_DEFAULT_ID;

	public FWFormTag()
	{
		ignoreAuth = true;
	}
	
	protected void renderForm() throws UIException
	{
		if (name == null)
		{
			name = FORM_NAME;
		}		
		if (action == null)
		{
			action = getHttpRequest().getRequestURI();
		}		
		startTag(FORM);	
		addAttribute(NAME,name);
		addAttribute(ACTION,action);
		addAttribute(METHOD,method);
		addAttribute(TARGET,target);
		addAttribute(ONSUBMIT,JS_RETURN_FALSE);
		endTag();
		appendln();
		renderHiddenFields();	
		appendln();	
	}
	
	protected void renderHiddenFields() throws UIException
	{
		renderHidden(FlowerUIUtil.REQUEST_PARAM_EVENT_NAME_FIELD_CMB,"");
		appendln();
		renderHidden(FlowerUIUtil.REQUEST_PARAM_FLOW_STATE_FIELD_CMB,"");
		appendln();
		renderHidden(FlowerUIUtil.REQUEST_PARAM_FLOW_ID_FIELD_CMB,"");
		appendln();
		renderHidden(FlowerUIUtil.REQUEST_PARAM_EXTRA_PARAMS_FIELD_CMB,"");
		appendln();
		renderHidden(FlowerUIUtil.REQUEST_PARAM_CHECK_WARNINGS_FIELD_CMB,"");
		appendln();
		renderHidden(FlowerUIUtil.REQUEST_PARAM_IS_POPUP_FIELD_CMB,"");
		appendln();
		renderGlobalDirty();
		appendln();	
	}	
}
