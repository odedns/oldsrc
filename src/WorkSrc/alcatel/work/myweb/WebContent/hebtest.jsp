<%@page import="java.util.*" %>
<BR>
<BR></P>


<p> Testing hebrew hash table key
<p>
<%

HashMap map = new HashMap();

map.put("?????","hebrew");
map.put("??????","english");
map.put("??????","french");

String s = (String) map.get("?????");

out.println("found value = " + s);





%>