package mataf.slika.operations;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;

import mataf.general.operations.MatafOperationStep;
import mataf.services.reftables.RefTables;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SetMutavDataToPrintOpStep extends MatafOperationStep {

	public static final int MAX_CHECKS_IN_1_PAGE_PRINT = 7;
	
	/**
	 * Constructor for SetDataToPrintOpStep.
	 */
	public SetMutavDataToPrintOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// NOTE!
		// The data that is bing map in this operation will be used in transaction
		// containing more then 7 checks
		
		String systemBranch = (String) getValueAt("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		String beneficiaryBranchId = (String) getValueAt("BeneficiaryBranchId");
		String asmachta = (String) getValueAt("Asmachta");
		boolean isAhzara = isAhzara();
		boolean isSysBranchEqualsToClerkBranch = isSysBankEqualsToClerkBank();
		
		// map mutav data  from journal record
		// also for 50 checks
		mapData("CZAP_LAZER_SCM_mapper");
		
		// map header of records in journal
		mapData("CZAP_LAZER_HDR1_mapper");
		setDateAndTime();
		setMishmeretDesc();
		mapData("CZAP_LAZER_T110_mapper");
		
        // page number 1 : need to be set according to number of checks
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_DAF", "01");

        // missing fields : need to be handled
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.TL_SW_MVTL_DESC", " ");
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_OTEK", "");

		// map mutav data from journal record
		mapData("CZAP_LAZER_110_mapper");
					
		// map mutav data  from journal record
		mapData("CZAP_LAZER_T110_HALV_mapper");
		
		if(systemBranch.equals(beneficiaryBranchId)) {
			// map header of records in journal
			mapData("CZAP_T110_SNIF_mapper");
		}
		
		if((asmachta!=null) && (asmachta.length()>0)) {
			mapData("CZAP_LAZER_T110_ASM_mapper");
		}
		
		// kod ichud is 0 so there is no need to activate mapper for CZAP_T110_KOD_ICHUD
		
		if(isAhzara) {
			if(isSysBranchEqualsToClerkBranch) {
				mapData("CZAP_T110_CH_RTRN_mapper");
			} else {
				mapData("CZAP_T110_CH_RTRN_PG_mapper");
			}
		}


		return RC_OK;
	}
	
	private void mapData(String mapperName) throws DSEInvalidRequestException, DSEInvalidClassException, DSEInvalidArgumentException {
		DataMapperFormat dmf = (DataMapperFormat) getFormat(mapperName);
		dmf.mapContents(getContext(), getContext());
	}
	
	private boolean isSysBankEqualsToClerkBank() throws Exception {
		String bankOfSnifHachzara = (String) getBankBySnif("BeneficiaryBranchId");
		String bankOfSnifMaarechet = (String) getBankBySnif("GLSG_GLBL.GKSG_KEY.GL_SNIF");
		if(bankOfSnifHachzara.equals(bankOfSnifMaarechet))
			return true;
		else
			return false;
	}
	
	private String getBankBySnif(String snifFieldName) throws Exception {
		String snif = (String) getValueAt(snifFieldName);
		RefTables refTablesSrvs = getRefTablesService();
		IndexedCollection resultsInIcoll = refTablesSrvs.getByKey("GLST_SNIF", "GL_SNIF", snif);
		String ifyunSnif = (String) ((KeyedCollection) resultsInIcoll.getElementAt(0)).getValueAt("GL_IFYUN_SNIF");
		return ifyunSnif.substring(0,1);
	}
	
	private boolean isAhzara() throws DSEObjectNotFoundException {
		String cz_sw_ch_ahzara_str = (String) getValueAt("CZSJ_REC.CZ_SW_CH_AHZARA");
		int cz_sw_ch_ahzara = 0;
		if((cz_sw_ch_ahzara_str!=null) && (cz_sw_ch_ahzara_str.length()>0)) {
			cz_sw_ch_ahzara = Integer.parseInt(cz_sw_ch_ahzara_str);
		}
		
		return cz_sw_ch_ahzara==1;
	}
	
	/** Because the date and time is being saved differently
	 * in the printing data model, we need to
	 * format this data hard coded.
	 */
	private void setDateAndTime() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		String date = (String) getValueAt("CZSJ_REC.GL_TR_MARECHET");
		String time = (String) getValueAt("CZSJ_REC.GL_SHAA_MARECHET");
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_DD1", date.substring(0,2));
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_MM1", date.substring(2,4));
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_YYYY1", date.substring(4));
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_HH2", time.substring(0,2));
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_MM2", time.substring(2,4));
	}
	
	private void setMishmeretDesc() throws Exception {
		String mishmeret = (String) getValueAt("CZSJ_REC.GL_MISHMERET");
		String mishmeretDesc = (String) getRefTablesService().getByKey("TI_MISHMERET_TABLE", "TI_MISHMERET", mishmeret, "TI_TEUR_MISHMERET");
		setValueAt("CZSP_LAZER_T110.TLSP_LAZER_HDR1.GL_MISHMERET_TOFES_DESC", mishmeretDesc);
	}
}
