package mataf.general.operations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mataf.utils.MatafUtilities;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Hashtable;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.base.Vector;
import com.ibm.dse.services.jdbc.JDBCTable;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class SetReturnParamsAccordingToGlstGlblOpStep extends MatafOperationStep {

	public static final String MATI_DANACH_TYPE = "1";
	public static final String MATI_MATBEA = "97";
	
	public int executeOp() throws Exception {
		
		// map the single-row-table named GLST_GLBL to a KeyedCollection
		KeyedCollection mapedTableRow = 
					(KeyedCollection) getRefTablesService().getAll("GLST_GLBL").getElementAt(0);
		
		// Set commissionId & subCommissionId according to danachType
		String commissionId;
		String subCommissionId;
		String danachType = getValueAt("GiluyNaotData.danachType").toString();
		if(danachType.equals(MATI_DANACH_TYPE)) {
			commissionId = (String) mapedTableRow.getValueAt("GL_ZIHUY_AMLA_DANACH");
			subCommissionId = (String) mapedTableRow.getValueAt("GL_TAT_AMLA_DANACH");
		} else { // mathch danach type
			commissionId = (String) mapedTableRow.getValueAt("GL_ZIHUY_DANACH_MAT");
			subCommissionId = (String) mapedTableRow.getValueAt("GL_TAT_DANACH_MAT");
		}
		
		// commit a query to TLST_HIYUV_AM_SCHUM
		JDBCTable tlstHiyuvSchumTableService = (JDBCTable) getService("TLST_HIYUV_AM_SCHUM");
		String sqlCondition = buildSqlCondition(commissionId, subCommissionId);
		Hashtable queryResult = 
				(Hashtable) tlstHiyuvSchumTableService.retrieveRecordsMatching(sqlCondition).elementAt(0);
		
		setValueAt("GiluyNaotData.commissionAmount", (String) queryResult.get("TL_HIYUV_AMLA_SCHSHI"));
		setHiyuvAmlaMatbea(queryResult);
		setMatbeaDesc(queryResult);
		setMsgAccordingToDanachType(danachType);
		
		return RC_OK;
	}
	
	private void setMsgAccordingToDanachType(String danachType) throws Exception {
		
		StringBuffer message = new StringBuffer();
		
		if(danachType.equals(MATI_DANACH_TYPE)) {
			message.append((String) getRefTablesService().getByKey("GLST_SGIA", "GL_ZIHUY_HODAA", "GL940", "GL_HODAA"));
		} else {
			message.append((String) getRefTablesService().getByKey("GLST_SGIA", "GL_ZIHUY_HODAA", "ML990", "GL_HODAA"));
		}
		
		message.append(" ").append((String) getValueAt("GiluyNaotData.commissionAmount"));
		message.append(" ").append((String) getValueAt("GiluyNaotData.GL_MATBEA_DESC"));
		
		MatafUtilities.addBusinessMessage(getContext(), message.toString());
	}

	private void setMatbeaDesc(Hashtable queryResult) throws Exception {
		String hiyuvAmlaMatbea = (String) queryResult.get("TL_HIYUV_AMLA_MATBEA");
		
		String matbeaDesc = (String) getRefTablesService().getByKey("MZST_MATBEA_SHEM", 
																	"GL_MATBEA", 
																	hiyuvAmlaMatbea, 
																	"MZ_SHEM_MATBEA_KATZAR");
																
		setValueAt("GiluyNaotData.GL_MATBEA_DESC", matbeaDesc);
	}

	
	private String buildSqlCondition(String commissionId, String subCommissionId) 
								throws DSEObjectNotFoundException, ParseException {
									
		String systemBank = (String) getValueAt("GLSG_GLBL.GL_BANK");
		String buisnessDate = getFormatedBuisnessDate();
		
		StringBuffer sqlCondition = new StringBuffer();
		sqlCondition.append(" TL_HIYUV_AMLA_ZIHUI='").append(commissionId);
		sqlCondition.append("' AND TL_KOD_AMLA='").append(subCommissionId);
		sqlCondition.append("' AND TL_BANK='").append(systemBank);
		sqlCondition.append("' AND (DATEDIFF(dd, '");
		sqlCondition.append(buisnessDate).append("', TL_TARICH_TOKEF) < 1)");
		
		return sqlCondition.toString();
	}
	
	private String getFormatedBuisnessDate() throws DSEObjectNotFoundException, ParseException {
		SimpleDateFormat sourcePattern = new SimpleDateFormat("ddmmyyyy");
		SimpleDateFormat targetPattern = new SimpleDateFormat("mm/dd/yyyy");
		String GL_TR_ASAKIM = (String) getValueAt("GLSG_GLBL.GL_TR_ASAKIM");
		Date date = sourcePattern.parse(GL_TR_ASAKIM);
		return targetPattern.format(date);
	}		
	
	private void setHiyuvAmlaMatbea(Hashtable queryResult) 
					throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		String hiyuvAmlaMatbea = (String) queryResult.get("TL_HIYUV_AMLA_MATBEA");
		if(hiyuvAmlaMatbea.equals(MATI_MATBEA)) {
			setValueAt("GiluyNaotData.GL_MATBEA", "0");
		} else {
			setValueAt("GiluyNaotData.GL_MATBEA", hiyuvAmlaMatbea);
		}
	}

}
