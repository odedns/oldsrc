package mataf.slika.operations;

import mataf.general.operations.*;

import com.ibm.dse.base.OperationStep;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (04/09/2003 18:17:42).  
 */
public class HostReplyTestParamsSetterOpStep extends MatafOperationStep {

	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		// test params 4 hostMessageSetterOpStep
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_OFI_SHEDER", "S");
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_NOSE_SGIA", "A");
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_SGIA", "001");
		setValueAt("HostHeaderReplyData.GKSR_HDR_CONT.GL_HODAA", "");
		
		// test params 4 HandleAccountBalanceKodTipulOpStep
		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL", "L");
//		setValueAt("HostHeaderReplyData.GKSR_HDR.GL_KOD_TIPUL", "M");
		
		// test params 4 AccountBalanceViewSetterOpStep
		setValueAt("AccountBalanceHostReplyData.GL_MAZAV_LAKOACH", ""+CheckMazavLakochHostErrorOpStep.HOST_ERROR);
		
		// test params 4 MoneyLaunderyCheckOpStep, 
		setValueAt("AccountBalanceHostReplyData.GKSG_IFYUN", "81ג9ל#ר");
		
		// test params 4 netuney itrot
		setValueAt("AccountBalanceHostReplyData.GL_ITRA_MESHICHA", "1234.33");
		setValueAt("AccountBalanceHostReplyData.GL_ITRA_ADKANIT", "-4321.08");
		setValueAt("AccountBalanceHostReplyData.GL_ITRA_KLALIT", "4444444.99");
		setValueAt("AccountBalance.OwnerName", "אדון תושיה");
		
		return RC_OK;
	}

}
