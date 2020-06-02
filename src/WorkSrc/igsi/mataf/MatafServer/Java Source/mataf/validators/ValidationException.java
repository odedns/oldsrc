package mataf.validators;

/**
 *
 * 
 * @author Nati Dykstein. Creation Date : (16/11/2003 17:31:46).  
 */
public class ValidationException extends Exception 
{
	/**
	 * Constructor for ValidationException.
	 */
	public ValidationException() {
		super();
	}

	/**
	 * Constructor for ValidationException.
	 * @param message
	 */
	public ValidationException(String message) {
		super(message);
	}	
}
