/*
 * Created on 20/11/2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package fwpilot.general.dao;

import java.util.*;

import com.ness.fw.bl.DAO;
import com.ness.fw.bl.Identifiable;
import com.ness.fw.persistence.*;
import com.ness.fw.persistence.exceptions.*;
import com.ness.fw.common.exceptions.PersistenceException;
import fwpilot.general.vo.AppUser;


public class AppUserDAO extends DAO implements AppUser,Identifiable
{
	protected static String PROPS_FILE_NAME = "fwpilot/general/dao/sql";
	
	private Integer id;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private Integer sex;
	private Integer identification;
	
	/**
	 * Default constructor
	 */
	public AppUserDAO() throws PersistenceException
	{
		super();
	}

	/** Insert this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @return Map The keys of the inserted object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void insert(Transaction transaction, Map keys)
		throws PersistenceException
	{
		String sqlStatement = transaction.getProperty(PROPS_FILE_NAME, "AppUser.insert");

		List params = new ArrayList();		
		params.add(identification);
		params.add(firstName);
		params.add(lastName);
		params.add(birthDate);
		params.add(sex);
		params.add(transaction.getUserId());
		params.add(transaction.getUserId());
		
		id = executeInsertStatement(sqlStatement,params, transaction, true);
	}

	/** Update this object into the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 * @throws PersistenceException Any PersistenceException that may occur.
	 */
	public void update(Transaction transaction, Map keys)
		throws PersistenceException
	{
		List params = new ArrayList();		
		params.add(identification);
		params.add(firstName);
		params.add(lastName);
		params.add(birthDate);
		params.add(sex);
		params.add(transaction.getUserId());

		// keys
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AppUser.update"), params, transaction);
	}

	/** Delete this object from the database.
	 * @param transaction the {@link Transaction} object to use.
	 * @param keys The map of keys that identifies this object.
	 */
	public void delete(Transaction transaction, Map keys)
		throws PersistenceException
	{
		// keys
		List params = new ArrayList();		
		params.add(id);
		
		executeStatement(transaction.getProperty(PROPS_FILE_NAME, "AppUser.delete"), params, transaction);
	}


	/** build the keys of the current object.
	 * @param keys The keys of the parent object
	 * @return A Map with the keys of the current object.
	 */
	public void setKeyValues(Map keys) 
	{
		keys.put("userId",id);
	}


	/**
	 * @param p
	 */
	private void loadObjectData (Page p)
	{
		setStateAsNonDirty();
				
		id = p.getInteger("ID"); 
		firstName = p.getString("FIRST_NAME");
		lastName = p.getString("LAST_NAME");
		birthDate = p.getDate("BIRTH_DATE");
		identification = p.getInteger("IDENTIFICATION"); 
		sex = p.getInteger("SEX"); 
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
	 * @param date
	 */
	public void setBirthDate(Date date)
	{
		birthDate = date;
		setStateAsDirty();
	}


	/**
	 * @param string
	 */
	public void setFirstName(String string)
	{
		firstName = string;
		setStateAsDirty();
	}

	/**
	 * @param string
	 */
	public void setLastName(String string)
	{
		lastName = string;
		setStateAsDirty();
	}

	/**
	 * @param userId
	 * @throws ObjectNotFoundException
	 * @throws PersistenceException
	 */
	public static AppUserDAO findByID(int userId, ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		AppUserDAO user = null;
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AppUser.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AppUser"); 
		SqlService ss = new SqlService(sqlStatement);
		
		// keys
		ss.addParameter(new Integer(userId));
		
		Page p = Query.execute(ss, cp);
		if (p.next())
		{
			user = new AppUserDAO();
			user.loadObjectData(p);
		}
		else
		{
			throw new ObjectNotFoundException("AppUser does not exist");
		}
		return user;
	}

	public Page findAll(ConnectionProvider cp) throws ObjectNotFoundException, PersistenceException
	{
		String sqlStatement = cp.getProperty(PROPS_FILE_NAME, "AppUser.genericSelect") 
			+ cp.getProperty(PROPS_FILE_NAME, "AppUser.findAll"); 
		SqlService ss = new SqlService(sqlStatement);
		
		Page p = Query.execute(ss, cp);
		return p;
	}

	/**
	 * @return
	 */
	public Integer getSex()
	{
		return sex;
	}

	/**
	 * @param i
	 */
	public void setSex(Integer i)
	{
		sex = i;
		setStateAsDirty();
	}

	/**
	 * @return
	 */
	public Integer getIdentification()
	{
		return identification;
	}

	/**
	 * @param i
	 */
	public void setIdentification(Integer i)
	{
		identification = i;
		setStateAsDirty();
	}
}
