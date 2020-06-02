package mataf.slika.panels;

import java.awt.LayoutManager;

import com.ibm.dse.gui.FloatConverter;

import mataf.types.MatafButtonGroup;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafScrollPane;

/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class ConclusionBasePanel extends MatafEmbeddedPanel
{

     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafLabel matafLabel3 = null;
     private mataf.types.MatafLabel matafLabel4 = null;
     private mataf.types.MatafLabel matafLabel5 = null;
     private mataf.types.MatafLabel matafLabel6 = null;
     private mataf.types.MatafLabel matafLabel7 = null;
     private mataf.types.MatafLabel matafLabel8 = null;
     private mataf.types.MatafLabel matafLabel9 = null;
     private mataf.types.MatafLabel matafLabel10 = null;
     private mataf.types.MatafLabel matafLabel11 = null;
     private mataf.types.MatafLabel matafLabel12 = null;
     private mataf.types.MatafLabel matafLabel13 = null;
     private mataf.types.MatafLabel matafLabel14 = null;
     private mataf.types.MatafLabel matafLabel15 = null;
     private mataf.types.MatafLabel matafLabel16 = null;
     private mataf.types.MatafLabel matafLabel18 = null;
     private mataf.types.MatafLabel matafLabel19 = null;
     private mataf.types.MatafLabel matafLabel20 = null;
     private mataf.types.MatafLabel matafLabel21 = null;
     private mataf.types.MatafLabel matafLabel22 = null;
     private mataf.types.MatafScrollPane matafScrollPane = null;
     private mataf.types.table.MatafTable matafTable = null;
     private MatafScrollPane jScrollPane = null;
     private mataf.types.table.MatafTable matafTable2 = null;
     private mataf.types.MatafLabel matafLabel23 = null;
     private mataf.types.MatafLabel matafLabel24 = null;
     private mataf.types.MatafLabel matafLabel25 = null;
     private mataf.types.MatafLabel matafLabel26 = null;
	private mataf.types.MatafRadioButton matafRadioButton = null;
	private mataf.types.MatafRadioButton matafRadioButton1 = null;
	
	private MatafButtonGroup group = null;
	/**
	 * Constructor for SlikaConclusionPanel.
	 * @param layout
	 */
	public ConclusionBasePanel(LayoutManager layout)
	{
		super(layout);
	}

	/**
	 * Constructor for SlikaConclusionPanel.
	 * @param arg0
	 * @param arg1
	 */
	public ConclusionBasePanel(LayoutManager arg0, boolean arg1)
	{
		super(arg0, arg1);
	}

	/**
	 * Constructor for SlikaConclusionPanel.
	 * @param arg0
	 */
	public ConclusionBasePanel(boolean arg0)
	{
		super(arg0);
	}

	/**
	 * Constructor for SlikaConclusionPanel.
	 */
	public ConclusionBasePanel()
	{
		super();
		group= new MatafButtonGroup();
		group.setDataName("DepositSource");
		group.add(getMatafRadioButton());		
		group.add(getMatafRadioButton1());
		
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafLabel(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafLabel4(), null);
        this.add(getMatafLabel5(), null);
        this.add(getMatafLabel6(), null);
        this.add(getMatafLabel7(), null);
        this.add(getMatafLabel8(), null);
        this.add(getMatafLabel9(), null);
        this.add(getMatafLabel10(), null);
        this.add(getMatafLabel11(), null);
        this.add(getMatafLabel12(), null);
        this.add(getMatafLabel13(), null);
        this.add(getMatafLabel14(), null);
        this.add(getMatafLabel15(), null);
        this.add(getMatafLabel16(), null);
        this.add(getMatafLabel18(), null);
        this.add(getMatafLabel19(), null);
        this.add(getMatafLabel20(), null);
        this.add(getMatafLabel21(), null);
        this.add(getMatafLabel22(), null);
        this.add(getMatafScrollPane(), null);
        this.add(getJScrollPane(), null);
        this.add(getMatafLabel23(), null);
        this.add(getMatafLabel24(), null);
        this.add(getMatafLabel25(), null);
        this.add(getMatafLabel26(), null);
        this.add(getMatafRadioButton(), null);
        this.add(getMatafRadioButton1(), null);
        this.setBounds(0, 0, 780, 300);
			
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(642, 10, 89, 21);
			matafLabel.setText("סניף:");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel2() {
		if(matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(636, 40, 95, 21);
			matafLabel2.setText("מס' וסוג חשבון:");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel3() {
		if(matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(634, 70, 97, 21);
			matafLabel3.setText("חשבון להחזרה:");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes matafLabel4
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel4() {
		if(matafLabel4 == null) {
			matafLabel4 = new mataf.types.MatafLabel();
			matafLabel4.setBounds(625, 100, 106, 21);
			matafLabel4.setText("סה\"כ סכום מסוכם:");
		}
		return matafLabel4;
	}
	/**
	 * This method initializes matafLabel5
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel5() {
		if(matafLabel5 == null) {
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(626, 130, 105, 21);
			matafLabel5.setText("מקור הפקדה:");
		}
		return matafLabel5;
	}
	/**
	 * This method initializes matafLabel6
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel6() {
		if(matafLabel6 == null) {
			matafLabel6 = new mataf.types.MatafLabel();
			matafLabel6.setBounds(633, 190, 98, 21);
			matafLabel6.setText("מס' המחאות");
		}
		return matafLabel6;
	}
	/**
	 * This method initializes matafLabel7
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel7() {
		if(matafLabel7 == null) {
			matafLabel7 = new mataf.types.MatafLabel();
			matafLabel7.setBounds(629, 220, 102, 21);
			matafLabel7.setText("אסמכתא:");
		}
		return matafLabel7;
	}
	/**
	 * This method initializes matafLabel8
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel8() {
		if(matafLabel8 == null) {
			matafLabel8 = new mataf.types.MatafLabel();
			matafLabel8.setBounds(634, 250, 97, 21);
			matafLabel8.setText("פעולת המשך:");
		}
		return matafLabel8;
	}
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel9() {
		if(matafLabel9 == null) {
			matafLabel9 = new mataf.types.MatafLabel();
			matafLabel9.setBounds(574, 10, 44, 21);
			matafLabel9.setText("");
			matafLabel9.setDataName("BranchIdInput");
		}
		return matafLabel9;
	}
	/**
	 * This method initializes matafLabel10
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel10() {
		if(matafLabel10 == null) {
			matafLabel10 = new mataf.types.MatafLabel();
			matafLabel10.setBounds(457, 10, 112, 21);
			matafLabel10.setText("");
			matafLabel10.setDataName("BranchDescIdInput");
		}
		return matafLabel10;
	}
	/**
	 * This method initializes matafLabel11
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel11() {
		if(matafLabel11 == null) {
			matafLabel11 = new mataf.types.MatafLabel();
			matafLabel11.setBounds(534, 40, 84, 21);
			matafLabel11.setText("");
			matafLabel11.setDataName("AccountNumber");
		}
		return matafLabel11;
	}
	/**
	 * This method initializes matafLabel12
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel12() {
		if(matafLabel12 == null) {
			matafLabel12 = new mataf.types.MatafLabel();
			matafLabel12.setBounds(457, 40, 71, 21);
			matafLabel12.setText("");
			matafLabel12.setDataName("AccountType");
		}
		return matafLabel12;
	}
	/**
	 * This method initializes matafLabel13
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel13() {
		if(matafLabel13 == null) {
			matafLabel13 = new mataf.types.MatafLabel();
			matafLabel13.setBounds(551, 70, 67, 21);
			matafLabel13.setText("");
			matafLabel13.setDataName("BeneficiaryAccountNumber");
		}
		return matafLabel13;
	}
	/**
	 * This method initializes matafLabel14
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel14() {
		if(matafLabel14 == null) {
			matafLabel14 = new mataf.types.MatafLabel();
			matafLabel14.setBounds(512, 70, 34, 21);
			matafLabel14.setText("");
			matafLabel14.setDataName("BeneficiaryAccountType");
		}
		return matafLabel14;
	}
	/**
	 * This method initializes matafLabel15
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel15() {
		if(matafLabel15 == null) {
			matafLabel15 = new mataf.types.MatafLabel();
			matafLabel15.setBounds(472, 70, 35, 21);
			matafLabel15.setText("");
			matafLabel15.setDataName("BeneficiaryBranchId");
		}
		return matafLabel15;
	}
	/**
	 * This method initializes matafLabel16
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel16() {
		if(matafLabel16 == null) {
			matafLabel16 = new mataf.types.MatafLabel();
			matafLabel16.setBounds(475, 100, 143, 21);
			matafLabel16.setText("");
			matafLabel16.setDataName("TotalAmount");
		}
		return matafLabel16;
	}
	/**
	 * This method initializes matafLabel18
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel18() {
		if(matafLabel18 == null) {
			matafLabel18 = new mataf.types.MatafLabel();
			matafLabel18.setBounds(627, 160, 104, 21);
			matafLabel18.setText("החזר ע\"ח הלוואה:");
		}
		return matafLabel18;
	}
	/**
	 * This method initializes matafLabel19
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel19() {
		if(matafLabel19 == null) {
			matafLabel19 = new mataf.types.MatafLabel();
			matafLabel19.setBounds(475, 160, 143, 21);
			matafLabel19.setText("");
		}
		return matafLabel19;
	}
	/**
	 * This method initializes matafLabel20
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel20() {
		if(matafLabel20 == null) {
			matafLabel20 = new mataf.types.MatafLabel();
			matafLabel20.setBounds(509, 190, 109, 21);
			matafLabel20.setText("");
			matafLabel20.setDataName("NumberOfCheques");
		}
		return matafLabel20;
	}
	/**
	 * This method initializes matafLabel21
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel21() {
		if(matafLabel21 == null) {
			matafLabel21 = new mataf.types.MatafLabel();
			matafLabel21.setBounds(510, 220, 108, 21);
			matafLabel21.setText("");
			matafLabel21.setDataName("Asmachta");
		}
		return matafLabel21;
	}
	/**
	 * This method initializes matafLabel22
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel22() {
		if(matafLabel22 == null) {
			matafLabel22 = new mataf.types.MatafLabel();
			matafLabel22.setBounds(562, 250, 56, 21);
			matafLabel22.setText("");
			matafLabel22.setDataName("ContinueAction");
		}
		return matafLabel22;
	}
	/**
	 * This method initializes matafScrollPane
	 * 
	 * @return mataf.types.MatafScrollPane
	 */
	private mataf.types.MatafScrollPane getMatafScrollPane() {
		if(matafScrollPane == null) {
			matafScrollPane = new mataf.types.MatafScrollPane();
			matafScrollPane.setViewportView(getMatafTable());
			matafScrollPane.setBounds(7, 10, 446, 140);
		}
		return matafScrollPane;
	}
	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	private mataf.types.table.MatafTable getMatafTable() {
		if(matafTable == null) {
			matafTable = new mataf.types.table.MatafTable();
			matafTable.setEditable(false);
			matafTable.setColumnTypes(new Class[]{Long.class,Long.class,Long.class,Long.class,Long.class,String.class});
			matafTable.setDataNameAndColumns((((((((new com.ibm.dse.gui.VectorEditor(7)).setElemAt("CZSS_T110_LIST",0)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_MISPAR_CHEQ","מספר המחאה",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,100),1)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_BANK_CHOTEM","בנק",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,30),2)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_SNIF_CHOTEM","סניף",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,40),3)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_SNIF_S_B","סוג",null,false,true,false,30),4)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_CH_CHOTEM","מספר חשבון",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,100),5)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("CZSS_T110_LINE.CH_SCHUM_CHEQ","סכום המחאה",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,150),6)
			
			);
		}
		return matafTable;
	}
	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private MatafScrollPane getJScrollPane() {
		if(jScrollPane == null) {
			jScrollPane = new MatafScrollPane();
			jScrollPane.setViewportView(getMatafTable2());
			jScrollPane.setBounds(7, 160, 446, 84);
			jScrollPane.setDataName("HalvaotTable");
		}
		return jScrollPane;
	}
	/**
	 * This method initializes matafTable2
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	private mataf.types.table.MatafTable getMatafTable2() {
		if(matafTable2 == null) {
			matafTable2 = new mataf.types.table.MatafTable();
			matafTable2.setEditable(false);
			/*matafTable2.setColumnTypes(new Class[]{Long.class,Double.class});
			matafTable2.setDataNameAndColumns((((new com.ibm.dse.gui.VectorEditor(3)).setElemAt("LoansList",0)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("LoanRecord.loanNumber","מספר הלוואה",new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"),false,true,false,150),1)
			).setElemAt(new com.ibm.dse.gui.ColumnFormatter("LoanRecord.loanAmount","סכום",new FloatConverter("הערך אינו נומרי", "", '.', 2),false,true,false,150),2));
			*/
			
			matafTable2.setDataNameForList("LoansList");
			matafTable2.getOurModel().addColumn(Long.class, "מספר הלוואה", "LoanRecord.loanNumber");
			matafTable2.getOurModel().addColumn(String.class, "סכום", "LoanRecord.loanAmount");
			
			matafTable2.setSortable(false);
		}
		return matafTable2;
	}
	/**
	 * This method initializes matafLabel23
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel23() {
		if(matafLabel23 == null) {
			matafLabel23 = new mataf.types.MatafLabel();
			matafLabel23.setBounds(7, 250, 109, 21);
			matafLabel23.setText("");
			matafLabel23.setDataName("HotsaotRishoniotLoanAmmount");
		}
		return matafLabel23;
	}
	/**
	 * This method initializes matafLabel24
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel24() {
		if(matafLabel24 == null) {
			matafLabel24 = new mataf.types.MatafLabel();
			matafLabel24.setBounds(124, 250, 109, 21);
			matafLabel24.setText("");
			matafLabel24.setDataName("HotsaotRishoniotLoanAmmountTitle");
		}
		return matafLabel24;
	}
	/**
	 * This method initializes matafLabel25
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel25() {
		if(matafLabel25 == null) {
			matafLabel25 = new mataf.types.MatafLabel();
			matafLabel25.setBounds(241, 250, 109, 21);
			matafLabel25.setText("");
			matafLabel25.setDataName("HotsaotRishoniotLoanNumber");
		}
		return matafLabel25;
	}
	/**
	 * This method initializes matafLabel26
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel26() {
		if(matafLabel26 == null) {
			matafLabel26 = new mataf.types.MatafLabel();
			matafLabel26.setBounds(358, 250, 109, 21);
			matafLabel26.setText("");
			matafLabel26.setDataName("HotsaotRishoniotLoanNumberTitle");
		}
		return matafLabel26;
	}
	/**
	 * This method initializes matafRadioButton
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	private mataf.types.MatafRadioButton getMatafRadioButton() {
		if(matafRadioButton == null) {
			matafRadioButton = new mataf.types.MatafRadioButton("פקיד", true);
			matafRadioButton.setBounds(556, 130, 62, 21);
			matafRadioButton.setValue("0");
			matafRadioButton.setEnabled(false);
		}
		return matafRadioButton;
	}
	/**
	 * This method initializes matafRadioButton1
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	private mataf.types.MatafRadioButton getMatafRadioButton1() {
		if(matafRadioButton1 == null) {
			matafRadioButton1 = new mataf.types.MatafRadioButton();
			matafRadioButton1.setBounds(460, 130, 88, 21);
			matafRadioButton1.setValue("1");
			matafRadioButton1.setText("שרות עצמי");
			matafRadioButton1.setEnabled(false);
		}
		return matafRadioButton1;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="-1,2"
