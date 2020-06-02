/**
 * Created on 09/01/2005
 * @author P0006439
 * @version $id$
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package dyncollector;

import java.util.*;


public class MethodInfo
{
	private String methodName;
	private List args;
	private List params;
			
	/**
	 * constuctor create a methodInfo object.
	 * @param methodName the name of the method.
	 */
	public MethodInfo(String methodName)
	{
		this.methodName = methodName;
		args = new LinkedList();
		params = new LinkedList();		
	}

	/**
	 * add parameter to the list of parameters
	 * for the method.
	 * @param param the object containing parameter data.
	 */
	public void addParam(Object param)
	{
		params.add(param);
	}
	
	/**
	 * add argument class .
	 * @param arg
	 */
	public void addArgument(Class arg)
	{
		args.add(arg);
	}
	
	
	/**
	 * @return
	 */
	public String getMethodName()
	{
		return methodName;
	}


	/**
	 * @param string
	 */
	public void setMethodName(String string)
	{
		methodName = string;
	}

}
