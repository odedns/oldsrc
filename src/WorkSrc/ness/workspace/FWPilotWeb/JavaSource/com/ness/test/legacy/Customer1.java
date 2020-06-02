/*
 * Created on: 17/10/2004
 * Author: yifat har-nof
 */
package com.ness.test.legacy;

import java.util.Date;
import java.util.ArrayList;

/**
 * 
 */
public class Customer1
{

	private Integer id;
	private String firstName;
	private String lastName;
	private Integer sex;
	private Integer smoking;
	private String birthDateStr;
	private Date birthDate; 
	private Date birthDate2; 
	private Integer telephone;

	private ArrayList addresses = new ArrayList();
	private ArrayList professions = new ArrayList();

	/**
	 * @return
	 */
	public String getBirthDateStr()
	{
		return birthDateStr;
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
	public Integer getSex()
	{
		return sex;
	}

	/**
	 * @return
	 */
	public Integer getSmoking()
	{
		return smoking;
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
	public void setSex(Integer integer)
	{
		sex = integer;
	}

	/**
	 * @param integer
	 */
	public void setSmokingInd(Integer integer)
	{
		smoking = integer;
	}

	/**
	 * @param integer
	 */
	public void setTelephone(Integer integer)
	{
		telephone = integer;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer(1024);
		
		sb.append("\n Customer:  id=" + id + " firstName=" + firstName + " lastName=" + lastName + " smoking=" + smoking + " birthDateStr=" + birthDateStr + " birthDate=" + birthDate + " birthDate2=" + birthDate2 + " telephone=" + telephone + " profession count=" + professions.size() + " address count=" + addresses.size());
		
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
	public Date getBirthDate()
	{
		return birthDate;
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
	public void setBirthDateStr(String string)
	{
		birthDateStr = string;
	}

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
	public void addProfession(CustomerProfession1 profession)
	{
		professions.add(profession);
	}

	/**
	 * @param list
	 */
	public CustomerProfession1 getProfession(int index)
	{
		return (CustomerProfession1)professions.get(index);
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
	public void addAddress(CustomerAddress1 address)
	{
		addresses.add(address);
	}

	/**
	 * @param list
	 */
	public CustomerAddress1 getAddress(int index)
	{
		return (CustomerAddress1)addresses.get(index);
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
	public Date getBirthDate2()
	{
		return birthDate2;
	}

	/**
	 * @param date
	 */
	public void setBirthDate2(Date date)
	{
		birthDate2 = date;
	}

}
