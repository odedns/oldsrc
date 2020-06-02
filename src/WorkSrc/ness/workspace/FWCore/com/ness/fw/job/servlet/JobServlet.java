/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job.servlet;

import java.io.*;
import java.util.HashMap;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ness.fw.bl.proxy.ServerExternalizer;
import com.ness.fw.bl.proxy.ServerExternalizerException;
import com.ness.fw.common.exceptions.FatalException;
import com.ness.fw.job.JobOperator;

public class JobServlet extends HttpServlet
{

	/**
	 * initialize the servlet.
	 */
	public void init() throws ServletException
	{
		initializeConfiguration();
	}

	/**
	 * 
	 */
	private void initializeConfiguration() throws ServletException
	{
		try
		{
			ServerExternalizer.initialize("/config/servers");
		}
		catch (ServerExternalizerException e)
		{
			throw new ServletException();
		}

	}

	/**
	 * 
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
				
		String inputLine;
		String userParamString = "";
		
		//Architecturally, when the client writes to a a URLConnection,
		//the data is encapsulated in an HTTP message and when it reads,
		//it reads data that is similarly returned as part of an HTTP message. 
		//On the server side, such data when directed to a Servlet will 
		//become available in the doPost method through the HttpServletRequest 
		//InputStream.  However, it must be read from that Stream using an
		//appropriate Reader rather than relying on the Servlet's 
		//getParameterNames or getParameterValues methods.

		// getting the parameter that sent from the client
		while ((inputLine = in.readLine()) != null)  
		{
			userParamString = userParamString + inputLine;
		}
	
		in.close();

		int jobId;
		HashMap userParams = HttpUtils.parseQueryString(userParamString);
		String job = (String)userParams.get("jobId");
		if (job != null)
		{
			jobId = Integer.parseInt(job);
		}
		else
		{
			jobId = Integer.parseInt(request.getParameter("jobId"));
		}
		
		try
		{
			JobOperator.run(jobId);
		}
		catch (FatalException e)
		{
			throw new ServletException("fatal",e);
		}
	}	
}
