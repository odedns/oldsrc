/*
 * Created on: 07/11/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy;

import java.util.ArrayList;

/**
 * 
 */
public class CustomerAddress1
{
	private Integer id;
	private Integer customerId;
	private String city;
	private String street;
	private Integer streetNumber;
	private Integer main;
	private Integer telephone;
	private Integer fax;
	
	private ArrayList telephones = new ArrayList();

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
	public Integer getCustomerId()
	{
		return customerId;
	}

	/**
	 * @return
	 */
	public Integer getFax()
	{
		return fax;
	}

	/**
	 * @return
	 */
	public Integer getId()
	{
		return id;
	}

	/**
	 * @return
	 */
	public Integer getMain()
	{
		return main;
	}

	/**
	 * @return
	 */
	public String getStreet()
	{
		return street;
	}

	/**
	 * @return
	 */
	public Integer getStreetNumber()
	{
		return streetNumber;
	}

	/**
	 * @return
	 */
	public Integer getTelephone()
	{
		return telephone;
	}

	/**
	 * @param string
	 */
	public void setCity(String string)
	{
		city = string;
	}

	/**
	 * @param integer
	 */
	public void setCustomerId(Integer integer)
	{
		customerId = integer;
	}

	/**
	 * @param integer
	 */
	public void setFax(Integer integer)
	{
		fax = integer;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer)
	{
		id = integer;
	}

	/**
	 * @param integer
	 */
	public void setMain(Integer integer)
	{
		main = integer;
	}

	/**
	 * @param string
	 */
	public void setStreet(String string)
	{
		street = string;
	}

	/**
	 * @param integer
	 */
	public void setStreetNumber(Integer integer)
	{
		streetNumber = integer;
	}

	/**
	 * @param integer
	 */
	public void setTelephone(Integer integer)
	{
		telephone = integer;
	}

	public void addTelephone(CustomerTelephone1 customerTelephone1)
	{
		telephones.add(customerTelephone1);
	}

	public String toString()
	{

		StringBuffer sb = new StringBuffer(1024);
		
		sb.append("\n " + "CustomerAddress:  id=" + id 
			+ " customerId=" + customerId 
			+ " city=" + city 
			+ " street=" + street 
			+ " streetNumber=" + streetNumber 
			+ " main=" + main 
			+ " telephone=" + telephone 
			+ " fax=" + fax);
		

		for (int i = 0 ; i < telephones.size() ; i++)
		{
			sb.append("\n telephone [" + i + "]= " + telephones.get(i));
		}
		
		return sb.toString(); 
	}
}
