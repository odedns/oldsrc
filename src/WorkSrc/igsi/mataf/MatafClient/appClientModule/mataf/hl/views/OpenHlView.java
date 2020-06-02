//package com.mataf.hl.views;
//
//import mataf.desktop.views.MatafClientView;
//import mataf.types.textfields.MatafNumericField;
//
//import com.ibm.dse.base.OperationRepliedEvent;
//import com.mataf.operations.ConfirmOpenHlClientOp;
//
///**
// * @author ronenk
// *
// * To change this generated comment edit the template variable "typecomment":
// * Window>Preferences>Java>Templates.
// * To enable and disable the creation of type comments go to
// * Window>Preferences>Java>Code Generation.
// */
//public class OpenHlView extends MatafClientView {
//
//     
//     private MatafNumericField textField4 = null;
//     private MatafNumericField textField5 = null;
//     private mataf.hl.panels.PirteyCheshbon pirteyCheshbon = null;
//	/**
//	 * This method initializes 
//	 * 
//	 */
//	public OpenHlView() {
//		super();
////		controller = new OpenHlViewController(this);
//		initialize();
//				
//	}
//	/**
//	 * This method initializes this
//	 * 
//	 * @return void
//	 */
//	private void initialize() {
///*        this.add(getLabel5(), null);
//        this.add(getLabel6(), null);
//        this.add(getLabel7(), null);
//        this.add(getLabel8(), null);
//        this.add(getLabel9(), null);
//        this.add(getLabel10(), null);
//        this.add(getLabel11(), null);
//        this.add(getLabel12(), null);
//        this.add(getLabel13(), null);
//        this.add(getLabel14(), null);
//        this.add(getTextField4(), null);
//        this.add(getTextField5(), null);
//        this.add(getTextField6(), null);
//        this.add(getComboBox3(), null);
//        this.add(getComboBox4(), null);
//        this.add(getComboBox5(), null);
//        this.add(getTextField7(), null);
//        this.add(getComboBox7(), null);
//        this.add(getComboBox8(), null);
//        this.add(getComboBox9(), null);
//        this.add(getComboBox10(), null);
//        this.add(getComboBox11(), null);
//        this.add(getTextField1(), null);
//        this.add(getTextField8(), null);
//        this.add(getTextField12(), null);
//        this.add(getLabel15(), null);
//        this.add(getTextField13(), null);
//        this.add(getLabel16(), null);
//        this.add(getTextField14(), null);
//        this.add(getTextField15(), null);
//        this.add(getTextField16(), null);
//        this.add(getTextField17(), null);
//        this.add(getLabel17(), null);
//        this.add(getTextField18(), null);
//        this.add(getLabel18(), null);
//        this.add(getTextField19(), null);
//        this.add(getLabel19(), null);
//        this.add(getTextField20(), null);
//        this.add(getLabel20(), null);
//        this.add(getComboBox12(), null);
//        this.add(getTextField21(), null);
//        this.add(getTextField22(), null);       
//        this.add(getMatafErrorList(), null);*/
//        this.addTransactionView(getPirteyCheshbon());
//        this.setName("OpenHlView");
//        this.setContextName("openHlViewCtx");
////        this.setOperationName("initOpenHlClientOp");
////        this.setDisableWhileOperationRunning(true);
////        this.setExecuteWhenOpen(true);
//        this.setViewName("OpenHlView");
//        this.setFocusCycleRoot(true);
////		setFocusTraversalPolicy(new OpenHlfocusTraversal(this));
//		this.setTitle("פתיחת הלוואה");
//	}
//	
//	/**
//	 * This method initializes textField4
//	 * 
//	 * @return com.mataf.dse.gui.MatafTextField
//	 */
//	public MatafNumericField getTextField4() {
//		if(textField4 == null) {
//			textField4 = new MatafNumericField();
//			textField4.setBounds(388, 219, 91, 19);
//			textField4.setDataName("schum");
//			textField4.setEnabled(false);
//		}
//		return textField4;
//	}
//	/**
//	 * This method initializes textField5
//	 * 
//	 * @return com.mataf.dse.gui.MatafTextField
//	 */
//	public MatafNumericField getTextField5() {
//		if(textField5 == null) {
//			textField5 = new MatafNumericField();
//			textField5.setBounds(388, 243, 91, 19);
//			textField5.setEnabled(false);
//		}
//		return textField5;
//	}
//
//
//	/**
//	 * @see com.ibm.dse.gui.DSECoordinatedPanel#handleOperationRepliedEvent(OperationRepliedEvent)
//	 */
//	public void handleOperationRepliedEvent(OperationRepliedEvent anEvent) {
//		super.handleOperationRepliedEvent(anEvent);
//		if (anEvent.getSource() instanceof ConfirmOpenHlClientOp) {
//			System.out.println("I will set the panels on/off here...");
//			
//		}
//	}
//	
//	/**
//	 * This method initializes pirteyCheshbon
//	 * 
//	 * @return mataf.hl.panels.PirteyCheshbon
//	 */
//	private mataf.hl.panels.PirteyCheshbon getPirteyCheshbon() {
//		if(pirteyCheshbon == null) {
//			pirteyCheshbon = new mataf.hl.panels.PirteyCheshbon();
//			pirteyCheshbon.setLocation(0,35);
//		}
//		return pirteyCheshbon;
//	}
//}  //  @jve:visual-info  decl-index=0 visual-constraint="7,-4"
