/*
 * Created on 15/09/2005
 *
 */
package hoshen.scheduler;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import hoshen.common.utils.db.ConnectionManager;
import hoshen.common.utils.db.ConnectionManagerFactory;
import hoshen.common.utils.exception.HoshenException;

/**
 * @author odedn
 * 
 * Contains Some general methods for the Scheduler.
 */
public class HoshenSchdulerUtils {

	private static Logger log = Logger.getLogger(HoshenSchdulerUtils.class);

	public static List findAllTasks() throws HoshenException
	{
		List taskList= new LinkedList();
		String tablePrefix = HoshenSchedulerConfig.getInstance().getTablePrefix();
		String sql = "SELECT  TASKID FROM " + tablePrefix + "TASK";
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		log.debug("sql= " + sql);
		try {						
			conn = ConnectionManagerFactory.getConnectionManager().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				int taskId = rs.getInt(1);
				String s = new Integer(taskId).toString();		
				taskList.add(s);					
			}
		} catch(Exception e) {
			log.error(e);
			throw new HoshenException("Error in findAllTasks",e);
		}
		finally {
			try  {
				if(null != rs) {				
					rs.close();
				}
				if(st != null) {				
					st.close();
				}
				if(conn != null) {				
					conn.close();
				}
			}catch(SQLException sqe){
				
			}
		}	// finally				
		return(taskList);
				
	}	
	
	/**
	 * delete taks info object from the DB.
	 * @param taskId the taskid key.
	 * @throws HoshenException in case of error.
	 */
	public static void deleteTaskInfo(String taskId) throws HoshenException
	{
		String tablePrefix = HoshenSchedulerConfig.getInstance().getTablePrefix();
		String sql = "DELETE FROM " + tablePrefix + "TASK_INFO WHERE TASKID="	+ taskId;
		Connection conn = null;
		Statement st = null;
		try {			
			log.debug(sql);	
			conn = ConnectionManagerFactory.getConnectionManager().getConnection();
			st = conn.createStatement();
			st.executeUpdate(sql);
		
		} catch (Exception e) {
			throw new HoshenException("Error in deleteTaskInfo",e);
		}
		finally {
			try  {
				if(st != null) {				
					st.close();
				}
				if(conn != null) {				
					conn.close();
				}
				
			}catch(SQLException sqe){
				
			}
		} // finally 
		
	}

	/**
	 * find a task info object in the db.
	 * @param taskId the key
	 * @return HoshenTaskInfo 
	 * @throws HoshenException in case of error.
	 */
	public static HoshenTaskInfo findTaskInfo(String taskId) throws HoshenException
	{
		String tablePrefix = HoshenSchedulerConfig.getInstance().getTablePrefix();
	//	String sql = "SELECT  A.TASKID, A.TASK_CLASS_NAME , B.NAME FROM TB_HSCHED_TASK_INFO A, TB_HSCHED_TASK B WHERE A.TASKID=B.TASKID AND A.TASKID="	+ taskId;
		String sql = "SELECT  TASKID, TASK_CLASS_NAME FROM " + tablePrefix + "TASK_INFO WHERE TASKID="	+ taskId;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		HoshenTaskInfo info = new HoshenTaskInfo();
		try {
			
			int id = new Integer(taskId).intValue();
			conn = ConnectionManagerFactory.getConnectionManager().getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if(rs.next()) {
				String className = rs.getString(2);
				//String name = rs.getString(3);
				info.setClassName(className);
				 // info.setName(name);			
			}
		} catch(Exception e) {
			log.error(e);
			throw new HoshenException("Error in findTaskInfo",e);
		}
		finally {
			try  {
				if(null != rs) {				
					rs.close();
				}
				if(st != null) {				
					st.close();
				}
				if(conn != null) {				
					conn.close();
				}
				
			}catch(SQLException sqe){
				
			}
		}					
		return(info);
		
	}
	

	/**
	 * save the task class name for the hoshen task.
	 * @param taskId the id of the task.
	 * @param className the name of the task class to run.
	 * @throws HoshenException in case of error.
	 */
	public static void saveTaskClassName(String taskId, String className) throws HoshenException
	{
		String tablePrefix = HoshenSchedulerConfig.getInstance().getTablePrefix();
		Connection conn = null;
		String sql = "insert into " + tablePrefix + "TASK_INFO values (?, ?)";
		PreparedStatement st = null;
		log.debug(sql);
		try {
			int id = new Integer(taskId).intValue();
			conn = ConnectionManagerFactory.getConnectionManager().getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1,id);
			st.setString(2,className);
			st.execute();			
		} catch(Exception e) {
			log.error(e);
			throw new HoshenException("Error in saveTaskClassName",e);
		}
		finally {
			try  {
				
				if(st != null) {				
					st.close();
				}
				if(conn != null) {				
					conn.close();
				}
				
			}catch(SQLException sqe){
				
			}
			
		}
		
	}
	
	/*
	 * test driver
	 */
	public static void main(String args[])
	{
		try {
			//saveTaskClassName("111020","myclass");
			HoshenTaskInfo info = findTaskInfo("901");			 
			log.debug("taskInfo =" + info.getClassName() + "\tname = " + info.getName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
