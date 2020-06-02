package mataf.slika.journal.operations;

import java.util.Enumeration;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataElement;
import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.IndexedCollection;
import com.ibm.dse.base.KeyedCollection;
import com.ibm.dse.services.jdbc.JournalService;

import mataf.general.operations.MatafOperationStep;
import mataf.services.electronicjournal.MatafJournalService;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/10/2003 16:21:26).  
 */
public class ChotemCtxSetter4SlikaJournalOpStep extends MatafOperationStep {
			
	private JournalService journal;

	/**
	 * Constructor for SlikaJournalContextSetterOpStep.
	 */
	public ChotemCtxSetter4SlikaJournalOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String journalServiceName = null;
		String entityName = null;
		String formatName = null;
		IndexedCollection checksIcoll = null;
		KeyedCollection currentRecord = null;
		
		try {
			journalServiceName = (String) getParams().getValueAt(SERVICE_NAME_ATT_NAME);
			entityName = (String) getParams().getValueAt(JOURNAL_ENTITY_NAME_ATT_NAME);
			formatName = (String) getParams().getValueAt(JOURNAL_FORMAT_NAME_ATT_NAME);
			
			journal = MatafJournalService.getJournalService(getContext(), journalServiceName, entityName); 
			checksIcoll = (IndexedCollection) getElementAt("CZSS_T110_LIST");						
			for(int counter=0 ; counter<checksIcoll.size() ; counter++) {
				currentRecord = (KeyedCollection) checksIcoll.getElementAt(counter);
				if(isCheckRecordEmpty(currentRecord)) // finish writting checks 2 journal
					break;
				
				mapContents(currentRecord, (KeyedCollection) getElementAt("CZSJ_REC"));
				mapContents("CZAJ_ADD_CHOTEM_MapperFmt");
				setDescFields();
				mapContentsForPrinter(counter);
								
				
				// writting 2 journal
				journal.addRecord(getContext(), formatName);
				journal.commit();
			}
			
			return RC_OK;
		} catch(Exception ex) {
			ex.printStackTrace();
			addToErrorListFromXML();
			return RC_ERROR;
		} finally {
			MatafJournalService.returnJournalService(journal);
		}
			
	}
			
	private void mapContents(String mapperName) 
				throws DSEInvalidRequestException, DSEInvalidClassException, DSEInvalidArgumentException {
		DataMapperFormat dmf = (DataMapperFormat) getFormat(mapperName);
		dmf.mapContents(getContext(), getContext());
	}
	
	/**
	 * Map between the values of the source keyedCollection and the target keyedCollection.
	 */
	private void mapContents(KeyedCollection source, KeyedCollection target) 
								throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		Enumeration enum = source.getEnumeration();
		DataElement sourceElement = null;
		while(enum.hasMoreElements()) {
			sourceElement = (DataElement) enum.nextElement();
			target.trySetValueAt(sourceElement.getName(), sourceElement.getValue());
		}
	}
		
	private boolean isCheckRecordEmpty(KeyedCollection checkRecord) throws DSEObjectNotFoundException {
		return ((String) checkRecord.getValueAt("CH_MISPAR_CHEQ")).length()==0;
	}
	
	private void setDescFields() throws Exception {
		// set bank desc
		setDescField("GLST_BANK", "GL_BANK", (String) getValueAt("CZSJ_REC.CH_BANK_CHOTEM"), "GL_SHEM_BANK", "CZSJ_REC.CH_BANK_CHOTEM_DESC");
		// set snif desc
		setDescField("GLST_SNIF", "GL_SNIF", (String) getValueAt("CZSJ_REC.CH_SNIF_CHOTEM"), "GL_SHEM_SNIF", "CZSJ_REC.CH_SNIF_CHOTEM_DESC");
		// set makor hafkada desc 
		setDescField("TLST_MAKOR_HAFKADA", "TL_MAKOR_HAFKADA", (String) getValueAt("CZSJ_REC.CH_SUG_HAFKADA"), "TL_SHEM_MAKOR_HFK", "CZSJ_REC.CH_SUG_HAFKADA_DESC");
	}
	
	private void setDescField(String tableName, String keyColumnName, String keyValue, String valueColumnName, String fieldName2set) 
									throws Exception {
		IndexedCollection iColl = getRefTablesService().getByKey(tableName, keyColumnName, keyValue);
		String value2set = "";
		if(iColl.size()>0) {
			value2set = (String) ((KeyedCollection)iColl.getElementAt(0)).getValueAt(valueColumnName);
		}
		setValueAt(fieldName2set, value2set);
	}	

	private void mapContentsForPrinter(int index) throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		IndexedCollection CZSP_TOFES_LINE1 = (IndexedCollection) getElementAt("CZSP_LAZER_T110.CZSP_TOFES_LINE1");
		KeyedCollection CZSP_TOFES_LINE = (KeyedCollection) CZSP_TOFES_LINE1.getElementAt(index);
		KeyedCollection CZSJ_REC = (KeyedCollection) getElementAt("CZSJ_REC");
		CZSP_TOFES_LINE.setValueAt("TL_MISPAR_CHK", CZSJ_REC.getValueAt("CH_MISPAR_CHEQ"));
		CZSP_TOFES_LINE.setValueAt("TL_SCHUM", CZSJ_REC.getValueAt("CH_SCHUM_CHEQ"));
		CZSP_TOFES_LINE.setValueAt("TL_CH", CZSJ_REC.getValueAt("CH_CH_CHOTEM"));
		CZSP_TOFES_LINE.setValueAt("TL_SNIF", CZSJ_REC.getValueAt("CH_SNIF_CHOTEM"));
		CZSP_TOFES_LINE.setValueAt("CH_SNIF_S_B", CZSJ_REC.getValueAt("CH_SNIF_S_B"));
		CZSP_TOFES_LINE.setValueAt("TL_BANK", CZSJ_REC.getValueAt("CH_BANK_CHOTEM"));
	}
	
}
