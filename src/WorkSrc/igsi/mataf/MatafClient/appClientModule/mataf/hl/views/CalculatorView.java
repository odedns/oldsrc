package mataf.hl.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

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

import com.ibm.bridge2java.ActiveXCanvas;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CalculatorView extends JFrame implements DWebBrowserEvents, ActionListener {

     private javax.swing.JPanel jContentPane = null;
     private ActiveXCanvas activeXCanvas;
     private MatafButton calButton1;
	/**
	 * This method initializes 
	 * 
	 */
	public CalculatorView() {
		super();
		initialize();
		this.pack();
		this.show();
	}
	public static void main(String[] args) {
		try {			
			new CalculatorView();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			com.ibm.bridge2java.OleEnvironment.UnInitialize();
		} 
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.setContentPane(getJContentPane());
        this.setSize(307, 200);
        this.setTitle("כלי חישוב ותכנון");
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(java.awt.Color.white);
        this.setResizable(true);
        this.setName("caculatorframe");
			
	}
	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private javax.swing.JPanel getJContentPane() {
		if(jContentPane == null) {
			jContentPane = new javax.swing.JPanel();
			java.awt.BorderLayout layBorderLayout1 = new java.awt.BorderLayout();
			jContentPane.setLayout(layBorderLayout1);
			jContentPane.add(getActiveXCanvas(), java.awt.BorderLayout.CENTER);
			jContentPane.add(getCalButton1(), BorderLayout.NORTH);
		}
		return jContentPane;
	}
	/**
	 * This method initializes apointmentPanel
	 * 
	 * @return mataf.outlook.ApointmentPanel
	 */
	public ActiveXCanvas getActiveXCanvas() {
		if (activeXCanvas == null) {
			activeXCanvas = new ActiveXCanvas();
			activeXCanvas.setBounds(20, 50, 380, 465);			
		}
		return activeXCanvas;
	}
	public void BeforeNavigate(DWebBrowserEvents_BeforeNavigateEvent e) {
	}

	public void CommandStateChange(DWebBrowserEvents_CommandStateChangeEvent e) {
	}

	public void DownloadBegin(DWebBrowserEvents_DownloadBeginEvent e) {
	}

	public void DownloadComplete(DWebBrowserEvents_DownloadCompleteEvent e) {
	}

	public void FrameBeforeNavigate(DWebBrowserEvents_FrameBeforeNavigateEvent e) {
	}

	public void FrameNavigateComplete(DWebBrowserEvents_FrameNavigateCompleteEvent e) {
	}

	public void FrameNewWindow(DWebBrowserEvents_FrameNewWindowEvent e) {
	}

	public void NavigateComplete(DWebBrowserEvents_NavigateCompleteEvent e) {
	}

	public void NewWindow(DWebBrowserEvents_NewWindowEvent e) {
	}

	public void ProgressChange(DWebBrowserEvents_ProgressChangeEvent e) {
	}

	public void PropertyChange(DWebBrowserEvents_PropertyChangeEvent e) {
	}

	public void Quit(DWebBrowserEvents_QuitEvent e) {
	}

	public void StatusTextChange(DWebBrowserEvents_StatusTextChangeEvent e) {
	}

	public void TitleChange(DWebBrowserEvents_TitleChangeEvent e) {
	}

	public void WindowActivate(DWebBrowserEvents_WindowActivateEvent e) {
	}

	public void WindowMove(DWebBrowserEvents_WindowMoveEvent e) {
	}

	public void WindowResize(DWebBrowserEvents_WindowResizeEvent e) {
	}

	public void actionPerformed(ActionEvent anEvent) {
		if (anEvent.getActionCommand().equals("Calculate_Kupat_Gemel")) {
			
		}
	}

	/**
	 * Returns the calButton1.
	 * @return MatafButton
	 */
	public MatafButton getCalButton1() {
		if (calButton1 == null) {
			calButton1 = new MatafButton();
			calButton1.setText("מחשבון הטבות מס בקופות גמל");
			calButton1.setSize(150,20);
			calButton1.setActionCommand("Calculate_Kupat_Gemel");
			calButton1.setPreferredSize(new Dimension(150,20));
		}
		return calButton1;
	}

}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
