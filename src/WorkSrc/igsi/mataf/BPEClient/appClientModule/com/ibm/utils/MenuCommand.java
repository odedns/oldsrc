package com.ibm.utils;

/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MenuCommand {
	public String theDescription = null; 
	public String theMethodName=null;
	public String thePreconditionMethod=null;
	              
	public MenuCommand() {
	}

	              
	public MenuCommand(String aDescription, String aMethodName) {
		theDescription = aDescription;
		theMethodName = aMethodName;
	}
	
	public MenuCommand(String aDescription, String aMethodName, String aPreconditionMethod) {
		theDescription = aDescription;
		theMethodName = aMethodName;
		thePreconditionMethod = aPreconditionMethod;
	}
	

}
