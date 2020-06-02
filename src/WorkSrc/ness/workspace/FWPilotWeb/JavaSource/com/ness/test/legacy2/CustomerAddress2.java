/*
 * Created on: 07/11/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

import java.util.ArrayList;

/**
 * 
 */
public class CustomerAddress2
{
	private Integer id;
	private String city;
	private String street;
	private Integer main;

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
	 * @param string
	 */
	public void setCity(String string)
	{
		city = string;
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
	 * @return
	 */
	public ArrayList getTelephones()
	{
		return telephones;
	}

	/**
	 * @param list
	 */
	public void addTelephone(Telephone2 telephone)
	{
		telephones.add(telephone);
	}

	/**
	 * @param list
	 */
	public Telephone2 getTelephone(int index)
	{
		return (Telephone2)telephones.get(index);
	}

	/**
	 * @param list
	 */
	public int getTelephoneCount()
	{
		return telephones.size();
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(1024);
		
		sb.append("\n CustomerAddress:  id=" + id 
			+ " city=" + city 
			+ " street=" + street 
			+ " main=" + main
			+ telephones.size() + " telephone count=" + telephones.size());

		for (int i = 0 ; i < telephones.size() ; i++)
		{
			sb.append("\n telephone [" + i + "]= " + telephones.get(i));
		}
		
		return sb.toString(); 
	}

}
