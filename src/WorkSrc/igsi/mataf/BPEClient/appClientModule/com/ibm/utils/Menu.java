package com.ibm.utils;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Søren Kristiansen, IBM Denmark A/S
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class Menu {
	private MenuServices theServices=null;
	private Stack menuStack= new Stack();
	
	public Menu(MenuServices aService) {
		theServices = aService;
	}
	
	public void printMenu(MenuCommand[] theMenu) {
		final String SPACES="  ";
		final int    THEINDENT=SPACES.length();
		// Print the menu header
		String[] theHeader = theServices.getHeader();
		theServices.print("Menu: MAIN", true);
		Iterator iter = menuStack.iterator();
		while (iter.hasNext())
			theServices.print("->"+ iter.next(),false);
		theServices.println();
		if (theHeader !=null){
			for (int i = 0; i < theHeader.length; i++) {
				theServices.println(theHeader[i]);
			}
		}
		
		//Print the menu items
		
		theServices.println();
		for (int i = 0; i < theMenu.length; i++) {
			MenuCommand theCommand = theMenu[i];
			if (theCommand != null) {
				String theCommandText = theCommand.theDescription;
				if (theCommand instanceof MenuSubCommand)
					theCommandText+="...";
				// Check if there is a pre condition
				if (theCommand.thePreconditionMethod != null) {
					Boolean preCondition = (Boolean) this.executeMethod(theCommand.thePreconditionMethod, true);
					if (!preCondition.booleanValue())
						continue;
				}
				// Skip settting user if process already created
				theServices.println(SPACES.substring(0,((i+1<10) ? THEINDENT : THEINDENT-1)) + (i+1) + ") "+ theCommandText);
			}
		}
		theServices.println(SPACES.substring(0, THEINDENT)+0+ ") " + "Return");	
		theServices.println();
	}

	public Object executeMethod(String theMethod, boolean quiet) {
		Object theResult = null;
		if (!quiet)
			theServices.println("---------------- Executing " + theMethod);
		try {
			theResult = theServices.getClass().getDeclaredMethod(theMethod, null).invoke(theServices, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!quiet)
		{
			theServices.prompt("---------------- Finished Executing " + theMethod+" ------ Press 'enter' to continue");

		}
		return theResult;
	}

	public boolean executeCommand(MenuCommand[] theMenu, int theChoice) {
		try{

			MenuCommand theCommand = theMenu[theChoice];
			if (theCommand instanceof MenuSubCommand)
			{ 
				menuStack.push(theCommand.theDescription);
				this.showMenu(  ((MenuSubCommand) theCommand).theCommand );
				menuStack.pop();
				return true;
			}
			
			if (theCommand.theMethodName == null) // If method name not set, quit is assumed
				return false;

			
			boolean doQuiet = theCommand.theMethodName.endsWith("Menu");
			this.executeMethod(theCommand.theMethodName, doQuiet);
			return true;

		} catch (Exception exp)
		{
			// An error occured
			return true;
		}
	}
	
	public void showMenu(MenuCommand[] theMenu)
	{
		String theInput;
		int theChoice;
		do {
			while(true) 
			try {
				this.printMenu(theMenu);
				theInput = theServices.prompt("");
				if (theInput.equals("99")) System.exit(0);
				if (theInput.equals("0")) return;			
				if (theInput.equals("")) return;						
			
				theChoice =  Integer.parseInt(theInput)-1;
				break;
			}
			catch(Exception exp)
			{}
			
		} while (this.executeCommand(theMenu, theChoice));
		
	}
	
}
