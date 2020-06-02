package mataf.utils;
import java.io.Serializable;

/**
 * Create a data pair object.
 * The data pair object holds a key and a value.
 * @author Oded Nissan
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class DataPair implements Serializable {
	
	String m_key;
	Object m_value;
	/**
	 * Constructor for DataPair.
	 */
	public DataPair() {
		super();
	}
	
	/**
	 * Consturct a dataPair object with 
	 * a specific key and value.
	 * @param key the key
	 * @param value the value for the DataPair object.
	 */
	public DataPair(String key, Object value)
	{
		m_key  = key;
		m_value = value;	
	}

	/**
	 * set a key for the current dataPair.
	 * @param key 
	 */	
	public void setKey(String key)
	{
		m_key = key;
	}
	
	public void setValue(Object val)
	{
		m_value = val;
	}
	
	/**
	 * get this DataPair's key.
	 * @return String  the DataPair's key.
	 */		
	public String getKey()
	{
		return(m_key);
	}

	/**
	 * get this DataPair's value object.
	 * @return Object the DataPair's value.
	 */	
	public Object getValue()
	{
		return(m_value);
	}

}
