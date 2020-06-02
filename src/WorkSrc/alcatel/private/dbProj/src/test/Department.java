/**
 * Created on 16/01/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;

import java.util.HashSet;
import java.util.Set;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Department
{
	private Long id;
	private String name;
	private Integer type;
	private Set employees = null;
	
	
	
	public Department()
	{
		employees = new HashSet();
	}
	/**
	 * @return
	 */
	public Set getEmployees()
	{
		return employees;
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
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param set
	 */
	public void setEmployees(Set set)
	{
		employees = set;
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
	 * @param integer
	 */
	public void setType(Integer integer)
	{
		type = integer;
	}


	public void addEmployee(Employee emp)
	{
		employees.add(emp);
	}
}
