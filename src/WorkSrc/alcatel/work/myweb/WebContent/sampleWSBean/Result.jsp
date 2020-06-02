<%@page contentType="text/html;charset=UTF-8"%><HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="sampleWSBeanid" scope="session" class="webservices.WSBean" />

<%
String method = request.getParameter("method");
int methodID = 0;
if (method == null) methodID = -1;

if(methodID != -1) methodID = Integer.parseInt(method);
boolean gotMethod = false;

try {
switch (methodID){ 
case 2:
        gotMethod = true;
        String data_0id=  request.getParameter("data5");
        java.lang.String data_0idTemp  = data_0id;
        sampleWSBeanid.setData(data_0idTemp);
break;
case 7:
        gotMethod = true;
        java.lang.String getData7mtemp = sampleWSBeanid.getData();
if(getData7mtemp == null){
%>
<%=getData7mtemp %>
<%
}else{
        String tempResultreturnp8 = webserviceutils.com.ibm.etools.webservice.util.JspUtils.markup(String.valueOf(getData7mtemp));
        %>
        <%= tempResultreturnp8 %>
        <%
}
break;
case 10:
        gotMethod = true;
        %>
        <jsp:useBean id="java1util1HashMap_1id" scope="session" class="java.util.HashMap" />
        <%
        java.util.HashMap execute10mtemp = sampleWSBeanid.execute(java1util1HashMap_1id);
if(execute10mtemp == null){
%>
<%=execute10mtemp %>
<%
}else{
%>
<TABLE>
<TR>
<TD COLSPAN="3" ALIGN="LEFT">returnp:</TD>
<TR>
<TD WIDTH="5%"></TD>
<TD COLSPAN="2" ALIGN="LEFT">empty:</TD>
<TD>
<%
if(execute10mtemp != null){
%>
<%=execute10mtemp.isEmpty()
%><%}%>
</TD>
</TABLE>
<%
}
break;
}
} catch (Exception e) { 
%>
exception: <%= e %>
<%
return;
}
if(!gotMethod){
%>
result: N/A
<%
}
%>
</BODY>
</HTML>