<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.service.sampleLogin" %>
<%@ page import="com.service.command.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.HttpException" %>
<%
	sampleLogin jsonDAO	= sampleLogin.getInstance();

	String command	= StringUtil.nullTo(request.getParameter("command"),"");

	String resultMessage = null;
	String id = null;
	String password = null;
	if (command.equals("login")) {
		request.setCharacterEncoding("utf-8");
		id = request.getParameter("email");
		password = request.getParameter("password");
		resultMessage = jsonDAO.validateID(id, password);
	} else {
		//resultMessage.add(0, "Anyway... FAIL!!");
	}
	
%>
<%=resultMessage%>