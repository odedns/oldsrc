<%@ page contentType="text/html; charset=windows-1255" %>
<%@ page errorPage="Jsp1ErrorPage.jsp" %>
<HTML>
<HEAD>
<jsp:useBean id="Jsp1BeanId" scope="session" class=".Jsp1Bean" />
<jsp:setProperty name="Jsp1BeanId" property="*" />
<TITLE>
Jsp1
</TITLE>
</HEAD>
<BODY>
<H1>
JBuilder Generated JSP
</H1>
<FORM method="post">
<BR>Enter new value   :  <INPUT NAME="sample"><BR>
<BR><BR>
<INPUT TYPE="SUBMIT" NAME="Submit" VALUE="Submit">
<INPUT TYPE="RESET" VALUE="Reset">
<BR>
Value of Bean property is :<jsp:getProperty name="Jsp1BeanId" property="sample" />
</FORM>
</BODY>
</HTML>
