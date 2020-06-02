package mataf.halbanathon.panels;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEClientOperation;
import com.ibm.dse.base.DSEException;
import com.mataf.dse.appl.OpenDesktop;

import mataf.types.MatafButton;
import mataf.types.MatafButtonGroup;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.specific.AccountTypeTableComboBoxButton;
import mataf.types.specific.BranchTableComboBoxButton;
import mataf.types.table.MatafTable;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (07/10/2003 16:49:56).  
 */
public class SugLakoachPanel extends MatafEmbeddedPanel {

	public mataf.types.MatafTitle matafTitle = null;
	public mataf.types.MatafLabel matafLabel = null;
	public mataf.types.MatafLabel matafLabel2 = null;
	public mataf.types.MatafLabel matafLabel3 = null;
	public mataf.types.MatafLabel matafLabel4 = null;
	public mataf.types.MatafLabel matafLabel5 = null;
	public mataf.types.MatafLabel matafLabel6 = null;
	public mataf.types.MatafLabel matafLabel7 = null;
	public mataf.types.MatafLabel matafLabel8 = null;
	public mataf.types.MatafLabel matafLabel9 = null;
	public mataf.types.MatafLabel matafLabel10 = null;
	public mataf.types.MatafLabel matafLabel11 = null;
	public mataf.types.MatafLabel matafLabel12 = null;
	public mataf.types.MatafLabel matafLabel13 = null;
	public mataf.types.MatafLabel matafLabel14 = null;
	public mataf.types.MatafLabel matafLabel15 = null;
	public mataf.types.MatafLabel matafLabel16 = null;
	public mataf.types.MatafLabel matafLabel17 = null;
	public mataf.types.MatafLabel matafLabel18 = null;
	public mataf.types.MatafLabel matafLabel19 = null;
	public mataf.types.MatafRadioButton matafRadioButton = null;
	public mataf.types.MatafRadioButton matafRadioButton2 = null;
	public mataf.types.MatafRadioButton matafRadioButton3 = null;
	public mataf.types.MatafRadioButton matafRadioButton4 = null;
	public mataf.types.MatafButton matafButton = null;
	public MatafButtonGroup group1 = null;
	public MatafButtonGroup group2 = null;
	public mataf.types.MatafButton matafButton2 = null;
	public mataf.types.MatafButton matafButton3 = null;
	public mataf.types.MatafButton matafButton4 = null;
	public mataf.types.MatafLabel matafLabel20 = null;
	public mataf.types.MatafLabel matafLabel21 = null;
	public mataf.types.MatafLabel matafLabel22 = null;
	public mataf.types.MatafButton matafButton5 = null;
	public BranchTableComboBoxButton snifTableComboBox = null;

	private AccountTypeTableComboBoxButton accountTypeTableComboBox = null;
	private mataf.types.MatafTextField matafTextField = null;
     private mataf.types.MatafComboTextField matafComboTextField = null;
     private mataf.types.MatafLabel matafLabel23 = null;
     private mataf.types.MatafComboTextField matafComboTextField2 = null;
     private mataf.types.MatafLabel matafLabel24 = null;
	/**
	 * Constructor for SugLakoachPanel.
	 * @param layout
	 */
	public SugLakoachPanel(LayoutManager layout) {
		super(layout);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 * @param arg1
	 */
	public SugLakoachPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 */
	public SugLakoachPanel(boolean arg0) {
		super(arg0);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 */
	public SugLakoachPanel() {
		super();
		initSpButtonGroupObjects();
		initialize();
	}

	public void initSpButtonGroupObjects() {
		group1 = new MatafButtonGroup();
		group1.setDataName("HASS_LAKOACH_SUG.HA_SUG_LAKOACH");
		group1.add(getMatafRadioButton());
		group1.add(getMatafRadioButton2());

		group2 = new MatafButtonGroup();
		group2.setDataName("HASS_LAKOACH_SUG.SH_SIBAT_DIVUACH");
		group2.add(getMatafRadioButton3());
		group2.add(getMatafRadioButton4());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void initialize() {
		this.add(getMatafTitle(), null);
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
		this.add(getMatafLabel17(), null);
		this.add(getMatafLabel18(), null);
		this.add(getMatafLabel19(), null);
		this.add(getMatafRadioButton(), null);
		this.add(getMatafRadioButton2(), null);
		this.add(getMatafRadioButton3(), null);
		this.add(getMatafRadioButton4(), null);
		this.add(getMatafButton(), null);
		this.add(getMatafButton2(), null);
		this.add(getMatafButton3(), null);
		this.add(getMatafButton4(), null);
		this.add(getMatafLabel20(), null);
		this.add(getMatafLabel21(), null);
		this.add(getMatafLabel22(), null);
		this.add(getMatafButton5(), null);
		this.add(getSnifTableComboBox(), null);
		this.add(getAccountTypeTableComboBox(), null);
		this.add(getMatafTextField(), null);
		this.add(getMatafComboTextField(), null);
		this.add(getMatafLabel23(), null);
		this.add(getMatafComboTextField2(), null);
		this.add(getMatafLabel24(), null);
		this.setBounds(0, 0, 780, 450);

	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	public mataf.types.MatafTitle getMatafTitle() {
		if (matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("פרטי מבצע הפעולה - קליטת סוג לקוח");
		}
		return matafTitle;
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel() {
		if (matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(688, 30, 85, 20);
			matafLabel.setText("פעולה :");
		}
		return matafLabel;
	}
	/**
	 * This method initializes matafLabel2
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel2() {
		if (matafLabel2 == null) {
			matafLabel2 = new mataf.types.MatafLabel();
			matafLabel2.setBounds(688, 55, 85, 20);
			matafLabel2.setText("בנק :");
		}
		return matafLabel2;
	}
	/**
	 * This method initializes matafLabel3
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel3() {
		if (matafLabel3 == null) {
			matafLabel3 = new mataf.types.MatafLabel();
			matafLabel3.setBounds(688, 80, 85, 20);
			matafLabel3.setText("סניף :");
		}
		return matafLabel3;
	}
	/**
	 * This method initializes matafLabel4
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel4() {
		if (matafLabel4 == null) {
			matafLabel4 = new mataf.types.MatafLabel();
			matafLabel4.setBounds(688, 105, 85, 20);
			matafLabel4.setText("חשבון :");
		}
		return matafLabel4;
	}
	/**
	 * This method initializes matafLabel5
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel5() {
		if (matafLabel5 == null) {
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(688, 130, 85, 20);
			matafLabel5.setText("מטבע :");
		}
		return matafLabel5;
	}
	/**
	 * This method initializes matafLabel6
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel6() {
		if (matafLabel6 == null) {
			matafLabel6 = new mataf.types.MatafLabel();
			matafLabel6.setBounds(688, 155, 85, 20);
			matafLabel6.setText("סכום בפעולה :");
		}
		return matafLabel6;
	}
	/**
	 * This method initializes matafLabel7
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel7() {
		if (matafLabel7 == null) {
			matafLabel7 = new mataf.types.MatafLabel();
			matafLabel7.setBounds(688, 205, 85, 20);
			matafLabel7.setText("סוג לקוח :");
		}
		return matafLabel7;
	}
	/**
	 * This method initializes matafLabel8
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel8() {
		if (matafLabel8 == null) {
			matafLabel8 = new mataf.types.MatafLabel();
			matafLabel8.setBounds(688, 230, 85, 20);
			matafLabel8.setText("סיבת דיווח :");
		}
		return matafLabel8;
	}
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel9() {
		if (matafLabel9 == null) {
			matafLabel9 = new mataf.types.MatafLabel();
			matafLabel9.setBounds(688, 280, 85, 20);
			matafLabel9.setText("סניף לקוח :");
		}
		return matafLabel9;
	}
	/**
	 * This method initializes matafLabel10
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel10() {
		if (matafLabel10 == null) {
			matafLabel10 = new mataf.types.MatafLabel();
			matafLabel10.setBounds(688, 305, 85, 20);
			matafLabel10.setText("חשבון לקוח :");
		}
		return matafLabel10;
	}
	/**
	 * This method initializes matafLabel11
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel11() {
		if (matafLabel11 == null) {
			matafLabel11 = new mataf.types.MatafLabel();
			matafLabel11.setBounds(649, 30, 31, 20);
			matafLabel11.setText("ט001");
			matafLabel11.setDataName("HASS_LAKOACH_SUG.HA_SUG_PEULA_SCREEN");
		}
		return matafLabel11;
	}
	/**
	 * This method initializes matafLabel12
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel12() {
		if (matafLabel12 == null) {
			matafLabel12 = new mataf.types.MatafLabel();
			matafLabel12.setBounds(649, 55, 31, 20);
			matafLabel12.setText("52");
			matafLabel12.setDataName("HASS_LAKOACH_SUG.HA_BANK");
		}
		return matafLabel12;
	}
	/**
	 * This method initializes matafLabel13
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel13() {
		if (matafLabel13 == null) {
			matafLabel13 = new mataf.types.MatafLabel();
			matafLabel13.setBounds(649, 80, 31, 20);
			matafLabel13.setText("285");
			matafLabel13.setDataName("HASS_LAKOACH_SUG.HA_SNIF");
		}
		return matafLabel13;
	}
	/**
	 * This method initializes matafLabel14
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel14() {
		if (matafLabel14 == null) {
			matafLabel14 = new mataf.types.MatafLabel();
			matafLabel14.setBounds(633, 105, 47, 20);
			matafLabel14.setText("100102");
			matafLabel14.setDataName("HASS_LAKOACH_SUG.HA_CH");
		}
		return matafLabel14;
	}
	/**
	 * This method initializes matafLabel15
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel15() {
		if (matafLabel15 == null) {
			matafLabel15 = new mataf.types.MatafLabel();
			matafLabel15.setBounds(649, 130, 31, 20);
			matafLabel15.setText("00");
			matafLabel15.setDataName("HASS_LAKOACH_SUG.HA_MATBEA");
		}
		return matafLabel15;
	}
	/**
	 * This method initializes matafLabel16
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel16() {
		if (matafLabel16 == null) {
			matafLabel16 = new mataf.types.MatafLabel();
			matafLabel16.setBounds(534, 155, 146, 20);
			matafLabel16.setText("100000");
			matafLabel16.setDataName("HASS_LAKOACH_SUG.HA_SCHUM");
		}
		return matafLabel16;
	}
	/**
	 * This method initializes matafLabel17
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel17() {
		if (matafLabel17 == null) {
			matafLabel17 = new mataf.types.MatafLabel();
			matafLabel17.setBounds(628, 105, 5, 20);
			matafLabel17.setText("-");
		}
		return matafLabel17;
	}
	/**
	 * This method initializes matafLabel18
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel18() {
		if (matafLabel18 == null) {
			matafLabel18 = new mataf.types.MatafLabel();
			matafLabel18.setBounds(595, 105, 31, 20);
			matafLabel18.setText("XXX");
			matafLabel18.setDataName("HASS_LAKOACH_SUG.HA_SCH");
		}
		return matafLabel18;
	}
	/**
	 * This method initializes matafLabel19
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel19() {
		if (matafLabel19 == null) {
			matafLabel19 = new mataf.types.MatafLabel();
			matafLabel19.setBounds(622, 130, 25, 20);
			matafLabel19.setText("ש\"ח");
			matafLabel19.setDataName("HASS_LAKOACH_SUG.HA_MATBEA_DESC");
		}
		return matafLabel19;
	}
	/**
	 * This method initializes matafRadioButton
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton() {
		if (matafRadioButton == null) {
			matafRadioButton = new mataf.types.MatafRadioButton("קשור לחשבון");
			matafRadioButton.setActionCommand("sugLakoachRadioButton1");
			matafRadioButton.setType("Execute_Operation");
			matafRadioButton.setBounds(574, 205, 106, 18);
			matafRadioButton.setValue("1");
			matafRadioButton.setNavigationParameters(
				new com.ibm.dse.gui.NavigationParameters("processor", 0, "halbanatHonSugLakoachClientOp", 0, "", "", "", "", "", "", 0, 0, 0, 0, false, false));
			//matafRadioButton.setSelected(true);
		}
		return matafRadioButton;
	}
	/**
	 * This method initializes matafRadioButton2
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton2() {
		if (matafRadioButton2 == null) {
			matafRadioButton2 = new mataf.types.MatafRadioButton("אחר");
			matafRadioButton2.setType("Execute_Operation");
			matafRadioButton2.setActionCommand("sugLakoachRadioButton2");
			matafRadioButton2.setBounds(500, 205, 60, 18);
			matafRadioButton2.setText("אחר");
			matafRadioButton2.setValue("2");
			matafRadioButton2.setNavigationParameters(
				new com.ibm.dse.gui.NavigationParameters("processor", 0, "halbanatHonSugLakoachClientOp", 0, "", "", "", "", "", "", 0, 0, 0, 0, false, false));
		}
		return matafRadioButton2;
	}
	/**
	 * This method initializes matafRadioButton3
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton3() {
		if (matafRadioButton3 == null) {
			matafRadioButton3 = new mataf.types.MatafRadioButton("אובייקטיבי"); //, true);
			//matafRadioButton3.setActionCommand("sibatDivuachRadioButton1");
			matafRadioButton3.setType("Execute_Operation");
			matafRadioButton3.setBounds(574, 230, 106, 18);
			matafRadioButton3.setValue("1");
			//matafRadioButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonSibatDivuachClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafRadioButton3;
	}
	/**
	 * This method initializes matafRadioButton4
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton4() {
		if (matafRadioButton4 == null) {
			matafRadioButton4 = new mataf.types.MatafRadioButton("סובייקטיבי");
			//matafRadioButton4.setActionCommand("sibatDivuachRadioButton2");
			matafRadioButton4.setType("Execute_Operation");
			matafRadioButton4.setBounds(474, 230, 86, 18);
			matafRadioButton4.setValue("2");
			//matafRadioButton4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonSibatDivuachClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafRadioButton4;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton() {
		if (matafButton == null) {
			matafButton = new mataf.types.MatafButton();
			matafButton.setBounds(30, 425, 50, 20);
			matafButton.setText("צא");
			matafButton.setType(MatafButton.CLOSE_VIEW);
			matafButton.setDataName("HASS_LAKOACH_SUG.ExitButton");
			matafButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"",3,"slikaView","mataf.slika.views.SlikaView","","","","",0,0,0,0,false,false));
			matafButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						OpenDesktop.getActiveContext().setValueAt("HASX_PIRTEY_CHESHBON.HA_SW_DIVUACH_BUTZA", "0");
					} catch (DSEException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return matafButton;
	}
	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton2() {
		if (matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(100, 425, 120, 20);
			matafButton2.setText("מסך הבא >>");
			matafButton2.setDataName("HASS_LAKOACH_SUG.NextButton");
			matafButton2.setType(MatafButton.NEXT_VIEW);
			matafButton2.setNavigationParameters(
				new com.ibm.dse.gui.NavigationParameters(
					"activeView",
					2,
					"halbanatHonDetailsClientOp",
					0,
					"halbanatHonDetailsView",
					"mataf.halbanathon.views.HalbanatHonDetailsView",
					"",
					"",
					"",
					"",
					0,
					0,
					0,
					0,
					false,
					false));
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafButton3
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton3() {
		if (matafButton3 == null) {
			matafButton3 = new mataf.types.MatafButton();
			matafButton3.setBounds(230, 425, 120, 20);
			matafButton3.setText("א.מנהל מרוחק");
			matafButton3.setDataName("HASS_LAKOACH_SUG.RemoteManagerButton");
			matafButton3.setType(MatafButton.NEXT_VIEW);
			matafButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",3,"SendOverride","mataf.override.SendOverrideView","","","","",0,0,0,0,false,false));
			matafButton3.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					try {
							Context ctxTemp=OpenDesktop.getActiveContext();
							ctxTemp.setValueAt("trxORData.trxUuid", new Long(System.currentTimeMillis()).toString());
							ctxTemp.setValueAt("trxORData.trxId","X410");
							ctxTemp.setValueAt("trxORData.trxName","הלבנת הון");		
							ctxTemp.setValueAt("trxORData.viewName","mataf.halbanathon.panels.HalbanathonConclusionPanel");
							ctxTemp.setValueAt("trxORData.ctxName","halbanathonCtx");			
						}
					 catch (DSEException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return matafButton3;
	}
	/**
	 * This method initializes matafButton4
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton4() {
		if (matafButton4 == null) {
			matafButton4 = new mataf.types.MatafButton();
			matafButton4.setBounds(360, 425, 110, 20);
			matafButton4.setText("אישור מנהל");
			matafButton4.setDataName("HASS_LAKOACH_SUG.ManagerButton");
			matafButton4.setType("Next_View");
			matafButton4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"",0,"LocalOverride","mataf.override.LocalOverrideView","","","","",0,0,0,0,false,false));
		}
		return matafButton4;
	}
	/**
	 * This method initializes matafLabel20
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel20() {
		if (matafLabel20 == null) {
			matafLabel20 = new mataf.types.MatafLabel();
			matafLabel20.setBounds(534, 30, 110, 20);
			matafLabel20.setText("הפקדת מזומן");
			matafLabel20.setDataName("HASS_LAKOACH_SUG.HA_SUG_PEULA_SCREEN_DESC");
		}
		return matafLabel20;
	}
	/**
	 * This method initializes matafLabel21
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel21() {
		if (matafLabel21 == null) {
			matafLabel21 = new mataf.types.MatafLabel();
			matafLabel21.setBounds(534, 55, 110, 20);
			matafLabel21.setText("פאג\"י");
			matafLabel21.setDataName("HASS_LAKOACH_SUG.HA_BANK_DESC");
		}
		return matafLabel21;
	}
	/**
	 * This method initializes matafLabel22
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel22() {
		if (matafLabel22 == null) {
			matafLabel22 = new mataf.types.MatafLabel();
			matafLabel22.setBounds(534, 80, 110, 20);
			matafLabel22.setText("מתף - טסט 3");
			matafLabel22.setDataName("HASS_LAKOACH_SUG.HA_SNIF_DESC");
		}
		return matafLabel22;
	}
	/**
	 * This method initializes matafButton5
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton5() {
		if (matafButton5 == null) {
			matafButton5 = new mataf.types.MatafButton();
			matafButton5.setBounds(480, 425, 80, 20);
			matafButton5.setText("שידור");
			matafButton5.setDataName("HASS_LAKOACH_SUG.TransmitButton");
			matafButton5.setType(MatafButton.EXECUTE_OPERATION);
			matafButton5.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor", 0, "halbanatHonTransmitSugLakoachClientOp", 0, "", "", "", "", "", "", 0, 0, 0, 0, false, false));
			matafButton5.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						String HA_DIVUACH_KAYAM = (String) OpenDesktop.getActiveContext().getValueAt("HASR_PRATIM_SHIDUR.HA_DIVUACH_KAYAM");
						if ((HA_DIVUACH_KAYAM !=null) && (HA_DIVUACH_KAYAM.equals("1"))) {
							OpenDesktop.getActiveContext().setValueAt("HASI_PRATIM_SHIDUR.HA_DRISAT_DIVUACH", "1");
						}
					} catch (DSEException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return matafButton5;
	}
	/**
	 * This method initializes snifTableComboBox
	 * 
	 * @return mataf.types.table.TableComboBox
	 */
	public BranchTableComboBoxButton getSnifTableComboBox() {
		if (snifTableComboBox == null) 
		{
			snifTableComboBox = new BranchTableComboBoxButton();
			snifTableComboBox.setBounds(430, 281, 60, 20);
		}
		return snifTableComboBox;
	}
	/**
	 * This method initializes accountTypeTableComboBox
	 * 
	 * @return mataf.types.MatafTableComboBox
	 */
	public AccountTypeTableComboBoxButton getAccountTypeTableComboBox() {
		if (accountTypeTableComboBox == null) 
		{
			accountTypeTableComboBox = new AccountTypeTableComboBoxButton();
			accountTypeTableComboBox.setBounds(430, 305, 60, 20);
		}
		return accountTypeTableComboBox;
	}
	/**
	 * This method initializes matafTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	private mataf.types.MatafTextField getMatafTextField() {
		if (matafTextField == null) {
			matafTextField = new mataf.types.MatafTextField();
			matafTextField.setBounds(625, 305, 55, 20);
			matafTextField.setDataName("HASS_LAKOACH_SUG.HA_CH_ACHER");
			matafTextField.setType(MatafButton.EXECUTE_OPERATION);
			matafTextField.setName("matafTextField");
			matafTextField.setNavigationParameters(
				new com.ibm.dse.gui.NavigationParameters("processor", 0, "halbanatHonAccountAcherClientOp", 0, "", "", "", "", "", "", 0, 0, 0, 0, false, false));
			matafTextField.setMaxChars(6);
			//			matafTextField.setMinChars(new com.ibm.dse.gui.TextFieldMinLengthProperty(6,"שדה צריך להכיל 6 ספרות"));
			//matafTextField.setFormatter(new com.ibm.dse.gui.NumericConverter("הערך אינו נומרי"));
		}
		return matafTextField;
	}
	/**
	 * This method initializes matafComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField() {
		if(matafComboTextField == null) {
			matafComboTextField = new mataf.types.MatafComboTextField();
			matafComboTextField.setBounds(645, 280, 35, 21);
			matafComboTextField.setDataName("HASS_LAKOACH_SUG.HA_SNIF_ACHER");
			matafComboTextField.setTableComboBox(getSnifTableComboBox());
			matafComboTextField.setDescriptionLabel(getMatafLabel23());
			matafComboTextField.setMaxChars(3);
			
		}
		return matafComboTextField;
	}
	/**
	 * This method initializes matafLabel23
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel23() {
		if(matafLabel23 == null) {
			matafLabel23 = new mataf.types.MatafLabel();
			matafLabel23.setBounds(551, 280, 91, 21);
			matafLabel23.setText("משוב לסניף");
			matafLabel23.setDataName("HASS_LAKOACH_SUG.HA_SNIF_ACHER_DESC");
		}
		return matafLabel23;
	}
	/**
	 * This method initializes matafComboTextField2
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField2() {
		if(matafComboTextField2 == null) {
			matafComboTextField2 = new mataf.types.MatafComboTextField();
			matafComboTextField2.setBounds(590, 305, 35, 20);
			matafComboTextField2.setDataName("HASS_LAKOACH_SUG.HA_SCH_ACHER");
			matafComboTextField2.setDescriptionLabel(getMatafLabel24());
			matafComboTextField2.setTableComboBox(getAccountTypeTableComboBox());
			matafComboTextField2.setMaxChars(3);
		}
		return matafComboTextField2;
	}
	/**
	 * This method initializes matafLabel24
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel24() {
		if(matafLabel24 == null) {
			matafLabel24 = new mataf.types.MatafLabel();
			matafLabel24.setBounds(490, 305, 98, 20);
			matafLabel24.setDataName("HASS_LAKOACH_SUG.HA_SCH_ACHER_DESC");
			matafLabel24.setText("משוב לסוג חשבון");
		}
		return matafLabel24;
	}
}
