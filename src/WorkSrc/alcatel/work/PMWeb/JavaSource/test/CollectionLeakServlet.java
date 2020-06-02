package test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CollectionLeakServlet extends HttpServlet implements Servlet {
	
	private static List tbl = new LinkedList();
	
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */	
	public CollectionLeakServlet() {
		super();
	}

	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doGet(HttpServletRequest arg0, HttpServletResponse arg1)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter pw = resp.getWriter();
		pw.println("<HTML><BODY>");
		pw.println("<H1> CollectionLeakServlet</H1>");		
		tbl.add(new String("item-" + System.currentTimeMillis()));
		pw.println("<p> items in table: " + tbl.size());
		
		pw.println("</BODY></HTML>");
	}

}