<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpServletRequest" %>
<%@ page import="com.service.sampleSelect" %>
<%@ page import="com.service.command.util.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@ page import="org.apache.commons.httpclient.methods.PostMethod" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.HttpException" %>
<%
	sampleSelect jsonDAO	= sampleSelect.getInstance();

	String command	= StringUtil.nullTo(request.getParameter("command"),"");

	String resultMessage = null;
	
	if (command.equals("login")) {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		resultMessage = jsonDAO.selectBrand();
	} else {
		//resultMessage.add(0, "Anyway... FAIL!!");
	}
	
%>
<%=resultMessage%>