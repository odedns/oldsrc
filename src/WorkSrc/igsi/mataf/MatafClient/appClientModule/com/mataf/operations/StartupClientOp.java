package com.mataf.operations;

/*_
 * Licensed Material - Property of IBM
 * (C) Copyright IBM Corp. 1998, 2000 - All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or disclosure
 * restricted by GSA ADP Schedule Contract with IBM Corp.
 * 
 * DISCLAIMER:
 * The following [enclosed] code is sample code created by IBM
 * Corporation. This sample code is not part of any standard IBM product
 * and is provided to you solely for the purpose of assisting you in the
 * development of your applications. The code is provided 'AS IS',
 * without warranty of any kind. IBM shall not be liable for any damages
 * arising out of your use of the sample code, even if they have been
 * advised of the possibility of such damages.
 */
import mataf.desktop.handlers.CursorHandler;
import mataf.desktop.handlers.ErrorLineHandler;
import mataf.desktop.handlers.IconsSwitcherHandler;
import mataf.desktop.handlers.MenuItemHandler;
import mataf.desktop.handlers.MessagesWindowHandler;
import mataf.desktop.handlers.RTCommands;
import mataf.desktop.handlers.TextHandler;
import mataf.services.proxy.ProxyService;
import mataf.utils.GLogger;

import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;
/**
 * This operation is invoked after initialization to create the
 * client workstation context in the server workstation. 
 * This is the client side.
 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 * 
 * Functionality Added : 
 * The operation registers handlers to specific commands
 * recieved from the RT environment.
 * 
 * @author Nati Dykstein.
 */
public class StartupClientOp extends DSEClientOperation 
									implements RTCommands
{
	/**
	 * Executes the operation.
	 * Initializes the terminal identification, gets the instance of the
	 * client-server service and invokes the server operation using a
	 * synchronous call.
	 * @exception java.io.Exception.
	 */
	public void execute() throws Exception 
	{
		GLogger.debug("In Startup Client op.");
	
		//Service.readObject("Monitor");
		
		CSClientService csClientService=null;
		setValueAt("TID",Settings.getTID());//$NON-NLS-1$
		csClientService	=((CSClientService)getService("CSClient"));//$NON-NLS-1$
		csClientService.sendAndWait(this,40000);
		
		registerRTHandlers();
	}
	
	/**
	 * Method registers the handlers to their specific command types.
	 */
	private void registerRTHandlers()
	{
		try 
		{
			GLogger.debug("Registering Handlers!");
			ProxyService proxy = (ProxyService)getService("proxyService");
			//proxy.addRequestHandler("*",new DefaultHandler());
			proxy.addRequestHandler(ICON_COMMANDS,new IconsSwitcherHandler());
			proxy.addRequestHandler(TEXT_COMMANDS,new TextHandler());
			proxy.addRequestHandler(ERRORLINE_COMMANDS,new ErrorLineHandler());
			proxy.addRequestHandler(MESSAGE_COMMANDS,new MessagesWindowHandler());
			proxy.addRequestHandler(MENUITEMS_COMMANDS,new MenuItemHandler());
			proxy.addRequestHandler(CURSOR_COMMANDS,new CursorHandler());
		} catch (DSEObjectNotFoundException e) {e.printStackTrace();
		}
	}
}
