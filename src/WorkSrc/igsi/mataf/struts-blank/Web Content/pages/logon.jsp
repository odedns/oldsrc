<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="/tags/struts-bean" prefix="bean" %>
<%@ taglib uri="/tags/struts-html" prefix="html" %>
<%@ taglib uri="/tags/struts-logic" prefix="logic" %>

<html:html locale="true">
<head>
<title> <bean:message key="logon.title"/> 
</title>
<html:base/>
</head>
<body bgcolor="white">

<html:errors/>

<html:form action="/logon" focus="username"
         onsubmit="return validateLogonForm(this);">
<table border="0" width="100%">

  <tr>
    <th align="right">
      <bean:message key="prompt.username"/>:
    </th>
    <td align="left">
      <html:text property="username" size="16" maxlength="18"/>
    </td>
  </tr>

  <tr>
    <th align="right">    
      <bean:message key="prompt.password"/>:            
    </th>
    <td align="left">
      <html:password property="password" size="16" maxlength="18"
                    redisplay="false"/>
    </td>
  </tr>

  <tr>
    <td align="right">
      <html:submit value="Submit"/>
    </td>
    <td align="left">
      <html:reset/>
    </td>
  </tr>

</table>

</html:form>

<html:javascript formName="logonForm"
        dynamicJavascript="true"
         staticJavascript="false"/>
       <!--  
<script language="Javascript1.1" src="staticJavascript.jsp"></script>
-->
</body>
</html:html>