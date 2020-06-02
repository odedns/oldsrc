package mataf.validators;

import java.util.Map;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/11/2003 17:30:04).  
 */
public class IDNumberValidator implements MatafValidator {

	public static final String ID_CARD_NUMBER_PARAM_NAME = "idCardNumber";
	
	/**
	 * @see mataf.validators.MatafValidator#isValid(Map)
	 */
	public boolean isValid(Map parameters) throws ValidationException {
		
		String idCardNumber = (String) parameters.get(ID_CARD_NUMBER_PARAM_NAME);
		int sum1=0, sum2=0, sum3=0;
		
    	for ( int counter = idCardNumber.length() - 1; counter >= 0; counter--) {
    		sum1 += Character.getNumericValue(idCardNumber.charAt(counter));
        	counter--;
        	if (counter < 0)
            	break;

			sum2 = Character.getNumericValue(idCardNumber.charAt(counter))*2;
			sum1 += sum2%10;
			sum3 = sum3+sum2-(sum2%10);
        }

    	sum1 += sum3 / 10;
 	   	if (sum1 != 0 && (sum1 % 10) == 0) {
 	   		return true;
 	   	} else {
 	   		return false;
 	   	}
	}

}
