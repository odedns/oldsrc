package mataf.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import mataf.operations.JDBCClientOp;
import mataf.services.JdbcConnectionService;

import com.ibm.dse.base.Context;
import com.ibm.dse.base.DSEObjectNotFoundException;
import com.ibm.dse.base.Service;

/**
 * @author yossid
 *
 * This Sql query util enables you to execute a query over a database using JdbcConnectionService
 * defined in the context.
 * It's using the connection and closing it after the query is done.
 */
public class MatafSqlQueryUtil {

	/**
	 * Execute the sqlQuery using the JdbcConnectionService 
	 * and return the query results in a java.util.List.
	 * 
	 * @param
	 * 		ctx  the context to get the JdbcConnectionService from.
	 * @param
	 * 		jdbcServiceName  the name of the JdbcConnctionService.
	 * @param
	 * 		sqlQuery  the query to be executed.
	 * @return
	 * 		A java.util.List contains the records (as java.util.Hashtable) returned as results from the database.
	 * @throws
	 * 		DSEObjectNotFoundException  if there is a problem to get the JdbcConnectionService from the context.
	 * @throws
	 * 		SQLException  if there are problems to execute the sqlQuery.
	 * @throws
	 * 		Exception  if could not get the connection from the service.
	 */
	public static List executeQuery(Context ctx, String jdbcServiceName, String sqlQuery) 
							throws SQLException, DSEObjectNotFoundException, Exception
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		List queryResults = new ArrayList();
		
		try {
			// get the JdbcConnectionService from the context
			JdbcConnectionService srv = (JdbcConnectionService) ctx.getService(jdbcServiceName);			
						
			// execute the query using the connection from the service	
			conn = srv.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery(sqlQuery);
			
			// seting the results in a java.util.List
			String []columnsNames = getColumnsNames(rs);
			while(rs.next()) {
				Hashtable currentRecord = new Hashtable();
				for(int counter=0 ; counter<columnsNames.length ; counter++) {
					currentRecord.put(columnsNames[counter], rs.getString(columnsNames[counter]));
				}
				queryResults.add(currentRecord);
			}
			
			return queryResults;
		} finally {
			// releasing the resources
			if(rs!=null)
				rs.close();
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
		}			
	}
	
	/**
	 * Executes the given SQL statement, which may be an INSERT, UPDATE, or DELETE 
	 * statement or an SQL statement that returns nothing.
	 * @param
	 * 		ctx  the context to get the JdbcConnectionService from.
	 * @param
	 * 		jdbcServiceName  the name of the JdbcConnctionService.
	 * @param
	 * 		sqlStatement  the statement to be executed.
	 * @throws
	 * 		DSEObjectNotFoundException  if there is a problem to get the JdbcConnectionService from the context.
	 * @throws
	 * 		SQLException  if there are problems to execute the sqlQuery.
	 * @throws
	 * 		Exception  if could not get the connection from the service.
	 */
	
	public static void executeUpdate(Context ctx, String jdbcServiceName, String sqlStatement) 
								throws DSEObjectNotFoundException, SQLException, Exception {
		Connection conn = null;
		Statement st = null;
		
		try {
			// get the JdbcConnectionService from the context
			JdbcConnectionService srv = (JdbcConnectionService) ctx.getService(jdbcServiceName);			
			
			// execute the query using the connection from the service	
			conn = srv.getConnection();
			st = conn.createStatement();
			st.executeUpdate(sqlStatement);
			
		} finally {
			// releasing the resources
			if(st!=null)
				st.close();
			if(conn!=null)
				conn.close();
		}
	} 
	
	/**
	 * Get the columns names from the ResultSetMetaData and return it as a String array.
	 * 
	 * Parameters:
	 * 		rs - the ResultSet to get the columns names from.
	 * Returns:
	 * 		column names in a String array.
	 */
	private static String[] getColumnsNames(ResultSet rs) throws SQLException {
		ResultSetMetaData resultSetMetaData = rs.getMetaData();
		
		String[] columnNames = new String[resultSetMetaData.getColumnCount()];
		
		for(int counter=0 ; counter<resultSetMetaData.getColumnCount() ; counter++ ) {
			columnNames[counter] = resultSetMetaData.getColumnName(counter);
		}
		
		return columnNames;
	}

}
