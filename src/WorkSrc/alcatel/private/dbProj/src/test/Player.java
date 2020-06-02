/*
 * Created on 16/11/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package test;
import java.util.Date;

/**
 * @author P0006439
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Player
{
	private Long id;
	private String firstName;
	private String lastName;
	private Date draftDate;
	private Float annualSalary;
	private Team team;
	
	/**
	 * @return
	 */
	public Float getAnnualSalary()
	{
		return annualSalary;
	}

	/**
	 * @return
	 */
	public Date getDraftDate()
	{
		return draftDate;
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
	public Long getId()
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
	 * @param float1
	 */
	public void setAnnualSalary(Float float1)
	{
		annualSalary = float1;
	}

	/**
	 * @param date
	 */
	public void setDraftDate(Date date)
	{
		draftDate = date;
	}

	/**
	 * @param string
	 */
	public void setFirstName(String string)
	{
		firstName = string;
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
	public void setLastName(String string)
	{
		lastName = string;
	}

	/**
	 * @return
	 */
	public Team getTeam()
	{
		return team;
	}

	/**
	 * @param team
	 */
	public void setTeam(Team team)
	{
		this.team = team;
	}

}
