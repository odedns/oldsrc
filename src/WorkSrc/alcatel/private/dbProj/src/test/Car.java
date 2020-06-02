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
public class Car extends Vehicle
{
	String model;
	Integer year;
	

	/**
	 * 
	 */
	public Car()
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
	public String getModel()
	{
		return model;
	}

	/**
	 * @return
	 */
	public Integer getYear()
	{
		return year;
	}

	/**
	 * @param string
	 */
	public void setModel(String string)
	{
		model = string;
	}

	/**
	 * @param integer
	 */
	public void setYear(Integer integer)
	{
		year = integer;
	}

}
