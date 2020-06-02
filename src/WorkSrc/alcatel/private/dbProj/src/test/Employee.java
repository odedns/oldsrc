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
public class Employee
{
	private Long id;
	protected String name;
	protected Float salary;
	protected Integer type;
	
	public static final int EMP = 1;
	public static final int MANAGER = 2;

	/**
	 * 
	 */
	public Employee()
	{
		super();
		// TODO Auto-generated constructor stub
		type = new Integer(EMP);
	}

	/**
	 * @return
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public Float getSalary()
	{
		return salary;
	}

	/**
	 * @param long1
	 */
	public void setId(Long long1)
	{
		id = long1;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param float1
	 */
	public void setSalary(Float float1)
	{
		salary = float1;
	}

	/**
	 * @return
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param integer
	 */
	public void setType(Integer integer)
	{
		type = integer;
	}

}
