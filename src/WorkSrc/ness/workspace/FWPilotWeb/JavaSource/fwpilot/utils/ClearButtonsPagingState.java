package fwpilot.utils;

import com.ness.fw.common.exceptions.AuthorizationException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.flower.core.Context;
import com.ness.fw.flower.core.ContextException;
import com.ness.fw.ui.ButtonModel;

public class ClearButtonsPagingState 
{
	public static void disableButtons(Context ctx) throws ContextException, FatalException, AuthorizationException
	{
		// Getting the buttons models
		ButtonModel firstBtnModel = (ButtonModel)ctx.getField("firstBtnModel");
		ButtonModel prevBtnModel = (ButtonModel)ctx.getField("prevBtnModel");
		ButtonModel nextBtnModel = (ButtonModel)ctx.getField("nextBtnModel");
		ButtonModel lastBtnState = (ButtonModel)ctx.getField("lastBtnModel");

		// Setting the state to disables
		firstBtnModel.setState(ButtonModel.BUTTON_STATE_DISABLED);
		nextBtnModel.setState(ButtonModel.BUTTON_STATE_DISABLED);
		prevBtnModel.setState(ButtonModel.BUTTON_STATE_DISABLED);
		lastBtnState.setState(ButtonModel.BUTTON_STATE_DISABLED);
	}
	
	public static void enableButtons(Context ctx) throws ContextException, FatalException, AuthorizationException
	{
		// Getting the buttons models
		ButtonModel firstBtnModel = (ButtonModel)ctx.getField("firstBtnModel");
		ButtonModel prevBtnModel = (ButtonModel)ctx.getField("prevBtnModel");
		ButtonModel nextBtnModel = (ButtonModel)ctx.getField("nextBtnModel");
		ButtonModel lastBtnState = (ButtonModel)ctx.getField("lastBtnModel");

		// Setting the state to enables
		firstBtnModel.setState(ButtonModel.BUTTON_STATE_ENABLED);
		nextBtnModel.setState(ButtonModel.BUTTON_STATE_ENABLED);
		prevBtnModel.setState(ButtonModel.BUTTON_STATE_ENABLED);
		lastBtnState.setState(ButtonModel.BUTTON_STATE_ENABLED);
	}
}