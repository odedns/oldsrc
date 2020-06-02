<%
	String userid = request.getParameter("userid");
	if(userid != null){
		request.setAttribute("userid", userid);
%>
<jsp:forward page="cs"/>
<%
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML dir="rtl">
<HEAD>
<%@ page language="java" contentType="text/html; charset=WINDOWS-1255" pageEncoding="WINDOWS-1255" %>
<%@ page session="false" %>
<META http-equiv="Content-Type" content="text/html; charset=WINDOWS-1255">
<META name="GENERATOR" content="IBM WebSphere Studio">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/system.css" rel="stylesheet" type="text/css">
<TITLE>Login</TITLE>
</HEAD>
<body class="body">
	<form method="post">
		<table width="100%" height="100%" cellpadding="0" cellspacing="0" style="text-align: center;">
			<tr>
				<td class="headerTitle">מסך כניסה למערכת אופק</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" cellspacing="20">
						<tr>
							<td>
								<table cellpadding="0" cellspacing="8">
									<tr>
										<td class="label">משתמש:</td>				
										<td>
											<SELECT name="userid" style="width: 80px;">
												<option value="1">user1</option>
												<option value="2">user2</option>
												<option value="3">user3</option>
												<option value="4">user4</option>
												<option value="5">user5</option>
											</SELECT>
										</td>
									</tr>
									<tr>
										<td></td>
										<td><br><br><input type="submit" value="התחבר" class="buttonEnabled"></td>
									</tr>
								</table>
							</td>
							<td>
								<img src="images/heb/Harel_LogoBig.jpg" width="270" height="396"/>
							</td>
						</tr>
					</table>
				</td>						
			</tr>
		</table>		
	</form>	
</body>
</HTML>

