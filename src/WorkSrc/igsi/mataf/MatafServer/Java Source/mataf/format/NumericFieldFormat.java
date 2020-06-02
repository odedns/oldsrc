package mataf.format;

import java.io.IOException;
import java.math.BigDecimal;

import com.ibm.dse.base.DSEException;
import com.ibm.dse.base.DSEInvalidArgumentException;
import com.ibm.dse.base.DSEInvalidClassException;
import com.ibm.dse.base.DataField;
import com.ibm.dse.base.NumericStringFormat;
import com.ibm.dse.base.Tag;

/**
 * @author Tibi Glazer
 *
 * Class: NumericFieldFormat.java
 * Description:
 * Created at: Dec 22, 2003
 * 
 */
public class NumericFieldFormat extends NumericStringFormat {

	/**
	 * @see com.ibm.dse.base.FieldFormat#formatField(DataField)
	 */
	public String formatField(DataField arg0) {

		try {
			DataField df = (DataField) arg0.clone();
			Object number = df.getValue();
			if (number == null) {
				df.setValue(new BigDecimal(0));
			}
			else if (number instanceof String) {
				String s = (String) number;
				if (s.trim().length() == 0) {
					df.setValue(new BigDecimal(0));
				}	
				else {
					df.setValue(new BigDecimal(s));
				}	
			}
			return super.formatField(df);
		}
		catch (CloneNotSupportedException e) {
			return null;
		}
	}
}
