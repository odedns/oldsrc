/*
 * Created on: 02/11/2004
 * Author: baruch hizkya
 */
package com.ness.fw.job;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

/**
 * A Helper class to open connection to the JobServlet
 * Any scheduler task should be run it. 
 */
public class HTTPRequestor
{
	public static final String LOGGER_CONTEXT = "JOBS_SERVLET";

	public static void main(String args[]) throws Exception
	{
		HTTPRequestor requestor = new HTTPRequestor();
		if (args.length > 0)
		{
			int jobId = Integer.parseInt(args[0]);
			requestor.runServlet(jobId);
		}
	}

	/**
	 * Open a url connection and send request to the servlet with jobId
	 * @param jobId
	 * @throws Exception
	 */
	public void runServlet(int jobId) throws Exception
	{
		URLConnection connection;
		try
		{

			String servletUrl = ResourceBundle.getBundle("batch").getString("servletUrl");		
			// opening the connection
			connection = new URL(servletUrl).openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches (false);
			connection.setDefaultUseCaches (false);

			// adding jobId parameter
			PrintWriter out = new PrintWriter(connection.getOutputStream());
			out.println("jobId=" + jobId);
			out.close();

			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
			{ 
				System.out.println(inputLine);
			}
			in.close();
		}
		catch (MalformedURLException e)
		{
			throw new Exception("error while running servlet",e);
		}
		
		catch (IOException e)
		{
			throw new Exception("error while running servlet",e);
		}  
	}
}
