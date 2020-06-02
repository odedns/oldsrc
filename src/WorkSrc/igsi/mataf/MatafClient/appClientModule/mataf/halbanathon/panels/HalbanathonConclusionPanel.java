package mataf.halbanathon.panels;

import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import mataf.types.MatafButton;
import mataf.types.MatafButtonGroup;
import mataf.types.MatafEmbeddedPanel;
import mataf.types.table.MatafTable;

import com.ibm.dse.base.DSEException;
import com.mataf.dse.appl.OpenDesktop;


/**
 * @author ronenk
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class HalbanathonConclusionPanel extends MatafEmbeddedPanel
{
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
     public mataf.types.MatafLabel matafLabel20 = null;
     public mataf.types.MatafLabel matafLabel21 = null;
     public mataf.types.MatafLabel matafLabel22 = null;
     public mataf.types.MatafLabel matafLabel6 = null;
     public mataf.types.MatafLabel matafLabel7 = null;
     public mataf.types.MatafLabel matafLabel8 = null;
     public mataf.types.MatafLabel matafLabel9 = null;
     public mataf.types.MatafLabel matafLabel16 = null;
     public mataf.types.MatafLabel matafLabel24 = null;
     public mataf.types.MatafLabel matafLabel25 = null;
     public mataf.types.MatafLabel matafLabel26 = null;
     public mataf.types.MatafLabel matafLabel27 = null;
     public mataf.types.MatafLabel matafLabel28 = null;
     public mataf.types.MatafLabel matafLabel29 = null;
     public mataf.types.MatafLabel matafLabel30 = null;
     public mataf.types.MatafLabel matafLabel31 = null;
     public mataf.types.MatafLabel matafLabel23 = null;
     public mataf.types.MatafRadioButton matafRadioButton = null;
     public mataf.types.MatafRadioButton matafRadioButton2 = null;
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
     private mataf.types.MatafLabel matafLabel39 = null;
     private mataf.types.MatafLabel matafLabel38 = null;
     private mataf.types.MatafLabel matafLabel40 = null;
     private mataf.types.MatafLabel matafLabel41 = null;
     private mataf.types.MatafLabel matafLabel42 = null;
     private mataf.types.MatafLabel matafLabel43 = null;
     private mataf.types.MatafLabel matafLabel44 = null;
     private mataf.types.MatafLabel matafLabel45 = null;
     private mataf.types.MatafLabel matafLabel46 = null;
     private mataf.types.MatafLabel matafLabel47 = null;
     private mataf.types.MatafLabel matafLabel48 = null;
     private mataf.types.MatafLabel matafLabel49 = null;
     private mataf.types.MatafLabel matafLabel50 = null;
     private mataf.types.MatafLabel matafLabel51 = null;
     private mataf.types.MatafLabel matafLabel52 = null;
     private mataf.types.MatafLabel matafLabel53 = null;
     private mataf.types.MatafLabel matafLabel54 = null;
	/**
	 * Constructor for SugLakoachPanel.
	 * @param layout
	 */
	public HalbanathonConclusionPanel(LayoutManager layout) {
		super(layout);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 * @param arg1
	 */
	public HalbanathonConclusionPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 * @param arg0
	 */
	public HalbanathonConclusionPanel(boolean arg0) {
		super(arg0);
	}

	/**
	 * Constructor for SugLakoachPanel.
	 */
	public HalbanathonConclusionPanel() {
		super();
		initialize();
		initSpButtonGroupObjects();
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
        this.add(getMatafLabel20(), null);
        this.add(getMatafLabel21(), null);
        this.add(getMatafLabel22(), null);
        this.add(getMatafLabel6(), null);
        this.add(getMatafLabel7(), null);
        this.add(getMatafLabel8(), null);
        this.add(getMatafLabel9(), null);
        this.add(getMatafLabel16(), null);
        this.add(getMatafLabel24(), null);
        this.add(getMatafLabel25(), null);
        this.add(getMatafLabel26(), null);
        this.add(getMatafLabel27(), null);
        this.add(getMatafLabel28(), null);
        this.add(getMatafLabel29(), null);
        this.add(getMatafLabel30(), null);
        this.add(getMatafLabel31(), null);
        this.add(getMatafLabel23(), null);
        this.add(getMatafRadioButton(), null);
        this.add(getMatafRadioButton2(), null);
        this.add(getMatafLabel32(), null);
        this.add(getMatafLabel33(), null);
        this.add(getMatafLabel34(), null);
        this.add(getMatafLabel35(), null);
        this.add(getMatafLabel36(), null);
        this.add(getMatafLabel37(), null);
        this.add(getMatafRadioButton3(), null);
        this.add(getMatafRadioButton4(), null);
        this.add(getMatafLabel39(), null);
        this.add(getMatafLabel38(), null);
        this.add(getMatafLabel40(), null);
        this.add(getMatafLabel41(), null);
        this.add(getMatafLabel42(), null);
        this.add(getMatafLabel43(), null);
        this.add(getMatafLabel44(), null);
        this.add(getMatafLabel45(), null);
        this.add(getMatafLabel46(), null);
        this.add(getMatafLabel47(), null);
        this.add(getMatafLabel48(), null);
        this.add(getMatafLabel49(), null);
        this.add(getMatafLabel50(), null);
        this.add(getMatafLabel51(), null);
        this.add(getMatafLabel52(), null);
        this.add(getMatafLabel53(), null);
        this.add(getMatafLabel54(), null);
        this.setBounds(0, 0, 780, 300);
			
	}
	/**
	 * This method initializes matafLabel
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel() {
		if(matafLabel == null) {
			matafLabel = new mataf.types.MatafLabel();
			matafLabel.setBounds(689, 5, 85, 20);
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
			matafLabel2.setBounds(689, 30, 85, 20);
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
			matafLabel3.setBounds(689, 55, 85, 20);
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
			matafLabel4.setBounds(689, 80, 85, 20);
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
			matafLabel5.setBounds(689, 105, 85, 20);
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
			matafLabel10.setBounds(689, 137, 85, 20);
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
			matafLabel11.setBounds(650, 5, 31, 20);
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
			matafLabel12.setBounds(650, 30, 31, 20);
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
			matafLabel13.setBounds(650, 55, 31, 20);
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
			matafLabel14.setBounds(634, 80, 47, 20);
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
			matafLabel15.setBounds(650, 105, 31, 20);
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
			matafLabel17.setBounds(629, 80, 5, 20);
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
			matafLabel18.setBounds(596, 80, 31, 20);
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
			matafLabel19.setBounds(623, 105, 25, 20);
			matafLabel19.setText("ש\"ח");
			matafLabel19.setDataName("HASS_LAKOACH_SUG.HA_MATBEA_DESC");
		}
		return matafLabel19;
	}
	/**
	 * This method initializes matafLabel20
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel20() {
		if(matafLabel20 == null) {
			matafLabel20 = new mataf.types.MatafLabel();
			matafLabel20.setBounds(535, 5, 110, 20);
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
			matafLabel21.setBounds(535, 30, 110, 20);
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
			matafLabel22.setBounds(535, 55, 110, 20);
			matafLabel22.setText("מתף - טסט 3");
			matafLabel22.setDataName("HASS_LAKOACH_SUG.HA_SNIF_DESC");
		}
		return matafLabel22;
	}
	/**
	 * This method initializes matafLabel6
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel6() {
		if(matafLabel6 == null) {
			matafLabel6 = new mataf.types.MatafLabel();
			matafLabel6.setBounds(689, 162, 85, 20);
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
			matafLabel7.setBounds(689, 212, 85, 20);
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
			matafLabel8.setBounds(356, 5, 85, 20);
			matafLabel8.setText("טלפון :");
		}
		return matafLabel8;
	}
	/**
	 * This method initializes matafLabel9
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel9() {
		if(matafLabel9 == null) {
			matafLabel9 = new mataf.types.MatafLabel();
			matafLabel9.setBounds(337, 131, 60, 20);
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
			matafLabel16.setBounds(337, 206, 43, 20);
			matafLabel16.setText("טלפון :");
			matafLabel16.setVisible(false);
			matafLabel16.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2_LABEL");
		}
		return matafLabel16;
	}
	/**
	 * This method initializes matafLabel24
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel24() {
		if(matafLabel24 == null) {
			matafLabel24 = new mataf.types.MatafLabel();
			matafLabel24.setBounds(682, 187, 92, 20);
			matafLabel24.setText("ת.לידה לאימות :");
			matafLabel24.setVisible(false);
			matafLabel24.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA_LABEL");
		}
		return matafLabel24;
	}
	/**
	 * This method initializes matafLabel25
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel25() {
		if(matafLabel25 == null) {
			matafLabel25 = new mataf.types.MatafLabel();
			matafLabel25.setBounds(337, 106, 105, 20);
			matafLabel25.setText("ת.הנפקת תעודה :");
			//matafLabel25.setVisible(false);
			matafLabel25.setDataName("HASS_LAK_PRATIM.HASG_TR_HANPAKA_LABEL");
		}
		return matafLabel25;
	}
	/**
	 * This method initializes matafLabel26
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel26() {
		if(matafLabel26 == null) {
			matafLabel26 = new mataf.types.MatafLabel();
			matafLabel26.setBounds(689, 237, 85, 20);
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
			matafLabel27.setBounds(689, 262, 85, 20);
			matafLabel27.setText("        ישוב :");
			matafLabel27.setVisible(false);
			matafLabel27.setDataName("HASS_LAK_PRATIM.HA_HOME_CITY_LABEL");
		}
		return matafLabel27;
	}
	/**
	 * This method initializes matafLabel28
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel28() {
		if(matafLabel28 == null) {
			matafLabel28 = new mataf.types.MatafLabel();
			matafLabel28.setBounds(337, 156, 36, 20);
			matafLabel28.setText("בית :");
			matafLabel28.setVisible(false);
			matafLabel28.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_NUM_LABEL");
		}
		return matafLabel28;
	}
	/**
	 * This method initializes matafLabel29
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel29() {
		if(matafLabel29 == null) {
			matafLabel29 = new mataf.types.MatafLabel();
			matafLabel29.setBounds(241, 156, 36, 20);
			matafLabel29.setText("דירה :");
			matafLabel29.setVisible(false);
			matafLabel29.setDataName("HASS_LAK_PRATIM.HA_HOME_APT_LABEL");
		}
		return matafLabel29;
	}
	/**
	 * This method initializes matafLabel30
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel30() {
		if(matafLabel30 == null) {
			matafLabel30 = new mataf.types.MatafLabel();
			matafLabel30.setBounds(337, 181, 43, 20);
			matafLabel30.setText("מיקוד :");
			matafLabel30.setVisible(false);
			matafLabel30.setDataName("HASS_LAK_PRATIM.HA_HOME_ZIP_LABEL");
		}
		return matafLabel30;
	}
	/**
	 * This method initializes matafLabel31
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel31() {
		if(matafLabel31 == null) {
			matafLabel31 = new mataf.types.MatafLabel();
			matafLabel31.setBounds(252, 206, 5, 20);
			matafLabel31.setText("-");
			matafLabel31.setVisible(false);
			matafLabel31.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2_SEPERATOR_LABEL");
		}
		return matafLabel31;
	}
	/**
	 * This method initializes matafLabel23
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel23() {
		if(matafLabel23 == null) {
			matafLabel23 = new mataf.types.MatafLabel();
			matafLabel23.setBounds(356, 30, 85, 20);
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
			matafRadioButton.setBounds(302, 30, 46, 20);
			matafRadioButton.setText("זכר");
			matafRadioButton.setVisible(true);
			matafRadioButton.setEnabled(false);
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
			matafRadioButton2.setBounds(223, 30, 60, 20);
			matafRadioButton2.setText("נקבה");
			matafRadioButton2.setVisible(true);
			matafRadioButton2.setEnabled(false);
		}
		return matafRadioButton2;
	}
	/**
	 * This method initializes matafLabel32
	 * 
	 * @return mataf.types.MatafLabel
	 */
	public mataf.types.MatafLabel getMatafLabel32() {
		if(matafLabel32 == null) {
			matafLabel32 = new mataf.types.MatafLabel();
			matafLabel32.setBounds(272, 5, 5, 20);
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
			matafLabel33.setBounds(337, 231, 99, 20);
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
			matafLabel34.setBounds(337, 256, 71, 20);
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
			matafLabel35.setBounds(210, 231, 118, 20);
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
			matafLabel36.setBounds(164, 256, 164, 20);
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
			matafLabel37.setBounds(314, 55, 127, 20);
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
			matafRadioButton3.setBounds(267, 55, 44, 20);
			matafRadioButton3.setText("אין");
			matafRadioButton3.setValue("1");
			matafRadioButton3.setEnabled(false);
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
			matafRadioButton4.setBounds(207, 55, 43, 20);
			matafRadioButton4.setText("יש");
			matafRadioButton4.setValue("2");
			matafRadioButton4.setEnabled(false);
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
	 * This method initializes matafLabel39
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel39() {
		if(matafLabel39 == null) {
			matafLabel39 = new mataf.types.MatafLabel();
			matafLabel39.setBounds(646, 137, 35, 20);
			matafLabel39.setText("JLabel");
			matafLabel39.setDataName("HASS_LAK_PRATIM.HA_COUNTRY_CODE");
		}
		return matafLabel39;
	}
	/**
	 * This method initializes matafLabel38
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel38() {
		if(matafLabel38 == null) {
			matafLabel38 = new mataf.types.MatafLabel();
			matafLabel38.setBounds(596, 162, 85, 20);
			matafLabel38.setText("JLabel");
			matafLabel38.setDataName("HASS_LAK_PRATIM.HA_TZ");
		}
		return matafLabel38;
	}
	/**
	 * This method initializes matafLabel40
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel40() {
		if(matafLabel40 == null) {
			matafLabel40 = new mataf.types.MatafLabel();
			matafLabel40.setBounds(596, 187, 85, 20);
			matafLabel40.setText("JLabel");
			matafLabel40.setDataName("HASS_LAK_PRATIM.HASG_TR_LEDA");
		}
		return matafLabel40;
	}
	/**
	 * This method initializes matafLabel41
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel41() {
		if(matafLabel41 == null) {
			matafLabel41 = new mataf.types.MatafLabel();
			matafLabel41.setBounds(517, 212, 164, 20);
			matafLabel41.setText("JLabel");
			matafLabel41.setDataName("GLSX_K86P_RESULT.HA_NAME_LAST");
		}
		return matafLabel41;
	}
	/**
	 * This method initializes matafLabel42
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel42() {
		if(matafLabel42 == null) {
			matafLabel42 = new mataf.types.MatafLabel();
			matafLabel42.setBounds(517, 237, 164, 20);
			matafLabel42.setText("JLabel");
			matafLabel42.setDataName("HASS_LAK_PRATIM.HA_HOME_STREET");
		}
		return matafLabel42;
	}
	/**
	 * This method initializes matafLabel43
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel43() {
		if(matafLabel43 == null) {
			matafLabel43 = new mataf.types.MatafLabel();
			matafLabel43.setBounds(517, 262, 164, 20);
			matafLabel43.setText("JLabel");
			matafLabel43.setDataName("HASS_LAK_PRATIM.HA_HOME_CITY");
		}
		return matafLabel43;
	}
	/**
	 * This method initializes matafLabel44
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel44() {
		if(matafLabel44 == null) {
			matafLabel44 = new mataf.types.MatafLabel();
			matafLabel44.setBounds(501, 137, 141, 20);
			matafLabel44.setText("JLabel");
			matafLabel44.setDataName("HASS_LAK_PRATIM.HA_COUNTRY_CODE_DESC");
		}
		return matafLabel44;
	}
	/**
	 * This method initializes matafLabel45
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel45() {
		if(matafLabel45 == null) {
			matafLabel45 = new mataf.types.MatafLabel();
			matafLabel45.setBounds(279, 5, 69, 20);
			matafLabel45.setText("JLabel");
			matafLabel45.setDataName("GLSX_K86P_RESULT.HA_PHONE_NUM1");
		}
		return matafLabel45;
	}
	/**
	 * This method initializes matafLabel46
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel46() {
		if(matafLabel46 == null) {
			matafLabel46 = new mataf.types.MatafLabel();
			matafLabel46.setBounds(244, 5, 26, 20);
			matafLabel46.setText("JLabel");
			matafLabel46.setDataName("GLSX_K86P_RESULT.HA_PHONE_AREA1");
		}
		return matafLabel46;
	}
	/**
	 * This method initializes matafLabel47
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel47() {
		if(matafLabel47 == null) {
			matafLabel47 = new mataf.types.MatafLabel();
			matafLabel47.setBounds(210, 106, 118, 20);
			matafLabel47.setText("JLabel");
			matafLabel47.setDataName("HASS_LAK_PRATIM.HASG_TR_HANPAKA");
		}
		return matafLabel47;
	}
	/**
	 * This method initializes matafLabel48
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel48() {
		if(matafLabel48 == null) {
			matafLabel48 = new mataf.types.MatafLabel();
			matafLabel48.setBounds(210, 131, 118, 20);
			matafLabel48.setText("JLabel");
			matafLabel48.setDataName("GLSX_K86P_RESULT.HA_NAME_FIRST");
		}
		return matafLabel48;
	}
	/**
	 * This method initializes matafLabel49
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel49() {
		if(matafLabel49 == null) {
			matafLabel49 = new mataf.types.MatafLabel();
			matafLabel49.setBounds(302, 156, 26, 20);
			matafLabel49.setText("JLabel");
			matafLabel49.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_NUM");
		}
		return matafLabel49;
	}
	/**
	 * This method initializes matafLabel50
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel50() {
		if(matafLabel50 == null) {
			matafLabel50 = new mataf.types.MatafLabel();
			matafLabel50.setBounds(291, 156, 10, 20);
			matafLabel50.setText("JLabel");
			matafLabel50.setDataName("HASS_LAK_PRATIM.HA_HOME_ST_SUB_NUM");
		}
		return matafLabel50;
	}
	/**
	 * This method initializes matafLabel51
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel51() {
		if(matafLabel51 == null) {
			matafLabel51 = new mataf.types.MatafLabel();
			matafLabel51.setBounds(210, 156, 26, 20);
			matafLabel51.setText("JLabel");
			matafLabel51.setDataName("HASS_LAK_PRATIM.HA_HOME_APT");
		}
		return matafLabel51;
	}
	/**
	 * This method initializes matafLabel52
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel52() {
		if(matafLabel52 == null) {
			matafLabel52 = new mataf.types.MatafLabel();
			matafLabel52.setBounds(259, 181, 69, 20);
			matafLabel52.setText("JLabel");
			matafLabel52.setDataName("HASS_LAK_PRATIM.HA_HOME_ZIP");
		}
		return matafLabel52;
	}
	/**
	 * This method initializes matafLabel53
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel53() {
		if(matafLabel53 == null) {
			matafLabel53 = new mataf.types.MatafLabel();
			matafLabel53.setBounds(259, 206, 69, 20);
			matafLabel53.setText("JLabel");
			matafLabel53.setDataName("HASS_LAK_PRATIM.HA_PHONE_NUM2");
		}
		return matafLabel53;
	}
	/**
	 * This method initializes matafLabel54
	 * 
	 * @return mataf.types.MatafLabel
	 */
	private mataf.types.MatafLabel getMatafLabel54() {
		if(matafLabel54 == null) {
			matafLabel54 = new mataf.types.MatafLabel();
			matafLabel54.setBounds(226, 206, 26, 20);
			matafLabel54.setText("JLabel");
			matafLabel54.setDataName("HASS_LAK_PRATIM.HA_PHONE_AREA2");
		}
		return matafLabel54;
	}
}
