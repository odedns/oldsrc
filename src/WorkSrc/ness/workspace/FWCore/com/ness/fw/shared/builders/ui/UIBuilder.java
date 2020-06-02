package com.ness.fw.shared.builders.ui;

import com.ness.fw.flower.core.*;
import com.ness.fw.ui.AbstractModel;
import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.UIException;

public class UIBuilder implements ContextFieldBuilder 
{
	public void initialize(ParameterList parameterList) throws Exception 
	{
		
	}

	public AbstractModel buildModel(ContextFieldBuilderParams builderParams, AbstractModel model, String modelData) throws AuthorizationException, UIException
	{
		if (model != null)
		{
			model.parseEventData(modelData,builderParams.isCheckAuthorization());	
		}
		return model;
	}
}
