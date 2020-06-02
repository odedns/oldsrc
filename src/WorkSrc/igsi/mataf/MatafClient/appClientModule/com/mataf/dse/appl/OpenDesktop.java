package com.mataf.dse.appl;

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
import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.MenuElement;
import javax.swing.MenuSelectionManager;

import mataf.desktop.beans.MatafInternalFrame;
import mataf.desktop.beans.MatafMenuBar;
import mataf.desktop.beans.MatafMenuItem;
import mataf.desktop.beans.MenuShortcutManager;
import mataf.desktop.views.MatafDSEPanel;
import mataf.mq.listener.MQListenerOverride;
import mataf.operations.ActivateRTTransactionOp;
import mataf.services.proxy.ProxyService;
import mataf.services.proxy.RTCommands;
import mataf.services.proxy.RequestException;
import mataf.types.MatafErrorLabel;
import mataf.types.MatafOptionPane;
import mataf.utils.FontFactory;
import mataf.utils.GLogger;

import com.ibm.dse.base.ClientOperation;
import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEEventObject;
import com.ibm.dse.base.DSEHandler;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DSEOperation;
import com.ibm.dse.base.Handler;
import com.ibm.dse.base.Service;
import com.ibm.dse.base.Settings;
import com.ibm.dse.clientserver.CSClientService;
import com.ibm.dse.desktop.Desktop;
import com.ibm.dse.desktop.ExitButton;
import com.ibm.dse.desktop.MultipleStateIconLabel;
import com.ibm.dse.desktop.TaskArea;
import com.ibm.dse.desktop.TaskButton;
import com.ibm.dse.desktop.TaskInfo;
import com.ibm.dse.desktop.WorkingArea;
import com.mataf.operations.EndSessionClientOp;

/**
 * An instance of this class is created when the sample application is started.
 * This class does the following:
 * <UL><LI>Initializes the framework unless the Desktop is running as an applet. If this is the case, the Startup applet does the initialization process.</li>
 * <LI>Creates the initial context in the client (workstationCtx).</li>
 * <LI>Starts the desktop.</li>
 * <LI>Updates some desktop fields with values from the context.</li>
 * <LI>Establishes the session with the server.</li>
 * <LI>Invokes the startup operation.</li>
 * <LI>Manages the client server events to re-establish the session if it goes down.</li>
 * <LI>Maintains a semaphore in the desktop showing the status of the client server session.</li>
 * <LI>Closes the client server session and destroys the desktop on request.</li>
 * </UL>
 * <p>The Desktop can be started as an application or as an applet.
 * When the Desktop is running as an applet, the initialization parameters should be provided as parameters
 * of the Startup applet in the HTML page. 
 * When the Desktop is running as an application, the path to the configuration file can be provided as an
 * argument as shown in the following example:<br>
 * <pre>java com.ibm.dse.samples.appl.OpenDesktop -p c:\mydir\myinifile.ini</pre></p>

 * @copyright(c) Copyright IBM Corporation 1998, 2000.
 * 
 * 
 * Added functionality : (@author Nati Dykstein)
 * - "initUI()" - makes specific customization of the
 *   desktop's component.
 * - "activateRuntime()" - Initializes and starts the RT.
 * - "InitializeRootPane()" - creates application's general
 * 	 keyboard bindings.
 * - "getContext()" - returns the workstation's context.
 * - "getActiveContext()" - Returns the active business screen's context.
 *
 * 
 */	
public class OpenDesktop extends DSEHandler implements java.awt.event.ItemListener 
{
	public static final boolean 	RT_LOAD_COMPLETED 			= true;
	public static final boolean 	RT_LOAD_FAILED 				= false;
	
	public static final boolean	SHOW_CLIENT_CONTEXT_MONITOR = true;
	
	private static java.util.ResourceBundle resResources = java.util.ResourceBundle.getBundle("sampleappl");  //$NON-NLS-1$
	
	/** Indicates whether the desktop is running as an application or as an applet **/
	static boolean isApplication = true;
	
	/** Workstation context **/
	static private Context ctxt;

	/** Default path to the configuration file **/
	static private String iniPath = "http://127.0.0.1:80/MatafServer/dse/dse.ini";//$NON-NLS-1$
	
	/** Default path to the server. */
	static private String serverPath = "http://localhost/MatafServer";

	/** The running instance of this class **/
	static private OpenDesktop aOpenDesktop;
		
	private javax.swing.JComboBox comboBox;

	private java.util.Locale currentLocale=java.util.Locale.getDefault();
	
	private String currentLanguage=currentLocale.getLanguage();

	/** Used to keep track of the start-up load time.*/
	private static int		progressNumber;
	private static int		progressMaxValue = 190;
	private static boolean	finishedLoadingRT;

	/**
	 * This constructor creates an OpenDesktop object.
	 */
	public OpenDesktop() {
		super();
	}
	/**
	 * Changes the Locale of the Desktop application. The language passed
	 * as a parameter must match with one of the different languages set here.
	 * @param String language
	 */
	public void changeLocale(String language){
		java.util.Locale locale=null;
		if (language.equals("Italian")) locale=java.util.Locale.ITALIAN;
		if (language.equals("English")) locale=java.util.Locale.ENGLISH;
		if (language.equals("Spanish")) locale=new java.util.Locale("es","");
		if (locale!=null){
			Desktop.getDesktop().setDesktopLocale(locale);
			Desktop.getDesktop().regenerate();
			currentLanguage=language;
			currentLocale=locale;
		 }
	}
	/**
	 * Builds the desktop and initializes some fields with values from the context. This method
	 * initializes the semaphore that indicates the status of the session with the server.
	 * @exception com.ibm.dse.base.DSEObjectNotFoundException. 
	 */
	public void createAndInitializeDesktop() throws DSEObjectNotFoundException {	
		Desktop.main(null);
		
		// Hide frame while constructing desktop.
		Desktop.getFrame().setVisible(false);
		Desktop.getFrame().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
	//	((TextField)(((Desktop) Desktop.getDesktop()).getComponentByName("USERNAME"))).setText((Object) ctxt.getValueAt("UserId"));//$NON-NLS-2$//$NON-NLS-1$
	//	((TextField)(((Desktop) Desktop.getDesktop()).getComponentByName("BRANCHID"))).setText((Object) ctxt.getValueAt("BranchId"));//$NON-NLS-2$//$NON-NLS-1$
		
		initUI();
	}
	/**
	 * Handles events of type CSStatusChangedEvent. Once it handles the event, this method
	 * returns null, which means that the event is consumed and is not propagated to
	 * the next handler in the handlers queue. This method does not handle any other event type and these events are
	 * are propagated to the next handler.
	 * @return Handler 
	 * @param anEvent com.ibm.dse.base.DSEEventObject
	 */
	public Handler dispatchEvent(DSEEventObject anEvent) {
		
		if (anEvent.getName().equals("CSStatusChangedEvent")) {//$NON-NLS-1$
			manageClientServerEvents(anEvent);			// Manages CSStatusChangedEvent events.
			return null;
		}
		return this;
	}
	/**
	 * Establishes session with the server. This method also
	 * registers interest in the event <I>CSStatusChangedEvent</I> fired by the service whose
	 * alias is <I>CSClient</I> so that the Desktop is notified of any change in the status in the
	 * client/server session. Status change events are received in the <I>dispatchEvent(DSEEventObject)</I> method.
	 * @exception java.lang.Exception. Thrown if an error occurs when registering interest in events or establishing session
	 */
	public void establishServerSession() throws Exception {
		handleEvent("CSStatusChangedEvent","CSClient", ctxt);				// Register interest in the status changed events.//$NON-NLS-2$//$NON-NLS-1$
		Desktop.getDesktop().showMessage(resResources.getString("____Establishing_session_w"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
		((CSClientService) ctxt.getService("CSClient")).setKeepAliveConnection(false);//$NON-NLS-1$
		((CSClientService) ctxt.getService("CSClient")).establishSession();	// Establish session with the server.//$NON-NLS-1$
	}
	
	/**
	 * Executes the end session operation, closes the server session, deregisters interest in client server
	 * events, and disposes the desktop. The Desktop calls this method when the user clicks the exit button in the application desktop.
	 * <p>This method name is defined in the desktop.xml file as a value in the finalizerMethod attribute
	 * of the Desktop tag. The class name (OpenDesktop in this case) is also defined in the desktop.xml
	 * file as a value in the finalizerClass attribute of the Desktop tag.</p>
	 */
	public static void exit() {
		try {
//			((Thread)((com.ibm.dse.desktop.DateField)Desktop.getDesktop().getComponentByName("DateField")).dateField).interrupt();//$NON-NLS-1$
//			((Thread)((com.ibm.dse.desktop.Timer)Desktop.getDesktop().getComponentByName("Timer")).timer).interrupt();//$NON-NLS-1$
		
			EndSessionClientOp oper=null;
			oper = (EndSessionClientOp) DSEOperation.readObject("endSessionClientOp");//$NON-NLS-1$
			oper.execute();													// Executes the end session operation.  
			oper.close();
			((CSClientService)ctxt.getService("CSClient")).closeSession();	// Closes the session with the server.//$NON-NLS-1$
			aOpenDesktop.stopHandlingEvent("CSStatusChangedEvent","CSClient", ctxt);//$NON-NLS-2$//$NON-NLS-1$
		} catch (Exception e) {
			System.out.println(e.toString());
			// Ignore exceptions.
		}
		if ((Desktop.getFrame()) != null) {
			Desktop.getFrame().dispose();						// Disposes the desktop.
		} 
		if (isApplication) System.exit(0);
	}

	/**
	 * Initializes the framework if the application desktop has been started as an application. If the application desktop 
	 * is started as an applet, the Startup applet does the framework initialization. This method also
	 * creates the initial context (workstationCtx), builds and initializes the application desktop, 
	 * and tries to establish the session with the server. 
	 * 
	 */
	public void init() 
	{
		try 
		{
			if (isApplication)
			{
				initializeFramework();	// Initializes the framework in the client.
			}		
					
			ctxt = (Context) Context.readObject("workstationCtx");//$NON-NLS-1$
			createAndInitializeDesktop();	// Builds and initializes the application desktop.
				
			comboBox = ((javax.swing.JComboBox)Desktop.getDesktop().getComponentByName("LanguageBox"));//$NON-NLS-1$
			if (comboBox!=null) comboBox.addItemListener(this); 	// Listens the language combo box events.
			
			establishServerSession();	// Tries to establish the session with the server.
			
			if(SHOW_CLIENT_CONTEXT_MONITOR)
				Service.readObject("Monitor");
			
			// Puts a dummy component to prevent NullPointerException when closing a task.
			Desktop.getDesktop().getNamedComponents().put("ExitButton",new ExitButton());
		} 
		catch (Exception e) 
		{
			System.out.println("Failed to init desktop : "+e); // Just in case the desktop did not initialize correctly.
			String message = e.getMessage();
			if(message==null)
				message="��� ��� ����";			
			MatafOptionPane.showMessageDialog(Desktop.getDesktop(),message,e.getClass().getName(),JOptionPane.ERROR_MESSAGE);
			if(Desktop.getDesktop() != null)
				Desktop.getDesktop().showMessage(resResources.getString("Error__")+e.toString(),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			System.exit(1);
		}
	}

	/**
	 * Initializes the framework. This method is called by the init method.
	 * @exception java.lang.Exception Thrown if there is an error in processing the XML definition files.
	 */
	public void initializeFramework() throws Exception {
		Context.reset();
		Settings.reset(iniPath);
		Settings.initializeExternalizers(Settings.MEMORY);
		
		
	}
	/**
	 * Changes the locale. This method is invoked when an item has been selected or deselected
	 * in the language combo box.
	 * 
	 * @param e java.awt.event.ItemEvent
	 */
	public void itemStateChanged(java.awt.event.ItemEvent e) {
		String language=(String)(e.getItem());
		if ((e.getSource() == comboBox) &&(language!=currentLanguage))
			changeLocale(language);
	}
	
	/**
	 * Manages client server events. These events include the <I>sessionStatus</I> parameter which contains the event code.
	 * Each event code determines the execution of some actions:<ul>
	 * <li><b>Session DOWN</b>:
	 * This event code is received when an existing session goes down or when there is an unsuccessful reconnection attempt.
	 * This causes the Desktop to display a message in the message are and sets the session status semaphore to off. 
	 * <p>The reconnectionAttempts tag specifies how many attempts are made to reconnect to the session. The time that passes
	 * between attempts is set by the timeBetweenReconnectionAttempts tag. Both of these tags are located in
	 * the definition of the client server service in the client. If the connection cannot be established, an event with the
	 * <I>RESET</I> event code is received.</p></li>
	 * <li><b>Session OK</b>:
	 * <p>This event code is received when the session is established or re-establised. This causes the Desktop to display
	 * a message in the desktop message area, sets the session status semaphore to on, and executes the startup operation.</p></li>
	 * 
	 * <li><b>Session NOT_ESTABLISHED</b>:
	 * <p>This event code is received when the client server establishSession() method fails. This causes a pause (30 seconds in this case)
	 * before another attempt is made to establish the session (establishSession()) again. If this fails, the Desktop displays
	 * a message in the desktop message area and the same event code is received. This process is repeated until the session is
	 * successfully established.</p></li>
	 * <li><b>Session RESET</b>:
	 * <p>This event code is received after the last unsuccessfull reconnection attempt is made under the DOWN event code. One
	 * last attempt is made to establish the session (establishSession()) and if this fails, the Desktop displays a message in
	 * the message area and the event code is changed to NOT_ESTABLISHED.</p></li></ul>
	 * @param anEvent com.ibm.dse.base.DSEEventObject
	 */
	public void manageClientServerEvents(DSEEventObject anEvent) {
		if((((Integer)(anEvent.getParameters().get("sessionStatus"))).intValue()) == CSClientService.DOWN) {//$NON-NLS-1$
			Desktop.getDesktop().showMessage(resResources.getString("____Session_with_server_is"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			((MultipleStateIconLabel) ((Desktop) Desktop.getDesktop()).getComponentByName("SEMAPHORE")).setState("OFF");//$NON-NLS-2$//$NON-NLS-1$
		} else if((((Integer)(anEvent.getParameters().get("sessionStatus"))).intValue()) == CSClientService.OK) {//$NON-NLS-1$
			try {
				runStartupOperation();
				Desktop.getDesktop().showMessage(resResources.getString("____Startup_process_comple"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
				((MultipleStateIconLabel) ((Desktop) Desktop.getDesktop()).getComponentByName("SEMAPHORE")).setState("ON");//$NON-NLS-2$//$NON-NLS-1$
			} catch (Exception e){
				Desktop.getDesktop().showMessage(resResources.getString("____Startup_process_failed"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}
		} else if((((Integer)(anEvent.getParameters().get("sessionStatus"))).intValue()) == CSClientService.NOT_ESTABLISHED) {//$NON-NLS-1$
			try {
				Thread.sleep(30000);	// wait for 30 seconds.
				((CSClientService) ctxt.getService("CSClient")).establishSession();//$NON-NLS-1$
			} catch (Exception e){
				Desktop.getDesktop().showMessage(resResources.getString("____Startup_process_failed"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}
		} else if((((Integer)(anEvent.getParameters().get("sessionStatus"))).intValue()) == CSClientService.RESET) {//$NON-NLS-1$
			try {
				((CSClientService) ctxt.getService("CSClient")).establishSession();//$NON-NLS-1$
			} catch (Exception e){
				Desktop.getDesktop().showMessage(resResources.getString("____Startup_process_failed"),JOptionPane.INFORMATION_MESSAGE); //$NON-NLS-1$
			}
		}
	}
	/**
	 * Rebuilds the Desktop. The Desktop calls this method when the user clicks the rebuild button in the application desktop.
	 * <p>This method name is defined in the desktop.xml file as a value in the rebuilderMethod attribute
	 * of the Desktop tag. The class name (OpenDesktop in this case) is also defined in the desktop.xml
	 * file as a value in the rebuilderClass attribute of the Desktop tag.</p>
	 * 
	 */
	public static void rebuild() {
		try{ 
				//Context	ctxt = (Context) Context.readObject("workstationCtx");//$NON-NLS-1$
				//((TextField)(((Desktop) Desktop.getDesktop()).getComponentByName("USERNAME"))).setText((Object) ctxt.getValueAt("UserId"));//$NON-NLS-2$//$NON-NLS-1$
				//((TextField)(((Desktop) Desktop.getDesktop()).getComponentByName("BRANCHID"))).setText((Object) ctxt.getValueAt("BranchId"));//$NON-NLS-2$//$NON-NLS-1$
				((MultipleStateIconLabel) ((Desktop) Desktop.getDesktop()).getComponentByName("SEMAPHORE")).setState("ON");//$NON-NLS-2$//$NON-NLS-1$
		
		}catch (Exception e) {
		System.out.println(e);	
		}
	}
	/**
	 * Instantiates and executes the startup operation.
	 * The startup operation is the first operation to be executed when the session with the server is established.
	 * @exception java.lang.Exception. Error executing the startup operation.
	 */
	public void runStartupOperation() throws Exception {
		GLogger.debug("Operation StartupClientOp");
		ClientOperation op = (ClientOperation) DSEClientOperation.readObject("startupClientOp");//$NON-NLS-1$
		op.execute();			// Executes the startup operation.
		op.close();				// Closes the startup operation.  
	}
	
	
	/**
	 * Asynchronically sends a request to the RT.
	 */
	public static void asynchronicSend(final String commandType,final String key,final String value)
	{
	   	new Thread()
	   	{
	   		public void run()
	   		{
		   		try
			   	{
			   		ProxyService proxy = 
			   			(ProxyService)OpenDesktop.getContext().getService("proxyService");
					proxy.sendRequest(
								 Integer.parseInt(commandType),
								key,
								value);
				}
			   	catch(Exception e2){e2.printStackTrace();}
	   		}
	   	}.start();
	}
	
	/**
	 * Starts the application desktop as an applet.
	 */
	public void start() 
	{
		aOpenDesktop = this;
		isApplication = false;
		init();	
	}
	
	/**
	 * Returns whether there is a business screen open.
	 */
	public static boolean isBusinessScreenOpen()
	{
		return Desktop.getDesktop().getTaskArea().getCurrentTask()!=null;
	}
	
	/**
	 * Return a reference to the workstation context.
	 */
	public static Context getContext()
	{
		return ctxt;
	}	
	

	/**
	 * Returns a desktop component by its name as a JComponent.
	 */
	public static JComponent getJComponentFromDesktop(String componentName)
	{
		Desktop deskTop = Desktop.getDesktop();
		return (JComponent)deskTop.getComponentByName(componentName);
	}
	
	/**
	 * Adds specific desktop customization.
	 */
	private void initUI()
	{
		GLogger.debug("init the desktop UI");

		// Set the semaphore default state.
		((MultipleStateIconLabel)getJComponentFromDesktop("SEMAPHORE")).setState("OFF");

		setupTaskbarPanels();
	
		GLogger.debug("After init the desktop UI");
	}
	
	/**
	 * Make specific customization of the task bar panels.
	 */
	private void setupTaskbarPanels()
	{
		// Create text font and color.
		Font f = FontFactory.createFont("Tahoma",Font.BOLD,12);
		Color c = new Color(0,0,132);
		
		// Area 1.
		getJComponentFromDesktop("area1").setBorder(null);
		getJComponentFromDesktop("time").setFont(f);
		getJComponentFromDesktop("time").setForeground(c);
		getJComponentFromDesktop("date").setFont(f);
		getJComponentFromDesktop("date").setForeground(c);
		
		// Area 2.
		getJComponentFromDesktop("area2").setBorder(BorderFactory.createEtchedBorder());
		
		// Area 3.
		getJComponentFromDesktop("area3").setBorder(BorderFactory.createEtchedBorder());
		getJComponentFromDesktop("account").setFont(f);
		getJComponentFromDesktop("account").setForeground(c);
		
		// Area 4.
		getJComponentFromDesktop("area4").setBorder(BorderFactory.createEtchedBorder());
		getJComponentFromDesktop("counter").setFont(f);
		getJComponentFromDesktop("counter").setForeground(Color.blue);
		
		// Area 5.
		getJComponentFromDesktop("area5").setBorder(null);
		
		// Area 6.
		getJComponentFromDesktop("area6").setBorder(BorderFactory.createEtchedBorder());
		getJComponentFromDesktop("userID").setFont(f);
		getJComponentFromDesktop("userID").setForeground(c);
		
		// Area 7.
		getJComponentFromDesktop("area7").setBorder(null);
		
		// Area 8.
		getJComponentFromDesktop("area8").setBorder(BorderFactory.createEtchedBorder());
		getJComponentFromDesktop("PU").setFont(f);
		getJComponentFromDesktop("PU").setForeground(c);
		getJComponentFromDesktop("stationNumber").setFont(f);
		getJComponentFromDesktop("stationNumber").setForeground(c);
		getJComponentFromDesktop("snifNumber").setFont(f);
		getJComponentFromDesktop("snifNumber").setForeground(c);
		
		// Menu is disabled at start-up.
		MatafMenuBar.setMenuEnabled(false);
	}
	
	
	public static void updateProgress(boolean state)
	{
		if(state==RT_LOAD_COMPLETED)
			progressNumber=progressMaxValue;
		updateProgress();
	}
	
	/**
	 * Update progress while loading RT-environment.
	 */
	public static void updateProgress()
	{
		if(finishedLoadingRT)
			return;
		
		int precentage = (int)(((float)++progressNumber/progressMaxValue)*100);
		String message = "���� ... " + (precentage>0 ? precentage + "%" : "");
		
		if(precentage<100)		
			getMainMatafPanel().getTheErrorLabel().setText(message);
		else
		{
			MatafErrorLabel errorLabel = getMainMatafPanel().getTheErrorLabel();
			errorLabel.queueErrorMessage(message,Color.black);
			errorLabel.queueErrorMessage("������ ������ ������",Color.black);
			errorLabel.queueErrorMessage(resResources.getString("desktop_version"),Color.BLUE);
			finishedLoadingRT = true;
		}
	}
	
	/**
	 * Activate the RT process and start the progess bar
	 * on a different thread.
	 */
	private static void activateRuntime()
	{
		// Activate RT on a seperate thread.
		new Thread()
		{
			public void run()
			{
				try
				{
					// Get RT Properties from the settings.properties file.
					String RTExecuteCommand = 
						(String)com.ibm.dse.gui.Settings.getValueAt("RTExecuteCommand");
					String RTExecutePath = 
						(String)com.ibm.dse.gui.Settings.getValueAt("RTExecutePath");

					// Execute the RT as a seperate process.
					Process p = Runtime.getRuntime().exec(RTExecuteCommand, 
															null, 
															new File(RTExecutePath));
					BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
					String buffer;
					// Output the process's stream to the console.
					while((buffer = in.readLine())!=null)
						GLogger.debug(buffer);
				}
				catch(IOException e)
				{
					e.printStackTrace();
					GLogger.debug(e);
					MatafOptionPane.showMessageDialog(Desktop.getFrame(),
														"����� ����� � runtime �����.",
														"����� ������",
														JOptionPane.ERROR_MESSAGE);
					MatafMenuBar.setMenuEnabled(true);
				}
			}
		}.start();
	}
	
	/**
	 * Method is used to install specific UI changes in the desktop's 
	 * components.
	 * Method is invoked after the installation of the Desktop's L&F.
	 */
	private void setDesktopComponentsSpecialUI()
	{
		// No need at the moment.
	}
	
	/**
	 * A convenient method for referencing the active matafPanel.
	 */
	public static MatafDSEPanel getActiveMatafPanel()
	{
		TaskButton taskButton = null;
		try
		{
			TaskArea taskArea = Desktop.getDesktop().getTaskArea();
			Vector v = taskArea.getTasks();
			for(int i=0;i<v.size();i++)
				System.out.println("Task "+i+" : "+v.elementAt(i));
			taskButton = Desktop.getDesktop().getTaskArea().getCurrentTask();
		}
		catch(NullPointerException e)
		{
			// Could happend when executing parts of the application 
			// without the desktop.(getDesktop()==null)
			e.printStackTrace();
			return null;
		}

		// No active business screen is open.
		if(taskButton==null)
			return getMainMatafPanel();
		
		MatafInternalFrame internalFrame = (MatafInternalFrame)taskButton.getInternalFrame();
		JScrollPane scroller = (JScrollPane)internalFrame.getContentPane();
		Component comp = scroller.getViewport().getView();
		
		// The view is in construction.
		if(comp==null)
			return null;

		// There is an active view that is NOT an instance of MatafPanel.
		if(!(comp instanceof MatafDSEPanel))
			return getMainMatafPanel();

		return (MatafDSEPanel)scroller.getViewport().getView();
	}

	/**
	 * A convenient method for referencing the active context.
	 * The active context is the context of the currently active
	 * view of the business screen.
	 */
	public static Context getActiveContext()
	{
		MatafDSEPanel dsePanel = getActiveMatafPanel();
		return (dsePanel!=null) ? dsePanel.getContext() : null;
	}
	
	/** 
	 * A convenient method for referencing the main matafPanel.
	 * The main matafPanel is the panel that is always open underneath
	 * all the business screens.
	 */
	public static MatafDSEPanel getMainMatafPanel()
	{
		Desktop desktop = Desktop.getDesktop();
		if(desktop==null)
			return null;
		WorkingArea workingArea = desktop.getWorkingArea();
		if(workingArea==null)
			return null;
		Component[] comps = workingArea.getComponents();
		for(int i=comps.length-1;i>=0;i--)
			if(comps[i] instanceof MatafDSEPanel)
				return (MatafDSEPanel)comps[i];
		// Should never be reached.
		return null;
	}
	
	
	/**
	 * Adding keys to the root pane to allow them to operate fron both 
	 * the menu and the content pane.	
	 * Creates the additional keys functionality :
	 * - Pressing F7 under the teller menu will activate
	 *   a teller-related transaction.
	 * - Pressing Shift+F9 will toggle the nesting mode.
	 * 
	 * Note : These keys are attached to the InputMap of the root pane.
	 * 			to view keys that are processed with key listeners view the
	 * 			comments inside the method.
	 */	 
	private void initializeRootPane()
	{
		final JRootPane rootPane = Desktop.getDesktop().getRootPane();

		// Create the input map.
		InputMap im = rootPane.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		im.put(KeyStroke.getKeyStroke("F7"), "tellerInfo");
		im.put(KeyStroke.getKeyStroke("control F7"), "openRTDebugger");
		im.put(KeyStroke.getKeyStroke("shift F9"), "toggleNesting");
		im.put(KeyStroke.getKeyStroke("shift F5"),"openCalculator");
		
		im.put(KeyStroke.getKeyStroke("alt control SCROLL_LOCK"), "DEBUG");

		ActionMap am = rootPane.getActionMap();
		
		// DEBUG
		am.put("DEBUG", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				Desktop.getFrame().setSize(1024,568);
				JOptionPane.showMessageDialog(Desktop.getFrame(),"DUMPING DEBUG INFORMATION");
				System.out.println("------------------------- DUMPING DEBUG INFORMATION ----------------------");
				
				System.out.println("Number Frames In WorkingArea = "+Desktop.getDesktop().getWorkingArea().getFrames().size());
				Component[] comps = Desktop.getDesktop().getWorkingArea().getComponents();
				for(int i=0;i<comps.length;i++)
				{
					System.out.println("comps["+i+"]="+comps[i]);
				}
				
				System.out.println("Current InternalFrame = "+Desktop.getDesktop().getTaskArea().getCurrentTask().getInternalFrame());
				MatafInternalFrame internalFrame = (MatafInternalFrame)Desktop.getDesktop().getTaskArea().getCurrentTask().getInternalFrame();
				JScrollPane scroller = (JScrollPane)internalFrame.getContentPane();
				MatafDSEPanel p = (MatafDSEPanel)scroller.getViewport().getView();
				System.out.println("\tcomponent="+p.getShortcutField());				
			}
		});
		
		// Verify that the teller menu is open !
		am.put("tellerInfo", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				MatafMenuBar.disableMenuReopening();
				
				MenuElement[] path = MenuSelectionManager.defaultManager().getSelectedPath();

				// No menu is open.
				if(path.length==0)
					return;
					
				// Check if currently opened menu element is part of
				// the teller menu.
				if(MatafMenuBar.getTellerMenu().isMenuComponent((Component)path[path.length-1]))
					asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+KeyEvent.VK_F7);
			}
		});				
		
		/**
		 * Switches between two opened RT screens.
		 */
		am.put("toggleNesting", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			   	
				asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_SHIFT<<8) + KeyEvent.VK_F9));
			}
		});		
		
		am.put("openCalculator", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			   	
				asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_SHIFT<<8) + KeyEvent.VK_F5));
			}
		});
		
		am.put("openRTDebugger", new AbstractAction() 
		{			
			public void actionPerformed(ActionEvent e) 
			{			   	
				asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
									"Key",
									""+((KeyEvent.VK_CONTROL<<8) + KeyEvent.VK_F7));
			}
		});
		
		// Bind the shortcut keys to the rootPane.		
		MenuShortcutManager.bindShortcutKeys(rootPane);
		
		/**
		 * The following keys are processed in this listener :
		 * 'All numeric keys' - Dispatched to the corresponding Action in
		 * the component's ActionMap.
		 * 'ENTER' - When the menu is closed, it will open the 'Sheiltot' menu.
		 * 'ESCAPE - When the menu is closed, it will exit from nesting mode.
		 * 
		 * This keyListener is invoked when typing keys while the JRootPane is
		 * in focus.
		 */
		rootPane.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				//GLogger.debug("From ROOTPANE e = "+e);
				
				// Make the ESCAPE key exit from nesting mode (only
				// when the menu is not open).
				if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
				{
					// Check that the menu is closed.
					if(MenuSelectionManager.defaultManager().getSelectedPath().length==0)
					{
						asynchronicSend(RTCommands.RT_KEYBOARD_COMMANDS,
											"Key",
											""+KeyEvent.VK_ESCAPE);
					}
				}
				
				// When the menu is closed, Enter opens the first menu.
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					// Check that the menu is closed.
					if(MenuSelectionManager.defaultManager().getSelectedPath().length==0)
					{
						JMenuItem menuItem = MatafMenuBar.getMenuItemByTaskName("1000");
						menuItem.doClick();
					}
				}
				
				// Dispatch digits to thier corresponding Action class.
				if(Character.isDigit(e.getKeyChar()))
				{
					Action a = (Action)rootPane.getActionMap().get(""+e.getKeyChar());
					GLogger.debug("Action = "+a);
					
					// Activate the desired Action.
					if(a!=null)
						a.actionPerformed(new ActionEvent(this,0,"Shortcut Key = "+e.getKeyChar()));
				}
			}
		});
		
		
		/*
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
		
			public void eventDispatched(AWTEvent event) 
			{
				KeyEvent keyEvent = (KeyEvent)event;
				System.out.println("Key Event : "+keyEvent);
				
			}
		},AWTEvent.KEY_EVENT_MASK);*/
	}
	
	/**
	 * Sets the keys that should function as the default forward/backward
	 * focus traversal keys.
	 */
	private void installFocusTraversalKeys()
	{
		// Create the 2 sets for forward and backward focus keys.
		Set forwardFocusKeysSet = new HashSet();
		Set backwardFocusKeysSet = new HashSet();
		StringTokenizer tokenizer;
		
		// Get the forward focus keys from the settings.properties file.
		tokenizer = new StringTokenizer(
						(String)com.ibm.dse.gui.Settings.getValueAt("forwardTraversalKeys"),",");		
		while (tokenizer.hasMoreTokens()) {
			forwardFocusKeysSet.add(AWTKeyStroke.getAWTKeyStroke(tokenizer.nextToken()));
		}			
		
		// Get the backward focus keys from the settings.properties file.
		tokenizer = new StringTokenizer(
						(String)com.ibm.dse.gui.Settings.getValueAt("backwardTraversalKeys"),",");
		while (tokenizer.hasMoreTokens()) {
			backwardFocusKeysSet.add(AWTKeyStroke.getAWTKeyStroke(tokenizer.nextToken()));
		}
			
		KeyboardFocusManager keyboardFocusManager = 
							KeyboardFocusManager.getCurrentKeyboardFocusManager();
		
		// Install the forward focus keys.
		keyboardFocusManager.setDefaultFocusTraversalKeys(
				KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,
				forwardFocusKeysSet);
				
		// Install the backward focus keys.					
		keyboardFocusManager.setDefaultFocusTraversalKeys(
				KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,
				backwardFocusKeysSet);
	}
	
	/**
	 * Convenient method for activating a transaction by its name.
	 */
	public static void activateTranscationByNumber(String trxNumber) throws Exception
	{
		TaskInfo ti = Desktop.getDesktop().getTaskInfo(trxNumber);
		
		// Transaction does not exist.
		if(ti==null)
		{		
			int fromIndex = trxNumber.length()-3;
			MatafOptionPane.showMessageDialog(Desktop.getFrame(),
												"��� ����� "+trxNumber.substring(fromIndex)+" ����.",
												"�����",
												JOptionPane.ERROR_MESSAGE);
			return;
		}
			
		GLogger.debug("Executing : "+trxNumber);
		
		DSEClientOperation clientOp = 
			(DSEClientOperation) DSEClientOperation.readObject(ti.getOperation());
		String taskName = ti.getTaskName();
		
		// Activate the RT transaction.
		if(clientOp instanceof ActivateRTTransactionOp)
			((ActivateRTTransactionOp)clientOp).execute(taskName);
		else
		{
			MatafMenuItem menuItem = 
				(MatafMenuItem)MatafMenuBar.getMenuItemByTaskName(trxNumber);
			Desktop.getDesktop().openTask(menuItem);
		}
	}
	
	/**
	 * If no progress was made after 60 seconds, this thread
	 * displays an error message.
	 */
	private static void startLoadingTimeoutThread()
	{
		final int TIMEOUT_IN_SECONDS = 60;
		final int DELAY = 125;
		new Thread()
		{
			public void run() 
			{
				int counter = 0;
				String[] progressSigns = new String[]{"|","\\","--","/"};
				String[] progressMessages = new String[progressSigns.length];
				String loadingMessage = "�����";
				
				for(int i=0;i<progressSigns.length;i++)
					progressMessages[i] = loadingMessage + " " + progressSigns[i];
				
				while(++counter<(TIMEOUT_IN_SECONDS*1000/DELAY))
				{
					try
					{
						Thread.sleep(DELAY);
					}
					catch(InterruptedException e) {}
				
					MatafDSEPanel matafDSEPanel = getMainMatafPanel();
					if(matafDSEPanel==null)
						continue;
					if(progressNumber==0)
						matafDSEPanel.getTheErrorLabel().setText(progressMessages[counter%progressSigns.length]);
				}				
				
				if(progressNumber==0)
				{
					getMainMatafPanel().getTheErrorLabel().queueErrorMessage("������ ���� � : "+GregorianCalendar.getInstance().getTime(), Color.red);
					MatafOptionPane.showMessageDialog(Desktop.getFrame(), "����� �-RT �����","����� ������",JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}.start();
	}
	
	public static void closeCurrentView()
	{
		getActiveMatafPanel().closeCurrentView();
	}
	
	/** 
	 * Init the Log4J.
	 */
	private static void startLog4J()
	{
		
		GLogger.configure(serverPath + "/log4j.properties",
								GLogger.CLIENT_LOG);
		
	}	
	
	/**
	 * Starts the application desktop as an application.
	 * If the first argument is -p, the second argument is assumed to be the path
	 * to the configuration file. If there are no arguments, the default path is assumed.
	 * This method invokes the initialization (init()) method.
	 * @param args an array of command-line arguments
	 */
	public static void main(java.lang.String[] args) 
	{
		try 
		{
			if(!DesktopSettings.RT_ONLY_COEXSISTANCE)
				com.ibm.bridge2java.OleEnvironment.Initialize();
						
			aOpenDesktop = new OpenDesktop();
			if (args.length >= 2) 
			{
				if (args[0].equals("-url"))//$NON-NLS-1$
					serverPath = args[1];
				iniPath = serverPath + "/dse/dse.ini";
				GLogger.info("iniPath = " + iniPath);
			}		
			
			startLog4J();
			
			aOpenDesktop.init();
			
			// Sending SHUTDOWN command to the RT when closing the desktop.
			Desktop.getFrame().addWindowListener(new WindowAdapter() 
			{			
				public void windowClosing(WindowEvent e) 
				{
					try
					{
						// Request user permission for exit.
						int input = 
							MatafOptionPane.showConfirmDialog(Desktop.getFrame(),
															 "���� ������� ������� ?",
															  "����� �������",
															  JOptionPane.YES_NO_OPTION,
															  JOptionPane.QUESTION_MESSAGE);
						// User canceled exit.
						if (input == JOptionPane.NO_OPTION)
							return;
						
						if(!DesktopSettings.ACTIVATE_RUNTIME)
						{
							System.exit(0);
						}
						
						// Release the RT Process.
						GLogger.debug("In windowClosing.");
						ProxyService proxy = (ProxyService)ctxt.getService("proxyService");
						proxy.sendRequest(
										Integer.parseInt(RTCommands.SHUTDOWN_COMMANDS),
										"Shutdown",
										"now!");
						GLogger.debug("Shutting down.");
					}
					catch(RequestException e2)
					{
						GLogger.debug("Exiting Application without sending disconnection Request. (Connection to RT not established)");	
					}
					catch(Exception e2)
					{
						e2.printStackTrace();
					}
					
					exit();
				}
			});
	
			// Method returns immediatly.
			if(DesktopSettings.ACTIVATE_RUNTIME)
				activateRuntime();
			else
				MatafMenuBar.setMenuEnabled(true);
			
			// Update components UI After the desktop has installed its L&F.
			//aOpenDesktop.setDesktopComponentsSpecialUI();
			
			aOpenDesktop.initializeRootPane();
			
			aOpenDesktop.installFocusTraversalKeys();			

			// Show the completed Desktop.
			Desktop.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
			Desktop.getFrame().setVisible(true);
			
			new MQListenerOverride().start();
			
			startLoadingTimeoutThread();			
			
		} 
		catch (Throwable exception)
		{
			GLogger.debug(exception);
			System.err.println(resResources.getString("Exception_occurred_in_main3")); //$NON-NLS-1$
			exception.printStackTrace(System.out);
		}	
	}	
}
