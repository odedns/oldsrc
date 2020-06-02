package tests;
import java.io.*;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.dse.base.DSEOperation;

/**
 * @version 	1.0
 * @author
 */
public class TestServlet extends HttpServlet {

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		out.println("in doGet ...");
		try {
			/*
			DSEOperation op = (DSEOperation) Class.forName("tests.ClientTest1").newInstance();
			op.execute();
*/
			DSEOperation op = (DSEOperation) Class.forName("tst.Test1").newInstance();
			op.execute();
		} catch(Exception e) {
			out.println("Exception e = " + e);	
		}
	}

	/**
	* @see javax.servlet.http.HttpServlet#void (javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	*/
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {

	}

}
