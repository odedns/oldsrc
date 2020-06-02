package mataf.general.operations;



/**
 * @author yossid
 *
 * Return RC_OK if the values to compare are equels, otherwise return RC_ERROR.
 */
public class CompareValuesOpStep extends MatafOperationStep {
	
	/**
	 * @see mataf.slika.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		String fieldValue2cmpr = (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CMPR_PARAM_NAME));
		String value2cmpr = (String) getParams().getValueAt(VALUE_2_CMPR_PARAM_NAME);
		
		if(fieldValue2cmpr.equals(value2cmpr)) {
			return RC_OK;
		} else {
			return RC_ERROR;
		}
	}

}
