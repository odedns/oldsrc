package mataf.types.textfields;

/**
 * This class represents a specific textfield for handling the account number.
 * It fires a special event each time the focus leaves the field.<p>
 * 
 * The event should trigger the dynamic sale system.
 * 
 * PENDING : Implement.
 * 
 * @author Nati Dykstein. Creation Date : (04/04/2004 15:49:56).  
 */
public class MatafAccountField extends MatafNumericField
{

	/**
	 * 
	 */
	public MatafAccountField()
	{
		super();		
	}

	/**
	 * @param text
	 */
	public MatafAccountField(String text)
	{
		super(text);
	}
}
