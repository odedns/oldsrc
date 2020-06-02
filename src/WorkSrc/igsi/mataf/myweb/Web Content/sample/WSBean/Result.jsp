<HTML>
<HEAD>
<TITLE>Result</TITLE>
</HEAD>
<BODY>
<H1>Result</H1>

<jsp:useBean id="WSBeanid" scope="session" class="webservices.WSBeanProxy" />

<%!
public static String markup(String text) {
    if (text == null) {
        return null;
    }

    StringBuffer buffer = new StringBuffer();
    for (int i = 0; i < text.length(); i++) {
        char c = text.charAt(i);
        switch (c) {
            case '<':
                buffer.append("&lt;");
                break;
            case '&':
                buffer.append("&amp;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '"':
                buffer.append("&quot;");
                break;
            default:
                buffer.append(c);
                break;
        }
    }
    return buffer.toString();
}
%>
<%!
public static java.lang.String domWriter(org.w3c.dom.Node node,java.lang.StringBuffer buffer)
{
    if ( node == null ) {
        return "";
    }
    int type = node.getNodeType();
    switch ( type ) {
        case org.w3c.dom.Node.DOCUMENT_NODE: {
            buffer.append(markup("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") + "<br>");
            domWriter(((org.w3c.dom.Document)node).getDocumentElement(),buffer);
            break;
        }
        case org.w3c.dom.Node.ELEMENT_NODE: {
             buffer.append(markup("<" + node.getNodeName()));
            org.w3c.dom.Attr attrs[] = sortAttributes(node.getAttributes());
            for ( int i = 0; i < attrs.length; i++ ) {
                org.w3c.dom.Attr attr = attrs[i];
                buffer.append(" " + attr.getNodeName() + "=\"" + markup(attr.getNodeValue()) + "\"");
            }
             buffer.append(markup(">"));
            org.w3c.dom.NodeList children = node.getChildNodes();
            if ( children != null ) {
                int len = children.getLength();
                for ( int i = 0; i < len; i++ ) {
                if(((org.w3c.dom.Node)children.item(i)).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE)
                buffer.append("<br>");
                domWriter(children.item(i),buffer);
                }
            }
            buffer.append(markup("</" + node.getNodeName() + ">"));
            break;
        }
        case org.w3c.dom.Node.ENTITY_REFERENCE_NODE: {
            org.w3c.dom.NodeList children = node.getChildNodes();
            if ( children != null ) {
                int len = children.getLength();
                for ( int i = 0; i < len; i++ )
                {
                buffer.append(children.item(i));
                }
            }
            break;
        }
        case org.w3c.dom.Node.CDATA_SECTION_NODE: {
            buffer.append(markup(node.getNodeValue()));
            break;
        }
        case org.w3c.dom.Node.TEXT_NODE:{
            buffer.append(markup(node.getNodeValue()));
            break;
        }
        case org.w3c.dom.Node.PROCESSING_INSTRUCTION_NODE:{
            buffer.append(markup("<?"));
            buffer.append(node.getNodeName());
            String data = node.getNodeValue();
            if ( data != null && data.length() > 0 ){
                buffer.append(" ");
                buffer.append(data);
            }
            buffer.append(markup("?>"));
            break;
        }
        }
    return buffer.toString();
}
%>
<%!
public static org.w3c.dom.Attr[] sortAttributes(org.w3c.dom.NamedNodeMap attrs)
{
    int len = (attrs != null) ? attrs.getLength() : 0;
    org.w3c.dom.Attr array[] = new org.w3c.dom.Attr[len];
    for ( int i = 0; i < len; i++ ){
        array[i] = (org.w3c.dom.Attr)attrs.item(i);
    }
    for ( int i = 0; i < len - 1; i++ ) {
        String name  = array[i].getNodeName();
        int    index = i;
        for ( int j = i + 1; j < len; j++ ) {
            String curName = array[j].getNodeName();
            if ( curName.compareTo(name) < 0 ) {
                name  = curName;
                index = j;
            }
        }
        if ( index != i ) {
            org.w3c.dom.Attr temp    = array[i];
            array[i]     = array[index];
            array[index] = temp;
        }
    }
    return (array);
}
%>


<%
String method = request.getParameter("method");
if (method == null) method = "";

boolean gotMethod = false;

try {
if (method.equals("setEndPoint(java.net.URL)")) {

        gotMethod = true;
        String url0id=  markup(request.getParameter("url5"));
        java.net.URL url0idTemp  = new java.net.URL(url0id);
        WSBeanid.setEndPoint(url0idTemp);
}else if (method.equals("getEndPoint()")) {

        gotMethod = true;
        java.net.URL mtemp = WSBeanid.getEndPoint();
if(mtemp == null){
%>
<%=mtemp %>
<%
}else{
        String tempResultresult8 = markup(mtemp.toString());
        %>
        <%= tempResultresult8 %>
        <%
}
}else if (method.equals("getData()")) {

        gotMethod = true;
        java.lang.String mtemp = WSBeanid.getData();
if(mtemp == null){
%>
<%=mtemp %>
<%
}else{
        String tempResultresult11 = markup(String.valueOf(mtemp));
        %>
        <%= tempResultresult11 %>
        <%
}
}else if (method.equals("setData(java.lang.String)")) {

        gotMethod = true;
        String data1id=  markup(request.getParameter("data16"));
        java.lang.String data1idTemp  = data1id;
        WSBeanid.setData(data1idTemp);
}} catch (Exception e) { 
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