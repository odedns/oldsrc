<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>
<%@ page import="java.io.IOException" %>

<%!
	public String getProtocolHostPortPrefix (HttpServletRequest req) 
		throws ServletException, IOException
	{									
		int DEFAULT_PROTO_PORT = -1;
		int DEFAULT_HTTP_PORT = 80;
		int DEFAULT_HTTPS_PORT = 443;
     
		StringBuffer sbuf = new StringBuffer ();
		String protocol = req.getScheme();
		//String host = req.getHeader ("host");
		String serverName = req.getServerName();
		int port = req.getServerPort ();
		if (protocol.equals ("http")) {
			if (port == DEFAULT_HTTP_PORT) {
			port = DEFAULT_PROTO_PORT;
			}
		} else if (protocol.equals ("https")) {
			if (port == DEFAULT_HTTPS_PORT) {
			port = DEFAULT_PROTO_PORT;
			}
		}
		sbuf.append (protocol).append("://").append(serverName);
				
		if (port != DEFAULT_PROTO_PORT) {
			sbuf.append(":").append(port);
		}
		return sbuf.toString();
	}

%>

   <% String name  			= (String)request.getAttribute ("rte_name"); %>
   <% String jspContext 	= (String)request.getContextPath() ; %>
   <% String jsp 			= (String)request.getAttribute ("rte_jsp"); %>
   <% String mode 			= (String)request.getAttribute ("rte_mode"); %>
   <% String locale 		= (String)request.getAttribute ("rte_locale"); %>
   <% String imageDirectory = (String)request.getAttribute ("rte_imageDirectory"); %> 
   <% String width 			= (String)request.getAttribute ("rte_width"); %>
   <% String height 		= (String)request.getAttribute ("rte_height"); %>
   <% String listener 		= (String)request.getAttribute ("listener"); %>
   <%-- String formName 		= (String)request.getAttribute ("rte_formName"); --%>
   <% String formItem 		= (String)request.getAttribute ("rte_formItem"); %>
   <% String jspPath 		= jspContext + "/.rteres/DocEditor/"; %>
   <% String mozPath 		= getProtocolHostPortPrefix(request) + jspPath; %>
   <% String eventHandler   = "rteSetEvent_" + name + "()"; %>
   <% String enableDebug    = (String)request.getAttribute ("rte_debug"); 
      if (enableDebug.compareToIgnoreCase("true") != 0) {
        enableDebug = "false";
      } 
   %>
	<% String blankfile = jspContext+"/.rteres/DocEditor/jsf/blank.html"; %>
	<% String tabIndex 		= (String)request.getAttribute ("rte_tabindex"); 
		boolean usetabindex = false;
		if(tabIndex != null && (tabIndex.trim().length() > 0))
			usetabindex = true;

	%>

<script language="javascript">    
    function checkEvent_<%= name %>() {
   	 	
    	//if ( (formname != null) && (formname.length > 0)) {       
        	var elmt = document.getElementById('<%=formItem%>');
        	//if (elmt) {
          	elmt.value = IBM_RTE_getEditorHTMLFragment('<%=name%>');
        	//}        
    	//} else {
        //	return;
    	//}
    } 
    
    function rteSetEvent_<%= name %>() {
        if (IBM_RTE_isMozilla()) {          
          IBM_RTE_getDocument("<%= name %>").addEventListener("focus", checkEvent_<%= name %>, false);
          IBM_RTE_getDocument("<%= name %>").addEventListener("blur",  checkEvent_<%= name %>, false);
        } else {
          IBM_RTE_getFrame("<%= name %>").onfocus = checkEvent_<%= name %>;
          IBM_RTE_getFrame("<%= name %>").onblur  = checkEvent_<%= name %>;
        }
    }
</script>

<%	try{ /* this works on everything bar tomcat 5.0 */

		if(!usetabindex)
		{%> 
	<docEditor:createEditor
	    name="<%=name%>"
	    jspContext="<%=jspContext%>"
	    jsp="<%=jsp%>"
	    imageDirectory="<%=imageDirectory%>"
	    mode="<%=mode%>"
	    locale="<%=locale%>"
	    listener="<%=listener%>"
	    enableDebug="<%=enableDebug%>"
		document="<%=blankfile%>">
	    <docEditor:addEditorParam param="jspPath" value="<%=mozPath%>"/>
	    <docEditor:addEditorParam param="jspCtx" value="<%=jspContext%>" />
	    <docEditor:addEditorParam param="jspDir" value=".rteres/DocEditor/"/>
	    <docEditor:addEditorParam param="evtHandler" value="<%=eventHandler%>"/>
    </docEditor:createEditor>
	<%}else{%>
	<docEditor:createEditor
	    name="<%=name%>"
	    jspContext="<%=jspContext%>"
	    jsp="<%=jsp%>"
	    imageDirectory="<%=imageDirectory%>"
	    mode="<%=mode%>"
	    locale="<%=locale%>"
	    listener="<%=listener%>"
	    enableDebug="<%=enableDebug%>"
		document="<%=blankfile%>"
		tabindex="<%=tabIndex%>">
	    <docEditor:addEditorParam param="jspPath" value="<%=mozPath%>"/>
	    <docEditor:addEditorParam param="jspCtx" value="<%=jspContext%>" />
	    <docEditor:addEditorParam param="jspDir" value=".rteres/DocEditor/"/>
	    <docEditor:addEditorParam param="evtHandler" value="<%=eventHandler%>"/>
    </docEditor:createEditor>
	<%}%>

<%}catch(Exception e){    /* this is the fix to make it work on tomcat 5.0 */
		jspContext = jspContext + "/";
		blankfile = jspContext+ ".rteres/DocEditor/jsf/blank.html";
		jspPath = jspContext + ".rteres/DocEditor/";
 
		if(!usetabindex)
		{%> 
	<docEditor:createEditor
	    name="<%=name%>"
	    jspContext="<%=jspContext%>"
	    jsp="<%=jsp%>"
	    imageDirectory="<%=imageDirectory%>"
	    mode="<%=mode%>"
	    locale="<%=locale%>"
	    listener="<%=listener%>"
	    enableDebug="<%=enableDebug%>"
		document="<%=blankfile%>">     
	    <docEditor:addEditorParam param="jspPath" value="<%=mozPath%>"/>
	    <docEditor:addEditorParam param="jspCtx" value="<%=jspContext%>" />
	    <docEditor:addEditorParam param="jspDir" value=".rteres/DocEditor/"/>
	    <docEditor:addEditorParam param="evtHandler" value="<%=eventHandler%>"/>
    </docEditor:createEditor>
	<%}else{%>
	<docEditor:createEditor
	    name="<%=name%>"
	    jspContext="<%=jspContext%>"
	    jsp="<%=jsp%>"
	    imageDirectory="<%=imageDirectory%>"
	    mode="<%=mode%>"
	    locale="<%=locale%>"
	    listener="<%=listener%>"
	    enableDebug="<%=enableDebug%>"
		document="<%=blankfile%>"
		tabindex="<%=tabIndex%>">     
	    <docEditor:addEditorParam param="jspPath" value="<%=mozPath%>"/>
	    <docEditor:addEditorParam param="jspCtx" value="<%=jspContext%>" />
	    <docEditor:addEditorParam param="jspDir" value=".rteres/DocEditor/"/>
	    <docEditor:addEditorParam param="evtHandler" value="<%=eventHandler%>"/>
    </docEditor:createEditor>
	<%}%>

<%}%>
    
    <docEditor:getEditor name="<%=name%>" width="<%=width%>" height="<%=height%>"/>

    
