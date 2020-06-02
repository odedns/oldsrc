package mataf.general.operations;

import com.ibm.dse.base.DataMapperFormat;
import com.ibm.dse.base.KeyedCollection;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (12/10/2003 18:06:41).  
 */
public class DataMapperOpStep extends MatafOperationStep {

	/**
	 * Constructor for DataMapperOpStep.
	 */
	public DataMapperOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String mapperName = (String) getParams().getValueAt(DATA_MAPPER_PARAM_NAME);
		DataMapperFormat dmf = (DataMapperFormat) getFormat(mapperName);
		dmf.mapContents(getContext(), getContext());
				
		return RC_OK;
	}

}
