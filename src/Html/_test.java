/*
* This code was automatically generated at 17:53:30 on 08/09/2001
* by weblogic.servlet.jsp.Jsp2Java -- do not edit.
*/

package jsp_servlet;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

// User imports
import java.util.*; //[ /test.jsp; Line: 5]


// built-in init parameters:
// boolean             _verbose -- wants debugging

// Well-known variables:
// JspWriter out                  -- to write to the browser
// HttpServletRequest  request    -- the request object.
// HttpServletResponse response   -- the response object.
// PageContext pageContext        -- the page context for this JSP
// HttpSession session            -- the session object for the client (if any)
// ServletContext application     -- The servlet (application) context
// ServletConfig config           -- The ServletConfig for this JSP
// Object page                    -- the instance of this page's implementation class (i.e., 'this')

/**
* This code was automatically generated at 17:53:30 on 08/09/2001
* by weblogic.servlet.jsp.Jsp2Java -- do not edit.
*
* Copyright (c) 2001 by BEA Systems, Inc. All Rights Reserved.
*/
public class _test
extends
weblogic.servlet.jsp.JspBase
implements weblogic.servlet.jsp.StaleIndicator
{
    
    int cnt = 0;  //[ /test.jsp; Line: 6]
    // StaleIndicator interface
    public boolean _isStale() {
        weblogic.servlet.internal.WebAppServletContext sci =(weblogic.servlet.internal.WebAppServletContext)(getServletConfig().getServletContext());
        java.io.File f = null;
        long lastModWhenBuilt = 0L;
        if (sci.isResourceStale("/test.jsp", 999964403099L, "6.0 12/13/2000 22:49:45 #94427")) return true;
        return false;
    }
    
    
    
    
    public void _jspService(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws java.io.IOException, javax.servlet.ServletException 
    {  
        
        // declare and set well-known variables:
        javax.servlet.ServletConfig config = getServletConfig();
        javax.servlet.ServletContext application = config.getServletContext();
        // variables for Tag extension protocol
        
        Object page = this;
        javax.servlet.jsp.JspWriter out;
        PageContext pageContext =
        JspFactory.getDefaultFactory().getPageContext(this, request, response, null, true, 8192, true);
        
        out = pageContext.getOut();
        HttpSession session = request.getSession(true);
        
        
        
        try { // begin check exception try block
            if (false) throw new ServletException("needed to compile some pages");
            if (false) throw new IOException("needed to compile some pages");
            
            out.print("\n");
            out.print("\n");
            out.print("\n");
            //[ /test.jsp; Line: 7]
            out.println("test jsp page"); //[ /test.jsp; Line: 8]
            if(session.isNew() ) { //[ /test.jsp; Line: 9]
                out.println("session is new .."); //[ /test.jsp; Line: 10]
            }else { //[ /test.jsp; Line: 11]
                out.println("existing session id = " + session.getId()); //[ /test.jsp; Line: 12]
            } //[ /test.jsp; Line: 13]
            //[ /test.jsp; Line: 14]
            //[ /test.jsp; Line: 15]
            //[ /test.jsp; Line: 15]
            out.print("\n\t<p>\n");
            //[ /test.jsp; Line: 17]
            out.println(request.toString()); //[ /test.jsp; Line: 18]
            Enumeration e = request.getHeaderNames(); //[ /test.jsp; Line: 19]
            while(e.hasMoreElements()) { //[ /test.jsp; Line: 20]
                //[ /test.jsp; Line: 21]
                //[ /test.jsp; Line: 21]
                out.print("\n\t<p> \n\t");
                //[ /test.jsp; Line: 22]
                String s = (String)e.nextElement();	 //[ /test.jsp; Line: 23]
                out.println(s + "\t" + request.getHeader(s)); //[ /test.jsp; Line: 24]
            } //[ /test.jsp; Line: 25]
            //[ /test.jsp; Line: 26]
            //[ /test.jsp; Line: 26]
            out.print("\n\n");
        } catch (RuntimeException __ee) { // propagate runtime exception
            throw __ee;
        } catch (IOException __ee) { // propagate ioexception
            throw __ee;
        } catch (ServletException __ee) { // propagate servletexception
            throw __ee;
        } catch (Exception __ee) { // wrap all others
            String __classname = getClass().getName();
            ServletException __filtered = new weblogic.servlet.jsp.JspFilterException(__ee, pageContext.getServletContext(), __classname.substring(0, __classname.lastIndexOf(".")));
            throw __filtered;
        } // end exception handling block
        
        
        //before final close brace...
    }
    
    
}
