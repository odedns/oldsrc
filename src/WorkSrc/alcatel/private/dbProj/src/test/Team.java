/*
 * Created on 16/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;

import java.util.Set;
/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Team
{
	private Long id;
	private String name;
	private String city;

	private Set players;
	/**
	 * @return
	 */
	public String getCity()
	{
		return city;
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
	 * @param string
	 */
	public void setCity(String string)
	{
		city = string;
	}

	/**
	 * @param i
	 */
	public void setId(Long i)
	{
		id = i;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}



	/**
	 * @return
	 */
	public Set getPlayers()
	{
		return players;
	}

	/**
	 * @param set
	 */
	public void setPlayers(Set set)
	{
		players = set;
	}

}
