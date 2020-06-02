/**
 * Created on 29/12/2004
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package jpatest;

import javax.persistence.Entity;
import javax.persistence.InheritanceType;
import javax.persistence.Inheritance;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Truck extends Vehicle
{

	String make;
	Integer horsePower;
	/**
	 * 
	 */
	public Truck()
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
	public Integer getHorsePower()
	{
		return horsePower;
	}

	/**
	 * @return
	 */
	public String getMake()
	{
		return make;
	}

	/**
	 * @param integer
	 */
	public void setHorsePower(Integer integer)
	{
		horsePower = integer;
	}

	/**
	 * @param string
	 */
	public void setMake(String string)
	{
		make = string;
	}

}
