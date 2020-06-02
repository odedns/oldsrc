
import java.sql.*;
import java.util.Properties;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Oded Nissan
 * @version 1.0
 */

public class ConvertAnswers {

	Connection m_conn = null;

	/**
	 * Constructor. init the connnection.
	 */
	public ConvertAnswers()
	{

	}


	void close()
	{
		ConnectionManager.close();
	}
	/**
	 * convert the amswers in the data_text table.
	 */
	void convert()
	{
		Connection conn = ConnectionManager.getConnection();
		Statement st = null;
		Statement st2 = null;
		Statement updSt = null;
		ResultSet rs = null;
		ResultSet rs2 = null;

		int id;
		int propId;
		int propInst;
		int nPropId;
		String sql="select distinct obj_id,prop_id, prop_inst from data_bin where prop_id=12 or prop_id=13 or prop_id=14 or prop_id=2 or prop_id=9 order by obj_id,prop_id, prop_inst";



		try {
			st = conn.createStatement();
			updSt = conn.createStatement();
			rs = st.executeQuery(sql);
			while(rs.next()) {
				id = rs.getInt(1);
				propId= rs.getInt(2);
				propInst = rs.getInt(3);
				System.out.println(id + ":" + propId + ":" + propInst);
				if(propId==2 || propId==9) {
					nPropId=10;
				} else {
					nPropId = propId - 10;
				}
				st2 = conn.createStatement();
				rs2 = st2.executeQuery("select obj_id from data_text where obj_id =" + id +
					    " and prop_id=" +nPropId + "and prop_inst=" + propInst);
				if(!rs2.next()) {
					String updSql = "insert into data_text values (" + id +
							"," + nPropId + "," + propInst + ",'')" ;
					System.out.println(updSql);
					updSt.executeUpdate(updSql);
				}
				rs2.close();
				rs2 = null;
				st2.close();
				st2 = null;



			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			updSt.close();
			updSt = null;

		}  catch (SQLException ex) {
				ex.printStackTrace();
		} finally {
			try {
				if(null != rs) {
					rs.close();
				}
				if(st != null) {
					st.close();
				}
				if(rs2 != null) {
					rs2.close();
				}
				if(st2 != null) {
					st2.close();
				}
				if(updSt != null) {
					updSt.close();
				}


		    } catch(SQLException se) {
				se.printStackTrace();
		    }
		} //finally
	}   // convert


	/**
	 * Main program.
	 */
	 public static void main(String argv[])
	 {

		ConvertAnswers cva = new ConvertAnswers();
		cva.convert();
		cva.close();
	 } // main




} // Convert Answers
