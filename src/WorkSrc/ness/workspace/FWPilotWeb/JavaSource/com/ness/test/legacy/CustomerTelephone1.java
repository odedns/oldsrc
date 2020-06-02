package com.ness.test.legacy;

public class CustomerTelephone1
{
	private Integer id;
	private Integer adressID;
	private String description;	
	/**
	 * @return
	 */
	public Integer getAdressID()
	{
		return adressID;
	}

	/**
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @param integer
	 */
	public void setAdressID(Integer integer)
	{
		adressID = integer;
	}

	/**
	 * @param string
	 */
	public void setDescription(String string)
	{
		description = string;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer)
	{
		id = integer;
	}
	
	public String toString()
	{
		return "Telephone: id = "  + id + " addrressid =" + adressID + " description =" + description;		
	}

}