/**
 * Created on 28/12/2004
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
public class Manager extends Employee
{

	Integer numEmps;
	/**
	 * 
	 */
	public Manager()
	{
		super();
		// TODO Auto-generated constructor stub
		type = new Integer(MANAGER);
	}

	/**
	 * @return
	 */
	public Integer getNumEmps()
	{
		return numEmps;
	}

	/**
	 * @param integer
	 */
	public void setNumEmps(Integer integer)
	{
		numEmps = integer;
	}

}
