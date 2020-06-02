/*
 * Created on: 15/10/2003
 * Author: yifat har-nof
 * @version $Id: HTTPRequestEventFactory.java,v 1.1 2005/02/21 15:07:15 baruch Exp $
 */
package com.ness.fw.flower.servlet;

import com.ness.fw.flower.core.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * Used to parse HTTP request and generate <code>HTTPRequestEvent</code>
 */
public abstract class HTTPRequestEventFactory
{
	public static HTTPRequestEvent createHttpRequestEventInstance(HttpServletRequest request) throws ParametersMissingException
	{
		//list for missing parameters names
		ArrayList missingParams = new ArrayList();

		//retrieve event name
        String eventName = request.getParameter(Event.EVENT_NAME_FIELD_CMB);
		if (eventName == null) missingParams.add(Event.EVENT_NAME_FIELD_CMB);

		//retrieve event extra data
		

		//retrieve flow state name
		String flowState = request.getParameter(Event.FLOW_STATE_FIELD_CMB);
		if (flowState == null) missingParams.add(Event.FLOW_STATE_FIELD_CMB);

		//creating FlowPath instance
		String flowIdString = request.getParameter(Event.FLOW_ID_FIELD_CMB);
		FlowPath flowPath = null;
		if (flowIdString == null)
		{
			missingParams.add(Event.FLOW_ID_FIELD_CMB);
		}
		else
		{
			try
			{
				flowPath = FlowPath.newInstance();
				flowPath.parseFlowIdString(flowIdString);
				// skip the path of the main flow
				flowPath.getNextLevel();
				
			} catch (MalformedFlowIdException ex)
			{
				String s ="Parameters Missing: ";
				for (int i = 0; i < missingParams.size(); i++)
				{
					s += "[" + (String) missingParams.get(i) + "] ";

				}
				throw new ParametersMissingException(s, ex);
			}
		}

		//if there are missing parameters - throw exception
		if (missingParams.size() > 0)
		{
            String s ="Parameters Missing: ";
			for (int i = 0; i < missingParams.size(); i++)
			{
				s += "[" + (String) missingParams.get(i) + "] ";

			}
			throw new ParametersMissingException(s);
		}
		else
		{
            return new HTTPRequestEvent(request, eventName, flowPath, flowState);
		}
	}
}

