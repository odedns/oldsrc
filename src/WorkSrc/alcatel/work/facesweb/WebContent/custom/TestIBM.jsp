<%-- jsf:pagecode language="java" location="/JavaSource/pagecode/custom/TestIBM.java" --%><%-- /jsf:pagecode --%>
<%@taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<f:view>
	<%@taglib uri="http://www.ibm.com/jsf/BrowserFramework" prefix="odc"%>
	<%@taglib uri="http://www.ibm.com/jsf/html_extended" prefix="hx"%>
	<%@taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
	<%@taglib uri="http://www.ibm.com/jsf/rte" prefix="r"%>
	<%-- tpl:insert page="/theme/B-03_gray.htpl" --%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=windows-1255">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="/facesweb/theme/gray.css" type="text/css">
<%-- tpl:put name="headarea" --%>
<%@page language="java" contentType="text/html; charset=windows-1255" pageEncoding="windows-1255"%>
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="../theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>TestIBM.jsp</TITLE>
			<LINK rel="stylesheet" type="text/css" href="../theme/tabpanel.css"
				title="Style">
			<LINK rel="stylesheet" type="text/css" href="../theme/stylesheet.css"
				title="Style">
			<LINK rel="stylesheet" type="text/css" href="../theme/rte_style.css"
				title="Style">
			<LINK rel="stylesheet" type="text/css" href="../theme/tree.css"
				title="Style"><%-- /tpl:put --%></head>
<body>
<table width="760" cellspacing="0" cellpadding="0" border="0">
   <tbody>
      <tr>
         <td valign="top">
         <table class="header" cellspacing="0" cellpadding="0" border="0" width="100%">
            <tbody>
               <tr>
                  <td width="150"><img border="0" width="150" height="55" alt="Company's LOGO" src="/facesweb/theme/logo_gray.gif"></td>
                  <td></td>
               </tr>
            </tbody>
         </table>
         </td>
      </tr>
      <tr>
         <td valign="top"><!-- siteedit:navbar spec="/facesweb/theme/nav_head.html" target="topchildren" --><table class="nav_head" cellspacing="0" cellpadding="0" border="0" width="100%">
   <tbody>
      <tr>
         <td width="150"><img src="/facesweb/theme/c.gif" width="150" height="1" border="0" alt=""></td>
         <td>
         <table border="0" cellspacing="2" cellpadding="2">
            <tbody>
               <tr>
                  <td class="nav-h-highlighted">Highlighted</td>
                  <td class="nav-h-normal">Normal</td>
               </tr>
            </tbody>
         </table>
         </td>
         <td width="5"><img src="/facesweb/theme/c.gif" width="5" height="1" border="0" alt=""></td>
      </tr>
   </tbody>
</table><!-- /siteedit:navbar --></td>
      </tr>
      <tr>
         <td valign="top">
         <table border="0" cellpadding="0" cellspacing="0" width="100%">
            <tbody>
               <tr>
                  <td width="150" class="nav"><img src="/facesweb/theme/c.gif" width="150" height="1" border="0" alt=""></td>
                  <td align="center"><img src="/facesweb/theme/c.gif" width="1" height="5" border="0" alt=""></td>
               </tr>
            </tbody>
         </table>
         <!-- siteedit:navbar spec="/facesweb/theme/nav_top3.html" target="children" --><table class="nav_top3" cellspacing="0" cellpadding="0" border="0" width="100%">
   <tbody>
      <tr>
         <td width="150"><img src="/facesweb/theme/c.gif" width="150" height="1" border="0" alt=""></td>
         <td align="center">
         <table border="0" cellspacing="0" cellpadding="2">
            <tbody>
               <tr>
                  <td class="nav-t3-highlighted">Highlighted</td>
                  <td class="nav-t3-normal">Normal</td>
               </tr>
            </tbody>
         </table>
         </td>
         <td width="5"><img src="/facesweb/theme/c.gif" width="5" height="1" border="0" alt=""></td>
      </tr>
   </tbody>
</table><!-- /siteedit:navbar --></td>
      </tr>
      <tr>
         <td valign="top">
         <table border="0" width="100%" cellspacing="0" cellpadding="0">
            <tbody>
               <tr>
                  <td valign="top" width="150"><!-- siteedit:navbar spec="/facesweb/theme/nav_side.html" targetlevel="1-3" --><table class="nav_side" cellspacing="0" cellpadding="0" border="0" width="100%" height="100%">
   <tbody>
      <tr>
         <td valign="top">
         <table border="0" width="100%" cellspacing="0" cellpadding="2">
            <tbody>
               <tr>
                  <td class="nav-s-highlighted">Highlighted</td>
               </tr>
               <tr>
                  <td class="nav-s-normal">Normal</td>
               </tr>
            </tbody>
         </table>
         </td>
      </tr>
   </tbody>
</table>
<!-- /siteedit:navbar --></td>
                  <td valign="top" class="content-area"><%-- tpl:put name="bodyarea" --%><hx:scriptCollector id="scriptCollector1">
	<h:form styleClass="form" id="form1"><P>Place content here.<odc:tabbedPanel
												slantActiveRight="4" styleClass="tabbedPanel" width="562"
												slantInactiveRight="4" height="300"
												variableTabLength="false" showBackNextButton="true"
												showTabs="true" id="tabbedPanel1">
												<odc:bfPanel id="bfpanel1" name="Tab1"
													showFinishCancelButton="false">Some test for tab1<BR>
													<h:outputText styleClass="outputText" id="text1" value="Some text for tab1"></h:outputText>
													<h:inputText styleClass="inputText" id="text2">
														<hx:validateConstraint regex="^[0-9]+$" />
													</h:inputText>
												</odc:bfPanel>
												<odc:bfPanel id="bfpanel2" name="Tab2"
													showFinishCancelButton="false">Some text for tab2<BR>
													<h:dataTable border="0" cellpadding="2" cellspacing="0"
														columnClasses="columnClass1" headerClass="headerClass"
														footerClass="footerClass" rowClasses="rowClass1"
														styleClass="dataTable" id="table1" value="#{movieBean.movies}" var="movie">
														<h:column id="column1">
															<hx:inputRowSelect styleClass="inputRowSelect"
																id="rowSelect1" value="Select"></hx:inputRowSelect>
															<f:facet name="header"></f:facet>
														</h:column>
														<h:column id="name">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Name" id="text22"></h:outputText>
					</f:facet>
					<h:commandLink styleClass="commandLink" id="link2" action="#{movieBean.movieDetails}">
									<h:outputText styleClass="outputText" value="#{movie.name}" />
					</h:commandLink>
				</h:column>
				<h:column id="year1">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Year" id="text3"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.year}" />
				</h:column>
				<h:column id="director1">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Director" id="text4"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.director}" />
				</h:column>
				<h:column id="gender">
					<f:facet name="header">
						<h:outputText styleClass="outputText" value="Gender" id="text5"></h:outputText>
					</f:facet>
					<h:outputText styleClass="outputText" value="#{movie.genderString}" />
				</h:column>
														
														<f:facet name="header">
															<hx:panelBox styleClass="panelBox" id="box1"></hx:panelBox>
														</f:facet>
														<f:facet name="footer">
															<hx:panelBox styleClass="panelBox" id="box2"></hx:panelBox>
														</f:facet>
													</h:dataTable>
												</odc:bfPanel>
												
													
												
												<f:facet name="back">
													<hx:commandExButton type="submit" value="&lt; Back"
														id="tabbedPanel1_back" style="display:none"></hx:commandExButton>
												</f:facet>
												<f:facet name="next">
													<hx:commandExButton type="submit" value="Next &gt;"
														id="tabbedPanel1_next" style="display:none"></hx:commandExButton>
												</f:facet>
												<f:facet name="finish">
													<hx:commandExButton type="submit" value="Finish"
														id="tabbedPanel1_finish" style="display:none"></hx:commandExButton>
												</f:facet>
												<f:facet name="cancel">
													<hx:commandExButton type="submit" value="Cancel"
														id="tabbedPanel1_cancel" style="display:none"></hx:commandExButton>
												</f:facet>
											</odc:tabbedPanel></P>
											
												
												</h:form>
	
	
	
	
	</hx:scriptCollector><%-- /tpl:put --%></td>
               </tr>
            </tbody>
         </table>
         </td>
      </tr>
      <tr>
         <td valign="top"><!-- siteedit:navbar spec="/facesweb/theme/footer.html" target="sibling" --><table class="footer" cellspacing="0" cellpadding="0" width="100%" border="0">
   <tbody>
      <tr>
         <td width="5"><img src="/facesweb/theme/c.gif" width="5" height="1" border="0" alt=""></td>
         <td>
         <table border="0" cellspacing="0" cellpadding="2">
            <tbody>
               <tr>
                  <td class="nav-f-highlighted">Highlighted</td>
                  <td class="nav-f-normal">Normal</td>
               </tr>
            </tbody>
         </table>
         </td>
         <td width="5"><img src="/facesweb/theme/c.gif" width="5" height="1" border="0" alt=""></td>
      </tr>
   </tbody>
</table><!-- /siteedit:navbar --></td>
      </tr>
   </tbody>
</table>
</body>
</html><%-- /tpl:insert --%>
</f:view>