/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: LegacyConstants.java,v 1.3 2005/03/22 13:23:10 yifat Exp $
 */
package com.ness.fw.legacy;


/**
 * The constants for XML parsing of legacy command definition files.
 */
public class LegacyConstants
{
	protected static final int COMMAND_TYPE_SP = 1; 
	protected static final int COMMAND_TYPE_OS400 = 2; 

	protected static final int ACTIVITY_TYPE_READ_WRITE = 1; 
	protected static final int ACTIVITY_TYPE_READ_ONLY = 2;
	
	public static final String LEGACY_BPO_COMMAND= "FWLegacyBPOCmd";
	 
}
