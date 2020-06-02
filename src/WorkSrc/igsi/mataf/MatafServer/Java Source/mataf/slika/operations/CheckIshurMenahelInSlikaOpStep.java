package mataf.slika.operations;

import mataf.data.VisualDataField;
import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CheckIshurMenahelInSlikaOpStep extends MatafOperationStep {

	public static final String KOD_TIPUL_ISHUR_MENAHEL_1 = "M";
	public static final String KOD_TIPUL_ISHUR_MENAHEL_2 = "A";
	
	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String kodTipul = (String) getValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL");
		boolean shouldRequestIshurMenahel = Boolean.valueOf((String) getContext().getValueAt("shouldRequestIshurMenahel")).booleanValue();
		if(kodTipul.equals(KOD_TIPUL_ISHUR_MENAHEL_1) || kodTipul.equals(KOD_TIPUL_ISHUR_MENAHEL_2) || shouldRequestIshurMenahel) {
			((VisualDataField) getElementAt("IshurMenahelButton")).setIsEnabled(Boolean.TRUE);
			((VisualDataField) getElementAt("IshurMenahelMeruchakButton")).setIsEnabled(Boolean.TRUE);
			((VisualDataField) getElementAt("VadeNetunimButton")).setIsEnabled(Boolean.FALSE);
			setValueAt("trxORData.trxUuid", new String(new Long(System.currentTimeMillis()).toString()));
			setValueAt("trxORData.trxId","T410");
			setValueAt("trxORData.trxName","סליקה אלקטרונית");		
			setValueAt("trxORData.viewName","mataf.slika.panels.ConclusionBasePanel");
			setValueAt("trxORData.ctxName","slikaCtx");			
			
			return RC_ERROR;
		} else {
			((VisualDataField) getElementAt("KlotHamchaotButton")).setIsEnabled(Boolean.TRUE);
			return RC_OK;
		}
	}

}
