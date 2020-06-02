package com.mataf.hl.views;

import mataf.desktop.views.MatafDSEPanel;

import com.ibm.dse.base.OperationRepliedEvent;
import com.ibm.dse.gui.SpErrorList;
import com.mataf.operations.ConfirmOpenHlClientOp;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class OpenHlView extends MatafDSEPanel {

     private mataf.types.MatafLabel label5 = null;
     private mataf.types.MatafLabel label6 = null;
     private mataf.types.MatafLabel label7 = null;
     private mataf.types.MatafLabel label8 = null;
     private mataf.types.MatafLabel label9 = null;
     private mataf.types.MatafLabel label10 = null;
     private mataf.types.MatafLabel label11 = null;
     private mataf.types.MatafLabel label12 = null;
     private mataf.types.MatafLabel label13 = null;
     private mataf.types.MatafLabel label14 = null;
     private mataf.types.MatafTextField textField4 = null;
     private mataf.types.MatafTextField textField5 = null;
     private mataf.types.MatafTextField textField6 = null;
     private mataf.dse.gui.MatafComboBox comboBox3 = null;
     private mataf.dse.gui.MatafComboBox comboBox4 = null;
     private mataf.dse.gui.MatafComboBox comboBox5 = null;
     private mataf.types.MatafTextField textField7 = null;
     private mataf.dse.gui.MatafComboBox comboBox7 = null;
     private mataf.dse.gui.MatafComboBox comboBox8 = null;
     private mataf.dse.gui.MatafComboBox comboBox9 = null;
     private mataf.dse.gui.MatafComboBox comboBox10 = null;
     private mataf.dse.gui.MatafComboBox comboBox11 = null;
     private mataf.types.MatafTextField textField1 = null;
     private mataf.types.MatafTextField textField8 = null;
     private mataf.types.MatafTextField textField12 = null;
     private mataf.types.MatafLabel label15 = null;
     private mataf.types.MatafTextField textField13 = null;
     private mataf.types.MatafLabel label16 = null;
     private mataf.types.MatafTextField textField14 = null;
     private mataf.types.MatafTextField textField15 = null;
     private mataf.types.MatafTextField textField16 = null;
     private mataf.types.MatafTextField textField17 = null;
     private mataf.types.MatafLabel label17 = null;
     private mataf.types.MatafTextField textField18 = null;
     private mataf.types.MatafLabel label18 = null;
     private mataf.types.MatafTextField textField19 = null;
     private mataf.types.MatafLabel label19 = null;
     private mataf.types.MatafTextField textField20 = null;
     private mataf.types.MatafLabel label20 = null;
     private mataf.dse.gui.MatafComboBox comboBox12 = null;
     private mataf.types.MatafTextField textField21 = null;
     private mataf.types.MatafTextField textField22 = null;
     private SpErrorList matafErrorList = null;
//     private OpenHlViewController controller;
     private mataf.hl.panels.PirteyCheshbon pirteyCheshbon = null;
	/**
	 * This method initializes 
	 * 
	 */
	public OpenHlView() {
		super();
//		controller = new OpenHlViewController(this);
		initialize();
				
	}
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
/*        this.add(getLabel5(), null);
        this.add(getLabel6(), null);
        this.add(getLabel7(), null);
        this.add(getLabel8(), null);
        this.add(getLabel9(), null);
        this.add(getLabel10(), null);
        this.add(getLabel11(), null);
        this.add(getLabel12(), null);
        this.add(getLabel13(), null);
        this.add(getLabel14(), null);
        this.add(getTextField4(), null);
        this.add(getTextField5(), null);
        this.add(getTextField6(), null);
        this.add(getComboBox3(), null);
        this.add(getComboBox4(), null);
        this.add(getComboBox5(), null);
        this.add(getTextField7(), null);
        this.add(getComboBox7(), null);
        this.add(getComboBox8(), null);
        this.add(getComboBox9(), null);
        this.add(getComboBox10(), null);
        this.add(getComboBox11(), null);
        this.add(getTextField1(), null);
        this.add(getTextField8(), null);
        this.add(getTextField12(), null);
        this.add(getLabel15(), null);
        this.add(getTextField13(), null);
        this.add(getLabel16(), null);
        this.add(getTextField14(), null);
        this.add(getTextField15(), null);
        this.add(getTextField16(), null);
        this.add(getTextField17(), null);
        this.add(getLabel17(), null);
        this.add(getTextField18(), null);
        this.add(getLabel18(), null);
        this.add(getTextField19(), null);
        this.add(getLabel19(), null);
        this.add(getTextField20(), null);
        this.add(getLabel20(), null);
        this.add(getComboBox12(), null);
        this.add(getTextField21(), null);
        this.add(getTextField22(), null);       
        this.add(getMatafErrorList(), null);*/
        this.setActivePanel(getPirteyCheshbon());
        this.setName("OpenHlView");
        this.setContextName("openHlViewCtx");
//        this.setOperationName("initOpenHlClientOp");
//        this.setDisableWhileOperationRunning(true);
//        this.setExecuteWhenOpen(true);
        this.setViewName("OpenHlView");
        this.setFocusCycleRoot(true);
//		setFocusTraversalPolicy(new OpenHlfocusTraversal(this));
		this.setTitle("פתיחת הלוואה");
	}
	/**
	 * This method initializes label5
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel5() {
		if(label5 == null) {
			label5 = new mataf.types.MatafLabel();
			label5.setBounds(487, 219, 79, 19);
			label5.setText("סכום");
			label5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label5;
	}
	/**
	 * This method initializes label6
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel6() {
		if(label6 == null) {
			label6 = new mataf.types.MatafLabel();
			label6.setBounds(487, 243, 79, 19);
			label6.setText("י.קרן משוערכת");
			label6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label6;
	}
	/**
	 * This method initializes label7
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel7() {
		if(label7 == null) {
			label7 = new mataf.types.MatafLabel();
			label7.setBounds(487, 267, 79, 19);
			label7.setText("חשבון תמורה");
			label7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label7;
	}
	/**
	 * This method initializes label8
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel8() {
		if(label8 == null) {
			label8 = new mataf.types.MatafLabel();
			label8.setBounds(487, 291, 79, 19);
			label8.setText("ריבית");
			label8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label8;
	}
	/**
	 * This method initializes label9
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel9() {
		if(label9 == null) {
			label9 = new mataf.types.MatafLabel();
			label9.setBounds(487, 315, 79, 19);
			label9.setText("קוד הלוואה");
			label9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label9;
	}
	/**
	 * This method initializes label10
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel10() {
		if(label10 == null) {
			label10 = new mataf.types.MatafLabel();
			label10.setBounds(487, 339, 79, 19);
			label10.setText("קוד לקוח");
			label10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label10;
	}
	/**
	 * This method initializes label11
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel11() {
		if(label11 == null) {
			label11 = new mataf.types.MatafLabel();
			label11.setBounds(487, 387, 79, 19);
			label11.setText("קוד מדד");
			label11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label11;
	}
	/**
	 * This method initializes label12
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel12() {
		if(label12 == null) {
			label12 = new mataf.types.MatafLabel();
			label12.setBounds(487, 411, 79, 19);
			label12.setText("קוד שער");
			label12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label12;
	}
	/**
	 * This method initializes label13
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel13() {
		if(label13 == null) {
			label13 = new mataf.types.MatafLabel();
			label13.setBounds(487, 435, 79, 19);
			label13.setText("קוד סגירה/פירעון");
			label13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label13;
	}
	/**
	 * This method initializes label14
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel14() {
		if(label14 == null) {
			label14 = new mataf.types.MatafLabel();
			label14.setBounds(486, 363, 79, 19);
			label14.setText("JLabel");
			label14.setVisible(false);
		}
		return label14;
	}
	/**
	 * This method initializes textField4
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	public mataf.types.MatafTextField getTextField4() {
		if(textField4 == null) {
			textField4 = new mataf.types.MatafTextField();
			textField4.setBounds(388, 219, 91, 19);
			textField4.setDataName("schum");
			textField4.setEnabled(false);
		}
		return textField4;
	}
	/**
	 * This method initializes textField5
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	public mataf.types.MatafTextField getTextField5() {
		if(textField5 == null) {
			textField5 = new mataf.types.MatafTextField();
			textField5.setBounds(388, 243, 91, 19);
			textField5.setEnabled(false);
		}
		return textField5;
	}
	/**
	 * This method initializes textField6
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField6() {
		if(textField6 == null) {
			textField6 = new mataf.types.MatafTextField();
			textField6.setBounds(410, 267, 69, 19);
			textField6.setEnabled(false);
		}
		return textField6;
	}
	/**
	 * This method initializes comboBox3
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox3() {
		if(comboBox3 == null) {
			comboBox3 = new mataf.dse.gui.MatafComboBox();
			comboBox3.setBounds(342, 267, 58, 19);
			comboBox3.setEnabled(false);
		}
		return comboBox3;
	}
	/**
	 * This method initializes comboBox4
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox4() {
		if(comboBox4 == null) {
			comboBox4 = new mataf.dse.gui.MatafComboBox();
			comboBox4.setBounds(275, 267, 58, 19);
			comboBox4.setEnabled(false);
		}
		return comboBox4;
	}
	/**
	 * This method initializes comboBox5
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox5() {
		if(comboBox5 == null) {
			comboBox5 = new mataf.dse.gui.MatafComboBox();
			comboBox5.setBounds(221, 267, 46, 19);
			comboBox5.setEnabled(false);
		}
		return comboBox5;
	}
	/**
	 * This method initializes textField7
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField7() {
		if(textField7 == null) {
			textField7 = new mataf.types.MatafTextField();
			textField7.setBounds(397, 291, 82, 19);
			textField7.setEnabled(false);
		}
		return textField7;
	}
	/**
	 * This method initializes comboBox7
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox7() {
		if(comboBox7 == null) {
			comboBox7 = new mataf.dse.gui.MatafComboBox();
			comboBox7.setBounds(421, 315, 58, 19);
			comboBox7.setEnabled(false);
		}
		return comboBox7;
	}
	/**
	 * This method initializes comboBox8
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox8() {
		if(comboBox8 == null) {
			comboBox8 = new mataf.dse.gui.MatafComboBox();
			comboBox8.setBounds(421, 339, 58, 19);
			comboBox8.setEnabled(false);
		}
		return comboBox8;
	}
	/**
	 * This method initializes comboBox9
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox9() {
		if(comboBox9 == null) {
			comboBox9 = new mataf.dse.gui.MatafComboBox();
			comboBox9.setBounds(421, 387, 58, 19);
			comboBox9.setEnabled(false);
		}
		return comboBox9;
	}
	/**
	 * This method initializes comboBox10
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox10() {
		if(comboBox10 == null) {
			comboBox10 = new mataf.dse.gui.MatafComboBox();
			comboBox10.setBounds(421, 411, 58, 19);
			comboBox10.setEnabled(false);
		}
		return comboBox10;
	}
	/**
	 * This method initializes comboBox11
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox11() {
		if(comboBox11 == null) {
			comboBox11 = new mataf.dse.gui.MatafComboBox();
			comboBox11.setBounds(421, 435, 58, 19);
			comboBox11.setEnabled(false);
		}
		return comboBox11;
	}
	/**
	 * This method initializes textField1
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField1() {
		if(textField1 == null) {
			textField1 = new mataf.types.MatafTextField();
			textField1.setBounds(329, 387, 81, 19);
			textField1.setEnabled(false);
		}
		return textField1;
	}
	/**
	 * This method initializes textField8
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField8() {
		if(textField8 == null) {
			textField8 = new mataf.types.MatafTextField();
			textField8.setBounds(329, 411, 81, 19);
			textField8.setEnabled(false);
		}
		return textField8;
	}
	/**
	 * This method initializes textField12
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField12() {
		if(textField12 == null) {
			textField12 = new mataf.types.MatafTextField();
			textField12.setBounds(15, 172, 190, 19);
			textField12.setEnabled(false);
		}
		return textField12;
	}
	/**
	 * This method initializes label15
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel15() {
		if(label15 == null) {
			label15 = new mataf.types.MatafLabel();
			label15.setBounds(129, 219, 74, 19);
			label15.setText("סכום הלוואה");
			label15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label15;
	}
	/**
	 * This method initializes textField13
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField13() {
		if(textField13 == null) {
			textField13 = new mataf.types.MatafTextField();
			textField13.setBounds(15, 219, 103, 19);
			textField13.setEnabled(false);
		}
		return textField13;
	}
	/**
	 * This method initializes label16
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel16() {
		if(label16 == null) {
			label16 = new mataf.types.MatafLabel();
			label16.setBounds(129, 244, 74, 19);
			label16.setText("חוב להיום");
			label16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label16;
	}
	/**
	 * This method initializes textField14
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField14() {
		if(textField14 == null) {
			textField14 = new mataf.types.MatafTextField();
			textField14.setBounds(15, 243, 103, 19);
			textField14.setEnabled(false);
		}
		return textField14;
	}
	/**
	 * This method initializes textField15
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField15() {
		if(textField15 == null) {
			textField15 = new mataf.types.MatafTextField();
			textField15.setBounds(15, 267, 190, 19);
			textField15.setEnabled(false);
		}
		return textField15;
	}
	/**
	 * This method initializes textField16
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField16() {
		if(textField16 == null) {
			textField16 = new mataf.types.MatafTextField();
			textField16.setBounds(15, 315, 190, 19);
			textField16.setEnabled(false);
		}
		return textField16;
	}
	/**
	 * This method initializes textField17
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField17() {
		if(textField17 == null) {
			textField17 = new mataf.types.MatafTextField();
			textField17.setBounds(15, 339, 190, 19);
			textField17.setEnabled(false);
		}
		return textField17;
	}
	/**
	 * This method initializes label17
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel17() {
		if(label17 == null) {
			label17 = new mataf.types.MatafLabel();
			label17.setBounds(129, 363, 74, 19);
			label17.setText("אחוז הצמדה");
			label17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label17;
	}
	/**
	 * This method initializes textField18
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField18() {
		if(textField18 == null) {
			textField18 = new mataf.types.MatafTextField();
			textField18.setBounds(15, 363, 103, 19);
			textField18.setEnabled(false);
		}
		return textField18;
	}
	/**
	 * This method initializes label18
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel18() {
		if(label18 == null) {
			label18 = new mataf.types.MatafLabel();
			label18.setBounds(129, 387, 74, 19);
			label18.setText("מדד");
			label18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label18;
	}
	/**
	 * This method initializes textField19
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField19() {
		if(textField19 == null) {
			textField19 = new mataf.types.MatafTextField();
			textField19.setBounds(15, 387, 103, 19);
			textField19.setEnabled(false);
		}
		return textField19;
	}
	/**
	 * This method initializes label19
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel19() {
		if(label19 == null) {
			label19 = new mataf.types.MatafLabel();
			label19.setBounds(277, 411, 41, 19);
			label19.setText("שער");
			label19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label19;
	}
	/**
	 * This method initializes textField20
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField20() {
		if(textField20 == null) {
			textField20 = new mataf.types.MatafTextField();
			textField20.setBounds(210, 411, 61, 19);
			textField20.setEnabled(false);
		}
		return textField20;
	}
	/**
	 * This method initializes label20
	 * 
	 * @return com.mataf.dse.gui.MatafLabel
	 */
	private mataf.types.MatafLabel getLabel20() {
		if(label20 == null) {
			label20 = new mataf.types.MatafLabel();
			label20.setBounds(162, 411, 41, 19);
			label20.setText("מטבע");
			label20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		}
		return label20;
	}
	/**
	 * This method initializes comboBox12
	 * 
	 * @return com.mataf.dse.gui.MatafComboBox
	 */
	private mataf.dse.gui.MatafComboBox getComboBox12() {
		if(comboBox12 == null) {
			comboBox12 = new mataf.dse.gui.MatafComboBox();
			comboBox12.setBounds(95, 411, 58, 19);
		}
		return comboBox12;
	}
	/**
	 * This method initializes textField21
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField21() {
		if(textField21 == null) {
			textField21 = new mataf.types.MatafTextField();
			textField21.setBounds(15, 411, 74, 19);
			textField21.setEnabled(false);
		}
		return textField21;
	}
	/**
	 * This method initializes textField22
	 * 
	 * @return com.mataf.dse.gui.MatafTextField
	 */
	private mataf.types.MatafTextField getTextField22() {
		if(textField22 == null) {
			textField22 = new mataf.types.MatafTextField();
			textField22.setBounds(15, 435, 190, 19);
			textField22.setEnabled(false);
		}
		return textField22;
	}
	
	/**
	 * @see com.ibm.dse.gui.DSEPanel#handleDSECoordinationEvent(DSECoordinationEvent)
	 */
//	public void handleDSECoordinationEvent(DSECoordinationEvent anEvent) {
//		System.out.println("*****	EVENT   *****");
//		System.out.println("getEventName = "+anEvent.getEventName());
//		System.out.println("getEventSourceType = "+anEvent.getEventSourceType());
//		System.out.println("getEventType = "+anEvent.getEventType());
//		System.out.println("getName = "+anEvent.getName());
//		System.out.println("getSource = "+anEvent.getSource());
//		System.out.println("getSourceName = "+anEvent.getSourceName());
//		super.handleDSECoordinationEvent(anEvent);
//	//	if (anEvent.getName().equals("")) {
//			
//	//	}
//	}
	/**
	 * @see com.ibm.dse.gui.DSECoordinatedPanel#handleOperationRepliedEvent(OperationRepliedEvent)
	 */
	public void handleOperationRepliedEvent(OperationRepliedEvent anEvent) {
		super.handleOperationRepliedEvent(anEvent);
		if (anEvent.getSource() instanceof ConfirmOpenHlClientOp) {
			System.out.println("I will set the panels on/off here...");
			
		}
	}


	/**
	 * This method initializes matafErrorList
	 * 
	 * @return com.mataf.dse.gui.MatafErrorList
	 */
	private SpErrorList getMatafErrorList() {
		if(matafErrorList == null) {
			matafErrorList = new SpErrorList();
			matafErrorList.setBounds(1, 505, 1015, 75);
			matafErrorList.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}
		return matafErrorList;
	}
	
	/**
	 * This method initializes pirteyCheshbon
	 * 
	 * @return mataf.hl.panels.PirteyCheshbon
	 */
	private mataf.hl.panels.PirteyCheshbon getPirteyCheshbon() {
		if(pirteyCheshbon == null) {
			pirteyCheshbon = new mataf.hl.panels.PirteyCheshbon();
			pirteyCheshbon.setLocation(0,35);
		}
		return pirteyCheshbon;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="7,-4"
