package mataf.hl.views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mataf.types.MatafButton;
import MSIE.DWebBrowserEvents;
import MSIE.DWebBrowserEvents_BeforeNavigateEvent;
import MSIE.DWebBrowserEvents_CommandStateChangeEvent;
import MSIE.DWebBrowserEvents_DownloadBeginEvent;
import MSIE.DWebBrowserEvents_DownloadCompleteEvent;
import MSIE.DWebBrowserEvents_FrameBeforeNavigateEvent;
import MSIE.DWebBrowserEvents_FrameNavigateCompleteEvent;
import MSIE.DWebBrowserEvents_FrameNewWindowEvent;
import MSIE.DWebBrowserEvents_NavigateCompleteEvent;
import MSIE.DWebBrowserEvents_NewWindowEvent;
import MSIE.DWebBrowserEvents_ProgressChangeEvent;
import MSIE.DWebBrowserEvents_PropertyChangeEvent;
import MSIE.DWebBrowserEvents_QuitEvent;
import MSIE.DWebBrowserEvents_StatusTextChangeEvent;
import MSIE.DWebBrowserEvents_TitleChangeEvent;
import MSIE.DWebBrowserEvents_WindowActivateEvent;
import MSIE.DWebBrowserEvents_WindowMoveEvent;
import MSIE.DWebBrowserEvents_WindowResizeEvent;
import MSIE.WebBrowser_V1;

import com.ibm.bridge2java.ActiveXCanvas;
import com.ibm.dse.gui.DSEPanel;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BrowserView
	extends DSEPanel
	implements DWebBrowserEvents, ActionListener {
		//private static QuickIE        ieTest;
	private WebBrowser_V1 browser;
	private ActiveXCanvas activeXCanvas;
	private MatafButton goGoogleButton;
	private MatafButton cancelButton;
	private MatafButton newAppointmentButton;
		//Constructor
	public BrowserView() {
		initilize();
	}

	private void initilize() {
		
		this.setName("BrowserView");
		this.setContextName("browserViewCtx");
		this.setViewName("BrowserView");
		this.setFocusCycleRoot(true);
		this.setTitle("אינטרנט");
		this.setBounds(0, 0, 1016, 550);		

		this.add(getActiveXCanvas());
		this.add(getGoGoogleButton());
		this.add(getCancelButton());
		this.add(getNewAppointmentButton());
		this.setVisible(true);		
	}

	/**
	 * @see MSIE.DWebBrowserEvents#BeforeNavigate(DWebBrowserEvents_BeforeNavigateEvent)
	 */
	public void BeforeNavigate(DWebBrowserEvents_BeforeNavigateEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#CommandStateChange(DWebBrowserEvents_CommandStateChangeEvent)
	 */
	public void CommandStateChange(DWebBrowserEvents_CommandStateChangeEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#DownloadBegin(DWebBrowserEvents_DownloadBeginEvent)
	 */
	public void DownloadBegin(DWebBrowserEvents_DownloadBeginEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#DownloadComplete(DWebBrowserEvents_DownloadCompleteEvent)
	 */
	public void DownloadComplete(DWebBrowserEvents_DownloadCompleteEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#FrameBeforeNavigate(DWebBrowserEvents_FrameBeforeNavigateEvent)
	 */
	public void FrameBeforeNavigate(DWebBrowserEvents_FrameBeforeNavigateEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#FrameNavigateComplete(DWebBrowserEvents_FrameNavigateCompleteEvent)
	 */
	public void FrameNavigateComplete(DWebBrowserEvents_FrameNavigateCompleteEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#FrameNewWindow(DWebBrowserEvents_FrameNewWindowEvent)
	 */
	public void FrameNewWindow(DWebBrowserEvents_FrameNewWindowEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#NavigateComplete(DWebBrowserEvents_NavigateCompleteEvent)
	 */
	public void NavigateComplete(DWebBrowserEvents_NavigateCompleteEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#NewWindow(DWebBrowserEvents_NewWindowEvent)
	 */
	public void NewWindow(DWebBrowserEvents_NewWindowEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#ProgressChange(DWebBrowserEvents_ProgressChangeEvent)
	 */
	public void ProgressChange(DWebBrowserEvents_ProgressChangeEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#PropertyChange(DWebBrowserEvents_PropertyChangeEvent)
	 */
	public void PropertyChange(DWebBrowserEvents_PropertyChangeEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#Quit(DWebBrowserEvents_QuitEvent)
	 */
	public void Quit(DWebBrowserEvents_QuitEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#StatusTextChange(DWebBrowserEvents_StatusTextChangeEvent)
	 */
	public void StatusTextChange(DWebBrowserEvents_StatusTextChangeEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#TitleChange(DWebBrowserEvents_TitleChangeEvent)
	 */
	public void TitleChange(DWebBrowserEvents_TitleChangeEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#WindowActivate(DWebBrowserEvents_WindowActivateEvent)
	 */
	public void WindowActivate(DWebBrowserEvents_WindowActivateEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#WindowMove(DWebBrowserEvents_WindowMoveEvent)
	 */
	public void WindowMove(DWebBrowserEvents_WindowMoveEvent e) {
	}

	/**
	 * @see MSIE.DWebBrowserEvents#WindowResize(DWebBrowserEvents_WindowResizeEvent)
	 */
	public void WindowResize(DWebBrowserEvents_WindowResizeEvent e) {
	}

	/**
	 * Returns the activeXCanvas.
	 * @return ActiveXCanvas
	 */
	public ActiveXCanvas getActiveXCanvas() {
		if (activeXCanvas == null) {
			activeXCanvas = new ActiveXCanvas();
			activeXCanvas.setBounds(0, 50, 1017, 550);
			
		}
		return activeXCanvas;
	}

	/**
	 * Returns the browser.
	 * @return WebBrowser_V1
	 */
	public WebBrowser_V1 getBrowser() {
		if (browser == null) {
			browser = new WebBrowser_V1(activeXCanvas.getCanvasHwnd(), 0);
			browser.addDWebBrowserEventsListener(this);
		}
		return browser;
	}

	/**
	 * Sets the activeXCanvas.
	 * @param activeXCanvas The activeXCanvas to set
	 */
	public void setActiveXCanvas(ActiveXCanvas activeXCanvas) {
		this.activeXCanvas = activeXCanvas;
	}

	/**
	 * Sets the browser.
	 * @param browser The browser to set
	 */
	public void setBrowser(WebBrowser_V1 browser) {
		this.browser = browser;
	}

	/**
	 * Returns the goGoogleButton.
	 * @return MatafButton
	 */
	public MatafButton getGoGoogleButton() {
		if (goGoogleButton == null) {
			goGoogleButton = new MatafButton();
			goGoogleButton.setActionCommand("NAV");
			goGoogleButton.setBounds(913, 30, 100, 20);
			goGoogleButton.addActionListener(this);
			goGoogleButton.setText("Intranet");
			goGoogleButton.setToolTipText("Browse");
			goGoogleButton.setBackground(new Color(37,114,185));
		}
		return goGoogleButton;
	}

	/**
	 * Sets the goGoogleButton.
	 * @param goGoogleButton The goGoogleButton to set
	 */
	public void setGoGoogleButton(MatafButton goGoogleButton) {
		this.goGoogleButton = goGoogleButton;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			String action = e.getActionCommand();
			if (action.equals("NAV")) {
				getBrowser().Navigate("www.fibi.co.il");
			} else if (action.equals("Set_Appointment")) {
				getBrowser().Navigate("http://s5380056/SNIF/");
			} 

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns the cancelButton.
	 * @return MatafButton
	 */
	public MatafButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new MatafButton();
			cancelButton.setType("Cancel");
			cancelButton.setBounds(808, 30, 100, 20);			
			cancelButton.setText("תפריט ראשי");
			cancelButton.setToolTipText("יציאה מדף האינטרנט");
			cancelButton.setBackground(new Color(37,114,185));
		}
		return cancelButton;
	}

	/**
	 * Sets the cancelButton.
	 * @param cancelButton The cancelButton to set
	 */
	public void setCancelButton(MatafButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	/**
	 * Returns the newAppointmentButton.
	 * @return MatafButton
	 */
	public MatafButton getNewAppointmentButton() {
		if (newAppointmentButton == null) {
			newAppointmentButton = new MatafButton();
			newAppointmentButton.setActionCommand("Set_Appointment");
			newAppointmentButton.setBounds(703, 30, 100, 20);			
			newAppointmentButton.setText("מחשבון הטבות מס");
			newAppointmentButton.addActionListener(this);
			newAppointmentButton.setBackground(new Color(37,114,185));
		}
		return newAppointmentButton;
	}

	/**
	 * Sets the newAppointmentButton.
	 * @param newAppointmentButton The newAppointmentButton to set
	 */
	public void setNewAppointmentButton(MatafButton newAppointmentButton) {
		this.newAppointmentButton = newAppointmentButton;
	}

}
