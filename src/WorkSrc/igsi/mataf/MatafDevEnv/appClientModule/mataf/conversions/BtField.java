package mataf.conversions;

/**
 * @author odedn
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */
public class BtField {
	
	String m_name;
	Object m_value;
	int m_len;
	boolean m_ignore;
	boolean m_yn;
	/**
	 * Constructor for BtField.
	 */
	public BtField() {
		super();
	}
	
	/**
	 * construct a BtField with the specified
	 * name and length.
	 */
	public BtField(String name, int len) 
	{
		super();
		m_name = name;
		m_len = len;
	}

	/**
	 * set the name for the BtField.
	 * @param name the name to set.
	 */	
	public void setName(String name)
	{
		m_name = name;
	}

	public String getName()
	{
		return(m_name);
	}
	/**
	 * set the length for the BtField.
	 * @param len the length to set.
	 */		
	public void setLength(int len)
	{
		m_len = len;
	}


	public int getLength()
	{
		return(m_len);
	}
	/**
	 * sets the ignore flag in the BtField.
	 */
	public void setIgnore(boolean ignore) 
	{	
		m_ignore = ignore;
	}
	
	
	public boolean getIgnore()
	{
		return(m_ignore);
	}
	/**
	 * set the value of the BtField.
	 */
	public void setValue(Object val)
	{
		m_value = val;
	}
	
	public Object getValue()
	{
		return(m_value);
	}


	/**
	 * set the yn property of the field.
	 * @param yn boolean yn parameter.
	 */
	public void setYn(boolean yn)
	{	
		m_yn = yn;
	}
	
	/**
	 * return the yn flag for the field.
	 */
	public boolean getYn()
	{
		return(m_yn);
	}
	/**
	 * convert field to a String.
	 */
	public String toString()
	{
		String s = new String(m_name + ":" + m_len + ":" + m_ignore + ":" + m_value.toString());
		return(s);
	}
}
