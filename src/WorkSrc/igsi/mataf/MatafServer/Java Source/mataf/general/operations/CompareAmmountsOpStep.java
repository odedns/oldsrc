package mataf.general.operations;

import com.ibm.dse.base.DSEObjectNotFoundException;

import mataf.data.VisualDataField;
import mataf.utils.MatafUtilities;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class CompareAmmountsOpStep extends MatafOperationStep {
		
	/**
	 * Constructor for CompareAmmount2zero.
	 */
	public CompareAmmountsOpStep() {
		super();
	}

	/**
	 * @see mataf.general.operations.MatafOperationStep#executeOp()
	 */
	public int executeOp() throws Exception {
		
		String fieldValue2checkStr = (String) getValueAt(getFieldName2check());
		String fieldValue2cmprStr = getValue2compare();
		
		String comparisonTypeStr = (String) getParams().getValueAt(COMPARISON_TYPE_PARAM_NAME);
		
		double fieldValue2check = Double.parseDouble(fieldValue2checkStr);
		double fieldValue2cmpr = Double.parseDouble(fieldValue2cmprStr);
		int comparisonType = Integer.parseInt(comparisonTypeStr);
		
		if(!isComparisonOk(fieldValue2check, fieldValue2cmpr, comparisonType)) {
			setError();
			return RC_ERROR;
		}

		return RC_OK;
	}
	
	private String getValue2compare() throws DSEObjectNotFoundException {
		String value2cmpr = (String) getParams().tryGetValueAt(VALUE_2_CMPR_PARAM_NAME);
		if(value2cmpr!=null) {
			return value2cmpr;
		} else {
			return (String) getValueAt((String) getParams().getValueAt(FIELD_NAME_2_CMPR_PARAM_NAME));
		}
	}
	
	/**
	 * Return GREATER_COMPARISON_TYPE if value1>value2
	 * Return LESS_COMPARISON_TYPE if value1<value2
	 * Return EQUALS_COMPARISON_TYPE if value1==value2
	 */
	private boolean isComparisonOk(double value1, double value2, int comparisonType) {
		if((value1-value2)>MatafUtilities.DOUBLE_ZERO_VALUE) {
			return GREATER_COMPARISON_TYPE==comparisonType;
		} else if((value2-value1)>MatafUtilities.DOUBLE_ZERO_VALUE) {
			return LESS_COMPARISON_TYPE==comparisonType;
		} else {
			return EQUALS_COMPARISON_TYPE==comparisonType;
		}
	}

}
