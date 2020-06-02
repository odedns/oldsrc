<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<HTML >
<HEAD>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@taglib uri="../WEB-INF/tld/onjlib.tld" prefix="my" %>
<%@ page 
language="java"
contentType="text/html; charset=WINDOWS-1255"
pageEncoding="WINDOWS-1255"
%>
<META http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../css/Master.css" rel="stylesheet" type="text/css">
  
<LINK href="../css/suck_men.css" rel="stylesheet" type="text/css">
<LINK href="../css/style.css" rel="stylesheet" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/stylesheet.css"	title="Style" >


<script type="text/javascript"><!--//--><![CDATA[//><!--

sfHover = function() {
	var sfEls = document.getElementById("nav").getElementsByTagName("LI");
	for (var i=0; i<sfEls.length; i++) {
		sfEls[i].onmouseover=function() {
			this.className+=" sfhover";
		}
		sfEls[i].onmouseout=function() {
			this.className=this.className.replace(new RegExp(" sfhover\\b"), "");
		}
	}
}
if (window.attachEvent) window.attachEvent("onload", sfHover);

//--><!]]></script>

<TITLE>a.jsp</TITLE>
</HEAD>
<f:view>
	<BODY>
	<P><BR>
	
	</P>
		<h:form styleClass="form" id="form1">
		 
		 <my:display id="mDisplay" value="#{sharedBean.displayState}" >
			<my:imageButton src="../css/submit.gif" id="test" action="#{sharedBean.testAction}"></my:imageButton>		
		</my:display>
			<BR>
			<my:menu id="mymenu2" label="mymenu" styleClass="outputText" value="#{sharedBean.items}"/>
			<br>
			
			<%-- 
			<my:menu id="mymenu3" label="File Menu" styleClass="outputText" >
					<my:menuItem id="open" label="Open" action="#{sharedBean.item1Action}" />
					<my:menuItem id="save" label="Save" action="#{sharedBean.item2Action}" />
					<my:menuItem id="exit" label="Exit" action="#{sharedBean.item3Action}" />
			</my:menu>
			--%>
	
	
				
		
	     <my:panelTabbed labelAreaClass="labels"
          selectedLabelClass="selected-tab" unselectedLabelClass="tab">
		
		 <h:panelGrid columns="2">

            <f:facet name="label">
              <my:tabLabel>				
                <h:outputText value="tab-1" />
              </my:tabLabel>
            </f:facet>
            <f:verbatim>
			<p> Some data
			</f:verbatim>
          </h:panelGrid>

          <h:panelGrid columns="2">

            <f:facet name="label">

              <my:tabLabel>
                <h:outputText value="Tab-2" />
              </my:tabLabel>
            </f:facet>
            <f:verbatim><p> Some data for tab-2</f:verbatim>
         			
          </h:panelGrid>

	
		</my:panelTabbed>		
			
		</h:form>
</BODY>
</f:view>



</HTML>
