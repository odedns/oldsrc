package mataf.halbanathon.operationsteps;

import java.util.Enumeration;

import com.ibm.dse.base.DataField;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.FieldFormat;
import com.ibm.dse.base.KeyedCollection;

import mataf.general.operations.MatafOperationStep;

/**
 *
 * 
 * @author Eyal Ben ze'ev. Creation Date : (14/11/2003 11:27:58).  
 */
public class PreparePirteyLakoachMizdamen extends MatafOperationStep {

	private DataMapperFormat mapper;

	public int executeOp() throws Exception {
		assign_GKAI_HDR();
		assign_HAAI_INQ_SHIDUR();
		assign_GL_TRANS_ID();
		assign_HAAI_MIZD_PRATIM();
		assign_HA_SW_IMUT_MALAM();

		KeyedCollection kc = (KeyedCollection) getElementAt("HASI_MIZDAMEN_PRATIM");
		Enumeration enum = kc.getEnumeration();
		Object dataElement = null;
		String value = null;
		FieldFormat fe = null;

		String nullValue = (String) getValueAt("HASI_MIZDAMEN_PRATIM.HA_SUG_KESHER_CH_KG");
		if (nullValue == null) {
			System.out.println("the value at HASI_MIZDAMEN_PRATIM.HA_SUG_KESHER_CH_KG was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_MIZDAMEN_PRATIM.HA_SUG_KESHER_CH_KG", "0");
		}

		nullValue = (String) getValueAt("HASI_MIZDAMEN_PRATIM.GL_FILLER");
		if (nullValue == null) {
			System.out.println("the value at HASI_MIZDAMEN_PRATIM.GL_FILLER was null and handled locally !!!! this must be fixed");
			setValueAt("HASI_MIZDAMEN_PRATIM.GL_FILLER", " ");
		}

		while (enum.hasMoreElements()) {
			dataElement = enum.nextElement();
			if (dataElement instanceof DataField) {
				value = (String) ((DataField) dataElement).getValue();
				if (value == null) {
					System.out.println("the value of DataElement:" + dataElement + "was null !!!");
				}
			}

		}
		return 0;
	}

	/**
	 * Method assign_GKAI_HDR.
	 */
	public void assign_GKAI_HDR() throws Exception {
		//		(3)	 GKAI_HDR assignment is handled by GKSI_HDR format here we will assign just
		//		specific fields
		setValueAt("GKSI_HDR.GL_PEULA", "Z410");
	}

	/**
	 * Method assign_GL_TRANS_ID.
	 */
	public void assign_GL_TRANS_ID() throws Exception {
		//		(5)	Sets the Values in SHEM_SHEDER (GL_TRANS_ID) & SHEM_TSHUVA
		setValueAt("GKSI_HDR.GL_TRANS_ID", "O361");
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_TRANS_ID", "HM");
	}

	/**
	 * Method assign_HAAI_MIZD_PRATIM.
	 */
	public void assign_HAAI_MIZD_PRATIM() throws Exception {
		//	(7)	 HAAI_MIZD_PRATIM assignment
		mapper = (DataMapperFormat) getFormat("HAAI_MIZD_PRATIM_mapFormat");
		mapper.mapContents(getContext(), getContext());
	}

	/**
	 * Method assign_HA_SW_IMUT_MALAM.
	 */
	public void assign_HA_SW_IMUT_MALAM() throws Exception {
		//	(8) assign HA_SW_IMUT_MALAM field
		String HA_SW_IMUT_MALAM = (String) getValueAt("HASX_PIRTEY_CHESHBON.HA_SW_IMUT_MALAM");
		setValueAt("HASI_MIZDAMEN_PRATIM.HA_SW_IMUT_MALAM", HA_SW_IMUT_MALAM);
	}

	/**
	 * Method assign_HAAI_INQ_SHIDUR.
	 */
	public void assign_HAAI_INQ_SHIDUR() throws Exception {
		//		this is not in Avi's doc ...(7)	HAAI_INQ_SHIDUR assignment
		mapper = (DataMapperFormat) getFormat("HAAI_INQ_SHIDUR_mapFormat");
		mapper.mapContents(getContext(), getContext());
	}

}
