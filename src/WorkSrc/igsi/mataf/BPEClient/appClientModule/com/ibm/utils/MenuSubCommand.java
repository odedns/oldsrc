package com.ibm.utils;
/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class MenuSubCommand extends MenuCommand {
	public MenuCommand[] theCommand=null;
	
	public MenuSubCommand(String aDescription, MenuCommand[] aCommand) {
		theDescription = aDescription;
		theCommand = aCommand;
	}
}
