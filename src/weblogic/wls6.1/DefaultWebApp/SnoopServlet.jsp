<html>
<!-- Copyright (c) 1999-2001 by BEA Systems, Inc. All Rights Reserved.-->
<head>
<title>Snoop Servlet</title>
</head>

<body bgcolor=#FFFFFF>
<p><img src="images/BEA_Button_Final_web.gif" align=right>
<font face="Helvetica">

<h2>
<font color=#DB1260>
Snoop Servlet
</font>
</h2>

<p>
This servlet returns information about the HTTP request
itself. You can modify this servlet to take this information
and store it elsewhere for your HTTP server records. This
servlet is also useful for debugging.

<h3>
Servlet Spec Version Implemented
</h3>

<pre>
<%= getServletConfig().getServletContext().getMajorVersion() + "." + getServletConfig().getServletContext().getMinorVersion() %>
</pre>

<h3>
Requested URL
</h3>

<pre>
<%= HttpUtils.getRequestURL(request) %>
</pre>

<h3>
Request parameters
</h3>

<pre>
<%
Enumeration enum = request.getParameterNames();
while(enum.hasMoreElements()){
  String key = (String)enum.nextElement();
  String[] paramValues = request.getParameterValues(key);
  for(int i=0;i < paramValues.length;i++){
      out.println(key + " : "  + paramValues[i]); 
  }
}
%>
</pre>

<h3>
Request information
</h3>

<pre>
Request Method: <%= request.getMethod() %>
Request URI: <%= request.getRequestURI() %>
Request Protocol: <%= request.getProtocol() %>
Servlet Path: <%= request.getServletPath() %>
Path Info: <%= request.getPathInfo() %>
Path Translated: <%= request.getPathTranslated() %>
Query String: <%= request.getQueryString() %>
Content Length: <%= request.getContentLength() %>
Content Type: <%= request.getContentType() %>
Server Name: <%= request.getServerName() %>
Server Port: <%= request.getServerPort() %>
Remote User: <%= request.getRemoteUser() %>
Remote Address: <%= request.getRemoteAddr() %>
Remote Host: <%= request.getRemoteHost() %>
Authorization Scheme: <%= request.getAuthType() %>
</pre>

<h3>Certificate Information</h3>
<pre>
<%
  try {
    weblogic.security.X509 certs [] = (weblogic.security.X509 [])
	request.getAttribute("javax.net.ssl.peer_certificates");

    if (certs != null) {
      weblogic.security.JDK11Certificate jdk11cert = new weblogic.security.JDK11Certificate(certs[0]);
%>
Subject Name : <%= jdk11cert.getPrincipal().getName() %> <br>
Issuer Name :<%= jdk11cert.getGuarantor().getName() %> <br>
Certificate Chain Length : <%= certs.length %> <br>
<%
      // List the Certificate chain
      for (int i=0; i<certs.length;i++) {
%>  Certificate[<%= i %>] : <%= certs[i].toString() %> 
<%
      } // end of for loop
%>
<%
    } 
    else // certs==null 
    {
%>
Not using SSL or client certificate not required.
<%
    }
  } catch (ClassCastException cce) {
    System.out.println(cce.getMessage());
    cce.printStackTrace();
  }
%>
</pre>


<h3>
Request headers
</h3>

<pre>
<%
enum = request.getHeaderNames();
while (enum.hasMoreElements()) {
  String name = (String)enum.nextElement();
  out.println(name + ": " + request.getHeader(name));
}
%>
</pre>

<p>
<font size=-1>Copyright &copy; 1999-2000 by BEA Systems, Inc. All Rights Reserved.
</font>

</font>
</body>
</html>
