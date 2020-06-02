package mataf.outlook;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.PopupFactory;
import javax.swing.SpinnerDateModel;

import mataf.types.MatafSpinner;
import Outlook.Application;
import Outlook.AppointmentItem;
import Outlook.OlItemType;
import Outlook._NameSpace;

/**
 * @author Eyal Ben Ze'ev
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class AppointmentPanel extends JPanel implements ActionListener {

	private javax.swing.JLabel jLabel = null;
	private javax.swing.JLabel jLabel1 = null;
	private javax.swing.JLabel jLabel2 = null;
	private javax.swing.JLabel jLabel3 = null;
	private javax.swing.JButton jButton = null;
	private javax.swing.JTextField jTextField = null;
	private javax.swing.JTextField jTextField1 = null;	
	private Application outlook;
	private _NameSpace outlookNameSpace;
	private AppointmentItem appointment;

	private mataf.types.misc.CalendarButton calendarButton = null;
	private PopupFactory popFactory;
	private mataf.types.misc.MatafCalendarDateField startDate = null;
	private mataf.types.misc.MatafCalendarDateField endDate = null;
	private mataf.types.misc.CalendarButton calendarButton2 = null;
	private MatafSpinner startTimeSpinner = null;
	private MatafSpinner endTimeSpinner = null;


	/**
	 * This method initializes 
	 * 
	 */
	public AppointmentPanel() {
		super();
		popFactory = PopupFactory.getSharedInstance();
		initialize();
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		this.setLayout(null);
		this.add(getJLabel(), null);
		this.add(getJLabel1(), null);
		this.add(getJLabel2(), null);
		this.add(getJLabel3(), null);
		this.add(getJButton(), null);
		this.add(getJTextField(), null);
		this.add(getJTextField1(), null);
		this.add(getEndDate(), null);
		this.add(getCalendarButton(), null);
		this.add(getStartDate(), null);
		this.add(getCalendarButton2(), null);
		this.add(getStartTimeSpinner(), null);
		this.add(getEndTimeSpinner(), null);
		this.setSize(338, 164);
		this.setPreferredSize(new java.awt.Dimension(338,164));
		this.setName("appointmentPanel");
		this.setBackground(new java.awt.Color(240, 240, 255));
		this.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);

	}
	/**
	 * This method initializes jLabel
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel() {
		if (jLabel == null) {
			jLabel = new javax.swing.JLabel();
			jLabel.setSize(80, 20);
			jLabel.setText("נושא:");
			jLabel.setBackground(java.awt.Color.white);
			jLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			jLabel.setLocation(250, 10);
		}
		return jLabel;
	}
	/**
	 * This method initializes jLabel1
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new javax.swing.JLabel();
			jLabel1.setSize(80, 20);
			jLabel1.setText("מיקום:");
			jLabel1.setBackground(java.awt.Color.white);
			jLabel1.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			jLabel1.setLocation(250, 41);
		}
		return jLabel1;
	}
	/**
	 * This method initializes jLabel2
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new javax.swing.JLabel();
			jLabel2.setSize(80, 20);
			jLabel2.setText("מועד התחלה:");
			jLabel2.setBackground(java.awt.Color.white);
			jLabel2.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			jLabel2.setLocation(250, 72);
		}
		return jLabel2;
	}
	/**
	 * This method initializes jLabel3
	 * 
	 * @return javax.swing.JLabel
	 */
	private javax.swing.JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new javax.swing.JLabel();
			jLabel3.setSize(80, 20);
			jLabel3.setText("מועד סיום:");
			jLabel3.setBackground(java.awt.Color.white);
			jLabel3.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			jLabel3.setLocation(250, 103);
		}
		return jLabel3;
	}
	/**
	 * This method initializes jButton
	 * 
	 * @return javax.swing.JButton
	 */
	private javax.swing.JButton getJButton() {
		if (jButton == null) {
			jButton = new javax.swing.JButton();
			jButton.setSize(110, 20);
			jButton.setText("שמור פגישה");
			jButton.setLocation(6, 134);
			jButton.setActionCommand("setAppointment");
			jButton.addActionListener(this);
		}
		return jButton;
	}
	/**
	 * This method initializes jTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField() {
		if (jTextField == null) {
			jTextField = new javax.swing.JTextField();
			jTextField.setBounds(6, 10, 233, 20);
			jTextField.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return jTextField;
	}
	/**
	 * This method initializes jTextField1
	 * 
	 * @return javax.swing.JTextField
	 */
	private javax.swing.JTextField getJTextField1() {
		if (jTextField1 == null) {
			jTextField1 = new javax.swing.JTextField();
			jTextField1.setBounds(6, 41, 233, 20);
			jTextField1.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return jTextField1;
	}
	/**
	 * This method initializes endDate
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	private mataf.types.misc.MatafCalendarDateField getEndDate() {
		if (endDate == null) {
			endDate = new mataf.types.misc.MatafCalendarDateField();
			// change the simple date format to local date format
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
			endDate.setDateFormatter(formatter);
			endDate.setBounds(91, 103, 148, 20);
			endDate.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return endDate;
	}
	/**
	 * @see java.awt.event.ActionListener#actionPerformed(ActionEvent)
	 */
	public void actionPerformed(ActionEvent anEvent) {
		// Check all reqiered fields has entered
		Calendar startDate = Calendar.getInstance();
		Calendar endDate = Calendar.getInstance();
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		
		String subject = getJTextField().getText();
		String location = getJTextField1().getText();
		
		startDate.setTime(getStartDate().getDate());
		
		endDate.setTime(getEndDate().getDate());
		
		startTime.setTime((Date)getStartTimeSpinner().getValue());
		
		endTime.setTime((Date)getEndTimeSpinner().getValue());
		
		// Combine Date and time to 1 Date object
		startDate.set(Calendar.HOUR, startTime.get(Calendar.HOUR_OF_DAY));
		startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
		
		endDate.set(Calendar.HOUR, endTime.get(Calendar.HOUR_OF_DAY));
		endDate.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
				
		// Initialize the Java2Com Environment
		com.ibm.bridge2java.OleEnvironment.Initialize();
		if (anEvent.getActionCommand().equals("setAppointment")) {
			try {
				// Create an Outlook application.
				outlook = new Application();

				// Get NameSpace and Logon.
				outlookNameSpace = outlook.GetNamespace("MAPI");
				outlookNameSpace.Logon();

				// Create a new AppointmentItem.
				Integer appointObj = (Integer) outlook.CreateItem(OlItemType.olAppointmentItem);
				appointment = new AppointmentItem(appointObj.intValue());

				// Set some common properties.
				appointment.set_Subject(subject);
				
				appointment.set_Location(location);
				
				appointment.set_Start(startDate.getTime());

				appointment.set_End(endDate.getTime());

				// Save to Calendar.
				appointment.Save();

				// Logoff.
				outlookNameSpace.Logoff();
				
			} catch (java.lang.Exception e) {
				System.out.println("message: " + e.getMessage());
				e.printStackTrace();
			} finally {
				outlook = null;
				com.ibm.bridge2java.OleEnvironment.UnInitialize();
			}
		}
	}

	/**
	 * This method initializes calendarButton
	 * 
	 * @return mataf.dse.gui.CalendarButton
	 */
	private mataf.types.misc.CalendarButton getCalendarButton() {
		if (calendarButton == null) {
			calendarButton = new mataf.types.misc.CalendarButton(getStartDate());
			calendarButton.setBounds(73, 72, 20, 20);
		}
		return calendarButton;
	}
	/**
	 * This method initializes startDate
	 * 
	 * @return mataf.dse.gui.MatafTextField
	 */
	private mataf.types.misc.MatafCalendarDateField getStartDate() {
		if (startDate == null) {
			startDate = new mataf.types.misc.MatafCalendarDateField();
			// change the simple date format to local date format
			DateFormat formatter = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
			startDate.setDateFormatter(formatter);
			startDate.setBounds(91, 72, 148, 20);
			startDate.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return startDate;
	}
	/**
	 * This method initializes calendarButton2
	 * 
	 * @return mataf.dse.gui.CalendarButton
	 */
	private mataf.types.misc.CalendarButton getCalendarButton2() {
		if (calendarButton2 == null) {
			calendarButton2 = new mataf.types.misc.CalendarButton(getEndDate());
			calendarButton2.setBounds(73, 103, 20, 20);
		}
		return calendarButton2;
	}
	/**
	 * This method initializes matafSpinner
	 * 
	 * @return mataf.dse.gui.MatafSpinner
	 */
	private MatafSpinner getStartTimeSpinner() {
		if (startTimeSpinner == null) {
			startTimeSpinner = new MatafSpinner();
			startTimeSpinner.setModel(getNewSpinnerDateModel());
			JSpinner.DateEditor dateEditor = 
								(JSpinner.DateEditor) startTimeSpinner.getEditor();
			dateEditor.getFormat().applyPattern("HH:mm");
			startTimeSpinner.setBounds(6, 72, 56, 20);
		}
		return startTimeSpinner;
	}
	/**
	 * This method initializes matafSpinner2
	 * 
	 * @return mataf.dse.gui.MatafSpinner
	 */
	private MatafSpinner getEndTimeSpinner() {
		if (endTimeSpinner == null) {
			endTimeSpinner = new MatafSpinner();
			endTimeSpinner.setModel(getNewSpinnerDateModel());
			JSpinner.DateEditor dateEditor = 
									(JSpinner.DateEditor) endTimeSpinner.getEditor();
			dateEditor.getFormat().applyPattern("HH:mm");
			endTimeSpinner.setBounds(6, 103, 56, 20);
		}
		return endTimeSpinner;
	}

	private SpinnerDateModel getNewSpinnerDateModel() {
		SpinnerDateModel spinnerDateModel = new SpinnerDateModel();
		spinnerDateModel.setCalendarField(Calendar.MINUTE);

		return spinnerDateModel;
	}
} //  @jve:visual-info  decl-index=0 visual-constraint="0,0"
