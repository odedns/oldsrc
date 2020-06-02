package mataf.halbanathon.panels;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import mataf.types.MatafButton;
import mataf.types.MatafButtonGroup;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.MatafTableComboBoxButton;
import mataf.types.table.MatafTable;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEException;
import com.mataf.dse.appl.OpenDesktop;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (07/10/2003 16:49:56).  
 */
public class MevatseaHapeulaComplete extends MatafEmbeddedPanel {

     public mataf.types.MatafTitle matafTitle = null;
     public mataf.types.MatafLabel matafLabel = null;
     public mataf.types.MatafLabel matafLabel2 = null;
     public mataf.types.MatafLabel matafLabel3 = null;
     public mataf.types.MatafLabel matafLabel4 = null;
     public mataf.types.MatafLabel matafLabel5 = null;
     public mataf.types.MatafLabel matafLabel10 = null;
     public mataf.types.MatafLabel matafLabel11 = null;
     public mataf.types.MatafLabel matafLabel12 = null;
     public mataf.types.MatafLabel matafLabel13 = null;
     public mataf.types.MatafLabel matafLabel14 = null;
     public mataf.types.MatafLabel matafLabel15 = null;
     public mataf.types.MatafLabel matafLabel17 = null;
     public mataf.types.MatafLabel matafLabel18 = null;
     public mataf.types.MatafLabel matafLabel19 = null;
     public mataf.types.MatafButton matafButton = null;
     public mataf.types.MatafButton matafButton2 = null;
     public mataf.types.MatafButton matafButton3 = null;
     public mataf.types.MatafButton matafButton4 = null;
     public mataf.types.MatafLabel matafLabel20 = null;
     public mataf.types.MatafLabel matafLabel21 = null;
     public mataf.types.MatafLabel matafLabel22 = null;
     public mataf.types.MatafButton matafButton5 = null;
     public mataf.types.MatafTableComboBoxButton tableComboBox2 = null;
     public mataf.types.MatafButton matafButton6 = null;
     public mataf.types.MatafLabel matafLabel6 = null;
     public mataf.types.MatafLabel matafLabel7 = null;
     public mataf.types.MatafLabel matafLabel8 = null;
     public mataf.types.MatafTextField matafTextField = null;
     public mataf.types.MatafTextField matafTextField2 = null;
     public mataf.types.MatafLabel matafLabel9 = null;
     public mataf.types.MatafLabel matafLabel16 = null;
     public mataf.types.MatafTextField matafTextField3 = null;
     public mataf.types.MatafLabel matafLabel24 = null;
     public mataf.types.MatafTextField matafTextField4 = null;
     public mataf.types.MatafLabel matafLabel25 = null;
     public mataf.types.MatafTextField matafTextField5 = null;
     public mataf.types.MatafLabel matafLabel26 = null;
     public mataf.types.MatafLabel matafLabel27 = null;
     public mataf.types.MatafTextField matafTextField6 = null;
     public mataf.types.MatafLabel matafLabel28 = null;
     public mataf.types.MatafTextField matafTextField7 = null;
     public mataf.types.MatafTextField matafTextField8 = null;
     public mataf.types.MatafLabel matafLabel29 = null;
     public mataf.types.MatafTextField matafTextField9 = null;
     public mataf.types.MatafLabel matafLabel30 = null;
     public mataf.types.MatafTextField matafTextField10 = null;
     public mataf.types.MatafTextField matafTextField11 = null;
     public mataf.types.MatafLabel matafLabel31 = null;
     public mataf.types.MatafTextField matafTextField12 = null;
     public mataf.types.MatafLabel matafLabel23 = null;
     public mataf.types.MatafRadioButton matafRadioButton = null;
     public mataf.types.MatafRadioButton matafRadioButton2 = null;
     public mataf.types.MatafTextField matafTextField13 = null;
     public mataf.types.MatafTextField matafTextField14 = null;
     public mataf.types.MatafTextField matafTextField15 = null;
     public mataf.types.MatafLabel matafLabel32 = null;
     public mataf.types.MatafLabel matafLabel33 = null;
     public mataf.types.MatafLabel matafLabel34 = null;
     public mataf.types.MatafLabel matafLabel35 = null;
     public mataf.types.MatafLabel matafLabel36 = null;
     public mataf.types.MatafLabel matafLabel37 = null;
     public mataf.types.MatafRadioButton matafRadioButton3 = null;
     public mataf.types.MatafRadioButton matafRadioButton4 = null;
     public MatafButtonGroup group1 = null;
     public MatafButtonGroup group2 = null;
     private mataf.types.MatafComboTextField matafComboTextField = null;
     private mataf.types.MatafLabel matafLabel38 = null;
	/**
	 * Constructor for SugLakoachPanel.
	 * @param layout
	 */
	public MevatseaHapeulaComplete(LayoutManager layout) {
		super(layout);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 * @param arg1
	 */
	public MevatseaHapeulaComplete(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 */
	public MevatseaHapeulaComplete(boolean arg0) {
		super(arg0);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 */
	public MevatseaHapeulaComplete() {
		super();
		initialize();
		initSpButtonGroupObjects();
		setFocusTraversalPolicy(new MevatseaHapeulaFocusPolicy(this));
		System.out.println("default = "+getFocusTraversalPolicy().getDefaultComponent(this));
	}
	
	public void initSpButtonGroupObjects() {
		group1 = new MatafButtonGroup();
		group1.setDataName("HASS_LAK_PRATIM.HA_SEX");
		group1.add(getMatafRadioButton());
		group1.add(getMatafRadioButton2());		

		group2 = new MatafButtonGroup();
		group2.setDataName("HASS_LAK_PRATIM.HA_SW_HATSHARAT_NENE");
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
        this.add(getMatafLabel10(), null);
        this.add(getMatafLabel11(), null);
        this.add(getMatafLabel12(), null);
        this.add(getMatafLabel13(), null);
        this.add(getMatafLabel14(), null);
        this.add(getMatafLabel15(), null);
        this.add(getMatafLabel17(), null);
        this.add(getMatafLabel18(), null);
        this.add(getMatafLabel19(), null);
        this.add(getMatafButton(), null);
        this.add(getMatafButton2(), null);
        this.add(getMatafButton3(), null);
        this.add(getMatafButton4(), null);
        this.add(getMatafLabel20(), null);
        this.add(getMatafLabel21(), null);
        this.add(getMatafLabel22(), null);
        this.add(getMatafButton5(), null);
        this.add(getTableComboBox2(), null);
        this.add(getMatafButton6(), null);
        this.add(getMatafLabel6(), null);
        this.add(getMatafLabel7(), null);
        this.add(getMatafLabel8(), null);
        this.add(getMatafTextField(), null);
        this.add(getMatafTextField2(), null);
        this.add(getMatafLabel9(), null);
        this.add(getMatafLabel16(), null);
        this.add(getMatafTextField3(), null);
        this.add(getMatafLabel24(), null);
        this.add(getMatafTextField4(), null);
        this.add(getMatafLabel25(), null);
        this.add(getMatafTextField5(), null);
        this.add(getMatafLabel26(), null);
        this.add(getMatafLabel27(), null);
        this.add(getMatafTextField6(), null);
        this.add(getMatafLabel28(), null);
        this.add(getMatafTextField7(), null);
        this.add(getMatafTextField8(), null);
        this.add(getMatafLabel29(), null);
        this.add(getMatafTextField9(), null);
        this.add(getMatafLabel30(), null);
        this.add(getMatafTextField10(), null);
        this.add(getMatafTextField11(), null);
        this.add(getMatafLabel31(), null);
        this.add(getMatafTextField12(), null);
        this.add(getMatafLabel23(), null);
        this.add(getMatafRadioButton(), null);
        this.add(getMatafRadioButton2(), null);
        this.add(getMatafTextField13(), null);
        this.add(getMatafTextField14(), null);
        this.add(getMatafTextField15(), null);
        this.add(getMatafLabel32(), null);
        this.add(getMatafLabel33(), null);
        this.add(getMatafLabel34(), null);
        this.add(getMatafLabel35(), null);
        this.add(getMatafLabel36(), null);
        this.add(getMatafLabel37(), null);
        this.add(getMatafRadioButton3(), null);
        this.add(getMatafRadioButton4(), null);
        this.add(getMatafComboTextField(), null);
        this.add(getMatafLabel38(), null);
        this.setBounds(0, 0, 780, 450);
			
	}
	/**
	 * This method initializes matafTitle
	 * 
	 * @return mataf.types.MatafTitle
	 */
	public mataf.types.MatafTitle getMatafTitle() {
		if(matafTitle == null) {
			matafTitle = new mataf.types.MatafTitle();
			matafTitle.setText("פרטי מבצע הפעולה - הזנת פרטים");
		}
		return matafTitle;
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
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
		if(matafLabel2 == null) {
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
		if(matafLabel3 == null) {
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
		if(matafLabel4 == null) {
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
		if(matafLabel5 == null) {
			matafLabel5 = new mataf.types.MatafLabel();
			matafLabel5.setBounds(688, 130, 85, 20);
			matafLabel5.setText("מטבע :");
		}
		return matafLabel5;
	}
	/**
	 * This method initializes matafLabel10
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel10() {
		if(matafLabel10 == null) {
			matafLabel10 = new mataf.types.MatafLabel();
			matafLabel10.setBounds(681, 180, 92, 20);
			matafLabel10.setText("מדינה :");
		}
		return matafLabel10;
	}
	/**
	 * This method initializes matafLabel11
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel11() {
		if(matafLabel11 == null) {
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
		if(matafLabel12 == null) {
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
		if(matafLabel13 == null) {
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
		if(matafLabel14 == null) {
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
		if(matafLabel15 == null) {
			matafLabel15 = new mataf.types.MatafLabel();
			matafLabel15.setBounds(649, 130, 31, 20);
			matafLabel15.setText("00");
			matafLabel15.setDataName("HASS_LAKOACH_SUG.HA_MATBEA");
		}
		return matafLabel15;
	}
	/**
	 * This method initializes matafLabel17
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel17() {
		if(matafLabel17 == null) {
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
		if(matafLabel18 == null) {
			matafLabel18 = new mataf.types.MatafLabel();
			matafLabel18.setBounds(595, 105, 31, 20);
			matafLabel18.setText("105");
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
		if(matafLabel19 == null) {
			matafLabel19 = new mataf.types.MatafLabel();
			matafLabel19.setBounds(622, 130, 25, 20);
			matafLabel19.setText("ש\"ח");
			matafLabel19.setDataName("HASS_LAKOACH_SUG.HA_MATBEA_DESC");
		}
		return matafLabel19;
	}
	/**
	 * This method initializes matafButton
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton() {
		if(matafButton == null) {
			matafButton = new mataf.types.MatafButton();			
			matafButton.setBounds(30, 425, 50, 20);
			matafButton.setType("Close_Navigation");
			matafButton.setDataName("HASS_LAK_PRATIM.ExitButton");
//			matafButton.setViewsToCloseAlso(new String[]{"halbanatHonView"});
			matafButton.setText("צא");
			matafButton.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"",3,"halbanatHonView","mataf.halbanathon.HalbanatHonView","","","","",0,0,0,0,false,false));
		}
		return matafButton;
	}
	/**
	 * This method initializes matafButton2
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton2() {
		if(matafButton2 == null) {
			matafButton2 = new mataf.types.MatafButton();
			matafButton2.setBounds(100, 425, 120, 20);
			matafButton2.setText("<< מסך קודם");
			matafButton2.setType(MatafButton.PREVIOUS_VIEW);
		}
		return matafButton2;
	}
	/**
	 * This method initializes matafButton3
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton3() {
		if(matafButton3 == null) {
			matafButton3 = new mataf.types.MatafButton();
			matafButton3.setBounds(500, 424, 120, 20);
			matafButton3.setBounds(500, 425, 120, 20);
			matafButton3.setText("אישור מנהל");
			//matafButton3.setDataName("HASS_LAKOACH_SUG.ManagerButton");
			matafButton3.setType("Execute_Operation");
			matafButton3.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",0,"localOverrideOp",0,"LocalOverride","mataf.override.LocalOverrideView","","","","",0,0,0,0,false,false));
		}
		return matafButton3;
	}
	/**
	 * This method initializes matafButton4
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton4() {
		if(matafButton4 == null) {
			matafButton4 = new mataf.types.MatafButton();
			matafButton4.setBounds(370, 425, 120, 20);
			matafButton4.setText("א.מנהל מרוחק");
			//matafButton4.setDataName("HASS_LAKOACH_SUG.RemoteManagerButton");
			matafButton4.setType(MatafButton.NEXT_VIEW);
			matafButton4.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("activeView",2,"",3,"SendOverride","mataf.override.SendOverrideView","","","","",0,0,0,0,false,false));
			matafButton4.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					try {
							Context ctxTemp=OpenDesktop.getActiveContext();
							ctxTemp.setValueAt("trxORData.trxUuid", new Long(System.currentTimeMillis()).toString());
							ctxTemp.setValueAt("trxORData.trxId","X410");
							ctxTemp.setValueAt("trxORData.trxName","הלבנת הון");		
							ctxTemp.setValueAt("trxORData.viewName","mataf.halbanathon.panels.HalbanathonConclusionPanel");
							ctxTemp.setValueAt("trxORData.ctxName","halbanatHonDetailsCtx");			
						}
					 catch (DSEException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		return matafButton4;
	}
	/**
	 * This method initializes matafLabel20
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel20() {
		if(matafLabel20 == null) {
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
		if(matafLabel21 == null) {
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
		if(matafLabel22 == null) {
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
		if(matafButton5 == null) {
			matafButton5 = new mataf.types.MatafButton();
			matafButton5.setBounds(630, 425, 80, 20);
			matafButton5.setText("שידור");
			matafButton5.setType(MatafButton.EXECUTE_OPERATION);
			matafButton5.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonTransmitDetailsClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafButton5;
	}
	/**
	 * This method initializes tableComboBox2
	 * 
	 * @return mataf.types.table.TableComboBox
	 */
	public mataf.types.MatafTableComboBoxButton getTableComboBox2() 	{
		if(tableComboBox2 == null) {
			MatafTable table = new MatafTable();
			table.setDataNameForList("countriesList");
			table.getOurModel().addColumn(Integer.class, "קוד מדינה", "countriesData.GL_KOD_MEDINA",Integer.MAX_VALUE, 80);
			table.getOurModel().addColumn(String.class, "מדינה", "countriesData.GL_SHEM_MEDINA",Integer.MAX_VALUE, 150);
						
			tableComboBox2 = new mataf.types.MatafTableComboBoxButton();
			tableComboBox2.setTable(table);
			tableComboBox2.setBounds(510, 180, 60, 20);
			tableComboBox2.setClientOperation("halbanatHonCountriesClientOp");
		}
		return tableComboBox2;
	}
	/**
	 * This method initializes matafButton6
	 * 
	 * @return mataf.types.MatafButton
	 */
	public mataf.types.MatafButton getMatafButton6() {
		if(matafButton6 == null) {
			matafButton6 = new mataf.types.MatafButton();
			matafButton6.setBounds(230, 425, 130, 20);
			matafButton6.setType(MatafButton.CLEAR_VIEW);
			matafButton6.setText("ניקוי מסך");
		}
		return matafButton6;
	}
	/**
	 * This method initializes matafLabel6
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel6() {
		if(matafLabel6 == null) {
			matafLabel6 = new mataf.types.MatafLabel();
			matafLabel6.setBounds(681, 205, 92, 20);
			matafLabel6.setLabelFor(getMatafTextField());
			matafLabel6.setText("ת.ז. / דרכון :");
		}
		return matafLabel6;
	}
	/**
	 * This method initializes matafLabel7
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel7() {
		if(matafLabel7 == null) {
			matafLabel7 = new mataf.types.MatafLabel();
			matafLabel7.setBounds(681, 255, 92, 20);
			matafLabel7.setText("שם משפחה :");
		}
		return matafLabel7;
	}
	/**
	 * This method initializes matafLabel8
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel8() {
		if(matafLabel8 == null) {
			matafLabel8 = new mataf.types.MatafLabel();
			matafLabel8.setBounds(681, 330, 92, 20);
			matafLabel8.setText("טלפון :");
		}
		return matafLabel8;
	}
	/**
	 * This method initializes matafTextField
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField() {
		if(matafTextField == null) {
			matafTextField = new mataf.types.MatafTextField();
			matafTextField.setBounds(595, 205, 85, 20);
			matafTextField.setDataName("HASS_LAK_PRATIM.HA_TZ");
			matafTextField.setType(MatafButton.EXECUTE_OPERATION);
			//matafTextField.setFormatter(new com.ibm.dse.gui.NumericConverter("ערך אינו נומרי"));
			matafTextField.setMaxChars(9);
			matafTextField.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonIDClientOp",0,"","","","","","",0,0,0,0,false,false));
			matafTextField.addFocusListener(new java.awt.event.FocusListener() 
			{
				public void focusGained(java.awt.event.FocusEvent e) 
				{
					String id = matafTextField.getText().trim();
					if(!id.equals("0"))
					{
						try
						{
							OpenDesktop.getActiveContext().setValueAt("HASX_PIRTEY_CHESHBON.HA_TZ_EZER",id);
						}
						catch(DSEException ex)
						{
							ex.printStackTrace();
						}
					}
				}
				public void focusLost(java.awt.event.FocusEvent e) {}
			});
		}
		return matafTextField;
	}
	/**
	 * This method initializes matafTextField2
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField2() {
		if(matafTextField2 == null) {
			matafTextField2 = new mataf.types.MatafTextField();
			matafTextField2.setBounds(516, 255, 164, 20);
			matafTextField2.setDataName("GLSX_K86P_RESULT.HA_NAME_LAST");
			matafTextField2.setFreeTextEnabled(true);
		}
		return matafTextField2;
	}
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel9() {
		if(matafLabel9 == null) {
			matafLabel9 = new mataf.types.MatafLabel();
			matafLabel9.setBounds(241, 255, 60, 20);
			matafLabel9.setText("שם פרטי :");
		}
		return matafLabel9;
	}
	/**
	 * This method initializes matafLabel16
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel16() {
		if(matafLabel16 == null) {
			matafLabel16 = new mataf.types.MatafLabel();
			matafLabel16.setBounds(241, 330, 43, 20);
			matafLabel16.setText("טלפון :");
			matafLabel16.setVisible(false);
			matafLabel16.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2_LABEL");
		}
		return matafLabel16;
	}
	/**
	 * This method initializes matafTextField3
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField3() {
		if(matafTextField3 == null) {
			matafTextField3 = new mataf.types.MatafTextField();
			matafTextField3.setBounds(114, 255, 118, 20);
			matafTextField3.setDataName("GLSX_K86P_RESULT.HA_NAME_FIRST");
			matafTextField3.setFreeTextEnabled(true);
		}
		return matafTextField3;
	}
	/**
	 * This method initializes matafLabel24
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel24() {
		if(matafLabel24 == null) {
			matafLabel24 = new mataf.types.MatafLabel();
			matafLabel24.setBounds(681, 230, 92, 20);
			matafLabel24.setText("ת.לידה לאימות :");
			matafLabel24.setVisible(false);
			matafLabel24.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA_LABEL");
		}
		return matafLabel24;
	}
	/**
	 * This method initializes matafTextField4
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField4() {
		if(matafTextField4 == null) {
			matafTextField4 = new mataf.types.MatafTextField();
			matafTextField4.setBounds(595, 230, 85, 20);
			matafTextField4.setVisible(false);
			matafTextField4.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA");
		}
		return matafTextField4;
	}
	/**
	 * This method initializes matafLabel25
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel25() {
		if(matafLabel25 == null) {
			matafLabel25 = new mataf.types.MatafLabel();
			matafLabel25.setBounds(241, 230, 105, 20);
			matafLabel25.setText("ת.הנפקת תעודה :");
			//matafLabel25.setVisible(false);
			matafLabel25.setDataName("HASS_LAK_PRATIM.HASG_TR_HANPAKA_LABEL");
		}
		return matafLabel25;
	}
	/**
	 * This method initializes matafTextField5
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField5() {
		if(matafTextField5 == null) {
			matafTextField5 = new mataf.types.MatafTextField();
			matafTextField5.setBounds(114, 230, 118, 20);
			//matafTextField5.setVisible(false);
			matafTextField5.setDataName("HASS_LAK_PRATIM.HASG_TR_HANPAKA");
		}
		return matafTextField5;
	}
	/**
	 * This method initializes matafLabel26
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel26() {
		if(matafLabel26 == null) {
			matafLabel26 = new mataf.types.MatafLabel();
			matafLabel26.setBounds(681, 280, 92, 20);
			matafLabel26.setText("מען : רחוב :");
			matafLabel26.setVisible(false);
			matafLabel26.setDataName("HASS_LAK_PRATIM.HA_HOME_STREET_LABEL");
		}
		return matafLabel26;
	}
	/**
	 * This method initializes matafLabel27
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel27() {
		if(matafLabel27 == null) {
			matafLabel27 = new mataf.types.MatafLabel();
			matafLabel27.setBounds(681, 305, 92, 20);
			matafLabel27.setText("        ישוב :");
			matafLabel27.setVisible(false);
			matafLabel27.setDataName("HASS_LAK_PRATIM.HA_HOME_CITY_LABEL");
		}
		return matafLabel27;
	}
	/**
	 * This method initializes matafTextField6
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField6() {
		if(matafTextField6 == null) {
			matafTextField6 = new mataf.types.MatafTextField();
			matafTextField6.setBounds(516, 280, 164, 20);
			matafTextField6.setVisible(false);
			matafTextField6.setDataName("HASS_LAK_PRATIM.HA_HOME_STREET");
			matafTextField6.setFreeTextEnabled(true);
		}
		return matafTextField6;
	}
	/**
	 * This method initializes matafLabel28
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel28() {
		if(matafLabel28 == null) {
			matafLabel28 = new mataf.types.MatafLabel();
			matafLabel28.setBounds(241, 280, 36, 20);
			matafLabel28.setText("בית :");
			matafLabel28.setVisible(false);
			matafLabel28.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_NUM_LABEL");
		}
		return matafLabel28;
	}
	/**
	 * This method initializes matafTextField7
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField7() {
		if(matafTextField7 == null) {
			matafTextField7 = new mataf.types.MatafTextField();
			matafTextField7.setBounds(206, 280, 26, 20);
			matafTextField7.setVisible(false);
			matafTextField7.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_NUM");
		}
		return matafTextField7;
	}
	/**
	 * This method initializes matafTextField8
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField8() {
		if(matafTextField8 == null) {
			matafTextField8 = new mataf.types.MatafTextField();
			matafTextField8.setBounds(195, 280, 10, 20);
			matafTextField8.setVisible(false);
			matafTextField8.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM");
		}
		return matafTextField8;
	}
	/**
	 * This method initializes matafLabel29
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel29() {
		if(matafLabel29 == null) {
			matafLabel29 = new mataf.types.MatafLabel();
			matafLabel29.setBounds(145, 280, 36, 20);
			matafLabel29.setText("דירה :");
			matafLabel29.setVisible(false);
			matafLabel29.setDataName("HASS_LAK_PRATIM.HA_HOME_APT_LABEL");
		}
		return matafLabel29;
	}
	/**
	 * This method initializes matafTextField9
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField9() {
		if(matafTextField9 == null) {
			matafTextField9 = new mataf.types.MatafTextField();
			matafTextField9.setBounds(114, 280, 26, 20);
			matafTextField9.setVisible(false);
			matafTextField9.setDataName("HASS_LAK_PRATIM.HA_HOME_APT");
		}
		return matafTextField9;
	}
	/**
	 * This method initializes matafLabel30
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel30() {
		if(matafLabel30 == null) {
			matafLabel30 = new mataf.types.MatafLabel();
			matafLabel30.setBounds(241, 305, 43, 20);
			matafLabel30.setText("מיקוד :");
			matafLabel30.setVisible(false);
			matafLabel30.setDataName("HASS_LAK_PRATIM.HA_HOME_ZIP_LABEL");
		}
		return matafLabel30;
	}
	/**
	 * This method initializes matafTextField10
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField10() {
		if(matafTextField10 == null) {
			matafTextField10 = new mataf.types.MatafTextField();
			matafTextField10.setBounds(163, 305, 69, 20);
			matafTextField10.setVisible(false);
			matafTextField10.setDataName("HASS_LAK_PRATIM.HA_HOME_ZIP");
		}
		return matafTextField10;
	}
	/**
	 * This method initializes matafTextField11
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField11() {
		if(matafTextField11 == null) {
			matafTextField11 = new mataf.types.MatafTextField();
			matafTextField11.setBounds(130, 330, 26, 20);
			matafTextField11.setVisible(false);
			matafTextField11.setDataName("HASS_LAK_PRATIM.HA_PHONE_AREA2");
		}
		return matafTextField11;
	}
	/**
	 * This method initializes matafLabel31
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel31() {
		if(matafLabel31 == null) {
			matafLabel31 = new mataf.types.MatafLabel();
			matafLabel31.setBounds(156, 330, 5, 20);
			matafLabel31.setText("-");
			matafLabel31.setVisible(false);
			matafLabel31.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2_SEPERATOR_LABEL");
		}
		return matafLabel31;
	}
	/**
	 * This method initializes matafTextField12
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField12() {
		if(matafTextField12 == null) {
			matafTextField12 = new mataf.types.MatafTextField();
			matafTextField12.setBounds(163, 330, 69, 20);
			matafTextField12.setVisible(false);
			matafTextField12.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2");
		}
		return matafTextField12;
	}
	/**
	 * This method initializes matafLabel23
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel23() {
		if(matafLabel23 == null) {
			matafLabel23 = new mataf.types.MatafLabel();
			matafLabel23.setBounds(681, 355, 92, 20);
			matafLabel23.setText("מין :");
			matafLabel23.setVisible(false);
			matafLabel23.setDataName("HASS_LAK_PRATIM.HA_SEX_LABEL");
		}
		return matafLabel23;
	}
	/**
	 * This method initializes matafRadioButton
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton() {
		if(matafRadioButton == null) {
			matafRadioButton = new mataf.types.MatafRadioButton();
			matafRadioButton.setBounds(634, 355, 46, 20);
			matafRadioButton.setText("זכר");
			matafRadioButton.setVisible(false);
		}
		return matafRadioButton;
	}
	/**
	 * This method initializes matafRadioButton2
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton2() {
		if(matafRadioButton2 == null) {
			matafRadioButton2 = new mataf.types.MatafRadioButton();
			matafRadioButton2.setBounds(555, 355, 60, 20);
			matafRadioButton2.setText("נקבה");
			matafRadioButton2.setVisible(false);
		}
		return matafRadioButton2;
	}
	/**
	 * This method initializes matafTextField13
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField13() {
		if(matafTextField13 == null) {
			matafTextField13 = new mataf.types.MatafTextField();
			matafTextField13.setBounds(516, 305, 164, 20);
			matafTextField13.setVisible(false);
			matafTextField13.setDataName("HASS_LAK_PRATIM.HA_HOME_CITY");
			matafTextField13.setFreeTextEnabled(true);
		}
		return matafTextField13;
	}
	/**
	 * This method initializes matafTextField14
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField14() {
		if(matafTextField14 == null) {
			matafTextField14 = new mataf.types.MatafTextField();
			matafTextField14.setBounds(611, 330, 69, 20);
			matafTextField14.setDataName("GLSX_K86P_RESULT.HA_PHONE_NUM1");
			matafTextField14.setType(MatafButton.EXECUTE_OPERATION);
			matafTextField14.setMaxChars(7);
			matafTextField14.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonPhoneClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafTextField14;
	}
	/**
	 * This method initializes matafTextField15
	 * 
	 * @return mataf.types.MatafTextField
	 */
	public mataf.types.MatafTextField getMatafTextField15() {
		if(matafTextField15 == null) {
			matafTextField15 = new mataf.types.MatafTextField();
			matafTextField15.setBounds(576, 330, 26, 20);
			matafTextField15.setDataName("GLSX_K86P_RESULT.HA_PHONE_AREA1");
			matafTextField15.setMaxChars(3);
			matafTextField15.setType(MatafButton.EXECUTE_OPERATION);
			matafTextField15.setNavigationParameters(new com.ibm.dse.gui.NavigationParameters("processor",0,"halbanatHonKidometClientOp",0,"","","","","","",0,0,0,0,false,false));
		}
		return matafTextField15;
	}
	/**
	 * This method initializes matafLabel32
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel32() {
		if(matafLabel32 == null) {
			matafLabel32 = new mataf.types.MatafLabel();
			matafLabel32.setBounds(604, 330, 5, 20);
			matafLabel32.setText("-");
		}
		return matafLabel32;
	}
	/**
	 * This method initializes matafLabel33
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel33() {
		if(matafLabel33 == null) {
			matafLabel33 = new mataf.types.MatafLabel();
			matafLabel33.setBounds(241, 355, 99, 20);
			matafLabel33.setText("ת.לידה בתעודה :");
			matafLabel33.setVisible(false);
			matafLabel33.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM_LABEL");
		}
		return matafLabel33;
	}
	/**
	 * This method initializes matafLabel34
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel34() {
		if(matafLabel34 == null) {
			matafLabel34 = new mataf.types.MatafLabel();
			matafLabel34.setBounds(241, 380, 71, 20);
			matafLabel34.setText("מעמד לקוח :");
			matafLabel34.setVisible(false);
			matafLabel34.setDataName("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD_LABEL");
		}
		return matafLabel34;
	}
	/**
	 * This method initializes matafLabel35
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel35() {
		if(matafLabel35 == null) {
			matafLabel35 = new mataf.types.MatafLabel();
			matafLabel35.setBounds(114, 355, 118, 20);
			matafLabel35.setText("01/01/1111");
			matafLabel35.setVisible(false);
			matafLabel35.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA_MLM");
		}
		return matafLabel35;
	}
	/**
	 * This method initializes matafLabel36
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel36() {
		if(matafLabel36 == null) {
			matafLabel36 = new mataf.types.MatafLabel();
			matafLabel36.setBounds(68, 380, 164, 20);
			matafLabel36.setText("תושב הארץ");
			matafLabel36.setVisible(false);
			matafLabel36.setDataName("HASS_LAK_PRATIM.HA_LAKOACH_MAAMAD");
		}
		return matafLabel36;
	}
	/**
	 * This method initializes matafLabel37
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel37() {
		if(matafLabel37 == null) {
			matafLabel37 = new mataf.types.MatafLabel();
			matafLabel37.setBounds(646, 380, 127, 20);
			matafLabel37.setText("הצהרת נהנים לפעולה :");
		}
		return matafLabel37;
	}
	/**
	 * This method initializes matafRadioButton3
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton3() {
		if(matafRadioButton3 == null) {
			matafRadioButton3 = new mataf.types.MatafRadioButton();
			matafRadioButton3.setBounds(599, 380, 44, 20);
			matafRadioButton3.setText("אין");
			matafRadioButton3.setValue("1");
		}
		return matafRadioButton3;
	}
	/**
	 * This method initializes matafRadioButton4
	 * 
	 * @return mataf.types.MatafRadioButton
	 */
	public mataf.types.MatafRadioButton getMatafRadioButton4() {
		if(matafRadioButton4 == null) {
			matafRadioButton4 = new mataf.types.MatafRadioButton();
			matafRadioButton4.setBounds(539, 380, 43, 20);
			matafRadioButton4.setText("יש");
			matafRadioButton4.setValue("2");
		}
		return matafRadioButton4;
	}
	
	public static void main(String[] args)
	{
		JFrame f = new JFrame("Test");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(new MevatseaHapeulaComplete());
		f.setSize(800,600);
		f.setVisible(true);
	}
	
	/**
	 * This method initializes matafComboTextField
	 * 
	 * @return mataf.types.MatafComboTextField
	 */
	private mataf.types.MatafComboTextField getMatafComboTextField() {
		if(matafComboTextField == null) {
			matafComboTextField = new mataf.types.MatafComboTextField();
			matafComboTextField.setBounds(645, 180, 35, 20);
			matafComboTextField.setDataName("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
			matafComboTextField.setTableComboBox(getTableComboBox2());
			matafComboTextField.setDescriptionLabel(getMatafLabel38());
			matafComboTextField.setMaxChars(3);	
		}
		return matafComboTextField;
	}
	/**
	 * This method initializes matafLabel38
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel38() {
		if(matafLabel38 == null) {
			matafLabel38 = new mataf.types.MatafLabel();
			matafLabel38.setBounds(570, 180, 73, 20);
			matafLabel38.setText("משוב למדינה");
			matafLabel38.setDataName("HASS_LAK_PRATIM.HA_COUNTRY_CODE_DESC");
		}
		return matafLabel38;
	}
}  //  @jve:visual-info  decl-index=0 visual-constraint="7,-1"
