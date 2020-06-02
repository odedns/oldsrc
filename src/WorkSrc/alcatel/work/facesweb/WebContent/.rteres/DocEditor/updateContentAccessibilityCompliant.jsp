<% /* @copyright jsp */ %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.*" %>
<%@ page import="com.ibm.pvc.wps.docEditor.standards.*" %>
<%@ taglib uri="/WEB-INF/tld/DocEditor.tld" prefix="docEditor" %>
<jsp:useBean id="com_ibm_pvc_wps_docEditor" class="java.lang.String" scope="request"/>
<% String inputContent = request.getParameter("inputContent"); 
 String editorName = request.getParameter("editorName"); 
 String images = (String)request.getParameter("images"); 
	String rulesDir = images.replaceFirst("images", "rules");
	if(rulesDir.indexOf("DocEditor") != -1){
		rulesDir = rulesDir.substring(rulesDir.indexOf("DocEditor")-1);
	}
	String loc = request.getParameter("locale");
	String fileDir = pageContext.getServletContext().getRealPath( ".rteres" + File.separatorChar +rulesDir+ File.separatorChar + "xhtmltags_10.xml");
	File ruleFile = new File (fileDir);//rulesDir+ File.separatorChar + "xhtmltags_10.xml");// + File.separatorChar, "xhtmltags_10.xml" );

	StandardsTokenizer sTokenizer =new StandardsTokenizer();
	String updatedContent = sTokenizer.xhtmlconversion(inputContent,ruleFile);
	StringBuffer sb = new StringBuffer();
	HashSet sh = sTokenizer.geterrorTags();
	ResourceBundle resourceBundle = LocaleHelper.getResourceBundle("com.ibm.pvc.wps.docEditor.nls.DocEditorNLS", loc);
	String temp="rte.xhtml.requiredattributes.";
	Iterator it=sh.iterator();
	StringBuffer warningMsg= new StringBuffer();
	Vector warnings=new Vector();

	String warning_msg = resourceBundle.getString("rte.xhtml.error");
	while(it.hasNext()){
		String temptag=(String)it.next();
		String localestring=temp+temptag;
		String attnames=resourceBundle.getString(localestring);
		String nextWarning = LocaleHelper.substitute(warning_msg, new String[]{attnames});
		//warnings.add(temptag+" tag must have the following attributes "+attnames+". ");
		warnings.add(temptag +" "+ nextWarning);
	}
	for(int i = 0 ; i < warnings.size() ; i++){
		warningMsg.append(" "+warnings.elementAt(i));
	}
    try{
        //System.out.println(inputContent);
        inputContent= updatedContent;
		int i = updatedContent.indexOf('\n');
		
		while (i > 0) {			
			sb = new StringBuffer(updatedContent);
			sb = sb.deleteCharAt(i);
			updatedContent = sb.toString();
			i = updatedContent.indexOf('\n');
		}

		int j = updatedContent.indexOf('\r');
		while (j > 0) {			
			sb = new StringBuffer(updatedContent);
			sb = sb.deleteCharAt(j);
			updatedContent = sb.toString();
			j = updatedContent.indexOf('\r');
		}
    } catch (Exception e) {System.out.println(e);}

%>

<html>
<head>
    <meta http-equiv=content-type content="text/html; charset=utf-8>">

    <script language="javascript">
	
	function updateComplete(){
		//alert("Inside the update complete " );
		var accessibleContent ='<%=updatedContent%>';	
		//alert("accessibleContent : " + accessibleContent);
		var scriptwarning="<%=warningMsg%>";
		if(!scriptwarning==""){
			alert(scriptwarning);
		}
		//alert('writing the accessible content : ' + accessibleContent);
		this.opener.IBM_RTE_getDocument("<%= editorName %>").body.innerHTML = accessibleContent;
		//alert('before the close');
		parent.close();
	}

	function HTMLEncode(str){
		//alert("Inside HTMLEncode ");

		if( !str )
			return '';
		if( typeof(str) != 'string' )
			str = str.toString();

		str = str.replace( /&/g, '&amp;' );
		str = str.replace( /</g, '&lt;' );
		str = str.replace( />/g, '&gt;' );
		str = str.replace( /"/g, '&quot;' );

		return str;
	}
    </script>

</head>
<body onload='updateComplete();'>
    
</body>
</html>

