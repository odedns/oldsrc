/*
 * Created on: 17/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy2;

import java.util.Date;
import java.util.ArrayList;

/**
 * 
 */
public class Customer2
{

	private Integer id;
	private String firstName;
	private String lastName;
	private Date birthDate; 
	private Integer telephone;
	private String exception;

	private ArrayList addresses = new ArrayList();
	private ArrayList professions = new ArrayList();

	/**
	 * @return
	 */
	public ArrayList getProfessions()
	{
		return professions;
	}

	/**
	 * @param list
	 */
	public void addProfession(CustomerProfession2 profession)
	{
		professions.add(profession);
	}

	/**
	 * @param list
	 */
	public CustomerProfession2 getProfession(int index)
	{
		return (CustomerProfession2)professions.get(index);
	}

	/**
	 * @param list
	 */
	public int getProfessionCount()
	{
		return professions.size();
	}


	/**
	 * @return
	 */
	public ArrayList getAddresses()
	{
		return addresses;
	}

	/**
	 * @param list
	 */
	public void addAddress(CustomerAddress2 address)
	{
		addresses.add(address);
	}

	/**
	 * @param list
	 */
	public CustomerAddress2 getAddress(int index)
	{
		return (CustomerAddress2)addresses.get(index);
	}

	/**
	 * @param list
	 */
	public int getAddressCount()
	{
		return addresses.size();
	}

	/**
	 * @return
	 */
	public Date getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @return
	 */
	public String getFirstName()
	{
		return firstName;
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
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * @return
	 */
	public Integer getTelephone()
	{
		return telephone;
	}

	/**
	 * @param date
	 */
	public void setBirthDate(Date date)
	{
		birthDate = date;
	}

	/**
	 * @param string
	 */
	public void setFirstName(String string)
	{
		firstName = string;
	}

	/**
	 * @param integer
	 */
	public void setId(Integer integer)
	{
		id = integer;
	}

	/**
	 * @param string
	 */
	public void setLastName(String string)
	{
		lastName = string;
	}

	/**
	 * @param integer
	 */
	public void setHomeTelephone(Integer integer)
	{
		telephone = integer;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(1024);
		
		sb.append("\n Customer:  id=" + id + " firstName=" + firstName + " lastName=" + lastName + " birthDate=" + birthDate + " telephone=" + telephone + " exception=" + exception + " profession count=" + professions.size() + " address count=" + addresses.size());
		
		for (int i = 0 ; i < professions.size() ; i++)
		{
			sb.append("\n profession [" + i + "]= " + professions.get(i));
		}

		for (int i = 0 ; i < addresses.size() ; i++)
		{
			sb.append("\n address [" + i + "]= " + addresses.get(i));
		}
		
		return sb.toString(); 
	}

	/**
	 * @return
	 */
	public String getException()
	{
		return exception;
	}

	/**
	 * @param string
	 */
	public void setException(String string)
	{
		exception = string;
	}

}
