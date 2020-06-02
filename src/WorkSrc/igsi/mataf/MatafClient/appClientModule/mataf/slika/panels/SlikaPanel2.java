package mataf.slika.panels;

import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.event.ListSelectionListener;

import com.ibm.dse.gui.DSECoordinationEvent;
import com.ibm.dse.gui.NavigationParameters;
import com.mataf.dse.appl.OpenDesktop;

import mataf.slika.panels.handlers.SlikaPanel2Handler;
import mataf.types.MatafButton;
import mataf.types.MatafComboTextField;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.specific.BranchTableComboBoxButton;
import mataf.types.table.MatafTable;
import mataf.utils.FontFactory;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (11/08/2003 14:09:08).  
 */
public class SlikaPanel2 extends MatafEmbeddedPanel 
{
     private mataf.types.MatafTitle matafTitle = null;
     private mataf.types.MatafLabel matafLabel = null;
     private mataf.types.MatafLabel matafLabel2 = null;
     private mataf.types.MatafLabel matafLabel3 = null;
     private mataf.types.MatafLabel matafLabel4 = null;
     private mataf.types.MatafLabel matafLabel5 = null;
     private mataf.types.MatafLabel matafLabel6 = null;
     private mataf.types.MatafLabel matafLabel7 = null;
     private mataf.types.MatafLabel matafLabel8 = null;
     private mataf.types.table.MatafTable matafTable = null;
     private mataf.types.MatafScrollPane matafScrollPane = null;
     private mataf.types.MatafButton matafButton = null;
     private mataf.types.MatafButton matafButton2 = null;
     private mataf.types.MatafButton matafButton3 = null;
     private mataf.types.MatafToggleButton matafButton4 = null;
     private mataf.types.MatafButton matafButton5 = null;
     private mataf.types.MatafButton matafButton6 = null;
     private mataf.types.MatafButton matafButton7 = null;
     private mataf.types.MatafButton matafButton8 = null;
     private mataf.types.MatafLabel matafLabel9 = null;
     private mataf.types.MatafLabel matafLabel10 = null;
     private mataf.types.MatafLabel matafLabel11 = null;
     private mataf.types.MatafLabel matafLabel12 = null;
     private SlikaPanel2Handler eventHandler;
     private mataf.types.MatafLabel matafLabel13 = null;
     private mataf.types.MatafLabel matafLabel14 = null;
     private mataf.types.MatafLabel matafLabel15 = null;
     private mataf.types.MatafLabel matafLabel16 = null;
     private mataf.types.MatafLabel matafLabel17 = null;
     private mataf.types.MatafLabel matafLabel18 = null;
     private mataf.types.MatafLabel matafLabel19 = null;
     private mataf.types.MatafLabel matafLabel20 = null;
     private mataf.types.MatafButton matafButton10 = null;
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
        this.add(getMatafTitle(), null);
        this.add(getMatafLabel(), null);
        this.add(getMatafLabel2(), null);
        this.add(getMatafLabel3(), null);
        this.add(getMatafLabel4(), null);
        this.add(getMatafLabel5(), null);
        this.add(getMatafLabel6(), null);
        this.add(getMatafLabel7(), null);
        this.add(getMatafLabel8(), null);
        this.add(getMatafScrollPane(), null);
        this.add(getMatafButton(), null);
        this.add(getMatafButton2(), null);
        this.add(getMatafButton3(), null);
        this.add(getMatafButton4(), null);
        this.add(getMatafButton5(), null);
        this.add(getMatafButton6(), null);
        this.add(getMatafButton7(), null);
        this.add(getMatafButton8(), null);
        this.add(getMatafLabel9(), null);
        this.add(getMatafLabel10(), null);
        this.add(getMatafLabel11(), null);
        this.add(getMatafLabel12(), null);
        this.add(getMatafLabel13(), null);
        this.add(getMatafLabel14(), null);
        this.add(getMatafLabel15(), null);
        this.add(getMatafLabel16(), null);
        this.add(getMatafLabel17(), null);
        this.add(getMatafLabel18(), null);
        this.add(getMatafLabel19(), null);
        this.add(getMatafLabel20(), null);
        this.add(getMatafButton10(), null);
        this.setBounds(0, 0, 780, 450);
	}
	
	private SlikaPanel2Handler getEventHandler() {
		if (eventHandler==null)
			eventHandler = new SlikaPanel2Handler(this);
		return eventHandler;
	}
	
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	private mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();			
			matafTitle.setText("410 - הפקדת המחאות - הזרמת המחאות");
		}
		return matafTitle;
	}
	/**
	 * This method initializes 
	 * 
	 */
	public SlikaPanel2() {
		super();
		initialize();
		getMatafTable().changeSelection(0,0,false,false);
		setFocusTraversalPolicy(new SlikaPanel2FocusPolicy(this));
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel() 
	{
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(729, 30, 44, 16);
			matafLabel.setText("סניף :");
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
			matafLabel2.setBounds(693, 30, 30, 16);
			matafLabel2.setDataName("BranchIdInput");
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
			matafLabel3.setBounds(538, 30, 150, 16);
			matafLabel3.setDataName("BranchDescIdInput");
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
			matafLabel4.setBounds(150, 30, 123, 15);
			matafLabel4.setText("תאריך יום עסקים :");
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
			matafLabel5.setBounds(10, 30, 83, 15);
			matafLabel5.setDataName("trAsakimFormated");
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
			matafLabel6.setBounds(150, 55, 123, 15);
			matafLabel6.setText("חש' מוטב/החזרה :");
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
			matafLabel7.setBounds(96, 55, 45, 15);
			matafLabel7.setDataName("BeneficiaryAccountNumber");
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
			matafLabel8.setBounds(350, 105, 83, 16);
			matafLabel8.setText("פרטי חותם");
			matafLabel8.setFont(FontFactory.createFont("Tahoma", Font.BOLD, 14));
		}
		return matafLabel8;
	}
	/**
	 * This method initializes matafTable
	 * 
	 * @return mataf.types.table.MatafTable
	 */
	public mataf.types.table.MatafTable getMatafTable() {
		if(matafTable == null) {
			matafTable = new mataf.types.table.MatafTable();
			matafTable.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			matafTable.setEditable(true);
			
			matafTable.setDataNameForList("CZSS_T110_LIST");
			matafTable.getOurModel().addColumn(Long.class,"מספר המחאה","CZSS_T110_LINE.CH_MISPAR_CHEQ",true,10);
			matafTable.getOurModel().addColumn(Long.class,"בנק","CZSS_T110_LINE.CH_BANK_CHOTEM",true, 2);
			matafTable.getOurModel().addColumn(MatafComboTextField.class,"סניף","CZSS_T110_LINE.CH_SNIF_CHOTEM", true,3);
			matafTable.getOurModel().addColumn(Long.class,"ספרת ביקורת","CZSS_T110_LINE.CH_SNIF_S_B",true, 2);
			matafTable.getOurModel().addColumn(Long.class,"מספר חשבון","CZSS_T110_LINE.CH_CH_CHOTEM",true, 10);
			matafTable.getOurModel().addColumn(String.class,"סכום המחאה","CZSS_T110_LINE.CH_SCHUM_CHEQ", true);
			//matafTable.getOurModel().addColumn(Boolean.class,"תקין ?","CZSS_T110_LINE.CH_KOD_TKINUT", false, false);
			matafTable.getOurModel().addColumn(String.class,"תקינות 1","CZSS_T110_LINE.CH_MSG1");
			matafTable.getOurModel().addColumn(String.class,"תקינות 2","CZSS_T110_LINE.CH_MSG2");
			
			matafTable.setNumberOfHiddenColumns(2);
			matafTable.setSortable(false);
			
			// Attach operations to the columns :
			matafTable.setNavigationParametersForColumn(1,new com.ibm.dse.gui.NavigationParameters("processor",0,"checkBankNumberClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTable.setNavigationParametersForColumn(2,new com.ibm.dse.gui.NavigationParameters("processor",0,"checkBranchIdAccording2bankClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTable.setNavigationParametersForColumn(4,new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAccountNumber4ChequeClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTable.setNavigationParametersForColumn(5,new com.ibm.dse.gui.NavigationParameters("processor",0,"checkAmmountOfCheckClientOp",0,"","","","","","",0,0,0,0,false,false));
			
			// Special configuration for the snif column.			
			MatafComboTextField mctf = 
				(MatafComboTextField)matafTable.getTextEditorAtColumn(2);

			// Create the snif list table.
			BranchTableComboBoxButton btcbb = new BranchTableComboBoxButton();
			mctf.setTable(btcbb.getTable());
			
			matafTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() 
			{
				/**
				 * Method synchronizes the CH_MSG1 and CH_MSG2 when changing row selection.
				 */
				public void valueChanged(ListSelectionEvent e) 
				{
					if(matafTable.getOurModel().getRowCount()==0)
						return;
			
					DefaultListSelectionModel dlsm = (DefaultListSelectionModel)e.getSource();
					int selectedRow = dlsm.getAnchorSelectionIndex();

					getMatafLabel19().setText((String)matafTable.getOurModel().getValueAt(selectedRow, 6));
					getMatafLabel20().setText((String)matafTable.getOurModel().getValueAt(selectedRow, 7));
				}
			});
			
			/**
			 * Allow the inserting of new data to the table through an automatic
			 * cheque reader.If the cheque-reader toggle button is selected
			 * the new line will be automatically fed from the reader, 
			 * and putting the focus on the amount column.
			 * 
			 * PENDING : Maybe join this code with the table's infra-structure.
			 */
			matafTable.getModel().addTableModelListener(new TableModelListener()
			{
				public void tableChanged(TableModelEvent e)
				{
					if(e.getType()==TableModelEvent.INSERT)
					{
						if(getMatafButton4().isSelected())
							activateCheckReader();
					}
				}
			});
		}
		return matafTable;				
	}
	
	/** 
	 * PENDING : Maybe join this code with the table's infra-structure.
	 */
	private void activateCheckReader()
	{
		// Activate operation.
		NavigationParameters navPar = new com.ibm.dse.gui.NavigationParameters("processor",0,"readCheckDetailsClientOp",0,"","","","","","",0,0,0,0,false,false);
		getMatafButton().fireCoordinationEvent(navPar.generateCoordinationEvent("Execute_Operation", this));
	}
	/**
	 * This method initializes matafScrollPane
	 * 
	 * @return mataf.types.MatafScrollPane
	 */
	private mataf.types.MatafScrollPane getMatafScrollPane() {
		if(matafScrollPane == null) {
			matafScrollPane = new mataf.types.MatafScrollPane();
			//matafScrollPane.setFocusCycleRoot(true);
			matafScrollPane.setViewportView(getMatafTable());
			matafScrollPane.setBounds(58, 130, 655, 200);
			matafScrollPane.setDataName("ChecksTable");
		}
		return matafScrollPane;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(10, 425, 50, 20);
			matafButton.setText("צא");
			matafButton.setType(MatafButton.CLOSE_VIEW);
			//matafButton.setViewsToCloseAlso(new String[]{"slikaView"});
			matafButton.setDataName("CloseButton2");
		}
		return matafButton;
	}
	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton2() {
		if(matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(70, 425, 80, 20);
			matafButton2.setActionCommand("goBackBtn");
			matafButton2.setText("<< חזור");
			matafButton2.setType(MatafButton.PREVIOUS_VIEW);
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafButton3
	 * 
	 * @return mataf.types.MatafButton
	 */
	private mataf.types.MatafButton getMatafButton3() {
		if(matafButton3 == null) {
			matafButton3 = new mataf.types.MatafButton();
			matafButton3.setBounds(270, 425, 100, 20);
			matafButton3.setText("ודא נתונים");
			matafButton3.setDataName("VadeNetunimButton");
			matafButton3.setType("Execute_Operation");
			matafButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"sendCheckDetailsClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton3;
	}
	
	private mataf.types.MatafToggleButton getMatafButton4() {
		if(matafButton4 == null) {
			matafButton4 = new mataf.types.MatafToggleButton();
			matafButton4.setBounds(58, 337, 168, 20);
			matafButton4.setText("הפעל קורא המחאות");
			matafButton4.setDataName("CheckReaderButton");
			matafButton4.addItemListener(new ItemListener()
			{
				public void itemStateChanged(ItemEvent e)
				{
					matafButton4.setText(matafButton4.isSelected() ? 
											"קורא המחאות פעיל" :
											"הפעל קורא המחאות");
				
					if(matafButton4.isSelected())
						activateCheckReader();
				}
			});
		}
		return matafButton4;
	}
	
	private mataf.types.MatafButton getMatafButton5() {
		if(matafButton5 == null) {
			matafButton5 = new mataf.types.MatafButton();
			matafButton5.setBounds(375, 425, 60, 20);
			matafButton5.setText("תקן");
			matafButton5.setDataName("TakenHamchaotButton");
			matafButton5.setActionCommand("TakenHamchaotBtn");
			matafButton5.addActionListener(getEventHandler());
		}
		return matafButton5;
	}
	
	private mataf.types.MatafButton getMatafButton6() {
		if(matafButton6 == null) {
			matafButton6 = new mataf.types.MatafButton();
			matafButton6.setBounds(440, 425, 70, 20);
			matafButton6.setText("קלוט");
			matafButton6.setDataName("KlotHamchaotButton");
			matafButton6.setType("Execute_Operation");
			matafButton6.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"acceptSlClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton6;
	}
	
	private mataf.types.MatafButton getMatafButton7() {
		if(matafButton7 == null) {
			matafButton7 = new mataf.types.MatafButton();
			matafButton7.setBounds(515, 425, 105, 20);
			matafButton7.setText("אישור מנהל");
			matafButton7.setDataName("IshurMenahelButton");
			matafButton7.setType("Next_View");
			matafButton7.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"",0,"LocalOverride","mataf.override.LocalOverrideView","","","","",0,0,0,0,false,false));			
		}
		return matafButton7;
	}
	
	private mataf.types.MatafButton getMatafButton8() {
		if(matafButton8 == null) {
			matafButton8 = new mataf.types.MatafButton();
			matafButton8.setBounds(625, 425, 145, 20);
			matafButton8.setText("אישור מנהל מרוחק");
			matafButton8.setDataName("IshurMenahelMeruchakButton");
			matafButton8.setType(MatafButton.NEXT_VIEW);
			matafButton8.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",3,"SendOverride","mataf.override.SendOverrideView","","","","",0,0,0,0,false,false));
		}
		return matafButton8;
	}
	
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel9() {
		if(matafLabel9 == null) {
			matafLabel9 = new mataf.types.MatafLabel();
			matafLabel9.setBounds(16, 55, 26, 15);
			matafLabel9.setDataName("BeneficiaryBranchId");
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
			matafLabel10.setBounds(56, 55, 26, 15);
			matafLabel10.setDataName("BeneficiaryAccountType");
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
			matafLabel11.setBounds(84, 55, 10, 15);
			matafLabel11.setText("-");
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
			matafLabel12.setBounds(44, 55, 10, 15);
			matafLabel12.setText("-");
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
			matafLabel13.setBounds(729, 55, 44, 16);
			matafLabel13.setText("חשבון :");
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
			matafLabel14.setBounds(679, 55, 44, 16);
			matafLabel14.setDataName("AccountNumber");
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
			matafLabel15.setBounds(645, 55, 30, 16);
			matafLabel15.setDataName("AccountType");
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
			matafLabel16.setBounds(537, 55, 91, 16);
			matafLabel16.setDataName("AccountBalance.OwnerName");
		}
		return matafLabel16;
	}
	/**
	 * This method initializes matafLabel17
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel17() {
		if(matafLabel17 == null) {
			matafLabel17 = new mataf.types.MatafLabel();
			matafLabel17.setBounds(714, 355, 59, 16);
			matafLabel17.setText("תקינות 1 :");
		}
		return matafLabel17;
	}
	/**
	 * This method initializes matafLabel18
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel18() {
		if(matafLabel18 == null) {
			matafLabel18 = new mataf.types.MatafLabel();
			matafLabel18.setBounds(714, 380, 59, 16);
			matafLabel18.setText("תקינות 2 :");
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
			matafLabel19.setBounds(308, 355, 405, 16);
	
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
			matafLabel20.setBounds(308, 380, 405, 16);
		}
		return matafLabel20;
	}
	/**
	 * This method initializes matafButton10
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton10() {
		if(matafButton10 == null) {
			matafButton10 = new mataf.types.MatafButton();
			matafButton10.setBounds(625, 400, 5, 5);
			matafButton10.setType("Next_View");
			matafButton10.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"initHalbanatHonClientOp",3,"halbanatHonView","mataf.halbanathon.views.HalbanatHonView","","","","TLAS_HAZRAMA_T410",0,0,0,0,false,false));
			matafButton10.setActionCommand("open_halbanat_hon");
			matafButton10.setVisible(false);
		}
		return matafButton10;
	}
}
