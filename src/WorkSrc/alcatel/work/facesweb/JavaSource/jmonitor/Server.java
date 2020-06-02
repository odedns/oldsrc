/* Created on 31/07/2006 */
package jmonitor;

import org.apache.log4j.Logger;

import com.ibm.websphere.management.AdminClient;

/**
 * 
 * @author odedn
 */
public class Server {
	private final static Logger logger = Logger.getLogger(Server.class);
	private String name;
	private String serverName;
	private String host;
	private long pid;
	private String  state;
	private String productName;
	private boolean selected = false;
	private String port;
	private String cellName;
	private String nodeName;
	private String platformName;
	private String platformVersion;
	private String serverVendor;
	private AdminClient adminClient = null;
	private JVMInfo jvmInfo = null;
	
	/**
	 * Constructor.
	 * @param name
	 * @param pid
	 * @param status
	 * @param productName
	 */
	public Server(String name, String host,long pid, String state, String productName,
			String port)
	{
		this.name = name;
		this.host = host;
		this.pid = pid;
		this.state = state;
		this.productName = productName;
		this.port = port;
	}
	
	/**
	 * 
	 */
	public Server()
	{
		
		// TODO Auto-generated constructor stub
	}

	/**
	 * Getter for name. <br>
	 * 
	 * @return name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * Setter for name. <br>
	 * 
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * Getter for pid. <br>
	 * 
	 * @return pid
	 */
	public long getPid()
	{
		return pid;
	}
	/**
	 * Setter for pid. <br>
	 * 
	 * @param pid the pid to set
	 */
	public void setPid(long pid)
	{
		this.pid = pid;
	}
	/**
	 * Getter for productName. <br>
	 * 
	 * @return productName
	 */
	public String getProductName()
	{
		return productName;
	}
	/**
	 * Setter for productName. <br>
	 * 
	 * @param productName the productName to set
	 */
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	
	/**
	 * Getter for selected. <br>
	 * 
	 * @return selected
	 */
	public boolean isSelected()
	{
		return selected;
	}
	/**
	 * Setter for selected. <br>
	 * 
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	/**
	 * Getter for machineName. <br>
	 * 
	 * @return machineName
	 */
	public String getHost()
	{
		return host;
	}
	/**
	 * Setter for machineName. <br>
	 * 
	 * @param machineName the machineName to set
	 */
	public void setHost(String host)
	{
		this.host = host;
	}
	/**
	 * Getter for port. <br>
	 * 
	 * @return port
	 */
	public String getPort()
	{
		return port;
	}
	/**
	 * Setter for port. <br>
	 * 
	 * @param port the port to set
	 */
	public void setPort(String port)
	{
		this.port = port;
	}
	/**
	 * Getter for adminClient. <br>
	 * 
	 * @return adminClient
	 */
	public AdminClient getAdminClient()
	{
		return adminClient;
	}
	/**
	 * Setter for adminClient. <br>
	 * 
	 * @param adminClient the adminClient to set
	 */
	public void setAdminClient(AdminClient adminClient)
	{
		this.adminClient = adminClient;
	}
	/**
	 * Getter for serverName. <br>
	 * 
	 * @return serverName
	 */
	public String getServerName()
	{
		return serverName;
	}
	/**
	 * Setter for serverName. <br>
	 * 
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}
	/**
	 * Getter for state. <br>
	 * 
	 * @return state
	 */
	public String getState()
	{
		return state;
	}
	/**
	 * Setter for state. <br>
	 * 
	 * @param state the state to set
	 */
	public void setState(String state)
	{
		this.state = state;
	}
	/**
	 * Getter for cellName. <br>
	 * 
	 * @return cellName
	 */
	public String getCellName()
	{
		return cellName;
	}
	/**
	 * Setter for cellName. <br>
	 * 
	 * @param cellName the cellName to set
	 */
	public void setCellName(String cellName)
	{
		this.cellName = cellName;
	}
	/**
	 * Getter for nodeName. <br>
	 * 
	 * @return nodeName
	 */
	public String getNodeName()
	{
		return nodeName;
	}
	/**
	 * Setter for nodeName. <br>
	 * 
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}
	/**
	 * Getter for platformName. <br>
	 * 
	 * @return platformName
	 */
	public String getPlatformName()
	{
		return platformName;
	}
	/**
	 * Setter for platformName. <br>
	 * 
	 * @param platformName the platformName to set
	 */
	public void setPlatformName(String platformName)
	{
		this.platformName = platformName;
	}
	/**
	 * Getter for serverVendor. <br>
	 * 
	 * @return serverVendor
	 */
	public String getServerVendor()
	{
		return serverVendor;
	}
	/**
	 * Setter for serverVendor. <br>
	 * 
	 * @param serverVendor the serverVendor to set
	 */
	public void setServerVendor(String serverVendor)
	{
		this.serverVendor = serverVendor;
	}
	/**
	 * Getter for platformVersion. <br>
	 * 
	 * @return platformVersion
	 */
	public String getPlatformVersion()
	{
		return platformVersion;
	}
	/**
	 * Setter for platformVersion. <br>
	 * 
	 * @param platformVersion the platformVersion to set
	 */
	public void setPlatformVersion(String platformVersion)
	{
		this.platformVersion = platformVersion;
	}
	
	

	/**
	 * Getter for jvmInfo. <br>
	 * 
	 * @return jvmInfo
	 */
	public JVMInfo getJvmInfo()
	{
		return jvmInfo;
	}
	/**
	 * Setter for jvmInfo. <br>
	 * 
	 * @param jvmInfo the jvmInfo to set
	 */
	public void setJvmInfo(JVMInfo jvmInfo)
	{
		this.jvmInfo = jvmInfo;
	}
}
