/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: OS400LegacyCommand.java,v 1.2 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;

import java.util.List;

import com.ness.fw.bl.BasicContainer;
import com.ness.fw.common.exceptions.BusinessLogicException;
import com.ness.fw.persistence.ConnectionProvider;

/**
*
*/
public class OS400LegacyCommand extends LegacyCommand
{
	private String libraryName;
	private String programName;

	/**
	 * 
	 * @param commandName
	 * @param activityType
	 * @param objectGraphClassName
	 * @param structureIdLength
	 * @param libraryName
	 * @param programName
	 * @param argumentStructures
	 * @param callArguments
	 */
	public OS400LegacyCommand(
		String commandName,
		int activityType,
		String objectGraphClassName,
		String libraryName,
		String programName,
		List argumentStructures,
		List callArguments)
	{
		super(commandName, LegacyConstants.COMMAND_TYPE_OS400, activityType, objectGraphClassName, argumentStructures, callArguments);
		this.libraryName = libraryName;
		this.programName = programName;
	}

	protected LegacyObjectGraph execute(BasicContainer bpc, ConnectionProvider connectionProvider) throws LegacyCommandException, BusinessLogicException
	{
		// retrieving method
		LegacyObjectGraph results = null;
		
		return results;
	}	


	/**
	 * @return
	 */
	public String getLibraryName()
	{
		return libraryName;
	}

	/**
	 * @return
	 */
	public String getProgramName()
	{
		return programName;
	}


}