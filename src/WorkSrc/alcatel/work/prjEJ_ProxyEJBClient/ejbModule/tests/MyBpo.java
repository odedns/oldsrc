package tests;



/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MyBpo implements MyBpoIF
{

	public String find(Integer id)
	{
		System.out.println("MyBpo.find id=" +id);
		return("found: " + id);
	}
}
