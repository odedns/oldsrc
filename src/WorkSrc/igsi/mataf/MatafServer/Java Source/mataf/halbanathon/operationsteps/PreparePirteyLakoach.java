package mataf.halbanathon.operationsteps;

import java.util.Enumeration;

import mataf.general.operations.MatafOperationStep;

import com.ibm.dse.base.DataField;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FieldFormat;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.KeyedCollectionFormat;
import com.ibm.dse.base.RecordFormat;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (17/11/2003 17:34:56).  
 */
public class PreparePirteyLakoach extends MatafOperationStep {
	private String MIVNE_EZER = "HASX_PIRTEY_CHESHBON";
	private String MIVNE_SHIDUR = "HASI_PRATIM_SHIDUR";
	private String originValue;

	private DataMapperFormat mapper;

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		handleDataElements();
		return 0;
	}

	public void assign_HA_MIZDAMEN_CHADASH() throws Exception {
		//		(3)		
		String HA_MIZDAMEN_CHADASH = (String) getValueAt(MIVNE_EZER + ".HA_MIZDAMEN_CHADASH");
		if (HA_MIZDAMEN_CHADASH == null) {
			setValueAt(MIVNE_SHIDUR + ".HA_MIZDAMEN_CHADASH", "0");
		} else if (HA_MIZDAMEN_CHADASH.equals("1")) {
			setValueAt(MIVNE_SHIDUR + ".HA_MIZDAMEN_CHADASH", HA_MIZDAMEN_CHADASH);
		}
	}

	public void assign_HA_SW_IMUT_MALAM() throws Exception {
		//		(4)		
		String HA_SW_IMUT_MALAM = (String) getValueAt(MIVNE_EZER + ".HA_SW_IMUT_MALAM");
		setValueAt(MIVNE_SHIDUR + ".HA_SW_IMUT_MALAM", HA_SW_IMUT_MALAM);
	}

	public void assign_GL_TRANS_ID() throws Exception {
		//		(5)	Sets the Valus in SHEM_SHEDER (GL_TRANS_ID) & SHEM_TSHUVA
		setValueAt("GKSI_HDR.GL_TRANS_ID", "O365");
		// This assignment should be handled in assignment but there is a problem
		// and Avi should give us an answer....
		setValueAt("GKSI_HDR.GL_PEULA", "Z410");
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_TRANS_ID", "HS");
	}

	public void assignAccordingTo_SW_SCREEN() throws Exception {
		//		(6)	Checking SW_SCREEN (SW_DO in Avi's doc) field and its following assignment
		mapper = (DataMapperFormat) getFormat("HAAI_PRT_CHEL_SHIDUR_mapFormat");
		mapper.mapContents(getContext(), getContext());

		String SW_SCREEN = (String) getValueAt("HelpData.SW_SCREEN");
		if (SW_SCREEN.equals("2")) {
			mapper = (DataMapperFormat) getFormat("HAAI_PRT_MALE_SHIDUR_mapFormat");
			mapper.mapContents(getContext(), getContext());
		}

		//		(7)	HAAI_INQ_SHIDUR assignment
		mapper = (DataMapperFormat) getFormat("HAAI_INQ_SHIDUR_mapFormat");
		mapper.mapContents(getContext(), getContext());
	}

	public void handleDataElements() throws Exception {
		assign_HA_MIZDAMEN_CHADASH();
		assign_HA_SW_IMUT_MALAM();
		assign_GL_TRANS_ID();
		assignAccordingTo_SW_SCREEN();

		//		(8)
		String sugCheshbonLakoach = (String) getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER");
		String cheshbonNegdi = (String) getValueAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
		String noseAnglit = (String) getValueAt("GLSX_K86P_PARAMS.GL_NOSE_PEULA");
		if ((sugCheshbonLakoach != null) && !sugCheshbonLakoach.equals("")) {
			String HA_SNIF_ACHER = (String) getValueAt("HASS_LAKOACH_SUG.HA_SNIF_ACHER");
			setValueAt(MIVNE_SHIDUR + ".HA_SNIF", HA_SNIF_ACHER);

			String HA_SCH_ACHER = (String) getValueAt("HASS_LAKOACH_SUG.HA_SCH_ACHER");
			setValueAt(MIVNE_SHIDUR + ".HA_SCH", HA_SCH_ACHER);

			String HA_CH_ACHER = (String) getValueAt("HASS_LAKOACH_SUG.HA_CH_ACHER");
			setValueAt(MIVNE_SHIDUR + ".HA_CH", HA_CH_ACHER);

		} else if ((cheshbonNegdi != null) && (!cheshbonNegdi.equals(""))) {
			setValueAt(MIVNE_SHIDUR + ".HA_CH", cheshbonNegdi);

			//		Additional checks should be written for "Teller" and "Machatz"
			if (noseAnglit.equalsIgnoreCase("M")) {
			} else if (noseAnglit.equalsIgnoreCase("T")) {

			} else if (noseAnglit.equalsIgnoreCase("W")) {

				//		(9)
			}
		}
		if ((!noseAnglit.equalsIgnoreCase("M")) && (!noseAnglit.equalsIgnoreCase("T")) && (!noseAnglit.equalsIgnoreCase("W"))) {
			String bankMaarechet = (String) getValueAt("GLSG_GLBL.GL_BANK");
			String bankMasach = (String) getValueAt("HASS_LAKOACH_SUG.HA_BANK");
			if ((bankMaarechet != null) && (!bankMaarechet.equals(bankMasach))) {
				setValueAt("GKSI_HDR.GL_SNIF", "0");
			}

			setValueAt(MIVNE_SHIDUR + ".HA_DRISAT_DIVUACH", "0");

			String peulaMemukenet = (String) getValueAt("HASX_PIRTEY_CHESHBON.HA_PEULA_MEMUKENET");
			if ((peulaMemukenet != null) && !peulaMemukenet.equals("")) {
				if ((peulaMemukenet.equals("2")) || (peulaMemukenet.equals("3"))) {
					originValue = (String) getValueAt("GLSF_GLBL.GL_MISPAR_TAHANA");
					setValueAt(MIVNE_SHIDUR + ".HA_MISPAR_TAHANA", originValue);

					originValue = (String) getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_TL");
					setValueAt(MIVNE_SHIDUR + ".HA_SIDURI_TAHANA", originValue);

				}

				if (peulaMemukenet.equals("3")) {
					setValueAt(MIVNE_SHIDUR + ".HA_PEULA_MEMUKENET", "2");
				}
			}

			String kodNoseIska = (String) getValueAt("GLSX_K86P_PARAMS.HA_KOD_NOSE_ISKA");
			if (kodNoseIska.equals("1")) {
				originValue = (String) getValueAt("GLSF_GLBL.GL_SIDURI_TAHANA_TL");
				setValueAt("GKSI_HDR_CONT.GL_SIDURI_TAHANA", originValue);
			}

		}
		// Additional fields that currently are null and should be handled after Avi's reply
		// THIS CODE SHOULD BE REMOVE AS SOON AS WE FIX THE NULLS PROBLEM !!!!
		// (that is why I used System.out....)

		String nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_STREET");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_STREET was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_STREET", " ");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ST_NUM");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_ST_NUM was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ST_NUM", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ST_SUB_NUM", " ");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_APT");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_APT was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_APT", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_CITY");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_CITY was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_CITY", " ");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ZIP");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_ZIP was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_ZIP", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_AREA2");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_PHONE_AREA2 was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_AREA2", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_NUM2");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_PHONE_NUM2 was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_NUM2", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_SEX");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_SEX was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_SEX", " ");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_KOD_MAAMAD");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_KOD_MAAMAD was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_KOD_MAAMAD", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HASG_TR_LEDA");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HASG_TR_LEDA was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HASG_TR_LEDA", "00000000");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HASG_TR_HANPAKA");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HASG_TR_HANPAKA was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HASG_TR_HANPAKA", "00000000");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_SUG_KESHER_CH_KG");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_SUG_KESHER_CH_KG was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_SUG_KESHER_CH_KG", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HOME_CITY_SEMEL");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HOME_CITY_SEMEL was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HOME_CITY_SEMEL", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_MIZDAMEN_CHADASH");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_MIZDAMEN_CHADASH was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_MIZDAMEN_CHADASH", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_HATZHARA_CHATAM");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_HATZHARA_CHATAM was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_HATZHARA_CHATAM", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_TZ");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_TZ was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_TZ", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_NUM1");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_PHONE_NUM1 was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_NUM1", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_AREA1");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_PHONE_AREA1 was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_PHONE_AREA1", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_COUNTRY_CODE");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_COUNTRY_CODE was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_COUNTRY_CODE", "0");
		}

		nullValue = (String) getValueAt("GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET");
		if (nullValue == null) {
			System.out.println("the value at GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET was null and handled locally !!!! this must be fixed");
			setValueAt("GKSI_HDR_CONT.GL_SAMCHUT_MEASHERET", "0");
		}
		nullValue = (String) getValueAt("GKSI_HDR_CONT.GL_ZIHUI_MEASHER");
		if (nullValue == null) {
			System.out.println("the value at GKSI_HDR_CONT.GL_ZIHUI_MEASHER was null and handled locally !!!! this must be fixed");
			setValueAt("GKSI_HDR_CONT.GL_ZIHUI_MEASHER", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.GL_FILLER");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.GL_FILLER was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.GL_FILLER", " ");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_SHEM_MUTAV_B_AHER");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_SHEM_MUTAV_B_AHER was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_SHEM_MUTAV_B_AHER", "0");
		}

		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_SW_HATSHARAT_NENE");
		if (nullValue == null) {
			System.out.println("the value at HASI_PRATIM_SHIDUR.HA_SW_HATSHARAT_NENE was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_PRATIM_SHIDUR.HA_SW_HATSHARAT_NENE", "0");
		}
		
		nullValue = (String) getValueAt("HASI_PRATIM_SHIDUR.HA_CH_BANK_AHER");
				if (nullValue == null) {
					System.out.println("the value at HASI_PRATIM_SHIDUR.HA_CH_BANK_AHER was null and handled locally !!!! this must be fixed");
					setValueAt("HASI_PRATIM_SHIDUR.HA_CH_BANK_AHER", "0");
				}

		KeyedCollection kc = (KeyedCollection) getElementAt("HASI_PRATIM_SHIDUR");
		Enumeration enum = kc.getEnumeration();
		Object dataElement = null;
		String value = null;

		while (enum.hasMoreElements()) {
			dataElement = enum.nextElement();
			if (dataElement instanceof DataField) {
				value = (String) ((DataField) dataElement).getValue();
				if (value == null) {
					System.out.println("the value of DataElement:" + dataElement + "was null !!!");
				}
			}

		}

	}

}
