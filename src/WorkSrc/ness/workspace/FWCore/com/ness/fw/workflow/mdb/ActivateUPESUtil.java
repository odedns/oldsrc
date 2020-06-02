/*
 * Created on 21/12/2004
 *
 * Author: Amit Mendelson
 * @version $Id: ActivateUPESUtil.java,v 1.9 2005/04/06 12:27:26 amit Exp $
 */
package com.ness.fw.workflow.mdb;

import com.ness.fw.bl.proxy.BPOProxy;
import com.ness.fw.common.auth.UserAuthDataFactory;
import com.ness.fw.common.exceptions.GeneralException;
import com.ness.fw.common.logger.Logger;
import com.ness.fw.common.resources.SystemResources;
import com.ness.fw.util.tree.Node;

/**
 * This class takes care of choosing the program to be activated
 * (By reflection, using BPOProxy).
 */
public class ActivateUPESUtil
{

	public static final String 	LOGGER_CONTEXT = "WF MDB";

	/**
	 *
	 * @param programInputData
	 * @param programName Logic name of the program to be activated.
	 * @param actImplCorrelId identifies the message that caused the UPES call.
	 * @param processName
	 * @param processTemplateName
	 * @param waitForDocuments
	 * @throws BPOCommandNotFoundException
	 * @throws BPOCommandException
	 * @throws BusinessLogicException
	 */
	public static void callService(Node programInputData, String programName, 
		String actImplCorrelId, String processName, String processTemplateName,
		String waitForDocuments) throws UpesException 
	{

		try
		{
			//Writing to DB became optional.
			//A process that needs to implement waiting for documents,
			//should pass "true" as value of the waitForDocuments string.
			Logger.debug(LOGGER_CONTEXT,"activate upes started, received programName: "+programName);
			Logger.debug(LOGGER_CONTEXT,"activate upes, received actImplCorrelId: "+actImplCorrelId);
			Logger.debug(LOGGER_CONTEXT,"activate upes, received processName: "+processName);
			Logger.debug(LOGGER_CONTEXT,"activate upes, received processTemplateName: "+processTemplateName);
			Logger.debug(LOGGER_CONTEXT,"activate upes, received waitForDocuments: "+waitForDocuments);
			
			if((waitForDocuments!=null) &(waitForDocuments.equals(UPESConstants.TRUE)))
			{
				Logger.debug(LOGGER_CONTEXT,"activate upes, calling writeProcess");
				WFDBUtil.writeProcess(processName, actImplCorrelId);
				Logger.debug(LOGGER_CONTEXT,"activate upes, completed writeProcess");
			}
			
			String wfUserId =
				SystemResources.getInstance().getProperty(
					UPESConstants.UPES_WF_USER_ID);
			Logger.debug(LOGGER_CONTEXT,"activate upes, wfUserId: "+wfUserId);
			UpesBPC upesContainer = 
				new UpesBPC(programInputData, 
					UserAuthDataFactory.getUserAuthData(wfUserId),
					actImplCorrelId, processName, processTemplateName);
			Logger.debug(LOGGER_CONTEXT,"activate upes, created upesContainer");
			BPOProxy.execute(programName, upesContainer);
			Logger.debug(LOGGER_CONTEXT,"activate upes completed");
		}
		catch (GeneralException e)
		{
			throw new UpesException(UPESConstants.ERROR_ACTIVATING_UPES_PREFIX + programName + "]" , e);
		}
	}

}
