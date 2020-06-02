package com.mataf.dse.appl;

/**
 * This Interface holds global constants that represents different states
 * of the application running configuration.
 * 
 * For explanation on each state refer to the constant description.
 * 
 * @author Nati Dykstein. Creation Date : (30/11/2003 12:10:54).  
 */
public interface DesktopSettings 
{
	/** Indicates wether the desktop will load the RT environment.*/
	public static final boolean ACTIVATE_RUNTIME = true;
	
	/** Indicates a co-existence version with no composer transactions. */
	public static final boolean RT_ONLY_COEXSISTANCE = false;
}
