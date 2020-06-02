/*
 * Created on: 31/03/2005
 * Author: yifat har-nof
 * @version $Id: RowNavigationService.java,v 1.5 2005/04/11 06:22:16 yifat Exp $
 */
package com.ness.fw.persistence;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;

import com.ness.fw.common.exceptions.PersistenceException;

/**
 * Provide basic implementation of row navigation service 
 * on query results with open connection to the db.
 */
public abstract class RowNavigationService implements Serializable
{

	/**
	 *  The {@link SqlService} object to exceute
	 */
	private SqlService sqlService;
	
	/**
	 * The ConnectionProvider to use its connection.
	 */
	private ConnectionProvider connectionProvider;
	
	/**
	 * The {@link ResultSet} object.
	 */
	private ResultSet resultSet;

	/** A list of the original Column names from the result set.
	 */
	private String colNames[];

	/** A list of the column types from the result set.
	 */
	private int colTypes[];

	/**
	 * Creates new RowNavigationService
	 * @param sqlService The {@link SqlService} object to exceute
	 * @param connectionProvider The ConnectionProvider to use its connection.
	 * @param loadOnStart Indicates whether to load the query results.  
	 * @throws PersistenceException
	 */
	public RowNavigationService(
		SqlService sqlService,
		ConnectionProvider connectionProvider,
		boolean loadOnStart)
		throws PersistenceException
	{
		this.sqlService = sqlService;
		this.connectionProvider = connectionProvider;

		if (loadOnStart)
		{
			loadResultSet();
		}
	}

	/**
	 * load the result set
	 */
	protected abstract void loadResultSet() throws PersistenceException;

	/**
	 * load the query results when reload=true or 
	 * when the constructor argument: loadOnStart was false.
	 * @param reload Indicates whether to reload the query results again.
	 * @throws PersistenceException
	 */
	public void begin(boolean reload) throws PersistenceException
	{
		if (resultSet == null || reload)
		{
			if (resultSet != null)
			{
				end();
			}

			loadResultSet();
		}
	}

	/**
	 * load the query results (when the constructor argument: loadOnStart was false).
	 * @throws PersistenceException
	 */
	public void begin() throws PersistenceException
	{
		begin(false);
	}

	/**
	 * Set the {@link ResultSet}.
	 * @param resultSet
	 * @throws SQLException
	 */
	protected void setResultSet(ResultSet resultSet) throws SQLException
	{
		this.resultSet = resultSet;
		setColumns(this.resultSet.getMetaData());
	}

	protected void checkResultSet() throws PersistenceException
	{
		if(resultSet == null)
		{
			throw new PersistenceException("The RowNavigationService was not started. Please call to begin method!!");
		}
	}	

	/** 
	 * Set the cursor position to the next row.
	 * @return boolean <B>True</B> - a valid record found.<br>
	 * <B>False</B> - invalid cursor position.
	 * @throws PersistenceException
	 */
	public boolean next() throws PersistenceException
	{
		try
		{
			checkResultSet();
			return resultSet.next();
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the ConnectionProvider to use its connection.
	 * @return ConnectionProvider
	 */
	public ConnectionProvider getConnectionProvider()
	{
		return connectionProvider;
	}

	/**
	 * Returns the {@link SqlService} object to exceute
	 * @return SqlService
	 */
	public SqlService getSqlService()
	{
		return sqlService;
	}

	/**
	 * Returns the current ResultSet. 
	 * @return ResultSet
	 */
	protected ResultSet getResultSet() throws PersistenceException
	{
		checkResultSet();
		return resultSet;
	}

	/** 
	 * Set columns infornation from a given {@link ResultSetMetaData}.
	 * @param rsMetaData The ResultSetMetaData to use.
	 * @throws SQLException Any SQLException that may occur.
	 */
	protected void setColumns(ResultSetMetaData rsMetaData) throws SQLException
	{
		int colCount = rsMetaData.getColumnCount();
		colNames = new String[colCount];
		colTypes = new int[colCount];
		for (int index = 1; index <= colCount; index++)
		{
			colNames[index - 1] = rsMetaData.getColumnName(index);
			colTypes[index - 1] = rsMetaData.getColumnType(index);
		}
	}

	/** 
	 * Get column name by index.
	 * @param index The column index to use.
	 * @return The column name.
	 */
	public String getColumnName(int index) throws PersistenceException
	{
		checkResultSet();
		return colNames[index];
	}

	/** 
	 * Get the column data type by index
	 * @param index The index to use.
	 * @return int The column data type.
	 */
	public int getColumnType(int index) throws PersistenceException
	{
		checkResultSet();
		return colTypes[index];
	}

	/** 
	 * Get the number of columns in the page.
	 * @return int The number of columns in the page.
	 */
	public int getColumnCount() throws PersistenceException
	{
		checkResultSet();
		return colNames.length;
	}

	/** 
	 * Release all the resources
	 */
	public void end() throws PersistenceException
	{
		try
		{
			if (resultSet != null)
			{
				resultSet.close();
			}
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Release all the resources if they have not been previously released.
	 */
	protected void finalize() throws Throwable
	{
		try
		{
			end();
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
	}


	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as an Object. 
	 * @param index The column index.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(int index) throws PersistenceException
	{
		try
		{
			return resultSet.getObject(index + 1);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Returns the column data according to the column name from the current row, 
	 * as an Object. 
	 * @param name The column name.
	 * @return Object An Object containing the column data.
	 */
	public Object getObject(String name) throws PersistenceException
	{
		try
		{
			return resultSet.getObject(name);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as an int. 
	 * @param index The column index.
	 * @return int An int containing the column data.
	 */
	public int getInt(int index) throws PersistenceException
	{
		try
		{
			return resultSet.getInt(index + 1);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as an int. 
	 * @param name The column name.
	 * @return int An int containing the column data.
	 */
	public int getInt(String name) throws PersistenceException
	{
		try
		{
			return resultSet.getInt(name);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as an Integer. 
	 * @param index The column index.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(int index) throws PersistenceException
	{
		return (Integer) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as an Integer. 
	 * @param name The column name.
	 * @return Integer An Integer containing the column data.
	 */
	public Integer getInteger(String name) throws PersistenceException
	{
		return (Integer) getObject(name);
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as a double. 
	 * @param index The column index.
	 * @return double A double containing the column data.
	 */
	public double getDouble(int index) throws PersistenceException
	{
		try
		{
			return resultSet.getDouble(index);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a double. 
	 * @param name The column name.
	 * @return double A double containing the column data.
	 */
	public double getDouble(String name) throws PersistenceException
	{
		try
		{
			return resultSet.getDouble(name);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as a Double. 
	 * @param index The column index.
	 * @return Double A Double containing the column data.
	 */
	public Double getDoubleObject(int index) throws PersistenceException
	{
		return (Double) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a Double. 
	 * @param name The column name.
	 * @return Double A Double containing the column data.
	 */
	public Double getDoubleObject(String name) throws PersistenceException
	{
		return (Double) getObject(name);
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as a long. 
	 * @param index The column index.
	 * @return long A long containing the column data.
	 */
	public long getLong(int index) throws PersistenceException
	{
		try
		{
			return resultSet.getLong(index);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a long. 
	 * @param name The column name.
	 * @return long A long containing the column data.
	 */
	public long getLong(String name) throws PersistenceException
	{
		try
		{
			return resultSet.getLong(name);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/** 
	 * Returns the column data according to the column index from the current row, 
	 * as a Long. 
	 * @param index The column index.
	 * @return Long A Long containing the column data.
	 */
	public Long getLongObject(int index) throws PersistenceException
	{
		return (Long) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a Long. 
	 * @param name The column name.
	 * @return Long A Long containing the column data.
	 */
	public Long getLongObject(String name) throws PersistenceException
	{
		return (Long) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a float. 
	 * @param index The column index.
	 * @return float A float containing the column data.
	 */
	public float getFloat(int index) throws PersistenceException
	{
		try
		{
			return resultSet.getFloat(index);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a float. 
	 * @param name The column name.
	 * @return float A float containing the column data.
	 */
	public float getFloat(String name) throws PersistenceException
	{
		try
		{
			return resultSet.getLong(name);
		}
		catch (SQLException e)
		{
			throw new PersistenceException(e);
		}
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a Float. 
	 * @param index The column index.
	 * @return Float A Float containing the column data.
	 */
	public Float getFloatObject(int index) throws PersistenceException
	{
		return (Float) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a Float. 
	 * @param name The column name.
	 * @return Float A Float containing the column data.
	 */
	public Float getFloatObject(String name) throws PersistenceException
	{
		return (Float) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a String. 
	 * @param index The column index.
	 * @return String A String containing the column data.
	 */
	public String getString(int index) throws PersistenceException
	{
		return (String) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a String. 
	 * @param name The column name.
	 * @return String A String containing the column data.
	 */
	public String getString(String name) throws PersistenceException
	{
		return (String) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a java.util.Date. 
	 * @param index The column index.
	 * @return java.util.Date A Date containing the column data.
	 */
	public java.util.Date getDate(int index) throws PersistenceException
	{
		return (java.util.Date) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a java.util.Date. 
	 * @param name The column name.
	 * @return java.util.Date A Date containing the column data.
	 */
	public java.util.Date getDate(String name) throws PersistenceException
	{
		return (java.util.Date) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a java.sql.Timestamp. 
	 * @param index The column index.
	 * @return java.sql.Timestamp A Timestamp containing the column data.
	 */
	public Timestamp getTimeStamp(int index) throws PersistenceException
	{
		return (Timestamp) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row,
	 * as a java.sql.Timestamp. 
	 * @param name The column name.
	 * @return java.sql.Timestamp A Timestamp containing the column data.
	 */
	public Timestamp getTimeStamp(String name) throws PersistenceException
	{
		return (Timestamp) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a java.sql.Time. 
	 * @param index The column index.
	 * @return java.sql.Time A Time containing the column data.
	 */
	public Time getTime(int index) throws PersistenceException
	{
		return (Time) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row,
	 * as a java.sql.Time. 
	 * @param name The column name.
	 * @return java.sql.Time A Time containing the column data.
	 */
	public Time getTime(String name) throws PersistenceException
	{
		return (Time) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a java.math.BigDecimal. 
	 * @param index The column index.
	 * @return java.math.BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(int index)
		throws PersistenceException
	{
		return (java.math.BigDecimal) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row,
	 * as a java.math.BigDecimal. 
	 * @param name The column name.
	 * @return java.math.BigDecimal A BigDecimal containing the column data.
	 */
	public java.math.BigDecimal getBigDecimal(String name)
		throws PersistenceException
	{
		return (java.math.BigDecimal) getObject(name);
	}

	/**
	 * Returns the column data according to the column index from the current row, 
	 * as a Number. 
	 * @param index The column index.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(int index) throws PersistenceException
	{
		return (Number) getObject(index);
	}

	/**
	 * Returns the column data according to the column name from the current row, 
	 * as a Number. 
	 * @param name The column name.
	 * @return Number A Number containing the column data.
	 */
	public Number getNumber(String name) throws PersistenceException
	{
		return (Number) getObject(name);
	}

}
