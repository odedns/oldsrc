/* Created on 15/08/2006 */
package jmonitor;

import java.io.Serializable;

/**
 * 
 * @author Odedn
 */
public class JVMInfo implements Serializable {
	private long heapSize;
	private long freeMemory;
	private String javaVendor;
	private String javaVersion;
	
	

	/**
	 * Getter for freeMemory. <br>
	 * 
	 * @return freeMemory
	 */
	public long getFreeMemory()
	{
		return freeMemory;
	}
	/**
	 * Setter for freeMemory. <br>
	 * 
	 * @param freeMemory the freeMemory to set
	 */
	public void setFreeMemory(long freeMemory)
	{
		this.freeMemory = freeMemory;
	}
	/**
	 * Getter for heapSize. <br>
	 * 
	 * @return heapSize
	 */
	public long getHeapSize()
	{
		return heapSize;
	}
	/**
	 * Setter for heapSize. <br>
	 * 
	 * @param heapSize the heapSize to set
	 */
	public void setHeapSize(long heapSize)
	{
		this.heapSize = heapSize;
	}
	/**
	 * Getter for javaVendor. <br>
	 * 
	 * @return javaVendor
	 */
	public String getJavaVendor()
	{
		return javaVendor;
	}
	/**
	 * Setter for javaVendor. <br>
	 * 
	 * @param javaVendor the javaVendor to set
	 */
	public void setJavaVendor(String javaVendor)
	{
		this.javaVendor = javaVendor;
	}
	/**
	 * Getter for javaVersion. <br>
	 * 
	 * @return javaVersion
	 */
	public String getJavaVersion()
	{
		return javaVersion;
	}
	/**
	 * Setter for javaVersion. <br>
	 * 
	 * @param javaVersion the javaVersion to set
	 */
	public void setJavaVersion(String javaVersion)
	{
		this.javaVersion = javaVersion;
	}
}
