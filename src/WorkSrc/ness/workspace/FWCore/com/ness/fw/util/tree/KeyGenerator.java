package com.ness.fw.util.tree;

/**
 * @author gtabakman
 *
 * A key generator for the Node class
 */
public class KeyGenerator
{

	private int numerator;

	/**
	 * Default constructor.
	 */
	public KeyGenerator()
	{
	}
	
	/**
	 * Generate keys starting from the specified initial value.
	 * @param initialValue the initial value.
	 */
	public KeyGenerator(int initialValue)
	{
		this.numerator = initialValue;
	}
	
	/**
	 * Generate a unique key.
	 * The generator is merely a static int that is incremented on each method call.
	 * @return the unique key.
	 */
	protected synchronized Integer nextKey()
	{
		return new Integer(numerator++);
	}

}
