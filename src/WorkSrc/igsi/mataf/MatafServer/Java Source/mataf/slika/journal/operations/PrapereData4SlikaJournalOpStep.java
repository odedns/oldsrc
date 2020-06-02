package mataf.slika.journal.operations;

import java.text.DecimalFormat;

import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DSEInvalidRequestException;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.DataMapperFormat;

import mataf.general.operations.MatafOperationStep;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class PrapereData4SlikaJournalOpStep extends MatafOperationStep {

	/**
	 * Constructor for PrapereData4SlikaJournalOpStep.
	 */
	public PrapereData4SlikaJournalOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		setGLSG_GLBL_GKSG_TR_MARECHET();
		setGL_ISHUR_MENAHEL();
		mapContents("preSlikaJournal_mapper");
		mapContents("CZAJ_HDR_mapper");
		return RC_OK;
	}
	
	private void mapContents(String mapperName) throws DSEInvalidRequestException, DSEInvalidClassException, DSEInvalidArgumentException {
		DataMapperFormat dmf = (DataMapperFormat) getFormat(mapperName);
		dmf.mapContents(getContext(), getContext());
	}
	
	private void setGL_ISHUR_MENAHEL() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		boolean shouldRequestIshurMenahel = Boolean.valueOf((String) getValueAt("shouldRequestIshurMenahel")).booleanValue();
		if(shouldRequestIshurMenahel) {
			setValueAt("TLSX_EZER.GL_ISHUR_MENAHEL", "1");
		}
	}
	
	private void setGLSG_GLBL_GKSG_TR_MARECHET() throws DSEObjectNotFoundException, DSEInvalidArgumentException {
		String day = (String) getValueAt("GLSG_GLBL.GKSG_TR_MARECHET.GL_TR_DD");
		String month = (String) getValueAt("GLSG_GLBL.GKSG_TR_MARECHET.GL_TR_MM");
		String year = (String) getValueAt("GLSG_GLBL.GKSG_TR_MARECHET.GL_TR_YYYY");
		setValueAt("TLSX_EZER.GLSG_GLBL_GKSG_TR_MARECHET", day+month+year);
	}

}
