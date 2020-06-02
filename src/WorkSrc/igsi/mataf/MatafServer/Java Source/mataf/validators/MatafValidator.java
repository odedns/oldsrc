package mataf.validators;

import java.util.Map;

/**
 * @author yossid
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public interface MatafValidator {
	
	public boolean isValid(Map parameters) throws ValidationException;

}
