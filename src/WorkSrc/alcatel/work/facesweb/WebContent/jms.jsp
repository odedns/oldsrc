<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ page language="java" contentType="text/html; charset=windows-1255"
	pageEncoding="windows-1255"%>
<%@page import="javax.naming.*,javax.jms.*" %>


<META http-equiv="Content-Type"
	content="text/html; charset=windows-1255">
<META name="GENERATOR" content="IBM Software Development Platform">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="theme/Master.css" rel="stylesheet" type="text/css">
<TITLE>jms.jsp</TITLE>
</HEAD>
<BODY>
<P>Jms Test.</P>

<FORM action="jms.jsp">
	<BR>Message Type: <INPUT type="text" name="msgType" />
	<BR>Message Text:
	<BR> <TEXTAREA name="msgText" rows="5" cols="40"></TEXTAREA>
	<BR> <INPUT type="submit" name="submit" />
</FORM>
<%

	String msgType = request.getParameter("msgType");
	if(msgType != null || "".equals(msgType)) {
		String msgText= request.getParameter("msgType");
		try {	
			InitialContext ctx = new InitialContext();
			QueueConnectionFactory qcf = (QueueConnectionFactory) ctx.lookup("jms/qcf");
			System.out.println("got connection Factory");		
			QueueConnection qcon = qcf.createQueueConnection();
		    QueueSession qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
	    	Queue queue = (Queue) ctx.lookup("jms/q1");
		    QueueSender qsender = qsession.createSender(queue);
    		TextMessage msg = qsession.createTextMessage();
		    qcon.start();
    	    msg.setText(msgText);
        	msg.setJMSType(msgType);
		    qsender.send(msg);    
		    out.println("<p> sent message: " + msg.getText());   	    
		} catch(Exception e) {
			e.printStackTrace();
			// out.println("error : " + e);
		}
	
	} else {
		out.println("Enter data");
	}
 %>

</BODY>
</HTML>
