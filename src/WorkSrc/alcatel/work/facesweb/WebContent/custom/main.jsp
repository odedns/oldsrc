<%@include file="genheader.jsp" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<html>
<body>
<f:view>

	<div id="pageFrame">
	<c:import url="header.jsp" />
  
	<f:subview id="leftView" >
		<c:import url="left.jsp" />
	</f:subview>
	
	<DIV id="contentColumn">
	<div id="innerContentColum">

		<H1> This is the content area </H1>

	<p>
	This is a simple two column layout with a left menu box. 
	By modifying the stylesheet, this layout can serve as the basis for many standard two column layouts. As is the case with most layouts in the Reservoir, the order of elements (header, content, menu) in the HTML source is friendly and accessible to mobile computers, 
	text-based browsers, 
	and alternative/accessible devices.
	Much effort has been made to ensure that the layouts in the BlueRobot Layout Reservoir 
	appear as intended in CSS2 compliant browsers. The content should be viewable, 
	though unstyled, in other web browsers. If you encounter a problem that is not listed as a known issue, I am most likely not aware of it. Please email me a heads-up. Your help will benefit the other five or six people who visit this site.
	</p>
	<p> some more fucking text </p>
<br>
sss
</div>
</DIV>
				
	<c:import url="footer.jsp" />
	
</div>
</body>
</f:view>


</HTML>
