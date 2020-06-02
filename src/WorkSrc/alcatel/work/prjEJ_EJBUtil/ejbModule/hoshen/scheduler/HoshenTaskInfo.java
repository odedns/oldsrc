package hoshen.scheduler;

import java.util.Date;


/**
 * Created on 11/09/2005
 * @author odedn
 *
 * This class holds information for a HoshenTask to be submitted to the
 * WAS Scheduler.
 *
 */
public class HoshenTaskInfo {
	private String m_name;
	private String m_className;
	private Date m_startDate;
	private String m_repeatInterval;
	private int m_numberOfRepeats;
	private int m_priority;
	
	/**
	 * empty constructor.
	 *
	 */
	public HoshenTaskInfo()
	{
		
	}
	/**
	 * Constructor - create a HoshenTaskInfo object.
	 * @param name the task name
	 * @param className the implementation class name
	 * @param startDate the startDate date object.
	 * @param repeatInterval the repeatInterval string.
	 * @param numberOfRepeats the number ofrepeats for the task.
	 */
	public HoshenTaskInfo(String name, String className, Date startDate, String repeatInterval,
			int numberOfRepeats, int priority)
	{		
		m_name = name;
		m_className = className;
		m_startDate = startDate;
		m_repeatInterval =  repeatInterval;
		m_numberOfRepeats = numberOfRepeats;
		m_priority = priority;
	}


	/**
	 * @return the implementation class name
	 */
	public String getClassName() {
		return m_className;
	}

	/**
	 * @return
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * @return
	 */
	public int getNumberOfRepeats() {
		return m_numberOfRepeats;
	}

	/**
	 * @return the repeat interval string.
	 */
	public String getRepeatInterval() {
		return m_repeatInterval;
	}

	/**
	 * @return the startDate date object.
	 */
	public Date getStartDate() {
		return m_startDate;
	}

	/**
	 * @param string
	 */
	public void setClassName(String string) {
		m_className = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		m_name = string;
	}

	/**
	 * @param i
	 */
	public void setNumberOfRepeats(int i) {
		m_numberOfRepeats = i;
	}

	/**
	 * @param string
	 */
	public void setRepeatInterval(String string) {
		m_repeatInterval = string;
	}

	/**
	 * @param date
	 */
	public void setStartDate(Date date) {
		m_startDate = date;
	}

}