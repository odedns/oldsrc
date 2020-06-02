/*
 * Created on: 05/10/2004
 * Author: yifat har-nof
 * @version $Id: CallArgument.java,v 1.1 2005/02/21 15:07:16 baruch Exp $
 */
package com.ness.fw.legacy;

/**
 * Contains the definitions of a specific argument to the legacy program. 
 */
public class CallArgument
{
	/** 
	 * The constant indicates that the parameter is input.
	 */
	protected static final int INPUT_TYPE_AS_INPUT = 1;
	
	/** 
	 * The constant indicates that the parameter is output.
	 */
	protected static final int INPUT_TYPE_AS_OUTPUT = 2;
	
	/** 
	 * The constant indicates that the parameter is input/output.
	 */
	protected static final int INPUT_TYPE_AS_INPUT_OUTPUT = 3;
	
	/** 
	 * The constant indicates that the value of the output parameter should be 
	 * returned as is, without construction of an objects graph from the data.
	 */
	protected static final int RESULT_AS_SIMPLE = 1;

	/** 
	 * The constant indicates that the value of the output parameter should be 
	 * returned as an objects graph from the data, according to the structure definitions.
	 */
	protected static final int RESULT_AS_STRUCTURE = 2;

	/**
	 * The name of the argument. (referenced from the {@link ArgumentStructureDefinition}. 
	 */
	private String name;
	
	/**
	 * The SQL type of the argument.
	 */
	private int sqlType;
	
	/**
	 * Indication whether the parameter is input, output or input/output
	 */
	private int inputType;
	
	/**
	 * Indication whether the value of the parameter will be returned as is or 
	 * as objects graph. 
	 */
	private int resultAs;
	
	/**
	 * The name of the getter method in the input container to get the value of the parameter.
	 */
	private String inputContainerGetter;
	
	/**
	 * The data type of the attribute in the class.
	 */
	private int attributeType;

	/**
	 * The name of the setter method in the results container, 
	 * to set the value of the parameter - only for resultAs=simple.
	 */
	private String simpleResultSetter; 

	/**
	 * 
	 * @param name The name of the argument. (referenced from the {@link ArgumentStructureDefinition}.
	 * @param sqlType The SQL type of the argument.
	 * @param inputType Indication whether the parameter is input, output or input/output
	 * @param inputContainerGetter The name of the getter method in the input 
	 * container to get the value of the parameter.
	 * @param resultAs Indication whether the value of the parameter will be returned 
	 * as is or as objects graph. 
	 * @param simpleResultSetter The name of the setter method in the results container, 
	 * to set the value of the parameter - only for resultAs=simple.
	 * @param attributeType The data type of the attribute in the class.
	 */
	public CallArgument(String name, int sqlType, int inputType, String inputContainerGetter, int resultAs, String simpleResultSetter, int attributeType)
	{
		this.name = name;
		this.sqlType = sqlType;
		this.inputType = inputType;
		this.inputContainerGetter = inputContainerGetter;
		this.resultAs = resultAs;
		this.simpleResultSetter = simpleResultSetter;
		this.attributeType = attributeType;
	}

	/**
	 * Returns The name of the getter method in the input container to get the
	 * value of the parameter.
	 * @return String
	 */
	public String getInputContainerGetter()
	{
		return inputContainerGetter;
	}

	/**
	 * Returns the indication whether the parameter is input, output or input/output
	 * @return int
	 */
	public int getInputType()
	{
		return inputType;
	}

	/**
	 * Returns the name of the argument. (referenced from the {@link ArgumentStructureDefinition}.
	 * @return String
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the SQL type of the argument.
	 * @return int
	 */
	public int getSQLType()
	{
		return sqlType;
	}

	/**
	 * Returns the indication whether the value of the parameter will be returned 
	 * as is or as objects graph.
	 * @return int Could be one of the following: {RESULT_AS_SIMPLE, RESULT_AS_STRUCTURE}
	 */
	public int getResultAs()
	{
		return resultAs;
	}

	/**
	 * Returns the name of the setter method in the results container, 
	 * to set the value of the parameter - only for resultAs=simple.
	 * @return String
	 */
	public String getSimpleResultSetter()
	{
		return simpleResultSetter;
	}

	/**
	 * Returns the data type of the attribute in the class.
	 * @return int
	 */
	public int getAttributeType()
	{
		return attributeType;
	}

	/**
	 * Indicates whether the parameter is define as output and should be resulted as is, 
	 * without objects graph construction.
	 * @return boolean
	 */
	public boolean isSimpleOutputArgument()
	{
		return resultAs == RESULT_AS_SIMPLE && inputType != INPUT_TYPE_AS_INPUT; 
	}

	/**
	 * Indicates whether the parameter is defined as output.
	 * @return boolean
	 */
	public boolean isOutputParameter()
	{
		return inputType != INPUT_TYPE_AS_INPUT; 
	}

}
