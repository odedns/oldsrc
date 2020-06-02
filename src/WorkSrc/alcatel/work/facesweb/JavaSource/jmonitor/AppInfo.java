/* Created on 18/10/2006 */
package jmonitor;

/**
 * 
 * @author Odedn
 */
public class AppInfo {
	private String name;
	private int state;
	public static final int APP_RUNNING = 1; 
	public static final int APP_STOPPED = 3;
	public static final int APP_UNKNOWN = -1;
	public static final String APP_RUNNING_VALUE = "STARTED";
	public static final String APP_STOPPED_VALUE = "STOPPED";
	public static final String APP_UNKNOWN_VALUE = "UNKNOWN";

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
	 * Getter for state. <br>
	 * 
	 * @return state
	 */
	public int getState()
	{
		return state;
	}
	/**
	 * Setter for state. <br>
	 * 
	 * @param state the state to set
	 */
	public void setState(int state)
	{
		this.state = state;
	}
	
	public String getStateValue()
	{
		String val = null;
		
		switch(state) {
			case APP_RUNNING:
				val = APP_RUNNING_VALUE;
				break;
			case APP_STOPPED:
				val = APP_STOPPED_VALUE;
				break;
			default:
				val = APP_UNKNOWN_VALUE;
				break;
					
		}
		return(val);
	}
}
