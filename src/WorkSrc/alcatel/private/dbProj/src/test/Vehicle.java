/**
 * Created on 29/12/2004
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Vehicle
{

	protected Long id;
	protected String desc; 
	/**
	 * 
	 */
	public Vehicle()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args)
	{
	}
	/**
	 * @return
	 */
	public String getDesc()
	{
		return desc;
	}

	/**
	 * @return
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param string
	 */
	public void setDesc(String string)
	{
		desc = string;
	}

	/**
	 * @param integer
	 */
	public void setId(Long integer)
	{
		id = integer;
	}

}
