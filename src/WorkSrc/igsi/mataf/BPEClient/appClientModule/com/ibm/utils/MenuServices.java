package com.ibm.utils;
/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface MenuServices {
	abstract void println(String theText);
	abstract void print(String theText, boolean useIndentation);	
	abstract void println();
	abstract String[] getHeader();
	
	abstract String prompt(String thePrompt);
	
}
