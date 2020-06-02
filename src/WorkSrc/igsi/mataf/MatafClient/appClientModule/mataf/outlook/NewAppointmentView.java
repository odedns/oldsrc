package mataf.outlook;

import javax.swing.JFrame;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class NewAppointmentView extends JFrame {

     private javax.swing.JPanel jContentPane = null;
     private mataf.outlook.AppointmentPanel apointmentPanel = null;
	/**
	 * This method initializes 
	 * 
	 */
	public NewAppointmentView() {
		super();
		initialize();
		this.pack();
		this.show();
	}
	public static void main(String[] args) {
		try {			
			NewAppointmentView test = new NewAppointmentView();
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
        this.setTitle("פגישה חדשה");
        this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        this.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        this.setBackground(java.awt.Color.white);
        this.setResizable(true);
        this.setName("appointmentframe");
			
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
			jContentPane.add(getApointmentPanel(), java.awt.BorderLayout.CENTER);
		}
		return jContentPane;
	}
	/**
	 * This method initializes apointmentPanel
	 * 
	 * @return mataf.outlook.ApointmentPanel
	 */
	private mataf.outlook.AppointmentPanel getApointmentPanel() {
		if(apointmentPanel == null) {
			apointmentPanel = new mataf.outlook.AppointmentPanel();
		}
		return apointmentPanel;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
